package p000;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class n61 {

    /* renamed from: a1 */
    public static final AtomicIntegerFieldUpdater f58458a1 = AtomicIntegerFieldUpdater.newUpdater(n61.class, "_size");
    private volatile int _size;

    /* renamed from: a0 */
    public AbstractRunnableC1422xm[] f58459a0;

    /* renamed from: a0 */
    public final void m214034a0(AbstractRunnableC1422xm abstractRunnableC1422xm) {
        abstractRunnableC1422xm.m215198a1((C1423xn) this);
        AbstractRunnableC1422xm[] abstractRunnableC1422xmArr = this.f58459a0;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f58458a1;
        if (abstractRunnableC1422xmArr == null) {
            abstractRunnableC1422xmArr = new AbstractRunnableC1422xm[4];
            this.f58459a0 = abstractRunnableC1422xmArr;
        } else if (atomicIntegerFieldUpdater.get(this) >= abstractRunnableC1422xmArr.length) {
            Object[] objArrCopyOf = Arrays.copyOf(abstractRunnableC1422xmArr, atomicIntegerFieldUpdater.get(this) * 2);
            t60.m214694b5(objArrCopyOf, "copyOf(this, newSize)");
            abstractRunnableC1422xmArr = (AbstractRunnableC1422xm[]) objArrCopyOf;
            this.f58459a0 = abstractRunnableC1422xmArr;
        }
        int i = atomicIntegerFieldUpdater.get(this);
        atomicIntegerFieldUpdater.set(this, i + 1);
        abstractRunnableC1422xmArr[i] = abstractRunnableC1422xm;
        abstractRunnableC1422xm.f61156a1 = i;
        m214036a2(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[SYNTHETIC] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AbstractRunnableC1422xm m214035a1(int i) {
        Object[] objArr = this.f58459a0;
        t60.m214692b3(objArr);
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f58458a1;
        atomicIntegerFieldUpdater.set(this, atomicIntegerFieldUpdater.get(this) - 1);
        if (i < atomicIntegerFieldUpdater.get(this)) {
            m214037a3(i, atomicIntegerFieldUpdater.get(this));
            int i2 = (i - 1) / 2;
            if (i > 0) {
                AbstractRunnableC1422xm abstractRunnableC1422xm = objArr[i];
                t60.m214692b3(abstractRunnableC1422xm);
                Object obj = objArr[i2];
                t60.m214692b3(obj);
                if (abstractRunnableC1422xm.compareTo(obj) < 0) {
                    m214037a3(i, i2);
                    m214036a2(i2);
                } else {
                    while (true) {
                        int i3 = i * 2;
                        int i4 = i3 + 1;
                        if (i4 >= atomicIntegerFieldUpdater.get(this)) {
                            break;
                        }
                        Object[] objArr2 = this.f58459a0;
                        t60.m214692b3(objArr2);
                        int i5 = i3 + 2;
                        if (i5 < atomicIntegerFieldUpdater.get(this)) {
                            Comparable comparable = objArr2[i5];
                            t60.m214692b3(comparable);
                            Object obj2 = objArr2[i4];
                            t60.m214692b3(obj2);
                            if (comparable.compareTo(obj2) >= 0) {
                                i5 = i4;
                            }
                            Comparable comparable2 = objArr2[i];
                            t60.m214692b3(comparable2);
                            Comparable comparable3 = objArr2[i5];
                            t60.m214692b3(comparable3);
                            if (comparable2.compareTo(comparable3) <= 0) {
                                break;
                            }
                            m214037a3(i, i5);
                            i = i5;
                        }
                    }
                }
            }
        }
        AbstractRunnableC1422xm abstractRunnableC1422xm2 = objArr[atomicIntegerFieldUpdater.get(this)];
        t60.m214692b3(abstractRunnableC1422xm2);
        abstractRunnableC1422xm2.m215198a1(null);
        abstractRunnableC1422xm2.f61156a1 = -1;
        objArr[atomicIntegerFieldUpdater.get(this)] = null;
        return abstractRunnableC1422xm2;
    }

    /* renamed from: a2 */
    public final void m214036a2(int i) {
        while (i > 0) {
            AbstractRunnableC1422xm[] abstractRunnableC1422xmArr = this.f58459a0;
            t60.m214692b3(abstractRunnableC1422xmArr);
            int i2 = (i - 1) / 2;
            AbstractRunnableC1422xm abstractRunnableC1422xm = abstractRunnableC1422xmArr[i2];
            t60.m214692b3(abstractRunnableC1422xm);
            AbstractRunnableC1422xm abstractRunnableC1422xm2 = abstractRunnableC1422xmArr[i];
            t60.m214692b3(abstractRunnableC1422xm2);
            if (abstractRunnableC1422xm.compareTo(abstractRunnableC1422xm2) <= 0) {
                return;
            }
            m214037a3(i, i2);
            i = i2;
        }
    }

    /* renamed from: a3 */
    public final void m214037a3(int i, int i2) {
        AbstractRunnableC1422xm[] abstractRunnableC1422xmArr = this.f58459a0;
        t60.m214692b3(abstractRunnableC1422xmArr);
        AbstractRunnableC1422xm abstractRunnableC1422xm = abstractRunnableC1422xmArr[i2];
        t60.m214692b3(abstractRunnableC1422xm);
        AbstractRunnableC1422xm abstractRunnableC1422xm2 = abstractRunnableC1422xmArr[i];
        t60.m214692b3(abstractRunnableC1422xm2);
        abstractRunnableC1422xmArr[i] = abstractRunnableC1422xm;
        abstractRunnableC1422xmArr[i2] = abstractRunnableC1422xm2;
        abstractRunnableC1422xm.f61156a1 = i;
        abstractRunnableC1422xm2.f61156a1 = i2;
    }
}
