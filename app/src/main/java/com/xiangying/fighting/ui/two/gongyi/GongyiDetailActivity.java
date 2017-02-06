package com.xiangying.fighting.ui.two.gongyi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.ShowMsg;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;
import service.entity.WelfareDetailResult;

public class GongyiDetailActivity extends BaseActivity {
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
  @Bind(R.id.activity_gongyi_adv_xuanchuantu)
  SimpleDraweeView activityGongyiAdvXuanchuantu;
  @Bind(R.id.activity_gongyi_tv_mubiao)
  FontTextView activityGongyiTvMubiao;
  @Bind(R.id.activity_gongyi_tv_dangqian)
  FontTextView activityGongyiTvDangqian;
  @Bind(R.id.activity_gongyi_tv_renshu)
  FontTextView activityGongyiTvRenshu;
  @Bind(R.id.icon_juankuan)
  SimpleDraweeView iconJuankuan;
  @Bind(R.id.activity_gongyi_juankuan_ll)
  RelativeLayout activityGongyiJuankuanLl;
  @Bind(R.id.juankuan_num_et)
  ClearEditText juankuanNumEt;
  @Bind(R.id.activity_gongyi_juankuansure_ll)
  RelativeLayout activityGongyiJuankuansureLl;
  @Bind(R.id.juankuan_cancle)
  SimpleDraweeView juankuanCancle;
  @Bind(R.id.enter_juankuan_num_ll)
  RelativeLayout enterJuankuanNumLl;
  @Bind(R.id.activity_gongyi_tv_info) FontTextView activityGongyiTvInfo;
  @Bind(R.id.activity_gongyi_tv_des) FontTextView activityGongyiTvDes;


  private String id;
  private XUtilsHelper mHelper;

  public static void start(Context context, String id) {
    Intent intent = new Intent(context, GongyiDetailActivity.class);
    intent.putExtra("id", id);
    context.startActivity(intent);
  }

  @Override
  protected void process() {
    id = getIntent().getStringExtra("id");
  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(GongyiDetailActivity.this, JuanzengListActivity.class));
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBarCommonTvTitle.setText("公益捐赠");
    titleBarCommonIvOperate3.setImageResource(R.drawable.wodejuankuan);

    activityGongyiJuankuanLl.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        enterJuankuanNumLl.setVisibility(View.VISIBLE);
      }
    });

    activityGongyiJuankuansureLl.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        welfareDepite();
      }
    });

    juankuanCancle.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        enterJuankuanNumLl.setVisibility(View.GONE);
      }
    });
  }

  private void welfareDepite() {
    String num = juankuanNumEt.getText().toString().trim();
    XUtilsHelper xUtilsHelper = new XUtilsHelper(GongyiDetailActivity.this, NetworkTools.API_WELFARE_DONATION, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        CommonReturn result = (CommonReturn) msg.obj;
        ShowMsg.showToast(GongyiDetailActivity.this, result.message);
        return true;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{{"wid", id}, {"price", num}});
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_gongyi);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    mHelper = new XUtilsHelper(this, NetworkTools.API_WELFARE_INFO, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        mHelper.hideProgress();
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          WelfareDetailResult result = (WelfareDetailResult) msg.obj;
          showDetailInfo(result);
        }
        return true;
      }
    }));
    mHelper.setRequestParams(new String[][]{{"id", id}});
    mHelper.sendPostAuto(WelfareDetailResult.class);
    mHelper.showProgress("努力加载中...");
  }

  private void showDetailInfo(WelfareDetailResult result) {
    activityGongyiAdvXuanchuantu.setImageURI(Uri.parse(Endpoint.HOST + result.data.litpic));
    activityGongyiTvMubiao.setText(result.data.price);
    activityGongyiTvDangqian.setText(TextUtils.isEmpty(result.data.w_price) ? "0" : result.data.w_price);
    activityGongyiTvRenshu.setText(result.data.count);
    activityGongyiTvInfo.setText(result.data.info);
    activityGongyiTvDes.setText(result.data.description);
  }
}