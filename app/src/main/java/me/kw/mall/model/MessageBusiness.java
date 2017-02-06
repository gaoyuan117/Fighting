package me.kw.mall.model;


import com.android.zcomponent.http.HttpDataLoader;

import me.kw.mall.dao.MessageDao;
import service.api.NewsDetail;
import service.api.NewsPageResult;
import service.entity.MessageService;

public class MessageBusiness {
  public static void queryMessageList(HttpDataLoader httpDataLoader,
                                      String type, int page) {
    MessageDao.sendCmdQueryMessage(httpDataLoader, type, page);
  }

  public static void queryMessageDetail(HttpDataLoader httpDataLoader,
                                        String id) {
    MessageDao.sendCmdQueryMessageDetail(httpDataLoader, id);
  }

  public static void queryNewsList(HttpDataLoader httpDataLoader, String type) {
    MessageService.NewsListRequest request = new MessageService.NewsListRequest();
    request.isHot = "1";
    request.Page = 1;
    request.Pagesize = Integer.MAX_VALUE;
    request.Type = type;
    httpDataLoader.doPostProcess(request, NewsPageResult.class);
  }

  public static void queryNewsDetail(HttpDataLoader httpDataLoader,
                                     String id) {
    MessageService.NewsDetailRequest request = new MessageService.NewsDetailRequest();
    request.Id = id;
    httpDataLoader.doPostProcess(request, NewsDetail.class);
  }
}
