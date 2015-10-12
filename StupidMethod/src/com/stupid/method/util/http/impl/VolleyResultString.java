package com.stupid.method.util.http.impl;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXHttpResultListener;

public class VolleyResultString implements Listener<String>, IXHttpResult,
		ErrorListener {

	private static final String tag = "VolleyResultString";
	private int resultCode;
	private IXHttpResultListener resultListener;

	/**
	 * Decoding lock so that we don't decode more than one image at a time (to
	 * avoid OOM's)
	 */

	public VolleyResultString(int resultCode,
			IXHttpResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
	}

	@Override
	public void onResponse(String data) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, data, true,
					IXHttpResult.RESULT_OK);
		} else
			XLog.e(tag, "resultListener");
	}

	@Override
	public void onErrorResponse(VolleyError v) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, v.getMessage(), false,
					v.networkResponse.statusCode);
		} else
			XLog.e(tag, "resultListener");

	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public IXHttpResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(IXHttpResultListener resultListener) {
		this.resultListener = resultListener;
	}

}
