package org.bouncycastle.eac.jcajce;

import java.security.KeyFactory;

/* loaded from: classes.dex */
class DefaultEACHelper implements EACHelper {
    @Override // org.bouncycastle.eac.jcajce.EACHelper
    public KeyFactory createKeyFactory(String str) {
        return KeyFactory.getInstance(str);
    }
}
