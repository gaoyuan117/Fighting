
package me.kw.mall.model;

import android.content.Context;
import android.text.TextUtils;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.util.LogEx;
import com.android.zcomponent.util.ShowMsg;

import service.api.BaseEntity;

public class CommonValidate {

  public static boolean validateQueryState(Context context, MessageData msg, BaseEntity entity) {
    return validateQueryState(context, msg, entity, null);
  }

  public static boolean validateQueryState(Context context, MessageData msg, BaseEntity entity,
                                           String failMsg) {
    if (null == entity) {
      if (!TextUtils.isEmpty(failMsg)) {
        ShowMsg.showToast(context, msg, failMsg);
      }
      return false;
    }

    if (entity.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
      return true;
    } else {
      if (!TextUtils.isEmpty(failMsg)) {
        ShowMsg.showToast(context, msg, entity.message);
      }
      return false;
    }
  }

  public static String getHTML(String des) {
    String result = "";
    String[] temp_arr = des.split("<img src=\"");
    if (temp_arr.length > 1) {
      for (int i = 0; i < temp_arr.length; i++) {
        if (i == 0) {
          continue;
        }
        String http_str = temp_arr[i].substring(0, 4);
        if (!http_str.equals("http")) {
          temp_arr[i] = "<img src=\"" + Endpoint.HOST + temp_arr[i];
        } else {
          temp_arr[i] = "<img src=\"" + temp_arr[i];
        }
      }
      StringBuilder builder = new StringBuilder();
      for (String str : temp_arr) {
        builder = builder.append(str);
      }
      result = builder.toString();
    }
    LogEx.d("http", result);
    return TextUtils.isEmpty(result) ? des : result;
  }
}
