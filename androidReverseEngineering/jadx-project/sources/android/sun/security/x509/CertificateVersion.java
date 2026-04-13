package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateVersion implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.version";
    public static final String NAME = "version";
    public static final int V1 = 0;
    public static final int V2 = 1;
    public static final int V3 = 2;
    public static final String VERSION = "number";
    int version;

    public CertificateVersion() {
        this.version = 0;
    }

    private void construct(DerValue derValue) {
        if (derValue.isConstructed() && derValue.isContextSpecific()) {
            DerValue derValue2 = derValue.data.getDerValue();
            this.version = derValue2.getInteger();
            if (derValue2.data.available() != 0) {
                throw new IOException("X.509 version, bad format");
            }
        }
    }

    private int getVersion() {
        return this.version;
    }

    public int compare(int i2) {
        return this.version - i2;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("number")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
        }
        this.version = 0;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        if (this.version == 0) {
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.version);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("number")) {
            return new Integer(getVersion());
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("number");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "version";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Integer)) {
            throw new IOException("Attribute must be of type Integer.");
        }
        if (!str.equalsIgnoreCase("number")) {
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
        }
        this.version = ((Integer) obj).intValue();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        return "Version: V" + (this.version + 1);
    }

    public CertificateVersion(int i2) {
        this.version = 0;
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IOException(AbstractC0000a.m12h("X.509 Certificate version ", i2, " not supported.\n"));
        }
        this.version = i2;
    }

    public CertificateVersion(DerInputStream derInputStream) {
        this.version = 0;
        construct(derInputStream.getDerValue());
    }

    public CertificateVersion(DerValue derValue) {
        this.version = 0;
        construct(derValue);
    }

    public CertificateVersion(InputStream inputStream) {
        this.version = 0;
        construct(new DerValue(inputStream));
    }
}
