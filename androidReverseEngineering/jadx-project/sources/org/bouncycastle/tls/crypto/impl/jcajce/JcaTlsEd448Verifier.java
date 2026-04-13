package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PublicKey;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

/* loaded from: classes.dex */
public class JcaTlsEd448Verifier extends JcaTlsEdDSAVerifier {
    public JcaTlsEd448Verifier(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey) {
        super(jcaTlsCrypto, publicKey, (short) 8, EdDSAParameterSpec.Ed448);
    }
}
