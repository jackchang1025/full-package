package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class zp0 {
    public static int nextInt(SecureRandom secureRandom, int i) {
        int iNextInt;
        int i2;
        if (((-i) & i) == i) {
            return (int) ((i * (secureRandom.nextInt() >>> 1)) >> 31);
        }
        do {
            iNextInt = secureRandom.nextInt() >>> 1;
            i2 = iNextInt % i;
        } while ((i - 1) + (iNextInt - i2) < 0);
        return i2;
    }
}
