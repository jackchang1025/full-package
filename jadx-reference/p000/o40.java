package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class o40 {
    public static void appendRDN(StringBuffer stringBuffer, np0 np0Var, Hashtable hashtable) {
        if (!np0Var.isMultiValued()) {
            if (np0Var.getFirst() != null) {
                appendTypeAndValue(stringBuffer, np0Var.getFirst(), hashtable);
                return;
            }
            return;
        }
        C0145bs[] typesAndValues = np0Var.getTypesAndValues();
        boolean z = true;
        for (int i = 0; i != typesAndValues.length; i++) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append('+');
            }
            appendTypeAndValue(stringBuffer, typesAndValues[i], hashtable);
        }
    }

    public static void appendTypeAndValue(StringBuffer stringBuffer, C0145bs c0145bs, Hashtable hashtable) {
        String id = (String) hashtable.get(c0145bs.getType());
        if (id == null) {
            id = c0145bs.getType().getId();
        }
        stringBuffer.append(id);
        stringBuffer.append('=');
        stringBuffer.append(valueToString(c0145bs.getValue()));
    }

    private static boolean atvAreEqual(C0145bs c0145bs, C0145bs c0145bs2) {
        if (c0145bs == c0145bs2) {
            return true;
        }
        return c0145bs != null && c0145bs2 != null && c0145bs.getType().equals((AbstractC0164c9) c0145bs2.getType()) && canonicalString(c0145bs.getValue()).equals(canonicalString(c0145bs2.getValue()));
    }

    public static String canonicalString(InterfaceC0117b0 interfaceC0117b0) {
        return canonicalize(valueToString(interfaceC0117b0));
    }

    public static String canonicalize(String str) {
        int i = 0;
        if (str.length() > 0 && str.charAt(0) == '#') {
            InterfaceC1394wy interfaceC1394wyDecodeObject = decodeObject(str);
            if (interfaceC1394wyDecodeObject instanceof InterfaceC0405d7) {
                str = ((InterfaceC0405d7) interfaceC1394wyDecodeObject).getString();
            }
        }
        String lowerCase = Strings.toLowerCase(str);
        int length = lowerCase.length();
        if (length < 2) {
            return lowerCase;
        }
        int i2 = length - 1;
        while (i < i2 && lowerCase.charAt(i) == '\\' && lowerCase.charAt(i + 1) == ' ') {
            i += 2;
        }
        int i3 = i + 1;
        int i4 = i2;
        while (i4 > i3 && lowerCase.charAt(i4 - 1) == '\\' && lowerCase.charAt(i4) == ' ') {
            i4 -= 2;
        }
        if (i > 0 || i4 < i2) {
            lowerCase = lowerCase.substring(i, i4 + 1);
        }
        return stripInternalSpaces(lowerCase);
    }

    private static int convertHex(char c) {
        return ('0' > c || c > '9') ? ('a' > c || c > 'f') ? c - '7' : c - 'W' : c - '0';
    }

    public static C0160c5 decodeAttrName(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new C0160c5(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new C0160c5(str);
        }
        C0160c5 c0160c5 = (C0160c5) hashtable.get(Strings.toLowerCase(str));
        if (c0160c5 != null) {
            return c0160c5;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m33b4("Unknown object id - ", str, " - passed to distinguished name"));
    }

    private static AbstractC0164c9 decodeObject(String str) {
        try {
            return AbstractC0164c9.fromByteArray(c40.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    public static String[] findAttrNamesForOID(C0160c5 c0160c5, Hashtable hashtable) {
        Enumeration enumerationElements = hashtable.elements();
        int i = 0;
        int i2 = 0;
        while (enumerationElements.hasMoreElements()) {
            if (c0160c5.equals(enumerationElements.nextElement())) {
                i2++;
            }
        }
        String[] strArr = new String[i2];
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            if (c0160c5.equals(hashtable.get(str))) {
                strArr[i] = str;
                i++;
            }
        }
        return strArr;
    }

    private static boolean isHexDigit(char c) {
        if ('0' <= c && c <= '9') {
            return true;
        }
        if ('a' > c || c > 'f') {
            return 'A' <= c && c <= 'F';
        }
        return true;
    }

    public static boolean rDNAreEqual(np0 np0Var, np0 np0Var2) {
        if (np0Var.size() != np0Var2.size()) {
            return false;
        }
        C0145bs[] typesAndValues = np0Var.getTypesAndValues();
        C0145bs[] typesAndValues2 = np0Var2.getTypesAndValues();
        if (typesAndValues.length != typesAndValues2.length) {
            return false;
        }
        for (int i = 0; i != typesAndValues.length; i++) {
            if (!atvAreEqual(typesAndValues[i], typesAndValues2[i])) {
                return false;
            }
        }
        return true;
    }

    public static np0[] rDNsFromString(String str, mh1 mh1Var) {
        nh1 nh1Var = new nh1(str);
        lh1 lh1Var = new lh1(mh1Var);
        while (nh1Var.hasMoreTokens()) {
            String strNextToken = nh1Var.nextToken();
            if (strNextToken.indexOf(43) > 0) {
                nh1 nh1Var2 = new nh1(strNextToken, '+');
                nh1 nh1Var3 = new nh1(nh1Var2.nextToken(), '=');
                String strNextToken2 = nh1Var3.nextToken();
                if (!nh1Var3.hasMoreTokens()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                String strNextToken3 = nh1Var3.nextToken();
                C0160c5 c0160c5AttrNameToOID = mh1Var.attrNameToOID(strNextToken2.trim());
                if (nh1Var2.hasMoreTokens()) {
                    Vector vector = new Vector();
                    Vector vector2 = new Vector();
                    while (true) {
                        vector.addElement(c0160c5AttrNameToOID);
                        vector2.addElement(unescape(strNextToken3));
                        if (!nh1Var2.hasMoreTokens()) {
                            lh1Var.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
                            break;
                        }
                        nh1 nh1Var4 = new nh1(nh1Var2.nextToken(), '=');
                        String strNextToken4 = nh1Var4.nextToken();
                        if (!nh1Var4.hasMoreTokens()) {
                            throw new IllegalArgumentException("badly formatted directory string");
                        }
                        strNextToken3 = nh1Var4.nextToken();
                        c0160c5AttrNameToOID = mh1Var.attrNameToOID(strNextToken4.trim());
                    }
                } else {
                    lh1Var.addRDN(c0160c5AttrNameToOID, unescape(strNextToken3));
                }
            } else {
                nh1 nh1Var5 = new nh1(strNextToken, '=');
                String strNextToken5 = nh1Var5.nextToken();
                if (!nh1Var5.hasMoreTokens()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                lh1Var.addRDN(mh1Var.attrNameToOID(strNextToken5.trim()), unescape(nh1Var5.nextToken()));
            }
        }
        return lh1Var.build().getRDNs();
    }

    public static String stripInternalSpaces(String str) {
        if (str.indexOf("  ") < 0) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char cCharAt = str.charAt(0);
        stringBuffer.append(cCharAt);
        for (int i = 1; i < str.length(); i++) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt != ' ' || cCharAt2 != ' ') {
                stringBuffer.append(cCharAt2);
                cCharAt = cCharAt2;
            }
        }
        return stringBuffer.toString();
    }

    private static C0160c5[] toOIDArray(Vector vector) {
        int size = vector.size();
        C0160c5[] c0160c5Arr = new C0160c5[size];
        for (int i = 0; i != size; i++) {
            c0160c5Arr[i] = (C0160c5) vector.elementAt(i);
        }
        return c0160c5Arr;
    }

    private static String[] toValueArray(Vector vector) {
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i = 0; i != size; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    private static String unescape(String str) {
        int i;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int length = 0;
        boolean z2 = false;
        boolean z3 = false;
        char c = 0;
        while (i != charArray.length) {
            char c2 = charArray[i];
            if (c2 != ' ') {
                z3 = true;
            }
            if (c2 == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                z = false;
                i++;
            } else {
                if (c2 == '\\' && !z && !z2) {
                    length = stringBuffer.length();
                    z = true;
                } else if (c2 != ' ' || z || z3) {
                    if (z && isHexDigit(c2)) {
                        if (c != 0) {
                            stringBuffer.append((char) ((convertHex(c) * 16) + convertHex(c2)));
                            z = false;
                            c = 0;
                        } else {
                            c = c2;
                        }
                    }
                }
                i++;
            }
            stringBuffer.append(c2);
            z = false;
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && length != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public static InterfaceC0117b0 valueFromHexString(String str, int i) throws IOException {
        int length = (str.length() - i) / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            int i3 = (i2 * 2) + i;
            char cCharAt = str.charAt(i3);
            char cCharAt2 = str.charAt(i3 + 1);
            bArr[i2] = (byte) (convertHex(cCharAt2) | (convertHex(cCharAt) << 4));
        }
        return AbstractC0164c9.fromByteArray(bArr);
    }

    public static String valueToString(InterfaceC0117b0 interfaceC0117b0) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!(interfaceC0117b0 instanceof InterfaceC0405d7) || (interfaceC0117b0 instanceof AbstractC0444e5)) {
            try {
                stringBuffer.append('#');
                stringBuffer.append(c40.toHexString(interfaceC0117b0.toASN1Primitive().getEncoded("DER")));
            } catch (IOException unused) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        } else {
            String string = ((InterfaceC0405d7) interfaceC0117b0).getString();
            if (string.length() > 0 && string.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(string);
        }
        int length = stringBuffer.length();
        int i = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i != length) {
            char cCharAt = stringBuffer.charAt(i);
            if (cCharAt != '\"' && cCharAt != '\\' && cCharAt != '+' && cCharAt != ',') {
                switch (cCharAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        i++;
                }
            }
            stringBuffer.insert(i, "\\");
            i += 2;
            length++;
        }
        if (stringBuffer.length() > 0) {
            for (int i2 = 0; stringBuffer.length() > i2 && stringBuffer.charAt(i2) == ' '; i2 += 2) {
                stringBuffer.insert(i2, "\\");
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= 0 && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }
}
