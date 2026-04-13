package org.bouncycastle.crypto.engines;

/* loaded from: classes.dex */
public class VMPCKSA3Engine extends VMPCEngine {
    @Override // org.bouncycastle.crypto.engines.VMPCEngine, org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "VMPC-KSA3";
    }

    @Override // org.bouncycastle.crypto.engines.VMPCEngine
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
        for (int i7 = 0; i7 < 768; i7++) {
            byte[] bArr5 = this.f1230P;
            byte b7 = this.f1232s;
            int i8 = i7 & 255;
            byte b8 = bArr5[i8];
            byte b9 = bArr5[(b7 + b8 + bArr[i7 % bArr.length]) & 255];
            this.f1232s = b9;
            bArr5[i8] = bArr5[b9 & 255];
            bArr5[b9 & 255] = b8;
        }
        this.f1231n = (byte) 0;
    }
}
