package com.stupid.method.app;

import android.os.Bundle;
import android.view.View;

public interface IXFragment extends IXActivity {
	/**
	 * 在系统调用 @link XFragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, Bundle) 后调用,
	 **/
	void initPager(Bundle savedInstanceState, Object data);

	void setData(Object object);

	View findViewById(int id);

	void onLeave();

}