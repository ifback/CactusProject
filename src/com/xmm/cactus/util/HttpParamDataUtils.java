package com.xmm.cactus.util;

import org.json.JSONException;
import org.json.JSONObject;


public class HttpParamDataUtils {
	
	/**
	 * 获取首页列表数据
	 * @return
	 */
	public static String requestHomeLists(int pno,String city,Double lat,Double lng,int pageSize){
		JSONObject root = new JSONObject();
		try {
			root.put("city", city);
			root.put("lat", lat);
			root.put("lng", lng);
			root.put("pno", pno);
			root.put("psize", pageSize);
			return root.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
