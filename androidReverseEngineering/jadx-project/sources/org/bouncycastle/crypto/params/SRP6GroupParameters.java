package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class SRP6GroupParameters {

    /* renamed from: N */
    private BigInteger f1334N;

    /* renamed from: g */
    private BigInteger f1335g;

    public SRP6GroupParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f1334N = bigInteger;
        this.f1335g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f1335g;
    }

    public BigInteger getN() {
        return this.f1334N;
    }
}
