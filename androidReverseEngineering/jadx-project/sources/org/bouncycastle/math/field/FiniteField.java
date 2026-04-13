package org.bouncycastle.math.field;

import java.math.BigInteger;

/* loaded from: classes.dex */
public interface FiniteField {
    BigInteger getCharacteristic();

    int getDimension();
}
