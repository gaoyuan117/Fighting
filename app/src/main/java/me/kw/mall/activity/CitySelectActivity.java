
package me.kw.mall.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.views.PullToRefreshView;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import me.kw.mall.adapter.CitySelectAdapter;
import me.kw.mall.constant.ProviderResultActivity;
import me.kw.mall.dao.ProviderAddress;
import service.api.Address;
import service.api.Area;
import service.api.City;
import service.api.Province;
import service.entity.AddressService;

public class CitySelectActivity extends MallBaseActivity implements
    PullToRefreshView.OnHeaderRefreshListener,
    PullToRefreshView.OnFooterRefreshListener {
  @Bind(R.id.common_listview_show) ListView mListView;
  @Bind(R.id.common_pull_refresh_view_show) PullToRefreshView mPullToRefreshView;

  private List<Province.Data> mlistProvince;
  private List<City.Data> mlistCity;
  private List<Area.Data> mlistArea;
  private int miProvince;
  private int miCity;
  private Type mType = Type.PROVINCE;

  private enum Type {
    PROVINCE, CITY, AREA;
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_city_select;
  }

  @Override protected void initUI() {
    mPullToRefreshView.setOnHeaderRefreshListener(this);
    mPullToRefreshView.setOnFooterRefreshListener(this);
    mPullToRefreshView.setFooterInvisible();
    mPullToRefreshView.setHeaderInvisible();

    getTitleBar().setTitleText("选择城市");
    getDataEmptyView().showViewWaiting();
    ProviderAddress.sendCmdQueryProvince(getHttpDataLoader());
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(AddressService.GetProvincesRequest.class)) {
      Province response = (Province) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          mlistProvince = new ArrayList<Province.Data>(Arrays.asList(response.Data));
          mListView.setAdapter(new CitySelectAdapter(this, mlistProvince));
          getDataEmptyView().setVisibility(View.GONE);
        } else {
          ShowMsg.showToast(getApplicationContext(), response.message);
        }
      } else {
        getDataEmptyView().showViewDataEmpty(true, false, msg, R.string.address_province_empty);
      }
    } else if (msg.valiateReq(AddressService.GetCitiesRequest.class)) {
      City response = (City) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          mType = Type.CITY;
          mlistCity = new ArrayList<City.Data>(Arrays.asList(response.Data));
          mListView.setAdapter(new CitySelectAdapter(this, mlistCity));
        } else {
          ShowMsg.showToast(getApplicationContext(), response.message);
        }
      } else {
        ShowMsg.showToast(getApplicationContext(), msg, R.string.address_province_empty);
      }
    } else if (msg.valiateReq(AddressService.GetAreasRequest.class)) {
      Area response = (Area) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          mType = Type.AREA;
          mlistArea = new ArrayList<Area.Data>(Arrays.asList(response.Data));
          mListView.setAdapter(new CitySelectAdapter(this, mlistArea));
        } else {
          ShowMsg.showToast(getApplicationContext(), response.message);
        }
      } else {
        ShowMsg.showToast(getApplicationContext(), msg, R.string.address_area_empty);
      }
    }
  }

  @OnItemClick(R.id.common_listview_show)
  void onItemClick(int position) {
    if (mType == Type.PROVINCE) {
      miProvince = position;
      ProviderAddress.sendCmdQueryCity(getHttpDataLoader(), mlistProvince.get(position).province_id);
    } else if (mType == Type.CITY) {
      miCity = position;
      ProviderAddress.sendCmdQueryArea(getHttpDataLoader(), mlistCity.get(position).city_id);
    } else if (mType == Type.AREA) {
      Address.Data address = new Address.Data();
      address.province_id = mlistProvince.get(miProvince).province_id;
      address.province = mlistProvince.get(miProvince).province;
      address.city_id = mlistCity.get(miCity).city_id;
      address.city = mlistCity.get(miCity).city;
      address.area_id = mlistArea.get(position).area_id;
      address.area = mlistArea.get(position).area;

      Intent intent = new Intent();
      intent.putExtra("address", JsonSerializerFactory.Create().encode(address));
      FramewrokApplication.getInstance().setActivityResult(ProviderResultActivity.CODE_CITY_SELECT, intent);
      finish();
    }
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {
  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {

  }
}