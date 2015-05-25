package com.stupid.method.androidquery.expansion;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/** 为符合我的业务需求所添加 ***/
public class AQCallbackString extends AjaxCallback<String> implements
		IAQCallback {
	/** 不缓存 **/
	boolean invalid = true;
	private int CallBack_id = CALL_BACK_ID_DEF;
	private AQCallbackListener listener;

	public AQCallbackString(int CallbackId, AQCallbackListener listener) {
		this.listener = listener;
		this.CallBack_id = CallbackId;
	}

	@Override
	public void callback(String url, String object, AjaxStatus status) {

		if (isInvalid())
			status.invalidate();
		if (listener != null) {
			listener.callback(url, object, status, CallBack_id);

		}
	}

	public AQCallbackListener getListener() {
		return listener;
	}

	public void setListener(AQCallbackListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isInvalid() {

		return invalid;
	}

	@Override
	public AjaxCallback<String> setInvalid(boolean invalid) {
		this.invalid = invalid;

		return this;
	}
}
