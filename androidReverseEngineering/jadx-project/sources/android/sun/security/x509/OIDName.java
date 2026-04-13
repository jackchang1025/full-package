package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OIDName implements GeneralNameInterface {
    private ObjectIdentifier oid;

    public OIDName(DerValue derValue) {
        this.oid = derValue.getOID();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 8) {
            return -1;
        }
        if (equals((OIDName) generalNameInterface)) {
            return 0;
        }
        throw new UnsupportedOperationException("Narrowing and widening are not supported for OIDNames");
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putOID(this.oid);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OIDName) {
            return this.oid.equals(((OIDName) obj).oid);
        }
        return false;
    }

    public ObjectIdentifier getOID() {
        return this.oid;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 8;
    }

    public int hashCode() {
        return this.oid.hashCode();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth() not supported for OIDName.");
    }

    public String toString() {
        return "OIDName: " + this.oid.toString();
    }

    public OIDName(ObjectIdentifier objectIdentifier) {
        this.oid = objectIdentifier;
    }

    public OIDName(String str) {
        try {
            this.oid = new ObjectIdentifier(str);
        } catch (Exception e2) {
            throw new IOException(AbstractC0000a.m14j("Unable to create OIDName: ", e2));
        }
    }
}
