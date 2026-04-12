package p000;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.work.impl.C0096a0;
import androidx.work.impl.background.systemalarm.ConstraintProxy$BatteryChargingProxy;
import androidx.work.impl.background.systemalarm.ConstraintProxy$BatteryNotLowProxy;
import androidx.work.impl.background.systemalarm.ConstraintProxy$NetworkStateProxy;
import androidx.work.impl.background.systemalarm.ConstraintProxy$StorageNotLowProxy;
import androidx.work.impl.background.systemalarm.ConstraintProxyUpdateReceiver;
import com.google.android.material.appbar.AppBarLayout;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import java.util.concurrent.ExecutionException;
import kotlin.Result;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lf */
/* loaded from: classes2.dex */
public final class RunnableC0818lf implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57901a0;

    /* renamed from: a1 */
    public Object f57902a1;

    /* renamed from: a2 */
    public Object f57903a2;

    /* renamed from: a3 */
    public Object f57904a3;

    public /* synthetic */ RunnableC0818lf(int i) {
        this.f57901a0 = i;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        Object objCall;
        OverScroller overScroller;
        boolean zBooleanValue = true;
        switch (this.f57901a0) {
            case 0:
                BroadcastReceiver.PendingResult pendingResult = (BroadcastReceiver.PendingResult) this.f57904a3;
                Context context = (Context) this.f57903a2;
                Intent intent = (Intent) this.f57902a1;
                try {
                    boolean booleanExtra = intent.getBooleanExtra("KEY_BATTERY_NOT_LOW_PROXY_ENABLED", false);
                    boolean booleanExtra2 = intent.getBooleanExtra("KEY_BATTERY_CHARGING_PROXY_ENABLED", false);
                    boolean booleanExtra3 = intent.getBooleanExtra("KEY_STORAGE_NOT_LOW_PROXY_ENABLED", false);
                    boolean booleanExtra4 = intent.getBooleanExtra("KEY_NETWORK_STATE_PROXY_ENABLED", false);
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    int i = ConstraintProxyUpdateReceiver.f45572a0;
                    c1351vvM214963a5.getClass();
                    xl0.m215196a0(context, ConstraintProxy$BatteryNotLowProxy.class, booleanExtra);
                    xl0.m215196a0(context, ConstraintProxy$BatteryChargingProxy.class, booleanExtra2);
                    xl0.m215196a0(context, ConstraintProxy$StorageNotLowProxy.class, booleanExtra3);
                    xl0.m215196a0(context, ConstraintProxy$NetworkStateProxy.class, booleanExtra4);
                    return;
                } finally {
                    pendingResult.finish();
                }
            case 1:
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.f57902a1;
                z30 z30Var = (z30) this.f57904a3;
                View view = (View) this.f57903a2;
                if (view == null || (overScroller = z30Var.f61431a3) == null) {
                    return;
                }
                if (overScroller.computeScrollOffset()) {
                    z30Var.m215339c2(coordinatorLayout, view, z30Var.f61431a3.getCurrY());
                    fa1.m212775b2(view, this);
                    return;
                }
                AppBarLayout appBarLayout = (AppBarLayout) view;
                ((AppBarLayout.BaseBehavior) z30Var).m210898c8(coordinatorLayout, appBarLayout);
                if (appBarLayout.f49029b1) {
                    appBarLayout.m210880a5(appBarLayout.m210881a6(AppBarLayout.BaseBehavior.m210884c5(coordinatorLayout)));
                    return;
                }
                return;
            case 2:
                try {
                    zBooleanValue = ((Boolean) ((ob0) this.f57904a3).get()).booleanValue();
                } catch (InterruptedException | ExecutionException unused) {
                }
                ((so0) this.f57902a1).mo210482a4((jg1) this.f57903a2, zBooleanValue);
                return;
            case 3:
                try {
                    objCall = ((CallableC0603i) this.f57902a1).call();
                } catch (Exception unused2) {
                    objCall = null;
                }
                ((Handler) this.f57904a3).post(new RunnableC0884n2((C0700j) this.f57903a2, objCall, 9, false));
                return;
            case 4:
                ((C0096a0) this.f57902a1).f45562a9.m214655a6((x11) this.f57903a2, (fh0) this.f57904a3);
                return;
            case 5:
                Ref$BooleanRef ref$BooleanRef = (Ref$BooleanRef) this.f57902a1;
                if (ref$BooleanRef.f57622a0) {
                    return;
                }
                ref$BooleanRef.f57622a0 = true;
                t60.m214704c5(((C0371a8) this.f57903a2).f55141a2, "[VIVO下滑手势] ❌ 超时！手机可能卡顿");
                C0530gb c0530gb = (C0530gb) this.f57904a3;
                int i2 = Result.f57558a1;
                c0530gb.resumeWith(Boolean.FALSE);
                return;
            default:
                ff1.m212804a7((View) this.f57902a1, (C1217sc) this.f57903a2);
                ((ValueAnimator) this.f57904a3).start();
                return;
        }
    }

    public /* synthetic */ RunnableC0818lf(Object obj, Object obj2, Object obj3, int i) {
        this.f57901a0 = i;
        this.f57902a1 = obj;
        this.f57903a2 = obj2;
        this.f57904a3 = obj3;
    }

    public RunnableC0818lf(z30 z30Var, CoordinatorLayout coordinatorLayout, View view) {
        this.f57901a0 = 1;
        this.f57904a3 = z30Var;
        this.f57902a1 = coordinatorLayout;
        this.f57903a2 = view;
    }

    public RunnableC0818lf(View view, jf1 jf1Var, C1217sc c1217sc, ValueAnimator valueAnimator) {
        this.f57901a0 = 6;
        this.f57902a1 = view;
        this.f57903a2 = c1217sc;
        this.f57904a3 = valueAnimator;
    }
}
