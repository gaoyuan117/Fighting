package com.xiangying.fighting.ui.three.setting;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YijianActivity extends BaseActivity {


    @Bind(R.id.mnickname_save)
    FontTextView mnicknameSave;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_yijian_et_content)
    EditText activityYijianEtContent;

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        mnicknameSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交操作
                commit();
            }
        });

    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_yijian);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }

    public void commit(){
        String content = activityYijianEtContent.getText().toString().trim();

        if ("".equals(content)){
            Utils.toast(YijianActivity.this,"输入为空，对我们没有帮助哦");
        }else {
            finish();
        }

    }

}
