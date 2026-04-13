package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;

/* loaded from: classes.dex */
public class X400Address implements GeneralNameInterface {
    byte[] nameValue;

    public X400Address(DerValue derValue) {
        this.nameValue = null;
        this.nameValue = derValue.toByteArray();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface != null && generalNameInterface.getType() == 3) {
            throw new UnsupportedOperationException("Narrowing, widening, and match are not supported for X400Address.");
        }
        return -1;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putDerValue(new DerValue(this.nameValue));
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 3;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth not supported for X400Address");
    }

    public String toString() {
        return "X400Address: <DER-encoded value>";
    }

    public X400Address(byte[] bArr) {
        this.nameValue = bArr;
    }
}
