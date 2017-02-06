package com.xiangying.fighting.ui.three.zichan;

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
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopRechargeActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.tv_shaop_recharge_amount)
    TextView mAmount;
    @Bind(R.id.btn_recharge_tx)
    Button mTx;
    @Bind(R.id.et_shop_deposit_amount)
    EditText mCard;
    @Bind(R.id.et_shop_deposit_cardName)
    EditText mCardName;
    @Bind(R.id.et_shop_deposit_name)
    EditText mTrueName;
    @Bind(R.id.et_shop_deposit_phone)
    EditText mPhone;
    private AlertDialog dialog;
    private Button mCancle;
    private Button mSure;
    private EditText editText;
    private String mMoney, amount, card, cardName, name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_recharge);
        ButterKnife.bind(this);
        initTitle();
        mTx.setOnClickListener(this);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonTvTitle.setText("开店余额提现");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取开店余额
     */
    private void getData() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SELF_PROPERTY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                PropertyBean propertyBean = (PropertyBean) msg.obj;
                if (propertyBean.getCode() == 1) {
                    amount = propertyBean.getData().getShopAmount();
                    mAmount.setText("￥" + amount);
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
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge_tx://提现
                creatDialog();
                break;
            case R.id.bt_dialog_cancle://对话框取消
                dialog.dismiss();
                break;
            case R.id.bt_dialog_sure://对话框 确定
                mMoney = editText.getText().toString().trim();
                card = mCard.getText().toString().trim();
                cardName = mCardName.getText().toString().trim();
                name = mTrueName.getText().toString().trim();
                phone = mPhone.getText().toString().trim();
                judge();
                break;
        }
    }

    /**
     * 确认  提现
     */
    private void judge() {
        if (TextUtils.isEmpty(mMoney) || TextUtils.isEmpty(card) || TextUtils.isEmpty(cardName)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请检查输入", Toast.LENGTH_SHORT).show();
            return;
        }

        if (card.length() < 19) {
            Toast.makeText(this, "银行卡号输入错误", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Double.parseDouble(mMoney) > Double.parseDouble(amount)) {
            Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
            return;
        }
        commit();
    }

    /**
     * 提现
     */
    private void commit() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SHOP_TX, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean renZhengBean = (RenZhengBean) msg.obj;
                Toast.makeText(ShopRechargeActivity.this, renZhengBean.getMessage(), Toast.LENGTH_SHORT).show();
                getData();
                dialog.dismiss();
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"price",mMoney},{"bankname",card},{"name",name},{"tel",phone},{"kahao",card}});
        helper.sendPostAuto(RenZhengBean.class);
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
}
