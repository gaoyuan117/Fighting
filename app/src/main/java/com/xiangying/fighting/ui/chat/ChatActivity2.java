/*
package com.xiangying.fighting.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.chat.videocall.VideoCallActivity;
import com.xiangying.fighting.common.AppContext;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.armygroup.GroupTalkDetailActivity;
import com.xiangying.fighting.ui.first.armyuser.FriendActivity;
import com.xiangying.fighting.ui.first.armyuser.UserTalkDetailActivity;

public class ChatActivity2 extends BaseActivity {

    private EaseChatFragment chatFragment;
    private String toChatUsername;
    private int TALK_TYPE=1;
    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        chatFragment.setViewClickCallBack(new EaseChatFragment.ViewClickCallBack() {
            @Override
            public void onRightBtnClick(View v) {
                Intent intent;
                if (TALK_TYPE== EaseConstant.CHATTYPE_SINGLE) {
                    intent=new Intent(ChatActivity2.this, UserTalkDetailActivity.class);
                } else {
                    intent=new Intent(ChatActivity2.this, GroupTalkDetailActivity.class);
                }
                intent.putExtra("id", toChatUsername);
                startActivity(intent);
            }

            @Override
            public void onAvatarClick(String s) {
                if(!s.equals(AppContext.instance.getUser().username)){
                    Intent intent=new Intent(ChatActivity2.this, FriendActivity.class);
                    intent.putExtra("userId",toChatUsername);
                    intent.putExtra("isfriend",true);
                    startActivity(intent);
                }
            }
        });

        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                            Intent intent=new Intent(ChatActivity2.this, VideoCallActivity.class);
                            intent.putExtra("username", EaseUserUtils.getUserInfo(toChatUsername).getNick());
                            startActivity(intent);
                        break;
                    case CONNECTED: // 双方已经建立连接

                        break;

                    case ACCEPTED: // 电话接通成功

                        break;
                    case DISCONNNECTED: // 电话断了

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }

            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else
        {
            finish();
            startActivity(intent);
        }

    }
    @Override
    protected void initViews() {
        */
/**
         *加载聊天界面
         * *//*


        toChatUsername=getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        TALK_TYPE=getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE,1);
        chatFragment = new EaseChatFragment();
        Bundle args = new Bundle();
        ImageView imageView=new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_chat_single);
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, TALK_TYPE);
        args.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void loadNetData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EaseChatFragment.ITEM_PICTURE:
                chatFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
*/
