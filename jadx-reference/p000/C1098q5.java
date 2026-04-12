package p000;

import android.graphics.RectF;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q5 */
/* loaded from: classes2.dex */
public final class C1098q5 implements InterfaceC0909nd {

    /* renamed from: a0 */
    public final InterfaceC0909nd f59385a0;

    /* renamed from: a1 */
    public final float f59386a1;

    public C1098q5(float f, InterfaceC0909nd interfaceC0909nd) {
        while (interfaceC0909nd instanceof C1098q5) {
            interfaceC0909nd = ((C1098q5) interfaceC0909nd).f59385a0;
            f += ((C1098q5) interfaceC0909nd).f59386a1;
        }
        this.f59385a0 = interfaceC0909nd;
        this.f59386a1 = f;
    }

    @Override // p000.InterfaceC0909nd
    /* renamed from: a0 */
    public final float mo212732a0(RectF rectF) {
        return Math.max(0.0f, this.f59385a0.mo212732a0(rectF) + this.f59386a1);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1098q5)) {
            return false;
        }
        C1098q5 c1098q5 = (C1098q5) obj;
        return this.f59385a0.equals(c1098q5.f59385a0) && this.f59386a1 == c1098q5.f59386a1;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.f59385a0, Float.valueOf(this.f59386a1)});
    }
}
