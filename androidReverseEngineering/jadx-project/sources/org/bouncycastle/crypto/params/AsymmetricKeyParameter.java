package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class AsymmetricKeyParameter implements CipherParameters {
    boolean privateKey;

    public AsymmetricKeyParameter(boolean z2) {
        this.privateKey = z2;
    }

    public boolean isPrivate() {
        return this.privateKey;
    }
}
