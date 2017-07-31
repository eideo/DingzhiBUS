package chenfei.com.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import chenfei.com.base.ApiInterface;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.category.DrtorderItem;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.Md5Utils;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;

public class PaySelectAcitivity extends BaseActivity {
	private Context context = this;
    private RadioButton zhifubaoButton=null;
    private RadioButton weixinButton=null;
    private RadioGroup radiogroup =null;  
    private TextView paymoneytTextView;
	private CheckBox checkBox;
	private Button submitButton;
	DrtorderItem Item=new DrtorderItem();
	private String submitflag;
	private boolean zhifubaoflag=false;
	private boolean weixinflag=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payselect);
		submitButton = (Button) findViewById(R.id.ordersubmit);
		zhifubaoButton = (RadioButton) findViewById(R.id.zhifubao);
		zhifubaoButton.setChecked(true);
		weixinButton = (RadioButton) findViewById(R.id.weixin);
		paymoneytTextView = (TextView) findViewById(R.id.paymoney);
		checkBox = (CheckBox) findViewById(R.id.youhuijuan);
		Bundle b=getIntent().getExtras();
		 Item.lineid=b.getString("lineid");
		 Item.userid= (String) SPUtils.get(PaySelectAcitivity.this,"userid","");
		 Item.startstop=b.getString("startstop");
		 Item.endstop=b.getString("endstop");
		 Item.distance=b.getString("distance");
		 Item.upstop=b.getString("upstop");
		 Item.downstop=b.getString("downstop");
		 Item.runtime=b.getString("runtime");
		 Item.paymoney=b.getString("paymoney");
		 Item.city="重庆";
		 Item.buytype="支付宝";
		 paymoneytTextView.setText("支付金额："+Item.paymoney+"元");
		 zhifubaoButton.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					weixinButton.setChecked(false);				
		    		Item.buytype="支付宝";
				}
			    });
		 weixinButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					zhifubaoButton.setChecked(false);					
			    	Item.buytype="微信支付";
				
				}
			    });	
	        //为RadioGroup设置监听器，需要注意的是，这里的监听器和Button控件的监听器有所不同  
//	        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
//	              
//	            public void onCheckedChanged(RadioGroup group, int checkedId) {  
//	                // TODO Auto-generated method stub  
//	                if(zhifubaoButton.getId() == checkedId){  
//	                	zhifubaoflag=true;
//	                	weixinflag=false;
//	                	Item.buytype="支付宝";
//	                }  
//	                else if(weixinButton.getId() == checkedId){  
//	                	zhifubaoflag=false;
//	                	weixinflag=true;
//	                	Item.buytype="微信支付";
//	                }  
//	            }  
//	        });  
	}
	public void doSubmit(View v) {
		submitProcess(Item);
	}
	private void submitProcess(DrtorderItem item) {
		DialogHelper.showLoadingDialog(this);
		OkHttpUtils
				.get()
		.addParams("orderid", getOutTradeNo())
		.addParams("lineid", item.lineid)
		.addParams("userid", item.userid)
		.addParams("startstop", item.startstop)
		.addParams("endstop", item.endstop)
		.addParams("upstop", item.upstop)
		.addParams("downstop", item.downstop)
		.addParams("distance", item.distance)
		.addParams("runtime", item.runtime)
		.addParams("paymoney", item.paymoney)
		.addParams("buytype", item.buytype)
		.addParams("city", item.city)

				.url(ApiInterface.Savedrtorder)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onResponse(String jsonstring) {
						DialogHelper.dismissLoadingDialog();
						if (jsonstring == null || jsonstring.equals("")) {
							Toast.makeText(getApplicationContext(), "订单提交失败",
									Toast.LENGTH_SHORT).show();
							return;
						}

						// analyze JSON string
						try {
							JSONObject jsonObject = new JSONObject(jsonstring);
							submitflag = jsonObject.getString("urlflag");
							if (submitflag.equals("success")) {

								PaySelectAcitivity.this.finish();
								Toast.makeText(getApplicationContext(), "订单提交成功",
										Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(getApplicationContext(), "订单提交失败",
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "订单提交失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						// TODO Auto-generated method stub
						ToastUtils.showLong(PaySelectAcitivity.this, "订单提交失败");
					}
				});
	}
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
}