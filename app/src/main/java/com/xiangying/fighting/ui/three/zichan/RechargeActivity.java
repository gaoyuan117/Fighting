package com.xiangying.fighting.ui.three.zichan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.PropertyBean;
import com.xiangying.fighting.ui.three.bean.RecargeOrder;
import com.xiangying.fighting.ui.three.bean.RechareBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kw.mall.alipay.AliPay;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.title_bar_common_iv_operate_3)
    TextView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    private TextView mRecharge;
    private String mMoney;
    private Button mSure, mCancle;
    private EditText editText;
    private AlertDialog dialog;
    private String orderNo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(RechargeActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
            notifySuccess();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        initTitle();
        mRecharge = (TextView) findViewById(R.id.tv_recharge);
        String recharge = getIntent().getStringExtra("recharge");
        if (!TextUtils.isEmpty(recharge)) {
            mRecharge.setText(recharge);
        }
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBarCommonTvTitle.setText("余额");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RechargeActivity.this, ZiJinDetailActivity.class));
            }
        });

    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_cz://充值
//                creatDialog();
                startActivity(new Intent(this, ChongZhiActivity.class));
                break;
            case R.id.bt_dialog_cancle://对话框取消
                dialog.dismiss();
                break;
            case R.id.bt_dialog_sure://对话框 确定
                mMoney = editText.getText().toString();
                if (TextUtils.isEmpty(mMoney)) {
                    return;
                }
                getOrder();
                break;
        }

    }

    /**
     * 创建对话框
     */
    public void creatDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.rechare_dialog, null);
        editText = (EditText) v.findViewById(R.id.et_dialog_money);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        dialog = builder.create();
        dialog.show();
        mCancle = (Button) v.findViewById(R.id.bt_dialog_cancle);
        mSure = (Button) v.findViewById(R.id.bt_dialog_sure);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);
    }

    /**
     * 充值
     */
    private void pay(String money, String orderNo) {
        new AliPay(this).payV2(money, "充值", "余额充值", orderNo, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onDeeling() {

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(RechargeActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取订单号并支付
     */
    private void getOrder() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.RECHARGE_ORDER, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RecargeOrder order = (RecargeOrder) msg.obj;
                if (order.getCode() == 1) {
                    orderNo = order.getData();
                    dialog.dismiss();
                    pay(mMoney, orderNo);
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"amount", mMoney}});
        helper.sendPostAuto(RecargeOrder.class);
    }

    /**
     * 支付成功通知后台
     */
    private void notifySuccess() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.NOTIFY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RechareBean commonReturn = (RechareBean) msg.obj;
                if (commonReturn.getCode() == 1) {
                    mRecharge.setText(commonReturn.getData());
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"trade_no", orderNo}});
        helper.sendPostAuto(RechareBean.class);
    }


    /**
     * load我的资产
     */
    public void loadMyProperty() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SELF_PROPERTY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    PropertyBean propertyBean = (PropertyBean) msg.obj;

                    //我的余额
                    String recharge = propertyBean.getData().getAmount();
                    mRecharge.setText(TextUtils.isEmpty(propertyBean.getData().getAmount()) ? "￥0.00" : "￥" + propertyBean.getData().getAmount());
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(PropertyBean.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMyProperty();
    }
}
