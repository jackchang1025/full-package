package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import java.util.Iterator;
import java.util.Stack;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.params.Blake3Parameters;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class Blake3Digest implements ExtendedDigest, Memoable, Xof {
    private static final int BLOCKLEN = 64;
    private static final int CHAINING0 = 0;
    private static final int CHAINING1 = 1;
    private static final int CHAINING2 = 2;
    private static final int CHAINING3 = 3;
    private static final int CHAINING4 = 4;
    private static final int CHAINING5 = 5;
    private static final int CHAINING6 = 6;
    private static final int CHAINING7 = 7;
    private static final int CHUNKEND = 2;
    private static final int CHUNKLEN = 1024;
    private static final int CHUNKSTART = 1;
    private static final int COUNT0 = 12;
    private static final int COUNT1 = 13;
    private static final int DATALEN = 14;
    private static final int DERIVECONTEXT = 32;
    private static final int DERIVEKEY = 64;
    private static final String ERR_OUTPUTTING = "Already outputting";
    private static final int FLAGS = 15;
    private static final int IV0 = 8;
    private static final int IV1 = 9;
    private static final int IV2 = 10;
    private static final int IV3 = 11;
    private static final int KEYEDHASH = 16;
    private static final int NUMWORDS = 8;
    private static final int PARENT = 4;
    private static final int ROOT = 8;
    private static final int ROUNDS = 7;
    private long outputAvailable;
    private boolean outputting;
    private final byte[] theBuffer;
    private final int[] theChaining;
    private long theCounter;
    private int theCurrBytes;
    private final int theDigestLen;
    private final byte[] theIndices;
    private final int[] theK;
    private final int[] theM;
    private int theMode;
    private int theOutputDataLen;
    private int theOutputMode;
    private int thePos;
    private final Stack theStack;
    private final int[] theV;
    private static final byte[] SIGMA = {2, 6, 3, 10, 7, 0, 4, 13, 1, 11, DerValue.tag_UTF8String, 5, 9, 14, 15, 8};
    private static final byte[] ROTATE = {Tnaf.POW_2_WIDTH, DerValue.tag_UTF8String, 8, 7};
    private static final int[] IV = {1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225};

    public Blake3Digest() {
        this(32);
    }

    private void adjustChaining() {
        if (!this.outputting) {
            for (int i2 = 0; i2 < 8; i2++) {
                int[] iArr = this.theChaining;
                int[] iArr2 = this.theV;
                iArr[i2] = iArr2[i2 + 8] ^ iArr2[i2];
            }
            return;
        }
        for (int i3 = 0; i3 < 8; i3++) {
            int[] iArr3 = this.theV;
            int i4 = i3 + 8;
            iArr3[i3] = iArr3[i3] ^ iArr3[i4];
            iArr3[i4] = iArr3[i4] ^ this.theChaining[i3];
        }
        for (int i5 = 0; i5 < 16; i5++) {
            Pack.intToLittleEndian(this.theV[i5], this.theBuffer, i5 * 4);
        }
        this.thePos = 0;
    }

    private void adjustStack() {
        for (long j2 = this.theCounter; j2 > 0 && (j2 & 1) != 1; j2 >>= 1) {
            System.arraycopy((int[]) this.theStack.pop(), 0, this.theM, 0, 8);
            System.arraycopy(this.theChaining, 0, this.theM, 8, 8);
            initParentBlock();
            compress();
        }
        this.theStack.push(Arrays.copyOf(this.theChaining, 8));
    }

    private void compress() {
        initIndices();
        int i2 = 0;
        while (true) {
            performRound();
            if (i2 >= 6) {
                adjustChaining();
                return;
            } else {
                permuteIndices();
                i2++;
            }
        }
    }

    private void compressBlock(byte[] bArr, int i2) {
        initChunkBlock(64, false);
        initM(bArr, i2);
        compress();
        if (this.theCurrBytes == 0) {
            adjustStack();
        }
    }

    private void compressFinalBlock(int i2) {
        initChunkBlock(i2, true);
        initM(this.theBuffer, 0);
        compress();
        processStack();
    }

    private void incrementBlockCount() {
        this.theCounter++;
        this.theCurrBytes = 0;
    }

    private void initChunkBlock(int i2, boolean z2) {
        System.arraycopy(this.theCurrBytes == 0 ? this.theK : this.theChaining, 0, this.theV, 0, 8);
        System.arraycopy(IV, 0, this.theV, 8, 4);
        int[] iArr = this.theV;
        long j2 = this.theCounter;
        iArr[12] = (int) j2;
        iArr[13] = (int) (j2 >> 32);
        iArr[14] = i2;
        int i3 = this.theMode;
        int i4 = this.theCurrBytes;
        iArr[15] = i3 + (i4 == 0 ? 1 : 0) + (z2 ? 2 : 0);
        int i5 = i4 + i2;
        this.theCurrBytes = i5;
        if (i5 >= 1024) {
            incrementBlockCount();
            int[] iArr2 = this.theV;
            iArr2[15] = iArr2[15] | 2;
        }
        if (z2 && this.theStack.isEmpty()) {
            setRoot();
        }
    }

    private void initIndices() {
        byte b = 0;
        while (true) {
            byte[] bArr = this.theIndices;
            if (b >= bArr.length) {
                return;
            }
            bArr[b] = b;
            b = (byte) (b + 1);
        }
    }

    private void initKey(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            this.theK[i2] = Pack.littleEndianToInt(bArr, i2 * 4);
        }
        this.theMode = 16;
    }

    private void initKeyFromContext() {
        System.arraycopy(this.theV, 0, this.theK, 0, 8);
        this.theMode = 64;
    }

    private void initM(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 16; i3++) {
            this.theM[i3] = Pack.littleEndianToInt(bArr, (i3 * 4) + i2);
        }
    }

    private void initNullKey() {
        System.arraycopy(IV, 0, this.theK, 0, 8);
    }

    private void initParentBlock() {
        System.arraycopy(this.theK, 0, this.theV, 0, 8);
        System.arraycopy(IV, 0, this.theV, 8, 4);
        int[] iArr = this.theV;
        iArr[12] = 0;
        iArr[13] = 0;
        iArr[14] = 64;
        iArr[15] = this.theMode | 4;
    }

    private void mixG(int i2, int i3, int i4, int i5, int i6) {
        int i7 = i2 << 1;
        int[] iArr = this.theV;
        int i8 = i7 + 1;
        int i9 = iArr[i4] + this.theM[this.theIndices[i7]] + iArr[i3];
        iArr[i3] = i9;
        int i10 = iArr[i6] ^ i9;
        byte[] bArr = ROTATE;
        iArr[i6] = Integers.rotateRight(i10, bArr[0]);
        int[] iArr2 = this.theV;
        int i11 = iArr2[i5] + iArr2[i6];
        iArr2[i5] = i11;
        iArr2[i4] = Integers.rotateRight(i11 ^ iArr2[i4], bArr[1]);
        int[] iArr3 = this.theV;
        int i12 = iArr3[i4] + this.theM[this.theIndices[i8]] + iArr3[i3];
        iArr3[i3] = i12;
        iArr3[i6] = Integers.rotateRight(iArr3[i6] ^ i12, bArr[2]);
        int[] iArr4 = this.theV;
        int i13 = iArr4[i5] + iArr4[i6];
        iArr4[i5] = i13;
        iArr4[i4] = Integers.rotateRight(i13 ^ iArr4[i4], bArr[3]);
    }

    private void nextOutputBlock() {
        this.theCounter++;
        System.arraycopy(this.theChaining, 0, this.theV, 0, 8);
        System.arraycopy(IV, 0, this.theV, 8, 4);
        int[] iArr = this.theV;
        long j2 = this.theCounter;
        iArr[12] = (int) j2;
        iArr[13] = (int) (j2 >> 32);
        iArr[14] = this.theOutputDataLen;
        iArr[15] = this.theOutputMode;
        compress();
    }

    private void performRound() {
        mixG(0, 0, 4, 8, 12);
        mixG(1, 1, 5, 9, 13);
        mixG(2, 2, 6, 10, 14);
        mixG(3, 3, 7, 11, 15);
        mixG(4, 0, 5, 10, 15);
        mixG(5, 1, 6, 11, 12);
        mixG(6, 2, 7, 8, 13);
        mixG(7, 3, 4, 9, 14);
    }

    private void permuteIndices() {
        byte b = 0;
        while (true) {
            byte[] bArr = this.theIndices;
            if (b >= bArr.length) {
                return;
            }
            bArr[b] = SIGMA[bArr[b]];
            b = (byte) (b + 1);
        }
    }

    private void processStack() {
        while (!this.theStack.isEmpty()) {
            System.arraycopy((int[]) this.theStack.pop(), 0, this.theM, 0, 8);
            System.arraycopy(this.theChaining, 0, this.theM, 8, 8);
            initParentBlock();
            if (this.theStack.isEmpty()) {
                setRoot();
            }
            compress();
        }
    }

    private void resetBlockCount() {
        this.theCounter = 0L;
        this.theCurrBytes = 0;
    }

    private void setRoot() {
        int[] iArr = this.theV;
        int i2 = iArr[15] | 8;
        iArr[15] = i2;
        this.theOutputMode = i2;
        this.theOutputDataLen = iArr[14];
        this.theCounter = 0L;
        this.outputting = true;
        this.outputAvailable = -1L;
        System.arraycopy(iArr, 0, this.theChaining, 0, 8);
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new Blake3Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        return doFinal(bArr, i2, getDigestSize());
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doOutput(byte[] bArr, int i2, int i3) {
        int i4;
        if (!this.outputting) {
            compressFinalBlock(this.thePos);
        }
        if (i3 >= 0) {
            long j2 = this.outputAvailable;
            if (j2 < 0 || i3 <= j2) {
                int i5 = this.thePos;
                if (i5 < 64) {
                    int min = Math.min(i3, 64 - i5);
                    System.arraycopy(this.theBuffer, this.thePos, bArr, i2, min);
                    this.thePos += min;
                    i2 += min;
                    i4 = i3 - min;
                } else {
                    i4 = i3;
                }
                while (i4 > 0) {
                    nextOutputBlock();
                    int min2 = Math.min(i4, 64);
                    System.arraycopy(this.theBuffer, 0, bArr, i2, min2);
                    this.thePos += min2;
                    i2 += min2;
                    i4 -= min2;
                }
                this.outputAvailable -= i3;
                return i3;
            }
        }
        throw new IllegalArgumentException("Insufficient bytes remaining");
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "BLAKE3";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 64;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.theDigestLen;
    }

    public void init(Blake3Parameters blake3Parameters) {
        byte[] key = blake3Parameters == null ? null : blake3Parameters.getKey();
        byte[] context = blake3Parameters != null ? blake3Parameters.getContext() : null;
        reset();
        if (key != null) {
            initKey(key);
            Arrays.fill(key, (byte) 0);
            return;
        }
        initNullKey();
        if (context == null) {
            this.theMode = 0;
            return;
        }
        this.theMode = 32;
        update(context, 0, context.length);
        doFinal(this.theBuffer, 0);
        initKeyFromContext();
        reset();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        resetBlockCount();
        this.thePos = 0;
        this.outputting = false;
        Arrays.fill(this.theBuffer, (byte) 0);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        if (this.outputting) {
            throw new IllegalStateException(ERR_OUTPUTTING);
        }
        byte[] bArr = this.theBuffer;
        if (bArr.length - this.thePos == 0) {
            compressBlock(bArr, 0);
            Arrays.fill(this.theBuffer, (byte) 0);
            this.thePos = 0;
        }
        byte[] bArr2 = this.theBuffer;
        int i2 = this.thePos;
        bArr2[i2] = b;
        this.thePos = i2 + 1;
    }

    public Blake3Digest(int i2) {
        this.theBuffer = new byte[64];
        this.theK = new int[8];
        this.theChaining = new int[8];
        this.theV = new int[16];
        this.theM = new int[16];
        this.theIndices = new byte[16];
        this.theStack = new Stack();
        this.theDigestLen = i2;
        init(null);
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doFinal(byte[] bArr, int i2, int i3) {
        if (this.outputting) {
            throw new IllegalStateException(ERR_OUTPUTTING);
        }
        int doOutput = doOutput(bArr, i2, i3);
        reset();
        return doOutput;
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        Blake3Digest blake3Digest = (Blake3Digest) memoable;
        this.theCounter = blake3Digest.theCounter;
        this.theCurrBytes = blake3Digest.theCurrBytes;
        this.theMode = blake3Digest.theMode;
        this.outputting = blake3Digest.outputting;
        this.outputAvailable = blake3Digest.outputAvailable;
        this.theOutputMode = blake3Digest.theOutputMode;
        this.theOutputDataLen = blake3Digest.theOutputDataLen;
        int[] iArr = blake3Digest.theChaining;
        int[] iArr2 = this.theChaining;
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        int[] iArr3 = blake3Digest.theK;
        int[] iArr4 = this.theK;
        System.arraycopy(iArr3, 0, iArr4, 0, iArr4.length);
        int[] iArr5 = blake3Digest.theM;
        int[] iArr6 = this.theM;
        System.arraycopy(iArr5, 0, iArr6, 0, iArr6.length);
        this.theStack.clear();
        Iterator it = blake3Digest.theStack.iterator();
        while (it.hasNext()) {
            this.theStack.push(Arrays.clone((int[]) it.next()));
        }
        byte[] bArr = blake3Digest.theBuffer;
        byte[] bArr2 = this.theBuffer;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.thePos = blake3Digest.thePos;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        if (bArr == null || i3 == 0) {
            return;
        }
        if (this.outputting) {
            throw new IllegalStateException(ERR_OUTPUTTING);
        }
        int i6 = this.thePos;
        if (i6 != 0) {
            i4 = 64 - i6;
            if (i4 >= i3) {
                System.arraycopy(bArr, i2, this.theBuffer, i6, i3);
                i5 = this.thePos + i3;
                this.thePos = i5;
            } else {
                System.arraycopy(bArr, i2, this.theBuffer, i6, i4);
                compressBlock(this.theBuffer, 0);
                this.thePos = 0;
                Arrays.fill(this.theBuffer, (byte) 0);
            }
        } else {
            i4 = 0;
        }
        int i7 = (i2 + i3) - 64;
        int i8 = i4 + i2;
        while (i8 < i7) {
            compressBlock(bArr, i8);
            i8 += 64;
        }
        int i9 = i2 + (i3 - i8);
        System.arraycopy(bArr, i8, this.theBuffer, 0, i9);
        i5 = this.thePos + i9;
        this.thePos = i5;
    }

    private Blake3Digest(Blake3Digest blake3Digest) {
        this.theBuffer = new byte[64];
        this.theK = new int[8];
        this.theChaining = new int[8];
        this.theV = new int[16];
        this.theM = new int[16];
        this.theIndices = new byte[16];
        this.theStack = new Stack();
        this.theDigestLen = blake3Digest.theDigestLen;
        reset(blake3Digest);
    }
}
