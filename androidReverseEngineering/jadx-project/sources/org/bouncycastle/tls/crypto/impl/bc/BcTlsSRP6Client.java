package org.bouncycastle.tls.crypto.impl.bc;

import java.math.BigInteger;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsSRP6Client;

/* loaded from: classes.dex */
final class BcTlsSRP6Client implements TlsSRP6Client {
    private final SRP6Client srp6Client;

    public BcTlsSRP6Client(SRP6Client sRP6Client) {
        this.srp6Client = sRP6Client;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSRP6Client
    public BigInteger calculateSecret(BigInteger bigInteger) {
        try {
            return this.srp6Client.calculateSecret(bigInteger);
        } catch (CryptoException e2) {
            throw new TlsFatalAlert((short) 47, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsSRP6Client
    public BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return this.srp6Client.generateClientCredentials(bArr, bArr2, bArr3);
    }
}
