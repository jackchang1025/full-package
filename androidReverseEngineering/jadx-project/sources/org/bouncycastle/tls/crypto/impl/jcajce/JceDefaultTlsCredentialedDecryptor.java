package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.Cipher;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsCredentialedDecryptor;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class JceDefaultTlsCredentialedDecryptor implements TlsCredentialedDecryptor {
    protected Certificate certificate;
    protected JcaTlsCrypto crypto;
    protected PrivateKey privateKey;

    public JceDefaultTlsCredentialedDecryptor(JcaTlsCrypto jcaTlsCrypto, Certificate certificate, PrivateKey privateKey) {
        if (jcaTlsCrypto == null) {
            throw new IllegalArgumentException("'crypto' cannot be null");
        }
        if (certificate == null) {
            throw new IllegalArgumentException("'certificate' cannot be null");
        }
        if (certificate.isEmpty()) {
            throw new IllegalArgumentException("'certificate' cannot be empty");
        }
        if (privateKey == null) {
            throw new IllegalArgumentException("'privateKey' cannot be null");
        }
        if (!(privateKey instanceof RSAPrivateKey) && !"RSA".equals(privateKey.getAlgorithm())) {
            throw new IllegalArgumentException("'privateKey' type not supported: ".concat(privateKey.getClass().getName()));
        }
        this.crypto = jcaTlsCrypto;
        this.certificate = certificate;
        this.privateKey = privateKey;
    }

    @Override // org.bouncycastle.tls.TlsCredentialedDecryptor
    public TlsSecret decrypt(TlsCryptoParameters tlsCryptoParameters, byte[] bArr) {
        return safeDecryptPreMasterSecret(tlsCryptoParameters, this.privateKey, bArr);
    }

    @Override // org.bouncycastle.tls.TlsCredentials
    public Certificate getCertificate() {
        return this.certificate;
    }

    public TlsSecret safeDecryptPreMasterSecret(TlsCryptoParameters tlsCryptoParameters, PrivateKey privateKey, byte[] bArr) {
        SecureRandom secureRandom = this.crypto.getSecureRandom();
        ProtocolVersion rSAPreMasterSecretVersion = tlsCryptoParameters.getRSAPreMasterSecretVersion();
        byte[] bArr2 = new byte[48];
        secureRandom.nextBytes(bArr2);
        byte[] clone = Arrays.clone(bArr2);
        try {
            Cipher createRSAEncryptionCipher = this.crypto.createRSAEncryptionCipher();
            createRSAEncryptionCipher.init(2, privateKey, secureRandom);
            byte[] doFinal = createRSAEncryptionCipher.doFinal(bArr);
            if (doFinal != null) {
                if (doFinal.length == 48) {
                    clone = doFinal;
                }
            }
        } catch (Exception unused) {
        }
        int minorVersion = (((rSAPreMasterSecretVersion.getMinorVersion() ^ (clone[1] & 255)) | (rSAPreMasterSecretVersion.getMajorVersion() ^ (clone[0] & 255))) - 1) >> 31;
        for (int i2 = 0; i2 < 48; i2++) {
            clone[i2] = (byte) ((clone[i2] & minorVersion) | (bArr2[i2] & (~minorVersion)));
        }
        return this.crypto.createSecret(clone);
    }
}
