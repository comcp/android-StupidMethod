package com.stupid.method.androidquery.expansion;

import java.io.File;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class AQCallbackFile extends AjaxCallback<File> implements IAQCallback {
	private AQCallbackListener listener;
	private int CallBack_id = CALL_BACK_ID_DEF;
	private boolean invalid;

	AQCallbackFile(int CallbackId, AQCallbackListener listener) {
		this.listener = listener;
		this.CallBack_id = CallbackId;
	}

	@Override
	public void callback(String url, File object, AjaxStatus status) {

		if (isInvalid())
			status.invalidate();
		if (listener != null) {
			listener.callback(url, object.getAbsolutePath(), status,
					CallBack_id);
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
	public AjaxCallback<File> setInvalid(boolean invalid) {
		this.invalid = invalid;

		return this;
	}
}
