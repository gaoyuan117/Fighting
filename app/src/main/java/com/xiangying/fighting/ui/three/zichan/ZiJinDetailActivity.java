package com.xiangying.fighting.ui.three.zichan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.ZiJinDetailBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZiJinDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.tv_zjdetail_start)
    TextView mStart;
    @Bind(R.id.tv_zjdetail_end)
    TextView mEnd;
    @Bind(R.id.tv_zjdetail_search)
    TextView mSearch;
    @Bind(R.id.lv_zjdetail_show)
    ListView mListView;

    private String startTime, endTime, time, type;//开始时间，结束时间
    private List<ZiJinDetailBean.DataBean> mList;
    private ZiJinDetailAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_jin_detail);
        ButterKnife.bind(this);
        initView();

        initTitle();
        setTime();
        setListener();
        initData();
    }

    /**
     * 初始化
     */
    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new ZiJinDetailAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 获取数据
     */
    private void initData() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.ZJLS, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mList.clear();
                ZiJinDetailBean bean = (ZiJinDetailBean) msg.obj;
                Logger.d("code = ", bean.getCode());
                if (bean.getCode() == 1) {
                    mList.addAll(bean.getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ZiJinDetailActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyDataSetChanged();
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"stime", startTime}, {"etime", endTime}});
        helper.sendPostAuto(ZiJinDetailBean.class);
    }

    /**
     * 设置点击事件
     */
    private void setListener() {
        mSearch.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
    }

    /**
     * 设置时间
     */
    private void setTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(time);
        mStart.setText(date);
        mEnd.setText(date);

        startTime = date;
        endTime = date;
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonTvTitle.setText("资金流水");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zjdetail_start://开始时间
                showDateDialog();
                type = "start";
                break;
            case R.id.tv_zjdetail_end://结束时间
                showDateDialog();
                type = "end";
                break;

            case R.id.tv_zjdetail_search://搜索
                startTime = mStart.getText().toString().trim();
                endTime = mEnd.getText().toString().trim();
                initData();
                break;
        }
    }

    /**
     * 显示日期对话框
     */
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ZiJinDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year, int monthOfYear,
                                  int dayOfMonth) {
                String date = "";

                if (monthOfYear < 10) {
                    date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                if (dayOfMonth < 10) {
                    date = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                }
                if (monthOfYear < 10 && dayOfMonth < 10) {
                    date = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                }

                if (monthOfYear > 10 && dayOfMonth > 10) {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }

                if (type.equals("start")) {
                    mStart.setText(date);
                } else if (type.equals("end")) {
                    mEnd.setText(date);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();
    }

}
