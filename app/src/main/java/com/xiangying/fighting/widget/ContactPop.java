/*
 * ContactPopUtils     2016/12/22 11:25
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiangying.fighting.R;

/**
 * Created by Koterwong on 2016/12/22 11:25
 */
public class ContactPop {
  private Context mContext;
  private int popupWidth;
  private int popupHeight;
  private PopupWindow popupWindow;
  private PopClickEvent mEvent;
  private TextView tv_top;
  private TextView tv_move;


  public static ContactPop Create(Context context,int type) {
    return new ContactPop(context,type);
  }

  public ContactPop(Context context, int type) {
    mContext = context;
    View popupView = LayoutInflater.from(mContext).inflate(R.layout.pop_talk_contract, null);
    tv_top = (TextView) popupView.findViewById(R.id.tv_talk_top);
    tv_move = (TextView) popupView.findViewById(R.id.tv_talk_move_to);
    if (type == 1) {
      //取消指定
      tv_top.setText("取消置顶");
    }else{
      tv_top.setText("置顶");
    }
    popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    popupWindow.setBackgroundDrawable(new BitmapDrawable());
    popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    popupWidth = popupView.getMeasuredWidth();
    popupHeight = popupView.getMeasuredHeight();
  }

  public void show(View view) {
    initEvent();
    int[] location = new int[2];
    view.getLocationOnScreen(location);
    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
  }

  public ContactPop setOnPopClickEvent(PopClickEvent mEvent) {
    this.mEvent = mEvent;
    return this;
  }

  private void initEvent() {
    if (mEvent != null) {
      tv_top.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          mEvent.onTopClick();
        }
      });
      tv_move.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          mEvent.onMoveClick();
        }
      });
    }
  }

  public interface PopClickEvent {
    public void onTopClick();

    public void onMoveClick();
  }
}
