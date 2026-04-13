package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificatePoliciesExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.CertificatePolicies";
    public static final String NAME = "CertificatePolicies";
    public static final String POLICIES = "policies";
    private List<PolicyInformation> certPolicies;

    public CertificatePoliciesExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.CertificatePolicies_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for CertificatePoliciesExtension.");
        }
        this.certPolicies = new ArrayList();
        while (derValue.data.available() != 0) {
            this.certPolicies.add(new PolicyInformation(derValue.data.getDerValue()));
        }
    }

    private void encodeThis() {
        List<PolicyInformation> list = this.certPolicies;
        if (list == null || list.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        Iterator<PolicyInformation> it = this.certPolicies.iterator();
        while (it.hasNext()) {
            it.next().encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(POLICIES)) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:CertificatePoliciesExtension."));
        }
        this.certPolicies = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.CertificatePolicies_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(POLICIES)) {
            return this.certPolicies;
        }
        throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:CertificatePoliciesExtension."));
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(POLICIES);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(POLICIES)) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:CertificatePoliciesExtension."));
        }
        if (!(obj instanceof List)) {
            throw new IOException("Attribute value should be of type List.");
        }
        this.certPolicies = (List) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        if (this.certPolicies == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("CertificatePolicies [\n");
        Iterator<PolicyInformation> it = this.certPolicies.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        sb.append("]\n");
        return sb.toString();
    }

    public CertificatePoliciesExtension(Boolean bool, List<PolicyInformation> list) {
        this.certPolicies = list;
        this.extensionId = PKIXExtensions.CertificatePolicies_Id;
        this.critical = bool.booleanValue();
        encodeThis();
    }

    public CertificatePoliciesExtension(List<PolicyInformation> list) {
        this(Boolean.FALSE, list);
    }
}
