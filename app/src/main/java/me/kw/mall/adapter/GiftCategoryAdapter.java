
package me.kw.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.CommonUtil;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.GiftCategory;

public class GiftCategoryAdapter extends CommonAdapter {

  public GiftCategoryAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_select_gift_images, null);
    }

    ImageView imgvewPhoto = findViewById(convertView, R.id.imgvew_category);
    RelativeLayout imgvewPhotoMask = findViewById(convertView, R.id.llayout_category_mask);
    if (position == getSelectPosition()) {
      imgvewPhotoMask.setVisibility(View.VISIBLE);
    } else {
      imgvewPhotoMask.setVisibility(View.GONE);
    }
    final GiftCategory.GiftCategoryItem category = (GiftCategory.GiftCategoryItem) mList.get
        (position);
    mImageLoader.displayImage(Endpoint.HOST + category.image, imgvewPhoto, options);
    imgvewPhoto.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        if (null != face) {
          face.onClickSelect(position);
        }
      }
    });

    imgvewPhotoMask.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        if (null != face) {
          face.onClickSelect(position);
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

    public void onClickSelect(int position);
  }

}