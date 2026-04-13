package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class SPHINCSPlusKeyParameters extends AsymmetricKeyParameter {
    final SPHINCSPlusParameters parameters;

    public SPHINCSPlusKeyParameters(boolean z2, SPHINCSPlusParameters sPHINCSPlusParameters) {
        super(z2);
        this.parameters = sPHINCSPlusParameters;
    }

    public SPHINCSPlusParameters getParameters() {
        return this.parameters;
    }
}
