package chenfei.com.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/31.
 */

public class SchoolBusActivity extends BaseActivity {
    @BindView(R.id.schoolname_edit)
    EditText schoolnameEdit;
    @BindView(R.id.schoolnum_edit)
    EditText schoolnumEdit;
    @BindView(R.id.login_user_edit)
    EditText loginUserEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.beizhu_edit)
    EditText beizhuEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolbus);
        ButterKnife.bind(this);
        InitBase();
        SetLeftVisible(true);
        SetMyTitle("校车");
        SetRightVisble(false);
    }

    @OnClick(R.id.login_login_btn)
    public void onClick() {

        String schoolname = schoolnameEdit.getText().toString();
        if(TextUtils.isEmpty(schoolname)){
            ToastUtils.showShort(this, schoolnameEdit.getHint());
            return;
        }
        String studentnum = schoolnumEdit.getText().toString();
        if(TextUtils.isEmpty(studentnum)){
            ToastUtils.showShort(this, schoolnumEdit.getHint());
            return;
        }
        String lianxiren = loginUserEdit.getText().toString();
        if(TextUtils.isEmpty(lianxiren)){
            ToastUtils.showShort(this, loginUserEdit.getHint());
            return;
        }
        String lianxidianhua = phoneEdit.getText().toString();
        if(TextUtils.isEmpty(lianxidianhua)){
            ToastUtils.showShort(this, phoneEdit.getHint());
            return;
        }
        String beizhu = beizhuEdit.getText().toString();
        if(TextUtils.isEmpty(beizhu)){
            ToastUtils.showShort(this, beizhuEdit.getHint());
            return;
        }

        OkHttpUtils
                .get()
                .addParams("schoolname", schoolname)
                .addParams("studentnum",studentnum )
                .addParams("lianxiren",lianxiren )
                .addParams("lianxidianhua",lianxidianhua )
                .addParams("beizhu",beizhu )
                .url(ApiInterface.SubmitSchoolbus)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String jsonstring) {
                        DialogHelper.dismissLoadingDialog();
                        if (jsonstring == null || jsonstring.equals("")) {
                            ToastUtils.showLong(SchoolBusActivity.this, "提交失败");
                            return;
                        }
                        JSONObject mJsonObject = null;
                        try {
                            mJsonObject = new JSONObject(jsonstring);
                            if (mJsonObject.getString("urlflag").equals("success")) {
                                ToastUtils.showLong(SchoolBusActivity.this, "提交成功");
                                SchoolBusActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Call arg0, Exception arg1) {
                        // TODO Auto-generated method stub
                        ToastUtils.showLong(SchoolBusActivity.this, "连接失败");
                    }
                });
    }
}
