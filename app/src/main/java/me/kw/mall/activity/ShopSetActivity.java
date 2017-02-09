
package me.kw.mall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.views.CircleImageView;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.dao.ProviderShop;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderFileBusiness;
import me.kw.mall.model.ProviderShopBusiness;
import service.api.CommonReturn;
import service.api.ShopInfo;
import service.entity.ShopService;

@ZTitleMore(false)
public class ShopSetActivity extends MallBaseActivity {

    @Bind(R.id.ivew_shop_set_shopicon_show)
    CircleImageView mIvewShopSetShopicon;
    @Bind(R.id.ivew_shop_fitment_signboard_show)
    ImageView mIvewShopSetSignboardicon;
    @Bind(R.id.tvew_shop_set_shopname_show)
    TextView mTvewShopSetShopname;
    @Bind(R.id.tvew_shop_set_shopintroduce_show)
    TextView mTvewShopSetShopintroduce;

    private boolean isSetShopicon = false;
    private String mShopIconId;
    private String mSignboardIconId;
    private String mTitle;
    private String mDesc;
    private ShopInfo.Data mShopInfo;
    protected ProviderFileBusiness mFileBusiness;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_set;
    }

    @Override
    protected void initUI() {
        getTitleBar().setTitleText("店铺设置");
        String strShopInfo = getIntent().getStringExtra("shop");
        if (!TextUtils.isEmpty(strShopInfo)) {
            mShopInfo = JsonSerializerFactory.Create().decode(strShopInfo, ShopInfo.Data.class);
            showShopInfo();
        }

        mFileBusiness = new ProviderFileBusiness(this, getHttpDataLoader());
        mFileBusiness.dismissRecordBtn();
        mFileBusiness.setOutParams(3, 5, 768, 1280);

        mFileBusiness.setOnUploadSuccessListener(new ProviderFileBusiness.OnUploadSuccessListener() {
            @Override
            public void onUploadSuccess(String id) {
                if (isSetShopicon) {
                    mShopIconId = id;
                } else {
                    mSignboardIconId = id;
                }
            }
        });
    }

    private void showShopInfo() {
        if (null != mShopInfo) {
            mTitle = mShopInfo.title;
            mDesc = mShopInfo.description;
            mShopIconId = mShopInfo.headpic;
            mSignboardIconId = mShopInfo.signpic;
            mTvewShopSetShopname.setText(mTitle);
            mTvewShopSetShopintroduce.setText(mDesc);
            loadImage(mIvewShopSetShopicon, Endpoint.HOST + mShopInfo.headpic_path, R.drawable
                    .transparent);
            loadImage(mIvewShopSetSignboardicon, Endpoint.HOST + mShopInfo.signpic_path, R.drawable
                    .transparent);
        }
    }

    @Override
    public void onRecvMsg(MessageData msg) throws Exception {
        mFileBusiness.onRecvMsg(msg);
        if (msg.valiateReq(ShopService.CreateShopRequest.class)) {
            CommonReturn response = (CommonReturn) msg.getRspObject();
            if (CommonValidate.validateQueryState(this, msg, response, "创建店铺失败")) {
                ShowMsg.showToast(getApplicationContext(), msg, "创建店铺成功");
                getIntentHandle().intentToActivity(MyShopActivity.class);
                finish();
            }
        } else if (msg.valiateReq(ShopService.EditShopRequest.class)) {
            CommonReturn response = (CommonReturn) msg.getRspObject();
            if (CommonValidate.validateQueryState(this, msg, response, "店铺设置失败")) {
                if (null != mShopInfo) {
                    mShopInfo.title = mTitle;
                    mShopInfo.description = mDesc;
                    mShopInfo.headpic = mShopIconId;
                    mShopInfo.signpic = mSignboardIconId;
                }
                Intent intent = new Intent();
                intent.putExtra("shop", JsonSerializerFactory.Create().encode(mShopInfo));
                FramewrokApplication.getInstance().setActivityResult(
                        ProviderResultActivity.CODE_EDIT_SHOP, intent);
                ShowMsg.showToast(getApplicationContext(), msg, "店铺设置成功");
                finish();
            }
        }
    }

    @OnClick(R.id.llayout_shop_set_shopicon_click)
    void onClickLlayoutShopSetShopicon() {
        isSetShopicon = true;
        mFileBusiness.setOutParams(1, 1, 600, 600);
        mFileBusiness.selectPicture();
    }

    @OnClick(R.id.llayout_shop_fitment_signboard_click)
    void onClickLlayoutShopSetSignboardicon() {
        isSetShopicon = false;
        mFileBusiness.setOutParams(5, 3, 500, 300);
        mFileBusiness.selectPicture();
    }

    @OnClick(R.id.btn_shop_save_click)
    void onClickBtnShopSet() {

        boolean isSend = false;
        if (null != mShopInfo) {
            isSend = ProviderShopBusiness.queryShopEdit(this, getHttpDataLoader(), mShopInfo.id,
                    mTitle, mDesc, mShopIconId, mSignboardIconId);
        } else {
            isSend = ProviderShopBusiness.queryShopCreate(this, getHttpDataLoader(), mTitle,
                    mDesc, mShopIconId, mSignboardIconId);


        }
        if (isSend) {
            showWaitDialog(1, false, R.string.common_submit_data);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("店铺审核成功后,需要交纳2500元的保证金，是否继续？");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProviderShop.sendCmdQueryShopCreate(getHttpDataLoader(), mTitle, mDesc,
                            mShopIconId, mSignboardIconId);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @OnClick(R.id.llayout_shop_set_shopname_click)
    void onClickLlayoutShopSetShopname() {
        Bundle bundle = new Bundle();
        bundle.putString("name", mTitle);
        getIntentHandle().intentToActivity(bundle, ShopNameActivity.class);
    }

    @OnClick(R.id.llayout_shop_set_shopintroduce_click)
    void onClickLlayoutShopSetShopintroduce() {
        Bundle bundle = new Bundle();
        bundle.putString("desc", mDesc);
        getIntentHandle()
                .intentToActivity(bundle, ShopIntroduceActivity.class);
    }

    @Override
    public void onActivityResultCallBack(int resultCode, Intent intent) {
        if (ProviderResultActivity.CODE_EDIT_SHOP_NAME == resultCode) {
            mTitle = intent.getStringExtra("name");
            mTvewShopSetShopname.setText(mTitle);
        } else if (ProviderResultActivity.CODE_EDIT_SHOP_DESC == resultCode) {
            mDesc = intent.getStringExtra("desc");
            mTvewShopSetShopintroduce.setText(mDesc);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = mFileBusiness.onActivityResult(requestCode, resultCode, data);
        if (null != bitmap) {
            if (isSetShopicon) {
                mIvewShopSetShopicon.setImageBitmap(bitmap);
            } else {
                mIvewShopSetSignboardicon.setImageBitmap(bitmap);
            }
        }
    }
}