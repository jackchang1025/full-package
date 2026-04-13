package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class DeltaCRLIndicatorExtension extends CRLNumberExtension {
    private static final String LABEL = "Base CRL Number";
    public static final String NAME = "DeltaCRLIndicator";

    public DeltaCRLIndicatorExtension(int i2) {
        super(PKIXExtensions.DeltaCRLIndicator_Id, true, BigInteger.valueOf(i2), NAME, LABEL);
    }

    @Override // android.sun.security.x509.CRLNumberExtension, android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        new DerOutputStream();
        super.encode(outputStream, PKIXExtensions.DeltaCRLIndicator_Id, true);
    }

    public DeltaCRLIndicatorExtension(Boolean bool, Object obj) {
        super(PKIXExtensions.DeltaCRLIndicator_Id, Boolean.valueOf(bool.booleanValue()), obj, NAME, LABEL);
    }

    public DeltaCRLIndicatorExtension(BigInteger bigInteger) {
        super(PKIXExtensions.DeltaCRLIndicator_Id, true, bigInteger, NAME, LABEL);
    }
}
