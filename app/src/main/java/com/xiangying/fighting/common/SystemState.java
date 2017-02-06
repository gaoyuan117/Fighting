package com.xiangying.fighting.common;

/**
 * Created by Administrator on 2016/2/2 0002.
 */
public class SystemState {

  /**
   * 定位地点
   */
  public static String province = "";
  public static String city = "";
  public static String street = "";
  public static String Address = "";
  public static String district = "";
  public static String userName = "";
  public static String cityCode = "";


  /**
   * 默认头像地址
   */
  public static String default_head_pic = "";

  /**
   * 分页15
   */
  public static String pageSize = "15";


  public static String timeTemp = "2016-3-29";


  public static String Root_Url = "http://192.168.1.51:8080/yuanben/";


  /**
   * 原框架所用地址
   */
  //天气查询地址
  public static String weather = "http://apis.baidu.com/heweather/weather/free";
  //天气查询次数
  public static int weatherLoadNum = 0;
  //天气查询apikey
  public static String weatherApiKey = "668074468bdd102f9aebe55892d672f4";
  //当前天气
  public static String nowWeather = "";
  //当前温度
  public static String nowTmp = "";
  //天气图标resid
  public static int weatherIconRes = 0;


  /**
   * 用户模块
   */
  public static String getAddress = "";

  /**
   * 首页
   */

  /**
   * 当前设备标识
   */
  public static String Iml = "";

  public static boolean isHasPermission = true;
}