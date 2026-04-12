package p000;

import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class aa1 {

    /* renamed from: a0 */
    public final String f56a0;

    /* renamed from: a1 */
    public final ArrayList f57a1;

    /* renamed from: a2 */
    public final String f58a2;

    public aa1(String str, ArrayList arrayList, String str2) {
        this.f56a0 = str;
        this.f57a1 = arrayList;
        this.f58a2 = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof aa1)) {
            return false;
        }
        aa1 aa1Var = (aa1) obj;
        return this.f56a0.equals(aa1Var.f56a0) && this.f57a1.equals(aa1Var.f57a1) && this.f58a2.equals(aa1Var.f58a2);
    }

    public final int hashCode() {
        return this.f58a2.hashCode() + ((this.f57a1.hashCode() + (this.f56a0.hashCode() * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CacheRule(packageName=");
        sb.append(this.f56a0);
        sb.append(", winClasses=");
        sb.append(this.f57a1);
        sb.append(", appName=");
        return AbstractC0003a2.m35b6(sb, this.f58a2, ")");
    }
}
