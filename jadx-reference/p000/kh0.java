package p000;

import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class kh0 {
    static final Hashtable objIds = new Hashtable();
    static final Hashtable names = new Hashtable();

    static {
        defineCurve("B-571", rs0.sect571r1);
        defineCurve("B-409", rs0.sect409r1);
        defineCurve("B-283", rs0.sect283r1);
        defineCurve("B-233", rs0.sect233r1);
        defineCurve("B-163", rs0.sect163r2);
        defineCurve("K-571", rs0.sect571k1);
        defineCurve("K-409", rs0.sect409k1);
        defineCurve("K-283", rs0.sect283k1);
        defineCurve("K-233", rs0.sect233k1);
        defineCurve("K-163", rs0.sect163k1);
        defineCurve("P-521", rs0.secp521r1);
        defineCurve("P-384", rs0.secp384r1);
        defineCurve("P-256", rs0.secp256r1);
        defineCurve("P-224", rs0.secp224r1);
        defineCurve("P-192", rs0.secp192r1);
    }

    public static void defineCurve(String str, C0160c5 c0160c5) {
        objIds.put(str, c0160c5);
        names.put(c0160c5, str);
    }

    public static bi1 getByName(String str) {
        C0160c5 c0160c5 = (C0160c5) objIds.get(Strings.toUpperCase(str));
        if (c0160c5 != null) {
            return getByOID(c0160c5);
        }
        return null;
    }

    public static bi1 getByOID(C0160c5 c0160c5) {
        return qs0.getByOID(c0160c5);
    }

    public static String getName(C0160c5 c0160c5) {
        return (String) names.get(c0160c5);
    }

    public static Enumeration getNames() {
        return objIds.keys();
    }

    public static C0160c5 getOID(String str) {
        return (C0160c5) objIds.get(Strings.toUpperCase(str));
    }
}
