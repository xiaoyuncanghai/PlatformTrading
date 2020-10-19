package com.pt.lib_common.ui.delegate;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.pt.lib_common.R;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.tencent.smtt.sdk.CookieSyncManager;

public class WebViewActDelegate extends AppDelegate {

    private ProgressBar webview_progressbar;
    private WebView webview_content;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        webview_progressbar = get(R.id.webview_progressbar);
        webview_content = get(R.id.webview_content);
        initWebView();
    }

    //初始化webView
    private void initWebView() {
        WebSettings webSetting = webview_content.getSettings();
        webSetting.setAllowFileAccess(true);
        //webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        //webview_content.loadUrl("file:///android_asset/index.html");
        webview_content.loadUrl("http://120.76.139.208:3355/bmw-bg/sys/terms.do");
        CookieSyncManager.createInstance(this.getActivity());
        CookieSyncManager.getInstance().sync();
    }
}
