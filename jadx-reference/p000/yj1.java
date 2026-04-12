package p000;

import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yj1 {

    /* renamed from: a0 */
    public final boolean f61327a0;

    /* renamed from: a1 */
    public final String f61328a1;

    /* renamed from: a2 */
    public final String f61329a2;

    /* renamed from: a3 */
    public final List f61330a3;

    /* renamed from: a4 */
    public final String f61331a4;

    /* renamed from: a5 */
    public final String f61332a5;

    public yj1(String str, boolean z, String str2) {
        EmptyList emptyList = EmptyList.f57568a0;
        t60.m214695b6(str2, "message");
        t60.m214695b6(emptyList, "attemptedMethods");
        this.f61327a0 = z;
        this.f61328a1 = str;
        this.f61329a2 = str2;
        this.f61330a3 = emptyList;
        this.f61331a4 = "";
        this.f61332a5 = "";
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof yj1)) {
            return false;
        }
        yj1 yj1Var = (yj1) obj;
        return this.f61327a0 == yj1Var.f61327a0 && t60.m214686a2(this.f61328a1, yj1Var.f61328a1) && t60.m214686a2(this.f61329a2, yj1Var.f61329a2) && t60.m214686a2(this.f61330a3, yj1Var.f61330a3) && t60.m214686a2(this.f61331a4, yj1Var.f61331a4) && t60.m214686a2(this.f61332a5, yj1Var.f61332a5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    public final int hashCode() {
        boolean z = this.f61327a0;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return this.f61332a5.hashCode() + tz0.m214801a1((this.f61330a3.hashCode() + tz0.m214801a1(tz0.m214801a1(r0 * 31, 31, this.f61328a1), 31, this.f61329a2)) * 31, 31, this.f61331a4);
    }

    public final String toString() {
        return "HideResult(success=" + this.f61327a0 + ", method=" + this.f61328a1 + ", message=" + this.f61329a2 + ", attemptedMethods=" + this.f61330a3 + ", deviceCompatibility=" + this.f61331a4 + ", recommendedAction=" + this.f61332a5 + ")";
    }
}
