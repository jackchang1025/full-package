package p000;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import androidx.appcompat.widget.C0041a1;
import androidx.fragment.app.C0068a4;
import androidx.fragment.app.C0073a9;
import androidx.work.Worker;
import androidx.work.impl.utils.futures.C0100a1;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;
import java.util.WeakHashMap;
import kotlin.Result;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.android.C0785a0;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: n2 */
/* loaded from: classes.dex */
public final class RunnableC0884n2 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58429a0;

    /* renamed from: a1 */
    public Object f58430a1;

    /* renamed from: a2 */
    public final Object f58431a2;

    public /* synthetic */ RunnableC0884n2(Object obj, int i, Object obj2) {
        this.f58429a0 = i;
        this.f58431a2 = obj;
        this.f58430a1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ze0 ze0Var;
        int i = 0;
        wg1 wg1Var = null;
        switch (this.f58429a0) {
            case 0:
                C0882n0 c0882n0 = (C0882n0) this.f58430a1;
                C0041a1 c0041a1 = (C0041a1) this.f58431a2;
                bf0 bf0Var = c0041a1.f44140a2;
                if (bf0Var != null && (ze0Var = bf0Var.f45870a4) != null) {
                    ze0Var.mo214683b0(bf0Var);
                }
                View view = (View) c0041a1.f44145a7;
                if (view != null && view.getWindowToken() != null) {
                    if (c0882n0.m214075a1()) {
                        c0041a1.f44157b9 = c0882n0;
                    } else if (c0882n0.f58620a4 != null) {
                        c0882n0.m214076a3(0, 0, false, false);
                        c0041a1.f44157b9 = c0882n0;
                    }
                }
                c0041a1.f44159c1 = null;
                return;
            case 1:
                ((C1054p3) this.f58430a1).f59139a0 = this.f58431a2;
                return;
            case 2:
                ((Application) this.f58430a1).unregisterActivityLifecycleCallbacks((C1054p3) this.f58431a2);
                return;
            case 3:
                Object obj = this.f58431a2;
                Object obj2 = this.f58430a1;
                try {
                    Method method = AbstractC1055p4.f59150a3;
                    if (method != null) {
                        method.invoke(obj2, obj, Boolean.FALSE, "AppCompat recreation");
                    } else {
                        AbstractC1055p4.f59151a4.invoke(obj2, obj, Boolean.FALSE);
                    }
                    return;
                } catch (RuntimeException e) {
                    if (e.getClass() == RuntimeException.class && e.getMessage() != null && e.getMessage().startsWith("Unable to stop")) {
                        throw e;
                    }
                    return;
                } catch (Throwable unused) {
                    return;
                }
            case 4:
                jl0 jl0Var = (jl0) this.f58430a1;
                Typeface typeface = (Typeface) this.f58431a2;
                cq0 cq0Var = (cq0) jl0Var.f57345a0;
                if (cq0Var != null) {
                    cq0Var.mo212509c7(typeface);
                    return;
                }
                return;
            case 5:
                C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                int i2 = C1194rv.f59821a3;
                wg1 wg1Var2 = (wg1) this.f58430a1;
                c1351vvM214963a5.getClass();
                ((C1194rv) this.f58431a2).f59822a0.mo212641a2(wg1Var2);
                return;
            case 6:
                ((C0530gb) this.f58430a1).m212935c6((C0785a0) this.f58431a2);
                return;
            case 7:
                Ref$BooleanRef ref$BooleanRef = (Ref$BooleanRef) this.f58430a1;
                if (ref$BooleanRef.f57622a0) {
                    return;
                }
                ref$BooleanRef.f57622a0 = true;
                t60.m214704c5("HuaweiSteps", "[下滑手势] 超时，手机可能卡顿");
                C0530gb c0530gb = (C0530gb) this.f58431a2;
                int i3 = Result.f57558a1;
                c0530gb.resumeWith(Boolean.FALSE);
                return;
            case 8:
                oa0 oa0Var = (oa0) this.f58431a2;
                AbstractC0781a1 abstractC0781a1 = oa0Var.f58772a2;
                while (true) {
                    try {
                        ((Runnable) this.f58430a1).run();
                    } catch (Throwable th) {
                        kj1.m213574c1(EmptyCoroutineContext.f57605a0, th);
                    }
                    Runnable runnableM214170c8 = oa0Var.m214170c8();
                    if (runnableM214170c8 == null) {
                        return;
                    }
                    this.f58430a1 = runnableM214170c8;
                    i++;
                    if (i >= 16 && abstractC0781a1.mo213698c7()) {
                        abstractC0781a1.mo212723c6(oa0Var, this);
                        return;
                    }
                }
                break;
            case 9:
                ((C0700j) this.f58430a1).accept(this.f58431a2);
                return;
            case 10:
                ((C0530gb) this.f58431a2).m212935c6((C1426xq) this.f58430a1);
                return;
            case oe0.DEFAULT_M /* 11 */:
                try {
                    ((Runnable) this.f58431a2).run();
                    synchronized (((ExecutorC0034an) this.f58430a1).f43727a3) {
                        ((ExecutorC0034an) this.f58430a1).m209823a0();
                    }
                    return;
                } catch (Throwable th2) {
                    synchronized (((ExecutorC0034an) this.f58430a1).f43727a3) {
                        ((ExecutorC0034an) this.f58430a1).m209823a0();
                        throw th2;
                    }
                }
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0068a4 c0068a4 = (C0068a4) this.f58431a2;
                ArrayList arrayList = c0068a4.f45072a1;
                C0073a9 c0073a9 = (C0073a9) this.f58430a1;
                arrayList.remove(c0073a9);
                c0068a4.f45073a2.remove(c0073a9);
                return;
            case 13:
                bb1 bb1Var = ((SwipeDismissBehavior) this.f58431a2).f49138a0;
                if (bb1Var == null || !bb1Var.m210637a6()) {
                    return;
                }
                View view2 = (View) this.f58430a1;
                WeakHashMap weakHashMap = xa1.f61054a0;
                fa1.m212775b2(view2, this);
                return;
            case 14:
                so0 so0Var = ((r31) this.f58431a2).f59610a0.f45562a9;
                String str = (String) this.f58430a1;
                synchronized (so0Var.f60049b1) {
                    try {
                        fh1 fh1Var = (fh1) so0Var.f60043a5.get(str);
                        if (fh1Var == null) {
                            fh1Var = (fh1) so0Var.f60044a6.get(str);
                        }
                        if (fh1Var != null) {
                            wg1Var = fh1Var.f56261a3;
                        }
                    } finally {
                    }
                }
                if (wg1Var == null || !wg1Var.m215067a2()) {
                    return;
                }
                synchronized (((r31) this.f58431a2).f59612a2) {
                    ((r31) this.f58431a2).f59615a5.put(cq0.m212483b3(wg1Var), wg1Var);
                    ((r31) this.f58431a2).f59616a6.add(wg1Var);
                    r31 r31Var = (r31) this.f58431a2;
                    r31Var.f59617a7.m215415b1(r31Var.f59616a6);
                }
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                if (((gg1) this.f58431a2).f56463a0.f56381a0 instanceof C0486f8) {
                    return;
                }
                try {
                    C1241t c1241t = (C1241t) ((C0100a1) this.f58430a1).get();
                    if (c1241t == null) {
                        throw new IllegalStateException("Worker was marked important (" + ((gg1) this.f58431a2).f56465a2.f60914a2 + ") but did not provide ForegroundInfo");
                    }
                    C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                    int i4 = gg1.f56462a6;
                    String str2 = ((gg1) this.f58431a2).f56465a2.f60914a2;
                    c1351vvM214963a52.getClass();
                    gg1 gg1Var = (gg1) this.f58431a2;
                    C0100a1 c0100a1 = gg1Var.f56463a0;
                    ig1 ig1Var = gg1Var.f56467a4;
                    Context context = gg1Var.f56464a1;
                    UUID uuid = gg1Var.f56466a3.f60191a1.f45534a0;
                    ig1Var.getClass();
                    C0100a1 c0100a12 = new C0100a1();
                    ig1Var.f56884a0.m214272b6(new hg1(ig1Var, c0100a12, uuid, c1241t, context));
                    c0100a1.m210486b0(c0100a12);
                    return;
                } catch (Throwable th3) {
                    ((gg1) this.f58431a2).f56463a0.m210485a9(th3);
                    return;
                }
            case 16:
                try {
                    ((Worker) this.f58431a2).getClass();
                    throw new IllegalStateException("Expedited WorkRequests require a Worker to provide an implementation for \n `getForegroundInfo()`");
                } catch (Throwable th4) {
                    ((C0100a1) this.f58430a1).m210485a9(th4);
                    return;
                }
            default:
                if (((fh1) this.f58431a2).f56273b5.f56381a0 instanceof C0486f8) {
                    return;
                }
                try {
                    ((ob0) this.f58430a1).get();
                    C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                    int i5 = fh1.f56257b7;
                    String str3 = ((fh1) this.f58431a2).f56261a3.f60914a2;
                    c1351vvM214963a53.getClass();
                    fh1 fh1Var2 = (fh1) this.f58431a2;
                    fh1Var2.f56273b5.m210486b0(fh1Var2.f56262a4.mo210455a4());
                    return;
                } catch (Throwable th5) {
                    ((fh1) this.f58431a2).f56273b5.m210485a9(th5);
                    return;
                }
        }
    }

    public /* synthetic */ RunnableC0884n2(Object obj, Object obj2, int i, boolean z) {
        this.f58429a0 = i;
        this.f58430a1 = obj;
        this.f58431a2 = obj2;
    }

    public RunnableC0884n2(SwipeDismissBehavior swipeDismissBehavior, View view, boolean z) {
        this.f58429a0 = 13;
        this.f58431a2 = swipeDismissBehavior;
        this.f58430a1 = view;
    }
}
