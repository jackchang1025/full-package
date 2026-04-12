package p000;

import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dd0 {

    /* renamed from: a0 */
    public final boolean f55699a0;

    /* renamed from: a1 */
    public final boolean f55700a1;

    /* renamed from: a2 */
    public final String f55701a2;

    /* renamed from: a3 */
    public final String f55702a3;

    /* renamed from: a4 */
    public final String f55703a4;

    /* renamed from: a5 */
    public final String f55704a5;

    /* renamed from: a6 */
    public final String f55705a6;

    /* renamed from: a7 */
    public final String f55706a7;

    /* renamed from: a8 */
    public final List f55707a8;

    /* renamed from: a9 */
    public final boolean f55708a9;

    public dd0(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, List list, boolean z3) {
        t60.m214695b6(list, "loadingTips");
        this.f55699a0 = z;
        this.f55700a1 = z2;
        this.f55701a2 = str;
        this.f55702a3 = str2;
        this.f55703a4 = str3;
        this.f55704a5 = str4;
        this.f55705a6 = str5;
        this.f55706a7 = str6;
        this.f55707a8 = list;
        this.f55708a9 = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof dd0)) {
            return false;
        }
        dd0 dd0Var = (dd0) obj;
        return this.f55699a0 == dd0Var.f55699a0 && this.f55700a1 == dd0Var.f55700a1 && this.f55701a2.equals(dd0Var.f55701a2) && this.f55702a3.equals(dd0Var.f55702a3) && this.f55703a4.equals(dd0Var.f55703a4) && this.f55704a5.equals(dd0Var.f55704a5) && this.f55705a6.equals(dd0Var.f55705a6) && this.f55706a7.equals(dd0Var.f55706a7) && t60.m214686a2(this.f55707a8, dd0Var.f55707a8) && this.f55708a9 == dd0Var.f55708a9;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    public final int hashCode() {
        boolean z = this.f55699a0;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        int i = r1 * 31;
        ?? r3 = this.f55700a1;
        int i2 = r3;
        if (r3 != 0) {
            i2 = 1;
        }
        int iHashCode = (this.f55707a8.hashCode() + tz0.m214801a1((((((this.f55705a6.hashCode() + tz0.m214801a1(tz0.m214801a1(tz0.m214801a1(tz0.m214801a1((i + i2) * 31, 31, this.f55701a2), 31, this.f55702a3), 31, this.f55703a4), 31, this.f55704a5)) * 31) + 1) * 31) + 1) * 31, 31, this.f55706a7)) * 31;
        boolean z2 = this.f55708a9;
        return iHashCode + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        return "MaskConfig(enableMask=" + this.f55699a0 + ", enableProgressBar=" + this.f55700a1 + ", maskText=" + this.f55701a2 + ", maskSubtitle=" + this.f55702a3 + ", maskStatus=" + this.f55703a4 + ", maskTextColor=" + this.f55704a5 + ", maskSubtitleColor=" + this.f55705a6 + ", autoRestart=true, preventUserInteraction=true, maskStyle=" + this.f55706a7 + ", loadingTips=" + this.f55707a8 + ", showAppIcon=" + this.f55708a9 + ")";
    }
}
