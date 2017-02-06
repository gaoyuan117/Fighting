/*
 * TabHostActivity     2016/10/17 13:44
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.kw.mall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.zcomponent.activity.BaseNavgationActivity;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.util.MyLayoutAdapter;
import com.xiangying.fighting.R;

import me.kw.mall.fragment.CartFragment;
import me.kw.mall.fragment.CategroyFragment;
import me.kw.mall.fragment.HomeFragment;

/**
 * Created by Koterwong on 2016/10/17 13:44
 */
@ZTitleMore(false)
public class TabHostActivity extends BaseNavgationActivity {

  private String[] titles = {"首页", "分类", "购物车"};

  private int[] drawables = {
      R.drawable.tabhost_index_bg_selector,
      R.drawable.tabhost_fenlei_bg_selector,
      R.drawable.tabhost_gwc_bg_selector
//      ,R.drawable.tabhost_my_bg_selector
  };

  private Fragment[] fragment = {
      new HomeFragment(),
      new CategroyFragment(),
      new CartFragment()};

  @Override
  public void onCreate(Bundle arg0) {
    new MyLayoutAdapter(this);
    super.onCreate(arg0);
  }

  @Override
  public void showCurrentTab(int position) {
    super.showCurrentTab(position);
    if (position == 2 || position == 3) {
      getTitleBar().getBackTextView().setVisibility(View.GONE);
      getTitleBar().setTitleText(titles[position]);
      setTitleBarVisibility(View.VISIBLE);
    } else {
      setTitleBarVisibility(View.GONE);
    }
  }


  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  public int[] getDrawables() {
    return drawables;
  }

  @Override
  public String[] getTitles() {
    return titles;
  }

  @Override
  public int getTabSelectedBackground() {
    return R.drawable.transparent;
  }

  @Override
  public int getTabSelectedTextColor() {
    return R.color.tabhost_tab_tv_color_selector1;
  }

  @Override
  public Fragment[] getFragments() {
    return fragment;
  }

  @Override
  public int getNavBackgroundResource() {
    return R.drawable.black;
  }

  @Override
  public int getNavHeight() {
    return (int) (54 * MyLayoutAdapter.getInstance().getDensityRatio());
  }

  @Override
  public int getBackgroundResource() {
    return R.drawable.common_bg;
  }

  @Override
  public boolean isSetContentPadding() {
    return true;
  }

  @Override
  public int getTextDipSize() {
    return getResources().getDimensionPixelSize(R.dimen.dimen_text_sm);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
