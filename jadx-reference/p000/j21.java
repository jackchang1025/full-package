package p000;

import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class j21 {

    /* renamed from: a0 */
    public final List f57256a0;

    public j21(List list) {
        this.f57256a0 = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof j21) && t60.m214686a2(this.f57256a0, ((j21) obj).f57256a0);
    }

    public final int hashCode() {
        int i = 110256243 * 31 * 923521;
        List list = this.f57256a0;
        return i + (list == null ? 0 : list.hashCode());
    }

    public final String toString() {
        return "StringCondition(fieldName=text, equals=null, contains=null, startsWith=null, endsWith=null, matches=null, inList=" + this.f57256a0 + ")";
    }
}
