package org.bouncycastle.tls.crypto;

import java.math.BigInteger;

/* loaded from: classes.dex */
public interface TlsSRP6Server {
    BigInteger calculateSecret(BigInteger bigInteger);

    BigInteger generateServerCredentials();
}
