package org.bouncycastle.pqc.crypto.sphincsplus;

/* loaded from: classes.dex */
class NodeEntry {
    final int nodeHeight;
    final byte[] nodeValue;

    public NodeEntry(byte[] bArr, int i2) {
        this.nodeValue = bArr;
        this.nodeHeight = i2;
    }
}
