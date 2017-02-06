package com.xiangying.fighting.ui.three.zichan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.bean.PropertyBean;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZichanActivity extends BaseActivity {
    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_zichan_tv_number)
    FontTextView activityZichanTvNumber;
    @Bind(R.id.sdv_yue)
    SimpleDraweeView sdvYue;
    @Bind(R.id.tv_yue)
    FontTextView tvYue;
    @Bind(R.id.tv_yue_num)
    FontTextView tvYueNum;
    @Bind(R.id.activity_zichan_ll_yue)
    RelativeLayout activityZichanLlYue;
    @Bind(R.id.sdv_yinghangka)
    SimpleDraweeView sdvYinghangka;
    @Bind(R.id.tv_yinghangka)
    FontTextView tvYinghangka;
    @Bind(R.id.tv_yinghangka_num)
    FontTextView tvYinghangkaNum;
    @Bind(R.id.activity_zichan_ll_yinghangka)
    RelativeLayout activityZichanLlYinghangka;
    @Bind(R.id.sdv_tuandui)
    SimpleDraweeView sdvTuandui;
    @Bind(R.id.tv_tuandui)
    FontTextView tvTuandui;
    @Bind(R.id.tv_tuandui_num)
    FontTextView tvTuanduiNum;
    @Bind(R.id.activity_zichan_ll_tuandui)
    RelativeLayout activityZichanLlTuandui;
    @Bind(R.id.sdv_tuanzhu)
    SimpleDraweeView sdvTuanzhu;
    @Bind(R.id.tv_tuanzhu)
    FontTextView tvTuanzhu;
    @Bind(R.id.tv_tuanzhu_num)
    FontTextView tvTuanzhuNum;
    @Bind(R.id.activity_zichan_ll_tuanzhu)
    RelativeLayout activityZichanLlTuanzhu;
    @Bind(R.id.sdv_zhuanzhang)
    SimpleDraweeView sdvZhuanzhang;
    @Bind(R.id.tv_zhuanzhang)
    FontTextView tvZhuanzhang;
    @Bind(R.id.tv_zhuanzhang_num)
    FontTextView tvZhuanzhangNum;
    @Bind(R.id.activity_zichan_ll_zhuanzhang)
    RelativeLayout activityZichanLlZhuanzhang;
    @Bind(R.id.sdv_tixian)
    SimpleDraweeView sdvTixian;
    @Bind(R.id.tv_tixian)
    FontTextView tvTixian;
    @Bind(R.id.tv_tixian_num)
    FontTextView tvTixianNum;
    @Bind(R.id.activity_zichan_ll_tixian)
    RelativeLayout activityZichanLlTixian;
    @Bind(R.id.sdv_baozhengjin)
    SimpleDraweeView sdvBaozhengjin;
    @Bind(R.id.tv_baozhengjin)
    FontTextView tvBaozhengjin;
    @Bind(R.id.tv_baozhengjin_num)
    FontTextView tvBaozhengjinNum;
    @Bind(R.id.activity_zichan_ll_baozhengjin)
    RelativeLayout activityZichanLlBaozhengjin;

    @Bind(R.id.sdv_yue_me)
    SimpleDraweeView mSdvYueMe;
    @Bind(R.id.tv_yue_me)
    FontTextView mTvYueMe;
    @Bind(R.id.tv_yue_num_me)
    FontTextView mTvYueNumMe;
    @Bind(R.id.activity_zichan_ll_yue_me)
    RelativeLayout mActivityZichanLlYueMe;
    @Bind(R.id.activity_zichan_rl_rz)
    RelativeLayout mRenZheng;
    @Bind(R.id.activity_zichan_rl_cz)
    RelativeLayout mCz;

    private String recharge;


    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setVisibility(View.INVISIBLE);
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_zichan);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {
//        loadMyProperty();
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
                    //总资产
                    if (!TextUtils.isEmpty(propertyBean.getData().getCount())) {
                        activityZichanTvNumber.setText("￥" + propertyBean.getData().getCount());
                    }
                    //开店账户与额
                    tvYueNum.setText("￥" + propertyBean.getData().getShopAmount());
                    //银行卡
                    tvYinghangkaNum.setText(propertyBean.getData().getBankCount() + "张");
                    //我的余额
                    recharge = propertyBean.getData().getAmount();
                    mTvYueNumMe.setText(TextUtils.isEmpty(propertyBean.getData().getAmount()) ? "￥0.00" : "￥" + propertyBean.getData().getAmount());
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(PropertyBean.class);

    }

    @OnClick({R.id.activity_zichan_ll_yue, R.id.activity_zichan_ll_yue_me, R.id.activity_zichan_ll_yinghangka, R.id.activity_zichan_ll_tuandui, R.id.activity_zichan_ll_tuanzhu, R.id.activity_zichan_ll_zhuanzhang, R.id.activity_zichan_ll_tixian, R.id.activity_zichan_ll_baozhengjin, R.id.activity_zichan_rl_rz, R.id.activity_zichan_rl_cz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_zichan_ll_yue: //开店y余额
                startActivity(new Intent(this, ShopRechargeActivity.class));
                break;

            case R.id.activity_zichan_ll_yue_me: // 我的余额
                Intent intent = new Intent(this, RechargeActivity.class);
                intent.putExtra("recharge", recharge);
                startActivity(intent);
                break;

            case R.id.activity_zichan_ll_yinghangka://银行卡
                BankActivity.start(ZichanActivity.this);
                break;

            case R.id.activity_zichan_ll_tuandui:
                break;

            case R.id.activity_zichan_ll_tuanzhu:
                break;

            case R.id.activity_zichan_ll_zhuanzhang://转账
                TransferActivity.start(this);
                break;

            case R.id.activity_zichan_ll_tixian://提现
//                DepositActivity.start(this);
                startActivity(new Intent(this, TiXianActivity.class));
                break;

            case R.id.activity_zichan_ll_baozhengjin://保证金
                break;

            case R.id.activity_zichan_rl_rz://实名认证
                isIdentify();
                break;

            case R.id.activity_zichan_rl_cz://充值
                startActivity(new Intent(this, ChongZhiActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMyProperty();
    }

    /**
     * 判断是否注册过
     */
    private void isIdentify() {
        XUtilsHelper helper = new XUtilsHelper<>(getApplication(), NetworkTools.HASIDENTIFY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean rechareBean = (RenZhengBean) msg.obj;
                if (rechareBean.getCode() == 1) {
                    startActivity(new Intent(ZichanActivity.this, RenZhengActivity.class));
                } else {
                    startActivity(new Intent(ZichanActivity.this, RenZhengSuccessActivity.class));
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(RenZhengBean.class);
    }
}
