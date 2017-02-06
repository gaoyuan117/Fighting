/*
 * TalkNewFragment     2016/12/17 15:30
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.ui.first.talks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.zcomponent.http.api.host.Endpoint;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.ContactExpandableAdapter;
import com.xiangying.fighting.adapter.GroupExpandableAdapter;
import com.xiangying.fighting.bean.ContactBean;
import com.xiangying.fighting.bean.GroupBean;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.chat.ChatActivity;
import com.xiangying.fighting.ui.chat.help.DemoHelper;
import com.xiangying.fighting.ui.first.armygroup.CreatGroupActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

/**
 * Created by Koterwong on 2016/12/17 15:30
 */
public class TalkNewFragment extends BaseFragment {
    @Bind(R.id.iv_talk_sing)
    ImageView mIvTalkSing;
    @Bind(R.id.tv_talk_single)
    FontTextView mTvTalkSingle;
    @Bind(R.id.layout_talk_single)
    LinearLayout mLayoutTalkSingle;

    @Bind(R.id.iv_talk_group)
    ImageView mIvTalkGroup;
    @Bind(R.id.tv_talk_group)
    FontTextView mTvTalkGroup;
    @Bind(R.id.layout_talk_group)
    LinearLayout mLayoutTalkGroup;

    @Bind(R.id.layout_container)
    FrameLayout mLayoutContainer;
    @Bind(R.id.layout_single)
    LinearLayout mLayoutSingle;
    @Bind(R.id.layout_group)
    LinearLayout mLayoutGroup;
    @Bind(R.id.list_single)
    ExpandableListView mElvSingle;
    @Bind(R.id.list_group)
    ExpandableListView mElvGroup;

    private XUtilsHelper mFriendHelper;
    private XUtilsHelper mGroupHelper;

    private ArrayList<ContactBean.Data> mContactList = new ArrayList<>();
    private ContactExpandableAdapter mContactExpandableAdapter;
    private ArrayList<GroupBean.Data> mGroupList = new ArrayList<>();
    private ArrayList<String> mGroupData = new ArrayList<>();
    private GroupExpandableAdapter mGroupExpandableAdapter;

    private XUtilsHelper mTopHelper;
    private XUtilsHelper mNoTopHelper;

    public static TalkNewFragment newInstance() {
        TalkNewFragment fragment = new TalkNewFragment();
        return fragment;
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        mLayoutTalkSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(1);
            }
        });

        mLayoutTalkGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(2);
            }
        });

        mElvSingle.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, mContactList.get(groupPosition).getUsers().get(childPosition).getUsername());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseUser.TYPE_USER);
                startActivity(intent);
                return false;
            }
        });

        mElvSingle.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (mContactList.get(groupPosition).getGroupName()) {
                    case "添加分队":
                        Intent intentadd = new Intent(getContext(), CreatGroupActivity.class);
                        intentadd.putExtra("element", "");
                        startActivity(intentadd);
                        break;
                }

                return false;
            }
        });

        mElvSingle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

        mContactExpandableAdapter.setOnGroupLongClick(new ContactExpandableAdapter.onGroupLongClick() {
            @Override
            public void longClick() {
                // TODO: 2016/12/22 重命名分组
            }
        });

        mContactExpandableAdapter.setOnPopClick(new ContactExpandableAdapter.OnPopClick() {
            @Override
            public void top(EaseUser easeUser) {
                //置顶好友
                mTopHelper = new XUtilsHelper(mActivity, NetworkTools.HX_TOP, new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        mTopHelper.hideProgress();
                        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
                            loadNetData();
                        }
                        return false;
                    }
                }));
                mTopHelper.showProgress("加载中...");
                mTopHelper.setRequestParams(new String[][]{{"friend_name", easeUser.getUsername()}});
                mTopHelper.sendPostAuto(CommonReturn.class);
            }

            @Override
            public void no_top(EaseUser easeUser) {
                //移除置顶
                mNoTopHelper = new XUtilsHelper(mActivity, NetworkTools.HX_NO_TOP, new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        mNoTopHelper.hideProgress();
                        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
                            loadNetData();
                        }
                        return false;
                    }
                }));
                mNoTopHelper.showProgress("加载中...");
                mNoTopHelper.setRequestParams(new String[][]{{"friend_name", easeUser.getUsername()}});
                mNoTopHelper.sendPostAuto(CommonReturn.class);
            }

            @Override
            public void move(EaseUser easeUser) {
                //移动好友
                if (mContactList.size() == 1) {
                    Toast.makeText(mApp, "没有可以移动的分组", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserFromListActivity.start(mActivity, easeUser.getUsername());
            }
        });

        mElvGroup.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (mGroupData.get(groupPosition)) {
                    case "创建军团":
                        Intent intentcreat = new Intent(getContext(), CreatGroupActivity.class);
                        startActivity(intentcreat);
                        break;
                }
                return false;
            }
        });

        mElvGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, mGroupList.get(childPosition).getId());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseUser.TYPE_GROUP);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void initViews(View view) {
        setFragment(1);
        mContactExpandableAdapter = new ContactExpandableAdapter(mContactList, mActivity);
        mElvSingle.setGroupIndicator(null);
        mElvSingle.setAdapter(mContactExpandableAdapter);
        mGroupExpandableAdapter = new GroupExpandableAdapter(mGroupList, mActivity, mGroupData);
        mElvGroup.setGroupIndicator(null);
        mElvGroup.setAdapter(mGroupExpandableAdapter);
    }

    private void setFragment(int i) {
        clearAllState();
        switch (i) {
            case 1:
                mTvTalkSingle.setTextColor(Color.parseColor("#c40b2b"));
                mIvTalkSing.setImageResource(R.drawable.ic_talk_single_red);
                showSingleChat();
                break;
            case 2:
                mTvTalkGroup.setTextColor(Color.parseColor("#c40b2b"));
                mIvTalkGroup.setImageResource(R.drawable.ic_talk_group_red);
                showGroupChat();
                break;
        }
    }

    private void showSingleChat() {
        mLayoutSingle.setVisibility(View.VISIBLE);
        mLayoutGroup.setVisibility(View.GONE);
    }

    private void showGroupChat() {
        mLayoutSingle.setVisibility(View.GONE);
        mLayoutGroup.setVisibility(View.VISIBLE);
    }

    private void clearAllState() {
        mIvTalkGroup.setImageResource(R.drawable.ic_talk_group_gray);
        mIvTalkSing.setImageResource(R.drawable.ic_talk_single_gray);
        mTvTalkGroup.setTextColor(Color.parseColor("#999999"));
        mTvTalkSingle.setTextColor(Color.parseColor("#999999"));
    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.fragment_talk_new, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void loadNetData() {
        mFriendHelper = new XUtilsHelper(mActivity, NetworkTools.HX_FRIEND_LIST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mContactList.clear();
                try {
                    ContactBean contactBean = (ContactBean) msg.obj;
                    if (contactBean.getCode() == 1 && contactBean.getData() != null && contactBean.getData().size() > 0) {
                        mContactList.addAll(contactBean.getData());
                        upContact(contactBean.getData());  //更新环信用户数据。
                    }
                    mContactList.add(new ContactBean.Data("添加分队"));
                } catch (Exception e) {
                    e.printStackTrace();
                    mContactList.add(new ContactBean.Data("添加分队"));
                }
                mContactExpandableAdapter.notifyDataSetChanged();
                return false;
            }
        }));
        mFriendHelper.setRequestParams(new String[][]{});
        mFriendHelper.sendPostAuto(ContactBean.class);

        mGroupHelper = new XUtilsHelper(mActivity, NetworkTools.HX_GROUP_LIST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mGroupList.clear();
                mGroupData.clear();
                try {
                    GroupBean groupBean = (GroupBean) msg.obj;
                    if (groupBean.getCode() == 1 && groupBean.getData() != null && groupBean.getData().size() > 0) {
                        mGroupList.addAll(groupBean.getData());
                        updataGroup(groupBean.getData());
                        mGroupData.add("军团");
                    }
                    mGroupData.add("创建军团");
                } catch (Exception e) {
                    e.printStackTrace();
                    mGroupData.add("创建军团");
                }
                mGroupExpandableAdapter.notifyDataSetChanged();
                return false;
            }
        }));
        mGroupHelper.setRequestParams(new String[][]{});
        mGroupHelper.sendPostAuto(GroupBean.class);
    }

    private void updataGroup(final List<GroupBean.Data> data) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getImgPath() != null) {
                        EaseUser easeUser = new EaseUser(data.get(i).getId());

                        if (easeUser != null) {
                            easeUser.setAvatar(Endpoint.HOST + data.get(i).getImgPath());
                            DemoHelper.getInstance().saveContact(easeUser);
                        }
                    }
                }
            }
        }.start();
    }

    private void upContact(final List<ContactBean.Data> data) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUsers() != null && data.get(i).getUsers().size() > 0) {
                        DemoHelper.getInstance().updateContactList(data.get(i).getUsers());
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        onReLoadData();
    }

    public void onReLoadData() {
        loadNetData();
    }

}
