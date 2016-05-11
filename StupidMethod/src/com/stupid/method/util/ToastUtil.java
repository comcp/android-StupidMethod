package com.stupid.method.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author wangx
 * 
 * **/
public class ToastUtil {
    static WeakReference<Toast> refToast = null;
    static WeakReference<Handler> refHandler = null;

    private static Toast getToast() {
        return refToast == null ? null : refToast.get();
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

        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, text, duration);

        toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
        refToast = new WeakReference<Toast>(toast);

        showToastOnUiThread(context, toast);

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
        refToast = new WeakReference<Toast>(toast);
        toast.setView(view);
        showToastOnUiThread(context, toast);
    }

    /**
     * 如果处于UI线程里,则直接显示,否则 放到ＵＩ线程里显示
     */
    public static void showToastOnUiThread(Context context, final Toast toast) {
        if (Looper.getMainLooper().getThread().getId() == Thread
                .currentThread().getId())
            toast.show();
        else {
            Handler handler = refHandler.get();
            if (handler == null)
                refHandler = new WeakReference<Handler>(handler = new Handler(
                        context.getMainLooper()));
            handler.post(new Runnable() {

                @Override
                public void run() {
                    toast.show();
                }
            });

        }
    }
}
