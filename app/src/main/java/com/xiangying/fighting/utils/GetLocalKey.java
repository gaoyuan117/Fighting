package com.xiangying.fighting.utils;

import android.content.SharedPreferences;

import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.common.BaseApplication;

public class GetLocalKey {
  private static SharedPreferences userInfo;

  private static SharedPreferences getUserInfo() {
    if (userInfo == null) {
      userInfo = BaseApplication.AppContext.getSharedPreferences("userInfo", 0);
    }
    return userInfo;
  }

  /**
   * 是否第一次进入app
   */
  public static boolean getIsLaunched() {
    SharedPreferences sharedPreferences = getUserInfo();
    return sharedPreferences.getBoolean("isLaunch", false);
  }

  public static void setIsLaunched(boolean b) {
    SharedPreferences.Editor edit = getUserInfo().edit();
    edit.putBoolean("isLaunch", b);
    edit.commit();
  }

  /**
   * 保存用户信息
   */
  public static LoginUser getUser() {
    SharedPreferences sharedPreferences = getUserInfo();
    LoginUser loginUser = new LoginUser();
    loginUser.username = sharedPreferences.getString("username", "");
    loginUser.password = sharedPreferences.getString("password", "");
    loginUser.token = sharedPreferences.getString("token", "");
    return loginUser;
  }

  public static String getUserName() {
    SharedPreferences sharedPreferences = getUserInfo();
    return sharedPreferences.getString("username", "");
  }

  public static void setUser(LoginUser loginUserInfo) {
    SharedPreferences.Editor edit = getUserInfo().edit();
    edit.putString("username", loginUserInfo.username);
    edit.putString("password", loginUserInfo.password);
    edit.putString("token", loginUserInfo.token);
    edit.commit();
  }


  public static void setNickName(String nikeName) {
    SharedPreferences.Editor editor = getUserInfo().edit();
    editor.putString("nickname", nikeName);
    editor.commit();
  }

  public static void setUid(String uid) {
    SharedPreferences.Editor editor = getUserInfo().edit();
    editor.putString("uid", uid);
    editor.commit();
  }

  public static String getUid() {
    SharedPreferences userInfo = getUserInfo();
    return userInfo.getString("uid", "");
  }

  public static String getNickName() {
    SharedPreferences userInfo = getUserInfo();
    return userInfo.getString("nickname", "");
  }

  public static void setToken(String token) {
    SharedPreferences.Editor edit = getUserInfo().edit();
    edit.putString("token", token);
    edit.commit();
  }

  public static String getToken() {
    SharedPreferences edit = getUserInfo();
    return edit.getString("token", "");
  }
}
