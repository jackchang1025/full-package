package p000;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.crypto.xmss.C1001a3;

/* loaded from: classes2.dex */
public final class yi1 {
    private static final Map<Integer, yi1> paramsLookupTable;
    private final int height;

    /* renamed from: k */
    private final int f61324k;
    private final xi1 oid;
    private final String treeDigest;
    private final C0160c5 treeDigestOID;
    private final int treeDigestSize;
    private final int winternitzParameter;
    private final zd1 wotsPlusParams;

    static {
        HashMap map = new HashMap();
        Integer numValueOf = q60.valueOf(1);
        C0160c5 c0160c5 = lh0.id_sha256;
        map.put(numValueOf, new yi1(10, c0160c5));
        map.put(q60.valueOf(2), new yi1(16, c0160c5));
        map.put(q60.valueOf(3), new yi1(20, c0160c5));
        Integer numValueOf2 = q60.valueOf(4);
        C0160c5 c0160c52 = lh0.id_sha512;
        map.put(numValueOf2, new yi1(10, c0160c52));
        map.put(q60.valueOf(5), new yi1(16, c0160c52));
        map.put(q60.valueOf(6), new yi1(20, c0160c52));
        Integer numValueOf3 = q60.valueOf(7);
        C0160c5 c0160c53 = lh0.id_shake128;
        map.put(numValueOf3, new yi1(10, c0160c53));
        map.put(q60.valueOf(8), new yi1(16, c0160c53));
        map.put(q60.valueOf(9), new yi1(20, c0160c53));
        Integer numValueOf4 = q60.valueOf(10);
        C0160c5 c0160c54 = lh0.id_shake256;
        map.put(numValueOf4, new yi1(10, c0160c54));
        map.put(q60.valueOf(11), new yi1(16, c0160c54));
        map.put(q60.valueOf(12), new yi1(20, c0160c54));
        paramsLookupTable = Collections.unmodifiableMap(map);
    }

    public yi1(int i, C0160c5 c0160c5) {
        if (i < 2) {
            throw new IllegalArgumentException("height must be >= 2");
        }
        if (c0160c5 == null) {
            throw new NullPointerException("digest == null");
        }
        this.height = i;
        this.f61324k = determineMinK();
        String digestName = C1254tc.getDigestName(c0160c5);
        this.treeDigest = digestName;
        this.treeDigestOID = c0160c5;
        zd1 zd1Var = new zd1(c0160c5);
        this.wotsPlusParams = zd1Var;
        int treeDigestSize = zd1Var.getTreeDigestSize();
        this.treeDigestSize = treeDigestSize;
        int winternitzParameter = zd1Var.getWinternitzParameter();
        this.winternitzParameter = winternitzParameter;
        this.oid = C1189rq.lookup(digestName, treeDigestSize, winternitzParameter, zd1Var.getLen(), i);
    }

    private int determineMinK() {
        int i = 2;
        while (true) {
            int i2 = this.height;
            if (i > i2) {
                throw new IllegalStateException("should never happen...");
            }
            if ((i2 - i) % 2 == 0) {
                return i;
            }
            i++;
        }
    }

    public static yi1 lookupByOID(int i) {
        return paramsLookupTable.get(q60.valueOf(i));
    }

    public int getHeight() {
        return this.height;
    }

    public int getK() {
        return this.f61324k;
    }

    public int getLen() {
        return this.wotsPlusParams.getLen();
    }

    public xi1 getOid() {
        return this.oid;
    }

    public String getTreeDigest() {
        return this.treeDigest;
    }

    public C0160c5 getTreeDigestOID() {
        return this.treeDigestOID;
    }

    public int getTreeDigestSize() {
        return this.treeDigestSize;
    }

    public C1001a3 getWOTSPlus() {
        return new C1001a3(this.wotsPlusParams);
    }

    public int getWinternitzParameter() {
        return this.winternitzParameter;
    }

    public yi1(int i, InterfaceC1236sv interfaceC1236sv) {
        this(i, C1254tc.getDigestOID(interfaceC1236sv.getAlgorithmName()));
    }
}
