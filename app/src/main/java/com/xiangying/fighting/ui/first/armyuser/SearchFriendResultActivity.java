package com.xiangying.fighting.ui.first.armyuser;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.TalkBeans;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.JsonParse;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFriendResultActivity extends BaseActivity {

  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.fragmentfind_edt)
  EditText fragmentfindEdt;
  @Bind(R.id.fragmenfind_tv_search)
  FontTextView fragmenfindTvSearch;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  LinearLayout titleBarCommonRvViewGroup;
  @Bind(R.id.common_listview_show)
  PullableListView commonListviewShow;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshLayout commonPullRefreshViewShow;

  private String keyWord = "", type = "friend";
  private CommonAdapter<TalkBeans> adaper;
  private ArrayList<TalkBeans> talkBeansArrayList = new ArrayList<>();
  private XUtilsHelper mSearchHelper;


  @Override
  protected void process() {
    keyWord = getIntent().getStringExtra("key");
    type = getIntent().getStringExtra("type");
    searchUserByKey(keyWord);
    fragmentfindEdt.setText(keyWord);
    adaper = new CommonAdapter<TalkBeans>(this, talkBeansArrayList, R.layout.fragmenttalk_child_item) {
      @Override
      public void convert(ViewHolder helper, final TalkBeans item, int position) {
        helper.setSimpleDraweeViewByUrl(R.id.fragmengtalk_child_item_img, item.getHeadimg());
        helper.setText(R.id.fragmengtalk_child_item_tv_name, item.getNickname());
        helper.setGone(R.id.fragmengtalk_child_item_tv_state);
        helper.setItemClick(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            FriendActivity.start(SearchFriendResultActivity.this, item.getUsername(), item);
          }
        });
      }
    };
    commonListviewShow.setAdapter(adaper);
  }

  private void searchUserByKey(String key) {
    if (mSearchHelper == null) {
      mSearchHelper = new XUtilsHelper(SearchFriendResultActivity.this, NetworkTools.HX_FIND_FRIEND, new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          adaper.clearDatas();
          mSearchHelper.hideProgress();
          try {
            String resut = msg.obj.toString();
            if (Utils.getResultCode(SearchFriendResultActivity.this, resut) == 1) {
              talkBeansArrayList = JsonParse.ParseUserList(resut);
              adaper.addmDatas(talkBeansArrayList);
              if (adaper.getCount() == 0) {
                Utils.toast(SearchFriendResultActivity.this, "没有搜索到相关用户");
              }
            }else{
              Utils.toast(SearchFriendResultActivity.this, "没有搜索到相关用户");
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          return false;
        }
      }));
    }
    mSearchHelper.setRequestParams(new String[][]{
        {"p", key}
    });
    mSearchHelper.showProgress(getString(R.string.loading));
    mSearchHelper.sendPost();

  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    fragmenfindTvSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        keyWord = fragmentfindEdt.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
          Utils.toast(SearchFriendResultActivity.this, "请输入关键字进行搜索");
          return;
        }
        searchUserByKey(keyWord);

      }
    });
  }

  @Override
  protected void initViews() {
    commonPullRefreshViewShow.setCanPullDown(false);
    commonPullRefreshViewShow.setCanPullUp(false);
    commonPullRefreshViewShow.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

      }

      @Override
      public void onLoadMore() {

      }
    });
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_search_friend_result);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

  }
}
