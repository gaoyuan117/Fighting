package com.xiangying.fighting.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.xiangying.fighting.R;


public class SplashActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash);

    Intent intent = new Intent(SplashActivity.this, FlashActivity.class);
    SplashActivity.this.startActivity(intent);
    SplashActivity.this.finish();

//    new Handler().postDelayed(new Runnable() {
//      @Override public void run() {
//
//              /*      if (!GetLocalKey.getIsLaunched()) {
//                         intent = new Intent(SplashActivity.this, FlashActivity.class);
//                          GetLocalKey.setIsLaunched(true);
//                    }else {
//                     intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    }*/
//        intent = new Intent(SplashActivity.this, FlashActivity.class);
//        SplashActivity.this.startActivity(intent);
//        SplashActivity.this.finish();
//      }
//    }, 1000);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    Intent intent;
//              /*      if (!GetLocalKey.getIsLaunched()) {
//                         intent = new Intent(SplashActivity.this, FlashActivity.class);
//                          GetLocalKey.setIsLaunched(true);
//                    }else {
//                     intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    }*/
//                    intent = new Intent(SplashActivity.this, FlashActivity.class);
//                    SplashActivity.this.startActivity(intent);
//                    SplashActivity.this.finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
  }
}
