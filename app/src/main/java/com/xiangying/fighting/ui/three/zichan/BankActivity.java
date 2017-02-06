package com.xiangying.fighting.ui.three.zichan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.BankListBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.NoScrollerListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankActivity extends BaseActivity {
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
    @Bind(R.id.list_item)
    NoScrollerListView mListItem;
    @Bind(R.id.tv_add_bank)
    TextView mTvAddBank;
    @Bind(R.id.ll_bank_add)
    LinearLayout mBankCard;

    private String t;

    public static void start(Context context) {
        Intent starter = new Intent(context, BankActivity.class);
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
        mTitleBarCommonTvTitle.setText("银行卡");
        mTitleBarCommonIvOperate3.setVisibility(View.GONE);

        Intent intent = getIntent();
        if (intent != null) {
            t = intent.getStringExtra("t");
        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SELE_BANK_LIST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                try {
                    BankListBean bankListBean = (BankListBean) msg.obj;
                    if (bankListBean.getCode() == 1) {
                        int count = bankListBean.getData().getResult().size();

                        for (int i = 0; i < count; i++) {
                            View view = View.inflate(BankActivity.this, R.layout.bank_card, null);
                            TextView name = (TextView) view.findViewById(R.id.tv_card_name);
                            TextView type = (TextView) view.findViewById(R.id.tv_card_type);
                            TextView no = (TextView) view.findViewById(R.id.tv_card_no);

                            String cardNo = bankListBean.getData().getResult().get(i).getCard_no().substring(15);
                            no.setText(cardNo);

                            String title = bankListBean.getData().getResult().get(i).getBank_title();
                            if (!TextUtils.isEmpty(title)) {
                                String[] types = title.split("-");
                                type.setText(types[2]);

                                name.setText(types[0]);
                            }

                            final int finalI = i;
                            if (!TextUtils.isEmpty(t)) {
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.putExtra("position", finalI);
                                        setResult(123, intent);
                                        finish();
                                    }
                                });
                            }

                            mBankCard.addView(view);
                        }
                    } else {
                        Toast.makeText(BankActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(BankListBean.class);
    }

    @OnClick({R.id.title_bar_common_iv_operate_1, R.id.tv_add_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_bar_common_iv_operate_1:
                finish();
                break;
            case R.id.tv_add_bank:
                AddBankActivity.start(this);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
