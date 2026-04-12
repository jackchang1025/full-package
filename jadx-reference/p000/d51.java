package p000;

import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class d51 {

    /* renamed from: a0 */
    public final String f55564a0;

    /* renamed from: a1 */
    public final String f55565a1;

    /* renamed from: a2 */
    public final String f55566a2;

    /* renamed from: a3 */
    public final List f55567a3;

    /* renamed from: a4 */
    public final List f55568a4;

    public d51(String str, String str2, String str3, List list, List list2) {
        t60.m214695b6(list, "columnNames");
        t60.m214695b6(list2, "referenceColumnNames");
        this.f55564a0 = str;
        this.f55565a1 = str2;
        this.f55566a2 = str3;
        this.f55567a3 = list;
        this.f55568a4 = list2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d51)) {
            return false;
        }
        d51 d51Var = (d51) obj;
        if (t60.m214686a2(this.f55564a0, d51Var.f55564a0) && t60.m214686a2(this.f55565a1, d51Var.f55565a1) && t60.m214686a2(this.f55566a2, d51Var.f55566a2) && t60.m214686a2(this.f55567a3, d51Var.f55567a3)) {
            return t60.m214686a2(this.f55568a4, d51Var.f55568a4);
        }
        return false;
    }

    public final int hashCode() {
        return this.f55568a4.hashCode() + ((this.f55567a3.hashCode() + tz0.m214801a1(tz0.m214801a1(this.f55564a0.hashCode() * 31, 31, this.f55565a1), 31, this.f55566a2)) * 31);
    }

    public final String toString() {
        return "ForeignKey{referenceTable='" + this.f55564a0 + "', onDelete='" + this.f55565a1 + " +', onUpdate='" + this.f55566a2 + "', columnNames=" + this.f55567a3 + ", referenceColumnNames=" + this.f55568a4 + '}';
    }
}
