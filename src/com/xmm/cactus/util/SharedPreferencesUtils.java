package com.xmm.cactus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.xmm.cactus.app.AppContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

/**
 * SharedPreferences 执行	
 * @author  DENG_MING_HAI
 * @date 	2016年4月15日
 */
public class SharedPreferencesUtils {

	private static final String SHAREDP_NAME = "com_cactus";
	public static final String KEY_LOGIN_TOKEN = "login_token";
	public static final String KEY_LOGIN_TYPE = "login_type";

	private static SharedPreferencesUtils instance = new SharedPreferencesUtils();

	/**
	 * 获取 Context对象 该SharedPreferences对象可以被同一应用程序下的其他组件共享
	 */
	private SharedPreferences getSharedPreferences() {
		return AppContext.getInstance().getSharedPreferences(SHAREDP_NAME, Context.MODE_PRIVATE);
	}

	public SharedPreferencesUtils() {
		
	}

	/**
	 * 获取实例
	 */
	public static SharedPreferencesUtils getInstance() {
		if (instance == null) {
			synchronized(instance){
				if(instance == null){
					instance = new SharedPreferencesUtils();
				}
			}
		}
		return instance;
	}

	/**
	 * int 获取数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            默认值
	 */
	public int getInt(String key, int def) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null)
				def = sp.getInt(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * int 存储数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            要存储的值
	 */
	public void putInt(String key, int val) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor e = sp.edit();
				e.putInt(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * long 获取数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            默认值
	 */
	public long getLong(String key, long def) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null)
				def = sp.getLong(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * long 存储数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            要存储的值
	 */
	public void putLong(String key, long val) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor e = sp.edit();
				e.putLong(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * String 获取数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            默认值
	 */
	public String getString(String key, String def) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null)
				def = sp.getString(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * String 存储数据
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            要存储的值
	 */
	public void putString(String key, String val) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor e = sp.edit();
				e.putString(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * boolean 数据获取
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            默认值
	 */
	public boolean getBoolean(String key, boolean def) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null)
				def = sp.getBoolean(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * boolean 数据存储
	 * 
	 * @param key
	 *            键值
	 * @param def
	 *            要存储的值
	 */
	public void putBoolean(String key, boolean val) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor e = sp.edit();
				e.putBoolean(key, val);
				e.commit();
			}
		} catch (Exception e) {
			Log.e("xmm", Log.getStackTraceString(e));
			e.printStackTrace();
		}
	}
	

	/**
	 * object 数据存储
	 * 
	 * @param key
	 *            键值
	 * @param object
	 *            要存储的值
	 */
	public void setData(String key, Object object) {
		SharedPreferences sp = getSharedPreferences();
		String data = JsonUtils.object2Json(object);
		Editor editor = sp.edit();
		editor.putString(key, data);
		editor.commit();

	}

	/**
	 * object 数据获取
	 * 
	 * @param key
	 *            键值
	 * @param object
	 *            要转换的值
	 */
	public <T> T getObjectData(String key, Class<T> valueType) {
		try {
			SharedPreferences sp = getSharedPreferences();
			String personBase64 = sp.getString(key, "");
			if (StringUtils.isBlank(personBase64)) {
				return null;
			}
			return JsonUtils.json2Object(personBase64, valueType);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Map 数据存储
	 * 
	 * @param key
	 *            键值
	 * @param object
	 *            要存储的值
	 */
	public void setDatasMap(String key, Map<String, Object> map) {
		if (map != null) {
			SharedPreferences sp = getSharedPreferences();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream;
			String recordBooleanData = null;
			try {
				objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(map);
				recordBooleanData = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Editor editor = sp.edit();
			editor.putString(key, recordBooleanData);
			editor.commit();
		}
	}

	/**
	 * Map 数据获取
	 * 
	 * @param  key
	 *            键值
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDatasMap(String key) {
		try {
			SharedPreferences sp = getSharedPreferences();
			String personBase64 = sp.getString(key, null);
			if (StringUtils.isBlank(personBase64)) {
				return null;
			}

			byte[] base64Bytes = personBase64.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object object = (Object) ois.readObject();
			return (Map<String, Object>) object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据Key移除数据
	 * 
	 * @param key
	 *            移除的key值
	 */
	public void remove(String key) {
		try {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor e = sp.edit();
				e.remove(key);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
