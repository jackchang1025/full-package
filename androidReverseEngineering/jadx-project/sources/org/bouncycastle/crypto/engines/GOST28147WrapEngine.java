package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.macs.GOST28147Mac;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithUKM;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class GOST28147WrapEngine implements Wrapper {
    private GOST28147Engine cipher = new GOST28147Engine();
    private GOST28147Mac mac = new GOST28147Mac();

    @Override // org.bouncycastle.crypto.Wrapper
    public String getAlgorithmName() {
        return "GOST28147Wrap";
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        ParametersWithUKM parametersWithUKM = (ParametersWithUKM) cipherParameters;
        this.cipher.init(z2, parametersWithUKM.getParameters());
        this.mac.init(new ParametersWithIV(parametersWithUKM.getParameters(), parametersWithUKM.getUKM()));
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i2, int i3) {
        int macSize = i3 - this.mac.getMacSize();
        byte[] bArr2 = new byte[macSize];
        this.cipher.processBlock(bArr, i2, bArr2, 0);
        this.cipher.processBlock(bArr, i2 + 8, bArr2, 8);
        this.cipher.processBlock(bArr, i2 + 16, bArr2, 16);
        this.cipher.processBlock(bArr, i2 + 24, bArr2, 24);
        byte[] bArr3 = new byte[this.mac.getMacSize()];
        this.mac.update(bArr2, 0, macSize);
        this.mac.doFinal(bArr3, 0);
        byte[] bArr4 = new byte[this.mac.getMacSize()];
        System.arraycopy(bArr, (i2 + i3) - 4, bArr4, 0, this.mac.getMacSize());
        if (Arrays.constantTimeAreEqual(bArr3, bArr4)) {
            return bArr2;
        }
        throw new IllegalStateException("mac mismatch");
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i2, int i3) {
        this.mac.update(bArr, i2, i3);
        byte[] bArr2 = new byte[this.mac.getMacSize() + i3];
        this.cipher.processBlock(bArr, i2, bArr2, 0);
        this.cipher.processBlock(bArr, i2 + 8, bArr2, 8);
        this.cipher.processBlock(bArr, i2 + 16, bArr2, 16);
        this.cipher.processBlock(bArr, i2 + 24, bArr2, 24);
        this.mac.doFinal(bArr2, i3);
        return bArr2;
    }
}
