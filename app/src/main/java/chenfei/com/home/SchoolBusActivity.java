package chenfei.com.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

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
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
            ToastUtils.showShort(this, "请输入学校名称");
            return;
        }
        String studentnum = schoolnumEdit.getText().toString();
        if(TextUtils.isEmpty(studentnum)){
            ToastUtils.showShort(this, "请输入乘车人数");
            return;
        }
        String lianxiren = loginUserEdit.getText().toString();
        if(TextUtils.isEmpty(lianxiren)){
            ToastUtils.showShort(this, "请输入联系人姓名");
            return;
        }
        String lianxidianhua = phoneEdit.getText().toString();
        if(TextUtils.isEmpty(lianxidianhua)){
            ToastUtils.showShort(this, "请输入联系人电话号码");
            return;
        }
        String beizhu = beizhuEdit.getText().toString();
       /* if(TextUtils.isEmpty(beizhu)){
            ToastUtils.showShort(this, beizhuEdit.getHint());
            return;
        }*/

        OkHttpUtils
                .get(ApiInterface.SubmitSchoolbus)
                .params("schoolname", schoolname)
                .params("studentnum",studentnum )
                .params("lianxiren",lianxiren )
                .params("lianxidianhua",lianxidianhua )
                .params("beizhu",beizhu )
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
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
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        // TODO Auto-generated method stub
                        ToastUtils.showLong(SchoolBusActivity.this, "连接失败");
                    }
                });
    }
}
