package p000;

import android.view.animation.Interpolator;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yq0 {

    /* renamed from: a0 */
    public int f61355a0;

    /* renamed from: a1 */
    public int f61356a1;

    /* renamed from: a2 */
    public int f61357a2;

    /* renamed from: a3 */
    public int f61358a3;

    /* renamed from: a4 */
    public Interpolator f61359a4;

    /* renamed from: a5 */
    public boolean f61360a5;

    /* renamed from: a0 */
    public final void m215303a0(RecyclerView recyclerView) {
        int i = this.f61358a3;
        if (i >= 0) {
            this.f61358a3 = -1;
            recyclerView.m210377d9(i);
            this.f61360a5 = false;
        } else if (this.f61360a5) {
            Interpolator interpolator = this.f61359a4;
            if (interpolator != null && this.f61357a2 < 1) {
                throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
            }
            int i2 = this.f61357a2;
            if (i2 < 1) {
                throw new IllegalStateException("Scroll duration must be a positive number");
            }
            recyclerView.f45303e9.m212518a1(this.f61355a0, this.f61356a1, i2, interpolator);
            this.f61360a5 = false;
        }
    }
}
