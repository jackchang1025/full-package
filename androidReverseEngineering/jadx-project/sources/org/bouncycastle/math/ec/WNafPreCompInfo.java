package org.bouncycastle.math.ec;

/* loaded from: classes.dex */
public class WNafPreCompInfo implements PreCompInfo {
    volatile int promotionCountdown = 4;
    protected int confWidth = -1;
    protected ECPoint[] preComp = null;
    protected ECPoint[] preCompNeg = null;
    protected ECPoint twice = null;
    protected int width = -1;

    public int decrementPromotionCountdown() {
        int i2 = this.promotionCountdown;
        if (i2 <= 0) {
            return i2;
        }
        int i3 = i2 - 1;
        this.promotionCountdown = i3;
        return i3;
    }

    public int getConfWidth() {
        return this.confWidth;
    }

    public ECPoint[] getPreComp() {
        return this.preComp;
    }

    public ECPoint[] getPreCompNeg() {
        return this.preCompNeg;
    }

    public int getPromotionCountdown() {
        return this.promotionCountdown;
    }

    public ECPoint getTwice() {
        return this.twice;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isPromoted() {
        return this.promotionCountdown <= 0;
    }

    public void setConfWidth(int i2) {
        this.confWidth = i2;
    }

    public void setPreComp(ECPoint[] eCPointArr) {
        this.preComp = eCPointArr;
    }

    public void setPreCompNeg(ECPoint[] eCPointArr) {
        this.preCompNeg = eCPointArr;
    }

    public void setPromotionCountdown(int i2) {
        this.promotionCountdown = i2;
    }

    public void setTwice(ECPoint eCPoint) {
        this.twice = eCPoint;
    }

    public void setWidth(int i2) {
        this.width = i2;
    }
}
