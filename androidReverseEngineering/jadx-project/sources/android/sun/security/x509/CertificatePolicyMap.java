package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;

/* loaded from: classes.dex */
public class CertificatePolicyMap {
    private CertificatePolicyId issuerDomain;
    private CertificatePolicyId subjectDomain;

    public CertificatePolicyMap(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for CertificatePolicyMap");
        }
        this.issuerDomain = new CertificatePolicyId(derValue.data.getDerValue());
        this.subjectDomain = new CertificatePolicyId(derValue.data.getDerValue());
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.issuerDomain.encode(derOutputStream2);
        this.subjectDomain.encode(derOutputStream2);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public CertificatePolicyId getIssuerIdentifier() {
        return this.issuerDomain;
    }

    public CertificatePolicyId getSubjectIdentifier() {
        return this.subjectDomain;
    }

    public String toString() {
        return "CertificatePolicyMap: [\nIssuerDomain:" + this.issuerDomain.toString() + "SubjectDomain:" + this.subjectDomain.toString() + "]\n";
    }

    public CertificatePolicyMap(CertificatePolicyId certificatePolicyId, CertificatePolicyId certificatePolicyId2) {
        this.issuerDomain = certificatePolicyId;
        this.subjectDomain = certificatePolicyId2;
    }
}
