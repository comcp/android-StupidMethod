package com.stupid.method.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class XViewPageAdapter<T> extends PagerAdapter {

	XAdapter2<T> adapter2;
	private int currterId = 0;
	private View removeView = null;

	public XViewPageAdapter(Context context, List<T> mData,
			Class<? extends XViewHolder<T>> xViewHolder) {
		adapter2 = new XAdapter2<T>(context, mData, xViewHolder);

	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

		adapter2.registerDataSetObserver(observer);
	}

	@Override
	public int getCount() {

		return adapter2.getCount();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View view = adapter2.getView(position, null, container);

		container.addView(view);
		System.out.println("inst:" + position + " ." + view.getTag());
		currterId = position;
		return null;
	}

	@Override
	public Object instantiateItem(View container, int position) {

		return adapter2.getView(position, container, null);

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		removeView = (View) object;
		((ViewGroup) removeView.getParent()).removeView(removeView);
		// container.removeView(removeView);
		System.out.println("destroyItem:" + position + " obj:"
				+ object.getClass().getSimpleName());

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

}
