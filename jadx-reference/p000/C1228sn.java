package p000;

import android.os.Build;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sn */
/* loaded from: classes2.dex */
public final class C1228sn {

    /* renamed from: a0 */
    public final String f60017a0;

    /* renamed from: a1 */
    public final String f60018a1;

    /* renamed from: a2 */
    public final String f60019a2;

    /* renamed from: a3 */
    public final int f60020a3;

    /* renamed from: a4 */
    public final String f60021a4;

    /* renamed from: a5 */
    public final String f60022a5;

    /* renamed from: a6 */
    public final int f60023a6;

    /* renamed from: a7 */
    public final boolean f60024a7;

    /* renamed from: a8 */
    public final int f60025a8;

    /* renamed from: a9 */
    public final int f60026a9;

    /* renamed from: b0 */
    public final long f60027b0;

    /* renamed from: b1 */
    public final boolean f60028b1;

    /* renamed from: b2 */
    public final String f60029b2;

    /* renamed from: b3 */
    public final String f60030b3;

    public C1228sn(String str, String str2, String str3, int i, String str4, String str5, int i2, boolean z, int i3, int i4, long j, boolean z2, String str6, String str7) {
        String str8 = Build.MODEL;
        String str9 = Build.MANUFACTURER;
        String str10 = Build.VERSION.RELEASE;
        t60.m214695b6(str2, "deviceName");
        t60.m214695b6(str8, "model");
        t60.m214695b6(str9, "manufacturer");
        t60.m214695b6(str10, "osVersion");
        t60.m214695b6(str4, "appName");
        t60.m214695b6(str6, "phoneNumber");
        this.f60017a0 = str;
        this.f60018a1 = str2;
        this.f60019a2 = str3;
        this.f60020a3 = i;
        this.f60021a4 = str4;
        this.f60022a5 = str5;
        this.f60023a6 = i2;
        this.f60024a7 = z;
        this.f60025a8 = i3;
        this.f60026a9 = i4;
        this.f60027b0 = j;
        this.f60028b1 = z2;
        this.f60029b2 = str6;
        this.f60030b3 = str7;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1228sn)) {
            return false;
        }
        C1228sn c1228sn = (C1228sn) obj;
        if (!this.f60017a0.equals(c1228sn.f60017a0) || !t60.m214686a2(this.f60018a1, c1228sn.f60018a1)) {
            return false;
        }
        String str = Build.MODEL;
        if (!t60.m214686a2(str, str) || !this.f60019a2.equals(c1228sn.f60019a2)) {
            return false;
        }
        String str2 = Build.MANUFACTURER;
        if (!t60.m214686a2(str2, str2)) {
            return false;
        }
        String str3 = Build.VERSION.RELEASE;
        return t60.m214686a2(str3, str3) && this.f60020a3 == c1228sn.f60020a3 && t60.m214686a2(this.f60021a4, c1228sn.f60021a4) && this.f60022a5.equals(c1228sn.f60022a5) && this.f60023a6 == c1228sn.f60023a6 && this.f60024a7 == c1228sn.f60024a7 && this.f60025a8 == c1228sn.f60025a8 && this.f60026a9 == c1228sn.f60026a9 && this.f60027b0 == c1228sn.f60027b0 && this.f60028b1 == c1228sn.f60028b1 && t60.m214686a2(this.f60029b2, c1228sn.f60029b2) && this.f60030b3.equals(c1228sn.f60030b3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int iM214800a0 = tz0.m214800a0(this.f60023a6, tz0.m214801a1(tz0.m214801a1(tz0.m214800a0(this.f60020a3, tz0.m214801a1(tz0.m214801a1(tz0.m214801a1(tz0.m214801a1(tz0.m214801a1(this.f60017a0.hashCode() * 31, 31, this.f60018a1), 31, Build.MODEL), 31, this.f60019a2), 31, Build.MANUFACTURER), 31, Build.VERSION.RELEASE), 31), 31, this.f60021a4), 31, this.f60022a5), 31);
        boolean z = this.f60024a7;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int iHashCode = (Long.hashCode(this.f60027b0) + tz0.m214800a0(this.f60026a9, tz0.m214800a0(this.f60025a8, (iM214800a0 + i) * 31, 31), 31)) * 31;
        boolean z2 = this.f60028b1;
        return this.f60030b3.hashCode() + tz0.m214801a1((iHashCode + (z2 ? 1 : z2 ? 1 : 0)) * 31, 31, this.f60029b2);
    }

    public final String toString() {
        String str = Build.MODEL;
        String str2 = Build.MANUFACTURER;
        String str3 = Build.VERSION.RELEASE;
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("DeviceInfo(deviceId=", this.f60017a0, ", deviceName=", this.f60018a1, ", model=");
        sbM41c2.append(str);
        sbM41c2.append(", brand=");
        sbM41c2.append(this.f60019a2);
        sbM41c2.append(", manufacturer=");
        sbM41c2.append(str2);
        sbM41c2.append(", osVersion=");
        sbM41c2.append(str3);
        sbM41c2.append(", sdkVersion=");
        sbM41c2.append(this.f60020a3);
        sbM41c2.append(", appName=");
        sbM41c2.append(this.f60021a4);
        sbM41c2.append(", appVersion=");
        sbM41c2.append(this.f60022a5);
        sbM41c2.append(", batteryLevel=");
        sbM41c2.append(this.f60023a6);
        sbM41c2.append(", isCharging=");
        sbM41c2.append(this.f60024a7);
        sbM41c2.append(", screenWidth=");
        sbM41c2.append(this.f60025a8);
        sbM41c2.append(", screenHeight=");
        sbM41c2.append(this.f60026a9);
        sbM41c2.append(", firstInstallTime=");
        sbM41c2.append(this.f60027b0);
        sbM41c2.append(", hasSim=");
        sbM41c2.append(this.f60028b1);
        sbM41c2.append(", phoneNumber=");
        sbM41c2.append(this.f60029b2);
        sbM41c2.append(", phoneNumber2=");
        sbM41c2.append(this.f60030b3);
        sbM41c2.append(")");
        return sbM41c2.toString();
    }
}
