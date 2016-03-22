package com.stupid.method.app.impl;

import java.io.Serializable;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.stupid.method.R;
import com.stupid.method.annotation.XClick;
import com.stupid.method.annotation.XViewById;
import com.stupid.method.app.XDialogViewHolder;
import com.stupid.method.util.AutoViewInit;

public class AlertDialog extends XDialogViewHolder implements OnClickListener {

	@XViewById
	TextView title, message;
	@XViewById
	@XClick
	Button button2, button1;

	public interface AlertDialogListener {

		void onAlertEvent(int type, AlertDialog dialog);
	}

	public static class AlertModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3342748179372200179L;

		public String title, message, button2, button1;

		public boolean bton1Show = true, bton2Show = true;

		public String getTitle() {
			return title;
		}

		public AlertModel setTitle(String title) {
			this.title = title;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public AlertModel setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getButton2() {
			return button2;
		}

		public AlertModel setButton2(String button2) {
			this.button2 = button2;
			return this;
		}

		public String getButton1() {
			return button1;
		}

		public AlertModel setButton1(String button1) {
			this.button1 = button1;
			return this;
		}

		public AlertDialogListener getCallback() {
			return callback;
		}

		public AlertModel setCallback(AlertDialogListener callback) {
			this.callback = callback;
			return this;
		}

		public AlertDialogListener callback;
	}

	public static class Builder extends XDialogViewHolder.Builder {

		public Builder(FragmentActivity activity) {
			super(activity, AlertDialog.class);
		}

		public Builder(FragmentActivity activity, AlertModel model) {
			super(activity, AlertDialog.class);
			setData(model);
		}

		public Builder show(AlertModel model) {
			setData(model);
			super.show();
			return this;
		}

	}

	@Override
	public int getLayoutId() {

		return R.layout.alert_dialog;
	}

	@Override
	public void onCreate(Context contex) {
		AutoViewInit.initObjectView(this, getView(), this);
	}

	@Override
	protected void onResetView(Object data) {
		if (data instanceof AlertModel) {
			title.setText(((AlertModel) data).title);
			message.setText(((AlertModel) data).message);
			button1.setText(((AlertModel) data).button1);
			button2.setText(((AlertModel) data).button2);
			button1.setVisibility(((AlertModel) data).bton1Show ? View.VISIBLE
					: View.GONE);
			button2.setVisibility(((AlertModel) data).bton2Show ? View.VISIBLE
					: View.GONE);

		}

	}

	@Override
	public void onClick(View v) {

		if (mData instanceof AlertModel) {

			if (((AlertModel) mData).callback != null)
				((AlertModel) mData).callback.onAlertEvent(v.getId(), this);

		}
	}
}
