
package me.kw.mall.dao;

import com.android.zcomponent.http.HttpDataLoader;

import service.api.ArticleDetail;
import service.api.ArticlesPageResult;
import service.api.CommonReturn;
import service.entity.HelpService;

public class HelpDao {

  public static void sendCmdAddFeedback(HttpDataLoader httpDataLoader,
                                        String content) {
    HelpService.FeedbackRequest request = new HelpService.FeedbackRequest();
    request.Content = content;
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }

  public static void sendCmdQueryArticles(HttpDataLoader httpDataLoader) {
    HelpService.ArticlesRequest request = new HelpService.ArticlesRequest();
    httpDataLoader.doPostProcess(request, ArticlesPageResult.class);
  }

  public static void sendCmdQueryArticlesDetail(
      HttpDataLoader httpDataLoader, String id) {
    HelpService.ArticlesDetailRequest request =
        new HelpService.ArticlesDetailRequest();
    request.Id = id;
    httpDataLoader.doPostProcess(request, ArticleDetail.class);
  }
}
