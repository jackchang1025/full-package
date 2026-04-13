package org.bouncycastle.crypto.modes.kgcm;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class Tables16kKGCMMultiplier_512 implements KGCMMultiplier {

    /* renamed from: T */
    private long[][] f1286T;

    @Override // org.bouncycastle.crypto.modes.kgcm.KGCMMultiplier
    public void init(long[] jArr) {
        long[][] jArr2 = this.f1286T;
        if (jArr2 == null) {
            this.f1286T = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 256, 8);
        } else if (KGCMUtil_512.equal(jArr, jArr2[1])) {
            return;
        }
        KGCMUtil_512.copy(jArr, this.f1286T[1]);
        for (int i2 = 2; i2 < 256; i2 += 2) {
            long[][] jArr3 = this.f1286T;
            KGCMUtil_512.multiplyX(jArr3[i2 >> 1], jArr3[i2]);
            long[][] jArr4 = this.f1286T;
            KGCMUtil_512.add(jArr4[i2], jArr4[1], jArr4[i2 + 1]);
        }
    }

    @Override // org.bouncycastle.crypto.modes.kgcm.KGCMMultiplier
    public void multiplyH(long[] jArr) {
        long[] jArr2 = new long[8];
        KGCMUtil_512.copy(this.f1286T[((int) (jArr[7] >>> 56)) & 255], jArr2);
        for (int i2 = 62; i2 >= 0; i2--) {
            KGCMUtil_512.multiplyX8(jArr2, jArr2);
            KGCMUtil_512.add(this.f1286T[((int) (jArr[i2 >>> 3] >>> ((i2 & 7) << 3))) & 255], jArr2, jArr2);
        }
        KGCMUtil_512.copy(jArr2, jArr);
    }
}
