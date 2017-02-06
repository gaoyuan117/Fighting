package com.xiangying.fighting.ui.chat.chatutil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.LogKw;
import com.xiangying.fighting.ui.chat.help.PreferenceManager;
import com.xiangying.fighting.utils.GetLocalKey;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ChaiUtils {

  public static void login(final LoginUser loginUser) {
    EMClient.getInstance().login(loginUser.getHx().getUsername(), loginUser.getHx().getEasemob(), new EMCallBack() {//回调
      @Override
      public void onSuccess() {
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        Log.d("main", "登录聊天服务器成功！" + loginUser.getHx().getUsername());
        PreferenceManager.getInstance().setCurrentUserName(loginUser.getHx().getUsername());
        PreferenceManager.getInstance().setCurrentUserAvatar(TalkBeans.Pics[0]);
      }

      @Override
      public void onProgress(int progress, String status) {

      }

      @Override
      public void onError(int code, String message) {
        Log.d("main", "登录聊天服务器失败！" + message + loginUser.getHx().getUsername());
      }
    });
  }

  public static void login() {
    final LoginUser user = GetLocalKey.getUser();
    if (user == null) {
      LogKw.e("loginEase,the use id null");
      return;
    }
    EMClient.getInstance().login(user.username, "123456", new EMCallBack() {
      @Override public void onSuccess() {
        LogKw.e("loginEase,success");
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
      }

      @Override public void onError(int i, String s) {
        LogKw.e("loginEase,onError---->" + "userName:" +  user.username + ",password:" +  user.password);
        LogKw.e("loginEase,onError---->" + "code:" +  i + ",message:" +  s);
      }

      @Override public void onProgress(int i, String s) {

      }
    });
  }

  public static void loginout() {
    //此方法为同步方法，里面的参数 true 表示退出登录时解绑 GCM 或者小米推送的 token
    EMClient.getInstance().logout(true);
  }

  public static void emptyHistory(Context context, final String chatUsername) {
    String msg = context.getResources().getString(com.hyphenate.easeui.R.string.Whether_to_empty_all_chats);
    new EaseAlertDialog(context, null, msg, null, new EaseAlertDialog.AlertDialogUser() {
      @Override
      public void onResult(boolean confirmed, Bundle bundle) {
        if (confirmed) {
          EMClient.getInstance().chatManager().deleteConversation(chatUsername, true);
        }
      }
    }, true).show();
  }
}
