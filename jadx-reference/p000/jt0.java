package p000;

import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class jt0 extends mt0 implements Iterator {

    /* renamed from: a0 */
    public kt0 f57379a0;

    /* renamed from: a1 */
    public kt0 f57380a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f57381a2;

    public jt0(kt0 kt0Var, kt0 kt0Var2, int i) {
        this.f57381a2 = i;
        this.f57379a0 = kt0Var2;
        this.f57380a1 = kt0Var;
    }

    @Override // p000.mt0
    /* renamed from: a0 */
    public final void mo213348a0(kt0 kt0Var) {
        kt0 kt0Var2;
        kt0 kt0VarM213349a1 = null;
        if (this.f57379a0 == kt0Var && kt0Var == this.f57380a1) {
            this.f57380a1 = null;
            this.f57379a0 = null;
        }
        kt0 kt0Var3 = this.f57379a0;
        if (kt0Var3 == kt0Var) {
            switch (this.f57381a2) {
                case 0:
                    kt0Var2 = kt0Var3.f57719a3;
                    break;
                default:
                    kt0Var2 = kt0Var3.f57718a2;
                    break;
            }
            this.f57379a0 = kt0Var2;
        }
        kt0 kt0Var4 = this.f57380a1;
        if (kt0Var4 == kt0Var) {
            kt0 kt0Var5 = this.f57379a0;
            if (kt0Var4 != kt0Var5 && kt0Var5 != null) {
                kt0VarM213349a1 = m213349a1(kt0Var4);
            }
            this.f57380a1 = kt0VarM213349a1;
        }
    }

    /* renamed from: a1 */
    public final kt0 m213349a1(kt0 kt0Var) {
        switch (this.f57381a2) {
            case 0:
                return kt0Var.f57718a2;
            default:
                return kt0Var.f57719a3;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f57380a1 != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        kt0 kt0Var = this.f57380a1;
        kt0 kt0Var2 = this.f57379a0;
        this.f57380a1 = (kt0Var == kt0Var2 || kt0Var2 == null) ? null : m213349a1(kt0Var);
        return kt0Var;
    }
}
