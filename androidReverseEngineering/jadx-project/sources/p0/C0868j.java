package p0;

/* renamed from: p0.j */
/* loaded from: classes.dex */
public final class C0868j {

    /* renamed from: a */
    public final boolean f1825a;

    /* renamed from: b */
    public String[] f1826b;

    /* renamed from: c */
    public String[] f1827c;

    /* renamed from: d */
    public boolean f1828d;

    public C0868j(C0869k c0869k) {
        this.f1825a = c0869k.f1844a;
        this.f1826b = c0869k.f1846c;
        this.f1827c = c0869k.f1847d;
        this.f1828d = c0869k.f1845b;
    }

    /* renamed from: a */
    public final void m1261a(String... strArr) {
        if (!this.f1825a) {
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }
        if (strArr.length == 0) {
            throw new IllegalArgumentException("At least one cipher suite is required");
        }
        this.f1826b = (String[]) strArr.clone();
    }

    /* renamed from: b */
    public final void m1262b(C0867i... c0867iArr) {
        if (!this.f1825a) {
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }
        String[] strArr = new String[c0867iArr.length];
        for (int i2 = 0; i2 < c0867iArr.length; i2++) {
            strArr[i2] = c0867iArr[i2].f1811a;
        }
        m1261a(strArr);
    }

    /* renamed from: c */
    public final void m1263c(String... strArr) {
        if (!this.f1825a) {
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
        if (strArr.length == 0) {
            throw new IllegalArgumentException("At least one TLS version is required");
        }
        this.f1827c = (String[]) strArr.clone();
    }

    /* renamed from: d */
    public final void m1264d(n0... n0VarArr) {
        if (!this.f1825a) {
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
        String[] strArr = new String[n0VarArr.length];
        for (int i2 = 0; i2 < n0VarArr.length; i2++) {
            strArr[i2] = n0VarArr[i2].f1884a;
        }
        m1263c(strArr);
    }

    public C0868j(boolean z2) {
        this.f1825a = z2;
    }
}
