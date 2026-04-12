package p000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.crypto.xmss.C1001a3;

/* loaded from: classes2.dex */
public final class qi1 {
    private static final Map<Integer, qi1> paramsLookupTable;
    private final int height;
    private final int layers;
    private final xi1 oid;
    private final yi1 xmssParams;

    static {
        HashMap map = new HashMap();
        Integer numValueOf = q60.valueOf(1);
        C0160c5 c0160c5 = lh0.id_sha256;
        map.put(numValueOf, new qi1(20, 2, c0160c5));
        tz0.m214804a4(20, 4, c0160c5, map, q60.valueOf(2));
        tz0.m214804a4(40, 2, c0160c5, map, q60.valueOf(3));
        tz0.m214804a4(40, 4, c0160c5, map, q60.valueOf(4));
        tz0.m214804a4(40, 8, c0160c5, map, q60.valueOf(5));
        tz0.m214804a4(60, 3, c0160c5, map, q60.valueOf(6));
        tz0.m214804a4(60, 6, c0160c5, map, q60.valueOf(7));
        tz0.m214804a4(60, 12, c0160c5, map, q60.valueOf(8));
        Integer numValueOf2 = q60.valueOf(9);
        C0160c5 c0160c52 = lh0.id_sha512;
        map.put(numValueOf2, new qi1(20, 2, c0160c52));
        tz0.m214804a4(20, 4, c0160c52, map, q60.valueOf(10));
        tz0.m214804a4(40, 2, c0160c52, map, q60.valueOf(11));
        tz0.m214804a4(40, 4, c0160c52, map, q60.valueOf(12));
        tz0.m214804a4(40, 8, c0160c52, map, q60.valueOf(13));
        tz0.m214804a4(60, 3, c0160c52, map, q60.valueOf(14));
        tz0.m214804a4(60, 6, c0160c52, map, q60.valueOf(15));
        tz0.m214804a4(60, 12, c0160c52, map, q60.valueOf(16));
        Integer numValueOf3 = q60.valueOf(17);
        C0160c5 c0160c53 = lh0.id_shake128;
        map.put(numValueOf3, new qi1(20, 2, c0160c53));
        tz0.m214804a4(20, 4, c0160c53, map, q60.valueOf(18));
        tz0.m214804a4(40, 2, c0160c53, map, q60.valueOf(19));
        tz0.m214804a4(40, 4, c0160c53, map, q60.valueOf(20));
        tz0.m214804a4(40, 8, c0160c53, map, q60.valueOf(21));
        tz0.m214804a4(60, 3, c0160c53, map, q60.valueOf(22));
        tz0.m214804a4(60, 6, c0160c53, map, q60.valueOf(23));
        tz0.m214804a4(60, 12, c0160c53, map, q60.valueOf(24));
        Integer numValueOf4 = q60.valueOf(25);
        C0160c5 c0160c54 = lh0.id_shake256;
        map.put(numValueOf4, new qi1(20, 2, c0160c54));
        tz0.m214804a4(20, 4, c0160c54, map, q60.valueOf(26));
        tz0.m214804a4(40, 2, c0160c54, map, q60.valueOf(27));
        tz0.m214804a4(40, 4, c0160c54, map, q60.valueOf(28));
        tz0.m214804a4(40, 8, c0160c54, map, q60.valueOf(29));
        tz0.m214804a4(60, 3, c0160c54, map, q60.valueOf(30));
        tz0.m214804a4(60, 6, c0160c54, map, q60.valueOf(31));
        tz0.m214804a4(60, 12, c0160c54, map, q60.valueOf(32));
        paramsLookupTable = Collections.unmodifiableMap(map);
    }

    public qi1(int i, int i2, C0160c5 c0160c5) {
        this.height = i;
        this.layers = i2;
        this.xmssParams = new yi1(xmssTreeHeight(i, i2), c0160c5);
        this.oid = C1188rp.lookup(getTreeDigest(), getTreeDigestSize(), getWinternitzParameter(), getLen(), getHeight(), i2);
    }

    public static qi1 lookupByOID(int i) {
        return paramsLookupTable.get(q60.valueOf(i));
    }

    private static int xmssTreeHeight(int i, int i2) throws IllegalArgumentException {
        if (i < 2) {
            throw new IllegalArgumentException("totalHeight must be > 1");
        }
        if (i % i2 != 0) {
            throw new IllegalArgumentException("layers must divide totalHeight without remainder");
        }
        int i3 = i / i2;
        if (i3 != 1) {
            return i3;
        }
        throw new IllegalArgumentException("height / layers must be greater than 1");
    }

    public int getHeight() {
        return this.height;
    }

    public int getLayers() {
        return this.layers;
    }

    public int getLen() {
        return this.xmssParams.getLen();
    }

    public xi1 getOid() {
        return this.oid;
    }

    public String getTreeDigest() {
        return this.xmssParams.getTreeDigest();
    }

    public C0160c5 getTreeDigestOID() {
        return this.xmssParams.getTreeDigestOID();
    }

    public int getTreeDigestSize() {
        return this.xmssParams.getTreeDigestSize();
    }

    public C1001a3 getWOTSPlus() {
        return this.xmssParams.getWOTSPlus();
    }

    public int getWinternitzParameter() {
        return this.xmssParams.getWinternitzParameter();
    }

    public yi1 getXMSSParameters() {
        return this.xmssParams;
    }

    public qi1(int i, int i2, InterfaceC1236sv interfaceC1236sv) {
        this(i, i2, C1254tc.getDigestOID(interfaceC1236sv.getAlgorithmName()));
    }
}
