
package me.kw.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.adapter.CartAdapter;
import me.kw.mall.constant.ResultActivity;
import me.kw.mall.dao.AddressDao;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.OrderBusiness;
import service.api.AddOrderResult;
import service.api.Address;
import service.api.CartItem;
import service.api.CommonReturn;
import service.entity.AddressService;
import service.entity.OrderService;

@ZTitleMore(false)
public class OrderEnsureActivity extends MallBaseActivity {
  @Bind(R.id.tvew_personal_show) TextView mTvewPersonal;
  @Bind(R.id.tvew_phone_show) TextView mTvewPhone;
  @Bind(R.id.tvew_address_show) TextView mTvewAddress;
  @Bind(R.id.tvew_settle_show) TextView mTvewSettle;
  @Bind(R.id.tvew_total_money_show) TextView mTvewTotalMoney;
  @Bind(R.id.llayout_product) LinearLayout mLlayoutProduct;
  @Bind(R.id.rlayout_address_info) RelativeLayout mRlayoutAddress;
  @Bind(R.id.rlayout_area_show) RelativeLayout mRlayoutArea;

  private List<Address.Data> mlistAddress;

  private List<CartItem> mCartItems;

  private Address.Data mAddress = null;

  private boolean isIntentToOrderSuccess = false;

    private int[] orderIds;

  private String mTotalPrice;

  @Override protected int getLayoutId() {
    return R.layout.activity_order_ensure;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("确认订单");
    Intent intent = getIntent();
    mCartItems = Arrays.asList(JsonSerializerFactory.Create().decode(
        intent.getStringExtra("product"), CartItem[].class));
    showOrderProduct(mCartItems);
    AddressDao.sendCmdQueryMyAddress(getHttpDataLoader());
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(AddressService.GetReceiverAddressRequest.class)) {
      Address response = msg.getRspObject();
      if (null != response && response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
        if (null != response.Data && response.Data.length > 0) {
          mlistAddress = new ArrayList<Address.Data>(Arrays.asList(response.Data));
          for (int i = 0; i < mlistAddress.size(); i++) {
            if (!TextUtils.isEmpty(mlistAddress.get(i).is_default)
                && Boolean.parseBoolean(mlistAddress.get(i).is_default)) {
              mAddress = mlistAddress.get(i);
              break;
            }
          }
          if (null == mAddress) {
            mAddress = mlistAddress.get(0);
          }
          showAddressInfo(mAddress);
        }
      }
    } else if (msg.valiateReq(OrderService.SubmitOrderRequest.class)) {
      AddOrderResult response = msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        ShowMsg.showToast(getApplicationContext(), "添加订单成功");
        BaseApplication.getInstance().setActivityResult(ResultActivity.CODE_ADD_ORDER, null);
//        if (response.Data.length > 1) {
//            Bundle bundle = new Bundle();
//            bundle.putString(GlobalConst.ORDER_STATUS,
//                    OrderStatus.PAY.getValue());
//            getIntentHandle().intentToActivity(bundle,
//                    MyShopOrderActivity_.class);
//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", response.Data[0].Id);
//            getIntentHandle().intentToActivity(bundle,
//                    MyShopOrderDetailActivity_.class);
//        }
        orderIds = new int[response.Data.length];
        for (int i = 0; i < response.Data.length; i++) {
          if (response.Data[i].Id != null) {
            orderIds[i] = Integer.parseInt(response.Data[i].Id);
          }
        }
        for (int i = 0; i < response.Data.length; i++) {
          if (response.Data[i].Id != null) {
            OrderBusiness.queryConfirmOrder(getHttpDataLoader(), response.Data[i].Id);
          }
        }

      } else {
        ShowMsg.showToast(getApplicationContext(), "添加订单失败");
      }
    } else if (msg.valiateReq(OrderService.ConfirmOrderRequest.class)) {
      CommonReturn response = (CommonReturn) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          if (!isIntentToOrderSuccess) {
//            getIntentHandle().intentToActivity(OrderSuccessActivity_.class);
            Bundle bundle = new Bundle();
            bundle.putIntArray("orderIds", orderIds);
            bundle.putString("price", mTotalPrice);
            getIntentHandle().intentToActivity(bundle, OrderPayActivity.class);
            finish();
            isIntentToOrderSuccess = true;
          }
        } else {
          dismissWaitDialog();
          ShowMsg.showToast(this, response.message);
        }
      } else {
        dismissWaitDialog();
        ShowMsg.showToast(this, msg, "提交订单失败");
      }
    }
  }

  private void showOrderProduct(List<CartItem> listCarts) {
    if (null == listCarts || listCarts.size() == 0) {
      return;
    }

    CartAdapter cartAdapter = new CartAdapter(this, listCarts);
    cartAdapter.setShowOrderProduct(true);
    BigDecimal totalMoney = new BigDecimal(0);
    for (int i = 0; i < listCarts.size(); i++) {
      CartItem.Data[] products = listCarts.get(i).product;
      LinearLayout linearLayout = new LinearLayout(this);
      linearLayout.setOrientation(LinearLayout.VERTICAL);
      linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

      View titleView = cartAdapter.getGroupView(i, false, null, null);
      linearLayout.addView(titleView);
      int count = 0;
      BigDecimal totalPrice = new BigDecimal(0);
      BigDecimal totalIdentifyPrice = new BigDecimal(0);
      for (int j = 0; j < products.length; j++) {
        View productView = cartAdapter.getChildView(i, j, false, null, null);
        linearLayout.addView(productView);

        TextView tvewLine = new TextView(this);
        tvewLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
            .MATCH_PARENT, 1));
        tvewLine.setBackgroundColor(getResources().getColor(
            R.color.list_line));
        linearLayout.addView(tvewLine);
        int quantity = Integer.parseInt(products[j].quantity);
        count = count + quantity;
        totalPrice = totalPrice.add(products[j].price.multiply(new BigDecimal(quantity)));
        totalIdentifyPrice = totalIdentifyPrice.add(products[j].identify_price);
      }
      totalMoney = totalMoney.add(totalPrice);
      View totalView = getTotalPriceView(count, totalPrice, totalIdentifyPrice);
      linearLayout.addView(totalView);
      mLlayoutProduct.addView(linearLayout);
    }
    mTotalPrice = StringUtil.formatProgress(totalMoney) + "";
    mTvewTotalMoney.setText("合计：¥" + mTotalPrice);
  }

  private View getTotalPriceView(int count, BigDecimal totalPrice,
                                 BigDecimal identifyPrice) {
    View view = LayoutInflater.from(this).inflate(R.layout.layout_cart_total, null);
    TextView tvewDelivery = (TextView) view.findViewById(R.id.tvew_delivery_price_show);
    TextView tvewDeliveryDesc = (TextView) view.findViewById(R.id.tvew_delivery_desc_show);
    TextView tvewTotalCount = (TextView) view.findViewById(R.id.tvew_total_count_show);
    TextView tvewTotalPrice = (TextView) view.findViewById(R.id.tvew_total_price_show);
    tvewDelivery.setText("¥" + StringUtil.formatProgress(identifyPrice));
    tvewTotalCount.setText("共" + count + "件商品");
    tvewTotalPrice.setText("¥" + StringUtil.formatProgress(totalPrice));
    return view;
  }

  private void showAddressInfo(Address.Data address) {
    if (null == address) {
      return;
    }
    mAddress = address;
    mTvewPersonal.setText("收货人：" + address.realname);
    mTvewPhone.setText(address.mobile);
    mTvewAddress.setText("收货地址：" + address.province + address.city + address.area + address
        .address);
    mRlayoutAddress.setVisibility(View.VISIBLE);
    mRlayoutArea.setVisibility(View.GONE);
  }

  @OnClick({R.id.rlayout_area_show, R.id.rlayout_address_info})
  void onClickRlayoutArea() {
    Bundle bundle = new Bundle();
    bundle.putBoolean("isSelectAddress", true);
    bundle.putString("address", JsonSerializerFactory.Create().encode(mlistAddress));
    getIntentHandle().intentToActivity(bundle, MyAddressActivity.class);
  }

  /**
   * 提交订单
   */
  @OnClick(R.id.tvew_settle_show)
  void onClickTvewSettle() {
    boolean isSend = OrderBusiness.addOrder(this, getHttpDataLoader(), mAddress, mCartItems);
    if (isSend) {
      showWaitDialog(1, false, R.string.common_submit_data);
    }
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (resultCode == ResultActivity.CODE_ADDRESS_SELECT) {
      Address.Data address = JsonSerializerFactory.Create().decode(
          intent.getStringExtra("address"),
          Address.Data.class);
      showAddressInfo(address);
    }
  }
}