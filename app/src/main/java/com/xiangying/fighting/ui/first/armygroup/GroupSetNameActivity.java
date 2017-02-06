package com.xiangying.fighting.ui.first.armygroup;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class GroupSetNameActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.invite_toptile)
    TextView inviteToptile;
    @Bind(R.id.invite_add_group_tv_ok)
    TextView inviteAddGroupTvOk;
    @Bind(R.id.id_top_bar)
    RelativeLayout idTopBar;
    @Bind(R.id.invite_addgroup_et_name)
    EditText inviteAddgroupEtName;
    @Bind(R.id.invite_liner_search)
    LinearLayout inviteLinerSearch;


    private int type = 1;
    private String groupid;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finish();
        }
    };

    @Override
    protected void process() {

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
                String name = inviteAddgroupEtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Utils.toast(GroupSetNameActivity.this, "你还没输入团名称");
                    return;
                }
                setgroupName(name);
            }
        });
    }


    private void setgroupName(final String name) {

        XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_RE_GROUP_NAME, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                CommonReturn groupBean = (CommonReturn) msg.obj;
                if (groupBean.code == 1) {
                    Toast.makeText(GroupSetNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    resetGroupname(name);
                } else {
                    Toast.makeText(GroupSetNameActivity.this, groupBean.message, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"name", name}, {"groupid", groupid}});
        xUtilsHelper.sendPostAuto(CommonReturn.class);
    }

    private void resetGroupname(final String name){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    EMClient.getInstance().groupManager().changeGroupName(groupid,name);//需异步处理
                    handler.sendEmptyMessage(0x111);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @Override
    protected void initViews() {
        type = getIntent().getIntExtra("type", 1);
        groupid = getIntent().getStringExtra("groupid");
        if (type == GroupFenzuActivity.GROUP_NAME) {
            inviteAddgroupEtName.setHint("输入团名称");
        } else if (type == GroupFenzuActivity.LEADER_NAME) {
            inviteAddgroupEtName.setHint("输入领导名称");
        } else {
            inviteAddgroupEtName.setHint("输入在团聊中的名称");
        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_group_set_name);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }
}
