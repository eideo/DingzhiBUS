package chenfei.com.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jaeger.library.StatusBarUtil;
import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.S3Object;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import chenfei.com.UI.CircleImageView;
import chenfei.com.fragment.BaocheFragment;
import chenfei.com.fragment.FaqiRoadFragment;
import chenfei.com.fragment.HomeFragment;
import chenfei.com.fragment.YonghuFragment;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.SPUtils;

/**
 * Created by Jaeger on 16/2/14.
 *
 * Email: chjie.jaeger@gamil.com
 * GitHub: https://github.com/laobie
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,OnTabSelectedListener {
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ViewGroup contentLayout;
    private int mStatusBarColor;
    private int mAlpha =0;
    private BottomNavigationBar bottomNavigationBar;
    private  int lastSelectedPosition = 0;
    private  BadgeItem numberBadgeItem;
    private HomeFragment homeFragment;
    private FaqiRoadFragment faqiRoadFragment;
    private BaocheFragment baochefragment;
    private YonghuFragment capturefragment;

    public FragmentManager fragmentManager;
    public String curFragmentTag = "";
    public static ImageView choutiimg;
    public static TextView titletv;
    public static ImageView shareimg;
    CircleImageView touxiang;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bitmap =getLoacalBitmap(Environment.getExternalStorageDirectory()+"/tempuserhead.jpg"); //从本地取图片(在cdcard中获取)  //
                if (bitmap!=null) {
                    touxiang.setImageBitmap(bitmap); //设置Bitmap
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        choutiimg= (ImageView) findViewById(R.id.chouti);
        choutiimg.setOnClickListener(this);
        titletv= (TextView) findViewById(R.id.title);
        shareimg= (ImageView) findViewById(R.id.share);
        shareimg.setVisibility(View.VISIBLE);
        shareimg.setOnClickListener(this);
         NavigationView navigationView= (NavigationView) findViewById(R.id.navigation);
         View headerView = navigationView.getHeaderView(0);
         touxiang = (CircleImageView) headerView.findViewById(R.id.touxiang);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getObject();
            }
        }).start();
        headerView.findViewById(R.id.guanyuwomenll).setOnClickListener(this);
        headerView.findViewById(R.id.xinshouzhinanll).setOnClickListener(this);
        headerView.findViewById(R.id.guizell).setOnClickListener(this);
        headerView.findViewById(R.id.versionupdatell).setOnClickListener(this);
        headerView.findViewById(R.id.shiyongxieyill).setOnClickListener(this);
        StatusBarUtil.setColorForDrawerLayout(MainActivity.this, mDrawerLayout, mStatusBarColor, mAlpha);
        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this, mDrawerLayout, mAlpha);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "首页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.line, "线路").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.baoche, "包车").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.user, "用户").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

    }
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
         else {
            super.onBackPressed();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chouti:// 侧滑菜单单
                mDrawerLayout.openDrawer(Gravity.LEFT);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                        Gravity.LEFT);
                break;
            case  R.id.guanyuwomenll://关于我们
              /*  InitEnviroment();
                Intent zhuxiaointent = new Intent();
                zhuxiaointent.setClass(this, LoginActivity.class);
                this.finish();
                startActivity(zhuxiaointent);*/
                Intent introduceIntent = new Intent();
                introduceIntent.setClass(MainActivity.this, IntroduceAcitivity.class);
                startActivity(introduceIntent);
                break;
            case  R.id.xinshouzhinanll://新手指南
               break;
            case  R.id.guizell://购票规则
                break;
            case  R.id.versionupdatell://版本更新
                startActivity(new Intent(MainActivity.this, UpadateversionActivity.class));
                break;
            case  R.id.shiyongxieyill://使用协议
                Intent dealIntent = new Intent();
                dealIntent.setClass(MainActivity.this, dealAcitivity.class);
                startActivity(dealIntent);
                break;
        }
    }


    @Override
    protected void setStatusBar() {
        mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), mStatusBarColor, mAlpha);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position){
            case 0:
                if (homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, homeFragment);
                break;
            case 1:
                if (faqiRoadFragment == null)
                {
                    faqiRoadFragment = new FaqiRoadFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, faqiRoadFragment);
                break;
            case 2:
                if (baochefragment == null)
                {
                    baochefragment = new BaocheFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, baochefragment);
                break;
            case 3:
                if (capturefragment == null)
                {
                    capturefragment = new YonghuFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, capturefragment);
                break;
            default:
                break;
        }
        transaction.commit();


    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.id_content, homeFragment);
        transaction.commit(); ;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        Fragment f = fragmentManager.findFragmentByTag(curFragmentTag);
        /*然后在碎片中调用重写的onActivityResult方法*/
        f.onActivityResult(requestCode, resultCode, data);
    }
    public static void InitTitle(String titlename,boolean leftvisble,boolean rightvisble){

        titletv.setText(titlename);
        if (leftvisble) {
            choutiimg.setVisibility(View.VISIBLE);
        } else {
            choutiimg.setVisibility(View.INVISIBLE);
        }
        if (rightvisble) {
            shareimg.setVisibility(View.VISIBLE);
        } else {
            shareimg.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 下载object
     *  //断点续传
     *  GetObjectRequest rangeObjectRequest = new GetObjectRequest("test11", "/test/file.txt");
     *  rangeObjectRequest.setRange(0, 10); // retrieve 1st 10 bytes.
     *  S3Object objectPortion = conn.getObject(rangeObjectRequest);
     *
     *  InputStream objectData = objectPortion.getObjectContent();
     *  // "Process the objectData stream.
     *  objectData.close();
     */
    public void getObject(){
        String accessKey = "2lr5pkyVNtZYXv6W44TV";
        String secretKey = "661f0c7733e6aa5d88c273d634b15f6846a688b2";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        SCS conn = new SCSClient(credentials);
        //SDKGlobalConfiguration.setGlobalTimeOffset(-60*5);//自定义全局超时时间5分钟以后(可选项)
        S3Object s3Obj = conn.getObject("dingzhibus", "touxiang/"+(String) SPUtils.get(this,"telephone","")+".jpg");
        InputStream in = s3Obj.getObjectContent();
        byte[] buf = new byte[1024];
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/tempuserhead.jpg"));
            int count;
            while( (count = in.read(buf)) != -1)
            {
                if( Thread.interrupted() )
                {
                    throw new InterruptedException();
                }
                out.write(buf, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //SDKGlobalConfiguration.setGlobalTimeOffset(0);//还原超时时间
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
