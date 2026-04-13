package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.security.auth.x500.X500Principal;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateIssuerName implements CertAttrSet<String> {
    public static final String DN_NAME = "dname";
    public static final String DN_PRINCIPAL = "x500principal";
    public static final String IDENT = "x509.info.issuer";
    public static final String NAME = "issuer";
    private X500Name dnName;
    private X500Principal dnPrincipal;

    public CertificateIssuerName(DerInputStream derInputStream) {
        this.dnName = new X500Name(derInputStream);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("dname")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuerName.");
        }
        this.dnName = null;
        this.dnPrincipal = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.dnName.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        X500Name x500Name;
        if (str.equalsIgnoreCase("dname")) {
            return this.dnName;
        }
        if (!str.equalsIgnoreCase("x500principal")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuerName.");
        }
        if (this.dnPrincipal == null && (x500Name = this.dnName) != null) {
            this.dnPrincipal = x500Name.asX500Principal();
        }
        return this.dnPrincipal;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("dname");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "issuer";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof X500Name)) {
            throw new IOException("Attribute must be of type X500Name.");
        }
        if (!str.equalsIgnoreCase("dname")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuerName.");
        }
        this.dnName = (X500Name) obj;
        this.dnPrincipal = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        X500Name x500Name = this.dnName;
        return x500Name == null ? BuildConfig.FLAVOR : x500Name.toString();
    }

    public CertificateIssuerName(X500Name x500Name) {
        this.dnName = x500Name;
    }

    public CertificateIssuerName(InputStream inputStream) {
        this.dnName = new X500Name(new DerValue(inputStream));
    }
}
