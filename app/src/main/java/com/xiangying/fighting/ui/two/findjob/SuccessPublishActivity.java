package com.xiangying.fighting.ui.two.findjob;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SuccessPublishActivity extends BaseActivity {


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

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("发布职位");
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }

}
