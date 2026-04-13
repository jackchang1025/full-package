package q0;

import a1.C0014e;
import a1.C0017h;
import a1.C0022m;
import a1.C0031v;
import a1.InterfaceC0029t;
import java.io.Closeable;
import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import p0.C0864f;
import p0.C0866h;
import p0.C0875q;
import p0.C0877s;
import p0.C0879u;
import p0.k0;
import p000a.AbstractC0000a;
import v0.C0932c;

/* renamed from: q0.c */
/* loaded from: classes.dex */
public abstract class AbstractC0887c {

    /* renamed from: a */
    public static final byte[] f1934a = new byte[0];

    /* renamed from: b */
    public static final String[] f1935b = new String[0];

    /* renamed from: c */
    public static final C0877s f1936c;

    /* renamed from: d */
    public static final k0 f1937d;

    /* renamed from: e */
    public static final C0022m f1938e;

    /* renamed from: f */
    public static final Charset f1939f;

    /* renamed from: g */
    public static final Charset f1940g;

    /* renamed from: h */
    public static final TimeZone f1941h;

    /* renamed from: i */
    public static final C0866h f1942i;

    /* renamed from: j */
    public static final Method f1943j;

    /* renamed from: k */
    public static final Pattern f1944k;

    /* JADX WARN: Code restructure failed: missing block: B:57:0x0149, code lost:
    
        continue;
     */
    static {
        Method method;
        String[] strArr = (String[]) new String[0].clone();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (str == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            strArr[i2] = str.trim();
        }
        for (int i3 = 0; i3 < strArr.length; i3 += 2) {
            String str2 = strArr[i3];
            String str3 = strArr[i3 + 1];
            C0877s.m1278a(str2);
            C0877s.m1279b(str3, str2);
        }
        f1936c = new C0877s(strArr);
        byte[] bArr = f1934a;
        C0014e c0014e = new C0014e();
        c0014e.m85I(bArr, 0, 0);
        long j2 = 0;
        f1937d = new k0(j2, c0014e);
        if ((j2 | j2) < 0 || j2 > j2 || j2 - j2 < j2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        C0017h[] c0017hArr = {C0017h.m116b("efbbbf"), C0017h.m116b("feff"), C0017h.m116b("fffe"), C0017h.m116b("0000ffff"), C0017h.m116b("ffff0000")};
        int i4 = C0022m.f39c;
        ArrayList arrayList = new ArrayList(Arrays.asList(c0017hArr));
        Collections.sort(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            arrayList2.add(-1);
        }
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            arrayList2.set(Collections.binarySearch(arrayList, c0017hArr[i6]), Integer.valueOf(i6));
        }
        if (((C0017h) arrayList.get(0)).mo125j() == 0) {
            throw new IllegalArgumentException("the empty byte string is not a supported option");
        }
        int i7 = 0;
        while (i7 < arrayList.size()) {
            C0017h c0017h = (C0017h) arrayList.get(i7);
            int i8 = i7 + 1;
            int i9 = i8;
            while (i9 < arrayList.size()) {
                C0017h c0017h2 = (C0017h) arrayList.get(i9);
                c0017h2.getClass();
                if (!c0017h2.mo124i(c0017h, c0017h.mo125j())) {
                    break;
                }
                if (c0017h2.mo125j() == c0017h.mo125j()) {
                    throw new IllegalArgumentException("duplicate option: " + c0017h2);
                }
                if (((Integer) arrayList2.get(i9)).intValue() > ((Integer) arrayList2.get(i7)).intValue()) {
                    arrayList.remove(i9);
                    arrayList2.remove(i9);
                } else {
                    i9++;
                }
            }
            i7 = i8;
        }
        C0014e c0014e2 = new C0014e();
        C0022m.m141a(0L, c0014e2, 0, arrayList, 0, arrayList.size(), arrayList2);
        int i10 = (int) (c0014e2.f22b / 4);
        int[] iArr = new int[i10];
        for (int i11 = 0; i11 < i10; i11++) {
            iArr[i11] = c0014e2.readInt();
        }
        if (!c0014e2.mo104n()) {
            throw new AssertionError();
        }
        f1938e = new C0022m((C0017h[]) c0017hArr.clone(), iArr);
        f1939f = Charset.forName("UTF-32BE");
        f1940g = Charset.forName("UTF-32LE");
        f1941h = TimeZone.getTimeZone("GMT");
        f1942i = new C0866h(1);
        try {
            method = Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class);
        } catch (Exception unused) {
            method = null;
        }
        f1943j = method;
        f1944k = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
    }

    /* renamed from: a */
    public static String m1304a(String str) {
        int i2 = -1;
        int i3 = 0;
        if (!str.contains(":")) {
            try {
                String lowerCase = IDN.toASCII(str).toLowerCase(Locale.US);
                if (lowerCase.isEmpty()) {
                    return null;
                }
                for (int i4 = 0; i4 < lowerCase.length(); i4++) {
                    char charAt = lowerCase.charAt(i4);
                    if (charAt > 31 && charAt < 127 && " #%/:?@[\\]".indexOf(charAt) == -1) {
                    }
                    i3 = 1;
                }
                if (i3 != 0) {
                    return null;
                }
                return lowerCase;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        InetAddress m1309f = (str.startsWith("[") && str.endsWith("]")) ? m1309f(str, 1, str.length() - 1) : m1309f(str, 0, str.length());
        if (m1309f == null) {
            return null;
        }
        byte[] address = m1309f.getAddress();
        if (address.length != 16) {
            if (address.length == 4) {
                return m1309f.getHostAddress();
            }
            throw new AssertionError(AbstractC0000a.m16l("Invalid IPv6 address: '", str, "'"));
        }
        int i5 = 0;
        int i6 = 0;
        while (i5 < address.length) {
            int i7 = i5;
            while (i7 < 16 && address[i7] == 0 && address[i7 + 1] == 0) {
                i7 += 2;
            }
            int i8 = i7 - i5;
            if (i8 > i6 && i8 >= 4) {
                i2 = i5;
                i6 = i8;
            }
            i5 = i7 + 2;
        }
        C0014e c0014e = new C0014e();
        while (i3 < address.length) {
            if (i3 == i2) {
                c0014e.m86J(58);
                i3 += i6;
                if (i3 == 16) {
                    c0014e.m86J(58);
                }
            } else {
                if (i3 > 0) {
                    c0014e.m86J(58);
                }
                c0014e.m88L(((address[i3] & 255) << 8) | (address[i3 + 1] & 255));
                i3 += 2;
            }
        }
        return c0014e.m80D();
    }

    /* renamed from: b */
    public static int m1305b(String str, long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException(str.concat(" < 0"));
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit == null");
        }
        long millis = timeUnit.toMillis(j2);
        if (millis > 2147483647L) {
            throw new IllegalArgumentException(str.concat(" too large."));
        }
        if (millis != 0 || j2 <= 0) {
            return (int) millis;
        }
        throw new IllegalArgumentException(str.concat(" too small."));
    }

    /* renamed from: c */
    public static void m1306c(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: d */
    public static void m1307d(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (AssertionError e2) {
                if (!m1317n(e2)) {
                    throw e2;
                }
            } catch (RuntimeException e3) {
                throw e3;
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: e */
    public static int m1308e(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'a';
        if (c < 'a' || c > 'f') {
            c2 = 'A';
            if (c < 'A' || c > 'F') {
                return -1;
            }
        }
        return (c - c2) + 10;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00db, code lost:
    
        if (r7 == r0) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00de, code lost:
    
        if (r8 != (-1)) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00e0, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e1, code lost:
    
        r1 = r7 - r8;
        java.lang.System.arraycopy(r3, r8, r3, 16 - r1, r1);
        java.util.Arrays.fill(r3, r8, (16 - r7) + r8, (byte) 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00f3, code lost:
    
        return java.net.InetAddress.getByAddress(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f9, code lost:
    
        throw new java.lang.AssertionError();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x009a, code lost:
    
        r14 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ab  */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static InetAddress m1309f(String str, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7 = 16;
        byte[] bArr = new byte[16];
        int i8 = 0;
        int i9 = -1;
        int i10 = i2;
        int i11 = 0;
        int i12 = -1;
        int i13 = -1;
        while (true) {
            if (i10 >= i3) {
                i4 = i7;
                break;
            }
            if (i11 != i7) {
                int i14 = i10 + 2;
                if (i14 <= i3 && str.regionMatches(i10, "::", i8, 2)) {
                    if (i12 == i9) {
                        i11 += 2;
                        if (i14 != i3) {
                            i12 = i11;
                            i13 = i14;
                            i10 = i13;
                            int i15 = 0;
                            while (i10 < i3) {
                            }
                            i6 = i10 - i13;
                            if (i6 == 0) {
                                break;
                            }
                            break;
                        }
                        i4 = i7;
                        i12 = i11;
                        break;
                    }
                    return null;
                }
                if (i11 != 0) {
                    if (str.regionMatches(i10, ":", i8, 1)) {
                        i10++;
                    } else {
                        if (!str.regionMatches(i10, ".", i8, 1)) {
                            return null;
                        }
                        int i16 = i11 - 2;
                        int i17 = i16;
                        loop2: while (true) {
                            if (i13 < i3) {
                                if (i17 == i7) {
                                    break;
                                }
                                if (i17 != i16) {
                                    if (str.charAt(i13) != '.') {
                                        break;
                                    }
                                    i13++;
                                }
                                int i18 = i8;
                                int i19 = i13;
                                while (i19 < i3) {
                                    char charAt = str.charAt(i19);
                                    if (charAt < '0' || charAt > '9') {
                                        break;
                                    }
                                    if ((i18 == 0 && i13 != i19) || (i18 = ((i18 * 10) + charAt) - 48) > 255) {
                                        break loop2;
                                    }
                                    i19++;
                                }
                                if (i19 - i13 == 0) {
                                    break;
                                }
                                bArr[i17] = (byte) i18;
                                i17++;
                                i13 = i19;
                                i7 = 16;
                                i8 = 0;
                            } else if (i17 == i16 + 4) {
                                i5 = 1;
                            }
                        }
                        i5 = i8;
                        if (i5 == 0) {
                            return null;
                        }
                        i11 += 2;
                        i4 = 16;
                    }
                }
                i13 = i10;
                i10 = i13;
                int i152 = 0;
                while (i10 < i3) {
                    int m1308e = m1308e(str.charAt(i10));
                    if (m1308e == -1) {
                        break;
                    }
                    i152 = (i152 << 4) + m1308e;
                    i10++;
                }
                i6 = i10 - i13;
                if (i6 == 0 || i6 > 4) {
                    break;
                }
                int i20 = i11 + 1;
                bArr[i11] = (byte) ((i152 >>> 8) & 255);
                i11 = i20 + 1;
                bArr[i20] = (byte) (i152 & 255);
                i7 = 16;
                i8 = 0;
                i9 = -1;
            } else {
                return null;
            }
        }
        return null;
    }

    /* renamed from: g */
    public static int m1310g(String str, int i2, int i3, char c) {
        while (i2 < i3) {
            if (str.charAt(i2) == c) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    /* renamed from: h */
    public static int m1311h(String str, int i2, int i3, String str2) {
        while (i2 < i3) {
            if (str2.indexOf(str.charAt(i2)) != -1) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    /* renamed from: i */
    public static String m1312i(Object[] objArr, String str) {
        return String.format(Locale.US, str, objArr);
    }

    /* renamed from: j */
    public static String m1313j(C0879u c0879u, boolean z2) {
        boolean contains = c0879u.f1910d.contains(":");
        String str = c0879u.f1910d;
        if (contains) {
            str = AbstractC0000a.m16l("[", str, "]");
        }
        int i2 = c0879u.f1911e;
        if (!z2 && i2 == C0879u.m1288c(c0879u.f1907a)) {
            return str;
        }
        return str + ":" + i2;
    }

    /* renamed from: k */
    public static List m1314k(List list) {
        return Collections.unmodifiableList(new ArrayList(list));
    }

    /* renamed from: l */
    public static List m1315l(Object... objArr) {
        return Collections.unmodifiableList(Arrays.asList((Object[]) objArr.clone()));
    }

    /* renamed from: m */
    public static String[] m1316m(C0866h c0866h, String[] strArr, String[] strArr2) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            int length = strArr2.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (c0866h.compare(str, strArr2[i2]) == 0) {
                    arrayList.add(str);
                    break;
                }
                i2++;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    /* renamed from: n */
    public static boolean m1317n(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    /* renamed from: o */
    public static boolean m1318o(C0866h c0866h, String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length != 0 && strArr2.length != 0) {
            for (String str : strArr) {
                for (String str2 : strArr2) {
                    if (c0866h.compare(str, str2) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: p */
    public static boolean m1319p(C0879u c0879u, C0879u c0879u2) {
        return c0879u.f1910d.equals(c0879u2.f1910d) && c0879u.f1911e == c0879u2.f1911e && c0879u.f1907a.equals(c0879u2.f1907a);
    }

    /* renamed from: q */
    public static boolean m1320q(InterfaceC0029t interfaceC0029t, int i2, TimeUnit timeUnit) {
        long nanoTime = System.nanoTime();
        long mo132c = interfaceC0029t.mo68a().mo134e() ? interfaceC0029t.mo68a().mo132c() - nanoTime : Long.MAX_VALUE;
        interfaceC0029t.mo68a().mo133d(Math.min(mo132c, timeUnit.toNanos(i2)) + nanoTime);
        try {
            C0014e c0014e = new C0014e();
            while (interfaceC0029t.mo69u(c0014e, 8192L) != -1) {
                c0014e.m113x();
            }
            C0031v mo68a = interfaceC0029t.mo68a();
            if (mo132c == Long.MAX_VALUE) {
                mo68a.mo130a();
                return true;
            }
            mo68a.mo133d(nanoTime + mo132c);
            return true;
        } catch (InterruptedIOException unused) {
            C0031v mo68a2 = interfaceC0029t.mo68a();
            if (mo132c == Long.MAX_VALUE) {
                mo68a2.mo130a();
                return false;
            }
            mo68a2.mo133d(nanoTime + mo132c);
            return false;
        } catch (Throwable th) {
            C0031v mo68a3 = interfaceC0029t.mo68a();
            if (mo132c == Long.MAX_VALUE) {
                mo68a3.mo130a();
            } else {
                mo68a3.mo133d(nanoTime + mo132c);
            }
            throw th;
        }
    }

    /* renamed from: r */
    public static int m1321r(String str, int i2, int i3) {
        while (i2 < i3) {
            char charAt = str.charAt(i2);
            if (charAt != '\t' && charAt != '\n' && charAt != '\f' && charAt != '\r' && charAt != ' ') {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    /* renamed from: s */
    public static int m1322s(String str, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            char charAt = str.charAt(i4);
            if (charAt != '\t' && charAt != '\n' && charAt != '\f' && charAt != '\r' && charAt != ' ') {
                return i4 + 1;
            }
        }
        return i2;
    }

    /* renamed from: t */
    public static C0877s m1323t(ArrayList arrayList) {
        C0864f c0864f = new C0864f();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0932c c0932c = (C0932c) it.next();
            C0875q c0875q = C0875q.f1891c;
            String mo128m = c0932c.f2140a.mo128m();
            String mo128m2 = c0932c.f2141b.mo128m();
            c0875q.getClass();
            c0864f.m1251a(mo128m, mo128m2);
        }
        return new C0877s(c0864f);
    }

    /* renamed from: u */
    public static String m1324u(String str, int i2, int i3) {
        int m1321r = m1321r(str, i2, i3);
        return str.substring(m1321r, m1322s(str, m1321r, i3));
    }
}
