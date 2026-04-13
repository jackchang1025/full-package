package org.bouncycastle.math.ec;

/* loaded from: classes.dex */
public interface ECLookupTable {
    int getSize();

    ECPoint lookup(int i2);

    ECPoint lookupVar(int i2);
}
