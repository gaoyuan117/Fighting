package com.xiangying.fighting;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.common.SystemState;
import com.xiangying.fighting.ui.chat.chatutil.ChaiUtils;
import com.xiangying.fighting.ui.first.FirstFragment;
import com.xiangying.fighting.ui.login.LoginActivity;
import com.xiangying.fighting.ui.three.ThreeFragment;
import com.xiangying.fighting.ui.two.TwoFragment;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.gaodeLocation.LocationService;
import com.xiangying.fighting.utils.gaodeLocation.MyAMapLocationListener;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {
    // 用于在其他activity中finish掉它
    public static Activity main;
    @Bind(R.id.activity_main_iv_first)
    ImageView activityMainIvFirst;
    @Bind(R.id.activity_main_tv_first)
    FontTextView activityMainTvFirst;
    @Bind(R.id.activity_main_ll_first)
    LinearLayout activityMainLlFirst;
    @Bind(R.id.activity_main_iv_two)
    ImageView activityMainIvTwo;
    @Bind(R.id.activity_main_tv_two)
    FontTextView activityMainTvTwo;
    @Bind(R.id.activity_main_ll_two)
    RelativeLayout activityMainLlTwo;
    @Bind(R.id.activity_main_iv_three)
    ImageView activityMainIvThree;
    @Bind(R.id.activity_main_tv_three)
    FontTextView activityMainTvThree;
    @Bind(R.id.activity_main_ll_three)
    LinearLayout activityMainLlThree;
    @Bind(R.id.flag_ll)
    LinearLayout flagLl;
    @Bind(R.id.activity_main_ll_viewgroup)
    RelativeLayout activityMainLlViewgroup;

    private ViewGroup mcontentView;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 110) {
                int tag = (int) msg.obj;
                setFragmet(tag);
            }
            return true;
        }
    });
    private String nameStr;
    private String typeStr;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消网络连接的监听，网络监听
//        unregisterReceiver(mConnectivityReceiver);
    }

    /**
     * 三个fragment
     */
    FirstFragment firstFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    //fragment manager
    FragmentManager manager;
    private FragmentTransaction transaction;

    String fragmentTagFirst = "first";
    String fragmentTagTwo = "two";
    String fragmentTagThree = "three";

    LocationService locationService;

    @Override
    protected void process() {
        //判断是否需要更新
        // UpdateApp.checkVersion(this);
        locationService = new LocationService(this, true, new MyAMapLocationListener(this));
        ChaiUtils.login();
    }

    @Override
    protected void setAllClick() {
        activityMainLlFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmet(1);
            }
        });

        activityMainLlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmet(2);
            }
        });

        activityMainLlThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmet(3);
            }
        });
    }

    @Override
    protected void initViews() {
        //初始化fragment管理器
        manager = getSupportFragmentManager();
        setFragmet(1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void loadLayout() {
        mcontentView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(mcontentView);
        ButterKnife.bind(this);
        main = this; // 初始化这个全局变量
        if (!BaseApplication.getInstance().isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void loadNetData() {
    }

    /**
     * 按照选择choice设置Fragmen的跳转
     */
    public void setFragmet(int choice) {
        transaction = manager.beginTransaction();
        clearAllState();
        hideAllFragment(transaction);
        switch (choice) {
            case 1:
                activityMainIvFirst.setImageResource(R.drawable.foot_zdb_on);
                activityMainTvFirst.setTextColor(Color.parseColor("#c40b2b"));

                if (firstFragment == null) {
                    firstFragment = (FirstFragment) FirstFragment.newInstance();
                    transaction.add(R.id.activity_main_fl_content, firstFragment, fragmentTagFirst);
                } else
                    transaction.show(firstFragment);
                break;
            case 2:
                activityMainIvTwo.setImageResource(R.drawable.foot_ok_on);
                activityMainTvTwo.setTextColor(Color.parseColor("#c40b2b"));

                if (twoFragment == null) {
                    twoFragment = (TwoFragment) TwoFragment.newInstance();
                    transaction.add(R.id.activity_main_fl_content, twoFragment, fragmentTagTwo);
                } else {
                    transaction.show(twoFragment);
//                    twoFragment.ReLoadMessage(); // 重新请求一次消息
                }

                break;
            case 3:
                activityMainIvThree.setImageResource(R.drawable.foot_user_on);
                activityMainTvThree.setTextColor(Color.parseColor("#c40b2b"));

                if (threeFragment == null) {
                    threeFragment = (ThreeFragment) ThreeFragment.newInstance();
                    transaction.add(R.id.activity_main_fl_content, threeFragment, fragmentTagThree);
                } else
                    transaction.show(threeFragment);
                break;
        }
        transaction.commit();
    }


    /**
     * 影藏所有Fragment，避免混淆
     */
    public void hideAllFragment(FragmentTransaction transaction) {

        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }

        if (twoFragment != null) {
            transaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
        }


    }

/**
 * 用来实时监听手机网络变化并给出提示
 */
   /* private BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {

                //判断网络情况
                if (MobileNetWorkUtils.GetNetworkType(MainActivity.this)) {//如果判断网络为2G 及 以下 或者断网 就要显示网络提示

                    networkNotice.setVisibility(View.VISIBLE);
                    networkNoticeClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            networkNotice.setVisibility(View.GONE);
                            networkNotice.setAnimation(AnimationUtils.makeOutAnimation(MainActivity.this, true));
                        }
                    });
                } else {

                    networkNotice.setVisibility(View.GONE);

                }
            }


        }


    };*/

    /**
     * 清除按钮状态
     */
    public void clearAllState() {
        activityMainIvFirst.setImageResource(R.drawable.foot_zdb);
        activityMainTvFirst.setTextColor(Color.parseColor("#999999"));

        activityMainIvTwo.setImageResource(R.drawable.foot_ok);
        activityMainTvTwo.setTextColor(Color.parseColor("#999999"));

        activityMainIvThree.setImageResource(R.drawable.foot_user);
        activityMainTvThree.setTextColor(Color.parseColor("#999999"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Fragment fragment = manager.findFragmentByTag(fragmentTagFirst);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Utils.toast(this, "再按一次退出程序");
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
//          System.exit(0);
                    finish();
                    appContext.getUser().setToken("");
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        //注册网络变化的brodcast
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册网络监听的receiver
//        registerReceiver(mConnectivityReceiver, filter);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SystemState.isHasPermission) {
            locationService.startLocationService();
        }
    }

}
