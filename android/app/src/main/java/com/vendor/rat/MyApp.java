package com.vendor.rat;

import android.app.Application;

/**
 * Application 入口类
 * 初始化顺序: attachBaseContext → onCreate → MainApplication.init
 */
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(base);
        // 全局异常处理
        com.vendor.rat.exception.GlobalExceptionHandler.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化主应用管理器
        MainApplication.init(this);
    }
}
