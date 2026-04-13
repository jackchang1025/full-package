package org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes.dex */
public class BasicGCMExponentiator implements GCMExponentiator {

    /* renamed from: x */
    private long[] f1275x;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j2, byte[] bArr) {
        long[] oneAsLongs = GCMUtil.oneAsLongs();
        if (j2 > 0) {
            long[] jArr = new long[2];
            GCMUtil.copy(this.f1275x, jArr);
            do {
                if ((1 & j2) != 0) {
                    GCMUtil.multiply(oneAsLongs, jArr);
                }
                GCMUtil.square(jArr, jArr);
                j2 >>>= 1;
            } while (j2 > 0);
        }
        GCMUtil.asBytes(oneAsLongs, bArr);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        this.f1275x = GCMUtil.asLongs(bArr);
    }
}
