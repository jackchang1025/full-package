package org.bouncycastle.util.encoders;

import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class UTF8 {
    private static final byte C_CR1 = 1;
    private static final byte C_CR2 = 2;
    private static final byte C_CR3 = 3;
    private static final byte C_ILL = 0;
    private static final byte C_L2A = 4;
    private static final byte C_L3A = 5;
    private static final byte C_L3B = 6;
    private static final byte C_L3C = 7;
    private static final byte C_L4A = 8;
    private static final byte C_L4B = 9;
    private static final byte C_L4C = 10;
    private static final byte S_CS1 = 0;
    private static final byte S_CS2 = 16;
    private static final byte S_CS3 = 32;
    private static final byte S_END = -1;
    private static final byte S_ERR = -2;
    private static final byte S_P3A = 48;
    private static final byte S_P3B = 64;
    private static final byte S_P4A = 80;
    private static final byte S_P4B = 96;
    private static final short[] firstUnitTable = new short[128];
    private static final byte[] transitionTable;

    static {
        byte[] bArr = new byte[112];
        transitionTable = bArr;
        byte[] bArr2 = new byte[128];
        fill(bArr2, 0, 15, (byte) 1);
        fill(bArr2, 16, 31, (byte) 2);
        fill(bArr2, 32, 63, (byte) 3);
        fill(bArr2, 64, 65, (byte) 0);
        fill(bArr2, 66, 95, (byte) 4);
        fill(bArr2, 96, 96, (byte) 5);
        fill(bArr2, 97, CipherSuite.TLS_DH_anon_WITH_AES_128_CBC_SHA256, (byte) 6);
        fill(bArr2, CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256, C_L3C);
        fill(bArr2, 110, 111, (byte) 6);
        fill(bArr2, 112, 112, C_L4A);
        fill(bArr2, 113, 115, C_L4B);
        fill(bArr2, 116, 116, (byte) 10);
        fill(bArr2, 117, CertificateBody.profileType, (byte) 0);
        fill(bArr, 0, bArr.length - 1, S_ERR);
        fill(bArr, 8, 11, S_END);
        fill(bArr, 24, 27, (byte) 0);
        fill(bArr, 40, 43, (byte) 16);
        fill(bArr, 58, 59, (byte) 0);
        fill(bArr, 72, 73, (byte) 0);
        fill(bArr, 89, 91, (byte) 16);
        fill(bArr, CipherSuite.TLS_DH_DSS_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_AES_256_CBC_SHA256, (byte) 16);
        byte[] bArr3 = {0, 0, 0, 0, 31, 15, 15, 15, C_L3C, C_L3C, C_L3C};
        byte[] bArr4 = {S_ERR, S_ERR, S_ERR, S_ERR, 0, 48, 16, 64, S_P4A, S_CS3, S_P4B};
        for (int i2 = 0; i2 < 128; i2++) {
            byte b = bArr2[i2];
            firstUnitTable[i2] = (short) (bArr4[b] | ((bArr3[b] & i2) << 8));
        }
    }

    private static void fill(byte[] bArr, int i2, int i3, byte b) {
        while (i2 <= i3) {
            bArr[i2] = b;
            i2++;
        }
    }

    public static int transcodeToUTF16(byte[] bArr, char[] cArr) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b < 0) {
                short s2 = firstUnitTable[b & Byte.MAX_VALUE];
                int i5 = s2 >>> 8;
                byte b2 = (byte) s2;
                while (b2 >= 0) {
                    if (i4 >= bArr.length) {
                        return -1;
                    }
                    int i6 = i4 + 1;
                    byte b3 = bArr[i4];
                    i5 = (i5 << 6) | (b3 & 63);
                    b2 = transitionTable[b2 + ((b3 & S_END) >>> 4)];
                    i4 = i6;
                }
                if (b2 == -2) {
                    return -1;
                }
                if (i5 <= 65535) {
                    if (i3 >= cArr.length) {
                        return -1;
                    }
                    cArr[i3] = (char) i5;
                    i3++;
                } else {
                    if (i3 >= cArr.length - 1) {
                        return -1;
                    }
                    int i7 = i3 + 1;
                    cArr[i3] = (char) ((i5 >>> 10) + 55232);
                    i3 = i7 + 1;
                    cArr[i7] = (char) ((i5 & 1023) | 56320);
                }
                i2 = i4;
            } else {
                if (i3 >= cArr.length) {
                    return -1;
                }
                cArr[i3] = (char) b;
                i2 = i4;
                i3++;
            }
        }
        return i3;
    }
}
