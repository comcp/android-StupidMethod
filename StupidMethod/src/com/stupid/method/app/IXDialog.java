package com.stupid.method.app;

public interface IXDialog {

	IXDialog show();

	void dismiss();

	boolean isShowing();

	void setCancelable(boolean cancelable);
}
