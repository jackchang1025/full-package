package org.bouncycastle.tls.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;

/* loaded from: classes.dex */
public interface TlsCrypto {
    TlsSecret adoptSecret(TlsSecret tlsSecret);

    TlsCertificate createCertificate(byte[] bArr);

    TlsCipher createCipher(TlsCryptoParameters tlsCryptoParameters, int i2, int i3);

    TlsDHDomain createDHDomain(TlsDHConfig tlsDHConfig);

    TlsECDomain createECDomain(TlsECConfig tlsECConfig);

    TlsHMAC createHMAC(int i2);

    TlsHMAC createHMACForHash(int i2);

    TlsHash createHash(int i2);

    TlsNonceGenerator createNonceGenerator(byte[] bArr);

    TlsSRP6Client createSRP6Client(TlsSRPConfig tlsSRPConfig);

    TlsSRP6Server createSRP6Server(TlsSRPConfig tlsSRPConfig, BigInteger bigInteger);

    TlsSRP6VerifierGenerator createSRP6VerifierGenerator(TlsSRPConfig tlsSRPConfig);

    TlsSecret createSecret(byte[] bArr);

    TlsSecret generateRSAPreMasterSecret(ProtocolVersion protocolVersion);

    SecureRandom getSecureRandom();

    boolean hasAllRawSignatureAlgorithms();

    boolean hasCryptoHashAlgorithm(int i2);

    boolean hasCryptoSignatureAlgorithm(int i2);

    boolean hasDHAgreement();

    boolean hasECDHAgreement();

    boolean hasEncryptionAlgorithm(int i2);

    boolean hasMacAlgorithm(int i2);

    boolean hasNamedGroup(int i2);

    boolean hasRSAEncryption();

    boolean hasSRPAuthentication();

    boolean hasSignatureAlgorithm(short s2);

    boolean hasSignatureAndHashAlgorithm(SignatureAndHashAlgorithm signatureAndHashAlgorithm);

    boolean hasSignatureScheme(int i2);

    TlsSecret hkdfInit(int i2);
}
