package com.xiangying.fighting.ui.first.armyuser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.EaseConstant;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.chat.chatutil.ChaiUtils;
import com.xiangying.fighting.ui.first.talks.GridPhotoActivity;
import com.xiangying.fighting.ui.first.talks.LookTalkContentActivity;
import com.xiangying.fighting.ui.first.talks.ReportActivity;
import com.xiangying.fighting.utils.JsonParse;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class UserTalkDetailActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView titleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.group_detail_img_avatar)
  SimpleDraweeView groupDetailImgAvatar;
  @Bind(R.id.group_detail_tv_name)
  TextView groupDetailTvName;
  @Bind(R.id.group_detail_tv_id)
  TextView groupDetailTvId;
  @Bind(R.id.group_talk_detail_check_zhiding)
  CheckBox groupTalkDetailCheckZhiding;
  @Bind(R.id.group_talk_detail_liner_zhiding)
  LinearLayout groupTalkDetailLinerZhiding;
  @Bind(R.id.group_talk_detail_check_miandarao)
  CheckBox groupTalkDetailCheckMiandarao;
  @Bind(R.id.group_talk_detail_liner_miandarao)
  LinearLayout groupTalkDetailLinerMiandarao;
  @Bind(R.id.group_talk_detail_liner_tupian)
  LinearLayout groupTalkDetailLinerTupian;
  @Bind(R.id.group_talk_detail_liner_lookcontent)
  LinearLayout groupTalkDetailLinerLookcontent;
  @Bind(R.id.group_talk_detail_liner_clearcontent)
  LinearLayout groupTalkDetailLinerClearcontent;
  @Bind(R.id.group_talk_detail_liner_jubao)
  LinearLayout groupTalkDetailLinerJubao;
  @Bind(R.id.talk_detail_rela_user)
  RelativeLayout rel_user;

  private String userName = "";
  private TalkBeans talkBeans;

  public static void start(Context context, String userName) {
    Intent starter = new Intent(context, UserTalkDetailActivity.class);
    starter.putExtra("user_name", userName);
    context.startActivity(starter);
  }

  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(this);
    rel_user.setOnClickListener(this);
    groupTalkDetailLinerTupian.setOnClickListener(this);
    groupTalkDetailLinerLookcontent.setOnClickListener(this);
    groupTalkDetailLinerClearcontent.setOnClickListener(this);
    groupTalkDetailLinerJubao.setOnClickListener(this);

    groupTalkDetailCheckMiandarao.setOnCheckedChangeListener(this);
    groupTalkDetailCheckZhiding.setOnCheckedChangeListener(this);

  }

  @Override
  protected void initViews() {
    titleBarCommonIvOperate3.setVisibility(View.GONE);
    titleBarCommonTvTitle.setText("聊天详情");
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    userName = getIntent().getStringExtra("user_name");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_talk_detail);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    XUtilsHelper mUserInfoHelper = new XUtilsHelper(this, NetworkTools.HX_FRIEND_INFO, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        try {
          String result = msg.obj.toString();
          if (Utils.getResultCode(UserTalkDetailActivity.this, result) == 1) {
            talkBeans = JsonParse.ParseUser(result);
            if (!TextUtils.isEmpty(talkBeans.getHeadimg())) {
              groupDetailImgAvatar.setImageURI(Uri.parse(Endpoint.HOST + talkBeans.getHeadimg()));
            }
            groupDetailTvName.setText(talkBeans.getNickname());
            groupDetailTvId.setText("战斗号：" + talkBeans.getZd_id());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    mUserInfoHelper.setRequestParams(new String[][]{{"friend_name", userName}});
    mUserInfoHelper.sendPost();
  }

  @Override
  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.title_bar_common_iv_operate_1:
        finish();
        break;
      case R.id.talk_detail_rela_user://查看好友
        FriendActivity.start(this, userName, talkBeans);
        break;
      case R.id.group_talk_detail_liner_tupian://聊天图片
        GridPhotoActivity.start(this, userName);
        break;
      case R.id.group_talk_detail_liner_lookcontent://查看聊天内容
        LookTalkContentActivity.start(this,userName, EaseConstant.CHATTYPE_SINGLE);
        break;
      case R.id.group_talk_detail_liner_clearcontent://清除聊天内容
        ChaiUtils.emptyHistory(UserTalkDetailActivity.this, userName);
        break;
      case R.id.group_talk_detail_liner_jubao://举报
        ReportActivity.start(this, userName,"single");
        break;
    }
    if (intent != null) {
      startActivity(intent);
    }
  }

  @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()) {
      case R.id.group_talk_detail_check_zhiding: //指定
        if (isChecked) {
          top();
        }else{
          no_top();
        }
        break;
      case R.id.group_talk_detail_check_miandarao:
        break;
    }
  }

  private void no_top() {
    //置顶好友
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_TOP, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
          loadNetData();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{{"friend_name",userName}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  private void top() {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_NO_TOP, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
          loadNetData();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{{"friend_name",userName}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }
}
