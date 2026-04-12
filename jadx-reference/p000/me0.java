package p000;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.pqc.jcajce.provider.mceliece.BCMcEliecePrivateKey;
import org.bouncycastle.pqc.jcajce.provider.mceliece.BCMcEliecePublicKey;

/* loaded from: classes2.dex */
public class me0 extends KeyFactorySpi implements InterfaceC0135bi {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";

    private static InterfaceC1236sv getDigest(C1168r5 c1168r5) {
        return new us0();
    }

    @Override // java.security.KeyFactorySpi
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (!(keySpec instanceof PKCS8EncodedKeySpec)) {
            throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
        }
        try {
            io0 io0Var = io0.getInstance(AbstractC0164c9.fromByteArray(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
            try {
                if (!vl0.mcEliece.equals((AbstractC0164c9) io0Var.getPrivateKeyAlgorithm().getAlgorithm())) {
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece private key");
                }
                pe0 pe0Var = pe0.getInstance(io0Var.parsePrivateKey());
                return new BCMcEliecePrivateKey(new qe0(pe0Var.getN(), pe0Var.getK(), pe0Var.getField(), pe0Var.getGoppaPoly(), pe0Var.getP1(), pe0Var.getP2(), pe0Var.getSInv()));
            } catch (IOException unused) {
                throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
            }
        } catch (IOException e) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec: " + e);
        }
    }

    @Override // java.security.KeyFactorySpi
    public PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (!(keySpec instanceof X509EncodedKeySpec)) {
            throw new InvalidKeySpecException("Unsupported key specification: " + keySpec.getClass() + ".");
        }
        try {
            u21 u21Var = u21.getInstance(AbstractC0164c9.fromByteArray(((X509EncodedKeySpec) keySpec).getEncoded()));
            try {
                if (!vl0.mcEliece.equals((AbstractC0164c9) u21Var.getAlgorithm().getAlgorithm())) {
                    throw new InvalidKeySpecException("Unable to recognise OID in McEliece public key");
                }
                re0 re0Var = re0.getInstance(u21Var.parsePublicKey());
                return new BCMcEliecePublicKey(new se0(re0Var.getN(), re0Var.getT(), re0Var.getG()));
            } catch (IOException e) {
                throw new InvalidKeySpecException(AbstractC0003a2.m26a7(e, new StringBuilder("Unable to decode X509EncodedKeySpec: ")));
            }
        } catch (IOException e2) {
            throw new InvalidKeySpecException(e2.toString());
        }
    }

    @Override // java.security.KeyFactorySpi
    public KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        return null;
    }

    @Override // java.security.KeyFactorySpi
    public Key engineTranslateKey(Key key) throws InvalidKeyException {
        return null;
    }

    @Override // p000.InterfaceC0135bi
    public PrivateKey generatePrivate(io0 io0Var) throws IOException {
        pe0 pe0Var = pe0.getInstance(io0Var.parsePrivateKey().toASN1Primitive());
        return new BCMcEliecePrivateKey(new qe0(pe0Var.getN(), pe0Var.getK(), pe0Var.getField(), pe0Var.getGoppaPoly(), pe0Var.getP1(), pe0Var.getP2(), pe0Var.getSInv()));
    }

    @Override // p000.InterfaceC0135bi
    public PublicKey generatePublic(u21 u21Var) throws IOException {
        re0 re0Var = re0.getInstance(u21Var.parsePublicKey());
        return new BCMcEliecePublicKey(new se0(re0Var.getN(), re0Var.getT(), re0Var.getG()));
    }

    public KeySpec getKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof BCMcEliecePrivateKey) {
            if (PKCS8EncodedKeySpec.class.isAssignableFrom(cls)) {
                return new PKCS8EncodedKeySpec(key.getEncoded());
            }
        } else {
            if (!(key instanceof BCMcEliecePublicKey)) {
                throw new InvalidKeySpecException("Unsupported key type: " + key.getClass() + ".");
            }
            if (X509EncodedKeySpec.class.isAssignableFrom(cls)) {
                return new X509EncodedKeySpec(key.getEncoded());
            }
        }
        throw new InvalidKeySpecException("Unknown key specification: " + cls + ".");
    }

    public Key translateKey(Key key) throws InvalidKeyException {
        if ((key instanceof BCMcEliecePrivateKey) || (key instanceof BCMcEliecePublicKey)) {
            return key;
        }
        throw new InvalidKeyException("Unsupported key type.");
    }
}
