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

public class TransferActivity extends BaseActivity {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.et_transfer_no)
    EditText mNo;
    @Bind(R.id.et_transfer_prices)
    EditText mPrices;
    @Bind(R.id.btn_transfer_click)
    Button mClick;

    private XUtilsHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
        titleBarCommonTvTitle.setText("转账");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }


    public static void start(Context context) {
        Intent starter = new Intent(context, TransferActivity.class);
        context.startActivity(starter);
    }


    @OnClick({R.id.btn_transfer_click,R.id.title_bar_common_iv_operate_1})
    public void onClick(View view) { //提交
        switch (view.getId()){
            case R.id.btn_transfer_click:
                transfer();
                break;

            case R.id.title_bar_common_iv_operate_1:
                finish();
                break;
        }
    }

    /**
     * 转账
     */
    private void transfer() {
        String no  = mNo.getText().toString().trim();
        String price = mPrices.getText().toString().trim();

        mHelper = new XUtilsHelper(this, NetworkTools.SELE_BANK_TRANSFER, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn bankListBean = (CommonReturn) msg.obj;
                    if (bankListBean.code == 1) {
                        Toast.makeText(TransferActivity.this, "提现成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TransferActivity.this, "提现失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TransferActivity.this, "提现失败", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        mHelper.showProgress("加载中...");
        mHelper.setRequestParams(new String[][]{{"fid", no},{"price", price}});
        mHelper.sendPostAuto(CommonReturn.class);
    }

}
