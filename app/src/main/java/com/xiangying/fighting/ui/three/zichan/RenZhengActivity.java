package com.xiangying.fighting.ui.three.zichan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class RenZhengActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;

    @Bind(R.id.et_rz_name)
    EditText etRzName;
    @Bind(R.id.et_rz_id)
    EditText etRzId;
    @Bind(R.id.img_rz_img1)
    ImageView imgRzImg1;
    @Bind(R.id.img_rz_img2)
    ImageView imgRzImg2;
    @Bind(R.id.bt_rz_sure)
    Button btRzSure;

    private String path1, path2;
    private int code1 = 0x110;
    private int code2 = 0x111;
    private ArrayList<String> mImgList;
    private String name;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng);
        ButterKnife.bind(this);
        mImgList = new ArrayList<>();
        initViewTitle();
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        imgRzImg1.setOnClickListener(this);
        imgRzImg2.setOnClickListener(this);
        btRzSure.setOnClickListener(this);
    }

    /**
     * 初始化标题
     */
    private void initViewTitle() {
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonTvTitle.setText("实名认证");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 选择图片
     */
    private void selectPic(int requestCode) {
        MultiImageSelector.create()
                .showCamera(false) // show camera or not. true by default
                .single() // single mode
                .multi() // multi mode, default mode;
                .start(this, requestCode);
    }

    /**
     * 图片回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        if (list.size() > 0) {
            mImgList.add(list.get(0));
        }
        Bitmap bitmap = BitmapFactory.decodeFile(list.get(0));
        if (requestCode == code1) {
            imgRzImg1.setImageBitmap(bitmap);
        } else if (requestCode == code2) {
            imgRzImg2.setImageBitmap(bitmap);
        }
    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_rz_img1://身份证正面照片
                selectPic(code1);
                break;
            case R.id.img_rz_img2://身份证反面照片
                selectPic(code2);
                break;
            case R.id.bt_rz_sure://提交
                name = etRzName.getText().toString().trim();
                cardId = etRzId.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cardId)) {
                    Toast.makeText(this, "请检查输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                addMyInfo(mImgList);
                break;
        }
    }

    /**
     * 上传图片和信息
     */
    private void addMyInfo(ArrayList<String> path) {
        XUtilsHelper mUploadImageHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    ImageFileBean imageFileBean = (ImageFileBean) msg.obj;
                    if (imageFileBean.getCode() == 1) {
                        postinfo(imageFileBean.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        mUploadImageHelper.setFileRequestParames2(path);
        mUploadImageHelper.sendPostAuto(ImageFileBean.class);
    }

    /**
     * 将信息上传到服务器
     */
    private void postinfo(String paths) {
        XUtilsHelper helper = new XUtilsHelper<>(getApplication(), NetworkTools.RENZHENGS, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean rechareBean = (RenZhengBean) msg.obj;
                if (rechareBean.getCode() == 1) {
                    Toast.makeText(RenZhengActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"username", name}, {"card", cardId}, {"covers_id", paths}});
        helper.sendPostAuto(RenZhengBean.class);
    }
}
