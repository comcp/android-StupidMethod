package com.stupid.method.app;

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
	private View mRootView;
	protected Object data;

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
			setRootView(inflater.inflate(getLayoutId(), null));
			initPager(savedInstanceState, data);

		}
		return getRootView();
	}

	@Override
	public AQuery getAQuery() {

		return getContent().getAQuery();
	}

	@Override
	public void setData(Object object) {
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

	public FragmentParam pushFragmentToBackStack(Class<?> cls, Object data) {
		return getContent().pushFragment(cls, data, true);
	}

	public FragmentParam pushFragment(Class<?> cls, Object data, boolean isBack) {

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
