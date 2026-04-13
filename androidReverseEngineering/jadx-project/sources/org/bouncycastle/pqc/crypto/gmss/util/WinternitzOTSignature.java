package org.bouncycastle.pqc.crypto.gmss.util;

import org.bouncycastle.crypto.Digest;

/* loaded from: classes.dex */
public class WinternitzOTSignature {
    private int checksumsize;
    private GMSSRandom gmssRandom;
    private int keysize;
    private int mdsize;
    private Digest messDigestOTS;
    private int messagesize;
    private byte[][] privateKeyOTS;

    /* renamed from: w */
    private int f1547w;

    public WinternitzOTSignature(byte[] bArr, Digest digest, int i2) {
        this.f1547w = i2;
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.mdsize = this.messDigestOTS.getDigestSize();
        int i3 = (((r3 << 3) + i2) - 1) / i2;
        this.messagesize = i3;
        this.checksumsize = getLog((i3 << i2) + 1);
        int i4 = (((r3 + i2) - 1) / i2) + this.messagesize;
        this.keysize = i4;
        this.privateKeyOTS = new byte[i4][];
        int i5 = this.mdsize;
        byte[] bArr2 = new byte[i5];
        System.arraycopy(bArr, 0, bArr2, 0, i5);
        for (int i6 = 0; i6 < this.keysize; i6++) {
            this.privateKeyOTS[i6] = this.gmssRandom.nextSeed(bArr2);
        }
    }

    private void hashPrivateKeyBlock(int i2, int i3, byte[] bArr, int i4) {
        if (i3 < 1) {
            System.arraycopy(this.privateKeyOTS[i2], 0, bArr, i4, this.mdsize);
            return;
        }
        this.messDigestOTS.update(this.privateKeyOTS[i2], 0, this.mdsize);
        while (true) {
            this.messDigestOTS.doFinal(bArr, i4);
            i3--;
            if (i3 <= 0) {
                return;
            } else {
                this.messDigestOTS.update(bArr, i4, this.mdsize);
            }
        }
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

    public byte[][] getPrivateKey() {
        return this.privateKeyOTS;
    }

    public byte[] getPublicKey() {
        int i2 = this.keysize * this.mdsize;
        byte[] bArr = new byte[i2];
        int i3 = (1 << this.f1547w) - 1;
        int i4 = 0;
        for (int i5 = 0; i5 < this.keysize; i5++) {
            hashPrivateKeyBlock(i5, i3, bArr, i4);
            i4 += this.mdsize;
        }
        this.messDigestOTS.update(bArr, 0, i2);
        byte[] bArr2 = new byte[this.mdsize];
        this.messDigestOTS.doFinal(bArr2, 0);
        return bArr2;
    }

    public byte[] getSignature(byte[] bArr) {
        int i2;
        int i3 = this.keysize;
        int i4 = this.mdsize;
        byte[] bArr2 = new byte[i3 * i4];
        byte[] bArr3 = new byte[i4];
        int i5 = 0;
        this.messDigestOTS.update(bArr, 0, bArr.length);
        this.messDigestOTS.doFinal(bArr3, 0);
        int i6 = this.f1547w;
        int i7 = 8;
        if (8 % i6 == 0) {
            int i8 = 8 / i6;
            int i9 = (1 << i6) - 1;
            int i10 = 0;
            int i11 = 0;
            for (int i12 = 0; i12 < i4; i12++) {
                for (int i13 = 0; i13 < i8; i13++) {
                    int i14 = bArr3[i12] & i9;
                    i10 += i14;
                    hashPrivateKeyBlock(i11, i14, bArr2, this.mdsize * i11);
                    bArr3[i12] = (byte) (bArr3[i12] >>> this.f1547w);
                    i11++;
                }
            }
            int i15 = (this.messagesize << this.f1547w) - i10;
            while (i5 < this.checksumsize) {
                hashPrivateKeyBlock(i11, i15 & i9, bArr2, this.mdsize * i11);
                int i16 = this.f1547w;
                i15 >>>= i16;
                i11++;
                i5 += i16;
            }
        } else if (i6 < 8) {
            int i17 = this.mdsize / i6;
            int i18 = (1 << i6) - 1;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            while (i19 < i17) {
                long j2 = 0;
                for (int i23 = 0; i23 < this.f1547w; i23++) {
                    j2 ^= (bArr3[i20] & 255) << (i23 << 3);
                    i20++;
                }
                int i24 = 0;
                long j3 = j2;
                while (i24 < i7) {
                    int i25 = ((int) j3) & i18;
                    i22 += i25;
                    hashPrivateKeyBlock(i21, i25, bArr2, this.mdsize * i21);
                    j3 >>>= this.f1547w;
                    i21++;
                    i24++;
                    i7 = 8;
                }
                i19++;
                i7 = 8;
            }
            int i26 = this.mdsize % this.f1547w;
            long j4 = 0;
            for (int i27 = 0; i27 < i26; i27++) {
                j4 ^= (bArr3[i20] & 255) << (i27 << 3);
                i20++;
            }
            int i28 = i26 << 3;
            int i29 = 0;
            while (i29 < i28) {
                int i30 = ((int) j4) & i18;
                i22 += i30;
                hashPrivateKeyBlock(i21, i30, bArr2, this.mdsize * i21);
                int i31 = this.f1547w;
                j4 >>>= i31;
                i21++;
                i29 += i31;
            }
            int i32 = (this.messagesize << this.f1547w) - i22;
            while (i5 < this.checksumsize) {
                hashPrivateKeyBlock(i21, i32 & i18, bArr2, this.mdsize * i21);
                int i33 = this.f1547w;
                i32 >>>= i33;
                i21++;
                i5 += i33;
            }
        } else if (i6 < 57) {
            int i34 = this.mdsize;
            int i35 = (i34 << 3) - i6;
            int i36 = (1 << i6) - 1;
            byte[] bArr4 = new byte[i34];
            int i37 = 0;
            int i38 = 0;
            int i39 = 0;
            while (i37 <= i35) {
                int i40 = i37 >>> 3;
                int i41 = i37 % 8;
                i37 += this.f1547w;
                int i42 = i5;
                long j5 = 0;
                while (i40 < ((i37 + 7) >>> 3)) {
                    j5 ^= (bArr3[i40] & 255) << (i42 << 3);
                    i42++;
                    i40++;
                    bArr3 = bArr3;
                    i35 = i35;
                }
                byte[] bArr5 = bArr3;
                int i43 = i35;
                long j6 = (j5 >>> i41) & i36;
                i39 = (int) (i39 + j6);
                System.arraycopy(this.privateKeyOTS[i38], 0, bArr4, 0, this.mdsize);
                while (j6 > 0) {
                    this.messDigestOTS.update(bArr4, 0, i34);
                    this.messDigestOTS.doFinal(bArr4, 0);
                    j6--;
                }
                int i44 = this.mdsize;
                System.arraycopy(bArr4, 0, bArr2, i38 * i44, i44);
                i38++;
                bArr3 = bArr5;
                i35 = i43;
                i5 = 0;
            }
            byte[] bArr6 = bArr3;
            int i45 = i37 >>> 3;
            if (i45 < this.mdsize) {
                int i46 = i37 % 8;
                int i47 = 0;
                long j7 = 0;
                while (true) {
                    i2 = this.mdsize;
                    if (i45 >= i2) {
                        break;
                    }
                    j7 ^= (bArr6[i45] & 255) << (i47 << 3);
                    i47++;
                    i45++;
                }
                long j8 = (j7 >>> i46) & i36;
                i39 = (int) (i39 + j8);
                System.arraycopy(this.privateKeyOTS[i38], 0, bArr4, 0, i2);
                while (j8 > 0) {
                    this.messDigestOTS.update(bArr4, 0, i34);
                    this.messDigestOTS.doFinal(bArr4, 0);
                    j8--;
                }
                int i48 = this.mdsize;
                System.arraycopy(bArr4, 0, bArr2, i38 * i48, i48);
                i38++;
            }
            int i49 = (this.messagesize << this.f1547w) - i39;
            int i50 = 0;
            while (i50 < this.checksumsize) {
                System.arraycopy(this.privateKeyOTS[i38], 0, bArr4, 0, this.mdsize);
                for (long j9 = i49 & i36; j9 > 0; j9--) {
                    this.messDigestOTS.update(bArr4, 0, i34);
                    this.messDigestOTS.doFinal(bArr4, 0);
                }
                int i51 = this.mdsize;
                System.arraycopy(bArr4, 0, bArr2, i38 * i51, i51);
                int i52 = this.f1547w;
                i49 >>>= i52;
                i38++;
                i50 += i52;
            }
        }
        return bArr2;
    }
}
