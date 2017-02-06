package com.xiangying.fighting.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.widget.FontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.api.CommonReturn;


/**
 * Created by Administrator on 2016/2/2 0002.
 */
public class Utils {
    //计算listview的长度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount（）返回数据项的数量
            View listItem = listAdapter.getView(i, null, listView);
            // 策画子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight（）获取子项间分隔符占用的高度
        // params.height最后获得全部ListView完全显示须要的高度
        listView.setLayoutParams(params);
    }




    //计算gridView的长度
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setGridViewHeightBasedOnChildren(GridView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int columNum = 3;
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i += columNum){
            // listAdapter.getCount（）返回数据项的数量
            View listItem = listAdapter.getView(i, null, listView);
            // 策画子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }



        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        params.height = totalHeight + (listView.getVerticalSpacing() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        Logger.v("GridView计算高度为--->" + totalHeight + "所有");
    }




    /**
     * 获取当前时间
     * @return
     */
    public static String getTodayStr(){
        long todayMill = System.currentTimeMillis();

        Date date = new Date(todayMill);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        String dataStr = sf.format(date);

        return dataStr;
    }


    /**
     * String 转换成long
     * @return
     */
    public static long paserDataString(String dataStr){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sf.parse(dataStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }


    /**
     * 保留两位小数
     */
    public static double baoliuliangweixiaoshu(double result_value){
        DecimalFormat df = new DecimalFormat("#.##");
        double get_double = 0.0;
        get_double = Double.parseDouble(df.format(result_value));
        return  get_double;
    }



    /**
     * Md5加密
     * @param val
     * @return
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return bytesToHexString(m).toLowerCase();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 使用saveUserInfo方法保存用户名和密码，sharepreferences
     * @param context
     * @param userName
     * @param password
     */
    public static void saveUserInfo(Context context, String userName , String password){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        //获取Editer对象
        SharedPreferences.Editor editor = preferences.edit();
        //设置参数
        editor.putString("userName",userName);
        editor.putString("password", password);
        //提交
        editor.commit();
    }

    /**
     * 使用SharePreferences来保存是否记住密码的状态
     * @param context
     * @param isSaveUser
     */
    public static void saveUserInfo(Context context,boolean isSaveUser){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        //获取Editer对象
        SharedPreferences.Editor editor = preferences.edit();
        //设置参数
        editor.putBoolean("isSaveUser", isSaveUser);
        //提交
        editor.commit();
    }

    /**
     * 使用SharePreferences来保存是否记住密码的状态
     * @param context
     * @param isSaveUserHead
     */
    public static void saveImageHead(Context context,boolean isSaveUserHead,String uri){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        //获取Editer对象
        SharedPreferences.Editor editor = preferences.edit();
        //设置参数
        editor.putString("headUri",uri);
        editor.putBoolean("isSaveUserHead", isSaveUserHead);
        //提交
        editor.commit();
    }


    //二次采样
    public static Bitmap compressImageFromFile(String srcPath){
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww){
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh){
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 监测是否已经保存头像
     * @param context
     * @return
     */
    public static boolean isSaveUserHead(Context context){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);

        boolean isSaved = preferences.getBoolean("isSaveUserHead",false);
        return isSaved;
    }
    /**
     * 获得已经保存头像
     * @param context
     * @return
     */
    public static String getSavedUserHead(Context context){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String uri = preferences.getString("headUri","");
        return uri;
    }


    /**
     * 获得已经保存的用户名和密码
     * @param context
     * @return
     */
    public static String getSavedUserName(Context context){
        //获取SharePerferences对象
        SharedPreferences preferences = context.getSharedPreferences("BHBUserIF",Context.MODE_PRIVATE);
        String uri = preferences.getString("userName","");
        return uri;
    }


    /**
     * 获取设备IMEI码
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        String mImei = "NULL";
        try {
            mImei = ((TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//            SystemState.Iml = mImei;
            Logger.i("我的IMEI" + mImei);
        } catch (Exception e) {
            System.out.println("获取IMEI码失败");
            mImei = "NULL";
        }
        return mImei;
    }

    public static void toast(Context context,String content)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_item, null);      //加载布局文件
       FontTextView textView=(FontTextView) view.findViewById(R.id.toast_text);    // 得到textview
        textView.setText(content);     //设置文本类荣，就是你想给用户看的提示数据
        Toast toast=new Toast(context);     //创建一个toast
        toast.setDuration(Toast.LENGTH_SHORT);          //设置toast显示时间，整数值
        toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);    //toast的显示位置，这里居中显示
        toast.setView(view);     //設置其显示的view,
        toast.show();             //显示toast
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }


    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

        /**
         * 保存文件
         * @param bm
         * @param fileName
         * @throws IOException
         */
    public static String saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile.getAbsolutePath();
    }

    public static String getSDPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }



public static void  showHintByMsg(Context context,String json){
    if(TextUtils.isEmpty(json)){
        return;
    }
    String message="";JSONObject object=null;
    try {
        object=new JSONObject(json);
        message=object.optString("message");
    } catch (JSONException e) {
        e.printStackTrace();
        message=object.optString("info");
    }
    Utils.toast(context,message);
}

    /***
     * 得到接口返回码
     * */
public static int getResultCode(Context context,String json){
    int message=-1;JSONObject object=null;
    if(TextUtils.isEmpty(json)){
        return message;
    }
    try {
        object=new JSONObject(json);
        message=object.optInt("code");
    } catch (JSONException e) {
        e.printStackTrace();
    }
    if(message!=1){
//        showHintByMsg(context,json);
    }
    return message;
}

    public static boolean valiueCommonReture(CommonReturn obj) {
        try {
            if (obj == null) {
                Utils.toast(BaseApplication.getInstance(), "未知错误");
                return false;
            }

            if (obj.code == 1) {
                Utils.toast(BaseApplication.getInstance(), obj.message);
                return true;
            } else {
                Utils.toast(BaseApplication.getInstance(), obj.message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
