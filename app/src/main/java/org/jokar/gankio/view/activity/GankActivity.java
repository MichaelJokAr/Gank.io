package org.jokar.gankio.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;


import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.utils.JToast;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.MyWebViewClient;
import org.jokar.gankio.widget.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 16/9/21.
 */
public class GankActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    ProgressWebView webView;
    @BindView(R.id.errorView)
    ErrorView errorView;

    private DataEntities mDataEntities;
    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        initToolbar(toolbar, "");
        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        mDataEntities = getIntent().getParcelableExtra("dataEntities");
        WebSettings settings = webView.getSettings();
        webView.setWebViewClient(new MyWebViewClient());
        settings.setJavaScriptEnabled(true);

        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
                    webView.goBack();   //后退

                    return true;    //已处理
                }
            }
            return false;
        });
        webView.loadUrl(mDataEntities.getUrl());
        toolbar.setTitle(mDataEntities.getDesc());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gank_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share: {
                share();
                break;
            }
            case R.id.openUrl: {
                openUrlOtherBrowser();
                break;
            }
            case R.id.copyUrl: {
                copyUrl();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 复制Url
     */
    private void copyUrl() {
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text", webView.getUrl());

        // Set the clipboard's primary clip.
        mClipboardManager.setPrimaryClip(clip);
        JToast.Toast(this, "已复制到剪切板");
    }

    /**
     * 分享
     */
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「Gank.io」的分享:" + mDataEntities.getUrl());
        startActivity(Intent.createChooser(intent, mDataEntities.getDesc()));
    }

    /**
     * 在其他浏览器中打开
     */
    private void openUrlOtherBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(webView.getUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


}
