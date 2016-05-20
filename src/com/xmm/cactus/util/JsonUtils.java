package com.xmm.cactus.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

/**
 * JSON处理				
 * @author  DENG_MING_HAI
 * @date 	2016年4月15日
 */
public class JsonUtils {

	private static final String TAG = JsonUtils.class.getSimpleName();

	/**
	 * 将json转换成对象
	 * 
	 * @param json
	 *            传入的Gson对象
	 * @param valueType
	 *            要转换的对象类名
	 * @return 转换后的对象
	 */
	public static <T> T json2Object(String json, Class<T> valueType) {
		T bean = null;
		try {
			Gson gson = new Gson();
			bean = gson.fromJson(json, valueType);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}

		return bean;
	}

	/**
	 * 将json转换成对象
	 * 
	 * @param json
	 *            传入的Gson对象
	 * @param valueType
	 *            要转换的类型名
	 * @return 转换后的对象
	 */
	public static <T> T json2Object(String json, Type classOfT) {
		Gson gson = new Gson();
		return gson.fromJson(json, classOfT);
	}

	/**
	 * 将对象转换成jsonString
	 * 
	 * @param valueType
	 *            要传化的对象
	 * @return json字符串
	 */
	public static String object2Json(Object valueType) {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(valueType);
		return json;
	}
}
