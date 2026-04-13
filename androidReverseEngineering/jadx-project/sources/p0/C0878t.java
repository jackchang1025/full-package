package p0;

import com.guard.wallet.http.C0203h;
import java.io.Serializable;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import com.guard.wallet.entity.BuildConfig;
import q0.AbstractC0887c;

/* renamed from: p0.t */
/* loaded from: classes.dex */
public final class C0878t {

    /* renamed from: a */
    public final /* synthetic */ int f1897a;

    /* renamed from: b */
    public List f1898b;

    /* renamed from: c */
    public int f1899c;

    /* renamed from: d */
    public List f1900d;

    /* renamed from: e */
    public Object f1901e;

    /* renamed from: f */
    public Object f1902f;

    /* renamed from: g */
    public Object f1903g;

    /* renamed from: h */
    public Object f1904h;

    /* renamed from: i */
    public Serializable f1905i;

    public C0878t() {
        this.f1897a = 0;
        this.f1902f = BuildConfig.FLAVOR;
        this.f1903g = BuildConfig.FLAVOR;
        this.f1899c = -1;
        ArrayList arrayList = new ArrayList();
        this.f1898b = arrayList;
        arrayList.add(BuildConfig.FLAVOR);
    }

    /* renamed from: a */
    public final C0879u m1284a() {
        if (((String) this.f1901e) == null) {
            throw new IllegalStateException("scheme == null");
        }
        if (((String) this.f1904h) != null) {
            return new C0879u(this);
        }
        throw new IllegalStateException("host == null");
    }

    /* JADX WARN: Code restructure failed: missing block: B:182:0x0228, code lost:
    
        if (r1 <= 65535) goto L120;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x027a  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1285b(C0879u c0879u, String str) {
        int i2;
        char c;
        char c2;
        int m1311h;
        int i3;
        boolean z2;
        char c3;
        int i4;
        int i5;
        int i6;
        int m1311h2;
        C0878t c0878t;
        C0878t c0878t2;
        int i7;
        Object obj;
        String str2;
        int i8;
        String str3;
        String str4;
        C0878t c0878t3;
        char c4;
        char charAt;
        int m1321r = AbstractC0887c.m1321r(str, 0, str.length());
        int m1322s = AbstractC0887c.m1322s(str, m1321r, str.length());
        char c5 = 65535;
        if (m1322s - m1321r >= 2) {
            char charAt2 = str.charAt(m1321r);
            char c6 = Matrix.MATRIX_TYPE_ZERO;
            if ((charAt2 >= 'a' && charAt2 <= 'z') || (charAt2 >= 'A' && charAt2 <= 'Z')) {
                int i9 = m1321r;
                while (true) {
                    i9++;
                    if (i9 >= m1322s) {
                        break;
                    }
                    char charAt3 = str.charAt(i9);
                    if ((charAt3 >= 'a' && charAt3 <= 'z') || ((charAt3 >= 'A' && charAt3 <= c6) || ((charAt3 >= '0' && charAt3 <= '9') || charAt3 == '+' || charAt3 == '-' || charAt3 == '.'))) {
                        c6 = Matrix.MATRIX_TYPE_ZERO;
                    } else if (charAt3 == ':') {
                        i2 = i9;
                    }
                }
            }
        }
        i2 = -1;
        if (i2 != -1) {
            int i10 = i2;
            if (str.regionMatches(true, m1321r, "https:", 0, 6)) {
                this.f1901e = "https";
                m1321r += 6;
            } else {
                if (!str.regionMatches(true, m1321r, "http:", 0, 5)) {
                    throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but was '" + str.substring(0, i10) + "'");
                }
                this.f1901e = "http";
                m1321r += 5;
            }
        } else {
            if (c0879u == null) {
                throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
            }
            this.f1901e = c0879u.f1907a;
        }
        int i11 = m1321r;
        int i12 = 0;
        while (true) {
            c = '\\';
            c2 = '/';
            if (i11 >= m1322s || !((charAt = str.charAt(i11)) == '\\' || charAt == '/')) {
                break;
            }
            i12++;
            i11++;
        }
        char c7 = '?';
        char c8 = '#';
        if (i12 < 2 && c0879u != null) {
            if (c0879u.f1907a.equals((String) this.f1901e)) {
                this.f1902f = c0879u.m1297h();
                this.f1903g = c0879u.m1293d();
                this.f1904h = c0879u.f1910d;
                this.f1899c = c0879u.f1911e;
                this.f1898b.clear();
                this.f1898b.addAll(c0879u.m1295f());
                if (m1321r == m1322s || str.charAt(m1321r) == '#') {
                    String m1296g = c0879u.m1296g();
                    this.f1900d = m1296g != null ? C0879u.m1292l(C0879u.m1287b(m1296g, " \"'<>#", true, false, true, true)) : null;
                }
                z2 = false;
                m1311h2 = AbstractC0887c.m1311h(str, m1321r, m1322s, "?#");
                if (m1321r != m1311h2) {
                    c0878t3 = this;
                    str4 = str;
                    str2 = str4;
                } else {
                    char charAt4 = str.charAt(m1321r);
                    if (charAt4 == '/' || charAt4 == '\\') {
                        this.f1898b.clear();
                        this.f1898b.add(BuildConfig.FLAVOR);
                        c0878t = this;
                        c0878t2 = c0878t;
                        i7 = m1311h2;
                        obj = BuildConfig.FLAVOR;
                        str2 = str;
                        i8 = i7;
                        str3 = str2;
                        m1321r++;
                    } else {
                        List list = this.f1898b;
                        list.set(list.size() - 1, BuildConfig.FLAVOR);
                        c0878t = this;
                        c0878t2 = c0878t;
                        i7 = m1311h2;
                        obj = BuildConfig.FLAVOR;
                        str2 = str;
                        i8 = i7;
                        str3 = str2;
                    }
                    while (m1321r < i7) {
                        int m1311h3 = AbstractC0887c.m1311h(str3, m1321r, i7, "/\\");
                        boolean z3 = m1311h3 < i7 ? true : z2;
                        String m1286a = C0879u.m1286a(str3, m1321r, m1311h3, " \"<>^`{}|/\\?#", true, false, false, true);
                        if (!((m1286a.equals(".") || m1286a.equalsIgnoreCase("%2e")) ? true : z2)) {
                            if ((m1286a.equals("..") || m1286a.equalsIgnoreCase("%2e.") || m1286a.equalsIgnoreCase(".%2e") || m1286a.equalsIgnoreCase("%2e%2e")) ? true : z2) {
                                if (!((String) c0878t2.f1898b.remove(r8.size() - 1)).isEmpty() || c0878t2.f1898b.isEmpty()) {
                                    c0878t2.f1898b.add(obj);
                                } else {
                                    c0878t2.f1898b.set(r8.size() - 1, obj);
                                }
                            } else {
                                if (((String) c0878t2.f1898b.get(r14.size() - 1)).isEmpty()) {
                                    c0878t2.f1898b.set(r1.size() - 1, m1286a);
                                } else {
                                    c0878t2.f1898b.add(m1286a);
                                }
                                if (z3) {
                                    c0878t2.f1898b.add(obj);
                                }
                            }
                        }
                        m1321r = m1311h3;
                        z2 = false;
                        if (z3) {
                            m1321r++;
                        }
                    }
                    m1311h2 = i8;
                    str4 = str;
                    c0878t3 = c0878t;
                }
                if (m1311h2 < m1322s || str2.charAt(m1311h2) != '?') {
                    c4 = '#';
                } else {
                    c4 = '#';
                    int m1310g = AbstractC0887c.m1310g(str2, m1311h2, m1322s, '#');
                    c0878t3.f1900d = C0879u.m1292l(C0879u.m1286a(str4, m1311h2 + 1, m1310g, " \"'<>#", true, false, true, true));
                    m1311h2 = m1310g;
                }
                if (m1311h2 < m1322s || str2.charAt(m1311h2) != c4) {
                }
                c0878t3.f1905i = C0879u.m1286a(str4, m1311h2 + 1, m1322s, BuildConfig.FLAVOR, true, false, false, false);
                return;
            }
        }
        int i13 = m1321r + i12;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            m1311h = AbstractC0887c.m1311h(str, i13, m1322s, "@/\\?#");
            char charAt5 = m1311h != m1322s ? str.charAt(m1311h) : c5;
            if (charAt5 == c5 || charAt5 == c8 || charAt5 == c2 || charAt5 == c || charAt5 == c7) {
                break;
            }
            if (charAt5 == '@') {
                if (z4) {
                    i6 = m1311h;
                    this.f1903g = ((String) this.f1903g) + "%40" + C0879u.m1286a(str, i13, i6, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                } else {
                    int m1310g2 = AbstractC0887c.m1310g(str, i13, m1311h, ':');
                    i6 = m1311h;
                    String m1286a2 = C0879u.m1286a(str, i13, m1310g2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                    if (z5) {
                        m1286a2 = ((String) this.f1902f) + "%40" + m1286a2;
                    }
                    this.f1902f = m1286a2;
                    if (m1310g2 != i6) {
                        this.f1903g = C0879u.m1286a(str, m1310g2 + 1, i6, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                        z4 = true;
                    }
                    z5 = true;
                }
                i13 = i6 + 1;
            }
            c8 = '#';
            c7 = '?';
            c2 = '/';
            c5 = 65535;
            c = '\\';
        }
        int i14 = i13;
        while (true) {
            if (i14 < m1311h) {
                char charAt6 = str.charAt(i14);
                if (charAt6 == ':') {
                    i3 = i14;
                    break;
                }
                if (charAt6 != '[') {
                    i5 = 1;
                } else {
                    i5 = 1;
                    do {
                        i14++;
                        if (i14 < m1311h) {
                        }
                    } while (str.charAt(i14) != ']');
                }
                i14 += i5;
            } else {
                i3 = m1311h;
                break;
            }
        }
        int i15 = i3 + 1;
        if (i15 < m1311h) {
            this.f1904h = AbstractC0887c.m1304a(C0879u.m1289i(str, i13, i3, false));
            c3 = '\"';
            try {
                i4 = Integer.parseInt(C0879u.m1286a(str, i15, m1311h, BuildConfig.FLAVOR, false, false, false, true));
                if (i4 > 0) {
                }
            } catch (NumberFormatException unused) {
            }
            i4 = -1;
            this.f1899c = i4;
            if (i4 == -1) {
                throw new IllegalArgumentException("Invalid URL port: \"" + str.substring(i15, m1311h) + '\"');
            }
            z2 = false;
        } else {
            z2 = false;
            c3 = '\"';
            this.f1904h = AbstractC0887c.m1304a(C0879u.m1289i(str, i13, i3, false));
            this.f1899c = C0879u.m1288c((String) this.f1901e);
        }
        if (((String) this.f1904h) == null) {
            throw new IllegalArgumentException("Invalid URL host: \"" + str.substring(i13, i3) + c3);
        }
        m1321r = m1311h;
        m1311h2 = AbstractC0887c.m1311h(str, m1321r, m1322s, "?#");
        if (m1321r != m1311h2) {
        }
        if (m1311h2 < m1322s) {
        }
        c4 = '#';
        if (m1311h2 < m1322s) {
        }
    }

    public final String toString() {
        String str;
        switch (this.f1897a) {
            case 0:
                StringBuilder sb = new StringBuilder();
                String str2 = (String) this.f1901e;
                if (str2 != null) {
                    sb.append(str2);
                    str = "://";
                } else {
                    str = "//";
                }
                sb.append(str);
                if (!((String) this.f1902f).isEmpty() || !((String) this.f1903g).isEmpty()) {
                    sb.append((String) this.f1902f);
                    if (!((String) this.f1903g).isEmpty()) {
                        sb.append(':');
                        sb.append((String) this.f1903g);
                    }
                    sb.append('@');
                }
                String str3 = (String) this.f1904h;
                if (str3 != null) {
                    if (str3.indexOf(58) != -1) {
                        sb.append('[');
                        sb.append((String) this.f1904h);
                        sb.append(']');
                    } else {
                        sb.append((String) this.f1904h);
                    }
                }
                int i2 = this.f1899c;
                if (i2 != -1 || ((String) this.f1901e) != null) {
                    if (i2 == -1) {
                        i2 = C0879u.m1288c((String) this.f1901e);
                    }
                    String str4 = (String) this.f1901e;
                    if (str4 == null || i2 != C0879u.m1288c(str4)) {
                        sb.append(':');
                        sb.append(i2);
                    }
                }
                List list = this.f1898b;
                int size = list.size();
                for (int i3 = 0; i3 < size; i3++) {
                    sb.append('/');
                    sb.append((String) list.get(i3));
                }
                if (this.f1900d != null) {
                    sb.append('?');
                    List list2 = this.f1900d;
                    int size2 = list2.size();
                    for (int i4 = 0; i4 < size2; i4 += 2) {
                        String str5 = (String) list2.get(i4);
                        String str6 = (String) list2.get(i4 + 1);
                        if (i4 > 0) {
                            sb.append('&');
                        }
                        sb.append(str5);
                        if (str6 != null) {
                            sb.append('=');
                            sb.append(str6);
                        }
                    }
                }
                if (((String) this.f1905i) != null) {
                    sb.append('#');
                    sb.append((String) this.f1905i);
                }
                return sb.toString();
            default:
                return super.toString();
        }
    }

    public C0878t(C0859a c0859a, C0203h c0203h, e0 e0Var, C0875q c0875q) {
        List m1315l;
        this.f1897a = 1;
        this.f1898b = Collections.emptyList();
        this.f1900d = Collections.emptyList();
        this.f1905i = new ArrayList();
        this.f1901e = c0859a;
        this.f1902f = c0203h;
        this.f1903g = e0Var;
        this.f1904h = c0875q;
        Proxy proxy = c0859a.f1689h;
        if (proxy != null) {
            m1315l = Collections.singletonList(proxy);
        } else {
            List<Proxy> select = c0859a.f1688g.select(c0859a.f1682a.m1299n());
            m1315l = (select == null || select.isEmpty()) ? AbstractC0887c.m1315l(Proxy.NO_PROXY) : AbstractC0887c.m1314k(select);
        }
        this.f1898b = m1315l;
        this.f1899c = 0;
    }
}
