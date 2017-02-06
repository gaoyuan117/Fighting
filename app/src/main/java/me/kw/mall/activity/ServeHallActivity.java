
package me.kw.mall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.hyphenate.easeui.EaseConstant;
import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.chat.ChatActivity;
import com.xiangying.fighting.ui.chat.help.DemoHelper;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderShopBusiness;
import service.api.OnlineService;
import service.entity.ShopService;

@ZTitleMore(false)
public class ServeHallActivity extends MallBaseActivity {
  @Bind(R.id.tvew_serve_hall_phone_show) TextView mTvewServeHallPhone;
  private OnlineService mOnlineService;

  @Override protected int getLayoutId() {
    return R.layout.activity_serve_hall;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("服务大厅");
    String strPhone = getIntent().getStringExtra("phone");
    if (!TextUtils.isEmpty(strPhone)) {
      mOnlineService = JsonSerializerFactory.Create().decode(strPhone, OnlineService.class);
    } else {
      ProviderShopBusiness.queryShopOnlineSerivce(getHttpDataLoader());
      showWaitDialog(1, false, "正在查询信息...");
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ShopService.OnlineServiceRequest.class)) {
      mOnlineService = msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, mOnlineService)) {
        mTvewServeHallPhone.setText(mOnlineService.data.phone);
      }
    }
  }

  @OnClick(R.id.llayout_serve_hall_online_click)
  void onClickLlayoutServeHallOnline() {
    // TODO: 2016/10/18  
    if (DemoHelper.getInstance().isLoggedIn()) {
      if (null != mOnlineService && null != mOnlineService.data && mOnlineService.data.hx_accoun.length > 0
          && !TextUtils.isEmpty(mOnlineService.data.hx_accoun[0])) {
        toChatActivity(mOnlineService.data.hx_accoun[0]);
      } else {
        toChatActivity("18954066795");
      }
    }
  }

  private void toChatActivity(String userName) {
    Intent intent = new Intent(this, ChatActivity.class);
    intent.putExtra(EaseConstant.EXTRA_USER_ID, userName);
    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
    startActivity(intent);
  }

  @OnClick(R.id.llayout_serve_hall_call_click)
  void onClickLlayoutServeHallCall() {
    if (!TextUtils.isEmpty(mTvewServeHallPhone.getText().toString())) {
      ShowMsg.showCallDialog(this, mTvewServeHallPhone.getText().toString());
    } else {
      ProviderShopBusiness.queryShopOnlineSerivce(getHttpDataLoader());
    }
  }

}