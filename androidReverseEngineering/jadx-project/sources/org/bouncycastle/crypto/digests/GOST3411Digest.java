package org.bouncycastle.crypto.digests;

import android.support.v4.view.MotionEventCompat;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class GOST3411Digest implements ExtendedDigest, Memoable {
    private static final byte[] C2 = {0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1};
    private static final int DIGEST_LENGTH = 32;

    /* renamed from: C */
    private byte[][] f1156C;

    /* renamed from: H */
    private byte[] f1157H;

    /* renamed from: K */
    private byte[] f1158K;

    /* renamed from: L */
    private byte[] f1159L;

    /* renamed from: M */
    private byte[] f1160M;

    /* renamed from: S */
    byte[] f1161S;
    private byte[] Sum;

    /* renamed from: U */
    byte[] f1162U;

    /* renamed from: V */
    byte[] f1163V;

    /* renamed from: W */
    byte[] f1164W;

    /* renamed from: a */
    byte[] f1165a;
    private long byteCount;
    private BlockCipher cipher;
    private byte[] sBox;
    short[] wS;
    short[] w_S;
    private byte[] xBuf;
    private int xBufOff;

    public GOST3411Digest() {
        this.f1157H = new byte[32];
        this.f1159L = new byte[32];
        this.f1160M = new byte[32];
        this.Sum = new byte[32];
        this.f1156C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        this.cipher = new GOST28147Engine();
        this.f1158K = new byte[32];
        this.f1165a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.f1161S = new byte[32];
        this.f1162U = new byte[32];
        this.f1163V = new byte[32];
        this.f1164W = new byte[32];
        byte[] sBox = GOST28147Engine.getSBox("D-A");
        this.sBox = sBox;
        this.cipher.init(true, new ParametersWithSBox(null, sBox));
        reset();
    }

    /* renamed from: A */
    private byte[] m1191A(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            this.f1165a[i2] = (byte) (bArr[i2] ^ bArr[i2 + 8]);
        }
        System.arraycopy(bArr, 8, bArr, 0, 24);
        System.arraycopy(this.f1165a, 0, bArr, 24, 8);
        return bArr;
    }

    /* renamed from: E */
    private void m1192E(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, int i3) {
        this.cipher.init(true, new KeyParameter(bArr));
        this.cipher.processBlock(bArr3, i3, bArr2, i2);
    }

    /* renamed from: P */
    private byte[] m1193P(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            byte[] bArr2 = this.f1158K;
            int i3 = i2 * 4;
            bArr2[i3] = bArr[i2];
            bArr2[i3 + 1] = bArr[i2 + 8];
            bArr2[i3 + 2] = bArr[i2 + 16];
            bArr2[i3 + 3] = bArr[i2 + 24];
        }
        return this.f1158K;
    }

    private void cpyBytesToShort(byte[] bArr, short[] sArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            sArr[i2] = (short) ((bArr[i3] & 255) | ((bArr[i3 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
        }
    }

    private void cpyShortToBytes(short[] sArr, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            short s2 = sArr[i2];
            bArr[i3 + 1] = (byte) (s2 >> 8);
            bArr[i3] = (byte) s2;
        }
    }

    private void finish() {
        Pack.longToLittleEndian(this.byteCount * 8, this.f1159L, 0);
        while (this.xBufOff != 0) {
            update((byte) 0);
        }
        processBlock(this.f1159L, 0);
        processBlock(this.Sum, 0);
    }

    private void fw(byte[] bArr) {
        cpyBytesToShort(bArr, this.wS);
        short[] sArr = this.w_S;
        short[] sArr2 = this.wS;
        sArr[15] = (short) (((((sArr2[0] ^ sArr2[1]) ^ sArr2[2]) ^ sArr2[3]) ^ sArr2[12]) ^ sArr2[15]);
        System.arraycopy(sArr2, 1, sArr, 0, 15);
        cpyShortToBytes(this.w_S, bArr);
    }

    private void sumByteArray(byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.Sum;
            if (i2 == bArr2.length) {
                return;
            }
            int i4 = (bArr2[i2] & 255) + (bArr[i2] & 255) + i3;
            bArr2[i2] = (byte) i4;
            i3 = i4 >>> 8;
            i2++;
        }
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new GOST3411Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        finish();
        byte[] bArr2 = this.f1157H;
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        reset();
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "GOST3411";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 32;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    public void processBlock(byte[] bArr, int i2) {
        System.arraycopy(bArr, i2, this.f1160M, 0, 32);
        System.arraycopy(this.f1157H, 0, this.f1162U, 0, 32);
        System.arraycopy(this.f1160M, 0, this.f1163V, 0, 32);
        for (int i3 = 0; i3 < 32; i3++) {
            this.f1164W[i3] = (byte) (this.f1162U[i3] ^ this.f1163V[i3]);
        }
        m1192E(m1193P(this.f1164W), this.f1161S, 0, this.f1157H, 0);
        for (int i4 = 1; i4 < 4; i4++) {
            byte[] m1191A = m1191A(this.f1162U);
            for (int i5 = 0; i5 < 32; i5++) {
                this.f1162U[i5] = (byte) (m1191A[i5] ^ this.f1156C[i4][i5]);
            }
            this.f1163V = m1191A(m1191A(this.f1163V));
            for (int i6 = 0; i6 < 32; i6++) {
                this.f1164W[i6] = (byte) (this.f1162U[i6] ^ this.f1163V[i6]);
            }
            int i7 = i4 * 8;
            m1192E(m1193P(this.f1164W), this.f1161S, i7, this.f1157H, i7);
        }
        for (int i8 = 0; i8 < 12; i8++) {
            fw(this.f1161S);
        }
        for (int i9 = 0; i9 < 32; i9++) {
            byte[] bArr2 = this.f1161S;
            bArr2[i9] = (byte) (bArr2[i9] ^ this.f1160M[i9]);
        }
        fw(this.f1161S);
        for (int i10 = 0; i10 < 32; i10++) {
            byte[] bArr3 = this.f1161S;
            bArr3[i10] = (byte) (this.f1157H[i10] ^ bArr3[i10]);
        }
        for (int i11 = 0; i11 < 61; i11++) {
            fw(this.f1161S);
        }
        byte[] bArr4 = this.f1161S;
        byte[] bArr5 = this.f1157H;
        System.arraycopy(bArr4, 0, bArr5, 0, bArr5.length);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.f1157H;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.f1159L;
            if (i3 >= bArr2.length) {
                break;
            }
            bArr2[i3] = 0;
            i3++;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.f1160M;
            if (i4 >= bArr3.length) {
                break;
            }
            bArr3[i4] = 0;
            i4++;
        }
        int i5 = 0;
        while (true) {
            byte[] bArr4 = this.f1156C[1];
            if (i5 >= bArr4.length) {
                break;
            }
            bArr4[i5] = 0;
            i5++;
        }
        int i6 = 0;
        while (true) {
            byte[] bArr5 = this.f1156C[3];
            if (i6 >= bArr5.length) {
                break;
            }
            bArr5[i6] = 0;
            i6++;
        }
        int i7 = 0;
        while (true) {
            byte[] bArr6 = this.Sum;
            if (i7 >= bArr6.length) {
                break;
            }
            bArr6[i7] = 0;
            i7++;
        }
        int i8 = 0;
        while (true) {
            byte[] bArr7 = this.xBuf;
            if (i8 >= bArr7.length) {
                byte[] bArr8 = C2;
                System.arraycopy(bArr8, 0, this.f1156C[2], 0, bArr8.length);
                return;
            } else {
                bArr7[i8] = 0;
                i8++;
            }
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
            sumByteArray(bArr);
            processBlock(this.xBuf, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    public GOST3411Digest(GOST3411Digest gOST3411Digest) {
        this.f1157H = new byte[32];
        this.f1159L = new byte[32];
        this.f1160M = new byte[32];
        this.Sum = new byte[32];
        this.f1156C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        this.cipher = new GOST28147Engine();
        this.f1158K = new byte[32];
        this.f1165a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.f1161S = new byte[32];
        this.f1162U = new byte[32];
        this.f1163V = new byte[32];
        this.f1164W = new byte[32];
        reset(gOST3411Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        GOST3411Digest gOST3411Digest = (GOST3411Digest) memoable;
        byte[] bArr = gOST3411Digest.sBox;
        this.sBox = bArr;
        this.cipher.init(true, new ParametersWithSBox(null, bArr));
        reset();
        byte[] bArr2 = gOST3411Digest.f1157H;
        System.arraycopy(bArr2, 0, this.f1157H, 0, bArr2.length);
        byte[] bArr3 = gOST3411Digest.f1159L;
        System.arraycopy(bArr3, 0, this.f1159L, 0, bArr3.length);
        byte[] bArr4 = gOST3411Digest.f1160M;
        System.arraycopy(bArr4, 0, this.f1160M, 0, bArr4.length);
        byte[] bArr5 = gOST3411Digest.Sum;
        System.arraycopy(bArr5, 0, this.Sum, 0, bArr5.length);
        byte[] bArr6 = gOST3411Digest.f1156C[1];
        System.arraycopy(bArr6, 0, this.f1156C[1], 0, bArr6.length);
        byte[] bArr7 = gOST3411Digest.f1156C[2];
        System.arraycopy(bArr7, 0, this.f1156C[2], 0, bArr7.length);
        byte[] bArr8 = gOST3411Digest.f1156C[3];
        System.arraycopy(bArr8, 0, this.f1156C[3], 0, bArr8.length);
        byte[] bArr9 = gOST3411Digest.xBuf;
        System.arraycopy(bArr9, 0, this.xBuf, 0, bArr9.length);
        this.xBufOff = gOST3411Digest.xBufOff;
        this.byteCount = gOST3411Digest.byteCount;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.xBufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (true) {
            byte[] bArr2 = this.xBuf;
            if (i3 <= bArr2.length) {
                break;
            }
            System.arraycopy(bArr, i2, bArr2, 0, bArr2.length);
            sumByteArray(this.xBuf);
            processBlock(this.xBuf, 0);
            byte[] bArr3 = this.xBuf;
            i2 += bArr3.length;
            i3 -= bArr3.length;
            this.byteCount += bArr3.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }

    public GOST3411Digest(byte[] bArr) {
        this.f1157H = new byte[32];
        this.f1159L = new byte[32];
        this.f1160M = new byte[32];
        this.Sum = new byte[32];
        this.f1156C = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 4, 32);
        this.xBuf = new byte[32];
        this.cipher = new GOST28147Engine();
        this.f1158K = new byte[32];
        this.f1165a = new byte[8];
        this.wS = new short[16];
        this.w_S = new short[16];
        this.f1161S = new byte[32];
        this.f1162U = new byte[32];
        this.f1163V = new byte[32];
        this.f1164W = new byte[32];
        byte[] clone = Arrays.clone(bArr);
        this.sBox = clone;
        this.cipher.init(true, new ParametersWithSBox(null, clone));
        reset();
    }
}
