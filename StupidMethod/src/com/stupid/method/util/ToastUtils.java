package com.stupid.method.util;

import android.view.Gravity;
import android.widget.Toast;

import com.stupid.method.app.AppManager;

public class ToastUtils {
	private static Toast mToast;

	private ToastUtils() {
		throw new AssertionError();
	}

	public static Toast showToast(CharSequence text, int duration, int gravity) {

		final Toast toast = Toast.makeText(AppManager.getInstance(), text,
				duration);
		switch (gravity) {
		case Gravity.TOP:
			toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0,
					toast.getYOffset());
			break;
		case Gravity.CENTER_VERTICAL:
			toast.setGravity(Gravity.CENTER_VERTICAL
					| Gravity.CENTER_HORIZONTAL, 0, toast.getYOffset());
			break;
		case Gravity.BOTTOM:
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
					toast.getYOffset());
			break;
		}

		if (AppUtils.isUIThread()) {
			if (mToast != null)
				mToast.cancel();
			mToast = toast;
			mToast.show();
		} else {

			AppManager.getInstance().post(new Runnable() {

				@Override
				public void run() {
					if (mToast != null)
						mToast.cancel();
					mToast = toast;
				}
			});
		}

		return mToast;

	}

	public static Toast showToast(CharSequence text) {

		return showToast(text, Toast.LENGTH_SHORT, 0);

	}

	public static Toast showToast(CharSequence text, int duration) {

		return showToast(text, duration, 0);

	}
}
