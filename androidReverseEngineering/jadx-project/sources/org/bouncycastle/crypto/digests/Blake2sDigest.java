package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class Blake2sDigest implements ExtendedDigest {
    private static final int BLOCK_LENGTH_BYTES = 64;
    private static final int ROUNDS = 10;
    private static final int[] blake2s_IV = {1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225};
    private static final byte[][] blake2s_sigma = {new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, DerValue.tag_UTF8String, 13, 14, 15}, new byte[]{14, 10, 4, 8, 9, 15, 13, 6, 1, DerValue.tag_UTF8String, 0, 2, 11, 7, 5, 3}, new byte[]{11, 8, DerValue.tag_UTF8String, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4}, new byte[]{7, 9, 3, 1, 13, DerValue.tag_UTF8String, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8}, new byte[]{9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, DerValue.tag_UTF8String, 6, 8, 3, 13}, new byte[]{2, DerValue.tag_UTF8String, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9}, new byte[]{DerValue.tag_UTF8String, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11}, new byte[]{13, 11, 7, 14, DerValue.tag_UTF8String, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10}, new byte[]{6, 15, 14, 9, 11, 3, 0, 8, DerValue.tag_UTF8String, 2, 13, 7, 1, 4, 10, 5}, new byte[]{10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, DerValue.tag_UTF8String, 13, 0}};
    private byte[] buffer;
    private int bufferPos;
    private int[] chainValue;
    private int depth;
    private int digestLength;

    /* renamed from: f0, reason: collision with root package name */
    private int f2333f0;
    private int fanout;
    private int innerHashLength;
    private int[] internalState;
    private byte[] key;
    private int keyLength;
    private int leafLength;
    private int nodeDepth;
    private long nodeOffset;
    private byte[] personalization;
    private byte[] salt;

    /* renamed from: t0, reason: collision with root package name */
    private int f2334t0;
    private int t1;

    public Blake2sDigest() {
        this(256);
    }

    /* renamed from: G */
    private void m1188G(int i2, int i3, int i4, int i5, int i6, int i7) {
        int[] iArr = this.internalState;
        int i8 = iArr[i4] + iArr[i5] + i2;
        iArr[i4] = i8;
        iArr[i7] = rotr32(iArr[i7] ^ i8, 16);
        int[] iArr2 = this.internalState;
        int i9 = iArr2[i6] + iArr2[i7];
        iArr2[i6] = i9;
        iArr2[i5] = rotr32(i9 ^ iArr2[i5], 12);
        int[] iArr3 = this.internalState;
        int i10 = iArr3[i4] + iArr3[i5] + i3;
        iArr3[i4] = i10;
        iArr3[i7] = rotr32(iArr3[i7] ^ i10, 8);
        int[] iArr4 = this.internalState;
        int i11 = iArr4[i6] + iArr4[i7];
        iArr4[i6] = i11;
        iArr4[i5] = rotr32(i11 ^ iArr4[i5], 7);
    }

    private void compress(byte[] bArr, int i2) {
        initializeInternalState();
        int[] iArr = new int[16];
        int i3 = 0;
        for (int i4 = 0; i4 < 16; i4++) {
            iArr[i4] = Pack.littleEndianToInt(bArr, (i4 * 4) + i2);
        }
        for (int i5 = 0; i5 < 10; i5++) {
            byte[][] bArr2 = blake2s_sigma;
            byte[] bArr3 = bArr2[i5];
            m1188G(iArr[bArr3[0]], iArr[bArr3[1]], 0, 4, 8, 12);
            byte[] bArr4 = bArr2[i5];
            m1188G(iArr[bArr4[2]], iArr[bArr4[3]], 1, 5, 9, 13);
            byte[] bArr5 = bArr2[i5];
            m1188G(iArr[bArr5[4]], iArr[bArr5[5]], 2, 6, 10, 14);
            byte[] bArr6 = bArr2[i5];
            m1188G(iArr[bArr6[6]], iArr[bArr6[7]], 3, 7, 11, 15);
            byte[] bArr7 = bArr2[i5];
            m1188G(iArr[bArr7[8]], iArr[bArr7[9]], 0, 5, 10, 15);
            byte[] bArr8 = bArr2[i5];
            m1188G(iArr[bArr8[10]], iArr[bArr8[11]], 1, 6, 11, 12);
            byte[] bArr9 = bArr2[i5];
            m1188G(iArr[bArr9[12]], iArr[bArr9[13]], 2, 7, 8, 13);
            byte[] bArr10 = bArr2[i5];
            m1188G(iArr[bArr10[14]], iArr[bArr10[15]], 3, 4, 9, 14);
        }
        while (true) {
            int[] iArr2 = this.chainValue;
            if (i3 >= iArr2.length) {
                return;
            }
            int i6 = iArr2[i3];
            int[] iArr3 = this.internalState;
            iArr2[i3] = (i6 ^ iArr3[i3]) ^ iArr3[i3 + 8];
            i3++;
        }
    }

    private void init(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.buffer = new byte[64];
        if (bArr3 != null && bArr3.length > 0) {
            if (bArr3.length > 32) {
                throw new IllegalArgumentException("Keys > 32 bytes are not supported");
            }
            byte[] bArr4 = new byte[bArr3.length];
            this.key = bArr4;
            System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
            this.keyLength = bArr3.length;
            System.arraycopy(bArr3, 0, this.buffer, 0, bArr3.length);
            this.bufferPos = 64;
        }
        if (this.chainValue == null) {
            this.chainValue = new int[]{r3[0] ^ ((this.digestLength | (this.keyLength << 8)) | ((this.fanout << 16) | (this.depth << 24))), r3[1] ^ this.leafLength, ((int) r4) ^ r3[2], ((r1 | (this.nodeDepth << 16)) | (this.innerHashLength << 24)) ^ r5, r3[4], r3[5], 0, 0};
            int[] iArr = blake2s_IV;
            long j2 = this.nodeOffset;
            int i2 = (int) (j2 >> 32);
            int i3 = iArr[3];
            if (bArr != null) {
                if (bArr.length != 8) {
                    throw new IllegalArgumentException("Salt length must be exactly 8 bytes");
                }
                byte[] bArr5 = new byte[8];
                this.salt = bArr5;
                System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
                int[] iArr2 = this.chainValue;
                iArr2[4] = iArr2[4] ^ Pack.littleEndianToInt(bArr, 0);
                int[] iArr3 = this.chainValue;
                iArr3[5] = Pack.littleEndianToInt(bArr, 4) ^ iArr3[5];
            }
            int[] iArr4 = this.chainValue;
            iArr4[6] = iArr[6];
            iArr4[7] = iArr[7];
            if (bArr2 != null) {
                if (bArr2.length != 8) {
                    throw new IllegalArgumentException("Personalization length must be exactly 8 bytes");
                }
                byte[] bArr6 = new byte[8];
                this.personalization = bArr6;
                System.arraycopy(bArr2, 0, bArr6, 0, bArr2.length);
                int[] iArr5 = this.chainValue;
                iArr5[6] = iArr5[6] ^ Pack.littleEndianToInt(bArr2, 0);
                int[] iArr6 = this.chainValue;
                iArr6[7] = Pack.littleEndianToInt(bArr2, 4) ^ iArr6[7];
            }
        }
    }

    private void initializeInternalState() {
        int[] iArr = this.chainValue;
        System.arraycopy(iArr, 0, this.internalState, 0, iArr.length);
        int[] iArr2 = blake2s_IV;
        System.arraycopy(iArr2, 0, this.internalState, this.chainValue.length, 4);
        int[] iArr3 = this.internalState;
        iArr3[12] = this.f2334t0 ^ iArr2[4];
        iArr3[13] = this.t1 ^ iArr2[5];
        iArr3[14] = this.f2333f0 ^ iArr2[6];
        iArr3[15] = iArr2[7];
    }

    private int rotr32(int i2, int i3) {
        return (i2 << (32 - i3)) | (i2 >>> i3);
    }

    public void clearKey() {
        byte[] bArr = this.key;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            Arrays.fill(this.buffer, (byte) 0);
        }
    }

    public void clearSalt() {
        byte[] bArr = this.salt;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        int[] iArr;
        int i3;
        this.f2333f0 = -1;
        int i4 = this.f2334t0;
        int i5 = this.bufferPos;
        int i6 = i4 + i5;
        this.f2334t0 = i6;
        if (i6 < 0 && i5 > (-i6)) {
            this.t1++;
        }
        compress(this.buffer, 0);
        Arrays.fill(this.buffer, (byte) 0);
        Arrays.fill(this.internalState, 0);
        int i7 = 0;
        while (true) {
            iArr = this.chainValue;
            if (i7 >= iArr.length || (i3 = i7 * 4) >= this.digestLength) {
                break;
            }
            byte[] intToLittleEndian = Pack.intToLittleEndian(iArr[i7]);
            int i8 = this.digestLength;
            if (i3 < i8 - 4) {
                System.arraycopy(intToLittleEndian, 0, bArr, i3 + i2, 4);
            } else {
                System.arraycopy(intToLittleEndian, 0, bArr, i2 + i3, i8 - i3);
            }
            i7++;
        }
        Arrays.fill(iArr, 0);
        reset();
        return this.digestLength;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "BLAKE2s";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 64;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.digestLength;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.bufferPos = 0;
        this.f2333f0 = 0;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.chainValue = null;
        Arrays.fill(this.buffer, (byte) 0);
        byte[] bArr = this.key;
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.buffer, 0, bArr.length);
            this.bufferPos = 64;
        }
        init(this.salt, this.personalization, this.key);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        int i2 = this.bufferPos;
        if (64 - i2 != 0) {
            this.buffer[i2] = b;
            this.bufferPos = i2 + 1;
            return;
        }
        int i3 = this.f2334t0 + 64;
        this.f2334t0 = i3;
        if (i3 == 0) {
            this.t1++;
        }
        compress(this.buffer, 0);
        Arrays.fill(this.buffer, (byte) 0);
        this.buffer[0] = b;
        this.bufferPos = 1;
    }

    public Blake2sDigest(int i2) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0L;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        if (i2 < 8 || i2 > 256 || i2 % 8 != 0) {
            throw new IllegalArgumentException("BLAKE2s digest bit length must be a multiple of 8 and not greater than 256");
        }
        this.digestLength = i2 / 8;
        init(null, null, null);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        int i4;
        if (bArr == null || i3 == 0) {
            return;
        }
        int i5 = this.bufferPos;
        if (i5 != 0) {
            i4 = 64 - i5;
            if (i4 >= i3) {
                System.arraycopy(bArr, i2, this.buffer, i5, i3);
                this.bufferPos += i3;
            }
            System.arraycopy(bArr, i2, this.buffer, i5, i4);
            int i6 = this.f2334t0 + 64;
            this.f2334t0 = i6;
            if (i6 == 0) {
                this.t1++;
            }
            compress(this.buffer, 0);
            this.bufferPos = 0;
            Arrays.fill(this.buffer, (byte) 0);
        } else {
            i4 = 0;
        }
        int i7 = i3 + i2;
        int i8 = i7 - 64;
        int i9 = i2 + i4;
        while (i9 < i8) {
            int i10 = this.f2334t0 + 64;
            this.f2334t0 = i10;
            if (i10 == 0) {
                this.t1++;
            }
            compress(bArr, i9);
            i9 += 64;
        }
        i3 = i7 - i9;
        System.arraycopy(bArr, i9, this.buffer, 0, i3);
        this.bufferPos += i3;
    }

    public Blake2sDigest(int i2, int i3, long j2) {
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        this.digestLength = i2;
        this.nodeOffset = j2;
        this.fanout = 0;
        this.depth = 0;
        this.leafLength = i3;
        this.innerHashLength = i3;
        this.nodeDepth = 0;
        init(null, null, null);
    }

    public Blake2sDigest(int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, long j2) {
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        this.digestLength = i2;
        this.nodeOffset = j2;
        init(bArr2, bArr3, bArr);
    }

    public Blake2sDigest(Blake2sDigest blake2sDigest) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0L;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        this.bufferPos = blake2sDigest.bufferPos;
        this.buffer = Arrays.clone(blake2sDigest.buffer);
        this.keyLength = blake2sDigest.keyLength;
        this.key = Arrays.clone(blake2sDigest.key);
        this.digestLength = blake2sDigest.digestLength;
        this.internalState = Arrays.clone(this.internalState);
        this.chainValue = Arrays.clone(blake2sDigest.chainValue);
        this.f2334t0 = blake2sDigest.f2334t0;
        this.t1 = blake2sDigest.t1;
        this.f2333f0 = blake2sDigest.f2333f0;
        this.salt = Arrays.clone(blake2sDigest.salt);
        this.personalization = Arrays.clone(blake2sDigest.personalization);
        this.fanout = blake2sDigest.fanout;
        this.depth = blake2sDigest.depth;
        this.leafLength = blake2sDigest.leafLength;
        this.nodeOffset = blake2sDigest.nodeOffset;
        this.nodeDepth = blake2sDigest.nodeDepth;
        this.innerHashLength = blake2sDigest.innerHashLength;
    }

    public Blake2sDigest(byte[] bArr) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0L;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        init(null, null, bArr);
    }

    public Blake2sDigest(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3) {
        this.digestLength = 32;
        this.keyLength = 0;
        this.salt = null;
        this.personalization = null;
        this.key = null;
        this.fanout = 1;
        this.depth = 1;
        this.leafLength = 0;
        this.nodeOffset = 0L;
        this.nodeDepth = 0;
        this.innerHashLength = 0;
        this.buffer = null;
        this.bufferPos = 0;
        this.internalState = new int[16];
        this.chainValue = null;
        this.f2334t0 = 0;
        this.t1 = 0;
        this.f2333f0 = 0;
        if (i2 < 1 || i2 > 32) {
            throw new IllegalArgumentException("Invalid digest length (required: 1 - 32)");
        }
        this.digestLength = i2;
        init(bArr2, bArr3, bArr);
    }
}
