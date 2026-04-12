package p000;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.util.Strings;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class f20 {
    static ci1 sm2p256v1 = new C0479a0();
    static ci1 wapip192v1 = new C0480a1();
    static final Hashtable objIds = new Hashtable();
    static final Hashtable curves = new Hashtable();
    static final Hashtable names = new Hashtable();

    /* renamed from: f20$a0 */
    public static class C0479a0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = f20.fromHex("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF");
            BigInteger bigIntegerFromHex2 = f20.fromHex("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC");
            BigInteger bigIntegerFromHex3 = f20.fromHex("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93");
            BigInteger bigIntegerFromHex4 = f20.fromHex("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = f20.configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex, bigIntegerFromHex2, bigIntegerFromHex3, bigIntegerFromHex4, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, f20.configureBasepoint(abstractC1316uxConfigureCurve, "0432C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"), bigIntegerFromHex4, bigIntegerValueOf, null);
        }
    }

    /* renamed from: f20$a1 */
    public static class C0480a1 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = f20.fromHex("BDB6F4FE3E8B1D9E0DA8C0D46F4C318CEFE4AFE3B6B8551F");
            BigInteger bigIntegerFromHex2 = f20.fromHex("BB8E5E8FBC115E139FE6A814FE48AAA6F0ADA1AA5DF91985");
            BigInteger bigIntegerFromHex3 = f20.fromHex("1854BEBDC31B21B7AEFC80AB0ECD10D5B1B3308E6DBF11C1");
            BigInteger bigIntegerFromHex4 = f20.fromHex("BDB6F4FE3E8B1D9E0DA8C0D40FC962195DFAE76F56564677");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = f20.configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex, bigIntegerFromHex2, bigIntegerFromHex3, bigIntegerFromHex4, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, f20.configureBasepoint(abstractC1316uxConfigureCurve, "044AD5F7048DE709AD51236DE65E4D4B482C836DC6E410664002BB3A02D4AAADACAE24817A4CA3A1B014B5270432DB27D2"), bigIntegerFromHex4, bigIntegerValueOf, null);
        }
    }

    static {
        defineCurve("wapip192v1", g20.wapip192v1, wapip192v1);
        defineCurve("sm2p256v1", g20.sm2p256v1, sm2p256v1);
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
        C0160c5 oid = getOID(str);
        if (oid == null) {
            return null;
        }
        return getByOID(oid);
    }

    public static bi1 getByOID(C0160c5 c0160c5) {
        ci1 ci1Var = (ci1) curves.get(c0160c5);
        if (ci1Var == null) {
            return null;
        }
        return ci1Var.getParameters();
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

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1316ux configureCurve(AbstractC1316ux abstractC1316ux) {
        return abstractC1316ux;
    }
}
