package com.xiangying.fighting.ui.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.MainActivity;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.common.BaseActivity2;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.JsonParse;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity2 {
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
    @Bind(R.id.regist_edit_password)
    EditText registEditPassword;
    @Bind(R.id.regist_edit_password2)
    EditText registEditPassword2;
    @Bind(R.id.regist_btn_ok)
    Button registBtnOk;
    @Bind(R.id.login_tv_foget)
    TextView loginTvFoget;

    private String name, password;
    private LoginUser loginUser = new LoginUser();

    private XUtilsHelper helperLogin;
    private String[][] loginParams;

    @Override
    protected void process() {
        loginParams = new String[3][3];
        loginParams[0][0] = "user";
        loginParams[1][0] = "pwd";
        loginParams[2][0] = "driver";

    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        loginTvFoget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegistGetCodeActivity.class);
                intent.putExtra("forget", "");
                startActivity(intent);
            }
        });
    }

    private void login() {
        if (CheckInput()) {
            helperLogin = new XUtilsHelper(LoginActivity.this, NetworkTools.LOGIN, mHandler);
            loginParams[0][1] = name;
            loginParams[1][1] = password;
            loginParams[2][1] = "android";
            helperLogin.setRequestParams(loginParams);
            helperLogin.sendPost();
            helperLogin.showProgress("登录中，请稍后");
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            helperLogin.hideProgress();
            switch (msg.what) {
                case XUtilsHelper.TAG_SUCCESS:
                    Utils.showHintByMsg(LoginActivity.this, msg.obj.toString());
                    try {
                        JSONObject object = new JSONObject(msg.obj.toString());
                        if (object.optInt("code") == 1) {
                            BaseApplication.instance.setUser(JsonParse.ParseLoginUserInfo(String.valueOf(object.getJSONObject("data"))));
                            BaseApplication.instance.setLogin(true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            SaveLoginInfo();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case XUtilsHelper.TAG_FAILURE:
                    Utils.toast(LoginActivity.this, "获取失败，请稍后再试");
                    break;
            }
            return false;
        }
    });

    private void SaveLoginInfo() {
        loginUser.username = name;
        loginUser.password = password;
        GetLocalKey.setUser(loginUser);
    }

    private boolean CheckInput() {
        name = registEditPassword.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        password = registEditPassword2.getText().toString();
        if (TextUtils.isEmpty(password) && password.length() < 6) {
            Toast.makeText(LoginActivity.this, "密码至少6位", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("登录");
        titleBarCommonTvTitle.setTextColor(getResources().getColor(R.color.text_grey));
        titleBarCommonIvOperate1.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_circle));
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonRvViewGroup.setBackgroundColor(getResources().getColor(R.color.bg_main));
        loginTvFoget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//底部加横线
        loginUser = GetLocalKey.getUser();
        registEditPassword.setText(loginUser.username);
        registEditPassword2.setText(loginUser.password);

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helperLogin = null;
        loginParams = null;
    }


}
