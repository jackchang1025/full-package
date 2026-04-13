package org.bouncycastle.tls.crypto.impl.jcajce;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.tls.TlsDHUtils;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.DHGroup;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCryptoException;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsDHDomain;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class JceTlsDHDomain implements TlsDHDomain {
    protected final JcaTlsCrypto crypto;
    protected final TlsDHConfig dhConfig;
    protected final DHParameterSpec dhSpec;

    public JceTlsDHDomain(JcaTlsCrypto jcaTlsCrypto, TlsDHConfig tlsDHConfig) {
        DHParameterSpec dHParameterSpec;
        DHGroup dHGroup = TlsDHUtils.getDHGroup(tlsDHConfig);
        if (dHGroup == null || (dHParameterSpec = DHUtil.getDHParameterSpec(jcaTlsCrypto, dHGroup)) == null) {
            throw new IllegalArgumentException("No DH configuration provided");
        }
        this.crypto = jcaTlsCrypto;
        this.dhConfig = tlsDHConfig;
        this.dhSpec = dHParameterSpec;
    }

    private static byte[] encodeValue(DHParameterSpec dHParameterSpec, boolean z2, BigInteger bigInteger) {
        return z2 ? BigIntegers.asUnsignedByteArray(getValueLength(dHParameterSpec), bigInteger) : BigIntegers.asUnsignedByteArray(bigInteger);
    }

    private static int getValueLength(DHParameterSpec dHParameterSpec) {
        return (dHParameterSpec.getP().bitLength() + 7) / 8;
    }

    public JceTlsSecret calculateDHAgreement(DHPrivateKey dHPrivateKey, DHPublicKey dHPublicKey) {
        return calculateDHAgreement(this.crypto, dHPrivateKey, dHPublicKey, this.dhConfig.isPadded());
    }

    @Override // org.bouncycastle.tls.crypto.TlsDHDomain
    public TlsAgreement createDH() {
        return new JceTlsDH(this);
    }

    public BigInteger decodeParameter(byte[] bArr) {
        if (!this.dhConfig.isPadded() || getValueLength(this.dhSpec) == bArr.length) {
            return new BigInteger(1, bArr);
        }
        throw new TlsFatalAlert((short) 47);
    }

    public DHPublicKey decodePublicKey(byte[] bArr) {
        try {
            return (DHPublicKey) this.crypto.getHelper().createKeyFactory("DiffieHellman").generatePublic(DHUtil.createPublicKeySpec(decodeParameter(bArr), this.dhSpec));
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new TlsFatalAlert((short) 40, (Throwable) e3);
        }
    }

    public byte[] encodeParameter(BigInteger bigInteger) {
        return encodeValue(this.dhSpec, this.dhConfig.isPadded(), bigInteger);
    }

    public byte[] encodePublicKey(DHPublicKey dHPublicKey) {
        return encodeValue(this.dhSpec, true, dHPublicKey.getY());
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator createKeyPairGenerator = this.crypto.getHelper().createKeyPairGenerator("DiffieHellman");
            createKeyPairGenerator.initialize(this.dhSpec, this.crypto.getSecureRandom());
            return createKeyPairGenerator.generateKeyPair();
        } catch (GeneralSecurityException e2) {
            throw new TlsCryptoException("unable to create key pair", e2);
        }
    }

    public static JceTlsSecret calculateDHAgreement(JcaTlsCrypto jcaTlsCrypto, DHPrivateKey dHPrivateKey, DHPublicKey dHPublicKey, boolean z2) {
        try {
            byte[] calculateKeyAgreement = jcaTlsCrypto.calculateKeyAgreement("DiffieHellman", dHPrivateKey, dHPublicKey, "TlsPremasterSecret");
            if (z2) {
                int valueLength = getValueLength(dHPrivateKey.getParams());
                byte[] bArr = new byte[valueLength];
                System.arraycopy(calculateKeyAgreement, 0, bArr, valueLength - calculateKeyAgreement.length, calculateKeyAgreement.length);
                Arrays.fill(calculateKeyAgreement, (byte) 0);
                calculateKeyAgreement = bArr;
            }
            return jcaTlsCrypto.adoptLocalSecret(calculateKeyAgreement);
        } catch (GeneralSecurityException e2) {
            throw new TlsCryptoException("cannot calculate secret", e2);
        }
    }
}
