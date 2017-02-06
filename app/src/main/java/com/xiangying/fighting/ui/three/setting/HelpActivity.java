package com.xiangying.fighting.ui.three.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {

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
    @Bind(R.id.activity_help2)
    LinearLayout activityHelp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonTvTitle.setText("帮助中心");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
    }
}
