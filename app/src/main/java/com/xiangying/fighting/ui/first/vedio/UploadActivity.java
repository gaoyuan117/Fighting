package com.xiangying.fighting.ui.first.vedio;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.two.findrent.ImageGridAdapter;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
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
public class UploadActivity extends BaseActivity {

  @Bind(R.id.img_back)
  ImageView imgBack;
  @Bind(R.id.upload_tv_title)
  TextView uploadTvTitle;
  @Bind(R.id.upload_ed)
  EditText uploadEd;
  @Bind(R.id.upload_gv_images)
  MyGridView uploadGvImages;
  @Bind(R.id.upload_tv_send)
  TextView uploadTvSend;
  public int REQUEST_IMAGE = 10001;
  /**
   * 已选择图片的路径
   */
  ArrayList<String> selectImagePath = new ArrayList<>();

  ImageGridAdapter imageGridAdapter;

  //发布的类型，0正能量 1分享屋。
  private String type = "0";

  public static void start(Context context, String type) {
    Intent starter = new Intent(context, UploadActivity.class);
    starter.putExtra("type", type);
    context.startActivity(starter);
  }


  @Override
  protected void process() {
    type = getIntent().getStringExtra("type");

    selectImagePath.add("");

    imageGridAdapter = new ImageGridAdapter(selectImagePath, this);

    uploadGvImages.setAdapter(imageGridAdapter);

    uploadGvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == (selectImagePath.size() - 1)) {
          UploadActivityPermissionsDispatcher.chooseImagesWithCheck(UploadActivity.this);
        }
      }
    });

  }

  @Override
  protected void setAllClick() {
    imgBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    uploadTvSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addPicture();
      }
    });
  }

  private void addEnergy(String imageIds) {
    String content = uploadEd.getText().toString();
    if (TextUtils.isEmpty(content)) {
      Utils.toast(UploadActivity.this, "说点什么吧");
      return;
    }
    XUtilsHelper xUtilsHelper = new XUtilsHelper(UploadActivity.this, NetworkTools.SHARE_ADD, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        try {
          CommonReturn result = (CommonReturn) msg.obj;
          if (result.code == 1) {
            Toast.makeText(UploadActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
          }else {
            Toast.makeText(UploadActivity.this, result.message, Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {
          Toast.makeText(UploadActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    }));

    xUtilsHelper.setRequestParams(new String[][]{
        {"content", uploadEd.getText().toString().trim()},
        {"cover_ids", imageIds},
        {"is_energy", type}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  private void addPicture() {

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
            addEnergy(reslut.getData());
          }
        } catch (Exception e) {

        }
        return false;
      }
    }));
    xUtilsHelper.setFileRequestParames2(selectImagePath);
    xUtilsHelper.sendPostAuto(ImageFileBean.class);
  }

  /**
   * 查看图库文件
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void chooseImages() {
    boolean showCamera = true;
    int maxNum = 9;

    MultiImageSelector selector = MultiImageSelector.create(UploadActivity.this);
    selector.showCamera(showCamera);
    selector.count(maxNum);

    selector.multi();
    ArrayList<String> tempSelectPath = new ArrayList<>();
    tempSelectPath.addAll(selectImagePath);

    tempSelectPath.remove(tempSelectPath.size() - 1);

    selector.origin(tempSelectPath);
    selector.start(UploadActivity.this, REQUEST_IMAGE);


  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void onCamaraDenied() {
    Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    UploadActivityPermissionsDispatcher.onRequestPermissionsResult(UploadActivity.this, requestCode, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE) {
      if (resultCode == RESULT_OK) {
        selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//        uploadImage(selectImagePath);
        selectImagePath.add("");
        //更新数据
        imageGridAdapter.setImageList(selectImagePath);
      }
    }
  }

  @Override
  protected void initViews() {
    type = getIntent().getStringExtra("type");

    if ("1".equals(type)) {
      uploadTvTitle.setText("发布分享屋");
    } else {
      uploadTvTitle.setText("发布正能量");
    }

  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_upload_image);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }
}
