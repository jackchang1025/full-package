package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.tls.crypto.impl.AbstractTlsCrypto;
import org.bouncycastle.tls.crypto.impl.AbstractTlsSecret;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class BcTlsSecret extends AbstractTlsSecret {
    private static final byte[] SSL3_CONST = generateSSL3Constants();
    protected final BcTlsCrypto crypto;

    public BcTlsSecret(BcTlsCrypto bcTlsCrypto, byte[] bArr) {
        super(bArr);
        this.crypto = bcTlsCrypto;
    }

    public static BcTlsSecret convert(BcTlsCrypto bcTlsCrypto, TlsSecret tlsSecret) {
        if (tlsSecret instanceof BcTlsSecret) {
            return (BcTlsSecret) tlsSecret;
        }
        if (tlsSecret instanceof AbstractTlsSecret) {
            return bcTlsCrypto.adoptLocalSecret(AbstractTlsSecret.copyData((AbstractTlsSecret) tlsSecret));
        }
        throw new IllegalArgumentException("unrecognized TlsSecret - cannot copy data: ".concat(tlsSecret.getClass().getName()));
    }

    private static byte[] generateSSL3Constants() {
        byte[] bArr = new byte[120];
        int i2 = 0;
        for (int i3 = 0; i3 < 15; i3++) {
            byte b = (byte) (i3 + 65);
            int i4 = 0;
            while (i4 <= i3) {
                bArr[i2] = b;
                i4++;
                i2++;
            }
        }
        return bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized TlsSecret deriveUsingPRF(int i2, String str, byte[] bArr, int i3) {
        checkAlive();
        try {
            if (i2 == 4) {
                return TlsCryptoUtils.hkdfExpandLabel(this, 4, str, bArr, i3);
            }
            if (i2 == 5) {
                return TlsCryptoUtils.hkdfExpandLabel(this, 5, str, bArr, i3);
            }
            if (i2 != 7) {
                return this.crypto.adoptLocalSecret(prf(i2, str, bArr, i3));
            }
            return TlsCryptoUtils.hkdfExpandLabel(this, 7, str, bArr, i3);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.AbstractTlsSecret
    public AbstractTlsCrypto getCrypto() {
        return this.crypto;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized TlsSecret hkdfExpand(int i2, byte[] bArr, int i3) {
        if (i3 < 1) {
            return this.crypto.adoptLocalSecret(TlsUtils.EMPTY_BYTES);
        }
        int hashOutputSize = TlsCryptoUtils.getHashOutputSize(i2);
        if (i3 > hashOutputSize * 255) {
            throw new IllegalArgumentException("'length' must be <= 255 * (output size of 'hashAlgorithm')");
        }
        checkAlive();
        byte[] bArr2 = this.data;
        HMac hMac = new HMac(this.crypto.createDigest(i2));
        hMac.init(new KeyParameter(bArr2));
        byte[] bArr3 = new byte[i3];
        byte[] bArr4 = new byte[hashOutputSize];
        byte b = 0;
        int i4 = 0;
        while (true) {
            hMac.update(bArr, 0, bArr.length);
            b = (byte) (b + 1);
            hMac.update(b);
            hMac.doFinal(bArr4, 0);
            int i5 = i3 - i4;
            if (i5 <= hashOutputSize) {
                System.arraycopy(bArr4, 0, bArr3, i4, i5);
                return this.crypto.adoptLocalSecret(bArr3);
            }
            System.arraycopy(bArr4, 0, bArr3, i4, hashOutputSize);
            i4 += hashOutputSize;
            hMac.update(bArr4, 0, hashOutputSize);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsSecret
    public synchronized TlsSecret hkdfExtract(int i2, TlsSecret tlsSecret) {
        byte[] bArr;
        checkAlive();
        byte[] bArr2 = this.data;
        this.data = null;
        HMac hMac = new HMac(this.crypto.createDigest(i2));
        hMac.init(new KeyParameter(bArr2));
        convert(this.crypto, tlsSecret).updateMac(hMac);
        bArr = new byte[hMac.getMacSize()];
        hMac.doFinal(bArr, 0);
        return this.crypto.adoptLocalSecret(bArr);
    }

    public void hmacHash(Digest digest, byte[] bArr, int i2, int i3, byte[] bArr2, byte[] bArr3) {
        HMac hMac = new HMac(digest);
        hMac.init(new KeyParameter(bArr, i2, i3));
        int macSize = hMac.getMacSize();
        byte[] bArr4 = new byte[macSize];
        byte[] bArr5 = new byte[macSize];
        int i4 = 0;
        byte[] bArr6 = bArr2;
        while (i4 < bArr3.length) {
            hMac.update(bArr6, 0, bArr6.length);
            hMac.doFinal(bArr4, 0);
            hMac.update(bArr4, 0, macSize);
            hMac.update(bArr2, 0, bArr2.length);
            hMac.doFinal(bArr5, 0);
            System.arraycopy(bArr5, 0, bArr3, i4, Math.min(macSize, bArr3.length - i4));
            i4 += macSize;
            bArr6 = bArr4;
        }
    }

    public byte[] prf(int i2, String str, byte[] bArr, int i3) {
        if (i2 == 0) {
            return prf_SSL(bArr, i3);
        }
        byte[] concatenate = Arrays.concatenate(Strings.toByteArray(str), bArr);
        return 1 == i2 ? prf_1_0(concatenate, i3) : prf_1_2(i2, concatenate, i3);
    }

    public byte[] prf_1_0(byte[] bArr, int i2) {
        int length = (this.data.length + 1) / 2;
        byte[] bArr2 = new byte[i2];
        hmacHash(this.crypto.createDigest(1), this.data, 0, length, bArr, bArr2);
        byte[] bArr3 = new byte[i2];
        Digest createDigest = this.crypto.createDigest(2);
        byte[] bArr4 = this.data;
        hmacHash(createDigest, bArr4, bArr4.length - length, length, bArr, bArr3);
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = (byte) (bArr2[i3] ^ bArr3[i3]);
        }
        return bArr2;
    }

    public byte[] prf_1_2(int i2, byte[] bArr, int i3) {
        Digest createDigest = this.crypto.createDigest(TlsCryptoUtils.getHashForPRF(i2));
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = this.data;
        hmacHash(createDigest, bArr3, 0, bArr3.length, bArr, bArr2);
        return bArr2;
    }

    public byte[] prf_SSL(byte[] bArr, int i2) {
        int i3 = 1;
        Digest createDigest = this.crypto.createDigest(1);
        Digest createDigest2 = this.crypto.createDigest(2);
        int digestSize = createDigest.getDigestSize();
        int digestSize2 = createDigest2.getDigestSize();
        byte[] bArr2 = new byte[Math.max(digestSize, digestSize2)];
        byte[] bArr3 = new byte[i2];
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            createDigest2.update(SSL3_CONST, i5, i3);
            int i6 = i3 + 1;
            i5 += i3;
            byte[] bArr4 = this.data;
            createDigest2.update(bArr4, 0, bArr4.length);
            createDigest2.update(bArr, 0, bArr.length);
            createDigest2.doFinal(bArr2, 0);
            byte[] bArr5 = this.data;
            createDigest.update(bArr5, 0, bArr5.length);
            createDigest.update(bArr2, 0, digestSize2);
            int i7 = i2 - i4;
            if (i7 < digestSize) {
                createDigest.doFinal(bArr2, 0);
                System.arraycopy(bArr2, 0, bArr3, i4, i7);
                i4 += i7;
            } else {
                createDigest.doFinal(bArr3, i4);
                i4 += digestSize;
            }
            i3 = i6;
        }
        return bArr3;
    }

    public synchronized void updateMac(Mac mac) {
        checkAlive();
        byte[] bArr = this.data;
        mac.update(bArr, 0, bArr.length);
    }
}
