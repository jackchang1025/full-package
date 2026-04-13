package org.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

/* loaded from: classes.dex */
public class RandUtils {
    public static int nextInt(SecureRandom secureRandom, int i2) {
        int nextInt;
        int i3;
        if (((-i2) & i2) == i2) {
            return (int) ((i2 * (secureRandom.nextInt() >>> 1)) >> 31);
        }
        do {
            nextInt = secureRandom.nextInt() >>> 1;
            i3 = nextInt % i2;
        } while ((i2 - 1) + (nextInt - i3) < 0);
        return i3;
    }
}
