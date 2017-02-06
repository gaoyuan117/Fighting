package com.xiangying.fighting.ui.login;

import android.content.Intent;
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
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetPasswordActivity extends BaseActivity {

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


  boolean isRegister = true;
  private XUtilsHelper helperRegist;
  private String[][] registParams;
  private String phone, password, verifyCode = "";

  @Override
  protected void process() {
    registParams = new String[4][4];
    registParams[0][0] = "user";
    registParams[1][0] = "pwd";
    registParams[2][0] = "repwd";
    registParams[3][0] = "verify";
  }

  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      helperRegist.hideProgress();
      switch (msg.what) {
        case XUtilsHelper.TAG_SUCCESS:
          Utils.showHintByMsg(SetPasswordActivity.this, msg.obj.toString());
          savaInfo(msg.obj.toString());
          break;
        case XUtilsHelper.TAG_FAILURE:
          Utils.toast(SetPasswordActivity.this, "获取失败，请稍后再试");
          break;
      }
      return false;
    }
  });

  private void savaInfo(String data) {
    try {
      JSONObject object = new JSONObject(data);
      JSONObject datajson = object.getJSONObject("data");
      if (object.optInt("code") != 1) {
        return;
      }
      LoginUser loginUser = new LoginUser();
      if (!datajson.isNull("username")) {
        loginUser.nickname = datajson.optString("nickname");
        loginUser.password = datajson.optString("password");
        loginUser.username = datajson.optString("username");
        GetLocalKey.setUser(loginUser);
      }
      Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
      startActivity(intent);
      finish();
      RegistGetCodeActivity.activity.finish();
      RegistGetCodeActivity.activity = null;
    } catch (JSONException e) {
      e.printStackTrace();
      Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
      startActivity(intent);
      finish();
      RegistGetCodeActivity.activity.finish();
      RegistGetCodeActivity.activity = null;
    }
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
        if (CheckInput()) {
          if (isRegister) {
            helperRegist = new XUtilsHelper(SetPasswordActivity.this, NetworkTools.REGIST, mHandler);
            registParams[0][1] = phone;
            registParams[1][1] = password;
            registParams[2][1] = password;
            registParams[3][1] = verifyCode;
            helperRegist.setRequestParams(registParams);
            helperRegist.sendPost();
            helperRegist.showProgress("注册中，请稍后");
          } else {
            helperRegist = new XUtilsHelper(SetPasswordActivity.this, NetworkTools.SET_PASSWORD, mHandler);
            helperRegist.setRequestParams(new String[][]{
                {"user", phone},
                {"verify", verifyCode},
                {"pwd", password},
                {"repwd", password}
            });
            helperRegist.sendPost();
            helperRegist.showProgress("修改密码中，请稍后");
          }

        }

      }
    });

  }

  @Override
  protected void initViews() {
    if (getIntent().getStringExtra("type").equals("register")) {
      isRegister = true;
      titleBarCommonTvTitle.setText("设置密码");
    } else {
      isRegister = false;
      titleBarCommonTvTitle.setText("找回密码");
    }
    titleBarCommonTvTitle.setTextColor(getResources().getColor(R.color.text_grey));
    titleBarCommonIvOperate1.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_circle));
    titleBarCommonIvOperate3.setVisibility(View.GONE);
    titleBarCommonRvViewGroup.setBackgroundColor(getResources().getColor(R.color.bg_main));
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_set_password);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    phone = getIntent().getStringExtra("phone");
    verifyCode = getIntent().getStringExtra("code");

  }


  private boolean CheckInput() {
    if (TextUtils.isEmpty(registEditPassword.getText().toString())) {
      Toast.makeText(SetPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (TextUtils.isEmpty(registEditPassword2.getText().toString())) {
      Toast.makeText(SetPasswordActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (!registEditPassword.getText().toString().equals(registEditPassword2.getText().toString())) {
      Toast.makeText(SetPasswordActivity.this, "两次密码的输入不一致", Toast.LENGTH_SHORT).show();
      return false;
    }
    password = registEditPassword.getText().toString();
    return true;
  }
}
