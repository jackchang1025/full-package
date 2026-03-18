package com.vendor.rat.keepalive.thread;

import android.util.Log;

/**
 * Vendor: com.guard.wallet.thread.c
 * Global uncaught exception handler singleton.
 */
public final class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "GlobalExceptionHandler";
    private static volatile GlobalExceptionHandler instance;
    private Thread.UncaughtExceptionHandler defaultHandler;

    public static GlobalExceptionHandler getInstance() {
        if (instance == null) {
            synchronized (GlobalExceptionHandler.class) {
                if (instance == null) {
                    synchronized (GlobalExceptionHandler.class) {
                        instance = new GlobalExceptionHandler();
                    }
                }
            }
        }
        return instance;
    }

    public Thread.UncaughtExceptionHandler getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(Thread.UncaughtExceptionHandler handler) {
        this.defaultHandler = handler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable th) {
        boolean handled = false;
        if (th != null) {
            // ADAPT: vendor calls a1.q.t("GlobalExceptionHandler", th)
            Log.e(TAG, "Uncaught exception", th);
            handled = true;
        }
        if (handled) {
            Log.d(TAG, "全局异常已捕获");
            return;
        }
        if (defaultHandler != null) {
            defaultHandler.uncaughtException(thread, th);
        }
    }
}
