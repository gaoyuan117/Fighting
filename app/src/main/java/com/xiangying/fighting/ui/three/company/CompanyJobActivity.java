package com.xiangying.fighting.ui.three.company;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.CompanyBean;
import com.xiangying.fighting.bean.MyWorkBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.two.findjob.PublishNewJobActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class CompanyJobActivity extends BaseActivity {
    @Bind(R.id.list_item)
    ListView mListItem;
    @Bind(R.id.activity_FJ_tv_add)
    FontTextView mFJTvAdd;
    private ArrayList<MyWorkBean.Data> listData = new ArrayList<>();
    private CommonAdapter<MyWorkBean.Data> adapter;

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        findViewById(R.id.title_bar_common_iv_operate_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFJTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeIsCompany();
            }
        });
    }

    @Override
    protected void initViews() {
        adapter = new CommonAdapter<MyWorkBean.Data>(CompanyJobActivity.this, listData, R.layout.item_my_work) {
            @Override
            public void convert(ViewHolder helper, final MyWorkBean.Data item, int position) {
                helper.setText(R.id.tb_job_title, item.getWork());
                helper.setText(R.id.tb_job_company, item.getCompany());
                helper.setText(R.id.tb_job_address, item.getAddress());
                helper.setText(R.id.tb_job_treament, item.getTreatment());

                helper.setViewClick(R.id.tv_job_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editJob(item);
                    }
                });

                helper.setViewClick(R.id.tv_job_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteJob(item);
                    }
                });

            }
        };
        mListItem.setAdapter(adapter);
    }

    private void editJob(MyWorkBean.Data item) {
        PublishNewJobActivity.start(this, item.getId(), item.getCid());
    }

    private void deleteJob(final MyWorkBean.Data item) {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.DELETE_WORK, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn commonReturn = (CommonReturn) msg.obj;
                    if (commonReturn.code == 1) {
                        Utils.toast(CompanyJobActivity.this, "删除成功");
                        adapter.deleteSingle(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"id", item.getId()}});
        xUtilsHelper.sendPostAuto(CommonReturn.class);

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_company_job);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_WORK_MYWORD, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    MyWorkBean myWorkBean = (MyWorkBean) msg.obj;
                    if (myWorkBean.getCode() == 1 && myWorkBean.getData().size() > 0) {
                        adapter.setmDatas(myWorkBean.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

        }));
        xUtilsHelper.setRequestParams(new String[][]{});
        xUtilsHelper.sendPostAuto(MyWorkBean.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 判断是否是企业
     */
    public void judgeIsCompany() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.JOB_MY_COMPANY, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CompanyBean bean = (CompanyBean) msg.obj;
                    if (bean.getCode() == 1) {
                        PublishNewJobActivity.start(CompanyJobActivity.this, bean.getData().getId());
                    } else {
                        startActivity(new Intent(CompanyJobActivity.this, CompanyInfoActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    startActivity(new Intent(CompanyJobActivity.this, CompanyInfoActivity.class));
                }

                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(CompanyBean.class);
    }
}
