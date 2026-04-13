package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class McElieceCCA2KeyParameters extends AsymmetricKeyParameter {
    private String params;

    public McElieceCCA2KeyParameters(boolean z2, String str) {
        super(z2);
        this.params = str;
    }

    public String getDigest() {
        return this.params;
    }
}
