/*
 * LogKw     2016/9/18-09-18
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package com.xiangying.fighting.common;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Koterwong on 2016/9/18.
 *
 * Thread.currentThread().getStackTrace();
 */
public class LogKw {

  private static boolean sDebug = true;
  private static String sTag = LogText.class.getSimpleName();

  private static final int JSON_INDENT = 2;

  public static void setDebug(boolean debug) {
    sDebug = debug;
  }

  public static void initLog(String tag, boolean debug) {
    sTag = tag;
    sDebug = debug;
  }

  public static void d(String msg) {
    d(null, msg);
  }

  public static void d(String tag, String msg) {
    if (!sDebug) return;
    LogText.d(getFinalTag(tag), msg);
  }

  public static void e(String msg) {
    e(null, msg);
  }

  public static void e(String tag, String msg) {
    if (!sDebug) return;
    LogText.e(getFinalTag(tag), msg);
  }

  public static void d(String tag, String msg, Object[] params) {
    if (!sDebug) return;
    LogText.d(getFinalTag(tag), String.format(msg, params));
  }

  public static void e(String tag, String msg, Object[] params) {
    if (!sDebug) return;
    LogText.e(getFinalTag(tag), String.format(msg, params));
  }

  public static void json(String json) {
    json(null, json);
  }

  public static void json(String tag, String json) {
    if (!sDebug) return;
    LogText.e(getFinalTag(tag), getPrettyJson(json));
  }

  private static String getPrettyJson(String jsonStr) {
    try {
      jsonStr = jsonStr.trim();
      if (jsonStr.startsWith("{")) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.toString(JSON_INDENT);
      }
      if (jsonStr.startsWith("[")) {
        JSONArray jsonArray = new JSONArray(jsonStr);
        return jsonArray.toString(JSON_INDENT);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return "Invalid Json, Please Check: " + jsonStr;
  }

  private static String getFinalTag(String tag) {
    if (!TextUtils.isEmpty(tag)) {
      return tag;
    }
    return sTag;
  }

  private static class LogText {
    private static final String SINGLE_DIVIDER = "╔════════════════════════════════════════════════════════════";
    private static final String DOUBLE_DIVIDER = "╚════════════════════════════════════════════════════════════";

    private String mTag;

    public LogText(Builder builder) {
      mTag = builder.tag;
    }

    public static Builder builder() {
      return new Builder();
    }

    private static class Builder {
      private String tag;

      public Builder tag(String tag) {
        this.tag = tag;
        return this;
      }

      public LogText build() {
        return new LogText(this);
      }
    }

    public static void d(String tag, String content) {
      LogText logText = LogText.builder().tag(tag).build();
      logText.setUpD(content);
    }

    public static void e(String tag, String content) {
      LogText logText = LogText.builder().tag(tag).build();
      logText.setUpE(content);
    }

    private void setUpD(String content) {
      Log.d(mTag, SINGLE_DIVIDER);
      setUpContentD(content);
      Log.d(mTag, DOUBLE_DIVIDER);
    }

    private void setUpE(String content) {
      Log.e(mTag, SINGLE_DIVIDER);
      setUpContentE(content);
      Log.e(mTag, DOUBLE_DIVIDER);
    }

    private void setUpContentD(String content) {
      StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
      Log.d(mTag, "(" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement
          .getLineNumber() + ")");
      Log.d(mTag, content);
    }

    private void setUpContentE(String content) {
      StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
      Log.e(mTag, "(" + targetStackTraceElement.getFileName() + ":" + targetStackTraceElement
          .getLineNumber() + ")");
      Log.e(mTag, content);
    }

    private StackTraceElement getTargetStackTraceElement() {
      StackTraceElement targetStackTrace = null;
      boolean shouldTrace = false;
      StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();  //获取栈桢集合
      for (StackTraceElement stackTrace : stackTraces) {
        boolean isLogMethod = stackTrace.getClassName().equals(LogKw.class.getName());
        if (shouldTrace && !isLogMethod) {
          targetStackTrace = stackTrace;
          break;
        }
        shouldTrace = isLogMethod;
      }
      return targetStackTrace;
    }
  }
}
