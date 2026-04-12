package p000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: rp */
/* loaded from: classes2.dex */
public final class C1188rp implements xi1 {
    private static final Map<String, C1188rp> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 20, 2), new C1188rp(1, "XMSSMT_SHA2_20/2_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 20, 4), new C1188rp(2, "XMSSMT_SHA2_20/4_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 40, 2), new C1188rp(3, "XMSSMT_SHA2_40/2_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 40, 2), new C1188rp(4, "XMSSMT_SHA2_40/4_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 40, 4), new C1188rp(5, "XMSSMT_SHA2_40/8_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 60, 8), new C1188rp(6, "XMSSMT_SHA2_60/3_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 60, 6), new C1188rp(7, "XMSSMT_SHA2_60/6_256"));
        map.put(createKey(ki1.SHA_256, 32, 16, 67, 60, 12), new C1188rp(8, "XMSSMT_SHA2_60/12_256"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 20, 2), new C1188rp(9, "XMSSMT_SHA2_20/2_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 20, 4), new C1188rp(10, "XMSSMT_SHA2_20/4_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 40, 2), new C1188rp(11, "XMSSMT_SHA2_40/2_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 40, 4), new C1188rp(12, "XMSSMT_SHA2_40/4_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 40, 8), new C1188rp(13, "XMSSMT_SHA2_40/8_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 60, 3), new C1188rp(14, "XMSSMT_SHA2_60/3_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 60, 6), new C1188rp(15, "XMSSMT_SHA2_60/6_512"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131, 60, 12), new C1188rp(16, "XMSSMT_SHA2_60/12_512"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 20, 2), new C1188rp(17, "XMSSMT_SHAKE_20/2_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 20, 4), new C1188rp(18, "XMSSMT_SHAKE_20/4_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 40, 2), new C1188rp(19, "XMSSMT_SHAKE_40/2_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 40, 4), new C1188rp(20, "XMSSMT_SHAKE_40/4_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 40, 8), new C1188rp(21, "XMSSMT_SHAKE_40/8_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 60, 3), new C1188rp(22, "XMSSMT_SHAKE_60/3_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 60, 6), new C1188rp(23, "XMSSMT_SHAKE_60/6_256"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67, 60, 12), new C1188rp(24, "XMSSMT_SHAKE_60/12_256"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 20, 2), new C1188rp(25, "XMSSMT_SHAKE_20/2_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 20, 4), new C1188rp(26, "XMSSMT_SHAKE_20/4_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 40, 2), new C1188rp(27, "XMSSMT_SHAKE_40/2_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 40, 4), new C1188rp(28, "XMSSMT_SHAKE_40/4_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 40, 8), new C1188rp(29, "XMSSMT_SHAKE_40/8_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 60, 3), new C1188rp(30, "XMSSMT_SHAKE_60/3_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 60, 6), new C1188rp(31, "XMSSMT_SHAKE_60/6_512"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131, 60, 12), new C1188rp(32, "XMSSMT_SHAKE_60/12_512"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private C1188rp(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new NullPointerException("algorithmName == null");
        }
        return str + "-" + i + "-" + i2 + "-" + i3 + "-" + i4 + "-" + i5;
    }

    public static C1188rp lookup(String str, int i, int i2, int i3, int i4, int i5) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3, i4, i5));
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
