package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class Tables64kGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private byte[] f1279H;

    /* renamed from: T */
    private long[][][] f1280T;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        if (this.f1280T == null) {
            this.f1280T = (long[][][]) Array.newInstance((Class<?>) Long.TYPE, 16, 256, 2);
        } else if (GCMUtil.areEqual(this.f1279H, bArr) != 0) {
            return;
        }
        byte[] bArr2 = new byte[16];
        this.f1279H = bArr2;
        GCMUtil.copy(bArr, bArr2);
        for (int i2 = 0; i2 < 16; i2++) {
            long[][][] jArr = this.f1280T;
            long[][] jArr2 = jArr[i2];
            if (i2 == 0) {
                GCMUtil.asLongs(this.f1279H, jArr2[1]);
                long[] jArr3 = jArr2[1];
                GCMUtil.multiplyP7(jArr3, jArr3);
            } else {
                GCMUtil.multiplyP8(jArr[i2 - 1][1], jArr2[1]);
            }
            for (int i3 = 2; i3 < 256; i3 += 2) {
                GCMUtil.divideP(jArr2[i3 >> 1], jArr2[i3]);
                GCMUtil.xor(jArr2[i3], jArr2[1], jArr2[i3 + 1]);
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        long[] jArr = this.f1280T[15][bArr[15] & 255];
        long j2 = jArr[0];
        long j3 = jArr[1];
        for (int i2 = 14; i2 >= 0; i2--) {
            long[] jArr2 = this.f1280T[i2][bArr[i2] & 255];
            j2 ^= jArr2[0];
            j3 ^= jArr2[1];
        }
        Pack.longToBigEndian(j2, bArr, 0);
        Pack.longToBigEndian(j3, bArr, 8);
    }
}
