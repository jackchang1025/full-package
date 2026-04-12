package p000;

import android.graphics.RectF;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: f3 */
/* loaded from: classes2.dex */
public final class C0481f3 implements InterfaceC0909nd {

    /* renamed from: a0 */
    public final float f56140a0;

    public C0481f3(float f) {
        this.f56140a0 = f;
    }

    @Override // p000.InterfaceC0909nd
    /* renamed from: a0 */
    public final float mo212732a0(RectF rectF) {
        return this.f56140a0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0481f3) && this.f56140a0 == ((C0481f3) obj).f56140a0;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.f56140a0)});
    }
}
