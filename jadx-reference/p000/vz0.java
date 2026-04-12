package p000;

import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vz0 implements Runnable {

    /* renamed from: a0 */
    public final C0076a0 f60724a0;

    /* renamed from: a1 */
    public final Lifecycle$Event f60725a1;

    /* renamed from: a2 */
    public boolean f60726a2;

    public vz0(C0076a0 c0076a0, Lifecycle$Event lifecycle$Event) {
        t60.m214695b6(c0076a0, "registry");
        t60.m214695b6(lifecycle$Event, "event");
        this.f60724a0 = c0076a0;
        this.f60725a1 = lifecycle$Event;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f60726a2) {
            return;
        }
        this.f60724a0.m210234g1(this.f60725a1);
        this.f60726a2 = true;
    }
}
