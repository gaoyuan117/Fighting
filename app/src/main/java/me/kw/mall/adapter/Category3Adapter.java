
package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.ProductCategory;

public class Category3Adapter extends CommonAdapter {
  private int miSelectPosition = -1;

  public Category3Adapter(Context context, List<?> list) {
    super(context, list);
  }

  public void setSelectPosition(int position) {
    miSelectPosition = position;
    notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_category3, null);
    }

    TextView tvewReviewTitle = findViewById(convertView, R.id.tvew_title_show);
    final ProductCategory.CategoryItem category = (ProductCategory.CategoryItem) mList.get(position);
    tvewReviewTitle.setText(category.title);
    if (miSelectPosition == position) {
      tvewReviewTitle.setTextColor(getColor(R.color.red));
    } else {
      tvewReviewTitle.setTextColor(getColor(R.color.black));
    }
    return convertView;
  }
}