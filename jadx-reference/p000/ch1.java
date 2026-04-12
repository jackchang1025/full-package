package p000;

import java.util.HashMap;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ch1 {

    /* renamed from: a0 */
    public final tg0 f46137a0;

    /* renamed from: a1 */
    public final HashMap f46138a1 = new HashMap();

    /* renamed from: a2 */
    public final HashMap f46139a2 = new HashMap();

    /* renamed from: a3 */
    public final Object f46140a3 = new Object();

    static {
        C1351vv.m214966b1("WorkTimer");
    }

    public ch1(tg0 tg0Var) {
        this.f46137a0 = tg0Var;
    }

    /* renamed from: a0 */
    public final void m210853a0(jg1 jg1Var) {
        synchronized (this.f46140a3) {
            try {
                if (((bh1) this.f46138a1.remove(jg1Var)) != null) {
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    Objects.toString(jg1Var);
                    c1351vvM214963a5.getClass();
                    this.f46139a2.remove(jg1Var);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
