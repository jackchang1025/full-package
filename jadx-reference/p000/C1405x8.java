package p000;

import androidx.appcompat.widget.AppCompatSpinner;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x8 */
/* loaded from: classes.dex */
public final class C1405x8 extends AbstractViewOnTouchListenerC1358w {

    /* renamed from: a9 */
    public final /* synthetic */ C1446y6 f61035a9;

    /* renamed from: b0 */
    public final /* synthetic */ AppCompatSpinner f61036b0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1405x8(AppCompatSpinner appCompatSpinner, AppCompatSpinner appCompatSpinner2, C1446y6 c1446y6) {
        super(appCompatSpinner2);
        this.f61036b0 = appCompatSpinner;
        this.f61035a9 = c1446y6;
    }

    @Override // p000.AbstractViewOnTouchListenerC1358w
    /* renamed from: a1 */
    public final p01 mo213948a1() {
        return this.f61035a9;
    }

    @Override // p000.AbstractViewOnTouchListenerC1358w
    /* renamed from: a2 */
    public final boolean mo213949a2() {
        AppCompatSpinner appCompatSpinner = this.f61036b0;
        if (appCompatSpinner.getInternalPopup().mo215224a1()) {
            return true;
        }
        appCompatSpinner.f43930a5.mo215232b3(AbstractC1440y0.m215220a1(appCompatSpinner), AbstractC1440y0.m215219a0(appCompatSpinner));
        return true;
    }
}
