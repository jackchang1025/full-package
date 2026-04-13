package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class X500Name implements GeneralNameInterface, Principal {
    private static final int[] DNQUALIFIER_DATA;
    public static final ObjectIdentifier DNQUALIFIER_OID;
    private static final int[] DOMAIN_COMPONENT_DATA;
    public static final ObjectIdentifier DOMAIN_COMPONENT_OID;
    private static final int[] GENERATIONQUALIFIER_DATA;
    public static final ObjectIdentifier GENERATIONQUALIFIER_OID;
    private static final int[] GIVENNAME_DATA;
    public static final ObjectIdentifier GIVENNAME_OID;
    private static final int[] INITIALS_DATA;
    public static final ObjectIdentifier INITIALS_OID;
    private static final int[] SERIALNUMBER_DATA;
    public static final ObjectIdentifier SERIALNUMBER_OID;
    private static final int[] SURNAME_DATA;
    public static final ObjectIdentifier SURNAME_OID;
    private static final int[] commonName_data;
    public static final ObjectIdentifier commonName_oid;
    private static final int[] countryName_data;
    public static final ObjectIdentifier countryName_oid;
    private static final Map<ObjectIdentifier, ObjectIdentifier> internedOIDs = new HashMap();
    private static final int[] ipAddress_data;
    public static final ObjectIdentifier ipAddress_oid;
    private static final int[] localityName_data;
    public static final ObjectIdentifier localityName_oid;
    private static final int[] orgName_data;
    public static final ObjectIdentifier orgName_oid;
    private static final int[] orgUnitName_data;
    public static final ObjectIdentifier orgUnitName_oid;
    private static final int[] stateName_data;
    public static final ObjectIdentifier stateName_oid;
    private static final int[] streetAddress_data;
    public static final ObjectIdentifier streetAddress_oid;
    private static final int[] title_data;
    public static final ObjectIdentifier title_oid;
    private static final int[] userid_data;
    public static final ObjectIdentifier userid_oid;
    private volatile List<AVA> allAvaList;
    private String canonicalDn;
    private String dn;
    private byte[] encoded;
    private RDN[] names;
    private volatile List<RDN> rdnList;
    private String rfc1779Dn;
    private String rfc2253Dn;
    private X500Principal x500Principal;

    static {
        int[] iArr = {2, 5, 4, 3};
        commonName_data = iArr;
        int[] iArr2 = {2, 5, 4, 4};
        SURNAME_DATA = iArr2;
        int[] iArr3 = {2, 5, 4, 5};
        SERIALNUMBER_DATA = iArr3;
        int[] iArr4 = {2, 5, 4, 6};
        countryName_data = iArr4;
        int[] iArr5 = {2, 5, 4, 7};
        localityName_data = iArr5;
        int[] iArr6 = {2, 5, 4, 8};
        stateName_data = iArr6;
        int[] iArr7 = {2, 5, 4, 9};
        streetAddress_data = iArr7;
        int[] iArr8 = {2, 5, 4, 10};
        orgName_data = iArr8;
        int[] iArr9 = {2, 5, 4, 11};
        orgUnitName_data = iArr9;
        int[] iArr10 = {2, 5, 4, 12};
        title_data = iArr10;
        int[] iArr11 = {2, 5, 4, 42};
        GIVENNAME_DATA = iArr11;
        int[] iArr12 = {2, 5, 4, 43};
        INITIALS_DATA = iArr12;
        int[] iArr13 = {2, 5, 4, 44};
        GENERATIONQUALIFIER_DATA = iArr13;
        int[] iArr14 = {2, 5, 4, 46};
        DNQUALIFIER_DATA = iArr14;
        int[] iArr15 = {1, 3, 6, 1, 4, 1, 42, 2, 11, 2, 1};
        ipAddress_data = iArr15;
        int[] iArr16 = {0, 9, 2342, 19200300, 100, 1, 25};
        DOMAIN_COMPONENT_DATA = iArr16;
        int[] iArr17 = {0, 9, 2342, 19200300, 100, 1, 1};
        userid_data = iArr17;
        commonName_oid = intern(ObjectIdentifier.newInternal(iArr));
        SERIALNUMBER_OID = intern(ObjectIdentifier.newInternal(iArr3));
        countryName_oid = intern(ObjectIdentifier.newInternal(iArr4));
        localityName_oid = intern(ObjectIdentifier.newInternal(iArr5));
        orgName_oid = intern(ObjectIdentifier.newInternal(iArr8));
        orgUnitName_oid = intern(ObjectIdentifier.newInternal(iArr9));
        stateName_oid = intern(ObjectIdentifier.newInternal(iArr6));
        streetAddress_oid = intern(ObjectIdentifier.newInternal(iArr7));
        title_oid = intern(ObjectIdentifier.newInternal(iArr10));
        DNQUALIFIER_OID = intern(ObjectIdentifier.newInternal(iArr14));
        SURNAME_OID = intern(ObjectIdentifier.newInternal(iArr2));
        GIVENNAME_OID = intern(ObjectIdentifier.newInternal(iArr11));
        INITIALS_OID = intern(ObjectIdentifier.newInternal(iArr12));
        GENERATIONQUALIFIER_OID = intern(ObjectIdentifier.newInternal(iArr13));
        ipAddress_oid = intern(ObjectIdentifier.newInternal(iArr15));
        DOMAIN_COMPONENT_OID = intern(ObjectIdentifier.newInternal(iArr16));
        userid_oid = intern(ObjectIdentifier.newInternal(iArr17));
    }

    public X500Name(DerInputStream derInputStream) {
        parseDER(derInputStream);
    }

    public static int countQuotes(String str, int i2, int i3) {
        int i4 = 0;
        for (int i5 = i2; i5 < i3; i5++) {
            if ((str.charAt(i5) == '\"' && i5 == i2) || (str.charAt(i5) == '\"' && str.charAt(i5 - 1) != '\\')) {
                i4++;
            }
        }
        return i4;
    }

    private static boolean escaped(int i2, int i3, String str) {
        if (i2 == 1 && str.charAt(0) == '\\') {
            return true;
        }
        if (i2 > 1 && str.charAt(i2 - 1) == '\\' && str.charAt(i2 - 2) != '\\') {
            return true;
        }
        if (i2 <= 1 || str.charAt(i2 - 1) != '\\' || str.charAt(i2 - 2) != '\\') {
            return false;
        }
        int i4 = 0;
        for (int i5 = i2 - 1; i5 >= i3; i5--) {
            if (str.charAt(i5) == '\\') {
                i4++;
            }
        }
        return i4 % 2 != 0;
    }

    private DerValue findAttribute(ObjectIdentifier objectIdentifier) {
        RDN[] rdnArr = this.names;
        if (rdnArr == null) {
            return null;
        }
        for (RDN rdn : rdnArr) {
            DerValue findAttribute = rdn.findAttribute(objectIdentifier);
            if (findAttribute != null) {
                return findAttribute;
            }
        }
        return null;
    }

    private void generateDN() {
        String sb;
        RDN[] rdnArr = this.names;
        if (rdnArr.length == 1) {
            sb = rdnArr[0].toString();
        } else {
            StringBuilder sb2 = new StringBuilder(48);
            RDN[] rdnArr2 = this.names;
            if (rdnArr2 != null) {
                for (int length = rdnArr2.length - 1; length >= 0; length--) {
                    if (length != this.names.length - 1) {
                        sb2.append(", ");
                    }
                    sb2.append(this.names[length].toString());
                }
            }
            sb = sb2.toString();
        }
        this.dn = sb;
    }

    private String generateRFC1779DN(Map<String, String> map) {
        RDN[] rdnArr = this.names;
        if (rdnArr.length == 1) {
            return rdnArr[0].toRFC1779String(map);
        }
        StringBuilder sb = new StringBuilder(48);
        RDN[] rdnArr2 = this.names;
        if (rdnArr2 != null) {
            for (int length = rdnArr2.length - 1; length >= 0; length--) {
                if (length != this.names.length - 1) {
                    sb.append(", ");
                }
                sb.append(this.names[length].toRFC1779String(map));
            }
        }
        return sb.toString();
    }

    private String generateRFC2253DN(Map<String, String> map) {
        if (this.names.length == 0) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder(48);
        for (int length = this.names.length - 1; length >= 0; length--) {
            if (length < this.names.length - 1) {
                sb.append(',');
            }
            sb.append(this.names[length].toRFC2253String(map));
        }
        return sb.toString();
    }

    private String getString(DerValue derValue) {
        if (derValue == null) {
            return null;
        }
        String asString = derValue.getAsString();
        if (asString != null) {
            return asString;
        }
        throw new IOException("not a DER string encoding, " + ((int) derValue.tag));
    }

    public static ObjectIdentifier intern(ObjectIdentifier objectIdentifier) {
        Map<ObjectIdentifier, ObjectIdentifier> map = internedOIDs;
        ObjectIdentifier objectIdentifier2 = map.get(objectIdentifier);
        if (objectIdentifier2 != null) {
            return objectIdentifier2;
        }
        map.put(objectIdentifier, objectIdentifier);
        return objectIdentifier;
    }

    private boolean isWithinSubtree(X500Name x500Name) {
        if (this == x500Name) {
            return true;
        }
        if (x500Name == null) {
            return false;
        }
        RDN[] rdnArr = x500Name.names;
        if (rdnArr.length == 0) {
            return true;
        }
        RDN[] rdnArr2 = this.names;
        if (rdnArr2.length == 0 || rdnArr2.length < rdnArr.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            RDN[] rdnArr3 = x500Name.names;
            if (i2 >= rdnArr3.length) {
                return true;
            }
            if (!this.names[i2].equals(rdnArr3[i2])) {
                return false;
            }
            i2++;
        }
    }

    private void parseDER(DerInputStream derInputStream) {
        DerValue[] sequence;
        byte[] byteArray = derInputStream.toByteArray();
        try {
            sequence = derInputStream.getSequence(5);
        } catch (IOException unused) {
            sequence = byteArray == null ? null : new DerInputStream(new DerValue((byte) 48, byteArray).toByteArray()).getSequence(5);
        }
        if (sequence == null) {
            this.names = new RDN[0];
            return;
        }
        this.names = new RDN[sequence.length];
        for (int i2 = 0; i2 < sequence.length; i2++) {
            this.names[i2] = new RDN(sequence[i2]);
        }
    }

    private void parseDN(String str, Map<String, String> map) {
        if (str == null || str.length() == 0) {
            this.names = new RDN[0];
            return;
        }
        this.x500Principal = new X500Principal(str, map);
        ArrayList arrayList = new ArrayList();
        int indexOf = str.indexOf(44);
        int indexOf2 = str.indexOf(59);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (indexOf < 0 && indexOf2 < 0) {
                arrayList.add(new RDN(str.substring(i2), map));
                Collections.reverse(arrayList);
                this.names = (RDN[]) arrayList.toArray(new RDN[0]);
                return;
            }
            if (indexOf2 >= 0) {
                indexOf = indexOf < 0 ? indexOf2 : Math.min(indexOf, indexOf2);
            }
            int countQuotes = countQuotes(str, i4, indexOf) + i3;
            if (countQuotes == 1 || escaped(indexOf, i4, str)) {
                i3 = countQuotes;
            } else {
                arrayList.add(new RDN(str.substring(i2, indexOf), map));
                i2 = indexOf + 1;
                i3 = 0;
            }
            i4 = indexOf + 1;
            indexOf = str.indexOf(44, i4);
            indexOf2 = str.indexOf(59, i4);
        }
    }

    private void parseRFC2253DN(String str) {
        if (str.length() == 0) {
            this.names = new RDN[0];
            return;
        }
        ArrayList arrayList = new ArrayList();
        int indexOf = str.indexOf(44);
        int i2 = 0;
        int i3 = 0;
        while (indexOf >= 0) {
            if (indexOf > 0 && !escaped(indexOf, i3, str)) {
                arrayList.add(new RDN(str.substring(i2, indexOf), "RFC2253"));
                i2 = indexOf + 1;
            }
            i3 = indexOf + 1;
            indexOf = str.indexOf(44, i3);
        }
        arrayList.add(new RDN(str.substring(i2), "RFC2253"));
        Collections.reverse(arrayList);
        this.names = (RDN[]) arrayList.toArray(new RDN[0]);
    }

    public List<AVA> allAvas() {
        List<AVA> list = this.allAvaList;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (RDN rdn : this.names) {
            arrayList.addAll(rdn.avas());
        }
        List<AVA> unmodifiableList = Collections.unmodifiableList(arrayList);
        this.allAvaList = unmodifiableList;
        return unmodifiableList;
    }

    @Nullable
    public X500Principal asX500Principal() {
        return this.x500Principal;
    }

    public int avaSize() {
        return allAvas().size();
    }

    public X500Name commonAncestor(X500Name x500Name) {
        if (x500Name == null) {
            return null;
        }
        int length = x500Name.names.length;
        int length2 = this.names.length;
        if (length2 != 0 && length != 0) {
            int min = Math.min(length2, length);
            int i2 = 0;
            while (true) {
                if (i2 >= min) {
                    break;
                }
                if (this.names[i2].equals(x500Name.names[i2])) {
                    i2++;
                } else if (i2 == 0) {
                    return null;
                }
            }
            RDN[] rdnArr = new RDN[i2];
            System.arraycopy(this.names, 0, rdnArr, 0, i2);
            try {
                return new X500Name(rdnArr);
            } catch (IOException unused) {
            }
        }
        return null;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 4) {
            return -1;
        }
        X500Name x500Name = (X500Name) generalNameInterface;
        if (x500Name.equals(this)) {
            return 0;
        }
        if (x500Name.names.length != 0) {
            if (this.names.length == 0 || x500Name.isWithinSubtree(this)) {
                return 1;
            }
            if (!isWithinSubtree(x500Name)) {
                return 3;
            }
        }
        return 2;
    }

    @Deprecated
    public void emit(DerOutputStream derOutputStream) {
        encode(derOutputStream);
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (RDN rdn : this.names) {
            rdn.encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    @Override // java.security.Principal
    public boolean equals(Object obj) {
        String str;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof X500Name)) {
            return false;
        }
        X500Name x500Name = (X500Name) obj;
        String str2 = this.canonicalDn;
        if (str2 != null && (str = x500Name.canonicalDn) != null) {
            return str2.equals(str);
        }
        int length = this.names.length;
        if (length != x500Name.names.length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (this.names[i2].assertion.length != x500Name.names[i2].assertion.length) {
                return false;
            }
        }
        return getRFC2253CanonicalName().equals(x500Name.getRFC2253CanonicalName());
    }

    public DerValue findMostSpecificAttribute(ObjectIdentifier objectIdentifier) {
        RDN[] rdnArr = this.names;
        if (rdnArr == null) {
            return null;
        }
        for (int length = rdnArr.length - 1; length >= 0; length--) {
            DerValue findAttribute = this.names[length].findAttribute(objectIdentifier);
            if (findAttribute != null) {
                return findAttribute;
            }
        }
        return null;
    }

    public String getCommonName() {
        return getString(findAttribute(commonName_oid));
    }

    public String getCountry() {
        return getString(findAttribute(countryName_oid));
    }

    public String getDNQualifier() {
        return getString(findAttribute(DNQUALIFIER_OID));
    }

    public String getDomain() {
        return getString(findAttribute(DOMAIN_COMPONENT_OID));
    }

    public byte[] getEncoded() {
        return (byte[]) getEncodedInternal().clone();
    }

    public byte[] getEncodedInternal() {
        if (this.encoded == null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            for (RDN rdn : this.names) {
                rdn.encode(derOutputStream2);
            }
            derOutputStream.write((byte) 48, derOutputStream2);
            this.encoded = derOutputStream.toByteArray();
        }
        return this.encoded;
    }

    public String getGeneration() {
        return getString(findAttribute(GENERATIONQUALIFIER_OID));
    }

    public String getGivenName() {
        return getString(findAttribute(GIVENNAME_OID));
    }

    public String getIP() {
        return getString(findAttribute(ipAddress_oid));
    }

    public String getInitials() {
        return getString(findAttribute(INITIALS_OID));
    }

    public String getLocality() {
        return getString(findAttribute(localityName_oid));
    }

    @Override // java.security.Principal
    public String getName() {
        return toString();
    }

    public String getOrganization() {
        return getString(findAttribute(orgName_oid));
    }

    public String getOrganizationalUnit() {
        return getString(findAttribute(orgUnitName_oid));
    }

    public String getRFC1779Name() {
        return getRFC1779Name(Collections.emptyMap());
    }

    public String getRFC2253CanonicalName() {
        String sb;
        String str = this.canonicalDn;
        if (str != null) {
            return str;
        }
        if (this.names.length == 0) {
            sb = BuildConfig.FLAVOR;
        } else {
            StringBuilder sb2 = new StringBuilder(48);
            for (int length = this.names.length - 1; length >= 0; length--) {
                if (length < this.names.length - 1) {
                    sb2.append(',');
                }
                sb2.append(this.names[length].toRFC2253String(true));
            }
            sb = sb2.toString();
        }
        this.canonicalDn = sb;
        return sb;
    }

    public String getRFC2253Name() {
        return getRFC2253Name(Collections.emptyMap());
    }

    public String getState() {
        return getString(findAttribute(stateName_oid));
    }

    public String getSurname() {
        return getString(findAttribute(SURNAME_OID));
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 4;
    }

    @Override // java.security.Principal
    public int hashCode() {
        return getRFC2253CanonicalName().hashCode();
    }

    public boolean isEmpty() {
        RDN[] rdnArr = this.names;
        if (rdnArr.length == 0) {
            return true;
        }
        for (RDN rdn : rdnArr) {
            if (rdn.assertion.length != 0) {
                return false;
            }
        }
        return true;
    }

    public List<RDN> rdns() {
        List<RDN> list = this.rdnList;
        if (list != null) {
            return list;
        }
        List<RDN> unmodifiableList = Collections.unmodifiableList(Arrays.asList(this.names));
        this.rdnList = unmodifiableList;
        return unmodifiableList;
    }

    public int size() {
        return this.names.length;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        return this.names.length;
    }

    @Override // java.security.Principal
    @NonNull
    public String toString() {
        if (this.dn == null) {
            generateDN();
        }
        return this.dn;
    }

    public X500Name(DerValue derValue) {
        this(derValue.toDerInputStream());
    }

    public String getRFC1779Name(Map<String, String> map) {
        if (!map.isEmpty()) {
            return generateRFC1779DN(map);
        }
        if (this.rfc1779Dn == null) {
            this.rfc1779Dn = generateRFC1779DN(map);
        }
        return this.rfc1779Dn;
    }

    public String getRFC2253Name(Map<String, String> map) {
        if (!map.isEmpty()) {
            return generateRFC2253DN(map);
        }
        if (this.rfc2253Dn == null) {
            this.rfc2253Dn = generateRFC2253DN(map);
        }
        return this.rfc2253Dn;
    }

    public X500Name(String str) {
        this(str, (Map<String, String>) Collections.emptyMap());
    }

    public X500Name(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("Name must not be null");
        }
        if (str2.equalsIgnoreCase("RFC2253")) {
            parseRFC2253DN(str);
        } else {
            if (!str2.equalsIgnoreCase("DEFAULT")) {
                throw new IOException("Unsupported format ".concat(str2));
            }
            parseDN(str, Collections.emptyMap());
        }
    }

    public X500Name(String str, String str2, String str3, String str4) {
        RDN[] rdnArr = new RDN[4];
        this.names = rdnArr;
        rdnArr[3] = new RDN(1);
        this.names[3].assertion[0] = new AVA(commonName_oid, new DerValue(str));
        this.names[2] = new RDN(1);
        this.names[2].assertion[0] = new AVA(orgUnitName_oid, new DerValue(str2));
        this.names[1] = new RDN(1);
        this.names[1].assertion[0] = new AVA(orgName_oid, new DerValue(str3));
        this.names[0] = new RDN(1);
        this.names[0].assertion[0] = new AVA(countryName_oid, new DerValue(str4));
    }

    public X500Name(String str, String str2, String str3, String str4, String str5, String str6) {
        RDN[] rdnArr = new RDN[6];
        this.names = rdnArr;
        rdnArr[5] = new RDN(1);
        this.names[5].assertion[0] = new AVA(commonName_oid, new DerValue(str));
        this.names[4] = new RDN(1);
        this.names[4].assertion[0] = new AVA(orgUnitName_oid, new DerValue(str2));
        this.names[3] = new RDN(1);
        this.names[3].assertion[0] = new AVA(orgName_oid, new DerValue(str3));
        this.names[2] = new RDN(1);
        this.names[2].assertion[0] = new AVA(localityName_oid, new DerValue(str4));
        this.names[1] = new RDN(1);
        this.names[1].assertion[0] = new AVA(stateName_oid, new DerValue(str5));
        this.names[0] = new RDN(1);
        this.names[0].assertion[0] = new AVA(countryName_oid, new DerValue(str6));
    }

    public X500Name(String str, Map<String, String> map) {
        parseDN(str, map);
    }

    public X500Name(byte[] bArr) {
        parseDER(new DerInputStream(bArr));
    }

    public X500Name(RDN[] rdnArr) {
        if (rdnArr == null) {
            this.names = new RDN[0];
            return;
        }
        RDN[] rdnArr2 = (RDN[]) rdnArr.clone();
        this.names = rdnArr2;
        for (RDN rdn : rdnArr2) {
            if (rdn == null) {
                throw new IOException("Cannot create an X500Name");
            }
        }
    }
}
