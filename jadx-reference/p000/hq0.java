package p000;

import android.database.Observable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class hq0 extends Observable {
    /* renamed from: a0 */
    public final boolean m213090a0() {
        return !((Observable) this).mObservers.isEmpty();
    }

    /* renamed from: a1 */
    public final void m213091a1() {
        for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
            ((iq0) ((Observable) this).mObservers.get(size)).mo209779a0();
        }
    }
}
