package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SubjectKeyIdentifierExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.SubjectKeyIdentifier";
    public static final String KEY_ID = "key_id";
    public static final String NAME = "SubjectKeyIdentifier";
    private KeyIdentifier id;

    public SubjectKeyIdentifierExtension(Boolean bool, Object obj) {
        this.id = null;
        this.extensionId = PKIXExtensions.SubjectKey_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        this.id = new KeyIdentifier(new DerValue(bArr));
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.id == null) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            this.id.encode(derOutputStream);
            byteArray = derOutputStream.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("key_id")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
        }
        this.id = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.SubjectKey_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("key_id")) {
            return this.id;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("key_id");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase("key_id")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
        }
        if (!(obj instanceof KeyIdentifier)) {
            throw new IOException("Attribute value should be of type KeyIdentifier.");
        }
        this.id = (KeyIdentifier) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "SubjectKeyIdentifier [\n" + String.valueOf(this.id) + "]\n";
    }

    public SubjectKeyIdentifierExtension(byte[] bArr) {
        this.id = null;
        this.id = new KeyIdentifier(bArr);
        this.extensionId = PKIXExtensions.SubjectKey_Id;
        this.critical = false;
        encodeThis();
    }
}
