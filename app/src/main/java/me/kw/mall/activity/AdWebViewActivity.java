/*
 * AdWebViewActivity     2016/11/18 09:48
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.kw.mall.activity;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.xiangying.fighting.R;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import me.kw.mall.model.ProductBusiness;
import service.api.AdHomeIndex;
import service.entity.ProductService;

/**
 * Created by Koterwong on 2016/11/18 09:48
 */
public class AdWebViewActivity extends MallBaseActivity {

  @Bind(R.id.title_bar_common_iv_operate_3) ImageView mTitleBarCommonIvOperate3;
  @Bind(R.id.title_bar_common_tv_title) FontTextView mTitleBarCommonTvTitle;
  @Bind(R.id.title_bar_common_iv_operate_1) ImageView mTitleBarCommonIvOperate1;
  @Bind(R.id.title_bar_common_iv_operate_2) ImageView mTitleBarCommonIvOperate2;
  @Bind(R.id.webview) WebView mWebView;

  @Override protected int getLayoutId() {
    return R.layout.activity_ad_webview;
  }

  @Override protected void initUI() {
    mTitleBarCommonIvOperate3.setVisibility(View.GONE);
    mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AdWebViewActivity.this.finish();
      }
    });


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


    String id = getIntent().getStringExtra("id");
    if (TextUtils.isEmpty(id)) {
      getDataEmptyView().showViewDataEmpty(true, false, 2, "未查询到数据");
    }
    getDataEmptyView().showViewWaiting();
    ProductBusiness.queryAdHomeIndexDetail(getHttpDataLoader(), id);
  }

  @Override public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(ProductService.IndexbannerDetailRequest.class)) {
      AdHomeIndex response = msg.getRspObject();
      if (response != null && response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
        mTitleBarCommonTvTitle.setText(response.data.get(0).title);
        String html = ProductBusiness.getHtmlData(response.data.get(0).content);
        String content = ProductBusiness.getHTML(html);
        mWebView.loadData(content, "text/html; charset=UTF-8", null);
      } else {
        getDataEmptyView().showViewDataEmpty(true, false, 2, "未查询到数据");
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

  private class MyWebViewClient extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (url != null) {
        view.loadUrl(url);
      }
      return true;
    }
  }
}
