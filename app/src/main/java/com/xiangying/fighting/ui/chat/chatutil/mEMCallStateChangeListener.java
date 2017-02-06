package com.xiangying.fighting.ui.chat.chatutil;

import com.hyphenate.chat.EMCallStateChangeListener;

/**
 * Created by Administrator on 2016/9/1.
 */
public class mEMCallStateChangeListener implements EMCallStateChangeListener {

    @Override
    public void onCallStateChanged(CallState callState, CallError callError) {
        switch (callState) {
            case CONNECTING: // 正在连接对方

                break;
            case CONNECTED: // 双方已经建立连接

                break;

            case ACCEPTED: // 电话接通成功

                break;
            case DISCONNNECTED: // 电话断了

                break;
            case NETWORK_UNSTABLE: //网络不稳定
                if(callError == CallError.ERROR_NO_DATA){
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
}
