package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AuthorityInfoAccessExtension extends Extension implements CertAttrSet<String> {
    public static final String DESCRIPTIONS = "descriptions";
    public static final String IDENT = "x509.info.extensions.AuthorityInfoAccess";
    public static final String NAME = "AuthorityInfoAccess";
    private List<AccessDescription> accessDescriptions;

    public AuthorityInfoAccessExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
        this.critical = bool.booleanValue();
        if (!(obj instanceof byte[])) {
            throw new IOException("Illegal argument type");
        }
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for AuthorityInfoAccessExtension.");
        }
        this.accessDescriptions = new ArrayList();
        while (derValue.data.available() != 0) {
            this.accessDescriptions.add(new AccessDescription(derValue.data.getDerValue()));
        }
    }

    private void encodeThis() {
        if (this.accessDescriptions.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        Iterator<AccessDescription> it = this.accessDescriptions.iterator();
        while (it.hasNext()) {
            it.next().encode(derOutputStream);
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write((byte) 48, derOutputStream);
        this.extensionValue = derOutputStream2.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("descriptions")) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:AuthorityInfoAccessExtension."));
        }
        this.accessDescriptions = new ArrayList();
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("descriptions")) {
            return this.accessDescriptions;
        }
        throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:AuthorityInfoAccessExtension."));
    }

    public List<AccessDescription> getAccessDescriptions() {
        return this.accessDescriptions;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("descriptions");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase("descriptions")) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:AuthorityInfoAccessExtension."));
        }
        if (!(obj instanceof List)) {
            throw new IOException("Attribute value should be of type List.");
        }
        this.accessDescriptions = (List) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "AuthorityInfoAccess [\n  " + this.accessDescriptions + "\n]\n";
    }

    public AuthorityInfoAccessExtension(List<AccessDescription> list) {
        this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
        this.critical = false;
        this.accessDescriptions = list;
        encodeThis();
    }
}
