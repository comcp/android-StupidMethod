package com.stupid.method.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.stupid.method.app.XActivity;
import com.stupid.method.app.XFragment;

public class XViewPagerAdapter extends FragmentPagerAdapter {

	List<Class<? extends XFragment>> mFragments;
	XActivity xActivity;

	public XViewPagerAdapter(XActivity activity,
			List<Class<? extends XFragment>> fragments) {
		super(activity.getSupportFragmentManager());
		mFragments = fragments;
		xActivity = activity;
	}

	@Override
	public Fragment getItem(int position) {
		try {

			return mFragments.get(position).newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			return new Fragment();
		}

	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

}
