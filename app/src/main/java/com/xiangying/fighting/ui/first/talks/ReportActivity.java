package com.xiangying.fighting.ui.first.talks;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;


public class ReportActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_3)
  ImageView titleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title)
  FontTextView titleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.report_btn_submit)
  Button reportBtnSubmit;
  @Bind(R.id.report_listview)
  ListView reportListview;

  private ArrayList<String> datas = new ArrayList<>();
  int choice = 0;
  private String userName;
  private String type = "single";

  public static void start(Context context, String userName, String type) {
    Intent starter = new Intent(context, ReportActivity.class);
    starter.putExtra("id", userName);
    starter.putExtra("type", type);
    context.startActivity(starter);
  }


  @Override
  protected void process() {
    setData();
  }

  private void setData() {
    datas.clear();
    datas.add("色情低俗");
    datas.add("广告骚扰");
    datas.add("政治敏感");
    datas.add("谣言");
    datas.add("欺诈骗钱");
    datas.add("违法");
    final CommonAdapter<String> adaper = new CommonAdapter<String>(this, datas, R.layout.item_report) {
      @Override
      public void convert(final ViewHolder helper, String item, final int position) {
        helper.setText(R.id.report_item_contcnt, item);
        final CheckBox checkBox = helper.getView(R.id.report_item_check);
        if (choice == position) {
          checkBox.setChecked(true);
        } else {
          checkBox.setChecked(false);
        }
        helper.setItemClick(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkBox.performClick();
            choice = position;
            setData();
          }
        });
      }
    };
    reportListview.setAdapter(adaper);
  }

  @Override
  protected void setAllClick() {
    reportListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    reportBtnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (choice == -1) {
          Utils.toast(ReportActivity.this, "你还没选择任何举报理由");
        } else {
          String s = datas.get(choice);
          if (type.equals("single")) {
            reportSingle(s);
          } else {
            reportGroup(s);
          }
        }
      }
    });
  }

  private void reportGroup(String s) {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_REPORT_GROUP, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        if (Utils.valiueCommonReture((CommonReturn) msg.obj)){
          finish();
        }
        return false;
      }

    }));
    xUtilsHelper.setRequestParams(new String[][]{{"content", getConvertion(userName)}, {"group_id", userName}, {"type", s}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  private void reportSingle(String s) {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_REPORT_FRIEND, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        if (Utils.valiueCommonReture((CommonReturn) msg.obj)){
          finish();
        }
        return false;
      }

    }));
    xUtilsHelper.setRequestParams(new String[][]{{"content", getConvertion(userName)}, {"toid", userName}, {"type", s}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void initViews() {
    titleBarCommonIvOperate3.setVisibility(View.GONE);
    titleBarCommonTvTitle.setText("举报");

    userName = getIntent().getStringExtra("id");
    type = getIntent().getStringExtra("type");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_report);
    ButterKnife.bind(this);
  }


  @Override
  protected void loadNetData() {

  }


  private String getConvertion(String userName) {
    StringBuffer stringBuffer = new StringBuffer();
    List<EMMessage> emMessages = EMClient
        .getInstance()
        .chatManager()
        .getConversation(userName)
        .searchMsgFromDB(EMMessage.Type.TXT, System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 10, 10, null, EMConversation.EMSearchDirection.DOWN);

    for (EMMessage message : emMessages) {
      EMTextMessageBody messageBody =(EMTextMessageBody) message.getBody();
      stringBuffer.append(message.getMsgTime() + message.getFrom() + messageBody.getMessage());
    }
    return stringBuffer.toString();
  }
}
