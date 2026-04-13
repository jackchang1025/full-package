package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class InvalidityDateExtension extends Extension implements CertAttrSet<String> {
    public static final String DATE = "date";
    public static final String NAME = "InvalidityDate";
    private Date date;

    public InvalidityDateExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.InvalidityDate_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        this.date = new DerValue(bArr).getGeneralizedTime();
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.date == null) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStream.putGeneralizedTime(this.date);
            byteArray = derOutputStream.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    public static InvalidityDateExtension toImpl(Extension extension) {
        return extension instanceof InvalidityDateExtension ? (InvalidityDateExtension) extension : new InvalidityDateExtension(Boolean.valueOf(extension.isCritical()), extension.getValue());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(DATE)) {
            throw new IOException("Name not supported by InvalidityDateExtension");
        }
        this.date = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.InvalidityDate_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (!str.equalsIgnoreCase(DATE)) {
            throw new IOException("Name not supported by InvalidityDateExtension");
        }
        if (this.date == null) {
            return null;
        }
        return new Date(this.date.getTime());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(DATE);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Date)) {
            throw new IOException("Attribute must be of type Date.");
        }
        if (!str.equalsIgnoreCase(DATE)) {
            throw new IOException("Name not supported by InvalidityDateExtension");
        }
        this.date = (Date) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "    Invalidity Date: " + String.valueOf(this.date);
    }

    public InvalidityDateExtension(Date date) {
        this(false, date);
    }

    public InvalidityDateExtension(boolean z2, Date date) {
        this.extensionId = PKIXExtensions.InvalidityDate_Id;
        this.critical = z2;
        this.date = date;
        encodeThis();
    }
}
