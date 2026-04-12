package p000;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import p000.AbstractC1316ux;

/* renamed from: vc */
/* loaded from: classes2.dex */
public class C1332vc {
    static final Hashtable names;
    static final Hashtable objIds;
    static final Hashtable params;

    static {
        Hashtable hashtable = new Hashtable();
        objIds = hashtable;
        Hashtable hashtable2 = new Hashtable();
        params = hashtable2;
        Hashtable hashtable3 = new Hashtable();
        names = hashtable3;
        BigInteger bigIntegerFromHex = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD97");
        BigInteger bigIntegerFromHex2 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF6C611070995AD10045841B09B761B893");
        BigInteger bigIntegerFromHex3 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD94");
        BigInteger bigIntegerFromHex4 = fromHex("A6");
        BigInteger bigInteger = InterfaceC1315uw.ONE;
        AbstractC1316ux abstractC1316uxConfigureCurve = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex, bigIntegerFromHex3, bigIntegerFromHex4, bigIntegerFromHex2, bigInteger));
        C1317uy c1317uy = new C1317uy(abstractC1316uxConfigureCurve, configureBasepoint(abstractC1316uxConfigureCurve, bigInteger, fromHex("8D91E471E0989CDA27DF505A453F2B7635294F2DDF23E3B122ACC99C9E9F1E14")), bigIntegerFromHex2, bigInteger);
        C0160c5 c0160c5 = InterfaceC0928nw.gostR3410_2001_CryptoPro_A;
        hashtable2.put(c0160c5, c1317uy);
        BigInteger bigIntegerFromHex5 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD97");
        BigInteger bigIntegerFromHex6 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF6C611070995AD10045841B09B761B893");
        AbstractC1316ux abstractC1316uxConfigureCurve2 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex5, fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD94"), fromHex("A6"), bigIntegerFromHex6, bigInteger));
        C1317uy c1317uy2 = new C1317uy(abstractC1316uxConfigureCurve2, configureBasepoint(abstractC1316uxConfigureCurve2, bigInteger, fromHex("8D91E471E0989CDA27DF505A453F2B7635294F2DDF23E3B122ACC99C9E9F1E14")), bigIntegerFromHex6, bigInteger);
        C0160c5 c0160c52 = InterfaceC0928nw.gostR3410_2001_CryptoPro_XchA;
        hashtable2.put(c0160c52, c1317uy2);
        BigInteger bigIntegerFromHex7 = fromHex("8000000000000000000000000000000000000000000000000000000000000C99");
        BigInteger bigIntegerFromHex8 = fromHex("800000000000000000000000000000015F700CFFF1A624E5E497161BCC8A198F");
        AbstractC1316ux abstractC1316uxConfigureCurve3 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex7, fromHex("8000000000000000000000000000000000000000000000000000000000000C96"), fromHex("3E1AF419A269A5F866A7D3C25C3DF80AE979259373FF2B182F49D4CE7E1BBC8B"), bigIntegerFromHex8, bigInteger));
        C1317uy c1317uy3 = new C1317uy(abstractC1316uxConfigureCurve3, configureBasepoint(abstractC1316uxConfigureCurve3, bigInteger, fromHex("3FA8124359F96680B83D1C3EB2C070E5C545C9858D03ECFB744BF8D717717EFC")), bigIntegerFromHex8, bigInteger);
        C0160c5 c0160c53 = InterfaceC0928nw.gostR3410_2001_CryptoPro_B;
        hashtable2.put(c0160c53, c1317uy3);
        BigInteger bigIntegerFromHex9 = fromHex("9B9F605F5A858107AB1EC85E6B41C8AACF846E86789051D37998F7B9022D759B");
        BigInteger bigIntegerFromHex10 = fromHex("9B9F605F5A858107AB1EC85E6B41C8AA582CA3511EDDFB74F02F3A6598980BB9");
        AbstractC1316ux abstractC1316uxConfigureCurve4 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex9, fromHex("9B9F605F5A858107AB1EC85E6B41C8AACF846E86789051D37998F7B9022D7598"), fromHex("805A"), bigIntegerFromHex10, bigInteger));
        BigInteger bigInteger2 = InterfaceC1315uw.ZERO;
        C1317uy c1317uy4 = new C1317uy(abstractC1316uxConfigureCurve4, configureBasepoint(abstractC1316uxConfigureCurve4, bigInteger2, fromHex("41ECE55743711A8C3CBF3783CD08C0EE4D4DC440D4641A8F366E550DFDB3BB67")), bigIntegerFromHex10, bigInteger);
        C0160c5 c0160c54 = InterfaceC0928nw.gostR3410_2001_CryptoPro_XchB;
        hashtable2.put(c0160c54, c1317uy4);
        BigInteger bigIntegerFromHex11 = fromHex("9B9F605F5A858107AB1EC85E6B41C8AACF846E86789051D37998F7B9022D759B");
        BigInteger bigIntegerFromHex12 = fromHex("9B9F605F5A858107AB1EC85E6B41C8AA582CA3511EDDFB74F02F3A6598980BB9");
        AbstractC1316ux abstractC1316uxConfigureCurve5 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex11, fromHex("9B9F605F5A858107AB1EC85E6B41C8AACF846E86789051D37998F7B9022D7598"), fromHex("805A"), bigIntegerFromHex12, bigInteger));
        C1317uy c1317uy5 = new C1317uy(abstractC1316uxConfigureCurve5, configureBasepoint(abstractC1316uxConfigureCurve5, bigInteger2, fromHex("41ECE55743711A8C3CBF3783CD08C0EE4D4DC440D4641A8F366E550DFDB3BB67")), bigIntegerFromHex12, bigInteger);
        C0160c5 c0160c55 = InterfaceC0928nw.gostR3410_2001_CryptoPro_C;
        hashtable2.put(c0160c55, c1317uy5);
        BigInteger bigIntegerFromHex13 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD97");
        BigInteger bigIntegerFromHex14 = fromHex("400000000000000000000000000000000FD8CDDFC87B6635C115AF556C360C67");
        BigInteger bigIntegerFromHex15 = fromHex("C2173F1513981673AF4892C23035A27CE25E2013BF95AA33B22C656F277E7335");
        BigInteger bigIntegerFromHex16 = fromHex("295F9BAE7428ED9CCC20E7C359A9D41A22FCCD9108E17BF7BA9337A6F8AE9513");
        BigInteger bigInteger3 = InterfaceC1315uw.FOUR;
        AbstractC1316ux abstractC1316uxConfigureCurve6 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex13, bigIntegerFromHex15, bigIntegerFromHex16, bigIntegerFromHex14, bigInteger3));
        C1317uy c1317uy6 = new C1317uy(abstractC1316uxConfigureCurve6, configureBasepoint(abstractC1316uxConfigureCurve6, fromHex("91E38443A5E82C0D880923425712B2BB658B9196932E02C78B2582FE742DAA28"), fromHex("32879423AB1A0375895786C4BB46E9565FDE0B5344766740AF268ADB32322E5C")), bigIntegerFromHex14, bigInteger3);
        C0160c5 c0160c56 = ks0.id_tc26_gost_3410_12_256_paramSetA;
        hashtable2.put(c0160c56, c1317uy6);
        BigInteger bigIntegerFromHex17 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDC7");
        BigInteger bigIntegerFromHex18 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF27E69532F48D89116FF22B8D4E0560609B4B38ABFAD2B85DCACDB1411F10B275");
        AbstractC1316ux abstractC1316uxConfigureCurve7 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex17, fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDC4"), fromHex("E8C2505DEDFC86DDC1BD0B2B6667F1DA34B82574761CB0E879BD081CFD0B6265EE3CB090F30D27614CB4574010DA90DD862EF9D4EBEE4761503190785A71C760"), bigIntegerFromHex18, bigInteger));
        C1317uy c1317uy7 = new C1317uy(abstractC1316uxConfigureCurve7, configureBasepoint(abstractC1316uxConfigureCurve7, InterfaceC1315uw.THREE, fromHex("7503CFE87A836AE3A61B8816E25450E6CE5E1C93ACF1ABC1778064FDCBEFA921DF1626BE4FD036E93D75E6A50E3A41E98028FE5FC235F5B889A589CB5215F2A4")), bigIntegerFromHex18, bigInteger);
        C0160c5 c0160c57 = ks0.id_tc26_gost_3410_12_512_paramSetA;
        hashtable2.put(c0160c57, c1317uy7);
        BigInteger bigIntegerFromHex19 = fromHex("8000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006F");
        BigInteger bigIntegerFromHex20 = fromHex("800000000000000000000000000000000000000000000000000000000000000149A1EC142565A545ACFDB77BD9D40CFA8B996712101BEA0EC6346C54374F25BD");
        AbstractC1316ux abstractC1316uxConfigureCurve8 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex19, fromHex("8000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006C"), fromHex("687D1B459DC841457E3E06CF6F5E2517B97C7D614AF138BCBF85DC806C4B289F3E965D2DB1416D217F8B276FAD1AB69C50F78BEE1FA3106EFB8CCBC7C5140116"), bigIntegerFromHex20, bigInteger));
        C1317uy c1317uy8 = new C1317uy(abstractC1316uxConfigureCurve8, configureBasepoint(abstractC1316uxConfigureCurve8, InterfaceC1315uw.TWO, fromHex("1A8F7EDA389B094C2C071E3647A8940F3C123B697578C213BE6DD9E6C8EC7335DCB228FD1EDF4A39152CBCAAF8C0398828041055F94CEEEC7E21340780FE41BD")), bigIntegerFromHex20, bigInteger);
        C0160c5 c0160c58 = ks0.id_tc26_gost_3410_12_512_paramSetB;
        hashtable2.put(c0160c58, c1317uy8);
        BigInteger bigIntegerFromHex21 = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDC7");
        BigInteger bigIntegerFromHex22 = fromHex("3FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC98CDBA46506AB004C33A9FF5147502CC8EDA9E7A769A12694623CEF47F023ED");
        AbstractC1316ux abstractC1316uxConfigureCurve9 = configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex21, fromHex("DC9203E514A721875485A529D2C722FB187BC8980EB866644DE41C68E143064546E861C0E2C9EDD92ADE71F46FCF50FF2AD97F951FDA9F2A2EB6546F39689BD3"), fromHex("B4C4EE28CEBC6C2C8AC12952CF37F16AC7EFB6A9F69F4B57FFDA2E4F0DE5ADE038CBC2FFF719D2C18DE0284B8BFEF3B52B8CC7A5F5BF0A3C8D2319A5312557E1"), bigIntegerFromHex22, bigInteger3));
        C1317uy c1317uy9 = new C1317uy(abstractC1316uxConfigureCurve9, configureBasepoint(abstractC1316uxConfigureCurve9, fromHex("E2E31EDFC23DE7BDEBE241CE593EF5DE2295B7A9CBAEF021D385F7074CEA043AA27272A7AE602BF2A7B9033DB9ED3610C6FB85487EAE97AAC5BC7928C1950148"), fromHex("F5CE40D95B5EB899ABBCCFF5911CB8577939804D6527378B8C108C3D2090FF9BE18E2D33E3021ED2EF32D85822423B6304F726AA854BAE07D0396E9A9ADDC40F")), bigIntegerFromHex22, bigInteger3);
        C0160c5 c0160c59 = ks0.id_tc26_gost_3410_12_512_paramSetC;
        hashtable2.put(c0160c59, c1317uy9);
        hashtable.put("GostR3410-2001-CryptoPro-A", c0160c5);
        hashtable.put("GostR3410-2001-CryptoPro-B", c0160c53);
        hashtable.put("GostR3410-2001-CryptoPro-C", c0160c55);
        hashtable.put("GostR3410-2001-CryptoPro-XchA", c0160c52);
        hashtable.put("GostR3410-2001-CryptoPro-XchB", c0160c54);
        hashtable.put("Tc26-Gost-3410-12-256-paramSetA", c0160c56);
        hashtable.put("Tc26-Gost-3410-12-512-paramSetA", c0160c57);
        hashtable.put("Tc26-Gost-3410-12-512-paramSetB", c0160c58);
        hashtable.put("Tc26-Gost-3410-12-512-paramSetC", c0160c59);
        hashtable3.put(c0160c5, "GostR3410-2001-CryptoPro-A");
        hashtable3.put(c0160c53, "GostR3410-2001-CryptoPro-B");
        hashtable3.put(c0160c55, "GostR3410-2001-CryptoPro-C");
        hashtable3.put(c0160c52, "GostR3410-2001-CryptoPro-XchA");
        hashtable3.put(c0160c54, "GostR3410-2001-CryptoPro-XchB");
        hashtable3.put(c0160c56, "Tc26-Gost-3410-12-256-paramSetA");
        hashtable3.put(c0160c57, "Tc26-Gost-3410-12-512-paramSetA");
        hashtable3.put(c0160c58, "Tc26-Gost-3410-12-512-paramSetB");
        hashtable3.put(c0160c59, "Tc26-Gost-3410-12-512-paramSetC");
    }

    private static AbstractC1341vl configureBasepoint(AbstractC1316ux abstractC1316ux, BigInteger bigInteger, BigInteger bigInteger2) {
        AbstractC1341vl abstractC1341vlCreatePoint = abstractC1316ux.createPoint(bigInteger, bigInteger2);
        xd1.configureBasepoint(abstractC1341vlCreatePoint);
        return abstractC1341vlCreatePoint;
    }

    private static BigInteger fromHex(String str) {
        return new BigInteger(1, c40.decodeStrict(str));
    }

    public static C1317uy getByName(String str) {
        C0160c5 c0160c5 = (C0160c5) objIds.get(str);
        if (c0160c5 == null) {
            return null;
        }
        return (C1317uy) params.get(c0160c5);
    }

    public static bi1 getByNameX9(String str) {
        C0160c5 c0160c5 = (C0160c5) objIds.get(str);
        if (c0160c5 == null) {
            return null;
        }
        return getByOIDX9(c0160c5);
    }

    public static C1317uy getByOID(C0160c5 c0160c5) {
        return (C1317uy) params.get(c0160c5);
    }

    public static bi1 getByOIDX9(C0160c5 c0160c5) {
        C1317uy c1317uy = (C1317uy) params.get(c0160c5);
        if (c1317uy == null) {
            return null;
        }
        return new bi1(c1317uy.getCurve(), new di1(c1317uy.getG(), false), c1317uy.getN(), c1317uy.getH(), c1317uy.getSeed());
    }

    public static String getName(C0160c5 c0160c5) {
        return (String) names.get(c0160c5);
    }

    public static Enumeration getNames() {
        return names.elements();
    }

    public static C0160c5 getOID(String str) {
        return (C0160c5) objIds.get(str);
    }

    private static AbstractC1316ux configureCurve(AbstractC1316ux abstractC1316ux) {
        return abstractC1316ux;
    }
}
