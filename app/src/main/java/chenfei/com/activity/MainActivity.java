package chenfei.com.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

import chenfei.com.fragment.BaocheFragment;
import chenfei.com.fragment.FaqiRoadFragment;
import chenfei.com.fragment.HomeFragment;
import chenfei.com.fragment.YonghuFragment;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;

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
        headerView.findViewById(R.id.zhuxiaodenglu).setOnClickListener(this);
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
            case  R.id.zhuxiaodenglu://注销登陆
                InitEnviroment();
                Intent zhuxiaointent = new Intent();
                zhuxiaointent.setClass(this, LoginActivity.class);
                this.finish();
                startActivity(zhuxiaointent);
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

}
