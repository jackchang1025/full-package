package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class ISAACEngine implements StreamCipher {
    private final int sizeL = 8;
    private final int stateArraySize = 256;
    private int[] engineState = null;
    private int[] results = null;

    /* renamed from: a */
    private int f1217a = 0;

    /* renamed from: b */
    private int f1218b = 0;

    /* renamed from: c */
    private int f1219c = 0;
    private int index = 0;
    private byte[] keyStream = new byte[1024];
    private byte[] workingKey = null;
    private boolean initialised = false;

    private void isaac() {
        int i2;
        int i3;
        int i4 = this.f1218b;
        int i5 = this.f1219c + 1;
        this.f1219c = i5;
        this.f1218b = i4 + i5;
        for (int i6 = 0; i6 < 256; i6++) {
            int[] iArr = this.engineState;
            int i7 = iArr[i6];
            int i8 = i6 & 3;
            if (i8 == 0) {
                i2 = this.f1217a;
                i3 = i2 << 13;
            } else if (i8 == 1) {
                i2 = this.f1217a;
                i3 = i2 >>> 6;
            } else if (i8 == 2) {
                i2 = this.f1217a;
                i3 = i2 << 2;
            } else if (i8 != 3) {
                int i9 = this.f1217a + iArr[(i6 + 128) & 255];
                this.f1217a = i9;
                int i10 = iArr[(i7 >>> 2) & 255] + i9 + this.f1218b;
                iArr[i6] = i10;
                int[] iArr2 = this.results;
                int i11 = iArr[(i10 >>> 10) & 255] + i7;
                this.f1218b = i11;
                iArr2[i6] = i11;
            } else {
                i2 = this.f1217a;
                i3 = i2 >>> 16;
            }
            this.f1217a = i2 ^ i3;
            int i92 = this.f1217a + iArr[(i6 + 128) & 255];
            this.f1217a = i92;
            int i102 = iArr[(i7 >>> 2) & 255] + i92 + this.f1218b;
            iArr[i6] = i102;
            int[] iArr22 = this.results;
            int i112 = iArr[(i102 >>> 10) & 255] + i7;
            this.f1218b = i112;
            iArr22[i6] = i112;
        }
    }

    private void mix(int[] iArr) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = i2 ^ (i3 << 11);
        iArr[0] = i4;
        int i5 = iArr[3] + i4;
        iArr[3] = i5;
        int i6 = iArr[2];
        int i7 = i3 + i6;
        iArr[1] = i7;
        int i8 = i7 ^ (i6 >>> 2);
        iArr[1] = i8;
        int i9 = iArr[4] + i8;
        iArr[4] = i9;
        int i10 = i6 + i5;
        iArr[2] = i10;
        int i11 = i10 ^ (i5 << 8);
        iArr[2] = i11;
        int i12 = iArr[5] + i11;
        iArr[5] = i12;
        int i13 = i5 + i9;
        iArr[3] = i13;
        int i14 = i13 ^ (i9 >>> 16);
        iArr[3] = i14;
        int i15 = iArr[6] + i14;
        iArr[6] = i15;
        int i16 = i9 + i12;
        iArr[4] = i16;
        int i17 = (i12 << 10) ^ i16;
        iArr[4] = i17;
        int i18 = iArr[7] + i17;
        iArr[7] = i18;
        int i19 = i12 + i15;
        iArr[5] = i19;
        int i20 = (i15 >>> 4) ^ i19;
        iArr[5] = i20;
        int i21 = i4 + i20;
        iArr[0] = i21;
        int i22 = i15 + i18;
        iArr[6] = i22;
        int i23 = (i18 << 8) ^ i22;
        iArr[6] = i23;
        int i24 = i8 + i23;
        iArr[1] = i24;
        int i25 = i18 + i21;
        iArr[7] = i25;
        int i26 = (i21 >>> 9) ^ i25;
        iArr[7] = i26;
        iArr[2] = i11 + i26;
        iArr[0] = i21 + i24;
    }

    private void setKey(byte[] bArr) {
        this.workingKey = bArr;
        if (this.engineState == null) {
            this.engineState = new int[256];
        }
        if (this.results == null) {
            this.results = new int[256];
        }
        for (int i2 = 0; i2 < 256; i2++) {
            int[] iArr = this.engineState;
            this.results[i2] = 0;
            iArr[i2] = 0;
        }
        this.f1219c = 0;
        this.f1218b = 0;
        this.f1217a = 0;
        this.index = 0;
        int length = bArr.length + (bArr.length & 3);
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int i3 = 0; i3 < length; i3 += 4) {
            this.results[i3 >>> 2] = Pack.littleEndianToInt(bArr2, i3);
        }
        int[] iArr2 = new int[8];
        for (int i4 = 0; i4 < 8; i4++) {
            iArr2[i4] = -1640531527;
        }
        for (int i5 = 0; i5 < 4; i5++) {
            mix(iArr2);
        }
        int i6 = 0;
        while (i6 < 2) {
            for (int i7 = 0; i7 < 256; i7 += 8) {
                for (int i8 = 0; i8 < 8; i8++) {
                    iArr2[i8] = iArr2[i8] + (i6 < 1 ? this.results[i7 + i8] : this.engineState[i7 + i8]);
                }
                mix(iArr2);
                for (int i9 = 0; i9 < 8; i9++) {
                    this.engineState[i7 + i9] = iArr2[i9];
                }
            }
            i6++;
        }
        isaac();
        this.initialised = true;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "ISAAC";
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to ISAAC init - "));
        }
        setKey(((KeyParameter) cipherParameters).getKey());
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            if (this.index == 0) {
                isaac();
                this.keyStream = Pack.intToBigEndian(this.results);
            }
            byte[] bArr3 = this.keyStream;
            int i6 = this.index;
            bArr2[i5 + i4] = (byte) (bArr3[i6] ^ bArr[i5 + i2]);
            this.index = (i6 + 1) & 1023;
        }
        return i3;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        setKey(this.workingKey);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b) {
        if (this.index == 0) {
            isaac();
            this.keyStream = Pack.intToBigEndian(this.results);
        }
        byte[] bArr = this.keyStream;
        int i2 = this.index;
        byte b2 = (byte) (b ^ bArr[i2]);
        this.index = (i2 + 1) & 1023;
        return b2;
    }
}
