package p000;

import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bh1 implements Runnable {

    /* renamed from: a0 */
    public final ch1 f45894a0;

    /* renamed from: a1 */
    public final jg1 f45895a1;

    public bh1(ch1 ch1Var, jg1 jg1Var) {
        this.f45894a0 = ch1Var;
        this.f45895a1 = jg1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.f45894a0.f46140a3) {
            try {
                if (((bh1) this.f45894a0.f46138a1.remove(this.f45895a1)) != null) {
                    ah1 ah1Var = (ah1) this.f45894a0.f46139a2.remove(this.f45895a1);
                    if (ah1Var != null) {
                        jg1 jg1Var = this.f45895a1;
                        C1193ru c1193ru = (C1193ru) ah1Var;
                        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                        Objects.toString(jg1Var);
                        c1351vvM214963a5.getClass();
                        c1193ru.f59813a7.execute(new RunnableC1192rt(c1193ru, 0));
                    }
                } else {
                    C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                    this.f45895a1.toString();
                    c1351vvM214963a52.getClass();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
