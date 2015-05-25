package com.stupid.method.adapter.t;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface SuperAdapterInterface<T> {
	public View convertView(int position, View convertView, ViewGroup parent,
			ArrayList<T> mData, LayoutInflater inflater);

}
