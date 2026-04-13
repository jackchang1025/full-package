package a0;

import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Predicate;
import p012o.AbstractC0414c;
import p012o.C0416e;
import p012o.C0418g;
import p012o.C0419h;
import p012o.C0420i;
import p012o.C0422k;
import p012o.C0423l;
import p012o.C0425n;
import p012o.C0426o;
import p012o.C0428q;
import p012o.C0431t;
import p012o.C0433v;
import p012o.C0435x;
import p012o.a0;
import p012o.e0;
import p012o.i0;

/* renamed from: a0.a */
/* loaded from: classes.dex */
public final class C0001a implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f0a;

    /* renamed from: b */
    public final /* synthetic */ AccessibilityDelegateManager f1b;

    public /* synthetic */ C0001a(AccessibilityDelegateManager accessibilityDelegateManager, int i2) {
        this.f0a = i2;
        this.f1b = accessibilityDelegateManager;
    }

    /* renamed from: a */
    public final boolean m31a(C0416e c0416e) {
        int i2 = this.f0a;
        AccessibilityDelegateManager accessibilityDelegateManager = this.f1b;
        switch (i2) {
            case 0:
                if (!(c0416e instanceof C0422k)) {
                    break;
                } else {
                    ((C0422k) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0422k.class.getName(), C0422k.m1125J());
                    break;
                }
            case 1:
                if (!(c0416e instanceof AbstractC0414c)) {
                    break;
                } else {
                    if (c0416e instanceof C0418g) {
                        accessibilityDelegateManager.m518C(C0418g.class.getName(), C0418g.k0());
                    }
                    if (c0416e instanceof C0425n) {
                        accessibilityDelegateManager.m518C(C0425n.class.getName(), C0425n.s0());
                    }
                    if (c0416e instanceof C0428q) {
                        accessibilityDelegateManager.m518C(C0428q.class.getName(), C0428q.l0());
                    }
                    if (c0416e instanceof C0433v) {
                        accessibilityDelegateManager.m518C(C0433v.class.getName(), C0433v.w0());
                    }
                    if (c0416e instanceof e0) {
                        accessibilityDelegateManager.m518C(e0.class.getName(), e0.n0());
                    }
                    if (c0416e instanceof i0) {
                        accessibilityDelegateManager.m518C(i0.class.getName(), i0.u0());
                        break;
                    }
                }
                break;
            case 2:
                if (!(c0416e instanceof C0426o)) {
                    break;
                } else {
                    ((C0426o) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0426o.class.getName(), Collections.singletonList(C0426o.m1133H()));
                    break;
                }
            case 3:
                if (!(c0416e instanceof C0423l)) {
                    break;
                } else {
                    ((C0423l) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0423l.class.getName(), C0423l.m1132J());
                    break;
                }
            case 4:
                Integer num = AccessibilityDelegateManager.f302j;
                if (!((c0416e instanceof a0) || (c0416e instanceof C0420i) || (c0416e instanceof C0435x) || (c0416e instanceof C0431t) || (c0416e instanceof C0419h) || (c0416e instanceof C0422k) || (c0416e instanceof AbstractC0414c) || (c0416e instanceof C0426o) || (c0416e instanceof C0423l))) {
                    c0416e.mo1001d();
                    accessibilityDelegateManager.m518C(c0416e.getClass().getName(), new LinkedList(c0416e.f865d));
                    break;
                } else {
                    break;
                }
                break;
            case 5:
                if (!(c0416e instanceof a0)) {
                    break;
                } else {
                    ((a0) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(a0.class.getName(), a0.E0());
                    break;
                }
            case 6:
                if (!(c0416e instanceof C0420i)) {
                    break;
                } else {
                    ((C0420i) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0420i.class.getName(), C0420i.m1117L());
                    break;
                }
            case 7:
                if (!(c0416e instanceof C0435x)) {
                    break;
                } else {
                    ((C0435x) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0435x.class.getName(), C0435x.m1156N());
                    break;
                }
            case 8:
                if (!(c0416e instanceof C0431t)) {
                    break;
                } else {
                    ((C0431t) c0416e).mo1001d();
                    accessibilityDelegateManager.m518C(C0431t.class.getName(), C0431t.m1141X());
                    break;
                }
            default:
                if (!(c0416e instanceof C0419h)) {
                    break;
                } else {
                    accessibilityDelegateManager.m518C(C0419h.class.getName(), C0419h.m1110M());
                    break;
                }
        }
        return false;
    }

    @Override // java.util.function.Predicate
    public final /* bridge */ /* synthetic */ boolean test(Object obj) {
        switch (this.f0a) {
        }
        return m31a((C0416e) obj);
    }
}
