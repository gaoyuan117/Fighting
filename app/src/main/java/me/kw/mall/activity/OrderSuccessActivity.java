
package me.kw.mall.activity;

import android.app.Activity;

import com.android.zcomponent.annotation.ZTitleMore;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;

import java.util.List;

import butterknife.OnClick;

@ZTitleMore(false)
public class OrderSuccessActivity extends MallBaseActivity {

  @Override protected int getLayoutId() {
    return R.layout.activity_order_success;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("提交订单");
  }

  @OnClick(R.id.btn_back_to_home_click)
  void onClickBackToHome() {
    backToHome();
    TabHostActivity activity = BaseApplication.getInstance().getActivity(TabHostActivity.class);
    if (null != activity) {
      activity.setSelectedTab(0);
    }
    finish();
  }

  @OnClick(R.id.btn_back_to_me_click)
  void onClickBackToMe() {
    backToHome();
    TabHostActivity activity = BaseApplication.getInstance().getActivity(TabHostActivity.class);
    if (null != activity) {
      activity.setSelectedTab(4);
    }
    finish();
  }

  private void backToHome() {
    List<Activity> activityList = BaseApplication.getInstance().getActivitys();
    for (int i = 0; i < activityList.size(); i++) {
      if (activityList.get(i) instanceof TabHostActivity || activityList.get(i) instanceof
          OrderSuccessActivity) {
      } else {
        activityList.get(i).finish();
      }
    }
  }
}