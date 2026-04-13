package org.bouncycastle.openssl.jcajce;

import java.security.PrivateKey;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.operator.OutputEncryptor;

/* loaded from: classes.dex */
public class JcaPKCS8Generator extends PKCS8Generator {
    public JcaPKCS8Generator(PrivateKey privateKey, OutputEncryptor outputEncryptor) {
        super(PrivateKeyInfo.getInstance(privateKey.getEncoded()), outputEncryptor);
    }
}
