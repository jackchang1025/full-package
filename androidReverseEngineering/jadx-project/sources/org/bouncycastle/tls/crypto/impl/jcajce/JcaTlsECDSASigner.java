package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PrivateKey;

/* loaded from: classes.dex */
public class JcaTlsECDSASigner extends JcaTlsDSSSigner {
    public JcaTlsECDSASigner(JcaTlsCrypto jcaTlsCrypto, PrivateKey privateKey) {
        super(jcaTlsCrypto, privateKey, (short) 3, "NoneWithECDSA");
    }
}
