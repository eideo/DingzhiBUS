package chenfei.com.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.ToastUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class TelephoneActivity extends BaseActivity implements View.OnClickListener{

//    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
//    private static String APPKEY = "15161f3c77092";
//    // 填写从短信SDK应用后台注册得到的APPSECRET
//    private static String APPSECRET = "4c92d077bbb3186baa314ff1cf406cba";
    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
    private static String APPKEY = "f3fc6baa9ac4";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "7f3dedcb36d92deebcb373af921d635a";
    private boolean ready;
    private EditText telephoneedit;
    private Button sendmsgbtn;
    private int time = 60;
    private boolean flag = true;
    private String telephone;
    private EventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephoneinput);
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        InitBase();
        initSDK();
        SetRightVisble(false);
        SetMyTitle("手机号码");
        telephoneedit= (EditText) findViewById(R.id.telephone);
        sendmsgbtn= (Button) findViewById(R.id.sendmsg);
        sendmsgbtn.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readSms = checkSelfPermission(Manifest.permission.READ_SMS);
            int readContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<String>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_SMS);
            }
            if (readContacts != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 4;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                this.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(eventHandler);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
    private void initSDK() {
        // 初始化短信SDK
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        // 注册回调监听接口
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.sendmsg:
                 telephone = telephoneedit.getText().toString().trim();
                if (TextUtils.isEmpty(telephone)) {
                    ToastUtils.showLong(TelephoneActivity.this,
                            "手机号码不能为空");
                    return;
                }
                if(isMobile(telephone)){
                    SMSSDK.getVerificationCode("86", telephone);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(TelephoneActivity.this, YanzhengmaInputActivity.class);
                    intent.putExtra("telephone",telephone);
                    startActivity(intent);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){;
                    Toast.makeText(TelephoneActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                }else{
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(TelephoneActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
