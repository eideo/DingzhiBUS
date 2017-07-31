package chenfei.com.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.ToastUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class YanzhengmaInputActivity extends BaseActivity implements View.OnClickListener {
    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
    private String telephone;
    private TextView telephonetv;
    private EditText yazhengmaedit;
    private Button yanzhengbtn;
    private Button Resendbtn;
    private int time = 60;
    private boolean flag = true;
    private EventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzhengmainput);
        InitBase();
        telephone=getIntent().getExtras().getString("telephone");
        SetRightVisble(false);
        SetMyTitle("验证码");
        InitView();
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        handlerText.sendEmptyMessageDelayed(1, 1000);

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
    private void InitView() {

        telephonetv= (TextView) findViewById(R.id.telephone);
        yazhengmaedit= (EditText) findViewById(R.id.yanzhengma);
        yanzhengbtn= (Button) findViewById(R.id.yanzhengsubmit);
        yanzhengbtn.setOnClickListener(this);
        Resendbtn= (Button) findViewById(R.id.resend);
        Resendbtn.setOnClickListener(this);
        telephonetv.setText(telephone);
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
                    handlerText.sendEmptyMessageDelayed(2, 1000);
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                    handlerText.sendEmptyMessageDelayed(1, 1000);

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){;
                    Toast.makeText(YanzhengmaInputActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                }else{
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(YanzhengmaInputActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    Resendbtn.setText("验证码已发送"+time+"秒");
                    Resendbtn.setClickable(false);
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    time = 60;
                    Resendbtn.setText("重新获取验证码");
                    Resendbtn.setClickable(true);
                }
            }else{
                Intent intent = new Intent();
                intent.setClass(YanzhengmaInputActivity.this, RegisterActivity.class);
                intent.putExtra("telephone",telephone);
                startActivity(intent);
            }
        };
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yanzhengsubmit:
             final String code = yazhengmaedit.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showLong(YanzhengmaInputActivity.this,
                            "验证码不能为空");
                    return;
                }
                if(code.length()==4){
                    SMSSDK.submitVerificationCode("86", telephone, code);
                    flag = false;
                }else{
                    Toast.makeText(YanzhengmaInputActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                    yazhengmaedit.requestFocus();
                }
                break;
            case R.id.resend:
                SMSSDK.getVerificationCode("86", telephone);
                break;
            default:
                break;
        }
    }
}
