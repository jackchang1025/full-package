package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes.dex */
public class VMPCEngine implements StreamCipher {
    protected byte[] workingIV;
    protected byte[] workingKey;

    /* renamed from: n */
    protected byte f1231n = 0;

    /* renamed from: P */
    protected byte[] f1230P = null;

    /* renamed from: s */
    protected byte f1232s = 0;

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "VMPC";
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("VMPC init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC init parameters must include a key");
        }
        KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
        byte[] iv = parametersWithIV.getIV();
        this.workingIV = iv;
        if (iv == null || iv.length < 1 || iv.length > 768) {
            throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
        }
        byte[] key = keyParameter.getKey();
        this.workingKey = key;
        initKey(key, this.workingIV);
    }

    public void initKey(byte[] bArr, byte[] bArr2) {
        this.f1232s = (byte) 0;
        this.f1230P = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.f1230P[i2] = (byte) i2;
        }
        for (int i3 = 0; i3 < 768; i3++) {
            byte[] bArr3 = this.f1230P;
            byte b = this.f1232s;
            int i4 = i3 & 255;
            byte b2 = bArr3[i4];
            byte b3 = bArr3[(b + b2 + bArr[i3 % bArr.length]) & 255];
            this.f1232s = b3;
            bArr3[i4] = bArr3[b3 & 255];
            bArr3[b3 & 255] = b2;
        }
        for (int i5 = 0; i5 < 768; i5++) {
            byte[] bArr4 = this.f1230P;
            byte b4 = this.f1232s;
            int i6 = i5 & 255;
            byte b5 = bArr4[i6];
            byte b6 = bArr4[(b4 + b5 + bArr2[i5 % bArr2.length]) & 255];
            this.f1232s = b6;
            bArr4[i6] = bArr4[b6 & 255];
            bArr4[b6 & 255] = b5;
        }
        this.f1231n = (byte) 0;
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
            byte[] bArr3 = this.f1230P;
            byte b = this.f1232s;
            byte b2 = this.f1231n;
            byte b3 = bArr3[(b + bArr3[b2 & 255]) & 255];
            this.f1232s = b3;
            byte b4 = bArr3[(bArr3[bArr3[b3 & 255] & 255] + 1) & 255];
            byte b5 = bArr3[b2 & 255];
            bArr3[b2 & 255] = bArr3[b3 & 255];
            bArr3[b3 & 255] = b5;
            this.f1231n = (byte) ((b2 + 1) & 255);
            bArr2[i5 + i4] = (byte) (bArr[i5 + i2] ^ b4);
        }
        return i3;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        initKey(this.workingKey, this.workingIV);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b) {
        byte[] bArr = this.f1230P;
        byte b2 = this.f1232s;
        byte b3 = this.f1231n;
        byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
        this.f1232s = b4;
        byte b5 = bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255];
        byte b6 = bArr[b3 & 255];
        bArr[b3 & 255] = bArr[b4 & 255];
        bArr[b4 & 255] = b6;
        this.f1231n = (byte) ((b3 + 1) & 255);
        return (byte) (b ^ b5);
    }
}
