/*
 * ItemViewD     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package com.xiangying.fighting.widget.listview.base;


import com.xiangying.fighting.widget.listview.ViewHolder;

/**
 * Created by Koterwong on 2016/9/28 13:36
 * Each viewType should implement this interface 。each viewType matches it's delegate.
 *
 * @see CommonAdapter
 */
public interface ItemViewDelegate<T> {
  /**
   * @return viewType's layout id.
   */
  int getItemViewLayoutId();

  /**
   * to judge is the item data use for this position type
   */
  boolean isForViewType(T item, int position);

  /**
   * convert data to your views
   */
  void convert(ViewHolder holder, T t, int position);
}
