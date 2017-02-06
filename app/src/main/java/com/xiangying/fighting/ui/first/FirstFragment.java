package com.xiangying.fighting.ui.first;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.NewsTitleBean;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.chat.ChatActivity;
import com.xiangying.fighting.ui.chat.help.DemoHelper;
import com.xiangying.fighting.ui.first.armygroup.AddFriendOrGroupActivity;
import com.xiangying.fighting.ui.first.armyuser.SearchFriendResultActivity;
import com.xiangying.fighting.ui.first.news.NewsFragment;
import com.xiangying.fighting.ui.first.sharehouse.ShareHouseFragment;
import com.xiangying.fighting.ui.first.talks.TalkNewFragment;
import com.xiangying.fighting.ui.first.vedio.UploadActivity;
import com.xiangying.fighting.ui.first.vedio.UploadVideoActivity;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.bottomlialog.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by xiaoniao on 2016/3/18.
 */
public class FirstFragment extends BaseFragment {
    @Bind(R.id.fragmentone_edit_search)
    EditText fragmentoneEditSearch;
    @Bind(R.id.fragmentone_tv_search)
    FontTextView fragmentoneTvSearch;
    @Bind(R.id.fragmentone_tv_add)
    FontTextView fragmentoneTvAdd;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    LinearLayout titleBarCommonRvViewGroup;
    @Bind(R.id.fragmentone_tab)
    TabLayout fragmentoneTab;
    @Bind(R.id.fragmentone_viewpager)
    ViewPager fragmentoneViewpager;
    @Bind(R.id.fragment_frist_img_vedio)
    ImageView fragmentFristImgVedio;

    private PagerAdapter adapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private XUtilsHelper mHelper;
    private int position;
    private EaseConversationListFragment mEaseConversationListFragment;

    public static Fragment newInstance() {
        Fragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    protected void process() {
        setUpViewPager();
    }

    private void setUpViewPager() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        fragments.add(mEaseConversationListFragment);
        fragments.add(TalkNewFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
//    fragments.add(ShareHouseFragment.newInstance("0"));
        fragments.add(ShareHouseFragment.newInstance("1"));

        titles.add("聊聊吧");
        titles.add("通讯录");
        titles.add("视频集");
//    titles.add("正能量");
        titles.add("分享屋");

        adapter = new PagerAdapter(getChildFragmentManager(), fragments, titles);
        fragmentoneViewpager.setAdapter(adapter);
        fragmentoneViewpager.setOffscreenPageLimit(fragments.size());
        fragmentoneTab.setupWithViewPager(fragmentoneViewpager);
    }

    @Deprecated
    private void setUpViewPager(List<NewsTitleBean.Data> newsTitle) {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        fragments.add(mEaseConversationListFragment);
        fragments.add(TalkNewFragment.newInstance());
        fragments.add(ShareHouseFragment.newInstance("0"));
        fragments.add(ShareHouseFragment.newInstance("1"));

        titles.add("聊聊吧");
        titles.add("通讯录");
        titles.add("正能量");
        titles.add("分享屋");

        for (int i = 0; i < newsTitle.size(); i++) {
            fragments.add(i + 2, NewsFragment.newInstance(newsTitle.get(i).getId()));
            titles.add(i + 2, newsTitle.get(i).getTitle());
        }

        adapter = new PagerAdapter(getChildFragmentManager(), fragments, titles);
        fragmentoneViewpager.setAdapter(adapter);
        fragmentoneViewpager.setOffscreenPageLimit(fragments.size());
        fragmentoneTab.setupWithViewPager(fragmentoneViewpager);
    }


    @Override
    protected void setAllClick() {
        fragmentoneTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "";
                key = fragmentoneEditSearch.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    Utils.toast(getContext(), "请输入关键字进行搜索");
                    return;
                }
                Intent intentsearh = new Intent(getContext(), SearchFriendResultActivity.class);
                intentsearh.putExtra("key", key);
                startActivity(intentsearh);
            }
        });

        fragmentoneTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentadd = new Intent(getContext(), AddFriendOrGroupActivity.class);
                startActivity(intentadd);
            }
        });

        fragmentFristImgVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == 1) {
                    final BottomDialog dialog = BottomDialog.create(getActivity().getSupportFragmentManager());
                    dialog.setLayoutRes(R.layout.dialog_upload_option)
                            .setCancelOutside(true)
                            .setViewListener(new BottomDialog.ViewListener() {
                                @Override
                                public void bindView(View rootView) {
                                    rootView.findViewById(R.id.tv_upload_image).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (position == fragments.size() - 1) {
                                                UploadActivity.start(mActivity, "1");
                                            } else {
                                                UploadActivity.start(mActivity, "0");
                                            }
                                            dialog.dismiss();
                                        }
                                    });

                                    rootView.findViewById(R.id.tv_upload_video).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (position == fragments.size() - 1) {
                                                UploadVideoActivity.start(mActivity, "1");
                                            } else {
                                                UploadVideoActivity.start(mActivity, "0");
                                            }
                                            dialog.dismiss();
                                        }
                                    });

                                    rootView.findViewById(R.id.tv_upload_cancel).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            })
                            .show();
                } else {
                    Intent intentadd = new Intent(getContext(), UploadActivity.class);
                    startActivity(intentadd);
                }
            }
        });
        fragmentoneViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 1 || position == 2) {
                    fragmentFristImgVedio.setVisibility(View.GONE);
                    fragmentoneTvAdd.setVisibility(View.VISIBLE);
                } else {
                    fragmentFristImgVedio.setVisibility(View.VISIBLE);
                    fragmentoneTvAdd.setVisibility(View.GONE);
                }
                FirstFragment.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initViews(View view) {
        mEaseConversationListFragment = new EaseConversationListFragment();
        mEaseConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
                if (conversation.getType() == EMConversation.EMConversationType.Chat) {
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseUser.TYPE_USER);
                } else if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseUser.TYPE_GROUP);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadNetData() {
//    mHelper = new XUtilsHelper(context, NetworkTools.NEWS_TITLE, new Handler(new Handler.Callback() {
//      @Override
//      public boolean handleMessage(Message msg) {
//        mHelper.hideProgress();
//        NewsTitleBean newsTitle = (NewsTitleBean) msg.obj;
//        if (newsTitle != null && newsTitle.getData().size() > 0) {
//          setUpViewPager(newsTitle.getData());
//        }
//        return false;
//      }
//    }));
//    mHelper.setRequestParams(new String[][]{});
//    mHelper.showProgress("加载中...");
//    mHelper.sendPostAuto(NewsTitleBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
//      for (EMMessage message : messages) {
//        EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//        final String action = cmdMsgBody.action();//获取自定义action
//        if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//          RedPacketUtil.receiveRedPacketAckMessage(message);
//        }
//      }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mEaseConversationListFragment != null) {
                    mEaseConversationListFragment.refresh();
                }
            }
        });
    }
}
