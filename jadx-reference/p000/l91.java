package p000;

import android.content.res.ColorStateList;
import android.graphics.Paint;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l91 extends o91 {

    /* renamed from: a3 */
    public C1401x4 f57847a3;

    /* renamed from: a4 */
    public float f57848a4;

    /* renamed from: a5 */
    public C1401x4 f57849a5;

    /* renamed from: a6 */
    public float f57850a6;

    /* renamed from: a7 */
    public float f57851a7;

    /* renamed from: a8 */
    public float f57852a8;

    /* renamed from: a9 */
    public float f57853a9;

    /* renamed from: b0 */
    public float f57854b0;

    /* renamed from: b1 */
    public Paint.Cap f57855b1;

    /* renamed from: b2 */
    public Paint.Join f57856b2;

    /* renamed from: b3 */
    public float f57857b3;

    @Override // p000.n91
    /* renamed from: a0 */
    public final boolean mo213797a0() {
        return this.f57849a5.m215117a2() || this.f57847a3.m215117a2();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    @Override // p000.n91
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean mo213798a1(int[] iArr) {
        boolean z;
        C1401x4 c1401x4 = this.f57849a5;
        boolean z2 = true;
        if (c1401x4.m215117a2()) {
            ColorStateList colorStateList = (ColorStateList) c1401x4.f61018a2;
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (colorForState != c1401x4.f61016a0) {
                c1401x4.f61016a0 = colorForState;
                z = true;
            } else {
                z = false;
            }
        }
        C1401x4 c1401x42 = this.f57847a3;
        if (c1401x42.m215117a2()) {
            ColorStateList colorStateList2 = (ColorStateList) c1401x42.f61018a2;
            int colorForState2 = colorStateList2.getColorForState(iArr, colorStateList2.getDefaultColor());
            if (colorForState2 != c1401x42.f61016a0) {
                c1401x42.f61016a0 = colorForState2;
            } else {
                z2 = false;
            }
        }
        return z | z2;
    }

    public float getFillAlpha() {
        return this.f57851a7;
    }

    public int getFillColor() {
        return this.f57849a5.f61016a0;
    }

    public float getStrokeAlpha() {
        return this.f57850a6;
    }

    public int getStrokeColor() {
        return this.f57847a3.f61016a0;
    }

    public float getStrokeWidth() {
        return this.f57848a4;
    }

    public float getTrimPathEnd() {
        return this.f57853a9;
    }

    public float getTrimPathOffset() {
        return this.f57854b0;
    }

    public float getTrimPathStart() {
        return this.f57852a8;
    }

    public void setFillAlpha(float f) {
        this.f57851a7 = f;
    }

    public void setFillColor(int i) {
        this.f57849a5.f61016a0 = i;
    }

    public void setStrokeAlpha(float f) {
        this.f57850a6 = f;
    }

    public void setStrokeColor(int i) {
        this.f57847a3.f61016a0 = i;
    }

    public void setStrokeWidth(float f) {
        this.f57848a4 = f;
    }

    public void setTrimPathEnd(float f) {
        this.f57853a9 = f;
    }

    public void setTrimPathOffset(float f) {
        this.f57854b0 = f;
    }

    public void setTrimPathStart(float f) {
        this.f57852a8 = f;
    }
}
