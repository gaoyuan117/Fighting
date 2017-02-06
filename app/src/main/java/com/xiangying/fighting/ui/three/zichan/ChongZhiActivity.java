package com.xiangying.fighting.ui.three.zichan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.RecargeOrder;
import com.xiangying.fighting.ui.three.bean.RechareBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kw.mall.alipay.AliPay;
import service.api.CommonReturn;

public class ChongZhiActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.sp_cz_choose)
    Spinner mChoose;
    @Bind(R.id.et_cz_money)
    EditText mMoney;
    @Bind(R.id.bt_cz_sure)
    Button mSure;
    @Bind(R.id.cb_cz_wx)
    CheckBox mWx;
    @Bind(R.id.cb_cz_zfb)
    CheckBox mZfb;

    private String type, money, path, orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chong_zhi);
        ButterKnife.bind(this);
        initTitle();
        setListener();
    }

    /**
     * 充值
     */
    private void chongzhi(String path) {
        XUtilsHelper mHelper = new XUtilsHelper(this, path, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn bankListBean = (CommonReturn) msg.obj;

                    if (bankListBean.code == 1) {
                        Toast.makeText(ChongZhiActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChongZhiActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ChongZhiActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        mHelper.showProgress("加载中...");
        mHelper.setRequestParams(new String[][]{});
        mHelper.sendPostAuto(CommonReturn.class);
    }

    /**
     * 设置点击事件
     */
    private void setListener() {
        mSure.setOnClickListener(this);

        mWx.setOnCheckedChangeListener(this);
        mZfb.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonTvTitle.setText("充值");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        money = mMoney.getText().toString().trim();
        if (mZfb.isChecked()) {
            type = "zfb";
        } else if (mWx.isChecked()) {
            type = "wx";
        }

        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(type)) {
            Toast.makeText(this, "请选择充值方式", Toast.LENGTH_SHORT).show();
            return;
        }
        getOrder();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_cz_wx:
                if (mZfb.isChecked()) {
                    mZfb.setChecked(false);
                }
                mWx.setChecked(b);
                break;
            case R.id.cb_cz_zfb:
                if (mWx.isChecked()) {
                    mWx.setChecked(false);
                }
                mZfb.setChecked(b);
                break;
        }
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
                    if (type.equals("zfb")) {
                        payZfb(money, orderNo);
                    } else if (type.equals("wx")) {
                        payWx();
                    }
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"amount", money}});
        helper.sendPostAuto(RecargeOrder.class);
    }

    /**
     * 支付宝充值
     */
    private void payZfb(String money, String orderNo) {
        new AliPay(this).payV2(money, "充值", "余额充值", orderNo, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(ChongZhiActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                notifySuccess();
            }

            @Override
            public void onDeeling() {

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(ChongZhiActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
            }
        });
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

                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"trade_no", orderNo}});
        helper.sendPostAuto(RechareBean.class);
    }

    /**
     * 微信充值
     */
    private void payWx() {

    }

}
