package me.kw.mall.fragment;

import android.widget.GridView;

import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import me.kw.mall.adapter.CategoryRecomAdapter;
import service.api.AdProduct;

import static java.util.Arrays.asList;

public class AdCategoryFragment extends MallBaseFragment {

  @Bind(R.id.gvew_recommend_show) GridView mGridView;

  private CategoryRecomAdapter mAdapter;
  private List<AdProduct.AdItem> mAdItems = new ArrayList<>();

  @Override protected int getLayoutId() {
    return R.layout.fragment_ad_category;
  }

  @Override protected void initUI() {
    mAdapter = new CategoryRecomAdapter(getActivity(), mAdItems);
    mGridView.setAdapter(mAdapter);
  }

  public void refreshData(AdProduct.AdItem[] adItems) {
    if (adItems == null) {
      return;
    }

    if (adItems.length == 0) {
      this.removeData();
      return;
    }

    mAdItems.clear();
    mAdItems.addAll(Arrays.asList(adItems));

    if (null == mAdapter) {
      mAdapter = new CategoryRecomAdapter(getActivity(), mAdItems);
      mGridView.setAdapter(mAdapter);
    } else {
      mAdapter.setData(mAdItems);
    }
  }

  public void addData(AdProduct.AdItem[] adItems) {
    if (adItems == null) {
      return;
    }
    if (adItems.length == 0) {
      return;
    }
    if (null == mAdapter) {
      mAdapter = new CategoryRecomAdapter(getActivity(), mAdItems);
      mGridView.setAdapter(mAdapter);
    } else {
      mAdapter.addData(asList(adItems));
    }
  }

  public void removeData() {
    mAdapter.clear();
  }

  public void showWaitingDialog(boolean isShow) {
    if (isShow) {
      showWaitDialog(2, false, "正在查询", false);
    } else {
      dismissWaitDialog();
    }
  }

  public boolean isWaitingDialogShow() {
    return isDialogShowing();
  }
}
