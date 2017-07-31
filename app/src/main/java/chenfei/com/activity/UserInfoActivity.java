package chenfei.com.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;
import chenfei.com.utils.SPUtils;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.name)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.homeaddress)
    TextView homeaddress;
    @BindView(R.id.companyadd)
    TextView companyadd;
    @OnClick(R.id.namell)
    public void namell(View view) {
        // TODO submit data to server...
    }
    @OnClick(R.id.emailll)
    public void emailll(View view) {
        // TODO submit data to server...
    }
    @OnClick(R.id.telephonell)
    public void telephonell(View view) {
        // TODO submit data to server...
    }
    @OnClick(R.id.homeaddressll)
    public void homeaddressll(View view) {
        // TODO submit data to server...
    }
    @OnClick(R.id.companyaddll)
    public void companyaddll(View view) {
        // TODO submit data to server...
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        InitBase();
        SetMyTitle("用户信息");
        SetRightVisble(false);
        username.setText((String)SPUtils.get(UserInfoActivity.this,"username",""));
        email.setText((String)SPUtils.get(UserInfoActivity.this,"email",""));
        telephone.setText((String)SPUtils.get(UserInfoActivity.this,"telephone",""));
        homeaddress.setText((String)SPUtils.get(UserInfoActivity.this,"homeaddress",""));
        companyadd.setText((String)SPUtils.get(UserInfoActivity.this,"companyaddress",""));
    }

}
