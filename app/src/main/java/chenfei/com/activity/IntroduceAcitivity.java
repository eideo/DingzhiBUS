package chenfei.com.activity;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;

/**
 * @author 作者 E-mail:
 * @version 1.0
 * @date 创建时间：2015-8-2 下午2:50:34
 * @parameter
 * @return
 */
public class IntroduceAcitivity extends BaseActivity {


    private WebView mWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("关于我们");
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        String urlStr = ApiInterface.baseurl + "/html/introduce.html";
        mWebView.loadUrl(urlStr);

    }
}