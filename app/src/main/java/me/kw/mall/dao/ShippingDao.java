package me.kw.mall.dao;


import com.android.zcomponent.http.HttpDataLoader;

import service.api.Shipping;
import service.api.ShippingAdminDetail;
import service.api.ShippingSellerDetail;
import service.entity.ShippingService;

public class ShippingDao {
  public static void sendCmdQueryShippingList(HttpDataLoader httpDataLoader) {
    ShippingService.ShippingListRequest request = new ShippingService.ShippingListRequest();
    httpDataLoader.doPostProcess(request, Shipping.class);
  }

  public static void sendCmdQueryOrderShippingDetail(HttpDataLoader httpDataLoader,
                                                     String orderId) {
    ShippingService.OrderShippingDetailRequest request = new ShippingService
        .OrderShippingDetailRequest();
    request.OrderId = orderId;
    httpDataLoader.doPostProcess(request, ShippingAdminDetail.class);
  }

  public static void sendCmdQueryDistinguishShippingDetail(HttpDataLoader httpDataLoader,
                                                           String distinguishId) {
    ShippingService.DistinguishShippingDetailRequest request = new ShippingService
        .DistinguishShippingDetailRequest();
    request.DistinguishId = distinguishId;
    httpDataLoader.doPostProcess(request, ShippingSellerDetail.class);
  }
}
