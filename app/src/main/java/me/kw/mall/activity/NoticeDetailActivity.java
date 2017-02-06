
package me.kw.mall.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.xiangying.fighting.R;

import butterknife.Bind;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.ProductBusiness;
import service.api.HomeNoticeDetail;
import service.entity.ProductService;

@ZTitleMore(false)
public class NoticeDetailActivity extends MallBaseActivity {
  @Bind(R.id.tvew_title_show) TextView mTvewTitle;
  @Bind(R.id.tvew_desc_show) WebView mWebView;
  private String mstrNewsId;

  @Override protected int getLayoutId() {
    return R.layout.activity_notice_detail;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("公告详情");
    getDataEmptyView().showViewWaiting();
    mstrNewsId = getIntent().getStringExtra("id");
    ProductBusiness.queryNoticeDetail(getHttpDataLoader(), mstrNewsId);
    mWebView.getSettings().setJavaScriptEnabled(true);
    mWebView.getSettings().setAllowFileAccess(true);
    mWebView.getSettings().setSupportZoom(true);
    mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    mWebView.getSettings().setDefaultTextEncodingName("utf-8");
    mWebView.getSettings().setAppCacheEnabled(true);
    mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    mWebView.addJavascriptInterface(this, "java2js");
    mWebView.setWebChromeClient(new ChromeClient());
    mWebView.setWebViewClient(new MyWebViewClient());
  }

  @Override
  public void onDataEmptyClickRefresh() {
    ProductBusiness.queryNoticeDetail(getHttpDataLoader(), mstrNewsId);
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ProductService.NoticeDetailRequest.class)) {
      HomeNoticeDetail response = msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        mTvewTitle.setText(response.Data.title);
        String html = ProductBusiness.getHtmlData(response.Data.content);
        html = CommonValidate.getHTML(html);
        mWebView.loadData(html, "text/html; charset=UTF-8", null);
        getDataEmptyView().dismiss();
      } else {
        getDataEmptyView().showViewDataEmpty(true, false, msg, "");
      }
    }
  }

  private class ChromeClient extends WebChromeClient {

    @Override public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (newProgress == 100) {
        getDataEmptyView().dismiss();
      }
    }

    @Override public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      setTitle(title);
    }
  }

  /**
   * 如果页面中链接,如果希望点击链接继续在当前browser中响应,
   * 而不是新开Android的系统browser中响应该链接,必须覆盖 WebView的WebViewClient对象.
   */
  private class MyWebViewClient extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (url != null) {
        view.loadUrl(url);
      }
      return true;
    }
  }
}