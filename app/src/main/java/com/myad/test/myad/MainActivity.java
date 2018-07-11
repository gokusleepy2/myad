package com.myad.test.myad;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = "";
        if(intent != null){
            url = intent.getStringExtra("url");
        }
        //实例化WebView对象
        webview = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings s = webview.getSettings();
        String ua = s.getUserAgentString();
        String cache_mode = "true";
        String proxy_ip = "";
        String proxy_port = "";
        if(intent != null){
            ua = intent.getStringExtra("user_agent");
            cache_mode = intent.getStringExtra("cache_mode");
            proxy_ip = intent.getStringExtra("proxy_ip");
            proxy_port = intent.getStringExtra("proxy_port");
        }
        s.setUserAgentString(ua);
        if( cache_mode == "true")
        {
            s.setCacheMode(WebSettings.LOAD_NORMAL);
        }
        else
        {
            s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        s.setAppCacheEnabled(false);

        s.setDomStorageEnabled(true);
        s.setBuiltInZoomControls(false);

        Properties prop = System.getProperties();
        if(proxy_ip != null) {
            prop.setProperty("proxySet", "true");
            prop.setProperty("proxyHost", proxy_ip);
            prop.setProperty("proxyPort", proxy_port);
        }
//        super.onCreate();
//        initMainHandler();
//        initContext();
//        initOKhttpAndCookie();

        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });

        //加载需要显示的网页
        //webview.loadUrl("file:///android_asset/index.html");//显示本地网页

        //设置Web视图
        setContentView(webview);
        webview.loadUrl(url);//显示远程网页
    }

    @Override//设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }
}