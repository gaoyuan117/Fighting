package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.FriendGrouplistBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.listview.CommonAdapter;
import com.xiangying.fighting.widget.listview.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class UserFromListActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView mTitleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView mTitleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView mTitleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_iv_operate_2)
  ImageView mTitleBarCommonIvOperate2;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout mTitleBarCommonRvViewGroup;
  @Bind(R.id.listview) ListView mListview;
  @Bind(R.id.activity_user_from_list)
  RelativeLayout mActivityUserFromList;

  private String friendName;
  public static void start(Context context,String f_username) {
    Intent starter = new Intent(context, UserFromListActivity.class);
    starter.putExtra("f_username",f_username);
    context.startActivity(starter);
  }

  @Override protected void process() {

  }

  @Override protected void setAllClick() {

  }

  @Override protected void initViews() {
    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    mTitleBarCommonIvOperate3.setVisibility(View.GONE);
    mTitleBarCommonTvTitle.setText("移动联系人");

    friendName = getIntent().getStringExtra("f_username");
  }

  @Override protected void loadLayout() {
    setContentView(R.layout.activity_user_from_list);
    ButterKnife.bind(this);
  }

  @Override protected void loadNetData() {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_FRIEND_GROUP, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          final FriendGrouplistBean bean = (FriendGrouplistBean) msg.obj;
          if (bean.getCode() == 1 && bean.getData().size() > 1) {
            mListview.setAdapter(new CommonAdapter<FriendGrouplistBean.Data>(UserFromListActivity.this,android.R.layout.simple_list_item_1,bean.getData()) {
              @Override protected void convert(ViewHolder holder, FriendGrouplistBean.Data item, int position) {
                holder.setText(android.R.id.text1, item.getName());
              }
            });

            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveFrient(bean.getData().get(position));
              }
            });
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{});
    xUtilsHelper.sendPostAuto(FriendGrouplistBean.class);
  }

  private void moveFrient(FriendGrouplistBean.Data data) {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_FRIEND_ADD_TO_GROUP, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
          finish();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{{"friend_name", friendName}, {"group_id", data.getId()}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }
}
