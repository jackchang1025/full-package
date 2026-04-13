package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl;

/* loaded from: classes.dex */
public class JceBlockCipherWithCBCImplicitIVImpl implements TlsBlockCipherImpl {
    private static final int BUF_SIZE = 32768;
    private final String algorithm;
    private final Cipher cipher;
    private final boolean isEncrypting;
    private SecretKey key;
    private byte[] nextIV;

    public JceBlockCipherWithCBCImplicitIVImpl(Cipher cipher, String str, boolean z2) {
        this.cipher = cipher;
        this.algorithm = str;
        this.isEncrypting = z2;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        try {
            this.cipher.init(this.isEncrypting ? 1 : 2, this.key, new IvParameterSpec(this.nextIV), (SecureRandom) null);
            this.nextIV = null;
            if (!this.isEncrypting) {
                int i5 = i2 + i3;
                this.nextIV = TlsUtils.copyOfRangeExact(bArr, i5 - this.cipher.getBlockSize(), i5);
            }
            int i6 = 0;
            while (i3 > 32768) {
                i6 += this.cipher.update(bArr, i2, 32768, bArr2, i4 + i6);
                i2 += 32768;
                i3 -= 32768;
            }
            int update = i6 + this.cipher.update(bArr, i2, i3, bArr2, i4 + i6);
            int doFinal = update + this.cipher.doFinal(bArr2, i4 + update);
            if (this.isEncrypting) {
                int i7 = i4 + doFinal;
                this.nextIV = TlsUtils.copyOfRangeExact(bArr2, i7 - this.cipher.getBlockSize(), i7);
            }
            return doFinal;
        } catch (GeneralSecurityException e2) {
            throw Exceptions.illegalStateException(e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void init(byte[] bArr, int i2, int i3) {
        if (this.nextIV != null) {
            throw new IllegalStateException("unexpected reinitialization of an implicit-IV cipher");
        }
        this.nextIV = TlsUtils.copyOfRangeExact(bArr, i2, i3 + i2);
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsBlockCipherImpl
    public void setKey(byte[] bArr, int i2, int i3) {
        this.key = new SecretKeySpec(bArr, i2, i3, this.algorithm);
    }
}
