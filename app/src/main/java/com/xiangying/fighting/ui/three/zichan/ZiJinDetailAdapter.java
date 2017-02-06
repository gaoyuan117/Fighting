package com.xiangying.fighting.ui.three.zichan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.ZiJinDetailBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：admin on 2017/2/6 10:04
 */

public class ZiJinDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ZiJinDetailBean> mList;
    private LayoutInflater mInflater;

    public ZiJinDetailAdapter(Context context, List<ZiJinDetailBean> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_lv_zjdetail, viewGroup,false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.mTime.setText(mList.get(i).time);
        vh.mSource.setText(mList.get(i).source);
        vh.mMoney.setText(mList.get(i).money);

        return view;
    }

    class ViewHolder {
        @Bind(R.id.tv_item_zjd_time)
        TextView mTime;
        @Bind(R.id.tv_item_zjd_source)
        TextView mSource;
        @Bind(R.id.tv_item_zjd_money)
        TextView mMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
