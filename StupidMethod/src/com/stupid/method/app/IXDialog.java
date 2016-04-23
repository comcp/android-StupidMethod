package com.stupid.method.app;

public interface IXDialog {

	XDialog show();

	void dismiss();

	boolean isShowing();

	void setCancelable(boolean cancelable);
}
