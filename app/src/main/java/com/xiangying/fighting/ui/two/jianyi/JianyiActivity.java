package com.xiangying.fighting.ui.two.jianyi;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class JianyiActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView titleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_iv_operate_2)
  ImageView titleBarCommonIvOperate2;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.activity_et_jianyi_content)
  ClearEditText activityEtJianyiContent;
  @Bind(R.id.activity_et_jianyi_commit)
  FontTextView activityEtJianyiCommit;

  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {
    activityEtJianyiCommit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addReport();
      }
    });
  }

  private void addReport() {

    if (TextUtils.isEmpty(activityEtJianyiContent.getText().toString().trim())) {
      Utils.toast(this,"请填写信息！");
      return;
    }

    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.ADDADVISE, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          CommonReturn commonReturn = (CommonReturn) msg.obj;
          if (commonReturn.code == 1
              ) {
            Toast.makeText(JianyiActivity.this, commonReturn.message, Toast.LENGTH_SHORT).show();
            finish();
          }else{
            Toast.makeText(JianyiActivity.this, commonReturn.message, Toast.LENGTH_SHORT).show();
          }
        } catch (Exception e) {
          Toast.makeText(JianyiActivity.this, "反馈失败", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    }));

    xUtilsHelper.setRequestParams(new String[][]{{"content", activityEtJianyiContent.getText().toString().trim()}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("建议与投诉");
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_jianyi);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {

  }
}
