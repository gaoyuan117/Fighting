package com.xiangying.fighting.ui.three.selfinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.bean.UserMovieBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.ThumbUtils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class UserVedioActivity extends BaseActivity
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

  CommonAdapter<UserMovieBean.Data> adapter;
  ArrayList<UserMovieBean.Data> dataList = new ArrayList<>();


  @Override
  protected void process() {
  }

  @Override
  protected void setAllClick() {
    commonPullRefreshViewShow.setOnFooterRefreshListener(this);
    commonPullRefreshViewShow.setOnHeaderRefreshListener(this);

    titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(UserVedioActivity.this, PublishNewVedioActivity.class));
      }
    });
  }

  @Override
  protected void initViews() {
    titleBarCommonTvTitle.setText("视频");
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
    XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.ME_VIDEO, new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (commonPullRefreshViewShow.isHeaderRefresh()) {
          commonPullRefreshViewShow.onHeaderRefreshComplete();
        }
        if (msg.what == XUtilsHelper.TAG_SUCCESS) {
          try {
            UserMovieBean userMovieBean = (UserMovieBean) msg.obj;
            if (userMovieBean.getCode() == 1 && userMovieBean.getData().size() > 0) {
              adapter = new CommonAdapter<UserMovieBean.Data>(UserVedioActivity.this, (ArrayList<UserMovieBean.Data>) userMovieBean.getData(), R.layout.item_me_video) {
                @Override public void convert(ViewHolder helper, UserMovieBean.Data item, int position) {
                  helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(item.getCreate_time() *1000,TimeUtil.STR_FORMAT_DATA));
                  helper.setText(R.id.item_colum_tv_content, item.getContent());
                  JCVideoPlayerStandard standard = helper.getView(R.id.jc_me_video);
                  standard.setUp(Endpoint.HOST + item.getMoviePath(), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
                  Bitmap videoThumbnail = ThumbUtils.get().createVideoThumbnail(Endpoint.HOST + item.getMoviePath(), MediaStore.Images.Thumbnails.MINI_KIND);
                  standard.thumbImageView.setImageBitmap(videoThumbnail);
                }
              };
              commonListviewShow.setAdapter(adapter);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return false;
      }
    }));
    helper.setRequestParams(new String[][]{});
    helper.sendPostAuto(UserMovieBean.class);
  }

  @Override
  public void onFooterRefresh(PullToRefreshView view) {
  }

  @Override
  public void onHeaderRefresh(PullToRefreshView view) {
    requestData();
  }
}