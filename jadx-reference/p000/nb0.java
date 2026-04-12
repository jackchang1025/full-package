package p000;

import java.util.ArrayList;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class nb0 {

    /* renamed from: a0 */
    public final String f58486a0;

    /* renamed from: a1 */
    public final String f58487a1;

    /* renamed from: a2 */
    public final Set f58488a2;

    /* renamed from: a3 */
    public final ArrayList f58489a3;

    static {
        new mb0(null);
    }

    public nb0(String str, String str2, Set set, ArrayList arrayList) {
        this.f58486a0 = str;
        this.f58487a1 = str2;
        this.f58488a2 = set;
        this.f58489a3 = arrayList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof nb0)) {
            return false;
        }
        nb0 nb0Var = (nb0) obj;
        return t60.m214686a2(this.f58486a0, nb0Var.f58486a0) && t60.m214686a2(this.f58487a1, nb0Var.f58487a1) && t60.m214686a2(this.f58488a2, nb0Var.f58488a2) && t60.m214686a2(this.f58489a3, nb0Var.f58489a3);
    }

    public final int hashCode() {
        String str = this.f58486a0;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.f58487a1;
        return this.f58489a3.hashCode() + ((this.f58488a2.hashCode() + ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("ListenWindow(packageName=", this.f58486a0, ", className=", this.f58487a1, ", eventTypes=");
        sbM41c2.append(this.f58488a2);
        sbM41c2.append(", matchFilters=");
        sbM41c2.append(this.f58489a3);
        sbM41c2.append(")");
        return sbM41c2.toString();
    }

    public /* synthetic */ nb0(String str, String str2, Set set) {
        this(str, str2, set, new ArrayList());
    }
}
