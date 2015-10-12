package com.stupid.method.util.http;

import java.io.File;
import java.util.Map;

public interface IXHttp {

	public static final int TIMEOUT = 20000;
	public static final String CONTENT_TYPE_CHARSET = "%s; charset=%s";
	public static final String CONTENT_TYPE = "%s; charset=%s";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT = "text/plain";
	public static String CONTENT_TYPE_DEFAULT = CONTENT_TYPE_TEXT;
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_GB2312 = "GB2312";

	public static String CHARSET_DEFAULT = CHARSET_UTF8;

	String getCharset();

	IXHttp setCharset(String charset);

	String getContentType();

	IXHttp setContentType(String type);

	IXHttp postString(int resultCode, String url, String data,
			IXHttpResultListener resultListener);

	IXHttp post(int resultCode, String contentType, String url, String data,
			IXHttpResultListener resultListener);

	IXHttp get(int resultCode, String url, Map<String, String> params,
			IXHttpResultListener resultListener);

	IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> header, IXHttpResultListener resultListener);

	IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXHttpResultListener resultListener);

	IXHttp download(int resultCode, String url, File target,
			IXHttpResultListener resultListener);
}
