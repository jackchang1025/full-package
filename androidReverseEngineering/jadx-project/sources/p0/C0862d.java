package p0;

import java.util.concurrent.TimeUnit;
import com.guard.wallet.entity.BuildConfig;
import t0.AbstractC0916e;

/* renamed from: p0.d */
/* loaded from: classes.dex */
public final class C0862d {

    /* renamed from: a */
    public final boolean f1755a;

    /* renamed from: b */
    public final boolean f1756b;

    /* renamed from: c */
    public final int f1757c;

    /* renamed from: d */
    public final int f1758d;

    /* renamed from: e */
    public final boolean f1759e;

    /* renamed from: f */
    public final boolean f1760f;

    /* renamed from: g */
    public final boolean f1761g;

    /* renamed from: h */
    public final int f1762h;

    /* renamed from: i */
    public final int f1763i;

    /* renamed from: j */
    public final boolean f1764j;

    /* renamed from: k */
    public final boolean f1765k;

    /* renamed from: l */
    public final boolean f1766l;

    /* renamed from: m */
    public String f1767m;

    static {
        C0861c c0861c = new C0861c();
        c0861c.f1744a = true;
        new C0862d(c0861c);
        C0861c c0861c2 = new C0861c();
        c0861c2.f1746c = true;
        long seconds = TimeUnit.SECONDS.toSeconds(Integer.MAX_VALUE);
        c0861c2.f1745b = seconds <= 2147483647L ? (int) seconds : Integer.MAX_VALUE;
        new C0862d(c0861c2);
    }

    public C0862d(C0861c c0861c) {
        this.f1755a = c0861c.f1744a;
        this.f1756b = false;
        this.f1757c = -1;
        this.f1758d = -1;
        this.f1759e = false;
        this.f1760f = false;
        this.f1761g = false;
        this.f1762h = c0861c.f1745b;
        this.f1763i = -1;
        this.f1764j = c0861c.f1746c;
        this.f1765k = false;
        this.f1766l = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0042  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C0862d m1244a(C0877s c0877s) {
        int i2;
        int i3;
        String str;
        char charAt;
        C0877s c0877s2 = c0877s;
        int length = c0877s2.f1896a.length / 2;
        int i4 = 0;
        boolean z2 = true;
        String str2 = null;
        boolean z3 = false;
        boolean z4 = false;
        int i5 = -1;
        int i6 = -1;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        int i7 = -1;
        int i8 = -1;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        while (i4 < length) {
            String m1281d = c0877s2.m1281d(i4);
            String m1283f = c0877s2.m1283f(i4);
            if (m1281d.equalsIgnoreCase("Cache-Control")) {
                if (str2 == null) {
                    str2 = m1283f;
                    for (i2 = 0; i2 < m1283f.length(); i2 = i3) {
                        int m1380e = AbstractC0916e.m1380e(m1283f, i2, "=,;");
                        String trim = m1283f.substring(i2, m1380e).trim();
                        if (m1380e == m1283f.length() || m1283f.charAt(m1380e) == ',' || m1283f.charAt(m1380e) == ';') {
                            i3 = m1380e + 1;
                            str = null;
                        } else {
                            while (true) {
                                m1380e++;
                                if (m1380e >= m1283f.length() || ((charAt = m1283f.charAt(m1380e)) != ' ' && charAt != '\t')) {
                                    break;
                                }
                            }
                            if (m1380e >= m1283f.length() || m1283f.charAt(m1380e) != '\"') {
                                i3 = AbstractC0916e.m1380e(m1283f, m1380e, ",;");
                                str = m1283f.substring(m1380e, i3).trim();
                            } else {
                                int i9 = m1380e + 1;
                                int m1380e2 = AbstractC0916e.m1380e(m1283f, i9, "\"");
                                str = m1283f.substring(i9, m1380e2);
                                i3 = m1380e2 + 1;
                            }
                        }
                        if ("no-cache".equalsIgnoreCase(trim)) {
                            z3 = true;
                        } else if ("no-store".equalsIgnoreCase(trim)) {
                            z4 = true;
                        } else if ("max-age".equalsIgnoreCase(trim)) {
                            i5 = AbstractC0916e.m1378c(-1, str);
                        } else if ("s-maxage".equalsIgnoreCase(trim)) {
                            i6 = AbstractC0916e.m1378c(-1, str);
                        } else if ("private".equalsIgnoreCase(trim)) {
                            z5 = true;
                        } else if ("public".equalsIgnoreCase(trim)) {
                            z6 = true;
                        } else if ("must-revalidate".equalsIgnoreCase(trim)) {
                            z7 = true;
                        } else if ("max-stale".equalsIgnoreCase(trim)) {
                            i7 = AbstractC0916e.m1378c(Integer.MAX_VALUE, str);
                        } else if ("min-fresh".equalsIgnoreCase(trim)) {
                            i8 = AbstractC0916e.m1378c(-1, str);
                        } else if ("only-if-cached".equalsIgnoreCase(trim)) {
                            z8 = true;
                        } else if ("no-transform".equalsIgnoreCase(trim)) {
                            z9 = true;
                        } else if ("immutable".equalsIgnoreCase(trim)) {
                            z10 = true;
                        }
                    }
                    i4++;
                    c0877s2 = c0877s;
                }
            } else if (!m1281d.equalsIgnoreCase("Pragma")) {
                i4++;
                c0877s2 = c0877s;
            }
            z2 = false;
            while (i2 < m1283f.length()) {
            }
            i4++;
            c0877s2 = c0877s;
        }
        return new C0862d(z3, z4, i5, i6, z5, z6, z7, i7, i8, z8, z9, z10, !z2 ? null : str2);
    }

    public final String toString() {
        String str = this.f1767m;
        if (str == null) {
            StringBuilder sb = new StringBuilder();
            if (this.f1755a) {
                sb.append("no-cache, ");
            }
            if (this.f1756b) {
                sb.append("no-store, ");
            }
            int i2 = this.f1757c;
            if (i2 != -1) {
                sb.append("max-age=");
                sb.append(i2);
                sb.append(", ");
            }
            int i3 = this.f1758d;
            if (i3 != -1) {
                sb.append("s-maxage=");
                sb.append(i3);
                sb.append(", ");
            }
            if (this.f1759e) {
                sb.append("private, ");
            }
            if (this.f1760f) {
                sb.append("public, ");
            }
            if (this.f1761g) {
                sb.append("must-revalidate, ");
            }
            int i4 = this.f1762h;
            if (i4 != -1) {
                sb.append("max-stale=");
                sb.append(i4);
                sb.append(", ");
            }
            int i5 = this.f1763i;
            if (i5 != -1) {
                sb.append("min-fresh=");
                sb.append(i5);
                sb.append(", ");
            }
            if (this.f1764j) {
                sb.append("only-if-cached, ");
            }
            if (this.f1765k) {
                sb.append("no-transform, ");
            }
            if (this.f1766l) {
                sb.append("immutable, ");
            }
            if (sb.length() == 0) {
                str = BuildConfig.FLAVOR;
            } else {
                sb.delete(sb.length() - 2, sb.length());
                str = sb.toString();
            }
            this.f1767m = str;
        }
        return str;
    }

    public C0862d(boolean z2, boolean z3, int i2, int i3, boolean z4, boolean z5, boolean z6, int i4, int i5, boolean z7, boolean z8, boolean z9, String str) {
        this.f1755a = z2;
        this.f1756b = z3;
        this.f1757c = i2;
        this.f1758d = i3;
        this.f1759e = z4;
        this.f1760f = z5;
        this.f1761g = z6;
        this.f1762h = i4;
        this.f1763i = i5;
        this.f1764j = z7;
        this.f1765k = z8;
        this.f1766l = z9;
        this.f1767m = str;
    }
}
