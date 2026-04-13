package org.bouncycastle.pqc.crypto.lms;

import org.bouncycastle.crypto.Digest;

/* loaded from: classes.dex */
class LmsUtils {
    public static void byteArray(byte[] bArr, int i2, int i3, Digest digest) {
        digest.update(bArr, i2, i3);
    }

    public static int calculateStrength(LMSParameters lMSParameters) {
        if (lMSParameters == null) {
            throw new NullPointerException("lmsParameters cannot be null");
        }
        LMSigParameters lMSigParam = lMSParameters.getLMSigParam();
        return lMSigParam.getM() * (1 << lMSigParam.getH());
    }

    public static void u16str(short s2, Digest digest) {
        digest.update((byte) (s2 >>> 8));
        digest.update((byte) s2);
    }

    public static void u32str(int i2, Digest digest) {
        digest.update((byte) (i2 >>> 24));
        digest.update((byte) (i2 >>> 16));
        digest.update((byte) (i2 >>> 8));
        digest.update((byte) i2);
    }

    public static void byteArray(byte[] bArr, Digest digest) {
        digest.update(bArr, 0, bArr.length);
    }
}
