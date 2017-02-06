package com.xiangying.fighting.ui.two.jiehun;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.zcomponent.util.ShowMsg;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.MerryIndex;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.utils.JsonParse;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiaoniao on 2016/7/21.
 */
public class LaoGongFragment extends BaseFragment
    implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {


  @Bind(R.id.fragment_laopo_gridview)
  GridView fragment_laopo_gridview;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshView common_pull_refresh_view_show;


  public static Fragment newInstance() {
    Fragment fragment = new LaoGongFragment();
    return fragment;
  }

  private XUtilsHelper xHelper;
  private String[][] requestParams;
  private MerryIndex mMerryIndex;
  private CommonAdapter<MerryIndex.Data> mAdapter;

  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      xHelper.hideProgress();
      switch (msg.what) {
        case XUtilsHelper.TAG_SUCCESS:
//          Utils.showHintByMsg(getActivity(), msg.obj.toString());
          try {
            JSONObject object = new JSONObject(msg.obj.toString());
            if (object.optInt("code") == 1) {
              if (common_pull_refresh_view_show.isHeaderRefresh()) {
                common_pull_refresh_view_show.onHeaderRefreshComplete();
              }
              parseJsonData(msg.obj.toString());
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
          break;
        case XUtilsHelper.TAG_FAILURE:
          Utils.toast(getActivity(), "获取失败，请稍后再试");
          break;
      }
      return false;
    }
  });

  private void parseJsonData(String s) {
    final MerryIndex merryIndex = JsonParse.parseCommonInfo(s, MerryIndex.class);
    if (merryIndex == null || merryIndex.data.size() == 0) {
      ShowMsg.showToast(mActivity, getResources().getString(R.string.msg_no_data));
      return;
    }
    mAdapter = new CommonAdapter<MerryIndex.Data>(getActivity(), merryIndex.data, R.layout.item_laopo) {
      @Override public void convert(ViewHolder helper, MerryIndex.Data item, int position) {
        helper.setSimpleDraweeViewByUrl(R.id.item_laopo_img, item.head);
        helper.setText(R.id.item_laopo_name, item.name);
        helper.setText(R.id.item_laopo_age, item.zo_age);
      }
    };
    fragment_laopo_gridview.setAdapter(mAdapter);
    fragment_laopo_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
        intent.putExtra("id", merryIndex.data.get(position).id);
        intent.putExtra("data", merryIndex.data.get(position));
        startActivity(intent);
      }
    });
  }

  @Override
  protected void process() {
    requestParams = new String[1][2];
    requestParams[0][0] = "sex";
    xHelper = new XUtilsHelper(getActivity(), NetworkTools.API_MERRY_INDEX, mHandler);
    requestParams[0][1] = "1";
    xHelper.setRequestParams(requestParams);
  }


  @Override
  protected void setAllClick() {
    common_pull_refresh_view_show.setOnFooterRefreshListener(this);
    common_pull_refresh_view_show.setOnHeaderRefreshListener(this);
    common_pull_refresh_view_show.setFooterInvisible();
  }

  @Override
  protected void initViews(View view) {

  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.fragment_laopo, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void loadNetData() {
    xHelper.sendPost();
    xHelper.showProgress("正在加载...");
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {

  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    loadNetData();
  }
}
