package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.domain.EaseUser;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.TalkGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/26.
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<TalkGroup> talkGroups=new ArrayList<>();
    private ArrayList<ArrayList<EaseUser>>  users=new ArrayList<>();
    private Context mContext;

    public MyExpandableAdapter(ArrayList<TalkGroup> talkGroups, ArrayList<ArrayList<EaseUser>> users, Context mContext) {
        this.talkGroups = talkGroups;
        this.users = users;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return talkGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return users.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return talkGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return users.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.fragmenttalk_group_item,parent, false);
            viewHolder=new ViewHolder();
            viewHolder.tv_name=(TextView)convertView.findViewById(R.id.fragmenttalk_item_group_tv_name);
            viewHolder.tv_state=(TextView)convertView.findViewById(R.id.fragmenttalk_item_group_tv_state);
            viewHolder.img=(ImageView)convertView.findViewById(R.id.fragmenttalk_item_group_tv_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        TalkGroup talkGroup=talkGroups.get(groupPosition);
        viewHolder.tv_name.setText(talkGroup.getName());
        viewHolder.tv_state.setText(talkGroup.getState());

        if(isExpanded&&!talkGroup.getState().equals("")){
            viewHolder.img.setImageResource(R.drawable.icon02);
        }else {
            viewHolder.img.setImageResource(R.drawable.icon01);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.fragmenttalk_child_item,parent, false);
            viewHolder=new ViewHolder();
            viewHolder.img=(SimpleDraweeView)convertView.findViewById(R.id.fragmengtalk_child_item_img);
            viewHolder.tv_name=(TextView)convertView.findViewById(R.id.fragmengtalk_child_item_tv_name);
            viewHolder.tv_state=(TextView)convertView.findViewById(R.id.fragmengtalk_child_item_tv_state);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        EaseUser talkBeans =users.get(groupPosition).get(childPosition);
        viewHolder.tv_name.setText(talkBeans.getNick());
        viewHolder.tv_state.setText(talkBeans.getState());
        Uri uri = Uri.parse(talkBeans.getAvatar());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolder{
        private TextView tv_name,tv_state;
        private ImageView img;
    }
}
