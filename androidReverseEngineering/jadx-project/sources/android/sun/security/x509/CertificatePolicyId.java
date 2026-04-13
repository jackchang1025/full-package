package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;

/* loaded from: classes.dex */
public class CertificatePolicyId {
    private ObjectIdentifier id;

    public CertificatePolicyId(DerValue derValue) {
        this.id = derValue.getOID();
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putOID(this.id);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CertificatePolicyId) {
            return this.id.equals(((CertificatePolicyId) obj).getIdentifier());
        }
        return false;
    }

    public ObjectIdentifier getIdentifier() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return "CertificatePolicyId: [" + this.id.toString() + "]\n";
    }

    public CertificatePolicyId(ObjectIdentifier objectIdentifier) {
        this.id = objectIdentifier;
    }
}
