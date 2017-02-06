package com.xiangying.fighting.utils.gaodeLocation;

import android.content.Context;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.common.SystemState;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by xiaoniao on 2016/4/8.
 */
public class MyAMapLocationListener implements AMapLocationListener {

    public Context context;
    //更新天气handler
    Handler handler;

    public MyAMapLocationListener(Context context,Handler handler) {
        this.context = context;
        this.handler = handler;
    }


    public MyAMapLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                if(aMapLocation.getDistrict() != null && !"".equals(aMapLocation.getDistrict())){
                    SystemState.district = aMapLocation.getDistrict();
                }

                if (aMapLocation.getProvince() != null && !"".equals(aMapLocation.getProvince())){
                    SystemState.province = aMapLocation.getProvince();
                }

                if (aMapLocation.getCity() != null && !"".equals(aMapLocation.getCity())){
                    SystemState.city = aMapLocation.getCity();
                }

                if (aMapLocation.getStreet() != null && !"".equals(aMapLocation.getStreet())){
                    SystemState.street = aMapLocation.getStreet();
                }

                if(aMapLocation.getAddress() != null && !"".equals(aMapLocation.getStreet())){
                    SystemState.Address = aMapLocation.getAddress();
                }
//                searchWheather(SystemState.city);
//                handler.sendEmptyMessage(1);
                Logger.i("高德地图定位----》" + SystemState.province + SystemState.city + SystemState.district + SystemState.street + SystemState.Address);
                aMapLocation.getDistrict();//城区信息
//                aMapLocation.getStreet();//街道信息
//                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
//                aMapLocation.getRoad();
//                aMapLocation.getAOIName();//获取当前定位点的AOI信息
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Logger.e("AmapError"+"location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


}
