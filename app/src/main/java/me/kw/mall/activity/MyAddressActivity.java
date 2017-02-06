
package me.kw.mall.activity;


import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.views.PullToRefreshView;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.adapter.MyAddressAdapter;
import me.kw.mall.constant.ResultActivity;
import me.kw.mall.dao.AddressDao;
import service.api.Address;
import service.entity.AddressService;

@ZTitleMore(false)
public class MyAddressActivity extends MallBaseActivity implements
    PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
  @Bind(R.id.common_listview_show) ListView mListView;
  @Bind(R.id.common_pull_refresh_view_show) PullToRefreshView mPullToRefreshView;

  private List<Address.Data> mlistAddress;
  private MyAddressAdapter mMyAddressAdapter;

  @Override protected int getLayoutId() {
    return R.layout.activity_my_address;
  }

  @Override protected void initUI() {
    mListView.setDividerHeight(0);
    mPullToRefreshView.setOnHeaderRefreshListener(this);
    mPullToRefreshView.setOnFooterRefreshListener(this);
    mPullToRefreshView.setFooterInvisible();
    getTitleBar().setTitleText("收货地址管理");
    AddressDao.sendCmdQueryMyAddress(getHttpDataLoader());
    getDataEmptyView().showViewWaiting();
  }

  @Override
  public void onDataEmptyClickRefresh() {
    AddressDao.sendCmdQueryMyAddress(getHttpDataLoader());
    getDataEmptyView().showViewWaiting();
  }

  private void clear() {
    if (null != mlistAddress) {
      mlistAddress.clear();
    }
    if (null != mMyAddressAdapter) {
      mMyAddressAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(AddressService.GetReceiverAddressRequest.class)) {
      Address response = msg.getRspObject();
      mPullToRefreshView.onHeaderRefreshComplete();
      if (null != response && response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
        if (null != response.Data && response.Data.length > 0) {
          //显示收货地址列表
          mlistAddress = new ArrayList<Address.Data>(Arrays.asList(response
              .Data));
          mMyAddressAdapter = new MyAddressAdapter(this, mlistAddress);
          mListView.setAdapter(mMyAddressAdapter);
          getDataEmptyView().setVisibility(View.GONE);
        } else {
          clear();
          getDataEmptyView().showViewDataEmpty(false, true, msg, R.string.personal_set_address);
        }
      } else {
        clear();
        getDataEmptyView().showViewDataEmpty(false, true, msg, R.string.personal_set_address);
      }
    }
  }

  /**
   * 新增收货地址
   */
  @OnClick(R.id.btn_submit_click)
  void onClickBtnSubmit() {
    getIntentHandle().intentToActivity(MyAddressEditActivity.class);
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (resultCode == ResultActivity.CODE_ADDRESS_ADD_SUCCESS
        || resultCode == ResultActivity.CODE_ADDRESS_DEL_SUCCESS) {
      mPullToRefreshView.headerRefreshing();
      AddressDao.sendCmdQueryMyAddress(getHttpDataLoader());
    }
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {}

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    AddressDao.sendCmdQueryMyAddress(getHttpDataLoader());
  }
}