package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l41 {

    /* renamed from: a0 */
    public final byte f57826a0;

    /* renamed from: a1 */
    public final byte f57827a1;

    /* renamed from: a2 */
    public final int f57828a2;

    public l41(byte b, byte b2, int i) {
        this.f57826a0 = b;
        this.f57827a1 = b2;
        this.f57828a2 = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l41)) {
            return false;
        }
        l41 l41Var = (l41) obj;
        return this.f57826a0 == l41Var.f57826a0 && this.f57827a1 == l41Var.f57827a1 && this.f57828a2 == l41Var.f57828a2;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f57828a2) + ((Byte.hashCode(this.f57827a1) + (Byte.hashCode(this.f57826a0) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("PairingHeader(version=", this.f57826a0, ", type=", this.f57827a1, ", payloadSize=");
        sbM38b9.append(this.f57828a2);
        sbM38b9.append(")");
        return sbM38b9.toString();
    }
}
