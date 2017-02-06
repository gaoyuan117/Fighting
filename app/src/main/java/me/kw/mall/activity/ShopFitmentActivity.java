
package me.kw.mall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

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
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderFileBusiness;
import me.kw.mall.model.ProviderShopBusiness;
import service.api.CommonReturn;
import service.api.ShopInfo;
import service.entity.ShopService;

@ZTitleMore(false)
public class ShopFitmentActivity extends MallBaseActivity {
  @Bind(R.id.ivew_shop_fitment_shopicon_show) CircleImageView mIvewShopFitmentShopicon;
  @Bind(R.id.ivew_shop_fitment_signboard_show) ImageView mIvewShopFitmentSignboard;

  private ShopInfo.Data mShopInfo;
  private boolean isSetShopicon = false;
  private String mShopIconId;
  private String mSignboardIconId;
  protected ProviderFileBusiness mFileBusiness;

  @Override protected int getLayoutId() {
    return R.layout.activity_shop_fitment;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("店铺装修");

    String strShopInfo = getIntent().getStringExtra("shop");
    if (!TextUtils.isEmpty(strShopInfo)) {
      mShopInfo = JsonSerializerFactory.Create().decode(strShopInfo, ShopInfo.Data.class);
      showShopInfo();
    }

    mFileBusiness = new ProviderFileBusiness(this, getHttpDataLoader());
    mFileBusiness.dismissRecordBtn();
    mFileBusiness.setOutParams(3, 5, 768, 1280);

    mFileBusiness.setOnUploadSuccessListener(new ProviderFileBusiness.OnUploadSuccessListener() {
      @Override public void onUploadSuccess(String id) {
        if (isSetShopicon) {
          mShopIconId = id;
        } else {
          mSignboardIconId = id;
        }
        boolean isSend = false;
        if (null != mShopInfo) {
          isSend = ProviderShopBusiness.queryShopEdit(
                  ShopFitmentActivity.this,
                  getHttpDataLoader(), mShopInfo.id,
                  mShopInfo.title, mShopInfo.description,
                  mShopIconId, mSignboardIconId);
        } else {
          isSend = ProviderShopBusiness.queryShopCreate(
                  ShopFitmentActivity.this,
                  getHttpDataLoader(), "", "", mShopIconId,
                  mSignboardIconId);
        }
        if (isSend) {
          showWaitDialog(1, false, R.string.common_submit_data);
        }
      }
    });
  }

  private void showShopInfo() {
    if (null != mShopInfo) {
      mShopIconId = mShopInfo.headpic;
      mSignboardIconId = mShopInfo.signpic;
      loadImage(mIvewShopFitmentShopicon, Endpoint.HOST + mShopInfo.headpic_path, R.drawable.transparent);
      loadImage(mIvewShopFitmentSignboard, Endpoint.HOST + mShopInfo.signpic_path, R.drawable.transparent);
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    mFileBusiness.onRecvMsg(msg);
    if (msg.valiateReq(ShopService.EditShopRequest.class)) {
      CommonReturn response = (CommonReturn) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response, "店铺设置失败")) {
        if (null != mShopInfo) {
          mShopInfo.headpic = mShopIconId;
          mShopInfo.signpic = mSignboardIconId;
        }
        Intent intent = new Intent();
        intent.putExtra("shop", JsonSerializerFactory.Create().encode(mShopInfo));
        FramewrokApplication.getInstance().setActivityResult(ProviderResultActivity.CODE_EDIT_SHOP, intent);
        ShowMsg.showToast(getApplicationContext(), msg, "修改成功");
      }
    }
  }

  @OnClick(R.id.llayout_shop_fitment_shopicon_click)
  void onClickLlayoutShopFitmentShopicon() {
    isSetShopicon = true;
    mFileBusiness.setOutParams(1, 1, 600, 600);
    mFileBusiness.selectPicture();
  }

  @OnClick(R.id.llayout_shop_fitment_signboard_click)
  void onClickLlayoutShopFitmentSignboard() {
    isSetShopicon = false;
    mFileBusiness.setOutParams(5, 3, 500, 300);
    mFileBusiness.selectPicture();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Bitmap bitmap = mFileBusiness.onActivityResult(requestCode, resultCode, data);
    if (null != bitmap) {
      if (isSetShopicon) {
        mIvewShopFitmentShopicon.setImageBitmap(bitmap);
      } else {
        mIvewShopFitmentSignboard.setImageBitmap(bitmap);
      }
    }
  }
}