package org.bouncycastle.crypto.modes.kgcm;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class Tables8kKGCMMultiplier_256 implements KGCMMultiplier {

    /* renamed from: T */
    private long[][] f1288T;

    @Override // org.bouncycastle.crypto.modes.kgcm.KGCMMultiplier
    public void init(long[] jArr) {
        long[][] jArr2 = this.f1288T;
        if (jArr2 == null) {
            this.f1288T = (long[][]) Array.newInstance((Class<?>) Long.TYPE, 256, 4);
        } else if (KGCMUtil_256.equal(jArr, jArr2[1])) {
            return;
        }
        KGCMUtil_256.copy(jArr, this.f1288T[1]);
        for (int i2 = 2; i2 < 256; i2 += 2) {
            long[][] jArr3 = this.f1288T;
            KGCMUtil_256.multiplyX(jArr3[i2 >> 1], jArr3[i2]);
            long[][] jArr4 = this.f1288T;
            KGCMUtil_256.add(jArr4[i2], jArr4[1], jArr4[i2 + 1]);
        }
    }

    @Override // org.bouncycastle.crypto.modes.kgcm.KGCMMultiplier
    public void multiplyH(long[] jArr) {
        long[] jArr2 = new long[4];
        KGCMUtil_256.copy(this.f1288T[((int) (jArr[3] >>> 56)) & 255], jArr2);
        for (int i2 = 30; i2 >= 0; i2--) {
            KGCMUtil_256.multiplyX8(jArr2, jArr2);
            KGCMUtil_256.add(this.f1288T[((int) (jArr[i2 >>> 3] >>> ((i2 & 7) << 3))) & 255], jArr2, jArr2);
        }
        KGCMUtil_256.copy(jArr2, jArr);
    }
}
