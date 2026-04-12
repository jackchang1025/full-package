package p000;

import java.util.AbstractSet;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class h51 {

    /* renamed from: a4 */
    public static final c51 f56613a4 = new c51(null);

    /* renamed from: a0 */
    public final String f56614a0;

    /* renamed from: a1 */
    public final Object f56615a1;

    /* renamed from: a2 */
    public final Set f56616a2;

    /* renamed from: a3 */
    public final Set f56617a3;

    public h51(String str, Map map, AbstractSet abstractSet, AbstractSet abstractSet2) {
        t60.m214695b6(abstractSet, "foreignKeys");
        this.f56614a0 = str;
        this.f56615a1 = map;
        this.f56616a2 = abstractSet;
        this.f56617a3 = abstractSet2;
    }

    public final boolean equals(Object obj) {
        Set set;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h51)) {
            return false;
        }
        h51 h51Var = (h51) obj;
        if (!this.f56614a0.equals(h51Var.f56614a0) || !this.f56615a1.equals(h51Var.f56615a1) || !t60.m214686a2(this.f56616a2, h51Var.f56616a2)) {
            return false;
        }
        Set set2 = this.f56617a3;
        if (set2 == null || (set = h51Var.f56617a3) == null) {
            return true;
        }
        return set2.equals(set);
    }

    public final int hashCode() {
        return this.f56616a2.hashCode() + ((this.f56615a1.hashCode() + (this.f56614a0.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "TableInfo{name='" + this.f56614a0 + "', columns=" + this.f56615a1 + ", foreignKeys=" + this.f56616a2 + ", indices=" + this.f56617a3 + '}';
    }
}
