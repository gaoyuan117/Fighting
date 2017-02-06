package com.xiangying.fighting.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.hymane.expandtextview.ExpandTextView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.AllNoteBean;
import com.xiangying.fighting.widget.NoScrollerGridView;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;
import com.xiangying.fighting.widget.listview.MultiItemTypeAdapter;
import com.xiangying.fighting.widget.listview.ViewHolder;
import com.xiangying.fighting.widget.listview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2017/1/23.
 */

public class UserAllNoteAdapter extends MultiItemTypeAdapter<AllNoteBean.DataBean> {
    public UserAllNoteAdapter(Context context, List<AllNoteBean.DataBean> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<AllNoteBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_me_note;
            }

            @Override
            public boolean isForViewType(AllNoteBean.DataBean item, int position) {
                return item.getType() == 0;
            }

            @Override
            public void convert(ViewHolder helper, AllNoteBean.DataBean dataBean, int position) {
                helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(dataBean.getCreate_time() * 1000, TimeUtil.STR_FORMAT_DATA));
                ExpandTextView expandTextView = helper.getView(R.id.expand_text_view);
                expandTextView.setContent(dataBean.getContent());
            }
        });

        addItemViewDelegate(new ItemViewDelegate<AllNoteBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_me_colum;
            }

            @Override
            public boolean isForViewType(AllNoteBean.DataBean item, int position) {
                return item.getType() == 1;
            }

            @Override
            public void convert(ViewHolder helper, final AllNoteBean.DataBean item, int position) {
                helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(item.getCreate_time() * 1000, TimeUtil.STR_FORMAT_DATA));
                helper.setText(R.id.item_colum_tv_content, item.getContent());
                NoScrollerGridView scrollerGridView = helper.getView(R.id.grid_view);
                if (item.getImgpath() != null && item.getImgpath().size() > 0) {
                    scrollerGridView.setAdapter(new com.xiangying.fighting.widget.listview.CommonAdapter<String>(mContext, R.layout.item_img, item.getImgpath()) {
                        @Override
                        protected void convert(com.xiangying.fighting.widget.listview.ViewHolder holder, String item, int position) {
                            ImageView imageView = holder.getView(R.id.item_image_sdv_image);
                            imageView.setImageResource(R.drawable.default_image);
                            Glide.with(mContext).load(Endpoint.HOST + item).into(imageView);
                        }
                    });

                    scrollerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(mContext, ImageGalleryActivity.class);
                            intent.putStringArrayListExtra("images", (ArrayList<String>) item.getImgpath());
                            intent.putExtra("position", position);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        });

        addItemViewDelegate(new ItemViewDelegate<AllNoteBean.DataBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_me_video;
            }

            @Override
            public boolean isForViewType(AllNoteBean.DataBean item, int position) {
                return item.getType() == 2;
            }

            @Override
            public void convert(ViewHolder helper, AllNoteBean.DataBean item, int position) {
                helper.setText(R.id.item_colum_tv_month, TimeUtil.transformLongTimeFormat(item.getCreate_time() *1000,TimeUtil.STR_FORMAT_DATA));
                helper.setText(R.id.item_colum_tv_content, item.getContent());
                JCVideoPlayerStandard standard = helper.getView(R.id.jc_me_video);
                standard.setUp(Endpoint.HOST + item.getMoviePath(), JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
//                Bitmap videoThumbnail = ThumbUtils.get().createVideoThumbnail(Endpoint.HOST + item.getMoviePath(), MediaStore.Images.Thumbnails.MINI_KIND);
//                standard.thumbImageView.setImageBitmap(videoThumbnail);
            }
        });
    }


}
