
package me.kw.mall.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.android.zcomponent.adapter.FragmentViewPagerAdapter;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.fragment.BaseFragment;
import com.android.zcomponent.util.LogEx;
import com.android.zcomponent.views.PagerSlidingTabStrip;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.kw.mall.constant.GlobalConst;
import me.kw.mall.constant.ResultActivity;
import me.kw.mall.enumerate.OrderStatus;
import me.kw.mall.fragment.ShopOrderFragment;

@ZTitleMore(false)
public class MyShopOrderActivity extends MallBaseActivity implements
    BaseFragment.OnFragmentCreatedListener {
  private final String TAG = "MyShopOrderActivity";
  @Bind(R.id.common_viewpaper_show) ViewPager mViewPager;
  @Bind(R.id.common_pager_slide_tab_show) PagerSlidingTabStrip mPagerSlidingTabStrip;

  private List<Fragment> mlistFragments;
  private List<String> mlistTitle;
  private int miCurSelectedFragmentPosition = 0;

  @Override protected int getLayoutId() {
    return R.layout.activity_my_order;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("我的订单");
    bindWidget();
    initViewPager();
  }

  private void bindWidget() {
    mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageSelected(int position) {
        miCurSelectedFragmentPosition = position;
        refreshCurFragment();
      }

      @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}

      @Override public void onPageScrollStateChanged(int arg0) {}
    });
  }

  public void initViewPager() {
    mlistFragments = new ArrayList<>();
    mlistTitle = new ArrayList<>();

    addShopOrderFragment();
    FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
            mViewPager, mlistFragments);
    mViewPager.setAdapter(adapter);
    mPagerSlidingTabStrip.setTabPaddingLeftRight(10);
    mPagerSlidingTabStrip.setIndicatorColorResource(R.color.red);
    mPagerSlidingTabStrip.setTextSelectColorResource(R.color.red);
    mPagerSlidingTabStrip.setTabWidth((int) (getWindow().getWindowManager().getDefaultDisplay().getWidth() / 4));

    mPagerSlidingTabStrip.setViewPager(mViewPager, mlistTitle);
    mViewPager.setCurrentItem(miCurSelectedFragmentPosition);
  }

  private void addShopOrderFragment() {
    Intent intent = getIntent();
    String strOrderStatus = intent.getStringExtra(GlobalConst.ORDER_STATUS);
    List<OrderStatus> orderStatus = new ArrayList<OrderStatus>();

    orderStatus.add(OrderStatus.ALL);
    orderStatus.add(OrderStatus.SUBMIT);
    orderStatus.add(OrderStatus.SUBMITED);
    orderStatus.add(OrderStatus.REFUND);

    mlistTitle.add("全部");
    mlistTitle.add("待支付");
    mlistTitle.add("已支付");
    mlistTitle.add("退款");

    int position = 0;
    for (OrderStatus status : orderStatus) {
      ShopOrderFragment takeoutFragment = new ShopOrderFragment();
      takeoutFragment.setOrderStatus(status);
      takeoutFragment.setOnFragmentCreatedListener(this);
      mlistFragments.add(takeoutFragment);

      if (strOrderStatus.equals(status.getValue())) {
        miCurSelectedFragmentPosition = position;
      }
      position++;
    }
  }

  private void refreshCurFragment() {
    if (getCurrentFragment().isCreated()) {
      getCurrentFragment().queryListData();
    }
  }

  private ShopOrderFragment getCurrentFragment() {
    ShopOrderFragment fragment = (ShopOrderFragment) mlistFragments.get(miCurSelectedFragmentPosition);
    return fragment;
  }

  @Override
  protected void onActivityResult(int arg0, int arg1, Intent data) {
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    LogEx.d(TAG, "onActivityResultCallBack resultCode " + resultCode);

    ShopOrderFragment fragment = getCurrentFragment();
    if (resultCode == ResultActivity.CODE_REFUND_SUCCESS) {
      fragment.refundSuccess();
    } else if (resultCode == ResultActivity.CODE_PAY_ECO_SUCCESS) {
      fragment.payEcoSuccess();
    } else if (resultCode == ResultActivity.CODE_ADD_SHOP_REVIEW) {
      fragment.addShopReviewSuccess(intent);
    } else if (resultCode == ResultActivity.CODE_UPDATE_ORDER) {
      fragment.refreshItem(intent, false);
    } else if (resultCode == ResultActivity.CODE_DEL_ORDER) {
      fragment.refreshData(true);
    }
  }

  @Override
  public void onFragmentCreated() {
    refreshCurFragment();
  }
//    @Override protected void onRestart() {
//        super.onRestart();
//        ((ShopOrderFragment_) mlistFragments.get(miCurSelectedFragmentPosition)).refreshData
// (true);
//    }
}
