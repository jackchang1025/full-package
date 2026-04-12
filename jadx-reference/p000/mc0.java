package p000;

/* loaded from: classes2.dex */
public abstract class mc0 implements InterfaceC1432xw, xe0, InterfaceC1395wz {
    private static final int BYTE_LENGTH = 128;

    /* renamed from: K */
    static final long[] f58321K = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};

    /* renamed from: H1 */
    protected long f58322H1;

    /* renamed from: H2 */
    protected long f58323H2;

    /* renamed from: H3 */
    protected long f58324H3;

    /* renamed from: H4 */
    protected long f58325H4;

    /* renamed from: H5 */
    protected long f58326H5;

    /* renamed from: H6 */
    protected long f58327H6;

    /* renamed from: H7 */
    protected long f58328H7;

    /* renamed from: H8 */
    protected long f58329H8;

    /* renamed from: W */
    private long[] f58330W;
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf;
    private int xBufOff;

    public mc0() {
        this.xBuf = new byte[8];
        this.f58330W = new long[80];
        this.xBufOff = 0;
        reset();
    }

    /* renamed from: Ch */
    private long m213966Ch(long j, long j2, long j3) {
        return ((~j) & j3) ^ (j2 & j);
    }

    private long Maj(long j, long j2, long j3) {
        return ((j & j3) ^ (j & j2)) ^ (j2 & j3);
    }

    private long Sigma0(long j) {
        return (j >>> 7) ^ (((j << 63) | (j >>> 1)) ^ ((j << 56) | (j >>> 8)));
    }

    private long Sigma1(long j) {
        return (j >>> 6) ^ (((j << 45) | (j >>> 19)) ^ ((j << 3) | (j >>> 61)));
    }

    private long Sum0(long j) {
        return ((j >>> 39) | (j << 25)) ^ (((j << 36) | (j >>> 28)) ^ ((j << 30) | (j >>> 34)));
    }

    private long Sum1(long j) {
        return ((j >>> 41) | (j << 23)) ^ (((j << 50) | (j >>> 14)) ^ ((j << 46) | (j >>> 18)));
    }

    private void adjustByteCounts() {
        long j = this.byteCount1;
        if (j > 2305843009213693951L) {
            this.byteCount2 += j >>> 61;
            this.byteCount1 = j & 2305843009213693951L;
        }
    }

    @Override // p000.xe0
    public abstract /* synthetic */ xe0 copy();

    public void copyIn(mc0 mc0Var) {
        byte[] bArr = mc0Var.xBuf;
        System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
        this.xBufOff = mc0Var.xBufOff;
        this.byteCount1 = mc0Var.byteCount1;
        this.byteCount2 = mc0Var.byteCount2;
        this.f58322H1 = mc0Var.f58322H1;
        this.f58323H2 = mc0Var.f58323H2;
        this.f58324H3 = mc0Var.f58324H3;
        this.f58325H4 = mc0Var.f58325H4;
        this.f58326H5 = mc0Var.f58326H5;
        this.f58327H6 = mc0Var.f58327H6;
        this.f58328H7 = mc0Var.f58328H7;
        this.f58329H8 = mc0Var.f58329H8;
        long[] jArr = mc0Var.f58330W;
        System.arraycopy(jArr, 0, this.f58330W, 0, jArr.length);
        this.wOff = mc0Var.wOff;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ int doFinal(byte[] bArr, int i);

    public void finish() {
        adjustByteCounts();
        long j = this.byteCount1 << 3;
        long j2 = this.byteCount2;
        byte b = Byte.MIN_VALUE;
        while (true) {
            update(b);
            if (this.xBufOff == 0) {
                processLength(j, j2);
                processBlock();
                return;
            }
            b = 0;
        }
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ String getAlgorithmName();

    @Override // p000.InterfaceC1432xw
    public int getByteLength() {
        return 128;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ int getDigestSize();

    @Override // p000.InterfaceC1395wz
    public abstract /* synthetic */ byte[] getEncodedState();

    public int getEncodedStateSize() {
        return (this.wOff * 8) + 96;
    }

    public void populateState(byte[] bArr) {
        System.arraycopy(this.xBuf, 0, bArr, 0, this.xBufOff);
        wl0.intToBigEndian(this.xBufOff, bArr, 8);
        wl0.longToBigEndian(this.byteCount1, bArr, 12);
        wl0.longToBigEndian(this.byteCount2, bArr, 20);
        wl0.longToBigEndian(this.f58322H1, bArr, 28);
        wl0.longToBigEndian(this.f58323H2, bArr, 36);
        wl0.longToBigEndian(this.f58324H3, bArr, 44);
        wl0.longToBigEndian(this.f58325H4, bArr, 52);
        wl0.longToBigEndian(this.f58326H5, bArr, 60);
        wl0.longToBigEndian(this.f58327H6, bArr, 68);
        wl0.longToBigEndian(this.f58328H7, bArr, 76);
        wl0.longToBigEndian(this.f58329H8, bArr, 84);
        wl0.intToBigEndian(this.wOff, bArr, 92);
        for (int i = 0; i < this.wOff; i++) {
            wl0.longToBigEndian(this.f58330W[i], bArr, (i * 8) + 96);
        }
    }

    public void processBlock() {
        adjustByteCounts();
        for (int i = 16; i <= 79; i++) {
            long[] jArr = this.f58330W;
            long jSigma1 = Sigma1(jArr[i - 2]);
            long[] jArr2 = this.f58330W;
            jArr[i] = jSigma1 + jArr2[i - 7] + Sigma0(jArr2[i - 15]) + this.f58330W[i - 16];
        }
        long j = this.f58322H1;
        long j2 = this.f58323H2;
        long j3 = this.f58324H3;
        long j4 = this.f58325H4;
        long j5 = this.f58326H5;
        long j6 = j4;
        long j7 = this.f58327H6;
        int i2 = 0;
        int i3 = 0;
        long j8 = j3;
        long j9 = this.f58328H7;
        long j10 = this.f58329H8;
        long j11 = j;
        long j12 = j5;
        long j13 = j2;
        while (i2 < 10) {
            long j14 = j7;
            long j15 = j12;
            long j16 = j9;
            long jSum1 = Sum1(j12) + m213966Ch(j12, j14, j9);
            long[] jArr3 = f58321K;
            int i4 = i3 + 1;
            long j17 = jSum1 + jArr3[i3] + this.f58330W[i3] + j10;
            long j18 = j6 + j17;
            long j19 = j11;
            long j20 = j13;
            long j21 = j8;
            long jSum0 = Sum0(j11) + Maj(j19, j20, j21) + j17;
            long jSum12 = Sum1(j18) + m213966Ch(j18, j15, j14) + jArr3[i4];
            int i5 = i3 + 2;
            long j22 = jSum12 + this.f58330W[i4] + j16;
            long j23 = j21 + j22;
            long jSum02 = Sum0(jSum0) + Maj(jSum0, j19, j20) + j22;
            int i6 = i3 + 3;
            long jSum13 = Sum1(j23) + m213966Ch(j23, j18, j15) + jArr3[i5] + this.f58330W[i5] + j14;
            long j24 = j20 + jSum13;
            long jSum03 = Sum0(jSum02) + Maj(jSum02, jSum0, j19) + jSum13;
            long jSum14 = Sum1(j24) + m213966Ch(j24, j23, j18) + jArr3[i6];
            int i7 = i3 + 4;
            long j25 = jSum14 + this.f58330W[i6] + j15;
            long j26 = j19 + j25;
            long jSum04 = Sum0(jSum03) + Maj(jSum03, jSum02, jSum0) + j25;
            long jSum15 = Sum1(j26) + m213966Ch(j26, j24, j23) + jArr3[i7];
            int i8 = i3 + 5;
            long j27 = jSum15 + this.f58330W[i7] + j18;
            long j28 = jSum0 + j27;
            long jSum05 = Sum0(jSum04) + Maj(jSum04, jSum03, jSum02) + j27;
            long jSum16 = Sum1(j28) + m213966Ch(j28, j26, j24) + jArr3[i8];
            int i9 = i3 + 6;
            long j29 = jSum16 + this.f58330W[i8] + j23;
            long j30 = jSum02 + j29;
            long jSum06 = Sum0(jSum05) + Maj(jSum05, jSum04, jSum03) + j29;
            int i10 = i3 + 7;
            long jSum17 = Sum1(j30) + m213966Ch(j30, j28, j26) + jArr3[i9] + this.f58330W[i9] + j24;
            long j31 = jSum03 + jSum17;
            long jSum07 = Sum0(jSum06) + Maj(jSum06, jSum05, jSum04) + jSum17;
            i3 += 8;
            long jSum18 = Sum1(j31) + m213966Ch(j31, j30, j28) + jArr3[i10] + this.f58330W[i10] + j26;
            long jSum08 = Sum0(jSum07) + Maj(jSum07, jSum06, jSum05) + jSum18;
            i2++;
            j13 = jSum07;
            j8 = jSum06;
            j12 = jSum04 + jSum18;
            j10 = j28;
            j7 = j31;
            j6 = jSum05;
            j11 = jSum08;
            j9 = j30;
        }
        this.f58322H1 += j11;
        this.f58323H2 += j13;
        this.f58324H3 += j8;
        this.f58325H4 += j6;
        this.f58326H5 += j12;
        this.f58327H6 += j7;
        this.f58328H7 += j9;
        this.f58329H8 += j10;
        this.wOff = 0;
        for (int i11 = 0; i11 < 16; i11++) {
            this.f58330W[i11] = 0;
        }
    }

    public void processLength(long j, long j2) {
        if (this.wOff > 14) {
            processBlock();
        }
        long[] jArr = this.f58330W;
        jArr[14] = j2;
        jArr[15] = j;
    }

    public void processWord(byte[] bArr, int i) {
        this.f58330W[this.wOff] = wl0.bigEndianToLong(bArr, i);
        int i2 = this.wOff + 1;
        this.wOff = i2;
        if (i2 == 16) {
            processBlock();
        }
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        this.byteCount1 = 0L;
        this.byteCount2 = 0L;
        int i = 0;
        this.xBufOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        this.wOff = 0;
        while (true) {
            long[] jArr = this.f58330W;
            if (i == jArr.length) {
                return;
            }
            jArr[i] = 0;
            i++;
        }
    }

    @Override // p000.xe0
    public abstract /* synthetic */ void reset(xe0 xe0Var);

    public void restoreState(byte[] bArr) {
        int iBigEndianToInt = wl0.bigEndianToInt(bArr, 8);
        this.xBufOff = iBigEndianToInt;
        System.arraycopy(bArr, 0, this.xBuf, 0, iBigEndianToInt);
        this.byteCount1 = wl0.bigEndianToLong(bArr, 12);
        this.byteCount2 = wl0.bigEndianToLong(bArr, 20);
        this.f58322H1 = wl0.bigEndianToLong(bArr, 28);
        this.f58323H2 = wl0.bigEndianToLong(bArr, 36);
        this.f58324H3 = wl0.bigEndianToLong(bArr, 44);
        this.f58325H4 = wl0.bigEndianToLong(bArr, 52);
        this.f58326H5 = wl0.bigEndianToLong(bArr, 60);
        this.f58327H6 = wl0.bigEndianToLong(bArr, 68);
        this.f58328H7 = wl0.bigEndianToLong(bArr, 76);
        this.f58329H8 = wl0.bigEndianToLong(bArr, 84);
        this.wOff = wl0.bigEndianToInt(bArr, 92);
        for (int i = 0; i < this.wOff; i++) {
            this.f58330W[i] = wl0.bigEndianToLong(bArr, (i * 8) + 96);
        }
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        int i2 = i + 1;
        this.xBufOff = i2;
        bArr[i] = b;
        if (i2 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount1++;
    }

    public mc0(mc0 mc0Var) {
        this.xBuf = new byte[8];
        this.f58330W = new long[80];
        copyIn(mc0Var);
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte[] bArr, int i, int i2) {
        while (this.xBufOff != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.xBuf.length) {
            processWord(bArr, i);
            byte[] bArr2 = this.xBuf;
            i += bArr2.length;
            i2 -= bArr2.length;
            this.byteCount1 += bArr2.length;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
