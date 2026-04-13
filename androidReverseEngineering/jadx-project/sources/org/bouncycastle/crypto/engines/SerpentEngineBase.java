package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes.dex */
public abstract class SerpentEngineBase implements BlockCipher {
    protected static final int BLOCK_SIZE = 16;
    static final int PHI = -1640531527;
    static final int ROUNDS = 32;
    protected int X0;
    protected int X1;
    protected int X2;
    protected int X3;
    protected boolean encrypting;
    protected int[] wKey;

    public static int rotateLeft(int i2, int i3) {
        return (i2 >>> (-i3)) | (i2 << i3);
    }

    public static int rotateRight(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    public final void LT() {
        int rotateLeft = rotateLeft(this.X0, 13);
        int rotateLeft2 = rotateLeft(this.X2, 3);
        int i2 = (this.X1 ^ rotateLeft) ^ rotateLeft2;
        int i3 = (this.X3 ^ rotateLeft2) ^ (rotateLeft << 3);
        this.X1 = rotateLeft(i2, 1);
        int rotateLeft3 = rotateLeft(i3, 7);
        this.X3 = rotateLeft3;
        this.X0 = rotateLeft((rotateLeft ^ this.X1) ^ rotateLeft3, 5);
        this.X2 = rotateLeft((this.X3 ^ rotateLeft2) ^ (this.X1 << 7), 22);
    }

    public abstract void decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3);

    public abstract void encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3);

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Serpent";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    public final void ib0(int i2, int i3, int i4, int i5) {
        int i6 = ~i2;
        int i7 = i3 ^ i2;
        int i8 = (i6 | i7) ^ i5;
        int i9 = i4 ^ i8;
        int i10 = i7 ^ i9;
        this.X2 = i10;
        int i11 = (i7 & i5) ^ i6;
        int i12 = (i10 & i11) ^ i8;
        this.X1 = i12;
        int i13 = (i2 & i8) ^ (i12 | i9);
        this.X3 = i13;
        this.X0 = i13 ^ (i11 ^ i9);
    }

    public final void ib1(int i2, int i3, int i4, int i5) {
        int i6 = i5 ^ i3;
        int i7 = i2 ^ (i3 & i6);
        int i8 = i6 ^ i7;
        int i9 = i4 ^ i8;
        this.X3 = i9;
        int i10 = i3 ^ (i6 & i7);
        int i11 = i7 ^ (i9 | i10);
        this.X1 = i11;
        int i12 = ~i11;
        int i13 = i10 ^ i9;
        this.X0 = i12 ^ i13;
        this.X2 = (i12 | i13) ^ i8;
    }

    public final void ib2(int i2, int i3, int i4, int i5) {
        int i6 = i3 ^ i5;
        int i7 = ~i6;
        int i8 = i2 ^ i4;
        int i9 = i4 ^ i6;
        int i10 = (i3 & i9) ^ i8;
        this.X0 = i10;
        int i11 = (((i2 | i7) ^ i5) | i8) ^ i6;
        this.X3 = i11;
        int i12 = ~i9;
        int i13 = i11 | i10;
        this.X1 = i12 ^ i13;
        this.X2 = (i13 ^ i8) ^ (i5 & i12);
    }

    public final void ib3(int i2, int i3, int i4, int i5) {
        int i6 = i2 | i3;
        int i7 = i3 ^ i4;
        int i8 = i2 ^ (i3 & i7);
        int i9 = i4 ^ i8;
        int i10 = i5 | i8;
        int i11 = i7 ^ i10;
        this.X0 = i11;
        int i12 = (i10 | i7) ^ i5;
        this.X2 = i9 ^ i12;
        int i13 = i6 ^ i12;
        int i14 = i8 ^ (i11 & i13);
        this.X3 = i14;
        this.X1 = i14 ^ (i13 ^ i11);
    }

    public final void ib4(int i2, int i3, int i4, int i5) {
        int i6 = i3 ^ ((i4 | i5) & i2);
        int i7 = i4 ^ (i2 & i6);
        int i8 = i5 ^ i7;
        this.X1 = i8;
        int i9 = ~i2;
        int i10 = (i7 & i8) ^ i6;
        this.X3 = i10;
        int i11 = i5 ^ (i8 | i9);
        this.X0 = i10 ^ i11;
        this.X2 = (i9 ^ i8) ^ (i6 & i11);
    }

    public final void ib5(int i2, int i3, int i4, int i5) {
        int i6 = ~i4;
        int i7 = (i3 & i6) ^ i5;
        int i8 = i2 & i7;
        int i9 = (i3 ^ i6) ^ i8;
        this.X3 = i9;
        int i10 = i9 | i3;
        this.X1 = i7 ^ (i2 & i10);
        int i11 = i5 | i2;
        this.X0 = (i6 ^ i10) ^ i11;
        this.X2 = ((i2 ^ i4) | i8) ^ (i3 & i11);
    }

    public final void ib6(int i2, int i3, int i4, int i5) {
        int i6 = ~i2;
        int i7 = i2 ^ i3;
        int i8 = i4 ^ i7;
        int i9 = (i4 | i6) ^ i5;
        this.X1 = i8 ^ i9;
        int i10 = i7 ^ (i8 & i9);
        int i11 = i9 ^ (i3 | i10);
        this.X3 = i11;
        int i12 = i3 | i11;
        this.X0 = i10 ^ i12;
        this.X2 = (i5 & i6) ^ (i12 ^ i8);
    }

    public final void ib7(int i2, int i3, int i4, int i5) {
        int i6 = (i2 & i3) | i4;
        int i7 = (i2 | i3) & i5;
        int i8 = i6 ^ i7;
        this.X3 = i8;
        int i9 = i3 ^ i7;
        int i10 = ((i8 ^ (~i5)) | i9) ^ i2;
        this.X1 = i10;
        int i11 = (i9 ^ i4) ^ (i5 | i10);
        this.X0 = i11;
        this.X2 = ((i2 & i8) ^ i11) ^ (i6 ^ i10);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.encrypting = z2;
            this.wKey = makeWorkingKey(((KeyParameter) cipherParameters).getKey());
        } else {
            throw new IllegalArgumentException("invalid parameter passed to " + getAlgorithmName() + " init - " + cipherParameters.getClass().getName());
        }
    }

    public final void inverseLT() {
        int rotateRight = (rotateRight(this.X2, 22) ^ this.X3) ^ (this.X1 << 7);
        int rotateRight2 = rotateRight(this.X0, 5) ^ this.X1;
        int i2 = this.X3;
        int i3 = rotateRight2 ^ i2;
        int rotateRight3 = rotateRight(i2, 7);
        int rotateRight4 = rotateRight(this.X1, 1);
        this.X3 = (rotateRight3 ^ rotateRight) ^ (i3 << 3);
        this.X1 = (rotateRight4 ^ i3) ^ rotateRight;
        this.X2 = rotateRight(rotateRight, 3);
        this.X0 = rotateRight(i3, 13);
    }

    public abstract int[] makeWorkingKey(byte[] bArr);

    @Override // org.bouncycastle.crypto.BlockCipher
    public final int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.wKey == null) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i2, bArr2, i3);
            return 16;
        }
        decryptBlock(bArr, i2, bArr2, i3);
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }

    public final void sb0(int i2, int i3, int i4, int i5) {
        int i6 = i2 ^ i5;
        int i7 = i4 ^ i6;
        int i8 = i3 ^ i7;
        int i9 = (i5 & i2) ^ i8;
        this.X3 = i9;
        int i10 = i2 ^ (i3 & i6);
        this.X2 = (i4 | i10) ^ i8;
        int i11 = (i7 ^ i10) & i9;
        this.X1 = (~i7) ^ i11;
        this.X0 = (~i10) ^ i11;
    }

    public final void sb1(int i2, int i3, int i4, int i5) {
        int i6 = (~i2) ^ i3;
        int i7 = (i2 | i6) ^ i4;
        int i8 = i5 ^ i7;
        this.X2 = i8;
        int i9 = i3 ^ (i5 | i6);
        int i10 = i8 ^ i6;
        int i11 = (i7 & i9) ^ i10;
        this.X3 = i11;
        int i12 = i9 ^ i7;
        this.X1 = i11 ^ i12;
        this.X0 = i7 ^ (i12 & i10);
    }

    public final void sb2(int i2, int i3, int i4, int i5) {
        int i6 = ~i2;
        int i7 = i3 ^ i5;
        int i8 = (i4 & i6) ^ i7;
        this.X0 = i8;
        int i9 = i4 ^ i6;
        int i10 = i3 & (i4 ^ i8);
        int i11 = i9 ^ i10;
        this.X3 = i11;
        int i12 = i2 ^ ((i10 | i5) & (i8 | i9));
        this.X2 = i12;
        this.X1 = (i12 ^ (i5 | i6)) ^ (i7 ^ i11);
    }

    public final void sb3(int i2, int i3, int i4, int i5) {
        int i6 = i2 ^ i3;
        int i7 = i2 & i4;
        int i8 = i2 | i5;
        int i9 = i4 ^ i5;
        int i10 = i7 | (i6 & i8);
        int i11 = i9 ^ i10;
        this.X2 = i11;
        int i12 = (i8 ^ i3) ^ i10;
        int i13 = i6 ^ (i9 & i12);
        this.X0 = i13;
        int i14 = i13 & i11;
        this.X1 = i12 ^ i14;
        this.X3 = (i3 | i5) ^ (i9 ^ i14);
    }

    public final void sb4(int i2, int i3, int i4, int i5) {
        int i6 = i2 ^ i5;
        int i7 = i4 ^ (i5 & i6);
        int i8 = i3 | i7;
        this.X3 = i6 ^ i8;
        int i9 = ~i3;
        int i10 = (i6 | i9) ^ i7;
        this.X0 = i10;
        int i11 = i9 ^ i6;
        int i12 = (i8 & i11) ^ (i10 & i2);
        this.X2 = i12;
        this.X1 = (i2 ^ i7) ^ (i11 & i12);
    }

    public final void sb5(int i2, int i3, int i4, int i5) {
        int i6 = ~i2;
        int i7 = i2 ^ i3;
        int i8 = i2 ^ i5;
        int i9 = (i4 ^ i6) ^ (i7 | i8);
        this.X0 = i9;
        int i10 = i5 & i9;
        int i11 = (i7 ^ i9) ^ i10;
        this.X1 = i11;
        int i12 = i8 ^ (i9 | i6);
        this.X2 = (i7 | i10) ^ i12;
        this.X3 = (i12 & i11) ^ (i3 ^ i10);
    }

    public final void sb6(int i2, int i3, int i4, int i5) {
        int i6 = ~i2;
        int i7 = i2 ^ i5;
        int i8 = i3 ^ i7;
        int i9 = i4 ^ (i6 | i7);
        int i10 = i3 ^ i9;
        this.X1 = i10;
        int i11 = (i7 | i10) ^ i5;
        int i12 = (i9 & i11) ^ i8;
        this.X2 = i12;
        int i13 = i11 ^ i9;
        this.X0 = i12 ^ i13;
        this.X3 = (i13 & i8) ^ (~i9);
    }

    public final void sb7(int i2, int i3, int i4, int i5) {
        int i6 = i3 ^ i4;
        int i7 = (i4 & i6) ^ i5;
        int i8 = i2 ^ i7;
        int i9 = i3 ^ ((i5 | i6) & i8);
        this.X1 = i9;
        int i10 = (i2 & i8) ^ i6;
        this.X3 = i10;
        int i11 = (i9 | i7) ^ i8;
        int i12 = i7 ^ (i10 & i11);
        this.X2 = i12;
        this.X0 = (i10 & i12) ^ (~i11);
    }
}
