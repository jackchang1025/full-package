package p000;

import android.view.ViewGroup;
import android.view.WindowId;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cf1 {

    /* renamed from: a0 */
    public final WindowId f46132a0;

    public cf1(ViewGroup viewGroup) {
        this.f46132a0 = viewGroup.getWindowId();
    }

    public final boolean equals(Object obj) {
        return (obj instanceof cf1) && ((cf1) obj).f46132a0.equals(this.f46132a0);
    }

    public final int hashCode() {
        return this.f46132a0.hashCode();
    }
}
