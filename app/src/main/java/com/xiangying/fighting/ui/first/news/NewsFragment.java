/*
 * NewsFragment     2016/12/12 10:30
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.ui.first.news;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.NewsListAdapter;
import com.xiangying.fighting.bean.NewsListBean;
import com.xiangying.fighting.bean.NewsTitleBean;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.common.recyclerviewadapter.BaseAdapterHelper;
import com.xiangying.fighting.common.recyclerviewadapter.QuickAdapter;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.RefreshHandler;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

import static android.content.Context.SENSOR_SERVICE;


/**
 * Created by Koterwong on 2016/12/12 10:30
 */
public class NewsFragment extends BaseFragment
    implements PullToRefreshLayout.OnRefreshListener {
  @Bind(R.id.fragmenttalk_sharehouse)
  PullableListView fragmenttalkSharehouse;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshLayout commonPullRefreshViewShow;

  NewsListAdapter mAdapter;
  List<NewsListBean.Data.Result> mListData = new ArrayList<>();

  SensorManager sensorManager;
  JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;

  private String mType;
  private int pageIndex = 1, pageSize = 10;
  private QuickAdapter<NewsTitleBean.Data> mQuickAdapter;

  @Deprecated
  public static Fragment newInstance(String typeId) {
    Bundle args = new Bundle();
    args.putString("type", typeId);
    NewsFragment fragment = new NewsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public static Fragment newInstance() {
    NewsFragment fragment = new NewsFragment();
    return fragment;
  }

  @Override
  protected void process() {
    mImlRefreshHandler = new NewsFragment.ImlRefreshHandler(commonPullRefreshViewShow);
  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnRefreshListener(this);
    fragmenttalkSharehouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });
  }

  @Override
  protected void initViews(View view) {
    sensorManager = (SensorManager) mActivity.getSystemService(SENSOR_SERVICE);
    sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    commonPullRefreshViewShow.setCanPullDown(true);
    commonPullRefreshViewShow.setCanPullUp(true);
    mAdapter = new NewsListAdapter(mActivity, mListData);
    fragmenttalkSharehouse.setAdapter(mAdapter);
  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.fragmentcommunity, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void loadNetData() {
    loadCategory();
  }

  private void loadCategory() {
    XUtilsHelper mHelper = new XUtilsHelper(context, NetworkTools.NEWS_TITLE, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        NewsTitleBean newsTitle = (NewsTitleBean) msg.obj;
        if (newsTitle != null && newsTitle.getData().size() > 0) {
          mType = newsTitle.getData().get(0).getId();
          View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_header_category, null);
          RecyclerView recyclerView = (RecyclerView) headView.findViewById(R.id.recyclerview);
          recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 5));
          mQuickAdapter = new QuickAdapter<NewsTitleBean.Data>(mActivity, R.layout.item_news_categroy, newsTitle.getData()) {
            @Override protected void convert(BaseAdapterHelper helper, final NewsTitleBean.Data item, int position) {
              if (item.getId().equals(mType)) {
                helper.setTextColor(R.id.tv_news_category, Color.parseColor("#ffffff"));
                helper.setBackgroundRes(R.id.tv_news_category, R.drawable.item_category_red_bg);
              } else {
                helper.setTextColor(R.id.tv_news_category, Color.parseColor("#000000"));
                helper.setBackgroundRes(R.id.tv_news_category, R.drawable.item_category_grey_bg);
              }

              helper.setText(R.id.tv_news_category, item.getTitle());
              helper.setOnClickListener(R.id.tv_news_category, new View.OnClickListener() {
                @Override public void onClick(View v) {
                  reloadNewCategoryData(item.getId());
                  mQuickAdapter.notifyDataSetChanged();
                }
              });
            }
          };
          recyclerView.setAdapter(mQuickAdapter);
          fragmenttalkSharehouse.addHeaderView(headView);
        }
        return false;
      }
    }));
    mHelper.setRequestParams(new String[][]{});
    mHelper.sendPostAuto(NewsTitleBean.class);
  }

  private void reloadNewCategoryData(String id) {
    if (mType == id) {
      return;
    }
    mType = id;
    mAdapter.clear();
    loadData(1 + "");
  }

  private void loadData(final String page) {
    XUtilsHelper helper = new XUtilsHelper(context, NetworkTools.NEWS_LIST, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        NewsListBean newsListBean = (NewsListBean) msg.obj;
        if (newsListBean.getCode() == 1 && newsListBean.getData().getTotal() > (Integer.valueOf(page) * pageSize - pageSize)) {
          mAdapter.addAll(newsListBean.getData().getResult());
        } else {
          isEnd = true;
        }
        mImlRefreshHandler.onDownToRefreshComplete();
        mImlRefreshHandler.onUpToRefreshComplete();
        return false;
      }
    }));

    helper.setRequestParams(new String[][]{
        {"page", page + ""},
        {"pageSize", pageSize + ""},
        {"type_id", mType}
    });
    helper.sendPostAuto(NewsListBean.class);
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

  private NewsFragment.ImlRefreshHandler mImlRefreshHandler;
  private boolean isEnd = false;

  private class ImlRefreshHandler extends RefreshHandler {

    public ImlRefreshHandler(PullToRefreshLayout pullToRefreshLayout) {
      super(pullToRefreshLayout);
    }

    @Override public void loadNextData(int page) {
      if (page == 1) {
        mAdapter.clear();
        loadData(1 + "");
      } else {
        loadData(page + "");
      }
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mImlRefreshHandler.onActivityDestory();
  }

  @Override public void onResume() {
    super.onResume();
    Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    getFocus();
  }

  @Override
  public void onPause() {
    super.onPause();
    sensorManager.unregisterListener(sensorEventListener);
    JCVideoPlayer.releaseAllVideos();
  }

  private void getFocus() {
    getView().setFocusable(true);
    getView().setFocusableInTouchMode(true);
    getView().requestFocus();
    getView().setOnKeyListener(new View.OnKeyListener() {

      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
          // 监听到返回按钮点击事件
          if (JCVideoPlayer.backPress()) {
            return true;// 未处理
          }
        }
        return false;
      }
    });
  }

}
