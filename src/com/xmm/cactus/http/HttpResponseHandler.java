package com.xmm.cactus.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
/**
 * 异步HTTP请求			
 * @author  DENG_MING_HAI
 * @date 	2016年4月19日
 */
public class HttpResponseHandler {
	
    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;

    private Handler handler;

    /**
     * Creates a new AsyncHttpResponseHandler
     */
    public HttpResponseHandler() {
        // Set up a handler to post events back to the correct thread if possible
    	// 译：如果可能的话，设置一个处理事件的处理程序返回到正确的线程
        if (Looper.myLooper() != null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    HttpResponseHandler.this.handleMessage(msg);
                }
            };
        }
    }

    //
    // Callbacks to be overridden, typically anonymously 
    // 译：回调被重写，通常匿名
    //

    /**
     * Fired when a request returns successfully, override to handle in your own code
     * 译：当请求返回成功时，请重写处理在您自己的代码中
     * @param content the body of the HTTP response from the server
     * 译：内容来自服务器的HTTP响应体
     */
    public void onSuccess(String content) { }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     * 译：当请求返回成功时，请重写处理在您自己的代码中
     * @param statusCode the status code of the response  译：状态码 的响应状态代码
     * @param headers    the headers of the HTTP response 译：头文件 的HTTP响应头
     * @param content    the body of the HTTP response from the server译：内容 来自服务器的HTTP响应体
     */
    public void onSuccess(int statusCode, Headers headers, String content) {
        onSuccess(statusCode, content);
    }

    public void onSuccess(int statusCode, String content) {
        onSuccess(content);
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     * 译：当请求不能被取消时调用时，连接问题或超时。因为网络可以在一个交换，可以远程服务器接受请求之前的失败
     */
    public void onFailure(Request request, IOException e) {
    	
    }


    //
    // 后台线程调用方法，通过Handler sendMessage把结果转到UI主线程
    //
    protected void sendSuccessMessage(Response response) {
        try {
            sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{new Integer(response.code()), response.headers(), response.body().string()}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sendFailureMessage(Request request, IOException e) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{e, request}));
    }

    //
    // 	Pre-processing of messages (in original calling thread, typically the UI thread)
    //	译：消息预处理（在原始的调用线程中，通常是用户界面线程
    //

    protected void handleSuccessMessage(int statusCode, Headers headers, String responseBody) {
        onSuccess(statusCode, headers, responseBody);
    }

    protected void handleFailureMessage(Request request, IOException e) {
        onFailure(request, e);
    }


    // Methods which emulate android's Handler and Message methods
    // 译：处理程序和消息方法的方法
    protected void handleMessage(Message msg) {
        Object[] response;
        switch (msg.what) {
            case SUCCESS_MESSAGE:
                response = (Object[]) msg.obj;
                handleSuccessMessage(((Integer) response[0]).intValue(), (Headers) response[1], (String) response[2]);
                break;
            case FAILURE_MESSAGE:
                response = (Object[]) msg.obj;
                handleFailureMessage((Request) response[1], (IOException) response[0]);
                break;
        }
    }

    protected void sendMessage(Message msg) {
        if (handler != null) {
            handler.sendMessage(msg);
        } else {
            handleMessage(msg);
        }
    }

    protected Message obtainMessage(int responseMessage, Object response) {
        Message msg = null;
        if (handler != null) {
            msg = this.handler.obtainMessage(responseMessage, response);
        } else {
            msg = Message.obtain();
            msg.what = responseMessage;
            msg.obj = response;
        }
        return msg;
    }
}
