package p000;

import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j */
/* loaded from: classes.dex */
public final class C0700j implements InterfaceC0854ma {

    /* renamed from: a0 */
    public final /* synthetic */ int f57250a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f57251a1;

    public /* synthetic */ C0700j(int i, Object obj) {
        this.f57250a0 = i;
        this.f57251a1 = obj;
    }

    @Override // p000.InterfaceC0854ma
    public final void accept(Object obj) {
        switch (this.f57250a0) {
            case 0:
                C0739k c0739k = (C0739k) obj;
                if (c0739k == null) {
                    c0739k = new C0739k(-3);
                }
                ((eo0) this.f57251a1).m212716c0(c0739k);
                return;
            default:
                C0739k c0739k2 = (C0739k) obj;
                synchronized (AbstractC0802l.f57817a2) {
                    try {
                        t01 t01Var = AbstractC0802l.f57818a3;
                        ArrayList arrayList = (ArrayList) t01Var.getOrDefault((String) this.f57251a1, null);
                        if (arrayList == null) {
                            return;
                        }
                        t01Var.remove((String) this.f57251a1);
                        for (int i = 0; i < arrayList.size(); i++) {
                            ((InterfaceC0854ma) arrayList.get(i)).accept(c0739k2);
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}
