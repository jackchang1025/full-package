package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl;

/* loaded from: classes.dex */
public class JceBlockCipherImpl implements TlsBlockCipherImpl {
    private static final int BUF_SIZE = 32768;
    private final String algorithm;
    private final Cipher cipher;
    private final int cipherMode;
    private SecretKey key;
    private final int keySize;

    public JceBlockCipherImpl(Cipher cipher, String str, int i2, boolean z2) {
        this.cipher = cipher;
        this.algorithm = str;
        this.keySize = i2;
        this.cipherMode = z2 ? 1 : 2;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = 0;
        while (i3 > 32768) {
            try {
                i5 += this.cipher.update(bArr, i2, 32768, bArr2, i4 + i5);
                i2 += 32768;
                i3 -= 32768;
            } catch (GeneralSecurityException e2) {
                throw Exceptions.illegalStateException(e2.getMessage(), e2);
            }
        }
        int update = i5 + this.cipher.update(bArr, i2, i3, bArr2, i4 + i5);
        return update + this.cipher.doFinal(bArr2, i4 + update);
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void init(byte[] bArr, int i2, int i3) {
        try {
            this.cipher.init(this.cipherMode, this.key, new IvParameterSpec(bArr, i2, i3), (SecureRandom) null);
        } catch (GeneralSecurityException e2) {
            throw Exceptions.illegalStateException(e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void setKey(byte[] bArr, int i2, int i3) {
        if (this.keySize != i3) {
            throw new IllegalStateException();
        }
        this.key = new SecretKeySpec(bArr, i2, i3, this.algorithm);
    }
}
