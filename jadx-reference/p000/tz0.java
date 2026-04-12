package p000;

import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class tz0 {
    /* renamed from: a0 */
    public static int m214800a0(int i, int i2, int i3) {
        return (Integer.hashCode(i) + i2) * i3;
    }

    /* renamed from: a1 */
    public static int m214801a1(int i, int i2, String str) {
        return (str.hashCode() + i) * i2;
    }

    /* renamed from: a2 */
    public static String m214802a2(int i, String str) {
        return str + i;
    }

    /* renamed from: a3 */
    public static String m214803a3(String str, long j) {
        return str + j;
    }

    /* renamed from: a4 */
    public static void m214804a4(int i, int i2, C0160c5 c0160c5, HashMap map, Integer num) {
        map.put(num, new qi1(i, i2, c0160c5));
    }

    /* renamed from: a5 */
    public static /* synthetic */ void m214805a5(Object obj) {
        if (obj != null) {
            throw new ClassCastException();
        }
    }

    /* renamed from: a6 */
    public static void m214806a6(String str, int i, String str2, String str3) {
        t60.m214704c5(str3, str + i + str2);
    }

    /* renamed from: a7 */
    public static void m214807a7(String str, String str2, String str3) {
        t60.m214704c5(str3, str + str2);
    }

    /* renamed from: a8 */
    public static void m214808a8(String str, String str2, String str3, Exception exc) {
        t60.m214705c6(str3, str + str2, exc);
    }

    /* renamed from: a9 */
    public static void m214809a9(String str, String str2, String str3, String str4) {
        t60.m214704c5(str4, str + str2 + str3);
    }

    /* renamed from: b0 */
    public static void m214810b0(String str, String str2, String str3) {
        t60.m214726f4(str3, str + str2);
    }
}
