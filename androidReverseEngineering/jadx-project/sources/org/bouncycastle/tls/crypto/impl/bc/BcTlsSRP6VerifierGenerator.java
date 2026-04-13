package org.bouncycastle.tls.crypto.impl.bc;

import java.math.BigInteger;
import org.bouncycastle.crypto.agreement.srp.SRP6VerifierGenerator;
import org.bouncycastle.tls.crypto.TlsSRP6VerifierGenerator;

/* loaded from: classes.dex */
final class BcTlsSRP6VerifierGenerator implements TlsSRP6VerifierGenerator {
    private final SRP6VerifierGenerator srp6VerifierGenerator;

    public BcTlsSRP6VerifierGenerator(SRP6VerifierGenerator sRP6VerifierGenerator) {
        this.srp6VerifierGenerator = sRP6VerifierGenerator;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSRP6VerifierGenerator
    public BigInteger generateVerifier(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return this.srp6VerifierGenerator.generateVerifier(bArr, bArr2, bArr3);
    }
}
