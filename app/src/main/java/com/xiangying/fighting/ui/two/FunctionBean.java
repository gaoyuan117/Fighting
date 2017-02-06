package com.xiangying.fighting.ui.two;

import com.xiangying.fighting.R;

import java.util.ArrayList;

/**
 * Created by xiaoniao on 2016/3/24.
 */
public class FunctionBean {


    String functionName = "";
    int functionRes ;

    public FunctionBean(String auth, int chuangdian) {
        this.functionName = auth;
        this.functionRes = chuangdian;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getFunctionRes() {
        return functionRes;
    }

    public void setFunctionRes(int functionRes) {
        this.functionRes = functionRes;
    }

    /**
     * 获得数据
     * @return
     */
    public static ArrayList<FunctionBean> getDatas(){
        ArrayList<FunctionBean> functionBeans = new ArrayList<>();

        FunctionBean functionBean = new FunctionBean("团店", R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("好工作",R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("好租房",R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("结婚吧",R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("公益",R.drawable.gongyi);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("红包",R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("建议和投诉",R.drawable.chuangdian);
        functionBeans.add(functionBean);

        functionBean = new FunctionBean("系统通知",R.drawable.chuangdian);
        functionBeans.add(functionBean);
        return functionBeans;
    }
}
