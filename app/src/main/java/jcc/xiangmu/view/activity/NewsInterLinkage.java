package jcc.xiangmu.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jcc.xiangmu.R;

public class NewsInterLinkage extends AppCompatActivity {

    private WebView mweb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_inter_linkage);
        url = getIntent().getStringExtra("link");
        initview();
        initdata();
        initevent();
    }

    private void initevent() {
        mweb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mweb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    private void initdata() {
        WebSettings settings = mweb.getSettings();
        settings.setJavaScriptEnabled(true);
        mweb.loadUrl(url);

    }

    private void initview() {
        mweb = (WebView) findViewById(R.id.webView);
    }

    @Override
    public void onBackPressed() {

        if (mweb.canGoBack()) {
            mweb.goBack();
        } else {

            super.onBackPressed();
            this.finish();
        }
    }
}
