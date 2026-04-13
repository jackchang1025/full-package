package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsEncryptor;

/* loaded from: classes.dex */
final class JcaTlsRSAEncryptor implements TlsEncryptor {
    private final JcaTlsCrypto crypto;
    private final PublicKey pubKeyRSA;

    public JcaTlsRSAEncryptor(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey) {
        this.crypto = jcaTlsCrypto;
        this.pubKeyRSA = publicKey;
    }

    @Override // org.bouncycastle.tls.crypto.TlsEncryptor
    public byte[] encrypt(byte[] bArr, int i2, int i3) {
        try {
            Cipher createRSAEncryptionCipher = this.crypto.createRSAEncryptionCipher();
            try {
                createRSAEncryptionCipher.init(3, this.pubKeyRSA, this.crypto.getSecureRandom());
                return createRSAEncryptionCipher.wrap(new SecretKeySpec(bArr, i2, i3, "TLS"));
            } catch (Exception e2) {
                try {
                    createRSAEncryptionCipher.init(1, this.pubKeyRSA, this.crypto.getSecureRandom());
                    return createRSAEncryptionCipher.doFinal(bArr, i2, i3);
                } catch (Exception unused) {
                    throw new TlsFatalAlert((short) 80, (Throwable) e2);
                }
            }
        } catch (GeneralSecurityException e3) {
            throw new TlsFatalAlert((short) 80, (Throwable) e3);
        }
    }
}
