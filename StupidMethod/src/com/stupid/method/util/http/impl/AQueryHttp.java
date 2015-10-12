package com.stupid.method.util.http.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXHttpResultListener;

public class AQueryHttp implements IXHttp {

	private AQuery query;

	public AQueryHttp(Context context) {
		setQuery(new AQuery(context));
	}

	public IXHttp postMap(int resultCode, String url, Map<String, ?> param,
			IXHttpResultListener resultListener) {

		getQuery().ajax(url, param, String.class,
				new AQResultString(resultCode, resultListener).setMethodPOST());

		return this;
	}

	public IXHttp download(int resultCode, String url, File target,
			IXHttpResultListener resultListener) {
		getQuery().download(url, target,
				new AQResultFile(resultCode, resultListener));
		return this;
	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			String data, IXHttpResultListener resultListener) {
		try {
			post(url, contentType, new StringEntity(data, CHARSET_DEFAULT),
					String.class,
					new AQResultString(resultCode, resultListener));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}

	public IXHttp postString(int resultCode, String url, String data,
			IXHttpResultListener resultListener) {
		post(resultCode, CONTENT_TYPE_DEFAULT, url, data, resultListener);
		return this;
	}

	public IXHttp get(int resultCode, String url, Map<String, String> param,
			Map<String, String> header, IXHttpResultListener resultListener) {
		getQuery().ajax(
				url,
				param,
				String.class,
				new AQResultString(resultCode, resultListener).setMethodGet()
						.setHeader(header));
		return this;
	}

	private void post(String url, String contentHeader, HttpEntity entity,
			Class<String> type, AjaxCallback<String> callback) {

		getQuery().post(url, contentHeader, entity, type, callback);
	}

	public IXHttp get(int resultCode, String url, Map<String, String> params,
			IXHttpResultListener resultListener) {

		getQuery().ajax(url, params, String.class,
				new AQResultString(resultCode, resultListener));
		return this;
	}

	public AQuery getQuery() {
		return query;
	}

	public void setQuery(AQuery aQuery) {
		this.query = aQuery;
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
