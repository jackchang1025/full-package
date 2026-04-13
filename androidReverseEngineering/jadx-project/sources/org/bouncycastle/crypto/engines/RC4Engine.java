package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RC4Engine implements StreamCipher {
    private static final int STATE_LENGTH = 256;
    private byte[] engineState = null;

    /* renamed from: x */
    private int f1221x = 0;

    /* renamed from: y */
    private int f1222y = 0;
    private byte[] workingKey = null;

    private void setKey(byte[] bArr) {
        this.workingKey = bArr;
        this.f1221x = 0;
        this.f1222y = 0;
        if (this.engineState == null) {
            this.engineState = new byte[256];
        }
        for (int i2 = 0; i2 < 256; i2++) {
            this.engineState[i2] = (byte) i2;
        }
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < 256; i5++) {
            int i6 = bArr[i3] & 255;
            byte[] bArr2 = this.engineState;
            byte b = bArr2[i5];
            i4 = (i6 + b + i4) & 255;
            bArr2[i5] = bArr2[i4];
            bArr2[i4] = b;
            i3 = (i3 + 1) % bArr.length;
        }
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "RC4";
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to RC4 init - "));
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        this.workingKey = key;
        setKey(key);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = (this.f1221x + 1) & 255;
            this.f1221x = i6;
            byte[] bArr3 = this.engineState;
            byte b = bArr3[i6];
            int i7 = (this.f1222y + b) & 255;
            this.f1222y = i7;
            bArr3[i6] = bArr3[i7];
            bArr3[i7] = b;
            bArr2[i5 + i4] = (byte) (bArr3[(bArr3[i6] + b) & 255] ^ bArr[i5 + i2]);
        }
        return i3;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        setKey(this.workingKey);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b) {
        int i2 = (this.f1221x + 1) & 255;
        this.f1221x = i2;
        byte[] bArr = this.engineState;
        byte b2 = bArr[i2];
        int i3 = (this.f1222y + b2) & 255;
        this.f1222y = i3;
        bArr[i2] = bArr[i3];
        bArr[i3] = b2;
        return (byte) (b ^ bArr[(bArr[i2] + b2) & 255]);
    }
}
