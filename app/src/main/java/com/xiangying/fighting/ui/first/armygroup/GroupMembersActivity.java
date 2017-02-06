package com.xiangying.fighting.ui.first.armygroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.armyuser.FriendActivity;
import com.xiangying.fighting.ui.first.talks.GroupUserAdapter;
import com.xiangying.fighting.ui.first.talks.GroupUsers;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupMembersActivity extends BaseActivity {

    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.lv_group)
    ListView lvGroup;
    private String groupId;
    private ArrayList<GroupUsers.DataBean> mList;
    private GroupUserAdapter mAdapter;
    private XUtilsHelper helper;
    private ProgressDialog dialog;

    @Override
    protected void process() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.setMessage("加载中..");
    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GroupMembersActivity.this, FriendActivity.class);
                intent.putExtra("userName", mList.get(position).getUsername());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        groupId = getIntent().getStringExtra("groupId");
        mList = new ArrayList<>();
        mAdapter = new GroupUserAdapter(this, mList);
        lvGroup.setAdapter(mAdapter);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_group_members);
        ButterKnife.bind(this);
        dialog =new ProgressDialog(this);
        dialog.setMessage("加载中..");
    }

    @Override
    protected void loadNetData() {
        getGropList();
    }

    /**
     * 获取群好友
     */
    private void getGropList() {
        helper = new XUtilsHelper<>(getApplication(), NetworkTools.HX_GET_GROUP_USER, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                GroupUsers groupUsers = (GroupUsers) msg.obj;
                mList.clear();
                dialog.dismiss();
                mList.addAll(groupUsers.getData());
                mAdapter.notifyDataSetChanged();
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"group_id", groupId + ""}});
        helper.sendPostAuto(GroupUsers.class);
    }

}
