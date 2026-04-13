package org.bouncycastle.crypto.macs;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class Poly1305 implements Mac {
    private static final int BLOCK_SIZE = 16;
    private final BlockCipher cipher;
    private final byte[] currentBlock;
    private int currentBlockOffset;

    /* renamed from: h0, reason: collision with root package name */
    private int f2339h0;
    private int h1;
    private int h2;
    private int h3;
    private int h4;

    /* renamed from: k0, reason: collision with root package name */
    private int f2340k0;

    /* renamed from: k1, reason: collision with root package name */
    private int f2341k1;
    private int k2;
    private int k3;

    /* renamed from: r0, reason: collision with root package name */
    private int f2342r0;
    private int r1;
    private int r2;
    private int r3;
    private int r4;
    private int s1;
    private int s2;
    private int s3;
    private int s4;
    private final byte[] singleByte;

    public Poly1305() {
        this.singleByte = new byte[1];
        this.currentBlock = new byte[16];
        this.currentBlockOffset = 0;
        this.cipher = null;
    }

    private static final long mul32x32_64(int i2, int i3) {
        return (i2 & BodyPartID.bodyIdMax) * i3;
    }

    private void processBlock() {
        int i2 = this.currentBlockOffset;
        if (i2 < 16) {
            this.currentBlock[i2] = 1;
            for (int i3 = i2 + 1; i3 < 16; i3++) {
                this.currentBlock[i3] = 0;
            }
        }
        long littleEndianToInt = Pack.littleEndianToInt(this.currentBlock, 0) & BodyPartID.bodyIdMax;
        long littleEndianToInt2 = Pack.littleEndianToInt(this.currentBlock, 4) & BodyPartID.bodyIdMax;
        long littleEndianToInt3 = Pack.littleEndianToInt(this.currentBlock, 8) & BodyPartID.bodyIdMax;
        long littleEndianToInt4 = BodyPartID.bodyIdMax & Pack.littleEndianToInt(this.currentBlock, 12);
        int i4 = (int) (this.f2339h0 + (littleEndianToInt & 67108863));
        this.f2339h0 = i4;
        this.h1 = (int) (this.h1 + ((((littleEndianToInt2 << 32) | littleEndianToInt) >>> 26) & 67108863));
        this.h2 = (int) (this.h2 + (((littleEndianToInt2 | (littleEndianToInt3 << 32)) >>> 20) & 67108863));
        this.h3 = (int) (this.h3 + ((((littleEndianToInt4 << 32) | littleEndianToInt3) >>> 14) & 67108863));
        int i5 = (int) (this.h4 + (littleEndianToInt4 >>> 8));
        this.h4 = i5;
        if (this.currentBlockOffset == 16) {
            this.h4 = i5 + 16777216;
        }
        long mul32x32_64 = mul32x32_64(i4, this.f2342r0) + mul32x32_64(this.h1, this.s4) + mul32x32_64(this.h2, this.s3) + mul32x32_64(this.h3, this.s2) + mul32x32_64(this.h4, this.s1);
        long mul32x32_642 = mul32x32_64(this.f2339h0, this.r1) + mul32x32_64(this.h1, this.f2342r0) + mul32x32_64(this.h2, this.s4) + mul32x32_64(this.h3, this.s3) + mul32x32_64(this.h4, this.s2);
        long mul32x32_643 = mul32x32_64(this.f2339h0, this.r2) + mul32x32_64(this.h1, this.r1) + mul32x32_64(this.h2, this.f2342r0) + mul32x32_64(this.h3, this.s4) + mul32x32_64(this.h4, this.s3);
        long mul32x32_644 = mul32x32_64(this.f2339h0, this.r3) + mul32x32_64(this.h1, this.r2) + mul32x32_64(this.h2, this.r1) + mul32x32_64(this.h3, this.f2342r0) + mul32x32_64(this.h4, this.s4);
        long mul32x32_645 = mul32x32_64(this.f2339h0, this.r4) + mul32x32_64(this.h1, this.r3) + mul32x32_64(this.h2, this.r2) + mul32x32_64(this.h3, this.r1) + mul32x32_64(this.h4, this.f2342r0);
        long j2 = mul32x32_642 + (mul32x32_64 >>> 26);
        long j3 = mul32x32_643 + (j2 >>> 26);
        this.h2 = ((int) j3) & 67108863;
        long j4 = mul32x32_644 + (j3 >>> 26);
        this.h3 = ((int) j4) & 67108863;
        long j5 = mul32x32_645 + (j4 >>> 26);
        this.h4 = ((int) j5) & 67108863;
        int i6 = (((int) (j5 >>> 26)) * 5) + (((int) mul32x32_64) & 67108863);
        this.h1 = (((int) j2) & 67108863) + (i6 >>> 26);
        this.f2339h0 = i6 & 67108863;
    }

    private void setKey(byte[] bArr, byte[] bArr2) {
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
        }
        int i2 = 16;
        if (this.cipher != null && (bArr2 == null || bArr2.length != 16)) {
            throw new IllegalArgumentException("Poly1305 requires a 128 bit IV.");
        }
        int littleEndianToInt = Pack.littleEndianToInt(bArr, 0);
        int littleEndianToInt2 = Pack.littleEndianToInt(bArr, 4);
        int littleEndianToInt3 = Pack.littleEndianToInt(bArr, 8);
        int littleEndianToInt4 = Pack.littleEndianToInt(bArr, 12);
        this.f2342r0 = 67108863 & littleEndianToInt;
        int i3 = ((littleEndianToInt >>> 26) | (littleEndianToInt2 << 6)) & 67108611;
        this.r1 = i3;
        int i4 = ((littleEndianToInt2 >>> 20) | (littleEndianToInt3 << 12)) & 67092735;
        this.r2 = i4;
        int i5 = ((littleEndianToInt3 >>> 14) | (littleEndianToInt4 << 18)) & 66076671;
        this.r3 = i5;
        int i6 = (littleEndianToInt4 >>> 8) & 1048575;
        this.r4 = i6;
        this.s1 = i3 * 5;
        this.s2 = i4 * 5;
        this.s3 = i5 * 5;
        this.s4 = i6 * 5;
        BlockCipher blockCipher = this.cipher;
        if (blockCipher != null) {
            byte[] bArr3 = new byte[16];
            blockCipher.init(true, new KeyParameter(bArr, 16, 16));
            this.cipher.processBlock(bArr2, 0, bArr3, 0);
            i2 = 0;
            bArr = bArr3;
        }
        this.f2340k0 = Pack.littleEndianToInt(bArr, i2 + 0);
        this.f2341k1 = Pack.littleEndianToInt(bArr, i2 + 4);
        this.k2 = Pack.littleEndianToInt(bArr, i2 + 8);
        this.k3 = Pack.littleEndianToInt(bArr, i2 + 12);
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        if (i2 + 16 > bArr.length) {
            throw new OutputLengthException("Output buffer is too short.");
        }
        if (this.currentBlockOffset > 0) {
            processBlock();
        }
        int i3 = this.h1;
        int i4 = this.f2339h0;
        int i5 = i3 + (i4 >>> 26);
        int i6 = this.h2 + (i5 >>> 26);
        int i7 = this.h3 + (i6 >>> 26);
        int i8 = i6 & 67108863;
        int i9 = this.h4 + (i7 >>> 26);
        int i10 = i7 & 67108863;
        int i11 = ((i9 >>> 26) * 5) + (i4 & 67108863);
        int i12 = i9 & 67108863;
        int i13 = (i5 & 67108863) + (i11 >>> 26);
        int i14 = i11 & 67108863;
        int i15 = i14 + 5;
        int i16 = (i15 >>> 26) + i13;
        int i17 = (i16 >>> 26) + i8;
        int i18 = (i17 >>> 26) + i10;
        int i19 = 67108863 & i18;
        int i20 = ((i18 >>> 26) + i12) - 67108864;
        int i21 = (i20 >>> 31) - 1;
        int i22 = ~i21;
        this.f2339h0 = (i14 & i22) | (i15 & 67108863 & i21);
        this.h1 = (i13 & i22) | (i16 & 67108863 & i21);
        this.h2 = (i8 & i22) | (i17 & 67108863 & i21);
        this.h3 = (i19 & i21) | (i10 & i22);
        this.h4 = (i12 & i22) | (i20 & i21);
        long j2 = (((r3 << 26) | r8) & BodyPartID.bodyIdMax) + (this.f2340k0 & BodyPartID.bodyIdMax);
        long j3 = (((r3 >>> 6) | (r6 << 20)) & BodyPartID.bodyIdMax) + (this.f2341k1 & BodyPartID.bodyIdMax);
        long j4 = (((r6 >>> 12) | (r5 << 14)) & BodyPartID.bodyIdMax) + (this.k2 & BodyPartID.bodyIdMax);
        long j5 = (((r5 >>> 18) | (r4 << 8)) & BodyPartID.bodyIdMax) + (this.k3 & BodyPartID.bodyIdMax);
        Pack.intToLittleEndian((int) j2, bArr, i2);
        long j6 = j3 + (j2 >>> 32);
        Pack.intToLittleEndian((int) j6, bArr, i2 + 4);
        long j7 = j4 + (j6 >>> 32);
        Pack.intToLittleEndian((int) j7, bArr, i2 + 8);
        Pack.intToLittleEndian((int) (j5 + (j7 >>> 32)), bArr, i2 + 12);
        reset();
        return 16;
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        if (this.cipher == null) {
            return "Poly1305";
        }
        return "Poly1305-" + this.cipher.getAlgorithmName();
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        byte[] bArr;
        if (this.cipher == null) {
            bArr = null;
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("Poly1305 requires an IV when used with a block cipher.");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            cipherParameters = parametersWithIV.getParameters();
        }
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("Poly1305 requires a key.");
        }
        setKey(((KeyParameter) cipherParameters).getKey(), bArr);
        reset();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        this.currentBlockOffset = 0;
        this.h4 = 0;
        this.h3 = 0;
        this.h2 = 0;
        this.h1 = 0;
        this.f2339h0 = 0;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b) {
        byte[] bArr = this.singleByte;
        bArr[0] = b;
        update(bArr, 0, 1);
    }

    public Poly1305(BlockCipher blockCipher) {
        this.singleByte = new byte[1];
        this.currentBlock = new byte[16];
        this.currentBlockOffset = 0;
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("Poly1305 requires a 128 bit block cipher.");
        }
        this.cipher = blockCipher;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        while (i3 > i4) {
            if (this.currentBlockOffset == 16) {
                processBlock();
                this.currentBlockOffset = 0;
            }
            int min = Math.min(i3 - i4, 16 - this.currentBlockOffset);
            System.arraycopy(bArr, i4 + i2, this.currentBlock, this.currentBlockOffset, min);
            i4 += min;
            this.currentBlockOffset += min;
        }
    }
}
