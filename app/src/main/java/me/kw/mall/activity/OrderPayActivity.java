
package me.kw.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.kw.mall.alipay.AliPay;
import me.kw.mall.constant.ResultActivity;
import me.kw.mall.dao.ScoreDao;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.OrderBusiness;
import me.kw.mall.model.scoremodel.JifenPayOrderReturn;
import me.kw.mall.model.scoremodel.OrderMergeReturn;
import service.api.JiFenService;
import service.api.Order;
import service.api.OrderDetail;
import service.entity.OrderService;

@ZTitleMore(false)
public class OrderPayActivity extends MallBaseActivity {
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final String CHANNEL_LIMIT = "limit";
    private static final String CHANNEL_UPACP = "upacp";
    private static final String CHANNEL_WECHAT = "wx";
    private static final String CHANNEL_ALIPAY = "alipay";
    private static final String CHANNEL_BFB = "bfb";
    private static final String CHANNEL_JDPAY_WAP = "jy_wap";

    @Bind(R.id.checkbox_pay_limit_show)
    CheckBox mCheckboxPayLimit;
    @Bind(R.id.checkbox_pay_zhifb_show)
    CheckBox mCheckboxPayZhifb;
    @Bind(R.id.checkbox_pay_weixin_show)
    CheckBox mCheckboxPayWeixin;
    @Bind(R.id.checkbox_pay_yinlian_show)
    CheckBox mCheckboxPayYinlian;
    @Bind(R.id.tvew_amount_show)
    TextView mTvewAmount;
    //可以显示我的积分
    @Bind(R.id.tvew_wallet_blance_show)
    TextView mTvewBlance;
    @Bind(R.id.editvew_input_money)
    EditText mEditvewMoney;
    @Bind(R.id.tvew_settle_show)
    TextView mTvewSettle;
    @Bind(R.id.rlayout_pay_limit_click)
    LinearLayout mRlayoutLimit;

    private Order mOrder;
    private int[] orderIds;
    private OrderMergeReturn.Data mergeOrder;
    private String mTotalPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void initUI() {
        getTitleBar().setTitleText("选择支付方式");
        mEditvewMoney.addTextChangedListener(new StringUtil.DecimalTextWatcher(mEditvewMoney, 2));
        Intent intent = getIntent();
//    if (getIntentHandle().isIntentFrom(DistinguishEnsureActivity_.class)) {
//        mDistinguish = JsonSerializerFactory.Create().decode(
//                intent.getStringExtra("order"),
//                DistinguishItem.class);
//        mTvewAmount.setText("应支付：¥" + StringUtil.formatProgress(mDistinguish.total));
//    } else if (getIntentHandle().isIntentFrom(MyWalletActivity_.class)) {
//        getTitleBar().setTitleText("充值");
//        mTvewAmount.setVisibility(View.GONE);
//        mEditvewMoney.setVisibility(View.VISIBLE);
//        mRlayoutLimit.setVisibility(View.GONE);
//    } else {
        mOrder = JsonSerializerFactory.Create().decode(intent.getStringExtra("order"), Order.class);
        orderIds = intent.getIntArrayExtra("orderIds");
        mTotalPrice = intent.getStringExtra("price");
        mTvewAmount.setText("应支付：¥" + mTotalPrice);
        if (orderIds != null && orderIds.length > 0) {
            if (orderIds.length == 1) {
                OrderBusiness.queryOrderDetail(getHttpDataLoader(), "" + orderIds[0]);
                getDataEmptyView().showViewWaiting();
            } else {
                ScoreDao.sendOrderMergeOrder(getHttpDataLoader(), orderIds);
                getDataEmptyView().showViewWaiting();
            }
        }
        if (mOrder != null) {
            mTvewAmount.setText("应支付：¥" + StringUtil.formatProgress(mOrder.total_price));
        }
    }

    @Override
    public void onRecvMsg(MessageData msg) throws Exception {
        if (msg.valiateReq(OrderService.OrderDetailRequest.class)) {
            getDataEmptyView().dismiss();
            OrderDetail response = (OrderDetail) msg.getRspObject();
            if (CommonValidate.validateQueryState(this, msg, response)) {
                if (null != response && null != response.Data) {
                    mOrder = response.Data;
//          mTvewAmount.setText("应支付：¥" + StringUtil.formatProgress(mOrder.total_price));
                }
            }
        } else if (msg.valiateReq(JiFenService.JifenOrderMergeRequest.class)) {
            getDataEmptyView().dismiss();
            OrderMergeReturn response = msg.getRspObject();
            if (CommonValidate.validateQueryState(this, msg, response)) {
                if (null != response && null != response.data) {
                    mergeOrder = response.data;
                    mTvewAmount.setText("应支付：￥" + mergeOrder.price);
                }
            }
        } else if (msg.valiateReq(JiFenService.JifenPayProductRequest.class)) {
            getDataEmptyView().dismiss();
            JifenPayOrderReturn response = msg.getRspObject();
            if (response != null) {
                if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
                    getIntentHandle().intentToActivity(OrderSuccessActivity.class);
                    finish();
                } else {
                    showToast(response.message);
                }
            } else {
                showToast("支付失败");
            }
        }

//        if (msg.valiateReq(PayService.PayOrderRequest.class)) {
//            Payment response = (Payment) msg.getRspObject();

//              if (CommonValidate.validateQueryState(this, msg, response)) {
//                if (!TextUtils.isEmpty(response.Data)) {
//                    String data = response.Data;
//                    Intent intent =new Intent(OrderPayActivity.this, PaymentActivity.class);
//                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
//                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//                } else {
//                    ShowMsg.showToast(getApplicationContext(), msg, "请求支付失败");
//                }
//
//            } else {
//                ShowMsg.showToast(getApplicationContext(), msg, "请求支付失败");
//            }
//        }
    }

    @OnClick(R.id.rlayout_pay_limit_click)
    void onClickRlayoutPayLimit() {
        initCheckBox();
        mCheckboxPayLimit.setChecked(true);
    }

    @OnClick(R.id.rlayout_pay_zhifb_click)
    void onClickRlayoutPayZhifb() {
        initCheckBox();
        mCheckboxPayZhifb.setChecked(true);
    }

    @OnClick(R.id.rlayout_pay_weixin_click)
    void onClickRlayoutPayWeixin() {
        initCheckBox();
        mCheckboxPayWeixin.setChecked(true);
    }

    @OnClick(R.id.rlayout_pay_yinlian_click)
    void onClickRlayoutPayYinlian() {
        initCheckBox();
        mCheckboxPayYinlian.setChecked(true);
    }

    @OnCheckedChanged({R.id.checkbox_pay_limit_show,
            R.id.checkbox_pay_weixin_show, R.id.checkbox_pay_yinlian_show,
            R.id.checkbox_pay_zhifb_show})
    void onChecked(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mCheckboxPayLimit) {
            mCheckboxPayZhifb.setChecked(false);
            mCheckboxPayWeixin.setChecked(false);
            mCheckboxPayYinlian.setChecked(false);
        } else if (buttonView == mCheckboxPayZhifb) {
            mCheckboxPayLimit.setChecked(false);
            mCheckboxPayWeixin.setChecked(false);
            mCheckboxPayYinlian.setChecked(false);
        } else if (buttonView == mCheckboxPayWeixin) {
            mCheckboxPayLimit.setChecked(false);
            mCheckboxPayZhifb.setChecked(false);
            mCheckboxPayYinlian.setChecked(false);
        } else if (buttonView == mCheckboxPayYinlian) {
            mCheckboxPayLimit.setChecked(false);
            mCheckboxPayZhifb.setChecked(false);
            mCheckboxPayWeixin.setChecked(false);
        }
    }

    private void initCheckBox() {
        mCheckboxPayLimit.setChecked(false);
        mCheckboxPayZhifb.setChecked(false);
        mCheckboxPayWeixin.setChecked(false);
        mCheckboxPayYinlian.setChecked(false);
    }

    @OnClick(R.id.tvew_settle_show)
    void onClickTvewPaySubmit() {
        // 支付宝，微信支付，银联，百度钱包 按键的点击响应处理
        String payCode = null;
        if (mCheckboxPayYinlian.isChecked()) {
            payCode = CHANNEL_UPACP;
        } else if (mCheckboxPayZhifb.isChecked()) {
            payCode = CHANNEL_ALIPAY;
        } else if (mCheckboxPayWeixin.isChecked()) {
            payCode = CHANNEL_WECHAT;
        } else if (mCheckboxPayLimit.isChecked()) {
            payCode = CHANNEL_LIMIT;
        } else {
            ShowMsg.showToast(getApplicationContext(), "请选择支付方式");
            return;
        }
//     if (getIntentHandle().isIntentFrom(DistinguishEnsureActivity_.class)) {
//         if (null != mDistinguish) {
//             if (StringUtil.formatProgress(mDistinguish.total).doubleValue() <= 0) {
//                 ShowMsg.showToast(getApplicationContext(), "支付金额需大于零");
//                 return;
//             }
//             if (mCheckboxPayLimit.isChecked()) {
//                 PayBusiness.queryDistinguishBalancePay(getHttpDataLoader(),
//                         mDistinguish.id, mDistinguish.checkup_no,
//                         StringUtil.formatProgress(mDistinguish.total));
//             } else {
//                 PayBusiness.queryDistinguishPay(getHttpDataLoader(),
//                         payCode, mDistinguish.id, mDistinguish.checkup_no,
//                         StringUtil.formatProgress(mDistinguish.total));
//             }
//             showWaitDialog(1, false, R.string.common_submit_data);
//         }
//     } else if (getIntentHandle().isIntentFrom(MyWalletActivity_.class)) {
//         boolean isSend =
//                 PayBusiness.queryPayRecharge(this, getHttpDataLoader(),
//                         payCode, mEditvewMoney.getText().toString());
//         if (isSend) {
//             showWaitDialog(1, false, R.string.common_submit_data);
//         }
//     } else {
        if (null != mOrder || null != mergeOrder) {
            if (null != mOrder) {
                if (StringUtil.formatProgress(mOrder.total_price).doubleValue() <= 0) {
                    ShowMsg.showToast(getApplicationContext(), "支付金额需大于零");
                    return;
                }
            }

            if (null != mergeOrder) {
                if (Double.parseDouble(mergeOrder.price) <= 0) {
                    ShowMsg.showToast(getApplicationContext(), "支付金额需大于零");
                    return;
                }
            }

            if (mCheckboxPayLimit.isChecked()) {

                if (mOrder == null && mergeOrder != null) {
                    ScoreDao.sendJiFenPayProductRequest(getHttpDataLoader(), mergeOrder.order_no);
                } else if (mOrder != null && mergeOrder == null) {
                    ScoreDao.sendJiFenPayProductRequest(getHttpDataLoader(), mOrder.order_id);
                }
            } else if (mCheckboxPayZhifb.isChecked()) {
                AliPay aliPay = new AliPay(OrderPayActivity.this);
                if (mOrder == null && mergeOrder != null) {
                    aliPay.payV2(mergeOrder.price, mergeOrder.name, mergeOrder.name,
                            mergeOrder.order_no, getAliPayCallBack());
                } else if (mOrder != null && mergeOrder == null) {
                    aliPay.payV2("" + mTotalPrice, mOrder.products[0].shop_title, mOrder.products[0]
                                    .product_title,
                            mOrder.order_no, getAliPayCallBack());
                }
            } else if (mCheckboxPayWeixin.isChecked()) {
                // TODO: 2016/8/23 微信支付
            }
//            showWaitDialog(1, false, R.string.common_submit_data);
        } else {
            showToast("正在查询订单信息,请稍等...");
        }
//        }
    }

    private AliPay.AlipayCallBack getAliPayCallBack() {
        return new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                if (getIntentHandle().isIntentFrom(OrderEnsureActivity.class)) {
                    getIntentHandle().intentToActivity(OrderSuccessActivity.class);
                    finish();
                } else {
                    BaseApplication.getInstance().setActivityResult(ResultActivity.CODE_PAY_ECO_SUCCESS, null);
                    finish();
                }
            }

            @Override
            public void onDeeling() {
                ShowMsg.showToast(OrderPayActivity.this, "支付完成，后台处理中");
            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFailure(String msg) {
                ShowMsg.showToast(OrderPayActivity.this, msg);
            }
        };
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。 最终支付成功根据异步通知为准
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /*
                 * 处理返回值 "success" - payment succeed "fail" - payment failed
				 * "cancel" - user canceld "invalid" - payment plugin not
				 * installed
				 */
                if (result.equals("success")) {
                    ShowMsg.showToast(getApplicationContext(), "支付成功");
                    BaseApplication.getInstance().setActivityResult(ResultActivity.CODE_PAY_ECO_SUCCESS, null);
                } else if (result.equals("fail")) {
                    ShowMsg.showToast(getApplicationContext(), "支付失败");
                } else if (result.equals("cancel")) {
                    ShowMsg.showToast(getApplicationContext(), "支付取消");
                }
                finish();
            }
        }
    }
}