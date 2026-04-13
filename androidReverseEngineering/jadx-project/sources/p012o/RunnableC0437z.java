package p012o;

import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import p014r.EnumC0894g;

/* renamed from: o.z */
/* loaded from: classes.dex */
public final class RunnableC0437z implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f976a;

    /* renamed from: b */
    public final /* synthetic */ a0 f977b;

    public /* synthetic */ RunnableC0437z(a0 a0Var, int i2) {
        this.f976a = i2;
        this.f977b = a0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f976a;
        a0 a0Var = this.f977b;
        switch (i2) {
            case 0:
                a0Var.D0();
                break;
            default:
                if (Objects.equals(a0Var.f841p.get(), EnumC0894g.PAIR_DEPT_UNKNOWN)) {
                    if (AbstractC0251g.F0(3)) {
                        AbstractC0251g.T0(5);
                    }
                    if (AbstractC0251g.F0(1)) {
                        AbstractC0251g.T0(5);
                    }
                    if (a0Var.m1072k() != null) {
                        a0Var.m1072k().refresh();
                        break;
                    }
                }
                break;
        }
    }
}
