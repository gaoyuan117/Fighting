
package me.kw.mall.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.video.surfaceview.VideoPlayView;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ListUtil;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.android.zcomponent.util.share.ShareDialog;
import com.android.zcomponent.util.share.ShareReqParams;
import com.android.zcomponent.util.share.WechatHandle;
import com.android.zcomponent.views.MeasureWebview;
import com.android.zcomponent.views.OverScrollView;
import com.android.zcomponent.views.imageslider.SliderLayout;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.ui.chat.ChatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.dao.JiFenDao;
import me.kw.mall.model.CartBusiness;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.FavoriteBusiness;
import me.kw.mall.model.ProductBusiness;
import service.api.CartItem;
import service.api.CartPageResult;
import service.api.CartTradeResult;
import service.api.CommonReturn;
import service.api.Image;
import service.api.Product;
import service.api.ProductItem;
import service.api.ProductOptions;
import service.entity.CartService;
import service.entity.ProductService;
@ZTitleMore(false)
public class ProductDetailActivity extends MallBaseActivity implements
    ProductBusiness.ProductOptionSelect.OnOptionSelectListener {

  @Bind(R.id.personal_message_dot_show) ImageView mPersonalMessageDot;
  @Bind(R.id.llayout_product_qi_show) LinearLayout mLlayoutProductQi;
  @Bind(R.id.llayout_product_qiang_show) LinearLayout mLlayoutProductQiang;
  @Bind(R.id.rlayout_product_option_click) RelativeLayout mRlayoutOptions;
  @Bind(R.id.tvew_product_name_show) TextView mTvewProductName;
  @Bind(R.id.tvew_product_price_show) TextView mTvewProductPrice;
  @Bind(R.id.tvew_product_qi_show) TextView mTvewProductQi;
  @Bind(R.id.tvew_product_qiang_show) TextView mTvewProductQiang;
  @Bind(R.id.btn_favorite_click) TextView mTvewFavorite;
  @Bind(R.id.tvew_option_show) TextView mTvewOption;
  @Bind(R.id.btn_shop_click) TextView mTvewShop;
  @Bind(R.id.tvew_top_click) ImageButton mBtnToTop;
  @Bind(R.id.scrollview) OverScrollView mScrollView;

  MeasureWebview mWebviewProductDetail;

  private VideoPlayView mVideoPlayView;
  private SliderLayout mSliderLayout;
  private ProductItem mProduct;
  private int iSelectProductCount = 1;
  private int mProductId;
  private FavoriteBusiness.FavoriteHelper mFavoriteHelper;
  private String mstrProductOptions = "0";
  private ProductBusiness.ProductOptionSelect mOptionSelect;
  private ProductBusiness.ProductNumSelect mProductNumSelect;
  private boolean isAddToOrder = false;
  private ShareDialog mShareCustomDialog;

  @Override protected int getLayoutId() {
    return R.layout.activity_product_detail;
  }

  @Override protected void initUI() {
    mScrollView.setOnScrollListener(new OverScrollView.OnScrollListener() {

      @Override public void onScroll(int l, int t, int oldl, int oldt) {
        int scrollY = mScrollView.getScrollY();

        if (scrollY > 600) {
          mBtnToTop.setVisibility(View.VISIBLE);
        } else {
          mBtnToTop.setVisibility(View.GONE);
        }
      }
    });
    mWebviewProductDetail = (MeasureWebview) findViewById(R.id.webview_product_detail_show);
    mWebviewProductDetail.getSettings().setDefaultTextEncodingName("utf-8");
    Intent intent = getIntent();
    mProductId = intent.getIntExtra("id", 0);
    ProductBusiness.queryProduct(getHttpDataLoader(), mProductId);
    mFavoriteHelper = new FavoriteBusiness.FavoriteHelper(this, getHttpDataLoader());
    mFavoriteHelper.queryIsProductFavorited(true, mProductId);
    mFavoriteHelper.setProductId(mProductId);
    mFavoriteHelper.favoriteStateView(mTvewFavorite, R.drawable.icon25, R.drawable.icon26);
//    EaseMessageNotify.getInstance().addView(mPersonalMessageDot);
    String strProduct = intent.getStringExtra("product");
    if (!TextUtils.isEmpty(strProduct)) {
      ProductItem product = JsonSerializerFactory.Create().decode(strProduct, ProductItem.class);
      showProductInfo(product);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
//    EaseMessageNotify.getInstance().onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
//    EaseMessageNotify.getInstance().removeView(mPersonalMessageDot);
  }

  private void bindSliderLayout() {
    mSliderLayout = SliderLayout.bindSliderLayout(this, R.id.flayout_slider_image);
    mSliderLayout.setOnViewCreatedListener(new SliderLayout.OnViewCreatedListener() {
      @Override public void onViewCreated() {
        if (null != mProduct && null != mProduct.images) {
          List<String> temp = new ArrayList<String>();
          for (Image image : mProduct.images) {
            temp.add(Endpoint.HOST + image.path);
          }
          mSliderLayout.setData(temp, true, true);
        }
      }
    });
  }

  private void showShareCustomDialog() {
    if (null == mProduct) {
      return;
    }
    if (null == mShareCustomDialog) {
      mShareCustomDialog = new ShareDialog(this, R.layout.dialog_share1_layout);
    }
    if (!mShareCustomDialog.isShowing()) {
      mShareCustomDialog.showDialog();
      ShareReqParams params = new ShareReqParams();
      params.summary = "【" + mProduct.title + "】";
      String[] imageUrls = null;
      if (null != mProduct.images) {
        imageUrls = new String[mProduct.images.length];
        for (int i = 0; i < imageUrls.length; i++) {
          imageUrls[i] = Endpoint.HOST + mProduct.images[i].path;
        }
      } else {
        imageUrls = new String[1];
        imageUrls[0] = mProduct.image;
      }

      params.imageUrls = ListUtil.arrayToList(imageUrls);
      params.title = mProduct.title;
      params.shareUrl = Endpoint.HOST + mProduct.share_href;
      params.appName = getString(R.string.app_name);
      params.type = "4";
      mShareCustomDialog.setShareParams(params);
    }
  }

  private void showProductInfo(ProductItem item) {
    if (null == item) {
      return;
    }
    mProduct = item;
    bindSliderLayout();
    if (TextUtils.isEmpty(mProduct.image) && null != mProduct.images && mProduct.images.length >
        0) {
      mProduct.image = mProduct.images[0].path;
    }
    if (TextUtils.isEmpty(mProduct.shop_id)) {
      mTvewShop.setVisibility(View.GONE);
    } else {
      mTvewShop.setVisibility(View.VISIBLE);
    }
//    String price = mProduct.price + "";
//    if (mProduct.price_max != null && mProduct.price_min != null) {
//        price = mProduct.price_min + "-" + mProduct.price_max;
//    }
//    mTvewProductPrice.setText("¥" + price);
    mTvewProductName.setText(mProduct.title);

    ProductBusiness.showProductAttribute(this, mTvewProductQi,
        mProduct.security_7days, R.drawable.product_detail_icon_qi_un,
        R.drawable.product_detail_icon_qi);

    ProductBusiness.showProductAttribute(this, mTvewProductQiang,
        mProduct.in_special_panic,
        R.drawable.product_detail_icon_qiang_un,
        R.drawable.product_detail_icon_qiang);

    if (!TextUtils.isEmpty(mProduct.description)) {
      String html = ProductBusiness.getHtmlData(mProduct.description);
      html = JiFenDao.getHTML(html);
      mWebviewProductDetail.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    if (null != mProduct.movie && !"0".equals(mProduct.movie.id) && !TextUtils.isEmpty(mProduct
        .movie.id)) {
      String path = Endpoint.HOST + mProduct.movie.path;
      String thumbnail = Endpoint.HOST + mProduct.movie.thumb_image_path;

      loadVideo(path, thumbnail);
    }
  }

  private void loadVideo(String videoUrl, String thumbnail) {
    if (!StringUtil.isEmptyString(videoUrl)) {
      mVideoPlayView = (VideoPlayView) findViewById(R.id.video_play_view);
      mVideoPlayView.setVideoPath(videoUrl, false);
      mVideoPlayView.setVisibility(View.VISIBLE);
      if (!StringUtil.isEmptyString(thumbnail)) {
        mVideoPlayView.setVideoThumbnail(thumbnail);
      }
    }
  }

  @Override public void onLoginSuccess() {
    if (null != mProduct) {
      mFavoriteHelper.queryIsProductFavorited(true, Integer.parseInt(mProduct.id));
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (null != mVideoPlayView) {
      mVideoPlayView.pause();
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (null != mFavoriteHelper) {
      mFavoriteHelper.onRecvMsg(msg);
    }

    if (msg.valiateReq(ProductService.ProductRequest.class)) {
      Product response = (Product) msg.getRspObject();

      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          showProductInfo(response.Data);
        }
      }
      ProductBusiness.queryProductOptions(getHttpDataLoader(), String.valueOf(mProductId));
    } else if (msg.valiateReq(ProductService.ProductOptionRequest.class)) {
      ProductOptions response = (ProductOptions) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        mOptionSelect = new ProductBusiness.ProductOptionSelect(this, response, mProduct);
        mOptionSelect.setOnOptionSelectListener(this);
        showPriceInfo(response);
        mTvewOption.setText("请选择 " + mOptionSelect.getOptionTitle());
        mRlayoutOptions.setVisibility(View.VISIBLE);
      } else {
        showPriceInfo(null);
        mProductNumSelect = new ProductBusiness.ProductNumSelect(this, mProduct);
        mProductNumSelect.setOnProductNumSelectorListenre(getOnProductNumSelectorListener());
        mRlayoutOptions.setVisibility(View.GONE);
      }
    } else if (msg.valiateReq(CartService.AddToCartRequest.class)) {
      CommonReturn response = msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          if (isAddToOrder) {
            CartBusiness.queryCarts(getHttpDataLoader(), 1);
          } else {
            ShowMsg.showToast(getApplicationContext(), "添加成功");
          }
        } else {
          ShowMsg.showToast(getApplicationContext(), response.message);
        }
      } else {
        ShowMsg.showToast(getApplicationContext(), "添加失败");
      }
    } else if (msg.valiateReq(CartService.QueryCartsRequest.class)) {
      CartPageResult response = msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        if (null != response.Data && null != response.Data && response.Data.Result.length > 0) {
          ArrayList<CartItem> listCartItems = new ArrayList<CartItem>();
          for (int i = 0; i < response.Data.Result.length; i++) {
            if (response.Data.Result[i].product[0].product_id.equals(mProduct.id)) {
              response.Data.Result[i].isSelect = true;
              response.Data.Result[i].product[0].isSelect = true;
              listCartItems.add(response.Data.Result[i]);
            }
          }
          Integer[] cartIds = CartBusiness.getAllSelectCartId(listCartItems);
          if (null != cartIds) {
            CartBusiness.toTrade(getHttpDataLoader(), cartIds);
          }
        } else {
          ShowMsg.showToast(this, msg, "添加失败");
        }
      } else {
        ShowMsg.showToast(this, msg, "添加失败");
      }
    } else if (msg.valiateReq(CartService.ToTradeRequest.class)) {
      CartTradeResult response = (CartTradeResult) msg.getRspObject();

      if (CommonValidate.validateQueryState(this, msg, response)) {
        Bundle bundle = new Bundle();
        bundle.putString("product", JsonSerializerFactory.Create().encode(response.Data));
        getIntentHandle().intentToActivity(bundle, OrderEnsureActivity.class);
      } else {
        ShowMsg.showToast(this, msg, "添加失败");
      }
    }
  }

  private void showPriceInfo(ProductOptions response) {
    if (response == null) {
      mTvewProductPrice.setText("¥" + mProduct.price);
      return;
    }
    if (response.data.option_price == null) {
      mTvewProductPrice.setText("¥" + mProduct.price);
      return;
    }
    if (response.data.option_price.length < 1) {
      mTvewProductPrice.setText("¥" + mProduct.price);
    }
    float arr_price[] = new float[response.data.option_price.length + 1];
    for (int i = 0; i < response.data.option_price.length + 1; i++) {
      if (i == 0) {
        arr_price[i] = mProduct.price.floatValue();
        continue;
      }
      arr_price[i] = response.data.option_price[i - 1].price.floatValue();
    }
    Arrays.sort(arr_price);
    String price = "¥" + arr_price[0] + "-" + arr_price[arr_price.length - 1];
    mTvewProductPrice.setText(price);
  }

  @OnClick(R.id.tvew_top_click)
  void onClickToTop() {
    mScrollView.smoothScrollTo(0, 0);
  }

  @OnClick({R.id.rlayout_message_click, R.id.personal_message_bg})
  void onClickRlayoutMessage() {
    if (!BaseApplication.isLogin()) {
      BaseApplication.intentToLoginActivity(this);
      return;
    }
    Intent intent = new Intent(this, MyMessageActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.rlayout_product_option_click)
  void onClickRlayoutOption() {
    if (null != mOptionSelect) {
      mOptionSelect.showWindowBottom(mRlayoutOptions);
    }
  }

  @OnClick(R.id.btn_add_cart_click)
  void onClickBtnAddCart() {
    if (!BaseApplication.isLogin()) {
      BaseApplication.intentToLoginActivity(this);
      return;
    }
    if (null != mProduct) {
      if (null != mOptionSelect) {
        mOptionSelect.showWindowBottom(mRlayoutOptions);
      } else {
        isAddToOrder = false;
        addToCart(mstrProductOptions, iSelectProductCount);
      }
    }
  }

  @OnClick(R.id.btn_add_order_click)
  void onClickBtnAddOrder() {
    if (!BaseApplication.isLogin()) {
      BaseApplication.intentToLoginActivity(this);
      return;
    }

    if (null != mOptionSelect) {
      mOptionSelect.showWindowBottom(mRlayoutOptions);
    } else {
      isAddToOrder = true;
      if (mProductNumSelect != null) {
        mProductNumSelect.showWindowBottom(mRlayoutOptions);
      } else {
        addToCart(mstrProductOptions, iSelectProductCount);
        showWaitDialog(3, false, R.string.common_submit_data);
      }
//            addToOrder(mstrProductOptions, iSelectProductCount);
    }
  }

  @OnClick(R.id.btn_favorite_click)
  void onClickAddFavorite() {
    if (null == mProduct) {
      return;
    }
    if (!BaseApplication.isLogin()) {
      BaseApplication.intentToLoginActivity(this);
      return;
    }

    mFavoriteHelper.queryIsProductFavorited(false,
        Integer.parseInt(mProduct.id));
    showWaitDialog(2, false, R.string.common_submit_data);
  }


  @OnClick(R.id.btn_shop_click) void onClickBtnShop() {
    Bundle bundle = new Bundle();
    bundle.putInt("id", Integer.parseInt(mProduct.shop_id));
    bundle.putString("title", mProduct.shop_title);
    bundle.putString("im", mProduct.im_account);
    getIntentHandle().intentToActivity(bundle, ShopDetailActivity.class);
  }

  @OnClick(R.id.btn_contract_click) void onClickBtnContract() {
//    ProductBusiness.intentToChartActivity(this, mProduct.im_account);
    ChatActivity.start(this, mProduct.im_account);
  }


  @OnClick(R.id.btn_weixin_click) void onClickBtnWeixin() {
    if (!new WechatHandle(this).isWXAppInstalled()) {
      return;
    }
    Intent intent = new Intent();
    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
    intent.setAction(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setComponent(cmp);
    startActivity(intent);
  }

  @OnClick(R.id.rlayout_product_review_click) void onClickRlayoutProductReview() {
    Bundle bundle = new Bundle();
    bundle.putString("productid", mProduct.id);
    getIntentHandle().intentToActivity(bundle, ReviewActivity.class);
  }

  @OnClick(R.id.tvew_back_click)
  void onClickTvewBack() {
    finish();
  }

  @OnClick(R.id.tvew_cart_click)
  void onClickTvewCart() {
    getIntentHandle().intentToActivity(CartActivity.class);
  }

  @OnClick(R.id.tvew_share_click)
  void onClickTvewShare() {
    showShareCustomDialog();
  }

  private void addToCart(String optionId, int productNum) {
    if (null == mProduct) {
      return;
    }
    BigDecimal price;
    if (TextUtils.isEmpty(optionId) || "0".equals(optionId)) {
      price = mProduct.price;
    } else {
      price = mOptionSelect.getSelectOptionPrice();
    }
    CartBusiness.addToCart(getHttpDataLoader(), price, Integer.parseInt(mProduct.id), productNum,
        optionId);
  }

  private void addToOrder(String optionId, int productNum) {
    if (null == mProduct) {
      return;
    }

    CartItem[] cartItems = new CartItem[1];
    cartItems[0] = new CartItem();
    cartItems[0].shop_id = mProduct.shop_id;
    cartItems[0].shop_title = mProduct.shop_title;
    cartItems[0].product = new CartItem.Data[1];
    cartItems[0].product[0] = new CartItem.Data();

    if (null != mProduct.images) {
      cartItems[0].product[0].images = mProduct.images[0];
    } else {
      Image image = new Image();
      image.path = mProduct.image;
      cartItems[0].product[0].images = image;
    }

    if (TextUtils.isEmpty(optionId) || "0".equals(optionId)) {
      cartItems[0].product[0].price = mProduct.price;
    } else {
      cartItems[0].product[0].price =
          mOptionSelect.getSelectOptionPrice();
    }
    cartItems[0].product[0].identify_price = mProduct.identify_price;
    cartItems[0].product[0].product_id = mProduct.id;
    cartItems[0].product[0].quantity = String.valueOf(productNum);
    cartItems[0].product[0].title = mProduct.title;
    cartItems[0].product[0].option_ids = optionId;
    if (null != mOptionSelect) {
      cartItems[0].product[0].product_attr = mOptionSelect.getSelectOption();
    }
    Bundle bundle = new Bundle();
    bundle.putString("product", JsonSerializerFactory.Create().encode(cartItems));
    getIntentHandle().intentToActivity(bundle, OrderEnsureActivity.class);
  }


  @Override
  public void onClickOptionAddCart(String optionId, int productNum) {
    isAddToOrder = false;
    addToCart(optionId, productNum);
  }

  @Override
  public void onClikcOptionAddOrder(String optionId, int productNum) {
//        addToOrder(optionId, productNum);
    isAddToOrder = true;
    addToCart(optionId, productNum);
    showWaitDialog(3, false, R.string.common_submit_data);
  }

  private ProductBusiness.ProductNumSelect.OnProductNumSelectorListenre
  getOnProductNumSelectorListener() {
    return new ProductBusiness.ProductNumSelect.OnProductNumSelectorListenre() {
      @Override public void onClickAddCart(int num) {
        isAddToOrder = false;
        addToCart(mstrProductOptions, num);
      }

      @Override public void onClickAddToOrder(int num) {
        isAddToOrder = true;
        addToCart(mstrProductOptions, num);
        showWaitDialog(3, false, R.string.common_submit_data);
      }
    };
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);

    if (null != mShareCustomDialog) {
      mShareCustomDialog.onNewIntent(intent);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (null != mShareCustomDialog) {
      mShareCustomDialog.onActivityResult(requestCode, resultCode, data);
    }
  }
}