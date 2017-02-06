package com.xiangying.fighting.ui.first.vedio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.zcomponent.activity.RecordActivity;
import com.android.zcomponent.util.BitmapUtil;
import com.google.gson.Gson;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.UploadVedioBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.CommonUtil;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class UploadVideoActivity extends BaseActivity {
  @Bind(R.id.mnickname_save) FontTextView mMnicknameSave;
  @Bind(R.id.title_bar_common_tv_title) FontTextView mTitleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1) ImageView mTitleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_rv_viewGroup) RelativeLayout mTitleBarCommonRvViewGroup;
  @Bind(R.id.activity_PNA_et_title) EditText mActivityPNAEtTitle;
  @Bind(R.id.activity_PNA_et_content) EditText mActivityPNAEtContent;
  @Bind(R.id.iv_new_vedio) ImageView mImageView;

  private static final int REQ_CODE_RECORD = 100;
  private String mFilePath = "";

  //发布的类型，0正能量 1分享屋。
  private String type = "0";

  public static void start(Context context, String type) {
    Intent starter = new Intent(context, UploadVideoActivity.class);
    starter.putExtra("type", type);
    context.startActivity(starter);
  }

  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {
    mMnicknameSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addVideo();
      }
    });

    mImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(UploadVideoActivity.this, RecordActivity.class);
        intent.putExtra("durtion_limit", 10);
        startActivityForResult(intent, REQ_CODE_RECORD);
      }
    });
  }

  private void addVideo() {
    uploadVideo(mFilePath);
  }

  private void addNewVideo(UploadVedioBean.Data data) {
    if (!CommonUtil.checkEt(this, mActivityPNAEtContent, "请输入内容")) {
      return;
    }

    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.SHARE_ADD, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          CommonReturn commonReturn = (CommonReturn) msg.obj;
          if (commonReturn.code == 1) {
            Toast.makeText(UploadVideoActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
          }
        } catch (Exception e) {
          Toast.makeText(UploadVideoActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    }));

    xUtilsHelper.setRequestParams(new String[][]{
        {"content", mActivityPNAEtContent.getText().toString().trim()},
        {"movie_id", data.getVideo().getId() + ""},
        {"is_energy",type}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void initViews() {
    type = getIntent().getStringExtra("type");

    if ("1".equals(type)) {
      mTitleBarCommonTvTitle.setText("发布分享屋");
    }else {
      mTitleBarCommonTvTitle.setText("发布正能量");
    }

    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mMnicknameSave.setText("发布");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_uoliad_vedio);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == REQ_CODE_RECORD) {
        String filePath = data.getStringExtra("video_path");
        if (!TextUtils.isEmpty(filePath)) {
          mFilePath = filePath;
          Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
          bitmap = BitmapUtil.rotateImage(bitmap, 90);
          mImageView.setImageBitmap(bitmap);
        }
      }
    }
  }

  private void uploadVideo(String filePath) {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Movie, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {

        try {
          String result = (String) msg.obj;
          if (result.startsWith("<!DOCTYPE html>")) {
            int i = result.lastIndexOf(">");
            result = result.substring(i + 1);
          }
          UploadVedioBean bean = new Gson().fromJson(result, UploadVedioBean.class);
          if (bean.getCode() == 1) {
            addNewVideo(bean.getData());
          }
        } catch (Exception e) {

        }

        return false;
      }
    }));

    xUtilsHelper.uploadFile("video", new File(filePath));
  }
}
