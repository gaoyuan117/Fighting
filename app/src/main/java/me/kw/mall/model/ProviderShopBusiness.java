
package me.kw.mall.model;

import android.content.Context;
import android.text.TextUtils;

import com.android.zcomponent.http.HttpDataLoader;
import com.android.zcomponent.util.ShowMsg;

import me.kw.mall.dao.ProviderShop;
import service.entity.FileService;

public class ProviderShopBusiness {

    public static void queryShopProtocol(HttpDataLoader httpDataLoader) {
        ProviderShop.sendCmdQueryShopProtocol(httpDataLoader);
    }

    public static void queryShopInfo(HttpDataLoader httpDataLoader) {
        ProviderShop.sendCmdQueryShopInfo(httpDataLoader);
    }

    public static void queryShopInfo2(HttpDataLoader httpDataLoader) {
        ProviderShop.sendCmdQueryShopInfo2(httpDataLoader);
    }

    public static void queryShopOnlineSerivce(HttpDataLoader httpDataLoader) {
        ProviderShop.sendCmdQueryOnlineService(httpDataLoader);
    }

    public static void queryCourse(HttpDataLoader httpDataLoader, String type, int page) {
        ProviderShop.sendCmdQueryCourse(httpDataLoader, type, page);
    }

    public static void queryCourseDetail(HttpDataLoader httpDataLoader, String id) {
        ProviderShop.sendCmdQueryCourseDetail(httpDataLoader, id);
    }

    public static boolean queryShopCreate(final Context context, final HttpDataLoader httpDataLoader,
                                          final String title, final String desc, final String headId, final String signId) {
        if (TextUtils.isEmpty(headId)) {
            ShowMsg.showToast(context, "请设置店铺头像");
            return false;
        }

        if (TextUtils.isEmpty(signId)) {
            ShowMsg.showToast(context, "请设置店铺招牌");
            return false;
        }

        if (TextUtils.isEmpty(title)) {
            ShowMsg.showToast(context, "请输入店铺名称");
            return false;
        }

        if (TextUtils.isEmpty(desc)) {
            ShowMsg.showToast(context, "请输入店铺简介");
            return false;
        }

        return true;
    }

    public static boolean queryShopEdit(Context context, HttpDataLoader httpDataLoader,
                                        String shopId, String title, String desc, String headId,
                                        String signId) {
        if (TextUtils.isEmpty(headId)) {
            ShowMsg.showToast(context, "请设置店铺头像");
            return false;
        }

        if (TextUtils.isEmpty(signId)) {
            ShowMsg.showToast(context, "请设置店铺招牌");
            return false;
        }

        if (TextUtils.isEmpty(title)) {
            ShowMsg.showToast(context, "请输入店铺名称");
            return false;
        }

        if (TextUtils.isEmpty(desc)) {
            ShowMsg.showToast(context, "请输入店铺简介");
            return false;
        }
        ProviderShop.sendCmdQueryShopEdit(httpDataLoader, shopId, title, desc,
                headId, signId);
        return true;
    }

    public static void sendPostRequest(HttpDataLoader httpDataLoader, String filePath) {
        FileService.PostFileRequest request = new FileService.PostFileRequest();
        httpDataLoader.doPostFileProcess(request, filePath, String.class);
    }
}
