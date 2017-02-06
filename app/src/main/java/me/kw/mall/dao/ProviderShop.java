
package me.kw.mall.dao;

import com.android.zcomponent.http.HttpDataLoader;

import me.kw.mall.constant.ProviderGlobalConst;
import service.api.CommonReturn;
import service.api.CourseDetail;
import service.api.CoursePageResult;
import service.api.OnlineService;
import service.api.ShopInfo;
import service.entity.ShopService;

public class ProviderShop {

  public static void sendCmdQueryShopProtocol(HttpDataLoader httpDataLoader) {
    ShopService.ShopProtocolRequest request = new ShopService.ShopProtocolRequest();
    httpDataLoader.doPostProcess(request, String.class, false);
  }

  public static void sendCmdQueryShopInfo(HttpDataLoader httpDataLoader) {
    ShopService.ShopInfoRequest request = new ShopService.ShopInfoRequest();
    httpDataLoader.doPostProcess(request, ShopInfo.class);
  }

  public static void sendCmdQueryShopInfo2(HttpDataLoader httpDataLoader) {
    ShopService.ShopInfoRequest2 request = new ShopService.ShopInfoRequest2();
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }


  public static void sendCmdQueryOnlineService(HttpDataLoader httpDataLoader) {
    ShopService.OnlineServiceRequest request = new ShopService.OnlineServiceRequest();
    httpDataLoader.doPostProcess(request, OnlineService.class);
  }


  public static void sendCmdQueryCourse(HttpDataLoader httpDataLoader,
                                        String type, int page) {
    ShopService.CourseListRequest request = new ShopService.CourseListRequest();
    request.Type = type;
    request.Page = page;
    request.Pagesize = ProviderGlobalConst.INT_PAGE_NUMBER;
    httpDataLoader.doPostProcess(request, CoursePageResult.class);
  }

  public static void sendCmdQueryCourseDetail(HttpDataLoader httpDataLoader,
                                              String id) {
    ShopService.CourseDetailRequest request = new ShopService.CourseDetailRequest();
    request.Id = id;
    httpDataLoader.doPostProcess(request, CourseDetail.class);
  }

  public static void sendCmdQueryShopCreate(HttpDataLoader httpDataLoader,
                                            String title, String desc, String headId,
                                            String signId) {
    ShopService.CreateShopRequest request = new ShopService.CreateShopRequest();
    request.Title = title;
    request.Description = desc;
    request.Headpic = headId;
    request.Signpic = signId;
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }

  public static void sendCmdQueryShopEdit(HttpDataLoader httpDataLoader,
                                          String shopId, String title, String desc,
                                          String headId, String signId) {
    ShopService.EditShopRequest request = new ShopService.EditShopRequest();
    request.ShopId = shopId;
    request.Title = title;
    request.Description = desc;
    request.Headpic = headId;
    request.Signpic = signId;
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }
}
