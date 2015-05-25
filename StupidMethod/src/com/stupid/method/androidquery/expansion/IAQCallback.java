package com.stupid.method.androidquery.expansion;

import com.androidquery.callback.AjaxCallback;

/**
 * AQCallback 类预留
 * 
 ***/
public interface IAQCallback {

	public static final int CALL_BACK_ID_DEF = -5;

	AQCallbackListener getListener();

	void setListener(AQCallbackListener listener);

	/** 是否缓存数据,默认不缓存 **/
	boolean isInvalid();

	/** 设置是否缓存数据 **/
	AjaxCallback setInvalid(boolean invalid);
}
