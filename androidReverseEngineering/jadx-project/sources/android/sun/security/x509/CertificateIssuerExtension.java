package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateIssuerExtension extends Extension implements CertAttrSet<String> {
    public static final String ISSUER = "issuer";
    public static final String NAME = "CertificateIssuer";
    private GeneralNames names;

    public CertificateIssuerExtension(GeneralNames generalNames) {
        this.extensionId = PKIXExtensions.CertificateIssuer_Id;
        this.critical = true;
        this.names = generalNames;
        encodeThis();
    }

    private void encodeThis() {
        GeneralNames generalNames = this.names;
        if (generalNames == null || generalNames.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        this.names.encode(derOutputStream);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("issuer")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
        }
        this.names = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.CertificateIssuer_Id;
            this.critical = true;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("issuer")) {
            return this.names;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("issuer");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase("issuer")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
        }
        if (!(obj instanceof GeneralNames)) {
            throw new IOException("Attribute value must be of type GeneralNames");
        }
        this.names = (GeneralNames) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "Certificate Issuer [\n" + String.valueOf(this.names) + "]\n";
    }

    public CertificateIssuerExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.CertificateIssuer_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        this.names = new GeneralNames(new DerValue(bArr));
    }
}
