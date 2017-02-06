package com.xiangying.fighting.ui.first.armygroup;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.talks.GroupUsers;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class InviteAddGroupActivity extends BaseActivity {
    public final static int INVITESUCUSS = 1;
    public final static int INVITEFAILED = 2;
    @Bind(R.id.invite_add_group_tv_cancle)
    TextView inviteAddGroupTvCancle;
    @Bind(R.id.invite_toptile)
    TextView inviteToptile;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.invite_add_group_tv_ok)
    TextView inviteAddGroupTvOk;
    @Bind(R.id.id_top_bar)
    RelativeLayout idTopBar;
    @Bind(R.id.invite_addgroup_et_search)
    EditText inviteAddgroupEtSearch;
    @Bind(R.id.pull_icon)
    ImageView pullIcon;
    @Bind(R.id.refreshing_icon)
    ProgressBar refreshingIcon;
    @Bind(R.id.state_tv)
    TextView stateTv;
    @Bind(R.id.xlistview_header_time)
    TextView xlistviewHeaderTime;
    @Bind(R.id.state_iv)
    ImageView stateIv;
    @Bind(R.id.head_view)
    RelativeLayout headView;
    @Bind(R.id.common_listview_show)
    PullableListView commonListviewShow;
    @Bind(R.id.pullup_icon)
    ImageView pullupIcon;
    @Bind(R.id.loading_icon)
    ProgressBar loadingIcon;
    @Bind(R.id.loadstate_tv)
    TextView loadstateTv;
    @Bind(R.id.loadstate_iv)
    ImageView loadstateIv;
    @Bind(R.id.invite_liner_search)
    LinearLayout liner_srarch;
    @Bind(R.id.loadmore_view)
    RelativeLayout loadmoreView;
    @Bind(R.id.common_pull_refresh_view_show)
    PullToRefreshLayout commonPullRefreshViewShow;

    private String groupId;
    private ArrayList<GroupUsers.DataBean> checklist = new ArrayList<>();
    HashMap<Integer, Boolean> isCkecked = new HashMap<>();
    CommonAdapter<GroupUsers.DataBean> adaper;
    private XUtilsHelper helper;
    private ArrayList<GroupUsers.DataBean> mList;

    @Override
    protected void process() {

    }

    private void setAdaper() {
        adaper = new CommonAdapter<GroupUsers.DataBean>(this, mList, R.layout.invite_add_group_item) {
            @Override
            public void convert(final ViewHolder helper, final GroupUsers.DataBean item, final int position) {
                if (isCkecked.size() > 0) {
                    if (isCkecked.get(position)) {
                        helper.setCheckBox(R.id.invite_add_group_checkbox, true);
                    } else {
                        helper.setCheckBox(R.id.invite_add_group_checkbox, false);
                    }
                }
                helper.setText(R.id.friend_item_name, item.getUsername());
                helper.setSimpleDraweeViewByUrl(R.id.item_avatar, item.getHeadimg());
                helper.setItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCkecked.get(position) == false) {
                            isCkecked.put(position, true);
                            helper.setCheckBox(R.id.invite_add_group_checkbox, true);
                            checklist.add(item);
                        } else {
                            isCkecked.put(position, false);
                            helper.setCheckBox(R.id.invite_add_group_checkbox, false);
                            checklist.remove(item);
                        }
                    }
                });

            }
        };
        commonListviewShow.setAdapter(adaper);
    }

    @Override
    protected void setAllClick() {
        inviteAddGroupTvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inviteAddGroupTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inviteAddGroupTvOk.getText().toString().equals("确认")) {
                    if (checklist.size() != 0) {
                        Intent intent = new Intent(InviteAddGroupActivity.this, GroupFenzuActivity.class);
                        intent.putParcelableArrayListExtra("data", checklist);
                        startActivity(intent);
                    } else {
                        Utils.toast(InviteAddGroupActivity.this, "你还没选择任何成员");
                    }
                } else {

                }
            }
        });
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        if (getIntent().hasExtra("fenzu")) {
            liner_srarch.setVisibility(View.GONE);
            inviteAddGroupTvCancle.setVisibility(View.GONE);
            img_back.setVisibility(View.VISIBLE);
            inviteToptile.setText("团队分组管理");
            inviteAddGroupTvOk.setText("确认");
        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_invite_add_group);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        groupId = getIntent().getStringExtra("groupId");
        getGropList();
    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            commonListviewShow.setPressed(false);
            switch (message.what) {
                case INVITESUCUSS:
                    Toast.makeText(InviteAddGroupActivity.this, "你已发出加群邀请", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case INVITEFAILED:
                    Toast.makeText(InviteAddGroupActivity.this, "邀请失败", Toast.LENGTH_SHORT).show();
                    break;
                case 0x110:
                    setAdaper();
                    break;
            }
            inviteAddGroupTvOk.setEnabled(true);
            return false;
        }
    });

    /**
     * 获取群好友
     */
    private void getGropList() {
        helper = new XUtilsHelper<>(getApplication(), NetworkTools.HX_GET_GROUP_USER, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                GroupUsers groupUsers = (GroupUsers) msg.obj;
                mList.clear();
                mList.addAll(groupUsers.getData());
                for (int i = 0; i < mList.size(); i++) {
                    isCkecked.put(i,false);
                }
                handler.sendEmptyMessage(0x110);
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"group_id", groupId}});
        helper.sendPostAuto(GroupUsers.class);
    }

}
