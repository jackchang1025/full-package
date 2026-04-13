package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/* loaded from: classes.dex */
public class CertificatePolicySet {
    private final Vector<CertificatePolicyId> ids;

    public CertificatePolicySet(DerInputStream derInputStream) {
        this.ids = new Vector<>();
        for (DerValue derValue : derInputStream.getSequence(5)) {
            this.ids.addElement(new CertificatePolicyId(derValue));
        }
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (int i2 = 0; i2 < this.ids.size(); i2++) {
            this.ids.elementAt(i2).encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public List<CertificatePolicyId> getCertPolicyIds() {
        return Collections.unmodifiableList(this.ids);
    }

    public String toString() {
        return "CertificatePolicySet:[\n" + this.ids.toString() + "]\n";
    }

    public CertificatePolicySet(Vector<CertificatePolicyId> vector) {
        this.ids = vector;
    }
}
