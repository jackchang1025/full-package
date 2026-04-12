package p000;

import androidx.core.internal.view.SupportMenu;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class jz0 extends AbstractC0759ki implements wj0 {

    /* renamed from: a3 */
    public static final AtomicIntegerFieldUpdater f57400a3 = AtomicIntegerFieldUpdater.newUpdater(jz0.class, "cleanedAndPointers");

    /* renamed from: a2 */
    public final long f57401a2;
    private volatile int cleanedAndPointers;

    public jz0(long j, jz0 jz0Var, int i) {
        super(jz0Var);
        this.f57401a2 = j;
        this.cleanedAndPointers = i << 16;
    }

    @Override // p000.AbstractC0759ki
    /* renamed from: a2 */
    public final boolean mo213361a2() {
        return f57400a3.get(this) == mo213019a5() && m213554a1() != null;
    }

    /* renamed from: a4 */
    public final boolean m213362a4() {
        return f57400a3.addAndGet(this, SupportMenu.CATEGORY_MASK) == mo213019a5() && m213554a1() != null;
    }

    /* renamed from: a5 */
    public abstract int mo213019a5();

    /* renamed from: a6 */
    public abstract void mo213020a6(int i, InterfaceC0912ng interfaceC0912ng);

    /* renamed from: a7 */
    public final void m213363a7() {
        if (f57400a3.incrementAndGet(this) == mo213019a5()) {
            m213555a3();
        }
    }

    /* renamed from: a8 */
    public final boolean m213364a8() {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i;
        do {
            atomicIntegerFieldUpdater = f57400a3;
            i = atomicIntegerFieldUpdater.get(this);
            if (i == mo213019a5() && m213554a1() != null) {
                return false;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, 65536 + i));
        return true;
    }
}
