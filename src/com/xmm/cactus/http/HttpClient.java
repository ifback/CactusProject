package com.xmm.cactus.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xmm.cactus.activity.R;
import com.xmm.cactus.app.AppContext;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiansj on 15/2/27.
 */
public class HttpClient {
	
	public static final int PAGE_SIZE = 30;
    private static final String UTF_8 = "UTF-8";
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain;");
    private static final OkHttpClient client = new OkHttpClient();
    
    static {
        client.setConnectTimeout(30, TimeUnit.SECONDS);
    }
    
    /**
     * get 方式请求
     * @param url  		链接地址
     * @param pairs		请求参数
     * @param httpResponseHandler	数据响应返回
     */
    public static void netGet(String url, List<BasicNameValuePair> pairs, final HttpResponseHandler httpResponseHandler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        if(pairs != null && pairs.size() > 0) {
            url = url + "?" + URLEncodedUtils.format(pairs, UTF_8);
        }
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                httpResponseHandler.sendSuccessMessage(response);
            }

            @Override
            public void onFailure(Request request, IOException e) {
                httpResponseHandler.sendFailureMessage(request, e);
            }
        });
    }

    /**
     * post 方式请求
     * @param url  		链接地址
     * @param pairs		请求参数
     * @param httpResponseHandler	数据响应返回
     */
    public static void netPost(String url, List<BasicNameValuePair> pairs, final HttpResponseHandler handler) {
        if (!isNetworkAvailable()) {
            Toast.makeText(AppContext.getInstance(), R.string.no_network_connection_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        String param = "";
        if(pairs != null && pairs.size() > 0) {
            param = URLEncodedUtils.format(pairs, UTF_8);
            url = url + "?" + param;
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE, param);
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) {
                handler.sendSuccessMessage(response);
            }

            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendFailureMessage(request, e);
            }
        });
    }
    
    /***
     * 网络是否可用
     */
    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("ConnectivityManager", e.getMessage());
        }
        return false;
    }
}
