package com.guard.wallet.permission;

import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.entity.RootInActiveWindowResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.service.MyAccessibilityService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步任务分发 Runnable（vendor原始: e/a.java）。
 *
 * <p>tableswitch on {@link #taskType}:
 * <ul>
 *   <li>case 0: EnableSecureDelegate(o/k) 完成回调 — check q && r → I(result)</li>
 *   <li>case 1: EventDelegate(o/e) root window 获取 — get accessibility root, match package, set root</li>
 *   <li>case 2: ADB secure write 检查 — if !root && !adbCanWriteSecure → trigger guide</li>
 *   <li>default: BlockView 显示 — cast to BlockViewVO → helper.BlockViewManager.b(vo)</li>
 * </ul>
 */
public final class DelegateTaskRunner implements Runnable {
    public final int taskType;
    public final Object data;

    public DelegateTaskRunner(int taskType, Object data) {
        this.taskType = taskType;
        this.data = data;
    }

    @Override
    public final void run() {
        switch (this.taskType) {
            case 0: {
                // case 0: EnableSecureDelegate 完成回调
                com.guard.wallet.delegate.EnableSecureDelegate delegate = (com.guard.wallet.delegate.EnableSecureDelegate) this.data;
                boolean success = delegate.q && delegate.r;
                delegate.I(success);
                return;
            }
            case 1: {
                // case 1: EventDelegate root window 获取
                AccessibilityDelegate eventDelegate = (AccessibilityDelegate) this.data;
                eventDelegate.getClass(); // null check

                MyAccessibilityService service = MyAccessibilityService.P();
                RootInActiveWindowResult rootResult = service.R();

                // 循环等待 root window 最多 10 次
                AtomicInteger retryCount = new AtomicInteger(10);
                while (!rootResult.isComplete() && retryCount.decrementAndGet() > 0) {
                    com.guard.wallet.utils.SystemHelper.T0(1);
                    rootResult = service.R();
                }

                // 获取 root node
                android.view.accessibility.AccessibilityNodeInfo curRoot = rootResult.getCurRoot();
                UiObject rootObj;
                if (curRoot != null) {
                    rootObj = UiObject.createRoot(curRoot);
                } else {
                    rootObj = null;
                }

                // 如果 rootObj 非空且 package 匹配, 设置到 delegate
                if (rootObj != null) {
                    String pkgName = rootObj.packageName();
                    if (Objects.equals(eventDelegate.j.get(), pkgName)) {
                        eventDelegate.h.set(rootObj);
                    }
                }

                // 标记完成
                eventDelegate.i.set(true);
                return;
            }
            case 2: {
                // case 2: ADB secure write 检查
                if (com.guard.wallet.utils.SystemHelper.j()) {
                    // 已有 root, 不需要引导
                    return;
                }
                boolean adbCanWriteSecure;
                synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                    adbCanWriteSecure = com.guard.wallet.utils.SharedPrefsManager.e("adbCanWriteSecure");
                }
                if (!adbCanWriteSecure) {
                    // 触发引导流程
                    com.guard.wallet.utils.GuideDialogUtils.triggerGuideFlow();
                }
                return;
            }
            default: {
                // default: BlockView 显示
                BlockViewVO vo = (BlockViewVO) this.data;
                com.guard.wallet.helper.BlockViewManager.b(vo);
                return;
            }
        }
    }
}
