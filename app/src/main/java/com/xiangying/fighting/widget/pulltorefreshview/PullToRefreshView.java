
package com.xiangying.fighting.widget.pulltorefreshview;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.xiangying.fighting.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PullToRefreshView extends LinearLayout implements OnScrollListener
{

	private static final String TAG = "PullToRefreshView";

	// refresh states
	public static final int PULL_TO_REFRESH = 2;

	public static final int RELEASE_TO_REFRESH = 3;

	private static final int REFRESHING = 4;

	// pull state
	private static final int PULL_UP_STATE = 0;

	private static final int PULL_DOWN_STATE = 1;

	/**
	 * last y
	 */
	private float mLastMotionY;

	private float mLastMotionX;

	/**
	 * lock
	 */
	private boolean mLock;

	private boolean isContentAdapterViewScroll = true;

	/**
	 * header view
	 */
	private View mHeaderView;

	/**
	 * footer view
	 */
	private View mFooterView;
	
	
	/**
	 * list or grid
	 */
	private AdapterView<?> mAdapterView;

	/**
	 * scrollview
	 */
	private ScrollView mScrollView;

	/**
	 * header view height
	 */
	private int mHeaderViewHeight;

	/**
	 * footer view height
	 */
	private int mFooterViewHeight;

	/**
	 * header view image
	 */
	private ImageView mHeaderImageView;

	/**
	 * footer view image
	 */
	private ImageView mFooterImageView;

	/**
	 * header tip text
	 */
	private TextView mHeaderTextView;

	/**
	 * footer tip text
	 */
	private TextView mFooterTextView;

	private TextView mFooterComplete;

	private RelativeLayout mFooterRefresh;

	/**
	 * header refresh time
	 */
	private TextView mHeaderUpdateTextView;

	/**
	 * footer refresh time
	 */
	// private TextView mFooterUpdateTextView;
	/**
	 * header progress bar
	 */
	private ProgressBar mHeaderProgressBar;

	/**
	 * footer progress bar
	 */
	private ProgressBar mFooterProgressBar;

	/**
	 * layout inflater
	 */
	private LayoutInflater mInflater;

	/**
	 * header view current state
	 */
	private int mHeaderState;

	/**
	 * footer view current state
	 */
	private int mFooterState;

	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */
	private int mPullState;

	/**
	 * 变为向下的箭头,改变箭头方向
	 */
	private RotateAnimation mFlipUpAnimation;

	/**
	 * 变为逆向的箭头,旋转
	 */
	private RotateAnimation mReverseFlipUpAnimation;

	/**
	 * 变为向下的箭头,改变箭头方向
	 */
	private RotateAnimation mFlipDownAnimation;

	/**
	 * 变为逆向的箭头,旋转
	 */
	private RotateAnimation mReverseFlipDownAnimation;

	/**
	 * footer refresh listener
	 */
	private OnFooterRefreshListener mOnFooterRefreshListener;

	/**
	 * footer refresh listener
	 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/**
	 * last update time
	 */
	private String mLastUpdateTime;

	private int footerImageId;

	private int headerImageId;
	
	private boolean isShowHeaderView = true;

	private boolean isShowFooterView = true;

	/** 上拉加载更多 */
	public static final int LOAD_MORE_TYPE_PULL = 0;

	/** 自动加载更多 */
	public static final int LOAD_MORE_TYPE_AUTO = 1;

	private int loadMoreType = LOAD_MORE_TYPE_AUTO;

	public PullToRefreshView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public PullToRefreshView(Context context)
	{
		super(context);
		init();
	}

	/**
	 * init
	 * 
	 * @description
	 *            hylin 2012-7-26上午10:08:33
	 */
	private void init()
	{
		// Load all of the animations we need in code rather than through XML
		mFlipUpAnimation =
				new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF,
						0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipUpAnimation.setInterpolator(new LinearInterpolator());
		mFlipUpAnimation.setDuration(200);
		mFlipUpAnimation.setFillAfter(true);
		mReverseFlipUpAnimation =
				new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF,
						0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipUpAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipUpAnimation.setDuration(200);
		mReverseFlipUpAnimation.setFillAfter(true);

		mFlipDownAnimation =
				new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF,
						0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipDownAnimation.setInterpolator(new LinearInterpolator());
		mFlipDownAnimation.setDuration(200);
		mFlipDownAnimation.setFillAfter(true);
		mReverseFlipDownAnimation =
				new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF,
						0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipDownAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipDownAnimation.setDuration(200);
		mReverseFlipDownAnimation.setFillAfter(true);


		/**
		 * 刷新图标
		 */
		headerImageId = R.drawable.pull_to_refresh_arrow_down;//往下拉
		footerImageId =  R.drawable.pull_to_refresh_arrow_up;//往上拉
		
		mInflater = LayoutInflater.from(getContext());
		// header view 在此添加,保证是第一个添加到linearlayout的最上端
		addHeaderView();
	}

	private void addHeaderView()
	{
		// header view
		mHeaderView =
				mInflater.inflate(R.layout.pull_refresh_header, this,
						false);
		mHeaderImageView =
				(ImageView) mHeaderView
						.findViewById(R.id.pull_to_refresh_image);
		mHeaderTextView =
				(TextView) mHeaderView
						.findViewById(R.id.pull_to_refresh_text);
		mHeaderUpdateTextView =
				(TextView) mHeaderView
						.findViewById(R.id.pull_to_refresh_updated_at);
		mHeaderProgressBar =
				(ProgressBar) mHeaderView
						.findViewById(R.id.pull_to_refresh_progress);
		
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params =
				new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		addView(mHeaderView, params);
		mHeaderUpdateTextView.setText("最近更新于："
				+ new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
	}

	private void addFooterView()
	{
		// footer view
		mFooterView =
				mInflater.inflate(R.layout.pull_refresh_footer, this,
						false);
		mFooterImageView =
				(ImageView) mFooterView
						.findViewById(R.id.pull_to_load_image);
		mFooterTextView =
				(TextView) mFooterView
						.findViewById(R.id.pull_to_load_text);
		mFooterProgressBar =
				(ProgressBar) mFooterView
						.findViewById(R.id.pull_to_load_progress);
	mFooterComplete =
				(TextView) mFooterView
						.findViewById(R.id.pull_to_load_complete);
		mFooterRefresh =
				(RelativeLayout) mFooterView
						.findViewById(R.id.pull_to_refresh);
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params =
				new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
		// int top = getHeight();
		// params.topMargin
		// =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
		// getHeight()什么时候会赋值,稍候再研究一下
		// 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
		addView(mFooterView, params);
	}

	public void setHeaderColor(int color)
	{
		mHeaderTextView.setTextColor(color);
	}

	public void setFooterColor(int color)
	{
		mFooterTextView.setTextColor(color);
	}

	public void setHeadImageResource(int resId)
	{
		headerImageId = resId;
		mHeaderImageView.setImageResource(resId);
	}

	public void setFooterImageResource(int resId)
	{
		footerImageId = resId;
		mFooterImageView.setImageResource(resId);
	}
	
	/**
	 * <p>
	 * Description: 隐藏下拉刷新头部布局， 只有弹性效果，没有回调
	 * <p>
	 * 
	 * @date 2015-3-23
	 * @author wei
	 */
	public void setHeaderInvisible()
	{
		isShowHeaderView = false;
		if (!isShowHeaderView)
		{
			mHeaderView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * <p>
	 * Description: 隐藏上拉刷新底部布局， 只有弹性效果，没有回调
	 * <p>
	 * 
	 * @date 2015-3-23
	 * @author wei
	 */
	public void setFooterInvisible()
	{
		isShowFooterView = false;
		mFooterView.setVisibility(View.GONE);
	}

	/**
	 * <p>
	 * Description: 上拉刷新完成
	 * <p>
	 * 
	 * @date 2015-3-23
	 * @author wei
	 */
	public void setFooterRefreshComplete()
	{
		isShowFooterView = true;
		mFooterView.setVisibility(View.VISIBLE);
		mFooterComplete.setVisibility(View.VISIBLE);
		mFooterRefresh.setVisibility(View.GONE);
	}

	public void setFooterVisible()
	{
		isShowFooterView = true;
		mFooterView.setVisibility(View.VISIBLE);
		mFooterComplete.setVisibility(View.GONE);
		mFooterRefresh.setVisibility(View.VISIBLE);
	}

	public boolean isFooterRefreshComplete()
	{
		if (mFooterComplete.getVisibility() == View.VISIBLE)
		{
			return true;
		}

		return false;
	}

	/**
	 * 设置底部加载更多的类型,如果使用了AUTO,那么在获取更多数据成功后一定要调用setIsNoMore()
	 * ,通知该组件是否已无更多,否则会一直加载更多
	 * 
	 * @param loadMoreType
	 *            the loadMoreType to set
	 */
	public void setLoadMoreType(int loadMoreType)
	{
		this.loadMoreType = loadMoreType;
		setScrollListener();
	}

	private void setScrollListener()
	{

		// ListView 或者GridView ,才支持自动加载更多
		if (loadMoreType == LOAD_MORE_TYPE_AUTO && mAdapterView != null
				&& isShowFooterView)
		{
			if (mAdapterView instanceof ListView)
			{
				((ListView) mAdapterView).setOnScrollListener(this);
				Log.e(TAG, "setScrollListener ListView");
			}
			if (mAdapterView instanceof GridView)
			{
				((GridView) mAdapterView).setOnScrollListener(this);
				Log.e(TAG, "setScrollListener GridView");
			}
		}
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		// footer view 在此添加保证添加到linearlayout中的最后
		addFooterView();
		initContentAdapterView();
		setScrollListener();
	}

	/**
	 * init AdapterView like ListView,GridView and so on;or init ScrollView
	 * 
	 * @description hylin 2012-7-30下午8:48:12
	 */
	private void initContentAdapterView()
	{
		int count = getChildCount();
		if (count < 3)
		{
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i)
		{
			view = getChildAt(i);
			if (view instanceof AdapterView<?>)
			{
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView)
			{
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null)
		{
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child)
	{
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null)
		{
			p =
					new ViewGroup.LayoutParams(
							ViewGroup.LayoutParams.FILL_PARENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
		{
			childHeightSpec =
					MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		}
		else
		{
			childHeightSpec =
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e)
	{
		float y = e.getRawY();
		float x = e.getRawX();
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionY = y;
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(mLastMotionX - x) / Math.abs(mLastMotionY - y) < 1)
			{
				// deltaY > 0 是向下运动,< 0是向上运动
				int deltaInterceptY = (int) (y - mLastMotionY);
				if (isRefreshViewScroll(deltaInterceptY))
				{
					return true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			Log.d(TAG, "onInterceptTouchEvent pull up!");
			break;
		}
		return super.onInterceptTouchEvent(e);
	}

	/*
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	private int deltaY = 0;

	private boolean isHeaderRefresh = false;

	private boolean isFooterRefresh = false;

	public boolean isHeaderRefresh()
	{
		return isHeaderRefresh;
	}

	public void setHeaderRefresh(boolean isHeaderRefresh)
	{
		this.isHeaderRefresh = isHeaderRefresh;
	}

	public boolean isFooterRefresh()
	{
		return isFooterRefresh;
	}

	public void setFooterRefresh(boolean isFooterRefresh)
	{
		this.isFooterRefresh = isFooterRefresh;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (mLock)
		{
			return true;
		}
		float y = event.getRawY();
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent已经记录
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			deltaY = (int) (y - mLastMotionY);
			if (mPullState == PULL_DOWN_STATE)
			{
				// PullToRefreshView执行下拉
				headerPrepareToRefresh(deltaY);
			}
			else if (mPullState == PULL_UP_STATE)
			{
				// PullToRefreshView执行上拉
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE)
			{
				if (topMargin >= 0)
				{
					// 开始刷新
					headerRefreshing();
					if (!isShowHeaderView)
					{
						onHeaderRefreshComplete();
					}
					else
					{
						if (mOnHeaderRefreshListener != null)
						{
							setHeaderRefresh(true);
							mOnHeaderRefreshListener.onHeaderRefresh(this);
						}
					}
				}
				else
				{
					// 还没有执行刷新，重新隐藏
					// setHeaderTopMargin(-mHeaderViewHeight);
					smoothScrollTo(-mHeaderViewHeight,
							getPullToRefreshScrollDurationShorter());
				}
			}
			else if (mPullState == PULL_UP_STATE)
			{
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight)
				{
					// 开始执行footer 刷新
					footerRefreshing();
				}
				else
				{
					// 还没有执行刷新，重新隐藏
					// setHeaderTopMargin(-mHeaderViewHeight);
					smoothScrollTo(-mHeaderViewHeight,
							getPullToRefreshScrollDurationShorter());
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 是否应该到了父View,即PullToRefreshView滑动
	 * 
	 * @param deltaY
	 *            , deltaY > 0 是向下运动,< 0是向上运动
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY)
	{
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING)
		{
			return false;
		}
		// 对于ListView和GridView
		if (mAdapterView != null)
		{
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0)
			{
				View child = mAdapterView.getChildAt(0);
				if (child == null)
				{
					// 如果mAdapterView中没有数据,不拦截
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0)
				{
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8)
				{// 这里之前用3可以判断,但现在不行,还没找到原因
					mPullState = PULL_DOWN_STATE;
					return true;
				}
			}
			else if (deltaY < 0)
			{
				View lastChild =
						mAdapterView
								.getChildAt(mAdapterView.getChildCount() - 1);
				if (lastChild == null)
				{
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1
						&& (loadMoreType == LOAD_MORE_TYPE_PULL || (loadMoreType == LOAD_MORE_TYPE_AUTO && isFooterRefreshComplete())))
				{
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 对于ScrollView
		if (mScrollView != null)
		{
			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0)
			{
				mPullState = PULL_DOWN_STATE;
				return true;
			}
			else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY())
			{
				mPullState = PULL_UP_STATE;
				return true;
			}
		}

		return false;
	}

	private boolean mbVisibility = false;

	public void setHeaderUpdateTimeVisibility(boolean visibility)
	{
		mbVisibility = visibility;
	}

	/**
	 * header 准备刷新,手指移动过程,还没有释放
	 * 
	 * @param deltaY
	 *            手指滑动的距离
	 */
	private void headerPrepareToRefresh(int deltaY)
	{
		if (isShowHeaderView)
		{
			mHeaderView.setVisibility(View.VISIBLE);
		}
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH)
		{
			mHeaderTextView
					.setText(R.string.pull_to_refresh_release_label);
			if (mbVisibility)
			{
				mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			}
			if (mHeaderState == PULL_TO_REFRESH)
			{
				mHeaderImageView.clearAnimation();
				mHeaderImageView.startAnimation(mFlipDownAnimation);
				mHeaderState = RELEASE_TO_REFRESH;
			}
			if (null != mOnPullStateChangeListener)
			{
				mOnPullStateChangeListener.onScrollStateChange(mHeaderState);
			}
		}
		else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight)
		{// 拖动时没有释放
			if (mHeaderState == RELEASE_TO_REFRESH)
			{
				mHeaderImageView.clearAnimation();
				mHeaderImageView.startAnimation(mReverseFlipDownAnimation);
			}
			mHeaderTextView
					.setText(R.string.pull_to_refresh_pull_label);
			mHeaderState = PULL_TO_REFRESH;
			if (null != mOnPullStateChangeListener)
			{
				mOnPullStateChangeListener.onScrollStateChange(mHeaderState);
			}
		}
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY)
	{
		if (isShowFooterView)
		{
			mFooterView.setVisibility(View.VISIBLE);
		}
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH)
		{
			mFooterTextView
					.setText(R.string.pull_to_refresh_footer_release_label);
			if (mFooterState == PULL_TO_REFRESH)
			{
				mFooterImageView.clearAnimation();
				mFooterImageView.startAnimation(mFlipUpAnimation);
				mFooterState = RELEASE_TO_REFRESH;
			}
		}
		else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight))
		{
			if (mFooterState == RELEASE_TO_REFRESH)
			{
				mFooterImageView.clearAnimation();
				mFooterImageView.startAnimation(mReverseFlipUpAnimation);
			}
			mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
			mFooterState = PULL_TO_REFRESH;
		}
	}

	/**
	 * 修改Header view top margin的值
	 * 
	 * @description
	 * @param deltaY
	 * @return hylin 2012-7-31下午1:14:31
	 */
	private int changingHeaderViewTopMargin(int deltaY)
	{
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.4f;// 阻力
		// 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
		// 表示如果是在上拉后一段距离,然后直接下拉
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight)
		{
			return params.topMargin;
		}
		// 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug 取消注释会出现无限向下不能回复的BUG
		// if (deltaY < 0 && mPullState == PULL_DOWN_STATE
		// && Math.abs(params.topMargin) >= mHeaderViewHeight)
		// {
		// return params.topMargin;
		// }
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	public void headerFirstRefreshing()
	{
		if (isShowHeaderView)
		{
			mHeaderView.setVisibility(View.VISIBLE);
		}
		mHeaderState = REFRESHING;
		setHeaderRefresh(true);
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView
				.setText(R.string.pull_to_refresh_refreshing_label);
		mHeaderUpdateTextView.setVisibility(View.GONE);
	}

	/**
	 * header refreshing
	 * 
	 * @description hylin 2012-7-31上午9:10:12
	 */
	public void headerRefreshing()
	{
		if (isShowHeaderView)
		{
			mHeaderView.setVisibility(View.VISIBLE);
		}
		mHeaderState = REFRESHING;
		setHeaderRefresh(true);
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView
				.setText(R.string.pull_to_refresh_refreshing_label);
		if (mbVisibility)
		{
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * footer refreshing
	 * 
	 * @description hylin 2012-7-31上午9:09:59
	 */
	private void footerRefreshing()
	{
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		if (!isShowFooterView || mFooterRefresh.getVisibility() == View.GONE)
		{
			setFooterRefresh(true);
			onFooterRefreshComplete(-top);
		}
		else
		{
			setHeaderTopMargin(-top);
			mFooterImageView.setVisibility(View.GONE);
			mFooterImageView.clearAnimation();
			mFooterImageView.setImageDrawable(null);
			mFooterProgressBar.setVisibility(View.VISIBLE);
			mFooterTextView
					.setText(R.string.pull_to_refresh_footer_refreshing_label);
			mFooterTextView.setVisibility(View.VISIBLE);
			if (mOnFooterRefreshListener != null)
			{
				setFooterRefresh(true);
				mOnFooterRefreshListener.onFooterRefresh(this);
			}
		}
	}

	/**
	 * 设置header view 的topMargin的值
	 * 
	 * @description
	 * @param topMargin
	 *            ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
	 *            hylin 2012-7-31上午11:24:06
	 */
	private void setHeaderTopMargin(int topMargin)
	{
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	/**
	 * header view 完成更新后恢复初始状态
	 * 
	 * @description hylin 2012-7-31上午11:54:23
	 */
	public void onHeaderRefreshComplete()
	{
		if (!isHeaderRefresh())
		{
			return;
		}
		// setHeaderTopMargin(-mHeaderViewHeight);
		smoothScrollTo(-mHeaderViewHeight);
		mHeaderView.setVisibility(View.INVISIBLE);
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView
				.setImageResource(headerImageId);
		mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
		mHeaderProgressBar.setVisibility(View.GONE);
		if (mbVisibility)
		{
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderUpdateTextView.setText("最近更新于："
					+ new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
		}
		mHeaderState = PULL_TO_REFRESH;
		if (null != mOnPullStateChangeListener)
		{
			mOnPullStateChangeListener.onScrollStateChange(mHeaderState);
		}
		setHeaderRefresh(false);
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onHeaderRefreshComplete(CharSequence lastUpdated)
	{
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	/**
	 * footer view 完成更新后恢复初始状态
	 */
	public void onFooterRefreshComplete()
	{
		// smoothScrollTo(-mHeaderViewHeight);
		onFooterRefreshComplete(-mHeaderViewHeight);
	}

	private void onFooterRefreshComplete(int toPosition)
	{
		if (!isFooterRefresh())
		{
			return;
		}
		smoothScrollTo(toPosition);
		if (mFooterRefresh.getVisibility() != View.GONE)
		{
			mFooterView.setVisibility(View.INVISIBLE);
		}
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView
				.setImageResource(footerImageId);
		mFooterTextView
				.setText(R.string.pull_to_refresh_footer_pull_label);
		mFooterProgressBar.setVisibility(View.GONE);
		mFooterState = PULL_TO_REFRESH;
		if (isContentAdapterViewScroll)
		{
			postDelayed(mAutoRunable, 200);
		}
		setFooterRefresh(false);
		
		if (loadMoreType == LOAD_MORE_TYPE_AUTO)
		{
			mFooterImageView.setVisibility(View.GONE);
			mFooterProgressBar.setVisibility(View.GONE);
			mFooterTextView.setVisibility(View.GONE);
		}
	}

	public void setContentAdapterViewScroll(boolean isScroll)
	{
		this.isContentAdapterViewScroll = isScroll;
	}

	private AutoRunable mAutoRunable = new AutoRunable();

	private class AutoRunable implements Runnable
	{

		@Override
		public void run()
		{
			contentAdapterViewScroll();
		}
	}

	private void contentAdapterViewScroll()
	{
		if (Build.VERSION.SDK_INT < 8)
		{
			return;
		}
		if (null != mAdapterView)
		{
			int childHeight =
					(int) (50 * MyLayoutAdapter.getInstance().getDensityRatio());
			;

			if (mAdapterView instanceof ListView)
			{
				((ListView) mAdapterView).smoothScrollBy(childHeight, 0);
			}
			else if (mAdapterView instanceof GridView)
			{
				((GridView) mAdapterView).smoothScrollBy(childHeight, 0);
			}
		}
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated)
	{
		if (lastUpdated != null)
		{
			if (mbVisibility)
			{
				mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			}
			mHeaderUpdateTextView.setText(lastUpdated);
		}
		else
		{
			mHeaderUpdateTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取当前header view 的topMargin
	 * 
	 * @description
	 * @return hylin 2012-7-31上午11:22:50
	 */
	private int getHeaderTopMargin()
	{
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	/**
	 * lock
	 * 
	 * @description hylin 2012-7-27下午6:52:25
	 */
	private void lock()
	{
		mLock = true;
	}

	/**
	 * unlock
	 * 
	 * @description hylin 2012-7-27下午6:53:18
	 */
	private void unlock()
	{
		mLock = false;
	}

	/**
	 * set headerRefreshListener
	 * 
	 * @description
	 * @param headerRefreshListener
	 *            hylin 2012-7-31上午11:43:58
	 */
	/**
	 * <p>
	 * Description: 下拉刷新监听
	 * <p>
	 * 
	 * @date 2015-3-23
	 * @author wei
	 * @param headerRefreshListener
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener)
	{
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	/**
	 * <p>
	 * Description: 上拉刷新监听
	 * <p>
	 * 
	 * @date 2015-3-23
	 * @author wei
	 * @param footerRefreshListener
	 */
	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener)
	{
		mOnFooterRefreshListener = footerRefreshListener;
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 */
	public interface OnFooterRefreshListener
	{

		public void onFooterRefresh(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener
	{

		public void onHeaderRefresh(PullToRefreshView view);
	}

	public interface OnPullStateChangeListener
	{

		/**
		 * <p>
		 * Description: 滑动状态监听，
		 * <p>
		 * 
		 * @date 2014-4-16
		 * @author WEI
		 * @param state
		 *            向上滑动 {@value # SCROLL_STATE_UP} 向下滑动
		 *            {@value # SCROLL_STATE_DOWN} 起始位置时，向上滑动
		 *            {@value # SCROLL_STATE_TOP_DOWN} 最低端位置时，向下滑动
		 *            {@value # SCROLL_STATE_BOTTOM_UP}
		 */
		public void onScrollStateChange(int state);
	}

	private OnPullStateChangeListener mOnPullStateChangeListener;

	/**
	 * <p>
	 * Description: Set listener for scroll status changed,
	 * <p>
	 * 
	 * @date 2014-4-16
	 * @author WEI
	 * @param onPullStateChangeListener
	 *            Listener for scroll status changed
	 */
	public void setOnPullStateChangeListener(
			OnPullStateChangeListener onPullStateChangeListener)
	{
		mOnPullStateChangeListener = onPullStateChangeListener;
	}

	// -----------------------------------测试动画
	private Interpolator mScrollAnimationInterpolator;

	private SmoothScrollRunnable mCurrentSmoothScrollRunnable;

	public static final int SMOOTH_SCROLL_SHORT_DURATION_MS = 150;

	public static final int SMOOTH_SCROLL_DURATION_MS = 250;

	public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;

	protected int getPullToRefreshScrollDurationShorter()
	{
		return SMOOTH_SCROLL_SHORT_DURATION_MS;
	}

	protected int getPullToRefreshScrollDuration()
	{
		return SMOOTH_SCROLL_DURATION_MS;
	}

	protected int getPullToRefreshScrollDurationLonger()
	{
		return SMOOTH_SCROLL_LONG_DURATION_MS;
	}

	/**
	 * Smooth Scroll to position using the default duration of
	 * {@value #SMOOTH_SCROLL_DURATION_MS} ms.
	 * 
	 * @param scrollValue
	 *            - Position to scroll to
	 */
	protected final void smoothScrollTo(int scrollValue)
	{
		smoothScrollTo(scrollValue, getPullToRefreshScrollDuration());
	}

	/**
	 * Smooth Scroll to position using the default duration of
	 * {@value #SMOOTH_SCROLL_DURATION_MS} ms.
	 * 
	 * @param scrollValue
	 *            - Position to scroll to
	 * @param listener
	 *            - Listener for scroll
	 */
	protected final void smoothScrollTo(int scrollValue,
			OnSmoothScrollFinishedListener listener)
	{
		smoothScrollTo(scrollValue, getPullToRefreshScrollDuration(), 0,
				listener);
	}

	/**
	 * Smooth Scroll to position using the longer default duration of
	 * {@value #SMOOTH_SCROLL_LONG_DURATION_MS} ms.
	 * 
	 * @param scrollValue
	 *            - Position to scroll to
	 */
	protected final void smoothScrollToLonger(int scrollValue)
	{
		smoothScrollTo(scrollValue, getPullToRefreshScrollDurationLonger());
	}

	/**
	 * Smooth Scroll to position using the specific duration
	 * 
	 * @param scrollValue
	 *            - Position to scroll to
	 * @param duration
	 *            - Duration of animation in milliseconds
	 */
	private final void smoothScrollTo(int scrollValue, long duration)
	{
		smoothScrollTo(scrollValue, duration, 0, null);
	}

	/***
	 * 回缩动画
	 */
	private final void smoothScrollTo(int newScrollValue, long duration,
			long delayMillis, OnSmoothScrollFinishedListener listener)
	{
		if (null != mCurrentSmoothScrollRunnable)
		{
			mCurrentSmoothScrollRunnable.stop();
		}
		newScrollValue = -mHeaderViewHeight;
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		final int oldScrollValue = params.topMargin;
		Log.d(TAG, "oldScrollValue:" + oldScrollValue);
		Log.d(TAG, "newScrollValue:" + newScrollValue);
		if (oldScrollValue != newScrollValue)
		{
			if (null == mScrollAnimationInterpolator)
			{
				mScrollAnimationInterpolator = new LinearInterpolator();
			}
			mCurrentSmoothScrollRunnable =
					new SmoothScrollRunnable(oldScrollValue, newScrollValue,
							duration, listener);
			if (delayMillis > 0)
			{
				postDelayed(mCurrentSmoothScrollRunnable, delayMillis);
			}
			else
			{
				post(mCurrentSmoothScrollRunnable);
			}
		}
	}

	final class SmoothScrollRunnable implements Runnable
	{

		private OnSmoothScrollFinishedListener mListener;

		private final Interpolator mInterpolator;

		private final int mScrollToY;

		private final int mScrollFromY;

		private final long mDuration;

		private boolean mContinueRunning = true;

		private long mStartTime = -1;

		private int mCurrentY = -1;

		public SmoothScrollRunnable(int fromY, int toY, long duration,
				OnSmoothScrollFinishedListener listener)
		{
			Log.d(TAG, "fromY:" + fromY);
			Log.d(TAG, "toY:" + toY);
			Log.d(TAG, "duration:" + duration);
			mScrollFromY = fromY;
			mScrollToY = toY;
			mInterpolator = mScrollAnimationInterpolator;
			mDuration = duration;
			mListener = listener;
		}

		@Override
		public void run()
		{
			/**
			 * Only set mStartTime if this is the first time we're starting,
			 * else actually calculate the Y delta
			 */
			if (mStartTime == -1)
			{
				mStartTime = System.currentTimeMillis();
			}
			else
			{
				/**
				 * We do do all calculations in long to reduce software float
				 * calculations. We use 1000 as it gives us good accuracy and
				 * small rounding errors
				 */
				long normalizedTime =
						(1000 * (System.currentTimeMillis() - mStartTime))
								/ mDuration;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
				final int deltaY =
						Math.round((mScrollFromY - mScrollToY)
								* mInterpolator
										.getInterpolation(normalizedTime / 1000f));
				mCurrentY = mScrollFromY - deltaY;
				// setHeaderScroll(mCurrentY);
				final int maximumPullScroll = getMaximumPullScroll();
				int value =
						Math.min(maximumPullScroll,
								Math.max(-maximumPullScroll, mCurrentY));
				// Log.d(TAG, "value:" + value);
				// 动态设置HeadView的位置 达到动画效果
				LayoutParams params =
						(LayoutParams) mHeaderView.getLayoutParams();
				params.topMargin = value;
				mHeaderView.setLayoutParams(params);
				mHandler.sendEmptyMessage(0);
			}
			// If we're not at the target Y, keep going...
			if (mContinueRunning && mScrollToY != mCurrentY)
			{
				ViewCompat.postOnAnimation(PullToRefreshView.this, this);
			}
			else
			{
				if (null != mListener)
				{
					mListener.onSmoothScrollFinished();
				}
			}
		}

		public void stop()
		{
			mContinueRunning = false;
			removeCallbacks(this);
		}
	}

	private Handler mHandler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			invalidate();
		}
	};

	static final float FRICTION = 2.0f;

	private int getMaximumPullScroll()
	{
		return Math.round(getHeight() / FRICTION);
	}

	static interface OnSmoothScrollFinishedListener
	{

		void onSmoothScrollFinished();
	}

	/** 最后的可视项索引 */
	private int visibleLastIndex = 0;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		// ListView 的FooterView也会算到visibleItemCount中去，所以如果存在footview需要再减去一
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == view.getCount() - 1
				&& mFooterState != REFRESHING && isShowFooterView
				&& !isFooterRefreshComplete())
		{
			Log.e(TAG, " 开始执行footer 刷新");
			// 开始执行footer 刷新
			footerRefreshing();
		}
	}
}