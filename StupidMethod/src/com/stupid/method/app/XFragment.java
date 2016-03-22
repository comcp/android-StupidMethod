package com.stupid.method.app;

import java.io.Serializable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.stupid.method.adapter.XFragmentPagerAdapter.FragmentParam;

abstract public class XFragment extends Fragment implements IXFragment {
	static final String TAG = "XFragment";
	static final String SAVE_SERIALIZABLE = "SAVE_SERIALIZABLE";
	static final String SAVE_INTENT = "SAVE_INTENT";
	private View mRootView;
	protected Serializable data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			if (savedInstanceState != null
					&& savedInstanceState.getBoolean(SAVE_INTENT, false))
				data = savedInstanceState.getSerializable(SAVE_SERIALIZABLE);

			setRootView(inflater.inflate(getLayoutId(), null));
			initPager(savedInstanceState, data);

		}
		return getRootView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (null != data) {
			outState.putSerializable(SAVE_SERIALIZABLE, data);
			outState.putBoolean(SAVE_INTENT, true);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public AQuery getAQuery() {

		return getContent().getAQuery();
	}

	@Override
	public void setData(Serializable object) {
		this.data = object;
	}

	@Override
	public View findViewById(int id) {

		return getRootView().findViewById(id);
	}

	public XActivity getContent() {

		return (XActivity) getActivity();
	}

	public View getRootView() {
		return mRootView;
	}

	public FragmentParam pushFragmentToBackStack(
			Class<? extends XFragment> cls, Serializable data) {
		return getContent().pushFragment(cls, data, true);
	}

	public FragmentParam pushFragment(Class<? extends XFragment> cls,
			Serializable data, boolean isBack) {

		return getContent().pushFragment(cls, data, isBack);
	}

	public FragmentParam addFragment(FragmentParam param) {
		return getContent().addFragment(param);
	}

	@Override
	public void showToast(String text, int duration) {
		getContent().showToast(text, duration);
	}

	@Override
	public void showToast(String text) {
		getContent().showToast(text);
	}

	@Override
	public void showToast(int text) {
		getContent().showToast(text);

	}

	@Override
	public void showToast(int text, int duration) {
		getContent().showToast(text, duration);

	}

	@Override
	public XDialogFragment waitof() {
		if (getContent() != null) {
			return getContent().waitof();
		} else
			return null;

	}

	@Override
	public XDialogFragment waitof(String msg, long timeout) {

		if (getContent() != null) {
			return getContent().waitof(msg, timeout);
		} else
			return null;
	}

	@Override
	public XDialogFragment waitof(String msg) {
		if (getContent() != null) {
			return getContent().waitof(msg);
		} else
			return null;
	}

	@Override
	public void waitEnd() {
		if (getContent() != null)
			getContent().waitEnd();

	}

	@Override
	public XDialogFragment waitof(String msg, boolean cancel, long timeout) {
		if (getContent() != null) {
			return getContent().waitof(msg, cancel, timeout);
		} else
			return null;
	}

	public void setRootView(View mRootView) {
		this.mRootView = mRootView;
	}

	@Override
	public void onServerResult(int resultCode, String data, boolean state,
			int statusCode) {
	}

}
