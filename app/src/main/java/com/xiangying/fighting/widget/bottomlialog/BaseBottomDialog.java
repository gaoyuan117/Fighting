/*
 * BaseBottomDialog     2016/10/30 09:59
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.widget.bottomlialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.xiangying.fighting.R;


/**
 * Created by Koterwong on 2016/10/30 09:59
 *
 * custom base bottom dialog,which instance of DialogFragment。
 */
public abstract class BaseBottomDialog extends DialogFragment {
  protected final String mTag = this.getClass().getSimpleName();
  protected static final float DEFAULT_DIM = 0.2f;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    View view = inflater.inflate(getLayoutRes(), container, false);
    bindView(view);
    return view;
  }

  @Override public void onStart() {
    super.onStart();
    WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
    getDialog().setCanceledOnTouchOutside(getCancelTouchOutside());
    if (getHeight() > 0) {
      params.height = getHeight();
    } else {
      params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
    params.dimAmount = getDimAmount();
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.gravity = Gravity.BOTTOM;
    getDialog().getWindow().setAttributes(params);
  }

  @LayoutRes protected abstract int getLayoutRes();

  protected abstract void bindView(View view);

  protected float getDimAmount() {
    return DEFAULT_DIM;
  }

  protected boolean getCancelTouchOutside() {
    return true;
  }

  protected int getHeight() {
    return -1;
  }

  public void show(FragmentManager fragmentManager) {
    show(fragmentManager,mTag);
  }
}
