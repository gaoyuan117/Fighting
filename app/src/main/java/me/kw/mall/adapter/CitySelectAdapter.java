package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.Area;
import service.api.City;
import service.api.Province;

public class CitySelectAdapter extends CommonAdapter {
  private int miSelectPosition = -1;

  public CitySelectAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_category1, null);
    }

    LinearLayout llayoutBg = findViewById(convertView, R.id.llayout_category_bg_show);
    TextView tvewReviewTitle = findViewById(convertView, R.id.tvew_review_title_show);

    if (mList.get(position) instanceof Province.Data) {
      Province.Data province = (Province.Data) mList.get(position);
      tvewReviewTitle.setText(province.province);
    } else if (mList.get(position) instanceof City.Data) {
      City.Data city = (City.Data) mList.get(position);
      tvewReviewTitle.setText(city.city);
    } else if (mList.get(position) instanceof Area.Data) {
      Area.Data area = (Area.Data) mList.get(position);
      tvewReviewTitle.setText(area.area);
    }

    if (miSelectPosition == position) {
      tvewReviewTitle.setTextColor(getColor(R.color.red));
      llayoutBg.setBackgroundColor(getColor(R.color.gray_white));
    } else {
      tvewReviewTitle.setTextColor(getColor(R.color.black));
      llayoutBg.setBackgroundColor(getColor(R.color.white));
    }
    return convertView;
  }
}