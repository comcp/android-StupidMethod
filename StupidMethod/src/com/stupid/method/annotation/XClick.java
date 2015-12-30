package com.stupid.method.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.support.annotation.IntDef;

@Documented
@IntDef({ XClick.NAVIGATION_MODE_STANDARD, XClick.NAVIGATION_MODE_LIST,
		XClick.NAVIGATION_MODE_TABS })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface XClick {
	public static final int NAVIGATION_MODE_STANDARD = 0;
	public static final int NAVIGATION_MODE_LIST = 1;
	public static final int NAVIGATION_MODE_TABS = 2;

	public String target() default "";
}
