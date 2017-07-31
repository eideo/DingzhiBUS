package chenfei.com.dialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import chenfei.com.base.R;

public class CustomDialog extends Dialog {

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private View contentView;
		private OnClickListener destinationClickListener;
		private OnClickListener homeClickListener;
		private OnClickListener companyClickListener;
		private OnClickListener cancelClickListener;

		public Builder(Context context) {
			this.context = context;
		}
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param
		 * @return
		 */
		public Builder setdestinationButton(
				OnClickListener listener) {
			this.destinationClickListener = listener;
			return this;
		}

		public Builder sethomeButton(
				OnClickListener listener) {
			this.homeClickListener = listener;
			return this;
		}

		public Builder setcompanyButton(
				OnClickListener listener) {
			this.companyClickListener = listener;
			return this;
		}

		public Builder setcancelButton(
				OnClickListener listener) {
			this.cancelClickListener = listener;
			return this;
		}

		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			// set the confirm button

				if (destinationClickListener != null) {
					((ImageView) layout.findViewById(R.id.Setdestination))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									destinationClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});				
		    	} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.Setdestination).setVisibility(
						View.GONE);
			}
				if (homeClickListener != null) {
					((ImageView) layout.findViewById(R.id.sethome))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									homeClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});				
		    	} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.sethome).setVisibility(
						View.GONE);
			}
				if (companyClickListener != null) {
					((ImageView) layout.findViewById(R.id.setcompany))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									companyClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});				
		    	} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.setcompany).setVisibility(
						View.GONE);
			}
				if (cancelClickListener != null) {
					((ImageView) layout.findViewById(R.id.cancel))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									cancelClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});				
		    	} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.cancel).setVisibility(
						View.GONE);
			}
			// set the cancel button
			// set the content message
			dialog.setContentView(layout);
			return dialog;
		}

	}
}
