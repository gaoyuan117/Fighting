package com.xiangying.fighting.ui.first.sharehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.bumptech.glide.Glide;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ShareHouseDetailBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.GlideCircleTransform;
import com.xiangying.fighting.widget.listview.CommonAdapter;
import com.xiangying.fighting.widget.listview.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareHouseDetailActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView mTitleBarCommonIvOperate1;

  @Bind(R.id.list_item)
  ListView mListView;
  private int e_id;

  public static void start(Context context, int id) {
    Intent starter = new Intent(context, ShareHouseDetailActivity.class);
    starter.putExtra("data", id);
    context.startActivity(starter);
  }

  @Override protected void process() {

  }

  @Override protected void setAllClick() {
    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initViews() {
    e_id = getIntent().getIntExtra("data", 0);
  }

  @Override protected void loadLayout() {
    setContentView(R.layout.activity_share_house_detail);
    ButterKnife.bind(this);
  }

  @Override protected void loadNetData() {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.SHARE_COMMENT_DETAIL, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          ShareHouseDetailBean shareHouseDetailBean = (ShareHouseDetailBean) msg.obj;
          if (shareHouseDetailBean.getCode() == 1) {
              mListView.setAdapter(new CommonAdapter<ShareHouseDetailBean.Data.Energy.Comment>(ShareHouseDetailActivity.this, R.layout.item_comment, shareHouseDetailBean.getData().getEnergy().getComment()) {
                @Override protected void convert(ViewHolder holder, ShareHouseDetailBean.Data.Energy.Comment item, int position) {
                  ImageView image = holder.getView(R.id.iv_comment);
                  Glide.with(mContext).load(Endpoint.HOST + item.getHeadimg()).placeholder(R.drawable.default_image).transform(new GlideCircleTransform(mContext)).into(image);
                  holder.setText(R.id.tv_comment_nickname, item.getUsername());
                  holder.setText(R.id.tv_comment_content, item.getText());
                }
              });
          }else{

          }
        } catch (Exception e) {

          e.printStackTrace();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{{"eid", e_id + ""}});
    xUtilsHelper.sendPostAuto(ShareHouseDetailBean.class);
  }
}
