package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PolicyMappingsExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.PolicyMappings";
    public static final String MAP = "map";
    public static final String NAME = "PolicyMappings";
    private List<CertificatePolicyMap> maps;

    public PolicyMappingsExtension() {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = false;
        this.maps = new ArrayList();
    }

    private void encodeThis() {
        List<CertificatePolicyMap> list = this.maps;
        if (list == null || list.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        Iterator<CertificatePolicyMap> it = this.maps.iterator();
        while (it.hasNext()) {
            it.next().encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(MAP)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
        }
        this.maps = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.PolicyMappings_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(MAP)) {
            return this.maps;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(MAP);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(MAP)) {
            throw new IOException("Attribute name not recognized by CertAttrSet:PolicyMappingsExtension.");
        }
        if (!(obj instanceof List)) {
            throw new IOException("Attribute value should be of type List.");
        }
        this.maps = (List) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        if (this.maps == null) {
            return BuildConfig.FLAVOR;
        }
        return super.toString() + "PolicyMappings [\n" + this.maps.toString() + "]\n";
    }

    public PolicyMappingsExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.PolicyMappings_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for PolicyMappingsExtension.");
        }
        this.maps = new ArrayList();
        while (derValue.data.available() != 0) {
            this.maps.add(new CertificatePolicyMap(derValue.data.getDerValue()));
        }
    }

    public PolicyMappingsExtension(List<CertificatePolicyMap> list) {
        this.maps = list;
        this.extensionId = PKIXExtensions.PolicyMappings_Id;
        this.critical = false;
        encodeThis();
    }
}
