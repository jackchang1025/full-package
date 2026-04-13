package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
class BcSSL3HMAC implements TlsHMAC {
    private Digest digest;
    private int padLength;
    private byte[] secret;
    private static final byte IPAD_BYTE = 54;
    private static final byte[] IPAD = genPad(IPAD_BYTE, 48);
    private static final byte OPAD_BYTE = 92;
    private static final byte[] OPAD = genPad(OPAD_BYTE, 48);

    public BcSSL3HMAC(Digest digest) {
        this.digest = digest;
        this.padLength = digest.getDigestSize() == 20 ? 40 : 48;
    }

    private void doFinal(byte[] bArr, int i2) {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        this.digest.doFinal(bArr2, 0);
        Digest digest = this.digest;
        byte[] bArr3 = this.secret;
        digest.update(bArr3, 0, bArr3.length);
        this.digest.update(OPAD, 0, this.padLength);
        this.digest.update(bArr2, 0, digestSize);
        this.digest.doFinal(bArr, i2);
        reset();
    }

    private static byte[] genPad(byte b, int i2) {
        byte[] bArr = new byte[i2];
        Arrays.fill(bArr, b);
        return bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void calculateMAC(byte[] bArr, int i2) {
        doFinal(bArr, i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHMAC
    public int getInternalBlockSize() {
        return ((ExtendedDigest) this.digest).getByteLength();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public int getMacLength() {
        return this.digest.getDigestSize();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void reset() {
        this.digest.reset();
        Digest digest = this.digest;
        byte[] bArr = this.secret;
        digest.update(bArr, 0, bArr.length);
        this.digest.update(IPAD, 0, this.padLength);
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void setKey(byte[] bArr, int i2, int i3) {
        this.secret = TlsUtils.copyOfRangeExact(bArr, i2, i3 + i2);
        reset();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public byte[] calculateMAC() {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        doFinal(bArr, 0);
        return bArr;
    }
}
