package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OtherName implements GeneralNameInterface {
    private static final byte TAG_VALUE = 0;
    private GeneralNameInterface gni;
    private int myhash = -1;
    private String name;
    private byte[] nameValue;
    private ObjectIdentifier oid;

    public OtherName(DerValue derValue) {
        String str;
        this.nameValue = null;
        this.gni = null;
        DerInputStream derInputStream = derValue.toDerInputStream();
        this.oid = derInputStream.getOID();
        byte[] byteArray = derInputStream.getDerValue().toByteArray();
        this.nameValue = byteArray;
        GeneralNameInterface gni = getGNI(this.oid, byteArray);
        this.gni = gni;
        if (gni != null) {
            str = gni.toString();
        } else {
            str = "Unrecognized ObjectIdentifier: " + this.oid.toString();
        }
        this.name = str;
    }

    private GeneralNameInterface getGNI(ObjectIdentifier objectIdentifier, byte[] bArr) {
        try {
            Class cls = OIDMap.getClass(objectIdentifier);
            if (cls == null) {
                return null;
            }
            return (GeneralNameInterface) cls.getConstructor(Object.class).newInstance(bArr);
        } catch (Exception e2) {
            throw ((IOException) new IOException(AbstractC0000a.m14j("Instantiation error: ", e2)).initCause(e2));
        }
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface != null && generalNameInterface.getType() == 0) {
            throw new UnsupportedOperationException("Narrowing, widening, and matching are not supported for OtherName.");
        }
        return -1;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        GeneralNameInterface generalNameInterface = this.gni;
        if (generalNameInterface != null) {
            generalNameInterface.encode(derOutputStream);
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.oid);
        derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), this.nameValue);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OtherName)) {
            return false;
        }
        OtherName otherName = (OtherName) obj;
        if (!otherName.oid.equals(this.oid)) {
            return false;
        }
        try {
            GeneralNameInterface gni = getGNI(otherName.oid, otherName.nameValue);
            if (gni != null) {
                return gni.constrains(this) == 0;
            }
            return Arrays.equals(this.nameValue, otherName.nameValue);
        } catch (IOException | UnsupportedOperationException unused) {
            return false;
        }
    }

    public byte[] getNameValue() {
        return (byte[]) this.nameValue.clone();
    }

    public ObjectIdentifier getOID() {
        return this.oid;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 0;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            this.myhash = this.oid.hashCode() + 37;
            int i2 = 0;
            while (true) {
                byte[] bArr = this.nameValue;
                if (i2 >= bArr.length) {
                    break;
                }
                this.myhash = (this.myhash * 37) + bArr[i2];
                i2++;
            }
        }
        return this.myhash;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth() not supported for generic OtherName");
    }

    public String toString() {
        return "Other-Name: " + this.name;
    }

    public OtherName(ObjectIdentifier objectIdentifier, byte[] bArr) {
        String str;
        this.nameValue = null;
        this.gni = null;
        if (objectIdentifier == null || bArr == null) {
            throw new NullPointerException("parameters may not be null");
        }
        this.oid = objectIdentifier;
        this.nameValue = bArr;
        GeneralNameInterface gni = getGNI(objectIdentifier, bArr);
        this.gni = gni;
        if (gni != null) {
            str = gni.toString();
        } else {
            str = "Unrecognized ObjectIdentifier: " + objectIdentifier.toString();
        }
        this.name = str;
    }
}
