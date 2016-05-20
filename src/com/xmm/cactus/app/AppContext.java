package com.xmm.cactus.app;


import android.app.Application;

/**
 * Application 是单例（singleton）模式的一个类。	
 * 	 在ApplicationManifest.xml文件中记得添加配置  <application android:name="AppContext"/>
 * @author  DENG_MING_HAI
 * @date 	2016年4月18日
 */
public class AppContext extends Application {

    private static AppContext app;

    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }
}
