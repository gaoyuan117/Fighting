package com.xiangying.fighting.ui.three.selfinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.UserAlbumBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.NoScrollerGridView;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserAlbumActivity extends BaseActivity
    implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
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
  @Bind(R.id.common_listview_show)
  ListView commonListviewShow;
  @Bind(R.id.common_pull_refresh_view_show)
  PullToRefreshView commonPullRefreshViewShow;

  CommonAdapter<UserAlbumBean.Data> adapter;
  ArrayList<UserAlbumBean.Data> dataList = new ArrayList<>();

  public static void start(Context context) {
    Intent starter = new Intent(context, UserAlbumActivity.class);
    context.startActivity(starter);
  }

  @Override
  protected void process() {
    adapter = new CommonAdapter<UserAlbumBean.Data>(this, dataList, R.layout.item_me_colum) {
      @Override
      public void convert(final ViewHolder helper, final UserAlbumBean.Data item, int position) {
        helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(item.getCreate_time() * 1000, TimeUtil.STR_FORMAT_DATA));
        helper.setText(R.id.item_colum_tv_content, item.getContent());
        NoScrollerGridView scrollerGridView = helper.getView(R.id.grid_view);
        if (item.getImgpath() != null && item.getImgpath().size() > 0) {
          scrollerGridView.setAdapter(new com.xiangying.fighting.widget.listview.CommonAdapter<String>(UserAlbumActivity.this, R.layout.item_img, item.getImgpath()) {
            @Override protected void convert(com.xiangying.fighting.widget.listview.ViewHolder holder, String item, int position) {
              ImageView imageView = holder.getView(R.id.item_image_sdv_image);
              imageView.setImageResource(R.drawable.default_image);
              Glide.with(UserAlbumActivity.this).load(Endpoint.HOST + item).into(imageView);
            }
          });

          scrollerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(UserAlbumActivity.this, ImageGalleryActivity.class);
              intent.putStringArrayListExtra("images", (ArrayList<String>) item.getImgpath());
              intent.putExtra("position", position);
              startActivity(intent);
            }
          });
        }
      }
    };
    commonListviewShow.setAdapter(adapter);
  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);

    //发布新照片
    titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(UserAlbumActivity.this, PublishNewAlbumActivity.class));
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("相册");
    titleBarCommonIvOperate3.setImageResource(R.drawable.icon_plus);
    titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    commonPullRefreshViewShow.setFooterRefreshComplete();
  }

  @Override
  protected void loadLayout() {
    setContentView(R.layout.activity_album);
    ButterKnife.bind(this);
  }

  @Override
  protected void loadNetData() {
    requestData();
  }

  @Override protected void onRestart() {
    super.onRestart();
    requestData();
  }

  private void requestData() {
    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.ME_THUMB, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (commonPullRefreshViewShow.isHeaderRefresh()) {
          commonPullRefreshViewShow.onHeaderRefreshComplete();
        }
        try {
          UserAlbumBean albumBean = (UserAlbumBean) msg.obj;
          if (albumBean.getCode() == 1 && albumBean.getData() != null && albumBean.getData().size() > 0) {
            adapter.clearDatas();
            adapter.addmDatas((ArrayList<UserAlbumBean.Data>) albumBean.getData());
          }
        } catch (Exception e) {

        }

        return false;
      }
    }));
    helper.setRequestParams(new String[][]{});
    helper.sendPostAuto(UserAlbumBean.class);
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {
  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    requestData();
  }
}