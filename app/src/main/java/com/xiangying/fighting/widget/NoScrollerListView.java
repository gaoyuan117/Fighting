/*
 * NoScrollerListView     2016/12/16 09:01
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Koterwong on 2016/12/16 09:01
 */
public class NoScrollerListView extends ListView{
  public NoScrollerListView(Context context) {
    super(context);
  }

  public NoScrollerListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoScrollerListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public NoScrollerListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
    super.onMeasure(widthMeasureSpec, expandSpec);
  }
}
