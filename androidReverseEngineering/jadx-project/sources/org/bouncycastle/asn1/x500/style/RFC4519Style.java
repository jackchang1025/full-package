package org.bouncycastle.asn1.x500.style;

import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.i18n.MessageBundle;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RFC4519Style extends AbstractX500NameStyle {
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;
    public static final X500NameStyle INSTANCE;
    public static final ASN1ObjectIdentifier businessCategory;

    /* renamed from: c */
    public static final ASN1ObjectIdentifier f1093c;
    public static final ASN1ObjectIdentifier cn;
    public static final ASN1ObjectIdentifier dc;
    public static final ASN1ObjectIdentifier description;
    public static final ASN1ObjectIdentifier destinationIndicator;
    public static final ASN1ObjectIdentifier distinguishedName;
    public static final ASN1ObjectIdentifier dnQualifier;
    public static final ASN1ObjectIdentifier enhancedSearchGuide;
    public static final ASN1ObjectIdentifier facsimileTelephoneNumber;
    public static final ASN1ObjectIdentifier generationQualifier;
    public static final ASN1ObjectIdentifier givenName;
    public static final ASN1ObjectIdentifier houseIdentifier;
    public static final ASN1ObjectIdentifier initials;
    public static final ASN1ObjectIdentifier internationalISDNNumber;

    /* renamed from: l */
    public static final ASN1ObjectIdentifier f1094l;
    public static final ASN1ObjectIdentifier member;
    public static final ASN1ObjectIdentifier name;

    /* renamed from: o */
    public static final ASN1ObjectIdentifier f1095o;
    public static final ASN1ObjectIdentifier ou;
    public static final ASN1ObjectIdentifier owner;
    public static final ASN1ObjectIdentifier physicalDeliveryOfficeName;
    public static final ASN1ObjectIdentifier postOfficeBox;
    public static final ASN1ObjectIdentifier postalAddress;
    public static final ASN1ObjectIdentifier postalCode;
    public static final ASN1ObjectIdentifier preferredDeliveryMethod;
    public static final ASN1ObjectIdentifier registeredAddress;
    public static final ASN1ObjectIdentifier roleOccupant;
    public static final ASN1ObjectIdentifier searchGuide;
    public static final ASN1ObjectIdentifier seeAlso;
    public static final ASN1ObjectIdentifier serialNumber;
    public static final ASN1ObjectIdentifier sn;
    public static final ASN1ObjectIdentifier st;
    public static final ASN1ObjectIdentifier street;
    public static final ASN1ObjectIdentifier telephoneNumber;
    public static final ASN1ObjectIdentifier teletexTerminalIdentifier;
    public static final ASN1ObjectIdentifier telexNumber;
    public static final ASN1ObjectIdentifier title;
    public static final ASN1ObjectIdentifier uid;
    public static final ASN1ObjectIdentifier uniqueMember;
    public static final ASN1ObjectIdentifier userPassword;
    public static final ASN1ObjectIdentifier x121Address;
    public static final ASN1ObjectIdentifier x500UniqueIdentifier;
    protected final Hashtable defaultSymbols = AbstractX500NameStyle.copyHashTable(DefaultSymbols);
    protected final Hashtable defaultLookUp = AbstractX500NameStyle.copyHashTable(DefaultLookUp);

    static {
        ASN1ObjectIdentifier m1025s = AbstractC0413b.m1025s("2.5.4.15");
        businessCategory = m1025s;
        ASN1ObjectIdentifier m1025s2 = AbstractC0413b.m1025s("2.5.4.6");
        f1093c = m1025s2;
        ASN1ObjectIdentifier m1025s3 = AbstractC0413b.m1025s("2.5.4.3");
        cn = m1025s3;
        ASN1ObjectIdentifier m1025s4 = AbstractC0413b.m1025s("0.9.2342.19200300.100.1.25");
        dc = m1025s4;
        ASN1ObjectIdentifier m1025s5 = AbstractC0413b.m1025s("2.5.4.13");
        description = m1025s5;
        ASN1ObjectIdentifier m1025s6 = AbstractC0413b.m1025s("2.5.4.27");
        destinationIndicator = m1025s6;
        ASN1ObjectIdentifier m1025s7 = AbstractC0413b.m1025s("2.5.4.49");
        distinguishedName = m1025s7;
        ASN1ObjectIdentifier m1025s8 = AbstractC0413b.m1025s("2.5.4.46");
        dnQualifier = m1025s8;
        ASN1ObjectIdentifier m1025s9 = AbstractC0413b.m1025s("2.5.4.47");
        enhancedSearchGuide = m1025s9;
        ASN1ObjectIdentifier m1025s10 = AbstractC0413b.m1025s("2.5.4.23");
        facsimileTelephoneNumber = m1025s10;
        ASN1ObjectIdentifier m1025s11 = AbstractC0413b.m1025s("2.5.4.44");
        generationQualifier = m1025s11;
        ASN1ObjectIdentifier m1025s12 = AbstractC0413b.m1025s("2.5.4.42");
        givenName = m1025s12;
        ASN1ObjectIdentifier m1025s13 = AbstractC0413b.m1025s("2.5.4.51");
        houseIdentifier = m1025s13;
        ASN1ObjectIdentifier m1025s14 = AbstractC0413b.m1025s("2.5.4.43");
        initials = m1025s14;
        ASN1ObjectIdentifier m1025s15 = AbstractC0413b.m1025s("2.5.4.25");
        internationalISDNNumber = m1025s15;
        ASN1ObjectIdentifier m1025s16 = AbstractC0413b.m1025s("2.5.4.7");
        f1094l = m1025s16;
        ASN1ObjectIdentifier m1025s17 = AbstractC0413b.m1025s("2.5.4.31");
        member = m1025s17;
        ASN1ObjectIdentifier m1025s18 = AbstractC0413b.m1025s("2.5.4.41");
        name = m1025s18;
        ASN1ObjectIdentifier m1025s19 = AbstractC0413b.m1025s("2.5.4.10");
        f1095o = m1025s19;
        ASN1ObjectIdentifier m1025s20 = AbstractC0413b.m1025s("2.5.4.11");
        ou = m1025s20;
        ASN1ObjectIdentifier m1025s21 = AbstractC0413b.m1025s("2.5.4.32");
        owner = m1025s21;
        ASN1ObjectIdentifier m1025s22 = AbstractC0413b.m1025s("2.5.4.19");
        physicalDeliveryOfficeName = m1025s22;
        ASN1ObjectIdentifier m1025s23 = AbstractC0413b.m1025s("2.5.4.16");
        postalAddress = m1025s23;
        ASN1ObjectIdentifier m1025s24 = AbstractC0413b.m1025s("2.5.4.17");
        postalCode = m1025s24;
        ASN1ObjectIdentifier m1025s25 = AbstractC0413b.m1025s("2.5.4.18");
        postOfficeBox = m1025s25;
        ASN1ObjectIdentifier m1025s26 = AbstractC0413b.m1025s("2.5.4.28");
        preferredDeliveryMethod = m1025s26;
        ASN1ObjectIdentifier m1025s27 = AbstractC0413b.m1025s("2.5.4.26");
        registeredAddress = m1025s27;
        ASN1ObjectIdentifier m1025s28 = AbstractC0413b.m1025s("2.5.4.33");
        roleOccupant = m1025s28;
        ASN1ObjectIdentifier m1025s29 = AbstractC0413b.m1025s("2.5.4.14");
        searchGuide = m1025s29;
        ASN1ObjectIdentifier m1025s30 = AbstractC0413b.m1025s("2.5.4.34");
        seeAlso = m1025s30;
        ASN1ObjectIdentifier m1025s31 = AbstractC0413b.m1025s("2.5.4.5");
        serialNumber = m1025s31;
        ASN1ObjectIdentifier m1025s32 = AbstractC0413b.m1025s("2.5.4.4");
        sn = m1025s32;
        ASN1ObjectIdentifier m1025s33 = AbstractC0413b.m1025s("2.5.4.8");
        st = m1025s33;
        ASN1ObjectIdentifier m1025s34 = AbstractC0413b.m1025s("2.5.4.9");
        street = m1025s34;
        ASN1ObjectIdentifier m1025s35 = AbstractC0413b.m1025s("2.5.4.20");
        telephoneNumber = m1025s35;
        ASN1ObjectIdentifier m1025s36 = AbstractC0413b.m1025s("2.5.4.22");
        teletexTerminalIdentifier = m1025s36;
        ASN1ObjectIdentifier m1025s37 = AbstractC0413b.m1025s("2.5.4.21");
        telexNumber = m1025s37;
        ASN1ObjectIdentifier m1025s38 = AbstractC0413b.m1025s("2.5.4.12");
        title = m1025s38;
        ASN1ObjectIdentifier m1025s39 = AbstractC0413b.m1025s("0.9.2342.19200300.100.1.1");
        uid = m1025s39;
        ASN1ObjectIdentifier m1025s40 = AbstractC0413b.m1025s("2.5.4.50");
        uniqueMember = m1025s40;
        ASN1ObjectIdentifier m1025s41 = AbstractC0413b.m1025s("2.5.4.35");
        userPassword = m1025s41;
        ASN1ObjectIdentifier m1025s42 = AbstractC0413b.m1025s("2.5.4.24");
        x121Address = m1025s42;
        ASN1ObjectIdentifier m1025s43 = AbstractC0413b.m1025s("2.5.4.45");
        x500UniqueIdentifier = m1025s43;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(m1025s, "businessCategory");
        hashtable.put(m1025s2, "c");
        hashtable.put(m1025s3, "cn");
        hashtable.put(m1025s4, "dc");
        hashtable.put(m1025s5, "description");
        hashtable.put(m1025s6, "destinationIndicator");
        hashtable.put(m1025s7, "distinguishedName");
        hashtable.put(m1025s8, "dnQualifier");
        hashtable.put(m1025s9, "enhancedSearchGuide");
        hashtable.put(m1025s10, "facsimileTelephoneNumber");
        hashtable.put(m1025s11, "generationQualifier");
        hashtable.put(m1025s12, "givenName");
        hashtable.put(m1025s13, "houseIdentifier");
        hashtable.put(m1025s14, "initials");
        hashtable.put(m1025s15, "internationalISDNNumber");
        hashtable.put(m1025s16, "l");
        hashtable.put(m1025s17, "member");
        hashtable.put(m1025s18, "name");
        hashtable.put(m1025s19, "o");
        hashtable.put(m1025s20, "ou");
        hashtable.put(m1025s21, "owner");
        hashtable.put(m1025s22, "physicalDeliveryOfficeName");
        hashtable.put(m1025s23, "postalAddress");
        hashtable.put(m1025s24, "postalCode");
        hashtable.put(m1025s25, "postOfficeBox");
        hashtable.put(m1025s26, "preferredDeliveryMethod");
        hashtable.put(m1025s27, "registeredAddress");
        hashtable.put(m1025s28, "roleOccupant");
        hashtable.put(m1025s29, "searchGuide");
        hashtable.put(m1025s30, "seeAlso");
        hashtable.put(m1025s31, "serialNumber");
        hashtable.put(m1025s32, "sn");
        hashtable.put(m1025s33, "st");
        hashtable.put(m1025s34, "street");
        hashtable.put(m1025s35, "telephoneNumber");
        hashtable.put(m1025s36, "teletexTerminalIdentifier");
        hashtable.put(m1025s37, "telexNumber");
        hashtable.put(m1025s38, MessageBundle.TITLE_ENTRY);
        hashtable.put(m1025s39, "uid");
        hashtable.put(m1025s40, "uniqueMember");
        hashtable.put(m1025s41, "userPassword");
        hashtable.put(m1025s42, "x121Address");
        hashtable.put(m1025s43, "x500UniqueIdentifier");
        hashtable2.put("businesscategory", m1025s);
        hashtable2.put("c", m1025s2);
        hashtable2.put("cn", m1025s3);
        hashtable2.put("dc", m1025s4);
        hashtable2.put("description", m1025s5);
        hashtable2.put("destinationindicator", m1025s6);
        hashtable2.put("distinguishedname", m1025s7);
        hashtable2.put("dnqualifier", m1025s8);
        hashtable2.put("enhancedsearchguide", m1025s9);
        hashtable2.put("facsimiletelephonenumber", m1025s10);
        hashtable2.put("generationqualifier", m1025s11);
        hashtable2.put("givenname", m1025s12);
        hashtable2.put("houseidentifier", m1025s13);
        hashtable2.put("initials", m1025s14);
        hashtable2.put("internationalisdnnumber", m1025s15);
        hashtable2.put("l", m1025s16);
        hashtable2.put("member", m1025s17);
        hashtable2.put("name", m1025s18);
        hashtable2.put("o", m1025s19);
        hashtable2.put("ou", m1025s20);
        hashtable2.put("owner", m1025s21);
        hashtable2.put("physicaldeliveryofficename", m1025s22);
        hashtable2.put("postaladdress", m1025s23);
        hashtable2.put("postalcode", m1025s24);
        hashtable2.put("postofficebox", m1025s25);
        hashtable2.put("preferreddeliverymethod", m1025s26);
        hashtable2.put("registeredaddress", m1025s27);
        hashtable2.put("roleoccupant", m1025s28);
        hashtable2.put("searchguide", m1025s29);
        hashtable2.put("seealso", m1025s30);
        hashtable2.put("serialnumber", m1025s31);
        hashtable2.put("sn", m1025s32);
        hashtable2.put("st", m1025s33);
        hashtable2.put("street", m1025s34);
        hashtable2.put("telephonenumber", m1025s35);
        hashtable2.put("teletexterminalidentifier", m1025s36);
        hashtable2.put("telexnumber", m1025s37);
        hashtable2.put(MessageBundle.TITLE_ENTRY, m1025s38);
        hashtable2.put("uid", m1025s39);
        hashtable2.put("uniquemember", m1025s40);
        hashtable2.put("userpassword", m1025s41);
        hashtable2.put("x121address", m1025s42);
        hashtable2.put("x500uniqueidentifier", m1025s43);
        INSTANCE = new RFC4519Style();
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    @Override // org.bouncycastle.asn1.x500.style.AbstractX500NameStyle
    public ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        return aSN1ObjectIdentifier.equals((ASN1Primitive) dc) ? new DERIA5String(str) : (aSN1ObjectIdentifier.equals((ASN1Primitive) f1093c) || aSN1ObjectIdentifier.equals((ASN1Primitive) serialNumber) || aSN1ObjectIdentifier.equals((ASN1Primitive) dnQualifier) || aSN1ObjectIdentifier.equals((ASN1Primitive) telephoneNumber)) ? new DERPrintableString(str) : super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    @Override // org.bouncycastle.asn1.x500.X500NameStyle
    public RDN[] fromString(String str) {
        RDN[] rDNsFromString = IETFUtils.rDNsFromString(str, this);
        RDN[] rdnArr = new RDN[rDNsFromString.length];
        for (int i2 = 0; i2 != rDNsFromString.length; i2++) {
            rdnArr[(r0 - i2) - 1] = rDNsFromString[i2];
        }
        return rdnArr;
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
        RDN[] rDNs = x500Name.getRDNs();
        boolean z2 = true;
        for (int length = rDNs.length - 1; length >= 0; length--) {
            if (z2) {
                z2 = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rDNs[length], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
