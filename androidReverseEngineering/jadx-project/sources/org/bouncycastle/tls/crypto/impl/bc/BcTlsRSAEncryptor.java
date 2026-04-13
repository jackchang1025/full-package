package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsEncryptor;

/* loaded from: classes.dex */
final class BcTlsRSAEncryptor implements TlsEncryptor {
    private final BcTlsCrypto crypto;
    private final RSAKeyParameters pubKeyRSA;

    public BcTlsRSAEncryptor(BcTlsCrypto bcTlsCrypto, RSAKeyParameters rSAKeyParameters) {
        this.crypto = bcTlsCrypto;
        this.pubKeyRSA = checkPublicKey(rSAKeyParameters);
    }

    private static RSAKeyParameters checkPublicKey(RSAKeyParameters rSAKeyParameters) {
        if (rSAKeyParameters == null || rSAKeyParameters.isPrivate()) {
            throw new IllegalArgumentException("No public RSA key provided");
        }
        return rSAKeyParameters;
    }

    @Override // org.bouncycastle.tls.crypto.TlsEncryptor
    public byte[] encrypt(byte[] bArr, int i2, int i3) {
        try {
            PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine());
            pKCS1Encoding.init(true, new ParametersWithRandom(this.pubKeyRSA, this.crypto.getSecureRandom()));
            return pKCS1Encoding.processBlock(bArr, i2, i3);
        } catch (InvalidCipherTextException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
