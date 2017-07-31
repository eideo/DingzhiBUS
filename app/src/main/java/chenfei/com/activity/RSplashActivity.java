package chenfei.com.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;

/**
 * 实时开屏，广告实时请求并且立即展现
 */
public class RSplashActivity extends Activity {


    private String telephone = "";
    private String password = "";
    private String Issaved = "";
    private Userinfo.DataBean userdata;//用户信息
    public static final String FILE_NAME = "share_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        telephone = (String) SPUtils.get(RSplashActivity.this,"loginname","");
        password = (String) SPUtils.get(RSplashActivity.this,"password","");
        Issaved = (String) SPUtils.get(RSplashActivity.this,"issaved","");
        if (Issaved.equals("true")&&telephone.length() > 0 && password.length() > 0) {
            loginProcess(telephone,password);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(RSplashActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    RSplashActivity.this.finish();
                }
            }, 3000);
        }
    }
    private void loginProcess(final String telephone, final String password) {

        OkHttpUtils
                .get()
                .addParams("loginid", telephone)
                .addParams("loginpass", password)
                .url(ApiInterface.AccountLogin)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String jsonstring) {
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(RSplashActivity.this, "登录失败，请检查账号密码");
                            Intent intent = new Intent(RSplashActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            RSplashActivity.this.finish();
                        }
                        try {
                            Userinfo userinfo = null;
                            userinfo = JSON.parseObject(jsonstring, Userinfo.class);
                            userdata = userinfo.getData().get(0);
                            saveEnvironment(telephone, password, userdata,Issaved);
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
                            intent.setClass(RSplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            RSplashActivity.this.finish();
                            ToastUtils.showLong(RSplashActivity.this,"登录成功");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Intent intent = new Intent(RSplashActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            RSplashActivity.this.finish();
                        }

                    }

                    @Override
                    public void onError(Call arg0, Exception arg1) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(RSplashActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        RSplashActivity.this.finish();
                    }
                });
    }
    private void showNotification() {
        // 创建一个NotificationManager的引用
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        // 定义Notification的各种属性
        Notification notification = new Notification(R.drawable.logo, "更新公告",
                System.currentTimeMillis());
        // FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
        // FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
        // FLAG_ONGOING_EVENT 通知放置在正在运行
        // FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
        // notification.flags |= Notification.FLAG_ONGOING_EVENT; //
        // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        // DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等
        // DEFAULT_LIGHTS 使用默认闪光提示
        // DEFAULT_SOUNDS 使用默认提示声音
        // DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission
        // android:name="android.permission.VIBRATE" />权限
        notification.defaults = Notification.DEFAULT_LIGHTS;
        // 叠加效果常量
        // notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
        notification.ledARGB = Color.BLUE;
        notification.ledOnMS = 5000; // 闪光时间，毫秒
        // 设置通知的事件消息
        CharSequence contentTitle = "新版本更新"; // 通知栏标题
        CharSequence contentText = "亲，请点我进行软件更新，么么哒！"; // 通知栏内容
   /*     Intent notificationIntent = new Intent(this,
                UpadateversionActivity.class); // 点击该通知后要跳转的Activity
        PendingIntent contentItent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(this, contentTitle, contentText,
                contentItent);*/
        // 把Notification传递给NotificationManager
        notificationManager.notify(0, notification);

    }

    private void clearNotification() {
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

    }
    public void saveEnvironment(String loginname, String loginpass, Userinfo.DataBean userdata,String issaved) {

        SharedPreferences sp = getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE); // 私有数据
        SharedPreferences.Editor editor = sp.edit();// 获取编辑�?
        editor.putString("loginname", loginname);
        editor.putString("loginpass", loginpass);
        editor.putString("userid", userdata.getUserid());
        editor.putString("username", userdata.getUsername());
        editor.putString("password", userdata.getPassword());
        editor.putString("sex", userdata.getSex());
        editor.putString("telephone", userdata.getTelephone());
        editor.putString("homebaiduzuobiao", userdata.getHomebaiduzuobiao());
        editor.putString("homeaddress", userdata.getHomeaddress());
        editor.putString("companybaiduzuobiao", userdata.getCompanybaiduzuobiao());
        editor.putString("companyaddress", userdata.getCompanyaddress());
        editor.putString("yue", userdata.getYue());
        editor.putString("email", userdata.getEmail());
        editor.putString("issaved", issaved);
        editor.commit();
    }
}
