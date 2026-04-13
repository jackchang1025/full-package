package org.bouncycastle.crypto.prng.drbg;

import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
public class DualECPoints {
    private final int cofactor;

    /* renamed from: p */
    private final ECPoint f1342p;

    /* renamed from: q */
    private final ECPoint f1343q;
    private final int securityStrength;

    public DualECPoints(int i2, ECPoint eCPoint, ECPoint eCPoint2, int i3) {
        if (!eCPoint.getCurve().equals(eCPoint2.getCurve())) {
            throw new IllegalArgumentException("points need to be on the same curve");
        }
        this.securityStrength = i2;
        this.f1342p = eCPoint;
        this.f1343q = eCPoint2;
        this.cofactor = i3;
    }

    private static int log2(int i2) {
        int i3 = 0;
        while (true) {
            i2 >>= 1;
            if (i2 == 0) {
                return i3;
            }
            i3++;
        }
    }

    public int getCofactor() {
        return this.cofactor;
    }

    public int getMaxOutlen() {
        return ((this.f1342p.getCurve().getFieldSize() - (log2(this.cofactor) + 13)) / 8) * 8;
    }

    public ECPoint getP() {
        return this.f1342p;
    }

    public ECPoint getQ() {
        return this.f1343q;
    }

    public int getSecurityStrength() {
        return this.securityStrength;
    }

    public int getSeedLen() {
        return this.f1342p.getCurve().getFieldSize();
    }
}
