package com.xiangying.fighting.ui.two.findrent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.MyRentBean;
import com.xiangying.fighting.widget.FontTextView;

import java.util.List;

/**
 * Created by admin on 2017/1/19.
 */

public class MyRentAdapter extends BaseAdapter{
    private Context context;
    private List<MyRentBean.Data> mList;
    private LayoutInflater mInflater;

    public MyRentAdapter(Context context, List<MyRentBean.Data> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
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
        ViewHolder holder;
        if(convertView ==null){
            convertView = mInflater.inflate(R.layout.item_rent_room,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Endpoint.HOST+mList.get(position).getImgPath().get(0)).error(R.drawable.default_image).into(holder.img);
        Logger.d(Endpoint.HOST+mList.get(position).getImgPath());
        holder.title.setText(mList.get(position).getTitle());
        holder.qu.setText(mList.get(position).getQu());
        holder.price.setText(mList.get(position).getPrice());

        return convertView;
    }

    class ViewHolder{
        ImageView img;
        FontTextView title,qu,price;

        public ViewHolder(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.item_rent_room_sdv_picture);
            title = (FontTextView) convertView.findViewById(R.id.item_rent_room_tv_title);
            qu = (FontTextView) convertView.findViewById(R.id.item_rent_room_tv_community);
            price = (FontTextView) convertView.findViewById(R.id.item_rent_room_tv_money);
        }
    }
}
