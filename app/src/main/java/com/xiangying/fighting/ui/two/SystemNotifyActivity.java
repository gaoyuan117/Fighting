package com.xiangying.fighting.ui.two;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.PagerAdapter;
import com.xiangying.fighting.ui.first.armygroup.FragmentSearchFriend;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SystemNotifyActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView mTitleBarCommonIvOperate1;
  @Bind(R.id.activity_notify_tablayout)
  TabLayout mActivityNotifyTablayout;
  @Bind(R.id.activity_notify_viewpager)
  ViewPager mActivityNotifyViewpager;


  @Override protected void process() {
  }

  @Override protected void setAllClick() {
    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initViews() {
    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> titles=new ArrayList<>();
    fragments.add(FragmentSearchFriend.newInstance("friend"));
    fragments.add(FragmentSearchFriend.newInstance("group"));
    titles.add("未处理");
    titles.add("已处理");
    PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),fragments,titles);
    mActivityNotifyViewpager.setAdapter(adapter);
    mActivityNotifyTablayout.setupWithViewPager(mActivityNotifyViewpager);
  }

  @Override protected void loadLayout() {
    setContentView(R.layout.activity_system_notift);
    ButterKnife.bind(this);
  }

  @Override protected void loadNetData() {

  }
}
