package p000;

import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class m20 {

    /* renamed from: a0 */
    public int f58243a0;

    /* renamed from: a1 */
    public int f58244a1;

    /* renamed from: a2 */
    public int[] f58245a2;

    /* renamed from: a3 */
    public int f58246a3;

    /* renamed from: a0 */
    public final void m213932a0(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Layout positions must be non-negative");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Pixel distance must be non-negative");
        }
        int i3 = this.f58246a3;
        int i4 = i3 * 2;
        int[] iArr = this.f58245a2;
        if (iArr == null) {
            int[] iArr2 = new int[4];
            this.f58245a2 = iArr2;
            Arrays.fill(iArr2, -1);
        } else if (i4 >= iArr.length) {
            int[] iArr3 = new int[i3 * 4];
            this.f58245a2 = iArr3;
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        }
        int[] iArr4 = this.f58245a2;
        iArr4[i4] = i;
        iArr4[i4 + 1] = i2;
        this.f58246a3++;
    }

    /* renamed from: a1 */
    public final void m213933a1(RecyclerView recyclerView, boolean z) {
        this.f58246a3 = 0;
        int[] iArr = this.f58245a2;
        if (iArr != null) {
            Arrays.fill(iArr, -1);
        }
        pq0 pq0Var = recyclerView.f45265b1;
        if (recyclerView.f45264b0 == null || pq0Var == null || !pq0Var.f59326a8) {
            return;
        }
        if (z) {
            if (!recyclerView.f45257a3.m214343a5()) {
                pq0Var.mo210301a8(recyclerView.f45264b0.mo211032a0(), this);
            }
        } else if (!recyclerView.m210375d7()) {
            pq0Var.mo210300a7(this.f58243a0, this.f58244a1, recyclerView.f45306f2, this);
        }
        int i = this.f58246a3;
        if (i > pq0Var.f59327a9) {
            pq0Var.f59327a9 = i;
            pq0Var.f59328b0 = z;
            recyclerView.f45255a1.m214948b0();
        }
    }
}
