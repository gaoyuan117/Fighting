package com.xiangying.fighting.ui.two.findjob;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.ui.two.findjob.bean.JobDetailBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiaoniao on 2016/8/29.
 */
public class CompanyProfileFragment extends BaseFragment {
  static String id = "";
  @Bind(R.id.fragment_jianjie_tv_company)
  FontTextView fragmentJianjieTvCompany;
  @Bind(R.id.fragment_jianjie_tv_linkman)
  FontTextView fragmentJianjieTvLinkman;
  @Bind(R.id.fragment_jianjie_tv_phone)
  FontTextView fragmentJianjieTvPhone;
  @Bind(R.id.fragment_jianjie_tv_company_tel)
  FontTextView fragmentJianjieTvCompanyTel;
  @Bind(R.id.fragment_jianjie_tv_address)
  FontTextView fragmentJianjieTvAddress;
  @Bind(R.id.fragment_jianjie_tv_detail)
  FontTextView fragmentJianjieTvDetail;
  private XUtilsHelper mHelper;

  public static Fragment newInstance(String id) {
    CompanyProfileFragment.id = id;
    Fragment fragment = new CompanyProfileFragment();
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
    View view = inflater.inflate(R.layout.fragment_jianjie, null);
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
          fragmentJianjieTvCompany.setText(dataBean.getCompany());
          fragmentJianjieTvLinkman.setText("联系人：" + dataBean.getName());
          fragmentJianjieTvPhone.setText("联系电话：" + dataBean.getPhone());
          fragmentJianjieTvCompanyTel.setText("公司电话：" + dataBean.getCriteria());
          fragmentJianjieTvAddress.setText("公司地址：" + dataBean.getSite());
          fragmentJianjieTvDetail.setText(dataBean.getInfo());
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
