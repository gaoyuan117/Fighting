package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiangying.fighting.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/1/23.
 */

public class GroupUserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GroupUsers.DataBean> mList;
    private LayoutInflater mInflater;

    public GroupUserAdapter(Context context, ArrayList<GroupUsers.DataBean> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolider holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.groupmember_item, null);
            holder = new ViewHolider(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolider) convertView.getTag();
        }
        String path = mList.get(position).getHeadimg();
        Glide.with(context).load(path).error(R.drawable.default_image).into(holder.img);


        holder.name.setText(mList.get(position).getNickname());
        return convertView;
    }

    class ViewHolider {
        ImageView img;
        TextView name;

        public ViewHolider(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.fragmengtalk_child_item_img);
            name = (TextView) convertView.findViewById(R.id.fragmengtalk_child_item_tv_name);
        }
    }
}
