package org.bouncycastle.pqc.crypto.lms;

import org.bouncycastle.crypto.Digest;

/* loaded from: classes.dex */
class SeedDerive {

    /* renamed from: I */
    private final byte[] f1568I;
    private final Digest digest;

    /* renamed from: j */
    private int f1569j;
    private final byte[] masterSeed;

    /* renamed from: q */
    private int f1570q;

    public SeedDerive(byte[] bArr, byte[] bArr2, Digest digest) {
        this.f1568I = bArr;
        this.masterSeed = bArr2;
        this.digest = digest;
    }

    public void deriveSeed(byte[] bArr, boolean z2) {
        deriveSeed(bArr, z2, 0);
    }

    public byte[] getI() {
        return this.f1568I;
    }

    public int getJ() {
        return this.f1569j;
    }

    public byte[] getMasterSeed() {
        return this.masterSeed;
    }

    public int getQ() {
        return this.f1570q;
    }

    public void setJ(int i2) {
        this.f1569j = i2;
    }

    public void setQ(int i2) {
        this.f1570q = i2;
    }

    public void deriveSeed(byte[] bArr, boolean z2, int i2) {
        deriveSeed(bArr, i2);
        if (z2) {
            this.f1569j++;
        }
    }

    public byte[] deriveSeed(byte[] bArr, int i2) {
        if (bArr.length < this.digest.getDigestSize()) {
            throw new IllegalArgumentException("target length is less than digest size.");
        }
        Digest digest = this.digest;
        byte[] bArr2 = this.f1568I;
        digest.update(bArr2, 0, bArr2.length);
        this.digest.update((byte) (this.f1570q >>> 24));
        this.digest.update((byte) (this.f1570q >>> 16));
        this.digest.update((byte) (this.f1570q >>> 8));
        this.digest.update((byte) this.f1570q);
        this.digest.update((byte) (this.f1569j >>> 8));
        this.digest.update((byte) this.f1569j);
        this.digest.update((byte) -1);
        Digest digest2 = this.digest;
        byte[] bArr3 = this.masterSeed;
        digest2.update(bArr3, 0, bArr3.length);
        this.digest.doFinal(bArr, i2);
        return bArr;
    }
}
