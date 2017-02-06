
package me.kw.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.constant.ProviderGlobalConst;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.enumerate.GoodsStatus;
import me.kw.mall.enumerate.ProviderOrderStatus;
import me.kw.mall.model.ProviderShopBusiness;
import service.api.CommonReturn;
import service.api.OnlineService;
import service.api.ShopInfo;
import service.entity.ShopService;

@ZTitleMore(false)
public class MyShopActivity extends MallBaseActivity {
  @Bind(R.id.imgvew_shop_logo_show) ImageView mImgvewShopLogo;
  @Bind(R.id.imgvew_signpic_show) ImageView mImgvewShopSignpic;
  @Bind(R.id.tvew_shopname_show) TextView mTvewShopname;
  @Bind(R.id.tvew_allmoeny_show) TextView mTvewAllmoeny;
  @Bind(R.id.tvew_visitorcount_show) TextView mTvewVisitorcount;
  @Bind(R.id.tvew_ordercount_show) TextView mTvewOrdercount;

  private OnlineService mOnlineService;
  private ShopInfo.Data mShopInfo;
  @Override protected int getLayoutId() {
    return R.layout.activity_myshop;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("我的店铺 ");

    ProviderShopBusiness.queryShopInfo(getHttpDataLoader());
    ProviderShopBusiness.queryShopOnlineSerivce(getHttpDataLoader());
    getDataEmptyView().showViewWaiting();
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ShopService.ShopInfoRequest.class)) {
      ShopInfo response = msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS && null != response.data
            && !TextUtils.isEmpty(response.data.id)) {
          showShopInfo(response.data);
          getDataEmptyView().dismiss();
        } else {
          if (response.code == -2) {
            showCreateShopConfirmDialog();
          } else {
            showToast(response.message);
            finish();
          }
        }
      } else {
        ProviderShopBusiness.queryShopInfo2(getHttpDataLoader());
      }
    } else if (msg.valiateReq(ShopService.OnlineServiceRequest.class)) {
      mOnlineService = msg.getRspObject();
    } else if (msg.valiateReq(ShopService.ShopInfoRequest2.class)) {
      CommonReturn response = msg.getRspObject();
      if (response != null) {
        if (response.code == -2) {
          showCreateShopConfirmDialog();
        } else {
          showToast(response.message);
          finish();
        }
      } else {
        if (null == mShopInfo) {
          getDataEmptyView().showViewDataEmpty(false, false, msg, "");
        }
      }
    }
  }

  private void showShopInfo(ShopInfo.Data shopInfo) {
    mShopInfo = shopInfo;
    if (!TextUtils.isEmpty(shopInfo.headpic_path)) {
      loadImage(mImgvewShopLogo, Endpoint.HOST + shopInfo.headpic_path, 0);
    }
    if (!TextUtils.isEmpty(shopInfo.signpic_path)) {
      loadImage(mImgvewShopSignpic, Endpoint.HOST + shopInfo.signpic_path, 0);
    }
    mTvewShopname.setText(shopInfo.title);
    mTvewAllmoeny.setText("¥" + StringUtil.formatProgress(shopInfo.amount));
    mTvewVisitorcount.setText(shopInfo.visitor_count);
    mTvewOrdercount.setText(shopInfo.order_count);
  }

  private void showCreateShopConfirmDialog() {
    getDataEmptyView().removeAllViews();
    ShowMsg.showConfirmDialog(this, new ShowMsg.IConfirmDialog() {
      @Override
      public void onConfirm(boolean confirmValue) {
        if (confirmValue) {
          getIntentHandle().intentToActivity(ProtocolActivity.class);
        }
        finish();
      }
    }, "创建", "取消", "您还没有创建店铺，快快去开店吧！");
  }


  @OnClick(R.id.llayout_myshop_goods_release_click)
  void onClickLlayoutMyshopGoodsRelease() {
//       getIntentHandle().intentToActivity(ReleaseActivity_.class);
    getIntentHandle().intentToActivity(ReleaseCommodityGoodsActivity.class);
  }

  @OnClick(R.id.llayout_myshop_goods_manage_click)
  void onClickLlayoutMyshopGoodsManage() {
    Bundle bundle = new Bundle();
    bundle.putString("type", GoodsStatus.PUTONG.getValue());
    getIntentHandle().intentToActivity(bundle, GoodsManageActivity.class);
  }

  @OnClick(R.id.llayout_myshop_order_manage_click)
  void onClickLlayoutMyshopOrderManage() {
    Bundle bundle = new Bundle();
    bundle.putString(ProviderGlobalConst.ORDER_STATUS, ProviderOrderStatus.ALL.getValue());
    getIntentHandle().intentToActivity(bundle, MyOrderActivity.class);
  }

  @OnClick(R.id.llayout_myshop_jifen_manager_click)
  void onClickLlayoutMyshopJifenManager() {
    Bundle bundle = new Bundle();
    bundle.putString("shop_id", mShopInfo.id);
//    getIntentHandle().intentToActivity(bundle, MyshopJifenManager.class);
  }

  @OnClick(R.id.llayout_myshop_shop_set_click)
  void onClickLlayoutMyshopShopSet() {
    if (null != mShopInfo) {
      Bundle bundle = new Bundle();
      bundle.putString("shop", JsonSerializerFactory.Create().encode(mShopInfo));
      getIntentHandle().intentToActivity(bundle, ShopSetActivity.class);
    } else {
      getIntentHandle().intentToActivity(ShopSetActivity.class);
    }
  }

  @OnClick(R.id.llayout_myshop_serve_hall_click)
  void onClickLlayoutMyshopServeHall() {
    Bundle bundle = new Bundle();
    bundle.putString("phone", JsonSerializerFactory.Create().encode(mOnlineService));
    getIntentHandle().intentToActivity(bundle, ServeHallActivity.class);
  }

  @OnClick(R.id.llayout_myshop_shop_fitment_click)
  void onClickLlayoutMyshopShopFitment() {
    Bundle bundle = new Bundle();
    bundle.putString("shop", JsonSerializerFactory.Create().encode(mShopInfo));
    getIntentHandle().intentToActivity(bundle, ShopFitmentActivity.class);
  }

  @OnClick(R.id.llayout_myshop_study_click)
  void onClickLlayoutMyshopStudy() {
//    getIntentHandle().intentToActivity(CourseActivity.class);
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (ProviderResultActivity.CODE_EDIT_SHOP == resultCode) {
      String strShopInfo = intent.getStringExtra("shop");
      if (!TextUtils.isEmpty(strShopInfo)) {
        mShopInfo = JsonSerializerFactory.Create().decode(strShopInfo, ShopInfo.Data.class);
        showShopInfo(mShopInfo);
        ProviderShopBusiness.queryShopInfo(getHttpDataLoader());
      }
    }
  }
}