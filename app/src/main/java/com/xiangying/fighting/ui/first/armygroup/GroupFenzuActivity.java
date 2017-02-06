package com.xiangying.fighting.ui.first.armygroup;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.talks.GroupUsers;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupFenzuActivity extends BaseActivity {

    public  static final int GROUP_NAME=1;
    public  static final int LEADER_NAME=2;
    public  static final int USER_NAME=3;


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.invite_toptile)
    TextView inviteToptile;
    @Bind(R.id.invite_add_group_tv_ok)
    TextView inviteAddGroupTvOk;
    @Bind(R.id.id_top_bar)
    RelativeLayout idTopBar;
    @Bind(R.id.common_listview_show)
    ListView commonListviewShow;
    @Bind(R.id.group_fenzu_tv_name1)
    TextView groupFenzuTvName1;
    @Bind(R.id.group_fenzu_liner_groupname)
    LinearLayout groupFenzuLinerGroupname;
    @Bind(R.id.group_fenzu_tv_name2)
    TextView groupFenzuTvName2;
    @Bind(R.id.group_fenzu_liner_lindaoname)
    LinearLayout groupFenzuLinerLindaoname;

    private ArrayList<GroupUsers.DataBean> datalist = new ArrayList<>();
    CommonAdapter<GroupUsers.DataBean> adaper;



    @Override
    protected void process() {
        datalist=getIntent().getParcelableArrayListExtra("data");
        adaper = new CommonAdapter<GroupUsers.DataBean>(this, datalist, R.layout.invite_add_group_item) {
            @Override
            public void convert(final ViewHolder helper, final GroupUsers.DataBean item, final int position) {
                helper.setGone(R.id.invite_add_group_checkbox);
                helper.setText(R.id.friend_item_name,item.getUsername());
                helper.setSimpleDraweeViewByUrl(R.id.item_avatar, item.getHeadimg());
            }
        };
        commonListviewShow.setAdapter(adaper);
    }

    @Override
    protected void setAllClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inviteAddGroupTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        groupFenzuLinerGroupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupFenzuActivity.this,GroupSetNameActivity.class);
                intent.putExtra("type",GROUP_NAME);
                startActivityForResult(intent,GROUP_NAME);
            }
        });
        groupFenzuLinerLindaoname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupFenzuActivity.this,GroupSetNameActivity.class);
                intent.putExtra("type",LEADER_NAME);
                startActivityForResult(intent,LEADER_NAME);
            }
        });
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_group_fenzu);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String name=data.getStringExtra("name");
            if(resultCode==GROUP_NAME){
                groupFenzuTvName1.setText(name);
            }else {
                groupFenzuTvName2.setText(name);
            }
        }
    }
}
