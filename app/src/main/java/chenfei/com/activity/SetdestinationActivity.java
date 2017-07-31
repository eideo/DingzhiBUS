package chenfei.com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.category.AddressItem;
import chenfei.com.utils.JsonParser;
import chenfei.com.utils.ToastUtils;

public class SetdestinationActivity extends BaseActivity implements OnGetSuggestionResultListener {
	private AutoCompleteTextView keyWorldsView = null;
	ListView chooseListView = null;
	MyAdapter adapter = null;
	List<AddressItem> AddressItems = null;
	Context mContext = this;
	public static HashMap<Integer, Boolean> states = new HashMap<Integer, Boolean>();
	//HashMap<String, Boolean> states = new HashMap<String, Boolean>();
	ImageView voice;
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	int ret = 0; // 函数调用返回值
	private ArrayAdapter<String> sugAdapter = null;
	private SuggestionSearch mSuggestionSearch = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setdestination);
		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.setdestination);
		TextView titleTextView = (TextView) findViewById(R.id.toptitle);
		titleTextView.setText("设置目的地");
		chooseListView = (ListView) findViewById(R.id.choose_list);
		chooseListView.setAdapter(adapter);		
		chooseListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub																
				ViewHolder holder = (ViewHolder) arg1.getTag();							
				if (holder.choose.isChecked() == true) {
				} else {
					//holder.choose.toggle();// 在每次获取点击的item时改变checkbox的状态
					states.clear();
					states.put(arg2, holder.choose.isChecked()); // 同时修改map的值保存状态
				}				
				adapter.notifyDataSetChanged();				
			}
		});       
		voice = (ImageView)findViewById(R.id.iv_voice);
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(SetdestinationActivity.this,
				mInitListener);

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(SetdestinationActivity.this, mInitListener);
		
		voice.setOnClickListener(new OnClickListener() {

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
						R.layout.serchlistitem, null);
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
			holder.title.setText(item.title);
			holder.text.setText(item.text);
			holder.choose= (CheckBox) convertView
					.findViewById(R.id.choose);
			holder.choose.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked){
					// TODO Auto-generated method stub
					if (isChecked) {
						states.clear();
						states.put(position, isChecked);					
					} else {
						states.put(position, false);				
					}
					adapter.notifyDataSetChanged();	
				}
			});	
			
			holder.choose.setChecked((states.get(position)==null? false:true));	
			return convertView;
		}
	}

	static class ViewHolder {
		TextView title;
		TextView text;
		CheckBox choose;
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
		ToastUtils.showLong(SetdestinationActivity.this,str);
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
								.keyword(cs.toString()).city("重庆"));
			}
		});

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出时释放连接
		mIat.cancel();
		mIat.destroy();
	}
	public void goSave(View v) { // 注册账号
		Intent intent = new Intent();
	//	Bundle bundle = new Bundle();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(Integer.valueOf(i))==null) {
				states.put(i,false);
			}
			if (states.get(Integer.valueOf(i))) {
				intent.putExtra("text", AddressItems.get(i).text);
				intent.putExtra("latLng", AddressItems.get(i).latLng.latitude+","+AddressItems.get(i).latLng.longitude);
				setResult(RESULT_OK, intent);
				this.finish();
			}			
		}
		
	}
	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		// TODO Auto-generated method stub
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		states.clear();
		states.put(0, true);
		sugAdapter.clear();
		AddressItems = new ArrayList<AddressItem>();
//		for (int i = 0; i < res.getAllSuggestions().size(); i++) {
//			if (res.getAllSuggestions().get(i).key!=null) {
//				try {					
//					AddressItem addressItem = new AddressItem();
//					addressItem.title = res.getAllSuggestions().get(i).key;
//					addressItem.text = res.getAllSuggestions().get(i).city+res.getAllSuggestions().get(i).district+"("+res.getAllSuggestions().get(i).key+")";
//					addressItem.latLng=res.getAllSuggestions().get(i).pt;
//					AddressItems.add(addressItem);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
//			}
//			if(i>0){
//				
//				states.put(i, false);
//				
//			}
//		}
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null) {
				try {					
					AddressItem addressItem = new AddressItem();
					addressItem.title = info.key;
					addressItem.text = info.city+info.district+"("+info.key+")";
					addressItem.latLng=info.pt;
					AddressItems.add(addressItem);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				// sugAdapter.add(info.key.toString());
			}
		}
    	adapter = new MyAdapter();
    	chooseListView.setAdapter(adapter);
	}
//	private void Clear(){
//		for (int i = 0; i < AddressItems.size(); i++) {
//			states.put(i, false);
//		}
//	}
}