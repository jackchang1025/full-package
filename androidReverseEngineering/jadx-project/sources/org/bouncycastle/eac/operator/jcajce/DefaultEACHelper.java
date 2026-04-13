package org.bouncycastle.eac.operator.jcajce;

import java.security.Signature;

/* loaded from: classes.dex */
class DefaultEACHelper extends EACHelper {
    @Override // org.bouncycastle.eac.operator.jcajce.EACHelper
    public Signature createSignature(String str) {
        return Signature.getInstance(str);
    }
}
