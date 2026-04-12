package p000;

import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class i41 {

    /* renamed from: a0 */
    public final int f56794a0;

    /* renamed from: a1 */
    public final int f56795a1;

    /* renamed from: a2 */
    public final int f56796a2;

    /* renamed from: a3 */
    public final byte[] f56797a3;

    public i41(int i, byte[] bArr, int i2, int i3) {
        this.f56794a0 = i;
        this.f56795a1 = i2;
        this.f56796a2 = i3;
        this.f56797a3 = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof i41)) {
            return false;
        }
        i41 i41Var = (i41) obj;
        return this.f56794a0 == i41Var.f56794a0 && this.f56795a1 == i41Var.f56795a1 && this.f56796a2 == i41Var.f56796a2 && t60.m214686a2(this.f56797a3, i41Var.f56797a3);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.f56797a3) + tz0.m214800a0(this.f56796a2, tz0.m214800a0(this.f56795a1, Integer.hashCode(this.f56794a0) * 31, 31), 31);
    }

    public final String toString() {
        String string = Arrays.toString(this.f56797a3);
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("AdbTransportMessage(command=", this.f56794a0, ", arg0=", this.f56795a1, ", arg1=");
        sbM38b9.append(this.f56796a2);
        sbM38b9.append(", data=");
        sbM38b9.append(string);
        sbM38b9.append(")");
        return sbM38b9.toString();
    }
}
