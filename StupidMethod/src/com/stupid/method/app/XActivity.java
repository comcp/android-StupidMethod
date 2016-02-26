package com.stupid.method.app;

import java.io.Serializable;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.stupid.method.BuildConfig;
import com.stupid.method.adapter.XFragmentPagerAdapter.FragmentParam;
import com.stupid.method.app.impl.WaitDialog;
import com.stupid.method.util.XLog;

/**
 * 我也不知道为啥就用了X作为类的开头<br>
 * 可能是当时心里比较浮躁吧---叉
 * 
 * ***/
abstract public class XActivity extends FragmentActivity implements IXActivity {
	private static String tag = "XActivity";
	private static boolean DEBUG = BuildConfig.DEBUG;
	private static long DOUBLE_CLICK_MENU = -1;
	private AQuery $;
	private XFragment mCurrentFragment;

	/**
	 * 设置屏幕常亮不锁屏开关
	 * 
	 * @param on
	 */
	public void setKeepScreen(boolean on) {
		if (on) {
			// 屏幕不锁屏, 常亮
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			// 屏幕自动锁屏
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public XActivity getContent() {

		return this;
	}

	@Override
	public AQuery getAQuery() {
		if ($ == null)
			$ = new AQuery(this);
		return $;
	}

	public void showToast(final String text, final int duration) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO 暂时先这么写着;
				Toast.makeText(getContent(), text, duration).show();
			}
		});

	}

	/**
	 * 获得状态栏高度
	 * */
	public int getStatusBarHeight() {
		int result = -1;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public void showToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	@Override
	public void showToast(int text) {
		showToast(getResources().getString(text));
	}

	@Override
	public void showToast(int text, int duration) {
		showToast(getResources().getString(text), duration);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (AppConfig.DEBUG && keyCode == KeyEvent.KEYCODE_MENU) {
			// 为了方便确认是那个类
			if (System.currentTimeMillis() - DOUBLE_CLICK_MENU < 500)
				showToast(this.getClass().getName());
			DOUBLE_CLICK_MENU = System.currentTimeMillis();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	/** 隐藏键盘 **/
	public void hideKeyboardForCurrentFocus() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	/** 显示键盘 **/
	public void showKeyboardAtView(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager
				.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	/** 退出全屏 **/
	protected void exitFullScreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	protected String getFragmentTag(FragmentParam param) {
		StringBuilder sb;
		if (param.getFragmentTag() == null) {

			if (param.cls != null)
				sb = new StringBuilder(param.cls.toString());
			else
				sb = new StringBuilder(param.fragment.toString());
			param.setFragmentTag(sb.toString());
		} else {
			sb = new StringBuilder(param.getFragmentTag());
		}
		return sb.toString();
	}

	public FragmentParam pushFragmentToBackStack(
			Class<? extends XFragment> cls, Serializable data) {

		return pushFragment(cls, data, true);
	}

	/**
	 * 
	 * 添加fragment
	 * 
	 * @param cls
	 *            {@link XFragment};
	 * @param data
	 * @param isBack
	 *            按返回键是否销毁
	 * @return
	 * **/
	public FragmentParam pushFragment(Class<? extends XFragment> cls,
			Serializable data, boolean isBack) {
		FragmentParam param = new FragmentParam();
		param.cls = cls;
		param.data = data;
		param.isBack = isBack;
		return addFragmentToLayout(getLayoutId(), param);
	}

	/***
	 * 添加到Fragment堆栈里
	 **/
	public FragmentParam addFragment(FragmentParam param) {
		return addFragmentToLayout(getLayoutId(), param);
	}

	private FragmentParam addFragmentToLayout(int layoutId, FragmentParam param) {
		Class<?> cls = param.cls;
		if (param.isEmpty()) {
			XLog.d(tag, "param 为空.");
			return null;
		}
		try {
			String fragmentTag = getFragmentTag(param);
			FragmentManager fm = getSupportFragmentManager();

			XFragment fragment = (XFragment) fm.findFragmentByTag(fragmentTag);

			if (fragment == null) {
				if (DEBUG)
					XLog.d(tag, "Fragment 没有生成");
				if (param.fragment == null) {
					fragment = (XFragment) cls.newInstance();
					param.fragment = fragment;
				} else
					fragment = param.fragment;

			}
			FragmentTransaction ft = fm.beginTransaction();
			XFragment oldFragment = mCurrentFragment;
			if (oldFragment != null && oldFragment != fragment) {

			} else {
				XLog.d(tag, "fragment 相等或者 oldFragment为空");

			}

			fragment.setData(param.data);

			if (fragment.isAdded()) {
				if (DEBUG) {
					XLog.d(tag, String.format("%s 已存在,调用显示", fragmentTag));
				}
				ft.show(fragment);

			} else {
				if (DEBUG) {
					XLog.d(tag, String.format("添加一个<%s> 到视图里", fragmentTag));
				}
				ft.add(layoutId, fragment, fragmentTag);

			}

			mCurrentFragment = fragment;
			if (param.isBack)
				ft.addToBackStack(fragmentTag);
			ft.commitAllowingStateLoss();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return param;
	}

	protected XDialogFragment xdialog;
	public Class<? extends XDialogFragment> dialogClz = WaitDialog.class;

	@Override
	public XDialogFragment waitof(String msg) {
		return waitof(msg, true, 0);

	}

	@Override
	public XDialogFragment waitof(String msg, long timeout) {

		return waitof(msg, false, timeout);
	}

	@Override
	public XDialogFragment waitof(String msg, boolean cancel, long timeout) {

		if (xdialog == null)
			try {
				if (dialogClz != null)
					xdialog = dialogClz.newInstance();
				else {
					XLog.e(tag, "未设置xdialog Class");
					return null;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		if (xdialog == null) {
			XLog.e(tag, "xdialog未能成功创建");
			return null;
		}
		xdialog.setData(msg);

		if (xdialog.getDialog() == null)
			xdialog.show(this, false, timeout);
		else {
			if (xdialog.isShowing())
				xdialog.dismiss();

			xdialog.show(this, cancel, timeout);

		}
		return xdialog;
	}

	@Override
	public void finish() {
		if (xdialog != null && xdialog.isShowing())
			xdialog.dismiss();
		super.finish();
	}

	@Override
	public XDialogFragment waitof() {
		return waitof(null);

	}

	@Override
	public void waitEnd() {
		if (xdialog != null && xdialog.isShowing())
			xdialog.dismiss();

	}

	@Override
	public void onServerResult(int resultCode, String data, boolean state,
			int statusCode) {
	}

	public XActivity self() {

		return this;
	}

}
