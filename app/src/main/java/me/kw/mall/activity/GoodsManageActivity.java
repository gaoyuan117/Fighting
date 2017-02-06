
package me.kw.mall.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.android.zcomponent.adapter.FragmentViewPagerAdapter;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.util.MyLayoutAdapter;
import com.android.zcomponent.views.PagerSlidingTabStrip;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.enumerate.GoodsStatus;
import me.kw.mall.fragment.GoodsFragment;

@ZTitleMore(false)
public class GoodsManageActivity extends MallBaseActivity {
  @Bind(R.id.viewpaper) ViewPager mViewPager;
  @Bind(R.id.order_select_table_titlebar_tabs) PagerSlidingTabStrip mPagerSlidingTabStrip;

  private List<Fragment> mlistFragments;
  private List<String> mlistTitle;
  private int miCurSelectedFragmentPosition = 0;

  @Override protected int getLayoutId() {
    return R.layout.activity_goods_manage;
  }

  protected void init() {
    getTitleBar().setTitleText("宝贝管理");
  }

  @Override protected void initUI() {
    init();
    bindWidget();
    initViewPager();
  }

  private void bindWidget() {
    mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener() {
      @Override public void onPageSelected(int position) {
        miCurSelectedFragmentPosition = position;
        refreshCurFragment();
      }

      @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}

      @Override public void onPageScrollStateChanged(int arg0) {}
    });
  }

  public void initViewPager() {
    mlistFragments = new ArrayList<Fragment>();
    mlistTitle = new ArrayList<String>();

    addShopOrderFragment();
    FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
            mViewPager, mlistFragments);
    mViewPager.setAdapter(adapter);

    mPagerSlidingTabStrip.setIndicatorHeight(0);
    mPagerSlidingTabStrip.setIndicatorColorResource(R.color.white);
    mPagerSlidingTabStrip.setTextSelectColorResource(R.color.black);
    mPagerSlidingTabStrip.setTabWidth((int) ((int) MyLayoutAdapter
        .getInstance().getScreenWidth()));
    mPagerSlidingTabStrip.setViewPager(mViewPager, mlistTitle);
    mViewPager.setCurrentItem(miCurSelectedFragmentPosition);
  }

  private void addShopOrderFragment() {
    List<GoodsStatus> orderStatus = new ArrayList<GoodsStatus>();

    String type = getIntentHandle().getString("type");
    if (GoodsStatus.PUTONG.getValue().equals(type)) {
      orderStatus.add(GoodsStatus.PUTONG);
    } else if (GoodsStatus.XIANZHI.getValue().equals(type)) {
      orderStatus.add(GoodsStatus.XIANZHI);
    }

//		orderStatus.add(GoodsStatus.PAIPIN);
//		orderStatus.add(GoodsStatus.DINGZHI);
//		orderStatus.add(GoodsStatus.XIANZHI);

    for (GoodsStatus status : orderStatus) {
      GoodsFragment takeoutFragment = new GoodsFragment();
      takeoutFragment.setOrderStatus(status);
      mlistFragments.add(takeoutFragment);
      mlistTitle.add(status.getName());
    }
  }

  private GoodsFragment getCurFragment() {
    GoodsFragment fragment = (GoodsFragment) mlistFragments.get(miCurSelectedFragmentPosition);

    return fragment;
  }

  private void refreshCurFragment() {
    getCurFragment().queryListViewData();
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (ProviderResultActivity.CODE_EDIT_PRODUCT == resultCode) {
      getCurFragment().refreshData(true);
    }
  }
}