package p000;

import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class lt0 extends mt0 implements Iterator {

    /* renamed from: a0 */
    public kt0 f58176a0;

    /* renamed from: a1 */
    public boolean f58177a1 = true;

    /* renamed from: a2 */
    public final /* synthetic */ nt0 f58178a2;

    public lt0(nt0 nt0Var) {
        this.f58178a2 = nt0Var;
    }

    @Override // p000.mt0
    /* renamed from: a0 */
    public final void mo213348a0(kt0 kt0Var) {
        kt0 kt0Var2 = this.f58176a0;
        if (kt0Var == kt0Var2) {
            kt0 kt0Var3 = kt0Var2.f57719a3;
            this.f58176a0 = kt0Var3;
            this.f58177a1 = kt0Var3 == null;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f58177a1) {
            return this.f58178a2.f58692a0 != null;
        }
        kt0 kt0Var = this.f58176a0;
        return (kt0Var == null || kt0Var.f57718a2 == null) ? false : true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f58177a1) {
            this.f58177a1 = false;
            this.f58176a0 = this.f58178a2.f58692a0;
        } else {
            kt0 kt0Var = this.f58176a0;
            this.f58176a0 = kt0Var != null ? kt0Var.f57718a2 : null;
        }
        return this.f58176a0;
    }
}
