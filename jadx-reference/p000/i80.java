package p000;

/* loaded from: classes2.dex */
public class i80 implements InterfaceC1432xw {
    private static long[] KeccakRoundConstants = {1, 32898, -9223372036854742902L, -9223372034707259392L, 32907, 2147483649L, -9223372034707259263L, -9223372036854743031L, 138, 136, 2147516425L, 2147483658L, 2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L, -9223372036854743038L, -9223372036854775680L, 32778, -9223372034707292150L, -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L};
    protected int bitsInQueue;
    protected byte[] dataQueue;
    protected int fixedOutputLength;
    protected int rate;
    protected boolean squeezing;
    protected long[] state;

    public i80() {
        this(288);
    }

    private void KeccakAbsorb(byte[] bArr, int i) {
        int i2 = this.rate >>> 6;
        for (int i3 = 0; i3 < i2; i3++) {
            long[] jArr = this.state;
            jArr[i3] = jArr[i3] ^ wl0.littleEndianToLong(bArr, i);
            i += 8;
        }
        KeccakPermutation();
    }

    private void KeccakExtract() {
        KeccakPermutation();
        wl0.longToLittleEndian(this.state, 0, this.rate >>> 6, this.dataQueue, 0);
        this.bitsInQueue = this.rate;
    }

    private void KeccakPermutation() {
        long[] jArr = this.state;
        long j = jArr[0];
        long j2 = jArr[1];
        char c = 2;
        long j3 = jArr[2];
        char c2 = 3;
        long j4 = jArr[3];
        char c3 = 4;
        long j5 = jArr[4];
        long j6 = jArr[5];
        long j7 = jArr[6];
        long j8 = jArr[7];
        long j9 = jArr[8];
        long j10 = jArr[9];
        long j11 = jArr[10];
        long j12 = jArr[11];
        long j13 = jArr[12];
        long j14 = jArr[13];
        long j15 = jArr[14];
        long j16 = jArr[15];
        long j17 = jArr[16];
        long j18 = jArr[17];
        long j19 = jArr[18];
        long j20 = jArr[19];
        long j21 = jArr[20];
        long j22 = jArr[21];
        long j23 = jArr[22];
        long j24 = jArr[23];
        int i = 24;
        long j25 = jArr[24];
        int i2 = 0;
        while (i2 < i) {
            long j26 = (((j ^ j6) ^ j11) ^ j16) ^ j21;
            long j27 = (((j2 ^ j7) ^ j12) ^ j17) ^ j22;
            long j28 = (((j3 ^ j8) ^ j13) ^ j18) ^ j23;
            long j29 = (((j4 ^ j9) ^ j14) ^ j19) ^ j24;
            long j30 = (((j5 ^ j10) ^ j15) ^ j20) ^ j25;
            long j31 = ((j27 << 1) | (j27 >>> (-1))) ^ j30;
            long j32 = ((j28 << 1) | (j28 >>> (-1))) ^ j26;
            long j33 = ((j29 << 1) | (j29 >>> (-1))) ^ j27;
            long j34 = ((j30 << 1) | (j30 >>> (-1))) ^ j28;
            long j35 = ((j26 << 1) | (j26 >>> (-1))) ^ j29;
            long j36 = j ^ j31;
            long j37 = j6 ^ j31;
            long j38 = j11 ^ j31;
            long j39 = j16 ^ j31;
            long j40 = j21 ^ j31;
            long j41 = j2 ^ j32;
            long j42 = j7 ^ j32;
            long j43 = j12 ^ j32;
            long j44 = j17 ^ j32;
            long j45 = j22 ^ j32;
            long j46 = j3 ^ j33;
            long j47 = j8 ^ j33;
            long j48 = j13 ^ j33;
            long j49 = j18 ^ j33;
            long j50 = j23 ^ j33;
            long j51 = j4 ^ j34;
            long j52 = j9 ^ j34;
            long j53 = j14 ^ j34;
            long j54 = j19 ^ j34;
            long j55 = j24 ^ j34;
            long j56 = j5 ^ j35;
            long j57 = j10 ^ j35;
            long j58 = j15 ^ j35;
            long j59 = j20 ^ j35;
            long j60 = j25 ^ j35;
            long j61 = (j41 << 1) | (j41 >>> 63);
            char c4 = c;
            long j62 = (j42 << 44) | (j42 >>> 20);
            char c5 = c2;
            long j63 = (j57 << 20) | (j57 >>> 44);
            char c6 = c3;
            long j64 = (j50 << 61) | (j50 >>> c5);
            int i3 = i;
            long j65 = (j58 << 39) | (j58 >>> 25);
            long j66 = (j40 << 18) | (j40 >>> 46);
            int i4 = i2;
            long j67 = (j46 << 62) | (j46 >>> c4);
            long j68 = (j48 << 43) | (j48 >>> 21);
            long j69 = (j53 << 25) | (j53 >>> 39);
            long j70 = (j59 << 8) | (j59 >>> 56);
            long j71 = (j55 << 56) | (j55 >>> 8);
            long j72 = (j39 << 41) | (j39 >>> 23);
            long j73 = (j56 << 27) | (j56 >>> 37);
            long j74 = (j60 << 14) | (j60 >>> 50);
            long j75 = (j45 << c4) | (j45 >>> 62);
            long j76 = (j52 << 55) | (j52 >>> 9);
            long j77 = (j44 << 45) | (j44 >>> 19);
            long j78 = (j37 << 36) | (j37 >>> 28);
            long j79 = (j51 << 28) | (j51 >>> 36);
            long j80 = (j54 << 21) | (j54 >>> 43);
            long j81 = (j49 << 15) | (j49 >>> 49);
            long j82 = (j43 << 10) | (j43 >>> 54);
            long j83 = (j47 << 6) | (j47 >>> 58);
            long j84 = (j38 << c5) | (j38 >>> 61);
            long j85 = j36 ^ ((~j62) & j68);
            j2 = ((~j68) & j80) ^ j62;
            long j86 = j68 ^ ((~j80) & j74);
            long j87 = j80 ^ ((~j74) & j36);
            long j88 = j74 ^ ((~j36) & j62);
            long j89 = j79 ^ ((~j63) & j84);
            long j90 = ((~j84) & j77) ^ j63;
            long j91 = ((~j77) & j64) ^ j84;
            long j92 = j77 ^ ((~j64) & j79);
            long j93 = ((~j79) & j63) ^ j64;
            j11 = j61 ^ ((~j83) & j69);
            long j94 = ((~j69) & j70) ^ j83;
            long j95 = ((~j70) & j66) ^ j69;
            long j96 = j70 ^ ((~j66) & j61);
            j15 = j66 ^ ((~j61) & j83);
            long j97 = j73 ^ ((~j78) & j82);
            long j98 = j78 ^ ((~j82) & j81);
            long j99 = ((~j81) & j71) ^ j82;
            long j100 = j81 ^ ((~j71) & j73);
            long j101 = ((~j73) & j78) ^ j71;
            long j102 = j67 ^ ((~j76) & j65);
            long j103 = ((~j65) & j72) ^ j76;
            long j104 = j65 ^ ((~j72) & j75);
            j24 = j72 ^ ((~j75) & j67);
            j17 = j98;
            j8 = j91;
            j22 = j103;
            j21 = j102;
            j23 = j104;
            j10 = j93;
            j9 = j92;
            j18 = j99;
            j14 = j96;
            j20 = j101;
            j6 = j89;
            j13 = j95;
            j7 = j90;
            c3 = c6;
            c = c4;
            j25 = j75 ^ ((~j67) & j76);
            jArr = jArr;
            i2 = i4 + 1;
            j = j85 ^ KeccakRoundConstants[i4];
            j4 = j87;
            j5 = j88;
            j12 = j94;
            i = i3;
            j19 = j100;
            j16 = j97;
            c2 = c5;
            j3 = j86;
        }
        long[] jArr2 = jArr;
        jArr2[0] = j;
        jArr2[1] = j2;
        jArr2[c] = j3;
        jArr2[c2] = j4;
        jArr2[c3] = j5;
        jArr2[5] = j6;
        jArr2[6] = j7;
        jArr2[7] = j8;
        jArr2[8] = j9;
        jArr2[9] = j10;
        jArr2[10] = j11;
        jArr2[11] = j12;
        jArr2[12] = j13;
        jArr2[13] = j14;
        jArr2[14] = j15;
        jArr2[15] = j16;
        jArr2[16] = j17;
        jArr2[17] = j18;
        jArr2[18] = j19;
        jArr2[19] = j20;
        jArr2[20] = j21;
        jArr2[21] = j22;
        jArr2[22] = j23;
        jArr2[23] = j24;
        jArr2[i] = j25;
    }

    private void init(int i) {
        if (i != 128 && i != 224 && i != 256 && i != 288 && i != 384 && i != 512) {
            throw new IllegalArgumentException("bitLength must be one of 128, 224, 256, 288, 384, or 512.");
        }
        initSponge(1600 - (i << 1));
    }

    private void initSponge(int i) {
        if (i <= 0 || i >= 1600 || i % 64 != 0) {
            throw new IllegalStateException("invalid rate value");
        }
        this.rate = i;
        int i2 = 0;
        while (true) {
            long[] jArr = this.state;
            if (i2 >= jArr.length) {
                C0133bg.fill(this.dataQueue, (byte) 0);
                this.bitsInQueue = 0;
                this.squeezing = false;
                this.fixedOutputLength = (1600 - i) / 2;
                return;
            }
            jArr[i2] = 0;
            i2++;
        }
    }

    private void padAndSwitchToSqueezingPhase() {
        byte[] bArr = this.dataQueue;
        int i = this.bitsInQueue;
        int i2 = i >>> 3;
        bArr[i2] = (byte) (bArr[i2] | ((byte) (1 << (i & 7))));
        int i3 = i + 1;
        this.bitsInQueue = i3;
        if (i3 == this.rate) {
            KeccakAbsorb(bArr, 0);
        } else {
            int i4 = i3 >>> 6;
            int i5 = i3 & 63;
            int i6 = 0;
            for (int i7 = 0; i7 < i4; i7++) {
                long[] jArr = this.state;
                jArr[i7] = jArr[i7] ^ wl0.littleEndianToLong(this.dataQueue, i6);
                i6 += 8;
            }
            if (i5 > 0) {
                long[] jArr2 = this.state;
                jArr2[i4] = (((1 << i5) - 1) & wl0.littleEndianToLong(this.dataQueue, i6)) ^ jArr2[i4];
            }
        }
        long[] jArr3 = this.state;
        int i8 = (this.rate - 1) >>> 6;
        jArr3[i8] = jArr3[i8] ^ Long.MIN_VALUE;
        this.bitsInQueue = 0;
        this.squeezing = true;
    }

    public void absorb(byte b) {
        int i = this.bitsInQueue;
        if (i % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        byte[] bArr = this.dataQueue;
        bArr[i >>> 3] = b;
        int i2 = i + 8;
        this.bitsInQueue = i2;
        if (i2 == this.rate) {
            KeccakAbsorb(bArr, 0);
            this.bitsInQueue = 0;
        }
    }

    public void absorbBits(int i, int i2) {
        if (i2 < 1 || i2 > 7) {
            throw new IllegalArgumentException("'bits' must be in the range 1 to 7");
        }
        int i3 = this.bitsInQueue;
        if (i3 % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        this.dataQueue[i3 >>> 3] = (byte) (i & ((1 << i2) - 1));
        this.bitsInQueue = i3 + i2;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        squeeze(bArr, i, this.fixedOutputLength);
        reset();
        return getDigestSize();
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "Keccak-" + this.fixedOutputLength;
    }

    @Override // p000.InterfaceC1432xw
    public int getByteLength() {
        return this.rate / 8;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return this.fixedOutputLength / 8;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        init(this.fixedOutputLength);
    }

    public void squeeze(byte[] bArr, int i, long j) {
        if (!this.squeezing) {
            padAndSwitchToSqueezingPhase();
        }
        long j2 = 0;
        if (j % 8 != 0) {
            throw new IllegalStateException("outputLength not a multiple of 8");
        }
        while (j2 < j) {
            if (this.bitsInQueue == 0) {
                KeccakExtract();
            }
            int iMin = (int) Math.min(this.bitsInQueue, j - j2);
            System.arraycopy(this.dataQueue, (this.rate - this.bitsInQueue) / 8, bArr, ((int) (j2 / 8)) + i, iMin / 8);
            this.bitsInQueue -= iMin;
            j2 += iMin;
        }
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte b) {
        absorb(b);
    }

    public i80(int i) {
        this.state = new long[25];
        this.dataQueue = new byte[192];
        init(i);
    }

    public void absorb(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        int i5 = this.bitsInQueue;
        if (i5 % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue");
        }
        if (this.squeezing) {
            throw new IllegalStateException("attempt to absorb while squeezing");
        }
        int i6 = i5 >>> 3;
        int i7 = this.rate >>> 3;
        int i8 = i7 - i6;
        if (i2 < i8) {
            System.arraycopy(bArr, i, this.dataQueue, i6, i2);
            i4 = this.bitsInQueue + (i2 << 3);
        } else {
            if (i6 > 0) {
                System.arraycopy(bArr, i, this.dataQueue, i6, i8);
                KeccakAbsorb(this.dataQueue, 0);
            } else {
                i8 = 0;
            }
            while (true) {
                i3 = i2 - i8;
                if (i3 < i7) {
                    break;
                }
                KeccakAbsorb(bArr, i + i8);
                i8 += i7;
            }
            System.arraycopy(bArr, i + i8, this.dataQueue, 0, i3);
            i4 = i3 << 3;
        }
        this.bitsInQueue = i4;
    }

    public int doFinal(byte[] bArr, int i, byte b, int i2) {
        if (i2 > 0) {
            absorbBits(b, i2);
        }
        squeeze(bArr, i, this.fixedOutputLength);
        reset();
        return getDigestSize();
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte[] bArr, int i, int i2) {
        absorb(bArr, i, i2);
    }

    public i80(i80 i80Var) {
        long[] jArr = new long[25];
        this.state = jArr;
        this.dataQueue = new byte[192];
        long[] jArr2 = i80Var.state;
        System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
        byte[] bArr = i80Var.dataQueue;
        System.arraycopy(bArr, 0, this.dataQueue, 0, bArr.length);
        this.rate = i80Var.rate;
        this.bitsInQueue = i80Var.bitsInQueue;
        this.fixedOutputLength = i80Var.fixedOutputLength;
        this.squeezing = i80Var.squeezing;
    }
}
