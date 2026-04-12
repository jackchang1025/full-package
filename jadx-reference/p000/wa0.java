package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class wa0 {

    /* renamed from: a0 */
    public AbstractC1371wc f60870a0;

    /* renamed from: a1 */
    public int f60871a1;

    /* renamed from: a2 */
    public int f60872a2;

    /* renamed from: a3 */
    public boolean f60873a3;

    /* renamed from: a4 */
    public boolean f60874a4;

    public wa0() {
        m215042a3();
    }

    /* renamed from: a0 */
    public final void m215039a0() {
        this.f60872a2 = this.f60873a3 ? this.f60870a0.mo214626a6() : this.f60870a0.mo214630b0();
    }

    /* renamed from: a1 */
    public final void m215040a1(View view, int i) {
        if (this.f60873a3) {
            int iMo214621a1 = this.f60870a0.mo214621a1(view);
            AbstractC1371wc abstractC1371wc = this.f60870a0;
            this.f60872a2 = (Integer.MIN_VALUE == abstractC1371wc.f60887a0 ? 0 : abstractC1371wc.mo214631b1() - abstractC1371wc.f60887a0) + iMo214621a1;
        } else {
            this.f60872a2 = this.f60870a0.mo214624a4(view);
        }
        this.f60871a1 = i;
    }

    /* renamed from: a2 */
    public final void m215041a2(View view, int i) {
        AbstractC1371wc abstractC1371wc = this.f60870a0;
        int iMo214631b1 = Integer.MIN_VALUE == abstractC1371wc.f60887a0 ? 0 : abstractC1371wc.mo214631b1() - abstractC1371wc.f60887a0;
        if (iMo214631b1 >= 0) {
            m215040a1(view, i);
            return;
        }
        this.f60871a1 = i;
        if (!this.f60873a3) {
            int iMo214624a4 = this.f60870a0.mo214624a4(view);
            int iMo214630b0 = iMo214624a4 - this.f60870a0.mo214630b0();
            this.f60872a2 = iMo214624a4;
            if (iMo214630b0 > 0) {
                int iMo214626a6 = (this.f60870a0.mo214626a6() - Math.min(0, (this.f60870a0.mo214626a6() - iMo214631b1) - this.f60870a0.mo214621a1(view))) - (this.f60870a0.mo214622a2(view) + iMo214624a4);
                if (iMo214626a6 < 0) {
                    this.f60872a2 -= Math.min(iMo214630b0, -iMo214626a6);
                    return;
                }
                return;
            }
            return;
        }
        int iMo214626a62 = (this.f60870a0.mo214626a6() - iMo214631b1) - this.f60870a0.mo214621a1(view);
        this.f60872a2 = this.f60870a0.mo214626a6() - iMo214626a62;
        if (iMo214626a62 > 0) {
            int iMo214622a2 = this.f60872a2 - this.f60870a0.mo214622a2(view);
            int iMo214630b02 = this.f60870a0.mo214630b0();
            int iMin = iMo214622a2 - (Math.min(this.f60870a0.mo214624a4(view) - iMo214630b02, 0) + iMo214630b02);
            if (iMin < 0) {
                this.f60872a2 = Math.min(iMo214626a62, -iMin) + this.f60872a2;
            }
        }
    }

    /* renamed from: a3 */
    public final void m215042a3() {
        this.f60871a1 = -1;
        this.f60872a2 = Integer.MIN_VALUE;
        this.f60873a3 = false;
        this.f60874a4 = false;
    }

    public final String toString() {
        return "AnchorInfo{mPosition=" + this.f60871a1 + ", mCoordinate=" + this.f60872a2 + ", mLayoutFromEnd=" + this.f60873a3 + ", mValid=" + this.f60874a4 + '}';
    }
}
