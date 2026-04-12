package p000;

import androidx.work.WorkInfo$State;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vg1 {

    /* renamed from: a0 */
    public String f60643a0;

    /* renamed from: a1 */
    public WorkInfo$State f60644a1;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof vg1)) {
            return false;
        }
        vg1 vg1Var = (vg1) obj;
        return t60.m214686a2(this.f60643a0, vg1Var.f60643a0) && this.f60644a1 == vg1Var.f60644a1;
    }

    public final int hashCode() {
        return this.f60644a1.hashCode() + (this.f60643a0.hashCode() * 31);
    }

    public final String toString() {
        return "IdAndState(id=" + this.f60643a0 + ", state=" + this.f60644a1 + ')';
    }
}
