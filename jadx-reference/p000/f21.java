package p000;

import androidx.work.impl.C0096a0;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class f21 implements Runnable {

    /* renamed from: a0 */
    public final C0096a0 f56137a0;

    /* renamed from: a1 */
    public final x11 f56138a1;

    /* renamed from: a2 */
    public final boolean f56139a2;

    static {
        C1351vv.m214966b1("StopWorkRunnable");
    }

    public f21(C0096a0 c0096a0, x11 x11Var, boolean z) {
        this.f56137a0 = c0096a0;
        this.f56138a1 = x11Var;
        this.f56139a2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        fh1 fh1Var;
        if (this.f56139a2) {
            so0 so0Var = this.f56137a0.f45562a9;
            x11 x11Var = this.f56138a1;
            so0Var.getClass();
            String str = x11Var.f60991a0.f57334a0;
            synchronized (so0Var.f60049b1) {
                try {
                    C1351vv.m214963a5().getClass();
                    fh1Var = (fh1) so0Var.f60043a5.remove(str);
                    if (fh1Var != null) {
                        so0Var.f60045a7.remove(str);
                    }
                } finally {
                }
            }
            so0.m214650a1(fh1Var);
        } else {
            so0 so0Var2 = this.f56137a0.f45562a9;
            x11 x11Var2 = this.f56138a1;
            so0Var2.getClass();
            String str2 = x11Var2.f60991a0.f57334a0;
            synchronized (so0Var2.f60049b1) {
                try {
                    fh1 fh1Var2 = (fh1) so0Var2.f60044a6.remove(str2);
                    if (fh1Var2 == null) {
                        C1351vv.m214963a5().getClass();
                    } else {
                        Set set = (Set) so0Var2.f60045a7.get(str2);
                        if (set != null && set.contains(x11Var2)) {
                            C1351vv.m214963a5().getClass();
                            so0Var2.f60045a7.remove(str2);
                            so0.m214650a1(fh1Var2);
                        }
                    }
                } finally {
                }
            }
        }
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        jg1 jg1Var = this.f56138a1.f60991a0;
        c1351vvM214963a5.getClass();
    }
}
