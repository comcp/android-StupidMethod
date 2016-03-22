package com.stupid.method.app;

import static android.view.View.MeasureSpec.getMode;

import java.io.Serializable;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.stupid.method.R;
import com.stupid.method.adapter.OnClickItemListener;
import com.stupid.method.adapter.OnLongClickItemListener;
import com.stupid.method.app.XDialog.IXDialogViewHolder;
import com.stupid.method.util.XLog;

public abstract class XDialogViewHolder implements IXDialogViewHolder {

	private static final String tag = "XDialogViewHolder";
	protected View mRoot;
	protected XDialog mDialog;
	protected LayoutInflater inflater;
	protected Context mContext;
	protected Object mData;
	int PARAMS_WIDTH = RelativeLayout.LayoutParams.MATCH_PARENT;
	int PARAMS_HEIGHT = RelativeLayout.LayoutParams.WRAP_CONTENT;

	protected static class Builder {
		protected XDialog.Builder builder;

		public XDialog.Builder dismiss() {
			return builder.dismiss();
		}

		public XDialog getDialog() {
			return builder.getDialog();
		}

		public boolean isCancelable() {
			return builder.isCancelable();
		}

		public boolean isShowing() {
			return builder.isShowing();
		}

		public XDialog.Builder setCancelable(boolean cancelable) {
			return builder.setCancelable(cancelable);
		}

		public XDialog.Builder setData(Serializable mData) {
			return builder.setData(mData);
		}

		public XDialog.Builder setStyle(int style) {
			return builder.setStyle(style);
		}

		public XDialog.Builder setTheme(int theme) {
			return builder.setTheme(theme);
		}

		public XDialog.Builder show() {
			return builder.show();
		}

		public int hashCode() {

			return builder.hashCode();
		}

		public Builder(FragmentActivity activity,
				Class<? extends XDialogViewHolder> clz) {
			builder = new XDialog.Builder(activity, clz);
		}

	}

	@Override
	public boolean isCancelable() {

		return true;
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onPause() {
	};

	@Override
	public View getView() {

		if (mRoot == null) {

			RelativeLayout layout = new RelativeLayout(mContext) {
				@Override
				protected void onMeasure(int widthMeasureSpec,
						int heightMeasureSpec) {

					float mMajorWeight = 1.0f, mMinorWeight = 1.0f;
					final DisplayMetrics metrics = getContext().getResources()
							.getDisplayMetrics();
					final int screenWidth = metrics.widthPixels;
					final boolean isPortrait = screenWidth < metrics.heightPixels;

					final int widthMode = getMode(widthMeasureSpec);

					super.onMeasure(widthMeasureSpec, heightMeasureSpec);

					int width = getMeasuredWidth();
					int height = getMeasuredHeight();
					boolean measure = false;

					widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
							MeasureSpec.EXACTLY);
					heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
							MeasureSpec.UNSPECIFIED);

					final float widthWeight = isPortrait ? mMinorWeight
							: mMajorWeight;
					if (widthMode == MeasureSpec.AT_MOST && widthWeight > 0.0f) {
						if (width < (screenWidth * widthWeight)) {
							widthMeasureSpec = MeasureSpec.makeMeasureSpec(
									(int) (screenWidth * widthWeight),
									MeasureSpec.EXACTLY);
							measure = true;
						}
					}

					if (measure) {
						super.onMeasure(widthMeasureSpec, heightMeasureSpec);
					}
				}

			};

			layout.addView(inflater.inflate(getLayoutId(), null), PARAMS_WIDTH,
					PARAMS_HEIGHT);

			mRoot = layout;

		}
		return mRoot;
	}

	public void setLayoutParams(int width, int height) {
		this.PARAMS_HEIGHT = height;
		this.PARAMS_WIDTH = width;
	}

	@Override
	public int getAnimStyle() {

		return R.style.anim_down_in_up_out;
	}

	abstract protected void onResetView(Object data);

	@Override
	public final View getView(Object data, int p, boolean onScrolling) {
		mData = data;
		onResetView(data);

		return mRoot;
	}

	protected View findViewById(int id) {
		return getView().findViewById(id);
	}

	/**
	 * 没什么卵用
	 */
	@Deprecated
	@Override
	public void onDestory(int p, int l) {

		switch (p) {
		case 0:
			XLog.d(tag, "onDismiss");
			onDismiss();
			break;
		case 1:
			XLog.d(tag, "onDestory");

			onDestroyView();
			break;
		default:
			break;
		}
	}

	protected void onDismiss() {

	}

	protected void onDestroyView() {

	}

	@Override
	public View setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
		this.mContext = inflater.getContext();

		if (mRoot == null)
			mRoot = getView();// 如果返回不为null,则使用该view
		if (mRoot == null)
			mRoot = inflater.inflate(getLayoutId(), null);

		return mRoot;
	}

	@Deprecated
	@Override
	public void setOnClickItemListener(OnClickItemListener listener) {

	}

	@Deprecated
	@Override
	public void setOnLongClickItemListener(OnLongClickItemListener listener) {

	}

	@Override
	public final void setDialog(XDialog dialog) {
		mDialog = dialog;
	}

	@Override
	public boolean customDialog(Dialog dialog) {
		if (dialog == null)
			return false;
		else {
			Window window = dialog.getWindow();
			window.setBackgroundDrawableResource(android.R.color.transparent);
			window.setGravity(getGravity());
			window.setWindowAnimations(getAnimStyle());
			return true;
		}
	}

	/**
	 * @see Gravity
	 * **/
	protected int getGravity() {

		return Gravity.BOTTOM;
	}
}
