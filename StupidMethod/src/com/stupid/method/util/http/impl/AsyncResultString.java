package com.stupid.method.util.http.impl;

import org.apache.http.Header;

import com.loopj.android.http.TextHttpResponseHandler;
import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXHttpResultListener;

public class AsyncResultString extends TextHttpResponseHandler implements
		IXHttpResult {
	private static final String tag = "AsyncResultString";
	private int resultCode;
	private IXHttpResultListener resultListener;

	public AsyncResultString(int resultCode,
			IXHttpResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
	}

	/**
	 * Called when request fails
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param responseString
	 *            string response of given charset
	 * @param throwable
	 *            throwable returned when processing request
	 */
	public void onFailure(int statusCode, Header[] headers, String data,
			Throwable throwable) {
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), data, true,
					statusCode);
		else {
			XLog.e(tag, "resultListener is null");
		}
	}

	/**
	 * Called when request succeeds
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param responseString
	 *            string response of given charset
	 */
	public void onSuccess(int statusCode, Header[] headers, String data) {
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), data, false,
					statusCode);
		else {
			XLog.e(tag, "resultListener is null");
		}
	}

	@Override
	public void onCancel() {
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), null, false,
					IXHttpResult.RESULT_CANCEL);
		else {
			XLog.e(tag, "resultListener is null");
		}
		super.onCancel();
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
