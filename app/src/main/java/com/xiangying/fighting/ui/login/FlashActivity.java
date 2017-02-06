package com.xiangying.fighting.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.xiangying.fighting.MainActivity;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlashActivity extends BaseActivity {

    @Bind(R.id.splash_logo)
    ImageView splashLogo;
    @Bind(R.id.splash_btn_regist)
    Button splashBtnRegist;
    @Bind(R.id.splash_btn_login)
    Button splashBtnLogin;

    private String[][] loginParams;
    private LoginUser loginUser = new LoginUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        splashBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashActivity.this, RegistGetCodeActivity.class);
                startActivity(intent);
            }
        });

        splashBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
//        loginUser = GetLocalKey.getUser();
//        if (!TextUtils.isEmpty(loginUser.username) && !TextUtils.isEmpty(loginUser.password)) {
//            loginParams = new String[3][3];
//            loginParams[0][0] = "user";
//            loginParams[1][0] = "pwd";
//            loginParams[2][0] = "driver";
//            loginParams[0][1] = loginUser.username;
//            loginParams[1][1] = loginUser.password;
//            loginParams[2][1] = "android";
//            login();
//        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void loadNetData() {

    }


    private void login() {
        XUtilsHelper helperLogin = new XUtilsHelper(this, NetworkTools.LOGIN, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                startActivity(new Intent(FlashActivity.this, MainActivity.class));
                return true;
            }
        }));
        helperLogin.setRequestParams(loginParams);
        helperLogin.sendPost();
        helperLogin.showProgress("登录中，请稍后");
    }
}
