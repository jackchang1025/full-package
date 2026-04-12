package p000;

import java.util.ArrayList;
import java.util.List;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class g51 {

    /* renamed from: a0 */
    public final String f56409a0;

    /* renamed from: a1 */
    public final boolean f56410a1;

    /* renamed from: a2 */
    public final List f56411a2;

    /* renamed from: a3 */
    public final List f56412a3;

    static {
        new f51(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.Object, java.util.Collection, java.util.List] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.util.ArrayList] */
    public g51(String str, boolean z, List list, List list2) {
        t60.m214695b6(list, "columns");
        t60.m214695b6(list2, "orders");
        this.f56409a0 = str;
        this.f56410a1 = z;
        this.f56411a2 = list;
        this.f56412a3 = list2;
        if (list2.isEmpty()) {
            int size = list.size();
            list2 = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                list2.add("ASC");
            }
        }
        this.f56412a3 = list2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof g51) {
            g51 g51Var = (g51) obj;
            String str = g51Var.f56409a0;
            if (this.f56410a1 == g51Var.f56410a1 && t60.m214686a2(this.f56411a2, g51Var.f56411a2) && t60.m214686a2(this.f56412a3, g51Var.f56412a3)) {
                String str2 = this.f56409a0;
                return AbstractC0779a1.m213679d2(str2, false, "index_") ? AbstractC0779a1.m213679d2(str, false, "index_") : str2.equals(str);
            }
        }
        return false;
    }

    public final int hashCode() {
        String str = this.f56409a0;
        return this.f56412a3.hashCode() + ((this.f56411a2.hashCode() + ((((AbstractC0779a1.m213679d2(str, false, "index_") ? -1184239155 : str.hashCode()) * 31) + (this.f56410a1 ? 1 : 0)) * 31)) * 31);
    }

    public final String toString() {
        return "Index{name='" + this.f56409a0 + "', unique=" + this.f56410a1 + ", columns=" + this.f56411a2 + ", orders=" + this.f56412a3 + "'}";
    }
}
