package org.bouncycastle.tls.crypto.impl.bc;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.tls.TlsDHUtils;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.DHGroup;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsDHDomain;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class BcTlsDHDomain implements TlsDHDomain {
    protected BcTlsCrypto crypto;
    protected TlsDHConfig dhConfig;
    protected DHParameters dhParameters;

    public BcTlsDHDomain(BcTlsCrypto bcTlsCrypto, TlsDHConfig tlsDHConfig) {
        this.crypto = bcTlsCrypto;
        this.dhConfig = tlsDHConfig;
        this.dhParameters = getParameters(tlsDHConfig);
    }

    private static byte[] encodeValue(DHParameters dHParameters, boolean z2, BigInteger bigInteger) {
        return z2 ? BigIntegers.asUnsignedByteArray(getValueLength(dHParameters), bigInteger) : BigIntegers.asUnsignedByteArray(bigInteger);
    }

    public static DHParameters getParameters(TlsDHConfig tlsDHConfig) {
        DHGroup dHGroup = TlsDHUtils.getDHGroup(tlsDHConfig);
        if (dHGroup != null) {
            return new DHParameters(dHGroup.getP(), dHGroup.getG(), dHGroup.getQ(), dHGroup.getL());
        }
        throw new IllegalArgumentException("No DH configuration provided");
    }

    private static int getValueLength(DHParameters dHParameters) {
        return (dHParameters.getP().bitLength() + 7) / 8;
    }

    public BcTlsSecret calculateDHAgreement(DHPrivateKeyParameters dHPrivateKeyParameters, DHPublicKeyParameters dHPublicKeyParameters) {
        return calculateDHAgreement(this.crypto, dHPrivateKeyParameters, dHPublicKeyParameters, this.dhConfig.isPadded());
    }

    @Override // org.bouncycastle.tls.crypto.TlsDHDomain
    public TlsAgreement createDH() {
        return new BcTlsDH(this);
    }

    public BigInteger decodeParameter(byte[] bArr) {
        if (!this.dhConfig.isPadded() || getValueLength(this.dhParameters) == bArr.length) {
            return new BigInteger(1, bArr);
        }
        throw new TlsFatalAlert((short) 47);
    }

    public DHPublicKeyParameters decodePublicKey(byte[] bArr) {
        try {
            return new DHPublicKeyParameters(decodeParameter(bArr), this.dhParameters);
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 40, (Throwable) e2);
        }
    }

    public byte[] encodeParameter(BigInteger bigInteger) {
        return encodeValue(this.dhParameters, this.dhConfig.isPadded(), bigInteger);
    }

    public byte[] encodePublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        return encodeValue(this.dhParameters, true, dHPublicKeyParameters.getY());
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        DHBasicKeyPairGenerator dHBasicKeyPairGenerator = new DHBasicKeyPairGenerator();
        dHBasicKeyPairGenerator.init(new DHKeyGenerationParameters(this.crypto.getSecureRandom(), this.dhParameters));
        return dHBasicKeyPairGenerator.generateKeyPair();
    }

    public static BcTlsSecret calculateDHAgreement(BcTlsCrypto bcTlsCrypto, DHPrivateKeyParameters dHPrivateKeyParameters, DHPublicKeyParameters dHPublicKeyParameters, boolean z2) {
        DHBasicAgreement dHBasicAgreement = new DHBasicAgreement();
        dHBasicAgreement.init(dHPrivateKeyParameters);
        return bcTlsCrypto.adoptLocalSecret(encodeValue(dHPrivateKeyParameters.getParameters(), z2, dHBasicAgreement.calculateAgreement(dHPublicKeyParameters)));
    }
}
