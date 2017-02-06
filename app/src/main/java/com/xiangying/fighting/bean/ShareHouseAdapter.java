/*
 * ShareHouseAdapter     2016/12/16 14:51
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.bumptech.glide.Glide;
import com.xiangying.fighting.R;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.widget.imagegallary.ImageGalleryActivity;
import com.xiangying.fighting.widget.listview.MultiItemTypeAdapter;
import com.xiangying.fighting.widget.listview.ViewHolder;
import com.xiangying.fighting.widget.listview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Koterwong on 2016/12/16 14:51
 */
public class ShareHouseAdapter extends MultiItemTypeAdapter<ShareHouseBean.Data> {
    public ShareHouseAdapter(Context context, List<ShareHouseBean.Data> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<ShareHouseBean.Data>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_share_house_image_one;
            }

            @Override
            public boolean isForViewType(ShareHouseBean.Data item, int position) {
                return (TextUtils.isEmpty(item.getMoviePath()) && (item.getImgPath() == null || item.getImgPath().size() < 3));
            }

            @Override
            public void convert(ViewHolder holder, final ShareHouseBean.Data data, int position) {
                ImageView imageView = holder.getView(R.id.commu_item_img_portrait1);
                imageView.setImageResource(R.drawable.default_image);
                Glide.with(mContext).load(Endpoint.HOST + data.getHeadimg()).into(imageView);
                holder.setText(R.id.tv_item_tv_nickname, data.getNickname());
                holder.setText(R.id.tv_item_tv_content, data.getContent());
                holder.setText(R.id.tv_item_tv_time, data.getCreate_time());
                bindList(holder, data, position);

                ImageView imageView1 = holder.getView(R.id.commu_item_img_content);
                imageView1.setImageResource(R.drawable.default_image);
                Glide.with(mContext).load(Endpoint.HOST + data.getImgPath().get(0)).into(imageView1);
                holder.setOnClickListener(R.id.commu_item_img_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImageGalleryActivity.class);
                        intent.putStringArrayListExtra("images", (ArrayList<String>) data.getImgPath());
                        mContext.startActivity(intent);
                    }
                });
            }
        });

        addItemViewDelegate(new ItemViewDelegate<ShareHouseBean.Data>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_share_house_iamge_three;
            }

            @Override
            public boolean isForViewType(ShareHouseBean.Data item, int position) {
                return (TextUtils.isEmpty(item.getMoviePath()) && item.getImgPath() != null && item.getImgPath().size() >= 3);
            }

            @Override
            public void convert(ViewHolder holder, final ShareHouseBean.Data data, int position) {
                ImageView imageView = holder.getView(R.id.commu_item_img_portrait1);
                imageView.setImageResource(R.drawable.default_image);
                Glide.with(mContext).load(Endpoint.HOST + data.getHeadimg()).into(imageView);
                holder.setText(R.id.tv_item_tv_nickname, data.getNickname());
                holder.setText(R.id.tv_item_tv_content, data.getContent());
                holder.setText(R.id.tv_item_tv_time, data.getCreate_time());
                bindList(holder, data, position);

                ImageView imageView1 = holder.getView(R.id.tv_item_img_content_1);
                ImageView imageView2 = holder.getView(R.id.tv_item_img_content_2);
                ImageView imageView3 = holder.getView(R.id.tv_item_img_content_3);
                imageView1.setImageResource(R.drawable.default_image);
                imageView2.setImageResource(R.drawable.default_image);
                imageView3.setImageResource(R.drawable.default_image);
                Glide.with(mContext).load(Endpoint.HOST + data.getImgPath().get(0)).into(imageView1);
                Glide.with(mContext).load(Endpoint.HOST + data.getImgPath().get(1)).into(imageView2);
                Glide.with(mContext).load(Endpoint.HOST + data.getImgPath().get(2)).into(imageView3);
                holder.setOnClickListener(R.id.tv_item_img_content_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImageGalleryActivity.class);
                        intent.putStringArrayListExtra("images", (ArrayList<String>) data.getImgPath());
                        intent.putExtra("position", 0);
                        mContext.startActivity(intent);
                    }
                });
                holder.setOnClickListener(R.id.tv_item_img_content_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImageGalleryActivity.class);
                        intent.putStringArrayListExtra("images", (ArrayList<String>) data.getImgPath());
                        intent.putExtra("position", 1);
                        mContext.startActivity(intent);
                    }
                });
                holder.setOnClickListener(R.id.tv_item_img_content_3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImageGalleryActivity.class);
                        intent.putStringArrayListExtra("images", (ArrayList<String>) data.getImgPath());
                        intent.putExtra("position", 2);
                        mContext.startActivity(intent);
                    }
                });
            }
        });

        addItemViewDelegate(new ItemViewDelegate<ShareHouseBean.Data>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_share_house_video;
            }

            @Override
            public boolean isForViewType(ShareHouseBean.Data item, int position) {
                return !TextUtils.isEmpty(item.getMoviePath());
            }

            @Override
            public void convert(ViewHolder holder, final ShareHouseBean.Data data, int position) {
                ImageView imageView = holder.getView(R.id.commu_item_img_portrait1);
                imageView.setImageResource(R.drawable.default_image);
                Glide.with(mContext).load(Endpoint.HOST + data.getHeadimg()).into(imageView);
                holder.setText(R.id.tv_item_tv_nickname, data.getNickname());
                holder.setText(R.id.tv_item_tv_content, data.getContent());
                holder.setText(R.id.tv_item_tv_time, data.getCreate_time());
                bindList(holder, data, position);

                final JCVideoPlayerStandard jcVideoPlayerStandard = holder.getView(R.id.jc_player);
                jcVideoPlayerStandard.setUp(Endpoint.HOST + data.getMoviePath(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
//                Bitmap videoThumbnail = ThumbUtils.get().createVideoThumbnail(Endpoint.HOST + data.getMoviePath(), MediaStore.Images.Thumbnails.MINI_KIND);
//                jcVideoPlayerStandard.thumbImageView.setImageBitmap(videoThumbnail);

            }
        });
    }

    //绑定点赞，评论事件
    private void bindList(ViewHolder holder, final ShareHouseBean.Data data, final int position) {

        if (!TextUtils.isEmpty(data.getPraise()) && data.getPraise().contains(GetLocalKey.getNickName())) {
            holder.setImageResource(R.id.iv_like, R.drawable.like_button_icon_h);
        } else {
            holder.setImageResource(R.id.iv_like, R.drawable.like_button_icon);
        }

        if (data.getComment() != null && data.getComment().size() > 0) {
            holder.setVisible(R.id.commu_item_liner_talk, true);
            if (data.getComment().size() == 1) {
                holder.setVisible(R.id.commu_item_tv_alltalk, false);
                holder.setVisible(R.id.commu_item_tv_talk1, true);
                holder.setVisible(R.id.commu_item_tv_talk2, false);
                holder.setText(R.id.commu_item_tv_talk1, data.getComment().get(0).getNickname() + ":" + data.getComment().get(0).getText());
            } else if (data.getComment().size() == 2) {
                holder.setVisible(R.id.commu_item_tv_alltalk, false);
                holder.setVisible(R.id.commu_item_tv_talk1, true);
                holder.setVisible(R.id.commu_item_tv_talk2, true);
                holder.setText(R.id.commu_item_tv_talk1, data.getComment().get(0).getNickname() + ":" + data.getComment().get(0).getText());
                holder.setText(R.id.commu_item_tv_talk2, data.getComment().get(1).getNickname() + ":" + data.getComment().get(1).getText());
            } else {
                holder.setVisible(R.id.commu_item_tv_alltalk, true);
                holder.setVisible(R.id.commu_item_tv_talk1, true);
                holder.setVisible(R.id.commu_item_tv_talk2, true);
                holder.setText(R.id.commu_item_tv_talk1, data.getComment().get(0).getNickname() + ":" + data.getComment().get(0).getText());
                holder.setText(R.id.commu_item_tv_talk2, data.getComment().get(1).getNickname() + ":" + data.getComment().get(1).getText());
            }
        } else {
            holder.setVisible(R.id.commu_item_liner_talk, false);
            holder.setText(R.id.tv_item_comment_num, "0");
        }

        if (!TextUtils.isEmpty(data.getPraise())) {
            holder.setText(R.id.tv_item_zan_num, data.getPraise().split(",").length + "");
        } else {
            holder.setText(R.id.tv_item_zan_num, "0");
        }

        if (!TextUtils.isEmpty(data.getCountComment())) {
            holder.setText(R.id.tv_item_comment_num, data.getCountComment());
            if (Integer.valueOf(data.getCountComment()) > 2) {
                holder.setVisible(R.id.commu_item_tv_alltalk, true);
                holder.setText(R.id.commu_item_tv_alltalk, "查看全部" + data.getCountComment() + "条评论");
                holder.setOnClickListener(R.id.commu_item_tv_alltalk, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemListener != null) {
                            mItemListener.onClickMoreComment(data);
                        }
                    }
                });
            } else {
                holder.setVisible(R.id.commu_item_tv_alltalk, false);
            }
        } else {
            holder.setText(R.id.tv_item_comment_num, "0");
        }

        //点赞
        holder.setOnClickListener(R.id.ll_item_linerzan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onClickZan(data, position);
                }
            }
        });

        //评论
        holder.setOnClickListener(R.id.commu_item_linertalk1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onClickComment(data, position);
            }
        });
    }

    public void setData(int position, ShareHouseBean.Data data) {
        mDatas.set(position, data);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mDatas.clear();
    }

    public void addDatas(List<ShareHouseBean.Data> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    private ItemListener mItemListener;

    public void setItemListener(ItemListener itemListener) {
        mItemListener = itemListener;
    }

    public interface ItemListener {
        void onClickZan(ShareHouseBean.Data data, int position);

        void onClickComment(ShareHouseBean.Data data, int position);

        void onClickMoreComment(ShareHouseBean.Data data);
    }
}
