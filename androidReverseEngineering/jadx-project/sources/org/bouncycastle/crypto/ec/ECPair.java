package org.bouncycastle.crypto.ec;

import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
public class ECPair {

    /* renamed from: x */
    private final ECPoint f1198x;

    /* renamed from: y */
    private final ECPoint f1199y;

    public ECPair(ECPoint eCPoint, ECPoint eCPoint2) {
        this.f1198x = eCPoint;
        this.f1199y = eCPoint2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ECPair) {
            return equals((ECPair) obj);
        }
        return false;
    }

    public ECPoint getX() {
        return this.f1198x;
    }

    public ECPoint getY() {
        return this.f1199y;
    }

    public int hashCode() {
        return (this.f1199y.hashCode() * 37) + this.f1198x.hashCode();
    }

    public boolean equals(ECPair eCPair) {
        return eCPair.getX().equals(getX()) && eCPair.getY().equals(getY());
    }
}
