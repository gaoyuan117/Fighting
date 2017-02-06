package com.xiangying.fighting.ui.three.selfinfo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.two.jiehun.ImageGridAdapter;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import service.api.CommonReturn;

@RuntimePermissions
public class PublishNewAlbumActivity extends BaseActivity {
  @Bind(R.id.activity_PNA_et_content)
  EditText activityPNAEtContent;
  @Bind(R.id.activity_PNA_gv_images)
  MyGridView activityPNAGvImages;
  @Bind(R.id.mnickname_save)
  FontTextView mnicknameSave;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;


  ImageGridAdapter imageGridAdapter;
  ArrayList<String> selectImagePath = new ArrayList<>();
  public int REQUEST_IMAGE = 10001;


  @Override
  protected void process() {

    /**
     * 选照片
     */
    selectImagePath.add("");

    imageGridAdapter = new ImageGridAdapter(selectImagePath, this);

    activityPNAGvImages.setAdapter(imageGridAdapter);

    activityPNAGvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == (selectImagePath.size() - 1)) {
          PublishNewAlbumActivityPermissionsDispatcher.chooseImagesWithCheck(PublishNewAlbumActivity.this);
        }
      }
    });

  }

  @Override
  protected void setAllClick() {
    mnicknameSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        uploadImage();
      }
    });
  }

  private void addNewAlbum(String data) {
    String content = activityPNAEtContent.getText().toString();
    if (TextUtils.isEmpty(content)) {
      Utils.toast(PublishNewAlbumActivity.this, "说点什么吧");
      return;
    }
    XUtilsHelper xUtilsHelper = new XUtilsHelper(PublishNewAlbumActivity.this, NetworkTools.ME_ALL_NOTE_ADD, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        try {
          CommonReturn result = (CommonReturn) msg.obj;
          if (result.code == 1) {
            Toast.makeText(PublishNewAlbumActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
          } else {
            Toast.makeText(PublishNewAlbumActivity.this, result.message, Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {
          Toast.makeText(PublishNewAlbumActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    }));

    xUtilsHelper.setRequestParams(new String[][]{{"type","1"},{"content", content}, {"cover_ids", data}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  private void uploadImage() {
    if (selectImagePath.size() == 1) {
      Toast.makeText(this, "请选择要上传的图片", Toast.LENGTH_SHORT).show();
      return;
    }

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("正在上传...");
    progressDialog.show();

    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        progressDialog.dismiss();
        try {
          ImageFileBean reslut = (ImageFileBean) msg.obj;
          if (reslut.getCode() == 1) {
            addNewAlbum(reslut.getData());
          }
        } catch (Exception e) {

        }
        return false;
      }
    }));
    xUtilsHelper.setFileRequestParames2(selectImagePath);
    xUtilsHelper.sendPostAuto(ImageFileBean.class);
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("照片");
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mnicknameSave.setText("发布");
    mnicknameSave.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        uploadImage();
      }
    });
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_publish_new_album);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {

  }

  /**
   * 查看图库文件
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void chooseImages() {
    boolean showCamera = true;
    int maxNum = 9;

    MultiImageSelector selector = MultiImageSelector.create(PublishNewAlbumActivity.this);
    selector.showCamera(showCamera);
    selector.count(maxNum);

    selector.multi();
    ArrayList<String> tempSelectPath = new ArrayList<>();
    tempSelectPath.addAll(selectImagePath);

    tempSelectPath.remove(tempSelectPath.size() - 1);

    selector.origin(tempSelectPath);
    selector.start(PublishNewAlbumActivity.this, REQUEST_IMAGE);
  }


  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void onCamaraDenied() {
    Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    PublishNewAlbumActivityPermissionsDispatcher.onRequestPermissionsResult(PublishNewAlbumActivity.this, requestCode, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE) {
      if (resultCode == RESULT_OK) {
        selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
        selectImagePath.add("");
        //更新数据
        imageGridAdapter.setImageList(selectImagePath);
      }
    }
  }
}