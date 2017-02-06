package com.xiangying.fighting.ui.first.sharehouse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiangying.fighting.R;
import com.xiangying.fighting.widget.MyGridView;
import com.xiangying.fighting.ui.first.sharehouse.bean.Community;

import java.util.ArrayList;


@SuppressLint("ResourceAsColor")
public class CommunityAdapter extends BaseAdapter {
  private ArrayList<Community> list = new ArrayList<Community>();
  private Context context;
  private LayoutInflater inflater;
  private ViewHolder viewHolder;
  private int zan = 0, isZan = 0;
  private Community commu;


  public CommunityAdapter(Context context) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  public ArrayList<Community> getList() {
    return list;
  }

  public void removeAll() {
    if (list.size() != 0) {
      list.clear();
    }
  }

  public void addFrist(ArrayList<Community> items) {
    this.list = items;
    notifyDataSetChanged();
  }

  public void setPosition(Community commu, int position) {
    this.list.set(position, commu);
    notifyDataSetChanged();
  }

  public void deletePosition(int position) {
    this.list.remove(position);
    notifyDataSetChanged();
  }

  public void addData(ArrayList<Community> items) {
    this.list.addAll(items);
    notifyDataSetChanged();
  }

  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    Log.v("CommunityAdapter", list.size() + "");
    viewHolder = null;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(context).inflate(R.layout.commu_item, parent, false);
      viewHolder.contentImg = (ImageView) convertView.findViewById(R.id.commu_item_img_content);
      viewHolder.nikename = (TextView) convertView.findViewById(R.id.commu_item_tv_nickname);
      viewHolder.img_sex = (ImageView) convertView.findViewById(R.id.img_sex);
      viewHolder.tv_time = (TextView) convertView.findViewById(R.id.commu_item_tv_time);
      viewHolder.tv_maincontent = (TextView) convertView.findViewById(R.id.commu_item_tv_content);
      viewHolder.tv_zan = (TextView) convertView.findViewById(R.id.commu_item_tv_zan);
      viewHolder.tv_talks = (TextView) convertView.findViewById(R.id.commu_item_tv_talk);
      viewHolder.tv_alltalks = (TextView) convertView.findViewById(R.id.commu_item_tv_alltalk);
      viewHolder.tv_talk1 = (TextView) convertView.findViewById(R.id.commu_item_tv_talk1);
      viewHolder.tv_talk2 = (TextView) convertView.findViewById(R.id.commu_item_tv_talk2);
      viewHolder.UserPortrait = (ImageView) convertView.findViewById(R.id.commu_item_img_portrait);
      viewHolder.UserPortrait1 = (ImageView) convertView.findViewById(R.id.commu_item_img_portrait1);
      viewHolder.img_zan = (ImageView) convertView.findViewById(R.id.commu_item_img_zan);
      viewHolder.img_talk = (ImageView) convertView.findViewById(R.id.commu_item_img_talk);
      viewHolder.liner_content = (LinearLayout) convertView.findViewById(R.id.commu_item_liner_content);
      viewHolder.liner_talk = (LinearLayout) convertView.findViewById(R.id.commu_item_liner_talk);
      viewHolder.liner_zan = (LinearLayout) convertView.findViewById(R.id.commu_item_linerzan);
      viewHolder.liner_talk1 = (LinearLayout) convertView.findViewById(R.id.commu_item_linertalk1);
      viewHolder.rela = (RelativeLayout) convertView.findViewById(R.id.com_item_rela);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
  /*	commu=list.get(position);
    initCommuData(viewHolder,commu,position);
		viewHolder.tv_link.setText(commu.getLocation());
		zan=commu.getPraiseAmount();//赞数量
		isZan=commu.getIsPraise();//当前用户是否赞
		viewHolder.nikename.setText(commu.getNickname());
		if(!TextUtils.isEmpty(commu.getGender())&&commu.getGender().equals("MALE")) {
			viewHolder.img_sex.setVisibility(View.GONE);
		}else{
			viewHolder.img_sex.setVisibility(View.VISIBLE);
		}
	*//*	Glide.with(context).load(commu.getAvatar())
        .placeholder(R.drawable.ic_photo)
				.bitmapTransform(new CropCircleTransformation(context)).
				 placeholder(R.drawable.ic_photo).into(viewHolder.UserPortrait);*//*
		if(!TextUtils.isEmpty(commu.getContent())){
		viewHolder.tv_maincontent.setVisibility(View.VISIBLE);
		viewHolder.tv_maincontent.setText(commu.getContent());
		}else {
		viewHolder.tv_maincontent.setVisibility(View.GONE);
		}

		viewHolder.tv_zan.setText(zan + "");
		viewHolder.tv_talks.setText(commu.getCommentAmount() + "");
		Log.v("CommuApapter",commu.getCommentAmount()+"");
		if (commu.getCommentAmount()>2){
			viewHolder.tv_talk1.setVisibility(View.VISIBLE);
			viewHolder.tv_talk2.setVisibility(View.VISIBLE);
			viewHolder.tv_alltalks.setVisibility(View.VISIBLE);
			viewHolder.tv_alltalks.setText("查看全部" + commu.getCommentAmount() + "条评论");
			viewHolder.liner_talk.setVisibility(View.VISIBLE);
		}else {
			viewHolder.tv_alltalks.setVisibility(View.GONE);
			if(commu.getCommentAmount()==0) {
				viewHolder.tv_talk1.setVisibility(View.GONE);
				viewHolder.tv_talk2.setVisibility(View.GONE);
				viewHolder.liner_talk.setVisibility(View.GONE);
		    }else if(commu.getCommentAmount()==1){
			viewHolder.tv_talk2.setVisibility(View.GONE);
			viewHolder.tv_talk1.setVisibility(View.VISIBLE);
			viewHolder.liner_talk.setVisibility(View.VISIBLE);
		   }else  if(commu.getCommentAmount()==2){
				viewHolder.tv_talk1.setVisibility(View.VISIBLE);
				viewHolder.tv_talk2.setVisibility(View.VISIBLE);
				viewHolder.liner_talk.setVisibility(View.VISIBLE);
			}
		}
		if(commu.getComments()!=null&&commu.getComments().size()>0){
			for (int i=0;i<commu.getComments().size();i++) {
				Comments comment;
				comment = commu.getComments().get(i);
				TextView textView=new TextView(context);
				if(i==0){
					textView=viewHolder.tv_talk1;
				}else if(i==1){
					textView=viewHolder.tv_talk2;
				}
				Log.v("repliename",comment.getReplies().size()+"");
				if (comment.getReplies().size() == 0) {//评论没有回复
						textView.setText(comment.getNickname() + ": " + comment.getContent());
				} else {
					Replies replie;
					replie = comment.getReplies().get(0);
					if (!replie.getReplyNickname().equals("")) {
						//String context="<font color='#76a4b3'>"+replie.getNickname()+"</font>"+"回复"+"<font color='#76a4b3'>"+name+"</font>:"+replie.getContent();
						textView.setText(replie.getNickname() + " 回复 " + replie.getReplyNickname() + ": " + replie.getContent());
					} else {
						textView.setText(replie.getNickname() + ": " + replie.getContent());
					}
				}
				if(i>2){break;}
			}
		}
		if(isZan==1){
//			viewHolder.img_zan.setImageResource(R.drawable.ic_zambia_2);
//			viewHolder.tv_zan.setTextColor(context.getResources().getColor(R.color.zan));
//		}else {
//			viewHolder.img_zan.setImageResource(R.drawable.ic_zambia_1);
//			viewHolder.tv_zan.setTextColor(context.getResources().getColor(R.color.text_time));
		}
		if(!TextUtils.isEmpty(commu.getPushTime())){
//		viewHolder.tv_time.setText(com.shuxiang.util.DateUtils
//				.getTimestampString(new Date(TimeFormat.getStringToDate(commu.getPushTime()))));
		}
		viewHolder.liner_content.setOnClickListener(new MyAdapterListener(position));
		viewHolder.liner_talk.setOnClickListener(new MyAdapterListener(position));
		viewHolder.liner_zan.setOnClickListener(new MyAdapterListener(position));
		viewHolder.liner_talk1.setOnClickListener(new MyAdapterListener(position));
		viewHolder.nikename.setOnClickListener(new MyAdapterListener(position));
		viewHolder.UserPortrait1.setOnClickListener(new MyAdapterListener(position));
		viewHolder.rela.setOnClickListener(new MyAdapterListener(position));
		viewHolder.liner_content.setOnLongClickListener(new MyAdapterOnItemLongClick(position));
		viewHolder.rela.setOnLongClickListener(new MyAdapterOnItemLongClick(position));
		viewHolder.img_position.setOnClickListener(new MyAdapterListener(position));
		viewHolder.tv_link.setOnClickListener(new MyAdapterListener(position));
*/
    return convertView;
  }


  private void initCommuData(final ViewHolder viewHolder, Community commu, final int position) {
    String picurl = commu.getImage();
    if (TextUtils.isEmpty(picurl)) {
      viewHolder.contentImg.setVisibility(View.GONE);
    } else {
      viewHolder.contentImg.setVisibility(View.VISIBLE);
      Glide.with(context).load(picurl).asBitmap().into(new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
          viewHolder.contentImg.setImageBitmap(resource);
					/*if (fragmentCommunity != null && !refrushList.contains(position)) {
						refrushList.add(position);
						Log.v("refrushHeight", "重置shouye高度" + position+"resource.getHeight()"+resource.getHeight());
						isRefresh = true;//一次数据加载只调用一次
						listView.post(new Runnable() {
							@Override
							public void run() {
								fragmentCommunity.addHomeViewPagerHeight(resource.getHeight());
								isRefresh=false;
							}
						});
					}*/
        }
      });
    }
    if (TextUtils.isEmpty(commu.getTitle())) {
      viewHolder.linea_link.setVisibility(View.GONE);
    } else {
      viewHolder.linea_link.setVisibility(View.VISIBLE);
      viewHolder.tv_linkcontent.setText("《" + commu.getTitle() + "》|  ");
      viewHolder.tv_linkcontent2.setText(commu.getPages() + "页");
    }
    viewHolder.contentImg.setOnClickListener(new MyAdapterListener(position));
    viewHolder.tv_linkcontent.setOnClickListener(new MyAdapterListener(position));
    viewHolder.tv_linkcontent2.setOnClickListener(new MyAdapterListener(position));
  }

  @Override
  public int getViewTypeCount() {
    return 3;
    //头部刷新  加两个布局  comm book
  }


  class MyAdapterOnItemLongClick implements View.OnLongClickListener {
    private int position;

    public MyAdapterOnItemLongClick(int position) {
      this.position = position;
    }

    @Override
    public boolean onLongClick(View view) {

      return false;
    }
  }

  class MyAdapterListener implements View.OnClickListener {
    private Handler handler = new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(Message message) {

        return false;
      }
    });
    private int position;

    public MyAdapterListener(int pos) {
      position = pos;
    }


    @Override
    public void onClick(View v) {

    }
  }


  public class ViewHolder {
    private int id;
    private LinearLayout liner_content, liner_talk1, liner_zan, liner_talk;
    private TextView nikename, tv_link, tv_time, tv_linkcontent, tv_linkcontent2, tv_zan, tv_talks, tv_alltalks, tv_talk1, tv_talk2;
    private ImageView UserPortrait, UserPortrait1, img_more, img_zan, img_talk, img_sex, img_position;
    private FrameLayout fra_more;
    private LinearLayout linea_link;
    private ImageView contentImg;
    private RelativeLayout rela;
    private TextView tv_maincontent;

    private RelativeLayout rela_onebook;
    private ImageView img_onebook;
    private TextView tv_booktitle, tv_rating;
    private RatingBar ratingBar;
    private LinearLayout liner_morebook;
    private MyGridView book_gv;
  }


}


