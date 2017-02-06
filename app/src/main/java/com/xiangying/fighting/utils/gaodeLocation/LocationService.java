package com.xiangying.fighting.utils.gaodeLocation;

import android.content.Context;
import android.os.Handler;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;



/**
 * Created by hujian on 2016/4/8.
 */
public class LocationService {


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明自定义定位回调监听器

    MyAMapLocationListener mapLocationListener;


    AMapLocationListener aMapLocationListener;

    Context context;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    //更新天气handler
    Handler handler;

    public LocationService(Context context,boolean isSingleTime,Handler handler) {
        this.context = context;
        this.handler = handler;
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //初始化定位坚挺
        mapLocationListener = new MyAMapLocationListener(context,handler);
        //设置定位回调监听
        mLocationClient.setLocationListener(mapLocationListener);
        //初始化定位参数
        initLocationOptions(isSingleTime);
    }






    public LocationService(Context context,boolean isSingleTime,AMapLocationListener myAMapLocationListener) {
        this.aMapLocationListener = myAMapLocationListener;
        this.context = context;
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //初始化定位坚挺

        //设置定位回调监听
        mLocationClient.setLocationListener(aMapLocationListener);
        //初始化定位参数
        initLocationOptions(isSingleTime);
    }

    //初始化定位参数
    private void initLocationOptions(boolean isSingleTime) {

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(isSingleTime);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }


    /**
     * 启动定位
     */
    public void startLocationService(){
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocationService(){
        mLocationClient.stopLocation();
    }

    /**
     *  onDestroy
     */
    public void onDestroy(){
        mLocationClient.onDestroy();
    }
}