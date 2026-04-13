package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateSerialNumber implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.serialNumber";
    public static final String NAME = "serialNumber";
    public static final String NUMBER = "number";
    private SerialNumber serial;

    public CertificateSerialNumber(int i2) {
        this.serial = new SerialNumber(i2);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("number")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
        }
        this.serial = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.serial.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("number")) {
            return this.serial;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("number");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "serialNumber";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof SerialNumber)) {
            throw new IOException("Attribute must be of type SerialNumber.");
        }
        if (!str.equalsIgnoreCase("number")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
        }
        this.serial = (SerialNumber) obj;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        SerialNumber serialNumber = this.serial;
        return serialNumber == null ? BuildConfig.FLAVOR : serialNumber.toString();
    }

    public CertificateSerialNumber(DerInputStream derInputStream) {
        this.serial = new SerialNumber(derInputStream);
    }

    public CertificateSerialNumber(DerValue derValue) {
        this.serial = new SerialNumber(derValue);
    }

    public CertificateSerialNumber(InputStream inputStream) {
        this.serial = new SerialNumber(inputStream);
    }

    public CertificateSerialNumber(BigInteger bigInteger) {
        this.serial = new SerialNumber(bigInteger);
    }
}
