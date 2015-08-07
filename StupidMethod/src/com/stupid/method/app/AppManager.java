package com.stupid.method.app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.androidquery.callback.BitmapAjaxCallback;
import com.stupid.method.db.bean.TmpData;
import com.stupid.method.db.dao.DaoMaster;
import com.stupid.method.db.dao.DaoSession;
import com.stupid.method.util.XLog;

public class AppManager extends Application {

	public static final String tag = "AppManager";
	public static final String DB_NAME = "TmpData";

	private static AppManager instance = null;
	private Point screenSize;
	private int versionCode = -1;
	private SharedPreferences sharedPreferences;
	private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

	}

	@Override
	public void onTerminate() {
		super.onTerminate();

	}

	public synchronized static AppManager getInstance() {

		return instance;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public int getVersionCode() {

		if (versionCode == -1) {
			PackageManager packageManager = this.getApplicationContext()
					.getPackageManager();
			/*
			 * getInstalledApplications 返回当前设备上安装的应用包集合
			 * ApplicationInfo对应着androidManifest
			 * .xml中的application标签。通过它可以获取该application对应的信息
			 */

			PackageInfo pinfo;
			try {
				pinfo = packageManager.getPackageInfo(this
						.getApplicationContext().getPackageName(),
						PackageManager.GET_CONFIGURATIONS);
				versionCode = pinfo.versionCode;

			} catch (NameNotFoundException e) {

				e.printStackTrace();
			} finally {
				pinfo = null;

			}
		}
		return versionCode;
	}

	/**
	 * @return the screenSize point( x:width y:height)
	 */
	public Point getScreenSize() {

		if (screenSize == null) {
			WindowManager wm = (WindowManager) this
					.getSystemService(Context.WINDOW_SERVICE);

			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			screenSize = new Point(dm.widthPixels, dm.heightPixels);
		}
		return screenSize;
	}

	/**
	 * DaoMaster
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
					DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	/**
	 * @DaoSession
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}

	public void setTmpData(String key, String value) {

		getDaoSession(this).getTmpDataDao().deleteByKey(key);
		getDaoSession(this).getTmpDataDao().insert(
				new TmpData(key, value, new Date()));

	}

	public TmpData getTmpData(String key) {

		List<TmpData> l = getDaoSession(this).getTmpDataDao().queryRaw(
				" where key=?", key);

		return l.size() > 0 ? l.get(0) : null;

	}

	public List<TmpData> getTmpDataAll() {

		List<TmpData> l = getDaoSession(this).getTmpDataDao().queryBuilder()
				.list();

		return l;

	}

	@Override
	public void onLowMemory() {
		// clear all memory cached images when system is in low memory
		// note that you can configure the max image cache count, see
		// CONFIGURATION
		BitmapAjaxCallback.clearCache();
	}

	/**
	 * get wifi mac address
	 * 
	 * @need "android.permission.ACCESS_WIFI_STATE" in you manifest.xml
	 *       uses-permission
	 * **/
	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * get IP
	 * 
	 * @need "android.permission.ACCESS_WIFI_STATE" in you manifest.xml
	 *       uses-permission
	 * */
	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			XLog.e("WifiPreference IpAddress", ex.toString());
		}
		return "";
	}

	public String getLocalIpV4() {
		try {
			String ipv4;
			List<NetworkInterface> nilist = Collections.list(NetworkInterface
					.getNetworkInterfaces());
			for (NetworkInterface ni : nilist) {
				List<InetAddress> ialist = Collections.list(ni
						.getInetAddresses());
				for (InetAddress address : ialist) {
					if (!address.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ipv4 = address
									.getHostAddress())) {
						return ipv4;
					}
				}

			}

		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public void execute(Runnable command) {
		mExecutor.execute(command);
	}

	private String getKey(Object key) {

		if (key == null) {
			return "";
		} else if (key instanceof String || key instanceof StringBuilder
				|| key instanceof StringBuffer) {

			return key.toString();
		} else if (key instanceof Class) {
			return ((Class) key).getSimpleName();
		} else

			return key.getClass().getSimpleName();
	}

	public AppManager setXmlBoolean(Object key, boolean value) {
		getSharedPreferences().edit().putBoolean(getKey(key), value);
		return this;
	}

	public boolean getXmlBoolean(Object key, boolean defValue) {
		return getSharedPreferences().getBoolean(getKey(key), defValue);
	}

	public int getXmlInt(Object key, int defValue) {
		return getSharedPreferences().getInt(getKey(key), defValue);
	}

	public AppManager setXmlInt(Object key, int value) {

		getSharedPreferences().edit().putInt(getKey(key), value);
		return this;

	}

	public AppManager setXmlJSON(Object key, Object value) {

		getSharedPreferences()
				.edit()
				.putString(
						getKey(key),
						value instanceof String ? value.toString() : JSON
								.toJSONString(value)).commit();
		return this;
	}

	public <T> T getXmlJSON(Class<T> clazz) {
		return getXmlJSON(clazz, clazz);

	}

	public <T> T getXmlJSON(Object key, Class<T> clazz) {
		try {
			return JSON.parseObject(
					getSharedPreferences().getString(getKey(key), ""), clazz);
		} catch (Exception e) {
			return null;
		}

	}

	public AppManager setXmlString(Object key, String value) {

		getSharedPreferences().edit().putString(getKey(key), value).commit();
		return this;
	}

	public String getXmlString(Object key) {

		return this.getXmlString(key, "");
	}

	public String getXmlString(Object key, String defValue) {

		return getSharedPreferences().getString(getKey(key), defValue);
	}

	public SharedPreferences getSharedPreferences() {
		if (sharedPreferences == null)
			sharedPreferences = getSharedPreferences("appData",
					Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public void setSharedPreferences(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

}
