package p000;

import android.graphics.Rect;
import java.util.List;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hx */
/* loaded from: classes2.dex */
public final class C0598hx {

    /* renamed from: a0 */
    public final String f56760a0;

    /* renamed from: a1 */
    public final String f56761a1;

    /* renamed from: a2 */
    public final List f56762a2;

    /* renamed from: a3 */
    public final List f56763a3;

    /* renamed from: a4 */
    public final boolean f56764a4;

    /* renamed from: a5 */
    public final long f56765a5;

    /* renamed from: a6 */
    public final Rect f56766a6;

    /* renamed from: a7 */
    public final Rect f56767a7;

    /* renamed from: a8 */
    public final List f56768a8;

    public C0598hx(String str, String str2, List list, List list2, boolean z, long j, Rect rect, Rect rect2, List list3, int i) {
        list2 = (i & 8) != 0 ? null : list2;
        rect = (i & 64) != 0 ? null : rect;
        rect2 = (i & 128) != 0 ? null : rect2;
        list3 = (i & PSKKeyManager.MAX_KEY_LENGTH_BYTES) != 0 ? null : list3;
        t60.m214695b6(str, "cipherGradeCode");
        this.f56760a0 = str;
        this.f56761a1 = str2;
        this.f56762a2 = list;
        this.f56763a3 = list2;
        this.f56764a4 = z;
        this.f56765a5 = j;
        this.f56766a6 = rect;
        this.f56767a7 = rect2;
        this.f56768a8 = list3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0598hx)) {
            return false;
        }
        C0598hx c0598hx = (C0598hx) obj;
        return t60.m214686a2(this.f56760a0, c0598hx.f56760a0) && t60.m214686a2(this.f56761a1, c0598hx.f56761a1) && t60.m214686a2(this.f56762a2, c0598hx.f56762a2) && t60.m214686a2(this.f56763a3, c0598hx.f56763a3) && this.f56764a4 == c0598hx.f56764a4 && this.f56765a5 == c0598hx.f56765a5 && t60.m214686a2(this.f56766a6, c0598hx.f56766a6) && t60.m214686a2(this.f56767a7, c0598hx.f56767a7) && t60.m214686a2(this.f56768a8, c0598hx.f56768a8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int iHashCode = this.f56760a0.hashCode() * 31;
        String str = this.f56761a1;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        List list = this.f56762a2;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        List list2 = this.f56763a3;
        int iHashCode4 = (iHashCode3 + (list2 == null ? 0 : list2.hashCode())) * 31;
        boolean z = this.f56764a4;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int iHashCode5 = (Long.hashCode(this.f56765a5) + ((iHashCode4 + i) * 31)) * 31;
        Rect rect = this.f56766a6;
        int iHashCode6 = (iHashCode5 + (rect == null ? 0 : rect.hashCode())) * 31;
        Rect rect2 = this.f56767a7;
        int iHashCode7 = (iHashCode6 + (rect2 == null ? 0 : rect2.hashCode())) * 31;
        List list3 = this.f56768a8;
        return (iHashCode7 + (list3 != null ? list3.hashCode() : 0)) * 31;
    }

    public final String toString() {
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("CipherData(cipherGradeCode=", this.f56760a0, ", textCipher=", this.f56761a1, ", patternCipher=");
        sbM41c2.append(this.f56762a2);
        sbM41c2.append(", patternScreenPoints=");
        sbM41c2.append(this.f56763a3);
        sbM41c2.append(", isLocked=");
        sbM41c2.append(this.f56764a4);
        sbM41c2.append(", captureTime=");
        sbM41c2.append(this.f56765a5);
        sbM41c2.append(", boundsInScreen=");
        sbM41c2.append(this.f56766a6);
        sbM41c2.append(", boundsInParent=");
        sbM41c2.append(this.f56767a7);
        sbM41c2.append(", touchCipher=");
        sbM41c2.append(this.f56768a8);
        sbM41c2.append(", eventCipher=null)");
        return sbM41c2.toString();
    }
}
