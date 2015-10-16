package com.stupid.method.app;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.stupid.method.util.http.IXHttp;

abstract public class XDialogFragment extends DialogFragment implements
		IXFragment {

	private onDialogCallback dialogCallback;
	private View mRootView;
	private Timer timer = new Timer();
	protected Object data;
	XTimerTask task = new XTimerTask() {

		@Override
		public void run() {
			if (isShowing()) {
				if (null != dialogCallback)
					dialogCallback.onTimeout();
				dismiss();

			}
		}
	};

	@Override
	public void onServerResult(int resultCode, String data, boolean state,
			int statusCode) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (null != mRootView) {
			ViewGroup parent = (ViewGroup) mRootView.getParent();
			if (null != parent) {
				parent.removeView(mRootView);
				parent = null;
			}
		} else {

			mRootView = inflater.inflate(getLayoutId(), container);

		}
		initPager(savedInstanceState, data);
		return mRootView;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (null != dialogCallback)
			dialogCallback.onDismiss();
		if (timer != null) {

			task.reset();
			timer.purge();
		}
	}

	public void show(XActivity xActivity, boolean cancel, long timeout) {
		setCancelable(cancel);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		show(xActivity.getSupportFragmentManager(), this.getClass().getName());
		if (timeout > 0) {
			timer.schedule(task.getTask(), timeout);
		}

	}

	public boolean isShowing() {
		return getDialog() == null ? false : getDialog().isShowing();
	}

	@Override
	public void showToast(String text, int duration) {
	}

	@Override
	public void showToast(int text, int duration) {
	}

	@Override
	public void showToast(String text) {
	}

	@Override
	public void showToast(int text) {
	}

	@Override
	public XActivity getContent() {

		return null;
	}

	@Override
	public AQuery getAQuery() {

		return null;
	}

	@Override
	public void waitEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setData(Object object) {
		data = object;
	}

	@Override
	final public View findViewById(int id) {

		return mRootView.findViewById(id);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public XDialogFragment waitof(String msg, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XDialogFragment waitof(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XDialogFragment waitof() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	abstract public void initPager(Bundle savedInstanceState, Object data);

	public interface onDialogCallback extends OnClickListener {
		void onDismiss();

		void onTimeout();

	}

	public onDialogCallback getDialogCallback() {
		return dialogCallback;
	}

	public void setDialogCallback(onDialogCallback dialogCallback) {
		this.dialogCallback = dialogCallback;
	}

	@Override
	public XDialogFragment waitof(String msg, boolean cancel, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	abstract public static class XTimerTask implements Runnable {
		TimerTask task;

		public TimerTask getTask() {
			if (task == null)
				task = new TimerTask() {

					@Override
					public void run() {
						XTimerTask.this.run();

					}
				};
			return task;
		}

		public boolean reset() {

			if (task != null) {
				task.cancel();
				task = null;
			}

			return false;
		}
	}

}
