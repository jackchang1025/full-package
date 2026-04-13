package org.bouncycastle.tsp.ers;

import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public interface ERSData {
    byte[] getHash(DigestCalculator digestCalculator);
}
