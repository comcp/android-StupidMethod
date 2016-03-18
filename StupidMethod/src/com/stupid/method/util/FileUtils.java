package com.stupid.method.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class FileUtils {

	private static final String tag = "FileUtils";

	public static String join(String prefix, String... suffixs) {

		StringBuilder sb = new StringBuilder(prefix);

		for (String suffix : suffixs) {
			int prefixLength = sb.length();
			boolean haveSlash = (prefixLength > 0 && sb
					.charAt(prefixLength - 1) == File.separatorChar);
			if (!haveSlash) {
				haveSlash = (suffix.length() > 0 && suffix.charAt(0) == File.separatorChar);
			}
			if (haveSlash)
				sb.append(suffix);
			else
				sb.append(File.separatorChar).append(suffix);
		}

		return sb.toString();
	}

	public static String getExtFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(dotPosition + 1, filename.length());
		}
		return "";
	}

	public static String getNameFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(0, dotPosition);
		}
		return "";
	}

	public static String copyFile(String src, String dest) {
		File file = new File(src);
		if (!file.exists() || file.isDirectory()) {
			Log.v(tag, "copyFile: file not exist or is directory, " + src);
			return null;
		}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(file);
			File destPlace = new File(dest);
			if (!destPlace.exists()) {
				if (!destPlace.mkdirs())
					return null;
			}

			String destPath = join(dest, file.getName());
			File destFile = new File(destPath);
			int i = 1;
			while (destFile.exists()) {
				String destName = getNameFromFilename(file.getName()) + " "
						+ i++ + "." + getExtFromFilename(file.getName());
				destPath = join(dest, destName);
				destFile = new File(destPath);
			}

			if (!destFile.createNewFile())
				return null;

			fo = new FileOutputStream(destFile);
			int count = 1024 * 10;
			byte[] buffer = new byte[count];
			int read = 0;
			while ((read = fi.read(buffer, 0, count)) != -1) {
				fo.write(buffer, 0, read);
			}

			return destPath;
		} catch (FileNotFoundException e) {
			XLog.e(tag, "copyFile: file not found, " + src);
			e.printStackTrace();
		} catch (IOException e) {
			XLog.e(tag, "copyFile: " + e.toString());
		} finally {
			try {
				if (fi != null)
					fi.close();
				if (fo != null)
					fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}
}
