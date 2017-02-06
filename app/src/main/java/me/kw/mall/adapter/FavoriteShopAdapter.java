
package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.CommonUtil;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.ShopFavorite;

public class FavoriteShopAdapter extends CommonAdapter {

  private boolean isEdit = false;

  public FavoriteShopAdapter(Context context, List<?> list) {
    super(context, list);
  }

  public void setEdit() {
    this.isEdit = !isEdit;
    this.notifyDataSetChanged();
  }

  public void setEdit(boolean isEdit) {
    this.isEdit = isEdit;
    this.notifyDataSetChanged();
  }

  public boolean isEdit() {
    return isEdit;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_favorite_shop, null);
    }
    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_photo_show);
    TextView tvewShopName = findViewById(convertView, R.id.tvew_shop_name_show);
    Button btnShop = findViewById(convertView, R.id.btn_shop_show);
    Button imgvewDel = findViewById(convertView, R.id.favorite_del);

    if (isEdit) {
      imgvewDel.setVisibility(View.VISIBLE);
    } else {
      imgvewDel.setVisibility(View.GONE);
    }

    final ShopFavorite favorite = (ShopFavorite) mList.get(position);
    tvewShopName.setText(favorite.shop_title);
    mImageLoader.displayImage(Endpoint.HOST + favorite.headpic_path, imgvewPhoto, options);
    imgvewDel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }

        if (null != mDeleteClickListener) {
          mDeleteClickListener.onClickDelete(position);
        }
      }
    });

    btnShop.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        if (null != mDeleteClickListener) {
          mDeleteClickListener.onClickSelect(position);
        }
      }
    });
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }

        if (null != mDeleteClickListener) {
          mDeleteClickListener.onClickSelect(position);
        }
      }
    });
    return convertView;
  }

  private OnItemActionClickListener mDeleteClickListener;

  public void setOnItemActionClickListener(OnItemActionClickListener deleteClickListener) {
    mDeleteClickListener = deleteClickListener;
  }

  public interface OnItemActionClickListener {

    public void onClickDelete(int position);

    public void onClickSelect(int position);
  }
}