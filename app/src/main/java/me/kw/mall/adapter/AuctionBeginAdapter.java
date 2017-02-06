
package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.model.ProductBusiness;
import service.api.ProductItem;

public class AuctionBeginAdapter extends CommonAdapter {

  public AuctionBeginAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_auction_begin, null);
    }
    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewProductName = findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductNowPrice = findViewById(convertView, R.id.tvew_product_now_price_show);
    TextView tvewProductStatus = findViewById(convertView, R.id.tvew_product_status_show);

    final ProductItem product = (ProductItem) mList.get(position);
    tvewProductName.setText(product.title);
    tvewProductNowPrice.setText("¥" + product.maxprice);
    if (ProductBusiness.validateImageUrl(product.image)) {
      mImageLoader.displayImage(Endpoint.HOST + product.image, imgvewPhoto, options);
    }
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ProductBusiness.intentToPaipinProductDetailActivity(mContext, product,
            Integer.parseInt(product.id));
      }
    });
    return convertView;
  }
}