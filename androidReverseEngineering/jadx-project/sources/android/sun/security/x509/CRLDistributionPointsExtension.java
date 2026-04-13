package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CRLDistributionPointsExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.CRLDistributionPoints";
    public static final String NAME = "CRLDistributionPoints";
    public static final String POINTS = "points";
    private List<DistributionPoint> distributionPoints;
    private String extensionName;

    public CRLDistributionPointsExtension(ObjectIdentifier objectIdentifier, Boolean bool, Object obj, String str) {
        this.extensionId = objectIdentifier;
        this.critical = bool.booleanValue();
        if (!(obj instanceof byte[])) {
            throw new IOException("Illegal argument type");
        }
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException(AbstractC0000a.m16l("Invalid encoding for ", str, " extension."));
        }
        this.distributionPoints = new ArrayList();
        while (derValue.data.available() != 0) {
            this.distributionPoints.add(new DistributionPoint(derValue.data.getDerValue()));
        }
        this.extensionName = str;
    }

    private void encodeThis() {
        if (this.distributionPoints.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        Iterator<DistributionPoint> it = this.distributionPoints.iterator();
        while (it.hasNext()) {
            it.next().encode(derOutputStream);
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write((byte) 48, derOutputStream);
        this.extensionValue = derOutputStream2.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(POINTS)) {
            throw new IOException(AbstractC0000a.m18n(AbstractC0000a.m23s("Attribute name [", str, "] not recognized by CertAttrSet:"), this.extensionName, "."));
        }
        this.distributionPoints = new ArrayList();
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        encode(outputStream, PKIXExtensions.CRLDistributionPoints_Id, false);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(POINTS)) {
            return this.distributionPoints;
        }
        throw new IOException(AbstractC0000a.m18n(AbstractC0000a.m23s("Attribute name [", str, "] not recognized by CertAttrSet:"), this.extensionName, "."));
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(POINTS);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return this.extensionName;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(POINTS)) {
            throw new IOException(AbstractC0000a.m18n(AbstractC0000a.m23s("Attribute name [", str, "] not recognized by CertAttrSet:"), this.extensionName, "."));
        }
        if (!(obj instanceof List)) {
            throw new IOException("Attribute value should be of type List.");
        }
        this.distributionPoints = (List) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + this.extensionName + " [\n  " + this.distributionPoints + "]\n";
    }

    public void encode(OutputStream outputStream, ObjectIdentifier objectIdentifier, boolean z2) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = objectIdentifier;
            this.critical = z2;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    public CRLDistributionPointsExtension(ObjectIdentifier objectIdentifier, boolean z2, List<DistributionPoint> list, String str) {
        this.extensionId = objectIdentifier;
        this.critical = z2;
        this.distributionPoints = list;
        encodeThis();
        this.extensionName = str;
    }

    public CRLDistributionPointsExtension(Boolean bool, Object obj) {
        this(PKIXExtensions.CRLDistributionPoints_Id, bool, obj, NAME);
    }

    public CRLDistributionPointsExtension(List<DistributionPoint> list) {
        this(false, list);
    }

    public CRLDistributionPointsExtension(boolean z2, List<DistributionPoint> list) {
        this(PKIXExtensions.CRLDistributionPoints_Id, z2, list, NAME);
    }
}
