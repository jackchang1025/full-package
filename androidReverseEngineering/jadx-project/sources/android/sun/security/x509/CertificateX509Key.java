package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateX509Key implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.key";
    public static final String KEY = "value";
    public static final String NAME = "key";
    private PublicKey key;

    public CertificateX509Key(DerInputStream derInputStream) {
        this.key = X509Key.parse(derInputStream.getDerValue());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("value")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
        }
        this.key = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.write(this.key.getEncoded());
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("value")) {
            return this.key;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("value");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "key";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase("value")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
        }
        this.key = (PublicKey) obj;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        PublicKey publicKey = this.key;
        return publicKey == null ? BuildConfig.FLAVOR : publicKey.toString();
    }

    public CertificateX509Key(InputStream inputStream) {
        this.key = X509Key.parse(new DerValue(inputStream));
    }

    public CertificateX509Key(PublicKey publicKey) {
        this.key = publicKey;
    }
}
