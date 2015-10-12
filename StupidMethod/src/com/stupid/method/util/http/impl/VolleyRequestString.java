/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stupid.method.util.http.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.stupid.method.util.MapUtil;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 * 
 * @param <String>
 *            JSON type of response expected
 */
public class VolleyRequestString extends Request<String> {
	Map<String, String> headers;

	/** Charset for request. */
	private String charset = "utf-8";

	/** Content type for request. */
	private String contentType = "application/json";

	private final Listener<String> mListener;
	private final String mRequestBody;

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {

		return MapUtil.isEmpty(headers) ? super.getHeaders() : headers;
	}

	public VolleyRequestString setHeaders(Map<String, String> map) {

		if (MapUtil.isEmpty(headers))
			headers = new HashMap<String, String>();
		if (!MapUtil.isEmpty(map))
			headers.putAll(map);

		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {

		this.charset = String.format("application/json; charset=%s", charset);

	}

	public VolleyRequestString(int method, String url, String requestBody,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mRequestBody = requestBody;
	}

	public VolleyRequestString(int method, String url, JSONObject requestBody,
			Listener<String> listener, ErrorListener errorListener) {
		this(method, url, requestBody.toJSONString(), listener, errorListener);
	}

	public VolleyRequestString(String url, Listener<String> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, "", listener, errorListener);
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}

	/**
	 * @deprecated Use {@link #getBodyContentType()}.
	 */
	@Override
	public String getPostBodyContentType() {
		return getBodyContentType();
	}

	/**
	 * @deprecated Use {@link #getBody()}.
	 */
	@Override
	public byte[] getPostBody() {
		return getBody();
	}

	@Override
	public String getBodyContentType() {
		return String.format("%s; charset=%s", getContentType(), getCharset());
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody
					.getBytes(getCharset());
		} catch (UnsupportedEncodingException uee) {
			VolleyLog
					.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
							mRequestBody, getCharset());
			return null;
		}
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(jsonString,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

}
