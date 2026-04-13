package org.conscrypt;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLX509CertificateFactory;

/* loaded from: classes.dex */
final class OpenSSLECPrivateKey implements ECPrivateKey, OpenSSLKeyHolder {
    private static final String ALGORITHM = "EC";
    private static final long serialVersionUID = -4036633595001083922L;
    protected transient OpenSSLECGroupContext group;
    protected transient OpenSSLKey key;

    public OpenSSLECPrivateKey(ECPrivateKeySpec eCPrivateKeySpec) {
        try {
            this.group = OpenSSLECGroupContext.getInstance(eCPrivateKeySpec.getParams());
            this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(this.group.getNativeRef(), null, eCPrivateKeySpec.getS().toByteArray()));
        } catch (Exception e2) {
            throw new InvalidKeySpecException(e2);
        }
    }

    public static OpenSSLKey getInstance(ECPrivateKey eCPrivateKey) {
        try {
            OpenSSLECGroupContext openSSLECGroupContext = OpenSSLECGroupContext.getInstance(eCPrivateKey.getParams());
            if (eCPrivateKey.getFormat() == null) {
                return wrapPlatformKey(eCPrivateKey, openSSLECGroupContext);
            }
            return new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(openSSLECGroupContext.getNativeRef(), null, eCPrivateKey.getS().toByteArray()));
        } catch (Exception e2) {
            throw new InvalidKeyException(e2);
        }
    }

    private BigInteger getPrivateKey() {
        return new BigInteger(NativeCrypto.EC_KEY_get_private_key(this.key.getNativeRef()));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        try {
            this.key = new OpenSSLKey(NativeCrypto.EVP_parse_private_key((byte[]) objectInputStream.readObject()));
            this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(this.key.getNativeRef())));
        } catch (OpenSSLX509CertificateFactory.ParsingException e2) {
            throw new IOException(e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) {
        ECParameterSpec eCParameterSpec;
        ECKey eCKey;
        if (privateKey instanceof ECKey) {
            eCKey = (ECKey) privateKey;
        } else {
            if (!(publicKey instanceof ECKey)) {
                eCParameterSpec = null;
                if (eCParameterSpec == null) {
                    return wrapJCAPrivateKeyForTLSStackOnly(privateKey, eCParameterSpec);
                }
                throw new InvalidKeyException("EC parameters not available. Private: " + privateKey + ", public: " + publicKey);
            }
            eCKey = (ECKey) publicKey;
        }
        eCParameterSpec = eCKey.getParams();
        if (eCParameterSpec == null) {
        }
    }

    public static OpenSSLKey wrapPlatformKey(ECPrivateKey eCPrivateKey) {
        try {
            return wrapPlatformKey(eCPrivateKey, OpenSSLECGroupContext.getInstance(eCPrivateKey.getParams()));
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException("Unknown group parameters", e2);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        if (this.key.isHardwareBacked()) {
            throw new NotSerializableException("Hardware backed keys cannot be serialized");
        }
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OpenSSLECPrivateKey) {
            return this.key.equals(((OpenSSLECPrivateKey) obj).key);
        }
        if (!(obj instanceof ECPrivateKey)) {
            return false;
        }
        ECPrivateKey eCPrivateKey = (ECPrivateKey) obj;
        if (!getPrivateKey().equals(eCPrivateKey.getS())) {
            return false;
        }
        ECParameterSpec params = getParams();
        ECParameterSpec params2 = eCPrivateKey.getParams();
        return params.getCurve().equals(params2.getCurve()) && params.getGenerator().equals(params2.getGenerator()) && params.getOrder().equals(params2.getOrder()) && params.getCofactor() == params2.getCofactor();
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return ALGORITHM;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override // java.security.Key
    public String getFormat() {
        if (this.key.isHardwareBacked()) {
            return null;
        }
        return "PKCS#8";
    }

    @Override // org.conscrypt.OpenSSLKeyHolder
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.group.getECParameterSpec();
    }

    @Override // java.security.interfaces.ECPrivateKey
    public BigInteger getS() {
        if (this.key.isHardwareBacked()) {
            throw new UnsupportedOperationException("Private key value S cannot be extracted");
        }
        return getPrivateKey();
    }

    public int hashCode() {
        return Arrays.hashCode(NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef()));
    }

    public String toString() {
        return "OpenSSLECPrivateKey{params={" + NativeCrypto.EVP_PKEY_print_params(this.key.getNativeRef()) + "}}";
    }

    public OpenSSLECPrivateKey(OpenSSLECGroupContext openSSLECGroupContext, OpenSSLKey openSSLKey) {
        this.group = openSSLECGroupContext;
        this.key = openSSLKey;
    }

    public static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec == null && (privateKey instanceof ECKey)) {
            eCParameterSpec = ((ECKey) privateKey).getParams();
        }
        if (eCParameterSpec == null) {
            throw new InvalidKeyException("EC parameters not available: " + privateKey);
        }
        try {
            return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(privateKey, OpenSSLECGroupContext.getInstance(eCParameterSpec).getNativeRef()), true);
        } catch (InvalidAlgorithmParameterException unused) {
            throw new InvalidKeyException("Invalid EC parameters: " + eCParameterSpec);
        }
    }

    private static OpenSSLKey wrapPlatformKey(ECPrivateKey eCPrivateKey, OpenSSLECGroupContext openSSLECGroupContext) {
        return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(eCPrivateKey, openSSLECGroupContext.getNativeRef()), true);
    }

    public OpenSSLECPrivateKey(OpenSSLKey openSSLKey) {
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(openSSLKey.getNativeRef())));
        this.key = openSSLKey;
    }
}
