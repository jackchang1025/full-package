package org.bouncycastle.pqc.crypto.rainbow;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class RainbowKeyParameters extends AsymmetricKeyParameter {
    private int docLength;

    public RainbowKeyParameters(boolean z2, int i2) {
        super(z2);
        this.docLength = i2;
    }

    public int getDocLength() {
        return this.docLength;
    }
}
