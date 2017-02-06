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

public class MNicknameActivity extends BaseActivity {


    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_mnickname_et_name)
    ClearEditText activityMnicknameEtName;
    @Bind(R.id.mnickname_save)
    FontTextView mnickname_save;


    String lastNickname = "";

    @Override
    protected void process() {
        Intent intent = getIntent();
        lastNickname = intent.getStringExtra("lastNickname");
        activityMnicknameEtName.setText(lastNickname);
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
        mnickname_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = activityMnicknameEtName.getText().toString().trim();

                if ("".equals(nickname)){
                    Utils.toast(MNicknameActivity.this,"昵称不能为空");
                }else if (nickname.equals(lastNickname)){
                    Utils.toast(MNicknameActivity.this,"未做任何修改");
                }else {
                    saveNickname(nickname);
                }
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_mnickname);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    /**
     * 更新用户昵称
     */
    public void saveNickname(final String nickName){
        Intent intentResult = new Intent();
        intentResult.putExtra("newNickname",nickName);
        setResult(RESULT_OK,intentResult);
        finish();
//        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.UPDATE_USER_NICKNAME,new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                Intent intentResult = new Intent();
//                intentResult.putExtra("newNickname",nickName);
//                setResult(RESULT_OK,intentResult);
//                finish();
//                return false;
//            }
//        }));
//        helper.setRequestParams(new String[][]{{"uid", BaseApplication.instance.getUid()},{"nickname",nickName}});
//        helper.sendPost();

    }
}
