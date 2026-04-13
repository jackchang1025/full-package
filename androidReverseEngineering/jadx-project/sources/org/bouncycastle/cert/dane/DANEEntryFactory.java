package org.bouncycastle.cert.dane;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.DigestCalculator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DANEEntryFactory {
    private final DANEEntrySelectorFactory selectorFactory;

    public DANEEntryFactory(DigestCalculator digestCalculator) {
        this.selectorFactory = new DANEEntrySelectorFactory(digestCalculator);
    }

    public DANEEntry createEntry(String str, int i2, X509CertificateHolder x509CertificateHolder) {
        if (i2 < 0 || i2 > 3) {
            throw new DANEException(AbstractC0000a.m11g("unknown certificate usage: ", i2));
        }
        return new DANEEntry(this.selectorFactory.createSelector(str).getDomainName(), new byte[]{(byte) i2, 0, 0}, x509CertificateHolder);
    }

    public DANEEntry createEntry(String str, X509CertificateHolder x509CertificateHolder) {
        return createEntry(str, 3, x509CertificateHolder);
    }
}
