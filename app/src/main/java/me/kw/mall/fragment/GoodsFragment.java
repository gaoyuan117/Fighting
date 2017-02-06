
package me.kw.mall.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xiangying.fighting.R;

import butterknife.Bind;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.enumerate.GoodsStatus;

public class GoodsFragment extends MallBaseFragment {
  private GoodsStatus mGoodsStatus;
  @Bind(R.id.llayout_putong_goods_type) LinearLayout mLlayoutPutongType;
  @Bind(R.id.rbtn_goods_manage_sell_click) RadioButton mRadionBtnSelling;
  @Bind(R.id.rbtn_goods_manage_warehouse_click) RadioButton mRadionBtnWarehouse;
  @Bind(R.id.radiogroup_show) RadioGroup mRadionGroup;
  @Bind(R.id.flayout_putaway) FrameLayout mFlayoutPutaway;
  @Bind(R.id.flayout_halt) FrameLayout mFlayoutHalt;

  private GoodStatusFragment mPutAwayFragment;
  private GoodStatusFragment mHaltFragment;
  private boolean isFragmentInstantiate = false;

  public void setOrderStatus(GoodsStatus orderStatus) {
    mGoodsStatus = orderStatus;
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_goods;
  }

  @Override protected void initUI() {
    mPutAwayFragment = new GoodStatusFragment();
    mPutAwayFragment.setOrderStatus(mGoodsStatus, 1);
    mPutAwayFragment.setTotalCountView(mRadionBtnSelling, mRadionBtnWarehouse);
    mPutAwayFragment.setOnFragmentCreatedListener(new OnFragmentCreatedListener() {

      @Override
      public void onFragmentCreated() {
        mPutAwayFragment.queryListViewData();
      }
    });

    mHaltFragment = new GoodStatusFragment();
    mHaltFragment.setOrderStatus(mGoodsStatus, 0);
    mHaltFragment.setTotalCountView(mRadionBtnSelling, mRadionBtnWarehouse);
    mHaltFragment.setOnFragmentCreatedListener(new OnFragmentCreatedListener() {

      @Override
      public void onFragmentCreated() {
        mHaltFragment.queryListViewData();
      }
    });
    addFragment(R.id.flayout_putaway, mPutAwayFragment);
    addFragment(R.id.flayout_halt, mHaltFragment);

    mFlayoutPutaway.setVisibility(View.VISIBLE);
    mFlayoutHalt.setVisibility(View.GONE);
    mLlayoutPutongType.setVisibility(View.VISIBLE);
    mRadionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbtn_goods_manage_sell_click) {
          mFlayoutPutaway.setVisibility(View.VISIBLE);
          mFlayoutHalt.setVisibility(View.GONE);
        } else {
          mFlayoutPutaway.setVisibility(View.GONE);
          mFlayoutHalt.setVisibility(View.VISIBLE);
        }
      }
    });
  }

  public void refreshData(boolean isRefreshByPull) {
    if (mRadionBtnSelling.isChecked()) {
      mPutAwayFragment.refreshData(isRefreshByPull);
    } else {
      mHaltFragment.refreshData(isRefreshByPull);
    }
  }

  public void queryListViewData() {
    if (!isFragmentInstantiate) {
      isFragmentInstantiate = true;

      if (mPutAwayFragment.isCreated()) {
        mPutAwayFragment.queryListViewData();
      }

      if (mHaltFragment.isCreated()) {
        mHaltFragment.queryListViewData();
      }
    }
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (ProviderResultActivity.CODE_EDIT_PRODUCT_PUTAWAY == resultCode) {
      mPutAwayFragment.refreshData(true);
      mHaltFragment.refreshData(true);
    }
  }
}