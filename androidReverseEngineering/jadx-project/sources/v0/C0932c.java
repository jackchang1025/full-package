package v0;

import a1.C0017h;
import q0.AbstractC0887c;

/* renamed from: v0.c */
/* loaded from: classes.dex */
public final class C0932c {

    /* renamed from: d */
    public static final C0017h f2134d = C0017h.m118d(":");

    /* renamed from: e */
    public static final C0017h f2135e = C0017h.m118d(":status");

    /* renamed from: f */
    public static final C0017h f2136f = C0017h.m118d(":method");

    /* renamed from: g */
    public static final C0017h f2137g = C0017h.m118d(":path");

    /* renamed from: h */
    public static final C0017h f2138h = C0017h.m118d(":scheme");

    /* renamed from: i */
    public static final C0017h f2139i = C0017h.m118d(":authority");

    /* renamed from: a */
    public final C0017h f2140a;

    /* renamed from: b */
    public final C0017h f2141b;

    /* renamed from: c */
    public final int f2142c;

    public C0932c(C0017h c0017h, C0017h c0017h2) {
        this.f2140a = c0017h;
        this.f2141b = c0017h2;
        this.f2142c = c0017h2.mo125j() + c0017h.mo125j() + 32;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0932c)) {
            return false;
        }
        C0932c c0932c = (C0932c) obj;
        return this.f2140a.equals(c0932c.f2140a) && this.f2141b.equals(c0932c.f2141b);
    }

    public final int hashCode() {
        return this.f2141b.hashCode() + ((this.f2140a.hashCode() + 527) * 31);
    }

    public final String toString() {
        return AbstractC0887c.m1312i(new Object[]{this.f2140a.mo128m(), this.f2141b.mo128m()}, "%s: %s");
    }

    public C0932c(C0017h c0017h, String str) {
        this(c0017h, C0017h.m118d(str));
    }

    public C0932c(String str, String str2) {
        this(C0017h.m118d(str), C0017h.m118d(str2));
    }
}
