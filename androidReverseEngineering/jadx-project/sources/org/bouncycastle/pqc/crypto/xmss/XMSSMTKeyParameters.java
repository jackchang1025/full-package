package org.bouncycastle.pqc.crypto.xmss;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class XMSSMTKeyParameters extends AsymmetricKeyParameter {
    private final String treeDigest;

    public XMSSMTKeyParameters(boolean z2, String str) {
        super(z2);
        this.treeDigest = str;
    }

    public String getTreeDigest() {
        return this.treeDigest;
    }
}
