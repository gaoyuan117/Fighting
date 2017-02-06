/*
 * CommonUtils     2016/11/5 10:36
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Koterwong on 2016/11/5 10:36
 */
public class CommonUtil {
  private static final String TAG = CommonUtil.class.getSimpleName();
  private static long lastClickTime;

  /**
   * 判断页面在一秒内是否被多次点击
   *
   * @return boolean
   */
  public static boolean isLeastSingleClick() {
    return isLeastSingleClick(1000);
  }

  /**
   * 判断页面否被多次点击
   *
   * @param durtion 间隔时长
   * @return
   */
  public static boolean isLeastSingleClick(int durtion) {
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < durtion) {
      return false;
    }
    lastClickTime = time;
    return true;
  }

  /**
   * 类型安全的String 转 Int
   *
   * @param objString 要转换的对象
   * @param defValue  默认值
   * @return
   */
  public final static int convertToInt(Object objString, int defValue) {
    if (objString == null || "".equals(objString.toString().trim())) {
      return defValue;
    }

    try {
      return Integer.valueOf(objString.toString());
    } catch (Exception e) {
      try {
        return Double.valueOf(objString.toString()).intValue();
      } catch (Exception el) {
        return defValue;
      }
    }
  }

  /**
   * 设置EditView光标在文字末尾
   *
   * @param editText
   */
  public static void setEditViewSelectionEnd(EditText editText) {
    CharSequence text = editText.getText();
    if (text instanceof Spannable) {
      Spannable spanText = (Spannable) text;
      Selection.setSelection(spanText, text.length());
    }
  }

  /**
   * 设置TextView 右侧图片
   *
   * @param context
   * @param tvew
   * @param resId   图片资源ID
   */
  public static void setDrawableRight(Context context, TextView tvew, int resId) {
    Drawable drawable = context.getResources().getDrawable(resId);
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    tvew.setCompoundDrawables(null, null, drawable, null);
  }

  /**
   * 设置TextView 右侧图片
   *
   * @param context
   * @param tvew
   * @param drawable 图片资源ID
   */
  public static void setDrawableRight(Context context, TextView tvew, Drawable drawable) {
    // / 这一步必须要做,否则不会显示.
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    tvew.setCompoundDrawables(null, null, drawable, null);
  }

  /**
   * 设置TextView 顶部图片
   *
   * @param context
   * @param tvew
   * @param resId   图片资源ID
   */
  public static void setDrawableTop(Context context, TextView tvew, int resId) {
    Drawable drawable = context.getResources().getDrawable(resId);
    // / 这一步必须要做,否则不会显示.
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    tvew.setCompoundDrawables(null, drawable, null, null);
  }

  /**
   * Description: 设置TextView 左侧图片
   *
   * @param context
   * @param tvew
   * @param resId   图片资源ID
   */
  public static void setDrawableLeft(Context context, TextView tvew, int resId) {
    Drawable drawable = context.getResources().getDrawable(resId);
    // / 这一步必须要做,否则不会显示.
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    tvew.setCompoundDrawables(drawable, null, null, null);
  }

  /**
   * Description: 设置TextView 底部图片
   *
   * @param context
   * @param tvew
   * @param resId   图片资源ID
   * @date 2014-3-31
   */
  public static void setDrawableBottom(Context context, TextView tvew, int resId) {
    Drawable drawable = context.getResources().getDrawable(resId);
    // / 这一步必须要做,否则不会显示.
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    tvew.setCompoundDrawables(null, null, null, drawable);
  }

  /**
   * 检验EditText输入的内容是否为空
   */
  public static boolean checkEt(Context context, EditText editText, String toast) {
    String content = editText.getText().toString().trim();

    if (TextUtils.isEmpty(content)) {
      Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
      return false;
    }

    return true;
  }

  /**
   * 检验EditText输入的内容是否为空
   */
  public static boolean checkEt(Context context, EditText editText) {
    return checkEt(context, editText, "请填写完整信息");
  }
}
