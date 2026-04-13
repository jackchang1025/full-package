package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class Extension {
    private static final int hashMagic = 31;
    protected boolean critical;
    protected ObjectIdentifier extensionId;
    protected byte[] extensionValue;

    public Extension() {
        this.extensionId = null;
        this.critical = false;
        this.extensionValue = null;
    }

    public static Extension newExtension(ObjectIdentifier objectIdentifier, boolean z2, byte[] bArr) {
        Extension extension = new Extension();
        extension.extensionId = objectIdentifier;
        extension.critical = z2;
        extension.extensionValue = bArr;
        return extension;
    }

    public void encode(DerOutputStream derOutputStream) {
        if (this.extensionId == null) {
            throw new IOException("Null OID to encode for the extension!");
        }
        if (this.extensionValue == null) {
            throw new IOException("No value to encode for the extension!");
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.extensionId);
        boolean z2 = this.critical;
        if (z2) {
            derOutputStream2.putBoolean(z2);
        }
        derOutputStream2.putOctetString(this.extensionValue);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Extension)) {
            return false;
        }
        Extension extension = (Extension) obj;
        if (this.critical == extension.critical && this.extensionId.equals(extension.extensionId)) {
            return Arrays.equals(this.extensionValue, extension.extensionValue);
        }
        return false;
    }

    public ObjectIdentifier getExtensionId() {
        return this.extensionId;
    }

    public byte[] getExtensionValue() {
        return this.extensionValue;
    }

    public String getId() {
        return this.extensionId.toString();
    }

    public byte[] getValue() {
        return (byte[]) this.extensionValue.clone();
    }

    public int hashCode() {
        byte[] bArr = this.extensionValue;
        int i2 = 0;
        if (bArr != null) {
            int length = bArr.length;
            while (length > 0) {
                int i3 = length - 1;
                i2 += length * bArr[i3];
                length = i3;
            }
        }
        return ((this.extensionId.hashCode() + (i2 * 31)) * 31) + (this.critical ? 1231 : 1237);
    }

    public boolean isCritical() {
        return this.critical;
    }

    public String toString() {
        StringBuilder m20p;
        String str;
        String str2 = "ObjectId: " + this.extensionId.toString();
        if (this.critical) {
            m20p = AbstractC0000a.m20p(str2);
            str = " Criticality=true\n";
        } else {
            m20p = AbstractC0000a.m20p(str2);
            str = " Criticality=false\n";
        }
        m20p.append(str);
        return m20p.toString();
    }

    public Extension(DerValue derValue) {
        this.extensionId = null;
        this.critical = false;
        this.extensionValue = null;
        DerInputStream derInputStream = derValue.toDerInputStream();
        this.extensionId = derInputStream.getOID();
        DerValue derValue2 = derInputStream.getDerValue();
        if (derValue2.tag == 1) {
            this.critical = derValue2.getBoolean();
            this.extensionValue = derInputStream.getDerValue().getOctetString();
        } else {
            this.critical = false;
            this.extensionValue = derValue2.getOctetString();
        }
    }

    public void encode(OutputStream outputStream) {
        outputStream.getClass();
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream.putOID(this.extensionId);
        boolean z2 = this.critical;
        if (z2) {
            derOutputStream.putBoolean(z2);
        }
        derOutputStream.putOctetString(this.extensionValue);
        derOutputStream2.write((byte) 48, derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    public Extension(ObjectIdentifier objectIdentifier, boolean z2, byte[] bArr) {
        this.extensionValue = null;
        this.extensionId = objectIdentifier;
        this.critical = z2;
        this.extensionValue = new DerValue(bArr).getOctetString();
    }

    public Extension(Extension extension) {
        this.extensionId = null;
        this.critical = false;
        this.extensionValue = null;
        this.extensionId = extension.extensionId;
        this.critical = extension.critical;
        this.extensionValue = extension.extensionValue;
    }
}
