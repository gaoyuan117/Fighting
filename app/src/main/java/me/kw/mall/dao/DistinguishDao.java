
package me.kw.mall.dao;

import com.android.zcomponent.http.HttpDataLoader;

import me.kw.mall.constant.GlobalConst;
import service.api.CommonReturn;
import service.api.DistinguishAdd;
import service.api.DistinguishDetail;
import service.api.DistinguishPageResult;
import service.entity.DistinguishService;


public class DistinguishDao {

  public static void sendCmdQueryAddDistinguish(
      HttpDataLoader httpDataLoader, DistinguishService.AddDistinguishRequest request) {
    httpDataLoader.doPostProcess(request, DistinguishAdd.class);
  }

  public static void sendCmdQueryQueryDistinguish(
      HttpDataLoader httpDataLoader, int page, String status) {
    DistinguishService.QueryOrderRequest request =
        new DistinguishService.QueryOrderRequest();
    request.Status = status;
    request.Page = page;
    request.Pagesize = GlobalConst.INT_NUM_PAGE;
    httpDataLoader.doPostProcess(request, DistinguishPageResult.class);
  }

  public static void sendCmdQueryQueryAllDistinguish(HttpDataLoader httpDataLoader, int page) {
    DistinguishService.QueryAllDistinguishRequest request = new DistinguishService
        .QueryAllDistinguishRequest();
    request.Page = page;
    request.Pagesize = GlobalConst.INT_NUM_PAGE;
    httpDataLoader.doPostProcess(request, DistinguishPageResult.class);
  }

  public static void sendCmdQueryDistinguishDetail(HttpDataLoader httpDataLoader, String
      distinguishId) {
    DistinguishService.QueryDistinguishDetailRequest request = new DistinguishService
        .QueryDistinguishDetailRequest();
    request.DistinguishId = distinguishId;
    httpDataLoader.doPostProcess(request, DistinguishDetail.class);
  }

  public static void sendCmdDelDistinguish(HttpDataLoader httpDataLoader, String distinguishId) {
    DistinguishService.DelDistinguishRequest request = new DistinguishService
        .DelDistinguishRequest();
    request.DistinguishId = distinguishId;
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }

  public static void sendCmdQueryDistinguishShippment(HttpDataLoader httpDataLoader, String
      distinguishId, String shippingCode, String code, String[] coverIds) {
    DistinguishService.DistinguishShipmentRequest request = new DistinguishService
        .DistinguishShipmentRequest();
    request.DistinguishId = distinguishId;
    request.ShippingCode = shippingCode;
    request.Code = code;
    request.CoverIds = coverIds;
    httpDataLoader.doPostProcess(request, CommonReturn.class);
  }
}
