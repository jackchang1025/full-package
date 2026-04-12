package p000;

/* loaded from: classes2.dex */
public final class zd1 {
    private final int digestSize;
    private final int len;
    private final int len1;
    private final int len2;
    private final xi1 oid;
    private final C0160c5 treeDigest;
    private final int winternitzParameter;

    public zd1(C0160c5 c0160c5) {
        if (c0160c5 == null) {
            throw new NullPointerException("treeDigest == null");
        }
        this.treeDigest = c0160c5;
        InterfaceC1236sv digest = C1254tc.getDigest(c0160c5);
        int digestSize = fj1.getDigestSize(digest);
        this.digestSize = digestSize;
        this.winternitzParameter = 16;
        int iCeil = (int) Math.ceil((digestSize * 8) / fj1.log2(16));
        this.len1 = iCeil;
        int iFloor = ((int) Math.floor(fj1.log2((16 - 1) * iCeil) / fj1.log2(16))) + 1;
        this.len2 = iFloor;
        int i = iCeil + iFloor;
        this.len = i;
        yd1 yd1VarLookup = yd1.lookup(digest.getAlgorithmName(), digestSize, 16, i);
        this.oid = yd1VarLookup;
        if (yd1VarLookup != null) {
            return;
        }
        throw new IllegalArgumentException("cannot find OID for digest algorithm: " + digest.getAlgorithmName());
    }

    public int getLen() {
        return this.len;
    }

    public int getLen1() {
        return this.len1;
    }

    public int getLen2() {
        return this.len2;
    }

    public xi1 getOid() {
        return this.oid;
    }

    public C0160c5 getTreeDigest() {
        return this.treeDigest;
    }

    public int getTreeDigestSize() {
        return this.digestSize;
    }

    public int getWinternitzParameter() {
        return this.winternitzParameter;
    }
}
