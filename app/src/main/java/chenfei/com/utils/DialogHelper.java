package chenfei.com.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.DoubleBounce;

import chenfei.com.base.R;

public class DialogHelper {
	public static Dialog mLoadingDialog;

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @return Dialog
	 */
	public static void showLoadingDialog(Context context) {
		mLoadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		View view= LayoutInflater.from(context).inflate(R.layout.dialogview,null);
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mprogress);
		DoubleBounce doubleBounce = new DoubleBounce();
		doubleBounce.setBounds(0, 0, 100, 100);
		doubleBounce.setColor(context.getResources().getColor(R.color.colorPrimary));
		progressBar.setIndeterminateDrawable(doubleBounce);
		mLoadingDialog.setContentView(view);// 设置布局
		mLoadingDialog.setCancelable(true);// 不可以用“返回键”取消
		mLoadingDialog.setCanceledOnTouchOutside(false);// 点击屏幕其它地方不消失
		try{
			mLoadingDialog.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void showLoadingDialog(Context context,String title) {
		mLoadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		View view= LayoutInflater.from(context).inflate(R.layout.dialogview,null);
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mprogress);
		TextView titletv = (TextView) view.findViewById(R.id.detail_tv);
		titletv.setText(title);
		DoubleBounce doubleBounce = new DoubleBounce();
		doubleBounce.setBounds(0, 0, 100, 100);
		doubleBounce.setColor(context.getResources().getColor(R.color.colorPrimary));
		progressBar.setIndeterminateDrawable(doubleBounce);
		mLoadingDialog.setContentView(view);// 设置布局
		mLoadingDialog.setCancelable(true);// 不可以用“返回键”取消
		mLoadingDialog.setCanceledOnTouchOutside(false);// 点击屏幕其它地方不消失
		try{
			mLoadingDialog.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void dismissLoadingDialog() {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}
	
	public static boolean Isshow(){
		
		if (mLoadingDialog != null && mLoadingDialog.isShowing()){
			
			return true;
		}
		else{
			
			return false;
		}
	}
}
