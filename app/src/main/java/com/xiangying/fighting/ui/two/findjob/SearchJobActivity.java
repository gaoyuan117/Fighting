package com.xiangying.fighting.ui.two.findjob;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.RecomentWorkBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchJobActivity extends BaseActivity implements
    PullToRefreshView.OnFooterRefreshListener,
    PullToRefreshView.OnHeaderRefreshListener {
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_tv_search)
  FontTextView titleBarCommonTvSearch;
  @Bind(R.id.title_bar_common_et_search)
  ClearEditText titleBarCommonEtSearch;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshView commonPullRefreshViewShow;

  CommonAdapter<RecomentWorkBean.Data> adaper;
  ArrayList<RecomentWorkBean.Data> mDatas = new ArrayList<>();

  @Override
  protected void process() {
    //为listView设置adapter
    adaper = new CommonAdapter<RecomentWorkBean.Data>(this, mDatas, R.layout.item_tuijian_zhiwei) {
      @Override
      public void convert(ViewHolder helper, RecomentWorkBean.Data item, int position) {
        helper.setText(R.id.tv_recom, item.getName());
        if (item.getList() == null) {
          item.setList(new ArrayList<RecomentWorkBean.Data.ListBean>());
        }

        CommonAdapter<RecomentWorkBean.Data.ListBean> adapter2 = new CommonAdapter<RecomentWorkBean.Data.ListBean>(SearchJobActivity.this, (ArrayList<RecomentWorkBean.Data.ListBean>) item.getList(), R.layout.item_quyu) {
          @Override
          public void convert(ViewHolder helper, final RecomentWorkBean.Data.ListBean item, int position) {
            helper.setText(R.id.item_quyu_tvquyu, item.getName());
            helper.setItemClick(new View.OnClickListener() {
              @Override public void onClick(View v) {
                SearchResultActivity.start(SearchJobActivity.this, item.getName());
              }
            });
          }
        };
        helper.setCommonAdapter(R.id.item_zhiwei_gridview, adapter2);
      }
    };
    commonListviewShow.setAdapter(adaper);

    View headerTextView = LayoutInflater.from(this).inflate(R.layout.layout_header_find_job, null);
    commonListviewShow.addHeaderView(headerTextView);
    //刷新控件设置监听
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    //首次刷新
    commonPullRefreshViewShow.headerRefreshing();
  }

  @Override
  protected void setAllClick() {
    /**
     * 搜索按钮监听
     */
    titleBarCommonTvSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SearchResultActivity.start(SearchJobActivity.this, titleBarCommonEtSearch.getText().toString().trim());
      }
    });

    //搜索
    titleBarCommonEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //判断是否为"Search"健
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
          if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
          }

          SearchResultActivity.start(SearchJobActivity.this,
              titleBarCommonEtSearch.getText().toString().trim());
        }
        return true;
      }
    });
  }

  @Override
  protected void initViews() {
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
    setContentView(R.layout.activity_search_job);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    loadData();
  }

  private void loadData() {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_HOTS_RECOMENT, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        RecomentWorkBean recomentWorkBean = (RecomentWorkBean) msg.obj;
        commonPullRefreshViewShow.onHeaderRefreshComplete();
        if (recomentWorkBean.getCode() == 1 && recomentWorkBean.getData().size() > 0) {
          adaper.setmDatas((ArrayList<RecomentWorkBean.Data>) recomentWorkBean.getData());
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{});
    xUtilsHelper.sendPostAuto(RecomentWorkBean.class);
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {

  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    loadData();
  }
}
