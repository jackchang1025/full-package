package org.bouncycastle.crypto.params;

/* loaded from: classes.dex */
public class DESParameters extends KeyParameter {
    public static final int DES_KEY_LENGTH = 8;
    private static byte[] DES_weak_keys = {1, 1, 1, 1, 1, 1, 1, 1, 31, 31, 31, 31, 14, 14, 14, 14, -32, -32, -32, -32, -15, -15, -15, -15, -2, -2, -2, -2, -2, -2, -2, -2, 1, -2, 1, -2, 1, -2, 1, -2, 31, -32, 31, -32, 14, -15, 14, -15, 1, -32, 1, -32, 1, -15, 1, -15, 31, -2, 31, -2, 14, -2, 14, -2, 1, 31, 1, 31, 1, 14, 1, 14, -32, -2, -32, -2, -15, -2, -15, -2, -2, 1, -2, 1, -2, 1, -2, 1, -32, 31, -32, 31, -15, 14, -15, 14, -32, 1, -32, 1, -15, 1, -15, 1, -2, 31, -2, 31, -2, 14, -2, 14, 31, 1, 31, 1, 14, 1, 14, 1, -2, -32, -2, -32, -2, -15, -2, -15};
    private static final int N_DES_WEAK_KEYS = 16;

    public DESParameters(byte[] bArr) {
        super(bArr);
        if (isWeakKey(bArr, 0)) {
            throw new IllegalArgumentException("attempt to create weak DES key");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001c, code lost:
    
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isWeakKey(byte[] bArr, int i2) {
        if (bArr.length - i2 < 8) {
            throw new IllegalArgumentException("key material too short.");
        }
        int i3 = 0;
        while (i3 < 16) {
            for (int i4 = 0; i4 < 8; i4++) {
                if (bArr[i4 + i2] != DES_weak_keys[(i3 * 8) + i4]) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public static void setOddParity(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b = bArr[i2];
            bArr[i2] = (byte) (((((b >> 7) ^ ((((((b >> 1) ^ (b >> 2)) ^ (b >> 3)) ^ (b >> 4)) ^ (b >> 5)) ^ (b >> 6))) ^ 1) & 1) | (b & 254));
        }
    }
}
