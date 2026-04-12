package p000;

import java.util.Hashtable;

/* renamed from: cd */
/* loaded from: classes2.dex */
public class C0168cd extends AbstractC0604i0 {
    public static final C0160c5 BUSINESS_CATEGORY;

    /* renamed from: C */
    public static final C0160c5 f46095C;

    /* renamed from: CN */
    public static final C0160c5 f46096CN;
    public static final C0160c5 COUNTRY_OF_CITIZENSHIP;
    public static final C0160c5 COUNTRY_OF_RESIDENCE;
    public static final C0160c5 DATE_OF_BIRTH;

    /* renamed from: DC */
    public static final C0160c5 f46097DC;
    public static final C0160c5 DESCRIPTION;
    public static final C0160c5 DMD_NAME;
    public static final C0160c5 DN_QUALIFIER;
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;

    /* renamed from: E */
    public static final C0160c5 f46098E;
    public static final C0160c5 EmailAddress;
    public static final C0160c5 GENDER;
    public static final C0160c5 GENERATION;
    public static final C0160c5 GIVENNAME;
    public static final C0160c5 INITIALS;
    public static final mh1 INSTANCE;

    /* renamed from: L */
    public static final C0160c5 f46099L;
    public static final C0160c5 NAME;
    public static final C0160c5 NAME_AT_BIRTH;

    /* renamed from: O */
    public static final C0160c5 f46100O;
    public static final C0160c5 ORGANIZATION_IDENTIFIER;

    /* renamed from: OU */
    public static final C0160c5 f46101OU;
    public static final C0160c5 PLACE_OF_BIRTH;
    public static final C0160c5 POSTAL_ADDRESS;
    public static final C0160c5 POSTAL_CODE;
    public static final C0160c5 PSEUDONYM;
    public static final C0160c5 ROLE;
    public static final C0160c5 SERIALNUMBER;

    /* renamed from: SN */
    public static final C0160c5 f46102SN;

    /* renamed from: ST */
    public static final C0160c5 f46103ST;
    public static final C0160c5 STREET;
    public static final C0160c5 SURNAME;

    /* renamed from: T */
    public static final C0160c5 f46104T;
    public static final C0160c5 TELEPHONE_NUMBER;
    public static final C0160c5 UID;
    public static final C0160c5 UNIQUE_IDENTIFIER;
    public static final C0160c5 UnstructuredAddress;
    public static final C0160c5 UnstructuredName;
    protected final Hashtable defaultSymbols = AbstractC0604i0.copyHashTable(DefaultSymbols);
    protected final Hashtable defaultLookUp = AbstractC0604i0.copyHashTable(DefaultLookUp);

    static {
        C0160c5 c0160c5M22a3 = AbstractC0003a2.m22a3("2.5.4.6");
        f46095C = c0160c5M22a3;
        C0160c5 c0160c5M22a32 = AbstractC0003a2.m22a3("2.5.4.10");
        f46100O = c0160c5M22a32;
        C0160c5 c0160c5M22a33 = AbstractC0003a2.m22a3("2.5.4.11");
        f46101OU = c0160c5M22a33;
        C0160c5 c0160c5M22a34 = AbstractC0003a2.m22a3("2.5.4.12");
        f46104T = c0160c5M22a34;
        C0160c5 c0160c5M22a35 = AbstractC0003a2.m22a3("2.5.4.3");
        f46096CN = c0160c5M22a35;
        f46102SN = AbstractC0003a2.m22a3("2.5.4.5");
        C0160c5 c0160c5M22a36 = AbstractC0003a2.m22a3("2.5.4.9");
        STREET = c0160c5M22a36;
        C0160c5 c0160c5M22a37 = AbstractC0003a2.m22a3("2.5.4.5");
        SERIALNUMBER = c0160c5M22a37;
        C0160c5 c0160c5M22a38 = AbstractC0003a2.m22a3("2.5.4.7");
        f46099L = c0160c5M22a38;
        C0160c5 c0160c5M22a39 = AbstractC0003a2.m22a3("2.5.4.8");
        f46103ST = c0160c5M22a39;
        C0160c5 c0160c5M22a310 = AbstractC0003a2.m22a3("2.5.4.4");
        SURNAME = c0160c5M22a310;
        C0160c5 c0160c5M22a311 = AbstractC0003a2.m22a3("2.5.4.42");
        GIVENNAME = c0160c5M22a311;
        C0160c5 c0160c5M22a312 = AbstractC0003a2.m22a3("2.5.4.43");
        INITIALS = c0160c5M22a312;
        C0160c5 c0160c5M22a313 = AbstractC0003a2.m22a3("2.5.4.44");
        GENERATION = c0160c5M22a313;
        C0160c5 c0160c5M22a314 = AbstractC0003a2.m22a3("2.5.4.45");
        UNIQUE_IDENTIFIER = c0160c5M22a314;
        C0160c5 c0160c5M22a315 = AbstractC0003a2.m22a3("2.5.4.13");
        DESCRIPTION = c0160c5M22a315;
        C0160c5 c0160c5M22a316 = AbstractC0003a2.m22a3("2.5.4.15");
        BUSINESS_CATEGORY = c0160c5M22a316;
        C0160c5 c0160c5M22a317 = AbstractC0003a2.m22a3("2.5.4.17");
        POSTAL_CODE = c0160c5M22a317;
        C0160c5 c0160c5M22a318 = AbstractC0003a2.m22a3("2.5.4.46");
        DN_QUALIFIER = c0160c5M22a318;
        C0160c5 c0160c5M22a319 = AbstractC0003a2.m22a3("2.5.4.65");
        PSEUDONYM = c0160c5M22a319;
        C0160c5 c0160c5M22a320 = AbstractC0003a2.m22a3("2.5.4.72");
        ROLE = c0160c5M22a320;
        C0160c5 c0160c5M22a321 = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = c0160c5M22a321;
        C0160c5 c0160c5M22a322 = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = c0160c5M22a322;
        C0160c5 c0160c5M22a323 = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.9.3");
        GENDER = c0160c5M22a323;
        C0160c5 c0160c5M22a324 = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = c0160c5M22a324;
        C0160c5 c0160c5M22a325 = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = c0160c5M22a325;
        C0160c5 c0160c5M22a326 = AbstractC0003a2.m22a3("1.3.36.8.3.14");
        NAME_AT_BIRTH = c0160c5M22a326;
        C0160c5 c0160c5M22a327 = AbstractC0003a2.m22a3("2.5.4.16");
        POSTAL_ADDRESS = c0160c5M22a327;
        DMD_NAME = AbstractC0003a2.m22a3("2.5.4.54");
        C0160c5 c0160c5 = wh1.id_at_telephoneNumber;
        TELEPHONE_NUMBER = c0160c5;
        C0160c5 c0160c52 = wh1.id_at_name;
        NAME = c0160c52;
        C0160c5 c0160c53 = wh1.id_at_organizationIdentifier;
        ORGANIZATION_IDENTIFIER = c0160c53;
        C0160c5 c0160c54 = ul0.pkcs_9_at_emailAddress;
        EmailAddress = c0160c54;
        C0160c5 c0160c55 = ul0.pkcs_9_at_unstructuredName;
        UnstructuredName = c0160c55;
        C0160c5 c0160c56 = ul0.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = c0160c56;
        f46098E = c0160c54;
        C0160c5 c0160c57 = new C0160c5("0.9.2342.19200300.100.1.25");
        f46097DC = c0160c57;
        C0160c5 c0160c58 = new C0160c5("0.9.2342.19200300.100.1.1");
        UID = c0160c58;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(c0160c5M22a3, "C");
        hashtable.put(c0160c5M22a32, "O");
        hashtable.put(c0160c5M22a34, "T");
        hashtable.put(c0160c5M22a33, "OU");
        hashtable.put(c0160c5M22a35, "CN");
        hashtable.put(c0160c5M22a38, "L");
        hashtable.put(c0160c5M22a39, "ST");
        hashtable.put(c0160c5M22a37, "SERIALNUMBER");
        hashtable.put(c0160c54, "E");
        hashtable.put(c0160c57, "DC");
        hashtable.put(c0160c58, "UID");
        hashtable.put(c0160c5M22a36, "STREET");
        hashtable.put(c0160c5M22a310, "SURNAME");
        hashtable.put(c0160c5M22a311, "GIVENNAME");
        hashtable.put(c0160c5M22a312, "INITIALS");
        hashtable.put(c0160c5M22a313, "GENERATION");
        hashtable.put(c0160c5M22a315, "DESCRIPTION");
        hashtable.put(c0160c5M22a320, "ROLE");
        hashtable.put(c0160c56, "unstructuredAddress");
        hashtable.put(c0160c55, "unstructuredName");
        hashtable.put(c0160c5M22a314, "UniqueIdentifier");
        hashtable.put(c0160c5M22a318, "DN");
        hashtable.put(c0160c5M22a319, "Pseudonym");
        hashtable.put(c0160c5M22a327, "PostalAddress");
        hashtable.put(c0160c5M22a326, "NameAtBirth");
        hashtable.put(c0160c5M22a324, "CountryOfCitizenship");
        hashtable.put(c0160c5M22a325, "CountryOfResidence");
        hashtable.put(c0160c5M22a323, "Gender");
        hashtable.put(c0160c5M22a322, "PlaceOfBirth");
        hashtable.put(c0160c5M22a321, "DateOfBirth");
        hashtable.put(c0160c5M22a317, "PostalCode");
        hashtable.put(c0160c5M22a316, "BusinessCategory");
        hashtable.put(c0160c5, "TelephoneNumber");
        hashtable.put(c0160c52, "Name");
        hashtable.put(c0160c53, "organizationIdentifier");
        hashtable2.put("c", c0160c5M22a3);
        hashtable2.put("o", c0160c5M22a32);
        hashtable2.put("t", c0160c5M22a34);
        hashtable2.put("ou", c0160c5M22a33);
        hashtable2.put("cn", c0160c5M22a35);
        hashtable2.put("l", c0160c5M22a38);
        hashtable2.put("st", c0160c5M22a39);
        hashtable2.put("sn", c0160c5M22a310);
        hashtable2.put("serialnumber", c0160c5M22a37);
        hashtable2.put("street", c0160c5M22a36);
        hashtable2.put("emailaddress", c0160c54);
        hashtable2.put("dc", c0160c57);
        hashtable2.put("e", c0160c54);
        hashtable2.put("uid", c0160c58);
        hashtable2.put("surname", c0160c5M22a310);
        hashtable2.put("givenname", c0160c5M22a311);
        hashtable2.put("initials", c0160c5M22a312);
        hashtable2.put("generation", c0160c5M22a313);
        hashtable2.put("description", c0160c5M22a315);
        hashtable2.put("role", c0160c5M22a320);
        hashtable2.put("unstructuredaddress", c0160c56);
        hashtable2.put("unstructuredname", c0160c55);
        hashtable2.put("uniqueidentifier", c0160c5M22a314);
        hashtable2.put("dn", c0160c5M22a318);
        hashtable2.put("pseudonym", c0160c5M22a319);
        hashtable2.put("postaladdress", c0160c5M22a327);
        hashtable2.put("nameatbirth", c0160c5M22a326);
        hashtable2.put("countryofcitizenship", c0160c5M22a324);
        hashtable2.put("countryofresidence", c0160c5M22a325);
        hashtable2.put("gender", c0160c5M22a323);
        hashtable2.put("placeofbirth", c0160c5M22a322);
        hashtable2.put("dateofbirth", c0160c5M22a321);
        hashtable2.put("postalcode", c0160c5M22a317);
        hashtable2.put("businesscategory", c0160c5M22a316);
        hashtable2.put("telephonenumber", c0160c5);
        hashtable2.put("name", c0160c52);
        hashtable2.put("organizationidentifier", c0160c53);
        INSTANCE = new C0168cd();
    }

    @Override // p000.AbstractC0604i0, p000.mh1
    public C0160c5 attrNameToOID(String str) {
        return o40.decodeAttrName(str, this.defaultLookUp);
    }

    @Override // p000.AbstractC0604i0
    public InterfaceC0117b0 encodeStringValue(C0160c5 c0160c5, String str) {
        return (c0160c5.equals((AbstractC0164c9) EmailAddress) || c0160c5.equals((AbstractC0164c9) f46097DC)) ? new C1045ov(str) : c0160c5.equals((AbstractC0164c9) DATE_OF_BIRTH) ? new C0123b6(str) : (c0160c5.equals((AbstractC0164c9) f46095C) || c0160c5.equals((AbstractC0164c9) f46102SN) || c0160c5.equals((AbstractC0164c9) DN_QUALIFIER) || c0160c5.equals((AbstractC0164c9) TELEPHONE_NUMBER)) ? new C1063pb(str) : super.encodeStringValue(c0160c5, str);
    }

    @Override // p000.AbstractC0604i0, p000.mh1
    public np0[] fromString(String str) {
        return o40.rDNsFromString(str, this);
    }

    @Override // p000.AbstractC0604i0, p000.mh1
    public String[] oidToAttrNames(C0160c5 c0160c5) {
        return o40.findAttrNamesForOID(c0160c5, this.defaultLookUp);
    }

    @Override // p000.AbstractC0604i0, p000.mh1
    public String oidToDisplayName(C0160c5 c0160c5) {
        return (String) DefaultSymbols.get(c0160c5);
    }

    @Override // p000.AbstractC0604i0, p000.mh1
    public String toString(kh1 kh1Var) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (np0 np0Var : kh1Var.getRDNs()) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            o40.appendRDN(stringBuffer, np0Var, this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
