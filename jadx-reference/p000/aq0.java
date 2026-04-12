package p000;

import kotlin.random.Random$Default;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class aq0 {

    /* renamed from: a0 */
    public static final Random$Default f45594a0 = new Random$Default(0);

    /* renamed from: a1 */
    public static final AbstractC0566h2 f45595a1;

    static {
        Integer num = f70.f56158a0;
        f45595a1 = (num == null || num.intValue() >= 34) ? new on0() : new C1486yn();
    }

    /* renamed from: a0 */
    public abstract int mo210496a0(int i);

    /* renamed from: a1 */
    public abstract int mo210497a1();

    /* renamed from: a2 */
    public int mo210498a2(int i, int i2) {
        int iMo210497a1;
        int i3;
        int iMo210496a0;
        if (i2 <= i) {
            throw new IllegalArgumentException(kj1.m213557a4(Integer.valueOf(i), Integer.valueOf(i2)).toString());
        }
        int i4 = i2 - i;
        if (i4 > 0 || i4 == Integer.MIN_VALUE) {
            if (((-i4) & i4) == i4) {
                iMo210496a0 = mo210496a0(31 - Integer.numberOfLeadingZeros(i4));
            } else {
                do {
                    iMo210497a1 = mo210497a1() >>> 1;
                    i3 = iMo210497a1 % i4;
                } while ((i4 - 1) + (iMo210497a1 - i3) < 0);
                iMo210496a0 = i3;
            }
            return i + iMo210496a0;
        }
        while (true) {
            int iMo210497a12 = mo210497a1();
            if (i <= iMo210497a12 && iMo210497a12 < i2) {
                return iMo210497a12;
            }
        }
    }
}
