/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chenfei.com.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import chenfei.com.activity.MainActivity;
import chenfei.com.activity.NearlinesAcitivity;
import chenfei.com.activity.WzActivity;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.JsonParser;
import chenfei.com.utils.ToastUtils;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class FaqiRoadFragment extends Fragment implements View.OnClickListener {
    private View faqiroadView;
    private static String TAG = FaqiRoadFragment.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private SharedPreferences mSharedPreferences;

    public static final String PREFER_NAME = "com.iflytek.setting";

    private ImageView voice1, voice2;
    private EditText et_startstop, et_endstop,choosetime_et;
    private TextView nearlinebtn,faqilinebtn;
    int ret = 0; // 函数调用返回值
    int flag = 0;// 判断启动哪个控件语音识别
    Calendar cal = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (faqiroadView == null) {
            faqiroadView = inflater.inflate(R.layout.fragment_road, container,
                    false);
        }
        MainActivity.InitTitle("发起路线",true,false);
        et_startstop = (EditText) faqiroadView.findViewById(R.id.start_et);
        et_startstop.setOnClickListener(this);
        et_endstop = (EditText) faqiroadView.findViewById(R.id.end_et);
        et_endstop.setOnClickListener(this);
        voice1 = (ImageView) faqiroadView.findViewById(R.id.iv_voice1);
        voice1.setOnClickListener(this);
        voice2 = (ImageView) faqiroadView.findViewById(R.id.iv_voice2);
        voice2.setOnClickListener(this);
        choosetime_et=(EditText) faqiroadView.findViewById(R.id.choosetime_et);
        choosetime_et.setOnClickListener(this);
        nearlinebtn= (TextView) faqiroadView.findViewById(R.id.nearline);
        nearlinebtn.setOnClickListener(this);
        faqilinebtn= (TextView) faqiroadView.findViewById(R.id.faqiline);
        faqilinebtn.setOnClickListener(this);
        mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(getActivity(), mInitListener);

        mSharedPreferences = getActivity().getSharedPreferences(PREFER_NAME,
                Activity.MODE_PRIVATE);
        super.onCreateView(inflater, container, savedInstanceState);
        faqiroadView.forceLayout();

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) faqiroadView.getParent();
        if (parent != null) {
            parent.removeView(faqiroadView);
        }
        return faqiroadView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_voice1:

                et_startstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 1;
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

                break;
            case R.id.iv_voice2:
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 2;
                isShowDialog = true;
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

                break;
            case R.id.start_et:
                Intent startintent = new Intent(getActivity(), WzActivity.class);
                startintent.putExtra("data", "出发地");
                startActivityForResult(startintent, 1);
                break;
            case R.id.end_et:
                Intent endintent = new Intent(getActivity(), WzActivity.class);
                endintent.putExtra("data", "目的地");
                startActivityForResult(endintent, 2);
                break;
            case R.id.choosetime_et:
                showTimeSelect();
                break;
            case R.id.nearline:
                Intent nearlineintent = new Intent(getActivity(), NearlinesAcitivity.class);
                startActivity(nearlineintent);
                break;
            case R.id.faqiline:


                break;

            default:
                break;

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

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
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
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
              ToastUtils.showLong(getActivity(),"初始化失败，错误码：" + code);
            }
        }
    };
    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            ToastUtils.showLong(getActivity(),"开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            ToastUtils.showLong(getActivity(),error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            ToastUtils.showLong(getActivity(),"结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            ToastUtils.showLong(getActivity(),"当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
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

    //    mResultText.setText(resultBuffer.toString());
   //     mResultText.setSelection(mResultText.length());
    }

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
            ToastUtils.showLong(getActivity(),error.getPlainDescription(true));
        }

    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }
    private void showTip(final String str) {
        ToastUtils.showLong(getActivity(),str);
    }

    public void Searchway() {
        final String chufadi = et_startstop.getText().toString().trim();
        final String mudidi = et_endstop.getText().toString().trim();
        final String time = choosetime_et.getText().toString().trim();

        if (TextUtils.isEmpty(chufadi)) {
            showTip("请输入出发地");
            return;
        }
        if (TextUtils.isEmpty(mudidi)) {
            showTip("请输入目的地");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            showTip("请输入发车时间");
            return;
        }
//        Intent intent = new Intent(this, LinesdetailAcitivity.class);
//        Bundle bdBundle = new Bundle();
//        bdBundle.putString("startstop", cfdwzString);
//        bdBundle.putString("endstop", mddwzString);
//        bdBundle.putString("starttime", choosetimeString);
//        intent.putExtras(bdBundle);
//        startActivityForResult(intent, 3);
//		LineItem lineItem=new LineItem();
//		lineItem.startstop=cfdwzString;
//		lineItem.endstop=mddwzString;
//		lineItem.starttime=choosetimeString;
//		LinesdetailAcitivity.actionStart(context, lineItem);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras(); // data为B中回传的Intent
                    String cfdwzString = b.getString("wz");// str即为回传的值
                    et_startstop.setText(cfdwzString);
                    // do something
                    break;
                }
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras(); // data为B中回传的Intent
                    String mddwzString = b.getString("wz");// str即为回传的值
                    et_endstop.setText(mddwzString);
                    // do something
                    break;
                }
            case 3:
                if (resultCode == Activity.RESULT_OK) {
//				Bundle b = data.getExtras(); // data为B中回传的Intent
//				flag = b.getString("flag");// str即为回传的值
//				System.out.println(flag);
//                    if(myrouteFragment!= null){
//                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                        ft.remove(myrouteFragment);
//                        myrouteFragment=new MyrouteFragment();
////					      ft.commit();
////					      ft=null;
////					      getSupportFragmentManager().executePendingTransactions();
//                        Message msg = new Message();
//                        msg.what = 1;
//                        handler.sendMessage(msg);
//                    }
                    break;
                }
        }
    }
    public void showTimeSelect() {
        new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, Calendar.MONTH,
                                Calendar.DAY_OF_MONTH, timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String dateTime=sdf.format(calendar.getTime());
                        choosetime_et.setText(dateTime);
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true).show();
    }
}
