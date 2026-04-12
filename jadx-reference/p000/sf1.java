package p000;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class sf1 extends rf1 {
    public sf1(xf1 xf1Var, WindowInsets windowInsets) {
        super(xf1Var, windowInsets);
    }

    @Override // p000.vf1
    /* renamed from: a0 */
    public xf1 mo214611a0() {
        return xf1.m215170a6(null, this.f59497a2.consumeDisplayCutout());
    }

    @Override // p000.vf1
    /* renamed from: a4 */
    public C1264tl mo214612a4() {
        DisplayCutout displayCutout = this.f59497a2.getDisplayCutout();
        if (displayCutout == null) {
            return null;
        }
        return new C1264tl(displayCutout);
    }

    @Override // p000.qf1, p000.vf1
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof sf1)) {
            return false;
        }
        sf1 sf1Var = (sf1) obj;
        return Objects.equals(this.f59497a2, sf1Var.f59497a2) && Objects.equals(this.f59501a6, sf1Var.f59501a6);
    }

    @Override // p000.vf1
    public int hashCode() {
        return this.f59497a2.hashCode();
    }
}
