package android.sun.security.x509;

import android.sun.security.util.Debug;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CRLNumberExtension extends Extension implements CertAttrSet<String> {
    private static final String LABEL = "CRL Number";
    public static final String NAME = "CRLNumber";
    public static final String NUMBER = "value";
    private BigInteger crlNumber;
    private String extensionLabel;
    private String extensionName;

    public CRLNumberExtension(int i2) {
        this(PKIXExtensions.CRLNumber_Id, false, BigInteger.valueOf(i2), NAME, LABEL);
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.crlNumber == null) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStream.putInteger(this.crlNumber);
            byteArray = derOutputStream.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("value")) {
            throw new IOException(AbstractC0000a.m18n(new StringBuilder("Attribute name not recognized by CertAttrSet:"), this.extensionName, "."));
        }
        this.crlNumber = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        new DerOutputStream();
        encode(outputStream, PKIXExtensions.CRLNumber_Id, true);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (!str.equalsIgnoreCase("value")) {
            throw new IOException(AbstractC0000a.m18n(new StringBuilder("Attribute name not recognized by CertAttrSet:"), this.extensionName, "."));
        }
        BigInteger bigInteger = this.crlNumber;
        if (bigInteger == null) {
            return null;
        }
        return bigInteger;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("value");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return this.extensionName;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase("value")) {
            throw new IOException(AbstractC0000a.m18n(new StringBuilder("Attribute name not recognized by CertAttrSet:"), this.extensionName, "."));
        }
        if (!(obj instanceof BigInteger)) {
            throw new IOException("Attribute must be of type BigInteger.");
        }
        this.crlNumber = (BigInteger) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(this.extensionLabel);
        sb.append(": ");
        BigInteger bigInteger = this.crlNumber;
        return AbstractC0000a.m18n(sb, bigInteger == null ? BuildConfig.FLAVOR : Debug.toHexString(bigInteger), "\n");
    }

    public CRLNumberExtension(ObjectIdentifier objectIdentifier, Boolean bool, Object obj, String str, String str2) {
        this.crlNumber = null;
        this.extensionId = objectIdentifier;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        this.crlNumber = new DerValue(bArr).getBigInteger();
        this.extensionName = str;
        this.extensionLabel = str2;
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

    public CRLNumberExtension(ObjectIdentifier objectIdentifier, boolean z2, BigInteger bigInteger, String str, String str2) {
        this.extensionId = objectIdentifier;
        this.critical = z2;
        this.crlNumber = bigInteger;
        this.extensionName = str;
        this.extensionLabel = str2;
        encodeThis();
    }

    public CRLNumberExtension(Boolean bool, Object obj) {
        this(PKIXExtensions.CRLNumber_Id, bool, obj, NAME, LABEL);
    }

    public CRLNumberExtension(BigInteger bigInteger) {
        this(PKIXExtensions.CRLNumber_Id, false, bigInteger, NAME, LABEL);
    }
}
