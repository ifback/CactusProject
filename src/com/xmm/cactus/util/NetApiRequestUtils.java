package com.xmm.cactus.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import com.xmm.cactus.config.NetRequestUrl;
import com.xmm.cactus.http.HttpClient;
import com.xmm.cactus.http.HttpResponseHandler;

import android.content.Context;
import android.util.Base64;

/**
 * 网络接口请求
 * 
 * @author DENG_MING_HAI
 * @date 2016年4月21日
 */
public class NetApiRequestUtils {

	private static final String SHOP_RECOMMEND = "dpSearch.recommendShop"; //推荐商家
	/**
	 * 获取首页数据
	 */
	public static void getHomeDatas(Context c,String paramStr, HttpResponseHandler httpResponseHandler) {
		/*
		 * SearchParam obj=new SearchParam();
		 * Map<String, Object> params = new HashMap<String, Object>();
		 * params.put("city", obj.getCity());
		 * String paramStr = JSON.toJSONString(params);
		 **/

		paramStr = Base64.encodeToString(paramStr.getBytes(), Base64.DEFAULT);
		List<BasicNameValuePair> rq = new ArrayList<BasicNameValuePair>();
		rq.add(new BasicNameValuePair("m", SHOP_RECOMMEND));
		rq.add(new BasicNameValuePair("p", paramStr));

		String url = NetRequestUrl.GETHOMELISTURL + "?" + URLEncodedUtils.format(rq, "UTF-8");
		HttpClient.netGet(url, rq, httpResponseHandler);
	}
}
