package com.xiangying.fighting.utils;

import android.os.Handler;

import com.google.gson.Gson;


/**
 * Created by xiaoniao on 2016/3/16.
 *
 * 解析数据任务
 */
public class PaserDataTask<T> {

    Handler handler;
    String dataStr = "";
    T dataResult;
    Class<T> classOfT;
    int msgWhat = 0;

    public PaserDataTask(Handler handler, String dataStr,Class<T> calssOfT,int msgWhat) {
        this.handler = handler;
        this.dataStr = dataStr;
        this.classOfT = calssOfT;
        this.msgWhat = msgWhat;
    }


    /**
     *  执行
     */
    public void execute(){

        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    dataResult = new Gson().fromJson(dataStr,classOfT);
                    handler.obtainMessage(msgWhat,dataResult).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
