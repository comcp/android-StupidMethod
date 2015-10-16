package com.stupid.method.app;

import com.androidquery.AQuery;
import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXServerResultListener;

/**
 * Activity 需要实现的方法,都在这里定义好之后,去实现 <br>
 * 为了方便查漏
 * 
 * ***/
public interface IXActivity extends IXServerResultListener {

	void showToast(String text, int duration);

	void showToast(int text, int duration);

	void showToast(String text);

	void showToast(int text);

	/**
	 * 获得content
	 * **/
	XActivity getContent();

	AQuery getAQuery();

	IXHttp getHttp();

	/** 获得容器id **/
	int getLayoutId();

	XDialogFragment waitof(String msg, boolean cancel, long timeout);

	XDialogFragment waitof(String msg, long timeout);

	XDialogFragment waitof(String msg);

	XDialogFragment waitof();

	void waitEnd();

}
