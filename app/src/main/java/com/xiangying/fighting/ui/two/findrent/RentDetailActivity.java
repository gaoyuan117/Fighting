package com.xiangying.fighting.ui.two.findrent;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.two.findrent.bean.RentDetailBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;
import com.xiangying.fighting.widget.loopviewpager.AutoLoopViewPager;
import com.xiangying.fighting.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RentDetailActivity extends BaseActivity {
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
  @Bind(R.id.activity_rent_detail_viewpager)
  AutoLoopViewPager activityRentDetailViewpager;
  @Bind(R.id.activity_rent_detail_indicator)
  CirclePageIndicator activityRentDetailIndicator;
  @Bind(R.id.layout_ent_gallery)
  RelativeLayout layoutEntGallery;

  //图片列表
  List<String> imageList = new ArrayList<>();
  //Adaptyer
  GalleryPagerAdapter pagerAdapter;
  @Bind(R.id.activity_rent_detail_tv_title)
  FontTextView activityRentDetailTvTitle;
  @Bind(R.id.activity_rent_detail_tv_price)
  FontTextView activityRentDetailTvPrice;
  @Bind(R.id.activity_rent_detail_tv_publishTime)
  FontTextView activityRentDetailTvPublishTime;
  @Bind(R.id.activity_rent_detail_tv_tingshi)
  FontTextView activityRentDetailTvTingshi;
  @Bind(R.id.activity_rent_detail_tv_mianji)
  FontTextView activityRentDetailTvMianji;
  @Bind(R.id.activity_rent_detail_tv_floor)
  FontTextView activityRentDetailTvFloor;
  @Bind(R.id.activity_rent_detail_tv_xiaoqu)
  FontTextView activityRentDetailTvXiaoqu;
  @Bind(R.id.activity_rent_detail_tv_address)
  FontTextView activityRentDetailTvAddress;
  @Bind(R.id.activity_rent_detail_tv_describ)
  FontTextView activityRentDetailTvDescrib;
  @Bind(R.id.activity_rent_detail_liaojie)
  FontTextView activityRentDetailLiaojie;
  @Bind(R.id.activity_rent_detail_fangdong_number)
  FontTextView activityRentDetailFangdongNumber;
  @Bind(R.id.activity_rent_detail_tv_add)
  FontTextView activityRentDetailTvAdd;

  String id = "";

  @Override
  protected void process() {

    Intent intent = getIntent();
    id = intent.getStringExtra("id");
//    setupViewPager();
  }

  private void setupViewPager() {//不让viewpager自动滚动
    activityRentDetailViewpager.setFocusable(false);
    pagerAdapter = new GalleryPagerAdapter();
    activityRentDetailViewpager.setAdapter(pagerAdapter);
    //为小圆点设置viewPager
    activityRentDetailIndicator.setViewPager(activityRentDetailViewpager);
    activityRentDetailIndicator.setPadding(5, 5, 10, 5);
    activityRentDetailViewpager.stopAutoScroll();
    //停止自动滚动
    activityRentDetailViewpager.setAutoScroll(false);
  }

  @Override
  protected void setAllClick() {
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  protected void initViews() {
    Intent intent = getIntent();
    titleBarCommonTvTitle.setText(intent.getStringExtra("itemTitle"));
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_rent_detail);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {

    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.RENT_DETAIL, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          RentDetailBean rentDetailBean = (RentDetailBean) msg.obj;
//          imageList = Arrays.asList(rentDetailBean.getData().getLitpic().split(","));
//          pagerAdapter.notifyDataSetChanged();
          imageList = rentDetailBean.getData().getImgPath();
          setupViewPager();
          activityRentDetailTvTitle.setText(rentDetailBean.getData().getTitle());
          activityRentDetailTvPrice.setText(rentDetailBean.getData().getPrice());
          activityRentDetailTvPublishTime.setText("发布：" + TimeUtil.transformLongTimeFormat(rentDetailBean.getData().getTime() * 1000, TimeUtil.STR_FORMAT_DATE));
          activityRentDetailTvTingshi.setText("厅室：" + rentDetailBean.getData().getHall_room());
          activityRentDetailTvMianji.setText("面积：" + rentDetailBean.getData().getArea() + "平米");
          activityRentDetailTvFloor.setText("楼层：" + rentDetailBean.getData().getFloor());
          activityRentDetailTvXiaoqu.setText("小区：" + rentDetailBean.getData().getQu_name());
          activityRentDetailTvAddress.setText("地址：" + rentDetailBean.getData().getQu_site());
          activityRentDetailTvDescrib.setText(rentDetailBean.getData().getDescription());
        }
        return false;
      }
    }));
    helper.setRequestParams(new String[][]{{"id", id}});
    helper.sendPostAuto(RentDetailBean.class);
  }

  //轮播图适配器
  public class GalleryPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      SimpleDraweeView item = new SimpleDraweeView(RentDetailActivity.this);
      item.setImageURI(Uri.parse(Endpoint.HOST + imageList.get(position)));
      ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
      item.setLayoutParams(params);

      GenericDraweeHierarchy hierarchy = item.getHierarchy();
      hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
      container.addView(item);

      final int pos = position;
      item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(RentDetailActivity.this, ImageGalleryActivity.class);
          intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
          intent.putExtra("position", pos);
          startActivity(intent);
        }
      });

      return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
      collection.removeView((View) view);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    activityRentDetailViewpager.startAutoScroll();
  }

  @Override
  protected void onPause() {
    super.onPause();
    activityRentDetailViewpager.stopAutoScroll();
  }
}
