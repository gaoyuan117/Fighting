package com.xiangying.fighting.ui.three.company;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyActivity extends BaseActivity {

    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView mTitleBarCommonIvOperate1;
    @Bind(R.id.layout_about_company)
    LinearLayout mLayoutAboutCompany;
    @Bind(R.id.layout_release_infomation)
    LinearLayout mLayoutReleaseInfomation;

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
        setContentView(R.layout.activity_company);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    @OnClick({R.id.title_bar_common_iv_operate_1, R.id.layout_about_company, R.id.layout_release_infomation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_bar_common_iv_operate_1:
                finish();
                break;
            case R.id.layout_about_company:
                startActivity(new Intent(this, CompanyInfoActivity.class));
                break;
            case R.id.layout_release_infomation:
                startActivity(new Intent(this, CompanyJobActivity.class));
                break;
        }
    }
}
