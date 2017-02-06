package com.xiangying.fighting.ui.first.armygroup;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupCardActivity extends AppCompatActivity {

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
    @Bind(R.id.et_group_card_position)
    EditText mPosition;
    @Bind(R.id.et_group_card_name)
    EditText mGroupCardName;
    @Bind(R.id.bt_group_card_sure)
    Button mSure;

    private String position, nickName, groupId, rename;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_card);
        ButterKnife.bind(this);
        //获取数据
        groupId = getIntent().getStringExtra("groupId");
        initTitle();
        setListener();
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonTvTitle.setText("我的群名片");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 按钮点击事件
     */
    private void setListener() {
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = mPosition.getText().toString().trim();
                nickName = mGroupCardName.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    Toast.makeText(GroupCardActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(position)) {
                    rename = nickName;
                } else {
                    rename = position + " - " + nickName;
                }
                commit(rename);
            }
        });
    }

    /**
     * 提交
     */
    private void commit(String rename) {
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        XUtilsHelper<RenZhengBean> helper = new XUtilsHelper<>(getApplication(), NetworkTools.HX_RENAME, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean commonReturn = (RenZhengBean) msg.obj;
                if (commonReturn.getCode() == 1) {
                    Toast.makeText(GroupCardActivity.this, commonReturn.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GroupCardActivity.this, "设置失败，请检查网络~", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"groupid", groupId}, {"rename", rename}});
        helper.sendPostAuto(RenZhengBean.class);
    }

}
