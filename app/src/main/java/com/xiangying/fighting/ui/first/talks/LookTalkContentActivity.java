package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LookTalkContentActivity extends BaseActivity {

  @Bind(R.id.img_back)
  ImageView imgBack;
  @Bind(R.id.look_talk_et_search)
  EditText lookTalkEtSearch;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  LinearLayout titleBarCommonRvViewGroup;
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;

  List<EMMessage> messages;
  private ArrayList<TalkBeans> datalist = new ArrayList<>();
  CommonAdapter<TalkBeans> adaper;

  private String chatId = "";
  private int chatType = 1;

  public static void start(Context context, String userid, int chatType) {
    Intent starter = new Intent(context, LookTalkContentActivity.class);
    starter.putExtra("userid", userid);
    starter.putExtra("type", chatType);
    context.startActivity(starter);
  }

  @Override
  protected void process() {
    onConversationInit();
    adaper = new CommonAdapter<TalkBeans>(this, datalist, R.layout.item_user_talk) {
      @Override
      public void convert(ViewHolder helper, TalkBeans item, int position) {
        helper.setText(R.id.fragmengtalk_child_item_tv_name, item.getName());
        helper.setText(R.id.fragmengtalk_child_item_tv_state, item.getContent());
        helper.setText(R.id.fragmengtalk_child_item_tv_time, DateUtils.getTimestampString(new Date(item.getMessagetime())));
        helper.setSimpleDraweeViewByUrl(R.id.fragmengtalk_child_item_img, item.getAvatar());
      }
    };
    commonListviewShow.setAdapter(adaper);
  }

  @Override
  protected void setAllClick() {
    imgBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    lookTalkEtSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        String str = lookTalkEtSearch.getText().toString();
        if (!TextUtils.isEmpty(str)) {
          searchEMessageByKey(str);
        } else {
          adaper.clearDatas();
          datalist.clear();
        }
      }
    });
  }

  /**
   * 根据关键字搜索聊天记录
   */
  private void searchEMessageByKey(String key) {
    EMTextMessageBody txtBody;
    Long time;
    adaper.clearDatas();
    datalist.clear();
    for (EMMessage message : messages) {
      if (message.getType() == EMMessage.Type.TXT) {
        txtBody = (EMTextMessageBody) message.getBody();
        if (txtBody.getMessage().contains(key)) {
          time = message.getMsgTime();
          if (chatType == EaseConstant.CHATTYPE_SINGLE || chatType == EaseConstant.CHATTYPE_GROUP) {
            if (EaseUserUtils.getUserInfo(chatId) != null) {
              EaseUser user = EaseUserUtils.getUserInfo(chatId);
              TalkBeans talkBeans = new TalkBeans(user.getNickname(), user.getAvatar());
              talkBeans.setContent(txtBody.getMessage());
              talkBeans.setMessagetime(time);
              datalist.add(talkBeans);
            }
          }
        }
      }
    }
    adaper.setmDatas(datalist);
  }

  protected void onConversationInit() {
    EMConversation conversation;
    conversation = EMClient.getInstance().chatManager().getConversation(chatId, EaseCommonUtils.getConversationType(chatType), true);
    messages = conversation.getAllMessages();
    int msgCount = messages != null ? messages.size() : 0;
    if (msgCount < conversation.getAllMsgCount() && msgCount < 100) {
      String msgId = null;
      if (messages != null && messages.size() > 0) {
        msgId = messages.get(0).getMsgId();
      }
      messages = conversation.loadMoreMsgFromDB(msgId, 100 - msgCount);
    }

  }

  @Override
  protected void initViews() {
    chatId = getIntent().getStringExtra("userid");
    chatType = getIntent().getIntExtra("type", 1);
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_look_talk_content);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }
}
