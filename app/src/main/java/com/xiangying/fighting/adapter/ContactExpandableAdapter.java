/*
 * ContactExpandableAdapter     2016/12/21 11:15
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.domain.EaseUser;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ContactBean;
import com.xiangying.fighting.widget.ContactPop;

import java.util.List;

/**
 * Created by Koterwong on 2016/12/21 11:15
 */
public class ContactExpandableAdapter extends BaseExpandableListAdapter {

  private List<ContactBean.Data> mContactList;
  private Context mContext;

  public ContactExpandableAdapter(List<ContactBean.Data> contactList, Context context) {
    mContactList = contactList;
    mContext = context;
  }

  @Override
  public int getGroupCount() {
    return mContactList.size();
  }

  @Override public int getChildrenCount(int groupPosition) {
    return mContactList.get(groupPosition).getUsers() == null ? 0 : mContactList.get(groupPosition).getUsers().size();
  }

  @Override public Object getGroup(int groupPosition) {
    return mContactList.get(groupPosition);
  }

  @Override public Object getChild(int groupPosition, int childPosition) {
    return mContactList.get(groupPosition).getUsers().get(childPosition);
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
    viewHolder.tv_name.setText(mContactList.get(groupPosition).getGroupName());
    viewHolder.tv_state.setText("");

    if (isExpanded) {
      viewHolder.img.setImageResource(R.drawable.icon02);
    } else {
      viewHolder.img.setImageResource(R.drawable.icon01);
    }

//    convertView.setOnLongClickListener(new View.OnLongClickListener() {
//      @Override public boolean onLongClick(View v) {
//        if (mOnGroupLongClick != null) {
//          mOnGroupLongClick.longClick();
//        }
//        return true;
//      }
//    });

    return convertView;
  }

  @Override public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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

    EaseUser talkBeans = mContactList.get(groupPosition).getUsers().get(childPosition);
    viewHolder.tv_name.setText(talkBeans.getNick());
    viewHolder.tv_state.setText(talkBeans.getState());

    Uri uri = Uri.parse(talkBeans.getAvatar().startsWith("http") ? talkBeans.getAvatar() : Endpoint.HOST + talkBeans.getAvatar());
    ((SimpleDraweeView) viewHolder.img).setImageURI(uri);

    if (!TextUtils.isEmpty(talkBeans.getIs_top()) && talkBeans.getIs_top().equals("1")) {
      viewHolder.tv_zhiding.setVisibility(View.VISIBLE);
      viewHolder.moreImg.setImageResource(R.drawable.icon_red_point);
      final ViewHolder finalViewHolder = viewHolder;
      viewHolder.moreImg.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ContactPop.Create(mContext,1)
              .setOnPopClickEvent(new ContactPop.PopClickEvent() {
                @Override public void onTopClick() {
                  if (mOnPopClick != null) {
                    mOnPopClick.no_top(mContactList.get(groupPosition).getUsers().get(childPosition));
                  }
                }

                @Override public void onMoveClick() {
                  if (mOnPopClick != null) {
                    mOnPopClick.move(mContactList.get(groupPosition).getUsers().get(childPosition));
                  }
                }
              }).
              show(finalViewHolder.moreImg);
        }
      });
    } else {
      viewHolder.tv_zhiding.setVisibility(View.GONE);
      viewHolder.moreImg.setImageResource(R.drawable.icon_gray_point);

      final ViewHolder finalViewHolder = viewHolder;
      viewHolder.moreImg.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ContactPop.Create(mContext,0)
              .setOnPopClickEvent(new ContactPop.PopClickEvent() {
                @Override public void onTopClick() {
                  if (mOnPopClick != null) {
                    mOnPopClick.top(mContactList.get(groupPosition).getUsers().get(childPosition));
                  }
                }

                @Override public void onMoveClick() {
                  if (mOnPopClick != null) {
                    mOnPopClick.move(mContactList.get(groupPosition).getUsers().get(childPosition));
                  }
                }
              }).show(finalViewHolder.moreImg);
        }
      });
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
    private ImageView  tv_zhiding;;
  }

  public OnPopClick mOnPopClick;

  public void setOnPopClick(OnPopClick onPopClick) {
    mOnPopClick = onPopClick;
  }

  public interface OnPopClick{
    void top(EaseUser easeUser);

    void no_top(EaseUser easeUser);

    void move(EaseUser easeUser);
  }

  public void setOnGroupLongClick(onGroupLongClick onGroupLongClick) {
    mOnGroupLongClick = onGroupLongClick;
  }

  private onGroupLongClick mOnGroupLongClick;

  public interface onGroupLongClick{
    void longClick();
  }
}
