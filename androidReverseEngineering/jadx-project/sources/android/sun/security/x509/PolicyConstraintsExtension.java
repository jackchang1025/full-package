package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PolicyConstraintsExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.PolicyConstraints";
    public static final String INHIBIT = "inhibit";
    public static final String NAME = "PolicyConstraints";
    public static final String REQUIRE = "require";
    private static final byte TAG_INHIBIT = 1;
    private static final byte TAG_REQUIRE = 0;
    private int inhibit;
    private int require;

    public PolicyConstraintsExtension(int i2, int i3) {
        this(Boolean.FALSE, i2, i3);
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.require == -1 && this.inhibit == -1) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            if (this.require != -1) {
                DerOutputStream derOutputStream3 = new DerOutputStream();
                derOutputStream3.putInteger(this.require);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 0), derOutputStream3);
            }
            if (this.inhibit != -1) {
                DerOutputStream derOutputStream4 = new DerOutputStream();
                derOutputStream4.putInteger(this.inhibit);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 1), derOutputStream4);
            }
            derOutputStream2.write((byte) 48, derOutputStream);
            byteArray = derOutputStream2.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase(REQUIRE)) {
            this.require = -1;
        } else {
            if (!str.equalsIgnoreCase(INHIBIT)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
            }
            this.inhibit = -1;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.PolicyConstraints_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(REQUIRE)) {
            return new Integer(this.require);
        }
        if (str.equalsIgnoreCase(INHIBIT)) {
            return new Integer(this.inhibit);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(REQUIRE);
        attributeNameEnumeration.addElement(INHIBIT);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Integer)) {
            throw new IOException("Attribute value should be of type Integer.");
        }
        if (str.equalsIgnoreCase(REQUIRE)) {
            this.require = ((Integer) obj).intValue();
        } else {
            if (!str.equalsIgnoreCase(INHIBIT)) {
                throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:PolicyConstraints."));
            }
            this.inhibit = ((Integer) obj).intValue();
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        StringBuilder m20p;
        String str;
        StringBuilder m20p2;
        String m18n = AbstractC0000a.m18n(new StringBuilder(), super.toString(), "PolicyConstraints: [  Require: ");
        if (this.require == -1) {
            m20p = AbstractC0000a.m20p(m18n);
            str = "unspecified;";
        } else {
            m20p = AbstractC0000a.m20p(m18n);
            m20p.append(this.require);
            str = ";";
        }
        m20p.append(str);
        String m30z = AbstractC0000a.m30z(m20p.toString(), "\tInhibit: ");
        if (this.inhibit == -1) {
            m20p2 = AbstractC0000a.m22r(m30z, "unspecified");
        } else {
            m20p2 = AbstractC0000a.m20p(m30z);
            m20p2.append(this.inhibit);
        }
        return AbstractC0000a.m30z(m20p2.toString(), " ]\n");
    }

    public PolicyConstraintsExtension(Boolean bool, int i2, int i3) {
        this.require = i2;
        this.inhibit = i3;
        this.extensionId = PKIXExtensions.PolicyConstraints_Id;
        this.critical = bool.booleanValue();
        encodeThis();
    }

    public PolicyConstraintsExtension(Boolean bool, Object obj) {
        this.require = -1;
        this.inhibit = -1;
        this.extensionId = PKIXExtensions.PolicyConstraints_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Sequence tag missing for PolicyConstraint.");
        }
        DerInputStream derInputStream = derValue.data;
        while (derInputStream != null && derInputStream.available() != 0) {
            DerValue derValue2 = derInputStream.getDerValue();
            if (!derValue2.isContextSpecific((byte) 0) || derValue2.isConstructed()) {
                if (!derValue2.isContextSpecific((byte) 1) || derValue2.isConstructed()) {
                    throw new IOException("Invalid encoding of PolicyConstraint");
                }
                if (this.inhibit != -1) {
                    throw new IOException("Duplicate inhibitPolicyMappingfound in the PolicyConstraintsExtension");
                }
                derValue2.resetTag((byte) 2);
                this.inhibit = derValue2.getInteger();
            } else {
                if (this.require != -1) {
                    throw new IOException("Duplicate requireExplicitPolicyfound in the PolicyConstraintsExtension");
                }
                derValue2.resetTag((byte) 2);
                this.require = derValue2.getInteger();
            }
        }
    }
}
