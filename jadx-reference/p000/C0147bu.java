package p000;

import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bu */
/* loaded from: classes2.dex */
public final class C0147bu {

    /* renamed from: a0 */
    public final boolean f46000a0;

    /* renamed from: a1 */
    public final List f46001a1;

    /* renamed from: a2 */
    public final List f46002a2;

    /* renamed from: a3 */
    public final List f46003a3;

    /* renamed from: a4 */
    public final List f46004a4;

    public C0147bu(boolean z, List list, List list2, List list3) {
        EmptyList emptyList = EmptyList.f57568a0;
        t60.m214695b6(list, "completedRoutes");
        t60.m214695b6(list2, "failedRoutes");
        t60.m214695b6(list3, "warnings");
        t60.m214695b6(emptyList, "nextSteps");
        this.f46000a0 = z;
        this.f46001a1 = list;
        this.f46002a2 = list2;
        this.f46003a3 = list3;
        this.f46004a4 = emptyList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0147bu)) {
            return false;
        }
        C0147bu c0147bu = (C0147bu) obj;
        return this.f46000a0 == c0147bu.f46000a0 && t60.m214686a2(this.f46001a1, c0147bu.f46001a1) && t60.m214686a2(this.f46002a2, c0147bu.f46002a2) && t60.m214686a2(this.f46003a3, c0147bu.f46003a3) && t60.m214686a2(this.f46004a4, c0147bu.f46004a4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    public final int hashCode() {
        boolean z = this.f46000a0;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return this.f46004a4.hashCode() + ((this.f46003a3.hashCode() + ((this.f46002a2.hashCode() + ((this.f46001a1.hashCode() + (r0 * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "AuthorizationResult(success=" + this.f46000a0 + ", completedRoutes=" + this.f46001a1 + ", failedRoutes=" + this.f46002a2 + ", warnings=" + this.f46003a3 + ", nextSteps=" + this.f46004a4 + ")";
    }
}
