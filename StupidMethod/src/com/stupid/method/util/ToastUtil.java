package com.neusoft.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ToastUtil {
    static WeakReference<Toast> reference = null;

    private static Toast getToast() {
        return reference == null ? null : reference.get();
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, CharSequence text,
            int duration) {
        showToast(context, text, duration, Gravity.BOTTOM);
    }

    public static void showToast(Context context, CharSequence text,
            int duration, int gravity) {

        Toast toast = getToast();

        if (toast == null)
            toast = Toast.makeText(context, text, duration);
        else {
            toast.cancel();
            toast = Toast.makeText(context, text, duration);
        }

        toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
        reference = new WeakReference<Toast>(toast);

        toast.show();

    }

    public static void showToast(Context context, int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getResources().getString(resId), duration,
                Gravity.BOTTOM);
    }

    public static void showToast(Context context, View view) {

        showToast(context, view, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showToast(Context context, View view, int duration) {

        showToast(context, view, duration, Gravity.BOTTOM);
    }

    public static void showToast(Context context, View view, int duration,
            int gravity) {

        Toast toast = getToast();

        if (toast == null)
            toast = new Toast(context);
        else {
            toast.cancel();
            toast = new Toast(context);
        }

        toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
        reference = new WeakReference<Toast>(toast);
        toast.setView(view);
        toast.show();
    }
}
