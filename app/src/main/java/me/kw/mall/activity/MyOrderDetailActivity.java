
package me.kw.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.CommonUtil;
import com.android.zcomponent.util.ImageLoaderUtil;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.android.zcomponent.util.TimeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiangying.fighting.R;

import butterknife.Bind;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderOrderBusiness;
import service.api.Area;
import service.api.City;
import service.api.CommonReturn;
import service.api.Order;
import service.api.OrderDetail;
import service.api.Province;
import service.entity.AddressService;
import service.entity.OrderService;

@ZTitleMore(false)
public class MyOrderDetailActivity extends MallBaseActivity {
  @Bind(R.id.llayout_pay_time_show) LinearLayout mLlayoutPayTime;
  @Bind(R.id.llayout_send_time_show) LinearLayout mLlayoutSendTime;
  @Bind(R.id.llayout_receive_time_show) LinearLayout mLlayoutReceiveTime;
  @Bind(R.id.llayout_order_item) LinearLayout mLlayoutOrderItem;
  @Bind(R.id.rlayout_delivery_info_show_click) RelativeLayout mRlayoutDeliveryInfo;
  @Bind(R.id.tvew_delivery_state_show) TextView mTvewDeliveryState;
  @Bind(R.id.tvew_delivery_time_show) TextView mTvewDeliveryTime;
  @Bind(R.id.tvew_personal_show) TextView mTvewPersonal;
  @Bind(R.id.tvew_phone_show) TextView mTvewPhone;
  @Bind(R.id.tvew_address_show) TextView mTvewAddress;
  @Bind(R.id.tvew_order_name_show) TextView mTvewOrderName;
  @Bind(R.id.tvew_order_time_show) TextView mTvewOrderTime;
  @Bind(R.id.tvew_pay_time_show) TextView mTvewPayTime;
  @Bind(R.id.tvew_send_time_show) TextView mTvewSendTime;
  @Bind(R.id.tvew_receive_time_show) TextView mTvewReceiveTime;
  @Bind(R.id.tvew_order_pay_show_click) TextView mTvewOrderPay;
  @Bind(R.id.tvew_order_del_show_click) TextView mTvewOrderDelete;
  @Bind(R.id.tvew_order_review_show_click) TextView mTvewOrderReview;
  @Bind(R.id.tvew_order_refund_show_click) TextView mTvewOrderRefund;
  @Bind(R.id.tvew_order_refuse_refund_show_click) TextView mTvewOrderRefuseRefund;
  //    @Bind(R.id.tvew_order_delivery_show_click)
//    TextView mTvewOrderDelivery;
  @Bind(R.id.tvew_order_received_show_click) TextView mTvewOrderReceived;
  @Bind(R.id.tvew_order_receiving_show_click) TextView mTvewOrderReceiving;
  @Bind(R.id.tvew_order_send_show_click) TextView mTvewOrderSend;
  @Bind(R.id.tvew_order_detail_show_click) TextView mTvewOrderDetail;

  private Order mOrder;
  private ProviderOrderBusiness.OrderHelper mOrderHelper;

  /**
   * 完整的收货地址
   */
  private String mFullAddress = "";

  @Override protected int getLayoutId() {
    return R.layout.activity_my_order_detail;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("订单详情");

    Intent intent = getIntent();
    String order = intent.getStringExtra("order");
    mOrder = JsonSerializerFactory.Create().decode(order, Order.class);
    if (null != mOrder) {
      showOrderInfo(mOrder);
      this.queryProvince();
    } else {
      mOrderHelper = new ProviderOrderBusiness.OrderHelper(this, getHttpDataLoader());
      ProviderOrderBusiness.queryProviderOrderDetail(getHttpDataLoader(), mOrder.order_id);
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (null != mOrderHelper) {
      mOrderHelper.onRecvMsg(msg);
    }
    if (msg.valiateReq(OrderService.ShopOrderDetailRequest.class)) {
      //查询卖家订单详情返回
      OrderDetail response = (OrderDetail) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        if (null != response) {
          mOrder = response.Data;
          ProviderOrderBusiness.updateOrderList(mOrder);
          ProviderOrderBusiness.showAddressInfo(mOrder, mTvewPersonal, mTvewPhone, mTvewAddress);
          showOrderInfo(mOrder);
        }
      }
    } else if (msg.valiateReq(OrderService.DelOrderRequest.class)) {
      //删除订单
      CommonReturn response = (CommonReturn) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          ShowMsg.showToast(this, msg, "成功删除订单");
          FramewrokApplication.getInstance().setActivityResult(ProviderResultActivity
              .CODE_DEL_ORDER, null);
          finish();
        } else {
          ShowMsg.showToast(this, response.message);
        }
      } else {
        ShowMsg.showToast(this, msg, "订单删除失败");
      }
    } else if (msg.valiateReq(AddressService.GetProvincesRequest.class)) {
      Province response = (Province) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          if (null != mOrder) {
            for (int i = 0; i < response.Data.length; i++) {
              if (mOrder.province_id.equals(response.Data[i].province_id)) {
                mFullAddress += response.Data[i].province;
                this.queryCity(mOrder.province_id);
                break;
              }
            }
          }
        }
      }
    } else if (msg.valiateReq(AddressService.GetCitiesRequest.class)) {
      City response = (City) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          if (null != mOrder) {
            for (int i = 0; i < response.Data.length; i++) {
              if (mOrder.city_id.equals(response.Data[i].city_id)) {
                mFullAddress += response.Data[i].city;
                this.queryAres(mOrder.city_id);
                break;
              }
            }
          }
        }
      }
    } else if (msg.valiateReq(AddressService.GetAreasRequest.class)) {
      Area response = (Area) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          if (null != mOrder) {
            for (int i = 0; i < response.Data.length; i++) {
              if (mOrder.area_id.equals(response.Data[i].area_id)) {
                mFullAddress += response.Data[i].area;
                this.setAddressInfo();
                break;
              }
            }
          }
        }
      }
    }
  }


  /**
   * 设置地址信息
   */
  private void setAddressInfo() {
    mTvewAddress.setText(mFullAddress + mOrder.address);
    mTvewPersonal.setText("收货人：" + mOrder.consignee);
    mTvewPhone.setText(mOrder.mobile);
  }

  private void queryProvince() {
    AddressService.GetProvincesRequest request = new AddressService.GetProvincesRequest();
    getHttpDataLoader().doPostProcess(request, Province.class);
  }

  private void queryCity(String province_id) {
    AddressService.GetCitiesRequest request = new AddressService.GetCitiesRequest();
    request.ProvinceId = province_id;
    getHttpDataLoader().doPostProcess(request, City.class);
  }

  private void queryAres(String city_id) {
    AddressService.GetAreasRequest request = new AddressService.GetAreasRequest();
    request.CityId = city_id;
    getHttpDataLoader().doPostProcess(request, Area.class);
  }

  private void showOrderInfo(Order order) {

    //买方卖方订单状态一致
    TextView tvewState = (TextView) findViewById(R.id.tvew_order_state);
    tvewState.setText(order.status_text);

    TextView tvewOrderId = (TextView) findViewById(R.id.tvew_order_id_show);
    tvewOrderId.setText(order.order_no);

    TextView tvewOrderPayId = (TextView) findViewById(R.id.tvew_order_pay_id_show);

    TextView tvewAuctionPay = (TextView) findViewById(R.id.tvew_auction_pay_show);
    TextView tvewTotalPay = (TextView) findViewById(R.id.tvew_total_pay_show);
    tvewTotalPay.setText("¥" + StringUtil.formatProgress(order.total_price));
    tvewAuctionPay.setText("¥" + ProviderOrderBusiness.getTotalIdentifyPrice(order.products));

    if (null != order.products[0].shop_title) {
      mTvewOrderName.setText(order.products[0].shop_title);
    }

    LinearLayout llayoutPayNum = (LinearLayout) findViewById(R.id.llayout_order_pay);
    llayoutPayNum.setVisibility(View.GONE);
    mLlayoutPayTime.setVisibility(View.GONE);
    mLlayoutSendTime.setVisibility(View.GONE);
    mLlayoutReceiveTime.setVisibility(View.GONE);

    if (!TextUtils.isEmpty(order.create_time)) {
      mTvewOrderTime.setText(TimeUtil.transformLongTimeFormat(
          Long.parseLong(order.create_time) * 1000, TimeUtil.STR_FORMAT_DATE_TIME));
    }

    DisplayImageOptions options = ImageLoaderUtil.getDisplayImageOptions(R.drawable
        .img_empty_logo_small);

    ProviderOrderBusiness.showOrderItem(this, mLlayoutOrderItem,
        order.products, ImageLoader.getInstance(), options);

    ProviderOrderBusiness.showProviderOrderStateAction(order,
        mTvewOrderPay,
        mTvewOrderSend,
        mTvewOrderReceiving,
        mTvewOrderReceived,
        mTvewOrderRefund,
        mTvewOrderRefuseRefund,
        mTvewOrderDelete,
        mTvewOrderDetail,
        mTvewOrderReview);
    mTvewOrderDetail.setVisibility(View.GONE);

    findViewById(R.id.order_action).setVisibility(View.GONE);

    setOrderActionClickListener();

    for (int i = 0; i < mLlayoutOrderItem.getChildCount(); i++) {
      mLlayoutOrderItem.getChildAt(i).setOnClickListener(new OrderItemClickListener(i));
    }
  }

  /**
   * Action事件
   */
  private void setOrderActionClickListener() {
    //退款
//        mTvewOrderRefund.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (!CommonUtil.isLeastSingleClick()) {
//                    return;
//                }
//
//                ProviderOrderBusiness.queryProviderOrderRefund(
//                        getHttpDataLoader(), mOrder.order_id,
//                        mOrder.products[0].product_id);
//                showWaitDialog(2, false, R.string.common_submit_data);
//            }
//        });
//
//        //拒绝退款
//        mTvewOrderRefuseRefund.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (!CommonUtil.isLeastSingleClick()) {
//                    return;
//                }
//
//                ProviderOrderBusiness.queryProviderOrderRefuseRefund(
//                        getHttpDataLoader(), mOrder.order_id,
//                        mOrder.products[0].product_id);
//                showWaitDialog(2, false, R.string.common_submit_data);
//            }
//        });

//        //查看物流
//        mTvewOrderDelivery.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (!CommonUtil.isLeastSingleClick()) {
//                    return;
//                }
//
//                ProviderOrderBusiness.intentToExpressDetailActivity(
//                        MyOrderDetailActivity.this, mOrder.order_id,
//                        mOrder.products[0].image_path);
//            }
//        });

    //查看评价
    mTvewOrderReview.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("order", JsonSerializerFactory.Create().encode(mOrder));
        bundle.putBoolean("isSaler", true);
        ProviderOrderBusiness.intentToReadReviewActivity(MyOrderDetailActivity.this, bundle);
      }
    });

    //发货
    mTvewOrderSend.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View view) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        Intent intent = new Intent(getApplicationContext(), SendGoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order", JsonSerializerFactory.Create().encode(mOrder));
        intent.putExtra("address", mFullAddress);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });

    mTvewOrderDelete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }
        OrderService.DelOrderRequest request = new OrderService.DelOrderRequest();
        request.id = mOrder.order_id;
        getHttpDataLoader().doPostProcess(request, CommonReturn.class);
      }
    });
  }

  /**
   * OrderItem点击跳转详情
   */
  private class OrderItemClickListener implements OnClickListener {

    private int position;

    public OrderItemClickListener(int position) {
      this.position = position;
    }

    @Override
    public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClassName("com.android.juzbao.mActivity", "ProductDetailActivity_");
//            intent.putExtra("id", Integer.parseInt(mOrder.products[position].product_id));
//            startActivity(intent);
    }
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (resultCode == ProviderResultActivity.CODE_REFUND_SUCCESS
        || resultCode == ProviderResultActivity.CODE_PAY_ECO_SUCCESS
        || resultCode == ProviderResultActivity.CODE_ADD_SHOP_REVIEW) {
      ProviderOrderBusiness.queryProviderOrderDetail(getHttpDataLoader(), mOrder.order_id);
    }
  }

  /**
   * 显示地址信息
   *
   * @param order        order
   * @param tvewPersonal tvewPersonal
   * @param tvewPhone    tvewPhone
   * @param tvewAddress  tvewAddress
   */
  public static void showAddressInfo(Order order,
                                     TextView tvewPersonal, TextView tvewPhone, TextView
                                         tvewAddress) {
    if (null == order) {
      return;
    }
    tvewPersonal.setText("收货人：" + order.consignee);
    tvewPhone.setText(order.mobile);
  }
}