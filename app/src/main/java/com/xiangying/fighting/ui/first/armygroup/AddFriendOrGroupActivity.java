package com.xiangying.fighting.ui.first.armygroup;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.PagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddFriendOrGroupActivity extends BaseActivity {

  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.activity_addfriend_tablayout)
  TabLayout activityAddfriendTablayout;
  @Bind(R.id.activity_addfriend_viewpager)
  ViewPager activityAddfriendViewpager;

  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void initViews() {
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    ArrayList<String> titles = new ArrayList<String>();
    fragments.add(FragmentSearchFriend.newInstance("friend"));
    fragments.add(FragmentSearchFriend.newInstance("group"));
    titles.add("找战友");
    titles.add("找军团");
    PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments, titles);
    activityAddfriendViewpager.setAdapter(adapter);
    activityAddfriendTablayout.setupWithViewPager(activityAddfriendViewpager);
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_add_friend_or_group);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }
}
