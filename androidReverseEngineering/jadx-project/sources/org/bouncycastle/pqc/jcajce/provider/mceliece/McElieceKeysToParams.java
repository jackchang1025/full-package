package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;

/* loaded from: classes.dex */
public class McElieceKeysToParams {
    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (!(privateKey instanceof BCMcEliecePrivateKey)) {
            throw new InvalidKeyException("can't identify McEliece private key.");
        }
        BCMcEliecePrivateKey bCMcEliecePrivateKey = (BCMcEliecePrivateKey) privateKey;
        return new McEliecePrivateKeyParameters(bCMcEliecePrivateKey.getN(), bCMcEliecePrivateKey.getK(), bCMcEliecePrivateKey.getField(), bCMcEliecePrivateKey.getGoppaPoly(), bCMcEliecePrivateKey.getP1(), bCMcEliecePrivateKey.getP2(), bCMcEliecePrivateKey.getSInv());
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof BCMcEliecePublicKey) {
            return ((BCMcEliecePublicKey) publicKey).getKeyParams();
        }
        throw new InvalidKeyException("can't identify McEliece public key: ".concat(publicKey.getClass().getName()));
    }
}
