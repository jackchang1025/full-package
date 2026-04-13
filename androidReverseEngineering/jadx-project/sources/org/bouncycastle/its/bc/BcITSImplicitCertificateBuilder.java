package org.bouncycastle.its.bc;

import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.ITSImplicitCertificateBuilder;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;

/* loaded from: classes.dex */
public class BcITSImplicitCertificateBuilder extends ITSImplicitCertificateBuilder {
    public BcITSImplicitCertificateBuilder(ITSCertificate iTSCertificate, ToBeSignedCertificate.Builder builder) {
        super(iTSCertificate, new BcDigestCalculatorProvider(), builder);
    }
}
