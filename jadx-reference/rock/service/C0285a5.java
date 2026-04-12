package com.storm.safe.rock.service;

import android.graphics.Rect;
import p000.AbstractC0003a2;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a5 */
/* loaded from: classes2.dex */
public final class C0285a5 {

    /* renamed from: a0 */
    public final String f52344a0;

    /* renamed from: a1 */
    public final String f52345a1;

    /* renamed from: a2 */
    public final Rect f52346a2;

    /* renamed from: a3 */
    public final boolean f52347a3;

    /* renamed from: a4 */
    public final long f52348a4;

    public C0285a5(String str, String str2, Rect rect, boolean z, long j) {
        this.f52344a0 = str;
        this.f52345a1 = str2;
        this.f52346a2 = rect;
        this.f52347a3 = z;
        this.f52348a4 = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0285a5)) {
            return false;
        }
        C0285a5 c0285a5 = (C0285a5) obj;
        return t60.m214686a2(this.f52344a0, c0285a5.f52344a0) && t60.m214686a2(this.f52345a1, c0285a5.f52345a1) && t60.m214686a2(this.f52346a2, c0285a5.f52346a2) && this.f52347a3 == c0285a5.f52347a3 && this.f52348a4 == c0285a5.f52348a4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int iHashCode = (this.f52346a2.hashCode() + tz0.m214801a1(this.f52344a0.hashCode() * 31, 31, this.f52345a1)) * 31;
        boolean z = this.f52347a3;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return Long.hashCode(this.f52348a4) + ((iHashCode + i) * 31);
    }

    public final String toString() {
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("CachedSourceData(text=", this.f52344a0, ", desc=", this.f52345a1, ", rect=");
        sbM41c2.append(this.f52346a2);
        sbM41c2.append(", isVisible=");
        sbM41c2.append(this.f52347a3);
        sbM41c2.append(", timestamp=");
        sbM41c2.append(this.f52348a4);
        sbM41c2.append(")");
        return sbM41c2.toString();
    }
}
