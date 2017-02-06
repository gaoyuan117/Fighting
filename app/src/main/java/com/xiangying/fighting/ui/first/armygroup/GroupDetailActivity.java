package com.xiangying.fighting.ui.first.armygroup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jock.pickerview.utils.ScreenUtils;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.DensityUtil;
import com.xiangying.fighting.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupDetailActivity extends BaseActivity {

    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.group_detail_img_avatar)
    SimpleDraweeView groupDetailImgAvatar;
    @Bind(R.id.group_detail_tv_name)
    TextView groupDetailTvName;
    @Bind(R.id.group_detail_tv_id)
    TextView groupDetailTvId;
    @Bind(R.id.group_detail_tv_createtime)
    TextView groupDetailTvCreatetime;
    @Bind(R.id.group_detail_tv_introduce)
    TextView groupDetailTvIntroduce;
    @Bind(R.id.group_detail_img_all)
    ImageView groupDetailImgAll;
    @Bind(R.id.group_detail_gv_liner_member)
    GridView groupDetailGvLinerMember;
    @Bind(R.id.group_detail_rea_all)
    RelativeLayout groupDetailReaAll;
    @Bind(R.id.group_detail_btn_add)
    Button groupDetailBtnAdd;

    private ArrayList<TalkBeans> talkBeanses=new ArrayList<>();
    private int groupId=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void process() {
        int num= ScreenUtils.getScreenW(this)/ DensityUtil.dip2px(this, 55);
        Log.v("GroupDetailActivity", num + "");
        for (int i=0;i<num;i++){
            TalkBeans talkBeans=new TalkBeans("","http://t2.27270.com/uploads/tu/201606/31/uoiednduejj.jpg");
            talkBeanses.add(talkBeans);
        }
        CommonAdapter<TalkBeans> adaper = new CommonAdapter<TalkBeans>(this,talkBeanses, R.layout.item_avatar) {
            @Override
            public void convert(ViewHolder helper, TalkBeans item, int position) {
                helper.setSimpleDraweeViewByUrl(R.id.item_avatar, item.getAvatar());
            }
        };
        int gridviewWidth = (num* ( DensityUtil.dip2px(this, (48 + 5))));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        groupDetailGvLinerMember.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        groupDetailGvLinerMember.setNumColumns(num);
        groupDetailGvLinerMember.setAdapter(adaper);
    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        groupDetailReaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupDetailActivity.this,GroupMembersActivity.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
            }
        });
        groupDetailBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(GroupDetailActivity.this, "申请发送成功，请等待管理员审核");
            }
        });
    }

    @Override
    protected void initViews() {
        groupId=getIntent().getIntExtra("groupId",-1);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }
}
