package android.sun.security.x509;

import java.io.OutputStream;
import java.util.List;

/* loaded from: classes.dex */
public class FreshestCRLExtension extends CRLDistributionPointsExtension {
    public static final String NAME = "FreshestCRL";

    public FreshestCRLExtension(Boolean bool, Object obj) {
        super(PKIXExtensions.FreshestCRL_Id, Boolean.valueOf(bool.booleanValue()), obj, NAME);
    }

    @Override // android.sun.security.x509.CRLDistributionPointsExtension, android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        super.encode(outputStream, PKIXExtensions.FreshestCRL_Id, false);
    }

    public FreshestCRLExtension(List<DistributionPoint> list) {
        super(PKIXExtensions.FreshestCRL_Id, false, list, NAME);
    }
}
