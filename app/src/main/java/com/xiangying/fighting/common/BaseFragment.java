package com.xiangying.fighting.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by keven on 15/11/5.
 */
public abstract class BaseFragment extends Fragment {

    public BaseApplication mApp;
    public Context context;
    public Activity mActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mApp = (BaseApplication) getActivity().getApplication();
//        mApp.addActivity(getActivity());

        mActivity = getActivity();
        /**
         * 加载布局
         */
        View view = loadLayout(inflater);
        /**
         * 获取所有的主件
         */
        initViews(view);
        /**
         * 设置所有监听
         */
        setAllClick();
        /**
         * 业务逻辑过程
         */
        process();
        loadNetData();
        return view;
    }



    public BaseApplication getApplication() {
        return mApp;
    }

    protected abstract void process();

    protected abstract void setAllClick();

    protected abstract void initViews(View view);

    protected abstract View loadLayout(LayoutInflater inflater);
    /**
     * 加载网络数据
     */
    protected abstract void loadNetData();


    @Override
    public void onAttach(Activity activity) {
        this.context = activity;
        super.onAttach(activity);
    }


    public Context getContext() {
        return context;
    }
}
