package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Date;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class CertificateValidity implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.validity";
    public static final String NAME = "validity";
    public static final String NOT_AFTER = "notAfter";
    public static final String NOT_BEFORE = "notBefore";
    private static final long YR_2050 = 2524636800000L;
    private Date notAfter;
    private Date notBefore;

    public CertificateValidity() {
    }

    public CertificateValidity(DerInputStream derInputStream) {
        construct(derInputStream.getDerValue());
    }

    private void construct(DerValue derValue) {
        Date generalizedTime;
        Date generalizedTime2;
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoded CertificateValidity, starting sequence tag missing.");
        }
        if (derValue.data.available() == 0) {
            throw new IOException("No data encoded for CertificateValidity");
        }
        DerValue[] sequence = new DerInputStream(derValue.toByteArray()).getSequence(2);
        if (sequence.length != 2) {
            throw new IOException("Invalid encoding for CertificateValidity");
        }
        byte b = sequence[0].tag;
        if (b == 23) {
            generalizedTime = derValue.data.getUTCTime();
        } else {
            if (b != 24) {
                throw new IOException("Invalid encoding for CertificateValidity");
            }
            generalizedTime = derValue.data.getGeneralizedTime();
        }
        this.notBefore = generalizedTime;
        byte b2 = sequence[1].tag;
        if (b2 == 23) {
            generalizedTime2 = derValue.data.getUTCTime();
        } else {
            if (b2 != 24) {
                throw new IOException("Invalid encoding for CertificateValidity");
            }
            generalizedTime2 = derValue.data.getGeneralizedTime();
        }
        this.notAfter = generalizedTime2;
    }

    private Date getNotAfter() {
        return new Date(this.notAfter.getTime());
    }

    private Date getNotBefore() {
        return new Date(this.notBefore.getTime());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase(NOT_BEFORE)) {
            this.notBefore = null;
        } else {
            if (!str.equalsIgnoreCase(NOT_AFTER)) {
                throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
            }
            this.notAfter = null;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        if (this.notBefore == null || this.notAfter == null) {
            throw new IOException("CertAttrSet:CertificateValidity: null values to encode.\n");
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.notBefore.getTime() < YR_2050) {
            derOutputStream.putUTCTime(this.notBefore);
        } else {
            derOutputStream.putGeneralizedTime(this.notBefore);
        }
        if (this.notAfter.getTime() < YR_2050) {
            derOutputStream.putUTCTime(this.notAfter);
        } else {
            derOutputStream.putGeneralizedTime(this.notAfter);
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write((byte) 48, derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(NOT_BEFORE)) {
            return getNotBefore();
        }
        if (str.equalsIgnoreCase(NOT_AFTER)) {
            return getNotAfter();
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(NOT_BEFORE);
        attributeNameEnumeration.addElement(NOT_AFTER);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "validity";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Date)) {
            throw new IOException("Attribute must be of type Date.");
        }
        if (str.equalsIgnoreCase(NOT_BEFORE)) {
            this.notBefore = (Date) obj;
        } else {
            if (!str.equalsIgnoreCase(NOT_AFTER)) {
                throw new IOException("Attribute name not recognized by CertAttrSet: CertificateValidity.");
            }
            this.notAfter = (Date) obj;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        if (this.notBefore == null || this.notAfter == null) {
            return BuildConfig.FLAVOR;
        }
        return "Validity: [From: " + this.notBefore.toString() + ",\n               To: " + this.notAfter.toString() + "]";
    }

    public void valid() {
        valid(new Date());
    }

    public CertificateValidity(Date date, Date date2) {
        this.notBefore = date;
        this.notAfter = date2;
    }

    public void valid(Date date) {
        if (this.notBefore.after(date)) {
            throw new CertificateNotYetValidException("NotBefore: " + this.notBefore.toString());
        }
        if (this.notAfter.before(date)) {
            throw new CertificateExpiredException("NotAfter: " + this.notAfter.toString());
        }
    }
}
