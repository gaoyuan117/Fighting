
package me.kw.mall.activity;

import com.android.zcomponent.annotation.ZTitleMore;
import com.xiangying.fighting.R;

import me.kw.mall.fragment.CartFragment;
@ZTitleMore(false)
public class CartActivity extends MallBaseActivity {

  private CartFragment mCartFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_cart;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("购物车");
    mCartFragment = new CartFragment();
    addFragment(R.id.flayout_cart, mCartFragment);
  }
}