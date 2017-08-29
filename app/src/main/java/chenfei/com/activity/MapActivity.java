package chenfei.com.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.LocationUtil;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends BaseActivity implements BDLocationListener {

    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.activity_map)
    LinearLayout activityMap;
    private BaiduMap mBaiduMap;
    LocationUtil mLocationUtil;
    boolean isFirstLoc = true;
    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_gcoding);
    int type =0 ;
    private  LatLng mlatlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        InitBase();
        SetLeftVisible(true);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            SetMyTitle("修改家庭位置");
        } else {
            SetMyTitle("修改公司位置");
        }
        rightbtn.setImageResource(R.mipmap.sure);
        mBaiduMap = mMapView.getMap();
        mLocationUtil = new LocationUtil(this, this);
        mLocationUtil.startLocate();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
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
                    Projection ject = mBaiduMap.getProjection();

                    final LatLng centerPoint = finishLng;
                    GeoCoder geoCoder = GeoCoder.newInstance();
                    //
                    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
                        // 反地理编码查询结果回调函数
                        @Override
                        public void onGetReverseGeoCodeResult(
                                ReverseGeoCodeResult result) {
                            if (result == null
                                    || result.error != SearchResult.ERRORNO.NO_ERROR) {
                                // 没有检测到结果
                                Toast.makeText(MapActivity.this, "抱歉，未能找到结果",
                                        Toast.LENGTH_LONG).show();
                            }
                            address.setText("当前位置："+result.getAddress());
                            mlatlng =result.getLocation();
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
                            .location(centerPoint));

                }

            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub
                Log.i(TAG, "==-->滚动状态改变");

            }

        });

    }

    @OnClick(R.id.more)
    public void onClick() {
        String addressstr =address.getText().toString().trim();
        addressstr=addressstr .substring(5,addressstr.length());
        if (type==1){
            SPUtils.put(this,"homeaddress",addressstr);
            SPUtils.put(this,"homelngstr",mlatlng.longitude+","+mlatlng.latitude);
        }
        else{
            SPUtils.put(this,"companyaddress",addressstr);
            SPUtils.put(this,"companylngstr",mlatlng.longitude+","+mlatlng.latitude);
        }
        UpdateuserinformationProcess();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation.getLocType() != BDLocation.TypeGpsLocation && bdLocation.getLocType() != BDLocation.TypeNetWorkLocation && bdLocation.getLocType() != BDLocation.TypeOffLineLocation) {
            //  ToastUtils.showLong(MiddleActivity.this, "定位不成功,2S后自动开启再次定位");
        } else {
            if (bdLocation == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                address.setText("当前位置："+bdLocation.getAddress());
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                mlatlng = ll;
                MarkerOptions option = new MarkerOptions()
                        .position(ll)
                        .icon(mCurrentMarker);
//在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(14.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        mLocationUtil.stopLocate();
    }

    private void UpdateuserinformationProcess() {
        DialogHelper.showLoadingDialog(MapActivity.this, "提交中...");
        OkHttpUtils.post(ApiInterface.Updateuserinformation)
                // 请求方式和请求url
                .tag(MapActivity.this)
                .params("telephone", (String) SPUtils.get(MapActivity.this, "telephone", ""))
                .params("homeaddress", (String) SPUtils.get(MapActivity.this, "homeaddress", ""))
                .params("companyaddress", (String) SPUtils.get(MapActivity.this, "companyaddress", ""))
                .params("email", (String) SPUtils.get(MapActivity.this, "email", ""))
                .params("homelngstr", (String) SPUtils.get(MapActivity.this, "homelngstr", ""))
                .params("companylngstr", (String) SPUtils.get(MapActivity.this, "companylngstr", ""))
                // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(MapActivity.this, "提交失败，请检查网络。。。");

                    }

                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(MapActivity.this, "保存用户信息失败");
                        }
                        // analyze JSON string
                        try {
                            JSONObject jsonObject = new JSONObject(jsonstring);
                            String saveinformationflag = jsonObject.getString("urlflag");
                            if (saveinformationflag.equals("success")) {
                                ToastUtils.showLong(MapActivity.this, "保存用户信息成功");
                                MapActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            ToastUtils.showLong(MapActivity.this, "解析失败");
                        }
                    }
                });
    }

}
