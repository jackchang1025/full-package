package org.bouncycastle.tls.crypto.impl.bc;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;
import org.bouncycastle.crypto.agreement.srp.SRP6Server;
import org.bouncycastle.crypto.agreement.srp.SRP6VerifierGenerator;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.ARIAEngine;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.SRP6GroupParameters;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsCipher;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsDHDomain;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsECDomain;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.tls.crypto.TlsNonceGenerator;
import org.bouncycastle.tls.crypto.TlsSRP6Client;
import org.bouncycastle.tls.crypto.TlsSRP6Server;
import org.bouncycastle.tls.crypto.TlsSRP6VerifierGenerator;
import org.bouncycastle.tls.crypto.TlsSRPConfig;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.tls.crypto.impl.AbstractTlsCrypto;
import org.bouncycastle.tls.crypto.impl.TlsAEADCipher;
import org.bouncycastle.tls.crypto.impl.TlsBlockCipher;
import org.bouncycastle.tls.crypto.impl.TlsImplUtils;
import org.bouncycastle.tls.crypto.impl.TlsNullCipher;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BcTlsCrypto extends AbstractTlsCrypto {
    private final SecureRandom entropySource;

    public BcTlsCrypto(SecureRandom secureRandom) {
        this.entropySource = secureRandom;
    }

    public BcTlsSecret adoptLocalSecret(byte[] bArr) {
        return new BcTlsSecret(this, bArr);
    }

    public Digest cloneDigest(int i2, Digest digest) {
        switch (i2) {
            case 1:
                return new MD5Digest((MD5Digest) digest);
            case 2:
                return new SHA1Digest((SHA1Digest) digest);
            case 3:
                return new SHA224Digest((SHA224Digest) digest);
            case 4:
                return new SHA256Digest((SHA256Digest) digest);
            case 5:
                return new SHA384Digest((SHA384Digest) digest);
            case 6:
                return new SHA512Digest((SHA512Digest) digest);
            case 7:
                return new SM3Digest((SM3Digest) digest);
            default:
                throw new IllegalArgumentException(AbstractC0000a.m11g("invalid CryptoHashAlgorithm: ", i2));
        }
    }

    public AEADBlockCipher createAEADBlockCipher_AES_CCM() {
        return createCCMMode(createAESEngine());
    }

    public AEADBlockCipher createAEADBlockCipher_AES_GCM() {
        return createGCMMode(createAESEngine());
    }

    public AEADBlockCipher createAEADBlockCipher_ARIA_GCM() {
        return createGCMMode(createARIAEngine());
    }

    public AEADBlockCipher createAEADBlockCipher_Camellia_GCM() {
        return createGCMMode(createCamelliaEngine());
    }

    public AEADBlockCipher createAEADBlockCipher_SM4_CCM() {
        return createCCMMode(createSM4Engine());
    }

    public AEADBlockCipher createAEADBlockCipher_SM4_GCM() {
        return createGCMMode(createSM4Engine());
    }

    public BlockCipher createAESEngine() {
        return new AESEngine();
    }

    public BlockCipher createARIAEngine() {
        return new ARIAEngine();
    }

    public BlockCipher createBlockCipher(int i2) {
        if (i2 == 7) {
            return createDESedeEngine();
        }
        if (i2 == 8 || i2 == 9) {
            return createAESEngine();
        }
        if (i2 == 22 || i2 == 23) {
            return createARIAEngine();
        }
        if (i2 == 28) {
            return createSM4Engine();
        }
        switch (i2) {
            case 12:
            case 13:
                return createCamelliaEngine();
            case 14:
                return createSEEDEngine();
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    public BlockCipher createCBCBlockCipher(int i2) {
        return createCBCBlockCipher(createBlockCipher(i2));
    }

    public AEADBlockCipher createCCMMode(BlockCipher blockCipher) {
        return new CCMBlockCipher(blockCipher);
    }

    public BlockCipher createCamelliaEngine() {
        return new CamelliaEngine();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsCertificate createCertificate(byte[] bArr) {
        return new BcTlsCertificate(this, bArr);
    }

    public TlsCipher createChaCha20Poly1305(TlsCryptoParameters tlsCryptoParameters) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcChaCha20Poly1305(true), new BcChaCha20Poly1305(false), 32, 16, 2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsCipher createCipher(TlsCryptoParameters tlsCryptoParameters, int i2, int i3) {
        if (i2 == 0) {
            return createNullCipher(tlsCryptoParameters, i3);
        }
        switch (i2) {
            case 7:
                return createCipher_CBC(tlsCryptoParameters, i2, 24, i3);
            case 8:
            case 12:
            case 14:
            case 22:
            case 28:
                return createCipher_CBC(tlsCryptoParameters, i2, 16, i3);
            case 9:
            case 13:
            case 23:
                return createCipher_CBC(tlsCryptoParameters, i2, 32, i3);
            case 10:
                return createCipher_AES_GCM(tlsCryptoParameters, 16, 16);
            case 11:
                return createCipher_AES_GCM(tlsCryptoParameters, 32, 16);
            case 15:
                return createCipher_AES_CCM(tlsCryptoParameters, 16, 16);
            case 16:
                return createCipher_AES_CCM(tlsCryptoParameters, 16, 8);
            case 17:
                return createCipher_AES_CCM(tlsCryptoParameters, 32, 16);
            case 18:
                return createCipher_AES_CCM(tlsCryptoParameters, 32, 8);
            case 19:
                return createCipher_Camellia_GCM(tlsCryptoParameters, 16, 16);
            case 20:
                return createCipher_Camellia_GCM(tlsCryptoParameters, 32, 16);
            case 21:
                return createChaCha20Poly1305(tlsCryptoParameters);
            case 24:
                return createCipher_ARIA_GCM(tlsCryptoParameters, 16, 16);
            case 25:
                return createCipher_ARIA_GCM(tlsCryptoParameters, 32, 16);
            case 26:
                return createCipher_SM4_CCM(tlsCryptoParameters);
            case 27:
                return createCipher_SM4_GCM(tlsCryptoParameters);
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    public TlsAEADCipher createCipher_AES_CCM(TlsCryptoParameters tlsCryptoParameters, int i2, int i3) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_AES_CCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_AES_CCM(), false), i2, i3, 1);
    }

    public TlsAEADCipher createCipher_AES_GCM(TlsCryptoParameters tlsCryptoParameters, int i2, int i3) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_AES_GCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_AES_GCM(), false), i2, i3, 3);
    }

    public TlsAEADCipher createCipher_ARIA_GCM(TlsCryptoParameters tlsCryptoParameters, int i2, int i3) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_ARIA_GCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_ARIA_GCM(), false), i2, i3, 3);
    }

    public TlsCipher createCipher_CBC(TlsCryptoParameters tlsCryptoParameters, int i2, int i3, int i4) {
        return new TlsBlockCipher(tlsCryptoParameters, new BcTlsBlockCipherImpl(createCBCBlockCipher(i2), true), new BcTlsBlockCipherImpl(createCBCBlockCipher(i2), false), createMAC(tlsCryptoParameters, i4), createMAC(tlsCryptoParameters, i4), i3);
    }

    public TlsAEADCipher createCipher_Camellia_GCM(TlsCryptoParameters tlsCryptoParameters, int i2, int i3) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_Camellia_GCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_Camellia_GCM(), false), i2, i3, 3);
    }

    public TlsAEADCipher createCipher_SM4_CCM(TlsCryptoParameters tlsCryptoParameters) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_SM4_CCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_SM4_CCM(), false), 16, 16, 1);
    }

    public TlsAEADCipher createCipher_SM4_GCM(TlsCryptoParameters tlsCryptoParameters) {
        return new TlsAEADCipher(tlsCryptoParameters, new BcTlsAEADCipherImpl(createAEADBlockCipher_SM4_GCM(), true), new BcTlsAEADCipherImpl(createAEADBlockCipher_SM4_GCM(), false), 16, 16, 3);
    }

    public BlockCipher createDESedeEngine() {
        return new DESedeEngine();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsDHDomain createDHDomain(TlsDHConfig tlsDHConfig) {
        return new BcTlsDHDomain(this, tlsDHConfig);
    }

    public Digest createDigest(int i2) {
        switch (i2) {
            case 1:
                return new MD5Digest();
            case 2:
                return new SHA1Digest();
            case 3:
                return new SHA224Digest();
            case 4:
                return new SHA256Digest();
            case 5:
                return new SHA384Digest();
            case 6:
                return new SHA512Digest();
            case 7:
                return new SM3Digest();
            default:
                throw new IllegalArgumentException(AbstractC0000a.m11g("invalid CryptoHashAlgorithm: ", i2));
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsECDomain createECDomain(TlsECConfig tlsECConfig) {
        int namedGroup = tlsECConfig.getNamedGroup();
        return namedGroup != 29 ? namedGroup != 30 ? new BcTlsECDomain(this, tlsECConfig) : new BcX448Domain(this) : new BcX25519Domain(this);
    }

    public AEADBlockCipher createGCMMode(BlockCipher blockCipher) {
        return new GCMBlockCipher(blockCipher);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsHMAC createHMAC(int i2) {
        return createHMACForHash(TlsCryptoUtils.getHashForHMAC(i2));
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsHMAC createHMACForHash(int i2) {
        return new BcTlsHMAC(new HMac(createDigest(i2)));
    }

    public TlsHMAC createHMAC_SSL(int i2) {
        if (i2 == 1) {
            return new BcSSL3HMAC(createDigest(1));
        }
        if (i2 == 2) {
            return new BcSSL3HMAC(createDigest(2));
        }
        if (i2 == 3) {
            return new BcSSL3HMAC(createDigest(4));
        }
        if (i2 == 4) {
            return new BcSSL3HMAC(createDigest(5));
        }
        if (i2 == 5) {
            return new BcSSL3HMAC(createDigest(6));
        }
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsHash createHash(int i2) {
        return new BcTlsHash(this, i2);
    }

    public TlsHMAC createMAC(TlsCryptoParameters tlsCryptoParameters, int i2) {
        return TlsImplUtils.isSSL(tlsCryptoParameters) ? createHMAC_SSL(i2) : createHMAC(i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsNonceGenerator createNonceGenerator(byte[] bArr) {
        Digest createDigest = createDigest(4);
        byte[] bArr2 = new byte[createDigest.getDigestSize()];
        getSecureRandom().nextBytes(bArr2);
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(createDigest);
        digestRandomGenerator.addSeedMaterial(bArr);
        digestRandomGenerator.addSeedMaterial(bArr2);
        return new BcTlsNonceGenerator(digestRandomGenerator);
    }

    public TlsNullCipher createNullCipher(TlsCryptoParameters tlsCryptoParameters, int i2) {
        return new TlsNullCipher(tlsCryptoParameters, createMAC(tlsCryptoParameters, i2), createMAC(tlsCryptoParameters, i2));
    }

    public BlockCipher createSEEDEngine() {
        return new SEEDEngine();
    }

    public BlockCipher createSM4Engine() {
        return new SM4Engine();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSRP6Client createSRP6Client(TlsSRPConfig tlsSRPConfig) {
        BigInteger[] explicitNG = tlsSRPConfig.getExplicitNG();
        SRP6GroupParameters sRP6GroupParameters = new SRP6GroupParameters(explicitNG[0], explicitNG[1]);
        SRP6Client sRP6Client = new SRP6Client();
        sRP6Client.init(sRP6GroupParameters, createDigest(2), getSecureRandom());
        return new BcTlsSRP6Client(sRP6Client);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSRP6Server createSRP6Server(TlsSRPConfig tlsSRPConfig, BigInteger bigInteger) {
        BigInteger[] explicitNG = tlsSRPConfig.getExplicitNG();
        SRP6GroupParameters sRP6GroupParameters = new SRP6GroupParameters(explicitNG[0], explicitNG[1]);
        SRP6Server sRP6Server = new SRP6Server();
        sRP6Server.init(sRP6GroupParameters, bigInteger, createDigest(2), getSecureRandom());
        return new BcTlsSRP6Server(sRP6Server);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSRP6VerifierGenerator createSRP6VerifierGenerator(TlsSRPConfig tlsSRPConfig) {
        BigInteger[] explicitNG = tlsSRPConfig.getExplicitNG();
        SRP6VerifierGenerator sRP6VerifierGenerator = new SRP6VerifierGenerator();
        sRP6VerifierGenerator.init(explicitNG[0], explicitNG[1], createDigest(2));
        return new BcTlsSRP6VerifierGenerator(sRP6VerifierGenerator);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSecret createSecret(byte[] bArr) {
        return adoptLocalSecret(Arrays.clone(bArr));
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSecret generateRSAPreMasterSecret(ProtocolVersion protocolVersion) {
        byte[] bArr = new byte[48];
        getSecureRandom().nextBytes(bArr);
        TlsUtils.writeVersion(protocolVersion, bArr, 0);
        return adoptLocalSecret(bArr);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public SecureRandom getSecureRandom() {
        return this.entropySource;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasAllRawSignatureAlgorithms() {
        return (hasSignatureAlgorithm((short) 7) || hasSignatureAlgorithm((short) 8)) ? false : true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasCryptoHashAlgorithm(int i2) {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasCryptoSignatureAlgorithm(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return true;
            default:
                return false;
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasDHAgreement() {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasECDHAgreement() {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasEncryptionAlgorithm(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return false;
            default:
                return true;
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasMacAlgorithm(int i2) {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasNamedGroup(int i2) {
        return NamedGroup.refersToASpecificGroup(i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasRSAEncryption() {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasSRPAuthentication() {
        return true;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasSignatureAlgorithm(short s2) {
        switch (s2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return true;
            default:
                switch (s2) {
                    case 26:
                    case 27:
                    case 28:
                        return true;
                    default:
                        return false;
                }
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasSignatureAndHashAlgorithm(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        short signature = signatureAndHashAlgorithm.getSignature();
        return signatureAndHashAlgorithm.getHash() != 1 ? hasSignatureAlgorithm(signature) : 1 == signature && hasSignatureAlgorithm(signature);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public boolean hasSignatureScheme(int i2) {
        if (i2 == 1800) {
            return false;
        }
        short signatureAlgorithm = SignatureScheme.getSignatureAlgorithm(i2);
        return SignatureScheme.getCryptoHashAlgorithm(i2) != 1 ? hasSignatureAlgorithm(signatureAlgorithm) : 1 == signatureAlgorithm && hasSignatureAlgorithm(signatureAlgorithm);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCrypto
    public TlsSecret hkdfInit(int i2) {
        return adoptLocalSecret(new byte[TlsCryptoUtils.getHashOutputSize(i2)]);
    }

    public BlockCipher createCBCBlockCipher(BlockCipher blockCipher) {
        return new CBCBlockCipher(blockCipher);
    }
}
