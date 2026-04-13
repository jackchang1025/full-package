package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsECDomain;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class BcTlsECDomain implements TlsECDomain {
    protected final BcTlsCrypto crypto;
    protected final TlsECConfig ecConfig;
    protected final ECDomainParameters ecDomainParameters;

    public BcTlsECDomain(BcTlsCrypto bcTlsCrypto, TlsECConfig tlsECConfig) {
        this.crypto = bcTlsCrypto;
        this.ecConfig = tlsECConfig;
        this.ecDomainParameters = getDomainParameters(tlsECConfig);
    }

    public static BcTlsSecret calculateBasicAgreement(BcTlsCrypto bcTlsCrypto, ECPrivateKeyParameters eCPrivateKeyParameters, ECPublicKeyParameters eCPublicKeyParameters) {
        ECDHBasicAgreement eCDHBasicAgreement = new ECDHBasicAgreement();
        eCDHBasicAgreement.init(eCPrivateKeyParameters);
        return bcTlsCrypto.adoptLocalSecret(BigIntegers.asUnsignedByteArray(eCDHBasicAgreement.getFieldSize(), eCDHBasicAgreement.calculateAgreement(eCPublicKeyParameters)));
    }

    public static ECDomainParameters getDomainParameters(int i2) {
        if (!NamedGroup.refersToASpecificCurve(i2)) {
            return null;
        }
        String curveName = NamedGroup.getCurveName(i2);
        X9ECParameters byName = CustomNamedCurves.getByName(curveName);
        if (byName == null && (byName = ECNamedCurveTable.getByName(curveName)) == null) {
            return null;
        }
        return new ECDomainParameters(byName.getCurve(), byName.getG(), byName.getN(), byName.getH(), byName.getSeed());
    }

    public BcTlsSecret calculateECDHAgreement(ECPrivateKeyParameters eCPrivateKeyParameters, ECPublicKeyParameters eCPublicKeyParameters) {
        return calculateBasicAgreement(this.crypto, eCPrivateKeyParameters, eCPublicKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.TlsECDomain
    public TlsAgreement createECDH() {
        return new BcTlsECDH(this);
    }

    public ECPoint decodePoint(byte[] bArr) {
        return this.ecDomainParameters.getCurve().decodePoint(bArr);
    }

    public ECPublicKeyParameters decodePublicKey(byte[] bArr) {
        try {
            return new ECPublicKeyParameters(decodePoint(bArr), this.ecDomainParameters);
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 47, (Throwable) e2);
        }
    }

    public byte[] encodePoint(ECPoint eCPoint) {
        return eCPoint.getEncoded(false);
    }

    public byte[] encodePublicKey(ECPublicKeyParameters eCPublicKeyParameters) {
        return encodePoint(eCPublicKeyParameters.getQ());
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        ECKeyPairGenerator eCKeyPairGenerator = new ECKeyPairGenerator();
        eCKeyPairGenerator.init(new ECKeyGenerationParameters(this.ecDomainParameters, this.crypto.getSecureRandom()));
        return eCKeyPairGenerator.generateKeyPair();
    }

    public static ECDomainParameters getDomainParameters(TlsECConfig tlsECConfig) {
        return getDomainParameters(tlsECConfig.getNamedGroup());
    }
}
