package org.bouncycastle.jcajce.provider.asymmetric.edec;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.params.Ed448PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed448PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.signers.Ed448Signer;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

/* loaded from: classes.dex */
public class SignatureSpi extends java.security.SignatureSpi {
    private static final byte[] EMPTY_CONTEXT = new byte[0];
    private final String algorithm;
    private Signer signer;

    public static final class Ed25519 extends SignatureSpi {
        public Ed25519() {
            super(EdDSAParameterSpec.Ed25519);
        }
    }

    public static final class Ed448 extends SignatureSpi {
        public Ed448() {
            super(EdDSAParameterSpec.Ed448);
        }
    }

    public static final class EdDSA extends SignatureSpi {
        public EdDSA() {
            super(null);
        }
    }

    public SignatureSpi(String str) {
        this.algorithm = str;
    }

    private static AsymmetricKeyParameter getLwEdDSAKeyPrivate(Key key) {
        if (key instanceof BCEdDSAPrivateKey) {
            return ((BCEdDSAPrivateKey) key).engineGetKeyParameters();
        }
        throw new InvalidKeyException("cannot identify EdDSA private key");
    }

    private static AsymmetricKeyParameter getLwEdDSAKeyPublic(Key key) {
        if (key instanceof BCEdDSAPublicKey) {
            return ((BCEdDSAPublicKey) key).engineGetKeyParameters();
        }
        throw new InvalidKeyException("cannot identify EdDSA public key");
    }

    private Signer getSigner(String str) {
        String str2 = this.algorithm;
        if (str2 == null || str.equals(str2)) {
            return str.equals(EdDSAParameterSpec.Ed448) ? new Ed448Signer(EMPTY_CONTEXT) : new Ed25519Signer();
        }
        throw new InvalidKeyException("inappropriate key for " + this.algorithm);
    }

    @Override // java.security.SignatureSpi
    public Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey) {
        String str;
        AsymmetricKeyParameter lwEdDSAKeyPrivate = getLwEdDSAKeyPrivate(privateKey);
        if (lwEdDSAKeyPrivate instanceof Ed25519PrivateKeyParameters) {
            str = EdDSAParameterSpec.Ed25519;
        } else {
            if (!(lwEdDSAKeyPrivate instanceof Ed448PrivateKeyParameters)) {
                throw new IllegalStateException("unsupported private key type");
            }
            str = EdDSAParameterSpec.Ed448;
        }
        this.signer = getSigner(str);
        this.signer.init(true, lwEdDSAKeyPrivate);
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) {
        String str;
        AsymmetricKeyParameter lwEdDSAKeyPublic = getLwEdDSAKeyPublic(publicKey);
        if (lwEdDSAKeyPublic instanceof Ed25519PublicKeyParameters) {
            str = EdDSAParameterSpec.Ed25519;
        } else {
            if (!(lwEdDSAKeyPublic instanceof Ed448PublicKeyParameters)) {
                throw new IllegalStateException("unsupported public key type");
            }
            str = EdDSAParameterSpec.Ed448;
        }
        this.signer = getSigner(str);
        this.signer.init(false, lwEdDSAKeyPublic);
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() {
        try {
            return this.signer.generateSignature();
        } catch (CryptoException e2) {
            throw new SignatureException(e2.getMessage());
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte b) {
        this.signer.update(b);
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) {
        return this.signer.verifySignature(bArr);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        this.signer.update(bArr, i2, i3);
    }
}
