/*
 * UpLoadUtisl     2016/12/14 09:03
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.ui.three.selfinfo;

import android.os.Handler;
import android.os.Message;

import com.android.zcomponent.http.HttpDataLoader;
import com.android.zcomponent.http.api.model.MessageData;

import service.api.UploadFile;
import service.entity.FileService;

/**
 * Created by Koterwong on 2016/12/14 09:03
 */
public class UpLoadUtil {
  private Handler mHandler;
  private HttpDataLoader mHttpDataLoader;
  private static UpLoadUtil mUpLoadUtisl;
  private MsgCallback mMsgCallback;
  public interface MsgCallback{
    void onRecvMsg(MessageData messageData);
  }

  public static UpLoadUtil get() {
    if (mUpLoadUtisl == null) {
      return mUpLoadUtisl = new UpLoadUtil();
    }
    return mUpLoadUtisl;
  }

  private UpLoadUtil() {
    mHandler = new Handler(){
      @Override public void handleMessage(Message msg) {
        MessageData msgData = (MessageData) msg.obj;
        if (null == msgData) {
          return;
        }
        onRecvMsg(msgData);
      }
    };
    mHttpDataLoader = new HttpDataLoader(mHandler);
  }

  public UpLoadUtil setCallBack(MsgCallback msgCallback) {
    this.mMsgCallback = msgCallback;
    return this;
  }

  private void onRecvMsg(MessageData msgData) {
    if (mMsgCallback != null) {
      mMsgCallback.onRecvMsg(msgData);
    }
  }

  public UpLoadUtil sendPostRequest(String filePath) {
    FileService.PostFileRequest request = new FileService.PostFileRequest();
    getHttpDataLoader().doPostFileProcess(request, filePath, UploadFile.class);
    return this;
  }

  public UpLoadUtil sendPostVideoRequest(String filePath) {
    FileService.PostFileVideoRequest request = new FileService.PostFileVideoRequest();
    getHttpDataLoader().doPostFileProcess(request, filePath,  UploadFile.class);
    return this;
  }

  private HttpDataLoader getHttpDataLoader() {
    return mHttpDataLoader;
  }
}
