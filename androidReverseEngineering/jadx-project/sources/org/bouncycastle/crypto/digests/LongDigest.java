package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public abstract class LongDigest implements ExtendedDigest, Memoable, EncodableDigest {
    private static final int BYTE_LENGTH = 128;

    /* renamed from: K */
    static final long[] f1172K = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    protected long H1;
    protected long H2;
    protected long H3;
    protected long H4;
    protected long H5;
    protected long H6;
    protected long H7;
    protected long H8;

    /* renamed from: W */
    private long[] f1173W;
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf;
    private int xBufOff;

    public LongDigest() {
        this.xBuf = new byte[8];
        this.f1173W = new long[80];
        this.xBufOff = 0;
        reset();
    }

    private long Ch(long j2, long j3, long j4) {
        return ((~j2) & j4) ^ (j3 & j2);
    }

    private long Maj(long j2, long j3, long j4) {
        return ((j2 & j4) ^ (j2 & j3)) ^ (j3 & j4);
    }

    private long Sigma0(long j2) {
        return (j2 >>> 7) ^ (((j2 << 63) | (j2 >>> 1)) ^ ((j2 << 56) | (j2 >>> 8)));
    }

    private long Sigma1(long j2) {
        return (j2 >>> 6) ^ (((j2 << 45) | (j2 >>> 19)) ^ ((j2 << 3) | (j2 >>> 61)));
    }

    private long Sum0(long j2) {
        return ((j2 >>> 39) | (j2 << 25)) ^ (((j2 << 36) | (j2 >>> 28)) ^ ((j2 << 30) | (j2 >>> 34)));
    }

    private long Sum1(long j2) {
        return ((j2 >>> 41) | (j2 << 23)) ^ (((j2 << 50) | (j2 >>> 14)) ^ ((j2 << 46) | (j2 >>> 18)));
    }

    private void adjustByteCounts() {
        long j2 = this.byteCount1;
        if (j2 > 2305843009213693951L) {
            this.byteCount2 += j2 >>> 61;
            this.byteCount1 = j2 & 2305843009213693951L;
        }
    }

    public void copyIn(LongDigest longDigest) {
        byte[] bArr = longDigest.xBuf;
        System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
        this.xBufOff = longDigest.xBufOff;
        this.byteCount1 = longDigest.byteCount1;
        this.byteCount2 = longDigest.byteCount2;
        this.H1 = longDigest.H1;
        this.H2 = longDigest.H2;
        this.H3 = longDigest.H3;
        this.H4 = longDigest.H4;
        this.H5 = longDigest.H5;
        this.H6 = longDigest.H6;
        this.H7 = longDigest.H7;
        this.H8 = longDigest.H8;
        long[] jArr = longDigest.f1173W;
        System.arraycopy(jArr, 0, this.f1173W, 0, jArr.length);
        this.wOff = longDigest.wOff;
    }

    public void finish() {
        adjustByteCounts();
        long j2 = this.byteCount1 << 3;
        long j3 = this.byteCount2;
        byte b = DerValue.TAG_CONTEXT;
        while (true) {
            update(b);
            if (this.xBufOff == 0) {
                processLength(j2, j3);
                processBlock();
                return;
            }
            b = 0;
        }
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 128;
    }

    public int getEncodedStateSize() {
        return (this.wOff * 8) + 96;
    }

    public void populateState(byte[] bArr) {
        System.arraycopy(this.xBuf, 0, bArr, 0, this.xBufOff);
        Pack.intToBigEndian(this.xBufOff, bArr, 8);
        Pack.longToBigEndian(this.byteCount1, bArr, 12);
        Pack.longToBigEndian(this.byteCount2, bArr, 20);
        Pack.longToBigEndian(this.H1, bArr, 28);
        Pack.longToBigEndian(this.H2, bArr, 36);
        Pack.longToBigEndian(this.H3, bArr, 44);
        Pack.longToBigEndian(this.H4, bArr, 52);
        Pack.longToBigEndian(this.H5, bArr, 60);
        Pack.longToBigEndian(this.H6, bArr, 68);
        Pack.longToBigEndian(this.H7, bArr, 76);
        Pack.longToBigEndian(this.H8, bArr, 84);
        Pack.intToBigEndian(this.wOff, bArr, 92);
        for (int i2 = 0; i2 < this.wOff; i2++) {
            Pack.longToBigEndian(this.f1173W[i2], bArr, (i2 * 8) + 96);
        }
    }

    public void processBlock() {
        adjustByteCounts();
        for (int i2 = 16; i2 <= 79; i2++) {
            long[] jArr = this.f1173W;
            long Sigma1 = Sigma1(jArr[i2 - 2]);
            long[] jArr2 = this.f1173W;
            jArr[i2] = Sigma1 + jArr2[i2 - 7] + Sigma0(jArr2[i2 - 15]) + this.f1173W[i2 - 16];
        }
        long j2 = this.H1;
        long j3 = this.H2;
        long j4 = this.H3;
        long j5 = this.H4;
        long j6 = this.H5;
        long j7 = this.H6;
        long j8 = this.H7;
        long j9 = j7;
        long j10 = j5;
        int i3 = 0;
        long j11 = j3;
        long j12 = j4;
        long j13 = j6;
        int i4 = 0;
        long j14 = this.H8;
        long j15 = j2;
        long j16 = j8;
        while (i4 < 10) {
            long j17 = j13;
            long Sum1 = Sum1(j13) + Ch(j13, j9, j16);
            long[] jArr3 = f1172K;
            int i5 = i3 + 1;
            long j18 = Sum1 + jArr3[i3] + this.f1173W[i3] + j14;
            long j19 = j10 + j18;
            long Sum0 = Sum0(j15) + Maj(j15, j11, j12) + j18;
            int i6 = i5 + 1;
            long Sum12 = Sum1(j19) + Ch(j19, j17, j9) + jArr3[i5] + this.f1173W[i5] + j16;
            long j20 = j12 + Sum12;
            long j21 = j15;
            long Sum02 = Sum0(Sum0) + Maj(Sum0, j15, j11) + Sum12;
            int i7 = i6 + 1;
            long Sum13 = Sum1(j20) + Ch(j20, j19, j17) + jArr3[i6] + this.f1173W[i6] + j9;
            long j22 = j11 + Sum13;
            long Sum03 = Sum0(Sum02) + Maj(Sum02, Sum0, j21) + Sum13;
            int i8 = i7 + 1;
            long Sum14 = Sum1(j22) + Ch(j22, j20, j19) + jArr3[i7] + this.f1173W[i7] + j17;
            long j23 = j21 + Sum14;
            long Sum04 = Sum0(Sum03) + Maj(Sum03, Sum02, Sum0) + Sum14;
            int i9 = i8 + 1;
            long Sum15 = Sum1(j23) + Ch(j23, j22, j20) + jArr3[i8] + this.f1173W[i8] + j19;
            long j24 = Sum0 + Sum15;
            long Sum05 = Sum0(Sum04) + Maj(Sum04, Sum03, Sum02) + Sum15;
            int i10 = i9 + 1;
            long Sum16 = Sum1(j24) + Ch(j24, j23, j22) + jArr3[i9] + this.f1173W[i9] + j20;
            long j25 = Sum02 + Sum16;
            long Sum06 = Sum0(Sum05) + Maj(Sum05, Sum04, Sum03) + Sum16;
            int i11 = i10 + 1;
            long Sum17 = Sum1(j25) + Ch(j25, j24, j23) + jArr3[i10] + this.f1173W[i10] + j22;
            long j26 = Sum03 + Sum17;
            long Sum07 = Sum0(Sum06) + Maj(Sum06, Sum05, Sum04) + Sum17;
            long Sum18 = Sum1(j26) + Ch(j26, j25, j24) + jArr3[i11] + this.f1173W[i11] + j23;
            long j27 = Sum04 + Sum18;
            long Sum08 = Sum0(Sum07) + Maj(Sum07, Sum06, Sum05) + Sum18;
            i4++;
            j12 = Sum06;
            i3 = i11 + 1;
            j13 = j27;
            j16 = j25;
            j9 = j26;
            j10 = Sum05;
            j14 = j24;
            j11 = Sum07;
            j15 = Sum08;
        }
        this.H1 += j15;
        this.H2 += j11;
        this.H3 += j12;
        this.H4 += j10;
        this.H5 += j13;
        this.H6 += j9;
        this.H7 += j16;
        this.H8 += j14;
        this.wOff = 0;
        for (int i12 = 0; i12 < 16; i12++) {
            this.f1173W[i12] = 0;
        }
    }

    public void processLength(long j2, long j3) {
        if (this.wOff > 14) {
            processBlock();
        }
        long[] jArr = this.f1173W;
        jArr[14] = j3;
        jArr[15] = j2;
    }

    public void processWord(byte[] bArr, int i2) {
        this.f1173W[this.wOff] = Pack.bigEndianToLong(bArr, i2);
        int i3 = this.wOff + 1;
        this.wOff = i3;
        if (i3 == 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount1 = 0L;
        this.byteCount2 = 0L;
        int i2 = 0;
        this.xBufOff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = 0;
            i3++;
        }
        this.wOff = 0;
        while (true) {
            long[] jArr = this.f1173W;
            if (i2 == jArr.length) {
                return;
            }
            jArr[i2] = 0;
            i2++;
        }
    }

    public void restoreState(byte[] bArr) {
        int bigEndianToInt = Pack.bigEndianToInt(bArr, 8);
        this.xBufOff = bigEndianToInt;
        System.arraycopy(bArr, 0, this.xBuf, 0, bigEndianToInt);
        this.byteCount1 = Pack.bigEndianToLong(bArr, 12);
        this.byteCount2 = Pack.bigEndianToLong(bArr, 20);
        this.H1 = Pack.bigEndianToLong(bArr, 28);
        this.H2 = Pack.bigEndianToLong(bArr, 36);
        this.H3 = Pack.bigEndianToLong(bArr, 44);
        this.H4 = Pack.bigEndianToLong(bArr, 52);
        this.H5 = Pack.bigEndianToLong(bArr, 60);
        this.H6 = Pack.bigEndianToLong(bArr, 68);
        this.H7 = Pack.bigEndianToLong(bArr, 76);
        this.H8 = Pack.bigEndianToLong(bArr, 84);
        this.wOff = Pack.bigEndianToInt(bArr, 92);
        for (int i2 = 0; i2 < this.wOff; i2++) {
            this.f1173W[i2] = Pack.bigEndianToLong(bArr, (i2 * 8) + 96);
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i2 = this.xBufOff;
        int i3 = i2 + 1;
        this.xBufOff = i3;
        bArr[i2] = b;
        if (i3 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount1++;
    }

    public LongDigest(LongDigest longDigest) {
        this.xBuf = new byte[8];
        this.f1173W = new long[80];
        copyIn(longDigest);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.xBufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > this.xBuf.length) {
            processWord(bArr, i2);
            byte[] bArr2 = this.xBuf;
            i2 += bArr2.length;
            i3 -= bArr2.length;
            this.byteCount1 += bArr2.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
