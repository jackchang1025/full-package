package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SubjectAlternativeNameExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.SubjectAlternativeName";
    public static final String NAME = "SubjectAlternativeName";
    public static final String SUBJECT_NAME = "subject_name";
    GeneralNames names;

    public SubjectAlternativeNameExtension() {
        this.names = null;
        this.extensionId = PKIXExtensions.SubjectAlternativeName_Id;
        this.critical = false;
        this.names = new GeneralNames();
    }

    private void encodeThis() {
        GeneralNames generalNames = this.names;
        if (generalNames == null || generalNames.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        this.names.encode(derOutputStream);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(SUBJECT_NAME)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:SubjectAlternativeName.");
        }
        this.names = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.SubjectAlternativeName_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(SUBJECT_NAME)) {
            return this.names;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:SubjectAlternativeName.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(SUBJECT_NAME);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(SUBJECT_NAME)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:SubjectAlternativeName.");
        }
        if (!(obj instanceof GeneralNames)) {
            throw new IOException("Attribute value should be of type GeneralNames.");
        }
        this.names = (GeneralNames) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        String m18n = AbstractC0000a.m18n(new StringBuilder(), super.toString(), "SubjectAlternativeName [\n");
        GeneralNames generalNames = this.names;
        if (generalNames == null) {
            m18n = AbstractC0000a.m30z(m18n, "  null\n");
        } else {
            Iterator<GeneralName> it = generalNames.names().iterator();
            while (it.hasNext()) {
                m18n = m18n + "  " + it.next() + "\n";
            }
        }
        return AbstractC0000a.m30z(m18n, "]\n");
    }

    public SubjectAlternativeNameExtension(GeneralNames generalNames) {
        this(Boolean.FALSE, generalNames);
    }

    public SubjectAlternativeNameExtension(Boolean bool, GeneralNames generalNames) {
        this.names = generalNames;
        this.extensionId = PKIXExtensions.SubjectAlternativeName_Id;
        this.critical = bool.booleanValue();
        encodeThis();
    }

    public SubjectAlternativeNameExtension(Boolean bool, Object obj) {
        this.names = null;
        this.extensionId = PKIXExtensions.SubjectAlternativeName_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.data == null) {
            this.names = new GeneralNames();
        } else {
            this.names = new GeneralNames(derValue);
        }
    }
}
