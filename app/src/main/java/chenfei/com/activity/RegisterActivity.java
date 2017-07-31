package chenfei.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.Md5Utils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUser; // 帐号编辑框
    private EditText mPassword, mPasswordagin; // 密码编辑框
    private Context context;
    private Button registerBtn;
    private String registerflag = "";
    private String sex = "男";
    int i = 30;
    private String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        InitBase();
        SetMyTitle("注册帐号");
        SetRightVisble(false);
        telephone=getIntent().getExtras().getString("telephone");
        mUser = (EditText) findViewById(R.id.register_user_edit);
        mPassword = (EditText) findViewById(R.id.register_passwd_edit);
        mPasswordagin = (EditText) findViewById(R.id.register_passwdagin_edit);
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this);
        RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
        // 绑定一个匿名监听器
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                // 获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                // 根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) RegisterActivity.this
                        .findViewById(radioButtonId);
                // 更新文本内容，以符合选中项
                sex = rb.getText().toString();
            }
        });
    }

    //判断输入格式
    private void panduan() {
        String muser = mUser.getText().toString();
        String mpassword = Md5Utils.encode(mPassword.getText().toString());
        String mpasswordagin = Md5Utils.encode(mPasswordagin.getText().toString());
        if (TextUtils.isEmpty(muser)) {
            ToastUtils.showLong(this, "请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(mpassword)) {
            ToastUtils.showLong(this, "请输入密码");
            return;
        }
        if (TextUtils.isEmpty(mpasswordagin)) {

            ToastUtils.showLong(this, "请再次输入密码");
            return;
        }
        if (!mpassword.equals(mpasswordagin)) {
            ToastUtils.showLong(this, "两次密码不一致，请重新输入");
            mPassword.getText().clear();
            mPasswordagin.getText().clear();
            return;
        }
        dosubmit();
    }
    public void dosubmit() { // 标题栏 返回按钮

        String muser = mUser.getText().toString();
        String mpassword = Md5Utils.encode(mPassword.getText().toString());
        submit(muser, mpassword,telephone);

    }
    private void submit(final String muser, final String mpassword,
                        final String telephone) {
        // TODO Auto-generated method stub
        DialogHelper.showLoadingDialog(context, "注册中...");
        OkHttpUtils
                .get()
                .addParams("muser", muser)
                .addParams("mpassword", mpassword)
                .addParams("telephone", telephone)
                .addParams("sex", sex)
                .url(ApiInterface.AccountRegister)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String jsonstring) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(RegisterActivity.this, "注册失败，请检查网络");
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            registerflag = jsonObject.getString("urlflag");
                            if (registerflag.equals("success")) {
                                Userinfo.DataBean dataBean=null;
                                dataBean.setUserid(jsonObject.getString("userid"));
                                dataBean.setUsername(muser);
                                dataBean.setSex(sex);
                                dataBean.setPassword(Md5Utils.encode(mpassword));
                                dataBean.setTelephone(telephone);
                                saveEnvironment(telephone,Md5Utils.encode(mpassword),dataBean,"false");
                                Intent intent = new Intent();
                                intent.setClass(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                RegisterActivity.this.finish();
                                Toast.makeText(getApplicationContext(), "登录成功",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            ToastUtils.showLong(RegisterActivity.this, "注册失败，后台解析出错");
                            e.printStackTrace();
                            return;
                        }
                    }

                    @Override
                    public void onError(Call arg0, Exception arg1) {
                        // TODO Auto-generated method stub
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(RegisterActivity.this, "注册失败，请检查网络");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.register_btn:
                panduan();
                break;
        }
    }
}
