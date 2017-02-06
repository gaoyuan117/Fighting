package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GridPhotoActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_tv_ok)
  FontTextView titleBarCommonTvOk;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.gridphoto_grid)
  GridView gridphotoGrid;
  @Bind(R.id.grid_photo_liner_delete)
  LinearLayout liner_delete;

  private CommonAdapter<String> adaper;

  private boolean isSeleteState = false;

  private ArrayList<String> paths = new ArrayList<>();
  private ArrayList<String> choicePaths = new ArrayList<>();

  private int id = -1;

  private String userNmae;

  public static void start(Context context, String userNmae) {
    Intent starter = new Intent(context, GridPhotoActivity.class);
    starter.putExtra("userid", userNmae);
    context.startActivity(starter);
  }

  @Override
  protected void process() {
  }

  @Override
  protected void setAllClick() {
    titleBarCommonTvOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isSeleteState) {
          isSeleteState = true;
          titleBarCommonTvOk.setText("取消");
        } else {
          isSeleteState = false;
          titleBarCommonTvOk.setText("选择");
        }
        setGridphotoGrid(isSeleteState);
      }
    });
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    liner_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (choicePaths.size() == 0) {
          Utils.toast(GridPhotoActivity.this, "你还没选择任何图片");
        } else {
          paths.removeAll(choicePaths);
          adaper.setmDatas(paths);
        }
      }
    });
  }

  private void setGridphotoGrid(final boolean isSeleteState) {
    adaper = new CommonAdapter<String>(this, paths, R.layout.img_item) {
      @Override
      public void convert(final ViewHolder helper, final String item, final int position) {
        helper.setSimpleDraweeViewByUrl(R.id.item_img, item);
        if (isSeleteState) {
          helper.setVivisble(R.id.item_check);
        } else {
          helper.setGone(R.id.item_check);
        }
        final CheckBox checkBox = helper.getView(R.id.item_check);
        checkBox.setChecked(false);
        helper.setItemClick(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (isSeleteState) {
              checkBox.performClick();
              if (checkBox.isChecked()) {
                choicePaths.add(item);
              } else {
                choicePaths.remove(item);
              }
              return;
            }
            Intent intent = new Intent(GridPhotoActivity.this, ImageGalleryActivity.class);
            intent.putStringArrayListExtra("images", paths);
            intent.putExtra("position", position);
            startActivity(intent);
          }
        });
      }
    };
    gridphotoGrid.setAdapter(adaper);
  }

  @Override
  protected void initViews() {
    userNmae = getIntent().getStringExtra("userid");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_grid_photo);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    List<EMMessage> emMessages = EMClient
        .getInstance()
        .chatManager()
        .getConversation(userNmae)
        .searchMsgFromDB(EMMessage.Type.IMAGE, System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 10, 16, "", EMConversation.EMSearchDirection.DOWN);

    for (EMMessage message : emMessages) {
        EMImageMessageBody messageBody =(EMImageMessageBody) message.getBody();
      paths.add(messageBody.getRemoteUrl());
    }
    setGridphotoGrid(false);
  }
}
