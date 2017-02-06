package com.xiangying.fighting.ui.two.findrent;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.utils.ScreenUtils;
import com.jock.pickerview.view.OptionsPickerView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.two.findrent.bean.RentList;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindRentActivity extends BaseActivity
        implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
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
    @Bind(R.id.activity_findrent_tv_quyu)
    FontTextView activityFindrentTvQuyu;
    @Bind(R.id.activity_findrent_img_quyu)
    ImageView activityFindrentImgQuyu;
    @Bind(R.id.activity_findrent_rl_quyu)
    RelativeLayout activityFindrentRlQuyu;
    @Bind(R.id.activity_findrent_tv_tingshi)
    FontTextView activityFindrentTvTingshi;
    @Bind(R.id.activity_findrent_img_tingshi)
    ImageView activityFindrentImgTingshi;
    @Bind(R.id.activity_findrent_rl_tingshi)
    RelativeLayout activityFindrentRlTingshi;
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

    CommonAdapter<RentList.Data> adaper;
    ArrayList<RentList.Data> dataList = new ArrayList<>();

    PopupWindow popArea;
    PopupWindow popTingshi;
    PopupWindow popMoney;
    OptionsPickerView pickerViewArea;
    /**
     * 选中的条件
     */
    ArrayList<RegionInfo> areaList = new ArrayList<>();
    private CommonAdapter<RegionInfo> quyuAdapter;
    /**
     * 城市数据
     */
    static ArrayList<RegionInfo> items1;
    static ArrayList<ArrayList<RegionInfo>> items2 = new ArrayList<ArrayList<RegionInfo>>();
    static ArrayList<ArrayList<ArrayList<RegionInfo>>> items3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();
    int index = 1;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x123) {
                System.out.println(System.currentTimeMillis());
                // 三级联动效果
                pickerViewArea.setPicker(items1, items2, items3, true);
                pickerViewArea.setCyclic(true, true, true);
                pickerViewArea.setSelectOptions(0, 0, 0);
            }
            return true;
        }
    });
    private String qy;


    @Override
    protected void process() {
        pickerViewArea = new OptionsPickerView(this);
        /**
         * 设置pickerView的回调
         */
        pickerViewArea.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(Integer options1, Integer options2,
                                        Integer options3) {
                qy = items1.get(options1).getPickerViewText()
                        + (options2 != null ? items2.get(options1).get(options2).getPickerViewText() : "")
                        + (options3 != null ? items3.get(options1).get(options2).get(options3).getPickerViewText() : "");
                loadData();//加载数据
            }
        });
        new AreaDataInitThread().start();

        //为listView设置adapter
        adaper = new CommonAdapter<RentList.Data>(this, dataList, R.layout.item_rent_room) {
            @Override
            public void convert(ViewHolder helper, final RentList.Data item, int position) {
                if (item.imgPath != null && item.imgPath.size() > 0) {
                    helper.setSimpleDraweeViewByUrl(R.id.item_rent_room_sdv_picture, item.imgPath.get(0));
                }
                helper.setText(R.id.item_rent_room_tv_title, item.title);
                helper.setText(R.id.item_rent_room_tv_community, item.qu_name);
                helper.setText(R.id.item_rent_room_tv_money, item.price + "元");

                helper.setItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindRentActivity.this, RentDetailActivity.class);
                        intent.putExtra("itemTitle", item.qu_name);
                        intent.putExtra("id", item.id);
                        startActivity(intent);
                    }
                });
            }
        };
        commonListviewShow.setAdapter(adaper);

        //刷新控件设置监听
        commonPullRefreshViewShow.setOnHeaderRefreshListener(this);
        commonPullRefreshViewShow.setOnFooterRefreshListener(this);

        //首次刷新
        commonPullRefreshViewShow.headerRefreshing();

        //首次请求数据
        loadData();

        //初始化popupwindow
        popArea = new PopupWindow(this);
        popTingshi = new PopupWindow(this);
        popMoney = new PopupWindow(this);

        //设置三个popupwindow
        setUpPopArea();
        setUpPopTingshi();
        setUpPopMoney();
    }


    View viewQuYu;
    GridView gridview;
    View quyuViewQuyu;
    View quyuViewTingshi;
    View quyuViewMoney;

    /**
     * 设置区域popwindow
     */
    private void setUpPopArea() {

        viewQuYu = LayoutInflater.from(this).inflate(R.layout.popup_quyu, null);
        gridview = (GridView) viewQuYu.findViewById(R.id.popup_quyu_gridview);
        quyuViewQuyu = viewQuYu.findViewById(R.id.popup_quyu_quyu);
        quyuViewTingshi = viewQuYu.findViewById(R.id.popup_quyu_tingshi);
        quyuViewMoney = viewQuYu.findViewById(R.id.popup_quyu_money);


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

                /*darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);*/

//                leftLV.setSelection(0);
//                rightLV.setSelection(0);
                backgroundAlpha(1f);
            }
        });

        //设置区域选中效果
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popArea.dismiss();
                activityFindrentTvQuyu.setText(quyuAdapter.getItem(position).getName());
                loadData();//加载数据
            }
        });
        //设置三个按钮点击效果
        quyuViewTingshi.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 展示popwindow
     */
    public void showPopWindow(int choice) {
        switch (choice) {
            case 1:
                if (popArea.isShowing()) {
                    popArea.dismiss();
                } else {
                    popArea.showAsDropDown(findViewById(R.id.title_bar));
                    popArea.setAnimationStyle(-1);
                    activityFindrentTvQuyu.setTextColor(Color.rgb(184, 0, 18));
                }
                break;
            case 2:
                if (popTingshi.isShowing()) {
                    popTingshi.dismiss();
                } else {
                    popTingshi.showAsDropDown(findViewById(R.id.title_bar));
                    popTingshi.setAnimationStyle(-1);
                    activityFindrentTvTingshi.setTextColor(Color.rgb(184, 0, 18));
                }
                break;
            case 3:
                if (popMoney.isShowing()) {
                    popMoney.dismiss();
                } else {
                    popMoney.showAsDropDown(findViewById(R.id.title_bar));
                    popMoney.setAnimationStyle(-1);
                    activityFindrentTvMoney.setTextColor(Color.rgb(184, 0, 18));
                }
                break;

        }
    }


    View viewTingshi;
    ListView tingshiListView;
    View tingshiViewquyu;
    View tingshiViewTingshi;
    View tingshiViewMoney;

    /**
     * 设置厅室popwindow
     */
    private void setUpPopTingshi() {

        viewTingshi = LayoutInflater.from(this).inflate(R.layout.popup_tingshi, null);
        tingshiListView = (ListView) viewTingshi.findViewById(R.id.popup_tingshi_listview);
        tingshiViewquyu = viewTingshi.findViewById(R.id.popup_tingshi_quyu);
        tingshiViewTingshi = viewTingshi.findViewById(R.id.popup_tingshi_tingshi);
        tingshiViewMoney = viewTingshi.findViewById(R.id.popup_tingshi_money);

        ArrayList<String> tingshiList = new ArrayList<>();
        tingshiList.add("不限");
        tingshiList.add("一室");
        tingshiList.add("两室");
        tingshiList.add("三室");
        tingshiList.add("四室");
        tingshiList.add("四室以上");

        final CommonAdapter<String> tingshiAdapter = new CommonAdapter<String>(this, tingshiList, R.layout.item_tingshi) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setText(R.id.item_tingshi_tv_tingshi, item);
            }
        };
        tingshiListView.setAdapter(tingshiAdapter);


        popTingshi.setContentView(viewTingshi);
        popTingshi.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
        popTingshi.setFocusable(true);
        popTingshi.setTouchable(true);

        popTingshi.setHeight((int) (ScreenUtils.getScreenH(this) * 1 / 3));
        popTingshi.setWidth(ScreenUtils.getScreenW(this));

        popTingshi.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);

        popTingshi.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activityFindrentTvTingshi.setTextColor(Color.rgb(32, 32, 32));

                /*darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);*/

//                leftLV.setSelection(0);
//                rightLV.setSelection(0);
                backgroundAlpha(1f);
            }
        });

        //设置厅室选中效果
        tingshiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popTingshi.dismiss();
                activityFindrentTvTingshi.setText(tingshiAdapter.getItem(position));
                loadData();//加载数据

            }
        });

        //设置三个按钮的点击效果
        tingshiViewquyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTingshi.dismiss();
                showPopWindow(1);
            }
        });
        tingshiViewMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTingshi.dismiss();
                showPopWindow(3);
            }
        });

    }


    View viewMoney;
    ListView moneyListView;
    View moneyViewquyu;
    View moneyViewTingshi;
    View moneyViewMoney;

    /**
     * 设置价钱popwindow
     */
    private void setUpPopMoney() {

        viewMoney = LayoutInflater.from(this).inflate(R.layout.popup_money, null);
        moneyListView = (ListView) viewMoney.findViewById(R.id.popup_money_listview);
        moneyViewquyu = viewMoney.findViewById(R.id.popup_money_quyu);
        moneyViewTingshi = viewMoney.findViewById(R.id.popup_money_tingshi);
        moneyViewMoney = viewMoney.findViewById(R.id.popup_money_money);

        ArrayList<String> tingshiList = new ArrayList<>();
        tingshiList.add("不限");
        tingshiList.add("0-500");
        tingshiList.add("501-1000");
        tingshiList.add("1001-1500");
        tingshiList.add("1501-2000");
        tingshiList.add("2001-2500");
        tingshiList.add("2501-3000");
        tingshiList.add("3001-3500");
        tingshiList.add("3501-4000");
        tingshiList.add("4001元以上");

        final CommonAdapter<String> zujingAdapter = new CommonAdapter<String>(this, tingshiList, R.layout.item_tingshi) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setText(R.id.item_tingshi_tv_tingshi, item);
            }
        };
        moneyListView.setAdapter(zujingAdapter);


        popMoney.setContentView(viewMoney);
        popMoney.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
        popMoney.setFocusable(true);
        popMoney.setTouchable(true);

        popMoney.setHeight((int) (ScreenUtils.getScreenH(this) * 1 / 3));
        popMoney.setWidth(ScreenUtils.getScreenW(this));

        popMoney.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);

        popMoney.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activityFindrentTvMoney.setTextColor(Color.rgb(32, 32, 32));

                /*darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);*/

//                leftLV.setSelection(0);
//                rightLV.setSelection(0);
                backgroundAlpha(1f);
            }
        });

        //设置区域选中效果
        moneyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMoney.dismiss();
                activityFindrentTvMoney.setText(zujingAdapter.getItem(position));
                loadData();//加载数据

            }
        });

        //设置三个按钮的点击效果
        moneyViewquyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMoney.dismiss();
                showPopWindow(1);
            }
        });
        moneyViewTingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMoney.dismiss();
                showPopWindow(2);
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
                pickerViewArea.show();
            }

        });
        //厅室点击
        activityFindrentRlTingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(2);
            }
        });
        //价钱点击
        activityFindrentRlMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(3);
            }
        });

        titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindRentActivity.this, RentListActivity.class));
            }
        });

    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate3.setImageResource(R.drawable.fabu);
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonTvTitle.setText("好租房");
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_find_rent);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadNetData() {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        loadData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        loadData();
    }

    /**
     * 找出组
     */
    public void loadData() {

//        String quyu = activityFindrentTvQuyu.getText().toString().trim().equals("区域") || activityFindrentTvQuyu.getText().toString().trim().equals("不限") ? "" : qy;
        String quyu;
        if (TextUtils.isEmpty(qy)) {
            quyu = "";
        } else {
            quyu = qy;
        }
        String tingshi = activityFindrentTvTingshi.getText().toString().trim().equals("厅室") || activityFindrentTvTingshi.getText().toString().trim().equals("不限") ? "" : activityFindrentTvTingshi.getText().toString().trim();
        String zujing = activityFindrentTvMoney.getText().toString().trim().equals("租金") || activityFindrentTvMoney.getText().toString().trim().equals("不限") ? "" : activityFindrentTvMoney.getText().toString().trim();


        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.FIND_RENT_INDEX, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                commonPullRefreshViewShow.onHeaderRefreshComplete();
                commonPullRefreshViewShow.onFooterRefreshComplete();

                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    RentList rentList = (RentList) msg.obj;
                    adaper.setmDatas((ArrayList<RentList.Data>) rentList.data);

                }
                return true;
            }
        }));
        if (zujing.equals("4001元以上")) {
            zujing = "4001-99999";
        }
        helper.setRequestParams(new String[][]{{"qu", quyu}, {"hall_room", tingshi}, {"price", zujing}});
        helper.sendPostAuto(RentList.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 处理城市数据线程
     */
    class AreaDataInitThread extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
            if (items1 != null && items2 != null && items3 != null) {
                handler.sendEmptyMessage(0x123);
                return;
            }
            items1 = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCity(1);
            for (RegionInfo regionInfo : items1) {
                items2.add((ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId()));

            }

            for (ArrayList<RegionInfo> arrayList : items2) {
                ArrayList<ArrayList<RegionInfo>> list2 = new ArrayList<ArrayList<RegionInfo>>();
                for (RegionInfo regionInfo : arrayList) {
                    ArrayList<RegionInfo> q = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId());
                    list2.add(q);
                }
                items3.add(list2);
            }
            handler.sendEmptyMessage(0x123);
        }
    }
}
