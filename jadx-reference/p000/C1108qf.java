package p000;

import java.util.LinkedHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qf */
/* loaded from: classes2.dex */
public final class C1108qf {

    /* renamed from: a0 */
    public final String f59490a0;

    /* renamed from: a1 */
    public final LinkedHashMap f59491a1;

    public C1108qf(String str, LinkedHashMap linkedHashMap) {
        this.f59490a0 = str;
        this.f59491a1 = linkedHashMap;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1108qf)) {
            return false;
        }
        C1108qf c1108qf = (C1108qf) obj;
        return this.f59490a0.equals(c1108qf.f59490a0) && this.f59491a1.equals(c1108qf.f59491a1);
    }

    public final int hashCode() {
        return this.f59491a1.hashCode() + (this.f59490a0.hashCode() * 31);
    }

    public final String toString() {
        return "RemoteCommand(command=" + this.f59490a0 + ", params=" + this.f59491a1 + ")";
    }
}
