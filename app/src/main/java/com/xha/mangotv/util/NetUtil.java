package com.xha.mangotv.util;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
/**
 * 
 * 网路有关的工具类
 * 
 * @author dell-dell
 *
 */
public class NetUtil {
	/**
	 * �?查用户的网络:是否有网�?
	 */
	public static boolean checkNet(Context context) {
		// 判断：WIFI链接
		boolean isWIFI = isWIFIConnection(context);
		// 判断：Mobile链接
		boolean isMOBILE = isMOBILEConnection(context);

		// 如果Mobile在链接，判断是哪个APN被�?�中�?
		// if (isMOBILE) {
		// // APN被�?�中,的代理信息是否有内容，如果有wap方式
		// readAPN(context);// 判断是哪个APN被�?�中�?
		// }

		if (!isWIFI && !isMOBILE) {
			return false;
		}

		return true;
	}

	/**
	 * APN被�?�中,的代理信息是否有内容，如果有wap方式
	 * 
	 * @param context
	 */
	private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权�?

		// 操作联系人类�?
		ContentResolver resolver = context.getContentResolver();
		// 判断是哪个APN被�?�中�?
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null,
				null);

		if (cursor != null && cursor.moveToFirst()) {
			GlobalParams.PROXY = cursor.getString(cursor
					.getColumnIndex("proxy"));
			GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port"));
		}

	}

	/**
	 * 判断：Mobile链接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断：WIFI链接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
