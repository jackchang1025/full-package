package p000;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.util.Strings;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class n51 {
    static ci1 brainpoolP160r1 = new C0893a5();
    static ci1 brainpoolP160t1 = new C0894a6();
    static ci1 brainpoolP192r1 = new C0895a7();
    static ci1 brainpoolP192t1 = new C0896a8();
    static ci1 brainpoolP224r1 = new C0897a9();
    static ci1 brainpoolP224t1 = new C0898b0();
    static ci1 brainpoolP256r1 = new C0899b1();
    static ci1 brainpoolP256t1 = new C0900b2();
    static ci1 brainpoolP320r1 = new C0901b3();
    static ci1 brainpoolP320t1 = new C0888a0();
    static ci1 brainpoolP384r1 = new C0889a1();
    static ci1 brainpoolP384t1 = new C0890a2();
    static ci1 brainpoolP512r1 = new C0891a3();
    static ci1 brainpoolP512t1 = new C0892a4();
    static final Hashtable objIds = new Hashtable();
    static final Hashtable curves = new Hashtable();
    static final Hashtable names = new Hashtable();

    /* renamed from: n51$a0 */
    public static class C0888a0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("D35E472036BC4FB7E13C785ED201E065F98FCFA5B68F12A32D482EC7EE8658E98691555B44C59311");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E27"), n51.fromHex("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E24"), n51.fromHex("A7F561E038EB1ED560B3D147DB782013064C19F27ED27C6780AAF77FB8A547CEB5B4FEF422340353"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04925BE9FB01AFC6FB4D3E7D4990010F813408AB106C4F09CB7EE07868CC136FFF3357F624A21BED5263BA3A7A27483EBF6671DBEF7ABB30EBEE084E58A0B077AD42A5A0989D1EE71B1B9BC0455FB0D2C3"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a1 */
    public static class C0889a1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B31F166E6CAC0425A7CF3AB6AF6B7FC3103B883202E9046565");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC53"), n51.fromHex("7BC382C63D8C150C3C72080ACE05AFA0C2BEA28E4FB22787139165EFBA91F90F8AA5814A503AD4EB04A8C7DD22CE2826"), n51.fromHex("04A8C7DD22CE28268B39B55416F0447C2FB77DE107DCD2A62E880EA53EEB62D57CB4390295DBC9943AB78696FA504C11"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "041D1C64F068CF45FFA2A63A81B7C13F6B8847A3E77EF14FE3DB7FCAFE0CBD10E8E826E03436D646AAEF87B2E247D4AF1E8ABE1D7520F9C2A45CB1EB8E95CFD55262B70B29FEEC5864E19C054FF99129280E4646217791811142820341263C5315"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a2 */
    public static class C0890a2 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B31F166E6CAC0425A7CF3AB6AF6B7FC3103B883202E9046565");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC53"), n51.fromHex("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC50"), n51.fromHex("7F519EADA7BDA81BD826DBA647910F8C4B9346ED8CCDC64E4B1ABD11756DCE1D2074AA263B88805CED70355A33B471EE"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "0418DE98B02DB9A306F2AFCD7235F72A819B80AB12EBD653172476FECD462AABFFC4FF191B946A5F54D8D0AA2F418808CC25AB056962D30651A114AFD2755AD336747F93475B7A1FCA3B88F2B6A208CCFE469408584DC2B2912675BF5B9E582928"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a3 */
    public static class C0891a3 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA70330870553E5C414CA92619418661197FAC10471DB1D381085DDADDB58796829CA90069");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F3"), n51.fromHex("7830A3318B603B89E2327145AC234CC594CBDD8D3DF91610A83441CAEA9863BC2DED5D5AA8253AA10A2EF1C98B9AC8B57F1117A72BF2C7B9E7C1AC4D77FC94CA"), n51.fromHex("3DF91610A83441CAEA9863BC2DED5D5AA8253AA10A2EF1C98B9AC8B57F1117A72BF2C7B9E7C1AC4D77FC94CADC083E67984050B75EBAE5DD2809BD638016F723"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "0481AEE4BDD82ED9645A21322E9C4C6A9385ED9F70B5D916C1B43B62EEF4D0098EFF3B1F78E2D0D48D50D1687B93B97D5F7C6D5047406A5E688B352209BCB9F8227DDE385D566332ECC0EABFA9CF7822FDF209F70024A57B1AA000C55B881F8111B2DCDE494A5F485E5BCA4BD88A2763AED1CA2B2FA8F0540678CD1E0F3AD80892"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a4 */
    public static class C0892a4 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA70330870553E5C414CA92619418661197FAC10471DB1D381085DDADDB58796829CA90069");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F3"), n51.fromHex("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F0"), n51.fromHex("7CBBBCF9441CFAB76E1890E46884EAE321F70C0BCB4981527897504BEC3E36A62BCDFA2304976540F6450085F2DAE145C22553B465763689180EA2571867423E"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04640ECE5C12788717B9C1BA06CBC2A6FEBA85842458C56DDE9DB1758D39C0313D82BA51735CDB3EA499AA77A7D6943A64F7A3F25FE26F06B51BAA2696FA9035DA5B534BD595F5AF0FA2C892376C84ACE1BB4E3019B71634C01131159CAE03CEE9D9932184BEEF216BD71DF2DADF86A627306ECFF96DBB8BACE198B61E00F8B332"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a5 */
    public static class C0893a5 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("E95E4A5F737059DC60DF5991D45029409E60FC09");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("E95E4A5F737059DC60DFC7AD95B3D8139515620F"), n51.fromHex("340E7BE2A280EB74E2BE61BADA745D97E8F7C300"), n51.fromHex("1E589A8595423412134FAA2DBDEC95C8D8675E58"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC31667CB477A1A8EC338F94741669C976316DA6321"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a6 */
    public static class C0894a6 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("E95E4A5F737059DC60DF5991D45029409E60FC09");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("E95E4A5F737059DC60DFC7AD95B3D8139515620F"), n51.fromHex("E95E4A5F737059DC60DFC7AD95B3D8139515620C"), n51.fromHex("7A556B6DAE535B7B51ED2C4D7DAA7A0B5C55F380"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04B199B13B9B34EFC1397E64BAEB05ACC265FF2378ADD6718B7C7C1961F0991B842443772152C9E0AD"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a7 */
    public static class C0895a7 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("C302F41D932A36CDA7A3462F9E9E916B5BE8F1029AC4ACC1");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86297"), n51.fromHex("6A91174076B1E0E19C39C031FE8685C1CAE040E5C69A28EF"), n51.fromHex("469A28EF7C28CCA3DC721D044F4496BCCA7EF4146FBF25C9"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04C0A0647EAAB6A48753B033C56CB0F0900A2F5C4853375FD614B690866ABD5BB88B5F4828C1490002E6773FA2FA299B8F"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a8 */
    public static class C0896a8 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("C302F41D932A36CDA7A3462F9E9E916B5BE8F1029AC4ACC1");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86297"), n51.fromHex("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86294"), n51.fromHex("13D56FFAEC78681E68F9DEB43B35BEC2FB68542E27897B79"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "043AE9E58C82F63C30282E1FE7BBF43FA72C446AF6F4618129097E2C5667C2223A902AB5CA449D0084B7E5B3DE7CCC01C9"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$a9 */
    public static class C0897a9 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("D7C134AA264366862A18302575D0FB98D116BC4B6DDEBCA3A5A7939F");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FF"), n51.fromHex("68A5E62CA9CE6C1C299803A6C1530B514E182AD8B0042A59CAD29F43"), n51.fromHex("2580F63CCFE44138870713B1A92369E33E2135D266DBB372386C400B"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "040D9029AD2C7E5CF4340823B2A87DC68C9E4CE3174C1E6EFDEE12C07D58AA56F772C0726F24C6B89E4ECDAC24354B9E99CAA3F6D3761402CD"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$b0 */
    public static class C0898b0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("D7C134AA264366862A18302575D0FB98D116BC4B6DDEBCA3A5A7939F");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FF"), n51.fromHex("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FC"), n51.fromHex("4B337D934104CD7BEF271BF60CED1ED20DA14C08B3BB64F18A60888D"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "046AB1E344CE25FF3896424E7FFE14762ECB49F8928AC0C76029B4D5800374E9F5143E568CD23F3F4D7C0D4B1E41C8CC0D1C6ABD5F1A46DB4C"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$b1 */
    public static class C0899b1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("A9FB57DBA1EEA9BC3E660A909D838D718C397AA3B561A6F7901E0E82974856A7");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5377"), n51.fromHex("7D5A0975FC2C3057EEF67530417AFFE7FB8055C126DC5C6CE94A4B44F330B5D9"), n51.fromHex("26DC5C6CE94A4B44F330B5D9BBD77CBF958416295CF7E1CE6BCCDC18FF8C07B6"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "048BD2AEB9CB7E57CB2C4B482FFC81B7AFB9DE27E1E3BD23C23A4453BD9ACE3262547EF835C3DAC4FD97F8461A14611DC9C27745132DED8E545C1D54C72F046997"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$b2 */
    public static class C0900b2 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("A9FB57DBA1EEA9BC3E660A909D838D718C397AA3B561A6F7901E0E82974856A7");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5377"), n51.fromHex("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5374"), n51.fromHex("662C61C430D84EA4FE66A7733D0B76B7BF93EBC4AF2F49256AE58101FEE92B04"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "04A3E8EB3CC1CFE7B7732213B23A656149AFA142C47AAFBC2B79A191562E1305F42D996C823439C56D7F7B22E14644417E69BCB6DE39D027001DABE8F35B25C9BE"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    /* renamed from: n51$b3 */
    public static class C0901b3 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = n51.fromHex("D35E472036BC4FB7E13C785ED201E065F98FCFA5B68F12A32D482EC7EE8658E98691555B44C59311");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = n51.configureCurve(new AbstractC1316ux.a5(n51.fromHex("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E27"), n51.fromHex("3EE30B568FBAB0F883CCEBD46D3F3BB8A2A73513F5EB79DA66190EB085FFA9F492F375A97D860EB4"), n51.fromHex("520883949DFDBC42D3AD198640688A6FE13F41349554B49ACC31DCCD884539816F5EB4AC8FB1F1A6"), bigIntegerFromHex, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, n51.configureBasepoint(abstractC1316uxConfigureCurve, "0443BD7E9AFB53D8B85289BCC48EE5BFE6F20137D10A087EB6E7871E2A10A599C710AF8D0D39E2061114FDD05545EC1CC8AB4093247F77275E0743FFED117182EAA9C77877AAAC6AC7D35245D1692E8EE1"), bigIntegerFromHex, bigIntegerValueOf);
        }
    }

    static {
        defineCurve("brainpoolP160r1", o51.brainpoolP160r1, brainpoolP160r1);
        defineCurve("brainpoolP160t1", o51.brainpoolP160t1, brainpoolP160t1);
        defineCurve("brainpoolP192r1", o51.brainpoolP192r1, brainpoolP192r1);
        defineCurve("brainpoolP192t1", o51.brainpoolP192t1, brainpoolP192t1);
        defineCurve("brainpoolP224r1", o51.brainpoolP224r1, brainpoolP224r1);
        defineCurve("brainpoolP224t1", o51.brainpoolP224t1, brainpoolP224t1);
        defineCurve("brainpoolP256r1", o51.brainpoolP256r1, brainpoolP256r1);
        defineCurve("brainpoolP256t1", o51.brainpoolP256t1, brainpoolP256t1);
        defineCurve("brainpoolP320r1", o51.brainpoolP320r1, brainpoolP320r1);
        defineCurve("brainpoolP320t1", o51.brainpoolP320t1, brainpoolP320t1);
        defineCurve("brainpoolP384r1", o51.brainpoolP384r1, brainpoolP384r1);
        defineCurve("brainpoolP384t1", o51.brainpoolP384t1, brainpoolP384t1);
        defineCurve("brainpoolP512r1", o51.brainpoolP512r1, brainpoolP512r1);
        defineCurve("brainpoolP512t1", o51.brainpoolP512t1, brainpoolP512t1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static di1 configureBasepoint(AbstractC1316ux abstractC1316ux, String str) {
        di1 di1Var = new di1(abstractC1316ux, c40.decodeStrict(str));
        xd1.configureBasepoint(di1Var.getPoint());
        return di1Var;
    }

    public static void defineCurve(String str, C0160c5 c0160c5, ci1 ci1Var) {
        objIds.put(Strings.toLowerCase(str), c0160c5);
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
        return names.elements();
    }

    public static C0160c5 getOID(String str) {
        return (C0160c5) objIds.get(Strings.toLowerCase(str));
    }

    public static C0160c5 getOID(short s, boolean z) {
        StringBuilder sb = new StringBuilder("brainpoolP");
        sb.append((int) s);
        sb.append(z ? "t" : "r");
        sb.append("1");
        return getOID(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1316ux configureCurve(AbstractC1316ux abstractC1316ux) {
        return abstractC1316ux;
    }
}
