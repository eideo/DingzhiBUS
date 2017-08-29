package chenfei.com.activity;


import android.content.Context;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;


/**
 * @author  作者 E-mail: 
 *@date 创建时间：2015-10-7 下午10:53:05 
 * @version 1.0 
 * @parameter  
 * @since
 * @return  
 */
public class QuestionCenterActivity extends BaseActivity {

	
	private WebView mWebView;
	ProgressBar progressBar;
	private Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questioncenter);
	    InitBase();
		SetLeftVisible(true);
		SetMyTitle("帮助中心");
		mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String urlStr = ApiInterface.baseurl + "/html/questions.html";
        mWebView.loadUrl(urlStr);
}
}
