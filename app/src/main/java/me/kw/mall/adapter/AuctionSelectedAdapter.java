
package me.kw.mall.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.LogEx;
import com.android.zcomponent.util.TimeUtil;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.model.ProductBusiness;
import service.api.ProductItem;

public class AuctionSelectedAdapter extends CommonAdapter {

  public AuctionSelectedAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_auction_selected, null);
    }
    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewProductName = findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductCategory = findViewById(convertView, R.id.tvew_product_category_show);
    TextView tvewProductTime = findViewById(convertView, R.id.tvew_product_time_show);
    TextView tvewProductAuctionStatus = findViewById(convertView, R.id
        .tvew_product_auction_status_show);
    TextView tvewProductAuctionNum = findViewById(convertView, R.id.tvew_product_auction_num_show);

    final ProductItem product = (ProductItem) mList.get(position);
    tvewProductName.setText(product.title);
    tvewProductAuctionNum.setText(product.auction_count);
    tvewProductCategory.setText(product.activity_title);

    long currentTime = Endpoint.serverDate().getTime() / 1000;
    long diffTime = Long.parseLong(product.end_time) - currentTime;
    LogEx.e("zjw", TimeUtil.transformLongTimeFormat(Long.parseLong(product.end_time) * 1000,
        TimeUtil.STR_FORMAT_DATE_TIME));

    if (diffTime > 0) {
      int hour = (int) (diffTime / 3600);
      int minute = (int) (diffTime % 3600 / 60);
      int secend = (int) (diffTime % 60);
      tvewProductAuctionStatus.setText("竞拍中");
      tvewProductTime.setText(hour + "时" + minute + "分" + secend + "秒");
    } else {
      tvewProductTime.setText(0 + "时" + 0 + "分" + 0 + "秒");
      tvewProductAuctionStatus.setText("已结束");
    }
    if (ProductBusiness.validateImageUrl(product.image)) {
      mImageLoader.displayImage(Endpoint.HOST + product.image, imgvewPhoto, options);
    }
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ProductBusiness.intentToPaipinProductDetailActivity(mContext,
            product, Integer.parseInt(product.id));
      }
    });
    return convertView;
  }
}