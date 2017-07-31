package chenfei.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import chenfei.com.activity.PaySelectAcitivity;
import chenfei.com.base.R;
import chenfei.com.category.GetLinesInfo;
import chenfei.com.category.MyOrderInfo;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-24 上午10:56:16
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Myfinishedorderlistadapter extends BaseQuickAdapter<MyOrderInfo.DataBean> {

	private Context mcontext;
	public Myfinishedorderlistadapter(Context context, int layoutResId, List<MyOrderInfo.DataBean> data) {
		super(layoutResId, data);
		this.mcontext=context;

	}

	public Myfinishedorderlistadapter(Context context, int layoutResId, List<MyOrderInfo.DataBean> data, int dataSize) {
		super(layoutResId, getData(data, dataSize));
		this.mcontext=context;
	}

	public static List<MyOrderInfo.DataBean> getData(List<MyOrderInfo.DataBean> data, int dataSize) {
		List<MyOrderInfo.DataBean> newlist = new ArrayList<MyOrderInfo.DataBean>();
		if (data.size() < dataSize) {
			newlist = data;
		} else {
			for (int i = 0; i < dataSize; i++) {
				newlist.add(data.get(i));
			}
		}
		return newlist;
	}

	@Override
	protected void convert(BaseViewHolder helper, final MyOrderInfo.DataBean myorderitem) {

		helper.setText(R.id.tv_start_station, myorderitem.getStartstop());
		helper.setText(R.id.tv_end_station, myorderitem.getEndstop());
		//helper.setText(R.id.tv_passpoint, lineItem.getTujingdi());
		helper.setText(R.id.tv_start_time, myorderitem.getChengchedate()+myorderitem.getStarttime());
		helper.setText(R.id.tv_price, myorderitem.getPaymoney());
		helper.getView(R.id.gengduo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(mcontext, PaySelectAcitivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				Bundle mBundle = new Bundle();
				mBundle.putString("lineid", myorderitem.getLineid());
				mBundle.putString("startstop", myorderitem.getStartstop());
				mBundle.putString("endstop", myorderitem.getEndstop());
				mBundle.putString("distance", myorderitem.getDistance());
				mBundle.putString("starttime", myorderitem.getStarttime());
				mBundle.putString("endtime", myorderitem.getEndtime());
				mBundle.putString("runtime", myorderitem.getRuntime());
				mBundle.putString("paymoney", myorderitem.getPaymoney());
				mBundle.putString("status", myorderitem.getBuystatus());
				mBundle.putString("city", myorderitem.getCity());
				intent.putExtras(mBundle);
				mcontext.startActivity(intent);
			}
		});
	}
}
