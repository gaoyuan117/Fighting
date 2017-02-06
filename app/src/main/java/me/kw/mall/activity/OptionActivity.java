package me.kw.mall.activity;

import android.text.TextUtils;
import android.widget.EditText;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.util.ShowMsg;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.ui.login.LoginActivity;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.HelpBusiness;
import service.api.CommonReturn;
import service.entity.HelpService;

@ZTitleMore(false)
public class OptionActivity extends MallBaseActivity {
  @Bind(R.id.editvew_content_show) EditText mEditTvewContent;

  @Override protected int getLayoutId() {
    return R.layout.activity_feedback;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("意见反馈");
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(HelpService.FeedbackRequest.class)) {
      CommonReturn response = (CommonReturn) msg.getRspObject();

      if (CommonValidate.validateQueryState(this, msg, response, "提交失败")) {
        ShowMsg.showToast(getApplicationContext(), "提交成功");
        finish();
      }
    }
  }

  @OnClick(R.id.tvew_submit_click)
  void onClickTvewSubmit() {
    if (!BaseApplication.isLogin()) {
      getIntentHandle().intentToActivity(LoginActivity.class);
      return;
    }
    String content = mEditTvewContent.getText().toString();

    if (!TextUtils.isEmpty(content)) {
      HelpBusiness.queryFeedback(getHttpDataLoader(), content);
      showWaitDialog(1, false, R.string.common_submit_data);
    } else {
      ShowMsg.showToast(getApplicationContext(), "请输入意见内容");
    }
  }
}