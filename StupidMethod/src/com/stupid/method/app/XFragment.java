package com.stupid.method.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.stupid.method.androidquery.expansion.AQCallbackString;
import com.stupid.method.util.MapUtil;
import com.stupid.method.util.XLog;

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

		if (null != mRootView) {
			ViewGroup parent = (ViewGroup) mRootView.getParent();
			if (null != parent) {
				parent.removeView(mRootView);
				parent = null;
			}
		} else {
			mRootView = inflater.inflate(getLayoutId(), null);
			initPager(savedInstanceState, data);

		}
		return mRootView;
	}

	public AQuery ajax(int CallBack_id, String url, MapUtil<String, ?> params) {

		return getContent().getAQuery().ajax(url, params.getHashMap(),
				String.class, new AQCallbackString(CallBack_id, this));

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

		return mRootView.findViewById(id);
	}

	public XActivity getContent() {

		return (XActivity) getActivity();
	}

	public View getRootView() {
		return mRootView;
	}

	@Override
	public void callback(String url, String callback_data, AjaxStatus status,
			int CallBack_id) {
		if (AppConfig.DEBUG && status.getCode() != 200) {
			XLog.d(TAG, status.getMessage());
			XLog.d(TAG, status.getError());
			XLog.d(TAG, url);

		}
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
	public void waitof() {
		if (getContent() != null) {
			getContent().waitof();
		}

	}

	@Override
	public void waitof(String msg) {
		if (getContent() != null) {
			getContent().waitof(msg);
		}
	}

}
