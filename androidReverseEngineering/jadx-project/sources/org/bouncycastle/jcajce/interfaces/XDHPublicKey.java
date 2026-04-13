package org.bouncycastle.jcajce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

/* loaded from: classes.dex */
public interface XDHPublicKey extends XDHKey, PublicKey {
    BigInteger getU();

    byte[] getUEncoding();
}
