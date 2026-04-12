package p000;

import android.os.Handler;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rt */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1192rt implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59803a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1193ru f59804a1;

    public /* synthetic */ RunnableC1192rt(C1193ru c1193ru, int i) {
        this.f59803a0 = i;
        this.f59804a1 = c1193ru;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59803a0) {
            case 0:
                C1193ru.m214546a0(this.f59804a1);
                return;
            default:
                C1193ru c1193ru = this.f59804a1;
                if (c1193ru.f59812a6 != 0) {
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    Objects.toString(c1193ru.f59808a2);
                    c1351vvM214963a5.getClass();
                    return;
                }
                c1193ru.f59812a6 = 1;
                C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                Objects.toString(c1193ru.f59808a2);
                c1351vvM214963a52.getClass();
                if (!c1193ru.f59809a3.f59378a3.m214655a6(c1193ru.f59817b1, null)) {
                    c1193ru.m214547a2();
                    return;
                }
                ch1 ch1Var = c1193ru.f59809a3.f59377a2;
                jg1 jg1Var = c1193ru.f59808a2;
                synchronized (ch1Var.f46140a3) {
                    C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                    Objects.toString(jg1Var);
                    c1351vvM214963a53.getClass();
                    ch1Var.m210853a0(jg1Var);
                    bh1 bh1Var = new bh1(ch1Var, jg1Var);
                    ch1Var.f46138a1.put(jg1Var, bh1Var);
                    ch1Var.f46139a2.put(jg1Var, c1193ru);
                    ((Handler) ch1Var.f46137a0.f60218a1).postDelayed(bh1Var, 600000L);
                }
                return;
        }
    }
}
