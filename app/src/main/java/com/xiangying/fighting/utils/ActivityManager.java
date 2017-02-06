package com.xiangying.fighting.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * @ClassName: ActivityManager
 * @Description: Activity管理类：用于管理Activity和退出程序
 * @author ray
 * @date 2016 5 9
 *
 */
public class ActivityManager {
  
    private static ArrayList<Activity> activityStack;
    private static ActivityManager instance;
  
    private ActivityManager() {
    }  
  
    /** 
     * 单一实例 
     */  
    public static ActivityManager getAppManager() {
        if (instance == null) {  
            instance = new ActivityManager();
        }  
        return instance;  
    }  
  
    /** 
     * 添加Activity到堆栈 
     */  
    public void addActivity(Activity activity) {  
        if (activityStack == null) {  
            activityStack = new ArrayList<>();
        }  
        activityStack.add(activity);
        Log.v("addActivity",activityStack.size()+"");
    }
  
    /** 
     * 获取当前Activity（堆栈中最后一个压入的） 
     */  
    public Activity currentActivity() {  
        Activity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }
    /**
     * 获取当前Activity的上一个（堆栈中倒数第二个压入的）
     */
    public Activity currentLastActivity() {
        Activity activity = activityStack.get(activityStack.size() - 2);
        return activity;
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.get(activityStack.size()-1);
        finishActivity(activity);
    }
  
    /** 
     * 结束指定的Activity 
     */  
    public void finishActivity(Activity activity) {  
        if (activity != null) {  
            activityStack.remove(activity);  
            activity.finish();  
            activity = null;  
        }  
    }  
  
    /** 
     * 结束指定类名的Activity 
     */  
    public void finishActivity(Class<?> cls) {  
        for (Activity activity : activityStack) {  
            if (activity.getClass().equals(cls)) {  
                finishActivity(activity);  
            }  
        }  
    }  
  
    /** 
     * 结束所有Activity 
     */  
    public void finishAllActivity() {  
        for (int i = 0, size = activityStack.size(); i < size; i++) {  
            if (null != activityStack.get(i)) {  
                activityStack.get(i).finish();  
            }  
        }  
        activityStack.clear();  
    }  
  
    /** 
     * 退出应用程序 
     */  
    public void exitApp(Context context) {
        try {
            finishAllActivity();  
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);  
            activityMgr.killBackgroundProcesses(context.getPackageName());  
            System.exit(0);  
        } catch (Exception e) {  
        }  
    }  
}  