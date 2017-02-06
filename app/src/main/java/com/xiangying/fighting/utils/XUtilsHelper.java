package com.xiangying.fighting.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.MainActivity;
import com.xiangying.fighting.common.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by HJ on 2015/12/26.
 */
public class XUtilsHelper<T> {

    private Context context;
    private Handler handler;
    private String url;
    BaseApplication mApp;
    /**
     * 返回文本的编码， 默认编码UTF-8
     */
    private HttpUtils httpUtils;


    private ProgressDialog progressDialog;
    /**
     * 请求参数，默认编码UTF-8
     */
    private RequestParams requestParams;

    private String filename;
    String[][] param = new String[][]{};


    /**
     * 是否遇到error
     */
    boolean isError = false;


    int TAG_NOTHING = 0;
    public static final int TAG_FAILURE = -1;
    public static final int TAG_SUCCESS = 200;
    public static final int TAG_NET_ERROR = 0;

    String paramsStr = "参数列表:";

    //当前请求标记
    int SEND_POSTWITHKEY = 1;
    int SEND_POSTWITHKEYAUTO = 2;
    int tagNow;

    /**
     * 构造方法
     *
     * @param context 用于程序上下文，必须用当前Activity的this对象，否则报错
     * @param url     网络资源地址
     * @param handler 消息处理对象，用于请求完成后的怎么处理返回的结果数据
     */
    public XUtilsHelper(Context context, String url, Handler handler) {

        this.context = context;

        try {
            // 保存网络资源文件名，要在转码之前保存，否则是乱码
            filename = url.substring(url.lastIndexOf("/") + 1, url.length());
            // 解决中文乱码问题，地址中有中文字符造成乱码问题
            String old_url = URLEncoder.encode(url, "UTF-8");
            // 替换地址中的特殊字符
            String new_url = old_url.replace("%3A", ":").replace("%2F", "/")
                    .replace("%3F", "?").replace("%3D", "=")
                    .replace("%26", "&").replace("%2C", ",")
                    .replace("%20", " ").replace("+", "%20")
                    .replace("%2B", "+").replace("%23", "#")
                    .replace("#", "%23");
            this.url = new_url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.httpUtils = XutilsHttpClient.getInstence(context);
        httpUtils.configCookieStore(NetworkTools.cookieStore);
        this.handler = handler;

        this.progressDialog = new ProgressDialog(context);
        this.requestParams = new RequestParams(); // 编码与服务器端字符编码一致为utf-8
    }


    /**
     * get方法请求网络
     */
    public void sendGet() {
//        loadingDialog.show();
        httpUtils.configCurrentHttpCacheExpiry(20000);//设置请求缓存时间为5秒
        httpUtils.configCookieStore(NetworkTools.cookieStore);
        httpUtils.send(HttpRequest.HttpMethod.GET, url, requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Message msg = Message.obtain();
                        String resultStr = arg0.result;

                        if (arg0.statusCode == 200 || arg0.statusCode == 201) {
                            msg.what = TAG_SUCCESS;
                            msg.obj = resultStr;
                        } else {
                            msg.what = TAG_FAILURE;
                            msg.obj = resultStr;
                        }

                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        arg0.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = TAG_NET_ERROR;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                    }
                });

    }


    /**
     * 设置文件参数
     */
    public void setFileRequestParames(ArrayList<File> files) {

        for (int i = 0; i < files.size(); i++) {
            if (i == 0) {
                requestParams.addBodyParameter("imags", files.get(i));
            } else {
                requestParams.addBodyParameter("imags" + i, files.get(i));
            }
        }
    }

    public void setFileRequestParames2(ArrayList<String> files) {

        for (int i = 0; i < files.size(); i++) {

            if (TextUtils.isEmpty(files.get(i))) {
                continue;
            }

            if (i == 0) {
                requestParams.addBodyParameter("imags", new File(files.get(i)));
            } else {
                requestParams.addBodyParameter("imags" + i, new File(files.get(i)));
            }
        }
    }

    /**
     * 设置请求参数
     */
    public void setRequestParams(String[][] params) {
        param = params;

        String token = BaseApplication.instance.getUser().getToken();

        if (token != null && !"".equals(token)) {
            requestParams.addBodyParameter("token", token);
            paramsStr += "token" + "---->" + token+"\n";
        }
        for (int i = 0; i < param.length; i++) {
            requestParams.addBodyParameter(param[i][0], param[i][1]);
            paramsStr += param[i][0] + "---->" + param[i][1] + ";";
        }
    }


    public void showProgress(String content) {
        if (progressDialog != null) {
            progressDialog.setMessage(content);
            progressDialog.show();
        }
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    /**
     * POST方式请求服务器资源 不用传,不自动解析
     */
    public void sendPost() {
        tagNow = SEND_POSTWITHKEY;
        httpUtils.configCurrentHttpCacheExpiry(20000);//设置请求缓存时间为15秒
        httpUtils.configCookieStore(NetworkTools.cookieStore);

        Log.v("sendPost", url + paramsStr.toString());
        if (!isError) {
            httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams,
                    new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> arg0) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Message msg = Message.obtain();
                            Logger.i("网络请求:url--->" + url + "\nresultStr---->" + arg0.result + "\n请求参数--》" + paramsStr);
                            if (arg0.statusCode == 200 || arg0.statusCode == 201) {
                                try {
                                    JSONObject jsonObject = new JSONObject(arg0.result);
                                    int code = jsonObject.getInt("code");
                                    if (code == -96) {
                                        // TODO: 2016/11/16 重新登录
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String resultStr = arg0.result;
                                msg.obj = resultStr;
                                msg.what = TAG_SUCCESS;
                                handler.sendMessage(msg);

                            } else {
                                msg.what = TAG_FAILURE;
                                handler.sendMessage(msg);
                            }
                        }

                        @Override
                        public void onFailure(HttpException arg0, String arg1) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (arg0.getExceptionCode() == 904) {//key不正确
//                            getNewKey(null,null);
                            } else if (arg0.getExceptionCode() == 901 || arg0.getExceptionCode() == 903) {//需要重新登录
                                Utils.toast(context, "需要重新登录");
                                clearData();
//                            context.startActivity(new Intent(context, LoginActivity.class));
                            } else if (arg0.getExceptionCode() == 902) {
                                Utils.toast(context, "您没有访问权限");
                                context.startActivity(new Intent(context, MainActivity.class));
                                if (context instanceof Activity) {
                                    ((Activity) context).finish();
                                }
                            } else {
                                Utils.toast(context, "网络信号较差，请稍后再试");
                                arg0.printStackTrace();
                                Message msg = Message.obtain();
                                msg.what = TAG_FAILURE;
                                handler.sendMessage(msg);
                            }

                        }
                    });
        }
    }

    //清空SharedPrefrenced里面的数据
    public void clearData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserIF",
                Activity.MODE_PRIVATE);
        //清空
        sharedPreferences.edit().clear().commit();

    }


    /**
     * RealPOST方式请求服务器资源 不用传,自动解析
     */
    public void sendPostAuto(final Class<T> tClass) {
        tagNow = SEND_POSTWITHKEYAUTO;
        httpUtils.configCurrentHttpCacheExpiry(3 * 1000);//设置请求缓存时间为5秒
        httpUtils.configCookieStore(NetworkTools.cookieStore);

        if (!isError) {
            httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams,
                    new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> arg0) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Logger.i("网络请求:url--->" + url + "\nresultStr---->" + arg0.result + "\n" + paramsStr);

                            Message msg = Message.obtain();
                            if (arg0.statusCode == 200 || arg0.statusCode == 201) {
                                String resultStr = arg0.result;
                                msg.obj = resultStr;
                                msg.what = TAG_SUCCESS;
                                new PaserDataTask<>(handler, resultStr, tClass, TAG_SUCCESS).execute();
                            } else {
                                msg.what = TAG_FAILURE;
                                handler.sendMessage(msg);
                            }

                        }

                        @Override
                        public void onFailure(HttpException arg0, String arg1) {

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (arg0.getExceptionCode() == 904) {
//                            getNewKey(data, tClass);
                            } else if (arg0.getExceptionCode() == 901 || arg0.getExceptionCode() == 903) {//需要重新登录
                                Utils.toast(context, "需要重新登录");
                                clearData();
//                            context.startActivity(new Intent(context, LoginActivity.class));
                            } else if (arg0.getExceptionCode() == 902) {
                                Utils.toast(context, "您没有访问权限");
                                context.startActivity(new Intent(context, MainActivity.class));
                                if (context instanceof Activity) {
                                    ((Activity) context).finish();
                                }
                            } else {
                                Logger.i("网络信号较差，请稍后再试");
                                Utils.toast(context, "网络信号较差，请稍后再试");
                                arg0.printStackTrace();
                                Message msg = Message.obtain();
                                msg.what = TAG_FAILURE;
                                handler.sendMessage(msg);
                            }
                        }
                    });
        }
    }


    /**
     * 上传文件到服务器
     *
     * @param param 提交参数名称
     * @param file  要上传的文件对象
     */
    public void uploadFile(String param, File file) {
        progressDialog.setTitle("上传文件中，请稍等...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置进度条风格，风格为水平进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        requestParams.addBodyParameter(param, file);
        httpUtils.configCurrentHttpCacheExpiry(20000);//设置请求缓存时间为5秒

        httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
                        progressDialog.setIndeterminate(false);
                        progressDialog.setProgress((int) current);
                        progressDialog.setMax((int) total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println(arg0.statusCode);
                        System.out.println(arg0.result);
                        progressDialog.dismiss();
                        Message msg = Message.obtain();
                        msg.obj = arg0.result;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = TAG_NET_ERROR;
                        handler.sendMessage(msg);
                    }
                });
    }

    /**
     * 从服务器上下载文件保存到系统磁盘上
     *
     * @param saveLocation 下载的文件保存路径
     * @param downloadBtn  触发下载操作的控件按钮，用于设置下载进度情况
     */
    public void downloadFile(String saveLocation, final Button downloadBtn) {
        httpUtils.configCurrentHttpCacheExpiry(20000);//设置请求缓存时间为5秒

        httpUtils.download(url, saveLocation + filename,
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        downloadBtn.setText("连接服务器中...");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        DecimalFormat df = new DecimalFormat("#.##");
                        downloadBtn.setText("下载中... "
                                + df.format((double) current / 1024 / 1024)
                                + "MB/"
                                + df.format((double) total / 1024 / 1024)
                                + "MB");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        downloadBtn.setText("打开文件");
                        Toast.makeText(context, "下载成功！文件（"
                                + arg0.result.getAbsolutePath()
                                + "）保存在内部存储的Educ文件夹下。", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                        Toast.makeText(context, "下载失败，请重试！", Toast.LENGTH_SHORT).show();
                        downloadBtn.setText("下载附件");
                    }
                });
    }

    /**
     * 从服务器上下载文件保存到系统磁盘上，此方法会弹出进度对话框显示下载进度信息（
     * 有的需要知道文件是否下载完成，如果下载完成返回的是改文件在磁盘中的完整路径）
     *
     * @param saveLocation 下载的文件保存路径
     */
    public void downloadFile(String saveLocation) {
        progressDialog.setTitle("下载中，请稍等...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置进度条风格，风格为水平进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        progressDialog.setIndeterminate(false);
        httpUtils.configCurrentHttpCacheExpiry(20000);//设置请求缓存时间为5秒

        httpUtils.download(url, saveLocation + filename,
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        progressDialog.setProgress((int) current);
                        progressDialog.setMax((int) total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "下载成功！文件（"
                                + arg0.result.getAbsolutePath()
                                + "）保存在内部存储的Educ文件夹下。", Toast.LENGTH_LONG).show();
                        if (handler != null) {
                            Message msg = Message.obtain();
                            msg.obj = arg0.result.getAbsoluteFile();
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                        Toast.makeText(context, "下载失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class Configs {


    }
}

/**
 * 单例模式获取HttpUtils对象
 *
 * @author Shyky
 * @date 2014-11-19
 */
class XutilsHttpClient {

    private static HttpUtils client;

    /**
     * 单例模式获取实例对象
     *
     * @param context 应用程序上下文
     * @return HttpUtils对象实例
     */
    public synchronized static HttpUtils getInstence(Context context) {
        if (client == null) {
            // 设置请求超时时间为10秒
            client = new HttpUtils(1000 * 20);
            client.configSoTimeout(1000 * 20);
            client.configResponseTextCharset("UTF-8");
            // 保存服务器端(Session)的Cookie
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(
                    context);
            cookieStore.clear(); // 清除原来的cookie
            client.configCookieStore(cookieStore);
        }
        return client;
    }

}