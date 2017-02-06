package com.xiangying.fighting.ui.first.talks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.TalkGroup;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.chat.ChatActivity;
import com.xiangying.fighting.ui.first.armygroup.CreatGroupActivity;
import com.xiangying.fighting.utils.DateUtils;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableExpandableListView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TalkFragment extends BaseFragment {
  @Bind(R.id.fragmenttalk_group)
  PullableExpandableListView fragmenttalkGroup;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshLayout fragmenttalkGroup_pull;

  ArrayList<TalkGroup> gruops = new ArrayList<>();
  ArrayList<ArrayList<EaseUser>> childs = new ArrayList<>();

  private MyExpandableAdapter myExpandableAdapter;

  public static Fragment newInstance() {
    Fragment fragment = new TalkFragment();
    return fragment;
  }

  @Override
  protected void process() {
    gruops = TalkGroup.getTalkGroups();
    childs.add(new ArrayList<EaseUser>());
    childs.add(new ArrayList<EaseUser>());

    MyExpandableAdapter myExpandableAdapter = new MyExpandableAdapter(gruops, childs, getActivity());
    fragmenttalkGroup.setAdapter(myExpandableAdapter);
  }

  @Override
  protected void setAllClick() {
    fragmenttalkGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent;
        EaseUser talkBeans = childs.get(groupPosition).get(childPosition);
        intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, talkBeans.getUsername());
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, talkBeans.getType());
        startActivity(intent);
        return false;
      }
    });
    fragmenttalkGroup.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (gruops.get(groupPosition).getName()) {
          case "创建军团":
            Intent intentcreat = new Intent(getContext(), CreatGroupActivity.class);
            startActivity(intentcreat);
            break;
          case "添加分队":
            Intent intentadd = new Intent(getContext(), CreatGroupActivity.class);
            intentadd.putExtra("element", "");
            startActivity(intentadd);
            break;
        }
        return false;
      }
    });

    fragmenttalkGroup_pull.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fragmenttalkGroup_pull.postDelayed(new Runnable() {
          @Override
          public void run() {
            fragmenttalkGroup_pull.refreshFinish(DateUtils.getTimestampString(new Date(System.currentTimeMillis())));
          }
        }, 2000);

      }

      @Override
      public void onLoadMore() {

      }
    });
  }

  @Override
  protected void initViews(View view) {
    fragmenttalkGroup.setCanPullUp(false);
  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View rootView = inflater.inflate(R.layout.fragment_talk, null);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  protected void loadNetData() {
//    XUtilsHelper mylist = new XUtilsHelper(context, NetworkTools.MY_FRIEND_AND_GROUP_LIST, new Handler(new Handler.Callback() {
//      @Override
//      public boolean handleMessage(Message msg) {
//        String result = msg.obj.toString();
//        if (Utils.getResultCode(context, result) == 1) {
//
//        }
//        return false;
//      }
//    }));
//    mylist.setRequestParams(new String[][]{{"uid", BaseApplication.instance.getUid()},
//    });
//    mylist.sendPost();
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
