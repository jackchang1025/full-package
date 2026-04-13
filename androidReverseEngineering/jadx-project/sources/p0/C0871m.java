package p0;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import q0.AbstractC0887c;
import t0.AbstractC0915d;

/* renamed from: p0.m */
/* loaded from: classes.dex */
public final class C0871m {

    /* renamed from: j */
    public static final Pattern f1861j = Pattern.compile("(\\d{2,4})[^\\d]*");

    /* renamed from: k */
    public static final Pattern f1862k = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");

    /* renamed from: l */
    public static final Pattern f1863l = Pattern.compile("(\\d{1,2})[^\\d]*");

    /* renamed from: m */
    public static final Pattern f1864m = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");

    /* renamed from: a */
    public final String f1865a;

    /* renamed from: b */
    public final String f1866b;

    /* renamed from: c */
    public final long f1867c;

    /* renamed from: d */
    public final String f1868d;

    /* renamed from: e */
    public final String f1869e;

    /* renamed from: f */
    public final boolean f1870f;

    /* renamed from: g */
    public final boolean f1871g;

    /* renamed from: h */
    public final boolean f1872h;

    /* renamed from: i */
    public final boolean f1873i;

    public C0871m(String str, String str2, long j2, String str3, String str4, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.f1865a = str;
        this.f1866b = str2;
        this.f1867c = j2;
        this.f1868d = str3;
        this.f1869e = str4;
        this.f1870f = z2;
        this.f1871g = z3;
        this.f1873i = z4;
        this.f1872h = z5;
    }

    /* renamed from: a */
    public static int m1270a(String str, int i2, int i3, boolean z2) {
        while (i2 < i3) {
            char charAt = str.charAt(i2);
            if (((charAt < ' ' && charAt != '\t') || charAt >= 127 || (charAt >= '0' && charAt <= '9') || ((charAt >= 'a' && charAt <= 'z') || ((charAt >= 'A' && charAt <= 'Z') || charAt == ':'))) == (!z2)) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    /* renamed from: b */
    public static long m1271b(String str, int i2) {
        int m1270a = m1270a(str, 0, i2, false);
        Pattern pattern = f1864m;
        Matcher matcher = pattern.matcher(str);
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        int i6 = -1;
        int i7 = -1;
        int i8 = -1;
        while (m1270a < i2) {
            int m1270a2 = m1270a(str, m1270a + 1, i2, true);
            matcher.region(m1270a, m1270a2);
            if (i4 == -1 && matcher.usePattern(pattern).matches()) {
                i4 = Integer.parseInt(matcher.group(1));
                i7 = Integer.parseInt(matcher.group(2));
                i8 = Integer.parseInt(matcher.group(3));
            } else if (i5 == -1 && matcher.usePattern(f1863l).matches()) {
                i5 = Integer.parseInt(matcher.group(1));
            } else {
                if (i6 == -1) {
                    Pattern pattern2 = f1862k;
                    if (matcher.usePattern(pattern2).matches()) {
                        i6 = pattern2.pattern().indexOf(matcher.group(1).toLowerCase(Locale.US)) / 4;
                    }
                }
                if (i3 == -1 && matcher.usePattern(f1861j).matches()) {
                    i3 = Integer.parseInt(matcher.group(1));
                }
            }
            m1270a = m1270a(str, m1270a2 + 1, i2, false);
        }
        if (i3 >= 70 && i3 <= 99) {
            i3 += 1900;
        }
        if (i3 >= 0 && i3 <= 69) {
            i3 += 2000;
        }
        if (i3 < 1601) {
            throw new IllegalArgumentException();
        }
        if (i6 == -1) {
            throw new IllegalArgumentException();
        }
        if (i5 < 1 || i5 > 31) {
            throw new IllegalArgumentException();
        }
        if (i4 < 0 || i4 > 23) {
            throw new IllegalArgumentException();
        }
        if (i7 < 0 || i7 > 59) {
            throw new IllegalArgumentException();
        }
        if (i8 < 0 || i8 > 59) {
            throw new IllegalArgumentException();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(AbstractC0887c.f1941h);
        gregorianCalendar.setLenient(false);
        gregorianCalendar.set(1, i3);
        gregorianCalendar.set(2, i6 - 1);
        gregorianCalendar.set(5, i5);
        gregorianCalendar.set(11, i4);
        gregorianCalendar.set(12, i7);
        gregorianCalendar.set(13, i8);
        gregorianCalendar.set(14, 0);
        return gregorianCalendar.getTimeInMillis();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0871m)) {
            return false;
        }
        C0871m c0871m = (C0871m) obj;
        return c0871m.f1865a.equals(this.f1865a) && c0871m.f1866b.equals(this.f1866b) && c0871m.f1868d.equals(this.f1868d) && c0871m.f1869e.equals(this.f1869e) && c0871m.f1867c == this.f1867c && c0871m.f1870f == this.f1870f && c0871m.f1871g == this.f1871g && c0871m.f1872h == this.f1872h && c0871m.f1873i == this.f1873i;
    }

    public final int hashCode() {
        int hashCode = (this.f1869e.hashCode() + ((this.f1868d.hashCode() + ((this.f1866b.hashCode() + ((this.f1865a.hashCode() + 527) * 31)) * 31)) * 31)) * 31;
        long j2 = this.f1867c;
        return ((((((((hashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (!this.f1870f ? 1 : 0)) * 31) + (!this.f1871g ? 1 : 0)) * 31) + (!this.f1872h ? 1 : 0)) * 31) + (!this.f1873i ? 1 : 0);
    }

    public final String toString() {
        String format;
        StringBuilder sb = new StringBuilder();
        sb.append(this.f1865a);
        sb.append('=');
        sb.append(this.f1866b);
        if (this.f1872h) {
            long j2 = this.f1867c;
            if (j2 == Long.MIN_VALUE) {
                format = "; max-age=0";
            } else {
                sb.append("; expires=");
                format = ((DateFormat) AbstractC0915d.f2069a.get()).format(new Date(j2));
            }
            sb.append(format);
        }
        if (!this.f1873i) {
            sb.append("; domain=");
            sb.append(this.f1868d);
        }
        sb.append("; path=");
        sb.append(this.f1869e);
        if (this.f1870f) {
            sb.append("; secure");
        }
        if (this.f1871g) {
            sb.append("; httponly");
        }
        return sb.toString();
    }

    public C0871m(C0870l c0870l) {
        String str = c0870l.f1852a;
        if (str == null) {
            throw new NullPointerException("builder.name == null");
        }
        String str2 = c0870l.f1853b;
        if (str2 == null) {
            throw new NullPointerException("builder.value == null");
        }
        String str3 = c0870l.f1855d;
        if (str3 == null) {
            throw new NullPointerException("builder.domain == null");
        }
        this.f1865a = str;
        this.f1866b = str2;
        this.f1867c = c0870l.f1854c;
        this.f1868d = str3;
        this.f1869e = c0870l.f1856e;
        this.f1870f = c0870l.f1857f;
        this.f1871g = c0870l.f1858g;
        this.f1872h = c0870l.f1859h;
        this.f1873i = c0870l.f1860i;
    }
}
