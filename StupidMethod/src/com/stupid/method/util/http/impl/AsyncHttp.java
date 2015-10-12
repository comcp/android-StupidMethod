package com.stupid.method.util.http.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.entity.StringEntity;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.stupid.method.app.AppManager;
import com.stupid.method.util.MapUtil;
import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXHttpResultListener;

public class AsyncHttp implements IXHttp {
	private static SyncHttpClient query = new SyncHttpClient();
	static {
		getQuery().setTimeout(TIMEOUT);
	}
	private String contentType = String.format(CONTENT_TYPE_CHARSET,
			getContentType(), getCharset());

	@Override
	public IXHttp postString(int resultCode, String url, String data,
			IXHttpResultListener resultListener) {
		return post(resultCode, contentType, url, data, resultListener);

	}

	public IXHttp postMap(int resultCode, String url, Map<String, ?> param,
			IXHttpResultListener resultListener) {

		RequestParams requestParams = new RequestParams();
		if (!MapUtil.isEmpty(param))
			for (Entry<String, ?> p : param.entrySet()) {

				requestParams.put(p.getKey(), p.getValue());

			}
		getQuery().post(url, requestParams,
				new AsyncResultString(resultCode, resultListener));
		return this;
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> param,
			IXHttpResultListener resultListener) {

		getQuery().post(url, new RequestParams(param),
				new AsyncResultString(resultCode, resultListener));
		return this;
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> param,
			Map<String, String> header, IXHttpResultListener resultListener) {

		if (!MapUtil.isEmpty(header))
			for (Entry<String, String> e : header.entrySet()) {
				getQuery().addHeader(e.getKey(), e.getValue());
			}
		return get(resultCode, url, param, resultListener);
	}

	public IXHttp download(int resultCode, String url, File target,
			IXHttpResultListener resultListener) {
		getQuery().get(url,
				new AsyncResultFile(target, resultCode, resultListener));
		return this;
	}

	public static SyncHttpClient getQuery() {
		return query;
	}

	public static void setQuery(SyncHttpClient query) {
		AsyncHttp.query = query;
	}

	/**
	 * 取消所有http请求
	 */
	public static void cancelAllRequests() {
		// 会触发 responseHandler实现的的onCancel();
		getQuery().cancelAllRequests(true);

	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			String data, IXHttpResultListener resultListener) {

		try {
			getQuery().post(AppManager.getInstance(), url,
					new StringEntity(data), contentType,
					new AsyncResultString(resultCode, resultListener));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public String getCharset() {

		return CHARSET_DEFAULT;
	}

	@Override
	public IXHttp setCharset(String charset) {

		return null;
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
