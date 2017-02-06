
package me.kw.mall.model;

import android.content.Context;
import android.text.TextUtils;

import com.android.zcomponent.http.HttpDataLoader;
import com.android.zcomponent.util.ShowMsg;

import me.kw.mall.dao.ReviewDao;
import service.entity.ReviewService;


public class ReviewBusiness {

  public static void queryReviews(HttpDataLoader httpDataLoader,
                                  String productId, int type, int page) {
    ReviewDao.sendCmdQueryReviews(httpDataLoader, productId, type, page);
  }

  public static boolean queryAddReview(Context context, HttpDataLoader httpDataLoader,
                                       ReviewService.AddReviewRequest request) {
    if (request.Rate == 0) {
      ShowMsg.showToast(context, "请选择评价");
      return false;
    }

    if (TextUtils.isEmpty(request.Content)) {
      ShowMsg.showToast(context, "请输入评价内容");
      return false;
    }
    ReviewDao.sendCmdQueryAddReview(httpDataLoader, request);
    return true;
  }

}
