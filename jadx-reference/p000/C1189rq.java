package p000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: rq */
/* loaded from: classes2.dex */
public final class C1189rq implements xi1 {
    private static final Map<String, C1189rq> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 10), new C1189rq(1, "XMSS_SHA2_10_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 16), new C1189rq(2, "XMSS_SHA2_16_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 20), new C1189rq(3, "XMSS_SHA2_20_256"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 10), new C1189rq(4, "XMSS_SHA2_10_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 16), new C1189rq(5, "XMSS_SHA2_16_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 20), new C1189rq(6, "XMSS_SHA2_20_512"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 10), new C1189rq(7, "XMSS_SHAKE_10_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 16), new C1189rq(8, "XMSS_SHAKE_16_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 20), new C1189rq(9, "XMSS_SHAKE_20_256"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 10), new C1189rq(10, "XMSS_SHAKE_10_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 16), new C1189rq(11, "XMSS_SHAKE_16_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 20), new C1189rq(12, "XMSS_SHAKE_20_512"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private C1189rq(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3, int i4) {
        if (str == null) {
            throw new NullPointerException("algorithmName == null");
        }
        return str + "-" + i + "-" + i2 + "-" + i3 + "-" + i4;
    }

    public static C1189rq lookup(String str, int i, int i2, int i3, int i4) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3, i4));
        }
        throw new NullPointerException("algorithmName == null");
    }

    @Override // p000.xi1
    public int getOid() {
        return this.oid;
    }

    @Override // p000.xi1
    public String toString() {
        return this.stringRepresentation;
    }
}
