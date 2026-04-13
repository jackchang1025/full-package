package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl;

/* loaded from: classes.dex */
final class BcTlsBlockCipherImpl implements TlsBlockCipherImpl {
    private final BlockCipher cipher;
    private final boolean isEncrypting;
    private KeyParameter key;

    public BcTlsBlockCipherImpl(BlockCipher blockCipher, boolean z2) {
        this.cipher = blockCipher;
        this.isEncrypting = z2;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int blockSize = this.cipher.getBlockSize();
        for (int i5 = 0; i5 < i3; i5 += blockSize) {
            this.cipher.processBlock(bArr, i2 + i5, bArr2, i4 + i5);
        }
        return i3;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void init(byte[] bArr, int i2, int i3) {
        this.cipher.init(this.isEncrypting, new ParametersWithIV(this.key, bArr, i2, i3));
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void setKey(byte[] bArr, int i2, int i3) {
        this.key = new KeyParameter(bArr, i2, i3);
    }
}
