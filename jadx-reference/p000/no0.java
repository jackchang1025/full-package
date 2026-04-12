package p000;

import android.os.Handler;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class no0 implements ka0 {

    /* renamed from: a8 */
    public static final lo0 f58676a8 = new lo0(null);

    /* renamed from: a9 */
    public static final no0 f58677a9 = new no0();

    /* renamed from: a0 */
    public int f58678a0;

    /* renamed from: a1 */
    public int f58679a1;

    /* renamed from: a4 */
    public Handler f58682a4;

    /* renamed from: a2 */
    public boolean f58680a2 = true;

    /* renamed from: a3 */
    public boolean f58681a3 = true;

    /* renamed from: a5 */
    public final C0076a0 f58683a5 = new C0076a0(this, true);

    /* renamed from: a6 */
    public final RunnableC0941o6 f58684a6 = new RunnableC0941o6(17, this);

    /* renamed from: a7 */
    public final jl0 f58685a7 = new jl0(this);

    /* renamed from: a1 */
    public final void m214134a1() {
        int i = this.f58679a1 + 1;
        this.f58679a1 = i;
        if (i == 1) {
            if (this.f58680a2) {
                this.f58683a5.m210234g1(Lifecycle$Event.ON_RESUME);
                this.f58680a2 = false;
            } else {
                Handler handler = this.f58682a4;
                t60.m214692b3(handler);
                handler.removeCallbacks(this.f58684a6);
            }
        }
    }

    @Override // p000.ka0
    /* renamed from: a5 */
    public final C0076a0 mo209830a5() {
        return this.f58683a5;
    }
}
