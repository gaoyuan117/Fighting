package com.xiangying.fighting.ui.three.setting;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {
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
    @Bind(R.id.activity_setting_ll_aboutUs)
    LinearLayout activitySettingLlAboutUs;
    @Bind(R.id.activity_setting_ll_yijian)
    LinearLayout activitySettingLlYijian;
    @Bind(R.id.activity_setting_ll_help)
    LinearLayout activitySettingLlHelp;
    @Bind(R.id.activity_setting_ll_checkUpdate)
    LinearLayout activitySettingLlCheckUpdate;

    @Override
    protected void process() {
    }

    @Override
    protected void setAllClick() {
        //关于
        activitySettingLlAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
            }
        });
        //意见反馈
        activitySettingLlYijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, YijianActivity.class));
            }
        });

        //帮助中心
        activitySettingLlHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, HelpActivity.class));
            }
        });
        //检查更新
        activitySettingLlCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();//更新方法
            }
        });
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        Utils.toast(SettingActivity.this, "您的APP已经是最新版本！");
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("设置");
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
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
    }
}
