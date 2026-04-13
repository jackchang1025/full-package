package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PolicyInformation {
    public static final String ID = "id";
    public static final String NAME = "PolicyInformation";
    public static final String QUALIFIERS = "qualifiers";
    private CertificatePolicyId policyIdentifier;
    private Set<PolicyQualifierInfo> policyQualifiers;

    public PolicyInformation(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding of PolicyInformation");
        }
        this.policyIdentifier = new CertificatePolicyId(derValue.data.getDerValue());
        if (derValue.data.available() == 0) {
            this.policyQualifiers = Collections.emptySet();
            return;
        }
        this.policyQualifiers = new LinkedHashSet();
        DerValue derValue2 = derValue.data.getDerValue();
        if (derValue2.tag != 48) {
            throw new IOException("Invalid encoding of PolicyInformation");
        }
        if (derValue2.data.available() == 0) {
            throw new IOException("No data available in policyQualifiers");
        }
        while (derValue2.data.available() != 0) {
            this.policyQualifiers.add(new PolicyQualifierInfo(derValue2.data.getDerValue().toByteArray()));
        }
    }

    public void delete(String str) {
        if (str.equalsIgnoreCase(QUALIFIERS)) {
            this.policyQualifiers = Collections.emptySet();
        } else {
            if (!str.equalsIgnoreCase("id")) {
                throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by PolicyInformation."));
            }
            throw new IOException("Attribute ID may not be deleted from PolicyInformation.");
        }
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.policyIdentifier.encode(derOutputStream2);
        if (!this.policyQualifiers.isEmpty()) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            Iterator<PolicyQualifierInfo> it = this.policyQualifiers.iterator();
            while (it.hasNext()) {
                derOutputStream3.write(it.next().getEncoded());
            }
            derOutputStream2.write((byte) 48, derOutputStream3);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PolicyInformation)) {
            return false;
        }
        PolicyInformation policyInformation = (PolicyInformation) obj;
        if (this.policyIdentifier.equals(policyInformation.getPolicyIdentifier())) {
            return this.policyQualifiers.equals(policyInformation.getPolicyQualifiers());
        }
        return false;
    }

    public Object get(String str) {
        if (str.equalsIgnoreCase("id")) {
            return this.policyIdentifier;
        }
        if (str.equalsIgnoreCase(QUALIFIERS)) {
            return this.policyQualifiers;
        }
        throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by PolicyInformation."));
    }

    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement("id");
        attributeNameEnumeration.addElement(QUALIFIERS);
        return attributeNameEnumeration.elements();
    }

    public String getName() {
        return NAME;
    }

    public CertificatePolicyId getPolicyIdentifier() {
        return this.policyIdentifier;
    }

    public Set<PolicyQualifierInfo> getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    public int hashCode() {
        return this.policyQualifiers.hashCode() + ((this.policyIdentifier.hashCode() + 37) * 37);
    }

    public void set(String str, Object obj) {
        if (str.equalsIgnoreCase("id")) {
            if (!(obj instanceof CertificatePolicyId)) {
                throw new IOException("Attribute value must be instance of CertificatePolicyId.");
            }
            this.policyIdentifier = (CertificatePolicyId) obj;
        } else {
            if (!str.equalsIgnoreCase(QUALIFIERS)) {
                throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by PolicyInformation"));
            }
            if (this.policyIdentifier == null) {
                throw new IOException("Attribute must have a CertificatePolicyIdentifier value before PolicyQualifierInfo can be set.");
            }
            if (!(obj instanceof Set)) {
                throw new IOException("Attribute value must be of type Set.");
            }
            Set<PolicyQualifierInfo> set = (Set) obj;
            Iterator<PolicyQualifierInfo> it = set.iterator();
            while (it.hasNext()) {
                if (!(it.next() instanceof PolicyQualifierInfo)) {
                    throw new IOException("Attribute value must be aSet of PolicyQualifierInfo objects.");
                }
            }
            this.policyQualifiers = set;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("  [" + this.policyIdentifier.toString());
        sb.append(this.policyQualifiers + "  ]\n");
        return sb.toString();
    }

    public PolicyInformation(CertificatePolicyId certificatePolicyId, Set<PolicyQualifierInfo> set) {
        if (set == null) {
            throw new NullPointerException("policyQualifiers is null");
        }
        this.policyQualifiers = new LinkedHashSet(set);
        this.policyIdentifier = certificatePolicyId;
    }
}
