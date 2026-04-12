package p000;

/* loaded from: classes2.dex */
public class wd1 implements ao0 {
    volatile int promotionCountdown = 4;
    protected int confWidth = -1;
    protected AbstractC1341vl[] preComp = null;
    protected AbstractC1341vl[] preCompNeg = null;
    protected AbstractC1341vl twice = null;
    protected int width = -1;

    public int decrementPromotionCountdown() {
        int i = this.promotionCountdown;
        if (i <= 0) {
            return i;
        }
        int i2 = i - 1;
        this.promotionCountdown = i2;
        return i2;
    }

    public int getConfWidth() {
        return this.confWidth;
    }

    public AbstractC1341vl[] getPreComp() {
        return this.preComp;
    }

    public AbstractC1341vl[] getPreCompNeg() {
        return this.preCompNeg;
    }

    public int getPromotionCountdown() {
        return this.promotionCountdown;
    }

    public AbstractC1341vl getTwice() {
        return this.twice;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isPromoted() {
        return this.promotionCountdown <= 0;
    }

    public void setConfWidth(int i) {
        this.confWidth = i;
    }

    public void setPreComp(AbstractC1341vl[] abstractC1341vlArr) {
        this.preComp = abstractC1341vlArr;
    }

    public void setPreCompNeg(AbstractC1341vl[] abstractC1341vlArr) {
        this.preCompNeg = abstractC1341vlArr;
    }

    public void setPromotionCountdown(int i) {
        this.promotionCountdown = i;
    }

    public void setTwice(AbstractC1341vl abstractC1341vl) {
        this.twice = abstractC1341vl;
    }

    public void setWidth(int i) {
        this.width = i;
    }
}
