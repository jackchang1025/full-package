package org.bouncycastle.util;

import org.bouncycastle.crypto.digests.SHA512tDigest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class Fingerprint {
    private static char[] encodingTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final byte[] fingerprint;

    public Fingerprint(byte[] bArr) {
        this(bArr, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
    }

    public static byte[] calculateFingerprint(byte[] bArr) {
        return calculateFingerprint(bArr, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
    }

    public static byte[] calculateFingerprintSHA512_160(byte[] bArr) {
        SHA512tDigest sHA512tDigest = new SHA512tDigest(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        sHA512tDigest.update(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[sHA512tDigest.getDigestSize()];
        sHA512tDigest.doFinal(bArr2, 0);
        return bArr2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Fingerprint) {
            return Arrays.areEqual(((Fingerprint) obj).fingerprint, this.fingerprint);
        }
        return false;
    }

    public byte[] getFingerprint() {
        return Arrays.clone(this.fingerprint);
    }

    public int hashCode() {
        return Arrays.hashCode(this.fingerprint);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 != this.fingerprint.length; i2++) {
            if (i2 > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(encodingTable[(this.fingerprint[i2] >>> 4) & 15]);
            stringBuffer.append(encodingTable[this.fingerprint[i2] & 15]);
        }
        return stringBuffer.toString();
    }

    public Fingerprint(byte[] bArr, int i2) {
        this.fingerprint = calculateFingerprint(bArr, i2);
    }

    public static byte[] calculateFingerprint(byte[] bArr, int i2) {
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("bitLength must be a multiple of 8");
        }
        SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
        sHAKEDigest.update(bArr, 0, bArr.length);
        int i3 = i2 / 8;
        byte[] bArr2 = new byte[i3];
        sHAKEDigest.doFinal(bArr2, 0, i3);
        return bArr2;
    }

    public Fingerprint(byte[] bArr, boolean z2) {
        if (z2) {
            this.fingerprint = calculateFingerprintSHA512_160(bArr);
        } else {
            this.fingerprint = calculateFingerprint(bArr);
        }
    }
}
