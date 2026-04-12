package p000;

import android.content.res.Resources;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class wr0 {

    /* renamed from: a0 */
    public final Resources f60965a0;

    /* renamed from: a1 */
    public final Resources.Theme f60966a1;

    public wr0(Resources resources, Resources.Theme theme) {
        this.f60965a0 = resources;
        this.f60966a1 = theme;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && wr0.class == obj.getClass()) {
            wr0 wr0Var = (wr0) obj;
            if (this.f60965a0.equals(wr0Var.f60965a0) && tk0.m214759a0(this.f60966a1, wr0Var.f60966a1)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return tk0.m214760a1(this.f60965a0, this.f60966a1);
    }
}
