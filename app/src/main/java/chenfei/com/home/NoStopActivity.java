package chenfei.com.home;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.AppUtils;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/31.
 */

public class NoStopActivity extends BaseActivity {


    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.starttime)
    TextView starttime;
    @BindView(R.id.login_user_edit)
    EditText loginUserEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.idcard)
    EditText idcard;
    @BindView(R.id.beizhu_edit)
    EditText beizhuEdit;
    private String[] mString = {"陈家坪汽车站", "菜园坝汽车站", "龙头寺汽车站", "四公里汽车站", "永川", "璧山", "南川"
            , "荣昌", "涪陵", "万州", "铜梁", "北碚", "江津", "秀山", "合川", "长寿", "丰都", "垫江", "忠县"};
    private String[] mStringStart = {};
    private String[] mStringEnd = {};
    private String startname = "";
    private String endname = "";
    Calendar cal = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nostop);
        ButterKnife.bind(this);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("直通车");
        SetRightVisble(false);
    }
    public void Submit() {
        String startname = start.getText().toString();
        if (TextUtils.isEmpty(startname)) {
            ToastUtils.showShort(this, "请选择出发地点");
            return;
        }
        String endname = end.getText().toString();
        if (TextUtils.isEmpty(endname)) {
            ToastUtils.showShort(this, "请选择目的地");
            return;
        }
        String xiangxidizhiname = starttime.getText().toString();
        if (TextUtils.isEmpty(xiangxidizhiname)) {
            ToastUtils.showShort(this, "请选择出发时间");
            return;
        }
        String lianxiren = loginUserEdit.getText().toString();
        if (TextUtils.isEmpty(lianxiren)) {
            ToastUtils.showShort(this, "请输入联系人姓名");
            return;
        }
        String lianxidianhua = phoneEdit.getText().toString();
        if (TextUtils.isEmpty(lianxidianhua)) {
            ToastUtils.showShort(this, "请输入联系人电话号码");
            return;
        }
        String idcardname = idcard.getText().toString();
        if (TextUtils.isEmpty(idcardname)) {
            ToastUtils.showShort(this, "请输入身份证号码");
            return;
        }
        String beizhu = beizhuEdit.getText().toString();
        OkHttpUtils
                .get(ApiInterface.SubmitSchoolbus)
                //  .params("schoolname", schoolname)
                //   .params("studentnum", studentnum)
                .params("lianxiren", lianxiren)
                .params("lianxidianhua", lianxidianhua)
                .params("beizhu", beizhu)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(NoStopActivity.this, "提交失败");
                            return;
                        }
                        JSONObject mJsonObject = null;
                        try {
                            mJsonObject = new JSONObject(jsonstring);
                            if (mJsonObject.getString("urlflag").equals("success")) {
                                ToastUtils.showLong(NoStopActivity.this, "提交成功");
                                NoStopActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        // TODO Auto-generated method stub
                        ToastUtils.showLong(NoStopActivity.this, "连接失败");
                    }
                });
    }

    private void NormalListDialog(final int type) {

        final NormalListDialog dialog = new NormalListDialog(this, type == 1 ? mStringStart : mStringEnd);
        dialog.title("请选择" + (type == 1 ? "出发地" : "目的地"))//
                .titleTextSize_SP(18)//
                .titleBgColor(getResources().getColor(R.color.colorPrimary))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show();
        ;

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 1) {
                    startname = mStringStart[position];
                    start.setText(startname);
                } else {
                    endname = mStringEnd[position];
                    end.setText(endname);
                }
                dialog.dismiss();
            }
        });
    }
    private void SetSelectSource(int tpye, String name) {
        if (tpye == 1) {
            List<String> startlist = Arrays.asList(mString);
            List<String> startlist1 = new ArrayList<>(startlist);
            startlist1.remove(name);
            mStringStart = (String[]) startlist1.toArray(new String[startlist1.size()]);
        } else {
            List<String> endlist = Arrays.asList(mString);
            List<String> endlist1 = new ArrayList<>(endlist);
            endlist1.remove(name);
            mStringEnd = (String[]) endlist1.toArray(new String[endlist1.size()]);
        }

    }

    @OnClick({R.id.start, R.id.end, R.id.starttime, R.id.login_login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.starttime:
                showTimeSelect();
                break;
            case R.id.start:
                if (AppUtils.isNotNullOrEmpty(endname)) {
                    SetSelectSource(1, endname);
                } else {
                    mStringStart = mString;
                }
                NormalListDialog(1);
                break;
            case R.id.end:
                if (AppUtils.isNotNullOrEmpty(startname)) {
                    SetSelectSource(2, startname);
                } else {
                    mStringEnd = mString;
                }
                NormalListDialog(2);
                break;
            case R.id.login_login_btn:
                Submit();
                break;
        }
    }
    public void showTimeSelect() {
        new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, Calendar.MONTH,
                                Calendar.DAY_OF_MONTH, timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String dateTime=sdf.format(calendar.getTime());
                        starttime.setText(dateTime);
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true).show();
    }
}
