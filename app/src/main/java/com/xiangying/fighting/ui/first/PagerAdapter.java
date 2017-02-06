package com.xiangying.fighting.ui.first;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
  private ArrayList<Fragment> fragments = new ArrayList<>();
  private ArrayList<String> tags = new ArrayList<>();

  public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tags) {
    super(fm);
    this.fragments = fragments;
    this.tags = tags;
  }

  public void addData(Fragment fragment,String title) {
    this.fragments.add(fragment);
    this.tags.add(title);
    notifyDataSetChanged();
  }

  public void addData(ArrayList<Fragment> fragments,ArrayList<String> tags) {
    this.fragments = fragments;
    this.tags = tags;
    notifyDataSetChanged();
  }

  public void clear() {
    this.fragments.clear();
  }


  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
  }

  @Override
  public Fragment getItem(int position) {
    getPageTitle(position);
    return fragments.get(position);

  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return tags.get(position);
  }
}
