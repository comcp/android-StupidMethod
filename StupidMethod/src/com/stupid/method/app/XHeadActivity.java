package com.stupid.method.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.stupid.method.R;

public abstract class XHeadActivity extends XActivity {
	private XHeadViewHolder headViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		super.setContentView(getLayoutRes());
		setHeadViewHolder(new BaseHeadBar(), true);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

	}

	protected int getLayoutRes() {
		return R.layout.x_head_activity;
	}

	protected void setHeadViewVisibility(int visibility) {

		findViewById(R.id.xHead).setVisibility(visibility);
	}

	protected int getHeadViewVisibility() {

		return findViewById(R.id.xHead).getVisibility();
	}

	protected static class BaseHeadBar extends XHeadViewHolder<BaseHeadBar> {

		@Override
		public int getLayoutId() {

			return R.layout.base_head;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackEnabled(boolean enabled) {
			findViewById(R.id.baseHeadLeft).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackImg(int resId) {
			findViewById(R.id.baseHead).setBackgroundResource(resId);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackText(int resId) {

			((TextView) findViewById(R.id.baseHeadLeftTv)).setText(resId);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackText(String txt) {
			((TextView) findViewById(R.id.baseHeadLeftTv)).setText(txt);

			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleEnabled(boolean enabled) {
			findViewById(R.id.baseHeadCenter).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleImg(int resId) {
			((TextView) findViewById(R.id.baseHeadCenterTv))
					.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleText(int resId) {
			((TextView) findViewById(R.id.baseHeadCenterTv)).setText(resId);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleText(String txt) {
			((TextView) findViewById(R.id.baseHeadCenterTv)).setText(txt);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuEnabled(boolean enabled) {
			findViewById(R.id.baseHeadRight).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuImg(int resId) {
			((TextView) findViewById(R.id.baseHeadRightTv))
					.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuText(int resId) {
			((TextView) findViewById(R.id.baseHeadRightTv)).setText(resId);

			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuText(String txt) {
			((TextView) findViewById(R.id.baseHeadRightTv)).setText(txt);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackVisibility(int visibility) {
			findViewById(R.id.baseHeadLeft).setVisibility(visibility);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleVisibility(int visibility) {
			findViewById(R.id.baseHeadCenter).setVisibility(visibility);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuVisibility(int visibility) {
			findViewById(R.id.baseHeadRight).setVisibility(visibility);
			return this;
		}

		@Override
		public void onCreate(Context contex) {

		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackOnClickListener(
				OnClickListener click) {

			findViewById(R.id.baseHeadLeft).setOnClickListener(click);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setTitleOnClickListener(
				OnClickListener click) {
			findViewById(R.id.baseHeadCenter).setOnClickListener(click);
			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setMenuOnClickListener(
				OnClickListener click) {
			findViewById(R.id.baseHeadRight).setOnClickListener(click);

			return this;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackgroundImge(int resId) {
			getView().setBackgroundResource(resId);
			return null;
		}

		@Override
		public XHeadViewHolder<BaseHeadBar> setBackgroundColor(int color) {
			getView().setBackgroundColor(color);
			return this;
		}

	}

	@SuppressLint("NewApi")
	protected void setHeadViewHolder(XHeadViewHolder headViewHolder,
			boolean needTopPadding) {

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.xHead);
		viewGroup.setVisibility(View.VISIBLE);
		if (viewGroup != null) {
			XHeadViewHolder old = this.headViewHolder;

			if (viewGroup.getChildCount() > 0) {
				viewGroup.removeAllViews();
			}
			if (old != null)
				old.onDestory(0, 0);
			headViewHolder.setInflater(getLayoutInflater());
			headViewHolder.onCreate(this);

			viewGroup.addView(headViewHolder.getView());
			this.headViewHolder = headViewHolder;
			try {

				if (Build.VERSION.SDK_INT >= 19 && needTopPadding) {
					int statusb = getStatusBarHeight();
					// check theme attrs
					int[] _attrs = { android.R.attr.windowTranslucentStatus,
							android.R.attr.fitsSystemWindows };
					boolean _mStatusBarAvailable = false, _mfitsSyste = false;
					TypedArray a = obtainStyledAttributes(_attrs);

					try {
						_mStatusBarAvailable = a.getBoolean(0, false);
						_mfitsSyste = a.getBoolean(0, false);

					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						a.recycle();
					}

					if (_mStatusBarAvailable && _mfitsSyste)
						if (statusb > 0) {
							View _head = headViewHolder.getView();
							if (_head.getLayoutParams() instanceof ViewGroup.LayoutParams) {
								// ViewGroup.LayoutParams _rp =
								// (ViewGroup.LayoutParams) _head
								// .getLayoutParams();
								viewGroup.getLayoutParams().height += statusb;
								// _rp.height += statusb;
								_head.setPadding(_head.getPaddingLeft(),
										statusb, _head.getPaddingRight(),
										_head.getPaddingBottom());
							}
						}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setContentView(int layoutResID) {
		View view = View.inflate(this, layoutResID, null);
		setContentView(view);

	}

	@Override
	public void setContentView(View view) {

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.xContent);
		viewGroup.setVisibility(View.VISIBLE);
		if (viewGroup.getChildCount() > 0) {
			viewGroup.removeAllViews();
		}
		viewGroup.addView(view);
	}

	public void setSupContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

	public void setSupContentView(View view) {
		super.setContentView(view);
	}

	@Override
	public int getLayoutId() {

		return 0;
	}

	protected XHeadViewHolder getHeadViewHolder() {
		return headViewHolder;
	}

}
