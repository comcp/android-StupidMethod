package com.stupid.method.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stupid.method.util.XLog;

/**
 * 使用反射做adapter<br>
 * 使用的时候只需要写一个ViewHolder 继承自{@link XViewHolder}<br>
 * 当@link
 * {@link XAdapter2#convertView(int, View, ViewGroup, ArrayList, LayoutInflater)}
 * 时 <br>
 * 对viewholder进行实例化. 初始化view
 * 
 * @未来 打算重写 ViewHolder,给改成接口形式的,
 * 
 * **/
public class XAdapter2<T> extends XAdapter<T> implements ISuperAdapter<T> {
	private static final String tag = "XAdapter";
	private Class<T> ViewBean;
	private Constructor<?> mConstructor;
	private OnClickItemListener clickItemListener;

	private OnLongClickItemListener longClickItemListener;

	public XAdapter2(Context context, List<T> mData,
			Class<? extends XViewHolder<T>> xViewHolder) {
		super(context, mData, null);
		super.setAdapterInterface(this);

		try {
			this.ViewBean = (Class<T>) Class.forName(xViewHolder.getName());
			mConstructor = ViewBean.getConstructor(LayoutInflater.class);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public View convertView(int position, View convertView, ViewGroup parent,
			List<T> mData, LayoutInflater inflater) {
		XViewHolder<T> view = null;

		if (convertView == null) {
			// 在这里写 反射

			try {
				if (mConstructor != null) {
					view = (XViewHolder<T>) mConstructor.newInstance(inflater);
					if (clickItemListener != null)
						view.setOnClickItemListener(this.clickItemListener);
					if (longClickItemListener != null)
						view.setOnLongClickItemListener(longClickItemListener);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} else {
			view = (XViewHolder<T>) convertView.getTag();
		}

		return view.getView(mData.get(position), position);
	}

	public OnClickItemListener getClickItemListener() {
		return clickItemListener;
	}

	public OnLongClickItemListener getLongClickItemListener() {
		return longClickItemListener;
	}

	public void setClickItemListener(OnClickItemListener clickItemListener) {
		this.clickItemListener = clickItemListener;
	}

	public void setLongClickItemListener(
			OnLongClickItemListener longClickItemListener) {
		this.longClickItemListener = longClickItemListener;
	}
}