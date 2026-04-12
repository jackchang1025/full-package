package p000;

import android.graphics.Typeface;
import com.google.android.material.chip.Chip;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hm */
/* loaded from: classes2.dex */
public final class C0586hm extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ int f56681b0;

    /* renamed from: b1 */
    public final /* synthetic */ Object f56682b1;

    public /* synthetic */ C0586hm(int i, Object obj) {
        this.f56681b0 = i;
        this.f56682b1 = obj;
    }

    @Override // p000.cq0
    /* renamed from: c6 */
    public final void mo212508c6(int i) {
        switch (this.f56681b0) {
            case 0:
                break;
            default:
                u51 u51Var = (u51) this.f56682b1;
                u51Var.f60331a3 = true;
                t51 t51Var = (t51) u51Var.f60332a4.get();
                if (t51Var != null) {
                    t51Var.mo210828a0();
                    break;
                }
                break;
        }
    }

    @Override // p000.cq0
    /* renamed from: c8 */
    public final void mo212510c8(Typeface typeface, boolean z) {
        switch (this.f56681b0) {
            case 0:
                Chip chip = (Chip) this.f56682b1;
                C0590hq c0590hq = chip.f49332a4;
                chip.setText(c0590hq.f56746i0 ? c0590hq.f56696d0 : chip.getText());
                chip.requestLayout();
                chip.invalidate();
                break;
            default:
                if (!z) {
                    u51 u51Var = (u51) this.f56682b1;
                    u51Var.f60331a3 = true;
                    t51 t51Var = (t51) u51Var.f60332a4.get();
                    if (t51Var != null) {
                        t51Var.mo210828a0();
                        break;
                    }
                }
                break;
        }
    }

    /* renamed from: e4 */
    private final void m213054e4(int i) {
    }
}
