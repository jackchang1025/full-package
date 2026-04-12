package p000;

import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vc1 extends zc1 {

    /* renamed from: a6 */
    public final /* synthetic */ int f60620a6;

    @Override // p000.zc1
    /* renamed from: a1 */
    public final boolean mo214921a1(float f, long j, C1105qc c1105qc, View view) {
        switch (this.f60620a6) {
            case 0:
                view.setAlpha(m215392a0(f, j, c1105qc, view));
                break;
            case 1:
                view.setElevation(m215392a0(f, j, c1105qc, view));
                break;
            case 2:
                view.setRotation(m215392a0(f, j, c1105qc, view));
                break;
            case 3:
                view.setRotationX(m215392a0(f, j, c1105qc, view));
                break;
            case 4:
                view.setRotationY(m215392a0(f, j, c1105qc, view));
                break;
            case 5:
                view.setScaleX(m215392a0(f, j, c1105qc, view));
                break;
            case 6:
                view.setScaleY(m215392a0(f, j, c1105qc, view));
                break;
            case 7:
                view.setTranslationX(m215392a0(f, j, c1105qc, view));
                break;
            case 8:
                view.setTranslationY(m215392a0(f, j, c1105qc, view));
                break;
            default:
                view.setTranslationZ(m215392a0(f, j, c1105qc, view));
                break;
        }
        return this.f61504a3;
    }
}
