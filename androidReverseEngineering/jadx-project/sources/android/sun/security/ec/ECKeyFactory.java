package android.sun.security.ec;

import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyFactorySpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes.dex */
public final class ECKeyFactory extends KeyFactorySpi {
    public static final KeyFactory INSTANCE;
    public static final Provider ecInternalProvider;

    static {
        final Provider provider = new Provider("SunEC-Internal", 1.0d, null) { // from class: android.sun.security.ec.ECKeyFactory.1
        };
        AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: android.sun.security.ec.ECKeyFactory.2
            @Override // java.security.PrivilegedAction
            public Void run() {
                provider.put("KeyFactory.EC", "android.sun.security.ec.ECKeyFactory");
                provider.put("AlgorithmParameters.EC", "android.sun.security.ec.ECParameters");
                provider.put("Alg.Alias.AlgorithmParameters.1.2.840.10045.2.1", "EC");
                return null;
            }
        });
        try {
            INSTANCE = KeyFactory.getInstance("EC", provider);
            ecInternalProvider = provider;
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static void checkKey(ECKey eCKey) {
        if (eCKey instanceof ECPublicKey) {
            if (eCKey instanceof ECPublicKeyImpl) {
                return;
            }
        } else {
            if (!(eCKey instanceof ECPrivateKey)) {
                throw new InvalidKeyException("Neither a public nor a private key");
            }
            if (eCKey instanceof ECPrivateKeyImpl) {
                return;
            }
        }
        String algorithm = ((Key) eCKey).getAlgorithm();
        if (!algorithm.equals("EC")) {
            throw new InvalidKeyException("Not an EC key: ".concat(algorithm));
        }
    }

    private PrivateKey implGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            return new ECPrivateKeyImpl(((PKCS8EncodedKeySpec) keySpec).getEncoded());
        }
        if (!(keySpec instanceof ECPrivateKeySpec)) {
            throw new InvalidKeySpecException("Only ECPrivateKeySpec and PKCS8EncodedKeySpec supported for EC private keys");
        }
        ECPrivateKeySpec eCPrivateKeySpec = (ECPrivateKeySpec) keySpec;
        return new ECPrivateKeyImpl(eCPrivateKeySpec.getS(), eCPrivateKeySpec.getParams());
    }

    private PublicKey implGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof X509EncodedKeySpec) {
            return new ECPublicKeyImpl(((X509EncodedKeySpec) keySpec).getEncoded());
        }
        if (!(keySpec instanceof ECPublicKeySpec)) {
            throw new InvalidKeySpecException("Only ECPublicKeySpec and X509EncodedKeySpec supported for EC public keys");
        }
        ECPublicKeySpec eCPublicKeySpec = (ECPublicKeySpec) keySpec;
        return new ECPublicKeyImpl(eCPublicKeySpec.getW(), eCPublicKeySpec.getParams());
    }

    private PrivateKey implTranslatePrivateKey(PrivateKey privateKey) {
        if (!(privateKey instanceof ECPrivateKey)) {
            if ("PKCS#8".equals(privateKey.getFormat())) {
                return new ECPrivateKeyImpl(privateKey.getEncoded());
            }
            throw new InvalidKeyException("Private keys must be instance of ECPrivateKey or have PKCS#8 encoding");
        }
        if (privateKey instanceof ECPrivateKeyImpl) {
            return privateKey;
        }
        ECPrivateKey eCPrivateKey = (ECPrivateKey) privateKey;
        return new ECPrivateKeyImpl(eCPrivateKey.getS(), eCPrivateKey.getParams());
    }

    private PublicKey implTranslatePublicKey(PublicKey publicKey) {
        if (!(publicKey instanceof ECPublicKey)) {
            if ("X.509".equals(publicKey.getFormat())) {
                return new ECPublicKeyImpl(publicKey.getEncoded());
            }
            throw new InvalidKeyException("Public keys must be instance of ECPublicKey or have X.509 encoding");
        }
        if (publicKey instanceof ECPublicKeyImpl) {
            return publicKey;
        }
        ECPublicKey eCPublicKey = (ECPublicKey) publicKey;
        return new ECPublicKeyImpl(eCPublicKey.getW(), eCPublicKey.getParams());
    }

    public static ECKey toECKey(Key key) {
        if (!(key instanceof ECKey)) {
            return (ECKey) INSTANCE.translateKey(key);
        }
        ECKey eCKey = (ECKey) key;
        checkKey(eCKey);
        return eCKey;
    }

    @Override // java.security.KeyFactorySpi
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        try {
            return implGeneratePrivate(keySpec);
        } catch (InvalidKeySpecException e2) {
            throw e2;
        } catch (GeneralSecurityException e3) {
            throw new InvalidKeySpecException(e3);
        }
    }

    @Override // java.security.KeyFactorySpi
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        try {
            return implGeneratePublic(keySpec);
        } catch (InvalidKeySpecException e2) {
            throw e2;
        } catch (GeneralSecurityException e3) {
            throw new InvalidKeySpecException(e3);
        }
    }

    @Override // java.security.KeyFactorySpi
    public <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> cls) {
        try {
            Key engineTranslateKey = engineTranslateKey(key);
            if (engineTranslateKey instanceof ECPublicKey) {
                ECPublicKey eCPublicKey = (ECPublicKey) engineTranslateKey;
                if (ECPublicKeySpec.class.isAssignableFrom(cls)) {
                    return new ECPublicKeySpec(eCPublicKey.getW(), eCPublicKey.getParams());
                }
                if (X509EncodedKeySpec.class.isAssignableFrom(cls)) {
                    return new X509EncodedKeySpec(engineTranslateKey.getEncoded());
                }
                throw new InvalidKeySpecException("KeySpec must be ECPublicKeySpec or X509EncodedKeySpec for EC public keys");
            }
            if (!(engineTranslateKey instanceof ECPrivateKey)) {
                throw new InvalidKeySpecException("Neither public nor private key");
            }
            if (PKCS8EncodedKeySpec.class.isAssignableFrom(cls)) {
                return new PKCS8EncodedKeySpec(engineTranslateKey.getEncoded());
            }
            if (!ECPrivateKeySpec.class.isAssignableFrom(cls)) {
                throw new InvalidKeySpecException("KeySpec must be ECPrivateKeySpec or PKCS8EncodedKeySpec for EC private keys");
            }
            ECPrivateKey eCPrivateKey = (ECPrivateKey) engineTranslateKey;
            return new ECPrivateKeySpec(eCPrivateKey.getS(), eCPrivateKey.getParams());
        } catch (InvalidKeyException e2) {
            throw new InvalidKeySpecException(e2);
        }
    }

    @Override // java.security.KeyFactorySpi
    public Key engineTranslateKey(Key key) {
        if (key == null) {
            throw new InvalidKeyException("Key must not be null");
        }
        String algorithm = key.getAlgorithm();
        if (!algorithm.equals("EC")) {
            throw new InvalidKeyException("Not an EC key: ".concat(algorithm));
        }
        if (key instanceof PublicKey) {
            return implTranslatePublicKey((PublicKey) key);
        }
        if (key instanceof PrivateKey) {
            return implTranslatePrivateKey((PrivateKey) key);
        }
        throw new InvalidKeyException("Neither a public nor a private key");
    }
}
