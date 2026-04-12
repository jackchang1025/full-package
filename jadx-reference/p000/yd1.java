package p000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class yd1 implements xi1 {
    private static final Map<String, yd1> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(ki1.SHA_256, 32, 16, 67), new yd1(16777217, "WOTSP_SHA2-256_W16"));
        map.put(createKey(ki1.SHA_512, 64, 16, 131), new yd1(33554434, "WOTSP_SHA2-512_W16"));
        map.put(createKey(ki1.SHAKE128, 32, 16, 67), new yd1(50331651, "WOTSP_SHAKE128_W16"));
        map.put(createKey(ki1.SHAKE256, 64, 16, 131), new yd1(67108868, "WOTSP_SHAKE256_W16"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private yd1(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3) {
        if (str == null) {
            throw new NullPointerException("algorithmName == null");
        }
        return str + "-" + i + "-" + i2 + "-" + i3;
    }

    public static yd1 lookup(String str, int i, int i2, int i3) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3));
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
