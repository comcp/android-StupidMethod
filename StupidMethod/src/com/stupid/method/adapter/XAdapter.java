package com.stupid.method.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class XAdapter<T> extends BaseAdapter {
	private List<T> mData;
	private ISuperAdapter<T> adapterInterface;
	private LayoutInflater inflater;
	private Context context;

	public XAdapter(Context context, List<T> mData,
			ISuperAdapter<T> adapterInterface) {
		this.mData = mData;
		this.adapterInterface = adapterInterface;
		this.context = context;
		inflater = LayoutInflater.from(context);

	}

	public List<T> getmData() {
		return mData;
	}

	public ISuperAdapter<T> getAdapterInterface() {
		return adapterInterface;
	}

	public void setmData(ArrayList<T> mData) {
		this.mData = mData;
		this.notifyDataSetChanged();
	}

	public void setAdapterInterface(ISuperAdapter<T> adapterInterface) {
		this.adapterInterface = adapterInterface;
	}

	@Override
	public int getCount() {

		return mData == null ? 0 : mData.size();
	}

	@Override
	public T getItem(int position) {

		return mData == null ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	/***
	 * 更新整个列表 <br>
	 * 先删除列表内数据,再更新
	 * */
	public void upData(ArrayList<T> mData) {
		if (mData == null) {
			this.mData = mData;
		} else {
			this.mData.clear();
			this.mData.addAll((ArrayList<T>) mData.clone());
		}

		this.notifyDataSetChanged();

	}

	public void add(T t) {
		if (mData != null)
			mData.add(t);
	}

	/***
	 * 向列表尾部添加数据
	 * */
	public void addAll(ArrayList<T> mData) {

		if (mData != null)
			this.mData.addAll(mData);
		else {
			this.mData = mData;
		}

		this.notifyDataSetChanged();
	}

	public void remove(int index) {
		if (mData != null && -1 < index && index < mData.size())
			mData.remove(index);
		this.notifyDataSetChanged();

	}

	public void remove(T object) {
		if (mData != null)
			mData.remove(object);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (adapterInterface != null)
			return adapterInterface.convertView(position, convertView, parent,
					mData, inflater);
		else {
			TextView tv = new TextView(context);
			tv.setPadding(10, 10, 10, 10);
			tv.setText("没有设置 ISuperAdapter");
			return tv;
		}
	}

}