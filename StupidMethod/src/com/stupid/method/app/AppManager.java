package com.stupid.method.app;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.stupid.method.util.FileUtils;
import com.stupid.method.util.SharedPreferencesHelper;
import com.stupid.method.util.XLog;

public class AppManager extends Application {

	public static final String tag = "AppManager";
	public static final String DB_NAME = "TmpData";
	// app 独立命名空间（文件存储等等）
	// public static String NAMESPACE = null;
	// public static String DIR_FILE = "%s%s/file" + File.separator;
	// public static String DIR_PICS = "%s%s/pics" + File.separator;
	// public static String DIR_THUMB = "%s%s/thumb" + File.separator;
	// public static String SIR_MEDIAS = "%s%s/smedias" + File.separator;
	// public static String DIR_TEMP = "%s%s/stemp" + File.separator;
	// public static String DIR_LOGS = "%s%s/logs" + File.separator;

	private static AppManager instance = null;

	public synchronized static AppManager getInstance() {

		return instance;
	}

	private Point screenSize;

	private int versionCode = -1;
	private ExecutorService mExecutor = Executors.newFixedThreadPool(3);

	private Handler mHandler = new Handler();

	File mRoot;

	public void execute(Runnable command) {
		mExecutor.execute(command);
	}

	public File getAppCacheDir() {
		initDir();
		return mRoot;
	}

	public File getAppDataBaseDir() {
		File file = new File(getAppCacheDir(), "DBS");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public File getAppFileDir() {
		File file = new File(getAppCacheDir(), "FILES");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;

	}

	public File getAppImgDir() {
		File file = new File(getAppCacheDir(), "IMGS");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;

	}

	public File getAppLogDir() {
		File file = new File(getAppCacheDir(), "LOGS");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;

	}

	public ExecutorService getExecutorService() {
		return mExecutor;
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

	public String getNameSpace() {

		return getPackageName();
	}

	public File getOrCreateDataBase(String dbname) throws IOException {

		File file = new File(getAppCacheDir(), dbname);
		if (file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public File getPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		// 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录

		} else {

			sdDir = getExternalCacheDir();
		}
		if (sdDir != null)
			return sdDir;
		else
			return getCacheDir();
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

	public SharedPreferencesHelper getSharedPreferences() {

		return SharedPreferencesHelper.getCache(this, "AppData");
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

	public boolean getXmlBoolean(String key, boolean defValue) {
		return getSharedPreferences().getBoolean(key, defValue);
	}

	public int getXmlInt(String key, int defValue) {
		return getSharedPreferences().getInt(key, defValue);
	}

	public <T> T getXmlJSON(Class<T> clazz) {
		return getSharedPreferences().getJSON(clazz);

	}

	public <T> T getXmlJSON(String key, Class<T> clazz) {
		try {
			return getSharedPreferences().getJSON(key, clazz);
		} catch (Exception e) {
			return null;
		}

	}

	public String getXmlString(String key) {

		return getSharedPreferences().getString(key, "");
	}

	public String getXmlString(String key, String defValue) {

		return getSharedPreferences().getString(key, defValue);
	}

	private synchronized void initDir() {

		if (mRoot == null) {
			File path = getPath();
			File root = new File(FileUtils.join(path.getAbsolutePath(),
					"Android", "Data", getNameSpace()));
			if (!root.exists()) {
				root.mkdirs();
			}
			try {
				File testRW = new File(root, "test");
				testRW.createNewFile();
				testRW.delete();
			} catch (IOException e) {
				root = getCacheDir();
			} finally {

			}
			mRoot = root;
		}
		// DIR_FILE = String.format(DIR_FILE, root, NAMESPACE);
		// DIR_PICS = String.format(DIR_PICS, root, NAMESPACE);
		// DIR_THUMB = String.format(DIR_THUMB, root, NAMESPACE);
		// SIR_MEDIAS = String.format(SIR_MEDIAS, root, NAMESPACE);
		// DIR_TEMP = String.format(DIR_TEMP, root, NAMESPACE);
		// DIR_LOGS = String.format(DIR_LOGS, root, NAMESPACE);
		// File file = new File(DIR_FILE);
		// if (!file.exists())
		// file.mkdirs();
		// file = new File(DIR_LOGS);
		// if (!file.exists())
		// file.mkdirs();
		//
		// file = new File(DIR_PICS);
		// if (!file.exists())
		// file.mkdirs();
		// file = new File(DIR_THUMB);
		// if (!file.exists())
		// file.mkdirs();
		// file = new File(SIR_MEDIAS);
		// if (!file.exists())
		// file.mkdirs();
		// file = new File(DIR_TEMP);
		// if (!file.exists())
		// file.mkdirs();

	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	@Override
	public void onLowMemory() {
		// clear all memory cached images when system is in low memory
		// note that you can configure the max image cache count, see
		// CONFIGURATION
		com.androidquery.callback.BitmapAjaxCallback.clearCache();
	}

	public final boolean post(Runnable r) {

		return mHandler.post(r);
	}

	public AppManager setXmlBoolean(String key, boolean value) {
		getSharedPreferences().putBoolean(key, value);
		return this;
	}

	public AppManager setXmlInt(String key, int value) {

		getSharedPreferences().putInt(key, value);
		return this;

	}

	public AppManager setXmlJSON(String key, Object value) {
		getSharedPreferences().putJSON(key, value);
		return this;
	}

	public AppManager setXmlString(String key, String value) {
		getSharedPreferences().putString(key, value);
		return this;
	}

}
