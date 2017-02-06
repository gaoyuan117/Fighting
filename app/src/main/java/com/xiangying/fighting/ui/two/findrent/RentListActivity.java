package com.xiangying.fighting.ui.two.findrent;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.MyRentBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RentListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_rent_list)
    ListView mListView;
    @Bind(R.id.img_rent_list_add)
    ImageView imgRentListAdd;
    @Bind(R.id.img_rent_list_back)
    ImageView imgRentListBack;

    private XUtilsHelper mHelper;
    private MyRentAdapter mRentAdapter;
    private List<MyRentBean.Data> mList;


    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        imgRentListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgRentListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentListActivity.this, NewRentActivity.class));
            }
        });

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_rent_list);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        mRentAdapter = new MyRentAdapter(this,mList);
        mListView.setAdapter(mRentAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void loadNetData() {
        loadMyRent();
    }

    private void loadMyRent() {
        mHelper = new XUtilsHelper(this, NetworkTools.RENT_MY_RENT, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    MyRentBean myRentBean = (MyRentBean) msg.obj;
                    if (myRentBean.getCode() == 1) {
                        mList.clear();
                        mList.addAll(myRentBean.getData());
                        mRentAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        mHelper.showProgress("加载中");
        mHelper.setRequestParams(new String[][]{});
        mHelper.sendPostAuto(MyRentBean.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyRentBean.Data data = mList.get(position);
        Intent intent = new Intent(this,NewRentActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("type","my");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMyRent();
    }
}
