package chenfei.com.activity;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.Md5Utils;
import chenfei.com.utils.ToastUtils;
import chenfei.com.utils.YCUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-31 下午1:27:07
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class UpadatepassActivity extends BaseActivity implements OnClickListener {
     
	private TextView inputphonetView;
	private EditText inputCodeEt, inputpassword,
			inputpasswordagin;
	private TextView inputpasswordTextView,inputpasswordaginTextView;
	// 获取验证码按钮
	private Button requestCodeBtn, commitBtn, passwordButton;
	private String passwordflag = "failed";
	//
	int i = 30;
	private Context context;
     private String telephone ="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.updatepassword);
		InitBase();
		SetLeftVisible(true);
		SetMyTitle("更新密码");
		telephone = getStringData("telephone","");
		init();
	}

	private void init() {
		inputphonetView = (TextView) findViewById(R.id.login_input_phone_et);
		inputphonetView.setText("+86  "+telephone);
		inputCodeEt = (EditText) findViewById(R.id.login_input_code_et);
		inputpassword = (EditText) findViewById(R.id.newpassword);
		inputpasswordagin = (EditText) findViewById(R.id.newpasswordagin);
		inputpasswordTextView=(TextView) findViewById(R.id.newpasswordtv);
		inputpasswordaginTextView = (TextView) findViewById(R.id.newpasswordagintv);
		requestCodeBtn = (Button) findViewById(R.id.login_request_code_btn);
		passwordButton = (Button) findViewById(R.id.password_btn);
		commitBtn = (Button) findViewById(R.id.login_commit_btn);
		requestCodeBtn.setOnClickListener(this);
		commitBtn.setOnClickListener(this);
		passwordButton.setOnClickListener(this);

		// 启动短信验证sdk
		SMSSDK.initSDK(this, "966c0b023bc0", "bb466c532654d4cb6537d7bb66aa9b70");
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_request_code_btn:			
			// 1. 通过规则判断手机号
			SMSSDK.getVerificationCode("86", telephone);

			// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
			requestCodeBtn.setClickable(false);
			requestCodeBtn.setText("重新发送(" + i + ")");
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (; i > 0; i--) {
						handler.sendEmptyMessage(-9);
						if (i <= 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendEmptyMessage(-8);
				}
			}).start();
			break;

		case R.id.login_commit_btn:
			SMSSDK.submitVerificationCode("86", telephone, inputCodeEt
					.getText().toString());
		//	createProgressBar();
			break;
		case R.id.password_btn:
			String mpassword = inputpassword.getText().toString();
			String mpasswordagin = inputpasswordagin.getText().toString();
			if (mpassword.equals("") || mpassword == null) {
				YCUtils.showDialog(this, "请输入密码", "温馨提示");
				return;
			}
			if (mpasswordagin.equals("") || mpasswordagin == null) {
				YCUtils.showDialog(this, "请再次输入密码", "温馨提示");
				return;
			}
			if (!mpassword.equals(mpasswordagin)) {
				YCUtils.showDialog(this, "两次密码不一致，请重新输入", "温馨提示");
				return;
			}
			Updatepassword();
			break;
		}
	}

	/**
	 * 
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				requestCodeBtn.setText("重新发送(" + i + ")");
			} else if (msg.what == -8) {
				requestCodeBtn.setText("获取验证码");
				requestCodeBtn.setClickable(true);
				i = 30;
			} 
			else if(msg.what == 1){
				DialogHelper.showLoadingDialog(context, "提交中...");
				if (passwordflag.equals("success")) {
					UpadatepassActivity.this.finish();
					Toast.makeText(getApplicationContext(), "修改成功",
							Toast.LENGTH_SHORT).show();

				} else {														
					
					Toast.makeText(getApplicationContext(), "修改失败，请重新尝试",
							Toast.LENGTH_SHORT).show();

				}
			}
			
			else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						Toast.makeText(getApplicationContext(), "提交验证码成功",
								Toast.LENGTH_SHORT).show();
							commitBtn.setVisibility(View.GONE);							
							inputCodeEt.setClickable(false);
							inputCodeEt.setFocusable(false);
							requestCodeBtn.setClickable(false);
							inputpassword.setVisibility(View.VISIBLE);
							inputpasswordagin.setVisibility(View.VISIBLE);
							passwordButton.setVisibility(View.VISIBLE);
							inputpasswordTextView.setVisibility(View.VISIBLE);
							inputpasswordaginTextView.setVisibility(View.VISIBLE);
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(getApplicationContext(), "验证码已经发送",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};


	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}

	public void Updatepassword() {
		DialogHelper.dismissLoadingDialog();
		Thread loginThread = new Thread(new Runnable() {

			public void run() {

				Message msg = new Message();
				msg.what = 1;
				Updatepasswordprogess(telephone,
						Md5Utils.encode(inputpassword.getText().toString()));
				handler.sendMessage(msg);
			}
		});
		loginThread.start();
	}

	private void Updatepasswordprogess(String telephone, String password) {
		OkHttpUtils
				.post(ApiInterface.Updatepassword)
				.params("telephone", telephone)
				.params("password", password)
				.execute(new StringCallback() {
					@Override
					public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
						DialogHelper.dismissLoadingDialog();
						if (jsonstring == null || jsonstring.equals("")) {
							ToastUtils.showLong(UpadatepassActivity.this, "返回数据为空");
							passwordflag = "failed";
							return;
						}
						// analyze JSON string
						try {
							JSONObject jsonObject = new JSONObject(jsonstring);
							passwordflag = jsonObject.getString("urlflag");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							passwordflag = "failed";
							return;
						}
					}
					@Override
					public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
						super.onError(isFromCache, call, response, e);
						// TODO Auto-generated method stub
						ToastUtils.showLong(UpadatepassActivity.this, "网络连接失败");
					}
				});
	}
}
