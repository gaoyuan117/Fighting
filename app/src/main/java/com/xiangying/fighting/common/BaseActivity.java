package com.xiangying.fighting.common;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.utils.ActivityManager;


/**
 * Created by HJ on 2015/12/1.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

    private BaseApplication mApp;
    public static BaseApplication appContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);//添加到管理器
        Logger.e("current stack top mActivity is  ---> " + TAG);
        mApp = (BaseApplication) getApplication();
        appContext = (BaseApplication) getApplication();
//        initState();
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

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}