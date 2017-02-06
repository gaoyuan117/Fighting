package com.xiangying.fighting.ui.two.jiehun;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;

import java.util.ArrayList;

/**
 * Created by xiaoniao on 2016/8/26.
 */
public class ImageGridAdapter extends BaseAdapter{

    ArrayList<String> imageList = new ArrayList<>();
    Context mContext;
    LayoutInflater inflater;


    public ImageGridAdapter(ArrayList<String> imageList, Context mContext) {
        this.imageList = imageList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }



    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        String item = imageList.get(position);

        Viewholder viewholder = null;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_select_image,null);
            viewholder = new Viewholder();
            viewholder.image = (SimpleDraweeView) convertView.findViewById(R.id.item_image_sdv_image);
            convertView.setTag(viewholder);
        }else {
            viewholder = (Viewholder) convertView.getTag();
        }
        if (position == imageList.size() - 1 && item.equals("")) {
            viewholder.image.setImageURI(Uri.parse("res://" + mContext.getPackageName() + "/" + R.drawable.icon_add));
        } else if (!item.startsWith("http")) {
            viewholder.image.setImageURI(Uri.parse("file://" + item));
        } else if (item.startsWith("http")) {
            Glide.with(mContext).load(item).into(viewholder.image);
        }
        return convertView;
    }

    /**
     * 更新数据
     * @param imageList
     */
    public void setImageList(@Nullable ArrayList<String> imageList){
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    /**
     * VIewholder
     */
    class Viewholder {
        SimpleDraweeView image;
    }
}