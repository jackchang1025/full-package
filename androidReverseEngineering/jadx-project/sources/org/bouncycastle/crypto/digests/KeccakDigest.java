package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class KeccakDigest implements ExtendedDigest {
    private static long[] KeccakRoundConstants = {1, 32898, -9223372036854742902L, -9223372034707259392L, 32907, 2147483649L, -9223372034707259263L, -9223372036854743031L, 138, 136, 2147516425L, 2147483658L, 2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L, -9223372036854743038L, -9223372036854775680L, 32778, -9223372034707292150L, -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L};
    protected int bitsInQueue;
    protected byte[] dataQueue;
    protected int fixedOutputLength;
    protected int rate;
    protected boolean squeezing;
    protected long[] state;

    public KeccakDigest() {
        this(288);
    }

    private void KeccakAbsorb(byte[] bArr, int i2) {
        int i3 = this.rate >>> 6;
        for (int i4 = 0; i4 < i3; i4++) {
            long[] jArr = this.state;
            jArr[i4] = jArr[i4] ^ Pack.littleEndianToLong(bArr, i2);
            i2 += 8;
        }
        KeccakPermutation();
    }

    private void KeccakExtract() {
        KeccakPermutation();
        Pack.longToLittleEndian(this.state, 0, this.rate >>> 6, this.dataQueue, 0);
        this.bitsInQueue = this.rate;
    }

    private void KeccakPermutation() {
        long[] jArr = this.state;
        int i2 = 0;
        long j2 = jArr[0];
        char c = 1;
        long j3 = jArr[1];
        long j4 = jArr[2];
        char c2 = 3;
        long j5 = jArr[3];
        long j6 = jArr[4];
        long j7 = jArr[5];
        long j8 = jArr[6];
        long j9 = jArr[7];
        long j10 = jArr[8];
        long j11 = jArr[9];
        long j12 = jArr[10];
        long j13 = jArr[11];
        long j14 = jArr[12];
        long j15 = jArr[13];
        long j16 = jArr[14];
        long j17 = jArr[15];
        long j18 = jArr[16];
        long j19 = jArr[17];
        long j20 = jArr[18];
        long j21 = jArr[19];
        long j22 = jArr[20];
        long j23 = jArr[21];
        long j24 = jArr[22];
        long j25 = jArr[23];
        int i3 = 24;
        long j26 = jArr[24];
        while (i2 < i3) {
            long j27 = (((j2 ^ j7) ^ j12) ^ j17) ^ j22;
            long j28 = (((j3 ^ j8) ^ j13) ^ j18) ^ j23;
            long j29 = (((j4 ^ j9) ^ j14) ^ j19) ^ j24;
            long j30 = (((j5 ^ j10) ^ j15) ^ j20) ^ j25;
            long j31 = (((j6 ^ j11) ^ j16) ^ j21) ^ j26;
            long j32 = ((j28 << c) | (j28 >>> (-1))) ^ j31;
            long j33 = ((j29 << c) | (j29 >>> (-1))) ^ j27;
            long j34 = ((j30 << c) | (j30 >>> (-1))) ^ j28;
            long j35 = ((j31 << c) | (j31 >>> (-1))) ^ j29;
            long j36 = ((j27 << c) | (j27 >>> (-1))) ^ j30;
            long j37 = j2 ^ j32;
            long j38 = j7 ^ j32;
            long j39 = j12 ^ j32;
            long j40 = j17 ^ j32;
            long j41 = j22 ^ j32;
            long j42 = j3 ^ j33;
            long j43 = j8 ^ j33;
            long j44 = j13 ^ j33;
            long j45 = j18 ^ j33;
            long j46 = j23 ^ j33;
            long j47 = j4 ^ j34;
            long j48 = j9 ^ j34;
            long j49 = j14 ^ j34;
            long j50 = j19 ^ j34;
            long j51 = j24 ^ j34;
            long j52 = j5 ^ j35;
            long j53 = j10 ^ j35;
            long j54 = j15 ^ j35;
            long j55 = j20 ^ j35;
            long j56 = j25 ^ j35;
            long j57 = j6 ^ j36;
            long j58 = j11 ^ j36;
            long j59 = j16 ^ j36;
            long j60 = j21 ^ j36;
            long j61 = j26 ^ j36;
            long j62 = (j42 << c) | (j42 >>> 63);
            long j63 = (j43 << 44) | (j43 >>> 20);
            long j64 = (j58 << 20) | (j58 >>> 44);
            long j65 = (j51 << 61) | (j51 >>> c2);
            long j66 = (j59 << 39) | (j59 >>> 25);
            long j67 = (j41 << 18) | (j41 >>> 46);
            long j68 = (j47 << 62) | (j47 >>> 2);
            long j69 = (j49 << 43) | (j49 >>> 21);
            long j70 = (j54 << 25) | (j54 >>> 39);
            long j71 = (j60 << 8) | (j60 >>> 56);
            long j72 = (j56 << 56) | (j56 >>> 8);
            long j73 = (j40 << 41) | (j40 >>> 23);
            long j74 = (j57 << 27) | (j57 >>> 37);
            long j75 = (j61 << 14) | (j61 >>> 50);
            long j76 = (j46 << 2) | (j46 >>> 62);
            long j77 = (j53 << 55) | (j53 >>> 9);
            long j78 = (j45 << 45) | (j45 >>> 19);
            long j79 = (j38 << 36) | (j38 >>> 28);
            long j80 = (j52 << 28) | (j52 >>> 36);
            long j81 = (j55 << 21) | (j55 >>> 43);
            long j82 = (j50 << 15) | (j50 >>> 49);
            long j83 = (j44 << 10) | (j44 >>> 54);
            long j84 = (j48 << 6) | (j48 >>> 58);
            long j85 = (j39 << 3) | (j39 >>> 61);
            long j86 = ((~j63) & j69) ^ j37;
            long j87 = ((~j69) & j81) ^ j63;
            j4 = j69 ^ ((~j81) & j75);
            j5 = j81 ^ ((~j75) & j37);
            long j88 = j75 ^ ((~j37) & j63);
            long j89 = j80 ^ ((~j64) & j85);
            long j90 = ((~j85) & j78) ^ j64;
            long j91 = ((~j78) & j65) ^ j85;
            long j92 = j78 ^ ((~j65) & j80);
            long j93 = ((~j80) & j64) ^ j65;
            j12 = j62 ^ ((~j84) & j70);
            long j94 = ((~j70) & j71) ^ j84;
            long j95 = ((~j71) & j67) ^ j70;
            long j96 = j71 ^ ((~j67) & j62);
            long j97 = ((~j62) & j84) ^ j67;
            long j98 = j74 ^ ((~j79) & j83);
            long j99 = ((~j83) & j82) ^ j79;
            long j100 = j83 ^ ((~j82) & j72);
            long j101 = ((~j72) & j74) ^ j82;
            long j102 = ((~j74) & j79) ^ j72;
            long j103 = j68 ^ ((~j77) & j66);
            long j104 = ((~j66) & j73) ^ j77;
            j22 = j103;
            long j105 = j66 ^ ((~j73) & j76);
            long j106 = ((~j76) & j68) ^ j73;
            long j107 = ((~j68) & j77) ^ j76;
            long j108 = j86 ^ KeccakRoundConstants[i2];
            i2++;
            j8 = j90;
            j14 = j95;
            j13 = j94;
            j15 = j96;
            j23 = j104;
            c2 = 3;
            j25 = j106;
            j24 = j105;
            j11 = j93;
            jArr = jArr;
            j21 = j102;
            j16 = j97;
            j9 = j91;
            j10 = j92;
            j19 = j100;
            j17 = j98;
            j6 = j88;
            j7 = j89;
            i3 = 24;
            j20 = j101;
            j18 = j99;
            c = 1;
            j3 = j87;
            j26 = j107;
            j2 = j108;
        }
        long[] jArr2 = jArr;
        jArr2[0] = j2;
        jArr2[1] = j3;
        jArr2[2] = j4;
        jArr2[3] = j5;
        jArr2[4] = j6;
        jArr2[5] = j7;
        jArr2[6] = j8;
        jArr2[7] = j9;
        jArr2[8] = j10;
        jArr2[9] = j11;
        jArr2[10] = j12;
        jArr2[11] = j13;
        jArr2[12] = j14;
        jArr2[13] = j15;
        jArr2[14] = j16;
        jArr2[15] = j17;
        jArr2[16] = j18;
        jArr2[17] = j19;
        jArr2[18] = j20;
        jArr2[19] = j21;
        jArr2[20] = j22;
        jArr2[21] = j23;
        jArr2[22] = j24;
        jArr2[23] = j25;
        jArr2[24] = j26;
    }

    private void init(int i2) {
        if (i2 != 128 && i2 != 224 && i2 != 256 && i2 != 288 && i2 != 384 && i2 != 512) {
            throw new IllegalArgumentException("bitLength must be one of 128, 224, 256, 288, 384, or 512.");
        }
        initSponge(1600 - (i2 << 1));
    }

    private void initSponge(int i2) {
        if (i2 <= 0 || i2 >= 1600 || i2 % 64 != 0) {
            throw new IllegalStateException("invalid rate value");
        }
        this.rate = i2;
        int i3 = 0;
        while (true) {
            long[] jArr = this.state;
            if (i3 >= jArr.length) {
                Arrays.fill(this.dataQueue, (byte) 0);
                this.bitsInQueue = 0;
                this.squeezing = false;
                this.fixedOutputLength = (1600 - i2) / 2;
                return;
            }
            jArr[i3] = 0;
            i3++;
        }
    }

    private void padAndSwitchToSqueezingPhase() {
        byte[] bArr = this.dataQueue;
        int i2 = this.bitsInQueue;
        int i3 = i2 >>> 3;
        bArr[i3] = (byte) (bArr[i3] | ((byte) (1 << (i2 & 7))));
        int i4 = i2 + 1;
        this.bitsInQueue = i4;
        if (i4 == this.rate) {
            KeccakAbsorb(bArr, 0);
        } else {
            int i5 = i4 >>> 6;
            int i6 = i4 & 63;
            int i7 = 0;
            for (int i8 = 0; i8 < i5; i8++) {
                long[] jArr = this.state;
                jArr[i8] = jArr[i8] ^ Pack.littleEndianToLong(this.dataQueue, i7);
                i7 += 8;
            }
            if (i6 > 0) {
                long[] jArr2 = this.state;
                jArr2[i5] = (((1 << i6) - 1) & Pack.littleEndianToLong(this.dataQueue, i7)) ^ jArr2[i5];
            }
        }
        long[] jArr3 = this.state;
        int i9 = (this.rate - 1) >>> 6;
        jArr3[i9] = jArr3[i9] ^ Long.MIN_VALUE;
        this.bitsInQueue = 0;
        this.squeezing = true;
    }

    public void absorb(byte b) {
        int i2 = this.bitsInQueue;
        if (i2 % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        byte[] bArr = this.dataQueue;
        bArr[i2 >>> 3] = b;
        int i3 = i2 + 8;
        this.bitsInQueue = i3;
        if (i3 == this.rate) {
            KeccakAbsorb(bArr, 0);
            this.bitsInQueue = 0;
        }
    }

    public void absorbBits(int i2, int i3) {
        if (i3 < 1 || i3 > 7) {
            throw new IllegalArgumentException("'bits' must be in the range 1 to 7");
        }
        int i4 = this.bitsInQueue;
        if (i4 % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        this.dataQueue[i4 >>> 3] = (byte) (i2 & ((1 << i3) - 1));
        this.bitsInQueue = i4 + i3;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        squeeze(bArr, i2, this.fixedOutputLength);
        reset();
        return getDigestSize();
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "Keccak-" + this.fixedOutputLength;
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.rate / 8;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.fixedOutputLength / 8;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        init(this.fixedOutputLength);
    }

    public void squeeze(byte[] bArr, int i2, long j2) {
        if (!this.squeezing) {
            padAndSwitchToSqueezingPhase();
        }
        long j3 = 0;
        if (j2 % 8 != 0) {
            throw new IllegalStateException("outputLength not a multiple of 8");
        }
        while (j3 < j2) {
            if (this.bitsInQueue == 0) {
                KeccakExtract();
            }
            int min = (int) Math.min(this.bitsInQueue, j2 - j3);
            System.arraycopy(this.dataQueue, (this.rate - this.bitsInQueue) / 8, bArr, ((int) (j3 / 8)) + i2, min / 8);
            this.bitsInQueue -= min;
            j3 += min;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        absorb(b);
    }

    public KeccakDigest(int i2) {
        this.state = new long[25];
        this.dataQueue = new byte[192];
        init(i2);
    }

    public void absorb(byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7 = this.bitsInQueue;
        if (i7 % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        int i8 = i7 >>> 3;
        int i9 = this.rate >>> 3;
        int i10 = i9 - i8;
        if (i3 < i10) {
            System.arraycopy(bArr, i2, this.dataQueue, i8, i3);
            i6 = this.bitsInQueue + (i3 << 3);
        } else {
            if (i8 > 0) {
                System.arraycopy(bArr, i2, this.dataQueue, i8, i10);
                i4 = i10 + 0;
                KeccakAbsorb(this.dataQueue, 0);
            } else {
                i4 = 0;
            }
            while (true) {
                i5 = i3 - i4;
                if (i5 < i9) {
                    break;
                }
                KeccakAbsorb(bArr, i2 + i4);
                i4 += i9;
            }
            System.arraycopy(bArr, i2 + i4, this.dataQueue, 0, i5);
            i6 = i5 << 3;
        }
        this.bitsInQueue = i6;
    }

    public int doFinal(byte[] bArr, int i2, byte b, int i3) {
        if (i3 > 0) {
            absorbBits(b, i3);
        }
        squeeze(bArr, i2, this.fixedOutputLength);
        reset();
        return getDigestSize();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        absorb(bArr, i2, i3);
    }

    public KeccakDigest(KeccakDigest keccakDigest) {
        long[] jArr = new long[25];
        this.state = jArr;
        this.dataQueue = new byte[192];
        long[] jArr2 = keccakDigest.state;
        System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
        byte[] bArr = keccakDigest.dataQueue;
        System.arraycopy(bArr, 0, this.dataQueue, 0, bArr.length);
        this.rate = keccakDigest.rate;
        this.bitsInQueue = keccakDigest.bitsInQueue;
        this.fixedOutputLength = keccakDigest.fixedOutputLength;
        this.squeezing = keccakDigest.squeezing;
    }
}
