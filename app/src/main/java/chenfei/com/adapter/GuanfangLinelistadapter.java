package chenfei.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
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

import chenfei.com.activity.MainActivity;
import chenfei.com.activity.PaySelectAcitivity;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.MyAPP;
import chenfei.com.base.R;
import chenfei.com.category.GetLinesInfo;
import chenfei.com.category.LineItem;
import chenfei.com.category.Userinfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.Md5Utils;
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
public class GuanfangLinelistadapter extends BaseQuickAdapter<GetLinesInfo.DataBean> {

	private Context mcontext;
	public GuanfangLinelistadapter(Context context, int layoutResId, List<GetLinesInfo.DataBean> data) {
		super(layoutResId, data);
		this.mcontext=context;

	}

	public GuanfangLinelistadapter(Context context, int layoutResId, List<GetLinesInfo.DataBean> data, int dataSize) {
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
	protected void convert(final BaseViewHolder helper, final GetLinesInfo.DataBean lineItem) {

		helper.setText(R.id.tv_start_station, lineItem.getStartstop());
		helper.setText(R.id.tv_end_station, lineItem.getEndstop());
		//helper.setText(R.id.tv_passpoint, lineItem.getTujingdi());
		helper.setText(R.id.tv_start_time, lineItem.getStarttime());
		helper.setText(R.id.tv_end_time, lineItem.getEndtime());
		helper.setText(R.id.tv_price, lineItem.getRegisterpersonnum());
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
		LikeButton mlikeButton=helper.getView(R.id.yuyuelikebtn);
		final TextView textView=helper.getView(R.id.tv_price);
		final String telephonenumber= (String) SPUtils.get(mContext,"telephone","-1");
		if (lineItem.getRegisterusertelephone().contains(telephonenumber+",")){
			mlikeButton.setLiked(true);
		}
		else {
			mlikeButton.setLiked(false);
		}
		mlikeButton.setOnLikeListener(new OnLikeListener() {
			@Override
			public void liked(LikeButton likeButton) {

				String registerpersonnum=textView.getText().toString();
				String registerpersonnumnow=Integer.valueOf(registerpersonnum)+1+"";
				if (lineItem.getRegisterusertelephone().contains(telephonenumber+",")) {
					Doyuyue(lineItem.getLineid(), registerpersonnumnow, lineItem.getRegisterusertelephone(), textView, true);
				}
				else {
					Doyuyue(lineItem.getLineid(), registerpersonnumnow, lineItem.getRegisterusertelephone() + telephonenumber + ",", textView, true);
				}
			}

			@Override
			public void unLiked(LikeButton likeButton) {
				String registerpersonnum=textView.getText().toString();
				String registerpersonnumnow=Integer.valueOf(registerpersonnum)-1+"";
				if(lineItem.getRegisterusertelephone().contains(telephonenumber+",")) {
					Doyuyue(lineItem.getLineid(), registerpersonnumnow, lineItem.getRegisterusertelephone().replace(telephonenumber+",",""), textView, false);
				}
				else {
					Doyuyue(lineItem.getLineid(), registerpersonnumnow, lineItem.getRegisterusertelephone(), textView, false);
				}
			}

		});


	}

	private void Doyuyue(String lineid, final String registerpersonnumnow,
						 String registerusertelephone, final TextView tv, final boolean flag){
		OkHttpUtils
				.get(ApiInterface.Doyuyue)
				.params("lineid", lineid)
				.params("registerpersonnum", registerpersonnumnow)
				.params("registerusertelephone", registerusertelephone)
				.execute(new StringCallback() {
					@Override
					public void onResponse(boolean isFromCache, String jsonstring, Request request, @Nullable Response response) {
						DialogHelper.dismissLoadingDialog();
						if (jsonstring == null || jsonstring.equals("")) {
							if (flag){
								ToastUtils.showShort(mcontext, "预约失败");
							}
							else {
								ToastUtils.showShort(mcontext, "取消预约失败");
							}

							return;
						}
						// analyze JSON string
						try {JSONObject jsonObject = new JSONObject(jsonstring);
							String urlflag = jsonObject.getString("urlflag");
							if (urlflag.equals("success")) {
								if (flag) {
									ToastUtils.showShort(mContext, "预约成功");
									tv.setText(registerpersonnumnow);
								}
								else {
									ToastUtils.showShort(mContext, "取消预约成功");
									tv.setText(registerpersonnumnow);
								}
							} else {
								ToastUtils.showShort(mContext, "服务器连接失败，请重新尝试");
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}

					}

					@Override
					public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
						super.onError(isFromCache, call, response, e);
						// TODO Auto-generated method stub
						ToastUtils.showLong(mContext, "连接失败，请重试");
					}
				});


	}
}
