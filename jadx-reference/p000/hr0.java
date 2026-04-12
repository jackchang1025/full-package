package p000;

import android.graphics.RectF;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hr0 implements InterfaceC0909nd {

    /* renamed from: a0 */
    public final float f56750a0;

    public hr0(float f) {
        this.f56750a0 = f;
    }

    @Override // p000.InterfaceC0909nd
    /* renamed from: a0 */
    public final float mo212732a0(RectF rectF) {
        return Math.min(rectF.width(), rectF.height()) * this.f56750a0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof hr0) && this.f56750a0 == ((hr0) obj).f56750a0;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.f56750a0)});
    }
}
