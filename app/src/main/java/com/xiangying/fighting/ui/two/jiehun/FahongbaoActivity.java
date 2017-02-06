package com.xiangying.fighting.ui.two.jiehun;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FahongbaoActivity extends BaseActivity {


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
    @Bind(R.id.activity_fahongbao_et_money)
    ClearEditText activityFahongbaoEtMoney;
    @Bind(R.id.activity_fahongbao_et_remark)
    ClearEditText activityFahongbaoEtRemark;
    @Bind(R.id.activity_fahongbao_tv_money)
    FontTextView activityFahongbaoTvMoney;
    @Bind(R.id.activity_fahongbao_tv_send)
    FontTextView activityFahongbaoTvSend;

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        //发送按钮
        activityFahongbaoTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FahongbaoActivity.this,HongbaoSuccessActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
        titleBarCommonTvTitle.setText("发红包");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_fahongbao);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }

}
