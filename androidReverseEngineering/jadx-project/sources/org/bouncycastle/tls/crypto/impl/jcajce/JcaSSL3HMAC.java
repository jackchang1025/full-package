package org.bouncycastle.tls.crypto.impl.jcajce;

import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
class JcaSSL3HMAC implements TlsHMAC {
    private TlsHash digest;
    private final int digestSize;
    private final int internalBlockSize;
    private int padLength;
    private byte[] secret;
    private static final byte IPAD_BYTE = 54;
    private static final byte[] IPAD = genPad(IPAD_BYTE, 48);
    private static final byte OPAD_BYTE = 92;
    private static final byte[] OPAD = genPad(OPAD_BYTE, 48);

    public JcaSSL3HMAC(TlsHash tlsHash, int i2, int i3) {
        this.digest = tlsHash;
        this.digestSize = i2;
        this.internalBlockSize = i3;
        this.padLength = i2 == 20 ? 40 : 48;
    }

    private static byte[] genPad(byte b, int i2) {
        byte[] bArr = new byte[i2];
        Arrays.fill(bArr, b);
        return bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void calculateMAC(byte[] bArr, int i2) {
        byte[] calculateMAC = calculateMAC();
        System.arraycopy(calculateMAC, 0, bArr, i2, calculateMAC.length);
    }

    @Override // org.bouncycastle.tls.crypto.TlsHMAC
    public int getInternalBlockSize() {
        return this.internalBlockSize;
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public int getMacLength() {
        return this.digestSize;
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void reset() {
        this.digest.reset();
        TlsHash tlsHash = this.digest;
        byte[] bArr = this.secret;
        tlsHash.update(bArr, 0, bArr.length);
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
        byte[] calculateHash = this.digest.calculateHash();
        TlsHash tlsHash = this.digest;
        byte[] bArr = this.secret;
        tlsHash.update(bArr, 0, bArr.length);
        this.digest.update(OPAD, 0, this.padLength);
        this.digest.update(calculateHash, 0, calculateHash.length);
        byte[] calculateHash2 = this.digest.calculateHash();
        reset();
        return calculateHash2;
    }
}
