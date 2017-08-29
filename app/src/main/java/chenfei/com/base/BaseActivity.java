package chenfei.com.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.jaeger.library.StatusBarUtil;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.ActivityCollector;
import chenfei.com.utils.SPUtils;

public class BaseActivity extends AppCompatActivity {


    public ImageButton backbtn;//返回
    public static TextView titletv;//标题
    public ImageButton rightbtn;
    public static final String FILE_NAME = "share_data";
    public SharedPreferences sp;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void InitBase() {
        backbtn = (ImageButton) findViewById(R.id.back);
        titletv = (TextView) findViewById(R.id.title);
        rightbtn = (ImageButton) findViewById(R.id.more);
    }

    public void goBack(View view) {
        this.finish();
    }

    public void SetMyTitle(String string) {

        titletv.setText(string);
    }

    public void  SetLeftVisible(boolean b) {

        if (b) {
            backbtn.setVisibility(View.VISIBLE);
        } else {
            backbtn.setVisibility(View.INVISIBLE);
        }
    }

    public void SetRightVisble(boolean b) {
        if (b) {
            rightbtn.setVisibility(View.VISIBLE);
        } else {
            rightbtn.setVisibility(View.INVISIBLE);
        }

    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void saveEnvironment(String loginname, String loginpass, Userinfo.DataBean userdata,String issaved) {

        sp = getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE); // 私有数据
        SharedPreferences.Editor editor = sp.edit();// 获取编辑�?
        editor.putString("loginname", loginname);
        editor.putString("loginpass", loginpass);
        editor.putString("userid", userdata.getUserid());
        editor.putString("username", userdata.getUsername());
        editor.putString("password", userdata.getPassword());
        editor.putString("sex", userdata.getSex());
        editor.putString("telephone", userdata.getTelephone());
        editor.putString("homebaiduzuobiao", userdata.getHomebaiduzuobiao());
        editor.putString("homeaddress", userdata.getHomeaddress());
        editor.putString("companybaiduzuobiao", userdata.getCompanybaiduzuobiao());
        editor.putString("companyaddress", userdata.getCompanyaddress());
        editor.putString("yue", userdata.getYue());
        editor.putString("email", userdata.getEmail());
        editor.putString("issaved", issaved);
        editor.commit();
    }

    public void InitEnviroment() {
        SPUtils.clear(BaseActivity.this);
    }


    /**
     * @param name
     * @param defaultStr
     * @return 自定义默认值的String
     */
    public  String getStringData(String name, String defaultStr) {

        if (sp == null) {
            sp = getSharedPreferences(FILE_NAME, 0);
        }

        return sp.getString(name, defaultStr);
    }
}
