package com.xiangying.fighting.ui.two.jiehun;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.view.OptionsPickerView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MarrySearchActivity<T> extends BaseActivity {


  @Bind(R.id.title_bar_common_iv_operate_1)
  ImageView titleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_tv_search)
  FontTextView titleBarCommonTvSearch;
  @Bind(R.id.title_bar_common_et_search)
  ClearEditText titleBarCommonEtSearch;
  @Bind(R.id.title_bar_common_rv_viewGroup)
  RelativeLayout titleBarCommonRvViewGroup;
  @Bind(R.id.activity_publish_NJ_tv_area)
  FontTextView activityPublishNJTvArea;
  @Bind(R.id.activity_publish_NJ_ll_area)
  LinearLayout activityPublishNJLlArea;
  @Bind(R.id.activity_publish_NJ_tv_age)
  FontTextView activityPublishNJTvAge;
  @Bind(R.id.activity_publish_NJ_ll_age)
  LinearLayout activityPublishNJLlAge;
  @Bind(R.id.activity_publish_NJ_tv_height)
  FontTextView activityPublishNJTvHeight;
  @Bind(R.id.activity_publish_NJ_ll_height)
  LinearLayout activityPublishNJLlHeight;
  @Bind(R.id.activity_publish_NJ_tv_education)
  FontTextView activityPublishNJTvEducation;
  @Bind(R.id.activity_publish_NJ_ll_education)
  LinearLayout activityPublishNJLlEducation;
  @Bind(R.id.activity_publish_NJ_tv_income)
  FontTextView activityPublishNJTvIncome;
  @Bind(R.id.activity_publish_NJ_ll_income)
  LinearLayout activityPublishNJLlIncome;
  @Bind(R.id.activity_publish_NJ_tv_marryHis)
  FontTextView activityPublishNJTvMarryHis;
  @Bind(R.id.activity_publish_NJ_ll_marryHis)
  LinearLayout activityPublishNJLlMarryHis;
  @Bind(R.id.activity_publish_NJ_tv_car)
  FontTextView activityPublishNJTvCar;
  @Bind(R.id.activity_publish_NJ_ll_car)
  LinearLayout activityPublishNJLlCar;
  @Bind(R.id.activity_publish_NJ_tv_house)
  FontTextView activityPublishNJTvHouse;
  @Bind(R.id.activity_publish_NJ_ll_house)
  LinearLayout activityPublishNJLlHouse;

  /**
   * 各个pickerView
   */
  OptionsPickerView pickerViewArea;
  OptionsPickerView pickerViewAge;
  OptionsPickerView pickerViewHeight;
  OptionsPickerView pickerViewEducation;
  OptionsPickerView pickerViewIncome;
  OptionsPickerView pickerViewMarryHis;
  OptionsPickerView pickerViewCar;
  OptionsPickerView pickerViewHouse;

  /**
   * 其他选项的数据
   */
  ArrayList<String> ageList = new ArrayList<>();
  ArrayList<String> heightList = new ArrayList<>();
  ArrayList<String> educationList = new ArrayList<>();
  ArrayList<String> incomeList = new ArrayList<>();
  ArrayList<String> marryHisList = new ArrayList<>();
  ArrayList<String> carList = new ArrayList<>();
  ArrayList<String> houseList = new ArrayList<>();


  /**
   * 城市数据
   */
  static ArrayList<RegionInfo> item1;

  static ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();

  static ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();

  private Handler handler = new Handler() {
    public void handleMessage(android.os.Message msg) {
      System.out.println(System.currentTimeMillis());
      // 三级联动效果
      pickerViewArea.setPicker(item1, item2, null, true);
      pickerViewArea.setCyclic(true, true, true);
      pickerViewArea.setSelectOptions(0, 0, 0);
    }

    ;
  };


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

        // 返回的分别是三个级别的选中位置
        String tx = item1.get(options1).getPickerViewText()
            + (options2 != null ? item2.get(options1).get(options2).getPickerViewText() : "")
            + (options3 != null ? item3.get(options1).get(options2).get(options3).getPickerViewText() : "");
        activityPublishNJTvArea.setText(tx);
      }
    });
    //开启处理城市数据线程
    new AreaDataInitThread().start();


    /**
     * 设置其他几个的选择器
     */
    initOtherData();//数据选项
    setOptionPickerView();

  }

  @Override
  protected void setAllClick() {
    //地区选择
    activityPublishNJLlArea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewArea.show();
      }
    });
    //年龄选择
    activityPublishNJLlAge.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewAge.show();
      }
    });
    //身高选择
    activityPublishNJLlHeight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewHeight.show();
      }
    });
    //学历选择
    activityPublishNJLlEducation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewEducation.show();
      }
    });
    //收入选择
    activityPublishNJLlIncome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewIncome.show();
      }
    });
    //婚史选择
    activityPublishNJLlMarryHis.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewMarryHis.show();
      }
    });
    //购车选择
    activityPublishNJLlCar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewCar.show();
      }
    });
    //购房选择
    activityPublishNJLlHouse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewHouse.show();
      }
    });
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonEtSearch.setHint("输入关键字");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_marry_search);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {

  }


  /**
   * setOptionPickerView
   */
  public void setOptionPickerView() {
    pickerViewAge = new OptionsPickerView(this);
    pickerViewAge.setPicker(ageList);
    pickerViewAge.setCyclic(false);
    pickerViewAge.setSelectOptions(0);
    pickerViewAge.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvAge.setText(ageList.get(options1));
      }
    });

    //
    pickerViewHeight = new OptionsPickerView(this);
    pickerViewHeight.setPicker(heightList);
    pickerViewHeight.setCyclic(false);
    pickerViewHeight.setSelectOptions(0);
    pickerViewHeight.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvHeight.setText(heightList.get(options1));
      }
    });
    //
    pickerViewEducation = new OptionsPickerView(this);
    pickerViewEducation.setPicker(educationList);
    pickerViewEducation.setCyclic(false);
    pickerViewEducation.setSelectOptions(0);
    pickerViewEducation.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvEducation.setText(educationList.get(options1));
      }
    });
    //
    pickerViewIncome = new OptionsPickerView(this);
    pickerViewIncome.setPicker(incomeList);
    pickerViewIncome.setCyclic(false);
    pickerViewIncome.setSelectOptions(0);
    pickerViewIncome.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvIncome.setText(incomeList.get(options1));
      }
    });
    //
    pickerViewMarryHis = new OptionsPickerView(this);
    pickerViewMarryHis.setPicker(marryHisList);
    pickerViewMarryHis.setCyclic(false);
    pickerViewMarryHis.setSelectOptions(0);
    pickerViewMarryHis.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvMarryHis.setText(marryHisList.get(options1));
      }
    });
    //
    pickerViewCar = new OptionsPickerView(this);
    pickerViewCar.setPicker(carList);
    pickerViewCar.setCyclic(false);
    pickerViewCar.setSelectOptions(0);
    pickerViewCar.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvCar.setText(carList.get(options1));
      }
    });
    //
    pickerViewHouse = new OptionsPickerView(this);
    pickerViewHouse.setPicker(houseList);
    pickerViewHouse.setCyclic(false);
    pickerViewHouse.setSelectOptions(0);
    pickerViewHouse.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityPublishNJTvHouse.setText(houseList.get(options1));
      }
    });
  }

  /**
   * 为其他选项设置数据
   */
  public void initOtherData() {
    //年龄
    ageList.add("不限");
    ageList.add("18-22岁");
    ageList.add("23-27岁");
    ageList.add("28-32岁");
    ageList.add("33-36岁");
    ageList.add("37-40岁");
    ageList.add("41-45岁");
    //身高
    heightList.add("不限");
    heightList.add("150cm-155cm");
    heightList.add("155cm-160cm");
    heightList.add("160cm-165cm");
    heightList.add("165cm-170cm");
    heightList.add("170cm-175cm");
    heightList.add("175cm-180cm");
    heightList.add("180cm-185cm");
    heightList.add("185cm-190cm");
    heightList.add("190cm以上");
    //学历
    educationList.add("不限");
    educationList.add("专科");
    educationList.add("本科");
    educationList.add("硕士");
    educationList.add("博士");
    //收入
    incomeList.add("不限");
    incomeList.add("2000元以下");
    incomeList.add("2000-3000元");
    incomeList.add("3000-5000元");
    incomeList.add("5000-6000元");
    incomeList.add("6000-8000元");
    incomeList.add("8000元以上");
    //婚史
    marryHisList.add("不限");
    marryHisList.add("一次");
    marryHisList.add("两次");
    marryHisList.add("两次以上");
    //购车情况
    carList.add("不限");
    carList.add("有车");
    carList.add("无车");
    //购房情况
    houseList.add("不限");
    houseList.add("有房");
    houseList.add("无房");

  }


  /**
   * 处理城市数据线程
   */
  class AreaDataInitThread extends Thread {
    @Override
    public void run() {
      System.out.println(System.currentTimeMillis());
      if (item1 != null && item2 != null && item3 != null) {
        handler.sendEmptyMessage(0x123);
        return;
      }
      item1 = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCity(1);
      for (RegionInfo regionInfo : item1) {
        item2.add((ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId()));

      }

      for (ArrayList<RegionInfo> arrayList : item2) {
        ArrayList<ArrayList<RegionInfo>> list2 = new ArrayList<ArrayList<RegionInfo>>();
        for (RegionInfo regionInfo : arrayList) {


          ArrayList<RegionInfo> q = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId());
          list2.add(q);

        }
        item3.add(list2);
      }
      handler.sendEmptyMessage(0x123);
    }
  }
}
