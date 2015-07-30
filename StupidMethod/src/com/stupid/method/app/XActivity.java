package com.stupid.method.app;

import java.util.List;

import android.R;
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
import com.androidquery.callback.AjaxStatus;
import com.stupid.method.BuildConfig;
import com.stupid.method.adapter.XFragmentPagerAdapter.FragmentParam;
import com.stupid.method.androidquery.expansion.AQCallbackString;
import com.stupid.method.db.bean.TmpData;
import com.stupid.method.util.MapUtil;
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

	/**
	 * 从服务器请求数据
	 * 
	 * @回调:{@link XActivity#callback(String, String, AjaxStatus, int)}
	 * 
	 * **/
	public AQuery ajax(int CallBack_id, String url, MapUtil<String, ?> params) {

		return getAQuery().ajax(url,
				params == null ? null : params.getHashMap(), String.class,
				new AQCallbackString(CallBack_id, this));

	}

	public void showToast(String text, int duration) {
		Toast.makeText(this, text, duration).show();
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

	/**
	 * @param url
	 *            请求的URL
	 * @param callback_data
	 *            从服务器请求回来的数据
	 * @param status
	 *            aquery 原始数据
	 * @param CallBack_id
	 *            回调id
	 * ***/
	@Override
	public void callback(String url, String callback_data, AjaxStatus status,
			int CallBack_id) {
		if (AppConfig.DEBUG && status.getCode() != 200) {
			XLog.d(tag, status.getMessage());
			XLog.d(tag, status.getError());
			XLog.d(tag, url);

		}
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

	public void setTmpData(String key, String value) {

		AppManager.getInstance().setTmpData(key, value);

	}

	public TmpData getTmpData(String key) {

		return AppManager.getInstance().getTmpData(key);

	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	public List<TmpData> getTmpDataAll() {

		return AppManager.getInstance().getTmpDataAll();

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

	@Override
	public void waitof() {

	}

	@Override
	public void waitof(String msg) {
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

	public void pushFragmentToBackStack(Class<?> cls, Object data) {

		pushFragment(cls, data, true);
	}

	public void pushFragment(Class<?> cls, Object data, boolean isBack) {
		FragmentParam param = new FragmentParam();
		param.cls = cls;
		param.data = data;
		param.isBack = isBack;
		addFragmentToLayout(getLayoutId(), param);
	}

	public void addFragment(FragmentParam param) {
		addFragmentToLayout(getLayoutId(), param);
	}

	private void addFragmentToLayout(int layoutId, FragmentParam param) {
		Class<?> cls = param.cls;
		if (param.isEmpty()) {
			XLog.d(tag, "param 为空.");
			return;
		}
		try {
			String fragmentTag = getFragmentTag(param);
			FragmentManager fm = getSupportFragmentManager();

			XFragment fragment = (XFragment) fm.findFragmentByTag(fragmentTag);

			if (fragment == null) {
				if (DEBUG)
					XLog.d(tag, "Fragment 没有生成");
				if (param.fragment == null)
					fragment = (XFragment) cls.newInstance();
				else
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
	}
}
