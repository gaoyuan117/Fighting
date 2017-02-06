
package me.kw.mall.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.util.CommonUtil;
import com.android.zcomponent.util.ListUtil;
import com.android.zcomponent.util.MyLayoutAdapter;
import com.android.zcomponent.util.PageInditor;
import com.android.zcomponent.util.ValidateUtil;
import com.android.zcomponent.views.TimeTextView;
import com.android.zcomponent.views.pulltorefresh.PullToRefreshBase;
import com.android.zcomponent.views.pulltorefresh.PullToRefreshScrollView;
import com.xiangying.fighting.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.kw.mall.adapter.PanicBuyingAdapter;
import me.kw.mall.adapter.PanicTimeAdapter;
import me.kw.mall.enumerate.PaincBuyType;
import me.kw.mall.enumerate.PaincTimeType;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProductBusiness;
import service.api.AdProduct;
import service.api.PaincTimes;
import service.api.ProductItem;
import service.api.Products;
import service.entity.ProductService;

public class PaincBuyingFragment extends MallBaseFragment implements
    PullToRefreshBase.OnHeaderRefreshListener<ScrollView>, PullToRefreshBase
    .OnFooterRefreshListener<ScrollView> {

  @Bind(R.id.llayout_time_show) Gallery mGalleryTime;
  @Bind(R.id.llayout_time_end_show) RelativeLayout mllayoutTimeEnd;
  @Bind(R.id.common_listview_show) ListView mListView;
  @Bind(R.id.common_pull_refresh_view_show) PullToRefreshScrollView mPullToRefreshView;
  /**
   * 距离结束时间计时
   */
  @Bind(R.id.tvew_time_show) TimeTextView mTvewTime;
  /**
   * 抢购时间状态
   */
  @Bind(R.id.tvew_end_time_title_show) TextView mTvewTimeTitle;
  @Bind(R.id.llayout_time_bg_show) LinearLayout mLlayoutTimeBg;
  @Bind(R.id.imgvew_time_arrow_show) ImageView mImgvewTimeArrow;
  @Bind(R.id.flayout_slider_image) FrameLayout mflayoutImage;
  private PaincBuyType mPaincBuyType;
  private PageInditor<ProductItem> mPageInditor = new PageInditor<ProductItem>();
  private List<PaincTimes.Data> mlistPaincTimes = new ArrayList<PaincTimes.Data>();
  private PanicBuyingAdapter mPanicBuyingAdapter;
  private PanicTimeAdapter mPanicTimeAdapter;
  private boolean isPaincStarted = true;
  private long panicId;

  @Override protected int getLayoutId() {
    return R.layout.fragment_painc_buying;
  }

  @Override protected void initUI() {
    getDataEmptyView().setBackgroundResource(R.drawable.transparent);

    mPullToRefreshView.setOnHeaderRefreshListener(this);
    mPullToRefreshView.setOnFooterRefreshListener(this);
    if (mPaincBuyType == PaincBuyType.BUYING) {
      //查询抢购时间表
      ProductBusiness.queryPaincTime(getHttpDataLoader(), PaincTimeType.CURRDAY.getValue());
    } else if (mPaincBuyType == PaincBuyType.COUPON) {
      //折扣最多
      dismissPaincTimeView();
    } else if (mPaincBuyType == PaincBuyType.TIME) {
      mflayoutImage.setVisibility(View.GONE);
      //最后疯抢
      dismissPaincTimeView();
    }

    mGalleryTime.setCallbackDuringFling(false);

    mGalleryTime.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
        setEndTime(position);
        mPanicTimeAdapter.setSelectPosition(position);
        refreshData(true);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {}
    });
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (mPaincBuyType == PaincBuyType.BUYING) {
      ProductBusiness.queryPaincTime(getHttpDataLoader(), PaincTimeType.CURRDAY.getValue());
    } else {
      refreshData(true);
    }

    if (mPaincBuyType == PaincBuyType.BUYING) {
      ProductBusiness.queryAdProducts(getHttpDataLoader(), "panic_banner");
    } else if (mPaincBuyType == PaincBuyType.COUPON) {
      ProductBusiness.queryAdProducts(getHttpDataLoader(), "panic_banner");
    }

    getDataEmptyView().showViewWaiting();
  }

  private void dismissPaincTimeView() {
    mllayoutTimeEnd.setVisibility(View.GONE);
    mLlayoutTimeBg.setVisibility(View.GONE);
    mImgvewTimeArrow.setVisibility(View.GONE);
    mGalleryTime.getLayoutParams().height = 1;
  }

  private void setEndTime(int position) {
    long serverTime = Endpoint.serverDate().getTime();
    long startTime =
        Long.parseLong(mlistPaincTimes.get(position).start_time) * 1000;
    long endTime =
        Long.parseLong(mlistPaincTimes.get(position).end_time) * 1000;

    int seconds = 0;

    if (serverTime >= startTime && serverTime < endTime) {
      // 正在进行
      seconds = ProductBusiness.getDiffTime(mlistPaincTimes.get(position).end_time);
      mTvewTimeTitle.setText("距离本场结束：");
      isPaincStarted = true;
    } else if (serverTime < startTime) {
      // 即将开始
      seconds = ProductBusiness.getDiffTime(mlistPaincTimes.get(position).start_time);
      mTvewTimeTitle.setText("距离本场开始：");
      isPaincStarted = false;
    } else {
      // 已开始
      isPaincStarted = true;
    }
    panicId = Long.parseLong(mlistPaincTimes.get(position).id);
    mTvewTime.setTextColor(getResources().getColor(R.color.white), getResources().getColor(R
        .color.black));
    mTvewTime.setTimes(seconds);
    if (!mTvewTime.isRun()) {
      mTvewTime.run();
    }
  }

  private void refreshData(boolean isPullRefresh) {
    mPageInditor.setPullRefresh(isPullRefresh);

    if (mPaincBuyType == PaincBuyType.BUYING) {
      ProductBusiness.queryPanicProducts(getHttpDataLoader(), 0, 0, panicId, 0, mPageInditor
          .getPageNum());
    } else if (mPaincBuyType == PaincBuyType.COUPON) {
      ProductBusiness.queryPanicProducts(getHttpDataLoader(), 0, 1, 0, 0, mPageInditor.getPageNum
          ());
    } else if (mPaincBuyType == PaincBuyType.TIME) {
      ProductBusiness.queryPanicProducts(getHttpDataLoader(), 0, 0, 0, Endpoint.serverDate()
          .getTime() / 1000, mPageInditor.getPageNum());
    }
  }

  private void showViewDataEmpty(MessageData msg, String message) {
    if (mPaincBuyType == PaincBuyType.BUYING) {
      getDataEmptyView().getView().getLayoutParams().height =
          (int) (280 * MyLayoutAdapter.getInstance().getDensityRatio());
    }
    mPullToRefreshView.onRefreshComplete();
    getDataEmptyView().showViewDataEmpty(false, false, msg, message);
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ProductService.PanicTimesRequest.class)) {
      PaincTimes response = msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS && null != response.Data) {
          if (mPaincBuyType == PaincBuyType.BUYING) {
            mlistPaincTimes = ListUtil.arrayToList(response.Data);
            ProductBusiness.formatPaincingTime(mlistPaincTimes);
            mPanicTimeAdapter = new PanicTimeAdapter(getActivity(), mlistPaincTimes);
            mGalleryTime.setAdapter(mPanicTimeAdapter);
            int position = ProductBusiness.getCurrentPaincingTime(mlistPaincTimes);
            setEndTime(position);
            mGalleryTime.setSelection(position);
            mPanicTimeAdapter.setSelectPosition(position);
            ProductBusiness.queryPanicProducts(getHttpDataLoader(), 0, 0, panicId, 0,
                mPageInditor.getPageNum());
          } else if (mPaincBuyType == PaincBuyType.TIME) {
          }
        } else {
          showViewDataEmpty(msg, response.message);
        }
      } else {
        showViewDataEmpty(msg, getString(R.string.common_data_empty));
      }
    } else if (msg.valiateReq(ProductService.PanicProductsRequest.class)) {
      mPullToRefreshView.onRefreshComplete();
      mPageInditor.clear();
      Products responseProduct = msg.getRspObject();
      if (ProductBusiness.validateQueryProducts(responseProduct)) {
        mPageInditor.add(responseProduct.Data.Results);

        if (null == mPanicBuyingAdapter) {
          mPanicBuyingAdapter = new PanicBuyingAdapter(getActivity(), mPageInditor.getAll());
          mPageInditor.bindAdapter(mListView, mPanicBuyingAdapter);
        } else {
          mPanicBuyingAdapter.notifyDataSetChanged();
        }
        mPanicBuyingAdapter.setStartPanic(isPaincStarted);
        if (mPageInditor.getAll().size() == responseProduct.Data.Total) {
          mPullToRefreshView.setFooterRefreshComplete();
        } else {
          mPullToRefreshView.setFooterRefresh();
        }
        CommonUtil.setListViewHeightBasedOnChildren(mListView);
        getDataEmptyView().dismiss();
      } else {
        if (!ValidateUtil.isListEmpty(mPageInditor.getAll())) {
          getDataEmptyView().setVisibility(View.GONE);
        } else {
          String message = ProductBusiness.validateQueryState(responseProduct, getString(R.string
              .common_data_empty));
          if (mPaincBuyType == PaincBuyType.BUYING) {
            getDataEmptyView().getView().getLayoutParams().height = (int) (280 * MyLayoutAdapter
                .getInstance()
                .getDensityRatio());
          }
          getDataEmptyView().showViewDataEmpty(false, false, msg, message);
        }
      }
    } else if (msg.valiateReq(ProductService.AdvertRequest.class)) {
      AdProduct response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        ProductBusiness.bindSliderLayout(this, R.id.flayout_slider_image, response.Data);
      } else {
      }
    }
  }

  public void setPaincBuyType(PaincBuyType paincBuyType) {
    mPaincBuyType = paincBuyType;
  }

  @Override
  public void onFooterRefresh(PullToRefreshBase<ScrollView> view) {
    refreshData(false);
  }

  @Override
  public void onHeaderRefresh(PullToRefreshBase<ScrollView> view) {
    refreshData(true);
  }

}