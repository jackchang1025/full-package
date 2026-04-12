package p000;

import android.net.Uri;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lt */
/* loaded from: classes2.dex */
public final class C0834lt {

    /* renamed from: a0 */
    public final Uri f58174a0;

    /* renamed from: a1 */
    public final boolean f58175a1;

    public C0834lt(boolean z, Uri uri) {
        this.f58174a0 = uri;
        this.f58175a1 = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!C0834lt.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        t60.m214693b4(obj, "null cannot be cast to non-null type androidx.work.Constraints.ContentUriTrigger");
        C0834lt c0834lt = (C0834lt) obj;
        return t60.m214686a2(this.f58174a0, c0834lt.f58174a0) && this.f58175a1 == c0834lt.f58175a1;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.f58175a1) + (this.f58174a0.hashCode() * 31);
    }
}
