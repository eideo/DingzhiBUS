package chenfei.com.activity;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class UpadateversionActivity extends BaseActivity {

    private Button updatebutton;
    private String now_version = "";
    private String newversion = "";
    private String loginbgurl = "";
    private String updateversionurl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.versionupdate);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("版本更新");
        updatebutton = (Button) findViewById(R.id.updateversion);
        TextView version = (TextView) findViewById(R.id.version);
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            now_version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version.setText("当前版本:" + now_version);
        UpdateProcess();


    }
    private void UpdateProcess() {

        OkHttpUtils
                .post(ApiInterface.GetUpdateversion)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(UpadateversionActivity.this, "登录失败，请检查账号密码");
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            newversion = jsonObject.getString("newversion");
                            loginbgurl = jsonObject.getString("loginbgurl");
                            updateversionurl = jsonObject.getString("updateversionurl");
                            if (newversion.equals(now_version)) {
                                updatebutton.setText("已是最新版本");
                                updatebutton.setClickable(false);
                                updatebutton.setBackgroundColor(Color.GRAY);

                            } else {
                                updatebutton.setText("最新版本:" + newversion);
                                updatebutton.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        Uri uri = Uri.parse(updateversionurl);
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivityForResult(intent, 1);
                                    }
                                });

                            }
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
                        ToastUtils.showLong(UpadateversionActivity.this, "网络访问失败");
                    }
                });
    }
}