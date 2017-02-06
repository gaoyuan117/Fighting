package com.xiangying.fighting.ui.three.zichan;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddBankActivity extends BaseActivity {
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
    @Bind(R.id.et_add_bank_name)
    EditText mEtAddBankName;
    @Bind(R.id.et_add_bank_identity)
    EditText mEtAddBankIdentity;
    @Bind(R.id.et_add_bank_type)
    EditText mEtAddBankType;
    @Bind(R.id.et_add_bank_card_num)
    EditText mEtAddBankCardNum;
    @Bind(R.id.btn_recharge_click)
    Button mBtnRechargeClick;
    private XUtilsHelper mHelper;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddBankActivity.class);
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
        mTitleBarCommonTvTitle.setText("添加银行卡");
        mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBarCommonIvOperate3.setVisibility(View.GONE);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }


    @OnClick(R.id.btn_recharge_click)
    public void onClick() {
        addBank();
    }

    private void addBank() {
        String name = mEtAddBankName.getText().toString().trim();
        String identity = mEtAddBankIdentity.getText().toString().trim();
        String type = mEtAddBankType.getText().toString().trim();
        String num = mEtAddBankCardNum.getText().toString().trim();

        if(!match(name,identity,type,num)){
            return;
        }

        mHelper = new XUtilsHelper(this, NetworkTools.SELE_ADD_BANK, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn bankListBean = (CommonReturn) msg.obj;
                    if (bankListBean.code == 1) {
                        Toast.makeText(AddBankActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddBankActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddBankActivity.this, "异常", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        mHelper.showProgress("加载中...");
        mHelper.setRequestParams(new String[][]{{"card_no", num}, {"true_name", name}});
        mHelper.sendPostAuto(CommonReturn.class);
    }

    /**
     * 检测输入
     */
    private boolean match(String name, String identity, String type, String num) {

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(identity) || TextUtils.isEmpty(type) || TextUtils.isEmpty(num)) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (identity.length() < 18) {
            Toast.makeText(this, "请检查身份证号输入是否正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(num.length()<19){
            Toast.makeText(this, "请检查银行卡号输入是否正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
