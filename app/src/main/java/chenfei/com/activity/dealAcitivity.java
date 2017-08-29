package chenfei.com.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;

/**
 * @author  作者 E-mail: 
 *@date 创建时间：2015-8-2 下午2:50:34 
 * @version 1.0 
 * @parameter  
 * @since
 * @return  
 */
public class dealAcitivity extends BaseActivity {
	
	
	private WebView mWebView;
	ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deal);
		InitBase();
        SetLeftVisible(true);
		SetMyTitle("使用协议及条款");
		mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String urlStr = ApiInterface.baseurl + "/html/deal.html";
        mWebView.loadUrl(urlStr);

}
}