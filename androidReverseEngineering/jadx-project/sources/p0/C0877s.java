package p0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;

/* renamed from: p0.s */
/* loaded from: classes.dex */
public final class C0877s {

    /* renamed from: a */
    public final String[] f1896a;

    public C0877s(C0864f c0864f) {
        ArrayList arrayList = c0864f.f1776a;
        this.f1896a = (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    /* renamed from: a */
    public static void m1278a(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (str.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt <= ' ' || charAt >= 127) {
                throw new IllegalArgumentException(AbstractC0887c.m1312i(new Object[]{Integer.valueOf(charAt), Integer.valueOf(i2), str}, "Unexpected char %#04x at %d in header name: %s"));
            }
        }
    }

    /* renamed from: b */
    public static void m1279b(String str, String str2) {
        if (str == null) {
            throw new NullPointerException(AbstractC0000a.m16l("value for name ", str2, " == null"));
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if ((charAt <= 31 && charAt != '\t') || charAt >= 127) {
                throw new IllegalArgumentException(AbstractC0887c.m1312i(new Object[]{Integer.valueOf(charAt), Integer.valueOf(i2), str2, str}, "Unexpected char %#04x at %d in %s value: %s"));
            }
        }
    }

    /* renamed from: c */
    public final String m1280c(String str) {
        String[] strArr = this.f1896a;
        int length = strArr.length;
        do {
            length -= 2;
            if (length < 0) {
                return null;
            }
        } while (!str.equalsIgnoreCase(strArr[length]));
        return strArr[length + 1];
    }

    /* renamed from: d */
    public final String m1281d(int i2) {
        return this.f1896a[i2 * 2];
    }

    /* renamed from: e */
    public final C0864f m1282e() {
        C0864f c0864f = new C0864f();
        Collections.addAll(c0864f.f1776a, this.f1896a);
        return c0864f;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C0877s) && Arrays.equals(((C0877s) obj).f1896a, this.f1896a);
    }

    /* renamed from: f */
    public final String m1283f(int i2) {
        return this.f1896a[(i2 * 2) + 1];
    }

    public final int hashCode() {
        return Arrays.hashCode(this.f1896a);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int length = this.f1896a.length / 2;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(m1281d(i2));
            sb.append(": ");
            sb.append(m1283f(i2));
            sb.append("\n");
        }
        return sb.toString();
    }

    public C0877s(String[] strArr) {
        this.f1896a = strArr;
    }
}
