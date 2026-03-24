package com.vendor.rat.utils;

import android.util.Log;

/**
 * 函数式异常包装工具 — 消除 try-catch + Log.e + return default 的重复模式
 *
 * 自定义 Action 接口兼容 minSdk 21 (不依赖 java.util.function.Supplier)
 */
public final class SafeCall {

    /** 可抛异常的返回值操作 — minSdk 21 不支持 java.util.function.Supplier */
    public interface Action<T> {
        T call() throws Exception;
    }

    /**
     * 执行有返回值的操作，异常时返回默认值
     *
     * @param tag          日志 TAG
     * @param action       要执行的操作
     * @param defaultValue 异常时返回的默认值
     */
    public static <T> T get(String tag, Action<T> action, T defaultValue) {
        try {
            return action.call();
        } catch (Exception e) {
            Log.e(tag, "SafeCall error", e);
            return defaultValue;
        }
    }

    /**
     * 执行无返回值的操作，异常时仅记录日志
     *
     * @param tag    日志 TAG
     * @param action 要执行的操作
     */
    public static void run(String tag, Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            Log.e(tag, "SafeCall error", e);
        }
    }

    private SafeCall() {}
}
