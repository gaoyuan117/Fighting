/*
 * RefreshHandler     2016/12/9 13:20
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by Koterwong on 2016/12/9 13:20
 */
public abstract class RefreshHandler extends Handler {

  /** 发送handler的what */
  private static final int PULL_DOWM_REFRESH = 0;
  private static final int PULL_UP_REFRESH = 1;

  /** 默认加载时间 ,防止刷新过快，让子弹飞一会 */
  private static int DOWN_DEFAULT_TIME = 200;
  private static int UP_DEFAULT_TIME = 200;

  /** 可以上拉刷新和下拉加载的Layout */
  private WeakReference<PullToRefreshLayout> mPullToRefreshLayout;

  /**　默认页数　*/
  private int mPage = 1;

  public RefreshHandler(PullToRefreshLayout pullToRefreshLayout) {
    mPullToRefreshLayout = new WeakReference<>(pullToRefreshLayout);
  }

  @Override public void handleMessage(Message msg) {
    int what = msg.what;

    boolean isEnd = false;
    Object o = msg.obj;
    if (null != o) {
      isEnd = (boolean) msg.obj;
    }

    switch (what) {
      case PULL_UP_REFRESH:
        if (!isEnd) {
          mPage++;
          loadNextData(mPage);
        }else{
          Toast.makeText(mPullToRefreshLayout.get().getContext(), "亲已经没有数据咯~", Toast.LENGTH_SHORT).show();
        }
        break;
      case PULL_DOWM_REFRESH:
        resetPage();
        loadNextData(mPage);
        break;
    }

    if (isEnd) {
      mPullToRefreshLayout.get().loadmoreFinish(-1);
    }
  }

  /**
   * 将PullToRefresh的回调方法交给此方法处理。
   */
  public void onPullDownToRefresh() {
    Message msg = Message.obtain();
    msg.what = PULL_DOWM_REFRESH;
    sendMessageDelayed(msg, DOWN_DEFAULT_TIME);
  }

  /**
   * 将PullToRefresh的回调方法交给此方法处理。
   */
  public void onPullUpToRefresh(boolean isEnd) {
    Message msg = Message.obtain();
    msg.what = PULL_UP_REFRESH;
    msg.obj = isEnd;
    sendMessageDelayed(msg, UP_DEFAULT_TIME);
  }

  /**
   * 下拉加载完成，调用该方法。
   */
  public void onDownToRefreshComplete() {
    mPullToRefreshLayout.get().refreshFinish(DateUtils.getTimestampString(new Date(System.currentTimeMillis())));
  }

  /**
   * 上啦加载更多调用该方法。
   */
  public void onUpToRefreshComplete() {
    mPullToRefreshLayout.get().loadmoreFinish(1);
  }

  public abstract void loadNextData(int page);

  public void resetPage() {
    mPage = 1;
  }

  public void onActivityDestory() {
    removeMessages(PULL_DOWM_REFRESH);
    removeMessages(PULL_UP_REFRESH);

    mPullToRefreshLayout.clear();
    mPullToRefreshLayout = null;
  }
}
