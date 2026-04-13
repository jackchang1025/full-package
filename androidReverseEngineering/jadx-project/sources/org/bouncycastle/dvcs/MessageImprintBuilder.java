package org.bouncycastle.dvcs;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.DigestCalculator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class MessageImprintBuilder {
    private final DigestCalculator digestCalculator;

    public MessageImprintBuilder(DigestCalculator digestCalculator) {
        this.digestCalculator = digestCalculator;
    }

    public MessageImprint build(byte[] bArr) {
        try {
            OutputStream outputStream = this.digestCalculator.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
            return new MessageImprint(new DigestInfo(this.digestCalculator.getAlgorithmIdentifier(), this.digestCalculator.getDigest()));
        } catch (Exception e2) {
            throw new DVCSException(AbstractC0000a.m9e(e2, new StringBuilder("unable to build MessageImprint: ")), e2);
        }
    }
}
