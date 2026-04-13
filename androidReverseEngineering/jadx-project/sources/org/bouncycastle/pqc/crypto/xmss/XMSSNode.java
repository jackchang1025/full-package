package org.bouncycastle.pqc.crypto.xmss;

import java.io.Serializable;

/* loaded from: classes.dex */
public final class XMSSNode implements Serializable {
    private static final long serialVersionUID = 1;
    private final int height;
    private final byte[] value;

    public XMSSNode(int i2, byte[] bArr) {
        this.height = i2;
        this.value = bArr;
    }

    public int getHeight() {
        return this.height;
    }

    public byte[] getValue() {
        return XMSSUtil.cloneArray(this.value);
    }
}
