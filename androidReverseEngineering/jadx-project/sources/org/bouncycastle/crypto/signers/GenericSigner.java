package org.bouncycastle.crypto.signers;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class GenericSigner implements Signer {
    private final Digest digest;
    private final AsymmetricBlockCipher engine;
    private boolean forSigning;

    public GenericSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this.engine = asymmetricBlockCipher;
        this.digest = digest;
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() {
        if (!this.forSigning) {
            throw new IllegalStateException("GenericSigner not initialised for signature generation.");
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.doFinal(bArr, 0);
        return this.engine.processBlock(bArr, 0, digestSize);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forSigning = z2;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z2 && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        }
        if (!z2 && asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("verification requires public key");
        }
        reset();
        this.engine.init(z2, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b) {
        this.digest.update(b);
    }

    @Override // org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        if (this.forSigning) {
            throw new IllegalStateException("GenericSigner not initialised for verification");
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] processBlock = this.engine.processBlock(bArr, 0, bArr.length);
            if (processBlock.length < digestSize) {
                byte[] bArr3 = new byte[digestSize];
                System.arraycopy(processBlock, 0, bArr3, digestSize - processBlock.length, processBlock.length);
                processBlock = bArr3;
            }
            return Arrays.constantTimeAreEqual(processBlock, bArr2);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
