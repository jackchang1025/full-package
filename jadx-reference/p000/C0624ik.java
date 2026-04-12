package p000;

import android.graphics.RectF;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ik */
/* loaded from: classes2.dex */
public final class C0624ik implements InterfaceC0909nd {

    /* renamed from: a0 */
    public final float f56908a0;

    public C0624ik(float f) {
        this.f56908a0 = f;
    }

    @Override // p000.InterfaceC0909nd
    /* renamed from: a0 */
    public final float mo212732a0(RectF rectF) {
        return Math.min(this.f56908a0, Math.min(rectF.width() / 2.0f, rectF.height() / 2.0f));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0624ik) && this.f56908a0 == ((C0624ik) obj).f56908a0;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.f56908a0)});
    }
}
