package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

/* loaded from: classes.dex */
public interface LMSPrivateKey extends LMSKey, PrivateKey {
    LMSPrivateKey extractKeyShard(int i2);

    long getIndex();

    long getUsagesRemaining();
}
