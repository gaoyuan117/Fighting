
package me.kw.mall.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.activity.ReleaseCommodityGoodsActivity;
import me.kw.mall.enumerate.GoodsStatus;
import me.kw.mall.model.ProviderProductBusiness;
import service.api.ProductItem;

public class GoodsManageItemsAdapter extends CommonAdapter {

  private GoodsStatus mGoodsStatus;

  public GoodsManageItemsAdapter(Context context, List<?> list) {
    super(context, list);
  }

  public void setGoodsState(GoodsStatus goodsStatus) {
    mGoodsStatus = goodsStatus;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_goods_manage_items, null);
    }

    ImageView ivewAdapterGoodsIcon = findViewById(convertView, R.id.ivew_adapter_goods_icon_show);
    TextView tvewName = findViewById(convertView, R.id.tvew_adapter_goods_name_show);
    TextView tvewPrice = findViewById(convertView, R.id.tvew_adapter_goods_price_show);
    TextView tvewStock = findViewById(convertView, R.id.tvew_adapter_goods_warehouse_num);
    TextView tvewEdit = findViewById(convertView, R.id.tvew_adapter_goods_edit_click);
    TextView tvewUpOrDown = findViewById(convertView, R.id.tvew_adapter_goods_upordown);
    TextView tvewDel = findViewById(convertView, R.id.tvew_adapter_goods_delete);

    TextView tvewSales = findViewById(convertView, R.id.tvew_adapter_goods_sell_num);

    final ProductItem productItem = (ProductItem) mList.get(position);
    if (productItem.sales != null)
      tvewSales.setText("已售 " + productItem.sales);

    tvewName.setText(productItem.title);
    tvewPrice.setText("¥" + StringUtil.formatProgress(productItem.price));
    tvewStock.setText("库存" + productItem.quantity);

    if (ProviderProductBusiness.validateImageUrl(productItem.image)) {
      mImageLoader.displayImage(Endpoint.HOST + productItem.image,
          ivewAdapterGoodsIcon, options);
    } else {
      ivewAdapterGoodsIcon.setImageResource(R.drawable.img_empty_logo_small);
    }

    if ("0".equals(productItem.status)) {
      tvewUpOrDown.setText("上架");
    } else if ("1".equals(productItem.status)) {
      tvewUpOrDown.setText("下架");
    }

    if (mGoodsStatus == GoodsStatus.DINGZHI) {
      tvewEdit.setVisibility(View.INVISIBLE);
    } else {
      tvewEdit.setVisibility(View.VISIBLE);
    }

    tvewUpOrDown.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (null != face) {
          if ("0".equals(productItem.status)) {
            face.onClickUpdate(position, true);
          } else if ("1".equals(productItem.status)) {
            face.onClickUpdate(position, false);
          }
        }
      }
    });

    tvewDel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (null != face) {
          face.onClickDelete(position);
        }
      }
    });

    tvewEdit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if ("1".equals(productItem.status)) {
          ShowMsg.showToast(mContext, "请先下架商品再进行编辑");
          return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("id", Long.parseLong(productItem.id));
        if (mGoodsStatus == GoodsStatus.PUTONG) {
          intentToActivity(bundle, ReleaseCommodityGoodsActivity.class);
        } else if (mGoodsStatus == GoodsStatus.PAIPIN) {
//          intentToActivity(bundle, ReleaseAuctionGoodsActivity_.class);
        } else if (mGoodsStatus == GoodsStatus.DINGZHI) {
//          intentToActivity(bundle, ReleaseCustomizationGoodsActivity_.class);
        } else if (mGoodsStatus == GoodsStatus.XIANZHI) {
          ComponentName cn = new ComponentName("com.android.quna.mActivity",
                  "com.android.juzbao.mActivity.AddFreeActivity_");
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setComponent(cn);
          mContext.startActivity(intent);
        }
      }
    });
    return convertView;
  }

  CallBackInteface face;

  public void setCallBackInteface(CallBackInteface cbif) {
    this.face = cbif;
  }

  public interface CallBackInteface {
    public void onClickUpdate(int position, boolean isPutaway);

    public void onClickDelete(int position);

  }
}