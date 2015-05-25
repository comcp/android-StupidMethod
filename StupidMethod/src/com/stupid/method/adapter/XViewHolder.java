package com.stupid.method.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

/**
 * 
 * 说明：
 * 
 * @author comcp@126.com
 * 
 * @version
 * 
 * @创建时间：2014-12-3上午11:21:31
 * 
 */

public abstract class XViewHolder<T> implements OnClickListener,
		OnLongClickListener {
	private View mRoot;
	public LayoutInflater inflater;
	private OnClickItemListener itemListener;
	private OnLongClickItemListener longClickItemListener;
	public T mData;
	public int position;

	public XViewHolder(LayoutInflater inflater) {
		this.inflater = inflater;
		onCreat();

	}

	abstract protected void onCreat();

	public Drawable getDrawableById(int id) {
		// 获取到资源id
		if (null == mRoot)
			return null;
		Drawable drawable = mRoot.getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		return drawable;
	}

	/*** 设置item 显示内容的标签 **/
	protected void setContentView(int id) {
		mRoot = inflater.inflate(id, null);

	}

	/** 重新初始化 view 显示数据 **/
	@Deprecated
	protected void setData(T data) {
		mData = data;

	}

	protected View findViewById(int id) {
		if (mRoot != null)
			return mRoot.findViewById(id);
		else
			return null;
	}

	protected abstract void setView(T data, int position);

	public View getView(T t, int position) {
		this.mData = t;
		this.position = position;
		setView(t, position);
		mRoot.setTag(this);
		return mRoot;
	}

	@Override
	public void onClick(View v) {
		if (itemListener != null) {

			itemListener.onClickItem(v, position);
		}

	}

	@Override
	public boolean onLongClick(View v) {
		if (longClickItemListener != null) {
			return longClickItemListener.onLongClickItem(v, position);
		} else
			return false;
	}

	public void setOnLongClickItemListener(
			OnLongClickItemListener longClickItemListener) {
		this.longClickItemListener = longClickItemListener;
	}

	public void setOnClickItemListener(OnClickItemListener itemListener) {
		this.itemListener = itemListener;
	}

	public View getViewRoot() {
		return mRoot;
	}

}
