package com.xmm.cactus.util;

import android.util.Log;

/**
 * 字符串处理类			
 * @author  DENG_MING_HAI
 * @date 	2016年4月15日
 */
public class StringUtils {
	
	public static final String TAG = StringUtils.class.getSimpleName();

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return boolean true空，false 非空
	 */
	public static boolean isBlank(String str) {
		if (str != null) {
			return "".equals(str.trim());
		}
		return true;
	}
	
	/**
	 * String转Long类型
	 */
	public static Long stringToLong(String str) {
		if(str==null||str.equals("")){
			return null;
		}
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}

}
