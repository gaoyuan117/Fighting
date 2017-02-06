package com.xiangying.fighting.ui.three.selfinfo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.zcomponent.util.TimeUtil;
import com.hymane.expandtextview.ExpandTextView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.UserNoteBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserNoteActivity extends BaseActivity
    implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView titleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_iv_operate_2)
  ImageView titleBarCommonIvOperate2;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshView commonPullRefreshViewShow;

  CommonAdapter<UserNoteBean.Data> adapter;
  ArrayList<UserNoteBean.Data> dataList = new ArrayList<>();

  @Override
  protected void process() {
  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);

    //发布新照片
    titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(UserNoteActivity.this, PublishNewNoteActivity.class));
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("日记");
    titleBarCommonIvOperate3.setImageResource(R.drawable.icon_plus);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    commonPullRefreshViewShow.setFooterRefreshComplete();
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_album);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    requestData();
  }

  @Override protected void onRestart() {
    super.onRestart();
    requestData();
  }

  private void requestData() {
    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.ME_NOTE, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (commonPullRefreshViewShow.isHeaderRefresh()) {
          commonPullRefreshViewShow.onHeaderRefreshComplete();
        }
        try {
          UserNoteBean userNoteBean = (UserNoteBean) msg.obj;
          if (userNoteBean.getData() != null && userNoteBean.getData().size() > 0) {
            dataList = (ArrayList<UserNoteBean.Data>) userNoteBean.getData();
            adapter = new CommonAdapter<UserNoteBean.Data>(UserNoteActivity.this, dataList, R.layout.item_me_note) {
              @Override
              public void convert(ViewHolder helper, UserNoteBean.Data item, int position) {
                helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(item.getCreate_time() * 1000, TimeUtil.STR_FORMAT_DATA));
                ExpandTextView expandTextView = helper.getView(R.id.expand_text_view);
                expandTextView.setContent(item.getContent());
              }
            };
            commonListviewShow.setAdapter(adapter);
          } else {
            Toast.makeText(UserNoteActivity.this, "您还没有写过日记哦", Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {

        }

        return false;
      }
    }));
    helper.setRequestParams(new String[][]{});
    helper.sendPostAuto(UserNoteBean.class);
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {
  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    requestData();
  }
}

