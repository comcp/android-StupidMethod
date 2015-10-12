package com.stupid.method.util.http.impl;

import java.io.File;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXHttpResultListener;

public class VolleyResultFile implements Listener<File>, IXHttpResult,
		ErrorListener {

	private static final String tag = "VolleyResultFile";
	private int resultCode;
	private IXHttpResultListener resultListener;

	public VolleyResultFile(int resultCode,
			IXHttpResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
	}

	@Override
	public void onResponse(File data) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, data.getAbsolutePath(),
					true, IXHttpResult.RESULT_OK);
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
