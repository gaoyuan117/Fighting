package com.xiangying.fighting.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.xiangying.fighting.MainActivity;
import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.chat.runtimepermissions.PermissionsManager;

/**
 * chat mActivity，EaseChatFragment was used {@link #EaseChatFragment}
 */
public class ChatActivity extends EaseBaseActivity {
  public static ChatActivity activityInstance;
  private EaseChatFragment chatFragment;
  String toChatUsername;

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.em_activity_chat);
    activityInstance = this;
    toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
    chatFragment = new ChatFragment();
    chatFragment.setArguments(getIntent().getExtras());
    getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    activityInstance = null;
  }

  @Override
  protected void onNewIntent(Intent intent) {
    // make sure only one chat mActivity is opened
    String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
    if (toChatUsername.equals(username))
      super.onNewIntent(intent);
    else {
      finish();
      startActivity(intent);
    }

  }

  @Override
  public void onBackPressed() {
    chatFragment.onBackPressed();
    if (EasyUtils.isSingleActivity(this)) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }
  }

  public String getToChatUsername() {
    return toChatUsername;
  }

  public static void start(Context context, String userName) {
    Intent starter = new Intent(context, ChatActivity.class);
    starter.putExtra(EaseConstant.EXTRA_USER_ID, userName);
    starter.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
    context.startActivity(starter);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                   @NonNull int[] grantResults) {
    PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
  }
}
