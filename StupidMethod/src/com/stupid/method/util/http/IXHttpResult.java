package com.stupid.method.util.http;

public interface IXHttpResult {
	public static final int RESULT_OK = 200;
	public static final int RESULT_CANCEL = 1;

	void setResultCode(int resultCode);

	void setResultListener(IXHttpResultListener resultListener);

	int getResultCode();

	IXHttpResultListener getResultListener();
}
