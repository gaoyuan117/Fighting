/*
 * BottomDialog     2016/10/30 11:27
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.widget.bottomlialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by Koterwong on 2016/10/30 11:27
 *
 * 底部对话框，使用方法
 * BottomDialog dialog = BottomDialog.create(getSupportFragmentManager())
 *        .setCancelOutside(false)
 *        .setLayoutRes(R.layout.dialog_edit_text)
 *        .setDimAmount(0.5f)
 *        .setViewListener(new BottomDialog.ViewListener() {
 *              @Override public void bindView(View v) {
 *
 *              }
 *        });
 * dialog.show();
 */
public class BottomDialog extends BaseBottomDialog {
  private static final String KEY_LAYOUT_RES = "bottom_layout_res";
  private static final String KEY_HEIGHT = "bottom_height";
  private static final String KEY_DIM = "bottom_dim";
  private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";

  private FragmentManager mFragmentManager;
  private boolean mIsCancelOutside = super.getCancelTouchOutside();
  private float mDimAmount = super.getDimAmount();
  private int mHeight = super.getHeight();

  @LayoutRes private int mLayoutRes;
  private ViewListener mViewListener;

  public interface ViewListener {
    void bindView(View rootView);
  }

  public static BottomDialog create(FragmentManager manager) {
    BottomDialog dialog = new BottomDialog();
    dialog.setFragmentManager(manager);
    return dialog;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
      mHeight = savedInstanceState.getInt(KEY_HEIGHT);
      mDimAmount = savedInstanceState.getFloat(KEY_DIM);
      mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
    outState.putInt(KEY_HEIGHT, mHeight);
    outState.putFloat(KEY_DIM, mDimAmount);
    outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);
    super.onSaveInstanceState(outState);
  }

  @Override protected void bindView(View dialogRootView) {
    if (mViewListener != null) {
      mViewListener.bindView(dialogRootView);
    }
  }

  @Override public int getLayoutRes() {
    return mLayoutRes;
  }

  private BottomDialog setFragmentManager(FragmentManager manager) {
    mFragmentManager = manager;
    return this;
  }

  public BottomDialog setViewListener(ViewListener listener) {
    mViewListener = listener;
    return this;
  }

  /**
   * 设置Dialog的布局
   *
   * @param layoutRes
   * @return
   */
  public BottomDialog setLayoutRes(@LayoutRes int layoutRes) {
    mLayoutRes = layoutRes;
    return this;
  }

  public BottomDialog setCancelOutside(boolean cancel) {
    mIsCancelOutside = cancel;
    return this;
  }

  public BottomDialog setDimAmount(float dim) {
    mDimAmount = dim;
    return this;
  }

  public BottomDialog setHeight(int heightPx) {
    mHeight = heightPx;
    return this;
  }

  @Override public float getDimAmount() {
    return mDimAmount;
  }

  @Override public int getHeight() {
    return mHeight;
  }

  @Override protected boolean getCancelTouchOutside() {
    return mIsCancelOutside;
  }

  /**
   * 显示对话框
   *
   * @return
   */
  public BaseBottomDialog show() {
    show(mFragmentManager);
    return this;
  }
}
