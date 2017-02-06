
package me.kw.mall.activity;

import com.android.zcomponent.annotation.ZTitleMore;
import com.xiangying.fighting.R;

import me.kw.mall.fragment.SearchByNameFragment;
@ZTitleMore(false)
public class SearchActivity extends MallBaseActivity {
  private SearchByNameFragment mCagtegoryFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_search;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("分类");
    mCagtegoryFragment = new SearchByNameFragment();
    addFragment(R.id.flayout_category, mCagtegoryFragment);
  }

}