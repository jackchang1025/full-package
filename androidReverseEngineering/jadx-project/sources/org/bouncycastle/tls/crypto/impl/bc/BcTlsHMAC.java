package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.tls.crypto.TlsHMAC;

/* loaded from: classes.dex */
final class BcTlsHMAC implements TlsHMAC {
    private final HMac hmac;

    public BcTlsHMAC(HMac hMac) {
        this.hmac = hMac;
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void calculateMAC(byte[] bArr, int i2) {
        this.hmac.doFinal(bArr, i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHMAC
    public int getInternalBlockSize() {
        return ((ExtendedDigest) this.hmac.getUnderlyingDigest()).getByteLength();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public int getMacLength() {
        return this.hmac.getMacSize();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void reset() {
        this.hmac.reset();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void setKey(byte[] bArr, int i2, int i3) {
        this.hmac.init(new KeyParameter(bArr, i2, i3));
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void update(byte[] bArr, int i2, int i3) {
        this.hmac.update(bArr, i2, i3);
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public byte[] calculateMAC() {
        byte[] bArr = new byte[this.hmac.getMacSize()];
        this.hmac.doFinal(bArr, 0);
        return bArr;
    }
}
