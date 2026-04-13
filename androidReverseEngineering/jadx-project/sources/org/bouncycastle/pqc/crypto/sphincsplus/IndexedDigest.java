package org.bouncycastle.pqc.crypto.sphincsplus;

/* loaded from: classes.dex */
class IndexedDigest {
    final byte[] digest;
    final int idx_leaf;
    final long idx_tree;

    public IndexedDigest(long j2, int i2, byte[] bArr) {
        this.idx_tree = j2;
        this.idx_leaf = i2;
        this.digest = bArr;
    }
}
