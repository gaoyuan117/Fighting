package com.xiangying.fighting.ui.three.company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.CommonUtil;
import com.bumptech.glide.Glide;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.CompanyBean;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import service.api.CommonReturn;

public class CompanyInfoActivity extends BaseActivity {
    @Bind(R.id.title_bar_common_iv_operate_3)
    FontTextView titleBarCommonIvOrerate3;
    @Bind(R.id.et_company_name)
    ClearEditText mEtCompanyName;
    @Bind(R.id.et_company_contact)
    ClearEditText mEtCompanyContact;
    @Bind(R.id.et_company_mobile)
    ClearEditText mEtCompanyMobile;
    @Bind(R.id.et_company_phone)
    ClearEditText mEtCompanyPhone;
    @Bind(R.id.et_company_address)
    ClearEditText mEtCompanyAddress;
    @Bind(R.id.et_company_about)
    EditText mEtCompanyAbout;
    @Bind(R.id.img_company_add)
    ImageView mCompanyImg;
    private XUtilsHelper mXUtilsHelper;
    private XUtilsHelper mUploadImageHelper;
    private ArrayList<String> selectImagePath = new ArrayList<>();

    @Override
    protected void process() {

        findViewById(R.id.title_bar_common_iv_operate_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBarCommonIvOrerate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.isLeastSingleClick()) {
                    return;
                }
                addMyInfo();
            }
        });

        mCompanyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        MultiImageSelector.create()
                .single()
                .start(this, 1000);
    }

    private void addCompanyInfo(String license) {

        String companyName = mEtCompanyName.getText().toString().trim();
        String companyContact = mEtCompanyContact.getText().toString().trim();
        String companyContactMobile = mEtCompanyMobile.getText().toString().trim();
        String companyPhone = mEtCompanyPhone.getText().toString().trim();
        String companyAddress = mEtCompanyAddress.getText().toString().trim();
        String companyInfo = mEtCompanyAbout.getText().toString().trim();

        mXUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_COMPANY_ADD, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mXUtilsHelper.hideProgress();
                if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
                    finish();
                }
                return false;
            }
        }));
        mXUtilsHelper.setRequestParams(new String[][]{
                {"id", mData == null ? "" : mData.getId()},
                {"uid", GetLocalKey.getUid()},
                {"company", companyName}, //公司名称
                {"tel", companyPhone},   //公司电话
                {"phone", companyContactMobile}, //联系人电话
                {"name", companyContact},  //联系人姓名
                {"site", companyAddress}, //公司地址
                {"info", companyInfo}, //企业介绍
                {"license", license}
        });
        mXUtilsHelper.showProgress("正在提交");
        mXUtilsHelper.sendPostAuto(CommonReturn.class);
    }

    /**
     * 上传图片
     */
    private void addMyInfo() {
        mUploadImageHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    ImageFileBean imageFileBean = (ImageFileBean) msg.obj;
                    if (imageFileBean.getCode() == 1) {
                        addCompanyInfo(imageFileBean.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        mUploadImageHelper.setFileRequestParames2(selectImagePath);
        mUploadImageHelper.sendPostAuto(ImageFileBean.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            Bitmap bitmap = BitmapFactory.decodeFile(selectImagePath.get(0));
            mCompanyImg.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_company_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_MY_COMPANY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CompanyBean bean = (CompanyBean) msg.obj;
                    if (bean.getCode() == 1) {
                        showCompanyInfo(bean.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{});
        xUtilsHelper.sendPostAuto(CompanyBean.class);
    }


    private CompanyBean.Data mData;

    private void showCompanyInfo(CompanyBean.Data data) {
        mData = data;
        mEtCompanyName.setText(mData.getCompany());
        mEtCompanyContact.setText(mData.getName());
        mEtCompanyPhone.setText(mData.getTel());
        mEtCompanyMobile.setText(mData.getPhone());
        mEtCompanyAddress.setText(mData.getSite());
        mEtCompanyAbout.setText(mData.getInfo());
        Glide.with(this).load(Endpoint.HOST + mData.getLicense())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(mCompanyImg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
