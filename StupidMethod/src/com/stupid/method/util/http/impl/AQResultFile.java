package com.stupid.method.util.http.impl;

import java.io.File;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXHttpResultListener;

public class AQResultFile extends AjaxCallback<File> implements IXHttpResult {
	private static final String tag = "AQResultFile";
	private int resultCode;
	private IXHttpResultListener resultListener;

	AQResultFile(int resultCode, IXHttpResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);

	}

	@Override
	public void callback(String url, File data, AjaxStatus status) {
		if (resultListener != null)
			if (status.getCode() == 200) {
				resultListener.onServerResult(resultCode,
						data.getAbsolutePath(), true, status.getCode());
			} else {
				resultListener.onServerResult(resultCode, null, true,
						status.getCode());

			}
		else {
			XLog.e(tag, "resultListener is null");
		}
	}

	public IXHttpResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(IXHttpResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
