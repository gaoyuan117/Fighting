package com.xiangying.fighting.ui.two.jiehun;

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

import static com.xiangying.fighting.R.id.activity_marry_wanshan;

public class  MarryActivity extends BaseActivity {
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
  @Bind(R.id.activity_marry_laopo)
  FontTextView activityMarryLaopo;
  @Bind(R.id.activity_marry_laopo_gap)
  FontTextView activityMarryLaopoGap;
  @Bind(R.id.activity_marry_laopo_ll)
  LinearLayout activityMarryLaopoLl;
  @Bind(R.id.activity_marry_laogong)
  FontTextView activityMarryLaogong;
  @Bind(R.id.activity_marry_laogong_gap)
  FontTextView activityMarryLaogongGap;
  @Bind(R.id.activity_marry_laogong_ll)
  LinearLayout activityMarryLaogongLl;
  @Bind(R.id.activity_marry_content)
  FrameLayout activityMarryContent;

  LaopoFragment laopoFragment;
  LaoGongFragment laoGongFragment;

  FragmentManager fragmentManager;
  @Bind(R.id.activity_marry_liaojie)
  FontTextView activityMarryLiaojie;
  @Bind(activity_marry_wanshan)
  FontTextView activityMarryWanshan;
  private FragmentTransaction transaction;


  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {
    //两个选项卡点击坚挺
    activityMarryLaopoLl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setFragmet(1);
      }
    });
    activityMarryLaogongLl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setFragmet(2);
      }
    });
    titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MarryActivity.this, MarrySearchActivity.class));
      }
    });
    //完善资料
    activityMarryWanshan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MarryActivity.this, WanshanActivity.class));
      }
    });

    activityMarryWanshan.setVisibility(View.VISIBLE);
  }

  @Override
  protected void initViews() {
    fragmentManager = getSupportFragmentManager();
    //默认加在预售商品界面
    setFragmet(1);

    titleBarCommonTvTitle.setText("结婚吧");
    titleBarCommonIvOperate3.setImageResource(R.drawable.search_white);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_marry);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {

  }


  /**
   * 清除按钮状态
   */
  public void clearState() {
    activityMarryLaopoGap.setBackgroundColor(Color.parseColor("#ffffff"));
    activityMarryLaogongGap.setBackgroundColor(Color.parseColor("#ffffff"));
    activityMarryLaopo.setTextColor(Color.parseColor("#000000"));
    activityMarryLaogong.setTextColor(Color.parseColor("#000000"));

  }

  /**
   * 影藏所有Fragment，避免混淆
   */
  public void hideAllFragment(FragmentTransaction transaction) {

    if (laopoFragment != null) {
      transaction.hide(laopoFragment);
    }

    if (laoGongFragment != null) {
      transaction.hide(laoGongFragment);
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
        activityMarryLaopoGap.setBackgroundColor(Color.parseColor("#B80012"));
        activityMarryLaopo.setTextColor(Color.parseColor("#B80012"));

        if (laopoFragment == null) {
          laopoFragment = (LaopoFragment) LaopoFragment.newInstance();
          transaction.add(R.id.activity_marry_content, laopoFragment);
        } else
          transaction.show(laopoFragment);
        break;
      case 2:
        activityMarryLaogongGap.setBackgroundColor(Color.parseColor("#B80012"));
        activityMarryLaogong.setTextColor(Color.parseColor("#B80012"));

        if (laoGongFragment == null) {
          laoGongFragment = (LaoGongFragment) LaoGongFragment.newInstance();
          transaction.add(R.id.activity_marry_content, laoGongFragment);
        } else {
          transaction.show(laoGongFragment);
        }

        break;
    }
    transaction.commit();
  }

}
