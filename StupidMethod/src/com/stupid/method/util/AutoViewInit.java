package com.stupid.method.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.webkit.WebView.FindListener;

import com.stupid.method.annotation.XClick;
import com.stupid.method.annotation.XClickCallMethod;
import com.stupid.method.annotation.XLongClick;
import com.stupid.method.annotation.XViewById;

public class AutoViewInit {
	private final static String ID = "id";

	public static void initObjectView(Object obj, View root) {

		initObject(obj, root, root.getContext(), root.getResources(), null,
				null);

	}

	public static void initObjectView(Object obj, View root,
			OnClickListener onclick, OnLongClickListener onlongClick) {
		initObject(obj, root, root.getContext(), root.getResources(), onclick,
				onlongClick);
	}

	public static void initObjectView(Object obj, View root,
			OnClickListener onclick) {
		initObject(obj, root, root.getContext(), root.getResources(), onclick,
				null);
	}

	public static void initObjectView(Object obj, View root,
			OnLongClickListener onlongClick) {

		initObject(obj, root, root.getContext(), root.getResources(), null,
				onlongClick);

	}

	public static void initActivityView(Activity activity,
			OnClickListener onclick, OnLongClickListener onlongClick) {
		initObject(activity, activity, activity, activity.getResources(),
				onclick, onlongClick);
	}

	public static void initActivityView(Activity activity,
			OnLongClickListener onlongClick) {
		initObject(activity, activity, activity, activity.getResources(), null,
				onlongClick);
	}

	public static void initActivityView(Activity activity,
			OnClickListener onclick) {
		initObject(activity, activity, activity, activity.getResources(),
				onclick, null);
	}

	public static void initActivityView(Activity activity) {

		initObject(activity, activity, activity, activity.getResources(), null,
				null);

	}

	public static void initFragmentView(Fragment fragment, View root) {

		initObject(fragment, root, root.getContext(), root.getResources(),
				null, null);
	}

	public static void initFragmentView(Fragment fragment, View root,
			OnClickListener onclick, OnLongClickListener onlongClick) {

		initObject(fragment, root, root.getContext(), root.getResources(),
				onclick, onlongClick);
	}

	public static void initFragmentView(Fragment fragment, View root,
			OnClickListener onclick) {

		initObject(fragment, root, root.getContext(), root.getResources(),
				onclick, null);
	}

	public static void initFragmentView(Fragment fragment, View root,
			OnLongClickListener onlongClick) {

		initObject(fragment, root, root.getContext(), root.getResources(),
				null, onlongClick);
	}

	private static View getView(Object dataFrom, int id) {

		if (dataFrom == null) {
			return null;
		} else if (dataFrom instanceof Activity) {
			return ((Activity) dataFrom).findViewById(id);
		} else if (dataFrom instanceof View) {
			return ((View) dataFrom).findViewById(id);
		} else {
			return null;
		}

	}

	private static void initObject(Object target, Object dataFrom,
			Context context, Resources res, OnClickListener onclick,
			OnLongClickListener onlongClick) {
		ObjectUtils.requireNonNull(dataFrom);
		ObjectUtils.requireNonNull(target);
		ObjectUtils.requireNonNull(res);

		Class<?> clz = target.getClass();
		Field[] fields = clz.getDeclaredFields();
		Method[] methods = clz.getDeclaredMethods();

		for (final Method method : methods) {
			XClickCallMethod clickMethod = method.getAnnotation(XClickCallMethod.class);
			if (clickMethod != null) {
				int[] ids = clickMethod.value();
				for (int id : ids) {
					View view = getView(dataFrom, id);
					if (view != null) {

						view.setOnClickListener(new OnClickListener() {
							Method met;
							Object receiver;

							@Override
							public void onClick(View v) {

								Class[] param = method.getParameterTypes();

								Object[] args = new Object[param.length];

								for (int i = 0; i < param.length; i++) {
									Class clz = param[i];

									if (View.class.isAssignableFrom(clz)) {
										args[i] = v;
									} else if (clz.isAssignableFrom(int.class)) {
										args[i] = v.getId();
									} else if (clz
											.isAssignableFrom(boolean.class)) {
										args[i] = v.isEnabled();
									} else
										args[i] = null;

								}
								invoke(met, receiver, args);

							}

							public OnClickListener set(Method met,
									Object receiver) {
								this.met = met;
								this.receiver = receiver;
								return this;
							}
						}.set(method, target));
					}
				}

			}

		}

		for (Field field : fields) {
			XViewById xid = field.getAnnotation(XViewById.class);
			if (null != xid) {
				int id = xid.value();
				if (id == -1) {
					id = res.getIdentifier(field.getName(), ID,
							context.getPackageName());
				}

				field.setAccessible(true);
				try {
					Object data = field.get(target);
					if (data != null) {
						XLog.d(clz.getSimpleName(),
								String.format(
										"[%s.%s]已被赋值,结束本次赋值,onclick,onlongclick 如果没有赋值也会停止赋值",
										clz.getSimpleName(), field.getName()));
						continue;
					}

				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}

				View view = getView(dataFrom, id);

				if (view != null) {
					try {

						field.set(target, view);
						XLog.d(clz.getSimpleName(), String.format("设置%s.%s",
								clz.getSimpleName(), field.getName()));

						if (onclick != null) {
							XClick xcl = field.getAnnotation(XClick.class);
							if (xcl != null) {
								view.setOnClickListener(onclick);
							}
						}
						if (onlongClick != null) {
							XLongClick xlc = field
									.getAnnotation(XLongClick.class);
							if (xlc != null) {
								view.setOnLongClickListener(onlongClick);
							}
						}

					} catch (IllegalAccessException e) {

						XLog.e(clz.getSimpleName(),
								String.format("初始化[%s.%s]发生异常",
										clz.getSimpleName(), field.getName()),
								e);

					} catch (IllegalArgumentException e) {
						XLog.e(clz.getSimpleName(),
								String.format("初始化[%s.%s]发生异常",
										clz.getSimpleName(), field.getName()),
								e);
					}
				} else {

					XLog.i(clz.getSimpleName(), String.format(
							" id %s 在 %s 未找到 ", field.getName(),
							dataFrom.toString()));
				}
			}

		}
	}

	public static Object invoke(Method met, Object receiver, Object... args) {
		met.setAccessible(true);

		try {
			return met.invoke(receiver, args);

		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		}

		return null;

	}

}
