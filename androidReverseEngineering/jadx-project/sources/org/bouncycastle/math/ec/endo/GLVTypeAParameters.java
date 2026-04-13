package org.bouncycastle.math.ec.endo;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class GLVTypeAParameters {

    /* renamed from: i */
    protected final BigInteger f1493i;
    protected final BigInteger lambda;
    protected final ScalarSplitParameters splitParams;

    public GLVTypeAParameters(BigInteger bigInteger, BigInteger bigInteger2, ScalarSplitParameters scalarSplitParameters) {
        this.f1493i = bigInteger;
        this.lambda = bigInteger2;
        this.splitParams = scalarSplitParameters;
    }

    public BigInteger getI() {
        return this.f1493i;
    }

    public BigInteger getLambda() {
        return this.lambda;
    }

    public ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }
}
