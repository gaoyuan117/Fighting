
package me.kw.mall.dao;


import com.android.zcomponent.constant.GlobalConst;
import com.android.zcomponent.http.HttpDataLoader;

import service.api.MessageDetail;
import service.api.MessagePageResult;
import service.entity.MessageService;

public class MessageDao {

  public static void sendCmdQueryMessage(HttpDataLoader httpDataLoader,
                                         String type, int page) {
    MessageService.MessageListRequest request =
        new MessageService.MessageListRequest();
    request.Page = page;
    request.Pagesize = GlobalConst.INT_NUM_PAGE;
    request.Type = type;
    httpDataLoader.doPostProcess(request, MessagePageResult.class);
  }

  public static void sendCmdQueryMessageDetail(HttpDataLoader httpDataLoader,
                                               String id) {
    MessageService.MessageDetailRequest request =
        new MessageService.MessageDetailRequest();
    request.Id = id;
    httpDataLoader.doPostProcess(request, MessageDetail.class);
  }

}
