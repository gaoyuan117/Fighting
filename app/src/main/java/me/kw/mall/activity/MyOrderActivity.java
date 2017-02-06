
package me.kw.mall.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.zcomponent.adapter.FragmentViewPagerAdapter;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.fragment.BaseFragment;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.LogEx;
import com.android.zcomponent.util.MyLayoutAdapter;
import com.android.zcomponent.views.PagerSlidingTabStrip;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.constant.ProviderGlobalConst;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.enumerate.ProviderOrderStatus;
import service.api.Order;

@ZTitleMore(false)
public class MyOrderActivity extends MallBaseActivity implements
    BaseFragment.OnFragmentCreatedListener {
  private final String TAG = "PersonalMyReserveOrderActivity";
  @Bind(R.id.viewpaper) ViewPager mViewPager;
  @Bind(R.id.tvew_more_order) TextView mTvewMore;
  @Bind(R.id.llayout_more_order) LinearLayout mLlayoutMore;
  @Bind(R.id.order_select_table_titlebar_tabs)
  PagerSlidingTabStrip mPagerSlidingTabStrip;

  private List<Fragment> mlistFragments;
  private List<String> mlistTitle;
  private int miCurSelectedFragmentPosition = 0;

  @Override protected int getLayoutId() {
    return R.layout.activity_order;
  }


  protected void init() {
    getTitleBar().setTitleText("我的订单");
  }

  @Override protected void initUI() {
    init();
    bindWidget();
    initViewPager();
  }

  private void bindWidget() {
    mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageSelected(int position) {
        miCurSelectedFragmentPosition = position;
        dismissOptionWindow();
        refreshCurFragment();
      }

      @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}

      @Override public void onPageScrollStateChanged(int arg0) {}
    });
  }

  @OnClick(R.id.tvew_more_order)
  public void onClickTvewMore() {
    showMorePopWindow();
    if (isOptionWindShowing()) {
      dismissOptionWindow();
    } else {
      showOptionWindow();
    }
  }

  private PopupWindow mMorePopupWindow;

  private void showMorePopWindow() {
    if (null == mMorePopupWindow) {
      View view = LayoutInflater.from(this).inflate(R.layout.pop_more_order, null);
      mMorePopupWindow = new PopupWindow(view, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager
          .LayoutParams.WRAP_CONTENT);
      TextView tvewAuction = (TextView) view.findViewById(R.id.tvew_auction_show);
      TextView tvewSended = (TextView) view.findViewById(R.id.tvew_sended_show);
      TextView tvewReview = (TextView) view.findViewById(R.id.tvew_review_show);
      TextView tvewComplete = (TextView) view.findViewById(R.id.tvew_complete_show);
      TextView tvewClose = (TextView) view.findViewById(R.id.tvew_close_show);
      TextView tvewRefund = (TextView) view.findViewById(R.id.tvew_refund_show);
      TextView tvewRefunded = (TextView) view.findViewById(R.id.tvew_refunded_show);

//			tvewAuction.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					dismissOptionWindow();
//					intentToMoreOrderActivity(ProviderOrderStatus.RECEIPT2);
//				}
//			});

//			tvewSended.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					dismissOptionWindow();
//					intentToMoreOrderActivity(ProviderOrderStatus.RECEIPT3);
//				}
//			});
      tvewReview.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dismissOptionWindow();
          intentToMoreOrderActivity(ProviderOrderStatus.RECEIPT2);
        }
      });
      tvewComplete.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dismissOptionWindow();
          intentToMoreOrderActivity(ProviderOrderStatus.RECEIPT3);
        }
      });
      tvewClose.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dismissOptionWindow();
          intentToMoreOrderActivity(ProviderOrderStatus.ENSURE);
        }
      });

      tvewRefund.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismissOptionWindow();
          intentToMoreOrderActivity(ProviderOrderStatus.COMPLETE);
        }
      });

      tvewRefunded.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismissOptionWindow();
          intentToMoreOrderActivity(ProviderOrderStatus.CLOSE);
        }
      });

      view.setOnTouchListener(new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
          if (event.getAction() == MotionEvent.ACTION_UP) {
            mMorePopupWindow.dismiss();
          }
          return false;
        }
      });
    }
  }

  /**
   * 查询其他订单   3：已收货/待评价，4：交易完成，5：交易取消，6：订单提交后的所有状态查询
   *
   * @param orderStatus
   */
  private void intentToMoreOrderActivity(ProviderOrderStatus orderStatus) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("orderstatus", orderStatus);
    getIntentHandle().intentToActivity(bundle, MyMoreOrderActivity.class);
  }

  /**
   * <p>
   * Description: 显示Popup筛选框
   * <p>
   *
   * @date 2013-6-26
   * @author zte
   */
  private void showOptionWindow() {
//    if (!mMorePopupWindow.isShowing()) {

      mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
      mMorePopupWindow.setTouchable(true);
      mMorePopupWindow.setOutsideTouchable(true);
      mMorePopupWindow.setFocusable(true);

      mMorePopupWindow.showAsDropDown(mLlayoutMore);
//      mMorePopupWindow.showAtLocation(mPagerSlidingTabStrip, Gravity.BOTTOM,0,0);
      mMorePopupWindow.update();
//    }
  }

  /**
   * <p>
   * Description: 判断筛选框是否显示
   * <p>
   *
   * @return
   * @date 2014-2-28
   * @author WEI
   */
  public boolean isOptionWindShowing() {
    if (null == mMorePopupWindow) {
      return false;
    }
    return mMorePopupWindow.isShowing();
  }

  /**
   * <p>
   * Description: 关闭筛选框
   * <p>
   *
   * @date 2014-2-28
   * @author WEI
   */
  public void dismissOptionWindow() {
    if (null != mMorePopupWindow && isOptionWindShowing()) {
      mMorePopupWindow.dismiss();
    }
  }

  public void initViewPager() {
    mlistFragments = new ArrayList<Fragment>();
    mlistTitle = new ArrayList<String>();

    addShopOrderFragment();
    FragmentViewPagerAdapter adapter =
        new FragmentViewPagerAdapter(getSupportFragmentManager(),
            mViewPager, mlistFragments);
    mViewPager.setAdapter(adapter);

    mPagerSlidingTabStrip.setIndicatorColorResource(R.color.red);
    mPagerSlidingTabStrip.setTextSelectColorResource(R.color.red);

    int moreWidth = MyLayoutAdapter.dip2px(this, 75);
    int width = (int) MyLayoutAdapter.getInstance().getScreenWidth() - moreWidth;
    mPagerSlidingTabStrip.setTabWidth((int) (width / 5));
    mPagerSlidingTabStrip.setViewPager(mViewPager, mlistTitle);
    mViewPager.setCurrentItem(miCurSelectedFragmentPosition);
  }

  private void addShopOrderFragment() {
    Intent intent = getIntent();
    String strOrderStatus =
        intent.getStringExtra(ProviderGlobalConst.ORDER_STATUS);
    List<ProviderOrderStatus> orderStatus =
        new ArrayList<ProviderOrderStatus>();
    orderStatus.add(ProviderOrderStatus.ALL);  // all ->  全部订单
    orderStatus.add(ProviderOrderStatus.PAY);  // 0 ->待支付
    orderStatus.add(ProviderOrderStatus.DELIVERY); //1 -> 待发货
    orderStatus.add(ProviderOrderStatus.RECEIPT1); // 2 -> 已发货

    int position = 0;
    for (ProviderOrderStatus status : orderStatus) {
      OrderFragment takeoutFragment = new OrderFragment();
      takeoutFragment.setOrderStatus(status);
      takeoutFragment.setOnFragmentCreatedListener(this);
      mlistFragments.add(takeoutFragment);
      mlistTitle.add(status.getName());
      if (strOrderStatus.equals(status.getValue())) {
        miCurSelectedFragmentPosition = position;
      }
      position++;
    }
  }

  private void refreshCurFragment() {
    OrderFragment fragment = (OrderFragment) mlistFragments
        .get(miCurSelectedFragmentPosition);
    if (fragment.isCreated()) {
      fragment.queryListData();
    }
  }

  @Override
  protected void onActivityResult(int arg0, int arg1, Intent data) {
  }

  /**
   * 消息返回
   */
  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    LogEx.d(TAG, "onActivityResultCallBack resultCode " + resultCode);

    OrderFragment fragment = (OrderFragment) mlistFragments.get
        (miCurSelectedFragmentPosition);

    if (resultCode == ProviderResultActivity.CODE_REFUND_SUCCESS) {
      fragment.refundSuccess();
    } else if (resultCode == ProviderResultActivity.CODE_PAY_ECO_SUCCESS) {
      fragment.payEcoSuccess(intent);
    } else if (resultCode == ProviderResultActivity.CODE_ADD_SHOP_REVIEW) {
      fragment.addShopReviewSuccess(intent);
    } else if (resultCode == ProviderResultActivity.CODE_UPDATE_ORDER) {
      String strOrder = intent.getStringExtra("order");
      if (!TextUtils.isEmpty(strOrder)) {
        Order order = JsonSerializerFactory.Create().decode(strOrder, Order.class);
        fragment.refreshItem(order);
      }
    } else if (resultCode == ProviderResultActivity.CODE_DISTINGUISH_STATUS) {
      fragment.refreshItem();
    } else if (resultCode == ProviderResultActivity.CODE_SEND_GOODS_SUCCESS) {
      fragment.refreshItem();
    } else if (resultCode == ProviderResultActivity.CODE_DEL_ORDER) {
      fragment.delItem();
    }
  }

  @Override
  public void onFragmentCreated() {
    refreshCurFragment();
  }
}
