package chenfei.com.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;

public class WebviewActivity extends BaseActivity {

    @BindView(R.id.pb_web)
    ProgressBar mPbWeb;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("详细情况");
        InitWebview();
    }

    private void InitWebview() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //  titleview.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                mPbWeb.setProgress(newProgress);
                if (mPbWeb != null && newProgress != 100) {
                    mPbWeb.setVisibility(View.VISIBLE);
                } else if (mPbWeb != null) {
                    mPbWeb.setVisibility(View.GONE);
                }
            }
        });

        webview.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);//启用js
        webview.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webview.loadUrl(getIntent().getStringExtra("url"));
    }
}
