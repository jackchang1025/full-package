package org.bouncycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1UniversalString;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class IETFUtils {
    public static void appendRDN(StringBuffer stringBuffer, RDN rdn, Hashtable hashtable) {
        if (!rdn.isMultiValued()) {
            if (rdn.getFirst() != null) {
                appendTypeAndValue(stringBuffer, rdn.getFirst(), hashtable);
                return;
            }
            return;
        }
        AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
        boolean z2 = true;
        for (int i2 = 0; i2 != typesAndValues.length; i2++) {
            if (z2) {
                z2 = false;
            } else {
                stringBuffer.append('+');
            }
            appendTypeAndValue(stringBuffer, typesAndValues[i2], hashtable);
        }
    }

    public static void appendTypeAndValue(StringBuffer stringBuffer, AttributeTypeAndValue attributeTypeAndValue, Hashtable hashtable) {
        String str = (String) hashtable.get(attributeTypeAndValue.getType());
        if (str == null) {
            str = attributeTypeAndValue.getType().getId();
        }
        stringBuffer.append(str);
        stringBuffer.append('=');
        stringBuffer.append(valueToString(attributeTypeAndValue.getValue()));
    }

    private static boolean atvAreEqual(AttributeTypeAndValue attributeTypeAndValue, AttributeTypeAndValue attributeTypeAndValue2) {
        if (attributeTypeAndValue == attributeTypeAndValue2) {
            return true;
        }
        return attributeTypeAndValue != null && attributeTypeAndValue2 != null && attributeTypeAndValue.getType().equals((ASN1Primitive) attributeTypeAndValue2.getType()) && canonicalString(attributeTypeAndValue.getValue()).equals(canonicalString(attributeTypeAndValue2.getValue()));
    }

    public static String canonicalString(ASN1Encodable aSN1Encodable) {
        return canonicalize(valueToString(aSN1Encodable));
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:
    
        if (r5 >= r0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String canonicalize(String str) {
        int i2 = 0;
        if (str.length() > 0 && str.charAt(0) == '#') {
            ASN1Encodable decodeObject = decodeObject(str);
            if (decodeObject instanceof ASN1String) {
                str = ((ASN1String) decodeObject).getString();
            }
        }
        String lowerCase = Strings.toLowerCase(str);
        int length = lowerCase.length();
        if (length < 2) {
            return lowerCase;
        }
        int i3 = length - 1;
        while (i2 < i3 && lowerCase.charAt(i2) == '\\' && lowerCase.charAt(i2 + 1) == ' ') {
            i2 += 2;
        }
        int i4 = i2 + 1;
        int i5 = i3;
        while (i5 > i4 && lowerCase.charAt(i5 - 1) == '\\' && lowerCase.charAt(i5) == ' ') {
            i5 -= 2;
        }
        lowerCase = lowerCase.substring(i2, i5 + 1);
        return stripInternalSpaces(lowerCase);
    }

    private static int convertHex(char c) {
        if ('0' > c || c > '9') {
            return (('a' > c || c > 'f') ? c - 'A' : c - 'a') + 10;
        }
        return c - '0';
    }

    public static ASN1ObjectIdentifier decodeAttrName(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(str);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException(AbstractC0000a.m16l("Unknown object id - ", str, " - passed to distinguished name"));
    }

    private static ASN1Primitive decodeObject(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m13i("unknown encoding in name: ", e2));
        }
    }

    public static String[] findAttrNamesForOID(ASN1ObjectIdentifier aSN1ObjectIdentifier, Hashtable hashtable) {
        Enumeration elements = hashtable.elements();
        int i2 = 0;
        int i3 = 0;
        while (elements.hasMoreElements()) {
            if (aSN1ObjectIdentifier.equals(elements.nextElement())) {
                i3++;
            }
        }
        String[] strArr = new String[i3];
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            String str = (String) keys.nextElement();
            if (aSN1ObjectIdentifier.equals(hashtable.get(str))) {
                strArr[i2] = str;
                i2++;
            }
        }
        return strArr;
    }

    private static boolean isHexDigit(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    public static boolean rDNAreEqual(RDN rdn, RDN rdn2) {
        if (rdn.size() != rdn2.size()) {
            return false;
        }
        AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
        AttributeTypeAndValue[] typesAndValues2 = rdn2.getTypesAndValues();
        if (typesAndValues.length != typesAndValues2.length) {
            return false;
        }
        for (int i2 = 0; i2 != typesAndValues.length; i2++) {
            if (!atvAreEqual(typesAndValues[i2], typesAndValues2[i2])) {
                return false;
            }
        }
        return true;
    }

    public static RDN[] rDNsFromString(String str, X500NameStyle x500NameStyle) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str);
        X500NameBuilder x500NameBuilder = new X500NameBuilder(x500NameStyle);
        while (x500NameTokenizer.hasMoreTokens()) {
            String nextToken = x500NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                X500NameTokenizer x500NameTokenizer2 = new X500NameTokenizer(nextToken, '+');
                X500NameTokenizer x500NameTokenizer3 = new X500NameTokenizer(x500NameTokenizer2.nextToken(), '=');
                String nextToken2 = x500NameTokenizer3.nextToken();
                if (!x500NameTokenizer3.hasMoreTokens()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                String nextToken3 = x500NameTokenizer3.nextToken();
                ASN1ObjectIdentifier attrNameToOID = x500NameStyle.attrNameToOID(nextToken2.trim());
                if (x500NameTokenizer2.hasMoreTokens()) {
                    Vector vector = new Vector();
                    Vector vector2 = new Vector();
                    while (true) {
                        vector.addElement(attrNameToOID);
                        vector2.addElement(unescape(nextToken3));
                        if (!x500NameTokenizer2.hasMoreTokens()) {
                            x500NameBuilder.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
                            break;
                        }
                        X500NameTokenizer x500NameTokenizer4 = new X500NameTokenizer(x500NameTokenizer2.nextToken(), '=');
                        String nextToken4 = x500NameTokenizer4.nextToken();
                        if (!x500NameTokenizer4.hasMoreTokens()) {
                            throw new IllegalArgumentException("badly formatted directory string");
                        }
                        nextToken3 = x500NameTokenizer4.nextToken();
                        attrNameToOID = x500NameStyle.attrNameToOID(nextToken4.trim());
                    }
                } else {
                    x500NameBuilder.addRDN(attrNameToOID, unescape(nextToken3));
                }
            } else {
                X500NameTokenizer x500NameTokenizer5 = new X500NameTokenizer(nextToken, '=');
                String nextToken5 = x500NameTokenizer5.nextToken();
                if (!x500NameTokenizer5.hasMoreTokens()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                x500NameBuilder.addRDN(x500NameStyle.attrNameToOID(nextToken5.trim()), unescape(x500NameTokenizer5.nextToken()));
            }
        }
        return x500NameBuilder.build().getRDNs();
    }

    public static String stripInternalSpaces(String str) {
        if (str.indexOf("  ") < 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char charAt = str.charAt(0);
        stringBuffer.append(charAt);
        for (int i2 = 1; i2 < str.length(); i2++) {
            char charAt2 = str.charAt(i2);
            if (charAt != ' ' || charAt2 != ' ') {
                stringBuffer.append(charAt2);
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    private static ASN1ObjectIdentifier[] toOIDArray(Vector vector) {
        int size = vector.size();
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[size];
        for (int i2 = 0; i2 != size; i2++) {
            aSN1ObjectIdentifierArr[i2] = (ASN1ObjectIdentifier) vector.elementAt(i2);
        }
        return aSN1ObjectIdentifierArr;
    }

    private static String[] toValueArray(Vector vector) {
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 != size; i2++) {
            strArr[i2] = (String) vector.elementAt(i2);
        }
        return strArr;
    }

    private static String unescape(String str) {
        int i2;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i2 = 2;
        } else {
            i2 = 0;
        }
        boolean z2 = false;
        int i3 = 0;
        boolean z3 = false;
        boolean z4 = false;
        char c = 0;
        while (i2 != charArray.length) {
            char c2 = charArray[i2];
            if (c2 != ' ') {
                z4 = true;
            }
            if (c2 != '\"') {
                if (c2 == '\\' && !z2 && !z3) {
                    i3 = stringBuffer.length();
                    z2 = true;
                } else if (c2 != ' ' || z2 || z4) {
                    if (z2 && isHexDigit(c2)) {
                        if (c != 0) {
                            stringBuffer.append((char) ((convertHex(c) * 16) + convertHex(c2)));
                            z2 = false;
                            c = 0;
                        } else {
                            c = c2;
                        }
                    }
                }
                i2++;
            } else if (!z2) {
                z3 = !z3;
                z2 = false;
                i2++;
            }
            stringBuffer.append(c2);
            z2 = false;
            i2++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i3 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public static ASN1Encodable valueFromHexString(String str, int i2) {
        int length = (str.length() - i2) / 2;
        byte[] bArr = new byte[length];
        for (int i3 = 0; i3 != length; i3++) {
            int i4 = (i3 * 2) + i2;
            char charAt = str.charAt(i4);
            char charAt2 = str.charAt(i4 + 1);
            bArr[i3] = (byte) (convertHex(charAt2) | (convertHex(charAt) << 4));
        }
        return ASN1Primitive.fromByteArray(bArr);
    }

    public static String valueToString(ASN1Encodable aSN1Encodable) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!(aSN1Encodable instanceof ASN1String) || (aSN1Encodable instanceof ASN1UniversalString)) {
            try {
                stringBuffer.append('#');
                stringBuffer.append(Hex.toHexString(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER)));
            } catch (IOException unused) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        } else {
            String string = ((ASN1String) aSN1Encodable).getString();
            if (string.length() > 0 && string.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(string);
        }
        int length = stringBuffer.length();
        int i2 = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i2 != length) {
            char charAt = stringBuffer.charAt(i2);
            if (charAt != '\"' && charAt != '\\' && charAt != '+' && charAt != ',') {
                switch (charAt) {
                    case ';':
                    case '<':
                    case '=':
                    case CipherSuite.TLS_DH_DSS_WITH_AES_128_CBC_SHA256 /* 62 */:
                        break;
                    default:
                        i2++;
                        continue;
                }
            }
            stringBuffer.insert(i2, "\\");
            i2 += 2;
            length++;
        }
        if (stringBuffer.length() > 0) {
            for (int i3 = 0; stringBuffer.length() > i3 && stringBuffer.charAt(i3) == ' '; i3 += 2) {
                stringBuffer.insert(i3, "\\");
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= 0 && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }
}
