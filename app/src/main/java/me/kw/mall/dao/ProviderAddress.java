
package me.kw.mall.dao;

import com.android.zcomponent.http.HttpDataLoader;

import service.api.Area;
import service.api.City;
import service.api.Province;
import service.entity.AddressService;


public class ProviderAddress {

  public static void sendCmdQueryProvince(HttpDataLoader httpDataLoader) {
    AddressService.GetProvincesRequest request = new AddressService.GetProvincesRequest();
    httpDataLoader.doPostProcess(request, Province.class);
  }

  public static void sendCmdQueryCity(HttpDataLoader httpDataLoader, String provinceId) {
    AddressService.GetCitiesRequest request = new AddressService.GetCitiesRequest();
    request.ProvinceId = provinceId;
    httpDataLoader.doPostProcess(request, City.class);
  }

  public static void sendCmdQueryArea(HttpDataLoader httpDataLoader, String cityId) {
    AddressService.GetAreasRequest request = new AddressService.GetAreasRequest();
    request.CityId = cityId;
    httpDataLoader.doPostProcess(request, Area.class);
  }
}
