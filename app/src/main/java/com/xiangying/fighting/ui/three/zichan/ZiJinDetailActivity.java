package com.xiangying.fighting.ui.three.zichan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.ZiJinDetailBean;
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
    private List<ZiJinDetailBean> mList;
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
        for (int i = 0; i < 10; i++) {
            ZiJinDetailBean bean = new ZiJinDetailBean();
            bean.time = "2017年-01月-17日";
            bean.source = "转账";
            bean.money = "100元";
            mList.add(bean);
        }
        mAdapter.notifyDataSetChanged();
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
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

                //TODO 请求数据
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
                if (type.equals("start")) {
                    mStart.setText(year + "年-" + (monthOfYear + 1) + "月-" + dayOfMonth + "日");
                } else if (type.equals("end")) {
                    mEnd.setText(year + "年-" + (monthOfYear + 1) + "月-" + dayOfMonth + "日");
                }
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();
    }

}
