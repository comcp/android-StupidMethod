package com.stupid.method.util.http.impl;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.stupid.method.util.URLUtil;
import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXHttpResultListener;

public class VolleyHttp implements IXHttp {
	private RequestQueue queue;
	private static WeakHashMap<Context, RequestQueue> weakHashMap = new WeakHashMap<Context, RequestQueue>();

	public VolleyHttp(Context context) {

		setQueue(weakHashMap.get(context) == null ? Volley.newRequestQueue(
				context, 2) : weakHashMap.get(context));
	}

	public IXHttp postString(int resultCode, String url, String data,
			IXHttpResultListener resultListener) {
		return post(resultCode, CONTENT_TYPE_DEFAULT, url, data, resultListener);

	}

	public IXHttp get(int resultCode, String url, Map<String, String> params,
			IXHttpResultListener resultListener) {
		url = URLUtil.getUrlWithParas(url, params);
		VolleyResultString resultString = new VolleyResultString(resultCode,
				resultListener);
		VolleyRequestString request = new VolleyRequestString(url,
				resultString, resultString);
		request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 1, 1.0f));
		getQueue().add(request);
		getQueue().start();
		return this;

	}

	public RequestQueue getQueue() {
		return queue;
	}

	public void setQueue(RequestQueue queue) {
		this.queue = queue;
	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			String data, IXHttpResultListener resultListener) {
		VolleyResultString resultString = new VolleyResultString(resultCode,
				resultListener);

		VolleyRequestString request = new VolleyRequestString(Method.POST, url,
				data, resultString, resultString);

		request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 1, 1.0f));
		getQueue().add(request);
		getQueue().start();
		return this;
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> header, IXHttpResultListener resultListener) {
		url = URLUtil.getUrlWithParas(url, params);
		VolleyResultString resultString = new VolleyResultString(resultCode,
				resultListener);

		VolleyRequestString request = new VolleyRequestString(url,
				resultString, resultString);

		request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 1, 1.0f));
		getQueue().add(request);
		getQueue().start();
		return this;
	}

	@Override
	public IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXHttpResultListener resultListener) {
		return this;

	}

	@Override
	public IXHttp download(int resultCode, String url, File target,
			IXHttpResultListener resultListener) {
		// http://gdown.baidu.com/data/wisegame/ab0619d2271e21dd/baidutieba_101253632.apk
		// VolleyResultFile file=new VolleyResultFile(Method.GET, url, target,
		// listener, errorListener)
		VolleyResultFile resultFile = new VolleyResultFile(resultCode,
				resultListener);
		VolleyRequestFile request = new VolleyRequestFile(Method.GET, url,
				target, resultFile, resultFile);
		request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, 1, 1.0f));
		getQueue().add(request);
		getQueue().start();
		return this;
	}

	@Override
	public String getCharset() {

		return CHARSET_DEFAULT;
	}

	@Override
	public IXHttp setCharset(String charset) {

		return this;
	}

	@Override
	public String getContentType() {

		return CONTENT_TYPE_DEFAULT;
	}

	@Override
	public IXHttp setContentType(String type) {

		return this;
	}
}
