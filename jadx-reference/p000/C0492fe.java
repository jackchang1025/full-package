package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fe */
/* loaded from: classes2.dex */
public final class C0492fe {

    /* renamed from: a0 */
    public final String f56201a0;

    /* renamed from: a1 */
    public final String f56202a1;

    /* renamed from: a2 */
    public final int f56203a2;

    public C0492fe(String str, int i, String str2) {
        this.f56201a0 = str;
        this.f56202a1 = str2;
        this.f56203a2 = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0492fe)) {
            return false;
        }
        C0492fe c0492fe = (C0492fe) obj;
        return this.f56201a0.equals(c0492fe.f56201a0) && this.f56202a1.equals(c0492fe.f56202a1) && this.f56203a2 == c0492fe.f56203a2;
    }

    public final int hashCode() {
        return ((Integer.hashCode(this.f56203a2) + tz0.m214801a1(this.f56201a0.hashCode() * 31, 31, this.f56202a1)) * 31) + 78159;
    }

    public final String toString() {
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("BrandNotificationConfig(title=", this.f56201a0, ", content=", this.f56202a1, ", iconResId=");
        sbM41c2.append(this.f56203a2);
        sbM41c2.append(", channelName=OFF)");
        return sbM41c2.toString();
    }
}
