package com.stupid.method.adapter.t;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stupid.method.adapter.t.RecyclerAdapter.RecyView;

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyView> {

	List<T> list;
	private LayoutInflater inflater;
	private Context context;

	public RecyclerAdapter(Context context, List<T> list) {
		this.setContext(context);
		this.inflater = LayoutInflater.from(context);
		this.list = list;

	}

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public void onBindViewHolder(RecyView viewHolder, int position) {

		bindView((RecyView) viewHolder, list.get(position), position);
	}

	@Override
	public RecyView onCreateViewHolder(ViewGroup viewGroup, int i) {

		return new RecyView(getView(inflater));

	}

	public void setList(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	abstract public View getView(LayoutInflater inflater);

	abstract public View bindView(RecyView viewHolder, T t, int position);

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	static public class RecyView extends ViewHolder {

		public RecyView(View view) {
			super(view);
		}

		public View findViewById(int id) {

			return super.itemView.findViewById(id);
		}

	}

}
