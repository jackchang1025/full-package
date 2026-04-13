package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BasicConstraintsExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.BasicConstraints";
    public static final String IS_CA = "is_ca";
    public static final String NAME = "BasicConstraints";
    public static final String PATH_LEN = "path_len";
    private boolean ca;
    private int pathLen;

    public BasicConstraintsExtension(Boolean bool, Object obj) {
        int integer;
        this.ca = false;
        this.pathLen = -1;
        this.extensionId = PKIXExtensions.BasicConstraints_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding of BasicConstraints");
        }
        DerInputStream derInputStream = derValue.data;
        if (derInputStream == null || derInputStream.available() == 0) {
            return;
        }
        DerValue derValue2 = derValue.data.getDerValue();
        if (derValue2.tag != 1) {
            return;
        }
        this.ca = derValue2.getBoolean();
        if (derValue.data.available() == 0) {
            integer = Integer.MAX_VALUE;
        } else {
            DerValue derValue3 = derValue.data.getDerValue();
            if (derValue3.tag != 2) {
                throw new IOException("Invalid encoding of BasicConstraints");
            }
            integer = derValue3.getInteger();
        }
        this.pathLen = integer;
    }

    private void encodeThis() {
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        boolean z2 = this.ca;
        if (z2) {
            derOutputStream2.putBoolean(z2);
            int i2 = this.pathLen;
            if (i2 >= 0) {
                derOutputStream2.putInteger(i2);
            }
        }
        derOutputStream.write((byte) 48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase(IS_CA)) {
            this.ca = false;
        } else {
            if (!str.equalsIgnoreCase(PATH_LEN)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
            }
            this.pathLen = -1;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.BasicConstraints_Id;
            this.critical = this.ca;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(IS_CA)) {
            return Boolean.valueOf(this.ca);
        }
        if (str.equalsIgnoreCase(PATH_LEN)) {
            return Integer.valueOf(this.pathLen);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(IS_CA);
        attributeNameEnumeration.addElement(PATH_LEN);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (str.equalsIgnoreCase(IS_CA)) {
            if (!(obj instanceof Boolean)) {
                throw new IOException("Attribute value should be of type Boolean.");
            }
            this.ca = ((Boolean) obj).booleanValue();
        } else {
            if (!str.equalsIgnoreCase(PATH_LEN)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
            }
            if (!(obj instanceof Integer)) {
                throw new IOException("Attribute value should be of type Integer.");
            }
            this.pathLen = ((Integer) obj).intValue();
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        String m18n = AbstractC0000a.m18n(AbstractC0000a.m20p(AbstractC0000a.m18n(new StringBuilder(), super.toString(), "BasicConstraints:[\n")), this.ca ? "  CA:true" : "  CA:false", "\n");
        return AbstractC0000a.m30z(this.pathLen >= 0 ? AbstractC0000a.m17m(AbstractC0000a.m22r(m18n, "  PathLen:"), this.pathLen, "\n") : AbstractC0000a.m30z(m18n, "  PathLen: undefined\n"), "]\n");
    }

    public BasicConstraintsExtension(Boolean bool, boolean z2, int i2) {
        this.ca = z2;
        this.pathLen = i2;
        this.extensionId = PKIXExtensions.BasicConstraints_Id;
        this.critical = bool.booleanValue();
        encodeThis();
    }

    public BasicConstraintsExtension(boolean z2, int i2) {
        this(Boolean.valueOf(z2), z2, i2);
    }
}
