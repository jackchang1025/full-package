package t0;

import a1.C0017h;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import com.guard.wallet.entity.BuildConfig;
import p0.C0871m;
import p0.C0877s;
import p0.C0879u;
import p0.InterfaceC0872n;
import p0.j0;
import q0.AbstractC0887c;
import w0.C0966i;
import y0.C0977a;

/* renamed from: t0.e */
/* loaded from: classes.dex */
public abstract class AbstractC0916e {
    static {
        C0017h.m118d("\"\\");
        C0017h.m118d("\t ,=");
    }

    /* renamed from: a */
    public static long m1376a(j0 j0Var) {
        String m1280c = j0Var.f1834f.m1280c("Content-Length");
        if (m1280c != null) {
            try {
                return Long.parseLong(m1280c);
            } catch (NumberFormatException unused) {
            }
        }
        return -1L;
    }

    /* renamed from: b */
    public static boolean m1377b(j0 j0Var) {
        if (j0Var.f1829a.f1778b.equals("HEAD")) {
            return false;
        }
        int i2 = j0Var.f1831c;
        return (((i2 >= 100 && i2 < 200) || i2 == 204 || i2 == 304) && m1376a(j0Var) == -1 && !"chunked".equalsIgnoreCase(j0Var.m1265x("Transfer-Encoding", null))) ? false : true;
    }

    /* renamed from: c */
    public static int m1378c(int i2, String str) {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (parseLong < 0) {
                return 0;
            }
            return (int) parseLong;
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x021f, code lost:
    
        if (r11 != false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0331, code lost:
    
        if (r0 != null) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0230, code lost:
    
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x022e, code lost:
    
        if (r11 == false) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x01ec, code lost:
    
        if ((r0.equals(r15) || (r0.endsWith(r15) && r0.charAt((r0.length() - r15.length()) - 1) == '.' && !q0.AbstractC0887c.f1944k.matcher(r0).matches())) == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0117, code lost:
    
        if (r19 <= 0) goto L69;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0386 A[SYNTHETIC] */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1379d(InterfaceC0872n interfaceC0872n, C0879u c0879u, C0877s c0877s) {
        List list;
        long j2;
        int i2;
        String substring;
        C0871m c0871m;
        int i3;
        String str;
        String str2;
        String str3;
        String[] split;
        int i4;
        String sb;
        if (interfaceC0872n == InterfaceC0872n.f1877b) {
            return;
        }
        Pattern pattern = C0871m.f1861j;
        int length = c0877s.f1896a.length / 2;
        int i5 = 0;
        ArrayList arrayList = null;
        for (int i6 = 0; i6 < length; i6++) {
            if ("Set-Cookie".equalsIgnoreCase(c0877s.m1281d(i6))) {
                if (arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                arrayList.add(c0877s.m1283f(i6));
            }
        }
        List unmodifiableList = arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
        int size = unmodifiableList.size();
        int i7 = 0;
        ArrayList arrayList2 = null;
        while (i7 < size) {
            String str4 = (String) unmodifiableList.get(i7);
            long currentTimeMillis = System.currentTimeMillis();
            int length2 = str4.length();
            int m1310g = AbstractC0887c.m1310g(str4, i5, length2, ';');
            int m1310g2 = AbstractC0887c.m1310g(str4, i5, m1310g, '=');
            if (m1310g2 != m1310g) {
                String m1324u = AbstractC0887c.m1324u(str4, i5, m1310g2);
                if (!m1324u.isEmpty()) {
                    int length3 = m1324u.length();
                    int i8 = 0;
                    while (true) {
                        if (i8 >= length3) {
                            list = unmodifiableList;
                            i8 = -1;
                            break;
                        }
                        char charAt = m1324u.charAt(i8);
                        list = unmodifiableList;
                        if (charAt <= 31 || charAt >= 127) {
                            break;
                        }
                        i8++;
                        unmodifiableList = list;
                    }
                    if (i8 == -1) {
                        String m1324u2 = AbstractC0887c.m1324u(str4, m1310g2 + 1, m1310g);
                        int length4 = m1324u2.length();
                        int i9 = 0;
                        while (true) {
                            if (i9 >= length4) {
                                i9 = -1;
                                break;
                            }
                            char charAt2 = m1324u2.charAt(i9);
                            if (charAt2 <= 31 || charAt2 >= 127) {
                                break;
                            } else {
                                i9++;
                            }
                        }
                        if (i9 == -1) {
                            int i10 = m1310g + 1;
                            long j3 = 253402300799999L;
                            String str5 = null;
                            long j4 = -1;
                            boolean z2 = false;
                            boolean z3 = false;
                            boolean z4 = true;
                            boolean z5 = false;
                            String str6 = null;
                            while (true) {
                                if (i10 < length2) {
                                    int m1310g3 = AbstractC0887c.m1310g(str4, i10, length2, ';');
                                    int m1310g4 = AbstractC0887c.m1310g(str4, i10, m1310g3, '=');
                                    String m1324u3 = AbstractC0887c.m1324u(str4, i10, m1310g4);
                                    String m1324u4 = m1310g4 < m1310g3 ? AbstractC0887c.m1324u(str4, m1310g4 + 1, m1310g3) : BuildConfig.FLAVOR;
                                    if (m1324u3.equalsIgnoreCase("expires")) {
                                        try {
                                            j3 = C0871m.m1271b(m1324u4, m1324u4.length());
                                            z5 = true;
                                        } catch (NumberFormatException | IllegalArgumentException unused) {
                                        }
                                        i10 = m1310g3 + 1;
                                    } else if (m1324u3.equalsIgnoreCase("max-age")) {
                                        try {
                                            j4 = Long.parseLong(m1324u4);
                                        } catch (NumberFormatException e2) {
                                            if (!m1324u4.matches("-?\\d+")) {
                                                throw e2;
                                            }
                                            if (!m1324u4.startsWith("-")) {
                                                j4 = Long.MAX_VALUE;
                                            }
                                            j4 = Long.MIN_VALUE;
                                        }
                                    } else {
                                        if (m1324u3.equalsIgnoreCase("domain")) {
                                            if (m1324u4.endsWith(".")) {
                                                throw new IllegalArgumentException();
                                            }
                                            if (m1324u4.startsWith(".")) {
                                                m1324u4 = m1324u4.substring(1);
                                            }
                                            String m1304a = AbstractC0887c.m1304a(m1324u4);
                                            if (m1304a == null) {
                                                throw new IllegalArgumentException();
                                            }
                                            str5 = m1304a;
                                            z4 = false;
                                        } else if (m1324u3.equalsIgnoreCase("path")) {
                                            str6 = m1324u4;
                                        } else if (m1324u3.equalsIgnoreCase("secure")) {
                                            z2 = true;
                                        } else if (m1324u3.equalsIgnoreCase("httponly")) {
                                            z3 = true;
                                        }
                                        i10 = m1310g3 + 1;
                                    }
                                } else {
                                    if (j4 == Long.MIN_VALUE) {
                                        j2 = Long.MIN_VALUE;
                                    } else if (j4 != -1) {
                                        long j5 = currentTimeMillis + (j4 <= 9223372036854775L ? j4 * 1000 : Long.MAX_VALUE);
                                        j2 = (j5 < currentTimeMillis || j5 > 253402300799999L) ? 253402300799999L : j5;
                                    } else {
                                        j2 = j3;
                                    }
                                    String str7 = c0879u.f1910d;
                                    if (str5 == null) {
                                        str5 = str7;
                                    }
                                    if (str7.length() != str5.length()) {
                                        C0977a c0977a = C0977a.f2318h;
                                        c0977a.getClass();
                                        String[] split2 = IDN.toUnicode(str5).split("\\.");
                                        if (c0977a.f2319a.get() || !c0977a.f2319a.compareAndSet(false, true)) {
                                            try {
                                                c0977a.f2320b.await();
                                            } catch (InterruptedException unused2) {
                                                Thread.currentThread().interrupt();
                                            }
                                        } else {
                                            boolean z6 = false;
                                            while (true) {
                                                try {
                                                    try {
                                                        try {
                                                            c0977a.m1469b();
                                                            break;
                                                        } catch (InterruptedIOException unused3) {
                                                            Thread.interrupted();
                                                            z6 = true;
                                                        }
                                                    } catch (IOException e3) {
                                                        C0966i.f2293a.mo1455m(5, "Failed to read public suffix list", e3);
                                                    }
                                                } catch (Throwable th) {
                                                    if (z6) {
                                                        Thread.currentThread().interrupt();
                                                    }
                                                    throw th;
                                                }
                                            }
                                        }
                                        synchronized (c0977a) {
                                            if (c0977a.f2321c == null) {
                                                throw new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.");
                                            }
                                        }
                                        int length5 = split2.length;
                                        byte[][] bArr = new byte[length5][];
                                        for (int i11 = 0; i11 < split2.length; i11++) {
                                            bArr[i11] = split2[i11].getBytes(StandardCharsets.UTF_8);
                                        }
                                        int i12 = 0;
                                        while (true) {
                                            if (i12 >= length5) {
                                                i3 = 1;
                                                str = null;
                                                break;
                                            } else {
                                                str = C0977a.m1468a(c0977a.f2321c, bArr, i12);
                                                if (str != null) {
                                                    i3 = 1;
                                                    break;
                                                }
                                                i12++;
                                            }
                                        }
                                        if (length5 > i3) {
                                            byte[][] bArr2 = (byte[][]) bArr.clone();
                                            i2 = size;
                                            int i13 = 0;
                                            while (i13 < bArr2.length - i3) {
                                                bArr2[i13] = C0977a.f2315e;
                                                str2 = C0977a.m1468a(c0977a.f2321c, bArr2, i13);
                                                if (str2 != null) {
                                                    break;
                                                }
                                                i13++;
                                                i3 = 1;
                                            }
                                        } else {
                                            i2 = size;
                                        }
                                        str2 = null;
                                        if (str2 != null) {
                                            for (int i14 = 0; i14 < length5 - 1; i14++) {
                                                str3 = C0977a.m1468a(c0977a.f2322d, bArr, i14);
                                                if (str3 != null) {
                                                    break;
                                                }
                                            }
                                        }
                                        str3 = null;
                                        if (str3 != null) {
                                            split = "!".concat(str3).split("\\.");
                                        } else if (str == null && str2 == null) {
                                            split = C0977a.f2317g;
                                        } else {
                                            split = str != null ? str.split("\\.") : C0977a.f2316f;
                                            String[] split3 = str2 != null ? str2.split("\\.") : C0977a.f2316f;
                                            if (split.length <= split3.length) {
                                                split = split3;
                                            }
                                        }
                                        if (split2.length == split.length) {
                                            i4 = 0;
                                            if (split[0].charAt(0) != '!') {
                                                sb = null;
                                            }
                                        } else {
                                            i4 = 0;
                                        }
                                        char charAt3 = split[i4].charAt(i4);
                                        int length6 = split2.length;
                                        int length7 = split.length;
                                        if (charAt3 != '!') {
                                            length7++;
                                        }
                                        StringBuilder sb2 = new StringBuilder();
                                        String[] split4 = str5.split("\\.");
                                        for (int i15 = length6 - length7; i15 < split4.length; i15++) {
                                            sb2.append(split4[i15]);
                                            sb2.append('.');
                                        }
                                        sb2.deleteCharAt(sb2.length() - 1);
                                        sb = sb2.toString();
                                    } else {
                                        i2 = size;
                                    }
                                    String str8 = str6;
                                    if (str8 == null || !str8.startsWith("/")) {
                                        String m1294e = c0879u.m1294e();
                                        int lastIndexOf = m1294e.lastIndexOf(47);
                                        i5 = 0;
                                        substring = lastIndexOf != 0 ? m1294e.substring(0, lastIndexOf) : "/";
                                    } else {
                                        substring = str8;
                                        i5 = 0;
                                    }
                                    c0871m = new C0871m(m1324u, m1324u2, j2, str5, substring, z2, z3, z4, z5);
                                }
                            }
                            i5 = 0;
                            c0871m = null;
                            if (c0871m != null) {
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                }
                                arrayList2.add(c0871m);
                            }
                            i7++;
                            unmodifiableList = list;
                            size = i2;
                        }
                    }
                    i2 = size;
                    i5 = 0;
                    c0871m = null;
                    if (c0871m != null) {
                    }
                    i7++;
                    unmodifiableList = list;
                    size = i2;
                }
            }
            list = unmodifiableList;
            i2 = size;
            c0871m = null;
            if (c0871m != null) {
            }
            i7++;
            unmodifiableList = list;
            size = i2;
        }
        List unmodifiableList2 = arrayList2 != null ? Collections.unmodifiableList(arrayList2) : Collections.emptyList();
        if (unmodifiableList2.isEmpty()) {
            return;
        }
        interfaceC0872n.mo296e(c0879u, unmodifiableList2);
    }

    /* renamed from: e */
    public static int m1380e(String str, int i2, String str2) {
        while (i2 < str.length() && str2.indexOf(str.charAt(i2)) == -1) {
            i2++;
        }
        return i2;
    }
}
