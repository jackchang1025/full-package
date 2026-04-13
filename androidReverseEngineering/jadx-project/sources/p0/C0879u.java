package p0;

import a1.C0014e;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.guard.wallet.entity.BuildConfig;
import q0.AbstractC0887c;

/* renamed from: p0.u */
/* loaded from: classes.dex */
public final class C0879u {

    /* renamed from: i */
    public static final char[] f1906i = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a */
    public final String f1907a;

    /* renamed from: b */
    public final String f1908b;

    /* renamed from: c */
    public final String f1909c;

    /* renamed from: d */
    public final String f1910d;

    /* renamed from: e */
    public final int f1911e;

    /* renamed from: f */
    public final List f1912f;

    /* renamed from: g */
    public final String f1913g;

    /* renamed from: h */
    public final String f1914h;

    public C0879u(C0878t c0878t) {
        this.f1907a = (String) c0878t.f1901e;
        String str = (String) c0878t.f1902f;
        this.f1908b = m1289i(str, 0, str.length(), false);
        String str2 = (String) c0878t.f1903g;
        this.f1909c = m1289i(str2, 0, str2.length(), false);
        this.f1910d = (String) c0878t.f1904h;
        int i2 = c0878t.f1899c;
        this.f1911e = i2 == -1 ? m1288c((String) c0878t.f1901e) : i2;
        m1290j(false, c0878t.f1898b);
        List list = c0878t.f1900d;
        this.f1912f = list != null ? m1290j(true, list) : null;
        String str3 = (String) c0878t.f1905i;
        this.f1913g = str3 != null ? m1289i(str3, 0, str3.length(), false) : null;
        this.f1914h = c0878t.toString();
    }

    /* renamed from: a */
    public static String m1286a(String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        int i4 = i2;
        while (i4 < i3) {
            int codePointAt = str.codePointAt(i4);
            if (codePointAt < 32 || codePointAt == 127 || ((codePointAt >= 128 && z5) || str2.indexOf(codePointAt) != -1 || ((codePointAt == 37 && (!z2 || (z3 && !m1291k(str, i4, i3)))) || (codePointAt == 43 && z4)))) {
                C0014e c0014e = new C0014e();
                c0014e.m91O(str, i2, i4);
                C0014e c0014e2 = null;
                while (i4 < i3) {
                    int codePointAt2 = str.codePointAt(i4);
                    if (!z2 || (codePointAt2 != 9 && codePointAt2 != 10 && codePointAt2 != 12 && codePointAt2 != 13)) {
                        if (codePointAt2 == 43 && z4) {
                            String str3 = z2 ? "+" : "%2B";
                            c0014e.m91O(str3, 0, str3.length());
                        } else if (codePointAt2 < 32 || codePointAt2 == 127 || ((codePointAt2 >= 128 && z5) || str2.indexOf(codePointAt2) != -1 || (codePointAt2 == 37 && (!z2 || (z3 && !m1291k(str, i4, i3)))))) {
                            if (c0014e2 == null) {
                                c0014e2 = new C0014e();
                            }
                            c0014e2.m92P(codePointAt2);
                            while (!c0014e2.mo104n()) {
                                int readByte = c0014e2.readByte() & 255;
                                c0014e.m86J(37);
                                char[] cArr = f1906i;
                                c0014e.m86J(cArr[(readByte >> 4) & 15]);
                                c0014e.m86J(cArr[readByte & 15]);
                            }
                        } else {
                            c0014e.m92P(codePointAt2);
                        }
                    }
                    i4 += Character.charCount(codePointAt2);
                }
                return c0014e.m80D();
            }
            i4 += Character.charCount(codePointAt);
        }
        return str.substring(i2, i3);
    }

    /* renamed from: b */
    public static String m1287b(String str, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        return m1286a(str, 0, str.length(), str2, z2, z3, z4, z5);
    }

    /* renamed from: c */
    public static int m1288c(String str) {
        if (str.equals("http")) {
            return 80;
        }
        return str.equals("https") ? 443 : -1;
    }

    /* renamed from: i */
    public static String m1289i(String str, int i2, int i3, boolean z2) {
        int i4;
        int i5 = i2;
        while (i5 < i3) {
            char charAt = str.charAt(i5);
            if (charAt == '%' || (charAt == '+' && z2)) {
                C0014e c0014e = new C0014e();
                c0014e.m91O(str, i2, i5);
                while (i5 < i3) {
                    int codePointAt = str.codePointAt(i5);
                    if (codePointAt != 37 || (i4 = i5 + 2) >= i3) {
                        if (codePointAt == 43 && z2) {
                            c0014e.m86J(32);
                        }
                        c0014e.m92P(codePointAt);
                    } else {
                        int m1308e = AbstractC0887c.m1308e(str.charAt(i5 + 1));
                        int m1308e2 = AbstractC0887c.m1308e(str.charAt(i4));
                        if (m1308e != -1 && m1308e2 != -1) {
                            c0014e.m86J((m1308e << 4) + m1308e2);
                            i5 = i4;
                        }
                        c0014e.m92P(codePointAt);
                    }
                    i5 += Character.charCount(codePointAt);
                }
                return c0014e.m80D();
            }
            i5++;
        }
        return str.substring(i2, i3);
    }

    /* renamed from: j */
    public static List m1290j(boolean z2, List list) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            String str = (String) list.get(i2);
            arrayList.add(str != null ? m1289i(str, 0, str.length(), z2) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* renamed from: k */
    public static boolean m1291k(String str, int i2, int i3) {
        int i4 = i2 + 2;
        return i4 < i3 && str.charAt(i2) == '%' && AbstractC0887c.m1308e(str.charAt(i2 + 1)) != -1 && AbstractC0887c.m1308e(str.charAt(i4)) != -1;
    }

    /* renamed from: l */
    public static ArrayList m1292l(String str) {
        String str2;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 <= str.length()) {
            int indexOf = str.indexOf(38, i2);
            if (indexOf == -1) {
                indexOf = str.length();
            }
            int indexOf2 = str.indexOf(61, i2);
            if (indexOf2 == -1 || indexOf2 > indexOf) {
                arrayList.add(str.substring(i2, indexOf));
                str2 = null;
            } else {
                arrayList.add(str.substring(i2, indexOf2));
                str2 = str.substring(indexOf2 + 1, indexOf);
            }
            arrayList.add(str2);
            i2 = indexOf + 1;
        }
        return arrayList;
    }

    /* renamed from: d */
    public final String m1293d() {
        if (this.f1909c.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        int length = this.f1907a.length() + 3;
        String str = this.f1914h;
        return str.substring(str.indexOf(58, length) + 1, str.indexOf(64));
    }

    /* renamed from: e */
    public final String m1294e() {
        int length = this.f1907a.length() + 3;
        String str = this.f1914h;
        int indexOf = str.indexOf(47, length);
        return str.substring(indexOf, AbstractC0887c.m1311h(str, indexOf, str.length(), "?#"));
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C0879u) && ((C0879u) obj).f1914h.equals(this.f1914h);
    }

    /* renamed from: f */
    public final ArrayList m1295f() {
        int length = this.f1907a.length() + 3;
        String str = this.f1914h;
        int indexOf = str.indexOf(47, length);
        int m1311h = AbstractC0887c.m1311h(str, indexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (indexOf < m1311h) {
            int i2 = indexOf + 1;
            int m1310g = AbstractC0887c.m1310g(str, i2, m1311h, '/');
            arrayList.add(str.substring(i2, m1310g));
            indexOf = m1310g;
        }
        return arrayList;
    }

    /* renamed from: g */
    public final String m1296g() {
        if (this.f1912f == null) {
            return null;
        }
        String str = this.f1914h;
        int indexOf = str.indexOf(63) + 1;
        return str.substring(indexOf, AbstractC0887c.m1310g(str, indexOf, str.length(), '#'));
    }

    /* renamed from: h */
    public final String m1297h() {
        if (this.f1908b.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        int length = this.f1907a.length() + 3;
        String str = this.f1914h;
        return str.substring(length, AbstractC0887c.m1311h(str, length, str.length(), ":@"));
    }

    public final int hashCode() {
        return this.f1914h.hashCode();
    }

    /* renamed from: m */
    public final String m1298m() {
        C0878t c0878t;
        try {
            c0878t = new C0878t();
            c0878t.m1285b(this, "/...");
        } catch (IllegalArgumentException unused) {
            c0878t = null;
        }
        c0878t.getClass();
        c0878t.f1902f = m1287b(BuildConfig.FLAVOR, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        c0878t.f1903g = m1287b(BuildConfig.FLAVOR, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
        return c0878t.m1284a().f1914h;
    }

    /* renamed from: n */
    public final URI m1299n() {
        C0878t c0878t = new C0878t();
        String str = this.f1907a;
        c0878t.f1901e = str;
        c0878t.f1902f = m1297h();
        c0878t.f1903g = m1293d();
        c0878t.f1904h = this.f1910d;
        int m1288c = m1288c(str);
        int i2 = this.f1911e;
        if (i2 == m1288c) {
            i2 = -1;
        }
        c0878t.f1899c = i2;
        c0878t.f1898b.clear();
        c0878t.f1898b.addAll(m1295f());
        String m1296g = m1296g();
        String str2 = null;
        c0878t.f1900d = m1296g != null ? m1292l(m1287b(m1296g, " \"'<>#", true, false, true, true)) : null;
        if (this.f1913g != null) {
            String str3 = this.f1914h;
            str2 = str3.substring(str3.indexOf(35) + 1);
        }
        c0878t.f1905i = str2;
        int size = c0878t.f1898b.size();
        for (int i3 = 0; i3 < size; i3++) {
            c0878t.f1898b.set(i3, m1287b((String) c0878t.f1898b.get(i3), "[]", true, true, false, true));
        }
        List list = c0878t.f1900d;
        if (list != null) {
            int size2 = list.size();
            for (int i4 = 0; i4 < size2; i4++) {
                String str4 = (String) c0878t.f1900d.get(i4);
                if (str4 != null) {
                    c0878t.f1900d.set(i4, m1287b(str4, "\\^`{|}", true, true, true, true));
                }
            }
        }
        String str5 = (String) c0878t.f1905i;
        if (str5 != null) {
            c0878t.f1905i = m1287b(str5, " \"#<>\\^`{|}", true, true, false, false);
        }
        String c0878t2 = c0878t.toString();
        try {
            return new URI(c0878t2);
        } catch (URISyntaxException e2) {
            try {
                return URI.create(c0878t2.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", BuildConfig.FLAVOR));
            } catch (Exception unused) {
                throw new RuntimeException(e2);
            }
        }
    }

    public final String toString() {
        return this.f1914h;
    }
}
