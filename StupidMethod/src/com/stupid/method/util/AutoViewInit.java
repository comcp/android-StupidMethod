package com.stupid.method.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;

import com.stupid.method.annotation.XViewById;

public class AutoViewInit {

	public static void initObjectView(Object obj, View root) {

		initObject(obj, root, root.getContext(), root.getResources());

	}

	public static void initActivityView(Activity activity) {

		initObject(activity, activity, activity, activity.getResources());

	}

	public static void initFragmentView(Fragment fragment, View root) {

		initObject(fragment, root, root.getContext(), root.getResources());
	}

	private static void initObject(Object target, Object dataFrom,
			Context context, Resources res) {
		ObjectUtils.requireNonNull(dataFrom);
		ObjectUtils.requireNonNull(target);
		ObjectUtils.requireNonNull(res);

		Class<?> clz = target.getClass();
		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {
			XViewById xid = field.getAnnotation(XViewById.class);
			if (null != xid) {
				int id = xid.id();
				if (id == -1) {
					id = res.getIdentifier(field.getName(), "id",
							context.getPackageName());
				}
				View view = dataFrom instanceof View ? ((View) dataFrom)
						.findViewById(id)
						: dataFrom instanceof Activity ? ((Activity) dataFrom)
								.findViewById(id) : null;
				if (view != null) {
					try {
						field.setAccessible(true);
						field.set(target, view);
						XLog.d(clz.getSimpleName(), String.format("设置%s.%s",
								clz.getSimpleName(), field.getName()));

					} catch (IllegalAccessException e) {
						XLog.e(clz.getSimpleName(), "", e);
					} catch (IllegalArgumentException e) {
						XLog.e(clz.getSimpleName(), "", e);
					}
				} else {

					XLog.i(clz.getSimpleName(), String.format(
							" id %s 在 %s 未找到 ", field.getName(),
							dataFrom.toString()));
				}
			}

		}
	}
}
