package com.vendor.rat.utils;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * SafeCall.get() / SafeCall.run() 异常兜底测试
 *
 * 纯 JVM 测试，无需 Android 框架
 * (Log.e 在纯 JVM 下由 Robolectric shadow 处理)
 */
public class SafeCallTest {

    private static final String TAG = "SafeCallTest";

    // ============ get() ============

    @Test
    public void get_success_returnsResult() {
        String result = SafeCall.get(TAG, () -> "hello", "default");
        assertEquals("hello", result);
    }

    @Test
    public void get_exception_returnsDefault() {
        String result = SafeCall.get(TAG, () -> {
            throw new Exception("boom");
        }, "fallback");
        assertEquals("fallback", result);
    }

    @Test
    public void get_nullDefault_returnsNull() {
        String result = SafeCall.get(TAG, () -> {
            throw new Exception("boom");
        }, null);
        assertNull(result);
    }

    @Test
    public void get_runtimeException_returnsDefault() {
        int result = SafeCall.get(TAG, () -> {
            throw new IllegalStateException("runtime boom");
        }, -1);
        assertEquals(-1, result);
    }

    @Test
    public void get_actionReturnsNull_returnsNull() {
        // action 返回 null 时不应被 defaultValue 覆盖
        String result = SafeCall.get(TAG, () -> null, "default");
        assertNull(result);
    }

    // ============ run() ============

    @Test
    public void run_success_executesAction() {
        AtomicBoolean executed = new AtomicBoolean(false);
        SafeCall.run(TAG, () -> executed.set(true));
        assertTrue(executed.get());
    }

    @Test
    public void run_exception_doesNotThrow() {
        // Runnable.run() 不能抛 checked exception，用包装绕过
        SafeCall.run(TAG, () -> {
            throw new RuntimeException(new Exception("boom"));
        });
        // 到达此行即证明未抛出
    }

    @Test
    public void run_runtimeException_doesNotThrow() {
        SafeCall.run(TAG, () -> {
            throw new NullPointerException("npe");
        });
        // 到达此行即证明未抛出
    }
}
