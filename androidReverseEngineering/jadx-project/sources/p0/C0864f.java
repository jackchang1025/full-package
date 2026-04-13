package p0;

import java.util.ArrayList;

/* renamed from: p0.f */
/* loaded from: classes.dex */
public final class C0864f {

    /* renamed from: a */
    public final ArrayList f1776a = new ArrayList(20);

    /* renamed from: a */
    public final void m1251a(String str, String str2) {
        ArrayList arrayList = this.f1776a;
        arrayList.add(str);
        arrayList.add(str2.trim());
    }

    /* renamed from: b */
    public final void m1252b(String str) {
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.f1776a;
            if (i2 >= arrayList.size()) {
                return;
            }
            if (str.equalsIgnoreCase((String) arrayList.get(i2))) {
                arrayList.remove(i2);
                arrayList.remove(i2);
                i2 -= 2;
            }
            i2 += 2;
        }
    }

    /* renamed from: c */
    public final void m1253c(String str, String str2) {
        C0877s.m1278a(str);
        C0877s.m1279b(str2, str);
        m1252b(str);
        m1251a(str, str2);
    }
}
