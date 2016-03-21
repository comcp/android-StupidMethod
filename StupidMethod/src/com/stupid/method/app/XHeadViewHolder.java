package com.stupid.method.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stupid.method.adapter.IXViewHolder;
import com.stupid.method.adapter.OnClickItemListener;
import com.stupid.method.adapter.OnLongClickItemListener;
import com.stupid.method.util.XLog;

public abstract class XHeadViewHolder<T extends XHeadViewHolder<?>> implements
		IXViewHolder<Object> {
	private static final String tag = "XHeadViewHolder";
	private View mRoot;

	@Override
	public final View getView() {

		return mRoot;
	}

	public View findViewById(int id) {

		if (mRoot == null) {
			XLog.e(tag, "XHeadViewHolder.mRoot 未初始化");
			return null;
		}
		return mRoot.findViewById(id);
	}

	public XHeadViewHolder<T> setVisibility(int visibility) {
		if (mRoot != null)
			mRoot.setVisibility(visibility);
		else
			XLog.e(tag, "XHeadViewHolder.mRoot 未初始化");
		return this;
	}

	abstract public XHeadViewHolder<T> setTitleText(String txt);

	abstract public XHeadViewHolder<T> setTitleText(int resId);

	abstract public XHeadViewHolder<T> setTitleImg(int resId);

	abstract public XHeadViewHolder<T> setBackImg(int resId);

	abstract public XHeadViewHolder<T> setBackText(String txt);

	abstract public XHeadViewHolder<T> setBackText(int resId);

	abstract public XHeadViewHolder<T> setMenuText(int resId);

	abstract public XHeadViewHolder<T> setMenuText(String txt);

	abstract public XHeadViewHolder<T> setMenuImg(int resId);

	abstract public XHeadViewHolder<T> setMenuEnabled(boolean enabled);

	abstract public XHeadViewHolder<T> setBackEnabled(boolean enabled);

	abstract public XHeadViewHolder<T> setTitleEnabled(boolean enabled);

	abstract public XHeadViewHolder<T> setBackVisibility(int visibility);

	abstract public XHeadViewHolder<T> setTitleVisibility(int visibility);

	abstract public XHeadViewHolder<T> setMenuVisibility(int visibility);

	@Override
	@Deprecated
	public View getView(Object arg0, int arg1, boolean arg2) {

		return null;
	}

	@Override
	abstract public void onCreate(Context contex);

	@Deprecated
	@Override
	public void onDestory(int arg0, int arg1) {
	}

	@Override
	public View setInflater(LayoutInflater inflater) {

		return mRoot = inflater.inflate(getLayoutId(), null);
	}

	@Deprecated
	@Override
	public void setOnClickItemListener(OnClickItemListener arg0) {
	}

	@Deprecated
	@Override
	public void setOnLongClickItemListener(OnLongClickItemListener arg0) {

	}

	public T getTemplte() {
		return (T) this;
	}

}