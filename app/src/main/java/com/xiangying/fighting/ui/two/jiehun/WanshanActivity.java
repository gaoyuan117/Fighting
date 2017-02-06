package com.xiangying.fighting.ui.two.jiehun;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.CommonUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.view.OptionsPickerView;
import com.jock.pickerview.view.TimePickerView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.bean.MerryInfoBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.common.LogKw;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.MyGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import service.api.CommonReturn;

@RuntimePermissions
public class WanshanActivity extends BaseActivity {
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
  @Bind(R.id.activity_wanshan_gv_images)
  MyGridView activityWanshanGvImages;
  @Bind(R.id.activity_wanshan_sdv_head)
  SimpleDraweeView activityWanshanSdvHead;
  @Bind(R.id.activity_wanshan_ll_choose_head)
  LinearLayout activityWanshanLlChooseHead;

  ImageGridAdapter imageGridAdapter;
  ArrayList<String> selectImagePath = new ArrayList<>();
  ArrayList<String> selectImageSingle = new ArrayList<>();

  public int REQUEST_IMAGE = 10001;
  public int REQUEST_SIMGLE_IMAGE = 10000;
  @Bind(R.id.checkbox_nan)
  RadioButton checkboxNan;
  @Bind(R.id.checkbox_nv)
  RadioButton checkboxNv;
  @Bind(R.id.activity_wanshan_rg_sex)
  RadioGroup activityWanshanRgSex;
  @Bind(R.id.activity_wanshan_tv_area)
  FontTextView activityWanshanTvArea;
  @Bind(R.id.activity_wanshan_ll_area)
  LinearLayout activityWanshanLlArea;
  @Bind(R.id.activity_wanshan_tv_birthday)
  FontTextView activityWanshanTvBirthday;
  @Bind(R.id.activity_wanshan_ll_birthday)
  LinearLayout activityWanshanLlBirthday;
  @Bind(R.id.activity_wanshan_tv_height)
  FontTextView activityWanshanTvHeight;
  @Bind(R.id.activity_wanshan_ll_height)
  LinearLayout activityWanshanLlHeight;
  @Bind(R.id.activity_wanshan_tv_education)
  FontTextView activityWanshanTvEducation;
  @Bind(R.id.activity_wanshan_ll_education)
  LinearLayout activityWanshanLlEducation;
  @Bind(R.id.activity_wanshan_tv_income)
  FontTextView activityWanshanTvIncome;
  @Bind(R.id.activity_wanshan_ll_income)
  LinearLayout activityWanshanLlIncome;
  @Bind(R.id.checkbox_weihun)
  RadioButton checkboxWeihun;
  @Bind(R.id.checkbox_liyi)
  RadioButton checkboxLiyi;
  @Bind(R.id.checkbox_sangou)
  RadioButton checkboxSangou;
  @Bind(R.id.activity_wanshan_rg_marry_his)
  RadioGroup activityWanshanRgMarryHis;
  @Bind(R.id.activity_wanshan_ll_marryHis)
  LinearLayout activityWanshanLlMarryHis;
  @Bind(R.id.checkbox_youfang)
  RadioButton checkboxYoufang;
  @Bind(R.id.checkbox_wufang)
  RadioButton checkboxWufang;
  @Bind(R.id.activity_wanshan_rg_house)
  RadioGroup activityWanshanRgHouse;
  @Bind(R.id.checkbox_youche)
  RadioButton checkboxYouche;
  @Bind(R.id.checkbox_wuche)
  RadioButton checkboxWuche;
  @Bind(R.id.activity_wanshan_rg_car)
  RadioGroup activityWanshanRgCar;
  @Bind(R.id.activity_wanshan_dubai)
  EditText activityWanshanDubai;
  @Bind(R.id.activity_wanshan_tv_commit)
  FontTextView activityWanshanTvCommit;

  @Bind(R.id.activity_wanshan_ll_height_zo)
  LinearLayout activityWanshanLlHeightZo;
  @Bind(R.id.activity_wanshan_tv_height_zo)
  FontTextView activityWanshanTvHeightZo;
  @Bind(R.id.activity_wanshan_tv_area_zo)
  FontTextView activityWanshanTvAreaZo;
  @Bind(R.id.activity_wanshan_ll_area_zo)
  LinearLayout activityWanshanLlAreaZo;
  @Bind(R.id.activity_wanshan_tv_education_zo)
  FontTextView activityWanshanTvEducationZo;
  @Bind(R.id.activity_wanshan_ll_education_zo)
  LinearLayout activityWanshanLlEducationZo;
  @Bind(R.id.activity_wanshan_tv_income_zo)
  FontTextView activityWanshanTvIncomeZo;
  @Bind(R.id.activity_wanshan_ll_income_zo)
  LinearLayout activityWanshanLlIncomeZo;
  @Bind(R.id.activity_wanshan_ll_weight_zo)
  LinearLayout activityWanshanLlWeightZo;
  @Bind(R.id.activity_wanshan_tv_weight_zo)
  ClearEditText activityWanshanTvWeightZo;

  @Bind(R.id.checkbox_weihun_zo)
  RadioButton checkboxWeihunZo;
  @Bind(R.id.checkbox_liyi_zo)
  RadioButton checkboxLiyiZo;
  @Bind(R.id.checkbox_sangou_zo)
  RadioButton checkboxSangouZo;
  @Bind(R.id.activity_wanshan_rg_marry_his_zo)
  RadioGroup activityWanshanRgMarryHisZo;
  @Bind(R.id.activity_wanshan_ll_marryHis_zo)
  LinearLayout activityWanshanLlMarryHisZo;
  @Bind(R.id.checkbox_youfang_zo)
  RadioButton checkboxYoufangZo;
  @Bind(R.id.checkbox_wufang_zo)
  RadioButton checkboxWufangZo;
  @Bind(R.id.activity_wanshan_rg_house_zo)
  RadioGroup activityWanshanRgHouseZo;
  @Bind(R.id.checkbox_youche_zo)
  RadioButton checkboxYoucheZo;
  @Bind(R.id.checkbox_wuche_zo)
  RadioButton checkboxWucheZo;
  @Bind(R.id.activity_wanshan_rg_car_zo)
  RadioGroup activityWanshanRgCarZo;
  /**
   * 各个pickerView
   */
  OptionsPickerView pickerViewArea;
  TimePickerView pickerViewBirthday;
  OptionsPickerView pickerViewHeight;
  OptionsPickerView pickerViewEducation;
  OptionsPickerView pickerViewIncome;

  OptionsPickerView pickerViewHeightZo;
  OptionsPickerView pickerViewAreaZo;
  OptionsPickerView pickerViewEducationZo;
  OptionsPickerView pickerViewIncomeZo;
  /**
   * 其他选项的数据
   */
  ArrayList<String> heightList = new ArrayList<>();
  ArrayList<String> educationList = new ArrayList<>();
  ArrayList<String> incomeList = new ArrayList<>();


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
  };
  private XUtilsHelper myInfoHelper;
  private XUtilsHelper mUploadImageHelper;
  private Date mDate;
  private MerryInfoBean.Data mInfoBean;

  @Override
  protected void process() {

    /**
     * 选照片
     */
    selectImagePath.add("");
    imageGridAdapter = new ImageGridAdapter(selectImagePath, this);
    activityWanshanGvImages.setAdapter(imageGridAdapter);
    activityWanshanGvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == (selectImagePath.size() - 1)) {
          WanshanActivityPermissionsDispatcher.chooseImagesWithCheck(WanshanActivity.this);
        }
      }
    });

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
        activityWanshanTvArea.setText(tx);
      }
    });

    pickerViewAreaZo = new OptionsPickerView(this);
    pickerViewAreaZo.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        String text = item1.get(options1).getPickerViewText()
            + (options2 != null ? item2.get(options1).get(options2).getPickerViewText() : "")
            + (options3 != null ? item3.get(options1).get(options2).get(options3).getPickerViewText() : "");
        activityWanshanTvAreaZo.setText(text);
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
    /**
     * 选择头像监听
     */
    activityWanshanLlChooseHead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        WanshanActivityPermissionsDispatcher.chooseImageSingleWithCheck(WanshanActivity.this);
      }
    });

    //地区选择
    activityWanshanLlArea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewArea.show();
      }
    });
    //生日选择
    activityWanshanLlBirthday.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewBirthday.show();
      }
    });
    //身高选择
    activityWanshanLlHeight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewHeight.show();
      }
    });
    //学历选择
    activityWanshanLlEducation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewEducation.show();
      }
    });
    //收入选择
    activityWanshanLlIncome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pickerViewIncome.show();
      }
    });

    activityWanshanLlAreaZo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pickerViewAreaZo.show();
      }
    });

    activityWanshanLlEducationZo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pickerViewEducationZo.show();
      }
    });

    activityWanshanLlHeightZo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pickerViewHeightZo.show();
      }
    });

    activityWanshanLlIncomeZo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        pickerViewIncomeZo.show();
      }
    });

    activityWanshanTvCommit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!CommonUtil.isLeastSingleClick()) {
          return;
        }

        if (mInfoBean == null && selectImagePath.size() == 1) {
          Utils.toast(WanshanActivity.this, "请选择图片");
          return;
        }

        if (selectImagePath.size() == 1 && mInfoBean != null) {
          addJiehunInfo(mInfoBean.getLitpic());
          return;
        }

        addMyInfo();
      }
    });
  }

  /**
   * 添加结婚吧
   */
  //TODO 上传图片
  private void addMyInfo() {
    mUploadImageHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          ImageFileBean imageFileBean = (ImageFileBean) msg.obj;
          if (imageFileBean.getCode() == 1) {
            addJiehunInfo(imageFileBean.getData());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    mUploadImageHelper.setFileRequestParames2(selectImagePath);
    mUploadImageHelper.sendPostAuto(ImageFileBean.class);
  }

  private void addJiehunInfo(String data) {

    //性别
    if (!isChecked(activityWanshanRgSex)) {
      Utils.toast(this, "请选择性别！");
      return;
    }
    String sex = activityWanshanRgSex.getCheckedRadioButtonId() == R.id.checkbox_nan ? "1" : "2";

    //地区
    if (!isSelector(activityWanshanTvArea)) {
      Utils.toast(this, "请选择地区！");
      return;
    }
    String region = activityWanshanTvArea.getText().toString().trim();

    //出生日期
    String birthday = "";
    if (mDate == null && activityWanshanTvBirthday.getText().equals("请选择")) {
      Utils.toast(this, "请选择出生日期！");
      return;
    }

    if (mInfoBean != null) {
      birthday = mInfoBean.getBirthday();
    }

    if (mDate != null) {
      birthday = mDate.getTime() / 1000 + "";
    }

    //身高
    if (!isSelector(activityWanshanTvHeight)) {
      Utils.toast(this, "请选择身高！");
      return;
    }
    String stature = activityWanshanTvHeight.getText().toString().trim();

    //学历
    if (!isSelector(activityWanshanTvEducation)) {
      Utils.toast(this, "请选择学历！");
      return;
    }
    String education = activityWanshanTvEducation.getText().toString().trim();

    //收入
    if (!isSelector(activityWanshanTvIncome)) {
      Utils.toast(this, "请选择收入！");
      return;
    }
    String income = activityWanshanTvIncome.getText().toString().trim();

    //婚史
    if (!isChecked(activityWanshanRgMarryHis)) {
      Utils.toast(this, "请选择婚史！");
      return;
    }
    String married = "";
    switch (activityWanshanRgMarryHis.getCheckedRadioButtonId()) {
      case R.id.checkbox_weihun:
        married = "1";
        break;
      case R.id.checkbox_liyi:
        married = "2";
        break;
      case R.id.checkbox_sangou:
        married = "3";
        break;
    }

    //房子
    if (!isChecked(activityWanshanRgHouse)) {
      Utils.toast(this, "请选择是否有房！");
      return;
    }
    String room = activityWanshanRgHouse.getCheckedRadioButtonId() == R.id.checkbox_youfang ? "1" : "2";

    //车子
    if (!isChecked(activityWanshanRgCar)) {
      Utils.toast(this, "请选择是否有车！");
      return;
    }
    String car = activityWanshanRgCar.getCheckedRadioButtonId() == R.id.checkbox_youche ? "1" : "2";

    //独白
    String info = activityWanshanDubai.getText().toString().trim();

    //择偶收入
    if (!isSelector(activityWanshanTvIncomeZo)) {
      Utils.toast(this, "请选择择偶的收入！");
      return;
    }
    String zo_income = activityWanshanTvIncomeZo.getText().toString().trim();

    //择偶学历
    if (!isSelector(activityWanshanTvEducationZo)) {
      Utils.toast(this, "请选择择偶的学历！");
      return;
    }
    String zo_education = activityWanshanTvEducationZo.getText().toString().trim();

    //择偶身高
    if (!isSelector(activityWanshanTvHeightZo)) {
      Utils.toast(this, "请选择择偶的学历！");
      return;
    }
    String zo_stature = activityWanshanTvHeightZo.getText().toString().trim();

    String zo_weight = activityWanshanTvWeightZo.getText().toString().trim();
    if (TextUtils.isEmpty(zo_weight)) {
      Utils.toast(this, "请输入择偶的身高");
      return;
    }


    //婚史
    if (!isChecked(activityWanshanRgMarryHisZo)) {
      Utils.toast(this, "请选择婚史！");
      return;
    }
    String married_zo = "";
    switch (activityWanshanRgMarryHisZo.getCheckedRadioButtonId()) {
      case R.id.checkbox_weihun_zo:
        married_zo = "1";
        break;
      case R.id.checkbox_liyi_zo:
        married_zo = "2";
        break;
      case R.id.checkbox_sangou_zo:
        married_zo = "3";
        break;
    }

    //房子
    if (!isChecked(activityWanshanRgHouseZo)) {
      Utils.toast(this, "请选择是否有房！");
      return;
    }
    String room_zo = activityWanshanRgHouseZo.getCheckedRadioButtonId() == R.id.checkbox_youfang_zo ? "1" : "2";

    //车子
    if (!isChecked(activityWanshanRgCarZo)) {
      Utils.toast(this, "请选择是否有车！");
      return;
    }
    String car_zo = activityWanshanRgCarZo.getCheckedRadioButtonId() == R.id.checkbox_youche_zo ? "1" : "2";

    XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.API_MERRY_ADD, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          CommonReturn commonReturn = (CommonReturn) msg.obj;
          if (commonReturn.code == 1) {
            Utils.toast(WanshanActivity.this, "提交成功");
            finish();
          } else {
            Utils.toast(WanshanActivity.this, commonReturn.message);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    xUtilsHelper.setRequestParams(new String[][]{
        {"litpic", data},  //个人照片集
        {"sex", sex},  //性别1男2女
        {"region", region},  //地区
        {"birthday", birthday},  //出生日期，时间戳
        {"stature", stature},  //身高
        {"education", education},  //学历
        {"income", income},  //收入
        {"married", married},  //婚史：1未婚2离异3丧偶
        {"room", room},  //房子：1有2没有
        {"car", car},  //汽车：1有2没有
        {"info", info},  //独白
        {"zo_income", zo_income},  //收入
        {"zo_education", zo_education},
        {"zo_weight", zo_weight},
        {"zo_stature", zo_stature},
        {"zo_married", married_zo},
        {"zo_room", room_zo},
        {"zo_car", car_zo},
    });
    xUtilsHelper.sendPostAuto(CommonReturn.class);
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("完善资料");
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_wanshan);
    ButterKnife.bind(this);
  }

  //加载个人资料
  @Override
  protected void loadNetData() {
    myInfoHelper = new XUtilsHelper(this, NetworkTools.API_MERRY_INFO, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        myInfoHelper.hideProgress();
        try {
          MerryInfoBean merryInfoBean = (MerryInfoBean) msg.obj;
          if (merryInfoBean.getCode() == 1) {
            showMyInfo(merryInfoBean.getData());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    myInfoHelper.showProgress("加载中");
    myInfoHelper.setRequestParams(new String[][]{{"id", GetLocalKey.getUid()}});
    myInfoHelper.sendPostAuto(MerryInfoBean.class);
  }

  private void showMyInfo(MerryInfoBean.Data data) {

    if (data == null) {
      return;
    }

    mInfoBean = data;

    if (mInfoBean.getImgPath().size() > 0) {
      ArrayList<String> list = new ArrayList<>();
      for (String img : mInfoBean.getImgPath()) {
        if (TextUtils.isEmpty(img)) {
          continue;
        }
        list.add(Endpoint.HOST + img);
      }
      list.add("");
      LogKw.e(list.toString());
      imageGridAdapter.setImageList(list);
    }

    if ("1".equals(data.getSex())) {
      activityWanshanRgSex.check(R.id.checkbox_nan);
    } else {
      activityWanshanRgSex.check(R.id.checkbox_nv);
    }

    activityWanshanTvArea.setText(data.getRegion());
    activityWanshanTvBirthday.setText(getTime(new Date(Long.parseLong(data.getBirthday()) * 1000)));
    activityWanshanTvHeight.setText(data.getStature());
    activityWanshanTvEducation.setText(data.getEducation());
    activityWanshanTvIncome.setText(data.getIncome());

    if ("1".equals(data.getMarried())) {
      activityWanshanRgMarryHis.check(R.id.checkbox_weihun);
    } else if ("2".equals(data.getMarried())) {
      activityWanshanRgMarryHis.check(R.id.checkbox_liyi);
    } else if ("3".equals(data.getMarried())) {
      activityWanshanRgMarryHis.check(R.id.checkbox_sangou);
    }

    if ("1".equals(data.getRoom())) {
      activityWanshanRgHouse.check(R.id.checkbox_youfang);
    } else if ("2".equals(data.getRoom())) {
      activityWanshanRgHouse.check(R.id.checkbox_wufang);
    }

    if ("1".equals(data.getCar())) {
      activityWanshanRgCar.check(R.id.checkbox_youche);
    } else if ("2".equals(data.getCar())) {
      activityWanshanRgCar.check(R.id.checkbox_wuche);
    }

    activityWanshanDubai.setText(data.getInfo());

    activityWanshanTvHeightZo.setText(data.getZo_stature());
    activityWanshanTvWeightZo.setText(data.getZo_weight());
    activityWanshanTvEducationZo.setText(data.getZo_education());
    activityWanshanTvIncomeZo.setText(data.getZo_income());

    if ("1".equals(data.getZo_married())) {
      activityWanshanRgMarryHisZo.check(R.id.checkbox_weihun_zo);
    } else if ("2".equals(data.getZo_married())) {
      activityWanshanRgMarryHisZo.check(R.id.checkbox_liyi_zo);
    } else if ("3".equals(data.getZo_married())) {
      activityWanshanRgMarryHisZo.check(R.id.checkbox_sangou_zo);
    }

    if ("1".equals(data.getZo_room())) {
      activityWanshanRgHouseZo.check(R.id.checkbox_youfang_zo);
    } else if ("2".equals(data.getZo_room())) {
      activityWanshanRgHouseZo.check(R.id.checkbox_wufang_zo);
    }

    if ("1".equals(data.getZo_car())) {
      activityWanshanRgCarZo.check(R.id.checkbox_youche_zo);
    } else if ("2".equals(data.getZo_car())) {
      activityWanshanRgCarZo.check(R.id.checkbox_wuche_zo);
    }
  }


  /**
   * 查看图库文件
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void chooseImages() {
    boolean showCamera = true;
    int maxNum = 9;

    MultiImageSelector selector = MultiImageSelector.create(WanshanActivity.this);
    selector.showCamera(showCamera);
    selector.count(maxNum);

    selector.multi();
    ArrayList<String> tempSelectPath = new ArrayList<>();
    tempSelectPath.addAll(selectImagePath);

    tempSelectPath.remove(tempSelectPath.size() - 1);

    selector.origin(tempSelectPath);
    selector.start(WanshanActivity.this, REQUEST_IMAGE);
  }

  /**
   * 单选图片文件
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void chooseImageSingle() {
    boolean showCamera = true;
    int maxNum = 1;

    MultiImageSelector selector = MultiImageSelector.create(WanshanActivity.this);
    selector.showCamera(showCamera);
    selector.count(maxNum);

    selector.single();

    selector.origin(selectImageSingle);
    selector.start(WanshanActivity.this, REQUEST_SIMGLE_IMAGE);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
  public void onCamaraDenied() {
    Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    WanshanActivityPermissionsDispatcher.onRequestPermissionsResult(WanshanActivity.this, requestCode, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE) {
      if (resultCode == RESULT_OK) {
        selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
        selectImagePath.add("");
        //更新数据
        imageGridAdapter.setImageList(selectImagePath);
      }
    } else if (requestCode == REQUEST_SIMGLE_IMAGE && resultCode == RESULT_OK) {
      selectImageSingle = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
      activityWanshanSdvHead.setImageURI(Uri.parse("file://" + selectImageSingle.get(0)));
    }
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

  /**
   * setOptionPickerView
   */
  public void setOptionPickerView() {
    // 时间选择器
    pickerViewBirthday = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
    // 控制时间范围
//		 Calendar calendar = Calendar.getInstance();
//		 pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
//		 calendar.get(Calendar.YEAR));
    pickerViewBirthday.setTime(new Date());
    pickerViewBirthday.setCyclic(false);
    pickerViewBirthday.setCancelable(true);
    // 时间选择后回调
    pickerViewBirthday.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

      @Override
      public void onTimeSelect(Date date) {
        activityWanshanTvBirthday.setText(getTime(date));
        mDate = date;
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
        activityWanshanTvHeight.setText(heightList.get(options1));
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
        activityWanshanTvEducation.setText(educationList.get(options1));
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
        activityWanshanTvIncome.setText(incomeList.get(options1));
      }
    });
    //
    pickerViewIncomeZo = new OptionsPickerView(this);
    pickerViewIncomeZo.setPicker(incomeList);
    pickerViewIncomeZo.setCyclic(false);
    pickerViewIncomeZo.setSelectOptions(0);
    pickerViewIncomeZo.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityWanshanTvIncomeZo.setText(incomeList.get(options1));
      }
    });
    pickerViewEducationZo = new OptionsPickerView(this);
    pickerViewEducationZo.setPicker(educationList);
    pickerViewEducationZo.setCyclic(false);
    pickerViewEducationZo.setSelectOptions(0);
    pickerViewEducationZo.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityWanshanTvEducationZo.setText(educationList.get(options1));
      }
    });

    pickerViewHeightZo = new OptionsPickerView(this);
    pickerViewHeightZo.setPicker(heightList);
    pickerViewHeightZo.setCyclic(false);
    pickerViewHeightZo.setSelectOptions(0);
    pickerViewHeightZo.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
      @Override
      public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
        activityWanshanTvHeightZo.setText(heightList.get(options1));
      }
    });

  }

  /**
   * 为其他选项设置数据
   */
  public void initOtherData() {

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


  }

  public static String getTime(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(date);
  }

  private boolean isChecked(RadioGroup radioGroup) {
    if (radioGroup.getCheckedRadioButtonId() == -1) {
      return false;
    }
    return true;
  }

  private boolean isSelector(TextView textView) {
    if ("请选择".equals(textView.getText().toString().trim())) {
      return false;
    }
    return true;
  }
}
