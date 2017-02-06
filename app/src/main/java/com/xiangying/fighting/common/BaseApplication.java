package com.xiangying.fighting.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.android.zcomponent.common.uiframe.ConfigMgr;
import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.communication.http.Communicator;
import com.android.zcomponent.constant.ComponentR;
import com.android.zcomponent.delegate.Event;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.LogEx;
import com.easemob.redpacketsdk.RedPacket;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.hyphenate.easeui.domain.EaseUser;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.dao.DBManager;
import com.xiangying.fighting.ui.chat.help.DemoHelper;
import com.xiangying.fighting.ui.login.LoginActivity;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;

import java.util.Iterator;
import java.util.List;

import service.api.BaseEntity;


/**
 * Created by HJ on 2015/12/1.
 */
public class BaseApplication extends FramewrokApplication {
  private DBManager dbHelper;
  public static int version_code = 1;
  public boolean loadImage = true;
  public static Context AppContext;
  public static BaseApplication instance;
  public static String uid = "";

  Handler mHandler = null;

  private LoginUser user;

  public LoginUser getUser() {
    if (user == null) {
      user = GetLocalKey.getUser();
    }
    return user;
  }

  public void setUser(LoginUser user) {
    this.user = user;
    Endpoint.Token = user.token;
  }

  public Handler getmHandler() {
    return mHandler;
  }

  public void setmHandler(Handler mHandler) {
    this.mHandler = mHandler;
  }

  @Override
  public void onCreate() {
    new ComponentR(this);
    super.onCreate();
    init();
    initFresco();
    Logger.init("gy").setMethodCount(1)
            .hideThreadInfo();
    initImageLoader(getApplicationContext());
    initEM();
    initMall();
  }

  private void initMall() {
    setTitleBarMoreShow(true);
    Endpoint.setEncrypt(false);
    Endpoint.HOST = NetworkTools.BASE_URL.replace("/Api/", "");
    EaseUser.HOST = Endpoint.HOST;
    Endpoint.Token = GetLocalKey.getToken();
    ConfigMgr.setLogLevel(LogEx.LogLevelType.TYPE_LOG_LEVEL_DEBUG);
    addRequestEvent();
    addResponseEvent();
  }

  private void initEM() {
    DemoHelper.getInstance().init(AppContext);
    RedPacket.getInstance().initContext(AppContext);    //red packet code : 初始化红包上下文，开启日志输出开关
    RedPacket.getInstance().setDebugMode(true);
  }

  private void init() {
    AppContext = this;
    instance = this;
    dbHelper = new DBManager(this);
    dbHelper.openDatabase();

    try {
      version_code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void initFresco() {
    ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
      @Override public int getNextScanNumberToDecode(int scanNumber) {
        return scanNumber + 2;
      }

      public QualityInfo getQualityInfo(int scanNumber) {
        boolean isGoodEnough = (scanNumber >= 5);
        return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
      }
    };

    Fresco.initialize(this, ImagePipelineConfig
        .newBuilder(this)
        .setProgressiveJpegConfig(pjpegConfig)
        .build());
  }

  public static String getAppName(int pID) {
    String processName = null;
    ActivityManager am = (ActivityManager) AppContext.getSystemService(Context.ACTIVITY_SERVICE);
    List l = am.getRunningAppProcesses();
    Iterator i = l.iterator();
    PackageManager pm = AppContext.getPackageManager();
    while (i.hasNext()) {
      ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
          .next());
      try {
        if (info.pid == pID) {
          CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
          processName = info.processName;
          return processName;
        }
      } catch (Exception e) {
      }
    }
    return processName;
  }

  public String getUid() {
    if (user == null) {
      return "";
    } else
      return user.getInfo().getUid();
  }

  public void addRequestEvent() {
    Endpoint
        .communicator()
        .getRequestEvent()
        .addEventHandler(new Event.EventHandler<Communicator.RequestEventArgs>() {
          @Override public void handle(Object sender, Communicator.RequestEventArgs args) {
            String uri = args.context().uri().replace("jzb.jxlnxx.com", Endpoint.HOST.replace("http://",""));
            args.context().set(com.android.zcomponent.communication.http.Context.Attribute.Uri,
                uri);
          }
        });
  }

  public void addResponseEvent() {
    Endpoint
        .communicator()
        .getResponseEvent()
        .addEventHandler(new Event.EventHandler<Communicator.ResponseEventArgs>() {
          @Override
          public void handle(Object sender, final Communicator.ResponseEventArgs args) {
            getCurActivity().runOnUiThread(new Runnable() {
              @Override public void run() {
                if (null != args.context().data()) {
                  String data = new String(args.context().data());

                  BaseEntity baseEntity = JsonSerializerFactory.Create().decode(data, BaseEntity
                      .class);
                  if (null != baseEntity && baseEntity.code == -94) {
                    onUnauthorized();
                  }
                  if (null != baseEntity && baseEntity.code == -96) {
                    onUnauthorized();
                  }
                  if (null != baseEntity && baseEntity.code == -95) {
                    onUnauthorized();
                  }
                }
              }
            });
          }
        });
  }

  public static void intentToLoginActivity(Activity activity) {
    Intent intent = new Intent(activity, LoginActivity.class);
    activity.startActivity(intent);
  }

  public boolean isLoginAcitityActive() {
    for (int i = 0; i < getActivitys().size(); i++) {
      if (getActivitys().get(i) instanceof LoginActivity) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onTitleBarCreate(View view, View morePop, boolean isShowMore) {
    super.onTitleBarCreate(view, morePop, isShowMore);

    if (null != view) {
      view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
      TextView titleView = (TextView) view.findViewById(R.id.common_title_tvew_txt);
      titleView.setTextColor(getResources().getColor(R.color.white));
//      View tittleDot = view.findViewById(R.id.common_title_more_dot);
//      EaseMessageNotify.getInstance().addView(tittleDot);
    }

    if (null != morePop) {
      View messageDot = morePop.findViewById(R.id.message_more_dot);
//      EaseMessageNotify.getInstance().addView(messageDot);
    }
  }

  @Override
  public void onTitleBarResume() {
    super.onTitleBarResume();
//    EaseMessageNotify.getInstance().onResume();
  }

  @Override
  public void onTitleBarDestory(View view, View morePop, boolean isShowMore) {
    super.onTitleBarDestory(view, morePop, isShowMore);

    if (isShowMore) {
      if (null != view) {
        View tittleDot = view.findViewById(R.id.common_title_more_dot);
//        EaseMessageNotify.getInstance().removeView(tittleDot);
      }

      if (null != morePop) {
        View messageDot = morePop.findViewById(R.id.message_more_dot);
//        EaseMessageNotify.getInstance().removeView(messageDot);
      }
    }
  }

    /*public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of
        them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);

//        *
//         * 图片缓存
//
        config.memoryCache(new WeakMemoryCache());
//        *
//         * 线程池加载数量  一般使用默认的
//
        config.threadPoolSize(4);
//        *
//         * 线程优先级
//
        config.threadPriority(Thread.NORM_PRIORITY);
//        *
//         * 在内存中存储多图片多尺寸  不设置
//
        config.denyCacheImageMultipleSizesInMemory();
//
//        *
//         * 内存缓存位置，可自己定义内存缓存 多样式  默认的
//
//        config.memoryCache(new LruMemoryCache(1024 * 1024 * 20));
//        *
//         * 使用默认内存缓存时 设置内存缓存大小
//
        config.memoryCacheSize(1024 * 1024 * 50);
//        *
//         * 硬盘存储名称规则
//
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        *
//         * 缓存图片的名称编辑
//
//        config.diskCacheFileNameGenerator(new FileNameGenerator() {
//            @Override
//            public String generate(String imageUri) {
//                return null;
//            }
//        });
//        *
//         * 硬盘存储大小
//
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB

//        *
//         *   先进先出FIFO 后进先出LIFO
//

        config.memoryCacheExtraOptions(300, 300);
        config.tasksProcessingOrder(QueueProcessingType.FIFO);
//        *
//         * 是否打印BUG
//
        config.writeDebugLogs(); // Remove for release app

//        *
//         * 设置默认显示的配置
//
//        config.defaultDisplayImageOptions();

//        *
//         * 创建日期文件夹
//
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH");
        String format = sdf.format(new Date(l));
//        *
//         * 自定义图片存储路径
//
        String file = Environment.getExternalStorageDirectory().getAbsolutePath() +
        "/DCIM/MoCha/" + format;
        File fileDir = new File(file);
        if (fileDir == null || !fileDir.isDirectory()) {
            fileDir.mkdir();
        }
        config.diskCache(new UnlimitedDiskCache(fileDir));

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.image_failure)
                .showImageOnLoading(R.drawable.image_failure)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        config.defaultDisplayImageOptions(displayImageOptions);

//        *
//         * 初始化ImageLoader
//
        ImageLoader.getInstance().init(config.build());
    }*/
}