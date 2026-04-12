package p000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class d90 {

    /* renamed from: a0 */
    public final c90 f55580a0;

    /* renamed from: a1 */
    public final List f55581a1;

    /* renamed from: a2 */
    public final List f55582a2;

    /* renamed from: a3 */
    public final float[] f55583a3;

    /* renamed from: a4 */
    public final float[] f55584a4;

    /* renamed from: a5 */
    public final float f55585a5;

    /* renamed from: a6 */
    public final float f55586a6;

    public d90(c90 c90Var, ArrayList arrayList, ArrayList arrayList2) {
        this.f55580a0 = c90Var;
        this.f55581a1 = Collections.unmodifiableList(arrayList);
        this.f55582a2 = Collections.unmodifiableList(arrayList2);
        float f = ((c90) arrayList.get(arrayList.size() - 1)).m210773a1().f45751a0 - c90Var.m210773a1().f45751a0;
        this.f55585a5 = f;
        float f2 = c90Var.m210775a3().f45751a0 - ((c90) arrayList2.get(arrayList2.size() - 1)).m210775a3().f45751a0;
        this.f55586a6 = f2;
        this.f55583a3 = m212562a0(f, arrayList, true);
        this.f55584a4 = m212562a0(f2, arrayList2, false);
    }

    /* renamed from: a0 */
    public static float[] m212562a0(float f, ArrayList arrayList, boolean z) {
        int size = arrayList.size();
        float[] fArr = new float[size];
        int i = 1;
        while (i < size) {
            int i2 = i - 1;
            c90 c90Var = (c90) arrayList.get(i2);
            c90 c90Var2 = (c90) arrayList.get(i);
            fArr[i] = i == size + (-1) ? 1.0f : fArr[i2] + ((z ? c90Var2.m210773a1().f45751a0 - c90Var.m210773a1().f45751a0 : c90Var.m210775a3().f45751a0 - c90Var2.m210775a3().f45751a0) / f);
            i++;
        }
        return fArr;
    }

    /* renamed from: a1 */
    public static c90 m212563a1(List list, float f, float[] fArr) {
        int size = list.size();
        float f2 = fArr[0];
        int i = 1;
        while (i < size) {
            float f3 = fArr[i];
            if (f <= f3) {
                float fM214728a1 = AbstractC1249t7.m214728a1(0.0f, 1.0f, f2, f3, f);
                c90 c90Var = (c90) list.get(i - 1);
                c90 c90Var2 = (c90) list.get(i);
                float f4 = c90Var.f46078a0;
                List list2 = c90Var.f46079a1;
                if (f4 != c90Var2.f46078a0) {
                    throw new IllegalArgumentException("Keylines being linearly interpolated must have the same item size.");
                }
                List list3 = c90Var2.f46079a1;
                if (list2.size() != list3.size()) {
                    throw new IllegalArgumentException("Keylines being linearly interpolated must have the same number of keylines.");
                }
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    b90 b90Var = (b90) list2.get(i2);
                    b90 b90Var2 = (b90) list3.get(i2);
                    arrayList.add(new b90(AbstractC1249t7.m214727a0(b90Var.f45751a0, b90Var2.f45751a0, fM214728a1), AbstractC1249t7.m214727a0(b90Var.f45752a1, b90Var2.f45752a1, fM214728a1), AbstractC1249t7.m214727a0(b90Var.f45753a2, b90Var2.f45753a2, fM214728a1), AbstractC1249t7.m214727a0(b90Var.f45754a3, b90Var2.f45754a3, fM214728a1)));
                }
                return new c90(c90Var.f46078a0, arrayList, AbstractC1249t7.m214729a2(c90Var.f46080a2, fM214728a1, c90Var2.f46080a2), AbstractC1249t7.m214729a2(c90Var.f46081a3, fM214728a1, c90Var2.f46081a3));
            }
            i++;
            f2 = f3;
        }
        return (c90) list.get(0);
    }

    /* renamed from: a2 */
    public static c90 m212564a2(c90 c90Var, int i, int i2, float f, int i3, int i4) {
        ArrayList arrayList = new ArrayList(c90Var.f46079a1);
        arrayList.add(i2, (b90) arrayList.remove(i));
        a90 a90Var = new a90(c90Var.f46078a0);
        int i5 = 0;
        while (i5 < arrayList.size()) {
            b90 b90Var = (b90) arrayList.get(i5);
            float f2 = b90Var.f45754a3;
            a90Var.m70a0((f2 / 2.0f) + f, b90Var.f45753a2, f2, i5 >= i3 && i5 <= i4);
            f += b90Var.f45754a3;
            i5++;
        }
        return a90Var.m71a1();
    }
}
