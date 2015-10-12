package com.stupid.method.app.crash;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

public class XCrashHandler implements UncaughtExceptionHandler {

	private Context mContext;
	private UncaughtExceptionHandler mDefaultHandler;

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

	}

	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

}
