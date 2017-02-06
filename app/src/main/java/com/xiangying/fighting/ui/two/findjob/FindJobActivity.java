package com.xiangying.fighting.ui.two.findjob;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.CompanyBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.company.CompanyInfoActivity;
import com.xiangying.fighting.ui.two.findjob.bean.JobListBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindJobActivity extends BaseActivity
        implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_find_job_et_search)
    ClearEditText activityFindJobEtSearch;
    @Bind(R.id.common_listview_show)
    ListView commonListviewShow;
    @Bind(R.id.common_pull_refresh_view_show)
    PullToRefreshView commonPullRefreshViewShow;
    @Bind(R.id.activity_find_job_ll_search)
    LinearLayout activityFindJobLlSearch;
    @Bind(R.id.activity_FJ_liaojie)
    FontTextView activityFJLiaojie;
    @Bind(R.id.activity_FJ_tv_add)
    FontTextView activityFJTvAdd;

    CommonAdapter<JobListBean.Data> adaper;
    ArrayList<JobListBean.Data> dataList = new ArrayList<>();

    XUtilsHelper helper = null;

    @Override
    protected void process() {


        adaper = new CommonAdapter<JobListBean.Data>(this, dataList, R.layout.item_job) {
            @Override
            public void convert(ViewHolder helper, final JobListBean.Data item, int position) {
                helper.setText(R.id.item_job_tv_position, item.getWork());
                helper.setText(R.id.item_job_tv_company, item.getCompany());
                helper.setText(R.id.item_job_tv_location, item.getWorkplace());
                helper.setText(R.id.item_job_tv_salary, item.getTreatment().replace(",", "-"));
                helper.setText(R.id.item_job_tv_date, item.getTime());
                helper.setItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JobDetailActivity.start(FindJobActivity.this, item.getId());
                    }
                });
            }
        };

        View headerTextView = LayoutInflater.from(this).inflate(R.layout.layout_header_find_job, null);
        commonListviewShow.addHeaderView(headerTextView);

        commonListviewShow.setAdapter(adaper);

        //刷新控件设置监听
        commonPullRefreshViewShow.setOnHeaderRefreshListener(this);
        commonPullRefreshViewShow.setOnFooterRefreshListener(this);

        //首次刷新
        commonPullRefreshViewShow.headerRefreshing();

        //首次请求数据
        requestData();
    }


    /**
     * 请求数据
     */
    private void requestData() {
        helper = new XUtilsHelper(this, NetworkTools.FIND_JOB_INDEX, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                commonPullRefreshViewShow.onHeaderRefreshComplete();
                commonPullRefreshViewShow.onFooterRefreshComplete();
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    JobListBean jobListBean = (JobListBean) msg.obj;
                    adaper.setmDatas(jobListBean.getData());
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"word", ""}});
        helper.sendPostAuto(JobListBean.class);
    }

    @Override
    protected void setAllClick() {
        activityFindJobLlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindJobActivity.this, SearchJobActivity.class));
            }
        });

        //创建工作
        activityFJTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeIsCompany();
            }
        });
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("找工作");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_find_job);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        requestData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        requestData();
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
                        PublishNewJobActivity.start(FindJobActivity.this, bean.getData().getId());
                    } else {
                        startActivity(new Intent(FindJobActivity.this, CompanyInfoActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    startActivity(new Intent(FindJobActivity.this, CompanyInfoActivity.class));
                }

                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(CompanyBean.class);
    }


}