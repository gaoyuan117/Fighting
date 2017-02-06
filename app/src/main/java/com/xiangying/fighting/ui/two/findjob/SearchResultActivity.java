package com.xiangying.fighting.ui.two.findjob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.utils.ScreenUtils;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.common.SystemState;
import com.xiangying.fighting.ui.two.findjob.bean.JobListBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity
    implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_tv_search)
  FontTextView titleBarCommonTvSearch;
  @Bind(R.id.title_bar_common_et_search)
  ClearEditText titleBarCommonEtSearch;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.activity_findrent_tv_quyu)
  FontTextView activityFindrentTvQuyu;
  @Bind(R.id.activity_findrent_img_quyu)
  ImageView activityFindrentImgQuyu;
  @Bind(R.id.activity_findrent_rl_quyu)
  RelativeLayout activityFindrentRlQuyu;
  @Bind(R.id.activity_findrent_tv_xingzi)
  FontTextView activityFindrentTvXingzi;
  @Bind(R.id.activity_findrent_img_xingzi)
  ImageView activityFindrentImgXingzi;
  @Bind(R.id.activity_findrent_rl_xingzi)
  RelativeLayout activityFindrentRlXingzi;
  @Bind(R.id.activity_findrent_tv_money)
  FontTextView activityFindrentTvMoney;
  @Bind(R.id.activity_findrent_img_money)
  ImageView activityFindrentImgMoney;
  @Bind(R.id.activity_findrent_rl_money)
  RelativeLayout activityFindrentRlMoney;
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshView commonPullRefreshViewShow;

  PopupWindow popArea;
  PopupWindow popXingzi;

  CommonAdapter<JobListBean.Data> adaper;
  ArrayList<JobListBean.Data> dataList = new ArrayList<>();
  /**
   * 城市数据
   */
  static ArrayList<RegionInfo> item1;
  static ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();

  int index = 1;

  Handler handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      quyuAdapter.setmDatas(item2.get(index));
      return true;
    }
  });
  private CommonAdapter<RegionInfo> quyuAdapter;

  public static void start(Context context, String text) {
    Intent intent = new Intent(context, SearchResultActivity.class);
    intent.putExtra("SearchText", text);
    context.startActivity(intent);
  }

  @Override
  protected void process() {

    //开启线程处理数据
    new dellDataThread().start();

    //为listView设置adapter
    adaper = new CommonAdapter<JobListBean.Data>(this, dataList, R.layout.item_job) {
      @Override
      public void convert(ViewHolder helper, final JobListBean.Data item, int position) {
        helper.setText(R.id.item_job_tv_position, item.getWork());
        helper.setText(R.id.item_job_tv_company, item.getCompany());
        helper.setText(R.id.item_job_tv_location, item.getWorkplace());
        helper.setText(R.id.item_job_tv_salary, item.getTreatment().replace(",", "-"));
        helper.setText(R.id.item_job_tv_date,item.getTime());
        helper.setItemClick(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            JobDetailActivity.start(SearchResultActivity.this, item.getId());
          }
        });
      }
    };
    commonListviewShow.setAdapter(adaper);

    popArea = new PopupWindow(this);
    popXingzi = new PopupWindow(this);
    setUpPopArea();
    setUpPopXingzi();
  }


  View viewQuYu;
  GridView gridview;
  View quyuViewQuyu;
  View quyuViewXingzi;
  View quyuViewMoney;

  private void setUpPopArea() {

    viewQuYu = LayoutInflater.from(this).inflate(R.layout.popup_quyu2, null);
    gridview = (GridView) viewQuYu.findViewById(R.id.popup_quyu_gridview);
    quyuViewQuyu = viewQuYu.findViewById(R.id.popup_quyu_quyu);
    quyuViewXingzi = viewQuYu.findViewById(R.id.popup_quyu_xingzi);
    quyuViewMoney = viewQuYu.findViewById(R.id.popup_quyu_money);

    final ArrayList<RegionInfo> areaList = new ArrayList<>();

    quyuAdapter = new CommonAdapter<RegionInfo>(this, areaList, R.layout.item_quyu) {
      @Override
      public void convert(ViewHolder helper, RegionInfo item, int position) {
        helper.setText(R.id.item_quyu_tvquyu, item.getName());
      }
    };
    gridview.setAdapter(quyuAdapter);

    popArea.setContentView(viewQuYu);
    popArea.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
    popArea.setFocusable(true);
    popArea.setTouchable(true);

    popArea.setHeight((int) (ScreenUtils.getScreenH(this) * 1.2 / 4));
    popArea.setWidth(ScreenUtils.getScreenW(this));

    popArea.setBackgroundDrawable(new BitmapDrawable());
    backgroundAlpha(1f);

    popArea.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        activityFindrentTvQuyu.setTextColor(Color.rgb(32, 32, 32));
        backgroundAlpha(1f);
      }
    });

    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        activityFindrentTvQuyu.setText(quyuAdapter.getItem(position).getName());
        requestData();
        popArea.dismiss();
      }
    });

    quyuViewXingzi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popArea.dismiss();
        showPopWindow(2);
      }
    });
    quyuViewMoney.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popArea.dismiss();
        showPopWindow(3);
      }
    });
  }

  public void showPopWindow(int choice) {
    switch (choice) {
      case 1:
        if (popArea.isShowing()) {
          popArea.dismiss();
        } else {
          popArea.showAsDropDown(findViewById(R.id.title_bar_common_rv_viewGroup));
          popArea.setAnimationStyle(-1);
          activityFindrentTvQuyu.setTextColor(Color.rgb(184, 0, 18));
        }
        break;
      case 2:
        if (popXingzi.isShowing()) {
          popXingzi.dismiss();
        } else {
          popXingzi.showAsDropDown(findViewById(R.id.title_bar_common_rv_viewGroup));
          popXingzi.setAnimationStyle(-1);
          activityFindrentTvXingzi.setTextColor(Color.rgb(184, 0, 18));
        }
        break;

    }
  }


  View viewXingzi;
  ListView xingziListView;
  View xingziViewquyu;
  View xingziViewXingzi;
  View xingziViewMoney;

  private void setUpPopXingzi() {

    viewXingzi = LayoutInflater.from(this).inflate(R.layout.popup_xingzi, null);
    xingziListView = (ListView) viewXingzi.findViewById(R.id.popup_xingzi_listview);
    xingziViewquyu = viewXingzi.findViewById(R.id.popup_xingzi_quyu);
    xingziViewXingzi = viewXingzi.findViewById(R.id.popup_xingzi_xingzi);
    xingziViewMoney = viewXingzi.findViewById(R.id.popup_xingzi_money);

    final ArrayList<String> xingziList = new ArrayList<>();
    xingziList.add("全部薪资");
    xingziList.add("2000元以下");
    xingziList.add("2000-3000元");
    xingziList.add("3000-5000元");
    xingziList.add("5000-6000元");
    xingziList.add("6000-8000元");
    xingziList.add("8000元以上");

    CommonAdapter<String> xingziAdapter = new CommonAdapter<String>(this, xingziList, R.layout.item_xingzi) {
      @Override
      public void convert(ViewHolder helper, String item, int position) {
        helper.setText(R.id.item_xingzi_tv_xingzi, item);
      }
    };
    xingziListView.setAdapter(xingziAdapter);


    popXingzi.setContentView(viewXingzi);
    popXingzi.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
    popXingzi.setFocusable(true);
    popXingzi.setTouchable(true);

    popXingzi.setHeight(ScreenUtils.getScreenH(this) * 1 / 3);
    popXingzi.setWidth(ScreenUtils.getScreenW(this));

    popXingzi.setBackgroundDrawable(new BitmapDrawable());
    backgroundAlpha(1f);

    popXingzi.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        activityFindrentTvXingzi.setTextColor(Color.rgb(32, 32, 32));
        backgroundAlpha(1f);
      }
    });

    xingziListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        activityFindrentTvXingzi.setText(xingziList.get(position));
        requestData();
        popXingzi.dismiss();
      }
    });

    xingziViewquyu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popXingzi.dismiss();
        showPopWindow(1);
      }
    });

    xingziViewMoney.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        popXingzi.dismiss();
        showPopWindow(3);
      }
    });

  }

  /**
   * 设置添加屏幕的背景透明度
   *
   * @param bgAlpha
   */
  public void backgroundAlpha(float bgAlpha) {
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.alpha = bgAlpha; //0.0-1.0
    getWindow().setAttributes(lp);
  }


  @Override
  protected void setAllClick() {
    //区域点击
    activityFindrentRlQuyu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showPopWindow(1);
      }
    });
    //厅室点击
    activityFindrentRlXingzi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showPopWindow(2);
      }
    });

    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    /**
     * 搜索按钮监听
     */
    titleBarCommonTvSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /**
         * 隐藏软键盘
         */
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
          imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
      }
    });

    //搜索
    titleBarCommonEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //判断是否为"Search"健
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          /**
           * 隐藏软键盘
           */
          InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
          if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
          }
        }
        return true;
      }

    });
  }

  @Override
  protected void initViews() {
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    commonPullRefreshViewShow.setFooterRefreshComplete();
    commonPullRefreshViewShow.headerRefreshing();
    titleBarCommonEtSearch.setText(getIntent().getStringExtra("SearchText"));
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_search_result);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {
    requestData();
  }

  private void requestData() {

    String word = getIntent().getStringExtra("SearchText");
    String quyu = activityFindrentTvQuyu.getText().toString().trim().equals("区域") ||
        activityFindrentTvQuyu.getText().toString().trim().equals("不限") ? "" : activityFindrentTvQuyu.getText().toString().trim();
    String xinzhi = activityFindrentTvXingzi.getText().toString().trim().equals("薪资") ||
        activityFindrentTvXingzi.getText().toString().trim().equals("不限") ? "" : activityFindrentTvXingzi.getText().toString().trim();


    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.FIND_JOB_INDEX, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        commonPullRefreshViewShow.onHeaderRefreshComplete();
        commonPullRefreshViewShow.onFooterRefreshComplete();
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          JobListBean jobListBean = (JobListBean) msg.obj;
          adaper.setmDatas(jobListBean.getData());
          commonPullRefreshViewShow.onHeaderRefreshComplete();
        }
        return true;
      }
    }));
    helper.setRequestParams(new String[][]{{"word", word}, {"workplace", quyu}, {"treatment", xinzhi}});
    helper.sendPostAuto(JobListBean.class);

  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {

  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    requestData();
  }


  /**
   * 处理数据的线程
   */
  class dellDataThread extends Thread {
    @Override
    public void run() {
      System.out.println(System.currentTimeMillis());
      if (item1 != null && item2 != null) {
        handler.sendEmptyMessage(0x123);
        return;
      }
      item1 = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCity(1);
      for (RegionInfo regionInfo : item1) {
        item2.add((ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId()));
      }

      int i = 0;
      for (RegionInfo item : item1) {
        i++;
        if (SystemState.province.contains(item.getName())) {
          index = i - 1;
          break;
        }
      }
      handler.sendEmptyMessage(0x123);
    }
  }
}
