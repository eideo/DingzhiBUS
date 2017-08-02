package chenfei.com.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.activity.MainActivity;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.R;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.JsonParser;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 提交包车订单的界面
 * <p/>
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 *
 * @author guolin
 */
public class BaocheFragment extends Fragment {
    private TimePickerView pvTime;
    private View baocheView;
    EditText et_city, et_startstop, et_endstop, et_start_date,
            et_end_date, et_usernum, et_username,
            et_usertelephone, et_fapiao, et_beizhu;
    Spinner sp_province, sp_city, sp_baochefangshi, sp_cheling;
    int year, month, day, hour, min;
    CheckBox ck_fapiao;
    Button bt_ordersubmit;
    LinearLayout fapiaoll;
    String startstop = "";
    String endstop = "";
    String startdate = "";
    String starttime = "";
    String enddate = "";
    String endtime = "";
    String usernum = "";
    String username = "";
    String usertelephone = "";
    String fapiao = "";
    String beizhu = "";
    String baochefangshi = "";
    String cheling = "";
    ImageView voice1, voice2, voice3, voice4, voice5, voice6;
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
    int flag = 0;// 判断启动哪个控件语音识别
    private int[] city = {R.array.beijin_province_item,
            R.array.tianjin_province_item, R.array.heibei_province_item,
            R.array.shanxi1_province_item, R.array.neimenggu_province_item,
            R.array.liaoning_province_item, R.array.jilin_province_item,
            R.array.heilongjiang_province_item, R.array.shanghai_province_item,
            R.array.jiangsu_province_item, R.array.zhejiang_province_item,
            R.array.anhui_province_item, R.array.fujian_province_item,
            R.array.jiangxi_province_item, R.array.shandong_province_item,
            R.array.henan_province_item, R.array.hubei_province_item,
            R.array.hunan_province_item, R.array.guangdong_province_item,
            R.array.guangxi_province_item, R.array.hainan_province_item,
            R.array.chongqing_province_item, R.array.sichuan_province_item,
            R.array.guizhou_province_item, R.array.yunnan_province_item,
            R.array.xizang_province_item, R.array.shanxi2_province_item,
            R.array.gansu_province_item, R.array.qinghai_province_item,
            R.array.linxia_province_item, R.array.xinjiang_province_item,
            R.array.hongkong_province_item, R.array.aomen_province_item,
            R.array.taiwan_province_item};
    private ArrayAdapter<CharSequence> province_adapter;
    private ArrayAdapter<CharSequence> city_adapter;
    private Integer provinceId, cityId;
    private String strprovince, strcity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (baocheView == null) {
            baocheView = inflater.inflate(R.layout.fragment_baoche, container,
                    false);
        }
        MainActivity.InitTitle("我要包车", true, false);
        sp_province = (Spinner) baocheView.findViewById(R.id.sp_province);
        sp_city = (Spinner) baocheView.findViewById(R.id.sp_city);
        et_startstop = (EditText) baocheView.findViewById(R.id.et_startstop);
        et_endstop = (EditText) baocheView.findViewById(R.id.et_endstop);
        et_start_date = (EditText) baocheView.findViewById(R.id.et_start_date);
        //et_start_time = (EditText) view.findViewById(R.id.et_start_time);
        et_end_date = (EditText) baocheView.findViewById(R.id.et_end_date);
        //	et_end_time = (EditText) view.findViewById(R.id.et_end_time);
        et_usernum = (EditText) baocheView.findViewById(R.id.et_usernum);
        et_username = (EditText) baocheView.findViewById(R.id.et_username);
        et_usertelephone = (EditText) baocheView.findViewById(R.id.et_usertelephone);
        et_fapiao = (EditText) baocheView.findViewById(R.id.et_fapiao);
        et_beizhu = (EditText) baocheView.findViewById(R.id.et_beizhu);
        sp_baochefangshi = (Spinner) baocheView.findViewById(R.id.sp_baochefangshi);
        fapiaoll = (LinearLayout) baocheView.findViewById(R.id.fapiaoll);
        sp_cheling = (Spinner) baocheView.findViewById(R.id.sp_cheling);
        ck_fapiao = (CheckBox) baocheView.findViewById(R.id.ck_fapiao);
        bt_ordersubmit = (Button) baocheView.findViewById(R.id.bt_ordersubmit);
        voice1 = (ImageView) baocheView.findViewById(R.id.iv_voice1);
        voice2 = (ImageView) baocheView.findViewById(R.id.iv_voice2);
        voice3 = (ImageView) baocheView.findViewById(R.id.iv_voice3);
        voice4 = (ImageView) baocheView.findViewById(R.id.iv_voice4);
        voice5 = (ImageView) baocheView.findViewById(R.id.iv_voice5);
        voice6 = (ImageView) baocheView.findViewById(R.id.iv_voice6);
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this.getActivity(),
                mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this.getActivity(), mInitListener);
        super.onCreateView(inflater, container, savedInstanceState);
        baocheView.forceLayout();
        initTimePicker();
        ViewGroup parent = (ViewGroup) baocheView.getParent();
        if (parent != null) {
            parent.removeView(baocheView);
        }
        ButterKnife.bind(this, baocheView);
        return baocheView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        sp_province.setPrompt("请选择省份");
        province_adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.province_item, android.R.layout.simple_spinner_item);
        province_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_province.setAdapter(province_adapter);
        sp_province.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                provinceId = sp_province.getSelectedItemPosition();
                strprovince = sp_province.getSelectedItem().toString();
                select(sp_city, city_adapter, city[provinceId]);
                strcity = sp_city.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        ck_fapiao.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    fapiaoll.setVisibility(View.VISIBLE);
                } else {
                    fapiaoll.setVisibility(View.GONE);
                }
            }
        });
        bt_ordersubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowBaochequerenDialog();
            }
        });
        voice1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
            }
        });
        voice2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 2;
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
        voice3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 3;
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
        voice4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 4;
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
        voice5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 5;
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
        voice6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_endstop.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                flag = 6;
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

    }

    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key += r.nextInt();
        key = key.substring(0, 15);
        return key;
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
        ToastUtils.showLong(getActivity(), str);
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
        switch (flag) {
            case 1:
                et_startstop.setText(resultBuffer.toString());
                et_startstop.setSelection(et_startstop.length());
                break;
            case 2:
                et_endstop.setText(resultBuffer.toString());
                et_endstop.setSelection(et_startstop.length());
                break;
            case 3:
                et_username.setText(resultBuffer.toString());
                et_username.setSelection(et_startstop.length());
                break;
            case 4:
                et_usertelephone.setText(resultBuffer.toString());
                et_usertelephone.setSelection(et_startstop.length());
                break;
            case 5:
                et_fapiao.setText(resultBuffer.toString());
                et_fapiao.setSelection(et_startstop.length());
                break;
            case 6:
                et_beizhu.setText(resultBuffer.toString());
                et_beizhu.setSelection(et_startstop.length());
                break;

            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
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

    private void select(Spinner spin, ArrayAdapter<CharSequence> adapter,
                        int arry) {
        adapter = ArrayAdapter.createFromResource(this.getActivity(), arry,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        // spin.setSelection(0,true);
    }

    private void ShowBaochequerenDialog() {
        IsEmpty();
        final MaterialDialog dialog = new MaterialDialog(getActivity());
        dialog.content(
                "是否确定提交包车订单？")//
                .btnText("取消", "确定")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {
                        DialogHelper.showLoadingDialog(getActivity(), "提交中...");
                        OkHttpUtils.post(ApiInterface.AddBaocheOrder)
                                  // 请求方式和请求url
                                .tag(getActivity())
                                .params("orderid", getOutTradeNo())
                                .params("userid", (String) SPUtils.get(getActivity(), "userid", ""))
                                .params("province", strprovince)
                                .params("city", strcity)
                                .params("startstop", startstop)
                                .params("endstop", endstop)
                                .params("startdate", startdate)
                                .params("enddate", enddate)
                                .params("usernum", usernum)
                                .params("username", username)
                                .params("usertelephone", usertelephone)
                                .params("fapiao", fapiao)
                                .params("beizhu", beizhu)
                                .params("baochefangshi", baochefangshi)
                                .params("cheling", cheling)
                                // 请求的 tag, 主要用于取消对应的请求
                                .execute(new StringCallback() {

                                    @Override
                                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                        super.onError(isFromCache, call, response, e);
                                        DialogHelper.dismissLoadingDialog();
                                        ToastUtils.showLong(getActivity(), "提交失败，请检查网络。。。");

                                    }

                                    @Override
                                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                                        DialogHelper.dismissLoadingDialog();
                                        if (s.contains("success")) {
                                            ToastUtils.showLong(getActivity(), "提交成功");
                                            InitData();
                                        } else {
                                            ToastUtils.showLong(getActivity(), "提交失败，服务器解析失败");
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                }
        );
    }

    private void IsEmpty() {
        this.strprovince = sp_province.getSelectedItem().toString();
        strcity = sp_city.getSelectedItem().toString();
        startstop = et_startstop.getText().toString();
        endstop = et_endstop.getText().toString();
        startdate = et_start_date.getText().toString();
        //	starttime = et_start_time.getText().toString();
        enddate = et_end_date.getText().toString();
        //	endtime = et_end_time.getText().toString();
        usernum = et_usernum.getText().toString();
        username = et_username.getText().toString();
        usertelephone = et_usertelephone.getText().toString();
        fapiao = et_fapiao.getText().toString();
        beizhu = et_beizhu.getText().toString();
        baochefangshi = sp_baochefangshi.getSelectedItem().toString();
        cheling = sp_cheling.getSelectedItem().toString();
        if (TextUtils.isEmpty(strprovince)) {
            ToastUtils.showLong(getActivity(), "请填写省/直辖市");
            return;
        }
        if (TextUtils.isEmpty(strcity)) {
            ToastUtils.showLong(getActivity(), "请填写城市");
            return;
        }
        if (TextUtils.isEmpty(startstop)) {
            ToastUtils.showLong(getActivity(), "请填写起点");
            return;
        }
        if (TextUtils.isEmpty(endstop)) {
            ToastUtils.showLong(getActivity(), "请填写终点");
            return;
        }
        if (TextUtils.isEmpty(startdate)) {
            ToastUtils.showLong(getActivity(), "请填写出发时间");
            return;
        }
        if (TextUtils.isEmpty(usernum)) {
            ToastUtils.showLong(getActivity(), "请填写乘客数量");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showLong(getActivity(), "请填写联系姓名");
            return;
        }
        if (TextUtils.isEmpty(usertelephone)) {
            ToastUtils.showLong(getActivity(), "请填写联系电话");
            return;
        }
    }

    private void InitData() {

        et_startstop.setText("");
        et_endstop.setText("");
        et_start_date.setText("");
        //	starttime = et_start_time.getText().toString();
        et_end_date.setText("");
        //	endtime = et_end_time.getText().toString();
        et_usernum.setText("");
        et_username.setText("");
        et_usertelephone.setText("");
        et_fapiao.setText("");
        et_beizhu.setText("");
    }

    @OnClick({R.id.et_start_date, R.id.et_end_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_start_date:
                pvTime.show(view);
                break;
            case R.id.et_end_date:
                pvTime.show(view);
                break;
        }
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                EditText btn = (EditText) v;
                btn.setText(getTime(date));
            }
          })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false)
                .setDividerColor(Color.LTGRAY)
                .setContentSize(18)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
