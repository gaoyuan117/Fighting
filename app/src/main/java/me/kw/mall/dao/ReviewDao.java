package me.kw.mall.dao;


import com.android.zcomponent.http.HttpDataLoader;

import me.kw.mall.constant.GlobalConst;
import service.api.CommonReturn;
import service.api.ReviewPageResult;
import service.entity.ReviewService;

public class ReviewDao {
  public static void sendCmdQueryReviews(HttpDataLoader httpDataLoader,
                                         String productId, int type, int page) {
    ReviewService.GetProductReviewRequest request = new ReviewService.GetProductReviewRequest();
    request.Page = page;
    request.Pagesize = GlobalConst.INT_NUM_PAGE;
    request.ProductId = productId;
    request.Rate = type;
    httpDataLoader.doPostProcess(request, ReviewPageResult.class);
  }

  public static void sendCmdQueryAddReview(HttpDataLoader httpDataLoader,
                                           ReviewService.AddReviewRequest request) {
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }

}
