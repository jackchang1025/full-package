package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CRLReason;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CRLReasonCodeExtension extends Extension implements CertAttrSet<String> {
    public static final int AA_COMPROMISE = 10;
    public static final int AFFLIATION_CHANGED = 3;
    public static final int CA_COMPROMISE = 2;
    public static final int CERTIFICATE_HOLD = 6;
    public static final int CESSATION_OF_OPERATION = 5;
    public static final int KEY_COMPROMISE = 1;
    public static final String NAME = "CRLReasonCode";
    public static final int PRIVILEGE_WITHDRAWN = 9;
    public static final String REASON = "reason";
    public static final int REMOVE_FROM_CRL = 8;
    public static final int SUPERSEDED = 4;
    public static final int UNSPECIFIED = 0;
    private static CRLReason[] values = CRLReason.values();
    private int reasonCode;

    public CRLReasonCodeExtension(int i2) {
        this(false, i2);
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.reasonCode == 0) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStream.putEnumerated(this.reasonCode);
            byteArray = derOutputStream.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(REASON)) {
            throw new IOException("Name not supported by CRLReasonCodeExtension");
        }
        this.reasonCode = 0;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.ReasonCode_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(REASON)) {
            return new Integer(this.reasonCode);
        }
        throw new IOException("Name not supported by CRLReasonCodeExtension");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(REASON);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    public CRLReason getReasonCode() {
        int i2 = this.reasonCode;
        if (i2 > 0) {
            CRLReason[] cRLReasonArr = values;
            if (i2 < cRLReasonArr.length) {
                return cRLReasonArr[i2];
            }
        }
        return CRLReason.UNSPECIFIED;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Integer)) {
            throw new IOException("Attribute must be of type Integer.");
        }
        if (!str.equalsIgnoreCase(REASON)) {
            throw new IOException("Name not supported by CRLReasonCodeExtension");
        }
        this.reasonCode = ((Integer) obj).intValue();
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "    Reason Code: " + values[this.reasonCode];
    }

    public CRLReasonCodeExtension(Boolean bool, Object obj) {
        this.reasonCode = 0;
        this.extensionId = PKIXExtensions.ReasonCode_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        this.reasonCode = new DerValue(bArr).getEnumerated();
    }

    public CRLReasonCodeExtension(boolean z2, int i2) {
        this.reasonCode = 0;
        this.extensionId = PKIXExtensions.ReasonCode_Id;
        this.critical = z2;
        this.reasonCode = i2;
        encodeThis();
    }
}
