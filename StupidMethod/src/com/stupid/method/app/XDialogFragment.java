package com.stupid.method.app;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.stupid.method.util.MapUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class XDialogFragment extends DialogFragment implements
		IXFragment {

	private View mRootView;

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
			mRootView = inflater.inflate(getLayoutId(), null);
			initPager(savedInstanceState, null);

		}
		return mRootView;
	}
	
	public void show(XActivity xActivity, boolean cancel) {
		setCancelable(cancel);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);

		show(xActivity.getSupportFragmentManager(), this.getClass().getName());
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		super.show(manager, tag);
	}

	@Override
	public int show(FragmentTransaction transaction, String tag) {
		return super.show(transaction, tag);
	}

	@Override
	public void callback(String url, String callback_data, AjaxStatus status,
			int CallBack_id) {
	}

	@Override
	public AQuery ajax(int CallBack_id, String url, MapUtil<String, ?> params) {

		return null;
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
	public void waitof(String msg) {
	}

	@Override
	public void waitof() {
	}

	@Override
	public void setData(Object object) {
	}

	@Override
	final public View findViewById(int id) {

		return mRootView.findViewById(id);
	}
}
