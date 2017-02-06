package com.xiangying.fighting.ui.two.findjob;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.RecomentWorkBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JobCategoryActivity extends BaseActivity {
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;

  CommonAdapter<RecomentWorkBean.Data> adaper;
  ArrayList<RecomentWorkBean.Data> mDatas = new ArrayList<>();

  @Override protected void process() {
    //为listView设置adapter
    adaper = new CommonAdapter<RecomentWorkBean.Data>(this, mDatas, R.layout.item_tuijian_zhiwei) {
      @Override
      public void convert(ViewHolder helper, RecomentWorkBean.Data item, int position) {
        helper.setText(R.id.tv_recom, item.getName());
        if (item.getList() == null) {
          item.setList(new ArrayList<RecomentWorkBean.Data.ListBean>());
        }
        CommonAdapter<RecomentWorkBean.Data.ListBean> adapter2 = new CommonAdapter<RecomentWorkBean.Data.ListBean>(JobCategoryActivity.this, (ArrayList<RecomentWorkBean.Data.ListBean>) item.getList(), R.layout.item_quyu) {
          @Override
          public void convert(ViewHolder helper, final RecomentWorkBean.Data.ListBean item, int position) {
            helper.setText(R.id.item_quyu_tvquyu, item.getName());
            helper.setItemClick(new View.OnClickListener() {
              @Override public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("data", item.getName()));
                finish();
              }
            });
          }
        };
        helper.setCommonAdapter(R.id.item_zhiwei_gridview, adapter2);
//        helper.setGridViewHeight(R.id.item_zhiwei_gridview);
      }
    };
    commonListviewShow.setAdapter(adaper);
  }

  @Override protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @Override protected void initViews() {

  }

  @Override protected void loadLayout() {
    setContentView(R.layout.activity_job_category);
    ButterKnife.bind(this);
  }

  @Override protected void loadNetData() {
    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_HOTS_RECOMENT, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        RecomentWorkBean recomentWorkBean = (RecomentWorkBean) msg.obj;
        if (recomentWorkBean.getCode() == 1 && recomentWorkBean.getData().size() > 0) {
          adaper.setmDatas((ArrayList<RecomentWorkBean.Data>) recomentWorkBean.getData());
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{});
    xUtilsHelper.sendPostAuto(RecomentWorkBean.class);
  }
}
