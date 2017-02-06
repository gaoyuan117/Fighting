package com.xiangying.fighting.ui.first.sharehouse;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.first.sharehouse.bean.Community;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.RefreshHandler;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EnergyFragment extends BaseFragment
    implements PullToRefreshLayout.OnRefreshListener {
  @Bind(R.id.fragmenttalk_sharehouse)
  PullableListView fragmenttalkSharehouse;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshLayout commonPullRefreshViewShow;

  CommunityAdapter adapter;
  ArrayList<Community> communities = new ArrayList<>();

  private int pageIndex = 1, pageSize = 15;

  public static Fragment newInstance() {
    Fragment fragment = new EnergyFragment();
    return fragment;
  }

  @Override
  protected void process() {
    mImlRefreshHandler = new ImlRefreshHandler(commonPullRefreshViewShow);
  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnRefreshListener(this);
  }

  @Override
  protected void initViews(View view) {
    adapter = new CommunityAdapter(context);
    commonPullRefreshViewShow.setCanPullDown(true);
    commonPullRefreshViewShow.setCanPullUp(false);
    fragmenttalkSharehouse.setAdapter(adapter);
  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.fragmentcommunity, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void loadNetData() {
    loadData(1 + "");
  }

  private void loadData(String page) {
    XUtilsHelper helper = new XUtilsHelper(context, NetworkTools.SHARE_LIST, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {

        return false;
      }
    }));

    helper.setRequestParams(new String[][]{
        {"page", page + ""},
        {"pageSize", pageSize + ""}
    });

//    helper.sendPostAuto(Community.class);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onRefresh() {
    mImlRefreshHandler.onPullDownToRefresh();
  }

  @Override public void onLoadMore() {
    mImlRefreshHandler.onPullUpToRefresh(isEnd);
  }

  private ImlRefreshHandler mImlRefreshHandler;
  private boolean isEnd = false;

  private class ImlRefreshHandler extends RefreshHandler{

    public ImlRefreshHandler(PullToRefreshLayout pullToRefreshLayout) {
      super(pullToRefreshLayout);
    }

    @Override public void loadNextData(int page) {
      if (page == 1) {
        adapter.removeAll();
        loadData(1 + "");
      }
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mImlRefreshHandler.onActivityDestory();
  }
}
