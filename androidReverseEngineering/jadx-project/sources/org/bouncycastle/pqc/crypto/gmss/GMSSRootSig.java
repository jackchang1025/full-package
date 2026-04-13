package org.bouncycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.encoders.Hex;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GMSSRootSig {
    private long big8;
    private int checksum;
    private int counter;
    private GMSSRandom gmssRandom;
    private byte[] hash;
    private int height;
    private int ii;

    /* renamed from: k */
    private int f1543k;
    private int keysize;
    private int mdsize;
    private Digest messDigestOTS;
    private int messagesize;
    private byte[] privateKeyOTS;

    /* renamed from: r */
    private int f1544r;
    private byte[] seed;
    private byte[] sign;
    private int steps;
    private int test;
    private long test8;

    /* renamed from: w */
    private int f1545w;

    public GMSSRootSig(Digest digest, int i2, int i3) {
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.mdsize = this.messDigestOTS.getDigestSize();
        this.f1545w = i2;
        this.height = i3;
        this.f1543k = (1 << i2) - 1;
        this.messagesize = (int) Math.ceil((r3 << 3) / i2);
    }

    private void oneStep() {
        long j2;
        int i2 = this.f1545w;
        if (8 % i2 == 0) {
            int i3 = this.test;
            if (i3 == 0) {
                this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
                int i4 = this.ii;
                if (i4 < this.mdsize) {
                    byte[] bArr = this.hash;
                    byte b = bArr[i4];
                    this.test = this.f1543k & b;
                    bArr[i4] = (byte) (b >>> this.f1545w);
                } else {
                    int i5 = this.checksum;
                    this.test = this.f1543k & i5;
                    this.checksum = i5 >>> this.f1545w;
                }
            } else if (i3 > 0) {
                Digest digest = this.messDigestOTS;
                byte[] bArr2 = this.privateKeyOTS;
                digest.update(bArr2, 0, bArr2.length);
                byte[] bArr3 = new byte[this.messDigestOTS.getDigestSize()];
                this.privateKeyOTS = bArr3;
                this.messDigestOTS.doFinal(bArr3, 0);
                this.test--;
            }
            if (this.test == 0) {
                byte[] bArr4 = this.privateKeyOTS;
                byte[] bArr5 = this.sign;
                int i6 = this.counter;
                int i7 = this.mdsize;
                System.arraycopy(bArr4, 0, bArr5, i6 * i7, i7);
                int i8 = this.counter + 1;
                this.counter = i8;
                if (i8 % (8 / this.f1545w) == 0) {
                    this.ii++;
                    return;
                }
                return;
            }
            return;
        }
        if (i2 < 8) {
            int i9 = this.test;
            if (i9 == 0) {
                int i10 = this.counter;
                if (i10 % 8 == 0) {
                    int i11 = this.ii;
                    int i12 = this.mdsize;
                    if (i11 < i12) {
                        this.big8 = 0L;
                        if (i10 < ((i12 / i2) << 3)) {
                            for (int i13 = 0; i13 < this.f1545w; i13++) {
                                long j3 = this.big8;
                                byte[] bArr6 = this.hash;
                                int i14 = this.ii;
                                this.big8 = j3 ^ ((bArr6[i14] & 255) << (i13 << 3));
                                this.ii = i14 + 1;
                            }
                        } else {
                            for (int i15 = 0; i15 < this.mdsize % this.f1545w; i15++) {
                                long j4 = this.big8;
                                byte[] bArr7 = this.hash;
                                int i16 = this.ii;
                                this.big8 = j4 ^ ((bArr7[i16] & 255) << (i15 << 3));
                                this.ii = i16 + 1;
                            }
                        }
                    }
                }
                if (this.counter == this.messagesize) {
                    this.big8 = this.checksum;
                }
                this.test = (int) (this.big8 & this.f1543k);
                this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
            } else if (i9 > 0) {
                Digest digest2 = this.messDigestOTS;
                byte[] bArr8 = this.privateKeyOTS;
                digest2.update(bArr8, 0, bArr8.length);
                byte[] bArr9 = new byte[this.messDigestOTS.getDigestSize()];
                this.privateKeyOTS = bArr9;
                this.messDigestOTS.doFinal(bArr9, 0);
                this.test--;
            }
            if (this.test != 0) {
                return;
            }
            byte[] bArr10 = this.privateKeyOTS;
            byte[] bArr11 = this.sign;
            int i17 = this.counter;
            int i18 = this.mdsize;
            System.arraycopy(bArr10, 0, bArr11, i17 * i18, i18);
            this.big8 >>>= this.f1545w;
        } else {
            if (i2 >= 57) {
                return;
            }
            long j5 = this.test8;
            if (j5 == 0) {
                this.big8 = 0L;
                this.ii = 0;
                int i19 = this.f1544r;
                int i20 = i19 % 8;
                int i21 = i19 >>> 3;
                int i22 = this.mdsize;
                if (i21 < i22) {
                    if (i19 <= (i22 << 3) - i2) {
                        int i23 = i19 + i2;
                        this.f1544r = i23;
                        i22 = (i23 + 7) >>> 3;
                    } else {
                        this.f1544r = i19 + i2;
                    }
                    while (true) {
                        j2 = this.big8;
                        if (i21 >= i22) {
                            break;
                        }
                        int i24 = this.hash[i21] & 255;
                        int i25 = this.ii;
                        this.big8 = j2 ^ (i24 << (i25 << 3));
                        this.ii = i25 + 1;
                        i21++;
                    }
                    long j6 = j2 >>> i20;
                    this.big8 = j6;
                    this.test8 = j6 & this.f1543k;
                } else {
                    int i26 = this.checksum;
                    this.test8 = this.f1543k & i26;
                    this.checksum = i26 >>> i2;
                }
                this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
            } else if (j5 > 0) {
                Digest digest3 = this.messDigestOTS;
                byte[] bArr12 = this.privateKeyOTS;
                digest3.update(bArr12, 0, bArr12.length);
                byte[] bArr13 = new byte[this.messDigestOTS.getDigestSize()];
                this.privateKeyOTS = bArr13;
                this.messDigestOTS.doFinal(bArr13, 0);
                this.test8--;
            }
            if (this.test8 != 0) {
                return;
            }
            byte[] bArr14 = this.privateKeyOTS;
            byte[] bArr15 = this.sign;
            int i27 = this.counter;
            int i28 = this.mdsize;
            System.arraycopy(bArr14, 0, bArr15, i27 * i28, i28);
        }
        this.counter++;
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

    public byte[] getSig() {
        return this.sign;
    }

    public byte[][] getStatByte() {
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 5, this.mdsize);
        bArr[0] = this.privateKeyOTS;
        bArr[1] = this.seed;
        bArr[2] = this.hash;
        bArr[3] = this.sign;
        bArr[4] = getStatLong();
        return bArr;
    }

    public int[] getStatInt() {
        return new int[]{this.counter, this.test, this.ii, this.f1544r, this.steps, this.keysize, this.height, this.f1545w, this.checksum};
    }

    public byte[] getStatLong() {
        long j2 = this.test8;
        long j3 = this.big8;
        return new byte[]{(byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), (byte) ((j2 >> 32) & 255), (byte) ((j2 >> 40) & 255), (byte) ((j2 >> 48) & 255), (byte) ((j2 >> 56) & 255), (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), (byte) ((j3 >> 32) & 255), (byte) ((j3 >> 40) & 255), (byte) ((j3 >> 48) & 255), (byte) ((j3 >> 56) & 255)};
    }

    public void initSign(byte[] bArr, byte[] bArr2) {
        int i2;
        int i3;
        this.hash = new byte[this.mdsize];
        this.messDigestOTS.update(bArr2, 0, bArr2.length);
        byte[] bArr3 = new byte[this.messDigestOTS.getDigestSize()];
        this.hash = bArr3;
        this.messDigestOTS.doFinal(bArr3, 0);
        int i4 = this.mdsize;
        byte[] bArr4 = new byte[i4];
        System.arraycopy(this.hash, 0, bArr4, 0, i4);
        int log = getLog((this.messagesize << this.f1545w) + 1);
        int i5 = this.f1545w;
        int i6 = 8;
        if (8 % i5 == 0) {
            int i7 = 8 / i5;
            i2 = 0;
            for (int i8 = 0; i8 < this.mdsize; i8++) {
                for (int i9 = 0; i9 < i7; i9++) {
                    byte b = bArr4[i8];
                    i2 += this.f1543k & b;
                    bArr4[i8] = (byte) (b >>> this.f1545w);
                }
            }
            int i10 = (this.messagesize << this.f1545w) - i2;
            this.checksum = i10;
            int i11 = 0;
            while (i11 < log) {
                i2 += this.f1543k & i10;
                int i12 = this.f1545w;
                i10 >>>= i12;
                i11 += i12;
            }
        } else if (i5 < 8) {
            int i13 = this.mdsize / i5;
            int i14 = 0;
            int i15 = 0;
            int i16 = 0;
            while (i14 < i13) {
                long j2 = 0;
                for (int i17 = 0; i17 < this.f1545w; i17++) {
                    j2 ^= (bArr4[i15] & 255) << (i17 << 3);
                    i15++;
                }
                int i18 = 0;
                while (i18 < i6) {
                    i16 += (int) (this.f1543k & j2);
                    j2 >>>= this.f1545w;
                    i18++;
                    i13 = i13;
                    i6 = 8;
                }
                i14++;
                i6 = 8;
            }
            int i19 = this.mdsize % this.f1545w;
            long j3 = 0;
            for (int i20 = 0; i20 < i19; i20++) {
                j3 ^= (bArr4[i15] & 255) << (i20 << 3);
                i15++;
            }
            int i21 = i19 << 3;
            int i22 = 0;
            while (i22 < i21) {
                i16 += (int) (this.f1543k & j3);
                int i23 = this.f1545w;
                j3 >>>= i23;
                i22 += i23;
            }
            int i24 = (this.messagesize << this.f1545w) - i16;
            this.checksum = i24;
            int i25 = 0;
            i2 = i16;
            while (i25 < log) {
                i2 += this.f1543k & i24;
                int i26 = this.f1545w;
                i24 >>>= i26;
                i25 += i26;
            }
        } else if (i5 < 57) {
            int i27 = 0;
            int i28 = 0;
            while (true) {
                i3 = this.mdsize;
                int i29 = this.f1545w;
                if (i27 > (i3 << 3) - i29) {
                    break;
                }
                int i30 = i27 % 8;
                i27 += i29;
                int i31 = 0;
                long j4 = 0;
                for (int i32 = i27 >>> 3; i32 < ((i27 + 7) >>> 3); i32++) {
                    j4 ^= (bArr4[i32] & 255) << (i31 << 3);
                    i31++;
                }
                i28 = (int) (i28 + ((j4 >>> i30) & this.f1543k));
            }
            int i33 = i27 >>> 3;
            if (i33 < i3) {
                int i34 = i27 % 8;
                int i35 = 0;
                long j5 = 0;
                while (i33 < this.mdsize) {
                    j5 ^= (bArr4[i33] & 255) << (i35 << 3);
                    i35++;
                    i33++;
                }
                i28 = (int) (i28 + ((j5 >>> i34) & this.f1543k));
            }
            int i36 = (this.messagesize << this.f1545w) - i28;
            this.checksum = i36;
            int i37 = 0;
            i2 = i28;
            while (i37 < log) {
                i2 += this.f1543k & i36;
                int i38 = this.f1545w;
                i36 >>>= i38;
                i37 += i38;
            }
        } else {
            i2 = 0;
        }
        this.keysize = this.messagesize + ((int) Math.ceil(log / this.f1545w));
        this.steps = (int) Math.ceil((r2 + i2) / (1 << this.height));
        int i39 = this.keysize;
        int i40 = this.mdsize;
        this.sign = new byte[i39 * i40];
        this.counter = 0;
        this.test = 0;
        this.ii = 0;
        this.test8 = 0L;
        this.f1544r = 0;
        this.privateKeyOTS = new byte[i40];
        byte[] bArr5 = new byte[i40];
        this.seed = bArr5;
        System.arraycopy(bArr, 0, bArr5, 0, i40);
    }

    public String toString() {
        String str = BuildConfig.FLAVOR + this.big8 + "  ";
        int[] statInt = getStatInt();
        byte[][] statByte = getStatByte();
        for (int i2 = 0; i2 < 9; i2++) {
            str = AbstractC0000a.m17m(AbstractC0000a.m20p(str), statInt[i2], " ");
        }
        for (int i3 = 0; i3 < 5; i3++) {
            str = AbstractC0000a.m18n(AbstractC0000a.m20p(str), new String(Hex.encode(statByte[i3])), " ");
        }
        return str;
    }

    public boolean updateSign() {
        for (int i2 = 0; i2 < this.steps; i2++) {
            if (this.counter < this.keysize) {
                oneStep();
            }
            if (this.counter == this.keysize) {
                return true;
            }
        }
        return false;
    }

    public GMSSRootSig(Digest digest, byte[][] bArr, int[] iArr) {
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.counter = iArr[0];
        this.test = iArr[1];
        this.ii = iArr[2];
        this.f1544r = iArr[3];
        this.steps = iArr[4];
        this.keysize = iArr[5];
        this.height = iArr[6];
        this.f1545w = iArr[7];
        this.checksum = iArr[8];
        this.mdsize = this.messDigestOTS.getDigestSize();
        int i2 = this.f1545w;
        this.f1543k = (1 << i2) - 1;
        this.messagesize = (int) Math.ceil((r10 << 3) / i2);
        this.privateKeyOTS = bArr[0];
        this.seed = bArr[1];
        this.hash = bArr[2];
        this.sign = bArr[3];
        byte[] bArr2 = bArr[4];
        this.test8 = ((bArr2[1] & 255) << 8) | (bArr2[0] & 255) | ((bArr2[2] & 255) << 16) | ((bArr2[3] & 255) << 24) | ((bArr2[4] & 255) << 32) | ((bArr2[5] & 255) << 40) | ((bArr2[6] & 255) << 48) | ((bArr2[7] & 255) << 56);
        this.big8 = (bArr2[8] & 255) | ((bArr2[9] & 255) << 8) | ((bArr2[10] & 255) << 16) | ((bArr2[11] & 255) << 24) | ((bArr2[12] & 255) << 32) | ((bArr2[13] & 255) << 40) | ((bArr2[14] & 255) << 48) | ((bArr2[15] & 255) << 56);
    }
}
