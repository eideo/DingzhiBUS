package chenfei.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import chenfei.com.activity.PaySelectAcitivity;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.R;
import chenfei.com.category.DrtorderItem;
import chenfei.com.category.GetLinesInfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.SPUtils;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-24 上午10:56:16
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Myorderlistadapter extends BaseQuickAdapter<DrtorderItem> {

	private Context mcontext;
	public Myorderlistadapter(Context context, int layoutResId, List<DrtorderItem> data) {
		super(layoutResId, data);
		this.mcontext=context;

	}

	public Myorderlistadapter(Context context, int layoutResId, List<DrtorderItem> data, int dataSize) {
		super(layoutResId, getData(data, dataSize));
		this.mcontext=context;
	}

	public static List<DrtorderItem> getData(List<DrtorderItem> data, int dataSize) {
		List<DrtorderItem> newlist = new ArrayList<DrtorderItem>();
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
	protected void convert(final BaseViewHolder helper, final DrtorderItem item) {

		helper.setText(R.id.upstop,item.getUpstop());
		helper.setText(R.id.downstop,item.getDownstop());
		helper.setText(R.id.buslineid,item.getLineid()+"路");
		helper.setText(R.id.totolmoney,"￥"+item.getPaymoney()+"元");
		helper.setText(R.id.buytime,item.getBuytime());
		helper.getView(R.id.buyticket).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent();
				intent.setClass(mcontext, PaySelectAcitivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				Bundle mBundle = new Bundle();
				mBundle.putString("lineid", item.lineid);
				mBundle.putString("startstop", item.startstop);
				mBundle.putString("endstop", item.endstop);
				mBundle.putString("upstop", item.upstop);
				mBundle.putString("downstop", item.downstop);
				mBundle.putString("distance", item.distance);
				mBundle.putString("runtime", item.runtime);
				mBundle.putString("paymoney", item.paymoney);
				mBundle.putString("city", item.city);
				intent.putExtras(mBundle);
				mcontext.startActivity(intent);
			}
		});
		//helper.setText(R.id.tv_passpoint, lineItem.getTujingdi());

	}
}
