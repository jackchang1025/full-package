package org.bouncycastle.pqc.jcajce.interfaces;

import java.security.PrivateKey;

/* loaded from: classes.dex */
public interface XMSSMTPrivateKey extends XMSSMTKey, PrivateKey {
    XMSSMTPrivateKey extractKeyShard(int i2);

    long getIndex();

    long getUsagesRemaining();
}
