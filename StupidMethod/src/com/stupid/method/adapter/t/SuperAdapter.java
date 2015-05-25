package com.stupid.method.adapter.t;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.stupid.method.adapter.XViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class SuperAdapter<T> extends Adapter<ViewHolder> {
	private SuperAdapterInterface<T> adapterInterface;
	private List<T> mData;
	private Context context;
	private Class<? extends RecViewItemBase> holder;
	private Constructor<?> mConstructor;
	private LayoutInflater inflater;

	@Override
	public int getItemCount() {

		return mData == null ? 0 : mData.size();
	}

	@SuppressWarnings("unchecked")
	public SuperAdapter(Context context, Class<? extends RecViewItemBase> holder) {

		inflater = LayoutInflater.from(context);
		try {

			this.holder = (Class<? extends RecViewItemBase>) Class
					.forName(holder.getName());

			mConstructor = holder.getConstructor(LayoutInflater.class);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public SuperAdapter(Context context, List<T> mData,
			Class<? extends ViewHolder> cls) {

		this.mData = mData;

	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.itemView.getTag(100);

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecViewItemBase holder = null;

		try {
			holder = (RecViewItemBase) mConstructor.newInstance(inflater);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return holder.getHolder();
	}

	static abstract class RecViewItemBase<T> extends XViewHolder<T> {

		public RecViewItemBase(LayoutInflater inflater) {
			super(inflater);
		}

		abstract public ViewHolder getHolder();

	}

}