package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.util.DigestFactory;

/* loaded from: classes.dex */
public class PKCS5S2ParametersGenerator extends PBEParametersGenerator {
    private Mac hMac;
    private byte[] state;

    public PKCS5S2ParametersGenerator() {
        this(DigestFactory.createSHA1());
    }

    /* renamed from: F */
    private void m1224F(byte[] bArr, int i2, byte[] bArr2, byte[] bArr3, int i3) {
        if (i2 == 0) {
            throw new IllegalArgumentException("iteration count must be at least 1.");
        }
        if (bArr != null) {
            this.hMac.update(bArr, 0, bArr.length);
        }
        this.hMac.update(bArr2, 0, bArr2.length);
        this.hMac.doFinal(this.state, 0);
        byte[] bArr4 = this.state;
        System.arraycopy(bArr4, 0, bArr3, i3, bArr4.length);
        for (int i4 = 1; i4 < i2; i4++) {
            Mac mac = this.hMac;
            byte[] bArr5 = this.state;
            mac.update(bArr5, 0, bArr5.length);
            this.hMac.doFinal(this.state, 0);
            int i5 = 0;
            while (true) {
                byte[] bArr6 = this.state;
                if (i5 != bArr6.length) {
                    int i6 = i3 + i5;
                    bArr3[i6] = (byte) (bArr6[i5] ^ bArr3[i6]);
                    i5++;
                }
            }
        }
    }

    private byte[] generateDerivedKey(int i2) {
        int i3;
        int macSize = this.hMac.getMacSize();
        int i4 = ((i2 + macSize) - 1) / macSize;
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[i4 * macSize];
        this.hMac.init(new KeyParameter(this.password));
        int i5 = 0;
        for (int i6 = 1; i6 <= i4; i6++) {
            while (true) {
                byte b = (byte) (bArr[i3] + 1);
                bArr[i3] = b;
                i3 = b == 0 ? i3 - 1 : 3;
            }
            m1224F(this.salt, this.iterationCount, bArr, bArr2, i5);
            i5 += macSize;
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedMacParameters(int i2) {
        return generateDerivedParameters(i2);
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i2) {
        int i3 = i2 / 8;
        return new KeyParameter(generateDerivedKey(i3), 0, i3);
    }

    public PKCS5S2ParametersGenerator(Digest digest) {
        HMac hMac = new HMac(digest);
        this.hMac = hMac;
        this.state = new byte[hMac.getMacSize()];
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i2, int i3) {
        int i4 = i2 / 8;
        int i5 = i3 / 8;
        byte[] generateDerivedKey = generateDerivedKey(i4 + i5);
        return new ParametersWithIV(new KeyParameter(generateDerivedKey, 0, i4), generateDerivedKey, i4, i5);
    }
}
