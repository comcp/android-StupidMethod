package com.stupid.method.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.stupid.method.app.XActivity;
import com.stupid.method.app.XFragment;

public class XViewPagerAdapter extends FragmentPagerAdapter {

	List<FragmentParam> mFragments;

	public XViewPagerAdapter(XActivity activity,
			List<Class<? extends XFragment>> fragments) {
		super(activity.getSupportFragmentManager());
		mFragments = new ArrayList<XViewPagerAdapter.FragmentParam>();
		FragmentParam param = null;
		for (Class<? extends XFragment> class1 : fragments) {
			param = new FragmentParam();
			param.xFragment = class1;
			mFragments.add(param);
		}

	}

	public XViewPagerAdapter(XActivity activity, List<FragmentParam> fragments,
			int i) {
		super(activity.getSupportFragmentManager());
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		try {

			if (mFragments.get(position).fragment == null) {
				mFragments.get(position).fragment = (XFragment) mFragments
						.get(position).xFragment.newInstance();
			}
			mFragments.get(position).fragment
					.setData(mFragments.get(position).data);
			return mFragments.get(position).fragment;

		} catch (Exception e) {
			e.printStackTrace();
			return new Fragment();
		}

	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	public static class FragmentParam {
		private XFragment fragment;
		public Class<? extends XFragment> xFragment;
		public Object data;

	}
}
