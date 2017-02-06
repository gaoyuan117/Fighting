package com.xiangying.fighting.ui.three.selfinfo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MSignActivity extends BaseActivity {


    @Bind(R.id.mnickname_save)
    FontTextView mnicknameSave;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_msign_et_name)
    ClearEditText activityMsignEtName;


    String lastSign = "";

    @Override
    protected void process() {
        Intent intent = getIntent();
        lastSign = intent.getStringExtra("lastSign");
        activityMsignEtName.setText(lastSign);
    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //保存按钮
        mnicknameSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = activityMsignEtName.getText().toString().trim();

                if ("".equals(sign)){
                    Utils.toast(MSignActivity.this,"签名不能为空");
                }else if (sign.equals(lastSign)){
                    Utils.toast(MSignActivity.this,"未做任何修改");
                }else {
                    saveSign(sign);
                }
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_msign);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    /**
     * 更新用户签名
     */
    public void saveSign(final String sign){
        Intent intentResult = new Intent();
        intentResult.putExtra("newSign",sign);
        setResult(RESULT_OK, intentResult);
        finish();

//        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.UPDATE_USER_INFO,new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                Intent intentResult = new Intent();
//                intentResult.putExtra("newSign",sign);
//                setResult(RESULT_OK,intentResult);
//                finish();
//                return false;
//            }
//        }));
//        helper.setRequestParams(new String[][]{{"uid", BaseApplication.instance.getUid()},{"idiograph",sign}});
//        helper.sendPost();

    }

}
