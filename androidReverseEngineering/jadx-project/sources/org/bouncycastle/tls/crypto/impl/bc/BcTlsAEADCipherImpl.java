package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl;

/* loaded from: classes.dex */
final class BcTlsAEADCipherImpl implements TlsAEADCipherImpl {
    private final AEADBlockCipher cipher;
    private final boolean isEncrypting;
    private KeyParameter key;

    public BcTlsAEADCipherImpl(AEADBlockCipher aEADBlockCipher, boolean z2) {
        this.cipher = aEADBlockCipher;
        this.isEncrypting = z2;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int processBytes = this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        try {
            return processBytes + this.cipher.doFinal(bArr2, i4 + processBytes);
        } catch (InvalidCipherTextException e2) {
            throw new TlsFatalAlert((short) 20, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public int getOutputSize(int i2) {
        return this.cipher.getOutputSize(i2);
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public void init(byte[] bArr, int i2, byte[] bArr2) {
        this.cipher.init(this.isEncrypting, new AEADParameters(this.key, i2 * 8, bArr, bArr2));
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public void setKey(byte[] bArr, int i2, int i3) {
        this.key = new KeyParameter(bArr, i2, i3);
    }
}
