package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class IssuingDistributionPointExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.IssuingDistributionPoint";
    public static final String INDIRECT_CRL = "indirect_crl";
    public static final String NAME = "IssuingDistributionPoint";
    public static final String ONLY_ATTRIBUTE_CERTS = "only_attribute_certs";
    public static final String ONLY_CA_CERTS = "only_ca_certs";
    public static final String ONLY_USER_CERTS = "only_user_certs";
    public static final String POINT = "point";
    public static final String REASONS = "reasons";
    private static final byte TAG_DISTRIBUTION_POINT = 0;
    private static final byte TAG_INDIRECT_CRL = 4;
    private static final byte TAG_ONLY_ATTRIBUTE_CERTS = 5;
    private static final byte TAG_ONLY_CA_CERTS = 2;
    private static final byte TAG_ONLY_SOME_REASONS = 3;
    private static final byte TAG_ONLY_USER_CERTS = 1;
    private DistributionPointName distributionPoint;
    private boolean hasOnlyAttributeCerts;
    private boolean hasOnlyCACerts;
    private boolean hasOnlyUserCerts;
    private boolean isIndirectCRL;
    private ReasonFlags revocationReasons;

    public IssuingDistributionPointExtension(DistributionPointName distributionPointName, ReasonFlags reasonFlags, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.distributionPoint = null;
        this.revocationReasons = null;
        this.hasOnlyUserCerts = false;
        this.hasOnlyCACerts = false;
        this.hasOnlyAttributeCerts = false;
        this.isIndirectCRL = false;
        if ((z2 && (z3 || z4)) || ((z3 && (z2 || z4)) || (z4 && (z2 || z3)))) {
            throw new IllegalArgumentException("Only one of hasOnlyUserCerts, hasOnlyCACerts, hasOnlyAttributeCerts may be set to true");
        }
        this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
        this.critical = true;
        this.distributionPoint = distributionPointName;
        this.revocationReasons = reasonFlags;
        this.hasOnlyUserCerts = z2;
        this.hasOnlyCACerts = z3;
        this.hasOnlyAttributeCerts = z4;
        this.isIndirectCRL = z5;
        encodeThis();
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.distributionPoint != null || this.revocationReasons != null || this.hasOnlyUserCerts || this.hasOnlyCACerts || this.hasOnlyAttributeCerts || this.isIndirectCRL) {
            DerOutputStream derOutputStream = new DerOutputStream();
            if (this.distributionPoint != null) {
                DerOutputStream derOutputStream2 = new DerOutputStream();
                this.distributionPoint.encode(derOutputStream2);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream2);
            }
            if (this.hasOnlyUserCerts) {
                DerOutputStream derOutputStream3 = new DerOutputStream();
                derOutputStream3.putBoolean(this.hasOnlyUserCerts);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 1), derOutputStream3);
            }
            if (this.hasOnlyCACerts) {
                DerOutputStream derOutputStream4 = new DerOutputStream();
                derOutputStream4.putBoolean(this.hasOnlyCACerts);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 2), derOutputStream4);
            }
            if (this.revocationReasons != null) {
                DerOutputStream derOutputStream5 = new DerOutputStream();
                this.revocationReasons.encode(derOutputStream5);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 3), derOutputStream5);
            }
            if (this.isIndirectCRL) {
                DerOutputStream derOutputStream6 = new DerOutputStream();
                derOutputStream6.putBoolean(this.isIndirectCRL);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 4), derOutputStream6);
            }
            if (this.hasOnlyAttributeCerts) {
                DerOutputStream derOutputStream7 = new DerOutputStream();
                derOutputStream7.putBoolean(this.hasOnlyAttributeCerts);
                derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 5), derOutputStream7);
            }
            DerOutputStream derOutputStream8 = new DerOutputStream();
            derOutputStream8.write((byte) 48, derOutputStream);
            byteArray = derOutputStream8.toByteArray();
        } else {
            byteArray = null;
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase(POINT)) {
            this.distributionPoint = null;
        } else if (str.equalsIgnoreCase(INDIRECT_CRL)) {
            this.isIndirectCRL = false;
        } else if (str.equalsIgnoreCase(REASONS)) {
            this.revocationReasons = null;
        } else if (str.equalsIgnoreCase(ONLY_USER_CERTS)) {
            this.hasOnlyUserCerts = false;
        } else if (str.equalsIgnoreCase(ONLY_CA_CERTS)) {
            this.hasOnlyCACerts = false;
        } else {
            if (!str.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) {
                throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:IssuingDistributionPointExtension."));
            }
            this.hasOnlyAttributeCerts = false;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        boolean z2;
        if (str.equalsIgnoreCase(POINT)) {
            return this.distributionPoint;
        }
        if (str.equalsIgnoreCase(INDIRECT_CRL)) {
            z2 = this.isIndirectCRL;
        } else {
            if (str.equalsIgnoreCase(REASONS)) {
                return this.revocationReasons;
            }
            if (str.equalsIgnoreCase(ONLY_USER_CERTS)) {
                z2 = this.hasOnlyUserCerts;
            } else if (str.equalsIgnoreCase(ONLY_CA_CERTS)) {
                z2 = this.hasOnlyCACerts;
            } else {
                if (!str.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) {
                    throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:IssuingDistributionPointExtension."));
                }
                z2 = this.hasOnlyAttributeCerts;
            }
        }
        return Boolean.valueOf(z2);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(POINT);
        attributeNameEnumeration.addElement(REASONS);
        attributeNameEnumeration.addElement(ONLY_USER_CERTS);
        attributeNameEnumeration.addElement(ONLY_CA_CERTS);
        attributeNameEnumeration.addElement(ONLY_ATTRIBUTE_CERTS);
        attributeNameEnumeration.addElement(INDIRECT_CRL);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (str.equalsIgnoreCase(POINT)) {
            if (!(obj instanceof DistributionPointName)) {
                throw new IOException("Attribute value should be of type DistributionPointName.");
            }
            this.distributionPoint = (DistributionPointName) obj;
        } else if (str.equalsIgnoreCase(REASONS)) {
            if (!(obj instanceof ReasonFlags)) {
                throw new IOException("Attribute value should be of type ReasonFlags.");
            }
        } else if (str.equalsIgnoreCase(INDIRECT_CRL)) {
            if (!(obj instanceof Boolean)) {
                throw new IOException("Attribute value should be of type Boolean.");
            }
            this.isIndirectCRL = ((Boolean) obj).booleanValue();
        } else if (str.equalsIgnoreCase(ONLY_USER_CERTS)) {
            if (!(obj instanceof Boolean)) {
                throw new IOException("Attribute value should be of type Boolean.");
            }
            this.hasOnlyUserCerts = ((Boolean) obj).booleanValue();
        } else if (str.equalsIgnoreCase(ONLY_CA_CERTS)) {
            if (!(obj instanceof Boolean)) {
                throw new IOException("Attribute value should be of type Boolean.");
            }
            this.hasOnlyCACerts = ((Boolean) obj).booleanValue();
        } else {
            if (!str.equalsIgnoreCase(ONLY_ATTRIBUTE_CERTS)) {
                throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:IssuingDistributionPointExtension."));
            }
            if (!(obj instanceof Boolean)) {
                throw new IOException("Attribute value should be of type Boolean.");
            }
            this.hasOnlyAttributeCerts = ((Boolean) obj).booleanValue();
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("IssuingDistributionPoint [\n  ");
        DistributionPointName distributionPointName = this.distributionPoint;
        if (distributionPointName != null) {
            sb.append(distributionPointName);
        }
        ReasonFlags reasonFlags = this.revocationReasons;
        if (reasonFlags != null) {
            sb.append(reasonFlags);
        }
        sb.append(this.hasOnlyUserCerts ? "  Only contains user certs: true" : "  Only contains user certs: false");
        sb.append("\n");
        sb.append(this.hasOnlyCACerts ? "  Only contains CA certs: true" : "  Only contains CA certs: false");
        sb.append("\n");
        sb.append(this.hasOnlyAttributeCerts ? "  Only contains attribute certs: true" : "  Only contains attribute certs: false");
        sb.append("\n");
        return AbstractC0000a.m18n(sb, this.isIndirectCRL ? "  Indirect CRL: true" : "  Indirect CRL: false", "\n]\n");
    }

    public IssuingDistributionPointExtension(Boolean bool, Object obj) {
        this.distributionPoint = null;
        this.revocationReasons = null;
        this.hasOnlyUserCerts = false;
        this.hasOnlyCACerts = false;
        this.hasOnlyAttributeCerts = false;
        this.isIndirectCRL = false;
        this.extensionId = PKIXExtensions.IssuingDistributionPoint_Id;
        this.critical = bool.booleanValue();
        if (!(obj instanceof byte[])) {
            throw new IOException("Illegal argument type");
        }
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for IssuingDistributionPointExtension.");
        }
        DerInputStream derInputStream = derValue.data;
        if (derInputStream == null || derInputStream.available() == 0) {
            return;
        }
        DerInputStream derInputStream2 = derValue.data;
        while (derInputStream2 != null && derInputStream2.available() != 0) {
            DerValue derValue2 = derInputStream2.getDerValue();
            if (derValue2.isContextSpecific((byte) 0) && derValue2.isConstructed()) {
                this.distributionPoint = new DistributionPointName(derValue2.data.getDerValue());
            } else if (derValue2.isContextSpecific((byte) 1) && !derValue2.isConstructed()) {
                derValue2.resetTag((byte) 1);
                this.hasOnlyUserCerts = derValue2.getBoolean();
            } else if (derValue2.isContextSpecific((byte) 2) && !derValue2.isConstructed()) {
                derValue2.resetTag((byte) 1);
                this.hasOnlyCACerts = derValue2.getBoolean();
            } else if (derValue2.isContextSpecific((byte) 3) && !derValue2.isConstructed()) {
                this.revocationReasons = new ReasonFlags(derValue2);
            } else if (derValue2.isContextSpecific((byte) 4) && !derValue2.isConstructed()) {
                derValue2.resetTag((byte) 1);
                this.isIndirectCRL = derValue2.getBoolean();
            } else {
                if (!derValue2.isContextSpecific((byte) 5) || derValue2.isConstructed()) {
                    throw new IOException("Invalid encoding of IssuingDistributionPoint");
                }
                derValue2.resetTag((byte) 1);
                this.hasOnlyAttributeCerts = derValue2.getBoolean();
            }
        }
    }
}
