package com.xiangying.fighting.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistGetCodeActivity extends BaseActivity {

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
    @Bind(R.id.regist_edit_phone)
    EditText registEditPhone;
    @Bind(R.id.regist_edit_code)
    EditText registEditCode;
    @Bind(R.id.regist_btn_next)
    Button registBtnNext;
    @Bind(R.id.regist_btn_getcode)
    Button registBtnGetcode;

    public static Activity activity;
    private TimeCount timeCount;
    private   XUtilsHelper helperGetCode;

    private String  phone,code;
    private String [][]getcodeParams;
    private String  type="register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
    }

    @Override
    protected void process() {
        if(getIntent().hasExtra("forget")){
        }
         timeCount=new TimeCount(60*1000,1000);
        getcodeParams=new String[2][2];
        getcodeParams[0][0]="type";getcodeParams[0][1]=type;getcodeParams[1][0]="user";

    }

    private void stratNetWork(){
        helperGetCode=new XUtilsHelper(RegistGetCodeActivity.this, NetworkTools.GET_VERIFY_CODE,mHandler);
        getcodeParams[1][1]=phone;
        helperGetCode.setRequestParams(getcodeParams);
        helperGetCode.sendPost();
        helperGetCode.showProgress("获取验证码中，请稍后");
    }
    @Override
    protected void setAllClick(){
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registBtnGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=registEditPhone.getText().toString();
                if(TextUtils.isEmpty(phone)||phone.length()<11){
                    Toast.makeText(RegistGetCodeActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    return ;
                }
                stratNetWork();
            }
        });

        registBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(CheckInput()){
                     if(type.equals("forgotten")){
                    helperGetCode=new XUtilsHelper(RegistGetCodeActivity.this, NetworkTools.FIND_PASSWORD,new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            helperGetCode.hideProgress();
                            String result=msg.obj.toString();
                            Utils.showHintByMsg(RegistGetCodeActivity.this,result);
                            try {
                                JSONObject object= new JSONObject(result);
                                if(object.optInt("code")!=1){
                                    return true;
                                }
                                object = new JSONObject(msg.obj.toString());
                                JSONObject datajson=object.getJSONObject("data");
                                code=datajson.optString("verify");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                                startActivity();
                            return false;
                        }
                    }));
                    helperGetCode.setRequestParams(new String[][]{
                            {"user",phone},
                            {"verify",code}
                    });
                    helperGetCode.sendPost();
                    helperGetCode.showProgress("验证中,请稍后...");
                   return;
                }
                     startActivity();
                 }
            }
        });

    }

    private void startActivity(){
        Intent intent=new Intent(RegistGetCodeActivity.this,SetPasswordActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("code",code);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            helperGetCode.hideProgress();
            switch (msg.what){
                case XUtilsHelper.TAG_SUCCESS:
                    String result=msg.obj.toString();
                    Utils.showHintByMsg(RegistGetCodeActivity.this,result);
                    timeCount.start();
                    registBtnGetcode.setEnabled(false);
                    registBtnGetcode.setTextColor(getResources().getColor(R.color.colorTextGray));
                    break;
                case XUtilsHelper.TAG_FAILURE:
                    Utils.toast(RegistGetCodeActivity.this,"获取失败，请稍后再试");
                    break;
            }
            return false;
        }
    });
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
        }

        @Override
        public void onFinish() {
            registBtnGetcode.setText("重发验证码");
            registBtnGetcode.setEnabled(true);
            registBtnGetcode.setTextColor(getResources().getColor(R.color.main_color));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            registBtnGetcode.setText(millisUntilFinished / 1000 + "秒后可重发");
        }
    }
    @Override
    protected void initViews() {
        if(!getIntent().hasExtra("forget")){
            titleBarCommonTvTitle.setText("注册");
        }else {
            type="forgotten";
            titleBarCommonTvTitle.setText("找回密码");
        }
        titleBarCommonTvTitle.setTextColor(getResources().getColor(R.color.text_grey));
        titleBarCommonIvOperate1.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_circle));
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonRvViewGroup.setBackgroundColor(getResources().getColor(R.color.bg_main));
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_regist_get_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    private boolean CheckInput(){
        phone=registEditPhone.getText().toString();
        if(TextUtils.isEmpty(phone)||phone.length()<11){
            Toast.makeText(RegistGetCodeActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            return false;
        }
        code=registEditCode.getText().toString();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(RegistGetCodeActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
