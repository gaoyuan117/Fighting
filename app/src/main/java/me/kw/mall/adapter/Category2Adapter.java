
package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.MyLayoutAdapter;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.ProductCategory;

public class Category2Adapter extends CommonAdapter {

  private int itemWidth;

  public Category2Adapter(Context context, List<?> list) {
    super(context, list);

    int contentWidth = (int) (MyLayoutAdapter.getInstance().getScreenWidth() * 3 / 4);
    itemWidth = (int) (contentWidth - 40 * MyLayoutAdapter.getInstance().getDensityRatio()) / 3;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_category2, null);
      convertView.setLayoutParams(new AbsListView.LayoutParams(itemWidth, itemWidth));
    }

    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewReviewTitle = findViewById(convertView, R.id.tvew_review_title_show);

    imgvewPhoto.getLayoutParams().width = itemWidth - 55;
    imgvewPhoto.getLayoutParams().height = (itemWidth - 50) * 3 / 5;

    final ProductCategory.CategoryItem category = (ProductCategory.CategoryItem) mList.get
        (position);
    tvewReviewTitle.setText(category.title);
    mImageLoader.displayImage(Endpoint.HOST + category.image, imgvewPhoto, options);

    return convertView;
  }
}