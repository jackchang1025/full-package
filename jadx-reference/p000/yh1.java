package p000;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.util.Strings;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class yh1 {
    static ci1 prime192v1 = new C1468b0();
    static ci1 prime192v2 = new C1473b5();
    static ci1 prime192v3 = new C1474b6();
    static ci1 prime239v1 = new C1475b7();
    static ci1 prime239v2 = new C1476b8();
    static ci1 prime239v3 = new C1477b9();
    static ci1 prime256v1 = new C1478c0();
    static ci1 c2pnb163v1 = new C1479c1();
    static ci1 c2pnb163v2 = new C1480c2();
    static ci1 c2pnb163v3 = new C1458a0();
    static ci1 c2pnb176w1 = new C1459a1();
    static ci1 c2tnb191v1 = new C1460a2();
    static ci1 c2tnb191v2 = new C1461a3();
    static ci1 c2tnb191v3 = new C1462a4();
    static ci1 c2pnb208w1 = new C1463a5();
    static ci1 c2tnb239v1 = new C1464a6();
    static ci1 c2tnb239v2 = new C1465a7();
    static ci1 c2tnb239v3 = new C1466a8();
    static ci1 c2pnb272w1 = new C1467a9();
    static ci1 c2pnb304w1 = new C1469b1();
    static ci1 c2tnb359v1 = new C1470b2();
    static ci1 c2pnb368w1 = new C1471b3();
    static ci1 c2tnb431r1 = new C1472b4();
    static final Hashtable objIds = new Hashtable();
    static final Hashtable curves = new Hashtable();
    static final Hashtable names = new Hashtable();

    /* renamed from: yh1$a0 */
    public static class C1458a0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("03FFFFFFFFFFFFFFFFFFFE1AEE140F110AFF961309");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(163, 1, 2, 8, yh1.fromHex("07A526C63D3E25A256A007699F5447E32AE456B50E"), yh1.fromHex("03F7061798EB99E238FD6F1BF95B48FEEB4854252B"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0202F9F87B7C574D0BDECF8A22E6524775F98CDEBDCB"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a1 */
    public static class C1459a1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("010092537397ECA4F6145799D62B0A19CE06FE26AD");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(65390L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(176, 1, 2, 43, yh1.fromHex("E4E6DB2995065C407D9D39B8D0967B96704BA8E9C90B"), yh1.fromHex("5DDA470ABE6414DE8EC133AE28E9BBD7FCEC0AE0FFF2"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "038D16C2866798B600F9F08BB4A8E860F3298CE04A5798"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a2 */
    public static class C1460a2 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("40000000000000000000000004A20E90C39067C893BBB9A5");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(191, 9, yh1.fromHex("2866537B676752636A68F56554E12640276B649EF7526267"), yh1.fromHex("2E45EF571F00786F67B0081B9495A3D95462F5DE0AA185EC"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0236B3DAF8A23206F9C4F299D7B21A9C369137F2C84AE1AA0D"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("4E13CA542744D696E67687561517552F279A8C84"));
        }
    }

    /* renamed from: yh1$a3 */
    public static class C1461a3 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("20000000000000000000000050508CB89F652824E06B8173");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(4L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(191, 9, yh1.fromHex("401028774D7777C7B7666D1366EA432071274F89FF01E718"), yh1.fromHex("0620048D28BCBD03B6249C99182B7C8CD19700C362C46A01"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "023809B2B7CC1B28CC5A87926AAD83FD28789E81E2C9E3BF10"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a4 */
    public static class C1462a4 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("155555555555555555555555610C0B196812BFB6288A3EA3");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(6L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(191, 9, yh1.fromHex("6C01074756099122221056911C77D77E77A777E7E7E77FCB"), yh1.fromHex("71FE1AF926CF847989EFEF8DB459F66394D90F32AD3F15E8"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "03375D4CE24FDE434489DE8746E71786015009E66E38A926DD"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a5 */
    public static class C1463a5 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0101BAF95C9723C57B6C21DA2EFF2D5ED588BDD5717E212F9D");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(65096L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(208, 1, 2, 83, BigInteger.valueOf(0L), yh1.fromHex("C8619ED45A62E6212E1160349E2BFA844439FAFC2A3FD1638F9E"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0289FDFBE4ABE193DF9559ECF07AC0CE78554E2784EB8C1ED1A57A"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a6 */
    public static class C1464a6 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("2000000000000000000000000000000F4D42FFE1492A4993F1CAD666E447");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(4L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(239, 36, yh1.fromHex("32010857077C5431123A46B808906756F543423E8D27877578125778AC76"), yh1.fromHex("790408F2EEDAF392B012EDEFB3392F30F4327C0CA3F31FC383C422AA8C16"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0257927098FA932E7C0A96D3FD5B706EF7E5F5C156E16B7E7C86038552E91D"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a7 */
    public static class C1465a7 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("1555555555555555555555555555553C6F2885259C31E3FCDF154624522D");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(6L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(239, 36, yh1.fromHex("4230017757A767FAE42398569B746325D45313AF0766266479B75654E65F"), yh1.fromHex("5037EA654196CFF0CD82B2C14A2FCF2E3FF8775285B545722F03EACDB74B"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0228F9D04E900069C8DC47A08534FE76D2B900B7D7EF31F5709F200C4CA205"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a8 */
    public static class C1466a8 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0CCCCCCCCCCCCCCCCCCCCCCCCCCCCCAC4912D2D9DF903EF9888B8A0E4CFF");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(10L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(239, 36, yh1.fromHex("01238774666A67766D6676F778E676B66999176666E687666D8766C66A9F"), yh1.fromHex("6A941977BA9F6A435199ACFC51067ED587F519C5ECB541B8E44111DE1D40"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0370F6E9D04D289C4E89913CE3530BFDE903977D42B146D539BF1BDE4E9C92"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$a9 */
    public static class C1467a9 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0100FAF51354E0E39E4892DF6E319C72C8161603FA45AA7B998A167B8F1E629521");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(65286L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(272, 1, 3, 56, yh1.fromHex("91A091F03B5FBA4AB2CCF49C4EDD220FB028712D42BE752B2C40094DBACDB586FB20"), yh1.fromHex("7167EFC92BB2E3CE7C8AAAFF34E12A9C557003D7C73A6FAF003F99F6CC8482E540F7"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "026108BABB2CEEBCF787058A056CBE0CFE622D7723A289E08A07AE13EF0D10D171DD8D"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$b0 */
    public static class C1468b0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("ffffffffffffffffffffffff99def836146bc9b1b4d22831");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(yh1.fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"), yh1.fromHex("fffffffffffffffffffffffffffffffefffffffffffffffc"), yh1.fromHex("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "03188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("3045AE6FC8422f64ED579528D38120EAE12196D5"));
        }
    }

    /* renamed from: yh1$b1 */
    public static class C1469b1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0101D556572AABAC800101D556572AABAC8001022D5C91DD173F8FB561DA6899164443051D");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(65070L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(304, 1, 2, 11, yh1.fromHex("FD0D693149A118F651E6DCE6802085377E5F882D1B510B44160074C1288078365A0396C8E681"), yh1.fromHex("BDDB97E555A50A908E43B01C798EA5DAA6788F1EA2794EFCF57166B8C14039601E55827340BE"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "02197B07845E9BE2D96ADB0F5F3C7F2CFFBD7A3EB8B6FEC35C7FD67F26DDF6285A644F740A2614"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$b2 */
    public static class C1470b2 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("01AF286BCA1AF286BCA1AF286BCA1AF286BCA1AF286BC9FB8F6B85C556892C20A7EB964FE7719E74F490758D3B");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(76L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(359, 68, yh1.fromHex("5667676A654B20754F356EA92017D946567C46675556F19556A04616B567D223A5E05656FB549016A96656A557"), yh1.fromHex("2472E2D0197C49363F1FE7F5B6DB075D52B6947D135D8CA445805D39BC345626089687742B6329E70680231988"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "033C258EF3047767E7EDE0F1FDAA79DAEE3841366A132E163ACED4ED2401DF9C6BDCDE98E8E707C07A2239B1B097"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$b3 */
    public static class C1471b3 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("010090512DA9AF72B08349D98A5DD4C7B0532ECA51CE03E2D10F3B7AC579BD87E909AE40A6F131E9CFCE5BD967");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(65392L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(368, 1, 2, 85, yh1.fromHex("E0D2EE25095206F5E2A4F9ED229F1F256E79A0E2B455970D8D0D865BD94778C576D62F0AB7519CCD2A1A906AE30D"), yh1.fromHex("FC1217D4320A90452C760A58EDCD30C8DD069B3C34453837A34ED50CB54917E1C2112D84D164F444F8F74786046A"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "021085E2755381DCCCE3C1557AFA10C2F0C0C2825646C5B34A394CBCFA8BC16B22E7E789E927BE216F02E1FB136A5F"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$b4 */
    public static class C1472b4 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0340340340340340340340340340340340340340340340340340340323C313FAB50589703B5EC68D3587FEC60D161CC149C1AD4A91");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(10080L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(431, 120, yh1.fromHex("1A827EF00DD6FC0E234CAF046C6A5D8A85395B236CC4AD2CF32A0CADBDC9DDF620B0EB9906D0957F6C6FEACD615468DF104DE296CD8F"), yh1.fromHex("10D9B4A3D9047D8B154359ABFB1B7F5485B04CEB868237DDC9DEDA982A679A5A919B626D4E50A8DD731B107A9962381FB5D807BF2618"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "02120FC05D3C67A99DE161D2F4092622FECA701BE4F50F4758714E8A87BBF2A658EF8C21E7C5EFE965361F6C2999C0C247B0DBD70CE6B7"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: yh1$b5 */
    public static class C1473b5 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("fffffffffffffffffffffffe5fb1a724dc80418648d8dd31");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(yh1.fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"), yh1.fromHex("fffffffffffffffffffffffffffffffefffffffffffffffc"), yh1.fromHex("cc22d6dfb95c6b25e49c0d6364a4e5980c393aa21668d953"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "03eea2bae7e1497842f2de7769cfe9c989c072ad696f48034a"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("31a92ee2029fd10d901b113e990710f0d21ac6b6"));
        }
    }

    /* renamed from: yh1$b6 */
    public static class C1474b6 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("ffffffffffffffffffffffff7a62d031c83f4294f640ec13");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(yh1.fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"), yh1.fromHex("fffffffffffffffffffffffffffffffefffffffffffffffc"), yh1.fromHex("22123dc2395a05caa7423daeccc94760a7d462256bd56916"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "027d29778100c65a1da1783716588dce2b8b4aee8e228f1896"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("c469684435deb378c4b65ca9591e2a5763059a2e"));
        }
    }

    /* renamed from: yh1$b7 */
    public static class C1475b7 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("7fffffffffffffffffffffff7fffff9e5e9a9f5d9071fbd1522688909d0b");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), yh1.fromHex("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc"), yh1.fromHex("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("e43bb460f0b80cc0c0b075798e948060f8321b7d"));
        }
    }

    /* renamed from: yh1$b8 */
    public static class C1476b8 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("7fffffffffffffffffffffff800000cfa7e8594377d414c03821bc582063");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), yh1.fromHex("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc"), yh1.fromHex("617fab6832576cbbfed50d99f0249c3fee58b94ba0038c7ae84c8c832f2c"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0238af09d98727705120c921bb5e9e26296a3cdcf2f35757a0eafd87b830e7"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("e8b4011604095303ca3b8099982be09fcb9ae616"));
        }
    }

    /* renamed from: yh1$b9 */
    public static class C1477b9 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("7fffffffffffffffffffffff7fffff975deb41b3a6057c3c432146526551");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), yh1.fromHex("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc"), yh1.fromHex("255705fa2a306654b1f4cb03d6a750a30c250102d4988717d9ba15ab6d3e"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "036768ae8e18bb92cfcf005c949aa2c6d94853d0e660bbf854b1c9505fe95a"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("7d7374168ffe3471b60a857686a19475d3bfa2ff"));
        }
    }

    /* renamed from: yh1$c0 */
    public static class C1478c0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a5(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951"), yh1.fromHex("ffffffff00000001000000000000000000000000fffffffffffffffffffffffc"), yh1.fromHex("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "036b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("c49d360886e704936a6678e1139d26b7819f7e90"));
        }
    }

    /* renamed from: yh1$c1 */
    public static class C1479c1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("0400000000000000000001E60FC8821CC74DAEAFC1");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(163, 1, 2, 8, yh1.fromHex("072546B5435234A422E0789675F432C89435DE5242"), yh1.fromHex("00C9517D06D5240D3CFF38C74B20B6CD4D6F9DD4D9"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "0307AF69989546103D79329FCC3D74880F33BBE803CB"), bigIntegerFromHex, bigIntegerValueOf, c40.decodeStrict("D2C0FB15760860DEF1EEF4D696E6768756151754"));
        }
    }

    /* renamed from: yh1$c2 */
    public static class C1480c2 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = yh1.fromHex("03FFFFFFFFFFFFFFFFFFFDF64DE1151ADBB78F10A7");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(2L);
            AbstractC1316ux abstractC1316uxConfigureCurve = yh1.configureCurve(new AbstractC1316ux.a4(163, 1, 2, 8, yh1.fromHex("0108B39E77C4B108BED981ED0E890E117C511CF072"), yh1.fromHex("0667ACEB38AF4E488C407433FFAE4F1C811638DF20"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, yh1.configureBasepoint(abstractC1316uxConfigureCurve, "030024266E4EB5106D0A964D92C4860E2671DB9B6CC5"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    static {
        defineCurve("prime192v1", hi1.prime192v1, prime192v1);
        defineCurve("prime192v2", hi1.prime192v2, prime192v2);
        defineCurve("prime192v3", hi1.prime192v3, prime192v3);
        defineCurve("prime239v1", hi1.prime239v1, prime239v1);
        defineCurve("prime239v2", hi1.prime239v2, prime239v2);
        defineCurve("prime239v3", hi1.prime239v3, prime239v3);
        defineCurve("prime256v1", hi1.prime256v1, prime256v1);
        defineCurve("c2pnb163v1", hi1.c2pnb163v1, c2pnb163v1);
        defineCurve("c2pnb163v2", hi1.c2pnb163v2, c2pnb163v2);
        defineCurve("c2pnb163v3", hi1.c2pnb163v3, c2pnb163v3);
        defineCurve("c2pnb176w1", hi1.c2pnb176w1, c2pnb176w1);
        defineCurve("c2tnb191v1", hi1.c2tnb191v1, c2tnb191v1);
        defineCurve("c2tnb191v2", hi1.c2tnb191v2, c2tnb191v2);
        defineCurve("c2tnb191v3", hi1.c2tnb191v3, c2tnb191v3);
        defineCurve("c2pnb208w1", hi1.c2pnb208w1, c2pnb208w1);
        defineCurve("c2tnb239v1", hi1.c2tnb239v1, c2tnb239v1);
        defineCurve("c2tnb239v2", hi1.c2tnb239v2, c2tnb239v2);
        defineCurve("c2tnb239v3", hi1.c2tnb239v3, c2tnb239v3);
        defineCurve("c2pnb272w1", hi1.c2pnb272w1, c2pnb272w1);
        defineCurve("c2pnb304w1", hi1.c2pnb304w1, c2pnb304w1);
        defineCurve("c2tnb359v1", hi1.c2tnb359v1, c2tnb359v1);
        defineCurve("c2pnb368w1", hi1.c2pnb368w1, c2pnb368w1);
        defineCurve("c2tnb431r1", hi1.c2tnb431r1, c2tnb431r1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static di1 configureBasepoint(AbstractC1316ux abstractC1316ux, String str) {
        di1 di1Var = new di1(abstractC1316ux, c40.decodeStrict(str));
        xd1.configureBasepoint(di1Var.getPoint());
        return di1Var;
    }

    public static void defineCurve(String str, C0160c5 c0160c5, ci1 ci1Var) {
        objIds.put(str, c0160c5);
        names.put(c0160c5, str);
        curves.put(c0160c5, ci1Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BigInteger fromHex(String str) {
        return new BigInteger(1, c40.decodeStrict(str));
    }

    public static bi1 getByName(String str) {
        C0160c5 c0160c5 = (C0160c5) objIds.get(Strings.toLowerCase(str));
        if (c0160c5 != null) {
            return getByOID(c0160c5);
        }
        return null;
    }

    public static bi1 getByOID(C0160c5 c0160c5) {
        ci1 ci1Var = (ci1) curves.get(c0160c5);
        if (ci1Var != null) {
            return ci1Var.getParameters();
        }
        return null;
    }

    public static String getName(C0160c5 c0160c5) {
        return (String) names.get(c0160c5);
    }

    public static Enumeration getNames() {
        return objIds.keys();
    }

    public static C0160c5 getOID(String str) {
        return (C0160c5) objIds.get(Strings.toLowerCase(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1316ux configureCurve(AbstractC1316ux abstractC1316ux) {
        return abstractC1316ux;
    }
}
