package org.bouncycastle.pqc.crypto.qtesla;

import org.bouncycastle.crypto.digests.CSHAKEDigest;
import org.bouncycastle.crypto.digests.SHAKEDigest;

/* loaded from: classes.dex */
class HashUtils {
    static final int SECURE_HASH_ALGORITHM_KECCAK_128_RATE = 168;
    static final int SECURE_HASH_ALGORITHM_KECCAK_256_RATE = 136;

    public static void customizableSecureHashAlgorithmKECCAK128Simple(byte[] bArr, int i2, int i3, short s2, byte[] bArr2, int i4, int i5) {
        CSHAKEDigest cSHAKEDigest = new CSHAKEDigest(128, null, new byte[]{(byte) s2, (byte) (s2 >> 8)});
        cSHAKEDigest.update(bArr2, i4, i5);
        cSHAKEDigest.doFinal(bArr, i2, i3);
    }

    public static void customizableSecureHashAlgorithmKECCAK256Simple(byte[] bArr, int i2, int i3, short s2, byte[] bArr2, int i4, int i5) {
        CSHAKEDigest cSHAKEDigest = new CSHAKEDigest(256, null, new byte[]{(byte) s2, (byte) (s2 >> 8)});
        cSHAKEDigest.update(bArr2, i4, i5);
        cSHAKEDigest.doFinal(bArr, i2, i3);
    }

    public static void secureHashAlgorithmKECCAK128(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        SHAKEDigest sHAKEDigest = new SHAKEDigest(128);
        sHAKEDigest.update(bArr2, i4, i5);
        sHAKEDigest.doFinal(bArr, i2, i3);
    }

    public static void secureHashAlgorithmKECCAK256(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        SHAKEDigest sHAKEDigest = new SHAKEDigest(256);
        sHAKEDigest.update(bArr2, i4, i5);
        sHAKEDigest.doFinal(bArr, i2, i3);
    }
}
