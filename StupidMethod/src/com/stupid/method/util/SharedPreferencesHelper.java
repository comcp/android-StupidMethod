package com.neusoft.util;

import java.util.Map;
import java.util.WeakHashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

/**
 * 
 * SharedPreferences 工具类
 * 
 * @author wangx 2016-04-27
 * 
 * 
 * **/
public class SharedPreferencesHelper {

    private static final WeakHashMap<Context, Map<String, SharedPreferencesHelper>> mCache = new WeakHashMap<Context, Map<String, SharedPreferencesHelper>>(
            3);

    /***
     * 线程安全
     * **/
    public static SharedPreferencesHelper getCache(Context context, String name) {
        Map<String, SharedPreferencesHelper> hashmap = null;
        hashmap = mCache.get(context);// 根据主键 取出 主键缓存,

        if (hashmap == null) {// 如果二级缓存是否为空,
            synchronized (mCache) {// 给一级缓存加线程锁
                hashmap = mCache.get(context);// 再取出二级缓存
                if (hashmap == null) {// 判断二级缓存是否为空,在极端条件下,这个地方很有可能会被其他线程初始化
                    hashmap = new WeakHashMap<String, SharedPreferencesHelper>(
                            2);
                    mCache.put(context, hashmap);
                }

            }

        }
        SharedPreferencesHelper result = null;
        result = hashmap.get(name);
        if (result == null) {
            synchronized (hashmap) {
                result = hashmap.get(name);
                if (result == null) {
                    result = new SharedPreferencesHelper(context, name);
                    hashmap.put(name, result);
                }
            }
        }

        return result;
    }

    public synchronized static void onLowMemory() {

        mCache.clear();
    }

    private static final String empty = "";
    private static final String tag = "SharePreferenceHelper";

    private static String getKey(Object key) {

        if (key == null) {
            return empty;
        } else if (key instanceof CharSequence) {

            return key.toString();

        } else if (key instanceof Class<?>) {

            return ((Class<?>) key).getName();
        } else {

            return key.getClass().getName();
        }
    }

    public SharedPreferencesHelper remove(String key) {
        edit().remove(key).commit();
        return this;

    }

    public SharedPreferencesHelper clear() {
        edit().clear().commit();
        return this;
    }

    final private SharedPreferences sp;

    public SharedPreferencesHelper(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferencesHelper(SharedPreferences sp) {
        this.sp = sp;
    }

    public Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    public <T> T getJSON(Class<T> clazz) {
        return getJSON(clazz, clazz);

    }

    public <T> T getJSON(Class<?> key, Class<T> clazz) {
        return getJSON(key.getName(), clazz);

    }

    public <T> T getJSON(String key, Class<T> clazz) {
        try {
            return JsonUtils.parseObject(
                    getSharedPreferences().getString((key), empty), clazz);
        } catch (Exception e) {

            Log.w(tag, "getJSON", e);

            return null;
        }

    }

    private SharedPreferences getSharedPreferences() {

        return sp;
    }

    private Editor edit() {
        return getSharedPreferences().edit();
    }

    public String getString(String key) {

        return this.getString(key, empty);
    }

    public String getString(String key, String defValue) {

        return getSharedPreferences().getString(key, defValue);
    }

    public SharedPreferencesHelper registerOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener listener) {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(
                listener);
        return this;
    }

    public SharedPreferencesHelper setBoolean(String key, boolean value) {
        edit().putBoolean(key, value).commit();
        return this;
    }

    public SharedPreferencesHelper setInt(String key, int value) {

        edit().putInt(key, value).commit();
        return this;

    }

    public SharedPreferencesHelper setJSON(Object value) {

        setJSON(value.getClass().getName(), value);
        return this;
    }

    public SharedPreferencesHelper setJSON(Class<?> key, Object value) {

        setJSON(key.getName(), value);
        return this;
    }

    public SharedPreferencesHelper setJSON(String key, Object value) {

        edit().putString(
                getKey(key),
                value instanceof CharSequence ? value.toString() : JsonUtils
                        .toJSONString(value)).commit();
        return this;
    }

    public SharedPreferencesHelper setLont(String key, long value) {
        edit().putLong(key, value).commit();
        return this;
    }

    public SharedPreferencesHelper setString(String key, String value) {

        edit().putString(key, value).commit();
        return this;
    }

    public SharedPreferencesHelper unregisterOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener listener) {
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                listener);
        return this;
    }

}
