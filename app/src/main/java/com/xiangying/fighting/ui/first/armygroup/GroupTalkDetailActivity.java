package com.xiangying.fighting.ui.first.armygroup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.jock.pickerview.utils.ScreenUtils;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.GroupDetailBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.chat.activity.GroupPickContactsActivity;
import com.xiangying.fighting.ui.chat.chatutil.ChaiUtils;
import com.xiangying.fighting.ui.first.armyuser.FriendActivity;
import com.xiangying.fighting.ui.first.talks.GridPhotoActivity;
import com.xiangying.fighting.ui.first.talks.LookTalkContentActivity;
import com.xiangying.fighting.ui.first.talks.ReportActivity;
import com.xiangying.fighting.utils.DensityUtil;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

/**
 * 群聊详情
 */
public class GroupTalkDetailActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_CODE_ADD_USER = 0;
    private static final int REQUEST_CODE_EXIT = 1;
    private static final int REQUEST_CODE_EXIT_DELETE = 2;
    private static final int REQUEST_CODE_EDIT_GROUPNAME = 5;

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
    @Bind(R.id.group_talk_detail_liner_getredppark)
    LinearLayout groupTalkDetailLinerGetredppark;
    @Bind(R.id.group_talk_detail_tv_menmber_nums)
    TextView groupTalkDetailTvMenmberNums;
    @Bind(R.id.group_detail_img_all)
    ImageView groupDetailImgAll;
    @Bind(R.id.group_detail_gv_liner_member)
    GridView groupDetailGvLinerMember;
    @Bind(R.id.group_detail_rea_all)
    RelativeLayout groupDetailReaAll;
    @Bind(R.id.group_talk_detail_liner_renshi)
    LinearLayout groupTalkDetailLinerRenshi;
    @Bind(R.id.group_talk_detail_liner_bossredpack)
    LinearLayout groupTalkDetailLinerBossredpack;
    @Bind(R.id.group_talk_detail_liner_fenzu)
    LinearLayout groupTalkDetailLinerFenzu;
    @Bind(R.id.group_talk_detail_liner_name)
    LinearLayout groupTalkDetailLinerName;
    @Bind(R.id.group_talk_detail_check_zhiding)
    CheckBox groupTalkDetailCheckZhiding;
    @Bind(R.id.group_talk_detail_liner_zhiding)
    LinearLayout groupTalkDetailLinerZhiding;
    @Bind(R.id.group_talk_detail_check_miandarao)
    CheckBox groupTalkDetailCheckMiandarao;
    @Bind(R.id.group_talk_detail_liner_miandarao)
    LinearLayout groupTalkDetailLinerMiandarao;
    @Bind(R.id.group_talk_detail_check_jinyan)
    CheckBox groupTalkDetailCheckJinyan;
    @Bind(R.id.group_talk_detail_liner_jintan)
    LinearLayout groupTalkDetailLinerJintan;
    @Bind(R.id.group_talk_detail_liner_tupian)
    LinearLayout groupTalkDetailLinerTupian;
    @Bind(R.id.group_talk_detail_liner_lookcontent)
    LinearLayout groupTalkDetailLinerLookcontent;
    @Bind(R.id.group_talk_detail_liner_clearcontent)
    LinearLayout groupTalkDetailLinerClearcontent;
    @Bind(R.id.group_talk_detail_liner_myname)
    LinearLayout groupTalkDetailLinerMyname;
    @Bind(R.id.group_talk_detail_liner_jubao)
    LinearLayout groupTalkDetailLinerJubao;

    @Bind(R.id.group_talk_detail_out)
    Button groupTalkDetailOut;
    @Bind(R.id.group_talk_detail_liner_member)
    LinearLayout liner_member;
    @Bind(R.id.tv_group_detail_groupname)
    TextView mGroupname;
    @Bind(R.id.tv_group_detail_nickname)
    TextView mNickname;

    private String groupId = "";
    private XUtilsHelper mGroupDetailHelper;
    private EMGroup mGroup;
    private ProgressDialog progressDialog;

    public static void start(Context context, String g_username) {
        Intent starter = new Intent(context, GroupTalkDetailActivity.class);
        starter.putExtra("g_username", g_username);
        context.startActivity(starter);
    }

    @Override
    protected void process() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                    if (mGroup != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUpView();
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setUpView() {
        groupTalkDetailTvMenmberNums.setText(mGroup.getAffiliationsCount() + "人");

        final int num = ScreenUtils.getScreenW(this) / DensityUtil.dip2px(this, 55);
        final ArrayList<String> members = new ArrayList<>();
        members.addAll(mGroup.getMembers());
        members.add("");

        CommonAdapter<String> adaper = new CommonAdapter<String>(this, members, R.layout.item_avatar) {
            @Override
            public void convert(ViewHolder helper, final String item, int position) {
                if (position == members.size() - 1) {
                    helper.setImageResource(R.id.item_avatar, R.drawable.ic_groupmember_add);
                    helper.setItemClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//              Intent intent = new Intent(GroupTalkDetailActivity.this, InviteAddGroupActivity.class);
//              startActivity(intent);
                            startActivityForResult(
                                    (new Intent(GroupTalkDetailActivity.this, GroupPickContactsActivity.class).putExtra("groupId", groupId)),
                                    REQUEST_CODE_ADD_USER);
                        }
                    });
                } else {
                    ImageView imageView = helper.getView(R.id.item_avatar);
                    EaseUserUtils.setUserAvatar(GroupTalkDetailActivity.this, item, imageView);
                    helper.setItemClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FriendActivity.start(GroupTalkDetailActivity.this, item, null);
                        }
                    });
                }
            }
        };
        int gridviewHeight;
        if (members.size() > num) {
            gridviewHeight = DensityUtil.dip2px(this, (48 + 5) * 2);
        } else {
            gridviewHeight = DensityUtil.dip2px(this, (48 + 5));
        }
        int gridviewWidth = (num * (DensityUtil.dip2px(this, (48 + 5))));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, gridviewHeight);
        groupDetailGvLinerMember.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        groupDetailGvLinerMember.setNumColumns(num);
        groupDetailGvLinerMember.setAdapter(adaper);
    }

    @Override
    protected void setAllClick() {
        groupTalkDetailLinerGetredppark.setOnClickListener(this);
        groupTalkDetailLinerFenzu.setOnClickListener(this);
        groupDetailReaAll.setOnClickListener(this);
        groupTalkDetailLinerRenshi.setOnClickListener(this);
        groupTalkDetailLinerBossredpack.setOnClickListener(this);
        groupTalkDetailLinerName.setOnClickListener(this);
        groupTalkDetailLinerTupian.setOnClickListener(this);
        groupTalkDetailLinerLookcontent.setOnClickListener(this);
        groupTalkDetailLinerClearcontent.setOnClickListener(this);
        groupTalkDetailLinerMyname.setOnClickListener(this);
        groupTalkDetailLinerJubao.setOnClickListener(this);
        groupTalkDetailOut.setOnClickListener(this);
        titleBarCommonIvOperate1.setOnClickListener(this);
        groupTalkDetailCheckMiandarao.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonTvTitle.setText("聊天信息");
        groupId = getIntent().getStringExtra("g_username");


    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_group_talk_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        mGroupDetailHelper = new XUtilsHelper(this, NetworkTools.HX_GROUP_DETAIL, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    GroupDetailBean groupDetailBean = (GroupDetailBean) msg.obj;
                    if (groupDetailBean.getCode() == 1) {
                        groupTalkDetailTvMenmberNums.setText(groupDetailBean.getData().getData().getAffiliations_count());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        mGroupDetailHelper.showProgress("加载中...");
        mGroupDetailHelper.setRequestParams(new String[][]{{"group_ids", "" + groupId}});
        mGroupDetailHelper.sendPostAuto(GroupDetailBean.class);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.group_talk_detail_liner_getredppark://新人领红包
                break;
            case R.id.group_talk_detail_liner_fenzu://分组管理
                intent = new Intent(GroupTalkDetailActivity.this, InviteAddGroupActivity.class);
                intent.putExtra("fenzu", "fenzu");
                intent.putExtra("groupId", groupId);
                break;
            case R.id.group_detail_rea_all://全部成员
                intent = new Intent(GroupTalkDetailActivity.this, GroupMembersActivity.class);
                intent.putExtra("groupId", groupId);
                break;
            case R.id.group_talk_detail_liner_renshi://人事部
                break;
            case R.id.group_talk_detail_liner_bossredpack://团主发红包
                break;
            case R.id.group_talk_detail_liner_name://团聊名称
                intent = new Intent(GroupTalkDetailActivity.this, GroupSetNameActivity.class);
                intent.putExtra("type", GroupFenzuActivity.GROUP_NAME);
                intent.putExtra("groupid", groupId);
                break;
            case R.id.group_talk_detail_liner_tupian://聊天图片
                GridPhotoActivity.start(this, groupId);
                break;
            case R.id.group_talk_detail_liner_lookcontent://查找聊天内容
                LookTalkContentActivity.start(this, groupId, EaseConstant.CHATTYPE_GROUP);
                break;
            case R.id.group_talk_detail_liner_clearcontent://清空聊天内容
                ChaiUtils.emptyHistory(GroupTalkDetailActivity.this, groupId);
                break;
            case R.id.group_talk_detail_liner_myname://我在本团的名称
                intent = new Intent(GroupTalkDetailActivity.this, GroupCardActivity.class);
                intent.putExtra("groupId", groupId);
//                intent.putExtra("nickname",)
                break;
            case R.id.group_talk_detail_liner_jubao://举报
                ReportActivity.start(this, groupId, "group");
                break;
            case R.id.group_talk_detail_out://删除并推出
                showDialog();
                break;
            case R.id.title_bar_common_iv_operate_1://退出
                finish();
                break;
        }
        if (intent != null)
            startActivity(intent);
    }

    /**
     * 显示dialog
     */
    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确定退出该群吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitGroup();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String st1 = getResources().getString(R.string.being_added);
        String st2 = getResources().getString(R.string.is_quit_the_group_chat);
        String st3 = getResources().getString(R.string.chatting_is_dissolution);
        String st4 = getResources().getString(R.string.are_empty_group_of_news);
        String st5 = getResources().getString(R.string.is_modify_the_group_name);
        final String st6 = getResources().getString(R.string.Modify_the_group_name_successful);
        final String st7 = getResources().getString(R.string.change_the_group_name_failed_please);

        if (resultCode == RESULT_OK) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(GroupTalkDetailActivity.this);
                progressDialog.setMessage(st1);
                progressDialog.setCanceledOnTouchOutside(false);
            }
            switch (requestCode) {
                case REQUEST_CODE_ADD_USER:// 添加群成员
                    final String[] newmembers = data.getStringArrayExtra("newmembers");
                    progressDialog.setMessage(st1);
                    progressDialog.show();
                    addMembersToGroup(newmembers);
                    break;
                case REQUEST_CODE_EXIT: // 退出群
                    progressDialog.setMessage(st2);
                    progressDialog.show();
//          exitGrop();
                    break;
                case REQUEST_CODE_EXIT_DELETE: // 解散群
                    progressDialog.setMessage(st3);
                    progressDialog.show();
//          deleteGrop();
                    break;

                case REQUEST_CODE_EDIT_GROUPNAME: //修改群名称
                    final String returnData = data.getStringExtra("data");
                    if (!TextUtils.isEmpty(returnData)) {
                        progressDialog.setMessage(st5);
                        progressDialog.show();

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    EMClient.getInstance().groupManager().changeGroupName(groupId, returnData);
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            ((TextView) findViewById(R.id.group_name)).setText(returnData + "(" + mGroup.getAffiliationsCount()
                                                    + "人)");
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), st6, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), st7, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void addMembersToGroup(String[] newmembers) {
        if (newmembers == null && newmembers.length == 0) {
            return;
        }

        if (newmembers.length == 1) {
            XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_GROUP_ADDGROUPMEMBER, new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {

                    }
                    return false;
                }
            }));
            xUtilsHelper.setRequestParams(new String[][]{{"group_id", groupId}, {"username", newmembers[0]}});
            xUtilsHelper.sendPostAuto(CommonReturn.class);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < newmembers.length; i++) {
            stringBuilder.append(newmembers[i]);
            if (i != newmembers.length - 1) {
                stringBuilder.append(",");
            }
        }

        XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.HX_GROUP_ADDGROUPMEMBERS, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {

                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"group_id", groupId}, {"usernames", stringBuilder.toString()}});
        xUtilsHelper.sendPostAuto(CommonReturn.class);

//    final String st6 = getResources().getString(R.string.Add_group_members_fail);
//    new Thread(new Runnable() {
//
//      public void run() {
//        try {
//          // 创建者调用add方法
//          if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
//            EMClient.getInstance().groupManager().addUsersToGroup(groupId, newmembers);
//          } else {
//            // 一般成员调用invite方法
//            EMClient.getInstance().groupManager().inviteUser(groupId, newmembers, null);
//          }
//          runOnUiThread(new Runnable() {
//            public void run() {
//              refreshMembers();
//              ((TextView) findViewById(R.id.group_name)).setText(group.getGroupName() + "(" + group.getAffiliationsCount()
//                  + st);
//              progressDialog.dismiss();
//            }
//          });
//        } catch (final Exception e) {
//          runOnUiThread(new Runnable() {
//            public void run() {
//              progressDialog.dismiss();
//              Toast.makeText(getApplicationContext(), st6 + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//          });
//        }
//      }
//    }).start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.group_talk_detail_check_miandarao:  //消息免打扰
                if (isChecked) {
                    shieldMessage();
                } else {
                    unBlock();
                }
                break;
            case R.id.group_talk_detail_check_jinyan:  //禁言
                break;
        }
    }

    /**
     * 删除并退出群
     */
    private void exitGroup() {
        XUtilsHelper<CommonReturn> helper = new XUtilsHelper<>(getApplication(), NetworkTools.HX_EXIT_GROUP, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                CommonReturn commonReturn = (CommonReturn) msg.obj;
                if (commonReturn.code == 1) {
                    Toast.makeText(GroupTalkDetailActivity.this, commonReturn.message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GroupTalkDetailActivity.this, commonReturn.message, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"group_id", groupId}});
        helper.sendPostAuto(CommonReturn.class);
    }

    /**
     * 消息免打扰
     */
    private void shieldMessage() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    EMClient.getInstance().groupManager().blockGroupMessage(groupId);//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 解除消息免打扰
     */
    private void unBlock() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    EMClient.getInstance().groupManager().unblockGroupMessage(groupId);//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroupDetail();
    }

    /**
     * 获取消息
     */
    private void getGroupDetail() {
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        XUtilsHelper helper = new XUtilsHelper<>(getApplication(), NetworkTools.HX_GET_DETAIL, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                GetGroupDetailBean detailBean = (GetGroupDetailBean) msg.obj;
                if (detailBean.getCode() == 1) {
                    mGroupname.setText(detailBean.getData().getGroupname());
                    mNickname.setText(detailBean.getData().getRename());
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"groupid", groupId}});
        helper.sendPostAuto(GetGroupDetailBean.class);
    }
}
