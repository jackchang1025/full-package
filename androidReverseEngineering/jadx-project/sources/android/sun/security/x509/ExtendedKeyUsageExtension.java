package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ExtendedKeyUsageExtension extends Extension implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.ExtendedKeyUsage";
    public static final String NAME = "ExtendedKeyUsage";
    private static final int[] OCSPSigningOidData;
    public static final String USAGES = "usages";
    private static final int[] anyExtendedKeyUsageOidData;
    private static final int[] clientAuthOidData;
    private static final int[] codeSigningOidData;
    private static final int[] emailProtectionOidData;
    private static final int[] ipsecEndSystemOidData;
    private static final int[] ipsecTunnelOidData;
    private static final int[] ipsecUserOidData;
    private static final Map<ObjectIdentifier, String> map;
    private static final int[] serverAuthOidData;
    private static final int[] timeStampingOidData;
    private Vector<ObjectIdentifier> keyUsages;

    static {
        HashMap hashMap = new HashMap();
        map = hashMap;
        int[] iArr = {2, 5, 29, 37, 0};
        anyExtendedKeyUsageOidData = iArr;
        int[] iArr2 = {1, 3, 6, 1, 5, 5, 7, 3, 1};
        serverAuthOidData = iArr2;
        int[] iArr3 = {1, 3, 6, 1, 5, 5, 7, 3, 2};
        clientAuthOidData = iArr3;
        int[] iArr4 = {1, 3, 6, 1, 5, 5, 7, 3, 3};
        codeSigningOidData = iArr4;
        int[] iArr5 = {1, 3, 6, 1, 5, 5, 7, 3, 4};
        emailProtectionOidData = iArr5;
        int[] iArr6 = {1, 3, 6, 1, 5, 5, 7, 3, 5};
        ipsecEndSystemOidData = iArr6;
        int[] iArr7 = {1, 3, 6, 1, 5, 5, 7, 3, 6};
        ipsecTunnelOidData = iArr7;
        int[] iArr8 = {1, 3, 6, 1, 5, 5, 7, 3, 7};
        ipsecUserOidData = iArr8;
        int[] iArr9 = {1, 3, 6, 1, 5, 5, 7, 3, 8};
        timeStampingOidData = iArr9;
        int[] iArr10 = {1, 3, 6, 1, 5, 5, 7, 3, 9};
        OCSPSigningOidData = iArr10;
        hashMap.put(ObjectIdentifier.newInternal(iArr), "anyExtendedKeyUsage");
        hashMap.put(ObjectIdentifier.newInternal(iArr2), "serverAuth");
        hashMap.put(ObjectIdentifier.newInternal(iArr3), "clientAuth");
        hashMap.put(ObjectIdentifier.newInternal(iArr4), "codeSigning");
        hashMap.put(ObjectIdentifier.newInternal(iArr5), "emailProtection");
        hashMap.put(ObjectIdentifier.newInternal(iArr6), "ipsecEndSystem");
        hashMap.put(ObjectIdentifier.newInternal(iArr7), "ipsecTunnel");
        hashMap.put(ObjectIdentifier.newInternal(iArr8), "ipsecUser");
        hashMap.put(ObjectIdentifier.newInternal(iArr9), "timeStamping");
        hashMap.put(ObjectIdentifier.newInternal(iArr10), "OCSPSigning");
    }

    public ExtendedKeyUsageExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for ExtendedKeyUsageExtension.");
        }
        this.keyUsages = new Vector<>();
        while (derValue.data.available() != 0) {
            this.keyUsages.addElement(derValue.data.getDerValue().getOID());
        }
    }

    private void encodeThis() {
        Vector<ObjectIdentifier> vector = this.keyUsages;
        if (vector == null || vector.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (int i2 = 0; i2 < this.keyUsages.size(); i2++) {
            derOutputStream2.putOID(this.keyUsages.elementAt(i2));
        }
        derOutputStream.write((byte) 48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase(USAGES)) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:ExtendedKeyUsageExtension."));
        }
        this.keyUsages = null;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(USAGES)) {
            return this.keyUsages;
        }
        throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:ExtendedKeyUsageExtension."));
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t(USAGES);
    }

    public List<String> getExtendedKeyUsage() {
        ArrayList arrayList = new ArrayList(this.keyUsages.size());
        Iterator<ObjectIdentifier> it = this.keyUsages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        return arrayList;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!str.equalsIgnoreCase(USAGES)) {
            throw new IOException(AbstractC0000a.m16l("Attribute name [", str, "] not recognized by CertAttrSet:ExtendedKeyUsageExtension."));
        }
        if (!(obj instanceof Vector)) {
            throw new IOException("Attribute value should be of type Vector.");
        }
        this.keyUsages = (Vector) obj;
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        Vector<ObjectIdentifier> vector = this.keyUsages;
        if (vector == null) {
            return BuildConfig.FLAVOR;
        }
        Iterator<ObjectIdentifier> it = vector.iterator();
        String str = "  ";
        boolean z2 = true;
        while (it.hasNext()) {
            ObjectIdentifier next = it.next();
            if (!z2) {
                str = AbstractC0000a.m30z(str, "\n  ");
            }
            String str2 = map.get(next);
            if (str2 != null) {
                str = AbstractC0000a.m30z(str, str2);
            } else {
                StringBuilder m20p = AbstractC0000a.m20p(str);
                m20p.append(next.toString());
                str = m20p.toString();
            }
            z2 = false;
        }
        return super.toString() + "ExtendedKeyUsages [\n" + str + "\n]\n";
    }

    public ExtendedKeyUsageExtension(Boolean bool, Vector<ObjectIdentifier> vector) {
        this.keyUsages = vector;
        this.extensionId = PKIXExtensions.ExtendedKeyUsage_Id;
        this.critical = bool.booleanValue();
        encodeThis();
    }

    public ExtendedKeyUsageExtension(Vector<ObjectIdentifier> vector) {
        this(Boolean.FALSE, vector);
    }
}
