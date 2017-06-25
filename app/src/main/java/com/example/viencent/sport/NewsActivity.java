package com.example.viencent.sport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import application.MyApplication;

/**
 * 新闻界面
 * Created by Vincent on 2016/9/7.
 */
public class NewsActivity extends Activity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        MyApplication.getSingleton().addActivity(this);
        webView = (WebView) findViewById(R.id.news_main_body);
        Intent intent = getIntent();
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(intent.getStringExtra("newURL"));
    }


}
