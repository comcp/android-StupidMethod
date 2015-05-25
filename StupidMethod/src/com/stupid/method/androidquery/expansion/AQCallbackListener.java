package com.stupid.method.androidquery.expansion;

import com.androidquery.callback.AjaxStatus;

public interface AQCallbackListener {
	public void callback(String url, String callback_data, AjaxStatus status,
			int CallBack_id);
}