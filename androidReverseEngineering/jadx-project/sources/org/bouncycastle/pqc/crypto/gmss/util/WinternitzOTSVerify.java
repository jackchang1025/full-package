package org.bouncycastle.pqc.crypto.gmss.util;

import org.bouncycastle.crypto.Digest;

/* loaded from: classes.dex */
public class WinternitzOTSVerify {
    private int mdsize;
    private Digest messDigestOTS;

    /* renamed from: w */
    private int f1546w;

    public WinternitzOTSVerify(Digest digest, int i2) {
        this.f1546w = i2;
        this.messDigestOTS = digest;
        this.mdsize = digest.getDigestSize();
    }

    private void hashSignatureBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (i3 < 1) {
            System.arraycopy(bArr, i2, bArr2, i4, this.mdsize);
            return;
        }
        this.messDigestOTS.update(bArr, i2, this.mdsize);
        while (true) {
            this.messDigestOTS.doFinal(bArr2, i4);
            i3--;
            if (i3 <= 0) {
                return;
            } else {
                this.messDigestOTS.update(bArr2, i4, this.mdsize);
            }
        }
    }

    public byte[] Verify(byte[] bArr, byte[] bArr2) {
        int i2;
        int i3;
        int i4;
        WinternitzOTSVerify winternitzOTSVerify = this;
        int i5 = winternitzOTSVerify.mdsize;
        byte[] bArr3 = new byte[i5];
        int i6 = 0;
        winternitzOTSVerify.messDigestOTS.update(bArr, 0, bArr.length);
        winternitzOTSVerify.messDigestOTS.doFinal(bArr3, 0);
        int i7 = winternitzOTSVerify.mdsize << 3;
        int i8 = winternitzOTSVerify.f1546w;
        int i9 = ((i8 - 1) + i7) / i8;
        int log = winternitzOTSVerify.getLog((i9 << i8) + 1);
        int i10 = winternitzOTSVerify.f1546w;
        int i11 = winternitzOTSVerify.mdsize;
        int i12 = i11 * ((((log + i10) - 1) / i10) + i9);
        if (i12 != bArr2.length) {
            return null;
        }
        byte[] bArr4 = new byte[i12];
        int i13 = 8;
        if (8 % i10 == 0) {
            int i14 = 8 / i10;
            int i15 = (1 << i10) - 1;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            while (i18 < i5) {
                int i19 = i17;
                int i20 = 0;
                while (i20 < i14) {
                    int i21 = bArr3[i18] & i15;
                    int i22 = i16 + i21;
                    int i23 = winternitzOTSVerify.mdsize;
                    int i24 = i18;
                    hashSignatureBlock(bArr2, i19 * i23, i15 - i21, bArr4, i19 * i23);
                    bArr3[i24] = (byte) (bArr3[i24] >>> winternitzOTSVerify.f1546w);
                    i19++;
                    i20++;
                    i16 = i22;
                    i18 = i24;
                    i14 = i14;
                }
                i18++;
                i17 = i19;
            }
            int i25 = i17;
            int i26 = (i9 << winternitzOTSVerify.f1546w) - i16;
            int i27 = 0;
            while (i27 < log) {
                int i28 = winternitzOTSVerify.mdsize;
                hashSignatureBlock(bArr2, i25 * i28, i15 - (i26 & i15), bArr4, i25 * i28);
                int i29 = winternitzOTSVerify.f1546w;
                i26 >>>= i29;
                i25++;
                i27 += i29;
            }
            i4 = 0;
            i2 = i12;
        } else {
            long j2 = 0;
            if (i10 < 8) {
                int i30 = i11 / i10;
                int i31 = (1 << i10) - 1;
                int i32 = 0;
                int i33 = 0;
                int i34 = 0;
                int i35 = 0;
                while (i35 < i30) {
                    int i36 = i32;
                    int i37 = i6;
                    long j3 = 0;
                    while (i37 < winternitzOTSVerify.f1546w) {
                        j3 ^= (bArr3[i36] & 255) << (i37 << 3);
                        i36++;
                        i37++;
                        log = log;
                    }
                    int i38 = log;
                    int i39 = i34;
                    int i40 = 0;
                    while (i40 < i13) {
                        int i41 = (int) (j3 & i31);
                        int i42 = i33 + i41;
                        int i43 = this.mdsize;
                        winternitzOTSVerify = this;
                        hashSignatureBlock(bArr2, i39 * i43, i31 - i41, bArr4, i39 * i43);
                        j3 >>>= winternitzOTSVerify.f1546w;
                        i39++;
                        i40++;
                        i31 = i31;
                        i13 = 8;
                        i35 = i35;
                        i33 = i42;
                    }
                    i35++;
                    i34 = i39;
                    i32 = i36;
                    log = i38;
                    i6 = 0;
                }
                int i44 = log;
                int i45 = i31;
                int i46 = winternitzOTSVerify.mdsize % winternitzOTSVerify.f1546w;
                int i47 = 0;
                while (i47 < i46) {
                    j2 ^= (bArr3[i32] & 255) << (i47 << 3);
                    i32++;
                    i47++;
                    i33 = i33;
                    i34 = i34;
                }
                int i48 = i34;
                int i49 = i46 << 3;
                int i50 = 0;
                while (i50 < i49) {
                    int i51 = (int) (j2 & i45);
                    int i52 = i33 + i51;
                    int i53 = winternitzOTSVerify.mdsize;
                    hashSignatureBlock(bArr2, i48 * i53, i45 - i51, bArr4, i48 * i53);
                    int i54 = winternitzOTSVerify.f1546w;
                    j2 >>>= i54;
                    i48++;
                    i50 += i54;
                    i33 = i52;
                }
                int i55 = (i9 << winternitzOTSVerify.f1546w) - i33;
                int i56 = 0;
                while (i56 < i44) {
                    int i57 = winternitzOTSVerify.mdsize;
                    hashSignatureBlock(bArr2, i48 * i57, i45 - (i55 & i45), bArr4, i48 * i57);
                    int i58 = winternitzOTSVerify.f1546w;
                    i55 >>>= i58;
                    i48++;
                    i56 += i58;
                }
            } else if (i10 < 57) {
                int i59 = (i11 << 3) - i10;
                int i60 = (1 << i10) - 1;
                byte[] bArr5 = new byte[i11];
                int i61 = 0;
                int i62 = 0;
                int i63 = 0;
                while (i61 <= i59) {
                    int i64 = i61 >>> 3;
                    int i65 = i61 % 8;
                    int i66 = i59;
                    int i67 = i61 + winternitzOTSVerify.f1546w;
                    int i68 = (i67 + 7) >>> 3;
                    long j4 = 0;
                    int i69 = 0;
                    while (i64 < i68) {
                        j4 ^= (bArr3[i64] & 255) << (i69 << 3);
                        i69++;
                        i64++;
                        i68 = i68;
                        i67 = i67;
                    }
                    int i70 = i67;
                    long j5 = j4 >>> i65;
                    int i71 = i12;
                    long j6 = i60;
                    long j7 = j5 & j6;
                    int i72 = i9;
                    i62 = (int) (i62 + j7);
                    int i73 = winternitzOTSVerify.mdsize;
                    System.arraycopy(bArr2, i63 * i73, bArr5, 0, i73);
                    for (long j8 = j7; j8 < j6; j8++) {
                        winternitzOTSVerify.messDigestOTS.update(bArr5, 0, i11);
                        winternitzOTSVerify.messDigestOTS.doFinal(bArr5, 0);
                    }
                    int i74 = winternitzOTSVerify.mdsize;
                    System.arraycopy(bArr5, 0, bArr4, i63 * i74, i74);
                    i63++;
                    i59 = i66;
                    i9 = i72;
                    i12 = i71;
                    i61 = i70;
                }
                int i75 = i9;
                i2 = i12;
                int i76 = i61 >>> 3;
                if (i76 < winternitzOTSVerify.mdsize) {
                    int i77 = i61 % 8;
                    int i78 = 0;
                    while (true) {
                        i3 = winternitzOTSVerify.mdsize;
                        if (i76 >= i3) {
                            break;
                        }
                        j2 ^= (bArr3[i76] & 255) << (i78 << 3);
                        i78++;
                        i76++;
                    }
                    long j9 = i60;
                    long j10 = (j2 >>> i77) & j9;
                    i62 = (int) (i62 + j10);
                    System.arraycopy(bArr2, i63 * i3, bArr5, 0, i3);
                    while (j10 < j9) {
                        winternitzOTSVerify.messDigestOTS.update(bArr5, 0, i11);
                        winternitzOTSVerify.messDigestOTS.doFinal(bArr5, 0);
                        j10++;
                    }
                    int i79 = winternitzOTSVerify.mdsize;
                    System.arraycopy(bArr5, 0, bArr4, i63 * i79, i79);
                    i63++;
                }
                int i80 = (i75 << winternitzOTSVerify.f1546w) - i62;
                int i81 = 0;
                while (i81 < log) {
                    int i82 = winternitzOTSVerify.mdsize;
                    System.arraycopy(bArr2, i63 * i82, bArr5, 0, i82);
                    for (long j11 = i80 & i60; j11 < i60; j11++) {
                        winternitzOTSVerify.messDigestOTS.update(bArr5, 0, i11);
                        winternitzOTSVerify.messDigestOTS.doFinal(bArr5, 0);
                    }
                    int i83 = winternitzOTSVerify.mdsize;
                    System.arraycopy(bArr5, 0, bArr4, i63 * i83, i83);
                    int i84 = winternitzOTSVerify.f1546w;
                    i80 >>>= i84;
                    i63++;
                    i81 += i84;
                }
                i4 = 0;
            }
            i2 = i12;
            i4 = 0;
        }
        winternitzOTSVerify.messDigestOTS.update(bArr4, i4, i2);
        byte[] bArr6 = new byte[winternitzOTSVerify.mdsize];
        winternitzOTSVerify.messDigestOTS.doFinal(bArr6, i4);
        return bArr6;
    }

    public int getLog(int i2) {
        int i3 = 1;
        int i4 = 2;
        while (i4 < i2) {
            i4 <<= 1;
            i3++;
        }
        return i3;
    }

    public int getSignatureLength() {
        int digestSize = this.messDigestOTS.getDigestSize();
        int i2 = this.f1546w;
        int i3 = ((i2 - 1) + (digestSize << 3)) / i2;
        int log = getLog((i3 << i2) + 1);
        return ((((log + r2) - 1) / this.f1546w) + i3) * digestSize;
    }
}
