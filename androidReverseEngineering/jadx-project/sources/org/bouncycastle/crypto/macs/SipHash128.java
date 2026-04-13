package org.bouncycastle.crypto.macs;

import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class SipHash128 extends SipHash {
    public SipHash128() {
    }

    public SipHash128(int i2, int i3) {
        super(i2, i3);
    }

    @Override // org.bouncycastle.crypto.macs.SipHash, org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        this.f1254m = ((this.f1254m >>> ((7 - this.wordPos) << 3)) >>> 8) | ((((this.wordCount << 3) + r2) & 255) << 56);
        processMessageWord();
        this.v2 ^= 238;
        applySipRounds(this.f1253d);
        long j2 = this.f2345v0;
        long j3 = this.v1;
        long j4 = ((j2 ^ j3) ^ this.v2) ^ this.v3;
        this.v1 = j3 ^ 221;
        applySipRounds(this.f1253d);
        long j5 = ((this.f2345v0 ^ this.v1) ^ this.v2) ^ this.v3;
        reset();
        Pack.longToLittleEndian(j4, bArr, i2);
        Pack.longToLittleEndian(j5, bArr, i2 + 8);
        return 16;
    }

    @Override // org.bouncycastle.crypto.macs.SipHash, org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "SipHash128-" + this.f1252c + "-" + this.f1253d;
    }

    @Override // org.bouncycastle.crypto.macs.SipHash, org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.macs.SipHash, org.bouncycastle.crypto.Mac
    public void reset() {
        super.reset();
        this.v1 ^= 238;
    }

    @Override // org.bouncycastle.crypto.macs.SipHash
    public long doFinal() {
        throw new UnsupportedOperationException("doFinal() is not supported");
    }
}
