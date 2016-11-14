package com.guosun.lovego.util;

import android.widget.Toast;

import com.guosun.lovego.LovegoApplication;

public class ToastUtils {
	private static Toast toast = null;

	/**
	 * 显示Toast (short)
	 * @param msg
	 */
	public static void ShowToast_short(String msg) {
		if (toast == null) {
			toast = Toast.makeText(LovegoApplication.getInstance(), msg,
					Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	/**
	 * 显示Toast (short)
	 * @param resId
     */
	public static void ShowToast_short(int resId) {
		if (toast == null) {
			toast = Toast.makeText(LovegoApplication.getInstance(), resId,
					Toast.LENGTH_SHORT);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}

	/**
	 * 显示Toast (long)
	 * @param msg
	 */
	public static void ShowToast_long(String msg) {
		if (toast == null) {
			toast = Toast.makeText(LovegoApplication.getInstance(), msg,
					Toast.LENGTH_LONG);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	/**
	 * 显示Toast (long)
	 * @param resId
	 */
	public static void ShowToast_long(int resId) {
		if (toast == null) {
			toast = Toast.makeText(LovegoApplication.getInstance(), resId,
					Toast.LENGTH_LONG);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}
}
