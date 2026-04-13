package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class Tables8kGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private byte[] f1281H;

    /* renamed from: T */
    private long[][][] f1282T;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        if (this.f1282T == null) {
            this.f1282T = (long[][][]) Array.newInstance((Class<?>) Long.TYPE, 32, 16, 2);
        } else if (GCMUtil.areEqual(this.f1281H, bArr) != 0) {
            return;
        }
        byte[] bArr2 = new byte[16];
        this.f1281H = bArr2;
        GCMUtil.copy(bArr, bArr2);
        for (int i2 = 0; i2 < 32; i2++) {
            long[][][] jArr = this.f1282T;
            long[][] jArr2 = jArr[i2];
            if (i2 == 0) {
                GCMUtil.asLongs(this.f1281H, jArr2[1]);
                long[] jArr3 = jArr2[1];
                GCMUtil.multiplyP3(jArr3, jArr3);
            } else {
                GCMUtil.multiplyP4(jArr[i2 - 1][1], jArr2[1]);
            }
            for (int i3 = 2; i3 < 16; i3 += 2) {
                GCMUtil.divideP(jArr2[i3 >> 1], jArr2[i3]);
                GCMUtil.xor(jArr2[i3], jArr2[1], jArr2[i3 + 1]);
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        long j2 = 0;
        long j3 = 0;
        for (int i2 = 15; i2 >= 0; i2--) {
            long[][][] jArr = this.f1282T;
            int i3 = i2 + i2;
            long[][] jArr2 = jArr[i3 + 1];
            byte b = bArr[i2];
            long[] jArr3 = jArr2[b & 15];
            long[] jArr4 = jArr[i3][(b & 240) >>> 4];
            j2 ^= jArr3[0] ^ jArr4[0];
            j3 ^= jArr4[1] ^ jArr3[1];
        }
        Pack.longToBigEndian(j2, bArr, 0);
        Pack.longToBigEndian(j3, bArr, 8);
    }
}
