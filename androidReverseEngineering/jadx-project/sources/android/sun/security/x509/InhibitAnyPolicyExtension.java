package android.sun.security.x509;

import android.sun.security.util.Debug;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class InhibitAnyPolicyExtension extends Extension implements CertAttrSet<String> {
    public static ObjectIdentifier AnyPolicy_Id = null;
    public static final String IDENT = "x509.info.extensions.InhibitAnyPolicy";
    public static final String NAME = "InhibitAnyPolicy";
    public static final String SKIP_CERTS = "skip_certs";
    private static final Debug debug = Debug.getInstance("certpath");
    private int skipCerts;

    static {
        try {
            AnyPolicy_Id = new ObjectIdentifier(RFC3280CertPathUtilities.ANY_POLICY);
        } catch (IOException unused) {
        }
    }

    public InhibitAnyPolicyExtension(int i2) {
        this.skipCerts = Integer.MAX_VALUE;
        if (i2 < -1) {
            throw new IOException("Invalid value for skipCerts");
        }
        if (i2 == -1) {
            this.skipCerts = Integer.MAX_VALUE;
        } else {
            this.skipCerts = i2;
        }
        this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
        this.critical = true;
        encodeThis();
    }

    private void encodeThis() {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.skipCerts);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(SKIP_CERTS)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
        }
        throw new IOException("Attribute skip_certs may not be deleted.");
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
            this.critical = true;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(SKIP_CERTS)) {
            return new Integer(this.skipCerts);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(SKIP_CERTS);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(SKIP_CERTS)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
        }
        if (!(obj instanceof Integer)) {
            throw new IOException("Attribute value should be of type Integer.");
        }
        int intValue = ((Integer) obj).intValue();
        if (intValue < -1) {
            throw new IOException("Invalid value for skipCerts");
        }
        if (intValue == -1) {
            intValue = Integer.MAX_VALUE;
        }
        this.skipCerts = intValue;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("InhibitAnyPolicy: ");
        return AbstractC0000a.m17m(sb, this.skipCerts, "\n");
    }

    public InhibitAnyPolicyExtension(Boolean bool, Object obj) {
        this.skipCerts = Integer.MAX_VALUE;
        this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
        if (!bool.booleanValue()) {
            throw new IOException("Criticality cannot be false for InhibitAnyPolicy");
        }
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 2) {
            throw new IOException("Invalid encoding of InhibitAnyPolicy: data not integer");
        }
        if (derValue.data == null) {
            throw new IOException("Invalid encoding of InhibitAnyPolicy: null data");
        }
        int integer = derValue.getInteger();
        if (integer < -1) {
            throw new IOException("Invalid value for skipCerts");
        }
        if (integer == -1) {
            this.skipCerts = Integer.MAX_VALUE;
        } else {
            this.skipCerts = integer;
        }
    }
}
