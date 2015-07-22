package com.stupid.method.app;

import com.androidquery.AQuery;
import com.stupid.method.androidquery.expansion.AQCallbackListener;
import com.stupid.method.util.MapUtil;

/**
 * Activity 需要实现的方法,都在这里定义好之后,去实现 <br>
 * 为了方便查漏
 * 
 * ***/
public interface IXActivity extends AQCallbackListener {
	
	AQuery ajax(int CallBack_id, String url, MapUtil<String, ?> params);

	void showToast(String text, int duration);

	void showToast(int text, int duration);

	void showToast(String text);

	void showToast(int text);

	/**
	 * 获得content
	 * **/
	XActivity getContent();

	AQuery getAQuery();

	/** 获得容器id **/
	int getLayoutId();

	void waitof(String msg);

	void waitof();

}
