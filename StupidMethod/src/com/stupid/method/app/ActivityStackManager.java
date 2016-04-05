package com.stupid.method.app;

import java.util.Stack;

import android.app.Activity;

public class ActivityStackManager {

	private final static Stack<Activity> activityStack = new Stack<Activity>();

	/**
	 * 压栈
	 * 
	 * @param activity
	 */
	public static void pushActivity(Activity activity) {
		activityStack.push(activity);
	}

	/**
	 * 出栈
	 * 
	 * @return
	 */
	public static void popActivity() {
		if (!activityStack.isEmpty()) {
			activityStack.pop();
		}
	}

	/**
	 * 出栈，移除某个activity
	 * 
	 * @param activity
	 * @return
	 */
	public static void popActivity(Activity activity) {
		activityStack.remove(activity);
	}

	/**
	 * 取当前activity
	 * 
	 * @return
	 */
	public static Activity getCurrentActivity() {
		if (activityStack.isEmpty()) {
			return null;
		}
		return activityStack.peek();
	}

	/**
	 * 关闭除指定activity以外的其他activity
	 * 
	 * @param activity
	 */
	public static void finishOtherActivity(Activity activity) {
		if (activity == null) {
			return;
		}
		for (Activity act : activityStack) {
			if (act != null && !act.equals(activity)) {
				act.finish();
			}

		}
		activityStack.clear();
		activityStack.push(activity);
	}

	/**
	 * 关闭指定activity
	 * 
	 * @param activity
	 */
	public static void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
		}
	}

	/**
	 * 根据类名关闭activity
	 * 
	 * @param class
	 */
	public static void finishActivityByClassName(Class<? extends Activity> obj) {

		if (obj == null)
			return;
		for (Activity act : activityStack) {

			if (act.getClass().getName().equals(obj.getName())) {
				if (activityStack.remove(act))
					act.finish();
			}

		}

	}

	/**
	 * 关闭当前activity
	 */
	public static void finishCurrentActivity() {
		finishActivity(getCurrentActivity());
	}

}
