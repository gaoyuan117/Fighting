
package me.kw.mall.model;


import com.android.zcomponent.http.HttpDataLoader;

import me.kw.mall.dao.ShippingDao;

public class ShippingBusiness {

  public static void queryShippingList(HttpDataLoader httpDataLoader) {
    ShippingDao.sendCmdQueryShippingList(httpDataLoader);
  }

  public static void queryOrderShippingDetail(HttpDataLoader httpDataLoader,
                                              String orderId) {
    ShippingDao.sendCmdQueryOrderShippingDetail(httpDataLoader, orderId);
  }

  public static void queryDistinguishShippingDetail(
      HttpDataLoader httpDataLoader, String distinguishId) {
    ShippingDao.sendCmdQueryDistinguishShippingDetail(httpDataLoader, distinguishId);
  }
}
