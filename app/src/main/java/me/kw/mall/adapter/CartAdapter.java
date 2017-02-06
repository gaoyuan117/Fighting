
package me.kw.mall.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonExpandabelAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.CartItem;

public class CartAdapter extends CommonExpandabelAdapter {

  private boolean isShowOrderProduct;
  private EditProductClickListener mEditProductClickListener;

  public CartAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override public int getGroupCount() {
    if (null == mList) {
      return 0;
    }
    return mList.size();
  }

  public void setShowOrderProduct(boolean isShowOrderProduct) {
    this.isShowOrderProduct = isShowOrderProduct;
  }

  @Override public int getChildrenCount(int groupPosition) {
    if (null == ((CartItem) mList.get(groupPosition)).product) {
      return 0;
    }
    return ((CartItem) mList.get(groupPosition)).product.length;
  }

  @Override public Object getGroup(int groupPosition) {
    if (null == mList.get(groupPosition)) {
      return null;
    }
    return mList.get(groupPosition);
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    if (null == mList.get(groupPosition)) {
      return null;
    }
    return ((CartItem) mList.get(groupPosition)).product;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
                           View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_cart_group, null);
    }
    ImageView imgvewSelect = findViewById(convertView, R.id.imgvew_select_show);

    TextView tvewEdit = findViewById(convertView, R.id.tvew_edit_show);
    TextView tvewEditLine = findViewById(convertView, R.id.tvew_edit_line_show);
    TextView tvewShopName = findViewById(convertView, R.id.tvew_shop_name_show);

    if (isShowOrderProduct) {
      imgvewSelect.setVisibility(View.INVISIBLE);
      imgvewSelect.getLayoutParams().width = 20;
      tvewEdit.setVisibility(View.GONE);
      tvewEditLine.setVisibility(View.GONE);
    }

    CartItem cartItem = (CartItem) mList.get(groupPosition);
    tvewEdit.setOnClickListener(new ChangeEditStateClickListener(groupPosition));
    tvewShopName.setText(cartItem.shop_title);
    if (cartItem.isEdit) {
      tvewEdit.setText("完成");
    } else {
      tvewEdit.setText("编辑");
    }

    if (cartItem.isSelect) {
      imgvewSelect.setImageResource(R.drawable.cart_option_on);
    } else {
      imgvewSelect.setImageResource(R.drawable.cart_option);
    }

    imgvewSelect.setOnClickListener(new SelectClickListener(groupPosition, -1, true));
    return convertView;
  }

  //获取子分类
  @Override
  public View getChildView(int groupPosition, int childPosition,
                           boolean isLastChild, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView =
          layoutInflater.inflate(R.layout.adapter_cart_child, null);
    }

    ImageView imgvewSelect1 =
        findViewById(convertView, R.id.imgvew_select1_show);
    ImageView imgvewPhoto =
        findViewById(convertView, R.id.imgvew_photo_show);
    RelativeLayout rlayoutProductInfos =
        findViewById(convertView, R.id.rlayout_product_infos);
    RelativeLayout rlayoutAction =
        findViewById(convertView, R.id.rlayout_action);
    TextView tvewProductName =
        findViewById(convertView, R.id.tvew_product_name_show);
    TextView tvewProductPrice =
        findViewById(convertView, R.id.tvew_product_price_show);
    TextView tvewProductAttr =
        findViewById(convertView, R.id.tvew_product_attr_show);
    TextView tvewProductNum =
        findViewById(convertView, R.id.tvew_product_num_show);
    TextView tvewSelectNum =
        findViewById(convertView, R.id.tvew_select_num_show);

    TextView tvewSubProduct =
        findViewById(convertView, R.id.tvew_sub_product_show);
    TextView tvewAddProduct =
        findViewById(convertView, R.id.tvew_add_product_show);
    TextView tvewDelProduct =
        findViewById(convertView, R.id.tvew_del_product_show);

    if (isShowOrderProduct) {
      imgvewSelect1.setVisibility(View.GONE);
    }

    CartItem.Data[] product = ((CartItem) mList.get(groupPosition)).product;

    tvewProductName.setText(product[childPosition].title);

    tvewProductPrice.setText("¥" + StringUtil.formatProgress(product[childPosition].price));
    tvewProductNum.setText("x" + product[childPosition].quantity);

    tvewSelectNum.setText(product[childPosition].quantity);
    tvewProductAttr.setText(product[childPosition].product_attr);
    String imagePath = null;
    if (!TextUtils.isEmpty(product[childPosition].image_path)) {
      imagePath = product[childPosition].image_path;
    } else {
      if (null != product[childPosition].images
          && !TextUtils.isEmpty(product[childPosition].images.path)) {
        imagePath = product[childPosition].images.path;
      }
    }

    if (!TextUtils.isEmpty(imagePath)) {
      mImageLoader.displayImage(Endpoint.HOST + imagePath, imgvewPhoto,
          options);
    } else {
      imgvewPhoto.setImageResource(R.drawable.img_empty_logo_small);
    }

    if (((CartItem) mList.get(groupPosition)).isEdit) {
      rlayoutProductInfos.setVisibility(View.GONE);
      rlayoutAction.setVisibility(View.VISIBLE);
    } else {
      rlayoutProductInfos.setVisibility(View.VISIBLE);
      rlayoutAction.setVisibility(View.GONE);
    }

    if (product[childPosition].isSelect) {
      imgvewSelect1.setImageResource(R.drawable.cart_option_on);
    } else {
      imgvewSelect1.setImageResource(R.drawable.cart_option);
    }

    imgvewSelect1.setOnClickListener(new SelectClickListener(groupPosition, childPosition, false));

    tvewAddProduct.setOnClickListener(new EditClickListener(groupPosition, childPosition, true,
        false));
    tvewSubProduct.setOnClickListener(new EditClickListener(groupPosition, childPosition, false,
        false));
    tvewDelProduct.setOnClickListener(new EditClickListener(groupPosition, childPosition, false,
        true));
    return convertView;
  }

  public void setEditProductClickListener(EditProductClickListener listener) {
    mEditProductClickListener = listener;
  }

  public interface EditProductClickListener {

    public void onClickEditProduct(int groupPosition, int childPosition,
                                   boolean isAdd, boolean isRemove);

    public void onClickSelectProduct(int groupPosition, int childPosition,
                                     boolean isSelectAll);
  }

  private class SelectClickListener implements View.OnClickListener {

    private int groupPosition;
    private int childPosition;
    private boolean isSelectAll;

    public SelectClickListener(int groupPosition, int childPosition, boolean isSelectAll) {
      this.groupPosition = groupPosition;
      this.childPosition = childPosition;
      this.isSelectAll = isSelectAll;
    }

    @Override
    public void onClick(View view) {
      if (null != mEditProductClickListener) {
        mEditProductClickListener.onClickSelectProduct(groupPosition,
            childPosition, isSelectAll);
      }
    }
  }

  private class EditClickListener implements View.OnClickListener {

    private int groupPosition;
    private int childPosition;
    private boolean isAdd;
    private boolean isRemove;

    public EditClickListener(int groupPosition, int childPosition, boolean isAdd, boolean
        isRemove) {
      this.groupPosition = groupPosition;
      this.childPosition = childPosition;
      this.isAdd = isAdd;
      this.isRemove = isRemove;
    }

    @Override
    public void onClick(View view) {
      if (null != mEditProductClickListener) {
        mEditProductClickListener.onClickEditProduct(groupPosition, childPosition, isAdd, isRemove);
      }
    }
  }

  private class ChangeEditStateClickListener implements View.OnClickListener {
    private int groupPosition;

    public ChangeEditStateClickListener(int groupPosition) {
      this.groupPosition = groupPosition;
    }

    @Override public void onClick(View view) {
      ((CartItem) mList.get(groupPosition)).isEdit = !((CartItem) mList.get(groupPosition)).isEdit;
      notifyDataSetChanged();
    }
  }

}