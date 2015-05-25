package com.stupid.method.app;

import android.view.View;

abstract public class XFragView implements IFragView {
	XFragment mFragment;
	View mRoot;

	public XFragView(XFragment x) {
		mFragment = x;
		setContentView();
		onCreate();
	}

	protected View findViewById(int id) {
		return mRoot == null ? mFragment.findViewById(id) : mRoot
				.findViewById(id);
	}

	private void setContentView() {
		mRoot = findViewById(getLayoutId());
	}

	public View getRoot() {
		return mRoot;
	}

	public XFragView gone() {
		mRoot.setVisibility(View.GONE);
		return this;
	}

	public XFragView show() {
		mRoot.setVisibility(View.VISIBLE);

		return this;
	}
}