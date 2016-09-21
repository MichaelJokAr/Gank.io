package org.jokar.gankio.widget;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jokar.gankio.utils.JLog;

/**
 * Created by JokAr on 16/9/21.
 */

public class MyWebViewClient extends WebViewClient {

    //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        view.loadUrl(url);

        //如果不需要其他对点击链接事件的处理返回true，否则返回false

        return true;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        JLog.e(error.toString());
    }
}
