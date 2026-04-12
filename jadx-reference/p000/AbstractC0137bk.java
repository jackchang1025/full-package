package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bk */
/* loaded from: classes2.dex */
public abstract class AbstractC0137bk extends il0 {

    /* renamed from: a0 */
    public static final AtomicReferenceFieldUpdater f45901a0 = AtomicReferenceFieldUpdater.newUpdater(AbstractC0137bk.class, Object.class, "_consensus");
    private volatile Object _consensus = cq0.f55466a0;

    @Override // p000.il0
    /* renamed from: a0 */
    public final Object mo210735a0(Object obj) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f45901a0;
        Object obj2 = atomicReferenceFieldUpdater.get(this);
        C1347vr c1347vr = cq0.f55466a0;
        if (obj2 == c1347vr) {
            C1347vr c1347vrMo210737a2 = mo210737a2(obj);
            obj2 = atomicReferenceFieldUpdater.get(this);
            if (obj2 == c1347vr) {
                while (true) {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, c1347vr, c1347vrMo210737a2)) {
                        obj2 = c1347vrMo210737a2;
                        break;
                    }
                    if (atomicReferenceFieldUpdater.get(this) != c1347vr) {
                        obj2 = atomicReferenceFieldUpdater.get(this);
                        break;
                    }
                }
            }
        }
        mo210736a1(obj, obj2);
        return obj2;
    }

    /* renamed from: a1 */
    public abstract void mo210736a1(Object obj, Object obj2);

    /* renamed from: a2 */
    public abstract C1347vr mo210737a2(Object obj);
}
