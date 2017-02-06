
package me.kw.mall.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.enumerate.CategoryType;
import me.kw.mall.enumerate.ProductType;
import me.kw.mall.enumerate.SpecialType;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderProductBusiness;
import service.api.AddProduct;
import service.api.CommonReturn;
import service.api.ProductItem;
import service.entity.ProductService;

@ZTitleMore(false)
public class ReleaseCommodityGoodsActivity extends AddProductActivity {
  @Bind(R.id.edittxt_commodity_goods_price_show) EditText mEdittxtCommodityGoodsPrice;
  @Bind(R.id.edittxt_commodity_goods_stock_show) EditText mEdittxtCommodityGoodsStock;
  @Bind(R.id.edittxt_commodity_goods_indentify_show) EditText mEdittxtCommodityGoodsIndentify;
  @Bind(R.id.tvew_commodity_goods_category_show) TextView mTvewCommodityGoodsCategory;
  @Bind(R.id.tvew_commodity_goods_description_show) TextView mTvewCommodityGoodsDescription;
  @Bind(R.id.tvew_commodity_now_release_click) TextView mTvewCommodityGoodsRelease;
  @Bind(R.id.progressbar_categoryz_show) ProgressBar mProgressBar;

  private ProductService.ProductAddPutongRequest mRequest;
  private ProductService.ProductEditPutongRequest mPutongRequest;
  private boolean isPutHalt = false;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_release_commodity_goods);
    ButterKnife.bind(this);
    initUI();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  protected void initUI() {
    super.initUI();
    if (mProductId > 0) {
      mTvewWarehouse.setText("加入专题");
      mTvewCommodityGoodsRelease.setText("保存");
      getTitleBar().setTitleText("编辑商品");
    } else {
      getTitleBar().setTitleText("发布商品");
    }
    mEdittxtCommodityGoodsPrice.addTextChangedListener(new StringUtil.DecimalTextWatcher(
        mEdittxtCommodityGoodsPrice, 1));
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    super.onRecvMsg(msg);

    if (msg.valiateReq(ProductService.ProductAddPutongRequest.class)) {
      final AddProduct response = (AddProduct) msg.getRspObject();

      if (CommonValidate.validateQueryState(this, msg, response, "发布失败")) {
        mProductId = response.data.id;
        if (isPutHalt) {
          ProviderProductBusiness.queryHaltProduct(getHttpDataLoader(), String.valueOf(mProductId));
        } else {
          ProviderProductBusiness.queryPutawayProduct(getHttpDataLoader(), String.valueOf
              (mProductId));
        }
        ShowMsg.showToast(this, "发布成功");
        ShowMsg.showConfirmDialog(this, new ShowMsg.IConfirmDialogKeyBack() {
          @Override public void onConfirm(boolean confirmValue, boolean onKeyBack) {
            if (onKeyBack) {
              finish();
              return;
            }

            if (!confirmValue) {
              Bundle bundle = new Bundle();
              bundle.putString("id", String.valueOf(response.data.id));
              getIntentHandle().intentToActivity(bundle, EditOptionActivity.class);
            }
            finish();
          }
        }, "否", "是", "是否需要添加商品选项？");
      }
    } else if (msg.valiateReq(ProductService.ProductEditPutongRequest.class)) {
      final CommonReturn response = (CommonReturn) msg.getRspObject();

      if (CommonValidate.validateQueryState(this, msg, response, "编辑失败")) {
        ShowMsg.showToast(this, "编辑成功");
        FramewrokApplication.getInstance().setActivityResult(ProviderResultActivity
            .CODE_EDIT_PRODUCT, null);
        finish();
      }
    }
  }

  @Override protected void showProductInfo(ProductItem productItem) {
    super.showProductInfo(productItem);

    mEdittxtCommodityGoodsStock.setText(productItem.quantity);
    mEdittxtCommodityGoodsPrice.setText("" + StringUtil.formatProgress(productItem.price));
  }

  @OnClick(R.id.tvew_deposit_warehouse_click)
  void onOnClickTvewDepositWarehouse() {
    if (mProductId > 0) {
//      Bundle bundle = new Bundle();
//      bundle.putLong("id", mProductId);
//      getIntentHandle()
//              .intentToActivity(bundle, ToSpecialActivity_.class);
      intentToPanicBuyingActivity();
    } else {
      isPutHalt = true;
      addProduct();
    }
  }

  @OnClick(R.id.llayout_description_click)
  protected void onClickEditDescription() {
    super.onClickEditDescription();
  }

  @OnClick(R.id.layout_category_click)
  protected void onClickRlayoutProductCategory() {
    super.onClickRlayoutProductCategory();
  }

  @OnClick(R.id.tvew_commodity_now_release_click)
  void onClickTvewCommodityNowRelease() {
    if (mProductId > 0) {
      editProduct();
    } else {
      isPutHalt = false;
      addProduct();
    }
  }

  private void intentToPanicBuyingActivity() {
    Bundle bundle = new Bundle();
    bundle.putLong("id", mProductId);
    bundle.putString("type", SpecialType.PANIC.getValue());
    getIntentHandle().intentToActivity(bundle, PanicBuyingActivity.class);
  }

  private void editProduct() {
    mPutongRequest = new ProductService.ProductEditPutongRequest();

    mPutongRequest.Id = mProductId;
    mPutongRequest.Type = ProductType.PUTONG.getValue();
    mPutongRequest.Title = mEdittxtTitle.getText().toString();
    mPutongRequest.CategoryId = mCategoryId;
    mPutongRequest.Description = mstrDesc;
    mPutongRequest.Address = mstrAddress;
    mPutongRequest.Pics = getUploadFile();
    mPutongRequest.Movie = getVideoUploadFile();
    mPutongRequest.MovieThumbId = getVideoThumbnailFile();
    mPutongRequest.Security7days = getSecurity7days();
    mPutongRequest.SecurityDelivery = getSecurityDelivery();
    mPutongRequest.InSpecialPanic = getInSpecialPainc();
    mPutongRequest.InSpecialGift = getInSpecialGift();
    if (null != mAddress) {
      mPutongRequest.ProvinceId = mAddress.province_id;
      mPutongRequest.CityId = mAddress.city_id;
      mPutongRequest.AreaId = mAddress.area_id;
    }

    try {
      if (!TextUtils.isEmpty(mEdittxtCommodityGoodsStock.getText().toString())) {
        mPutongRequest.Quantity = Integer.parseInt(mEdittxtCommodityGoodsStock.getText().toString
            ());
      }

      if (!TextUtils.isEmpty(mEdittxtCommodityGoodsPrice.getText().toString())) {
        mPutongRequest.Price = Double.parseDouble(mEdittxtCommodityGoodsPrice.getText().toString());
      }
    } catch (Exception e) {
    }

    boolean isSend = ProviderProductBusiness.editPutongProduct(this, getHttpDataLoader(), mPutongRequest);
    if (isSend) {
      showWaitDialog(1, false, R.string.common_submit_data);
    }
  }

  private void addProduct() {
    mRequest = new ProductService.ProductAddPutongRequest();

    mRequest.Type = ProductType.PUTONG.getValue();
    mRequest.Title = mEdittxtTitle.getText().toString();
    mRequest.CategoryId = mCategoryId;
    mRequest.Description = mstrDesc;
    mRequest.Address = mstrAddress;
    mRequest.Pics = getUploadFile();
    mRequest.Movie = getVideoUploadFile();
    mRequest.MovieThumbId = getVideoThumbnailFile();
    mRequest.Security7days = getSecurity7days();
    mRequest.SecurityDelivery = getSecurityDelivery();
    if (null != mAddress) {
      mRequest.ProvinceId = mAddress.province_id;
      mRequest.CityId = mAddress.city_id;
      mRequest.AreaId = mAddress.area_id;
    }

    try {
      if (!TextUtils.isEmpty(mEdittxtCommodityGoodsStock.getText().toString())) {
        mRequest.Quantity = Integer.parseInt(mEdittxtCommodityGoodsStock.getText().toString());
      }
      if (!TextUtils.isEmpty(mEdittxtCommodityGoodsPrice.getText().toString())) {
        mRequest.Price = Double.parseDouble(mEdittxtCommodityGoodsPrice.getText().toString());
      }
    } catch (Exception e) {
    }
    boolean isSend = ProviderProductBusiness.addPutongProduct(this, getHttpDataLoader(), mRequest);
    if (isSend) {
      showWaitDialog(1, false, R.string.common_submit_data);
    }

  }

  @Override
  public TextView getCategoryEditvew() {
    return mTvewCommodityGoodsCategory;
  }

  @Override
  public String getCategoryType() {
    return CategoryType.PRODCUT.getValue();
  }

  @Override
  public ProgressBar getCategoryProgressBar() {
    return mProgressBar;
  }

  @Override
  public TextView getDescriptionTextView() {
    return mTvewCommodityGoodsDescription;
  }

}