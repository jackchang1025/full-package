package org.bouncycastle.tls.crypto.impl.bc;

import java.security.SecureRandom;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsCredentialedDecryptor;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BcDefaultTlsCredentialedDecryptor implements TlsCredentialedDecryptor {
    protected Certificate certificate;
    protected BcTlsCrypto crypto;
    protected AsymmetricKeyParameter privateKey;

    public BcDefaultTlsCredentialedDecryptor(BcTlsCrypto bcTlsCrypto, Certificate certificate, AsymmetricKeyParameter asymmetricKeyParameter) {
        if (bcTlsCrypto == null) {
            throw new IllegalArgumentException("'crypto' cannot be null");
        }
        if (certificate == null) {
            throw new IllegalArgumentException("'certificate' cannot be null");
        }
        if (certificate.isEmpty()) {
            throw new IllegalArgumentException("'certificate' cannot be empty");
        }
        if (asymmetricKeyParameter == null) {
            throw new IllegalArgumentException("'privateKey' cannot be null");
        }
        if (!asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("'privateKey' must be private");
        }
        if (!(asymmetricKeyParameter instanceof RSAKeyParameters)) {
            throw new IllegalArgumentException("'privateKey' type not supported: ".concat(asymmetricKeyParameter.getClass().getName()));
        }
        this.crypto = bcTlsCrypto;
        this.certificate = certificate;
        this.privateKey = asymmetricKeyParameter;
    }

    @Override // org.bouncycastle.tls.TlsCredentialedDecryptor
    public TlsSecret decrypt(TlsCryptoParameters tlsCryptoParameters, byte[] bArr) {
        return safeDecryptPreMasterSecret(tlsCryptoParameters, (RSAKeyParameters) this.privateKey, bArr);
    }

    @Override // org.bouncycastle.tls.TlsCredentials
    public Certificate getCertificate() {
        return this.certificate;
    }

    public TlsSecret safeDecryptPreMasterSecret(TlsCryptoParameters tlsCryptoParameters, RSAKeyParameters rSAKeyParameters, byte[] bArr) {
        SecureRandom secureRandom = this.crypto.getSecureRandom();
        ProtocolVersion rSAPreMasterSecretVersion = tlsCryptoParameters.getRSAPreMasterSecretVersion();
        byte[] bArr2 = new byte[48];
        secureRandom.nextBytes(bArr2);
        byte[] clone = Arrays.clone(bArr2);
        try {
            PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine(), bArr2);
            pKCS1Encoding.init(false, new ParametersWithRandom(rSAKeyParameters, secureRandom));
            clone = pKCS1Encoding.processBlock(bArr, 0, bArr.length);
        } catch (Exception unused) {
        }
        int minorVersion = (((rSAPreMasterSecretVersion.getMinorVersion() ^ (clone[1] & 255)) | (rSAPreMasterSecretVersion.getMajorVersion() ^ (clone[0] & 255))) - 1) >> 31;
        for (int i2 = 0; i2 < 48; i2++) {
            clone[i2] = (byte) ((clone[i2] & minorVersion) | (bArr2[i2] & (~minorVersion)));
        }
        return this.crypto.createSecret(clone);
    }
}
