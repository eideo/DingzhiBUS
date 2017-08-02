package chenfei.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import chenfei.com.UI.CircleImageView;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.category.AddressItem;
import chenfei.com.category.DrtlineItem;
import chenfei.com.dialog.CustomDialog;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class DingzhiBusActivity extends BaseActivity implements CloudListener,
        OnGetSuggestionResultListener, OnClickListener {
    private OverlayOptions ooPolyline;
    List<LatLng> points = new ArrayList<LatLng>();
    private OverlayOptions ooDot;
    private ArrayAdapter<String> sugAdapter = null;
    List<AddressItem> AddressItems = null;
    List<DrtlineItem> Drtbuslines = null;
    private MapView mMapView = null; // 地图View
    private LocationClient mLocClient;
    private MyLocationListenner myListener;
    private BaiduMap mBaidumap = null;
    private TextView destinationtitletv, destinationcontenttv = null;
    private String city = "重庆";
    private LatLng latLng;// 当前经纬度信息
    private String startpoint = "";// 当前位置字符串
    private String centerlngString = "";// 屏幕中心坐标字符串
    private String endpointString = "";// 目的地位置字符串
    Context mContext = this;
    private boolean isFirstLoc = true;
    BitmapDescriptor mCurrentMarker = null;
    private TextView popupText = null;// 泡泡view
    String adress = "";
    private boolean isRequest = false;// 是否手动触发请求定位
    private String serchflag, savephotoflag, savefaverateflag;
    private String photopath = "/photo/";
    private String calculatorflag;
    private String passpointString;
    private Marker mMarker;
    private InfoWindow mInfoWindow;
    private LatLng uplatlng;// 上车点
    private LatLng downlatlng;// 下车点
    private LatLng startlatlng;// 当前位置
    private LatLng endlatlng;// 目的地位置
    private LatLng centerlng;// 屏幕中心位置
    BitmapDescriptor popupbg = BitmapDescriptorFactory
            .fromResource(R.drawable.popup);
    BitmapDescriptor startbg = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_start);
    BitmapDescriptor endbg = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_end);
    BitmapDescriptor upicon = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_up);
    BitmapDescriptor downicon = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_down);
    BitmapDescriptor busicon = BitmapDescriptorFactory
            .fromResource(R.drawable.busonlineicon);
    private int startendflag = 0;// 判断选择上下车点时用 0，初始值，1 上车点，2下车点
    private PopupWindow pop;
    private ImageView btnNight, btnWord, btnExit;
    private CircleImageView circleImageView;
    private TextView arrivetimeTextView, calculatordistanceTextView,
            calculatortimeTextView, calculatorpriceTextView;
    private View view;
    private int upnum;
    private int downnum;
    private String calculatordistance;
    private String calculatorprice;
    private String calculatortime;
    private Bitmap photo;// 头像BITMAP
    private String uptitle;// 上车点的标题
    private String downtitle;// 下车点的标题
    private String lineid;
    private String startstop;
    private String endstop;
    private String buszuobiao;
    private List<Marker> busiconmarkers = new ArrayList<Marker>();
    private List<Marker> upmarkers = new ArrayList<Marker>();
    private List<Marker> downmarkers = new ArrayList<Marker>();
    private boolean Threadflag = true;
    private String saveinformationflag;
    private String Threadopenflag = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingzhibus);
        CloudManager.getInstance().init(DingzhiBusActivity.this);
        initViews();
        initinfowindowView();
        initinfowindowData();
        mBaidumap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
            LatLng startLng, finishLng;
            private final String TAG = "scroll";

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "==-->滚动状态开始");
                startLng = arg0.target;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "==-->滚动状态停止");

                finishLng = arg0.target;
                if (startLng.latitude != finishLng.latitude
                        || startLng.longitude != finishLng.longitude) {
                    GeoCoder geoCoder = GeoCoder.newInstance();
                    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                        // 反地理编码查询结果回调函数
                        @Override
                        public void onGetReverseGeoCodeResult(
                                ReverseGeoCodeResult result) {
                            if (result == null
                                    || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                // 没有检测到结果
                                Toast.makeText(getApplicationContext(),
                                        "没有检测到结果", Toast.LENGTH_SHORT).show();
                            }
                            centerlng = finishLng;
                            centerlngString = centerlng.longitude + ","
                                    + centerlng.latitude;
                            destinationtitletv.setText("目的地");
                            destinationcontenttv.setText(result.getAddress());
                            if (startendflag == 0) {
                                popupText.setVisibility(View.VISIBLE);
                                popupText.setText("点击图钉可以设置目的地");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        popupText.setVisibility(View.INVISIBLE);
                                    }
                                }, 3000);

                            }

                        }

                        // 地理编码查询结果回调函数
                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult result) {
                            if (result == null
                                    || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                // 没有检测到结果
                            }
                        }
                    };
                    // 设置地理编码检索监听者
                    geoCoder.setOnGetGeoCodeResultListener(listener);
                    //
                    geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                            .location(finishLng));

                }

            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "==-->滚动状态改变");

            }

        });

        mBaidumap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                if (startendflag == 1) {
                    for (int i = 0; i < upmarkers.size(); i++) {
                        upmarkers.get(i).remove();
                    }
                    upmarkers.removeAll(upmarkers);
                    uplatlng = marker.getPosition();
                    uptitle = marker.getTitle();
                    OverlayOptions oo = new MarkerOptions().position(uplatlng)
                            .icon(upicon);
                    mBaidumap.addOverlay(oo);
                    startendflag = 2;
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(endlatlng);
                    mBaidumap.animateMapStatus(u);
                    NearbySearchInfo info = new NearbySearchInfo();
                    info.ak = "46rquvuRoQPTz3c3rWRuutnz";
                    info.geoTableId = 120258;
                    info.radius = 1000;
                    info.pageIndex = 0;
                    info.pageSize = 8;
                    info.location = endpointString;
                    System.out.println(info.location);
                    CloudManager.getInstance().nearbySearch(info);
                    return true;
                }
                if (startendflag == 2) {
                    for (int i = 0; i < downmarkers.size(); i++) {
                        downmarkers.get(i).remove();
                    }
                    downmarkers.removeAll(downmarkers);
                    // OverlayOptions oo1 = new
                    // MarkerOptions().position(uplatlng)
                    // .icon(upicon);
                    // mBaidumap.addOverlay(oo1);
                    downlatlng = marker.getPosition();
                    downtitle = marker.getTitle();
                    OverlayOptions oo = new MarkerOptions()
                            .position(downlatlng).icon(downicon);
                    mBaidumap.addOverlay(oo);
                    startendflag = 3;
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(downlatlng);
                    mBaidumap.animateMapStatus(u);
                    doCalculator();
                    return true;
                }
                return true;
            }

        });

    }

    @SuppressLint("NewApi")
    private void initViews() {
        mLocClient = new LocationClient(this);
        myListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myListener);
        mMapView = (MapView) findViewById(R.id.map);
        mBaidumap = mMapView.getMap();
        destinationtitletv = (TextView) findViewById(R.id.destinationtitle);
        destinationcontenttv = (TextView) findViewById(R.id.destinationcontent);
        popupText = (TextView) findViewById(R.id.popup);
        // keyWorldsView = (AutoCompleteTextView) findViewById(R.id.et);
        mBaidumap.setMapStatus(MapStatusUpdateFactory.zoomTo(16.0f));
        mMapView.removeViewAt(1);
        mMapView.showZoomControls(false);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(100);
        option.setNeedDeviceDirect(true);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mBaidumap.setMyLocationEnabled(true);// 开启定位图层
        Button reqButton = (Button) findViewById(R.id.request);
        reqButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                requestLocation();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult result, int error) {
        // TODO Auto-generated method stub
        if (result != null) {
            if (result.poiInfo != null) {
                Toast.makeText(DingzhiBusActivity.this, result.poiInfo.title,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DingzhiBusActivity.this, "status:" + result.status,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onGetSearchResult(CloudSearchResult result, int error) {
        // TODO Auto-generated method stub
        if (result != null && result.poiList != null
                && result.poiList.size() > 0) {
            LatLng ll;
            for (CloudPoiInfo info : result.poiList) {
                ll = new LatLng(info.latitude, info.longitude);
                if (startendflag == 1) {
                    OverlayOptions oo = new MarkerOptions().position(ll)
                            .icon(startbg).title(info.title);
                    Marker marker = (Marker) (mBaidumap.addOverlay(oo));
                    upmarkers.add(marker);

                }
                if (startendflag == 2) {
                    OverlayOptions oo = new MarkerOptions().position(ll)
                            .icon(endbg).title(info.title);
                    Marker marker = (Marker) (mBaidumap.addOverlay(oo));
                    downmarkers.add(marker);
                }

            }
            if (startendflag == 1) {
                popupText.setText("点击绿色图标可以设置上车点");
                popupText.setVisibility(View.VISIBLE);
            }
            if (startendflag == 2) {
                popupText.setText("点击红色图标可以设置下车点");
                popupText.setVisibility(View.VISIBLE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    popupText.setVisibility(View.INVISIBLE);
                }
            }, 3000);
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            city = location.getCity();// 合肥市
            String street = location.getAddrStr();// 文曲
            destinationtitletv.setText("当前位置");
            destinationcontenttv.setText(street);
            startpoint = location.getLongitude() + "," + location.getLatitude();
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            // 移动节点至中心
            mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(ll));
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaidumap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                mBaidumap.animateMapStatus(u);
                popupText.setText("移动图钉更改位置");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popupText.setVisibility(View.INVISIBLE);
                    }
                }, 3000);

            }
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }

    }

    /**
     * 手动请求定位的方法
     */
    public void requestLocation() {
        isRequest = true;

        if (mLocClient != null && mLocClient.isStarted()) {
            Toast.makeText(getApplicationContext(), "正在定位中。。。。。",
                    Toast.LENGTH_SHORT).show();
            mLocClient.requestLocation();
        } else {
            Log.d("log", "locClient is null or not started");
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        // TODO Auto-generated method stub
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        AddressItems = new ArrayList<AddressItem>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                try {
                    AddressItem addressItem = new AddressItem();
                    addressItem.title = info.key;
                    addressItem.text = info.key + info.district;
                    addressItem.imagecheak = "false";
                    AddressItems.add(addressItem);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(info.key + info.district + info.city);
                // sugAdapter.add(info.key.toString());
            }
        }
    }

    private void SerchProcess(String startpoint, final String endpoint) {
        DialogHelper.showLoadingDialog(DingzhiBusActivity.this, "正在计算路线...");
        OkHttpUtils.post(ApiInterface.Getdrtlinelists)
                 // 请求方式和请求url
                .tag(DingzhiBusActivity.this)
                .params("startpoint", startpoint)
                .params("endpoint", endpoint)
                // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(DingzhiBusActivity.this, "计算失败，请检查网络。。。");

                    }

                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(DingzhiBusActivity.this, "计算失败，服务器返回出错。。。");
                            serchflag = "failed";
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);

                            if (!jsonstring.contains("linelist")) {
                                ToastUtils.showLong(DingzhiBusActivity.this, "没有匹配的路线。。。");
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("linelist");
                            endlatlng = centerlng;
                            endpointString = endpoint;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                // DrtlineItem item = new DrtlineItem();
                                lineid = jsonItem.getString("lineid");
                                startstop = jsonItem.getString("startstop");
                                endstop = jsonItem.getString("endstop");
                                // item.passpointname = jsonItem.getString("passpointname");
                                passpointString = jsonItem.getString("passpointlating");
                                buszuobiao = jsonItem.getString("buszuobiao");
                                try {
                                    upnum = Integer.parseInt(jsonItem.getString("upnum"));
                                    downnum = Integer.parseInt(jsonItem.getString("downnum"));
                                } catch (NumberFormatException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                                // item.fahuilineid = jsonItem.getString("fahuilineid");
                                // Drtbuslines.add(item);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return;
                        }
                    }
                });


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                    startendflag = 1;
                    NearbySearchInfo info = new NearbySearchInfo();
                    info.ak = "46rquvuRoQPTz3c3rWRuutnz";
                    info.geoTableId = 120258;
                    info.radius = 1000;
                    info.pageIndex = 0;
                    info.pageSize = 8;
                    info.location = startpoint;
                    System.out.println(info.location);
                    CloudManager.getInstance().nearbySearch(info);
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(latLng);
                    mBaidumap.animateMapStatus(u);
                    // String[]
                    // passpoint=Drtbuslines.get(0).getPasspointlating().split(";");
                    String[] passpoint = passpointString.split(";");
                    if (points.size() > 0) {
                        points.clear();
                    }
                    for (int i = upnum; i < downnum + 1; i++) {
                        String[] point = passpoint[i].split(",");
                        String str1 = point[0].substring(1, point[0].length());
                        String str2 = point[1].substring(0,
                                point[1].length() - 1);
                        double x = 0, y = 0;
                        try {
                            x = Double.parseDouble(str1);
                            y = Double.parseDouble(str2);
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        LatLng p = new LatLng(y, x);
                        OverlayOptions ooDot = new DotOptions().center(p)
                                .radius(10).color(0xAAFF0000);
                        mBaidumap.addOverlay(ooDot);
                        points.add(p);
                    }

                    ooPolyline = new PolylineOptions().width(8)
                            .color(0xAA00FF00).points(points);
                    mBaidumap.addOverlay(ooPolyline);
                    if (Threadflag == false) {
                        new Thread(sendable).start();
                        Threadflag = true;
                        // UpdateBuslocationThread.stop();
                    }
                }

            if (msg.what == 2) {
                // YCUtils.dismissProcessDialog();
                //YCUtils.dismissRoundProcessDialog();
                    changePopupWindowState();

            }
            if (msg.what == 3) {
                for (int i = 0; i < busiconmarkers.size(); i++) {
                    busiconmarkers.get(i).remove();
                }
                busiconmarkers.removeAll(busiconmarkers);
                if (buszuobiao.length() > 0) {
                    try {
                        String[] buszuobiaoStrings = buszuobiao.split(";");
                        for (int j = 0; j < buszuobiaoStrings.length; j++) {
                            String[] buszuobiaoString = buszuobiaoStrings[j]
                                    .split(",");
                            double m = Double.parseDouble(buszuobiaoString[1]);
                            double n = Double.parseDouble(buszuobiaoString[2]
                                    .substring(0,
                                            buszuobiaoString[2].length() - 1));
                            LatLng p = new LatLng(n, m);
                            OverlayOptions oo1 = new MarkerOptions()
                                    .position(p).icon(busicon);
                            // mBaidumap.addOverlay(oo1);
                            Marker marker = (Marker) (mBaidumap.addOverlay(oo1));
                            busiconmarkers.add(marker);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "服务器坐标格式错误",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
            if (msg.what == 4) {

                if (saveinformationflag.equals("success")) {
                    Toast.makeText(getApplicationContext(), "设置成功。",

                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "设置失败，请稍候再试。",
                            Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }
    };

    /**
     * 初始化组件
     */
    private void initinfowindowView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        view = inflater.inflate(R.layout.infowindowview, null);
        arrivetimeTextView = (TextView) view.findViewById(R.id.arrivetime);
        calculatordistanceTextView = (TextView) view
                .findViewById(R.id.calculatordistance);
        calculatortimeTextView = (TextView) view
                .findViewById(R.id.calculatortime);
        calculatorpriceTextView = (TextView) view
                .findViewById(R.id.calculatorprice);
        btnNight = (ImageView) view.findViewById(R.id.btnNight);
        btnWord = (ImageView) view.findViewById(R.id.btnWord);
        btnExit = (ImageView) view.findViewById(R.id.btnExit);

    }

    /**
     * 初始化数据
     */
    private void initinfowindowData() {
        btnNight.setOnClickListener(this);
        btnWord.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        view.setFocusableInTouchMode(true);
        pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        pop.setAnimationStyle(R.style.MenuAnimationFade);

    }

    /**
     * 改变 PopupWindow 的显示和隐藏
     */
    private void changePopupWindowState() {
        if (pop.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            pop.dismiss();
        } else {
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            calculatordistanceTextView.setText(calculatordistance + "KM");
            calculatortimeTextView.setText(calculatortime + "MINS");
            calculatorpriceTextView.setText("￥" + calculatorprice);
            pop.showAtLocation(mMapView, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnNight:
                Intent intent = new Intent();
                intent.setClass(this, PaySelectAcitivity.class).setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                ;
                Bundle mBundle = new Bundle();
                mBundle.putString("lineid", lineid);
                mBundle.putString("startstop", startstop);
                mBundle.putString("endstop", endstop);
                mBundle.putString("upstop", uptitle);
                mBundle.putString("downstop", downtitle);
                mBundle.putString("distance", calculatordistance);
                mBundle.putString("runtime", calculatortime);
                mBundle.putString("paymoney", calculatorprice);
                intent.putExtras(mBundle);
                startActivity(intent);
                changePopupWindowState();
                startendflag = 0;
                break;
            case R.id.btnWord:
                String userid = (String) SPUtils.get(DingzhiBusActivity.this, "usrid", "");
                savemyfaverprogress(lineid, userid, startstop, endstop,
                        uptitle, downtitle, calculatordistance, calculatortime,
                        calculatorprice, "重庆");
                break;
            case R.id.btnExit:
                changePopupWindowState();
                break;
        }

    }

    public void doCalculator() {
        String pointsstr = "";
        for (int i = 0; i < points.size(); i++) {
            pointsstr = points.get(i).longitude + "," + points.get(i).latitude
                    + ";" + pointsstr;
        }
        CalculatorProcess(pointsstr);

     ;
    }

    private void CalculatorProcess(String poinString) {

        // String curversion = getResources().getString(R.string.app_version);
        DialogHelper.showLoadingDialog(DingzhiBusActivity.this, "正在计算中...");
        OkHttpUtils.post(ApiInterface.Calculatorlinedistance)
                .url(ApiInterface.Calculatorlinedistance)     // 请求方式和请求url
                .params("points", poinString)
                // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(DingzhiBusActivity.this, "提交失败，请检查网络。。。");

                    }

                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            calculatorflag = "failed";
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);

                            if (!jsonstring.contains("distance")) {
                                calculatorflag = "nodata";
                                return;
                            }
                            calculatorflag = "sucess";
                            calculatordistance = jsonObject.getString("distance");
                            calculatorprice = jsonObject.getString("price");
                            calculatortime = jsonObject.getString("costtime");
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return;
                        }
                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras(); // data为B中回传的Intent
                    String latlngsString = b.getString("latLng");// str即为回传的值
                    String[] latlng = latlngsString.split(",");
                    LatLng destinationlatlng = new LatLng(
                            Double.parseDouble(latlng[0]),
                            Double.parseDouble(latlng[1]));
                    destinationcontenttv.setText(b.getString("text"));
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(destinationlatlng);
                    mBaidumap.animateMapStatus(u);
                    // keyWorldsView.setText(mddwzString);
                    break;
                }
                break;

        }
    }

    private void savemyfaverprogress(String lineid, String userid,
                                     String startstop, String endstop, String upstop, String downstop,
                                     String distance, String runtime, String paymoney, String city) {
        DialogHelper.showLoadingDialog(DingzhiBusActivity.this, "提交中...");
        OkHttpUtils.post(ApiInterface.Calculatorlinedistance)
                .tag(DingzhiBusActivity.this)
                .params("lineid", lineid)
                .params("userid", userid)
                .params("startstop", startstop)
                .params("endstop", endstop)
                .params("upstop", upstop)
                .params("downstop", downstop)
                .params("distance", distance)
                .params("runtime", runtime)
                .params("paymoney", paymoney)
                .params("city", city)
                // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(DingzhiBusActivity.this, "提交失败，请检查网络。。。");

                    }

                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            Toast.makeText(getApplicationContext(), "收藏失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            String savefaverateflag = jsonObject.getString("urlflag");
                            if (savefaverateflag.equals("success")) {
                                Toast.makeText(getApplicationContext(), "收藏成功",
                                        Toast.LENGTH_SHORT).show();
                                changePopupWindowState();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "收藏失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void UpdateuserinformationProcess() {
        DialogHelper.showLoadingDialog(DingzhiBusActivity.this, "提交中...");
        OkHttpUtils.post(ApiInterface.Updateuserinformation)
                // 请求方式和请求url
                .tag(DingzhiBusActivity.this)
                .params("telephone", (String) SPUtils.get(DingzhiBusActivity.this, "telephone", ""))
                .params("homeaddress", (String) SPUtils.get(DingzhiBusActivity.this, "homeaddress", ""))
                .params("companyaddress", (String) SPUtils.get(DingzhiBusActivity.this, "companyaddress", ""))
                .params("email", (String) SPUtils.get(DingzhiBusActivity.this, "email", ""))
                .params("homelngstr", (String) SPUtils.get(DingzhiBusActivity.this, "homelngstr", ""))
                .params("companylngstr", (String) SPUtils.get(DingzhiBusActivity.this, "companylngstr", ""))
                // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(DingzhiBusActivity.this, "提交失败，请检查网络。。。");

                    }

                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(DingzhiBusActivity.this, "保存用户信息失败");
                        }
                        // analyze JSON string
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            String saveinformationflag = jsonObject.getString("urlflag");
                            if (saveinformationflag.equals("success")) {
                                ToastUtils.showLong(DingzhiBusActivity.this, "保存用户信息成功");
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            ToastUtils.showLong(DingzhiBusActivity.this, "解析失败");
                        }
                    }
                });
    }

    public void Dosetdestination(View v) { // 目的地设置
        Intent intent = new Intent(DingzhiBusActivity.this, SetdestinationActivity.class);
        startActivityForResult(intent, 2);
    }

    public void gohome(View v) {
        // 回家
        String homelngstr = (String) SPUtils.get(DingzhiBusActivity.this, "homelngstr", "");
        if (homelngstr.equals("")) {
            Toast.makeText(getApplicationContext(), "请点击地图中心坐标设置回家点",
                    Toast.LENGTH_SHORT).show();
        } else {
            doSearch(homelngstr);
        }

    }

    public void gocompany(View v) { // 上班
        String companylngstr = (String) SPUtils.get(DingzhiBusActivity.this, "companylngstr", "");
        if (companylngstr.equals("")) {
            Toast.makeText(getApplicationContext(), "请点击地图中心坐标设置公司位置",
                    Toast.LENGTH_SHORT).show();
        } else {
            doSearch(companylngstr);
        }
    }

    public void doSearch(final String ltnstr) {

        mBaidumap.clear();
        SerchProcess(startpoint, ltnstr);
    }

    public void showAlertDialog(View view) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setdestinationButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                doSearch(centerlngString);
                // 设置你的操作事项
            }
        });

        builder.sethomeButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.put(DingzhiBusActivity.this,"homeaddress",destinationcontenttv.getText().toString());
                SPUtils.put(DingzhiBusActivity.this,"homelng",destinationcontenttv.getText().toString());
                SPUtils.put(DingzhiBusActivity.this,"homelngstr",destinationcontenttv.getText().toString());
                UpdateuserinformationProcess();
                dialog.dismiss();
            }
        });
        builder.setcompanyButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.put(DingzhiBusActivity.this,"companyaddress",destinationcontenttv.getText().toString());
                SPUtils.put(DingzhiBusActivity.this,"companylng",destinationcontenttv.getText().toString());
                SPUtils.put(DingzhiBusActivity.this,"companylngstr",destinationcontenttv.getText().toString());
                UpdateuserinformationProcess();
                dialog.dismiss();
            }
        });

        builder.setcancelButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    Runnable sendable = new Runnable() {
        public void run() {
            while (Threadflag) {

                synchronized (this) {
                    try {
                        wait(4 * 1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    OkHttpUtils.post(ApiInterface.Getdrtbuszuobiao)
                            // 请求方式和请求url
                            .tag(DingzhiBusActivity.this)
                            .params("lineid", lineid)
                            // 请求的 tag, 主要用于取消对应的请求
                            .execute(new StringCallback() {

                                @Override
                                public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                    super.onError(isFromCache, call, response, e);
                                    DialogHelper.dismissLoadingDialog();
                                    ToastUtils.showLong(DingzhiBusActivity.this, "提交失败，请检查网络。。。");

                                }

                                @Override
                                public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(jsonstring);
                                        buszuobiao = jsonObject.getString("buszuobiao");
                                        Message msg = Message.obtain();
                                        msg.what = 3;
                                        handler.sendMessage(msg);
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                        return;
                                    }
                                }
                            });

                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Threadflag = true;
     //   new Thread(sendable).start();

        // if (mMapView != null) {
        // mMapView.onResume(); // 使百度地图地图控件和Fragment的生命周期保持一致
        // }

    }

    @Override
    public void onPause() {
        super.onPause();
        // if (mMapView != null) {
        // mMapView.onPause(); // 使百度地图地图控件和Fragment的生命周期保持一致
        // }
    }

    @Override
    public void onStop() {
        super.onStop();
        Threadflag = false;
    }

    @Override
    public void onDestroy() {

        mLocClient.stop();
        mBaidumap.setMyLocationEnabled(false);
        if (mMapView != null) {
            mMapView.onDestroy(); // 使百度地图地图控件和Fragment的生命周期保持一致
            mMapView = null;
            CloudManager.getInstance().destroy();
        }
        Threadflag = false;
        super.onDestroy();

    }
}
