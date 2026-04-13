package org.bouncycastle.pqc.crypto.lms;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.util.Encodable;

/* loaded from: classes.dex */
public abstract class LMSKeyParameters extends AsymmetricKeyParameter implements Encodable {
    public LMSKeyParameters(boolean z2) {
        super(z2);
    }

    public abstract byte[] getEncoded();
}
