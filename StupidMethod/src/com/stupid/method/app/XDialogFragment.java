package com.stupid.method.app;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.stupid.method.R;

abstract public class XDialogFragment extends DialogFragment implements
		IXFragment {

	private onDialogCallback dialogCallback;
	private View mRootView;
	private Timer timer = new Timer();
	protected Serializable data;
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
		if (null != getRootView()) {
			ViewGroup parent = (ViewGroup) getRootView().getParent();
			if (null != parent) {
				parent.removeView(getRootView());
				parent = null;
			}
		} else {

			setRootView(inflater.inflate(getLayoutId(), container));

		}
		initPager(savedInstanceState, data);
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		window.setWindowAnimations(getAnimStyle());
		return getRootView();
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
	public void waitEnd() {

	}

	@Override
	public void setData(Serializable object) {
		data = object;
	}

	@Override
	final public View findViewById(int id) {

		return getRootView().findViewById(id);
	}

	@Override
	public XDialogFragment waitof(String msg, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	abstract public void initPager(Bundle savedInstanceState, Object data);

	public interface onDialogListener {
		boolean onDialogEvent(int type, XDialogFragment dialog);
	}

	public interface onDialogCallback extends OnClickListener {
		void onDismiss();

		void onTimeout();

	}

	/** 设置动画 */
	protected int getAnimStyle() {
		return R.style.anim_down_in_up_out;
	}

	public onDialogCallback getDialogCallback() {
		return dialogCallback;
	}

	public void setDialogCallback(onDialogCallback dialogCallback) {
		this.dialogCallback = dialogCallback;
	}

	@Override
	public XDialogFragment waitof(String msg, boolean cancel, long timeout) {
		return null;
	}

	public View getRootView() {
		return mRootView;
	}

	public void setRootView(View mRootView) {
		this.mRootView = mRootView;
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
