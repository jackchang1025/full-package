package p000;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.util.Strings;
import p000.AbstractC1316ux;

/* renamed from: a0 */
/* loaded from: classes2.dex */
public class C0001a0 {
    static ci1 FRP256v1 = new a0();
    static final Hashtable objIds = new Hashtable();
    static final Hashtable curves = new Hashtable();
    static final Hashtable names = new Hashtable();

    /* renamed from: a0$a0 */
    public static class a0 extends ci1 {
        @Override // p000.ci1
        public bi1 createParameters() {
            BigInteger bigIntegerFromHex = C0001a0.fromHex("F1FD178C0B3AD58F10126DE8CE42435B3961ADBCABC8CA6DE8FCF353D86E9C03");
            BigInteger bigIntegerFromHex2 = C0001a0.fromHex("F1FD178C0B3AD58F10126DE8CE42435B3961ADBCABC8CA6DE8FCF353D86E9C00");
            BigInteger bigIntegerFromHex3 = C0001a0.fromHex("EE353FCA5428A9300D4ABA754A44C00FDFEC0C9AE4B1A1803075ED967B7BB73F");
            BigInteger bigIntegerFromHex4 = C0001a0.fromHex("F1FD178C0B3AD58F10126DE8CE42435B53DC67E140D2BF941FFDD459C6D655E1");
            BigInteger bigIntegerValueOf = BigInteger.valueOf(1L);
            AbstractC1316ux abstractC1316uxConfigureCurve = C0001a0.configureCurve(new AbstractC1316ux.a5(bigIntegerFromHex, bigIntegerFromHex2, bigIntegerFromHex3, bigIntegerFromHex4, bigIntegerValueOf));
            return new bi1(abstractC1316uxConfigureCurve, C0001a0.configureBasepoint(abstractC1316uxConfigureCurve, "04B6B3D4C356C139EB31183D4749D423958C27D2DCAF98B70164C97A2DD98F5CFF6142E0F7C8B204911F9271F0F3ECEF8C2701C307E8E4C9E183115A1554062CFB"), bigIntegerFromHex4, bigIntegerValueOf, null);
        }
    }

    static {
        defineCurve("FRP256v1", InterfaceC0002a1.FRP256v1, FRP256v1);
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
