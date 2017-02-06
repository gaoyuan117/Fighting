
package me.kw.mall.activity;


import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.fragment.BaseFragment;
import com.xiangying.fighting.R;

import me.kw.mall.enumerate.ProviderOrderStatus;

@ZTitleMore(false)
public class MyMoreOrderActivity extends MallBaseActivity {

  private OrderFragment mOrderFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_my_order_more;
  }

  @Override protected void initUI() {
    ProviderOrderStatus orderStatus = (ProviderOrderStatus) getIntent().getSerializableExtra
        ("orderstatus");
    getTitleBar().setTitleText(orderStatus.getName() + "订单");
    mOrderFragment = new OrderFragment();
    mOrderFragment.setOrderStatus(orderStatus);
    mOrderFragment.setOnFragmentCreatedListener(new BaseFragment.OnFragmentCreatedListener() {

      @Override
      public void onFragmentCreated() {
        mOrderFragment.queryListData();
      }
    });
    addFragment(R.id.flayout_more_order, mOrderFragment);
  }
}