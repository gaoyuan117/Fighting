package com.xiangying.fighting.ui.two.findjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JobDetailActivity extends BaseActivity {
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
  @Bind(R.id.activity_JD_gangwei)
  FontTextView activityJDGangwei;
  @Bind(R.id.activity_JD_gangwei_gap)
  FontTextView activityJDGangweiGap;
  @Bind(R.id.activity_JD_gangwei_ll)
  LinearLayout activityJDGangweiLl;
  @Bind(R.id.activity_JD_gongsi)
  FontTextView activityJDGongsi;
  @Bind(R.id.activity_JD_gongsi_gap)
  FontTextView activityJDGongsiGap;
  @Bind(R.id.activity_JD_gongsi_ll)
  LinearLayout activityJDGongsiLl;
  @Bind(R.id.activity_JD_content)
  FrameLayout activityJDContent;
  @Bind(R.id.activity_JD_liaojie)
  FontTextView activityJDLiaojie;
  @Bind(R.id.activity_JD_fangdong_number)
  FontTextView activityJDFangdongNumber;
  @Bind(R.id.activity_JD_tv_add)
  FontTextView activityJDTvAdd;

  DutyFragment dutyFragment;
  CompanyProfileFragment companyProfileFragment;

  FragmentManager fragmentManager;
  private FragmentTransaction transaction;

  public static void start(Context context, String id) {
    Intent intent = new Intent(context, JobDetailActivity.class);
    intent.putExtra("id", id);
    context.startActivity(intent);
  }

  @Override
  protected void process() {
  }

  @Override
  protected void setAllClick() {

    //两个选项卡点击坚挺
    activityJDGangweiLl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setFragmet(1);
      }
    });

    activityJDGongsiLl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setFragmet(2);
      }
    });

  }

  @Override
  protected void initViews() {


    fragmentManager = getSupportFragmentManager();
    setFragmet(1);
    titleBarCommonTvTitle.setText("职位详情");
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
    setContentView(R.layout.activity_job_detail);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
  }

  /**
   * 清除按钮状态
   */
  public void clearState() {
    activityJDGangweiGap.setBackgroundColor(Color.parseColor("#ffffff"));
    activityJDGongsiGap.setBackgroundColor(Color.parseColor("#ffffff"));
    activityJDGangwei.setTextColor(Color.parseColor("#000000"));
    activityJDGongsi.setTextColor(Color.parseColor("#000000"));
  }

  /**
   * 影藏所有Fragment，避免混淆
   */
  public void hideAllFragment(FragmentTransaction transaction) {
    if (dutyFragment != null) {
      transaction.hide(dutyFragment);
    }

    if (companyProfileFragment != null) {
      transaction.hide(companyProfileFragment);
    }
  }


  /**
   * 按照选择choice设置Fragmen的跳转
   */
  public void setFragmet(int choice) {
    transaction = fragmentManager.beginTransaction();
    clearState();
    hideAllFragment(transaction);
    switch (choice) {
      case 1:
        activityJDGangweiGap.setBackgroundColor(Color.parseColor("#B80012"));
        activityJDGangwei.setTextColor(Color.parseColor("#B80012"));

        if (dutyFragment == null) {
          dutyFragment = (DutyFragment) DutyFragment.newInstance(getIntent().getStringExtra("id"));
          transaction.add(R.id.activity_JD_content, dutyFragment);
        } else
          transaction.show(dutyFragment);
        break;
      case 2:
        activityJDGongsiGap.setBackgroundColor(Color.parseColor("#B80012"));
        activityJDGongsi.setTextColor(Color.parseColor("#B80012"));

        if (companyProfileFragment == null) {
          companyProfileFragment = (CompanyProfileFragment) CompanyProfileFragment.newInstance(getIntent().getStringExtra("id"));
          transaction.add(R.id.activity_JD_content, companyProfileFragment);
        } else {
          transaction.show(companyProfileFragment);
//        twoFragment.ReLoadMessage(); // 重新请求一次消息
        }

        break;
    }
    transaction.commit();
  }
}
