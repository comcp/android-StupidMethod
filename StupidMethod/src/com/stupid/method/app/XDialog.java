package com.stupid.method.app;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stupid.method.adapter.IXViewHolder;
import com.stupid.method.util.XLog;

public class XDialog extends DialogFragment implements IXDialog {

	public static class Builder {
		private final XDialogController mController;

		protected XDialog mDialog = null;

		protected int theme, style;

		public Builder(FragmentActivity activity,
				Class<? extends IXDialogViewHolder> clz) {
			this(activity, clz, STYLE_NO_FRAME, android.R.style.Theme_Dialog,
					null);
		}

		public Builder setOnDismissListener(OnDismissListener listener) {
			mController.listener = listener;
			return this;
		}

		public Builder(FragmentActivity activity,
				Class<? extends IXDialogViewHolder> clz, int style, int theme,
				OnDismissListener listener) {
			mController = new XDialogController();
			mController.mClz = clz;
			mController.mFm = activity.getSupportFragmentManager();
			this.style = style;
			this.theme = theme;
		}

		public Builder dismiss() {
			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
			}
			return this;
		}

		public XDialog getDialog() {
			return mDialog;
		}

		public boolean isCancelable() {
			return mDialog.isCancelable();
		}

		public boolean isShowing() {
			return mDialog.isShowing();
		}

		public Builder setCancelable(boolean cancelable) {
			if (mDialog != null) {
				mDialog.setCancelable(cancelable);
			}
			return this;
		}

		public Builder setData(Serializable mData) {
			this.mController.mData = mData;
			return this;
		}

		/**
		 * 调用show 前调用
		 * **/
		public Builder setStyle(int style) {
			this.style = style;
			return this;
		}

		/**
		 * 调用show 前调用
		 * **/
		public Builder setTheme(int theme) {
			this.theme = theme;
			return this;
		}

		public XDialog show() {
			if (mDialog == null) {
				mDialog = new XDialog(style, theme);
				mDialog.mController = mController;
			}

			return mDialog.show();
		}

	}

	public interface IXDialogViewHolder extends IXViewHolder<Object> {

		/**
		 * 返回true 则代表用户已经按照自己的意图去装饰dialog,
		 * 
		 * **/
		boolean customDialog(Dialog dialog);

		int getAnimStyle();

		boolean isCancelable();

		void setDialog(XDialog dialog);

		void onResume();

		void onPause();

	}

	private static class XDialogController {

		public FragmentManager mFm = null;
		public FragmentTransaction mFt = null;
		public Serializable mData = null;
		public Class<? extends IXDialogViewHolder> mClz;
		public WeakReference<IXDialogViewHolder> mReferenceHolder;
		OnDismissListener listener;
	}

	XDialogController mController;
	private static final String tag = "XDialog";
	private static final String BUNDL_DATA = "BUNDL_DATA";
	private static final String BUNDL_CLASS = "BUNDL_CLASS";

	public XDialog(int style, int theme) {
		setStyle(style, theme);
	}

	public boolean isShowing() {
		return getDialog() == null ? false : getDialog().isShowing();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		IXDialogViewHolder _holder = null;

		if (savedInstanceState != null && mController == null) {
			if (savedInstanceState.getSerializable(tag) instanceof XDialogController)

				mController = new XDialogController();
			if (mController == null)
				try {
					mController.mClz = (Class<? extends IXDialogViewHolder>) Class
							.forName(savedInstanceState.getString(BUNDL_CLASS));
					mController.mData = savedInstanceState
							.getSerializable(BUNDL_DATA);

				} catch (Exception e) {
					TextView error = new TextView(inflater.getContext());
					error.setTextColor(0XFFF54545);
					error.setText("发生了无法解决的异常,XDialogController 数据无法进行恢复");
					error.append(e.getMessage());
					return error;
				}

		}
		if (mController.mReferenceHolder == null) {
			try {

				_holder = (IXDialogViewHolder) mController.mClz.newInstance();
				_holder.setInflater(inflater);
				_holder.setDialog(this);
				_holder.onCreate(getActivity());
				_holder.setOnClickItemListener(null);
				_holder.setOnLongClickItemListener(null);
				mController.mReferenceHolder = new WeakReference<XDialog.IXDialogViewHolder>(
						_holder);

			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		} else {
			_holder = mController.mReferenceHolder.get();
			ViewGroup parent = (ViewGroup) _holder.getView().getParent();
			if (null != parent) {
				parent.removeView(_holder.getView());
				parent = null;
			}
		}
		_holder.getView(mController.mData, 0, false);
		setCancelable(_holder.isCancelable());
		_holder.customDialog(getDialog());
		return _holder.getView();
	}

	@Override
	public void onDestroyView() {
		if (mController.mReferenceHolder != null) {
			IXDialogViewHolder _holder = mController.mReferenceHolder.get();
			_holder.onDestory(1, 1);
			_holder = null;
			mController.mReferenceHolder = null;
		}
		super.onDestroyView();

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (mController.mReferenceHolder != null) {

			IXDialogViewHolder holder = mController.mReferenceHolder.get();
			if (holder != null) {
				holder.onDestory(0, 0);
			}
		}
		if (mController.listener != null) {
			mController.listener.onDismiss(dialog);
		}
		super.onDismiss(dialog);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(BUNDL_CLASS, mController.mClz.getName());
		outState.putSerializable(BUNDL_DATA, mController.mData);
		super.onSaveInstanceState(outState);
	}

	public void setData(Serializable object) {
		mController.mData = object;
	}

	@Override
	public void onResume() {
		if (mController.mReferenceHolder != null) {
			IXDialogViewHolder _holder = mController.mReferenceHolder.get();
			_holder.onResume();

		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (mController.mReferenceHolder != null) {
			IXDialogViewHolder _holder = mController.mReferenceHolder.get();
			_holder.onPause();
		}
		super.onPause();

	}

	public XDialog show() {
		if (mController == null)
			return null;
		if (mController.mFm != null)
			show(mController.mFm, mController.toString());
		else if (mController.mFt != null)
			show(mController.mFt, mController.toString());
		else
			XLog.e(tag, "无法显示该dialog");
		return this;
	}
}
