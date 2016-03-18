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
		super.setContentView(R.layout.x_head_activity);
		setHeadViewHolder(new BaseHeadBar(), true);

	}

	protected class BaseHeadBar extends XHeadViewHolder {

		@Override
		public int getLayoutId() {

			return R.layout.base_head;
		}

		@Override
		public void onCreate(Context contex) {
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			findViewById(R.id.baseHeadLeft).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							onBackPressed();
						}
					});
		}

		@Override
		public XHeadViewHolder setBackEnabled(boolean enabled) {
			findViewById(R.id.baseHeadLeft).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder setBackImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackText(int resId) {

			((TextView) findViewById(R.id.baseHeadLeftTv)).setText(resId);
			return this;
		}

		@Override
		public XHeadViewHolder setBackText(String txt) {
			((TextView) findViewById(R.id.baseHeadLeftTv)).setText(txt);

			return this;
		}

		@Override
		public XHeadViewHolder setTitleEnabled(boolean enabled) {
			findViewById(R.id.baseHeadCenter).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder setTitleImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setTitleText(int resId) {
			((TextView) findViewById(R.id.baseHeadCenterTv)).setText(resId);

			return this;
		}

		@Override
		public XHeadViewHolder setTitleText(String txt) {
			((TextView) findViewById(R.id.baseHeadCenterTv)).setText(txt);
			return this;
		}

		@Override
		public XHeadViewHolder setMenuEnabled(boolean enabled) {
			findViewById(R.id.baseHeadRight).setEnabled(enabled);
			return this;
		}

		@Override
		public XHeadViewHolder setMenuImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuText(int resId) {
			((TextView) findViewById(R.id.baseHeadRightTv)).setText(resId);

			return this;
		}

		@Override
		public XHeadViewHolder setMenuText(String txt) {
			((TextView) findViewById(R.id.baseHeadRightTv)).setText(txt);
			return this;
		}

		@Override
		public XHeadViewHolder setBackVisibility(int visibility) {
			findViewById(R.id.baseHeadLeft).setVisibility(visibility);
			return this;
		}

		@Override
		public XHeadViewHolder setTitleVisibility(int visibility) {
			findViewById(R.id.baseHeadCenter).setVisibility(visibility);
			return this;
		}

		@Override
		public XHeadViewHolder setMenuVisibility(int visibility) {
			findViewById(R.id.baseHeadRight).setVisibility(visibility);
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
