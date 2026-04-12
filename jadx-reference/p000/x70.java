package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlinx.coroutines.internal.C0787a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class x70 extends AbstractC0137bk {

    /* renamed from: a1 */
    public final u70 f61028a1;

    /* renamed from: a2 */
    public uj0 f61029a2;

    /* renamed from: a3 */
    public final /* synthetic */ y70 f61030a3;

    /* renamed from: a4 */
    public final /* synthetic */ k50 f61031a4;

    public x70(u70 u70Var, y70 y70Var, k50 k50Var) {
        this.f61030a3 = y70Var;
        this.f61031a4 = k50Var;
        this.f61028a1 = u70Var;
    }

    @Override // p000.AbstractC0137bk
    /* renamed from: a1 */
    public final void mo210736a1(Object obj, Object obj2) {
        C0787a0 c0787a0 = (C0787a0) obj;
        boolean z = obj2 == null;
        C0787a0 c0787a02 = this.f61028a1;
        C0787a0 c0787a03 = z ? c0787a02 : this.f61029a2;
        if (c0787a03 != null) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0787a0.f57685a0;
            while (!atomicReferenceFieldUpdater.compareAndSet(c0787a0, this, c0787a03)) {
                if (atomicReferenceFieldUpdater.get(c0787a0) != this) {
                    return;
                }
            }
            if (z) {
                C0787a0 c0787a04 = this.f61029a2;
                t60.m214692b3(c0787a04);
                c0787a02.m213730a6(c0787a04);
            }
        }
    }

    @Override // p000.AbstractC0137bk
    /* renamed from: a2 */
    public final C1347vr mo210737a2(Object obj) {
        if (this.f61030a3.m215262c4() == this.f61031a4) {
            return null;
        }
        return cq0.f55468a2;
    }
}
