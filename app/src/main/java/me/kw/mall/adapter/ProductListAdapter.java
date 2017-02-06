
package me.kw.mall.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.model.ProductBusiness;
import service.api.ProductItem;

public class ProductListAdapter extends CommonAdapter {

  public ProductListAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_product_list, null);
    }

    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewProductName = findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductPrice = findViewById(convertView, R.id.tvew_product_price_show);
    TextView tvewProductSales = findViewById(convertView, R.id.tvew_product_sales_show);
    TextView tvewProductNowPrice = findViewById(convertView, R.id.tvew_time_title_show);

    final ProductItem product = (ProductItem) mList.get(position);
    tvewProductPrice.setVisibility(View.INVISIBLE);
    tvewProductName.setText(product.title);
    tvewProductNowPrice.setText("¥" + StringUtil.formatProgress(product.price, 1));

    tvewProductSales.setVisibility(View.VISIBLE);
    if (TextUtils.isEmpty(product.sales)) {
      tvewProductSales.setText("0人付款");
    } else {
      tvewProductSales.setText(product.sales + "人付款");
    }

    if (!TextUtils.isEmpty(product.view)) {
      tvewProductSales.setText(product.view + "人浏览");
    }

    if (ProductBusiness.validateImageUrl(product.image)) {
      mImageLoader.displayImage(Endpoint.HOST + product.image,
          imgvewPhoto, options);
    } else if (ProductBusiness.validateImageUrl(product.image_path)) {
      mImageLoader.displayImage(Endpoint.HOST + product.image_path,
          imgvewPhoto, options);
    }

    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ProductBusiness.intentToProductDetailActivity(mContext,
            product, Integer.parseInt(product.id));
      }
    });
    return convertView;
  }
}