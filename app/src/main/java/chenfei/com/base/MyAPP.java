package chenfei.com.base;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Config;
import android.view.WindowManager;
import org.xutils.DbManager;
import org.xutils.x;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyAPP extends Application {

    public static MyAPP ycapp;
    public static String homeaddress = null;
    public static String companyaddress = null;
    public static LatLng homelng;
    public static LatLng companylng;
    public static String companylngstr;
    public static String homelngstr;
    public static String version;
    public static DbManager.DaoConfig daoConfig;
    public static ImageSize mImageSize;
    public static DisplayImageOptions defaultOptions;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        ycapp = this;
        version = getVersion();
        InitImageload();
        SDKInitializer.initialize(this);
        SpeechUtility.createUtility(ycapp, "appid=" + getString(R.string.app_id));
        //初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(20000)               //全局的连接超时时间
                .setReadTimeOut(20000)                  //全局的读取超时时间
                .setWriteTimeOut(20000)                 //全局的写入超时时间
                .addInterceptor(new RetryIntercepter())
//                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore());
        x.Ext.init(this);
        daoConfig = new DbManager.DaoConfig()
                .setDbName("ququfilm.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File("/sdcard/dingzhibus")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        mImageSize = new ImageSize(100, 100);
        //显示图片的配置
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.mipmap.empty_photo)
                .showImageOnFail(R.mipmap.empty_photo)
                .build();

    }

    private void InitImageload() {
        // TODO Auto-generated method stub
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(240, 400)//每个缓存文件最大的长宽
                .diskCacheExtraOptions(240, 400, null)//保存到硬盘的最大的长宽
                .threadPoolSize(3)//设置最大的线池数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//线程线内的加载数量
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(16 * 1024 * 1024))
                .memoryCacheSize(16 * 1024 * 1024)//我们内存的最大的大小,当图片缓存超过这个大小 会自动释放
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(200 * 1024 * 1024) // 200 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)//将保存的时候的URL名称用MD5加密
                .diskCacheFileCount(2000)//缓存文件 的数量
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) //后面的是加载超时的时间(30秒)，前面 的是连接的时间(5秒)
                .build();//构建完成
//最后一步将写好的配置设置进去
        ImageLoader.getInstance().init(config);
    }

    public static String getVersion() {
        PackageManager packageManager = ycapp.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    ycapp.getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "1.0";
    }

    public static int getWindowWith() {
        WindowManager wm = (WindowManager) ycapp
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) ycapp
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    public class RetryIntercepter implements Interceptor {

        public int maxRetryCount = 3;
        private int retryCount = 0;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryCount < maxRetryCount) {
                retryCount++;
                response = chain.proceed(request);
            }

            return response;
        }

    }
}
