package com.xiangying.fighting.ui.three.setting;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {


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
    @Bind(R.id.activity_about_us_tv_version_code)
    FontTextView activityAboutUsTvVersionCode;
    @Bind(R.id.activity_about_us_tv_introduce)
    FontTextView activityAboutUsTvIntroduce;

    @Override
    protected void process() {
        //设置版本号
        try {
            activityAboutUsTvVersionCode.setText("战斗吧：  "+getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("关于我们");
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
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }
}
