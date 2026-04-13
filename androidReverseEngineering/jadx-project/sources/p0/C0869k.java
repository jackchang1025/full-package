package p0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLSocket;
import q0.AbstractC0887c;

/* renamed from: p0.k */
/* loaded from: classes.dex */
public final class C0869k {

    /* renamed from: e */
    public static final C0869k f1842e;

    /* renamed from: f */
    public static final C0869k f1843f;

    /* renamed from: a */
    public final boolean f1844a;

    /* renamed from: b */
    public final boolean f1845b;

    /* renamed from: c */
    public final String[] f1846c;

    /* renamed from: d */
    public final String[] f1847d;

    static {
        C0867i c0867i = C0867i.f1808q;
        C0867i c0867i2 = C0867i.f1809r;
        C0867i c0867i3 = C0867i.f1810s;
        C0867i c0867i4 = C0867i.f1802k;
        C0867i c0867i5 = C0867i.f1804m;
        C0867i c0867i6 = C0867i.f1803l;
        C0867i c0867i7 = C0867i.f1805n;
        C0867i c0867i8 = C0867i.f1807p;
        C0867i c0867i9 = C0867i.f1806o;
        C0867i[] c0867iArr = {c0867i, c0867i2, c0867i3, c0867i4, c0867i5, c0867i6, c0867i7, c0867i8, c0867i9};
        C0867i[] c0867iArr2 = {c0867i, c0867i2, c0867i3, c0867i4, c0867i5, c0867i6, c0867i7, c0867i8, c0867i9, C0867i.f1800i, C0867i.f1801j, C0867i.f1798g, C0867i.f1799h, C0867i.f1796e, C0867i.f1797f, C0867i.f1795d};
        C0868j c0868j = new C0868j(true);
        c0868j.m1262b(c0867iArr);
        n0 n0Var = n0.TLS_1_3;
        n0 n0Var2 = n0.TLS_1_2;
        c0868j.m1264d(n0Var, n0Var2);
        c0868j.f1828d = true;
        new C0869k(c0868j);
        C0868j c0868j2 = new C0868j(true);
        c0868j2.m1262b(c0867iArr2);
        c0868j2.m1264d(n0Var, n0Var2);
        c0868j2.f1828d = true;
        f1842e = new C0869k(c0868j2);
        C0868j c0868j3 = new C0868j(true);
        c0868j3.m1262b(c0867iArr2);
        c0868j3.m1264d(n0Var, n0Var2, n0.TLS_1_1, n0.TLS_1_0);
        c0868j3.f1828d = true;
        new C0869k(c0868j3);
        f1843f = new C0869k(new C0868j(false));
    }

    public C0869k(C0868j c0868j) {
        this.f1844a = c0868j.f1825a;
        this.f1846c = c0868j.f1826b;
        this.f1847d = c0868j.f1827c;
        this.f1845b = c0868j.f1828d;
    }

    /* renamed from: a */
    public final boolean m1266a(SSLSocket sSLSocket) {
        if (!this.f1844a) {
            return false;
        }
        String[] strArr = this.f1847d;
        if (strArr != null && !AbstractC0887c.m1318o(AbstractC0887c.f1942i, strArr, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        String[] strArr2 = this.f1846c;
        return strArr2 == null || AbstractC0887c.m1318o(C0867i.f1793b, strArr2, sSLSocket.getEnabledCipherSuites());
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0869k)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        C0869k c0869k = (C0869k) obj;
        boolean z2 = c0869k.f1844a;
        boolean z3 = this.f1844a;
        if (z3 != z2) {
            return false;
        }
        return !z3 || (Arrays.equals(this.f1846c, c0869k.f1846c) && Arrays.equals(this.f1847d, c0869k.f1847d) && this.f1845b == c0869k.f1845b);
    }

    public final int hashCode() {
        if (this.f1844a) {
            return ((((527 + Arrays.hashCode(this.f1846c)) * 31) + Arrays.hashCode(this.f1847d)) * 31) + (!this.f1845b ? 1 : 0);
        }
        return 17;
    }

    public final String toString() {
        List list;
        if (!this.f1844a) {
            return "ConnectionSpec()";
        }
        StringBuilder sb = new StringBuilder("ConnectionSpec(cipherSuites=");
        List list2 = null;
        String[] strArr = this.f1846c;
        if (strArr != null) {
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str : strArr) {
                arrayList.add(C0867i.m1257a(str));
            }
            list = Collections.unmodifiableList(arrayList);
        } else {
            list = null;
        }
        sb.append(Objects.toString(list, "[all enabled]"));
        sb.append(", tlsVersions=");
        String[] strArr2 = this.f1847d;
        if (strArr2 != null) {
            ArrayList arrayList2 = new ArrayList(strArr2.length);
            for (String str2 : strArr2) {
                arrayList2.add(n0.m1272a(str2));
            }
            list2 = Collections.unmodifiableList(arrayList2);
        }
        sb.append(Objects.toString(list2, "[all enabled]"));
        sb.append(", supportsTlsExtensions=");
        sb.append(this.f1845b);
        sb.append(")");
        return sb.toString();
    }
}
