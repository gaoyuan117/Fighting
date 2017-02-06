package com.xiangying.fighting.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.xiangying.fighting.R;
import com.xiangying.fighting.utils.ActivityManager;


/**
 * Created by HJ on 2015/12/1.
 */
public abstract class BaseActivity2 extends Activity {

    private BaseApplication mApp;
    public static BaseApplication appContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);//添加到管理器
        mApp = (BaseApplication) getApplication();
        appContext = (BaseApplication) getApplication();
        loadLayout();
        initViews();
        setAllClick();
        process();
        loadNetData();
    }


    /**
     * 在activity销毁的时候调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.gc); //通过这样的方法，改变父类Activity的contentview对象的引用，让oncreate的setContentview创建的 那个view对象失去引用，被GC掉
        ActivityManager.getAppManager().finishActivity(this);
    }

    public void setMHandler (Handler handler){
        mApp.setmHandler(handler);
    }

    @Override
    public Context getApplicationContext() {
        return mApp;
    }

    /**
     * 业务逻辑过程
     */
    protected abstract void process();

    /**
     * 设置所有监听
     */
    protected abstract void setAllClick();

    /**
     * 获取所有的主件
     */
    protected abstract void initViews();

    /**
     * 加载布局
     */
    protected abstract void loadLayout();

    /**
     * 加载网络数据
     */
    protected abstract void loadNetData();

}