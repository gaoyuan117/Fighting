/*
 * BaseFragment     2016/10/17 16:10
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.kw.mall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Koterwong on 2016/10/17 16:10
 */
public abstract class MallBaseFragment
    extends com.android.zcomponent.common.uiframe.fragment.BaseFragment {
  protected final String TAG = this.getClass().getSimpleName();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View inflate = inflater.inflate(getLayoutId(), container, false);
    ButterKnife.bind(this, inflate);
    return inflate;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.initUI();
  }

  protected abstract int getLayoutId();

  protected abstract void initUI();

  @Override public void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
