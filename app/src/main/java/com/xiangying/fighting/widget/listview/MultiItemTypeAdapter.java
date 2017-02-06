/*
 * MutliItemTypeAdapter     2016/9/28-09-28
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package com.xiangying.fighting.widget.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiangying.fighting.widget.listview.base.ItemViewDelegate;
import com.xiangying.fighting.widget.listview.base.ItemViewDelegateManager;

import java.util.List;


/**
 * Created by Koterwong on 2016/9/28 15:17
 */
public class MultiItemTypeAdapter<T> extends BaseAdapter {
  private ItemViewDelegateManager mItemViewDelegateManager;

  protected Context mContext;
  protected List<T> mDatas;

  public MultiItemTypeAdapter(Context context, List<T> datas) {
    mContext = context;
    mDatas = datas;
    mItemViewDelegateManager = new ItemViewDelegateManager();
  }

  public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
    mItemViewDelegateManager.addDelegate(itemViewDelegate);
    return this;
  }

  private boolean useItemViewDelegateManager() {
    return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
  }

  @Override public int getViewTypeCount() {
    if (useItemViewDelegateManager())
      return mItemViewDelegateManager.getItemViewDelegateCount();
    return super.getViewTypeCount();
  }

  @Override public int getItemViewType(int position) {
    if (useItemViewDelegateManager()) {
      int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
      return viewType;
    }
    return super.getItemViewType(position);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get
        (position), position);
    int layoutId = itemViewDelegate.getItemViewLayoutId();
    ViewHolder viewHolder = null;
    if (convertView == null) {
      View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
      viewHolder = new ViewHolder(mContext, itemView, parent, position);
      onViewHolderCreate(viewHolder, viewHolder.getConvertView());
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
      viewHolder.mPosition = position;
    }
    convert(viewHolder, getItem(position), position);
    return viewHolder.getConvertView();
  }

  public void onViewHolderCreate(ViewHolder viewHOlder, View convertView) {}

  protected void convert(ViewHolder holder, T item, int position) {
    mItemViewDelegateManager.convert(holder, item, position);
  }

  @Override public int getCount() {
    return mDatas.size();
  }

  @Override public T getItem(int position) {
    return mDatas.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }
}
