package me.kw.mall.adapter;


import android.content.Context;
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
import service.api.AdProduct;

public class IndexRecomAdapter extends CommonAdapter {

  public IndexRecomAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_product, null);
    }
    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewProductName = findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductPrice = findViewById(convertView, R.id.tvew_product_price_show);
    TextView tvewProductSales = findViewById(convertView, R.id.tvew_product_sales_show);
    TextView tvewProductNowPrice = findViewById(convertView, R.id.tvew_time_title_show);

    final AdProduct.AdItem adItem = (AdProduct.AdItem) mList.get(position);
    tvewProductPrice.setVisibility(View.GONE);
    tvewProductName.setText(adItem.title);
    tvewProductNowPrice.setText("¥" + StringUtil.formatProgress(adItem.product.price, 1));
    if (null != adItem.product.view) {
      tvewProductSales.setText("浏览量：" + adItem.product.view);
    } else {
      tvewProductSales.setVisibility(View.GONE);
    }

    if (ProductBusiness.validateImageUrl(adItem.image)) {
      mImageLoader.displayImage(Endpoint.HOST + adItem.image, imgvewPhoto, options);
    }

    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ProductBusiness.intentToProductDetailActivity(mContext, null,
            Integer.parseInt(adItem.product_id));
      }
    });
    return convertView;
  }

}
