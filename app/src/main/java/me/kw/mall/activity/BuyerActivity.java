/*
 * SellerActivity     2016/10/18 15:42
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.kw.mall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.constant.GlobalConst;
import me.kw.mall.enumerate.OrderStatus;

/**
 * Created by Koterwong on 2016/10/18 15:42
 */
@ZTitleMore(false)
public class BuyerActivity extends MallBaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView titleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;


  @Override protected int getLayoutId() {
    return R.layout.activity_buyer;
  }

  @Override protected void initUI() {
    titleBarCommonTvTitle.setText("我是买家");
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    titleBarCommonIvOperate1.setVisibility(View.VISIBLE);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @OnClick({R.id.rlayout_my_order_click, R.id.rlayout_my_favorite_product, R.id
      .rlayout_my_favorite_shop, R.id.rlayout_my_footprint, R.id.rlayout_my_merchant_click})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rlayout_my_order_click:
        if (!BaseApplication.isLogin()){
          BaseApplication.intentToLoginActivity(BuyerActivity.this);
          return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(GlobalConst.ORDER_STATUS, OrderStatus.PAY.getValue());
        getIntentHandle().intentToActivity(bundle,MyShopOrderActivity.class);
        break;
      case R.id.rlayout_my_favorite_product:
        if (!BaseApplication.isLogin()){
          BaseApplication.intentToLoginActivity(BuyerActivity.this);
          return;
        }
        getIntentHandle().intentToActivity(MyFavoriteProductActivity.class);
        break;
      case R.id.rlayout_my_favorite_shop:
        if (!BaseApplication.isLogin()){
          BaseApplication.intentToLoginActivity(BuyerActivity.this);
          return;
        }
        getIntentHandle().intentToActivity(MyFavoriteShopActivity.class);
        break;
      case R.id.rlayout_my_footprint:
        if (!BaseApplication.isLogin()){
          BaseApplication.intentToLoginActivity(BuyerActivity.this);
          return;
        }
        getIntentHandle().intentToActivity(MyBrowseProductActivity.class);
        break;
      case R.id.rlayout_my_merchant_click:
        if (!BaseApplication.isLogin()){
          BaseApplication.intentToLoginActivity(BuyerActivity.this);
          return;
        }
        getIntentHandle().intentToActivity(HelpActivity.class);
        break;
    }
  }
}
