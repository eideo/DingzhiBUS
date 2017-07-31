package chenfei.com.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import chenfei.com.base.MyAPP;
import chenfei.com.base.R;

/** 
 * @author  作者 E-mail: 
 *@date 创建时间：2015-7-25 下午10:55:31 
 * @version 1.0 
 * @parameter  
 * @since
 * @return  
 */
public class YCUtils {
	private YCUtils() {
	};
	private static final int DATE_DIALOG = 0;
	public static Handler handler;
	public static ProgressDialog mypDialog;
	private static Dialog mDialog;
	public static void showToast(final String info) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (mypDialog != null) {
					mypDialog.dismiss();
				}
				try {
					Toast.makeText(MyAPP.ycapp, info, 2000).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public static void showToast(final int id) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (mypDialog != null) {
					mypDialog.dismiss();
				}
				try {
					Toast.makeText(
							MyAPP.ycapp,
							MyAPP.ycapp.getResources().getString(id),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public static void showToast(  final String info,final int time) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (mypDialog != null) {
					mypDialog.dismiss();
				}
				try {
					Toast.makeText(MyAPP.ycapp, info, time).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	  // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

	public static void showDialog(Context ac, String info, String title) {
		try {
			new AlertDialog.Builder(ac)
			         .setIcon(R.drawable.logo)
					 .setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).setMessage(info).setTitle(title).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void showTimeDialog(final Activity ac, int type,
			final TextView tv) {
		Calendar calendar = Calendar.getInstance();
		if (type == DATE_DIALOG) {
			DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year,
						int month, int dayOfMonth) {
					tv.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
				}
			};
			DatePickerDialog dlg = new DatePickerDialog(ac, dateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dlg.show();
		} else {
			TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker timePicker, int hourOfDay,
						int minute) {
					tv.setText(hourOfDay + ":" + minute + ":00");
				}
			};
			new TimePickerDialog(ac, timeListener,
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE), true).show();
		}

	}
	public static void dismissProcessDialog() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mypDialog != null) {
					mypDialog.dismiss();
					mypDialog = null;
				}
			}
		});

	}

	public static ProgressDialog showProcessDialog(Context ac, String message) {
		mypDialog = new ProgressDialog(ac);
		mypDialog.setIcon(R.drawable.logo);
		mypDialog.setTitle("重庆亿程信息科技有限公司");
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setMessage(message);
		mypDialog.setCancelable(false);
		mypDialog.setIndeterminate(false);
		mypDialog.setCancelable(false);
		mypDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				handler.post(new Runnable() {

					@Override
					public void run() {
						mypDialog.dismiss();
						// mypDialog = null;

					}
				});

				return false;
			}
		});
		try {
			handler.post(new Runnable() {

				@Override
				public void run() {
					mypDialog.show();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mypDialog;
	}
//	public static void showloaddingProcessDialog(Context ac) {
//		shapeLoadingDialog=new ShapeLoadingDialog(ac);
//        shapeLoadingDialog.setLoadingText("加载中...");
//        shapeLoadingDialog.show();		
//	}
//	public static void dismissloaddingProcessDialog() {
//		 shapeLoadingDialog.dismiss();
//	}

	public static ProgressDialog showProcessDialog(Context ac, int id) {
		if (mypDialog != null) {
			try {
				mypDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mypDialog.setMessage(ac.getResources().getString(id));

			return mypDialog;
		}
		mypDialog = new ProgressDialog(ac);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setMessage(ac.getResources().getString(id));
		mypDialog.setCancelable(false);
		mypDialog.setIndeterminate(false);
		mypDialog.setCancelable(false);
		mypDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				mypDialog.dismiss();
				mypDialog = null;
				return false;
			}
		});
		try {
			mypDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mypDialog;
	}
	public static void showRoundProcessDialog(Context mContext, int layout)
    {
        OnKeyListener keyListener = new OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH)
                {
                    return true;
                }
                return false;
            }
        };

        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.setOnKeyListener(keyListener);
        mDialog.show();
        // 注意此处要放在show之后 否则会报异常
        mDialog.setContentView(layout);
    }
	public static void dismissRoundProcessDialog() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mDialog != null) {
					mDialog.dismiss();
					mDialog = null;
				}
			}
		});

	}
}
