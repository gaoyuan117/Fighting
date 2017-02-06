/*
 * GongyiListActivity     2016/11/18 17:39
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.ui.two.gongyi;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.entity.WelfareResult;

/**
 * Created by Koterwong on 2016/11/18 17:39
 */
public class GongyiListActivity extends BaseActivity
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

  CommonAdapter adapter;
  ArrayList<WelfareResult.Data> dataList = new ArrayList<>();

  @Override
  protected void process() {

    adapter = new CommonAdapter<WelfareResult.Data>(this, dataList, R.layout.item_juankuan) {
      @Override
      public void convert(ViewHolder helper, WelfareResult.Data item, int position) {
        helper.setText(R.id.item_juankuan_object, item.title);
        helper.setText(R.id.item_juankuan_num, "目标：" + item.price + "元");
        helper.setText(R.id.item_juankuan_has, "已筹款：" + (TextUtils.isEmpty(item.hasmoney) ? "0.00" : item.hasmoney) + "元");
        helper.setText(R.id.item_juankuan_time, item.time);
      }
    };
    commonListviewShow.setAdapter(adapter);

    commonListviewShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GongyiDetailActivity.start(GongyiListActivity.this, ((WelfareResult.Data) adapter.getItem(position)).id);
      }
    });

  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    commonPullRefreshViewShow.setFooterRefreshComplete();
  }

  @Override
  protected void initViews() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBarCommonTvTitle.setText("公益");
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_juanzeng_list);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {
    requestData();
  }

  private void requestData() {
    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.API_WELFARE_INDEX, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        commonPullRefreshViewShow.onHeaderRefreshComplete();
        commonPullRefreshViewShow.onFooterRefreshComplete();
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          WelfareResult result = (WelfareResult) msg.obj;
          adapter.setmDatas((ArrayList) result.data);
        }
        return true;
      }
    }));

    helper.setRequestParams(new String[][]{});
    helper.sendPostAuto(WelfareResult.class);
  }


  @Override
  public void onFooterRefresh(PullToRefreshView view) {

  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    requestData();
  }
}
