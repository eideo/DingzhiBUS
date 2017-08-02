package chenfei.com.activity;

import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.Md5Utils;
import chenfei.com.utils.NetUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUser; // 帐号编辑框
    private EditText mPassword; // 密码编辑框
    private CheckBox IssavedcheckBox;//是否保存密码选项框
    private Userinfo.DataBean userdata;//用户信息
    private String Issaved="false";//是否保存密码


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        InitBase();
        SetRightVisble(false);
        SetMyTitle("登录");
        Button btnRegist = (Button) findViewById(R.id.register);
        mUser = (EditText) findViewById(R.id.login_user_edit);
        mPassword = (EditText) findViewById(R.id.login_passwd_edit);
        IssavedcheckBox = (CheckBox) findViewById(R.id.Savepassword);
    }

    public void doLogin(View v) {
        final String userName = mUser.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showLong(LoginActivity.this,
                    R.string.clue_username_empty_error);
            return;
        }

        final String userPwd = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userPwd)) {
            ToastUtils.showLong(LoginActivity.this,
                    R.string.clue_password_empty_error);
            return;
        }
        if (IssavedcheckBox.isChecked()) {
            Issaved = "true";
        } else {
            Issaved = "false";
        }
        if (NetUtils.isConnected(LoginActivity.this)) {
            DialogHelper.showLoadingDialog(this);
            loginProcess(userName, userPwd);
        } else {
            ToastUtils.showLong(LoginActivity.this, "网络未连接，请检查网络");
        }
    }

    private void loginProcess(final String name, final String psd) {

        OkHttpUtils
                .post(ApiInterface.AccountLogin)
                .params("loginid", name)
                .params("loginpass", Md5Utils.encode(psd))
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(LoginActivity.this, "登录失败，请检查账号密码");
                            return;
                        }
                        try {
                            Userinfo userinfo = null;
                            userinfo = JSON.parseObject(jsonstring, Userinfo.class);
                            userdata = userinfo.getData().get(0);
                            saveEnvironment(name, psd, userdata,Issaved);
                            if (userdata.getHomebaiduzuobiao().length() > 0) {
                                try {
                                    String[] homelatlng = userdata.getHomebaiduzuobiao().split(",");
                                    MyAPP.homelng = new LatLng(
                                            Double.parseDouble(homelatlng[0]),
                                            Double.parseDouble(homelatlng[1]));

                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }

                            if (userdata.getCompanybaiduzuobiao().length() > 0) {
                                try {
                                    String[] companylatlng = userdata.getCompanybaiduzuobiao().split(",");
                                    MyAPP.companylng = new LatLng(
                                            Double.parseDouble(companylatlng[0]),
                                            Double.parseDouble(companylatlng[1]));
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                }
                            }
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                            Toast.makeText(getApplicationContext(), "登录成功",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return;
                        }
                    }
                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        // TODO Auto-generated method stub
                        ToastUtils.showLong(LoginActivity.this, "登录失败，请检查权限");
                    }
                });
    }



    public void Doregister(View v) { // 注册账号
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, TelephoneActivity.class);
	    startActivity(intent);
    }


    public void login_pw(View v) { // 忘记密码按钮
        // Uri uri = Uri.parse("http://3g.qq.com");
        // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // startActivity(intent);
        Intent intent = new Intent();
        //intent.setClass(LoginActivity.this, ForgetpassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }
}