package org.jokar.gankio.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.ProgressBar;


/** 带进度条的WebView
 * <ul>
 * <li>author: jokAr</li>
 * <li>date: 2015/8/7,14:40</li>
 * <li>mail: langzaitianyag@vip.qq.com</li>
 * </ul>
 */
public class ProgressWebView extends WebView {

    private static final String TAG = "ProgressWebView";
    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20, 0, 0));
        addView(progressbar);
        //        setWebViewClient(new WebViewClient(){});
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            CrashReport.setJavascriptMonitor(view, true);
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

	    @Override
	    public boolean onConsoleMessage(ConsoleMessage cm) {
		    Log.d(TAG, cm.message() + " -- From line "
				    + cm.lineNumber() + " of "
				    + cm.sourceId());
		    return true;
	    }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}