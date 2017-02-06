
package me.kw.mall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ImageLoaderUtil;
import com.android.zcomponent.util.PageInditor;
import com.android.zcomponent.util.SettingsBase;
import com.android.zcomponent.views.MeasureGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import me.kw.mall.activity.MyMessageActivity;
import me.kw.mall.activity.NoticeDetailActivity;
import me.kw.mall.activity.PaincBuyingActivity;
import me.kw.mall.activity.TabHostActivity;
import me.kw.mall.adapter.HomeGroupAdapter;
import me.kw.mall.adapter.IndexRecomAdapter;
import me.kw.mall.enumerate.CategoryType;
import me.kw.mall.enumerate.ProductType;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProductBusiness;
import service.api.AdHomeIndex;
import service.api.AdProduct;
import service.api.HomeNotice;
import service.api.HomeProduct;
import service.api.ProductCategory;
import service.entity.ProductService;

public class HomeFragment extends MallBaseFragment {
  @Bind(R.id.editvew_search_show) TextView mEditvewSearch;
  @Bind(R.id.imgvew_panic1_show) ImageView mImgvewPanic1;
  @Bind(R.id.imgvew_panic2_show) ImageView mImgvewPanic2;
  @Bind(R.id.imgvew_panic3_show) ImageView mImgvewPanic3;
  @Bind(R.id.imgvew_panic4_show) ImageView mImgvewPanic4;
  @Bind(R.id.imgvew_message_dot_show) ImageView mImgvewDot;
  @Bind(R.id.tvew_bulletin1_show) TextView mTvewBulletin1;
  @Bind(R.id.tvew_bulletin2_show) TextView mTvewBulletin2;
  @Bind(R.id.llayout_bulletin) LinearLayout mLlayoutBulletin;
  @Bind(R.id.gvew_recommend_show) MeasureGridView mGridViewRecommend;
  @Bind(R.id.gvew_group_show) MeasureGridView mGridViewGroup;

  private ImageLoader mImageLoader;
  private DisplayImageOptions mOptions;
  private AlphaAnimation alphaAnimation;
  private TranslateAnimation closeAnimation;
  private TranslateAnimation openAnimation;
  private boolean isOpenBulletin1;
  private int bulletinPosition;
  private MyBulletinHandler mBulletinHandler;

  private List<ProductCategory.CategoryItem> mCategoryItems = new ArrayList<>();
  private PageInditor<AdProduct.AdItem> mPageInditor = new PageInditor<>();
  private IndexRecomAdapter mAdapter;
  private HomeGroupAdapter mAdapterGroup;

  @Override protected int getLayoutId() {
    return R.layout.fragment_home;
  }

  protected void initUI() {
    mImageLoader = ImageLoader.getInstance();
    mOptions = ImageLoaderUtil.getDisplayImageOptions(R.drawable.img_empty_logo_small);
    initTranslateAnimation();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initGroup();
//    ProductBusiness.queryAdProducts(getHttpDataLoader(), "index_banner");
    ProductBusiness.queryAdHomeIndex(getHttpDataLoader(),"index_banner");
    ProductBusiness.queryGroup(getHttpDataLoader(), CategoryType.PRODCUT.getValue());
    ProductBusiness.queryNotice(getHttpDataLoader());
    ProductBusiness.queryHomeProducts(getHttpDataLoader());
    ProductService.AdIndexRecomRequest request = new ProductService.AdIndexRecomRequest();
    request.Identifier = "index_recom";
    getHttpDataLoader().doPostProcess(request, AdProduct.class);
  }

  private void initGroup() {
    String group = SettingsBase.getInstance().readStringByKey("group");
    if (!TextUtils.isEmpty(group)) {
      ProductCategory.CategoryItem[] items = JsonSerializerFactory.Create().decode(group,
          ProductCategory.CategoryItem[].class);
      if (null != items) {
        mCategoryItems.addAll(Arrays.asList(items));
        mAdapterGroup = new HomeGroupAdapter(getActivity(), mCategoryItems);
        mGridViewGroup.setAdapter(mAdapterGroup);
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
//    EaseMessageNotify.getInstance().onResume();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
//    EaseMessageNotify.getInstance().removeView(mImgvewDot);
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ProductService.HomeRequest.class)) {
      HomeProduct response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        if (null != response) {
          showPanicProducts(response.Data.PanicProducts);
        }
      }
    } else if (msg.valiateReq(ProductService.NoticeRequest.class)) {
      HomeNotice response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        if (null != response) {
          showTopProducts(response.Data.Results);
        }
      }
    } else if (msg.valiateReq(ProductService.AdvertRequest.class)) {
      AdHomeIndex response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        if (response.data.size() == 0) {
          return;
        }
        ProductBusiness.bindSliderLayout(this, R.id.flayout_slider_image, response);
      }
    } else if (msg.valiateReq(ProductService.GroupRequest.class)) {
      ProductCategory responseProduct = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, responseProduct)) {
        mCategoryItems.clear();
        mCategoryItems.addAll(responseProduct.Data);
        mAdapterGroup = new HomeGroupAdapter(getActivity(), mCategoryItems);
        mGridViewGroup.setAdapter(mAdapterGroup);
        SettingsBase.getInstance().writeStringByKey("group", JsonSerializerFactory.Create().encode(mCategoryItems));
      }
    } else if (msg.valiateReq(ProductService.AdIndexRecomRequest.class)) {
      //首页推荐
      AdProduct response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        mPageInditor.add(response.Data);
        if (null == mAdapter) {
          mAdapter = new IndexRecomAdapter(getActivity(), mPageInditor.getAll());
          mPageInditor.bindAdapter(mGridViewRecommend, mAdapter);
        } else {
          mAdapter.notifyDataSetChanged();
        }
      }
    }
  }

  private void showPanicProducts(HomeProduct.HomeItem[] datas) {
    if (null == datas) {
      return;
    }
    showProductImage(mImgvewPanic1, 0, datas);
    showProductImage(mImgvewPanic2, 1, datas);
    showProductImage(mImgvewPanic3, 2, datas);
    showProductImage(mImgvewPanic4, 3, datas);
  }

  private void showTopProducts(HomeNotice.NoticeItem[] datas) {
    mBulletinHandler = new MyBulletinHandler(datas);
    mBulletinHandler.sendEmptyMessageDelayed(0, 5000);
    mTvewBulletin1.setText(datas[bulletinPosition].title);
    mLlayoutBulletin.setOnClickListener(new NoticeClickListener(bulletinPosition, datas));
  }

  private void initTranslateAnimation() {
    alphaAnimation = new AlphaAnimation(1, 0f);
    alphaAnimation.setDuration(300);

    closeAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, -1);
    closeAnimation.setDuration(300);
    openAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 1,
        Animation.RELATIVE_TO_SELF, 0);
    closeAnimation.setAnimationListener(new AnimationListener() {

      @Override public void onAnimationStart(Animation animation) {}

      @Override public void onAnimationRepeat(Animation animation) {}

      @Override public void onAnimationEnd(Animation animation) {}
    });
    openAnimation.setAnimationListener(new AnimationListener() {

      @Override public void onAnimationStart(Animation animation) {}

      @Override public void onAnimationRepeat(Animation animation) {}

      @Override public void onAnimationEnd(Animation animation) {
        if (isOpenBulletin1) {
          mTvewBulletin2.setVisibility(View.GONE);
        } else {
          mTvewBulletin1.setVisibility(View.GONE);
        }
      }
    });
  }

  private void showProductImage(ImageView imageView, int position, HomeProduct.HomeItem[] datas) {
    if (datas.length > position) {
      mImageLoader.displayImage(Endpoint.HOST + datas[position].image, imageView, mOptions);
      imageView.setOnClickListener(new ImageClickListener(position, datas));
    }
  }

  @OnItemClick(R.id.gvew_group_show)
  void onClickItemGroup(final int position) {
    TabHostActivity tabHostActivity = BaseApplication.getInstance().getActivity(TabHostActivity.class);
    int size = mCategoryItems.size();
    tabHostActivity.setSelectedTab(1);
    final CategroyFragment categroyFragment = (CategroyFragment) tabHostActivity.getFragments()[1];
    if (null != categroyFragment) {
      categroyFragment.selectCategory1(mCategoryItems.get(position).id);
    }
  }

  @OnClick(R.id.llayout_painc_buy_click)
  void onClickLlayoutPaincBut() {
    getIntentHandle().intentToActivity(PaincBuyingActivity.class);
  }

  @OnClick(R.id.imgvew_clear_icon_click)
  void onClickImgvewClearIcon() {
    mEditvewSearch.setText("");
  }

  @OnClick(R.id.llayout_search_click)
  void onClickRlayoutSearch() {
    ProductBusiness.intentToSearchActivity(getActivity(), "");
  }

  @OnClick(R.id.tvew_scan_click)
  void onClickTvewScan() {
//    getIntentHandle().intentToActivity(BarcodeActivity.class);
  }

  @OnClick(R.id.tvew_message_click)
  void onClickTvewMessage() {
    if (!BaseApplication.isLogin()) {
      getIntentHandle().intentToActivity(LoginActivity.class);
      return;
    }
    getIntentHandle().intentToActivity(MyMessageActivity.class);
  }

  private class MyBulletinHandler extends Handler {
    private HomeNotice.NoticeItem[] datas;

    public MyBulletinHandler(HomeNotice.NoticeItem[] datas) {
      this.datas = datas;
    }

    @Override
    public void handleMessage(Message msg) {
      if(datas==null){
        return;
      }
      if (bulletinPosition < datas.length - 1) {
        bulletinPosition++;
      } else {
        bulletinPosition = 0;
      }

      AnimationSet set = new AnimationSet(false);
      set.addAnimation(closeAnimation);
      set.addAnimation(alphaAnimation);

      if (mTvewBulletin1.getVisibility() == View.VISIBLE) {
        isOpenBulletin1 = false;
        mTvewBulletin2.setText(this.datas[bulletinPosition].title);
        mTvewBulletin2.setVisibility(View.VISIBLE);

        mTvewBulletin1.startAnimation(set);
        mTvewBulletin2.startAnimation(openAnimation);
      } else {
        isOpenBulletin1 = true;
        mTvewBulletin1.setText(this.datas[bulletinPosition].title);
        mTvewBulletin1.setVisibility(View.VISIBLE);

        mTvewBulletin1.startAnimation(openAnimation);
        mTvewBulletin2.startAnimation(set);
      }
      mLlayoutBulletin.setOnClickListener(new NoticeClickListener(bulletinPosition, datas));
      sendEmptyMessageDelayed(0, 5000);
    }
  }

  public class NoticeClickListener implements OnClickListener {
    private HomeNotice.NoticeItem[] datas;
    private int position;

    public NoticeClickListener(int position, HomeNotice.NoticeItem[] datas) {
      this.datas = datas;
      this.position = position;
    }

    @Override
    public void onClick(View v) {
      Bundle bundle = new Bundle();
      bundle.putString("id", datas[position].id);
      getIntentHandle().intentToActivity(bundle, NoticeDetailActivity.class);
    }
  }

  public class ImageClickListener implements OnClickListener {
    private HomeProduct.HomeItem[] datas;
    private int position;

    public ImageClickListener(int position, HomeProduct.HomeItem[] datas) {
      this.datas = datas;
      this.position = position;
    }

    @Override
    public void onClick(View v) {
      if (datas.length > position) {
        if (ProductType.PAIPIN.getValue().equals(datas[position].type)) {
          ProductBusiness.intentToPaipinProductDetailActivity(getActivity(), null,
              Integer.parseInt(datas[position].product_id));
        } else {
          ProductBusiness.intentToProductDetailActivity(getActivity(), null,
              Integer.parseInt(datas[position].product_id));
        }
      }
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (mBulletinHandler != null)
      mBulletinHandler.removeMessages(0);
  }
}