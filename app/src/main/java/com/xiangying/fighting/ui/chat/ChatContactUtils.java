/*
 * ChatContractUtisl     2016/12/20 15:29
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.ui.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.xiangying.fighting.common.LogKw;
import com.xiangying.fighting.ui.chat.db.InviteMessgeDao;
import com.xiangying.fighting.ui.chat.help.DemoHelper;
import com.xiangying.fighting.ui.chat.wigdet.InviteMessage;
import com.xiangying.fighting.ui.chat.wigdet.UserDao;

/**
 * Created by Koterwong on 2016/12/20 15:29
 */
public class ChatContactUtils {
  public static final String TAG = ChatContactUtils.class.getSimpleName();

  /**
   * 发送好友请求
   *
   * @param activity   activity
   * @param toUserName 发送好友的用户名
   * @param reason     打招呼消息
   */
  public static void addContact(final Activity activity, final String toUserName, final String reason) {
    if (EMClient.getInstance().getCurrentUser().equals(toUserName)) {
      new EaseAlertDialog(activity, "不能添加自己").show();
      return;
    }

    if (DemoHelper.getInstance().getContactList().containsKey(toUserName)) {
      //let the user know the contact already in your contact list
      if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(toUserName)) {
        new EaseAlertDialog(activity, "此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可").show();
        return;
      }
      new EaseAlertDialog(activity, "此用户已经是你的好友啦").show();
      return;
    }

    new Thread(new Runnable() {
      public void run() {
        try {
          EMClient.getInstance().contactManager().addContact(toUserName, reason);
          activity.runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(activity, "发送成功", Toast.LENGTH_LONG).show();
            }
          });
        } catch (final Exception e) {
          activity.runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(activity, "发送请求失败", Toast.LENGTH_LONG).show();
              LogKw.e(TAG,"添加好友失败，" + e.getMessage());
            }
          });
        }
      }
    }).start();
  }

  /**
   * 删除联系人
   */
  public static void deleteContact(final Activity activity, final EaseUser tobeDeleteUser) {
    final ProgressDialog pd = new ProgressDialog(activity);
    pd.setMessage("正在删除");
    pd.setCanceledOnTouchOutside(false);
    pd.show();
    new Thread(new Runnable() {
      public void run() {
        try {
          EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
          // remove user from memory and database
          UserDao dao = new UserDao(activity);
          dao.deleteContact(tobeDeleteUser.getUsername());
          DemoHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());
          activity.runOnUiThread(new Runnable() {
            public void run() {
              pd.dismiss();
            }
          });
        } catch (final Exception e) {
          activity.runOnUiThread(new Runnable() {
            public void run() {
              pd.dismiss();
              Toast.makeText(activity, "删除失败", Toast.LENGTH_LONG).show();
            }
          });
        }
      }
    }).start();
  }

  /**
   * 同意加为好友
   *
   * @param activity activity
   * @param msg      msg
   */
  private void acceptInvitation(final Activity activity, final InviteMessage msg) {
    final ProgressDialog pd = new ProgressDialog(activity);
    pd.setMessage("正在同意...");
    pd.setCanceledOnTouchOutside(false);
    pd.show();

    new Thread(new Runnable() {
      public void run() {
        // call api
        try {
          if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {//accept be friends
            EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
          } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //accept application to join group
            EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(), msg.getGroupId());
          } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
            EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(), msg.getGroupInviter());
          }
          msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
          // update database
          ContentValues values = new ContentValues();
          values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
          InviteMessgeDao messgeDao = new InviteMessgeDao(activity);
          messgeDao.updateMessage(msg.getId(), values);
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              pd.dismiss();
            }
          });
        } catch (final Exception e) {
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              pd.dismiss();
              Toast.makeText(activity, "同意失败...", Toast.LENGTH_LONG).show();
            }
          });
        }
      }
    }).start();
  }

  /**
   * 拒绝好友申请。
   *
   * @param activity activity
   * @param msg      msg
   */
  private void refuseInvitation(final Activity activity, final InviteMessage msg) {
    final ProgressDialog pd = new ProgressDialog(activity);
    String str1 = "正在拒绝...";
    final String str2 = "已拒绝";
    final String str3 = "拒绝失败";
    pd.setMessage(str1);
    pd.setCanceledOnTouchOutside(false);
    pd.show();

    new Thread(new Runnable() {
      public void run() {
        // call api
        try {
          if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {//decline the invitation
            EMClient.getInstance().contactManager().declineInvitation(msg.getFrom());
          } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //decline application to join group
            EMClient.getInstance().groupManager().declineApplication(msg.getFrom(), msg.getGroupId(), "");
          } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
            EMClient.getInstance().groupManager().declineInvitation(msg.getGroupId(), msg.getGroupInviter(), "");
          }
          msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
          // update database
          ContentValues values = new ContentValues();
          values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
          InviteMessgeDao messgeDao = new InviteMessgeDao(activity);
          messgeDao.updateMessage(msg.getId(), values);
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              pd.dismiss();
            }
          });
        } catch (final Exception e) {
          activity.runOnUiThread(new Runnable() {
            @Override public void run() {
              pd.dismiss();
              Toast.makeText(activity, str3 + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          });
        }
      }
    }).start();
  }
}
