/*
 * GroupExpandableAdapter     2016/12/23 09:57
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koterwong on 2016/12/23 09:57
 */
public class GroupExpandableAdapter extends BaseExpandableListAdapter {
  private List<String> mGroup = new ArrayList<>();
  private List<GroupBean.Data> mGroupList;
  private Context mContext;

  public GroupExpandableAdapter(List<GroupBean.Data> groupList, Context context, List<String> group) {
    mGroupList = groupList;
    mContext = context;
    mGroup = group;
  }

  @Override public int getGroupCount() {
    return mGroup.size();
  }

  @Override public int getChildrenCount(int groupPosition) {
    if (groupPosition == 0) {
      return mGroupList.size();
    } else {
      return 0;
    }
  }

  @Override public Object getGroup(int groupPosition) {
    return mGroup.get(groupPosition);
  }

  @Override public Object getChild(int groupPosition, int childPosition) {
    return null;
  }

  @Override public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override public boolean hasStableIds() {
    return false;
  }

  @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.fragmenttalk_group_item, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.tv_name = (TextView) convertView.findViewById(R.id.fragmenttalk_item_group_tv_name);
      viewHolder.tv_state = (TextView) convertView.findViewById(R.id.fragmenttalk_item_group_tv_state);
      viewHolder.img = (ImageView) convertView.findViewById(R.id.fragmenttalk_item_group_tv_img);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    viewHolder.tv_name.setText(mGroup.get(groupPosition));
    viewHolder.tv_state.setText("");

    if (isExpanded) {
      viewHolder.img.setImageResource(R.drawable.icon02);
    } else {
      viewHolder.img.setImageResource(R.drawable.icon01);
    }
    return convertView;
  }

  @Override public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.fragmenttalk_child_item, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.img = (SimpleDraweeView) convertView.findViewById(R.id.fragmengtalk_child_item_img);
      viewHolder.tv_name = (TextView) convertView.findViewById(R.id.fragmengtalk_child_item_tv_name);
      viewHolder.tv_state = (TextView) convertView.findViewById(R.id.fragmengtalk_child_item_tv_state);
      viewHolder.tv_zhiding = (ImageView) convertView.findViewById(R.id.id_zhiding);
      viewHolder.moreImg = (ImageView) convertView.findViewById(R.id.ic_more);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    if (groupPosition == 0) {
      viewHolder.tv_zhiding.setVisibility(View.GONE);
      viewHolder.moreImg.setVisibility(View.GONE);
      GroupBean.Data groupBean = mGroupList.get(childPosition);
      viewHolder.tv_name.setText(groupBean.getName());
      viewHolder.tv_state.setText("[" + groupBean.getCountMember() + "]");
      if (groupBean.getImgPath() != null) {
        Uri uri = Uri.parse(groupBean.getImgPath().startsWith("http") ? groupBean.getImgPath() : Endpoint.HOST + groupBean.getImgPath());
        ((SimpleDraweeView) viewHolder.img).setImageURI(uri);
      }
    }
    return convertView;
  }

  @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  public static class ViewHolder {
    private TextView tv_name, tv_state;
    private ImageView moreImg;
    private ImageView img;
    private ImageView tv_zhiding;
  }
}
