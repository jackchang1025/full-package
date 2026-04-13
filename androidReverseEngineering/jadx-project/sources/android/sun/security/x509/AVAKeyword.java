package android.sun.security.x509;

import android.sun.security.pkcs.PKCS9Attribute;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class AVAKeyword {
    private final String keyword;
    private final ObjectIdentifier oid;
    private final boolean rfc1779Compliant;
    private final boolean rfc2253Compliant;
    private static final Map<ObjectIdentifier, AVAKeyword> oidMap = new HashMap();
    private static final Map<String, AVAKeyword> keywordMap = new HashMap();

    static {
        new AVAKeyword("CN", X500Name.commonName_oid, true, true);
        new AVAKeyword("C", X500Name.countryName_oid, true, true);
        new AVAKeyword("L", X500Name.localityName_oid, true, true);
        ObjectIdentifier objectIdentifier = X500Name.stateName_oid;
        new AVAKeyword("S", objectIdentifier, false, false);
        new AVAKeyword("ST", objectIdentifier, true, true);
        new AVAKeyword("O", X500Name.orgName_oid, true, true);
        new AVAKeyword("OU", X500Name.orgUnitName_oid, true, true);
        new AVAKeyword("T", X500Name.title_oid, false, false);
        new AVAKeyword("IP", X500Name.ipAddress_oid, false, false);
        new AVAKeyword("STREET", X500Name.streetAddress_oid, true, true);
        new AVAKeyword("DC", X500Name.DOMAIN_COMPONENT_OID, false, true);
        ObjectIdentifier objectIdentifier2 = X500Name.DNQUALIFIER_OID;
        new AVAKeyword("DNQUALIFIER", objectIdentifier2, false, false);
        new AVAKeyword("DNQ", objectIdentifier2, false, false);
        new AVAKeyword("SURNAME", X500Name.SURNAME_OID, false, false);
        new AVAKeyword("GIVENNAME", X500Name.GIVENNAME_OID, false, false);
        new AVAKeyword("INITIALS", X500Name.INITIALS_OID, false, false);
        new AVAKeyword("GENERATION", X500Name.GENERATIONQUALIFIER_OID, false, false);
        ObjectIdentifier objectIdentifier3 = PKCS9Attribute.EMAIL_ADDRESS_OID;
        new AVAKeyword("EMAIL", objectIdentifier3, false, false);
        new AVAKeyword("EMAILADDRESS", objectIdentifier3, false, false);
        new AVAKeyword("UID", X500Name.userid_oid, false, true);
        new AVAKeyword("SERIALNUMBER", X500Name.SERIALNUMBER_OID, false, false);
    }

    private AVAKeyword(String str, ObjectIdentifier objectIdentifier, boolean z2, boolean z3) {
        this.keyword = str;
        this.oid = objectIdentifier;
        this.rfc1779Compliant = z2;
        this.rfc2253Compliant = z3;
        oidMap.put(objectIdentifier, this);
        keywordMap.put(str, this);
    }

    public static String getKeyword(ObjectIdentifier objectIdentifier, int i2) {
        return getKeyword(objectIdentifier, i2, Collections.emptyMap());
    }

    public static ObjectIdentifier getOID(String str, int i2) {
        return getOID(str, i2, Collections.emptyMap());
    }

    public static boolean hasKeyword(ObjectIdentifier objectIdentifier, int i2) {
        AVAKeyword aVAKeyword = oidMap.get(objectIdentifier);
        if (aVAKeyword == null) {
            return false;
        }
        return aVAKeyword.isCompliant(i2);
    }

    private boolean isCompliant(int i2) {
        if (i2 == 1) {
            return true;
        }
        if (i2 == 2) {
            return this.rfc1779Compliant;
        }
        if (i2 == 3) {
            return this.rfc2253Compliant;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid standard ", i2));
    }

    public static String getKeyword(ObjectIdentifier objectIdentifier, int i2, Map<String, String> map) {
        String objectIdentifier2 = objectIdentifier.toString();
        String str = map.get(objectIdentifier2);
        if (str == null) {
            AVAKeyword aVAKeyword = oidMap.get(objectIdentifier);
            return (aVAKeyword == null || !aVAKeyword.isCompliant(i2)) ? i2 == 3 ? objectIdentifier2 : AbstractC0000a.m15k("OID.", objectIdentifier2) : aVAKeyword.keyword;
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("keyword cannot be empty");
        }
        String trim = str.trim();
        char charAt = trim.charAt(0);
        if (charAt < 'A' || charAt > 'z' || (charAt > 'Z' && charAt < 'a')) {
            throw new IllegalArgumentException("keyword does not start with letter");
        }
        for (int i3 = 1; i3 < trim.length(); i3++) {
            char charAt2 = trim.charAt(i3);
            if ((charAt2 < 'A' || charAt2 > 'z' || (charAt2 > 'Z' && charAt2 < 'a')) && ((charAt2 < '0' || charAt2 > '9') && charAt2 != '_')) {
                throw new IllegalArgumentException("keyword character is not a letter, digit, or underscore");
            }
        }
        return trim;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0065, code lost:
    
        if (r4.startsWith("OID.") != false) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ObjectIdentifier getOID(String str, int i2, Map<String, String> map) {
        char charAt;
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        if (i2 != 3) {
            upperCase = upperCase.trim();
        } else if (upperCase.startsWith(" ") || upperCase.endsWith(" ")) {
            throw new IOException(AbstractC0000a.m16l("Invalid leading or trailing space in keyword \"", upperCase, "\""));
        }
        String str2 = map.get(upperCase);
        if (str2 != null) {
            return new ObjectIdentifier(str2);
        }
        AVAKeyword aVAKeyword = keywordMap.get(upperCase);
        if (aVAKeyword != null && aVAKeyword.isCompliant(i2)) {
            return aVAKeyword.oid;
        }
        if (i2 != 2) {
            if (i2 == 1) {
            }
            if (upperCase.length() == 0 && (charAt = upperCase.charAt(0)) >= '0' && charAt <= '9') {
                throw new IOException(AbstractC0000a.m16l("Invalid keyword \"", upperCase, "\""));
            }
            return new ObjectIdentifier(upperCase);
        }
        if (!upperCase.startsWith("OID.")) {
            throw new IOException("Invalid RFC1779 keyword: ".concat(upperCase));
        }
        upperCase = upperCase.substring(4);
        if (upperCase.length() == 0 && (charAt = upperCase.charAt(0)) >= '0' && charAt <= '9') {
        }
    }
}
