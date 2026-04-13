package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateIssuerUniqueIdentity implements CertAttrSet<String> {
    public static final String ID = "id";
    public static final String IDENT = "x509.info.issuerID";
    public static final String NAME = "issuerID";
    private UniqueIdentity id;

    public CertificateIssuerUniqueIdentity(DerInputStream derInputStream) {
        this.id = new UniqueIdentity(derInputStream);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("id")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateIssuerUniqueIdentity.");
        }
        this.id = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.id.encode(derOutputStream, DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 1));
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("id")) {
            return this.id;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateIssuerUniqueIdentity.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("id");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "issuerID";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof UniqueIdentity)) {
            throw new IOException("Attribute must be of type UniqueIdentity.");
        }
        if (!str.equalsIgnoreCase("id")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateIssuerUniqueIdentity.");
        }
        this.id = (UniqueIdentity) obj;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        UniqueIdentity uniqueIdentity = this.id;
        return uniqueIdentity == null ? BuildConfig.FLAVOR : uniqueIdentity.toString();
    }

    public CertificateIssuerUniqueIdentity(DerValue derValue) {
        this.id = new UniqueIdentity(derValue);
    }

    public CertificateIssuerUniqueIdentity(UniqueIdentity uniqueIdentity) {
        this.id = uniqueIdentity;
    }

    public CertificateIssuerUniqueIdentity(InputStream inputStream) {
        this.id = new UniqueIdentity(new DerValue(inputStream));
    }
}
