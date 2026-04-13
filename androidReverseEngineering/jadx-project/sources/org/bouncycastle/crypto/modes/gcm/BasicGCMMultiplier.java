package org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes.dex */
public class BasicGCMMultiplier implements GCMMultiplier {

    /* renamed from: H */
    private long[] f1276H;

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        this.f1276H = GCMUtil.asLongs(bArr);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        long[] asLongs = GCMUtil.asLongs(bArr);
        GCMUtil.multiply(asLongs, this.f1276H);
        GCMUtil.asBytes(asLongs, bArr);
    }
}
