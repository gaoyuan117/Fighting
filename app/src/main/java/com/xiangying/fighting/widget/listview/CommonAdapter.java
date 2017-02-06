/*
 * CommonAdapter     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package com.xiangying.fighting.widget.listview;

import android.content.Context;

import com.xiangying.fighting.widget.listview.base.ItemViewDelegate;

import java.util.List;


/**
 * Created by Koterwong on 2016/9/28 17:14
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
  public CommonAdapter(Context context, final int layoutId, List<T> datas) {
    super(context, datas);
    addItemViewDelegate(new ItemViewDelegate<T>() {
      @Override public int getItemViewLayoutId() {
        return layoutId;
      }

      @Override public boolean isForViewType(T item, int position) {
        return true;
      }

      @Override public void convert(ViewHolder holder, T t, int position) {
        CommonAdapter.this.convert(holder, t, position);
      }
    });
  }

  protected abstract void convert(ViewHolder holder, T item, int position);
}
