package com.stupid.method.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stupid.method.adapter.IXViewHolder;
import com.stupid.method.adapter.OnClickItemListener;
import com.stupid.method.adapter.OnLongClickItemListener;

public abstract class XHeadViewHolder implements IXViewHolder<Object> {
	private View mRoot;

	@Override
	public final View getView() {

		return mRoot;
	}

	public View findViewById(int id) {

		if (mRoot == null)
			return null;
		return mRoot.findViewById(id);
	}

	public abstract static class XHeadView extends XHeadViewHolder {

		@Override
		public XHeadViewHolder setTitleText(String txt) {

			return this;
		}

		@Override
		public XHeadViewHolder setTitleText(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setTitleImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackText(String txt) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackText(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuText(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuText(String txt) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuImg(int resId) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuEnabled(boolean enabled) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackEnabled(boolean enabled) {

			return this;
		}

		@Override
		public XHeadViewHolder setTitleEnabled(boolean enabled) {

			return this;
		}

		@Override
		public XHeadViewHolder setBackVisibility(int visibility) {

			return this;
		}

		@Override
		public XHeadViewHolder setTitleVisibility(int visibility) {

			return this;
		}

		@Override
		public XHeadViewHolder setMenuVisibility(int visibility) {

			return this;
		}

	}

	abstract public XHeadViewHolder setTitleText(String txt);

	abstract public XHeadViewHolder setTitleText(int resId);

	abstract public XHeadViewHolder setTitleImg(int resId);

	abstract public XHeadViewHolder setBackImg(int resId);

	abstract public XHeadViewHolder setBackText(String txt);

	abstract public XHeadViewHolder setBackText(int resId);

	abstract public XHeadViewHolder setMenuText(int resId);

	abstract public XHeadViewHolder setMenuText(String txt);

	abstract public XHeadViewHolder setMenuImg(int resId);

	abstract public XHeadViewHolder setMenuEnabled(boolean enabled);

	abstract public XHeadViewHolder setBackEnabled(boolean enabled);

	abstract public XHeadViewHolder setTitleEnabled(boolean enabled);

	abstract public XHeadViewHolder setBackVisibility(int visibility);

	abstract public XHeadViewHolder setTitleVisibility(int visibility);

	abstract public XHeadViewHolder setMenuVisibility(int visibility);

	@Override
	@Deprecated
	public View getView(Object arg0, int arg1, boolean arg2) {

		return null;
	}

	@Override
	public abstract void onCreate(Context contex);

	@Override
	public void onDestory(int arg0, int arg1) {
	}

	@Override
	public View setInflater(LayoutInflater arg0) {

		return mRoot = arg0.inflate(getLayoutId(), null);
	}

	@Override
	public void setOnClickItemListener(OnClickItemListener arg0) {
	}

	@Override
	public void setOnLongClickItemListener(OnLongClickItemListener arg0) {

	}

}
