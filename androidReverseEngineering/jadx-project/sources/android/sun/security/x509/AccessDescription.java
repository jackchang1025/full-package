package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class AccessDescription {
    private GeneralName accessLocation;
    private ObjectIdentifier accessMethod;
    private int myhash = -1;
    public static final ObjectIdentifier Ad_OCSP_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 1});
    public static final ObjectIdentifier Ad_CAISSUERS_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 2});
    public static final ObjectIdentifier Ad_TIMESTAMPING_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 3});
    public static final ObjectIdentifier Ad_CAREPOSITORY_Id = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 5});

    public AccessDescription(DerValue derValue) {
        DerInputStream data = derValue.getData();
        this.accessMethod = data.getOID();
        this.accessLocation = new GeneralName(data.getDerValue());
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.accessMethod);
        this.accessLocation.encode(derOutputStream2);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AccessDescription)) {
            return false;
        }
        AccessDescription accessDescription = (AccessDescription) obj;
        if (this == accessDescription) {
            return true;
        }
        return this.accessMethod.equals(accessDescription.getAccessMethod()) && this.accessLocation.equals(accessDescription.getAccessLocation());
    }

    public GeneralName getAccessLocation() {
        return this.accessLocation;
    }

    public ObjectIdentifier getAccessMethod() {
        return this.accessMethod;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.accessLocation.hashCode() + this.accessMethod.hashCode();
        }
        return this.myhash;
    }

    public String toString() {
        StringBuilder m23s = AbstractC0000a.m23s("\n   accessMethod: ", this.accessMethod.equals(Ad_CAISSUERS_Id) ? "caIssuers" : this.accessMethod.equals(Ad_CAREPOSITORY_Id) ? "caRepository" : this.accessMethod.equals(Ad_TIMESTAMPING_Id) ? "timeStamping" : this.accessMethod.equals(Ad_OCSP_Id) ? "ocsp" : this.accessMethod.toString(), "\n   accessLocation: ");
        m23s.append(this.accessLocation.toString());
        m23s.append("\n");
        return m23s.toString();
    }

    public AccessDescription(ObjectIdentifier objectIdentifier, GeneralName generalName) {
        this.accessMethod = objectIdentifier;
        this.accessLocation = generalName;
    }
}
