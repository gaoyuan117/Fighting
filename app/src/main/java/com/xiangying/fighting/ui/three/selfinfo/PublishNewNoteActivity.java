package com.xiangying.fighting.ui.three.selfinfo;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.CommonUtil;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class PublishNewNoteActivity extends BaseActivity {
  @Bind(R.id.mnickname_save) FontTextView mMnicknameSave;
  @Bind(R.id.title_bar_common_tv_title) FontTextView mTitleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1) ImageView mTitleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_rv_viewGroup) RelativeLayout mTitleBarCommonRvViewGroup;
  @Bind(R.id.activity_PNA_et_title) EditText mActivityPNAEtTitle;
  @Bind(R.id.activity_PNA_et_content) EditText mActivityPNAEtContent;

  @Override
  protected void process() {
  }

  @Override
  protected void setAllClick() {
    mMnicknameSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addNewNote();
      }
    });
  }

  private void addNewNote() {
    if (!CommonUtil.checkEt(this, mActivityPNAEtContent, "请输入日记内容")) {
      return;
    }

    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.ME_ALL_NOTE_ADD, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          CommonReturn commonReturn = (CommonReturn) msg.obj;
          if (commonReturn.code == 1) {
            Toast.makeText(PublishNewNoteActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
          }
        } catch (Exception e) {
          Toast.makeText(PublishNewNoteActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    }));

    xUtilsHelper.setRequestParams(new String[][]{{"type","0["},{"content", mActivityPNAEtContent.getText().toString().trim()}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void initViews() {
    mTitleBarCommonTvTitle.setText("发布日记");
    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mMnicknameSave.setText("发布");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_publish_new_note);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }
}