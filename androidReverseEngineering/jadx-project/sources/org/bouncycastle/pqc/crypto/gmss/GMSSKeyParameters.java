package org.bouncycastle.pqc.crypto.gmss;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class GMSSKeyParameters extends AsymmetricKeyParameter {
    private GMSSParameters params;

    public GMSSKeyParameters(boolean z2, GMSSParameters gMSSParameters) {
        super(z2);
        this.params = gMSSParameters;
    }

    public GMSSParameters getParameters() {
        return this.params;
    }
}
