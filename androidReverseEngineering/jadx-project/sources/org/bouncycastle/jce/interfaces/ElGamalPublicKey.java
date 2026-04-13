package org.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;

/* loaded from: classes.dex */
public interface ElGamalPublicKey extends ElGamalKey, DHPublicKey {
    BigInteger getY();
}
