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
import chenfei.com.category.LineItem;
/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-24 上午10:56:16
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class RunningLinelistadapter extends BaseQuickAdapter<GetLinesInfo.DataBean> {

	private Context mcontext;
	public RunningLinelistadapter(Context context,int layoutResId, List<GetLinesInfo.DataBean> data) {
		super(layoutResId, data);
		this.mcontext=context;

	}

	public RunningLinelistadapter(Context context,int layoutResId, List<GetLinesInfo.DataBean> data, int dataSize) {
		super(layoutResId, getData(data, dataSize));
		this.mcontext=context;
	}

	public static List<GetLinesInfo.DataBean> getData(List<GetLinesInfo.DataBean> data, int dataSize) {
		List<GetLinesInfo.DataBean> newlist = new ArrayList<GetLinesInfo.DataBean>();
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
	protected void convert(BaseViewHolder helper, final GetLinesInfo.DataBean lineItem) {

		helper.setText(R.id.tv_start_station, lineItem.getStartstop());
		helper.setText(R.id.tv_end_station, lineItem.getEndstop());
		//helper.setText(R.id.tv_passpoint, lineItem.getTujingdi());
		helper.setText(R.id.tv_start_time, lineItem.getStarttime());
		helper.setText(R.id.tv_end_time, lineItem.getEndtime());
		helper.setText(R.id.tv_price, lineItem.getPaymoney());
		helper.setText(R.id.tv_access_point, "全长" + lineItem.getDistance() + "km,预计运行"
				+ lineItem.getRuntime() + "分钟");
		String tujingdiString = "";
		if (lineItem.getTujingdi() != null && !lineItem.getTujingdi().equals("")) {
			String[] tujingdi = lineItem.getTujingdi().split(",");
			for (int i = 0; i < tujingdi.length; i++) {
				tujingdiString = tujingdiString + tujingdi[i].toString() + "→";
			}
		}
		helper.setText(R.id.tv_passpoint, lineItem.getStartstop() + "→" + tujingdiString
				+ lineItem.getEndstop());
		helper.getView(R.id.buyticket).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(mcontext, PaySelectAcitivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				Bundle mBundle = new Bundle();
				mBundle.putString("lineid", lineItem.getLineid());
				mBundle.putString("startstop", lineItem.getStartstop());
				mBundle.putString("endstop", lineItem.getEndstop());
				mBundle.putString("distance", lineItem.getDistance());
				mBundle.putString("starttime", lineItem.getStarttime());
				mBundle.putString("endtime", lineItem.getEndtime());
				mBundle.putString("runtime", lineItem.getRuntime());
				mBundle.putString("paymoney", lineItem.getPaymoney());
				mBundle.putString("type", lineItem.getType());
				mBundle.putString("city", lineItem.getCity());
				intent.putExtras(mBundle);
				mcontext.startActivity(intent);
			}
		});
	}
}
