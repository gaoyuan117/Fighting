package com.xiangying.fighting.ui.first.armyuser;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.chat.ChatActivity;
import com.xiangying.fighting.ui.chat.ChatContactUtils;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.ui.three.selfinfo.AllNoteActivity;
import com.xiangying.fighting.ui.three.selfinfo.UserAlbumActivity;
import com.xiangying.fighting.utils.AlertDialogUtil;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.JsonParse;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendActivity extends BaseActivity {
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
    @Bind(R.id.user_detail_img_avatar)
    SimpleDraweeView userDetailImgAvatar;
    @Bind(R.id.group_detail_tv_name)
    TextView groupDetailTvName;
    @Bind(R.id.group_detail_tv_id)
    TextView groupDetailTvId;
    @Bind(R.id.group_detail_img_all)
    ImageView groupDetailImgAll;
    @Bind(R.id.user_liner_photo)
    LinearLayout userLinerPhoto;
    @Bind(R.id.user_tv_address)
    TextView userTvAddress;
    @Bind(R.id.user_liner_address)
    LinearLayout userLinerAddress;
    @Bind(R.id.user_tv_sex)
    TextView userTvSex;
    @Bind(R.id.user_liner_sex)
    LinearLayout userLinerSex;
    @Bind(R.id.user_tv_sign)
    TextView userTvSign;
    @Bind(R.id.user_liner_sign)
    LinearLayout userLinerSign;
    @Bind(R.id.user_btn_add)
    Button userBtnAdd;
    @Bind(R.id.user_btn_delete)
    Button userBtnDelete;
    @Bind(R.id.user_btn_send)
    Button userBtnSend;
    @Bind(R.id.user_liner_isfriend)
    LinearLayout userLinerIsfriend;

    private String userName;
    private TalkBeans talkBeans;
    private XUtilsHelper mUserInfoHelper;

    public static void start(Context context, String userName, TalkBeans talkBean) {
        Intent starter = new Intent(context, FriendActivity.class);
        starter.putExtra("userName", userName);
        starter.putExtra("data", talkBean);
        context.startActivity(starter);
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userLinerPhoto.setOnClickListener(new View.OnClickListener() {
            //user picture
            @Override
            public void onClick(View v) {
                if (talkBeans == null) {
                    return;
                }

                if (userName.equals(GetLocalKey.getUserName())) {
                    UserAlbumActivity.start(FriendActivity.this);
                    return;
                }

                judgeAuth();

//                if (talkBeans.getIs_friend()) {
//                    Intent intent = new Intent(FriendActivity.this, AllNoteActivity.class);
//                    startActivity(intent);
//                    // TODO: 2016/12/22 查看好友照片
//                } else {
//                    Toast.makeText(FriendActivity.this, "成为好友后，即可查看对方相册", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        userBtnDelete.setOnClickListener(new View.OnClickListener() {
            //delete the friend
            @Override
            public void onClick(View v) {
                AlertDialogUtil.AlertDialog(FriendActivity.this, "确认删除好友？", "确认", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EaseUser easeUser = new EaseUser(talkBeans.getUsername());
                        ChatContactUtils.deleteContact(FriendActivity.this, easeUser);
                        deleteContactFormService();
                    }
                });
            }
        });

        userBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add friend
                ChatContactUtils.addContact(FriendActivity.this, talkBeans.getUsername(), "");
                addFriendOnService();
            }
        });

        //发消息
        userBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, talkBeans.getUsername());
                startActivity(intent);
            }
        });
    }

    private void addFriendOnService() {
        XUtilsHelper applyHelper = new XUtilsHelper(FriendActivity.this, NetworkTools.HX_ADD_FRIEND, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (Utils.getResultCode(FriendActivity.this, msg.obj.toString()) == 1) {
                    try {
                        Utils.toast(FriendActivity.this, new JSONObject(msg.obj.toString()).optString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isMyfriend(true);
                }
                return false;
            }
        }));
        applyHelper.setRequestParams(new String[][]{{"username", GetLocalKey.getUserName()}, {"f_zd_id", talkBeans.getZd_id()}});
        applyHelper.sendPost();
    }

    private void deleteContactFormService() {
        XUtilsHelper applyHelper = new XUtilsHelper(FriendActivity.this, NetworkTools.HX_DELETE_FRIEND, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (Utils.getResultCode(FriendActivity.this, msg.obj.toString()) == 1) {
                    try {
                        Utils.toast(FriendActivity.this, new JSONObject(msg.obj.toString()).optString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isMyfriend(false);
                }
                return false;
            }
        }));
        applyHelper.setRequestParams(new String[][]{
                {"username", GetLocalKey.getUserName()}, {"friend_name", talkBeans.getUsername()}
        });
        applyHelper.sendPost();
        applyHelper.showProgress("删除好友中，请稍后...");
    }

    private void isMyfriend(boolean b) {
        if (!b) {
            userLinerIsfriend.setVisibility(View.GONE);
            userBtnAdd.setVisibility(View.VISIBLE);
        } else {
            userLinerIsfriend.setVisibility(View.VISIBLE);
            userBtnAdd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initViews() {
        userName = getIntent().getStringExtra("userName");
        talkBeans = getIntent().getParcelableExtra("data");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
        titleBarCommonTvTitle.setText("个人信息");

        if (userName.equals(GetLocalKey.getUserName())) {
            userBtnAdd.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        if (mUserInfoHelper == null) {
            mUserInfoHelper = new XUtilsHelper(this, NetworkTools.HX_FRIEND_INFO, new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    try {
                        String result = msg.obj.toString();
                        if (Utils.getResultCode(FriendActivity.this, result) == 1) {
                            talkBeans = JsonParse.ParseUser(result);
                            if (!TextUtils.isEmpty(talkBeans.getHeadimg())) {
                                userDetailImgAvatar.setImageURI(Uri.parse(Endpoint.HOST + talkBeans.getHeadimg()));
                            }
                            groupDetailTvName.setText(talkBeans.getNickname());
                            groupDetailTvId.setText("战斗号：" + talkBeans.getZd_id());
                            if (TextUtils.isEmpty(talkBeans.getDistrict())) {
                                userTvAddress.setText("未选择地区");
                            } else {
                                userTvAddress.setText(talkBeans.getDistrict());
                            }
                            Logger.t("FriendActivity").d("性别 = " + talkBeans.getSex());
                            userTvSex.setText(talkBeans.getSex() == 1 ? "男" : "女");
                            if (TextUtils.isEmpty(talkBeans.getSign())) {
                                userTvSign.setText("他很懒，什么都没留下");
                            } else {
                                userTvSign.setText(talkBeans.getSign());
                            }
                            isMyfriend(talkBeans.getIs_friend());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }));
        }
        mUserInfoHelper.setRequestParams(new String[][]{{"friend_name", userName}});
        mUserInfoHelper.sendPost();
    }

    private void judgeAuth() {
        XUtilsHelper helper = new XUtilsHelper<>(getApplication(), NetworkTools.HAS_AUTH, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean renZhengBean = (RenZhengBean) msg.obj;
                if (renZhengBean.getCode() == 1) {
                    Intent intent = new Intent(FriendActivity.this, AllNoteActivity.class);
                    intent.putExtra("uid",talkBeans.getUid() + "");
                    startActivity(intent);
                } else {
                    Toast.makeText(FriendActivity.this, "没有权限访问哦~", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"fid", talkBeans.getUid() + ""}});
        helper.sendPostAuto(RenZhengBean.class);
    }
}
