package com.stupid.method.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.stupid.method.app.XActivity;
import com.stupid.method.app.XFragment;
import com.stupid.method.util.ListUtils;

public class XFragmentPagerAdapter extends FragmentPagerAdapter {

	List<FragmentParam> mFragments;
	private boolean isInfiniteLoop = true;

	public XFragmentPagerAdapter(XActivity activity,
			Class<? extends XFragment>... fragments) {
		super(activity.getSupportFragmentManager());
		mFragments = new ArrayList<XFragmentPagerAdapter.FragmentParam>();
		FragmentParam param = null;
		for (Class<? extends XFragment> class1 : fragments) {
			param = new FragmentParam();
			param.cls = class1;
			mFragments.add(param);
		}

	}

	public XFragmentPagerAdapter add(FragmentParam object) {
		mFragments.add(object);

		return this;
	}

	public XFragmentPagerAdapter remove(int idex) {

		mFragments.remove(idex);

		return this;
	}

	public XFragmentPagerAdapter(XActivity activity, FragmentParam... params) {
		this(activity, Arrays.asList(params));
	}

	public XFragmentPagerAdapter(XActivity activity,
			List<FragmentParam> fragments) {
		super(activity.getSupportFragmentManager());
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		FragmentParam param = mFragments.get(getPosition(position));
		try {

			if (param.fragment == null) {
				param.fragment = (XFragment) param.cls.newInstance();
			}
			param.fragment.setData(param.data);
			return param.fragment;

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
		public XFragment fragment;
		public Class<? extends XFragment> cls;
		public Serializable data;

		public boolean isBack = false;
		public String fragmentTag;

		public boolean isEmpty() {

			return cls == null && fragment == null;
		}

		public void setFragmentTag(String fragmentTag) {
			this.fragmentTag = fragmentTag;
		}

		public String getFragmentTag() {
			return fragmentTag;
		}

	}

	public XFragmentPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {

		return isInfiniteLoop ? position % ListUtils.getSize(mFragments)
				: position;
	}
}
