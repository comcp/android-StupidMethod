package com.stupid.method.util.http.impl;

import java.util.HashMap;
import java.util.Map;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.Constants;
import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXHttpResultListener;

/** 基于 Aquery ***/
public class AQResultString extends AjaxCallback<String> implements IXHttpResult {

	private static final String tag = "AQResultString";
	private int resultCode;
	private IXHttpResultListener resultListener;

	public AQResultString(int resultCode, IXHttpResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);

	}

	public AQResultString setMethodPOST() {

		method(Constants.METHOD_POST);
		return this;
	}

	public AQResultString setHeader(Map<String, String> header) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.putAll(header);
		return this;

	}

	public AQResultString setMethodGet() {
		method(Constants.METHOD_GET);
		return this;
	}

	@Override
	public void callback(String url, String data, AjaxStatus status) {
		if (resultListener != null)
			if (status.getCode() == 200) {
				resultListener.onServerResult(resultCode, data, true,
						status.getCode());
			} else {
				resultListener.onServerResult(resultCode, data, true,
						status.getCode());

			}
		else {
			XLog.e(tag, "resultListener is null");
		}
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
