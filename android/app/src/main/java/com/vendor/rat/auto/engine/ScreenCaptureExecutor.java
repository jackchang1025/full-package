package com.vendor.rat.auto.engine;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 屏幕截图执行器
 *
 * Vendor: o/c0.java (30 行)
 * 功能: 管理屏幕截图/事件捕获的线程池 + 事件类型判断
 *
 * 字段对齐:
 *   f612a → executorService (FixedThreadPool(2))
 *   b     → capturing (AtomicBoolean)
 *   c     → eventProcessing (AtomicBoolean)
 *   (匿名) → AtomicLong(0) (构造函数中创建但未赋值)
 *
 * 方法对齐:
 *   a(int) → isClickableEventType(int)
 *   b(int) → isScrollableEventType(int)
 */
public final class ScreenCaptureExecutor {

    // ADAPT: f612a → executorService, 固定2线程池用于截图任务
    public final ExecutorService executorService = Executors.newFixedThreadPool(2);

    // ADAPT: b → capturing, 是否正在截图
    public final AtomicBoolean capturing;

    // ADAPT: c → eventProcessing, 是否正在处理事件
    public final AtomicBoolean eventProcessing;

    public ScreenCaptureExecutor() {
        // ADAPT: vendor 创建了 AtomicLong(0) 但未赋值给字段
        new AtomicLong(0L);
        this.capturing = new AtomicBoolean(false);
        this.eventProcessing = new AtomicBoolean(false);
    }

    /**
     * 判断是否为可点击类事件类型
     * ADAPT: a(int) → isClickableEventType
     * TYPE_VIEW_CLICKED=1, TYPE_VIEW_LONG_CLICKED=2, TYPE_TOUCH_INTERACTION_START=8388608, TYPE_VIEW_FOCUSED=8
     */
    public static boolean isClickableEventType(int eventType) {
        return Objects.equals(Integer.valueOf(eventType), 1)
                || Objects.equals(Integer.valueOf(eventType), 2)
                || Objects.equals(Integer.valueOf(eventType), 8388608)
                || Objects.equals(Integer.valueOf(eventType), 8);
    }

    /**
     * 判断是否为可滚动类事件类型
     * ADAPT: b(int) → isScrollableEventType
     * TYPE_VIEW_SCROLLED=4096, TYPE_WINDOW_STATE_CHANGED=32, TYPE_WINDOWS_CHANGED=4,
     * TYPE_WINDOW_CONTENT_CHANGED=2048, TYPE_VIEW_SELECTED=4
     */
    public static boolean isScrollableEventType(int eventType) {
        return Objects.equals(Integer.valueOf(eventType), 2048)
                || Objects.equals(Integer.valueOf(eventType), 32)
                || Objects.equals(Integer.valueOf(eventType), 16384)
                || Objects.equals(Integer.valueOf(eventType), 4096)
                || Objects.equals(Integer.valueOf(eventType), 4);
    }
}
