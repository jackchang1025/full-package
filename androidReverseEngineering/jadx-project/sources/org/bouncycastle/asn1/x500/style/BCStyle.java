package org.bouncycastle.asn1.x500.style;

import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class BCStyle extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;

    /* renamed from: C */
    public static final ASN1ObjectIdentifier f1088C;
    public static final ASN1ObjectIdentifier CN;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
    public static final ASN1ObjectIdentifier DC;
    public static final ASN1ObjectIdentifier DESCRIPTION;
    public static final ASN1ObjectIdentifier DMD_NAME;
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;

    /* renamed from: E */
    public static final ASN1ObjectIdentifier f1089E;
    public static final ASN1ObjectIdentifier EmailAddress;
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;
    public static final X500NameStyle INSTANCE;

    /* renamed from: L */
    public static final ASN1ObjectIdentifier f1090L;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;

    /* renamed from: O */
    public static final ASN1ObjectIdentifier f1091O;
    public static final ASN1ObjectIdentifier ORGANIZATION_IDENTIFIER;
    public static final ASN1ObjectIdentifier OU;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final ASN1ObjectIdentifier ROLE;
    public static final ASN1ObjectIdentifier SERIALNUMBER;
    public static final ASN1ObjectIdentifier SN;
    public static final ASN1ObjectIdentifier ST;
    public static final ASN1ObjectIdentifier STREET;
    public static final ASN1ObjectIdentifier SURNAME;

    /* renamed from: T */
    public static final ASN1ObjectIdentifier f1092T;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    public static final ASN1ObjectIdentifier UID;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    protected final Hashtable defaultSymbols = AbstractX500NameStyle.copyHashTable(DefaultSymbols);
    protected final Hashtable defaultLookUp = AbstractX500NameStyle.copyHashTable(DefaultLookUp);

    static {
        ASN1ObjectIdentifier m1025s = AbstractC0413b.m1025s("2.5.4.6");
        f1088C = m1025s;
        ASN1ObjectIdentifier m1025s2 = AbstractC0413b.m1025s("2.5.4.10");
        f1091O = m1025s2;
        ASN1ObjectIdentifier m1025s3 = AbstractC0413b.m1025s("2.5.4.11");
        OU = m1025s3;
        ASN1ObjectIdentifier m1025s4 = AbstractC0413b.m1025s("2.5.4.12");
        f1092T = m1025s4;
        ASN1ObjectIdentifier m1025s5 = AbstractC0413b.m1025s("2.5.4.3");
        CN = m1025s5;
        SN = AbstractC0413b.m1025s("2.5.4.5");
        ASN1ObjectIdentifier m1025s6 = AbstractC0413b.m1025s("2.5.4.9");
        STREET = m1025s6;
        ASN1ObjectIdentifier m1025s7 = AbstractC0413b.m1025s("2.5.4.5");
        SERIALNUMBER = m1025s7;
        ASN1ObjectIdentifier m1025s8 = AbstractC0413b.m1025s("2.5.4.7");
        f1090L = m1025s8;
        ASN1ObjectIdentifier m1025s9 = AbstractC0413b.m1025s("2.5.4.8");
        ST = m1025s9;
        ASN1ObjectIdentifier m1025s10 = AbstractC0413b.m1025s("2.5.4.4");
        SURNAME = m1025s10;
        ASN1ObjectIdentifier m1025s11 = AbstractC0413b.m1025s("2.5.4.42");
        GIVENNAME = m1025s11;
        ASN1ObjectIdentifier m1025s12 = AbstractC0413b.m1025s("2.5.4.43");
        INITIALS = m1025s12;
        ASN1ObjectIdentifier m1025s13 = AbstractC0413b.m1025s("2.5.4.44");
        GENERATION = m1025s13;
        ASN1ObjectIdentifier m1025s14 = AbstractC0413b.m1025s("2.5.4.45");
        UNIQUE_IDENTIFIER = m1025s14;
        ASN1ObjectIdentifier m1025s15 = AbstractC0413b.m1025s("2.5.4.13");
        DESCRIPTION = m1025s15;
        ASN1ObjectIdentifier m1025s16 = AbstractC0413b.m1025s("2.5.4.15");
        BUSINESS_CATEGORY = m1025s16;
        ASN1ObjectIdentifier m1025s17 = AbstractC0413b.m1025s("2.5.4.17");
        POSTAL_CODE = m1025s17;
        ASN1ObjectIdentifier m1025s18 = AbstractC0413b.m1025s("2.5.4.46");
        DN_QUALIFIER = m1025s18;
        ASN1ObjectIdentifier m1025s19 = AbstractC0413b.m1025s("2.5.4.65");
        PSEUDONYM = m1025s19;
        ASN1ObjectIdentifier m1025s20 = AbstractC0413b.m1025s("2.5.4.72");
        ROLE = m1025s20;
        ASN1ObjectIdentifier m1025s21 = AbstractC0413b.m1025s("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = m1025s21;
        ASN1ObjectIdentifier m1025s22 = AbstractC0413b.m1025s("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = m1025s22;
        ASN1ObjectIdentifier m1025s23 = AbstractC0413b.m1025s("1.3.6.1.5.5.7.9.3");
        GENDER = m1025s23;
        ASN1ObjectIdentifier m1025s24 = AbstractC0413b.m1025s("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = m1025s24;
        ASN1ObjectIdentifier m1025s25 = AbstractC0413b.m1025s("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = m1025s25;
        ASN1ObjectIdentifier m1025s26 = AbstractC0413b.m1025s("1.3.36.8.3.14");
        NAME_AT_BIRTH = m1025s26;
        ASN1ObjectIdentifier m1025s27 = AbstractC0413b.m1025s("2.5.4.16");
        POSTAL_ADDRESS = m1025s27;
        DMD_NAME = AbstractC0413b.m1025s("2.5.4.54");
        ASN1ObjectIdentifier aSN1ObjectIdentifier = X509ObjectIdentifiers.id_at_telephoneNumber;
        TELEPHONE_NUMBER = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = X509ObjectIdentifiers.id_at_name;
        NAME = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = X509ObjectIdentifiers.id_at_organizationIdentifier;
        ORGANIZATION_IDENTIFIER = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        EmailAddress = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredName = aSN1ObjectIdentifier5;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = aSN1ObjectIdentifier6;
        f1089E = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        DC = aSN1ObjectIdentifier7;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        UID = aSN1ObjectIdentifier8;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(m1025s, "C");
        hashtable.put(m1025s2, "O");
        hashtable.put(m1025s4, "T");
        hashtable.put(m1025s3, "OU");
        hashtable.put(m1025s5, "CN");
        hashtable.put(m1025s8, "L");
        hashtable.put(m1025s9, "ST");
        hashtable.put(m1025s7, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier4, "E");
        hashtable.put(aSN1ObjectIdentifier7, "DC");
        hashtable.put(aSN1ObjectIdentifier8, "UID");
        hashtable.put(m1025s6, "STREET");
        hashtable.put(m1025s10, "SURNAME");
        hashtable.put(m1025s11, "GIVENNAME");
        hashtable.put(m1025s12, "INITIALS");
        hashtable.put(m1025s13, "GENERATION");
        hashtable.put(m1025s15, "DESCRIPTION");
        hashtable.put(m1025s20, "ROLE");
        hashtable.put(aSN1ObjectIdentifier6, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier5, "unstructuredName");
        hashtable.put(m1025s14, "UniqueIdentifier");
        hashtable.put(m1025s18, "DN");
        hashtable.put(m1025s19, "Pseudonym");
        hashtable.put(m1025s27, "PostalAddress");
        hashtable.put(m1025s26, "NameAtBirth");
        hashtable.put(m1025s24, "CountryOfCitizenship");
        hashtable.put(m1025s25, "CountryOfResidence");
        hashtable.put(m1025s23, "Gender");
        hashtable.put(m1025s22, "PlaceOfBirth");
        hashtable.put(m1025s21, "DateOfBirth");
        hashtable.put(m1025s17, "PostalCode");
        hashtable.put(m1025s16, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier2, "Name");
        hashtable.put(aSN1ObjectIdentifier3, "organizationIdentifier");
        hashtable2.put("c", m1025s);
        hashtable2.put("o", m1025s2);
        hashtable2.put("t", m1025s4);
        hashtable2.put("ou", m1025s3);
        hashtable2.put("cn", m1025s5);
        hashtable2.put("l", m1025s8);
        hashtable2.put("st", m1025s9);
        hashtable2.put("sn", m1025s10);
        hashtable2.put("serialnumber", m1025s7);
        hashtable2.put("street", m1025s6);
        hashtable2.put("emailaddress", aSN1ObjectIdentifier4);
        hashtable2.put("dc", aSN1ObjectIdentifier7);
        hashtable2.put("e", aSN1ObjectIdentifier4);
        hashtable2.put("uid", aSN1ObjectIdentifier8);
        hashtable2.put("surname", m1025s10);
        hashtable2.put("givenname", m1025s11);
        hashtable2.put("initials", m1025s12);
        hashtable2.put("generation", m1025s13);
        hashtable2.put("description", m1025s15);
        hashtable2.put("role", m1025s20);
        hashtable2.put("unstructuredaddress", aSN1ObjectIdentifier6);
        hashtable2.put("unstructuredname", aSN1ObjectIdentifier5);
        hashtable2.put("uniqueidentifier", m1025s14);
        hashtable2.put("dn", m1025s18);
        hashtable2.put("pseudonym", m1025s19);
        hashtable2.put("postaladdress", m1025s27);
        hashtable2.put("nameatbirth", m1025s26);
        hashtable2.put("countryofcitizenship", m1025s24);
        hashtable2.put("countryofresidence", m1025s25);
        hashtable2.put("gender", m1025s23);
        hashtable2.put("placeofbirth", m1025s22);
        hashtable2.put("dateofbirth", m1025s21);
        hashtable2.put("postalcode", m1025s17);
        hashtable2.put("businesscategory", m1025s16);
        hashtable2.put("telephonenumber", aSN1ObjectIdentifier);
        hashtable2.put("name", aSN1ObjectIdentifier2);
        hashtable2.put("organizationidentifier", aSN1ObjectIdentifier3);
        INSTANCE = new BCStyle();
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    @Override // org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    public ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        return (aSN1ObjectIdentifier.equals((ASN1Primitive) EmailAddress) || aSN1ObjectIdentifier.equals((ASN1Primitive) DC)) ? new DERIA5String(str) : aSN1ObjectIdentifier.equals((ASN1Primitive) DATE_OF_BIRTH) ? new ASN1GeneralizedTime(str) : (aSN1ObjectIdentifier.equals((ASN1Primitive) f1088C) || aSN1ObjectIdentifier.equals((ASN1Primitive) SN) || aSN1ObjectIdentifier.equals((ASN1Primitive) DN_QUALIFIER) || aSN1ObjectIdentifier.equals((ASN1Primitive) TELEPHONE_NUMBER)) ? new DERPrintableString(str) : super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public RDN[] fromString(String str) {
        return IETFUtils.rDNsFromString(str, this);
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public String[] oidToAttrNames(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public String oidToDisplayName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) DefaultSymbols.get(aSN1ObjectIdentifier);
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public String toString(X500Name x500Name) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = true;
        for (RDN rdn : x500Name.getRDNs()) {
            if (z2) {
                z2 = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rdn, this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
