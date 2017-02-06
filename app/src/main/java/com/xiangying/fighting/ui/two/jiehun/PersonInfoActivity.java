package com.xiangying.fighting.ui.two.jiehun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.MerryIndex;
import com.xiangying.fighting.bean.MerryInfoBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.common.layoutmanager.FullyLinearLayoutManager;
import com.xiangying.fighting.common.recyclerviewadapter.BaseAdapterHelper;
import com.xiangying.fighting.common.recyclerviewadapter.BaseQuickAdapter;
import com.xiangying.fighting.common.recyclerviewadapter.QuickAdapter;
import com.xiangying.fighting.enumerate.CarType;
import com.xiangying.fighting.enumerate.HouseType;
import com.xiangying.fighting.enumerate.MarryType;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonInfoActivity extends BaseActivity {
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
  @Bind(R.id.activity_person_info_sdv_head)
  SimpleDraweeView activityPersonInfoSdvHead;
  @Bind(R.id.activity_person_info_tv_name)
  FontTextView activityPersonInfoTvName;
  @Bind(R.id.activity_person_info_tv_age)
  FontTextView activityPersonInfoTvAge;
  @Bind(R.id.activity_person_info_tv_height)
  FontTextView activityPersonInfoTvHeight;
  @Bind(R.id.activity_person_info_tv_area)
  FontTextView activityPersonInfoTvArea;
  @Bind(R.id.activity_person_info_ll_fahongbao)
  LinearLayout activityPersonInfoLlFahongbao;
  @Bind(R.id.activity_person_info_ll_yushou_go)
  RelativeLayout activityPersonInfoLlYushouGo;
  @Bind(R.id.activity_person_info_rv_)
  RecyclerView activityPersonInfoRv;
  @Bind(R.id.activity_person_info_income_self)
  FontTextView activityPersonInfoIncomeSelf;
  @Bind(R.id.activity_person_info_education_self)
  FontTextView activityPersonInfoEducationSelf;
  @Bind(R.id.activity_person_info_weight_self)
  FontTextView activityPersonInfoWeightSelf;
  @Bind(R.id.activity_person_info_marry_self)
  FontTextView activityPersonInfoMarrySelf;
  @Bind(R.id.activity_person_info_income_choose)
  FontTextView activityPersonInfoIncomeChoose;
  @Bind(R.id.activity_person_info_education_choose)
  FontTextView activityPersonInfoEducationChoose;
  @Bind(R.id.activity_person_info_weight_choose)
  FontTextView activityPersonInfoWeightChoose;
  @Bind(R.id.activity_person_info_height_choose)
  FontTextView activityPersonInfoHeightChoose;
  @Bind(R.id.activity_person_info_marry_choose)
  FontTextView activityPersonInfoMarryChoose;
  @Bind(R.id.activity_person_info_house_choose)
  FontTextView activityPersonInfoHouseChoose;
  @Bind(R.id.activity_person_info_car_choose)
  FontTextView activityPersonInfoCarChoose;
  @Bind(R.id.tv_one_intro)
  FontTextView mTvOneIntro;

  private QuickAdapter<String> picturAdapter;
  private List<String> dataList = new ArrayList<>();
  private MerryIndex.Data mData;
  private XUtilsHelper mXUtilsHelper;

  private String id;

  public static void start(Context context, String id) {
    Intent intent = new Intent(context, PersonInfoActivity.class);
    intent.putExtra("id", id);
    context.startActivity(intent);
  }

  @Override
  protected void process() {

    picturAdapter = new QuickAdapter<String>(getApplication(), R.layout.item_info_picture, dataList) {
      @Override
      protected void convert(BaseAdapterHelper helper, String item, int position) {
        helper.setImageByUrl(R.id.item_info_picture_sdv_picture, Endpoint.HOST + item);
      }
    };

    activityPersonInfoRv.setAdapter(picturAdapter);
    activityPersonInfoRv.setLayoutManager(new FullyLinearLayoutManager(getApplication(), LinearLayoutManager.HORIZONTAL, false));
    picturAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Intent intent = new Intent(PersonInfoActivity.this, ImageGalleryActivity.class);
        intent.putStringArrayListExtra("images", (ArrayList<String>) dataList);
        intent.putExtra("position", position);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    //发红包
    activityPersonInfoLlFahongbao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(PersonInfoActivity.this, FahongbaoActivity.class));
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("个人信息");
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);

    id = getIntent().getStringExtra("id");
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_person_info);
    ButterKnife.bind(this);

  }

  @Override
  protected void loadNetData() {
    mXUtilsHelper = new XUtilsHelper(this, NetworkTools.API_MERRY_INFO, new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message msg) {
        try {
          MerryInfoBean merryInfoBean = (MerryInfoBean) msg.obj;
          if (merryInfoBean.getCode() == 1) {
            showMerryInfo(merryInfoBean.getData());
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    }));
    mXUtilsHelper.showProgress("加载中...");
    mXUtilsHelper.setRequestParams(new String[][]{{"id", id}});
    mXUtilsHelper.sendPostAuto(MerryInfoBean.class);
  }

  private void showMerryInfo(MerryInfoBean.Data data) {

    activityPersonInfoSdvHead.setImageURI(Uri.parse(Endpoint.HOST + data.getHead()));
    activityPersonInfoTvName.setText(data.getName());
//    activityPersonInfoTvAge.setText(data.getAge());
    activityPersonInfoTvHeight.setText("   " + data.getStature() + "cm");
    activityPersonInfoTvArea.setText(data.getRegion());
    mTvOneIntro.setText(data.getInfo());

    activityPersonInfoIncomeSelf.setText(data.getIncome());
    activityPersonInfoEducationSelf.setText(data.getEducation());
    activityPersonInfoWeightSelf.setText(data.getWeight());
    activityPersonInfoMarrySelf.setText(MarryType.getNameById(data.getMarried()));

    activityPersonInfoIncomeChoose.setText(data.getZo_income());
    activityPersonInfoEducationChoose.setText(data.getZo_education());
    activityPersonInfoWeightChoose.setText(data.getZo_weight());
    activityPersonInfoHeightChoose.setText(data.getZo_stature());
    activityPersonInfoMarryChoose.setText(MarryType.getNameById(data.getZo_married()));
    activityPersonInfoHouseChoose.setText(HouseType.getNameById(data.getZo_room()));
    activityPersonInfoCarChoose.setText(CarType.getNameById(data.getZo_car()));

    if (data.getImgPath() != null && data.getImgPath().size() > 0) {
      dataList.addAll(data.getImgPath());
      picturAdapter.notifyDataSetChanged();
    }
  }

  private int getAge(String birth) {
    return Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(birth.substring(0, 4));
  }

}
