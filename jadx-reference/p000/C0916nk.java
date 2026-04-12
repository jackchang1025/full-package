package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nk */
/* loaded from: classes2.dex */
public final class C0916nk extends AbstractC0483f5 {

    /* renamed from: a2 */
    public static final C1351vv f58640a2 = new C1351vv(18);

    /* renamed from: a1 */
    public final String f58641a1;

    public C0916nk(String str) {
        super(f58640a2);
        this.f58641a1 = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0916nk) && t60.m214686a2(this.f58641a1, ((C0916nk) obj).f58641a1);
    }

    public final int hashCode() {
        return this.f58641a1.hashCode();
    }

    public final String toString() {
        return "CoroutineName(" + this.f58641a1 + ')';
    }
}
