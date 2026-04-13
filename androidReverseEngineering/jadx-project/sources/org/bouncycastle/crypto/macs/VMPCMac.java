package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes.dex */
public class VMPCMac implements Mac {

    /* renamed from: T */
    private byte[] f1256T;

    /* renamed from: g */
    private byte f1257g;
    private byte[] workingIV;
    private byte[] workingKey;
    private byte x1;
    private byte x2;
    private byte x3;
    private byte x4;

    /* renamed from: n */
    private byte f1258n = 0;

    /* renamed from: P */
    private byte[] f1255P = null;

    /* renamed from: s */
    private byte f1259s = 0;

    private void initKey(byte[] bArr, byte[] bArr2) {
        this.f1259s = (byte) 0;
        this.f1255P = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.f1255P[i2] = (byte) i2;
        }
        for (int i3 = 0; i3 < 768; i3++) {
            byte[] bArr3 = this.f1255P;
            byte b = this.f1259s;
            int i4 = i3 & 255;
            byte b2 = bArr3[i4];
            byte b3 = bArr3[(b + b2 + bArr[i3 % bArr.length]) & 255];
            this.f1259s = b3;
            bArr3[i4] = bArr3[b3 & 255];
            bArr3[b3 & 255] = b2;
        }
        for (int i5 = 0; i5 < 768; i5++) {
            byte[] bArr4 = this.f1255P;
            byte b4 = this.f1259s;
            int i6 = i5 & 255;
            byte b5 = bArr4[i6];
            byte b6 = bArr4[(b4 + b5 + bArr2[i5 % bArr2.length]) & 255];
            this.f1259s = b6;
            bArr4[i6] = bArr4[b6 & 255];
            bArr4[b6 & 255] = b5;
        }
        this.f1258n = (byte) 0;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        for (int i3 = 1; i3 < 25; i3++) {
            byte[] bArr2 = this.f1255P;
            byte b = this.f1259s;
            byte b2 = this.f1258n;
            byte b3 = bArr2[(b + bArr2[b2 & 255]) & 255];
            this.f1259s = b3;
            byte b4 = this.x4;
            byte b5 = this.x3;
            byte b6 = bArr2[(b4 + b5 + i3) & 255];
            this.x4 = b6;
            byte b7 = this.x2;
            byte b8 = bArr2[(b5 + b7 + i3) & 255];
            this.x3 = b8;
            byte b9 = this.x1;
            byte b10 = bArr2[(b7 + b9 + i3) & 255];
            this.x2 = b10;
            byte b11 = bArr2[(b9 + b3 + i3) & 255];
            this.x1 = b11;
            byte[] bArr3 = this.f1256T;
            byte b12 = this.f1257g;
            bArr3[b12 & 31] = (byte) (b11 ^ bArr3[b12 & 31]);
            bArr3[(b12 + 1) & 31] = (byte) (b10 ^ bArr3[(b12 + 1) & 31]);
            bArr3[(b12 + 2) & 31] = (byte) (b8 ^ bArr3[(b12 + 2) & 31]);
            bArr3[(b12 + 3) & 31] = (byte) (b6 ^ bArr3[(b12 + 3) & 31]);
            this.f1257g = (byte) ((b12 + 4) & 31);
            byte b13 = bArr2[b2 & 255];
            bArr2[b2 & 255] = bArr2[b3 & 255];
            bArr2[b3 & 255] = b13;
            this.f1258n = (byte) ((b2 + 1) & 255);
        }
        for (int i4 = 0; i4 < 768; i4++) {
            byte[] bArr4 = this.f1255P;
            byte b14 = this.f1259s;
            int i5 = i4 & 255;
            byte b15 = bArr4[i5];
            byte b16 = bArr4[(b14 + b15 + this.f1256T[i4 & 31]) & 255];
            this.f1259s = b16;
            bArr4[i5] = bArr4[b16 & 255];
            bArr4[b16 & 255] = b15;
        }
        byte[] bArr5 = new byte[20];
        for (int i6 = 0; i6 < 20; i6++) {
            byte[] bArr6 = this.f1255P;
            int i7 = i6 & 255;
            byte b17 = bArr6[(this.f1259s + bArr6[i7]) & 255];
            this.f1259s = b17;
            bArr5[i6] = bArr6[(bArr6[bArr6[b17 & 255] & 255] + 1) & 255];
            byte b18 = bArr6[i7];
            bArr6[i7] = bArr6[b17 & 255];
            bArr6[b17 & 255] = b18;
        }
        System.arraycopy(bArr5, 0, bArr, i2, 20);
        reset();
        return 20;
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "VMPC-MAC";
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 20;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
        }
        byte[] iv = parametersWithIV.getIV();
        this.workingIV = iv;
        if (iv == null || iv.length < 1 || iv.length > 768) {
            throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
        }
        this.workingKey = keyParameter.getKey();
        reset();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        initKey(this.workingKey, this.workingIV);
        this.f1258n = (byte) 0;
        this.x4 = (byte) 0;
        this.x3 = (byte) 0;
        this.x2 = (byte) 0;
        this.x1 = (byte) 0;
        this.f1257g = (byte) 0;
        this.f1256T = new byte[32];
        for (int i2 = 0; i2 < 32; i2++) {
            this.f1256T[i2] = 0;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b) {
        byte[] bArr = this.f1255P;
        byte b2 = this.f1259s;
        byte b3 = this.f1258n;
        byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
        this.f1259s = b4;
        byte b5 = (byte) (b ^ bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255]);
        byte b6 = this.x4;
        byte b7 = this.x3;
        byte b8 = bArr[(b6 + b7) & 255];
        this.x4 = b8;
        byte b9 = this.x2;
        byte b10 = bArr[(b7 + b9) & 255];
        this.x3 = b10;
        byte b11 = this.x1;
        byte b12 = bArr[(b9 + b11) & 255];
        this.x2 = b12;
        byte b13 = bArr[(b11 + b4 + b5) & 255];
        this.x1 = b13;
        byte[] bArr2 = this.f1256T;
        byte b14 = this.f1257g;
        bArr2[b14 & 31] = (byte) (b13 ^ bArr2[b14 & 31]);
        bArr2[(b14 + 1) & 31] = (byte) (b12 ^ bArr2[(b14 + 1) & 31]);
        bArr2[(b14 + 2) & 31] = (byte) (b10 ^ bArr2[(b14 + 2) & 31]);
        bArr2[(b14 + 3) & 31] = (byte) (b8 ^ bArr2[(b14 + 3) & 31]);
        this.f1257g = (byte) ((b14 + 4) & 31);
        byte b15 = bArr[b3 & 255];
        bArr[b3 & 255] = bArr[b4 & 255];
        bArr[b4 & 255] = b15;
        this.f1258n = (byte) ((b3 + 1) & 255);
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i4 = 0; i4 < i3; i4++) {
            update(bArr[i2 + i4]);
        }
    }
}
