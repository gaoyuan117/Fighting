
package me.kw.mall.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.zcomponent.annotation.ZNetNotify;
import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.enumerate.MessageType;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProviderShopBusiness;
import service.api.OnlineService;
import service.entity.ShopService;

@ZNetNotify(false)
@ZTitleMore(false)
public class MyMessageActivity extends MallBaseActivity {
  @Bind(R.id.rlayout_my_help1_click) LinearLayout rlayout_my_help1_click;
  @Bind(R.id.rlayout_my_help3_click) LinearLayout rlayout_my_help3_click;

//  private ConversationListFragment mContactFragment;
  private OnlineService mOnlineService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_my_meaage;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("消息");

    rlayout_my_help1_click.setVisibility(View.GONE);
//        rlayout_my_help3_click.setVisibility(View.GONE);
//    mContactFragment = new ConversationListFragment();
//    addFragment(R.id.flayout_message_show, mContactFragment);
    ProviderShopBusiness.queryShopOnlineSerivce(getHttpDataLoader());
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ShopService.OnlineServiceRequest.class)) {
      mOnlineService = msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, mOnlineService)) {
      }
    }
  }

  @OnClick(R.id.rlayout_my_help1_click)
  void onClickRlayoutMyHelp1() {
    intentToMessageListActivity("物流助手", MessageType.SHIPPING.getValue());
  }

  @OnClick(R.id.rlayout_my_help2_click)
  void onClickRlayoutMyHelp2() {
    intentToMessageListActivity("交易信息", MessageType.ORDER.getValue());
  }

  @OnClick(R.id.rlayout_my_help3_click)
  void onClickRlayoutMyHelp3() {
    intentToMessageListActivity("通知消息", MessageType.NOTIFY.getValue());
  }

  @OnClick(R.id.rlayout_my_help4_click)
  void onClickRlayoutMyHelp4() {
    intentToMessageListActivity("定时提醒", MessageType.TIME.getValue());
  }

  @OnClick(R.id.rlayout_my_help5_click)
  void onClickRlayoutMyHelp5() {
    intentToMessageListActivity("趣那活动", MessageType.ACTIVITY.getValue());
  }

  @OnClick(R.id.rlayout_my_help6_click)
  void onClickRlayoutMyHelp6() {

  }

  private void intentToMessageListActivity(String title, String type) {
    Bundle bundle = new Bundle();
    bundle.putString("title", title);
    bundle.putString("type", type);
//    getIntentHandle().intentToActivity(bundle, MyMessageListActivity.class);
  }
}