package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class th1 extends AbstractC0158c3 {
    public static final C0160c5 BUSINESS_CATEGORY;

    /* renamed from: C */
    public static final C0160c5 f60223C;

    /* renamed from: CN */
    public static final C0160c5 f60224CN;
    public static final C0160c5 COUNTRY_OF_CITIZENSHIP;
    public static final C0160c5 COUNTRY_OF_RESIDENCE;
    public static final C0160c5 DATE_OF_BIRTH;

    /* renamed from: DC */
    public static final C0160c5 f60225DC;
    public static final C0160c5 DMD_NAME;
    public static final C0160c5 DN_QUALIFIER;
    public static final Hashtable DefaultLookUp;
    public static boolean DefaultReverse;
    public static final Hashtable DefaultSymbols;

    /* renamed from: E */
    public static final C0160c5 f60226E;
    public static final C0160c5 EmailAddress;
    private static final Boolean FALSE;
    public static final C0160c5 GENDER;
    public static final C0160c5 GENERATION;
    public static final C0160c5 GIVENNAME;
    public static final C0160c5 INITIALS;

    /* renamed from: L */
    public static final C0160c5 f60227L;
    public static final C0160c5 NAME;
    public static final C0160c5 NAME_AT_BIRTH;

    /* renamed from: O */
    public static final C0160c5 f60228O;
    public static final Hashtable OIDLookUp;

    /* renamed from: OU */
    public static final C0160c5 f60229OU;
    public static final C0160c5 PLACE_OF_BIRTH;
    public static final C0160c5 POSTAL_ADDRESS;
    public static final C0160c5 POSTAL_CODE;
    public static final C0160c5 PSEUDONYM;
    public static final Hashtable RFC1779Symbols;
    public static final Hashtable RFC2253Symbols;
    public static final C0160c5 SERIALNUMBER;

    /* renamed from: SN */
    public static final C0160c5 f60230SN;

    /* renamed from: ST */
    public static final C0160c5 f60231ST;
    public static final C0160c5 STREET;
    public static final C0160c5 SURNAME;
    public static final Hashtable SymbolLookUp;

    /* renamed from: T */
    public static final C0160c5 f60232T;
    public static final C0160c5 TELEPHONE_NUMBER;
    private static final Boolean TRUE;
    public static final C0160c5 UID;
    public static final C0160c5 UNIQUE_IDENTIFIER;
    public static final C0160c5 UnstructuredAddress;
    public static final C0160c5 UnstructuredName;
    private Vector added;
    private uh1 converter;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private Vector ordering;
    private AbstractC0400d2 seq;
    private Vector values;

    static {
        C0160c5 c0160c5 = new C0160c5("2.5.4.6");
        f60223C = c0160c5;
        C0160c5 c0160c52 = new C0160c5("2.5.4.10");
        f60228O = c0160c52;
        C0160c5 c0160c53 = new C0160c5("2.5.4.11");
        f60229OU = c0160c53;
        C0160c5 c0160c54 = new C0160c5("2.5.4.12");
        f60232T = c0160c54;
        C0160c5 c0160c55 = new C0160c5("2.5.4.3");
        f60224CN = c0160c55;
        C0160c5 c0160c56 = new C0160c5("2.5.4.5");
        f60230SN = c0160c56;
        C0160c5 c0160c57 = new C0160c5("2.5.4.9");
        STREET = c0160c57;
        SERIALNUMBER = c0160c56;
        C0160c5 c0160c58 = new C0160c5("2.5.4.7");
        f60227L = c0160c58;
        C0160c5 c0160c59 = new C0160c5("2.5.4.8");
        f60231ST = c0160c59;
        C0160c5 c0160c510 = new C0160c5("2.5.4.4");
        SURNAME = c0160c510;
        C0160c5 c0160c511 = new C0160c5("2.5.4.42");
        GIVENNAME = c0160c511;
        C0160c5 c0160c512 = new C0160c5("2.5.4.43");
        INITIALS = c0160c512;
        C0160c5 c0160c513 = new C0160c5("2.5.4.44");
        GENERATION = c0160c513;
        C0160c5 c0160c514 = new C0160c5("2.5.4.45");
        UNIQUE_IDENTIFIER = c0160c514;
        C0160c5 c0160c515 = new C0160c5("2.5.4.15");
        BUSINESS_CATEGORY = c0160c515;
        C0160c5 c0160c516 = new C0160c5("2.5.4.17");
        POSTAL_CODE = c0160c516;
        C0160c5 c0160c517 = new C0160c5("2.5.4.46");
        DN_QUALIFIER = c0160c517;
        C0160c5 c0160c518 = new C0160c5("2.5.4.65");
        PSEUDONYM = c0160c518;
        C0160c5 c0160c519 = new C0160c5("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = c0160c519;
        C0160c5 c0160c520 = new C0160c5("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = c0160c520;
        C0160c5 c0160c521 = new C0160c5("1.3.6.1.5.5.7.9.3");
        GENDER = c0160c521;
        C0160c5 c0160c522 = new C0160c5("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = c0160c522;
        C0160c5 c0160c523 = new C0160c5("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = c0160c523;
        C0160c5 c0160c524 = new C0160c5("1.3.36.8.3.14");
        NAME_AT_BIRTH = c0160c524;
        C0160c5 c0160c525 = new C0160c5("2.5.4.16");
        POSTAL_ADDRESS = c0160c525;
        DMD_NAME = new C0160c5("2.5.4.54");
        C0160c5 c0160c526 = wh1.id_at_telephoneNumber;
        TELEPHONE_NUMBER = c0160c526;
        C0160c5 c0160c527 = wh1.id_at_name;
        NAME = c0160c527;
        C0160c5 c0160c528 = ul0.pkcs_9_at_emailAddress;
        EmailAddress = c0160c528;
        C0160c5 c0160c529 = ul0.pkcs_9_at_unstructuredName;
        UnstructuredName = c0160c529;
        C0160c5 c0160c530 = ul0.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = c0160c530;
        f60226E = c0160c528;
        C0160c5 c0160c531 = new C0160c5("0.9.2342.19200300.100.1.25");
        f60225DC = c0160c531;
        C0160c5 c0160c532 = new C0160c5("0.9.2342.19200300.100.1.1");
        UID = c0160c532;
        DefaultReverse = false;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        RFC2253Symbols = hashtable2;
        Hashtable hashtable3 = new Hashtable();
        RFC1779Symbols = hashtable3;
        Hashtable hashtable4 = new Hashtable();
        DefaultLookUp = hashtable4;
        OIDLookUp = hashtable;
        SymbolLookUp = hashtable4;
        TRUE = new Boolean(true);
        FALSE = new Boolean(false);
        hashtable.put(c0160c5, "C");
        hashtable.put(c0160c52, "O");
        hashtable.put(c0160c54, "T");
        hashtable.put(c0160c53, "OU");
        hashtable.put(c0160c55, "CN");
        hashtable.put(c0160c58, "L");
        hashtable.put(c0160c59, "ST");
        hashtable.put(c0160c56, "SERIALNUMBER");
        hashtable.put(c0160c528, "E");
        hashtable.put(c0160c531, "DC");
        hashtable.put(c0160c532, "UID");
        hashtable.put(c0160c57, "STREET");
        hashtable.put(c0160c510, "SURNAME");
        hashtable.put(c0160c511, "GIVENNAME");
        hashtable.put(c0160c512, "INITIALS");
        hashtable.put(c0160c513, "GENERATION");
        hashtable.put(c0160c530, "unstructuredAddress");
        hashtable.put(c0160c529, "unstructuredName");
        hashtable.put(c0160c514, "UniqueIdentifier");
        hashtable.put(c0160c517, "DN");
        hashtable.put(c0160c518, "Pseudonym");
        hashtable.put(c0160c525, "PostalAddress");
        hashtable.put(c0160c524, "NameAtBirth");
        hashtable.put(c0160c522, "CountryOfCitizenship");
        hashtable.put(c0160c523, "CountryOfResidence");
        hashtable.put(c0160c521, "Gender");
        hashtable.put(c0160c520, "PlaceOfBirth");
        hashtable.put(c0160c519, "DateOfBirth");
        hashtable.put(c0160c516, "PostalCode");
        hashtable.put(c0160c515, "BusinessCategory");
        hashtable.put(c0160c526, "TelephoneNumber");
        hashtable.put(c0160c527, "Name");
        hashtable2.put(c0160c5, "C");
        hashtable2.put(c0160c52, "O");
        hashtable2.put(c0160c53, "OU");
        hashtable2.put(c0160c55, "CN");
        hashtable2.put(c0160c58, "L");
        hashtable2.put(c0160c59, "ST");
        hashtable2.put(c0160c57, "STREET");
        hashtable2.put(c0160c531, "DC");
        hashtable2.put(c0160c532, "UID");
        hashtable3.put(c0160c5, "C");
        hashtable3.put(c0160c52, "O");
        hashtable3.put(c0160c53, "OU");
        hashtable3.put(c0160c55, "CN");
        hashtable3.put(c0160c58, "L");
        hashtable3.put(c0160c59, "ST");
        hashtable3.put(c0160c57, "STREET");
        hashtable4.put("c", c0160c5);
        hashtable4.put("o", c0160c52);
        hashtable4.put("t", c0160c54);
        hashtable4.put("ou", c0160c53);
        hashtable4.put("cn", c0160c55);
        hashtable4.put("l", c0160c58);
        hashtable4.put("st", c0160c59);
        hashtable4.put("sn", c0160c56);
        hashtable4.put("serialnumber", c0160c56);
        hashtable4.put("street", c0160c57);
        hashtable4.put("emailaddress", c0160c528);
        hashtable4.put("dc", c0160c531);
        hashtable4.put("e", c0160c528);
        hashtable4.put("uid", c0160c532);
        hashtable4.put("surname", c0160c510);
        hashtable4.put("givenname", c0160c511);
        hashtable4.put("initials", c0160c512);
        hashtable4.put("generation", c0160c513);
        hashtable4.put("unstructuredaddress", c0160c530);
        hashtable4.put("unstructuredname", c0160c529);
        hashtable4.put("uniqueidentifier", c0160c514);
        hashtable4.put("dn", c0160c517);
        hashtable4.put("pseudonym", c0160c518);
        hashtable4.put("postaladdress", c0160c525);
        hashtable4.put("nameofbirth", c0160c524);
        hashtable4.put("countryofcitizenship", c0160c522);
        hashtable4.put("countryofresidence", c0160c523);
        hashtable4.put("gender", c0160c521);
        hashtable4.put("placeofbirth", c0160c520);
        hashtable4.put("dateofbirth", c0160c519);
        hashtable4.put("postalcode", c0160c516);
        hashtable4.put("businesscategory", c0160c515);
        hashtable4.put("telephonenumber", c0160c526);
        hashtable4.put("name", c0160c527);
    }

    public th1() {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
    }

    private void addEntry(Hashtable hashtable, String str, Boolean bool) {
        vh1 vh1Var = new vh1(str, '=');
        String strNextToken = vh1Var.nextToken();
        if (!vh1Var.hasMoreTokens()) {
            throw new IllegalArgumentException("badly formatted directory string");
        }
        String strNextToken2 = vh1Var.nextToken();
        this.ordering.addElement(decodeOID(strNextToken, hashtable));
        this.values.addElement(unescape(strNextToken2));
        this.added.addElement(bool);
    }

    private void appendValue(StringBuffer stringBuffer, Hashtable hashtable, C0160c5 c0160c5, String str) {
        String id = (String) hashtable.get(c0160c5);
        if (id == null) {
            id = c0160c5.getId();
        }
        stringBuffer.append(id);
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (length < length2 && stringBuffer.charAt(length) == ' ') {
            stringBuffer.insert(length, "\\");
            length += 2;
            length2++;
        }
        while (true) {
            length2--;
            if (length2 <= length || stringBuffer.charAt(length2) != ' ') {
                break;
            } else {
                stringBuffer.insert(length2, '\\');
            }
        }
        while (length <= length2) {
            char cCharAt = stringBuffer.charAt(length);
            if (cCharAt != '\"' && cCharAt != '\\' && cCharAt != '+' && cCharAt != ',') {
                switch (cCharAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        length++;
                }
            }
            stringBuffer.insert(length, "\\");
            length += 2;
            length2++;
        }
    }

    private String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return new String(cArr);
    }

    private String canonicalize(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() <= 0 || lowerCase.charAt(0) != '#') {
            return lowerCase;
        }
        InterfaceC1394wy interfaceC1394wyDecodeObject = decodeObject(lowerCase);
        return interfaceC1394wyDecodeObject instanceof InterfaceC0405d7 ? Strings.toLowerCase(((InterfaceC0405d7) interfaceC1394wyDecodeObject).getString().trim()) : lowerCase;
    }

    private C0160c5 decodeOID(String str, Hashtable hashtable) {
        String strTrim = str.trim();
        if (Strings.toUpperCase(strTrim).startsWith("OID.")) {
            return new C0160c5(strTrim.substring(4));
        }
        if (strTrim.charAt(0) >= '0' && strTrim.charAt(0) <= '9') {
            return new C0160c5(strTrim);
        }
        C0160c5 c0160c5 = (C0160c5) hashtable.get(Strings.toLowerCase(strTrim));
        if (c0160c5 != null) {
            return c0160c5;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m33b4("Unknown object id - ", strTrim, " - passed to distinguished name"));
    }

    private AbstractC0164c9 decodeObject(String str) {
        try {
            return AbstractC0164c9.fromByteArray(c40.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    private boolean equivalentStrings(String str, String str2) {
        String strCanonicalize = canonicalize(str);
        String strCanonicalize2 = canonicalize(str2);
        return strCanonicalize.equals(strCanonicalize2) || stripInternalSpaces(strCanonicalize).equals(stripInternalSpaces(strCanonicalize2));
    }

    public static th1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    private String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char cCharAt = str.charAt(0);
            stringBuffer.append(cCharAt);
            int i = 1;
            while (i < str.length()) {
                char cCharAt2 = str.charAt(i);
                if (cCharAt != ' ' || cCharAt2 != ' ') {
                    stringBuffer.append(cCharAt2);
                }
                i++;
                cCharAt = cCharAt2;
            }
        }
        return stringBuffer.toString();
    }

    private String unescape(String str) {
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
        while (i != charArray.length) {
            char c = charArray[i];
            if (c != ' ') {
                z3 = true;
            }
            if (c == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                z = false;
                i++;
            } else {
                if (c == '\\' && !z && !z2) {
                    length = stringBuffer.length();
                    z = true;
                } else if (c == ' ' && !z && !z3) {
                }
                i++;
            }
            stringBuffer.append(c);
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

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0085, code lost:
    
        r3[r9] = true;
        r4 = r4 + r6;
     */
    @Override // p000.AbstractC0158c3
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean equals(Object obj) {
        int i;
        int i2;
        int i3;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof th1) && !(obj instanceof AbstractC0400d2)) {
            return false;
        }
        if (toASN1Primitive().equals(((InterfaceC0117b0) obj).toASN1Primitive())) {
            return true;
        }
        try {
            th1 th1Var = getInstance(obj);
            int size = this.ordering.size();
            if (size != th1Var.ordering.size()) {
                return false;
            }
            boolean[] zArr = new boolean[size];
            if (this.ordering.elementAt(0).equals(th1Var.ordering.elementAt(0))) {
                i3 = 1;
                i2 = size;
                i = 0;
            } else {
                i = size - 1;
                i2 = -1;
                i3 = -1;
            }
            while (i != i2) {
                C0160c5 c0160c5 = (C0160c5) this.ordering.elementAt(i);
                String str = (String) this.values.elementAt(i);
                int i4 = 0;
                while (i4 < size) {
                    if (!zArr[i4] && c0160c5.equals((AbstractC0164c9) th1Var.ordering.elementAt(i4)) && equivalentStrings(str, (String) th1Var.values.elementAt(i4))) {
                        break;
                    }
                    i4++;
                }
                return false;
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            vector.addElement(this.ordering.elementAt(i));
        }
        return vector;
    }

    public Vector getValues() {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            vector.addElement(this.values.elementAt(i));
        }
        return vector;
    }

    @Override // p000.AbstractC0158c3
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i = 0; i != this.ordering.size(); i++) {
            String strStripInternalSpaces = stripInternalSpaces(canonicalize((String) this.values.elementAt(i)));
            int iHashCode = this.hashCodeValue ^ this.ordering.elementAt(i).hashCode();
            this.hashCodeValue = iHashCode;
            this.hashCodeValue = strStripInternalSpaces.hashCode() ^ iHashCode;
        }
        return this.hashCodeValue;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C1064pc c1064pc;
        if (this.seq == null) {
            C0118b1 c0118b1 = new C0118b1();
            C0118b1 c0118b12 = new C0118b1();
            C0160c5 c0160c5 = null;
            int i = 0;
            while (i != this.ordering.size()) {
                C0118b1 c0118b13 = new C0118b1(2);
                C0160c5 c0160c52 = (C0160c5) this.ordering.elementAt(i);
                c0118b13.add(c0160c52);
                c0118b13.add(this.converter.getConvertedValue(c0160c52, (String) this.values.elementAt(i)));
                if (c0160c5 == null || ((Boolean) this.added.elementAt(i)).booleanValue()) {
                    c1064pc = new C1064pc(c0118b13);
                } else {
                    c0118b1.add(new C1065pd(c0118b12));
                    c0118b12 = new C0118b1();
                    c1064pc = new C1064pc(c0118b13);
                }
                c0118b12.add(c1064pc);
                i++;
                c0160c5 = c0160c52;
            }
            c0118b1.add(new C1065pd(c0118b12));
            this.seq = new C1064pc(c0118b1);
        }
        return this.seq;
    }

    public String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }

    public th1(AbstractC0400d2 abstractC0400d2) {
        Vector vector;
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.seq = abstractC0400d2;
        Enumeration objects = abstractC0400d2.getObjects();
        while (objects.hasMoreElements()) {
            AbstractC0402d4 abstractC0402d4 = AbstractC0402d4.getInstance(((InterfaceC0117b0) objects.nextElement()).toASN1Primitive());
            int i = 0;
            while (i < abstractC0402d4.size()) {
                AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(abstractC0402d4.getObjectAt(i).toASN1Primitive());
                if (abstractC0400d22.size() != 2) {
                    throw new IllegalArgumentException("badly sized pair");
                }
                this.ordering.addElement(C0160c5.getInstance(abstractC0400d22.getObjectAt(0)));
                InterfaceC0117b0 objectAt = abstractC0400d22.getObjectAt(1);
                if (!(objectAt instanceof InterfaceC0405d7) || (objectAt instanceof AbstractC0444e5)) {
                    try {
                        this.values.addElement("#" + bytesToString(c40.encode(objectAt.toASN1Primitive().getEncoded("DER"))));
                    } catch (IOException unused) {
                        throw new IllegalArgumentException("cannot encode value");
                    }
                } else {
                    String string = ((InterfaceC0405d7) objectAt).getString();
                    if (string.length() <= 0 || string.charAt(0) != '#') {
                        vector = this.values;
                    } else {
                        vector = this.values;
                        string = "\\".concat(string);
                    }
                    vector.addElement(string);
                }
                this.added.addElement(i != 0 ? TRUE : FALSE);
                i++;
            }
        }
    }

    public static th1 getInstance(Object obj) {
        if (obj instanceof th1) {
            return (th1) obj;
        }
        if (obj instanceof kh1) {
            return new th1(AbstractC0400d2.getInstance(((kh1) obj).toASN1Primitive()));
        }
        if (obj != null) {
            return new th1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public boolean equals(Object obj, boolean z) {
        if (!z) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof th1) && !(obj instanceof AbstractC0400d2)) {
            return false;
        }
        if (toASN1Primitive().equals(((InterfaceC0117b0) obj).toASN1Primitive())) {
            return true;
        }
        try {
            th1 th1Var = getInstance(obj);
            int size = this.ordering.size();
            if (size != th1Var.ordering.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!((C0160c5) this.ordering.elementAt(i)).equals((AbstractC0164c9) th1Var.ordering.elementAt(i)) || !equivalentStrings((String) this.values.elementAt(i), (String) th1Var.values.elementAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public Vector getValues(C0160c5 c0160c5) {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            if (this.ordering.elementAt(i).equals(c0160c5)) {
                String strSubstring = (String) this.values.elementAt(i);
                if (strSubstring.length() > 2 && strSubstring.charAt(0) == '\\' && strSubstring.charAt(1) == '#') {
                    strSubstring = strSubstring.substring(1);
                }
                vector.addElement(strSubstring);
            }
        }
        return vector;
    }

    public String toString(boolean z, Hashtable hashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        Vector vector = new Vector();
        StringBuffer stringBuffer2 = null;
        for (int i = 0; i < this.ordering.size(); i++) {
            if (((Boolean) this.added.elementAt(i)).booleanValue()) {
                stringBuffer2.append('+');
                appendValue(stringBuffer2, hashtable, (C0160c5) this.ordering.elementAt(i), (String) this.values.elementAt(i));
            } else {
                stringBuffer2 = new StringBuffer();
                appendValue(stringBuffer2, hashtable, (C0160c5) this.ordering.elementAt(i), (String) this.values.elementAt(i));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z2 = true;
        if (z) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i2 = 0; i2 < vector.size(); i2++) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i2).toString());
            }
        }
        return stringBuffer.toString();
    }

    public th1(String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public th1(String str, uh1 uh1Var) {
        this(DefaultReverse, DefaultLookUp, str, uh1Var);
    }

    public th1(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    public th1(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, new ph1());
    }

    public th1(Vector vector, Hashtable hashtable, uh1 uh1Var) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = uh1Var;
        if (vector != null) {
            for (int i = 0; i != vector.size(); i++) {
                this.ordering.addElement(vector.elementAt(i));
                this.added.addElement(FALSE);
            }
        } else {
            Enumeration enumerationKeys = hashtable.keys();
            while (enumerationKeys.hasMoreElements()) {
                this.ordering.addElement(enumerationKeys.nextElement());
                this.added.addElement(FALSE);
            }
        }
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            C0160c5 c0160c5 = (C0160c5) this.ordering.elementAt(i2);
            if (hashtable.get(c0160c5) == null) {
                throw new IllegalArgumentException("No attribute for object id - " + c0160c5.getId() + " - passed to distinguished name");
            }
            this.values.addElement(hashtable.get(c0160c5));
        }
    }

    public th1(Vector vector, Vector vector2) {
        this(vector, vector2, new ph1());
    }

    public th1(Vector vector, Vector vector2, uh1 uh1Var) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = uh1Var;
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("oids vector must be same length as values.");
        }
        for (int i = 0; i < vector.size(); i++) {
            this.ordering.addElement(vector.elementAt(i));
            this.values.addElement(vector2.elementAt(i));
            this.added.addElement(FALSE);
        }
    }

    public th1(boolean z, String str) {
        this(z, DefaultLookUp, str);
    }

    public th1(boolean z, String str, uh1 uh1Var) {
        this(z, DefaultLookUp, str, uh1Var);
    }

    public th1(boolean z, Hashtable hashtable, String str) {
        this(z, hashtable, str, new ph1());
    }

    public th1(boolean z, Hashtable hashtable, String str, uh1 uh1Var) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = uh1Var;
        vh1 vh1Var = new vh1(str);
        while (vh1Var.hasMoreTokens()) {
            String strNextToken = vh1Var.nextToken();
            if (strNextToken.indexOf(43) > 0) {
                vh1 vh1Var2 = new vh1(strNextToken, '+');
                String strNextToken2 = vh1Var2.nextToken();
                Boolean bool = FALSE;
                while (true) {
                    addEntry(hashtable, strNextToken2, bool);
                    if (vh1Var2.hasMoreTokens()) {
                        strNextToken2 = vh1Var2.nextToken();
                        bool = TRUE;
                    }
                }
            } else {
                addEntry(hashtable, strNextToken, FALSE);
            }
        }
        if (z) {
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            int i = 1;
            for (int i2 = 0; i2 < this.ordering.size(); i2++) {
                if (((Boolean) this.added.elementAt(i2)).booleanValue()) {
                    vector.insertElementAt(this.ordering.elementAt(i2), i);
                    vector2.insertElementAt(this.values.elementAt(i2), i);
                    vector3.insertElementAt(this.added.elementAt(i2), i);
                    i++;
                } else {
                    vector.insertElementAt(this.ordering.elementAt(i2), 0);
                    vector2.insertElementAt(this.values.elementAt(i2), 0);
                    vector3.insertElementAt(this.added.elementAt(i2), 0);
                    i = 1;
                }
            }
            this.ordering = vector;
            this.values = vector2;
            this.added = vector3;
        }
    }
}
