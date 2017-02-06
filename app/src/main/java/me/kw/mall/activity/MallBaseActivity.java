/*
 * MallBaseActivity     2016/10/18 09:52
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.kw.mall.activity;

import android.os.Bundle;

import com.android.zcomponent.common.uiframe.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Koterwong on 2016/10/18 09:52
 */
public abstract class MallBaseActivity extends BaseActivity {
  protected final String TAG = this.getClass().getSimpleName();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    this.initUI();
  }

  protected abstract int getLayoutId();

  protected abstract void initUI();

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
