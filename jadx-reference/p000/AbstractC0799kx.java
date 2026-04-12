package p000;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kx */
/* loaded from: classes2.dex */
public abstract class AbstractC0799kx {

    /* renamed from: a0 */
    public final AbstractC0826ln f57740a0;

    /* renamed from: a1 */
    public final ArrayList f57741a1;

    /* renamed from: a2 */
    public final ArrayList f57742a2;

    /* renamed from: a3 */
    public Object f57743a3;

    /* renamed from: a4 */
    public zg1 f57744a4;

    public AbstractC0799kx(AbstractC0826ln abstractC0826ln) {
        t60.m214695b6(abstractC0826ln, "tracker");
        this.f57740a0 = abstractC0826ln;
        this.f57741a1 = new ArrayList();
        this.f57742a2 = new ArrayList();
    }

    /* renamed from: a0 */
    public abstract boolean mo212609a0(wg1 wg1Var);

    /* renamed from: a1 */
    public abstract boolean mo212610a1(Object obj);

    /* renamed from: a2 */
    public final void m213764a2(Iterable iterable) {
        t60.m214695b6(iterable, "workSpecs");
        this.f57741a1.clear();
        this.f57742a2.clear();
        ArrayList arrayList = this.f57741a1;
        for (Object obj : iterable) {
            if (mo212609a0((wg1) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = this.f57741a1;
        ArrayList arrayList3 = this.f57742a2;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList2.get(i);
            i++;
            arrayList3.add(((wg1) obj2).f60912a0);
        }
        if (this.f57741a1.isEmpty()) {
            this.f57740a0.m213873a1(this);
        } else {
            AbstractC0826ln abstractC0826ln = this.f57740a0;
            abstractC0826ln.getClass();
            synchronized (abstractC0826ln.f58055a2) {
                try {
                    if (abstractC0826ln.f58056a3.add(this)) {
                        if (abstractC0826ln.f58056a3.size() == 1) {
                            abstractC0826ln.f58057a4 = abstractC0826ln.mo212612a0();
                            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                            int i2 = AbstractC0827lo.f58059a0;
                            Objects.toString(abstractC0826ln.f58057a4);
                            c1351vvM214963a5.getClass();
                            abstractC0826ln.mo212613a3();
                        }
                        Object obj3 = abstractC0826ln.f58057a4;
                        this.f57743a3 = obj3;
                        m213765a3(this.f57744a4, obj3);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        m213765a3(this.f57744a4, this.f57743a3);
    }

    /* renamed from: a3 */
    public final void m213765a3(zg1 zg1Var, Object obj) {
        if (this.f57741a1.isEmpty() || zg1Var == null) {
            return;
        }
        if (obj == null || mo212610a1(obj)) {
            ArrayList arrayList = this.f57741a1;
            t60.m214695b6(arrayList, "workSpecs");
            synchronized (zg1Var.f61553a2) {
                bg1 bg1Var = (bg1) zg1Var.f61551a0;
                if (bg1Var != null) {
                    bg1Var.mo210487a1(arrayList);
                }
            }
            return;
        }
        ArrayList arrayList2 = this.f57741a1;
        t60.m214695b6(arrayList2, "workSpecs");
        synchronized (zg1Var.f61553a2) {
            try {
                ArrayList arrayList3 = new ArrayList();
                int size = arrayList2.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    if (zg1Var.m215406a2(((wg1) obj2).f60912a0)) {
                        arrayList3.add(obj2);
                    }
                }
                int size2 = arrayList3.size();
                while (i < size2) {
                    Object obj3 = arrayList3.get(i);
                    i++;
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    int i3 = cg1.f46135a0;
                    Objects.toString((wg1) obj3);
                    c1351vvM214963a5.getClass();
                }
                bg1 bg1Var2 = (bg1) zg1Var.f61551a0;
                if (bg1Var2 != null) {
                    bg1Var2.mo210488a3(arrayList3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
