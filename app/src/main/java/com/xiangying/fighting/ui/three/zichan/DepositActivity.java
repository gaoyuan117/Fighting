package com.xiangying.fighting.ui.three.zichan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import service.api.CommonReturn;

public class DepositActivity extends BaseActivity {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView mTitleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView mTitleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView mTitleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView mTitleBarCommonIvOperate2;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout mTitleBarCommonRvViewGroup;
    @Bind(R.id.et_deposit_name)
    EditText mBankName;
    @Bind(R.id.et_deposit_nickname)
    EditText mNickname;
    @Bind(R.id.et_deposit_tel)
    EditText mTel;
    @Bind(R.id.et_deposit_amount)
    EditText mAmount;
    @Bind(R.id.btn_desipot_click)
    Button mDesipotClick;
    @Bind(R.id.activity_deposit)
    LinearLayout activityDeposit;
    @Bind(R.id.et_deposit_card)
    EditText mCard;


    private XUtilsHelper mHelper;

    /**
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, DepositActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews() {
        mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBarCommonTvTitle.setText("提现");
        mTitleBarCommonIvOperate3.setVisibility(View.GONE);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    @OnClick({R.id.btn_desipot_click})
    public void onClick(View view) { //提交
        deposit();
    }

    private void deposit() {
        String bankName = mBankName.getText().toString().trim();
        String nickName = mNickname.getText().toString().trim();
        String tel = mTel.getText().toString().trim();
        String card = mCard.getText().toString().trim();
        String price = mAmount.getText().toString().trim();

        mHelper = new XUtilsHelper(this, NetworkTools.SELE_BANK_TIXIAN, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn bankListBean = (CommonReturn) msg.obj;
                    if (bankListBean.code == 1) {
                        Toast.makeText(DepositActivity.this, "提现成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DepositActivity.this, "提现失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DepositActivity.this, "提现失败", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        mHelper.showProgress("加载中...");
        mHelper.setRequestParams(new String[][]{{"price", price}, {"bankname",bankName}, {"name", nickName}, {"tel", tel}, {"kahao", card}});
        mHelper.sendPostAuto(CommonReturn.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
