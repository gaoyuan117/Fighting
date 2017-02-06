
package me.kw.mall.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.util.CommonUtil;
import com.android.zcomponent.util.PageInditor;
import com.android.zcomponent.views.PullToRefreshView;
import com.android.zcomponent.views.PullToRefreshView.OnFooterRefreshListener;
import com.android.zcomponent.views.PullToRefreshView.OnHeaderRefreshListener;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.adapter.ReviewAdapter;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ReviewBusiness;
import service.api.ReviewItem;
import service.api.ReviewPageResult;
import service.entity.ReviewService;

@ZTitleMore(false)
public class ReviewActivity extends MallBaseActivity implements
    OnHeaderRefreshListener, OnFooterRefreshListener {
  @Bind(R.id.common_listview_show) ListView mListView;
  @Bind(R.id.common_pull_refresh_view_show) PullToRefreshView mPullToRefreshView;

  private PageInditor<ReviewItem> mPageInditor = new PageInditor<ReviewItem>();
  private ReviewAdapter mAdapter;
  private String mProductId;
  private int rate;

  @Override protected int getLayoutId() {
    return R.layout.activity_review;
  }

  @Override protected void initUI() {
    mPullToRefreshView.setOnHeaderRefreshListener(this);
    mPullToRefreshView.setOnFooterRefreshListener(this);
    mProductId = getIntent().getStringExtra("productid");
    getTitleBar().setTitleText("评价");
    getDataEmptyView().showViewWaiting();
    selectReviewRate(1);
  }

  @Override public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ReviewService.GetProductReviewRequest.class)) {
      if (null != mPageInditor) {
        mPageInditor.clear();
      }

      ReviewPageResult response = (ReviewPageResult) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        if (null != response.Data && null != response.Data.Result) {
          mPageInditor.add(response.Data.Result);
        }
        if (null == mAdapter) {
          mAdapter = new ReviewAdapter(this, mPageInditor.getAll());
          mPageInditor.bindAdapter(mListView, mAdapter);
        }

        if (mPageInditor.getAll().size() == response.Data.Total) {
          mPullToRefreshView.setFooterRefreshComplete();
        } else {
          mPullToRefreshView.setFooterVisible();
        }

        getDataEmptyView().dismiss();
      } else {
        if (mPageInditor.getAll().size() == 0) {
          String message = "";

          if (rate == 1) {
            message = "暂无好评";
          } else if (rate == 2) {
            message = "没有中评";
          } else if (rate == 3) {
            message = "没有差评";
          }
          getDataEmptyView().showViewDataEmpty(false, true, msg,
              message);
        }
      }
      mPullToRefreshView.onFooterRefreshComplete();
      mPullToRefreshView.onHeaderRefreshComplete();
    }
  }

  @OnClick(R.id.tvew_review_best)
  void onClickTvewBest() {
    selectReviewRate(1);
  }

  @OnClick(R.id.tvew_review_better)
  void onClickTvewBetter() {
    selectReviewRate(2);
  }

  @OnClick(R.id.tvew_review_bad)
  void onClickTvewBad() {
    selectReviewRate(3);
  }

  private void selectReviewRate(int rate) {
    this.rate = rate;
    TextView tvewRateBest = (TextView) findViewById(R.id.tvew_review_best);
    TextView tvewRateBetter = (TextView) findViewById(R.id.tvew_review_better);
    TextView tvewRateBad = (TextView) findViewById(R.id.tvew_review_bad);

    CommonUtil.setDrawableLeft(this, tvewRateBest, R.drawable.cart_option);
    CommonUtil.setDrawableLeft(this, tvewRateBetter, R.drawable.cart_option);
    CommonUtil.setDrawableLeft(this, tvewRateBad, R.drawable.cart_option);

    if (rate == 1) {
      CommonUtil.setDrawableLeft(this, tvewRateBest, R.drawable.cart_option_on);
    } else if (rate == 2) {
      CommonUtil.setDrawableLeft(this, tvewRateBetter, R.drawable.cart_option_on);
    } else if (rate == 3) {
      CommonUtil.setDrawableLeft(this, tvewRateBad, R.drawable.cart_option_on);
    }

    refreshData(true);
  }

  public void refreshData(boolean isPullRefresh) {
    mPageInditor.setPullRefresh(isPullRefresh);
    ReviewBusiness.queryReviews(getHttpDataLoader(), mProductId, rate, mPageInditor.getPageNum());
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {
    refreshData(false);
  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    refreshData(true);
  }
}