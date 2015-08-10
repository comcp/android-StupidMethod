package com.stupid.method.app.impl;

import android.os.Bundle;
import android.widget.TextView;

import com.stupid.method.R;
import com.stupid.method.app.XDialogFragment;

public class WaitDialog extends XDialogFragment {

	@Override
	public void initPager(Bundle savedInstanceState, Object data) {

		if (data == null)
			return;
		TextView msg = (TextView) findViewById(R.id.wait_dialog_tv);
		msg.setText(data.toString());
	}

	@Override
	public int getLayoutId() {
		return R.layout.wait_dialog_fragment;
	}

}
