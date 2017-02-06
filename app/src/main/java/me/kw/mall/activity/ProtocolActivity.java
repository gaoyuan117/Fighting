package me.kw.mall.activity;


import android.widget.CheckBox;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.util.ShowMsg;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.kw.mall.model.ProviderShopBusiness;
import service.entity.ShopService;

@ZTitleMore(false)
public class ProtocolActivity extends MallBaseActivity {
  @Bind(R.id.wvew_protocol_show) TextView mWvewProtocol;
  @Bind(R.id.btn_openshop_click) TextView mBtnOpenShop;
  @Bind(R.id.check_protocol_click) CheckBox mCheckBox;

  @Override protected int getLayoutId() {
    return R.layout.activity_protocol;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("开店协议");
    ProviderShopBusiness.queryShopProtocol(getHttpDataLoader());
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ShopService.ShopProtocolRequest.class)) {
      if (null != msg.getContext().data()) {
        String response = new String(msg.getContext().data());
        mWvewProtocol.setText(response);
      } else {
        ShowMsg.showToast(getApplicationContext(), msg, "查询开店协议失败");
        finish();
      }
    }
  }

  @OnClick(R.id.btn_openshop_click)
  void onClickBtnOpenshop() {
    if (mCheckBox.isChecked()) {
      getIntentHandle().intentToActivity(ShopSetActivity.class);
      finish();
    } else {
      ShowMsg.showToast(this, "请勾选我已阅读并同意用户协议！");
    }
  }

  @OnCheckedChanged(R.id.check_protocol_click)
  void onCheckProtocol(boolean isChecked) {
    mBtnOpenShop.setEnabled(isChecked);
    if (isChecked) {
      mBtnOpenShop.setTextColor(getResources().getColor(
          R.color.common_btn_text_write_color_selector));
    } else {
      mBtnOpenShop.setTextColor(getResources().getColor(R.color.gray));
    }
  }

}