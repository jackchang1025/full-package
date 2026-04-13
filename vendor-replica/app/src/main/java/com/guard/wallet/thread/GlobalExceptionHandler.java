/**
 * vendor thread/c.java — GlobalExceptionHandler
 *
 * 全局未捕获异常处理器，双重检查单例模式。
 * 捕获异常后记录日志，回退到原始 UncaughtExceptionHandler。
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.util.Log;

public final class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static volatile GlobalExceptionHandler b;
    public Thread.UncaughtExceptionHandler a;

    public static GlobalExceptionHandler a() {
        if (b == null) {
            synchronized (GlobalExceptionHandler.class) {
                if (b == null) {
                    b = new GlobalExceptionHandler();
                }
            }
        }
        return b;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        boolean handled = false;
        if (throwable != null) {
            AppUtils.t("GlobalExceptionHandler", throwable);
            handled = true;
        }

        if (handled) {
            Log.d("GlobalExceptionHandler", "全局异常已捕获");
            return;
        }

        Thread.UncaughtExceptionHandler fallback = this.a;
        if (fallback != null) {
            fallback.uncaughtException(thread, throwable);
        }
    }
}
