package org.bouncycastle.pqc.crypto.sphincs;

import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
class Permute {
    private static final int CHACHA_ROUNDS = 12;

    public static void permute(int i2, int[] iArr) {
        int i3 = 16;
        if (iArr.length != 16) {
            throw new IllegalArgumentException();
        }
        if (i2 % 2 != 0) {
            throw new IllegalArgumentException("Number of rounds must be even");
        }
        char c = 0;
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = iArr[2];
        int i7 = iArr[3];
        int i8 = iArr[4];
        int i9 = iArr[5];
        int i10 = iArr[6];
        int i11 = 7;
        int i12 = iArr[7];
        int i13 = 8;
        int i14 = iArr[8];
        int i15 = iArr[9];
        int i16 = iArr[10];
        int i17 = iArr[11];
        int i18 = iArr[12];
        int i19 = iArr[13];
        int i20 = iArr[14];
        int i21 = iArr[15];
        int i22 = i20;
        int i23 = i19;
        int i24 = i18;
        int i25 = i17;
        int i26 = i16;
        int i27 = i15;
        int i28 = i14;
        int i29 = i12;
        int i30 = i10;
        int i31 = i9;
        int i32 = i8;
        int i33 = i7;
        int i34 = i6;
        int i35 = i5;
        int i36 = i4;
        int i37 = i2;
        while (i37 > 0) {
            int i38 = i36 + i32;
            int rotl = rotl(i24 ^ i38, i3);
            int i39 = i28 + rotl;
            int rotl2 = rotl(i32 ^ i39, 12);
            int i40 = i38 + rotl2;
            int rotl3 = rotl(rotl ^ i40, i13);
            int i41 = i39 + rotl3;
            int rotl4 = rotl(rotl2 ^ i41, i11);
            int i42 = i35 + i31;
            int rotl5 = rotl(i23 ^ i42, i3);
            int i43 = i27 + rotl5;
            int rotl6 = rotl(i31 ^ i43, 12);
            int i44 = i42 + rotl6;
            int rotl7 = rotl(rotl5 ^ i44, i13);
            int i45 = i43 + rotl7;
            int rotl8 = rotl(rotl6 ^ i45, i11);
            int i46 = i34 + i30;
            int rotl9 = rotl(i22 ^ i46, i3);
            int i47 = i26 + rotl9;
            int rotl10 = rotl(i30 ^ i47, 12);
            int i48 = i46 + rotl10;
            int rotl11 = rotl(rotl9 ^ i48, i13);
            int i49 = i47 + rotl11;
            int rotl12 = rotl(rotl10 ^ i49, i11);
            int i50 = i33 + i29;
            int rotl13 = rotl(i21 ^ i50, i3);
            int i51 = i25 + rotl13;
            int rotl14 = rotl(i29 ^ i51, 12);
            int i52 = i50 + rotl14;
            int rotl15 = rotl(rotl13 ^ i52, i13);
            int i53 = i51 + rotl15;
            int rotl16 = rotl(rotl14 ^ i53, 7);
            int i54 = i40 + rotl8;
            int rotl17 = rotl(rotl15 ^ i54, 16);
            int i55 = i49 + rotl17;
            int rotl18 = rotl(rotl8 ^ i55, 12);
            i36 = i54 + rotl18;
            i21 = rotl(rotl17 ^ i36, 8);
            i26 = i55 + i21;
            i31 = rotl(rotl18 ^ i26, 7);
            int i56 = i44 + rotl12;
            int rotl19 = rotl(rotl3 ^ i56, 16);
            int i57 = i53 + rotl19;
            int rotl20 = rotl(rotl12 ^ i57, 12);
            i35 = i56 + rotl20;
            i24 = rotl(rotl19 ^ i35, 8);
            i25 = i57 + i24;
            i30 = rotl(rotl20 ^ i25, 7);
            int i58 = i48 + rotl16;
            int rotl21 = rotl(rotl7 ^ i58, 16);
            int i59 = i41 + rotl21;
            int rotl22 = rotl(rotl16 ^ i59, 12);
            i34 = i58 + rotl22;
            i23 = rotl(rotl21 ^ i34, 8);
            i28 = i59 + i23;
            i29 = rotl(rotl22 ^ i28, 7);
            int i60 = i52 + rotl4;
            i3 = 16;
            int rotl23 = rotl(rotl11 ^ i60, 16);
            int i61 = i45 + rotl23;
            int rotl24 = rotl(rotl4 ^ i61, 12);
            i33 = i60 + rotl24;
            i22 = rotl(rotl23 ^ i33, 8);
            i27 = i61 + i22;
            i32 = rotl(rotl24 ^ i27, 7);
            i37 -= 2;
            i11 = 7;
            c = 0;
            i13 = 8;
        }
        iArr[c] = i36;
        iArr[1] = i35;
        iArr[2] = i34;
        iArr[3] = i33;
        iArr[4] = i32;
        iArr[5] = i31;
        iArr[6] = i30;
        iArr[i11] = i29;
        iArr[8] = i28;
        iArr[9] = i27;
        iArr[10] = i26;
        iArr[11] = i25;
        iArr[12] = i24;
        iArr[13] = i23;
        iArr[14] = i22;
        iArr[15] = i21;
    }

    public static int rotl(int i2, int i3) {
        return (i2 >>> (-i3)) | (i2 << i3);
    }

    public void chacha_permute(byte[] bArr, byte[] bArr2) {
        int[] iArr = new int[16];
        for (int i2 = 0; i2 < 16; i2++) {
            iArr[i2] = Pack.littleEndianToInt(bArr2, i2 * 4);
        }
        permute(12, iArr);
        for (int i3 = 0; i3 < 16; i3++) {
            Pack.intToLittleEndian(iArr[i3], bArr, i3 * 4);
        }
    }
}
