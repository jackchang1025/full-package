package org.bouncycastle.crypto.engines;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class ChaChaEngine extends Salsa20Engine {
    public ChaChaEngine() {
    }

    public ChaChaEngine(int i2) {
        super(i2);
    }

    public static void chachaCore(int i2, int[] iArr, int[] iArr2) {
        int i3 = 16;
        if (iArr.length != 16) {
            throw new IllegalArgumentException();
        }
        if (iArr2.length != 16) {
            throw new IllegalArgumentException();
        }
        if (i2 % 2 != 0) {
            throw new IllegalArgumentException("Number of rounds must be even");
        }
        char c = 0;
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = iArr[2];
        int i7 = iArr[3];
        int i8 = iArr[4];
        int i9 = iArr[5];
        int i10 = iArr[6];
        int i11 = 7;
        int i12 = iArr[7];
        int i13 = 8;
        int i14 = iArr[8];
        int i15 = iArr[9];
        int i16 = iArr[10];
        int i17 = iArr[11];
        int i18 = 12;
        int i19 = iArr[12];
        int i20 = iArr[13];
        int i21 = iArr[14];
        int i22 = iArr[15];
        int i23 = i21;
        int i24 = i20;
        int i25 = i19;
        int i26 = i17;
        int i27 = i16;
        int i28 = i15;
        int i29 = i14;
        int i30 = i12;
        int i31 = i10;
        int i32 = i9;
        int i33 = i8;
        int i34 = i7;
        int i35 = i6;
        int i36 = i5;
        int i37 = i4;
        int i38 = i2;
        while (i38 > 0) {
            int i39 = i37 + i33;
            int rotateLeft = Integers.rotateLeft(i25 ^ i39, i3);
            int i40 = i29 + rotateLeft;
            int rotateLeft2 = Integers.rotateLeft(i33 ^ i40, i18);
            int i41 = i39 + rotateLeft2;
            int rotateLeft3 = Integers.rotateLeft(rotateLeft ^ i41, i13);
            int i42 = i40 + rotateLeft3;
            int rotateLeft4 = Integers.rotateLeft(rotateLeft2 ^ i42, i11);
            int i43 = i36 + i32;
            int rotateLeft5 = Integers.rotateLeft(i24 ^ i43, i3);
            int i44 = i28 + rotateLeft5;
            int rotateLeft6 = Integers.rotateLeft(i32 ^ i44, i18);
            int i45 = i43 + rotateLeft6;
            int rotateLeft7 = Integers.rotateLeft(rotateLeft5 ^ i45, i13);
            int i46 = i44 + rotateLeft7;
            int rotateLeft8 = Integers.rotateLeft(rotateLeft6 ^ i46, i11);
            int i47 = i35 + i31;
            int rotateLeft9 = Integers.rotateLeft(i23 ^ i47, i3);
            int i48 = i27 + rotateLeft9;
            int rotateLeft10 = Integers.rotateLeft(i31 ^ i48, i18);
            int i49 = i47 + rotateLeft10;
            int rotateLeft11 = Integers.rotateLeft(rotateLeft9 ^ i49, i13);
            int i50 = i48 + rotateLeft11;
            int rotateLeft12 = Integers.rotateLeft(rotateLeft10 ^ i50, i11);
            int i51 = i34 + i30;
            int rotateLeft13 = Integers.rotateLeft(i22 ^ i51, 16);
            int i52 = i26 + rotateLeft13;
            int rotateLeft14 = Integers.rotateLeft(i30 ^ i52, i18);
            int i53 = i51 + rotateLeft14;
            int rotateLeft15 = Integers.rotateLeft(rotateLeft13 ^ i53, 8);
            int i54 = i52 + rotateLeft15;
            int rotateLeft16 = Integers.rotateLeft(rotateLeft14 ^ i54, 7);
            int i55 = i41 + rotateLeft8;
            int rotateLeft17 = Integers.rotateLeft(rotateLeft15 ^ i55, 16);
            int i56 = i50 + rotateLeft17;
            int rotateLeft18 = Integers.rotateLeft(rotateLeft8 ^ i56, 12);
            i37 = i55 + rotateLeft18;
            i22 = Integers.rotateLeft(rotateLeft17 ^ i37, 8);
            i27 = i56 + i22;
            i32 = Integers.rotateLeft(rotateLeft18 ^ i27, 7);
            int i57 = i45 + rotateLeft12;
            int rotateLeft19 = Integers.rotateLeft(rotateLeft3 ^ i57, 16);
            int i58 = i54 + rotateLeft19;
            int rotateLeft20 = Integers.rotateLeft(rotateLeft12 ^ i58, 12);
            i36 = i57 + rotateLeft20;
            i25 = Integers.rotateLeft(rotateLeft19 ^ i36, 8);
            i26 = i58 + i25;
            i31 = Integers.rotateLeft(rotateLeft20 ^ i26, 7);
            int i59 = i49 + rotateLeft16;
            int rotateLeft21 = Integers.rotateLeft(rotateLeft7 ^ i59, 16);
            int i60 = i42 + rotateLeft21;
            int rotateLeft22 = Integers.rotateLeft(rotateLeft16 ^ i60, 12);
            i35 = i59 + rotateLeft22;
            i24 = Integers.rotateLeft(rotateLeft21 ^ i35, 8);
            i29 = i60 + i24;
            i30 = Integers.rotateLeft(rotateLeft22 ^ i29, 7);
            int i61 = i53 + rotateLeft4;
            i3 = 16;
            int rotateLeft23 = Integers.rotateLeft(rotateLeft11 ^ i61, 16);
            int i62 = i46 + rotateLeft23;
            int rotateLeft24 = Integers.rotateLeft(rotateLeft4 ^ i62, 12);
            i34 = i61 + rotateLeft24;
            i23 = Integers.rotateLeft(rotateLeft23 ^ i34, 8);
            i28 = i62 + i23;
            i33 = Integers.rotateLeft(rotateLeft24 ^ i28, 7);
            i38 -= 2;
            c = 0;
            i18 = 12;
            i13 = 8;
            i11 = 7;
        }
        iArr2[c] = i37 + iArr[c];
        iArr2[1] = i36 + iArr[1];
        iArr2[2] = i35 + iArr[2];
        iArr2[3] = i34 + iArr[3];
        iArr2[4] = i33 + iArr[4];
        iArr2[5] = i32 + iArr[5];
        iArr2[6] = i31 + iArr[6];
        iArr2[7] = i30 + iArr[7];
        iArr2[8] = i29 + iArr[8];
        iArr2[9] = i28 + iArr[9];
        iArr2[10] = i27 + iArr[10];
        iArr2[11] = i26 + iArr[11];
        iArr2[12] = i25 + iArr[12];
        iArr2[13] = i24 + iArr[13];
        iArr2[14] = i23 + iArr[14];
        iArr2[15] = i22 + iArr[15];
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void advanceCounter() {
        int[] iArr = this.engineState;
        int i2 = iArr[12] + 1;
        iArr[12] = i2;
        if (i2 == 0) {
            iArr[13] = iArr[13] + 1;
        }
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void generateKeyStream(byte[] bArr) {
        chachaCore(this.rounds, this.engineState, this.f1225x);
        Pack.intToLittleEndian(this.f1225x, bArr, 0);
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine, org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "ChaCha" + this.rounds;
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public long getCounter() {
        int[] iArr = this.engineState;
        return (iArr[13] << 32) | (iArr[12] & BodyPartID.bodyIdMax);
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void resetCounter() {
        int[] iArr = this.engineState;
        iArr[13] = 0;
        iArr[12] = 0;
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void retreatCounter() {
        int[] iArr = this.engineState;
        int i2 = iArr[12];
        if (i2 == 0 && iArr[13] == 0) {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
        int i3 = i2 - 1;
        iArr[12] = i3;
        if (i3 == -1) {
            iArr[13] = iArr[13] - 1;
        }
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void setKey(byte[] bArr, byte[] bArr2) {
        if (bArr != null) {
            if (bArr.length != 16 && bArr.length != 32) {
                throw new IllegalArgumentException(getAlgorithmName() + " requires 128 bit or 256 bit key");
            }
            packTauOrSigma(bArr.length, this.engineState, 0);
            Pack.littleEndianToInt(bArr, 0, this.engineState, 4, 4);
            Pack.littleEndianToInt(bArr, bArr.length - 16, this.engineState, 8, 4);
        }
        Pack.littleEndianToInt(bArr2, 0, this.engineState, 14, 2);
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void advanceCounter(long j2) {
        int i2 = (int) (j2 >>> 32);
        int i3 = (int) j2;
        if (i2 > 0) {
            int[] iArr = this.engineState;
            iArr[13] = iArr[13] + i2;
        }
        int[] iArr2 = this.engineState;
        int i4 = iArr2[12];
        int i5 = i3 + i4;
        iArr2[12] = i5;
        if (i4 == 0 || i5 >= i4) {
            return;
        }
        iArr2[13] = iArr2[13] + 1;
    }

    @Override // org.bouncycastle.crypto.engines.Salsa20Engine
    public void retreatCounter(long j2) {
        int i2 = (int) (j2 >>> 32);
        int i3 = (int) j2;
        if (i2 != 0) {
            int[] iArr = this.engineState;
            int i4 = iArr[13];
            if ((i4 & BodyPartID.bodyIdMax) < (i2 & BodyPartID.bodyIdMax)) {
                throw new IllegalStateException("attempt to reduce counter past zero.");
            }
            iArr[13] = i4 - i2;
        }
        int[] iArr2 = this.engineState;
        int i5 = iArr2[12];
        if ((i5 & BodyPartID.bodyIdMax) >= (BodyPartID.bodyIdMax & i3)) {
            iArr2[12] = i5 - i3;
            return;
        }
        int i6 = iArr2[13];
        if (i6 == 0) {
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
        iArr2[13] = i6 - 1;
        iArr2[12] = i5 - i3;
    }
}
