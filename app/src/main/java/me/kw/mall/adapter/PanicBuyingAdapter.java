
package me.kw.mall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.activity.ProductDetailActivity;
import me.kw.mall.model.ProductBusiness;
import service.api.ProductItem;

public class PanicBuyingAdapter extends CommonAdapter {

  private boolean isStarted;

  public PanicBuyingAdapter(Context context, List<?> list) {
    super(context, list);
  }

  public void setStartPanic(boolean isStarted) {
    this.isStarted = isStarted;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_panic_buying, null);
    }
    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewProductName = findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductNowPrice = findViewById(convertView, R.id.tvew_product_now_price_show);
    TextView tvewProductSales = findViewById(convertView, R.id.tvew_product_sales_show);
    TextView tvewProductGet = findViewById(convertView, R.id.tvew_product_get_show);

    tvewProductSales.setVisibility(View.VISIBLE);
    final ProductItem product = (ProductItem) mList.get(position);
    tvewProductName.setText(product.title);
    tvewProductNowPrice.setText("¥" + StringUtil.formatProgress(product.price));
    if (isStarted) {
      tvewProductGet.setBackgroundResource(R.drawable.bg_qiang);
      tvewProductGet.setTextColor(getColor(R.color.red));
    } else {
      tvewProductGet.setBackgroundResource(R.drawable.bg_qiang2);
      tvewProductGet.setTextColor(getColor(R.color.white));
    }

    tvewProductGet.setText("马上抢");
    if (TextUtils.isEmpty(product.sales)) {
      tvewProductSales.setText("已抢0件");
    } else {
      tvewProductSales.setText("已抢" + product.sales + "件");
      if (product.sales.equals(product.quantity)) {
        tvewProductGet.setBackgroundResource(R.drawable.bg_qiang2);
        tvewProductGet.setTextColor(getColor(R.color.white));
        tvewProductGet.setText("抢光了");
        tvewProductSales.setVisibility(View.GONE);
      }
    }

    if (ProductBusiness.validateImageUrl(product.image)) {
      loadImage(Endpoint.HOST + product.image, imgvewPhoto);
    }

    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("product", JsonSerializerFactory.Create().encode(product));
        bundle.putInt("id", Integer.parseInt(product.id));
				intentToActivity(bundle, ProductDetailActivity.class);
      }
    });
    return convertView;
  }
}