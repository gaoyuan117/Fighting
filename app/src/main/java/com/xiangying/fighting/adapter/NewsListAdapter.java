/*
 * NewsListAdapter     2016/12/12 13:02
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.NewsListBean;
import com.xiangying.fighting.utils.ThumbUtils;
import com.xiangying.fighting.widget.listview.MultiItemTypeAdapter;
import com.xiangying.fighting.widget.listview.ViewHolder;
import com.xiangying.fighting.widget.listview.base.ItemViewDelegate;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Koterwong on 2016/12/12 13:02
 */
public class NewsListAdapter extends MultiItemTypeAdapter<NewsListBean.Data.Result> {
  private Handler mHandler = new Handler(){
    @Override public void handleMessage(Message msg) {
      ImageHolder imageHodle = (ImageHolder) msg.obj;
      imageHodle.mImageView.setImageBitmap(imageHodle.mBitmap);
    }
  };

  public NewsListAdapter(Context context, List<NewsListBean.Data.Result> datas) {
    super(context, datas);

    addItemViewDelegate(new ItemViewDelegate<NewsListBean.Data.Result>() {
      @Override public int getItemViewLayoutId() {
        return R.layout.item_news_list;
      }

      @Override public boolean isForViewType(NewsListBean.Data.Result item, int position) {
        return false;
      }

      @Override public void convert(ViewHolder holder, NewsListBean.Data.Result result, int position) {
        holder.setText(R.id.tv_item_news_title, result.getTitle());
        holder.setImageResource(R.id.iv_item_news_imgs, R.drawable.default_image);
        if (!TextUtils.isEmpty(result.getImgPath())) {
          holder.setSimpleDraweeViewByUrl(R.id.iv_item_news_imgs, Endpoint.HOST + result.getImgPath());
        }
      }
    });

    addItemViewDelegate(new ItemViewDelegate<NewsListBean.Data.Result>() {
      @Override public int getItemViewLayoutId() {
        return R.layout.item_news_video;
      }

      @Override public boolean isForViewType(NewsListBean.Data.Result item, int position) {
        return true;
      }

      @Override public void convert(ViewHolder holder, final NewsListBean.Data.Result result, int position) {
        holder.setText(R.id.tv_item_news_time, TimeUtil.transformLongTimeFormat(result.getCreate_time() * 1000, TimeUtil.STR_FORMAT_DATE_TIME2));
        holder.setText(R.id.tv_item_news_today, TimeUtil.daysBetween(result.getUpdate_time() * 1000, System.currentTimeMillis()));
        final JCVideoPlayerStandard jcVideoPlayerStandard = holder.getView(R.id.jcvp_item_news_video);
        jcVideoPlayerStandard.setUp(Endpoint.HOST + result.getMoviePath(), JCVideoPlayer.SCREEN_LAYOUT_LIST, result.getTitle());

        new Thread(){
          @Override public void run() {
            Bitmap videoThumbnail = ThumbUtils.get().createVideoThumbnail(Endpoint.HOST + result.getMoviePath(), MediaStore.Images.Thumbnails.MINI_KIND);
            ImageHolder imageHodle = new ImageHolder();
            imageHodle.mBitmap = videoThumbnail;
            imageHodle.mImageView = jcVideoPlayerStandard.thumbImageView;
            Message message = new Message();
            message.obj = imageHodle;
            mHandler.sendMessage(message);
          }
        }.start();
      }
    });
  }

  public void addAll(List<NewsListBean.Data.Result> listData) {
    mDatas.addAll(listData);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  private class ImageHolder {
    ImageView mImageView ;
    Bitmap mBitmap;
  }
}
