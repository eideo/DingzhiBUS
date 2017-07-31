package chenfei.com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;

import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Projection;

import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.category.AddressItem;
import chenfei.com.utils.JsonParser;
import chenfei.com.utils.ToastUtils;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-20 上午10:28:45
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class WzActivity extends BaseActivity implements
		BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener,
		OnGetSuggestionResultListener {
	Context mContext = this;
	private boolean isFirstLoc = true;
	private boolean isFirstserch = true;
	private LocationClient mLocClient;
	private MyLocationListenner myListener;
	private SuggestionSearch mSuggestionSearch = null;
	// 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	private MapView mMapView = null; // 地图View
	private ImageView imageView = null;
	private BaiduMap mBaidumap = null;
	private String city = "SHANGHAI";
	private LatLng latLng;// 当前经纬度信息
	private LocationMode mCurrentMode;
	String dataString = null;
	BitmapDescriptor mCurrentMarker = null;
	private TextView popupText = null;// 泡泡view
	private AutoCompleteTextView keyWorldsView = null;
	private ArrayAdapter<String> sugAdapter = null;
	ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String, String>>();
	// AddressItem addressItem=new AddressItem();
	List<AddressItem> AddressItems = null;
	MyAdapter adapter = null;
	String adress = "";
	ListView ls = null;
	private String centerimageString = "";
	HashMap<String, Boolean> states = new HashMap<String, Boolean>();
	ImageView voice1;
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	private Toast mToast;
	int ret = 0; // 函数调用返回值

	/**
	 * @param
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Intent intent = this.getIntent();
		dataString = intent.getStringExtra("data");
		setContentView(R.layout.wzqr);
		mLocClient = new LocationClient(this);
		myListener = new MyLocationListenner();
		mLocClient.registerLocationListener(myListener);
		mMapView = (MapView) findViewById(R.id.map);
		mBaidumap = mMapView.getMap();
		imageView = (ImageView) findViewById(R.id.mapzhizheng);

		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.et);
		mBaidumap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
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
		ls = (ListView) findViewById(R.id.serchresultlist);
		ls.setAdapter(adapter);

		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AddressItem item = AddressItems.get(position);
				for (int i = 0; i < AddressItems.size(); i++) {
					AddressItems.get(i).imagecheak = "false";
				}
				item.imagecheak = "true";
				adapter.notifyDataSetChanged();
			}
		});
		voice1 = (ImageView)findViewById(R.id.iv_voice1);
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(WzActivity.this,
				mInitListener);

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(WzActivity.this, mInitListener);
		
		voice1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyWorldsView.setText(null);// 清空显示内容
				mIatResults.clear();
				// 设置参数
				setParam();
				boolean isShowDialog = true;
				if (isShowDialog) {
					// 显示听写对话框
					mIatDialog.setListener(mRecognizerDialogListener);
					mIatDialog.show();
					showTip("请开始说话…");
				} else {
					// 不显示听写对话框
					ret = mIat.startListening(mRecognizerListener);
					if (ret != ErrorCode.SUCCESS) {
						showTip("听写失败,错误码：" + ret);
					} else {
						showTip("请开始说话…");
					}
				}
			}
		});
		
		SuggestSearch();
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
					Projection ject = mBaidumap.getProjection();

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
								Toast.makeText(WzActivity.this, "抱歉，未能找到结果",
										Toast.LENGTH_LONG).show();
							}
							centerimageString = result.getAddress();
							System.out.println(centerimageString);
							popupText = new TextView(WzActivity.this);
							popupText.setBackgroundResource(R.drawable.popup);
							popupText.setTextColor(0xFFFFFFFF);
							popupText.setText(centerimageString);
							mBaidumap.showInfoWindow(new InfoWindow(popupText,
									centerPoint, 0));
							// 移动节点至中心
							Toast.makeText(WzActivity.this,
									"位置：" + result.getAddress(),
									Toast.LENGTH_LONG).show();
							mSuggestionSearch
									.requestSuggestion((new SuggestionSearchOption())
											.keyword(centerimageString).city(
													city));
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

	public void SuggestSearch() {
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);


		sugAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_2);

		keyWorldsView.setAdapter(sugAdapter);
		/**
		 * 当输入关键字变化时，动态更新建议列表
		 */
		keyWorldsView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() <= 0) {
					return;
				}
				/**
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 */
				mSuggestionSearch
						.requestSuggestion((new SuggestionSearchOption())
								.keyword(cs.toString()).city(city));
			}
		});

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
		adapter = new MyAdapter();
		ls.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
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
		//	YCApp.city=city;
			TextView tx = (TextView) findViewById(R.id.city);
			tx.setText(city + "(" + dataString + ")");
			// Toast.makeText(getApplicationContext(), city, 0).show();
			String street = location.getStreet();// 文曲路
			//.setText(street);
			// district = location.getDistrict();// 肥西县
			// longitude = location.getLongitude();
			// latitude = location.getLatitude();
			adress = location.getAddrStr();
			// System.out.println(adress);
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			// 移动节点至中心
			mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(ll));
			// show popup
//			popupText = new TextView(WzActivity.this);
//			popupText.setBackgroundResource(R.drawable.popup);
//			popupText.setTextColor(0xFFFFFFFF);
//			popupText.setText(adress);
//			mBaidumap.showInfoWindow(new InfoWindow(popupText, ll, 0));
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
			}
			// getpoi(location);
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}

	}

	class MyAdapter extends BaseAdapter {
	

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return AddressItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return AddressItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			final AddressItem item = AddressItems.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.beforeserchlistitem, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.text1);
				holder.text = (TextView) convertView.findViewById(R.id.text2);				
				convertView.setTag(holder);
				// convertView =
				// LayoutInflater.from(mContext).inflate(R.layout.serchlistitem,
				// null);
			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position >= AddressItems.size() || position < 0) {
				return convertView;
			}
			holder.title.setText(item.title);
			holder.text.setText(item.text);
			final RadioButton radio = (RadioButton) convertView
					.findViewById(R.id.radio_btn);
			holder.rdBtn = radio;
			holder.rdBtn.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// 重置，确保最多只有一项被选中
					for (String key : states.keySet()) {
						states.put(key, false);
					}
					states.put(String.valueOf(position), radio.isChecked());
					MyAdapter.this.notifyDataSetChanged();
				}
			});
			boolean res = false;
			if (states.get(String.valueOf(position)) == null
					|| states.get(String.valueOf(position)) == false) {
				res = false;
				states.put(String.valueOf(position), false);
			} else
				res = true;

			holder.rdBtn.setChecked(res);
			return convertView;
		}
	}

	static class ViewHolder {
		TextView title;
		TextView text;
		RadioButton rdBtn;
	}

	public void wz_ok(View v) {
		String addString = "";
		for (int i = 0; i < states.size(); i++) {
	//		System.out.println(states.get(i+""));
			if(states.get(i+"")){
				addString = AddressItems.get(i).title;
				break;
			}
		}
		if (addString.length() == 0) {
			Toast.makeText(WzActivity.this, "请选择搜索结果列表中的一项", Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent intent = new Intent();
			intent.putExtra("wz", addString);
			setResult(RESULT_OK, intent);
			this.finish();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mMapView != null) {
			mMapView.onResume(); // 使百度地图地图控件和Fragment的生命周期保持一致
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (mMapView != null) {
			mMapView.onPause(); // 使百度地图地图控件和Fragment的生命周期保持一致
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mBaidumap.setMyLocationEnabled(false);
		mLocClient.stop();// 停止定位
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mIat.cancel();
		mIat.destroy();
		if (mMapView != null) {
			mMapView.onDestroy(); // 使百度地图地图控件和Fragment的生命周期保持一致
		}
	}

	/**
	 * 参数设置
	 * 
	 * @param
	 * @return
	 */
	public void setParam() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// 设置听写引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		String lag = "mandarin";
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, "0");

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				Environment.getExternalStorageDirectory() + "/msc/iat.wav");

		// 设置听写结果是否结果动态修正，为“1”则在听写过程中动态递增地返回结果，否则只在听写结束之后返回最终结果
		// 注：该参数暂时只对在线听写有效
		mIat.setParameter(SpeechConstant.ASR_DWA, "0");
	}

	private void showTip(final String str) {
		ToastUtils.showLong(WzActivity.this,str);
	}

	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			// 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			printResult(results);

			if (isLast) {
				// TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("当前正在说话，音量大小：" + volume);
			System.out.println("返回音频数据：" + data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			// if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			// String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			// Log.d(TAG, "session id =" + sid);
			// }
		}
	};
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			printResult(results);
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};

	private void printResult(RecognizerResult results) {
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}

		keyWorldsView.setText(resultBuffer.toString());
		keyWorldsView.setSelection(keyWorldsView.length());

	
	}


	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code);
			}
		}
	};

}
