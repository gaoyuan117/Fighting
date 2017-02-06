
package me.kw.mall.activity;


import android.view.View;
import android.widget.ListView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.views.PullToRefreshView;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import me.kw.mall.adapter.ArticlesAdapter;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.HelpBusiness;
import service.api.ArticleItem;
import service.api.ArticlesPageResult;
import service.entity.HelpService;

@ZTitleMore(false)
public class HelpActivity extends MallBaseActivity {
  @Bind(R.id.common_listview_show) ListView mListView;
  @Bind(R.id.common_pull_refresh_view_show) PullToRefreshView mPullToRefreshView;

  @Override protected int getLayoutId() {
    return R.layout.activity_help;
  }

  @Override protected void initUI() {
    mPullToRefreshView.setHeaderInvisible();
    mPullToRefreshView.setFooterInvisible();
    mListView.setDividerHeight(0);
    getTitleBar().setTitleText("帮助与反馈");
    getTitleBar().showRightTextView("意见反馈");
    getDataEmptyView().showViewWaiting();
    HelpBusiness.queryArticles(getHttpDataLoader());
  }

  @Override
  public void onTitleBarRightFirstViewClick(View view) {
    getIntentHandle().intentToActivity(OptionActivity.class);
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(HelpService.ArticlesRequest.class)) {
      ArticlesPageResult response = (ArticlesPageResult) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        mListView.setAdapter(new ArticlesAdapter(this, new ArrayList<ArticleItem>(Arrays.asList
            (response.Data))));
        getDataEmptyView().dismiss();
      } else {
        getDataEmptyView().showViewDataEmpty(true, false, msg, "");
      }
    }
  }

}