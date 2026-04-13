package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PublicKey;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

/* loaded from: classes.dex */
public class JcaTlsEd25519Verifier extends JcaTlsEdDSAVerifier {
    public JcaTlsEd25519Verifier(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey) {
        super(jcaTlsCrypto, publicKey, (short) 7, EdDSAParameterSpec.Ed25519);
    }
}
