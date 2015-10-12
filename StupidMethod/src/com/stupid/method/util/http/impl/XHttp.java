package com.stupid.method.util.http.impl;

import java.io.File;
import java.util.Map;

import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXHttpResultListener;

/** 插件化 HTTP 请求 */
public class XHttp implements IXHttp {
	IXHttp http;

	private String contentType = CONTENT_TYPE_DEFAULT;
	private String charset = CHARSET_DEFAULT;
	private String contentType_charset = String.format(CONTENT_TYPE_CHARSET,
			getContentType(), getCharset());

	public IXHttp postString(int resultCode, String url, String data,
			IXHttpResultListener resultListener) {
		post(resultCode, contentType_charset, url, data, resultListener);
		return this;
	}

	private void setContentType() {
		contentType_charset = String.format(CONTENT_TYPE_CHARSET,
				getContentType(), getCharset());
	}

	public IXHttp post(int resultCode, String contentType, String url,
			String data, IXHttpResultListener resultListener) {
		setContentType(contentType);
		http.post(resultCode, contentType_charset, url, data, resultListener);
		return this;
	}

	public IXHttp get(int resultCode, String url, Map<String, String> params,
			IXHttpResultListener resultListener) {
		http.get(resultCode, url, params, resultListener);
		return this;
	}

	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> header, IXHttpResultListener resultListener) {
		http.get(resultCode, url, params, header, resultListener);
		return this;
	}

	public IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXHttpResultListener resultListener) {
		http.postMap(resultCode, url, params, resultListener);
		return this;
	}

	public IXHttp download(int resultCode, String url, File target,
			IXHttpResultListener resultListener) {
		http.download(resultCode, url, target, resultListener);
		return this;
	}

	public XHttp(IXHttp http) {
		if (http == null)
			throw new NullPointerException();
		this.http = http;
	}

	public String getContentType() {
		return contentType;
	}

	public XHttp setContentType(String contentType) {
		this.contentType = contentType;
		setContentType();
		return this;
	}

	public String getCharset() {
		return charset;
	}

	public XHttp setCharset(String charset) {
		this.charset = charset;
		setContentType();
		return this;
	}
}
