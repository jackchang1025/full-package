package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class RSAEngine implements AsymmetricBlockCipher {
    private RSACoreEngine core;

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (this.core == null) {
            this.core = new RSACoreEngine();
        }
        this.core.init(z2, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        RSACoreEngine rSACoreEngine = this.core;
        if (rSACoreEngine != null) {
            return rSACoreEngine.convertOutput(rSACoreEngine.processBlock(rSACoreEngine.convertInput(bArr, i2, i3)));
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}
