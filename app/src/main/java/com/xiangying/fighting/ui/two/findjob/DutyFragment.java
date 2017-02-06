package com.xiangying.fighting.ui.two.findjob;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.two.findjob.bean.JobDetailBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiaoniao on 2016/8/29.
 */
public class DutyFragment extends BaseFragment {
  @Bind(R.id.fragment_gangwei_tv_position)
  FontTextView fragmentGangweiTvPosition;
  @Bind(R.id.fragment_gangwei_tv_money)
  FontTextView fragmentGangweiTvMoney;
  @Bind(R.id.fragment_gangwei_tv_location)
  FontTextView fragmentGangweiTvLocation;
  @Bind(R.id.fragment_gangwei_tv_salary)
  FontTextView fragmentGangweiTvSalary;
  @Bind(R.id.fragment_gangwei_tv_date)
  FontTextView fragmentGangweiTvDate;
  @Bind(R.id.fragment_gangwei_tv_zhize)
  FontTextView fragmentGangweiTvZhize;
  @Bind(R.id.fragment_gangwei_tv_tiaojian)
  FontTextView fragmentGangweiTvTiaojian;

  static String id = "";
  private XUtilsHelper mHelper;

  public static Fragment newInstance(String id) {
    DutyFragment.id = id;
    Fragment fragment = new DutyFragment();
    return fragment;
  }

  @Override
  protected void process() {

  }

  @Override
  protected void setAllClick() {

  }

  @Override
  protected void initViews(View view) {

  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.fragment_gangwei, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void loadNetData() {
    mHelper = new XUtilsHelper(getActivity(), NetworkTools.JOB_DETAIL, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        mHelper.hideProgress();
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          JobDetailBean jobDetailBean = (JobDetailBean) msg.obj;
          JobDetailBean.Data dataBean = jobDetailBean.getData();

          fragmentGangweiTvPosition.setText(dataBean.getWork());
          fragmentGangweiTvMoney.setText(dataBean.getTreatment());
          fragmentGangweiTvLocation.setText(dataBean.getWorkplace());
          fragmentGangweiTvDate.setText(dataBean.getTime());
          fragmentGangweiTvZhize.setText(dataBean.getObligation());
          fragmentGangweiTvTiaojian.setText(dataBean.getCriteria());
        }
        return false;
      }
    }));
    mHelper.setRequestParams(new String[][]{{"id", id}});
    mHelper.sendPostAuto(JobDetailBean.class);
    mHelper.showProgress("加载中...");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}